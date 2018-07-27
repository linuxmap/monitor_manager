<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>字典编码维护</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.core.min.js"></script>
	<link rel="stylesheet" href="${sys_ctx}/plug/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" href="${sys_ctx}/css/tree/treeStyle.css" type="text/css">  
	<!--[if lt IE 9]>
		<style>
		.jiedian{
				height:40px;
				width:100%;
				background-image:url(${sys_ctx}/css/jianbian.png);
				background-repeat: no-repeat;
			}
			html{
				height:100%
			}
		</style>
	<![endif]-->
  </head>
  <body style="width:100%;height:100%;overflow:hidden;">
     <div style="width:100%;height:100%;"class="background">
			<div style="width:20%;height:100%;float: left;" class="margin-top">	
				<div class="jiedian">
					<div class="jd-title">字典数据结构</div>
				</div>		
				<ul id="tree" class="ztree scroll-tree"></ul>
			</div>
			<div  style="width:80%;height:100%;float: right" >
				<iframe name="dictionaryItemList" id="dictionaryItemList" frameborder="0"  style="width:100%;height:100%;" ></iframe>
		    </div>
	</div>
  </body>
  <script type="text/javascript">
 	var setting = {	
		view: {  
	        selectedMulti: false  
	    },
		callback: {
			onClick: zTreeOnClick
		}
 	};
 	
 	function zTreeOnClick(event, treeId, treeNode) {
	 	if(treeNode){  
	    	var url = "<%=path%>/dictionaryItem/queryDictionaryItem.action?dictionaryId="+treeNode.code+"&dictionaryName="+treeNode.name;
	    	url = encodeURI(url);
	    	window.open(url,"dictionaryItemList");
	 	}
 	}
 	var treeObj = null;
 	var funtId = '${funtId}';//该页面funtId
 	var page_sign = 0;//判定是否刷新列表
	$(function(){
  		$.ajax({
			type:"POST",
			url:"<%=path%>/dictionaryInfo/queryDictionaryTree.action",
			cache: false,  
            dataType : "json",  
            success: function(data){  
            	if(data) {
            		$.fn.zTree.init($("#tree"),setting,eval(data));
            		treeObj = $.fn.zTree.getZTreeObj("tree");
            	}
         	}
		 });
  		window.open("<%=path%>/dictionaryItem/queryDictionaryItem.action","dictionaryItemList");
  	});
  
	layui.use('layer', function(){ 
		layer = layui.layer; 
	});
	
  </script>
</html>
