<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String meetingPath =request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>齐鲁石化公司视频监控系统</title>
<%@include file="/WEB-INF/jsp/common/common.html"%>
	<link rel="stylesheet" href="${sys_ctx}/plug/layui/css/ly-layui.css" />
	<link rel="stylesheet" href="${sys_ctx}/css/index.css" />
</head>
<style>
	body{
		width:100%;
        height:100%;
        background-size:cover;
        background-repeat: no-repeat;
        background-image:url(./css/bg.png);
        filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='./css/bg.png',sizingMethod='scale');
        -ms-filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='./css/bg.png',sizingMethod='scale');
        position: relative;
	}
	#links {
		height:463px;
		width:511px;
		background-image:url(./css/bg_1.png);
		position: absolute;
		top:50%;
		left:50%;
		margin-left: -255px;
		margin-top: -231px;
		padding:80px 100px 50px 100px;
		
	}
	#links ul{text-align:center;list-style-type:none;}
	#links ul li{
		height:60px;
		width:275px;
		line-height: 60px;
		margin:30px;
	}
	#links ul li a{
		color:#fff;
		font-size:20px;
		margin-left:30px;
	}
			
		
	.nav_one{
		background-image:url(./css/khd_nor.png)
	}
	.nav_one:hover{
		background-image:url(./css/khd_hover.png)
	}
	.nav_two{
		background-image:url(./css/gld_nor.png);
		background-repeat: no-repeat;
	}
	.nav_two:hover{
		background-image:url(./css/gld_hover.png)
	}
	.nav_three{
		background-image:url(./css/khdsj_nor.png);
		background-repeat: no-repeat;
	}
	.nav_three:hover{
		background-image:url(./css/khdsj_hover.png)
	}
	#logo{
		background: #fff;
	    font-size: 38px;
    	font-weight: 600;
    	}
	
</style>
<body>
<p id="logo">
	<span><img src="./img/login_logo.png"></span>齐鲁石化公司视频监控系统
</p>
<div id="links">
	<ul>
		<li title="登录客户端" class="nav_one " style="display: ${!empty loginclientShow ? loginclientShow : 'none' };">
			<a onclick="ssoLoginClientEnd('zstvmc://videomonitoringclient/login?username=${username }&password=${password }&from=ad');" href="javascript:void(0)">登录客户端</a>
		</li>
		<li title="登录管理端" class="nav_two" style="display: ${!empty loginbackShow ? loginbackShow : 'none' };">
			<a onclick="ssoLoginManagerEnd('${username}','${password }','${from }');" href="javascript:void(0)">登录管理端</a>
		</li>
		<li title="下载客户端" class="nav_three" style="display: ${!empty downloadclientShow ? downloadclientShow : 'none' };">
			<a onclick="downloadClient('${sys_ctx}/downloadClientSoftWare.action');" href="javascript:void(0)">下载客户端</a>
		</li>
		<!-- <li title="客户端升级(下载)" class="nav_three">
			<a onclick="window.open('http://localhost:8080/monitor_manager/ssoLoginEntry.action?username=liwanli.qlsh&&password=qlsh@1234&&from=ad')" href="javascript:void(0)">单点登录</a>
		</li> -->
	</ul>
</div>
</body>
<script type="text/javascript">
	//初始化layui
	layui.use('layer', function(){
		var layer = layui.layer;
	});
	//初始化js
/* 	$(document).ready(function() {
		
	}); */
	//单点登录管理端
	function ssoLoginManagerEnd(username,password,from) {
		var url = '${sys_ctx}/toSsoLoginManageEnd.action?username='+username+'&password='+password+'&from='+from;
		window.location.href = url;
	}
	//单点登录客户端
	function ssoLoginClientEnd(url) {
		window.location.href = url;
	}
	//客户端下载或者升级
	function downloadClient(url) {
		window.location.href = url;
	}
	
	//失效函数
	function openPostWindow(urlMix) {
		//第一种获取参数的方式
		if(urlMix){
			var urlMixArray = urlMix.split('?');
			var url = urlMixArray[0];
			var paramArray = urlMixArray[1].split('&&');
			var usernameEntry = paramArray[0];
			var usernameArray = usernameEntry.split('=');
			var username = usernameArray[1];
			var passwordEntry = paramArray[1];
			var passwordArray = passwordEntry.split('=');
			var password = passwordArray[1];
			var fromEntry = paramArray[2];
			var fromArray = fromEntry.split('=');
			var from = fromArray[1];
			
		    var newWin = window.open(),
		    formStr = '';
		     //设置样式为隐藏，打开新标签再跳转页面前，如果有可现实的表单选项，用户会看到表单内容数据
		    formStr = '<form style="visibility:hidden;" method="POST" action="' + url + '">' +
		          '<input type="hidden" name="username" value="' + username + '" />' +
		          '<input type="hidden" name="password" value="' + password + '" />' +
		          '<input type="hidden" name="from" value="' + from + '" />' +
		          '</form>';
		
		    newWin.document.body.innerHTML = formStr;
		    newWin.document.forms[0].submit();
		}
	    return newWin;
	}
</script>
</html>