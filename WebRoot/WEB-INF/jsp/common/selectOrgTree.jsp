<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>选择组织机构树</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.exhide.min.js"></script>
	<link rel="stylesheet" href="${sys_ctx}/plug/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">    
	<style>
	#basicform{
		padding:10px;
	}
		.tree-left{
			border:1px solid #a2a7a6;
			height:250px;
			overflow:auto;
			width:250px;
			background:none;
			float:left;
		}
		#mySelect{
			padding:0;margin:0;
			height:250px;
			width:250px;
			float:right;
			font-size:12px;
		}
		.bot-bottom{
			padding-top: 15px;
    		clear: both;
		}
		.default-one-input{width:180px;}
	</style>
  </head>
  <body>
		<div id="basicform" >
				<div style="margin-bottom:10px;">
					
      				<label>搜索 : </label>
	          		<input class="default-one-input" id="search" onkeypress="enterPress(event)" onkeyup="reStore();"  title="按回车搜索"/>
	          		<img src="${sys_ctx}/img/list/glass.png" onclick="serachRoom();" style="cursor:pointer;" title="搜索">
				</div>
          		
                	<div class="tree-left">
                		<ul id="tree" class="ztree"></ul>
                	</div>
				
          		<div class="tree-right">
          			
          		   <input name="methodName" id="methodName" value="${methodName}" type="hidden" />
                   <select id="mySelect"  multiple="multiple" >
                         <option value="${orgId }">${orgName }</option>
                   </select>
          		</div>
               
      	<div align="center" class="bot-bottom">
	        <button class="save" onclick="save()">确定</button>
	        <button class="cancel" onclick="cancel()">取消</button>
    	</div>
	</div>
  </body>
  <script type="text/javascript">
 	var setting = {	
		view: {  
	        selectedMulti: false  
	    },
		callback: {
			onDblClick: zTreeOnDblClick
		}
 	};
 	
 	function zTreeOnDblClick(event, treeId, treeNode) {
	 	if(treeNode){  
	 		$('#mySelect').val("");
			$('#mySelect').text("");//清空已有选择
	 		$('#mySelect').append("<option value='"+treeNode.id+"'>"+treeNode.name+"</option>");//添加
	 	}
 	}
 	
 	layui.use('layer', function(){ 
		layer = layui.layer; 
	});
 	
	$(function(){
  		$.ajax({
			type:"POST",
			url:"<%=path%>/org/queryOrgTree.action?open="+"false",
			cache: false,  
            dataType : "json",  
            success: function(data){  
            	if(data) {
            		$.fn.zTree.init($("#tree"),setting,eval(data));
            	}
         	}
		 });
  		
  		//双击清空选择
		$('#mySelect').dblclick(function(){
			$('#mySelect').val("");
			$('#mySelect').text("");
		});
  	});
	
	//搜索
	function serachRoom(){
		var searchRoom = $('#search').val();//搜索条件
		var zTree = $.fn.zTree.getZTreeObj("tree");
		var allNodes = zTree.transformToArray(zTree.getNodes());//将 zTree 使用的标准 JSON 嵌套格式的数据转换为简单 Array 格式
		if( searchRoom == "" ){
			for( var i=0; i<allNodes.length; i++ ){
				zTree.showNode(zTree.getNodeByParam("id",allNodes[i].id,null));
			}
			zTree.expandAll(false);
			return;
		}
		//先隐藏所有节点
		for( var i=0; i<allNodes.length; i++ ){
			zTree.hideNode(zTree.getNodeByParam("id",allNodes[i].id,null));
		}
		var isIn = false;
		for( var i=0; i<allNodes.length; i++ ){
			var node =allNodes[i];
			var flag = true;
			var strIndex = node.name.indexOf(searchRoom);
			if( strIndex != -1){//匹配到节点
				isIn = true;
				var showNode = zTree.getNodeByParam("id",node.id,null);//得到当前一个节点对象
				if (showNode.isParent == true){//如果不是子节点
					//所有子节点显示
					var childNodes = zTree.transformToArray(showNode);//获取该节点全部节点
					zTree.showNodes(childNodes);//显示其子节点
					zTree.expandNode(showNode,true,true,false,false);//展开该节点(其子节点执行同样操作)
				}
				while(flag){
					zTree.showNode(showNode);//显示该节点
					
					zTree.expandNode(showNode,true,false,false,false);//展开该节点(其子节点执行同样操作)
					if( showNode.isParent == false){//如果是子节点
					}
					if( showNode.getParentNode() != null){//如果不是根节点
						showNode = showNode.getParentNode();//获取父节点
					}else{
						flag = false;//是根节点则结束循环
					}
				}
			}
		}
		if( !isIn ){
			alert("没有相关信息");
		}
 	}
  
	//按回车搜索
	function enterPress(e){
 	   var e = e || window.event; 
 	   if(e.keyCode == 13){ 
 		   serachRoom();
 	   } 
    } 
	
	//清空搜索栏还原树结构
	function reStore(){
		var searchRoom = $('#search').val();//搜索条件
		var zTree = $.fn.zTree.getZTreeObj("tree");
		var allNodes = zTree.transformToArray(zTree.getNodes());
		if( searchRoom == "" ){
			for( var i=0; i<allNodes.length; i++ ){
				zTree.showNode(zTree.getNodeByParam("id",allNodes[i].id,null));
			}
			zTree.expandAll(false);
			return;
		}
	}
	
	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		parent.layer.close(index); //再执行关闭 
    }
	
	function save() {
		var mySelect = document.getElementById("mySelect");
		var length = mySelect.options.length;
		var orgIdArray = [];
		for(var k=0;k<length;k++){
			orgIdArray.push(mySelect.options[k].value);
        }
		if(!orgIdArray) {
			layer.msg('请选择单位！',{time: 2000, icon:5});
  		  	return;
		}
		var orgId = orgIdArray[0];
		var orgName = ($('#mySelect').text());
		var methodName = $('#methodName').val();//获取需要执行的方法
		eval("parent."+methodName+"('"+orgId+"','"+orgName+"')");
		laycls(0);
	}
	
    function cancel() {
  		laycls(0);
  	}
  </script>
</html>
