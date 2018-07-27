<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<base href="<%=basePath%>">
<%@include file="/WEB-INF/jsp/common/common.html"%>
<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.exhide.min.js"></script>
<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="${sys_ctx }/plug/ztree/js/jquery.ztree.exedit.min.js"></script>
<link rel="stylesheet" href="${sys_ctx}/plug/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">  
<!-- <link rel="stylesheet" href="jquery-ui/jquery-ui.css">--> 
<title>权限管理1</title>
 	<!--[if lt IE 9]>
		<style>
		.jiedian{
				height:40px;
				width:100%;
				background-image:url(${sys_ctx}/css/jianbian.png);
				background-repeat: no-repeat;
			}
		</style>
	<![endif]-->
<style type="text/css">
	body,html{height:100%;overflow: hidden;}
		.left-user{width:260px; height:100%;  float:left;}
		.right-user{overflow-x:hidden; height:100%;padding-bottom:50px;overflow-y:hidden;}
		input{
			display: inline-block;
			width:20px;
			height:15px;

		}
		label{
			padding-bottom: 2px;
			font-weight:500;
			margin-bottom: 0 !important;
		}
		.panel-default{
			border-color:transparent;
		}
		.panel{
			margin-bottom:0;
		}
		.layui-table td{padding:0;}
		.panel-body p{padding:8px;}
		.btn-group{margin-bottom:2px}
		input[type=checkbox], input[type=radio] {
		    margin:0;
		    margin-top: 1px\9;
		    line-height: normal;
		    vertical-align: middle;
		}
		#roles{
			overflow-y: auto;
		}
		.wh{
			width:18px;
			height:18px;
			border:none;
			margin:0 2 0 2px;
			
		}
		.bg-1{
			background: url(./img/list/new_nor.png) no-repeat!important;
			filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(
       		src='./img/new_nor.png', sizingMethod='scale');
		}
		.bg-6{
			background: url(./img/list/fzgjd_nor.png) no-repeat !important;
		}
		.bg-5{
			background: url(./img/list/copy_sel.png) no-repeat !important;
		}
		.bg-1:hover{
			background: url(./img/list/new_sel.png) no-repeat !important;
		}
		.bg-2{
			background: url(./img/list/edit_nor.png ) no-repeat !important;
		}
		.bg-2:hover{
			background: url(./img/list/edit_sel.png)no-repeat !important;
		}
		.bg-3{
			background: url(./img/list/user_nor.png) no-repeat !important;
		}
		.bg-3:hover{
			background: url(./img/list/user_sel.png) no-repeat !important;
		}
		.bg-4{
			background: url(./img/list/delete_nor.png) no-repeat !important;
		}
		.bg-4:hover{
			background: url(./img/list/delete_sel.png) no-repeat !important;
		}
		.jiedian {
		    margin-top: 2px;
		    padding: 10px;
		    background-image: -webkit-linear-gradient(to right, #dfeffe, #fff);
		    background-image: linear-gradient(to right, #dfeffe, #fff);
		}
		.jd-title {
		    height: 20px;
		    font-weight: 600px;
		    border-left: 4px #0077dd solid;
		    margin-left: 20px;
		    padding-left: 10px;
		}
		/* 操作的css */
		div#rMenu {position:absolute; visibility:hidden; top:0;}
		div#rMenu ul li{
			cursor: pointer;
			list-style: none outside none;
			background-size: cover;	
			float: left;
			margin-left:2px;
			margin-right:2px;
			
		}
		/* tab切换  */
		.top ul {
		    width: 100%;
		    height: 42px;
		    border: 1px solid #dedede;
		    background: #f9f9f9;
		}
		.top ul li a {
		    display: block;
		    float: left;
		    padding: 0 20px;
		    height: 41px;
		    line-height: 40px;
		    color: #000;
		    font-weight: 500;
		    font-size: 14px;
		}
		.top ul li {
		    float: left;
		    border-right: 1px solid #dedede;
		}
		.action {
		    height: 41px!important;
		    border-bottom: 0;
		    background: #039dff;
		    color: #555!important;
		    border-top: 3px solid #3997f4;
		    font-weight: 600!important;
		    background: #fff;
		}
</style>
</head>
<body style="margin-top:10px">
	<div class="left-user">
	<div class="panel panel-default">
			<div class="jiedian">
				<div class="jd-title">角色管理</div>
			</div>
			<div class="btn-group">
				<!-- <button type="button" class="wh bg-1" title="新增" id="addRole"></button>
				<button type="button" class="wh bg-4" title="删除" id="deleteRole"></button>
				<button type="button" class="wh bg-3" title="分配人员" id="allocateUser"></button> -->
			</div>
			<ul id="roles">
				<ul id="tree" class="ztree scroll-tree" style="padding-top:10px !important;">
				</ul>
			</ul>
			<!-- <div id="rMenu">
				<ul>
					<li id="m_add" class="bg-1 wh" onclick="addTreeNode();" title="增加角色"></li>
					<li id="m_copy" class="bg-5 wh" onclick="copyTreeNode();"title="复制角色"></li>
					<li id="m_rename" class="bg-2 wh" onclick="editTreeNode();"title="编辑角色"></li>
					<li id="m_del" class="bg-4 wh" onclick="removeTreeNode();"title="删除角色"></li>
					<li id="m_add_root" class="bg-6 wh" onclick="addTreeRootNode();"title="增加根节点"></li>
					<li id="m_check" onclick="checkTreeNode(true);">Check节点</li>
					<li id="m_unCheck" onclick="checkTreeNode(false);">unCheck节点</li>
					<li id="m_reset" onclick="resetTree();">恢复zTree</li>
				</ul>
			</div> -->
	</div>
	</div>
	<div class='right-user top' style="border-left:1px #ddd solid">
		<ul id="roleDataConfig">
		  <!-- 20180224数据权限加载组织-设备树形改为异步加载，id由toRoleRangeFrame改为toAsyncRoleRangeFrame -->
		  <li role="roleRangeDataConfig"  class="action" ><a id="toAsyncRoleRangeFrame">数据权限</a></li>
		  <li role="dataPermission"><a id="toDataPermission">分配用户</a></li>
		  <%--<li role="toRoleRangeFrame"><a id="toRoleRangeFrame">数据权限(同步)</a></li><!-- 生产环境注释掉 -->--%>
		</ul>
		<iframe name="roleList" id="roleList" frameborder="0" style="width:100%;height:100%;" ></iframe>
	</div>
</body>
<script>
	/* 获取角色类型，供角色的增加和修改 */
	var categorySel = '${categorySel }';//新增时使用
	var categoryJ = '${categoryJ }';//编辑时使用
	var categoryList = eval(categoryJ);
	//注意：折叠面板 依赖 element 模块，否则无法进行功能性操作
 	layui.use('layer', function(){ 
		layer = layui.layer; 
	});
	layui.use(['element','layer'], function(){
	 	var element = layui.element();
	});

	/* 点击tab切换 */
   	var globalRoleId;//定义全局角色
   	var globalCurrentFrame;//定义全局的选择的frame
	$('#roleDataConfig li').on('click', function(e) {
		e = e || window.event;
		var target = $(e.target);
   		var currentFrame = target.attr('id');
   		var selNode = treeObj.getSelectedNodes()[0];
   		if (selNode) {
   			globalRoleId = selNode.id;
   		} else {
   			layer.msg('请选择角色！',{time: 2000, icon:6});
			return;   		
   		}
   		var url = '${sys_ctx}/role/' + currentFrame + '.action';
   		//进入角色的分配用户页面
   		if ("toDataPermission" == currentFrame) {
   			url = '${sys_ctx}/role/' + currentFrame + '.action?globalRoleId='+globalRoleId;
   		} else {
   			url = '${sys_ctx}/role/' + currentFrame + '.action?roleId='+globalRoleId;
   		}
   		globalCurrentFrame = currentFrame;
		$(this).addClass("action").siblings().removeClass("action");
		document.getElementById("roleList").src = url;
	});
	
	/* 初始化ztree树 */
	var setting = {
	  	data: {
	  		simpleData: {  //简单的数据源<pre class="html" name="code">           
	           enable: true, 
	           open:true, 
	           idKey: "id",  //id和pid，树的目录级别  
	           pIdKey: "pId"
	          } 
	  	},
		check:{
			enable: false,
			chkboxType:  { "Y": "s", "N": "s" }
		},
		view: {
			addHoverDom: addHoverDomFunc,
			removeHoverDom: removeHoverDomFunc,
	        selectedMulti: false  
	    },
		callback: {
			onClick: zTreeOnClick
		}
	};
	$(function() {
		loadRoleTree();
  	});
	/* 定义ztree树对象 */
	var treeObj;
	//加载角色树
	function loadRoleTree() {
  		$.ajax({
			type:"POST",
			url:"<%=path%>/role/queryRoleTree.action",
			cache: false,  
            dataType : "json",  
            success: function(data){
            	if(data) {
            		var ztree = data.ower;
            		$.fn.zTree.init($("#tree"),setting,ztree);
            		treeObj = $.fn.zTree.getZTreeObj("tree");
            		if (treeObj) {//初始化选中第一个节点
	            		var node = treeObj.getNodeByParam('level', 0);
						treeObj.selectNode(node);
						treeObj.setting.callback.onClick(null, treeObj.setting.treeId, node);//调用事件
            		}
            	}
         	}
		});		
	}
	
	/* 定义ztree树点击事件  分为两种情况，第一个是角色的范围toAsyncRoleRangeFrame，第二个是分配用户  */
 	function zTreeOnClick (event, treeId, treeNode) {
 		if (treeNode) {
 			var urlPath;
 			var frameUserId;//用户个性化的变量
 			//第一种情况: 角色
 			if (!globalCurrentFrame || globalCurrentFrame == "toAsyncRoleRangeFrame") {
 				urlPath = '<%=basePath%>role/toAsyncRoleRangeFrame.action?roleId='+treeNode.id;
 		    	url = encodeURI(urlPath);
 		    	window.open(url,"roleList");  				
 			} else if (globalCurrentFrame == "toDataPermission") {
 				//第二种情况: 分配用户
 				var currentClickRoleId = treeObj.getSelectedNodes()[0].id;
 				urlPath = '<%=basePath%>role/toDataPermission.action?globalRoleId='+currentClickRoleId;
 				document.getElementById("roleList").src = urlPath;
 			}
 		}
 	}
		var addCount = 1;
		/* 悬浮框 */
		function addHoverDomFunc(treeId, treeNode) {
			//$("#tree li a").removeClass("curSelectedNode");
			//$(this).addClass("action").siblings().removeClass("action");
			//获取节点的属性
			var roleId = treeNode.id;
			var id ;
			var sObj = $("#" + treeNode.tId + "_span");
			if ($("#addBtn_"+treeNode.id).length>0) return;
			var addStr = "<span  id='idx" + treeNode.id+ "'><span class='button bg-1 wh' onclick='addTreeNode(" + roleId + ");' id='addBtn_" + treeNode.id+ 
							"' title='增加节点'  ></span><span class='button bg-5 wh' onclick='copyTreeNode(" + roleId + ");' id='addBtn_" + treeNode.id+
							"' title='复制节点'  ></span><span class='button bg-2 wh' onclick='editTreeNode("+ roleId +");' id='addBtn_" + treeNode.id+
							"' title='编辑节点'  ></span><span class='button bg-4 wh'onclick='removeTreeNode("+ roleId +");' id='addBtn_" + treeNode.id+
							"' title='移除节点'  ></span><span class='button bg-6 wh' id='addBtn_" + treeNode.id+
							"' title='增加根节点' onclick='addTreeRootNode();' ></span></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.id);
		};
		function removeHoverDomFunc(treeId, treeNode) {
			$("#idx"+treeNode.id).remove();
		};

		//添加节点
		function addTreeNode(parentId) {
		//获取父节点
		if (!parentId) {
			layer.msg('请选中父节点',{time: 2000, icon:5});
			return;
		}
		//使用layer
		layer.open({
			title: ['新增', 'background-color:#d0e3f0; color:#888889;'],
			anim: 'scale',
			content: '<span style="margin-bottom:10px;display: block;width: 70px;float: left; line-height: 30px;">名称：</span>'+'<input type="text" style="width:150px;height:30px;padding-left:10px;margin-bottom:10px;border-radius:3px;" id="newRoleName"></br><span style="margin-bottom:10px;display: block;width: 70px;float: left; line-height: 30px;">类别：</span>'+categorySel,
			btn: ['确认','取消'],
			yes: function(index) {
				//点击确认后保存角色名称
				var newRoleName = $("#newRoleName").val();
				//判断名字是否为空
				if (!newRoleName) {
					layer.msg('名称不能为空！',{time: 2000, icon:5});
					return;
				}
				var categoryId = $("#categoryId").val();
				if (!categoryId) {
					layer.msg('请选择角色类别！',{time: 2000, icon:5});
					return;
				}
				$.ajax({
					type: "POST",
					url: '<%=basePath%>role/addRole.action',
			    	data: {'roleName':newRoleName, 'parentId':parentId, 'categoryId':categoryId },
					dataType:'json',
					cache: false,
					success: function(data){
						if(data.success == 'true'){
							layer.msg('添加成功',{time: 2000, icon:6});
							//在网页上展示出来
							var newNodeId = data.msg;
							var newNode = {id : newNodeId, name : newRoleName ,pId : parentId ,categoryId : categoryId};
							var parNode = treeObj.getNodeByParam("id",parentId); 
							if (parNode) {
								newNode.checked = parNode.checked;
								treeObj.addNodes(parNode, newNode);
							} else {
								treeObj.addNodes(null, newNode);
							}
						}else{
							//data.result为false，保存失败
							var err = data.msg;
							layer.open({
								title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
								anim: 'scale',
								content: '保存失败，错误信息为：' + err + '！',
								btn: ['确认'],
								yes: function(index) {
									layer.close(index);
								},
							});
						 }
					},
					error: function(data){
						//window.webkit.messageHandlers.loadSuccess.postMessage("error");
						layer.open({
							title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
							anim: 'scale',
							content: '请检查网络！',
							btn: ['确认'],
							yes: function(index) {
								layer.close(index);
							},
						});
						
					}
				
				});
				layer.close(index);
			},
			no: function(index){
				document.getElementById("newCategoryName").blur();
				layer.close(index);
			},
		});
		removeHoverDomFunc()
	}
	/* 复制节点 */
	function copyTreeNode(roleId) {//被复制的节点id
		var node = treeObj.getNodeByParam("id",roleId);
		var newRoleName,parentId,categoryId;
		newRoleName = node.name + '副本';
		parentId = node.pId;
		categoryId = node.categoryId;
		$.ajax({
			type: "POST",
			url: '<%=basePath%>role/copyRole.action',
	    	data: {'roleName':newRoleName, 'parentId' : parentId, 'sublingRoleId':roleId, 'categoryId':categoryId },
			dataType:'json',
			cache: false,
			success: function(data){
				if(data.success == 'true'){
					layer.msg('添加成功',{time: 2000, icon:6});
					//在网页上展示出来
					var newNodeId = data.ower;
					var newNode = {id : newNodeId, name : newRoleName ,pId : parentId, categoryId : categoryId };
					if (parentId != -1) {
						//获取父节点
						var parNode = treeObj.getNodeByParam("id",parentId);
						if (parNode) {
							newNode.checked = parNode.checked;
							treeObj.addNodes(parNode, newNode);
						} else {
							treeObj.addNodes(null, newNode);
						}
					} else {
						treeObj.addNodes(null, newNode);
					}
				}else{
					var err = data.msg;
					layer.open({
						title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
						anim: 'scale',
						content: '保存失败，错误信息为：' + err + '！',
						btn: ['确认'],
						yes: function(index) {
							layer.close(index);
						},
					});
				 }
			},
			error: function(data){
				//window.webkit.messageHandlers.loadSuccess.postMessage("error");
				layer.open({
					title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
					anim: 'scale',
					content: '请检查网络！',
					btn: ['确认'],
					yes: function(index) {
						layer.close(index);
					},
				});
			}
		
		});
	}
	/* 添加根节点 */
	function addTreeRootNode() {
		layer.open({
			title: ['新增', 'background-color:#d0e3f0; color:#888889;'],
			anim: 'scale',
			content: '<span style="margin-bottom:10px;display: block;width: 70px;float: left; line-height: 30px;">名称：</span>'+'<input type="text" style="width:150px;height:30px;padding-left:10px;margin-bottom:10px;border-radius:3px;" id="newRoleName"></br><span style="margin-bottom:10px;display: block;width: 70px;float: left; line-height: 30px;">类别：</span>'+categorySel,
			btn: ['确认','取消'],
			yes: function(index) {
				//点击确认后保存角色名称
				var newRoleName = $("#newRoleName").val();
				//判断名字是否为空
				if (!newRoleName) {
					layer.msg('名称不能为空！',{time: 2000, icon:5});
					return;
				}
				var categoryId = $("#categoryId").val();
				if (!categoryId) {
					layer.msg('请选择角色类别！',{time: 2000, icon:5});
					return;
				}
				$.ajax({
					type: "POST",
					url: '<%=basePath%>role/addRole.action',
			    	data: {'roleName':newRoleName, 'categoryId':categoryId },
					dataType:'json',
					cache: false,
					success: function(data){
						if(data.success == 'true'){
							layer.msg('添加成功',{time: 2000, icon:6});
							//在网页上展示出来
							var newNodeId = data.msg;
							var newNode = {id : newNodeId, name : newRoleName ,pId : data.ower, categoryId : categoryId };
							treeObj.addNodes(null, newNode);//添加根节点
						}else{
							//data.result为false，保存失败
							var err = data.msg;
							layer.open({
								title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
								anim: 'scale',
								content: '保存失败，错误信息为：' + err + '！',
								btn: ['确认'],
								yes: function(index) {
									layer.close(index);
								},
							});
						 }
					},
					error: function(data){
						//window.webkit.messageHandlers.loadSuccess.postMessage("error");
						layer.open({
							title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
							anim: 'scale',
							content: '请检查网络！',
							btn: ['确认'],
							yes: function(index) {
								layer.close(index);
							},
						});
					}
				
				});
				layer.close(index);
			},
			no: function(index){
				document.getElementById("newCategoryName").blur();
				layer.close(index);
			},
		});
	}	
	/* 移除节点 */
	function removeTreeNode(roleId) {
		if (roleId==1) {
			layer.msg('超级管理员不能删除',{time: 2000, icon:6});
			return;
		}
		//将【系统管理员】可见，不可删除
		if (roleId==2) {
			layer.msg('系统管理员不能删除',{time: 2000, icon:6});
			return;
		}
		var node = treeObj.getNodeByParam("id",roleId);
		if (!node) {
			layer.msg('请选择节点',{time: 2000, icon:6});
			return;
		}
		//判断角色下是否有用户
		var deleteAvailableFlag = false;//是否可以删除
		$.ajax({
			type: "POST",
			async: false,//同步
			url: '<%=basePath%>role/checkRelatedRole.action',
			data: {'roleId':roleId },
			dataType:'json',
			cache: false,
			success: function(data){
				if(data.success=="true"){
					//可以删除
					deleteAvailableFlag = true;
				}else{
					//不能删除
					deleteAvailableFlag = false;
		   			if (!deleteAvailableFlag) {
		   				var msg = '该角色已分配用户，请在菜单【数据权限管理】->【分配用户】中取消关联用户。';
		   				layer.msg(msg,{time: 5000, icon:6});
		   				return;
		   			}
				}
			},
			error: function(data){
				layer.msg("请检查网络！",{time: 2000, icon:5});
			}
		});
		if (deleteAvailableFlag) {
			layer.confirm('将删除该角色及其关联的数据权限，确定删除？', {btn: ['确定', '取消'],title:'提示信息',icon: 3},
				function(index, layero) {//确定按钮回执函数
				if (node.children && node.children.length > 0) {
					var msg = "要删除的节点是父节点，请先删除子节点。\n\n请确认！";
					layer.msg(msg, {time: 2000, icon:6});
					return;
				}
				//后台移除操作
				$.ajax({
					type: "POST",
					url: '<%=basePath%>role/delByRoleId.action',
			    	data: {'roleId':roleId },
					dataType:'json',
					cache: false,
					success: function(data){
						if(data.success == 'true'){
							layer.msg('删除成功',{time: 2000, icon:6});
							//在网页上展示出来
							treeObj.removeNode(node);
						}else{
							//data.result为false，保存失败
							var err = data.msg;
							layer.open({
								title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
								anim: 'scale',
								content: '删除失败，错误信息为：' + err + '！',
								btn: ['确认'],
								yes: function(index) {
									layer.close(index);
								},
							});
						 }
					},
					error: function(data){
						//window.webkit.messageHandlers.loadSuccess.postMessage("error");
						layer.open({
							title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
							anim: 'scale',
							content: '请检查网络！',
							btn: ['确认'],
							yes: function(index) {
								layer.close(index);
							},
						});
					}
				});
				}, function(index){//取消按钮回执函数
			  				layer.close(index);
			  	});
			}
	}
	
	function StringBuffer() {
    	this.__strings__ = new Array();
	}
	StringBuffer.prototype.append = function (str) {
	    this.__strings__.push(str);
	    return this;    //方便链式操作
	}
	StringBuffer.prototype.toString = function () {
	    return this.__strings__.join("");
	}
	/* 编辑节点 */
	function editTreeNode(nodeId) {
		var editNode = treeObj.getNodeByParam("id",nodeId);
		nodeName = editNode.name;
		categoryId = editNode.categoryId;
		//获取节点id name
		//treeObj.editName(node); ztree自带更改
		//动态拼接select
		var categorySelEdit = new StringBuffer();
		categorySelEdit.append("<select name='categoryId' id='categoryId' style='width:150px;height:30px;padding-left:10px;margin-bottom:10px;border-radius:3px;'>");
		for(var i=0; i<categoryList.length; i++){
			if (categoryId == categoryList[i].categoryId) {
				categorySelEdit.append("<option selected='selected' value='"+categoryList[i].categoryId+"'>"+categoryList[i].name+"</option>");
			} else {
				categorySelEdit.append("<option value='"+categoryList[i].categoryId+"'>"+categoryList[i].name+"</option>");
			}
		}
		categorySelEdit.append("</select>");
		layer.open({
			title: ['编辑', 'background-color:#d0e3f0; color:#888889;'],
			anim: 'scale',
			content: '<span style="margin-bottom:10px;display: block;width: 70px;float: left; line-height: 30px;">名称：</span>'+'<input type="text" style="width:150px;height:30px;padding-left:10px;margin-bottom:10px;border-radius:3px;" id="editRoleName" value="'+nodeName+'"></br><span style="margin-bottom:10px;display: block;width: 70px;float: left; line-height: 30px;">类别：</span>'+categorySelEdit,
			btn: ['确认','取消'],
			yes: function(index) {
				//点击确认后保存角色名称
				var editRoleName = $("#editRoleName").val();
				//判断名字是否为空
				if (!editRoleName) {
					layer.msg('名称不能为空！',{time: 2000, icon:5});
					return;
				}
				var category_d = $("#categoryId").val();
				if (!category_d) {
					layer.msg('请选择角色类别！',{time: 2000, icon:5});
					return;
				}
				$.ajax({
					type: "POST",
					url: '<%=basePath%>role/updateRole.action',
			    	data: {'roleId':nodeId, 'roleName':editRoleName, 'categoryId':category_d },
					dataType:'json',
					cache: false,
					success: function(data){
						if(data.success == 'true'){
							layer.msg('修改成功',{time: 2000, icon:6});
							var node = treeObj.getNodeByParam("id",nodeId);
							if (node) {
								//在网页上更新节点
								node.name = editRoleName;
								node.categoryId = category_d;
								treeObj.updateNode(node);
							}
						}else{
							//data.result为false，保存失败
							var err = data.msg;
							layer.open({
								title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
								anim: 'scale',
								content: '保存失败，错误信息为：' + err + '！',
								btn: ['确认'],
								yes: function(index) {
									layer.close(index);
								},
							});
						 }
					},
					error: function(data){
						//window.webkit.messageHandlers.loadSuccess.postMessage("error");
						layer.open({
							title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
							anim: 'scale',
							content: '请检查网络！',
							btn: ['确认'],
							yes: function(index) {
								layer.close(index);
							},
						});
					}
				
				});
				layer.close(index);
			},
			no: function(index){
				document.getElementById("newCategoryName").blur();
				layer.close(index);
			},
		});
	}
	/* 复选框选中节点 */	
	function checkTreeNode(checked) {
		var nodes = treeObj.getSelectedNodes();
		if (nodes && nodes.length>0) {
			treeObj.checkNode(nodes[0], checked, true);
		}
		//hideRMenu();
		//$("#idx").hide();
	}
	/* 分配人员 */
	$("#allocateUser").on("click",function(){
		var nodes = treeObj.getSelectedNodes();
		if (nodes && nodes.length==0) {
			layer.msg('请先选择一个角色',{time: 2000, icon:6});
			return;
		} else {
			var roleId = nodes[0].id;
			window.location.href = "<%=basePath%>role/toAllocateUserRole.action?roleId="+roleId;
		}
	});
</script>
</html>