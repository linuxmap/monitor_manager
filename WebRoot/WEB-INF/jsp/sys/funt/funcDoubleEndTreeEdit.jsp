<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <title>功能菜单维护</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	
	<link rel="stylesheet" href="${sys_ctx}/plug/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" href="${sys_ctx}/css/tree/treeStyle.css" type="text/css">  
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript" src="${sys_ctx }/plug/ztree/js/jquery.ztree.excheck.min.js"></script>
	<script type="text/javascript" src="${sys_ctx }/plug/ztree/js/jquery.ztree.exedit.min.js"></script>
	<script type="text/javascript" src="${sys_ctx }/plug/ztree/js/jquery.ztree.exhide.min.js"></script>
	<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>
	<!--[if lt IE 9]>
		<style>
		.jiedian{
				height:40px;
				width:100%;
				background-image:url(${sys_ctx}/css/jianbian.png);
				background-repeat: no-repeat;
			}
		html{
			height:100%;
		}
		</style>
	<![endif]-->
	<style type="text/css">
		.hader{
			height: 42px;
			background: #000;
			color: #fff;
			line-height: 42px;
			padding-left: 10px;
		}
		.digbox{
			width: 300px;
			height:200px;
			border: 1px solid #eee;
			position: fixed;
			z-index: 200;
			background: #fff;
			top:50%;
			left:50%;
			margin-left: -150px;
			margin-top: -100px;
		}
		.btn-bot{
			position: absolute;
			bottom:10px;
			width: 100%;
			text-align: center;
		}
		.mask{
			width: 100%;
			height:100%;
			background: #666;
			opacity: 0.5;
			position: fixed;
			top:0;
			left: 0;
			z-index: 199;
		}
		.centant{
			padding: 20px;
			font-size: 14px;
		}
		.dig-bigbox{/*只需要控制此类名的显示隐藏即可  */
			
			display: none;
			
		}
	</style>
  </head>
  <body style="width:100%;height:100%;overflow:hidden;">
    <div style="width:100%;height:100%;"class="background">
			<div style="width:20%;height:100%;float: left;" class="margin-top">	
				<div class="jiedian">
					<div class="jd-title">菜单组织结构</div>
				</div>	
				<ul id="tree" class="ztree scroll-tree"></ul>
			</div>
			
			<div  style="width:80%;height:100%;float: right" >
				<iframe name="orgList" id="orgList" frameborder="0"  style="width:100%;height:100%;" ></iframe>
		    </div>
		     
		</div>
		<!--  tree删除提示框  -->
		<div class="dig-bigbox">
			<div class="digbox">
				<div class="hader">
					信息提示框
				</div>
				<p class="centant">确认删除系统文件吗？</p>
				<div class="btn-bot">
					<button class="save">确定</button>
					<button class="cancel">取消</button>
				</div>
			</div> 
			<div class="mask"></div>
	 	</div>
  </body>
   <script>
