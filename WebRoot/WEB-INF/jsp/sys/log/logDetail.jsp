<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>操作日志详细信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<link rel="stylesheet" href="${sys_ctx}/css/from/operateEquipmentModel.css" type="text/css"></link>
  </head>
  <body>
  	<div id="dlg" align="center" style="padding:10px 20px;" >
        <form id="fm" method="post">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                <tr >
                    <td><label>操作人:</label><input type="text" id="userName" name="userName" value="${sysLog.userName}"  readonly /></td>		                    
                </tr>
                <tr >
                	<td><label>操作模块</label><input type="text" id="loginIp" name="loginIp" value="${sysLog.operatorModule }"  readonly /></td>		                    
                </tr>
                <tr >
                    <td>
                    	<label style="margin-right:7px">日志级别:</label>
                    	<input type="text" id="logType" name="logType" value='<c:if test="${sysLog.logLevel == 1 }">非常重要</c:if><c:if test="${sysLog.logLevel == 2 }">重要</c:if><c:if test="${sysLog.logLevel == 3 }">次要</c:if><c:if test="${sysLog.logLevel == 4 }">正常</c:if><c:if test="${sysLog.logLevel == 5 }">提示</c:if>'  readonly />
					</td>		  		                    
                </tr>
                 <tr >
                    <td><label>操作时间:</label><input type="text" id="operatorDate" name="operatorDate" value="<fmt:formatDate value="${sysLog.operatorDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"  readonly /></td>
                </tr>                 	                         		                         
            </table>
            <div id="miaoshu" style="width: 120px">日志描述:</div>	
            <textarea id="operatorDesc" name="operatorDesc"  readonly style="height:150px;">${sysLog.operatorDesc}</textarea>                         	               
        </form>
    </div>
  </body>
  <script type="text/javascript">
    var url;
	var layer;
	$(function(){
		
		layui.use('layer', function(){ 
			layer = layui.layer; 
  		});
	});
  
	//关闭对话框
    function laycls(){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); //再执行关闭 
    }
	
  	function cancel() {
  		laycls();
  	}
  </script>
</html>
