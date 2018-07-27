 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>选择楼层树</title>
    
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
       		<div  class="tree-right">	
       		   <input name="methodName" id="methodName" value="${methodName}" type="hidden" />
                <select id="mySelect" multiple="multiple" >
                      <option value="${floorId }">${floorName}</option>
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
			var level = treeNode.level;
			if(level == "2") {
				$('#mySelect').append("<option value='"+treeNode.id+"'>"+treeNode.name+"</option>");//添加
			} else {
				layer.msg('只能选择楼层！',{time: 1000, icon:5});
			}
	 	}
 	}
 	
 	var treeObj = null;
	$(function(){
		$.ajax({
			type:"POST",
			url:"<%=path%>/floor/queryFloorTree.action",
			cache: false,  
            dataType : "json",  
            success: function(data){  
            	if(data) {
            		$.fn.zTree.init($("#tree"),setting,eval(data));
            		treeObj = $.fn.zTree.getZTreeObj("tree");
            	}
         	}
		 });

  		window.open("<%=path%>/floor/queryFloors.action","floorList");
  		
  		//双击清空选择
		$('#mySelect').dblclick(function(){
			$('#mySelect').val("");
			$('#mySelect').text("");
		});
  	});
	
	layui.use('layer', function(){ 
		layer = layui.layer; 
	});
	
	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		parent.layer.close(index); //再执行关闭 
    }
	
	function save() {
		var mySelect = document.getElementById("mySelect");
		var length = mySelect.options.length;
		var floorIdArray = [];
		for(var k=0;k<length;k++){
			floorIdArray.push(mySelect.options[k].value);
        }
		if(!floorIdArray) {
			layer.msg('请选择楼层！',{time: 2000, icon:5});
  		  	return;
		}
		var floorId = floorIdArray[0];
		var floorName = $('#mySelect').text();
		var methodName = $('#methodName').val();//获取需要执行的方法
		eval("parent."+methodName+"('"+floorId+"','"+floorName+"')");
		laycls(0);
	}
	
    function cancel() {
  		laycls(0);
  	}
  </script>
</html>
 