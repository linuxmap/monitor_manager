<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户管理</title>
    <meta http-equiv="X-UA-Compatible" content="IE=8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.core.min.js"></script>
	<link rel="stylesheet" href="${sys_ctx}/plug/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">  
	<link rel="stylesheet" href="${sys_ctx}/css/tree/treeStyle.css" type="text/css">  
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.exhide.min.js"></script>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.excheck.min.js"></script>
  </head>
  <style>

    .ztree li span.button.ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_scj_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
    .ztree li span.button.ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_scj_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
 	  	/*组织机构_2 
 	  	*第一个数字 1代表可见 2代表不可见
 	  	*/
 	  	/*节点1*/
 	  	.ztree li span.button.ztree1_2_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
 	  	.ztree li span.button.ztree1_2_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
 	  	.ztree li span.button.ztree2_2_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_invisible.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
 	  	.ztree li span.button.ztree2_2_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_invisible.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
 	   	.ztree li span.button.ztree1_2_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
 	    .ztree li span.button.ztree2_2_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_invisible.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
 	  	
 	  	/*重大作业_1 */
 	  	.ztree li span.button.ztree1_1_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
 	  	.ztree li span.button.ztree1_1_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
 	  	.ztree li span.button.ztree2_1_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_invisible.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
 	  	.ztree li span.button.ztree2_1_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_invisible.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
 	   	.ztree li span.button.ztree1_1_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
 	    .ztree li span.button.ztree2_1_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_invisible.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
 	  	
 	  	/*收藏夹_4 */
 	  	.ztree li span.button.ztree1_4_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
 	  	.ztree li span.button.ztree1_4_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
 	  	.ztree li span.button.ztree2_4_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_invisible.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
 	  	.ztree li span.button.ztree2_4_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_invisible.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
 	   	.ztree li span.button.ztree1_4_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
 	    .ztree li span.button.ztree2_4_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_invisible.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
 	inupt{
 		border:0;
 	}
  </style>
  <!--[if lt IE 9]>
		<style>
		.jiedian{
				height:40px;
				width:100%;
				background-image:url(${sys_ctx}/css/jianbian.png);
				background-repeat: no-repeat;
			}
			html{height:100%}
		</style>
	<![endif]-->
  <body style="width:100%;height:100%;overflow:hidden;">
    <div style="width:100%;height:100%;" class="background">
		<div style="width:20%;height:100%;float:left;" class="margin-top">
			<div>
				<c:if test="${fn:contains(buttonList,'setOrgVisible')}">
					<span name="setOrgVisible" style="text-align:center;">
						<button class="default-one-btn site-button-method font-color"  data-method="setOrgVisible" >设置组织可见性</button>
					</span>
				</c:if>
			</div>
			<div class="jiedian">
				<div class="jd-title">组织机构</div>
			</div>
			<div class="search-bar" style="padding-left:30px;padding-top:10px !important;">
	            <input id="keyword" type="text" class="default-one-input" placeholder="请输入..." style="width:50%"/>
	            <button id="search-bt" class="default-one-btn btn-green" style="vertical-align:middle;">搜索</button>
	        </div>
			<ul id="tree" class="ztree scroll-tree" style="overflow: auto;height: 80%;"></ul>
		</div>
		<div  style="width:80%;height:100%;float: right" >
			<iframe name="userList" id="userList" frameborder="0" style="width:100%;height:100%;" ></iframe>
	    </div>
	</div>
  </body>
  <script type="text/javascript">
 	var funtId = '${funtId}';//该页面funtId
 	//pageFuntAutyBtn('${sys_ctx}/getFuntBtnList.action',funtId);//控制按钮权限
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
 			enable: true,
 			chkboxType:  { "Y": "s", "N": "s" }
 		},
		view: {  
	        selectedMulti: false  
	    },
		callback: {
			onClick: zTreeOnClick
		}
 	};
 	
 	var hiddenNodes=[]; 
 	var hiddenNodesf=[];//用于存储被隐藏的结点
	$("#search-bt").on("click", function() {
        var zTree = $.fn.zTree.getZTreeObj("tree");
        
        //显示隐藏的节点
        var nodes = zTree.getNodesByParam("isHidden", true);
		treeObj.showNodes(nodes);
        
        var root = zTree.getNodeByParam("level", "0");
        
        var hiddenNodes = new Array();
        //筛选出要隐藏的企业
        var inputStr = $('#keyword').val().replace(/(^\s+)|(\s+$)/g,"");
        filterNodes(root, inputStr, hiddenNodes);
        zTree.hideNodes(hiddenNodes);
	});
	
	function filterNodes(node, inputStr, filterResult){
        if(node != null){
            //自身是否符合搜索条件
            var selfMatch = node.name.indexOf(inputStr) > -1;
            //子节点是否有满足的条件的节点
            var childMatch = false;
            
            var children = node.children;
            if(children != undefined){
                for(index in children){
                    childMatch = filterNodes(children[index], inputStr, filterResult) || childMatch;
                }
            }
            
            //自身不满足搜索条件 且其子节点不包含有满足条件的节点
            if(!selfMatch && !childMatch){
                filterResult.push(node);
            }
            
            return selfMatch || childMatch;
        }else{
            return true;
        }
    }
	
 	
 	function zTreeOnClick(event, treeId, treeNode) {
	 	if(treeNode){  
	    	var url = "<%=path%>/assetIntegration/queryAssets.action?orgId="+treeNode.id+"&orgName="+treeNode.name+ "&funtId="+funtId;
	    	url = encodeURI(url);
	    	window.open(url,"userList");  
	 	}
 	}
 	
 	var page_sign = 0;//判定是否刷新列表
 	var treeObj = null;
	$(function(){
		var winH =$(window).height()-223+"px";
		$("#tree").height(winH);
  		$.ajax({//URL 获取组织时 分为可见不可见，用图标标识
			type:"POST",
			url:"<%=path%>/org/queryOrgTreeVisible.action",
			cache: false,  
            dataType : "json",  
            success: function(data){
            	if(data) {
            		var ztree = data.ower;
            		$.fn.zTree.init($("#tree"),setting,ztree);
            		treeObj = $.fn.zTree.getZTreeObj("tree");
            		var node = treeObj.getNodeByParam('level', 1);//获取level为0的点
            		$("#orgId").val(node.id);//给子页面部门隐藏域赋值
					treeObj.selectNode(node);//选择点
					treeObj.setting.callback.onClick(null, treeObj.setting.treeId, node);//调用事件
					$("#tree_1_switch").css("visibility","hidden");
					$("#tree_1_check").css("visibility","hidden");
					$("#tree_1_a").css("visibility","hidden");
            	}
         	}
		 });
  		window.open("<%=path%>/assetIntegration/queryAssets.action","userList");
  	});
	
	layui.use('layer', function(){
		var $ = layui.jquery, layer = layui.layer;
		//触发事件
	    var active = {
	  		setOrgVisible: function(){
			    	//获取选中的节点
					var zTreeObj = $.fn.zTree.getZTreeObj("tree");
					var nodes = zTreeObj.getCheckedNodes(true);
					var orgIds = [];
					if(nodes && nodes.length > 0){
						for(var i=0; i<nodes.length; i++){
							orgIds.push(nodes[i].id);
						}
					}else{
						layer.msg("未勾选组织节点",{time: 2000, icon:6});
						return;
					}
			   		layer.open({
						title: ['设置组织可见性', 'background-color:#d0e3f0; color:#888889;'],
						anim: 'scale',
						content: '可见<input type="radio" id="nor" name="visibleOrg" value="1" style="width:20px;height:18px;margin-left:10px;margin-right:10px;border-radius:3px;border:0 !important;"/>不可见<input type="radio" name="visibleOrg" value="0" id="off" style="width:20px;height:18px;margin-left:10px;margin-right:10px;border-radius:3px;border:0 !important;"/>',
						btn: ['确认','取消'],
						yes: function(index) {
							//$("input[type='radio']:checked").val();
							var visibleOrg = $("input[name='visibleOrg']:checked").val();
							if(!visibleOrg){
								layer.msg("未勾选",{time: 2000, icon:6});
								return;
							}
							for (var k=0; k<orgIds.length; k++) {
								if (orgIds[k] == -5) {
									layer.msg('重大作业节点不可勾选',{time: 2000, icon:6});
									return;
								}
							}
							$.ajax({
								type: "POST",
								url: '<%=basePath%>org/setOrgVisible.action',
						    	data: {'orgIds':orgIds, 'visibleOrg':visibleOrg},
								dataType:'json',
								cache: false,
								success: function(data){
									if(data.success){
										layer.close(index);
										window.location.reload();
									}else{
										var err = data.message;
										layer.open({
											title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
											anim: 'scale',
											content: '设置失败，错误信息为：' + err + '！',
											btn: ['确认'],
											yes: function(index) {
												location.reload();
											}
										});
									 }
								},
								error: function(data){
									layer.open({
										title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
										anim: 'scale',
										content: '请检查网络！',
										btn: ['确认'],
										yes: function(index) {
											location.reload();
										//	layer.close(index);
										}
									});
								}
							});
							layer.close(index);
						},
						no: function(index){
							document.getElementById("typeName").blur();
							layer.close(index);
							
						}
					});
			    }
	    };
	    $('.site-button-method').on('click', function(){
		    var othis = $(this), method = othis.data('method');
		    active[method] ? active[method].call(this, othis) : '';
	    });
	});
	
  </script>
</html>
