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
<title>欢迎页</title>
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
        background-image:url(./img/welcome.png);
        filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='./img/welcome.png',sizingMethod='scale');
        -ms-filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='./img/welcome.png',sizingMethod='scale');
        position: relative;
	}
	
</style>
<body>

</body>
<script type="text/javascript">

</script>
</html>