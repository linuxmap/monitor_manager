<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<%@include file="/WEB-INF/jsp/common/common.html"%> 
<title></title>
<link rel="stylesheet" href="${sys_ctx}/css/from/operateOrg.css" type="text/css"></link>
	<style type="text/css">
		label{
		margin-top: 5px;
		}
	</style>
</head>
	<body>
		<form id="fm" name="fm" style='margin:0'>
			<input name="groupId" id="groupId" value="${vmsGroup.groupId}" type="hidden">
			<div class="tabcenter">
				<table class="table" style="margin-top:20;margin-bottom:0;">
				    <tr >
	                    <td style="border:none"><span style="margin-right:15px;"><span style="color: red;">*</span>分组名称:</span><input type="text" id="name" name="name" value="${vmsGroup.name}" data-rule-required="true" title="请输入分组名称" onchange="validateLevelName()" style="width:120px;"></input></td>		  		                    
	                </tr>
				</table>
			</div>
		</form>
	 <div class="btn-foot">
		 	<button id="save" onclick="save()">保存</button>
	        <button id="cancel" onclick="cancel()">取消</button>
     </div>
	</body>
<script type="text/javascript">
	layui.use('layer', function() {
		layer = layui.layer;
	});
		
	function cancel() {
  		laycls(0);
  	}
  	var isPass = true;//是否通过姓名重复验证
  	//验证告警级别名称是否重复
	function validateLevelName() {
		var name = $('#name').val();
		$.ajax({
			async:false,
			url:'${sys_ctx}/group/validateGroupName.action',
			type:"POST",
			cache:false,
			data:{'name':name},
			dataType:'json',
			success: function (data) {
				if(data.success=="false"){
					isPass = false;
				} else {
					isPass = true;
				}
			},
			error: function(xhr){
				isPass = false;
			}
		});
	}
	
	//保存告警级别
	function save() {
  		$('#fm').validate({
  			showErrors: function (errorMap, errorList) {
                $.each(errorList, function (i, v) {
                    //在此处用了layer的方法,显示效果更美观
                    layer.tips(v.message, v.element, { time: 2000,tips: [3, '#F69805'] });
                    return false;
                });  
            },
            /* 失去焦点时不验证 */
            onfocusout: false
  		});
  		//验证不通过
  		if(!$('#fm').valid()){
  			return;
  		}
  		
  		var groupId = $('#groupId').val();
  		var url;
  		if(groupId){
  			url = '${sys_ctx}/group/modifyGroup.action';
  		}else{
	  		//如果没有值则是新增,验证重复
  			url = '${sys_ctx}/group/addGroup.action';
	  		if(!isPass) {//验证组织机构名未通过
	  			layer.msg('级别名称重复，请确认！',{time: 2000, icon:5});
	  			return;
	  		}
  		}
  		
  		layer.confirm('确定要保存该信息吗？', {
	  		  btn: ['确定', '取消'],icon: 3 
	  		}, function(index, layero){//确定按钮回执函数
	  			$.ajax({
	  				async:true,
	  				url : url,
	  				type : "POST",
	  				cache : false,
	  				data : $('#fm').serialize(),
	  				dataType : 'json',
	  				success : function (data) {
	  					if(data.success=="true"){
	  						laycls(1);
	  					} else {
	  						layer.alert(data.msg, {title:'提示信息',icon: 5});
	  					}
	  				},
	  				error: function(xhr){
	  					
	  				}
	  			});
	  		
	  		}, function(index){//取消按钮回执函数
	  		});
  		
  	}
  	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		parent.layer.close(index); //再执行关闭 
    }
</script>
</html>
