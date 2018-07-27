<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>权限范围1</title>
<base href="<%=basePath%>">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<%@include file="/WEB-INF/jsp/common/common.html"%>
<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.core.min.js"></script>
<link rel="stylesheet" href="${sys_ctx}/plug/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">  
<link rel="stylesheet" href="${sys_ctx}/css/tree/treeStyle.css" type="text/css">  
<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.exhide.min.js"></script>
<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.excheck.min.js"></script>
</head>
  <style type="text/css">
	.layui-colla-item{position:relative;}
	.label-1{
			position: absolute;
		    z-index: 9;
		    top: 10px;
		    left: 45px;
	    }
    td {
		margin: 0;
		padding: 0;
	}

	body,html{height:100%;overflow: hidden;}
		.left-user{width:200px; height:100%;  float:left;}
		.right-user{overflow-x:hidden; height:100%;padding-bottom:50px}
		.Privilege-p{
			display: -webkit-flex;
			display: flex;
			justify-content: flex-start;
			flex-wrap: wrap;	
			border-bottom: 0;
		}
		.Privilege-p p{
			margin:8px;
			text-align: center;
		}
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
		.title-h2{
			padding-left:10px;
		}
		.panel-default{
			border-color:transparent;
		}
		.f_right{
			text-align:right;
			margin-bottom: 2px!important;
		}
		.panel{
			margin-bottom:0;
		}
		.list-group-item {
			border:0;
		    border-top: 1px solid #ddd;
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
		.list-group-item.active, .list-group-item.active:focus, .list-group-item.active:hover {
		    z-index: 2;
		    color: #fff;
		    background-color: #3997f4;
		    border-color: #3997f4;
		}
		#roles{
			overflow-y: auto;
		}
		#tree{
		    border: 1px #ccc solid;
		    padding: 40px 30px 0 100px;
		    position: absolute;
		    overflow: auto;
		    width: 380px;
		    height: -webkit-fill-available;
		    height: 84%;
		    left: 50%;
		    margin-left: -190px;
		    top: 30px;
		}
		.background{
		    width: 100%;
    		height: 90%;
    		background:#fff;
		}
		
		 
  		/*权限范围ICON配置
  		**class名前缀：  __设备
  		**1.服务器   2.NVR设备  3。交换机 4。接入网关设备
  		**5.存储设备  6.报警主机 7.编码器 8.解码器 9.路由器
  		**10.单兵 11.车载 12.无人机 13.视频平台  14.摄像机
  		**  */
  		
  		.ztree li span.button.__1_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_fwq.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}   
  		.ztree li span.button.__2_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_NVR.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle} 
  		.ztree li span.button.__3_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_jhj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}         
  		.ztree li span.button.__4_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_jrwg.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
  		.ztree li span.button.__5_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_ccsb.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
  		.ztree li span.button.__6_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_bjzj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
  		.ztree li span.button.__7_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_bmq.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
  		.ztree li span.button.__8_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_jmq.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
  		.ztree li span.button.__9_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_lyq.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
  		.ztree li span.button.__10_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_db.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
  		.ztree li span.button.__11_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_cz.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}   
  		.ztree li span.button.__12_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_wrj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle} 
		.ztree li span.button.__13_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_sxj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}   
		.ztree li span.button.__14_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_sxj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}                     
  		/* class名前缀： _组织
  		**1.组织机构  2.区域 4.收藏夹
  		** */
  		.ztree li span.button._1_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_zzjg.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
		.ztree li span.button._1_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_zzjg.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._1_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_zzjg.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
  		.ztree li span.button._2_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_qy.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
  		.ztree li span.button._4_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_scj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}        
  		
  		.ztree li span.button._org_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
		.ztree li span.button._org_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._org_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._enormouswork_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._directory_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_scj_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._enormouswork_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._enormouswork_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		
  </style>
<body>
  	<div style="width:100%;height:100%;" class="background">
  		<div class="panel panel-default"style="text-align: right;" > 
	    	<!-- 角色id -->
	    	<input type="hidden" id="roleId" name="" />
			<button type="button" class="default-one-btn _1_ico_close" id="save">确定</button>
			<button type="button" class="default-one-btn-top" id="reset">重置</button>
		</div> 
		<div style="float:left;margin-top:0" class="margin-top">
			<ul id="tree" class="ztree scroll-tree"></ul>
		</div>
	</div>
</body>
<script type="text/javascript">
//初始化layer插件
layui.use('layer', function() {
	layer = layui.layer; 
});
  
