<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>操作用户</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<style>
		table{
			font-size:12px;
		}
		textarea{
			resize: none;
		    padding: 5px;
		    border-radius: 3px;
		    
		    width: 510px;
    		height: 140px;
			} 
		label{
			 font-weight:500; 
			margin-right:10px !important;
			width: 100px;
   			text-align: right;
   			color:#000;
   			display: block;
    		margin-bottom: 0;
    		margin-top: 5px;
    		display: inline-block!important;
		}
		input{
			border-radius: 3px;
		    padding-left: 10px;
		    height: 28px;
		    width:160px;
		}
		td{
    		padding: 5px 0;
		}
		select{
			line-height: 30px;
		    height: 28px;
		    border-radius: 3px;
		    width: 160px;
		    padding-left:5px
		}
		.btn-foot{
		    display: inline-block;
		    width: 100%;
		    text-align: center;
		    margin-top: 16px;
		}
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
		    padding: 0 22px;
		    color: #399efc;
		    margin: 0 20px;
		}
		#close{
			background: #fff;
		    border: 1px #399efc solid ;
		    border-radius: 3px;
		    line-height: 30px;
		    padding: 0 22px;
		    color: #399efc;
		    margin: 0 20px;
		}
		input[type=text]{
			line-height: 28px;
		}
		input[type=password]{
			line-height: 28px;
		}
 	 </style>
 	 
  </head>
  
  <body>
  	<div id="dlg" align="center" style="padding:10px 0  20px 0;" >
        <form id="fm" method="post">
        	<input name="userId" id="userId" value="${sysUser.userId}" type="hidden" />
  			<input name="orgId" id="orgId" value="${sysUser.orgId}" type="hidden" />
  			
  			<!-- 信息变动隐藏域 -->
  			<input name="oldMail" id="oldMail" value="${sysUser.email}" type="hidden" />
  			<input name="oldPhone" id="oldPhone" value="${sysUser.phone}" type="hidden" />  
  			<input name="oldFixedPhone" id="oldFixedPhone" value="${sysUser.tell}" type="hidden" />
  			<input name="oldDescription" id="oldDescription" value="${sysUser.description}" type="hidden" />  

            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
            	<tr>
					<td>
						<label>所属组织：</label><input type="text" id="orgName" value="${sysUser.orgName }" readonly style="background-color: #EBEBEA;cursor:hand;" data-rule-required="true"/>
					</td>
					 <td><label>邮箱：</label><input type="text" value="${sysUser.email }" name="email" data-rule-email="true"/></td>	    
			    </tr>
                <tr >
                    <td><label><span style="color: red;">*</span>用户名：</label>
                    	<input type="text" id="loginName" name="loginName" style="background-color: #EBEBEA;cursor:hand;" value="${sysUser.loginName}" data-rule-required="true" data-rule-isUserid="true" onchange="validateLoginName()">
                    </td>
                    <td><label>真实姓名：</label>
                    	<input type="text" value="${sysUser.userName }" style="background-color: #EBEBEA;cursor:hand;" name="userName" data-rule-required="true" title="请输入真实姓名"/>
                    </td>
                </tr>
                <tr >
                    <td><label><span style="color: red;">*</span>手机号：</label>
                    	<input id="isMobilephone" type="text" value="${sysUser.phone }" name="phone" data-rule-required="true" />
                    </td>	
                    <td><label>座机号：</label><input type="text" value="${sysUser.tell }" name="tell"/></td>		                    
                </tr>
                <tr >
                    <td><label><span style="color: red;">*</span>密码：</label>
                    	<input type="password" readonly="readonly" value="123456" style="background-color: #EBEBEA;cursor:hand;"/>
                    </td>	
                    <td><span style="color: red;">注：用户初始密码为：123456,首次登录后请修改密码</span></td>	                    
                </tr>
                <tr>
	             	<td colspan="2"> <label> 备注：</label>
           				 <textarea  id="description"  name="description" data-rule-maxlength="100">${sysUser.description }</textarea>	
	             	</td>
	             </tr>             	                         		                         
            </table>
      
        </form>
    </div>
	 <div class="btn-foot">
	 	<c:if test="${funtType != 'view' }">
		 	<button id="save" onclick="save()">保存</button>
	        <button id="cancel" onclick="cancel()">取消</button>
	 	</c:if>
        <c:if test="${funtType == 'view' }">
        	<button id="close" onclick="cancel()">关闭</button>
        </c:if>
    </div>
  </body>
  <script type="text/javascript">
  	  //增加验证手机号、用户名
  	 /*  $.validator.addMethod("isMobilephone",function(value,element){
	  	var mobilephoneReg = /^((\+86)|(86))?(1[3|4|5|8])\d{9}$/;
	  	return mobilephoneReg.test(value);
	  },"请输入正确的手机格式");
	  $.validator.addMethod("isUserid",function(value,element){
		  	var mobilephoneReg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$/;
		  	return mobilephoneReg.test(value);
	  },"只能输入数字、字母、下划线不能以下划线开头和结尾！"); */
  	   /* $("#loginName").blur(function() {
  	  		var isMobVal = $("#loginName").val();
  	  		var mobilephoneReg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$/;
  	  		 if(!mobilephoneReg.test(isMobVal)){
  	  		 	layer.msg('请输入正确的用户名！',{time: 2000, icon:5});
  	  		 }
  	  }) */
  	var url;
	var funtType = '${funtType}';
	$(function() {
		//新增时赋值
		if(funtType == "add"){
		    url = '${sys_ctx}/user/addUser.action';
		} else if(funtType == "edit"){
			url = '${sys_ctx}/user/modifyUser.action';
		} else if(funtType == "view") {
			$("#orgName").css("background-color","");
			setFormDisable('fm');
		} else if(funtType == "copy") {
			url = '${sys_ctx}/user/addUser.action';
		}
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
	
	//修改单位
	function updateOrgName(orgId,orgName) {
		$('#orgId').val(orgId);
		$('#orgName').val(orgName);
	}
	
	//选择单位
	function selectOrg(that) {
		if(funtType != "edit" && funtType != "copy") return;//只有编辑、复制时才能修改
		var orgName = that.value;
		var orgId = $('#orgId').val();
		var methodName = "updateOrgName";//修改单位需要执行的方法名
		var url = '<%=path%>/user/selectOrg.action?orgId='+orgId+'&orgName='+orgName+'&methodName='+methodName;
		url = encodeURI(url);
		layer.open({
	        type: 2 
	        ,id:'selectOrg'
	        ,title: '选择单位'
	        ,area: ['600px', '350px']
	        ,offset:'t'
	        ,shade: 0.2
	        ,maxmin: false
	        ,content: url
	        ,end: function(){
	        }
	      });
	}
	
	var isPass = true;//是否通过用户名重复验证
	//验证用户名是否重复
	function validateLoginName() {
		var loginName = $('#loginName').val();
		if(funtType == "edit") {//若是在修改的情况下，防止用户触发该事件重新把值改为打开时的值，但仍进行判断的情况
			if(loginName == '${sysUser.loginName}') {
				isPass = true;
				return;
			}
		}
		$.ajax({
			async:false,
			url:'${sys_ctx}/user/validateLoginName.action',
			type:"POST",
			cache:false,
			data:{'loginName':loginName},
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
	//保存js
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
  		if(!isPass) {//验证用户名未通过
			layer.msg('用户名不能重复，请确认！',{time: 2000, icon:5});
  			return;
  		}
  		var isMobVal = document.getElementById('isMobilephone').value;
  		var phonereg = /^[1][3,4,5,7,8][0-9]{9}$/;
  	  	if(!(phonereg.test(isMobVal))){
  	  		layer.msg('请输入正确的手机格式！',{time: 2000, icon:5});
  	  		return;
  	  	}
  		layer.confirm('确定要保存该信息吗？', {
	  		  btn: ['确定', '取消'],title:'提示信息',icon: 3 
	  		}, function(index, layero){//确定按钮回执函数
	  			$.ajax({
	  				async:true,
	  				url:url,
	  				type:"POST",
	  				cache:false,
	  				data:$('#fm').serialize(),
	  				dataType:'json',
	  				success: function (data) {
	  					if(data.success=="true"){
	  						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);  // 关闭layer
							window.parent.location.reload(); //刷新父页面
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
  	function cancel() {
  		laycls(0);
  	}
  </script>
</html>
