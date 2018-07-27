<%@page import="zst.extend.util.PropertiesUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
String sysName = PropertiesUtil.getPropertiesValue("sysConfig.properties", "sys_name");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><%=sysName %></title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=8" /> 
	<%@include file="/WEB-INF/jsp/common/common.html"%> 
	<script type='text/javascript' src='${sys_ctx }/js/md5/md5.min.js'></script><!--MD5加密js -->
	<link rel="stylesheet" href="${sys_ctx }/css/login.css" />
	<script type="text/javascript"> 
		//解决session过期跳转到登陆页面并跳出iframe框架
		if(window != top)
			top.location.href = location.href;
	</script>

	<!--[if lt IE 9]>
		<style>
		::-ms-clear,
			::-ms-reveal
			{
			display:none;
			}
		.bot-rem{
			display:none
		}	
		</style>
	<![endif]-->
	
  </head>
  <body onkeydown="enterSubmit(event,this)">
 	<div class="top-login">
		<div class="navbar">
		  <div class="navbar-inner">
			<div class="container-fluid">
				<%-- <dl class="pull-left">
					<dt class="pull-left"><img class="img-circle" src="${sys_ctx}/img/login_logo.png?random=<%=Math.random() %>"/></dt>
					<dd class="pull-left"><%=sysName %></dd>
				</dl> --%>
				 <p class="pull-left line">
					<span class="pull-left"><img class="img-circle" src="${sys_ctx}/img/login_logo.png?random=<%=Math.random() %>"/></span>
					<span class="pull-left size"><%=sysName %></span>
				 </p>
			</div>
		</div>
      </div>	
	</div>
	<form method="POST" id="loginForm" name="loginForm">
		<div class="center-login">
			<img src="${sys_ctx }/img/login_background.png"  style="width:100%"/>
			<div class="panel panelztt" >
			  
			  <!-- 选择登录方式 0：本地验证，1：AD域集中验证 -->
			  <div class="panel-body">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="adLogin" name="loginType" type="radio" value="0" style="border: 0 !important;margin-top: 0;width:18px;height:18px;margin-right:10px"/><label for="adLogin">AD域账号</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="normalLogin" name="loginType" type="radio" value="1" style="border: 0 !important;margin-top: 0;width:18px;height:18px;margin-right:10px" checked="checked"/><label for="normalLogin">普通账号</label></div>
			  <div class="panel-body">
			  	<p style="height:30px; position:relative; margin-top:0px;">
			  		<span class="glyphicon glyphicon-user span-user"></span>
			  		<input id="loginName" name="loginName" type="text" class="form-control login-user" placeholder="用户名 "line-height: 30px>
			  		<span type="button" onclick="loginNameClear()" class="glyphicon glyphicon-remove form-control-feedback bot-rem"></span>
			  	</p>
			    <p>
			  		<span class="glyphicon glyphicon-lock span-user0"></span>
			  		<input id="loginPwd" name="loginPwd" type="password" class="form-control login-user" placeholder="密码" onchange="pwdChange()">
			  		<span type="button" onclick="loginPwdClear()" class="glyphicon glyphicon-remove form-control-feedback bot-rem"></span>
			  	</p>
			  	<!--  <p>
			  		<input style="width:50%" type="text"  id="code" name="code" class="form-control col-xs-6" placeholder="输入验证码">
			  		<span  style="display:inline-block;width:50%" class="yanimg  col-xs-6"><img id="codevalidate" alt="验证码" style="cursor: hand" onclick="getCode()"/></span>
			  	</p>-->
			  	<div class="checkbox checd-login">
				    <label>
				      <input type="checkbox" id="rember" name="rember" value="y"  style="border:0 !important"> 记住用户名和密码
				    </label>
			    </div>
			    <button type="button" class="btn" style="width:100%;background:#000;color:#fff" onclick="login()">登录</button>
			    <div id="message" style="margin-left: 12px;margin-top: 10px;">
			  		&nbsp;<span style="font-size:12px;color:#ff0000"><c:if test="${!empty forceLoginMsg }">提示信息: ${forceLoginMsg }</c:if></span>
	         	</div>
			  </div>
			</div>
		</div>
	</form>
	
	<!-- <div class="bottom-login">
		<address>
		  <strong>版权信息：</strong>
		 	 COPYRIGHT&copy; 2008 北京真视通科技股份有限公司VERSION 1.0.0
		</address>
	</div> -->
  </body>
  <script type="text/javascript">
  	$(function(){
  		//getCode();//获取验证码
  		getCookie();//获取cookie
  		$("#loginName").focus();
  		 $("#rember").click(function () {
		    if ($("#loginName").val() == "") {
		     alert("用户名不能为空！");
		    }
		    if($("#loginPwd").val() == "")
		    {
		     alert("密码不能为空！");
		    }
  		 });
  		 
  		 if(getCookie("uname") != null){
		    $('#rember').attr("checked", "checked");
		    $('#loginName').val(getCookie("uname"));
		    $('#loginPwd').val(getCookie("upwd"));
		   }
  	});
  	
  	  //写cookies
	function setCookie(name, value) {
		   var Days = 30;
		   var exp = new Date();
		   exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
		   document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
		}
		  //读取cookies 
		function getCookie(name) {
		   var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
		   if (arr = document.cookie.match(reg)) return unescape(arr[2]);
		   else return null;
		}
		  //删除cookies 
		function delCookie(name) {
		   var exp = new Date();
		   exp.setTime(exp.getTime() - 1);
		   var cval = getCookie(name);
		   if (cval != null) document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
		}
  	
  	
  	
  	//清空用户名
  	function loginNameClear() {
  		$('#loginName').val('');
  	}
  	//清空密码
  	function loginPwdClear() {
  		$('#loginPwd').val('');
  	}
  	
  	//获取验证码
  	function getCode() {
		$('#codevalidate').attr("src","${sys_ctx}/code?v="+new Date().valueOf());
	}
  	
  	var isMd5 = true;//判断是否进行md5加密
  	//密码框值改变，即需要进行md5加密
  	function pwdChange(){
  		isMd5 = true;
  	}
  	
    //按回车键提交
  	function enterSubmit(event, input) {
  		if(event.keyCode == 13) {
  			var loginName = $("#loginName").val();
  			var loginPwd = $("#loginPwd").val();
  			if(loginName && !loginPwd){
  				$("#loginPwd").focus();
  				return;
  			}
  			login();
  		}
  	}
    
  	//获取cookie