//定义ztree变量
var dataRoleid;
var roleId;
var zTree;
var treeObj;
var zTreeNodes;
//定义全局变量该角色是否已经有权限了
var isFirstConfigData = '${isFirstConfigData }';
//定义全局变量，重置是否  被点击，被点击则展开的节点都是false
var clickReset = false;
var globalFirstConfigData;
if (isFirstConfigData=="true") {
	globalFirstConfigData = true;
}
if (isFirstConfigData=="false") {
	globalFirstConfigData = false;
}
$(function(){
	//初始化ztree树
	var setting = {
			//采用异步加载
			view : {
				selectedMulti : true,
				showLine : true  //是否显示节点间的连线
			},
			check: {
				enable : true,
				autoCheckTrigger: true //设置自动关联勾选时是否触发 beforeCheck / onCheck 事件回调函数
			},
			data : {
				simpleData : {
					enable : true
				},
				key: {
					name :"name",
					title : "name"
				}
			},
			callback : {
				onExpand : expandNode, //展开事件
				onCheck: zTreeOnCheck //监听勾选
			}
	};
	var allNodes ={};
	var zn = '${zTreeNodes}';
	zTreeNodes = eval(zn);
	zTree = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
	treeObj = $.fn.zTree.getZTreeObj("tree");
	var node =  treeObj.getNodeByParam("id", 0, null);
	//treeObj.expandAll(true);//默认折叠
	if (node) {
		var node = treeObj.getNodes();
		for (var i=0;i<node.lenght;i++) {
			allNodes.push(nodes[i]);
		}
	}
});
//监听勾选
function zTreeOnCheck(event, treeId, treeNode) {
    if (treeNode.checked == true) {
    	treeNode.manualCheck = true;//表示手动勾选
    }
};
//节点展开事件 展开时定义组织节点的 子节点是否全被选中 属性 ，用allBranchCheckd来定义
function expandNode(event, treeId, treeNode) {
	//验证角色id
	var nodes = window.parent.treeObj.getSelectedNodes();
   	if (nodes && nodes.length==0) {
   		layer.msg('请选择角色！',{time: 2000, icon:6});
   		return;
   	}
	var node = treeObj.getNodeByTId(treeNode.tId);
	//自定义属性，标记该节点是否被展开过，如果没有展开过，则标记为已展开，否则不走后台
	if (!node.leftclicked) {
		node.leftclicked = true;
	} else {
		return;
	}
	//ajax异步获取组织下边的设备
	$.ajax({
		type: "POST",
		async:false,
		url: "${sys_ctx}/role/getAsyncChildrenByParentId.action",
		data:{
			parentId : treeNode.id,
			roleId : nodes[0].id
		},
		dataType:"json",
		success: function(data){
			if(data.success == "true"){
				//添加新节点
				if (data.ower && data.ower.length>0) {
					newNode = treeObj.addNodes(node, data.ower);
					//设置allBranchCheckd属性
					if (node.children) {
						var childrenNodes = node.children;
						var checkChildCount = 0;
						//设备的选中的个数
						var checkedAssetCount = 0;
						for (var j=0; j<childrenNodes.length; j++) {
							if (childrenNodes[j].checked == true) {
								checkChildCount++;
								//如果是设备勾选状态则计数
								if (childrenNodes[j].type == 2) {
									checkedAssetCount++;
								}
							}
						}
						//防止展开时父节点的选择被失误取消
						if (checkChildCount > 0) {
							node.checked = true;
							treeObj.updateNode(node);
						}
						//非手动勾选 如果角色该组织下的设备权限则需要把组织的勾选去除
						if (!node.manualCheck && checkChildCount==0) {
							node.checked = false;
							treeObj.updateNode(node);
						}
						//手动勾选属性：manualCheck 如果一开始配置，组织下的所有设备没有被勾选，操作者手动勾选了父节点，则设备节点自动勾选
						if (node.manualCheck && checkedAssetCount == 0 && node.checked == true) {
							for (var k=0; k<childrenNodes.length; k++) {
								if (childrenNodes[k].type == 2) {
									childrenNodes[k].checked = true;
									treeObj.updateNode(childrenNodes[k]);
								}
							}
						}
						/* if (checkChildCount == childrenNodes.length) {
							//若所有子节点被选中，则父节点属性设置为true
							node.allBranchCheckd = true;
						} */
					}
					
					//如果点击了重置则取消点击
					if (clickReset) {
						treeObj.checkAllNodes(false);
					}
				}
			}
		},
		error:function(event, XMLHttpRequest, ajaxOptions, thrownError){
			layer.msg('请求失败！',{time: 2000, icon:6});
		}
	});
}
 	
