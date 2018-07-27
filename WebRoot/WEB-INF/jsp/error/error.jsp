<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>错误页面提示</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="../css/err.css"/>
  </head>
  <body>
    <div class="err_center">
			<div class="err_box">
				<img src="../img/pic_404.png" style="margin-left:10%"/>
				<h2 style="position: absolute;top:100px;z-index:1000;left:130px;">页面遇到问题，请联系维护人员。</h2>
				<div class="btn_reasf">
					<p class="btn_flex"><a href="#"><img style="margin-right:5px;height:12px" src="../img/iocn_fresh.png" />刷新</a><a  href="javascript:history.go(-1)"><img style="margin-right:5px;height:12px" src="../img/icon_back.png" />返回上页</a></p>
					<img class="bor_shadow" src="../img/pic_shadow.png" />
				</div>
			</div>
		</div>
  <!-- 	<h2>页面遇到问题，请联系维护人员。</h2><br/>
  	<a href="javascript:history.go(-1)">返回上一页</a> -->
  </body>
</html>
