<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>错误提示</title>
<link rel="stylesheet" href="./css/err.css"/>
<%@include file="/WEB-INF/jsp/common/common.html"%> 
<style>
	.btn_reasf {
    height: 100px;
    width: 800px;
    border: 1px solid #ececec;
    position: absolute;
    bottom: -28px;
}
</style>
</head>
<body>
	  <div class="err_center">
			<div class="err_box">
				<img src="./img/pic_500.png" style="margin-left:10%"/>
				<h2 style="position: absolute;top: 140px;z-index:1000;left:130px;">
				页面遇到问题，请联系维护人员。
				<c:if test="${!empty message }">
					错误信息为:${message }
				</c:if>
				</h2>
				<div class="btn_reasf">
					<p class="btn_flex"><a href="#"><img style="margin-right:5px;height:12px" src="./img/iocn_fresh.png" />刷新</a><a  href="javascript:history.go(-1)"><img style="margin-right:5px;height:12px" src="./img/icon_back.png" />返回上页</a></p>
					<img class="bor_shadow" src="./img/pic_shadow.png" />
				</div>
			</div>
		</div>
</body>
</html>	