var funtId = '${funtId}';//该页面funtId
var page_sign = 0;//判定是否刷新列表

var hiddenNodes=[]; //用于存储被隐藏的结点
	
//保存
$("#save").on("click",function(){
   	var nodes = window.parent.treeObj.getSelectedNodes();
   	if (nodes && nodes.length==0) {
   		layer.msg('请选择角色！',{time: 2000, icon:6});
   		return;
   	}
   	roleId = nodes[0].id;
   	
 	//获取选中的节点，如果权限都取消则删除关联表中的关系
 	var selectNodes = treeObj.getCheckedNodes(true);
 	if (selectNodes.length == 0) {
  		//删除所有权限
		$.ajax({
			type: "POST",
			url: '${sys_ctx}/role/asyncDeleteRoleDataPermission.action',
	    	data: {
	    		'roleId' : roleId
	    		},
			dataType:'json',
			cache: false,
			success: function(data) {
				if (data.success) {
					layer.msg(data.msg,{time: 2000, icon:6});
					window.location.reload();
				} else {
					var err = data.msg;
					layer.alert("保存失败，错误信息："+err, {title:'提示信息',icon: 5});
				}
			},
			error: function(data) {
				layer.msg('请检查网络！',{time: 2000, icon:5});
			}
		}); 
  		return;
  	}   	
   	if (!globalFirstConfigData) {//globalFirstConfigData为false则是修改
   		//update 修改角色下对应的组织和设备权限
   		//修改角色数据权限的逻辑整理：获取变化的节点(注意保证新展开的节点处于原始状态) 2、获取增加的组织节点，获取增加的设备节点 4、获取减少的组织节点，获取减少的设备节点
   		//获取变化的节点
   		var changedNodes = treeObj.getChangeCheckedNodes();
   		var orgAddIdArray = new Array();
   		var orgDelIdArray = new Array();
   		var equAddIdArray = new Array();
   		var equDelIdArray = new Array();
   		//所有设备被选中的组织节点
   		var allCheckedAssetOrgArray = new Array();
   		if (changedNodes && changedNodes.length>0) {
   			//先处理组织
   			for (var n=0; n<changedNodes.length; n++) {
   				if (changedNodes[n].type == 1) {
   					if (changedNodes[n].checked) {
   						orgAddIdArray.push(changedNodes[n].id);
   						//判断是否需要加入allCheckedAssetOrgArray
   						if (changedNodes[n].children && changedNodes[n].children.length>0) {
   							//节点展开了
		   					var subNodes = changedNodes[n].children;
		   					//定义子节点的个数和子节点选中的个数
		   					var subNumber = subNodes.length;
		   					var subCheckedCount = 0;
		   					for (var j=0;j<subNodes.length; j++) {
		   						if (subNodes[j].checked) {
		   							subCheckedCount++;
		   						}
		   					}
		   					if (subCheckedCount == subNumber) {
		   						allCheckedAssetOrgArray.push(changedNodes[n].id);
		   					}
   						} else {
   							//没有展开
	   						allCheckedAssetOrgArray.push(changedNodes[n].id);
   						}
   					} else {
   						orgDelIdArray.push(changedNodes[n].id);
   					}
   				} 
   			}
   			//再处理设备
   			for (var m=0; m<changedNodes.length; m++) {
   				if (changedNodes[m].type == 2) {
					if (changedNodes[m].checked) {
						if (isInArray(allCheckedAssetOrgArray,changedNodes[m].pId)) {
							continue;//不再重复加入设备
						}
						equAddIdArray.push(changedNodes[m].dbassetId + "_" + changedNodes[m].pId);
					} else {
						equDelIdArray.push(changedNodes[m].dbassetId + "_" + changedNodes[m].pId);
					}
   				}
   			}
   			//该ajax后台请求修改角色对应的权限
			$.ajax({
				type: "POST",
				url: '${sys_ctx}/role/asyncUpdateRoleRange.action',
		    	data: {
		    		'roleId' : roleId,
		    		'allCheckedAssetOrgArray' : allCheckedAssetOrgArray,
		    		'orgAddIdArray' : orgAddIdArray,
		    		'orgDelIdArray' : orgDelIdArray,
		    		'equAddIdArray' : equAddIdArray,
		    		'equDelIdArray' : equDelIdArray
		    		},
				dataType:'json',
				cache: false,
				success: function(data){
					if(data.success){
						layer.msg(data.msg,{time: 2000, icon:6});
					}else{
						var err = data.msg;
						layer.alert("保存失败，错误信息："+err, {title:'提示信息',icon: 5});
					 }
					 window.location.reload();
				},
				error: function(data){
					layer.msg('请检查网络！',{time: 2000, icon:5});
				}
			}); 		
	   	}
	 } else {
		   	//insert 给角色第一次配置数据权限
	   		
	   		//新增节点的思路     1获取所有选中的节点，筛选出组织的ztree节点 2、组织分两种，设备全被选择和部分选择的  3、部分选择的设备则存入数组中
	   		var allOrgNodeChecked = new Array();
	   		var fullOrgChecked = new Array();
	   		var partOrgChecked = new Array();
	   		var assetSpecialChecked = new Array();//存放部分选择的设备
	   		//所有被勾选的组织，用于后台存储role_org记录
	   		var allCheckedOrgArray = new Array();
	   		for (var i=0;i<selectNodes.length; i++) {
	   			if (selectNodes[i].type == 1) {
		   			allOrgNodeChecked.push(selectNodes[i]);
	   			}
	   		}
	   		//如果组织节点展开了则需要对其子节点进行判断
	   		if (allOrgNodeChecked.length>0) {
	   			for (var k=0;k<allOrgNodeChecked.length; k++) {
	   				allCheckedOrgArray.push(allOrgNodeChecked[k].id);
	   				//var assetsOfNode = treeObj.transformToArray(treeObj.getNodeByParam("type", 2, allOrgNodeChecked[k]));
	   				var mixassetsOfNode = treeObj.transformToArray(allOrgNodeChecked[k].children);
	   				var assetsOfNode = new Array();
	   				for (var x=0; x<mixassetsOfNode.length; x++) {
	   					if (mixassetsOfNode[x].type == 2) {
		   					assetsOfNode.push(mixassetsOfNode[x]);
	   					}
	   				}
	   				if (assetsOfNode.length>0) {
	   					//定义子节点的个数和子节点选中的个数
	   					var sonNumber = assetsOfNode.length;
	   					var sonCheckedCount = 0;
	   					for (var j=0;j<assetsOfNode.length; j++) {
	   						if (assetsOfNode[j].checked) {
	   							sonCheckedCount++;
	   						}
	   					}
	   					if (sonCheckedCount == sonNumber) {
	   						//如果所有的子节点都被选中，则将组织节点都添加到fullOrgChecked数组中
	   						fullOrgChecked.push(allOrgNodeChecked[k].id);
	   					} else if (sonCheckedCount < sonNumber) {
	   						//有部分选择的设备
	   						for (var j=0;j<assetsOfNode.length; j++) {
		   						if (assetsOfNode[j].checked) {
		   							//将部分存入的设备存入节点中
		   							assetSpecialChecked.push(assetsOfNode[j].dbassetId + "_" + assetsOfNode[j].pId);
		   						}
	   						}
	   						partOrgChecked.push(allOrgNodeChecked[k].id);
	   					}
	   				} else {
	   					//如果该组织节点没有被展开
	   					fullOrgChecked.push(allOrgNodeChecked[k].id);
	   				}
	   			}
	   		}
	  		//与后台进行交互
			$.ajax({
				type: "POST",
				url: '${sys_ctx}/role/asyncInsertRoleRange.action',
		    	data: {
		    		'roleId' : roleId,
		    		'fullOrgChecked' : fullOrgChecked,
		    		'allCheckedOrgArray' : allCheckedOrgArray,
		    		'assetSpecialChecked' : assetSpecialChecked
		    		},
				dataType:'json',
				cache: false,
				success: function(data) {
					if (data.success) {
						layer.msg(data.msg,{time: 2000, icon:6});
					} else {
						var err = data.msg;
						layer.alert("保存失败，错误信息："+err, {title:'提示信息',icon: 5});
					}
					window.location.reload();
				},
				error: function(data){
					layer.msg('请检查网络！',{time: 2000, icon:5});
				}
			});
	  }
});
//判断某值是否在数组中
function isInArray(arr,value){
    for(var i = 0; i < arr.length; i++){
        if(value === arr[i]){
            return true;
        }
    }
    return false;
}
//重置，将所有选择框取消选择
$("#reset").on("click",function() {
	treeObj.checkAllNodes(false);
	clickReset = true;
});
	
  </script>
</html>
