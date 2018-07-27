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
	<%-- <link rel="stylesheet" href="${sys_ctx}/css/from/operateOrg.css" type="text/css"></link> --%>
  </head>
  <body>
  	<div id="dlg" align="center" style="padding:10px 20px;" >
        <form id="fm" method="post">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                <tr >
                    <td>
                    	<label style="margin-right:7px">日志类型:</label>
                    	<c:choose>
							<c:when test="${sysJournalOperation.logType == 0 }">
								<input type="text" id="logType" name="logType" value="登录日志"  readonly />
							</c:when>
							<c:when test="${sysJournalOperation.logType == 1 }">
								<input type="text" id="logType" name="logType" value="操作日志"  readonly />
							</c:when>
							<c:when test="${sysJournalOperation.logType == 2 }">
								<input type="text" id="logType" name="logType" value="设备控制日志"  readonly />
							</c:when>
							<c:when test="${sysJournalOperation.logType == 3 }">
								<input type="text" id="logType" name="logType" value="会议控制日志"  readonly />
							</c:when>
						</c:choose>
					</td>		  		                    
                </tr>
                <tr >
                    <td><label>操作人:</label><input type="text" id="userName" name="userName" value="${sysJournalOperation.userName}"  readonly /></td>		                    
                </tr>
                <tr >
                	<td><label>登录IP:</label><input type="text" id="loginIp" name="loginIp" value="${sysJournalOperation.loginIp}"  readonly /></td>		                    
                </tr>
                 <tr >
                    <td><label>操作时间:</label><input type="text" id="operatorDate" name="operatorDate" value="<fmt:formatDate value="${sysJournalOperation.operatorDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"  readonly /></td>
                </tr>                 	                         		                         
            </table>
            <div id="miaoshu">日志描述:</div>	
            <textarea id="operatorDesc" name="operatorDesc"  readonly style="height:150px;">${sysJournalOperation.operatorDesc}</textarea>                         	               
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
