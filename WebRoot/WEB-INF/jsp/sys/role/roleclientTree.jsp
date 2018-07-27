<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>客户端</title>
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
  </style>
  <body>
    
  	<div style="width:100%;height:100%;" class="background">
  		<div class="panel panel-default" style="text-align: right" > 
    		<!-- 角色id -->
    		<input type="hidden" id="roleId" name="" />
			<button type="button" class="default-one-btn" id="save">确定</button>
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
var clientTreeObj;
var zTreeNodes;
$(function(){
	//初始化ztree树
	var setting = {
			view : {
				selectedMulti : true
			},
			check: {
				enable: true,
				chkboxType : { "Y": "p", "N": "" }//取消父子关联
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
	var zn = '${clienTreeJson}';
	zTreeNodes = eval(zn);
	zTree = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
	clientTreeObj = $.fn.zTree.getZTreeObj("tree");
	var node =  clientTreeObj.getNodeByParam("id", 0, null);
	clientTreeObj.expandAll(true);
	if (node) {
		var node = clientTreeObj.getNodes();
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
    	var funtArray = new Array();
    	var choosedNodes = clientTreeObj.getCheckedNodes(true);
    	if(null != choosedNodes && choosedNodes.length>0){
	    	for(var i=0; i<choosedNodes.length; i++){
	    		funtArray.push(choosedNodes[i].id);
	    	}
    	} else {
    		funtArray.push(-1);
    	}
    	/* if(!orgIdArray){
    		orgIdArray = null;
    	}
    	if(!equIdArray){
    		equIdArray = null;
    	} */
		$.ajax({
			type: "POST",
			url: '${sys_ctx}/role/updateDoubleFuntByRoleId.action',
	    	data: {
	    		'roleId':roleId,
	    		'funtIds':funtArray,
	    		'funtGroup' : 2
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
		clientTreeObj.checkAllNodes(false);
	});
	
  </script>
</html>
