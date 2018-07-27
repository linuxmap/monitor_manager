<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>授权</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.core.min.js"></script>
	<link rel="stylesheet" href="${sys_ctx}/plug/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" href="${sys_ctx}/css/tree/treeStyle.css" type="text/css">
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.exhide.min.js"></script>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.excheck.min.js"></script>
	<style>
	#cancel {
	    background: #fff;
	    border: 1px #399efc solid;
	    border-radius: 3px;
	    line-height: 30px;
	    padding: 0 22px;
	    color: #399efc;
	    margin: 0 20px;
	}
	#saveUserRole {
	    background: #399efc;
	    border: 0;
	    border-radius: 3px;
	    line-height: 30px;
	    padding: 0 22px;
	    color: #fff;
	    margin: 0 20px;
	}
	.btn-foot{
		text-align:center;
	}
	</style>
  </head>
  <body style="width:100%;height:380px;overflow:hidden;">
    <div style="width:100%;height:100%;background:#fff" class="background">
    	<input  type="hidden" value="${userIds }" name="userIds" id="userIds"/>
		<div class="margin-top" style="margin-top:0px;border:none;">
			<ul id="tree" class="ztree scroll-tree" style="margin:10px;padding:10px;border:1px #ddd solid"></ul>
		</div>
		<div class="btn-foot">
			<button id="saveUserRole" onclick="saveUserRole()">保存</button>
	        <button id="cancel" onclick="cancel()">取消</button>
		</div>
	</div>
  </body>
  <script type="text/javascript">
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
 			chkStyle: "radio",
 			radioType: "all"
 		},
		view: {  
	        selectedMulti: false  
	    },
		callback: {
			onClick: zTreeOnClick
		}
 	};
 	
 	var hiddenNodes=[]; //用于存储被隐藏的结点
	$("#search-bt").on("click", function() {
		filter();
	})
	//过滤ztree显示数据
	function filter(){
	    //显示上次搜索后背隐藏的结点
	    var zTreeObj = $.fn.zTree.getZTreeObj("tree");
	    zTreeObj.showNodes(hiddenNodes);
	
	    //查找不符合条件的叶子节点
	    function filterFunc(node){
	        var _keywords=$("#keyword").val();
	        if(node.isParent||node.name.indexOf(_keywords)!=-1) return false;
	        return true;        
	    };
	
	    //获取不符合条件的叶子结点
	    hiddenNodes=zTreeObj.getNodesByFilter(filterFunc);
	
	    //隐藏不符合条件的叶子结点
	    zTreeObj.hideNodes(hiddenNodes);
	};
 	function zTreeOnClick(event, treeId, treeNode) {
	 	
 	}
 	
	var page_sign = 0;//判定是否刷新列表
 	var treeObj = null;
	$(function(){
		loadTree();
  	});
	
	function loadTree() {
		$.ajax({
			async:false,
			type:"POST",
			url:"<%=path%>/user/asyncLoadRoleTree.action",
			cache: false,  
            dataType : "json",  
            success: function(data){  
            	if(data) {
            		var ztree = data.ower;
            		$.fn.zTree.init($("#tree"),setting,eval(ztree));
            		treeObj = $.fn.zTree.getZTreeObj("tree");
            	}
         	}
		 });
	}
	
	layui.use('layer', function(){ 
		layer = layui.layer; 
	});
	
	//确定保存关联关系
	function saveUserRole(){
		//获取选中的节点
		var zTreeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = zTreeObj.getCheckedNodes(true);
		var roleId;
		if(nodes && nodes.length == 1){
			roleId = nodes[0].id;
		}else{
			roleId = "";
		}
		var userIds = $("#userIds").val();
		if (!userIds) {
			layer.msg("未携带用户信息",{time: 2000, icon:6});
			return;
		}
		var url = '${sys_ctx}/user/addOrgAsset.action';
		$.ajax({
			async:false,
			url:"${sys_ctx}/userRole/saveBatchSelectRole.action",
			type:"POST",
			cache:false,
			data:"userIds="+userIds+"&roleId="+roleId,
			dataType:'json',
			success: function (data) {
				if(data.success=="true"){
					laycls(1);
				} else {
					layer.alert(data.msg, {title:'提示信息',icon: 5});
				}
			},
			error: function(xhr){
				
			}
		});
	}

  	function cancel() {
 		laycls(0);
  	}
  	
  	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		parent.layer.close(index); //再执行关闭 
    }
  
  </script>
</html>
