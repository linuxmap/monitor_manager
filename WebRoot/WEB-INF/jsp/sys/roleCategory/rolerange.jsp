<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>权限范围4</title>
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
			<button type="button" class="default-one-btn-top" id="reset">取消</button>
		</div> 
		<div style="float:left;margin-top:0" class="margin-top">
			<ul id="tree" class="ztree scroll-tree"></ul>
		</div>


	</div>
	
  </body>
  <script type="text/javascript">
//定义ztree变量
var dataRoleid;
var roleId;
var zTree;
var treeObj;
var zTreeNodes;
$(function(){
	//初始化ztree树
	var setting = {
			view : {
				selectedMulti : true
			},
			check: {
				enable: true
			},
			data : {
				simpleData : {
					enable : true
				},
				key: {
					name:"name",
					title: "name"
				}
			},
			callback: {
			
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
		for(var i=0;i<node.lenght;i++){
			allNodes.push(nodes[i]);
		}
	}
	
	/* 测试权限节点push属性 */
});	
 	
 	var funtId = '${funtId}';//该页面funtId
 	var page_sign = 0;//判定是否刷新列表
 	
 	var hiddenNodes=[]; //用于存储被隐藏的结点
  
	layui.use('layer', function(){ 
		layer = layui.layer; 
	});
	
	//保存
    $("#save").on("click",function(){
		$('#roles li', window.parent.document).each(function(){
    		if($(this).hasClass("active")){
    			var choosedLi = $(this);
			   	dataRoleid = choosedLi.attr("data-roleid");
			    roleId=dataRoleid;
    		}
    	})
    	if(!roleId){
    		layer.alert("请选择角色");
    		return;
    	}
    	var orgIdArray = new Array();
    	var equIdArray = new Array();
    	var choosedNodes = treeObj.getCheckedNodes(true);
    	/* if(choosedNodes.length == 0){
    		layer.alert("请选择节点");
    		return;
    	} */
    	if(choosedNodes){
	    	for(var i=0; i<choosedNodes.length; i++){
	    		if(choosedNodes[i].type == 1){
	    			orgIdArray.push(choosedNodes[i].id);
	    		}else{
	    			//资产 及其 父节点组织     用  _ 分隔
	    			equIdArray.push(choosedNodes[i].dbassetId + "_" + choosedNodes[i].pId);
	    		}
	    	}
    	}
    	/* if(!orgIdArray){
    		orgIdArray = null;
    	}
    	if(!equIdArray){
    		equIdArray = null;
    	} */
		$.ajax({
			type: "POST",
			url: '${sys_ctx}/role/updateRoleRange.action',
	    	data: {
	    		'roleId':roleId,
	    		'orgIds':orgIdArray,
	    		'equIds':equIdArray
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
			},
			error: function(data){
				layer.msg('请检查网络！',{time: 2000, icon:5});
			}
	});
	});

	//重置，将所有选择框取消选择
	$("#reset").on("click",function(){
		treeObj.checkAllNodes(false);
	});
	
  </script>
</html>