/*     function getCookie() {
    	$.post('${sys_ctx}/login/getCookie.action',
			function(data){
 				if(data.success=="true"){
					$('#loginName').val(data.msg);
					$('#loginPwd').val(data.ower);
					isMd5 = false;
					login();//若该用户存入cookie,直接跳转到登录
				}
			},'json');
    } */
    
    //判断是否勾选保存账号密码
    
  	//登录
  	function login() {
  		//选择登录方式
  		var loginType;
  		var loginTypeArr = document.getElementsByName("loginType");
  		if (loginTypeArr) {
  			for (var i=0;i<loginTypeArr.length;i++){
  				if (loginTypeArr[i].checked) {
  					loginType = loginTypeArr[i].value;
  				}
  			}
  		}
  		if(!loginType) {
  			strMessage ="请选择登录方式！";
			$("#message").html('提示信息：<span style="font-size:12px;color:#ff0000">' + strMessage + '</span>');
			return;
  		}
  		var checked =$('#rember') ;
  		 if (checked[0].checked==true) {
		     setCookie("uname", $("#loginName").val(), 60);
		     setCookie("upwd", $("#loginPwd").val(), 60);
		     }else {
		      delCookie("uname");
		      delCookie("upwd");
		    }
  		
  		var strMessage = "";
  		//用户名
		var loginName = $.trim($("#loginName").val());
		$("#loginName").val(loginName);
		if(!loginName){
			strMessage ="请输入用户名！";
			$("#loginName").focus();
			$("#message").html('提示信息：<span style="font-size:12px;color:#ff0000">' + strMessage + '</span>');
			return;
		}
		
		//密码 
		var loginPwd = $.trim($("#loginPwd").val());
		if(isMd5 && loginType==1) {
			//loginPwd = md5(loginPwd);//普通账号加密处理
		}
		$("#loginPwd").val(loginPwd);
		if(!loginPwd){
			strMessage ="请输入密码！";
			$("#loginPwd").focus();
			$("#message").html('提示信息：<span style="font-size:12px;color:#ff0000">' + strMessage + '</span>');
			return;
		}
		
		/*//验证码
		var code = $.trim($("#code").val());
		if(!code){
			strMessage ="请输入验证码！";
			$("#code").focus();
			$("#message").html('提示信息：<span style="font-size:12px;color:#ff0000">' + strMessage + '</span>');
			return;
		}*/
		//普通账号登录
		if (loginType==1) {
			$.ajax({
				async:false,
				url:"${sys_ctx}/login/checkUser.action",
				type:"POST",
				cache:false,
				data:$('#loginForm').serialize(),
				dataType:'json',
				success: function (data) {
					if(data.success == "false"){
						 $("#message").html('提示信息：<span style="font-size:12px;color:#ff0000">' + data.msg + '</span>');
						 //getCode();//刷新验证码
						 isMd5 = false;//不进行md5加密
					} else {
						//跳转到首页
						loginForm.action = "${sys_ctx}/login.action";
	                	loginForm.submit();
					}
				},
				error: function(xhr){
					$("#message").html('提示信息：<span style="font-size:12px;color:#ff0000">系统异常,请联系管理员！</span>');
				}
			});
		}
		//AD域验证
		if (loginType==0) {
			$.ajax({
				async:false,
				url:"${sys_ctx}/login/checkADUser.action",
				type:"POST",
				cache:false,
				data:$('#loginForm').serialize(),
				dataType:'json',
				success: function (data) {
					if(data.success == "false"){
						 $("#message").html('提示信息：<span style="font-size:12px;color:#ff0000">' + data.msg + '</span>');
					} else {
						//跳转到首页
						loginForm.action = "${sys_ctx}/login.action";
	                	loginForm.submit();
					}
				},
				error: function(xhr){
					$("#message").html('提示信息：<span style="font-size:12px;color:#ff0000">系统异常,请联系管理员！</span>');
				}
			});
		}
  	}
  </script>
</html>
