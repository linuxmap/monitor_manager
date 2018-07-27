 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>选择用户树</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.exhide.min.js"></script>
	<link rel="stylesheet" href="${sys_ctx}/plug/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">    
	<style>
	#basicform{
		padding:5px;
	}
		.tree-left{
			border:1px solid #a2a7a6;
			height:250px;
			overflow:auto;
			width:210px;
			background:none;
			float:left;
		}
		#mySelect{
			padding:0;margin:0;
			height:250px;
			width:210px;
			float:right;
			font-size:12px;
		}
		.bot-bottom{
			padding-top: 15px;
    		clear: both;
		}
	</style>
  </head>
  <body>
		<div id="basicform" >
           	<div class="tree-left">
           		<ul id="tree" class="ztree"></ul>
           	</div>
			<div class="tree-right">
       		    <input name="methodName" id="methodName" value="${methodName}" type="hidden" />
                <select id="mySelect" multiple="multiple" >
                </select>
          	</div>
	      	<div align="center" class="bot-bottom">
	      	    <button class="save" onclick="revoke()">撤销</button>
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
			onClick: zTreeOnClick
		}
 	};
 	/* function hideNodes() {
		var zTree = $.fn.zTree.getZTreeObj("tree"),
		nodes = zTree.getSelectedNodes();
		if (nodes.length == 0) {
			alert("请至少选择一个节点");
			return;
		}
		
	}; */
	/* function showNodes() {
		var zTree = $.fn.zTree.getZTreeObj("tree"),
		nodes = zTree.getNodesByParam("isHidden", true);
		zTree.showNodes(nodes);
	}; */
 	function zTreeOnClick(event, treeId, treeNode) {
 		var ff=$("#mySelect option")
 		for(var i=0;i<ff.length;i++){
	 		if(treeNode.name === ff.eq(i).text()){
	 			return;
	 		}
 		}
 		$('#mySelect').append("<option tel='"+treeNode.telephone+"' value='"+treeNode.userId+"'>"+treeNode.name+"</option>");//添加
 	};
 	//单击清空选择
	$("#mySelect").delegate("option","click",function(){
		$(this).remove();
		
	});   
 	var adminName = '${adminName}';
 	var adminId = '${adminId}';
 	var adminTel = '${adminTel}';

 	var layer;//layer弹出框
 	var treeObj = null;
	$(function(){
  		$.ajax({
			type:"POST",
			url:"<%=path%>/user/queryUserTree.action?open="+"false",
			cache: false,  
            dataType : "json",  
            success: function(data){  
            	if(data) {
            		$.fn.zTree.init($("#tree"),setting,eval(data));
            	}
         	}
		 });
  		
  		
		if(null !=adminName &&""!=adminName && null !=adminId &&""!=adminId){
			var adminNames = adminName.split(",");
		 	var adminIds = adminId.split(",");
		 	var adminTels = adminTel.split(",");
	
			for(var i=0;i<adminNames.length;i++){
				$('#mySelect').append("<option tel='"+adminTels[i]+"' value='"+adminIds[i]+"'>"+adminNames[i]+"</option>");//添加
			}	
		}
  	});
		layui.use('layer', function(){ 
			layer = layui.layer; 
  		});
		
	function revoke(){
		$("#mySelect").empty(); 
	};
	
	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		parent.layer.close(index); //再执行关闭 
    };
	
	function save() {
		var adminId=[];
		var adminName=[];
		var adminTel = [];
		var options=$("#mySelect option");
		for(var k=0;k<options.length;k++){
			adminId.push(options[k].value);
			adminName.push(options[k].innerHTML);
			adminTel.push(options.eq(k).attr("tel"));
        }
		if(!adminId) {
			layer.msg('请选择用户！',{time: 2000, icon:5});
  		  	return;
		}
		var adminIds = adminId;
		var adminNames = adminName;
		var adminTels = adminTel;

		var methodName = $('#methodName').val();//获取需要执行的方法
		eval("parent."+methodName+"('"+adminIds+"','"+adminNames+"','"+adminTels+"')");
		laycls(0);
	};
	
    function cancel() {
  		laycls(0);
  	};
  </script>
</html>
 