//注意：折叠面板 依赖 element 模块，否则无法进行功能性操作
 layui.use(['element','layer'], function(){
  var element = layui.element();
  
  //…
});
</script>
  <script type="text/javascript">
 	var setting = {	
		view: { 
			addHoverDom: addHoverDomFunc,
			removeHoverDom: removeHoverDomFunc, 
	        selectedMulti: false  
	    },
		callback: {
				beforeDrag:beforeDragFunc,
				beforeRemove : beforeRemoveFunc,
				beforeRename : beforeRenameFunc,
				//onRemove: onRemoveFunc,
				onRename : onRenameFunc,
				onDrag : onDragFunc,
				onDrop : onDropFunc,
				onClick : onClickFunc				
			}, 
		data: {  
            simpleData: {   //简单的数据源<pre class="html" name="code">           
             enable: true, 
             open:true, 
             idKey: "id",  //id和pid，树的目录级别  
             pIdKey: "pId",  
             rootPId: 0   //根节点  
            }  
        },
        edit: {
				enable: true,
				drag: {
			          prev: true,
			          next: true,
			          inner: true
		        },
		        removeTitle: "删除节点",
		        renameTitle: "编辑节点名称"
			} 
 	};
 	var newCount = 1;
 			//添加节点
		function addHoverDomFunc(treeId, treeNode) {
			var id ;
			var sObj = $("#" + treeNode.tId + "_span");
			if ($("#addBtn_"+treeNode.id).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.id+ "' title='增加节点'  ></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.id);
			if (btn){btn.bind("click", function(){
				var level = treeNode.level;
				if(level<3){//只能有三级菜单
					var zTree = $.fn.zTree.getZTreeObj("tree");
					var newName="new node" + (newCount++);
					var newPId = treeNode.id;
					var newLeaf = treeNode.leaf;
					var newurl = "";
					
					$.ajax({
						async:false,
						type:"POST",
						url:"<%=path%>/doubleFunt/addFuntNode.action",
						cache: false,
						data: {
			    		'funtParId':newPId,
			    		'funtName':newName
			    		},  
			            dataType : "json",  
			            success: function(data){
			            	if(data.success) {
			            	   id = data.msg;
			            	   zTree.addNodes(treeNode, {id:id, pId:newPId, name:newName,url:newurl});
	         				}
	         			}
		 			});	
				}else {
					layer.alert("操作按钮不允许添加", {title:'提示信息',icon: 5});
				}
				return false;
			});
			}
		};
		function removeHoverDomFunc(treeId, treeNode) {
			$("#addBtn_"+treeNode.id).unbind().remove();
		};
 	
 	var treeObj = null;
 	//初始化功能菜单树
	$(function(){
  		$.ajax({
			type:"POST",
			url:"<%=path%>/doubleFunt/selectAllfunt.action",
			cache: false,  
            dataType : "json",  
            success: function(data){  
            	if(true) {
            		var ztree = data.ower;
            		$.fn.zTree.init($("#tree"),setting,ztree);
            		treeObj = $.fn.zTree.getZTreeObj("tree"); 
            		var nodes = treeObj.getNodes();
	            	/*  在初始加载树形控件的时候调用zTree的expandNode (node, expandFlag, sonSign, focus, callbackFlag)方法
						node：树形节点
						expandFlag:是否展开节点
						sonSign：是否展开其子孙节点
						focus：展开或折叠节点后是否设置焦点 
						callbackFlag：这行该方法是否触发回调函数 */
		            for (var i = 0; i < nodes.length; i++) { //设置节点展开
		                treeObj.expandNode(nodes[i], true, false, false);
		            }
            	}
         	}
		 });
  		
  	});
		function beforeDragFunc(treeId, treeNodes) {
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].drag === false) {
					return false;
				}
			}
			return true;
		};
		
		function beforeDropFunc(treeId, treeNodes, targetNode, moveType) {
		
			return targetNode ? targetNode.drop !== false : true;
		};
		//重命名节点之前
		function beforeRenameFunc(treeId, treeNode, newName) {
			if (newName.length == 0) {
				layer.alert("节点名称不能为空", {title:'提示信息',icon: 5});
				return false;
			}
				return true;
			
		};
		//删除节点之前
		function beforeRemoveFunc(treeId, treeNode) {
			var child =treeNode.children ;
			if(treeNode.pId==null){
				layer.alert("根节点不能删除", {title:'提示信息',icon: 5});
				return false;
			} if(child){
				layer.alert("该节点有子节点不能删除", {title:'提示信息',icon: 5});
				return false;
			} 
			var zTree = $.fn.zTree.getZTreeObj("tree");
			zTree.selectNode(treeNode);
			var id=treeNode.id;
			layer.confirm("确认删除节点吗？(删除后将刷新页面)", {icon: 3, title:'提示'},  
				    function(index){      //确认后执行的操作  
					    $.ajax({
								type:"POST",
								async:false,
								url:"<%=path%>/doubleFunt/deleteByFuntId.action",
								cache: false,
								data: {
					    		"id":id
					    		},  
					            dataType : "json",  
					            success: function(data){  
					            	if(data.success) {
					            		return true;
			         				}
			         			}
					 		});
					 	layer.close(index);
					 	refresh();
		       			return true;
				    },  
				    function(index){      //取消后执行的操作  
				        flag = false; 
				        layer.close(index);
				       return false; 
				    }); 
			return false;
		};
		//重命名节点名称
		function onRenameFunc(e, treeId, treeNode) {
			var id= treeNode.id;
			var name = treeNode.name;
				$.ajax({
					async:false,
					type:"POST",
					url:"<%=path%>/doubleFunt/updateFunt.action",
					cache: false,
					data: {
		    		'id':id,
		    		'funtName':name
		    		},  
		            dataType : "json",  
		            success: function(data){  
		            	if(data.success) {
		            		return true;
         			}
         			}
		 });	
			
		};
		//删除节点
		function onRemoveFunc(e,treeId, treeNode) {
		    var id = treeNode.id;
		    $.ajax({
					type:"POST",
					async:false,
					url:"<%=path%>/doubleFunt/deleteByFuntId.action",
					cache: false,
					data: {
		    		'id':id
		    		},  
		            dataType : "json",  
		            success: function(data){  
		            	if(data.success) {
		            		return true;
         			}
         			}
		 });
		};
		var funcId ;
		function onDragFunc(e, treeId, treeNodes){
		  funcId = treeNodes[0].id;
		};
		//拖拽节点
		function onDropFunc(e, treeId, treeNodes, targetNode, moveType){
			if(targetNode==null) {
			  layer.alert("请选择一个节点", {title:'提示信息',icon: 5});
			  return false;
			}
			var newpId=targetNode.id;
			
			//var treeNodePid = treeNodes[0].getParentNode().id;//被拖拽节点原来的pid
			//alert("目标pid："+targetNodePid+",选中pid:"+beforeDragPid);
			//alert(newpId);
			if(moveType=="inner"){
				 $.ajax({
						type:"POST",
						async:false,
						url:"<%=path%>/doubleFunt/changePar.action",
						cache: false,
						data: {
			    		'id':funcId,
			    		'funtParId':newpId
			    		},  
			            dataType : "json",  
			            success: function(data){  
			            	if(data.success) {
			            		return true;
	         				}
	         			}
			 	  });	
			}else {
				var targetNodePid = targetNode.getParentNode().id;//目标节点的pid
				 $.ajax({
						type:"POST",
						async:false,
						url:"<%=path%>/doubleFunt/changePar.action",
						cache: false,
						data: {
			    		'id':funcId,
			    		'funtParId':targetNodePid
			    		},  
			            dataType : "json",  
			            success: function(data){  
			            	if(data.success) {
			            		return true;
	         			}
	         			}
			 		});	
			}
		};
		
		//点击节点
		function onClickFunc(e, treeId, treeNode){
			//layer.close(layer.open());
			var url ="";
			var funtSort="";
			var className="";
			var funtGroup = "";
			var funtNo = "";
			var content;
			var zTree = $.fn.zTree.getZTreeObj("tree");
			nodes = zTree.getSelectedNodes();
			nodesNew = zTree.getNodes();
			var index = zTree.getNodeIndex(nodesNew[0]);
			var nodes1 = zTree.transformToArray(nodesNew);//ztree的所有节点的数据
			var funcID = nodes[0].id;
			$.ajax({
				type: "POST",
				async:false,
				url: '<%=basePath%>doubleFunt/selectByFuntId.action',
		    	data: {
		    		"id" : funcID
		    	},
				dataType:'json',
				cache: false,
				success: function(data){
					if(data.success){
						var data = data.ower;
						var datas = data.split(",");
						if(datas.length>2){
							url = datas[0];
							className = datas[1];
							if(datas[2]==0){//如果功能排序为0,显示在页面为空
								funtSort = "";
							}else{
								funtSort = datas[2];
							}
							//菜单类型是管理端还是客户端  1 管理端，2 PC端
							funtGroup = datas[3];
							funtNo = datas[5];
							if(funtGroup == 1){
								content = '所属模块<select id="funtGroup" style="width:99%;height:30px"><option value="1" selected="selected">管理端</option><option value="2">客户端</option></select></br>'+
										  'URL<input type="text" id="funtUrl" value="'+url+'" style="width:99%;height:30px" ></br>功能排序<input type="text" id="funtSort" value="'+funtSort+'" style="width:99%;height:30px" ></br>'+
										  '样式名称<input type="text" id="className" value="'+className+'" style="width:99%;height:30px" >'+
										  '权限按钮样式<input type="text" id="funtNo" readonly="readonly" value="'+funtNo+'" style="width:99%;height:30px" >';
							} else if(funtGroup == 2) {
								content = '所属模块<select id="funtGroup" style="width:99%;height:30px;padding:3px 0px 3px 10px"><option value="1">管理端</option><option value="2" selected="selected">客户端</option></select></br>'+
										  '菜单编码<input type="text" id="funtUrl" value="'+url+'" style="width:99%;height:30px" ></br>'+
										  '功能排序<input type="text" id="funtSort" value="'+funtSort+'" style="width:99%;height:30px" ></br>样式名称<input type="text" id="className" value="'+className+'" style="width:99%;height:30px" >' ;
							}
						}
					}
				}
			})
			var check = treeNode;//父节点允许修改URL
			if (check){
			layer.open({
				title: ['功能链接', 'background-color:#d0e3f0; color:#888889;'],
				 anim: -1, 
				 shade: false,
				content: content,
				move: false
				,area: ['300px', '400px'],
				btn: ['确认','取消'],
				yes: function(index) {
					var funtUrl = $("#funtUrl").val();
					var funtSort = $("#funtSort").val();
					var funtGroup = $("#funtGroup").val();
					var funtNo = $("#funtNo").val();
					if(!funtSort){//如果没有填写功能排序 默认赋值0(不赋值的话会因为类型状换导致失败报错)
						funtSort =0;
					}
					var className = $("#className").val();
					$.ajax({
						type: "POST",
						async:false,
						url: '<%=basePath%>doubleFunt/changeFuntUrl.action',
				    	data: {
				    		"id" : funcID,
				    		"funtUrl" : funtUrl,
				    		"funtSort":funtSort,
				    		"className":className,
				    		"funtGroup":funtGroup,
				    		"funtNo" : funtNo
				    	},
						dataType:'json',
						cache: false,
						success: function(data){
							if(data.success){
								//$("li.active").html(funtUrl);
								layer.alert("已修改", {title:'提示信息'})
								layer.close(index);
							}else{
								var err = data.message;
								layer.alert("更改功能链接失败，请重试！", {title:'提示信息',icon: 5});
							 }
						},
						error: function(data){
							layer.alert("更改功能信息失败，请重试！", {title:'提示信息',icon: 5});
						}
					});
				},
				no:function(index){
					document.getElementById("funtUrl").blur();
					layer.close(index);
				}
			});
			 $("#funtUrl").val(url);
			 $("#funtSort").val(funtSort);
			 $("#className").val(className);
			}
		}
		function refresh(){
			window.location.reload(true);
		}
  </script>
</html>
