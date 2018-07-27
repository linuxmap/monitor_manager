<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>日志操作记录列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%> 
	<!--列表公共样式  -->
	<%-- <link rel="stylesheet" href="${sys_ctx}/css/default-table.css" type="text/css"></link> --%>
	<link rel="stylesheet" href="${sys_ctx}/css/default-page.css" type="text/css"></link>
	<style type="text/css">
		select{
			width:140px;
			height:24px;
			border-radius: 3px;
			
		} -->
		.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
		    padding: 8px 3px!important;
		    line-height: 1.42857143;
		    vertical-align: top;
		    border-top: 1px solid #ddd;
		    font-size: 12px;
		}
	</style>
  </head>
  <body>
	 <div class="table-top" style="padding-top: 8px">
		<form action="${sys_ctx}/sysJournalOperation/querySysJournalOperation.action" name="frm" id="frm" method="post">
			<input type="hidden" name="rows" id="rows" value='${myPage.number}' />
			<input type="hidden" name="page" id="page" value='${myPage.intPage}' />
			<label class="default-one-lable">日期</label>
			<div class="layui-inline">
				<input class="default-one-input" placeholder="开始时间" name ="startTime" value="${sysJournalOperation.startTime}" onclick="layui.laydate({elem: this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" />
			</div>
		    <label class="default-one-lable">—</label>
			<div class="layui-inline">
				<input class="default-one-input" placeholder="结束时间" name ="endTime" value="${sysJournalOperation.endTime}"onclick="layui.laydate({elem: this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" />
			</div>
			<label class="default-one-lable">日志类型</label>
			<select id="logType" name="logType" class="default-one-input" >
				<option value=""  ${sysJournalOperation.logType == null ? "selected" : "" }>请选择</option>
				<option value="0" ${sysJournalOperation.logType == 0 ? "selected" : "" } >登录日志</option>
				<option value="1" ${sysJournalOperation.logType == 1 ? "selected" : "" } >操作日志</option>
				<option value="2" ${sysJournalOperation.logType == 2 ? "selected" : "" } >设备控制</option>
				<option value="3" ${sysJournalOperation.logType == 3 ? "selected" : "" } >会议控制</option>
				<option value="4" ${sysJournalOperation.logType == 4 ? "selected" : "" } >其他</option>
			</select>
			<input type="text" class="default-one-input" id="operatorUserOrIp" name="operatorUserOrIp" value="${sysJournalOperation.operatorUserOrIp}"placeholder="操作人或ip地址"/>
			<button class="default-one-btn btn-green" onclick="doSearch()">查询</button>
			<button class="default-one-btn-top" onClick="doClear()">重置</button>
		</form>
	</div>
	<div class="tabcenter">
    	<div>
			<span class="span-list">查询列表</span>
	   </div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>日志类型</th>
					<th>操作人</th>
					<th>登录IP</th>
					<th>操作时间</th>
					<th>日志描述</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty list}">
					<tr align="center">
						<td colspan="8" style="color: red;" bgcolor="#FFFFFF">没有查询到数据！</td>
					</tr>
				</c:if>
				<c:if test="${!empty list}">
					<c:forEach var="e" items="${list }" varStatus="status">
						<tr>
							<td>
								<c:choose>
									<c:when test="${e.logType == 0 }">
										登录日志
									</c:when>
									<c:when test="${e.logType == 1 }">
										操作日志
									</c:when>
									<c:when test="${e.logType == 2 }">
										设备控制日志
									</c:when>
									<c:when test="${e.logType == 3 }">
										会议控制日志
									</c:when>
								</c:choose>
							</td>
							<td>
								<c:out value="${e.userName}"/>
							</td>
							<td>
								<c:out value="${e.loginIp}"/>
							</td>
							<td>
								<fmt:formatDate value="${e.operatorDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<c:out value="${e.operatorDesc}"/>
							</td>
							<td>
								<a class="site-button-method" id="${e.id}" data-method="checkScreenWall">查看</a>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<%@ include file="../../common/page.html"%>
	</div>
  </body>
  <script type="text/javascript">
  	  var page_sign = 0;//判定是否刷新列表
  		
	  function doSearch() {
			setPage(); 
			frm.submit(); 	
	  }
  	  function doClear() {
			clearForm("frm");
			doSearch();
  	  }
  	  
	  layui.use('layer', function(){ 
			var $ = layui.jquery, layer = layui.layer; 
			  //触发事件
			  var active = {
				  //查看
				  checkScreenWall: function(){
			    	var that = this; 
					var id = that.id;//获取该用户主键id
				      parent.layer.open({
				        type: 2 
				        ,id:'addEqu'
				        ,title: '查看'
				        ,area: ['500px','340px']
				        ,shade: 0.2
				        ,maxmin: true
				        ,offset: 'c'
				        ,content: '${sys_ctx}/sysJournalOperation/querySysJournalOperationDetails.action?id='+id
				        ,end: function(){
				        	refresh();
				        }
				      });
			    }
			 };
			
			  $('.site-button-method').on('click', function(){
				    var othis = $(this), method = othis.data('method');
				    active[method] ? active[method].call(this, othis) : '';
			  });
		});
	  
	  function refresh() {
		  doSearch();
	  }

	    layui.use('laydate', function(){
	      var laydate = layui.laydate;
	      
	    });
  </script>
</html>
