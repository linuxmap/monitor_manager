<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>用户管理2</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.core.min.js"></script>
	<link rel="stylesheet" href="${sys_ctx}/plug/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">  
	<link rel="stylesheet" href="${sys_ctx}/css/tree/treeStyle.css" type="text/css">  
    <script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.exhide.min.js"></script></head>
  
  <body style="width:100%;height:100%;overflow:hidden;">
    <div style="width:100%;height:100%;" class="background">
		<div style="width:20%;height:100%;float:left;" class="margin-top">
			<div class="jiedian">
				<div class="jd-title">部门组织结构</div>
			</div>
			<div class="search-bar" style="padding-left:30px;">
	            <input id="keyword" type="text" class="default-one-input" placeholder="请输入...">
	            <button id="search-bt" class="" style="vertical-align:middle;">搜索</button>
	        </div>	
			<ul id="tree" class="ztree scroll-tree"></ul>
		</div>
		<div  style="width:80%;height:100%;float: right" >;
			<iframe name="userList" id="userList" frameborder="0" style="width:100%;height:100%; overflow-y:auto;" ></iframe>
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
 	
 	var hiddenNodes=[]; //用于存储被隐藏的结点
	$("#search-bt").on("click", function() {
		        var zTree = $.fn.zTree.getZTreeObj("tree");
		        //显示隐藏的节点
		        nodes = zTree.getNodesByParam("isHidden", true);
		        zTree.showNodes(nodes);
		        
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
	    	var url = "<%=path%>/user/queryUsers.action?orgId="+treeNode.id+"&orgName="+treeNode.name;
	    	url = encodeURI(url);
	    	window.open(url,"userList");  
	 	}
 	}
 	
 	var funtId = '${funtId}';//该页面funtId
 	var page_sign = 0;//判定是否刷新列表
 	var treeObj = null;
	$(function(){
		var winH =$(window).height()-123+"px";
		$("#tree").height(winH);
  		$.ajax({
			type:"POST",
			url:"<%=path%>/org/queryOrgTree.action",
			cache: false,  
            dataType : "json",  
            success: function(data){  
            	if(data) {
            		$.fn.zTree.init($("#tree"),setting,eval(data));
            		treeObj = $.fn.zTree.getZTreeObj("tree");
            	}
         	}
		 });
  		window.open("<%=path%>/user/queryUsers.action","userList");
  	});
  
	layui.use('layer', function(){ 
		layer = layui.layer; 
	});
	
  </script>
</html>
