<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>授权1</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<style>
		a:visited,a:link,a:hover,a:active,a{text-decoration: none;font-size: 12px;color:#000;}
		.bor-left{width:200px;height:320px;float:left}			
		.box-center{width:90px;margin-top:20%;float:left}
		.box-center	a{display: block;width:60px; border-radius: 3px;height:24px;font-size: 14px;background: #399efc;color: #fff;text-align: center;margin:10px;line-height:24px;margin: 10px 16px;}		
		.box-right{width:200px;height:320px;float:left;}
		select{width:200px;height:300px!important;padding-top:10px;padding-bottom:10px;}	
		.box-big{display: flex;align-items: center;flex-direction: column;font-size:12px;}
		.box-middle{padding: 10px 0 0 25px;width:520px;height:340px;}
		.box-big button{display:inline-block ;padding: 0 10px;}
		.box-big span{display: inline-block;width: 200px;height: 20px;font-size: 14px; color:#666;border-bottom: 0;line-height: 20px;}
		#save{
			background: #399efc;
		    border: 0;
		    border-radius: 3px;
		    line-height: 30px;
		    padding: 0 22px;
		    color: #fff;
		    margin: 0 20px;
		}
		#cancel{
			background: #fff;
		    border: 1px #399efc solid ;
		    border-radius: 3px;
		    line-height: 30px;
		    padding: 0 21px;
		    color: #399efc;
		    margin: 0 20px;
		}
	</style>
  </head>
  
  <body>
	<div class="box-big">
	   <div class="box-middle">	
	   	<input  type="hidden" value="${userId }" name="userId" id="userId"/>
        <div class="bor-left">
        	<span>未赋予的角色</span>
		    <select id="unSelect" multiple="multiple">
		    	<c:forEach items="${unRoleList}" var="unRole" >
					<option value="${unRole['role_id']}">
						${unRole['role_name'] }
					</option>
				</c:forEach>
		    </select>
         </div>
		<div class="box-center">
		    <p><a href="###" id="add">></a></p>
		    <p><a href="###" id="add_all">>></a></p>
		    <p><a href="###" id="remove"><</a></p>
		    <p><a href="###" id="remove_all"><<</a></p>
		</div>
		<div class="box-right">
		    <span>已赋予的角色</span>
		    <select id="mySelect" multiple="multiple">
		    	<c:forEach items="${myRoleList}" var="myRole" >
					<option value="${myRole['role_id']}" >
						${myRole['role_name'] }
					</option>
				</c:forEach>
		    </select>
		</div>
	  </div>
	  <p style="text-align: center;">
	  	<button id="save" onclick="save()">保存</button>
        <button id="cancel" onclick="cancel()">取消</button>
	  </p>
	</div>
  </body>
  <script type="text/javascript">
	$(function(){	
		//移到右边
		$('#add').click(function(){
			//先判断是否有选中
			if(!$("#unSelect option").is(":selected")){			
				alert("请选择需要移动的选项")
			}
			//获取选中的选项，删除并追加给对方
			else{
				$('#unSelect option:selected').appendTo('#mySelect');
			}	
		});
		
		//移到左边
		$('#remove').click(function(){
			//先判断是否有选中
			if(!$("#mySelect option").is(":selected")){			
				alert("请选择需要移动的选项")
			}
			else{
				$('#mySelect option:selected').appendTo('#unSelect');
			}
		});
		
		//全部移到右边
		$('#add_all').click(function(){
			//获取全部的选项,删除并追加给对方
			$('#unSelect option').appendTo('#mySelect');
		});
		
		//全部移到左边
		$('#remove_all').click(function(){
			$('#mySelect option').appendTo('#unSelect');
		});
		
		//双击选项
		$('#unSelect').dblclick(function(){ //绑定双击事件
			//获取全部的选项,删除并追加给对方
			$("option:selected",this).appendTo('#mySelect'); //追加给对方
		});
		
		//双击选项
		$('#mySelect').dblclick(function(){
			$("option:selected",this).appendTo('#unSelect');
		});
		
	});
	
	layui.use('layer', function(){ 
		var layer = layui.layer; 
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
		var roleIds = [];
		for(var k=0;k<length;k++){
			roleIds.push(mySelect.options[k].value);
        }
        if (roleIds.length > 1) {
        	layer.msg("只能选择一个角色", {title:'提示信息',icon: 6});
        	return;
        }
    	var userId = $('#userId').val();
    	$.ajax({
			async:false,
			url:"${sys_ctx}/userRole/saveSelectRole.action",
			type:"POST",
			cache:false,
			data:"userId="+userId+"&roleIds="+roleIds,
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
	</script>
</html>
