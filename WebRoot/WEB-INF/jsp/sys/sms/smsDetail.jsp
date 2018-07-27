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
                    <td>
                    	<label>操作人:</label><input type="text" id="userName" name="userName" value="${vmsSms.userName}"  readonly />	
					</td>		  		                    
                </tr>
                <tr >
                    <td><label>手机号:</label><input type="text" id="phoneno" name="phoneno" value="${vmsSms.phoneno}"  readonly /></td>		                    
                </tr>
                <tr >
                	<td><label>状态:</label>
                		<input type="text" id="phoneno" name="phoneno" value="${vmsSms.status==1 ? '成功' : '失败'}"  readonly />
                	</td>		                    
                </tr>
                 <tr >
                    <td><label>次数:</label><input type="text" id="sendcount" name="sendcount" value="${vmsSms.sendcount }"  readonly /></td>
                </tr>                 	                         		                         
            </table>
            <div id="miaoshu">内容:</div>	
            <textarea id="content" name="content"  readonly style="height:150px;">${vmsSms.content}</textarea>                         	               
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
