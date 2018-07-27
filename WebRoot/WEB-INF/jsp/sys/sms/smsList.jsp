<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>短信操作记录列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<!--列表公共样式  -->
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
		    vertical-align: middle;
		}
		.out{
			width:500;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;margin: 0 auto;
		}
	</style>
  </head>
  <body>
	 <div class="table-top" style="padding-top: 8px">
		<form action="${sys_ctx}/sms/getSmsList.action" name="frm" id="frm" method="post">
			<input type="hidden" id="funtId" name="funtId" value="${funtId }" />
			<input type="hidden" name="rows" id="rows" value='${myPage.number}' />
			<input type="hidden" name="page" id="page" value='${myPage.intPage}' />
			<!-- 日志个数 -->
			<input type="hidden" name="totalRows" id="totalRows" value='${totalRows}' />
			<label class="default-one-lable">时间</label>
			<div class="layui-inline">
				<input class="default-one-input" placeholder="开始时间" name ="queryStartTime" value='<fmt:formatDate value="${vmsSms.queryStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' onclick="layui.laydate({elem: this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" />
			</div>
		    <label class="default-one-lable">—</label>
			<div class="layui-inline">
				<input class="default-one-input" placeholder="结束时间" name ="queryEndTime" value='<fmt:formatDate value="${vmsSms.queryEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' onclick="layui.laydate({elem: this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" />
			</div>
			<label class="default-one-lable">状态</label>
			<select id="status" name="status" class="default-one-input" >
				<option value=""  ${empty vmsSms.status ? "selected" : "" }>请选择</option>
				<option value="1" ${vmsSms.status == 1 ? "selected" : "" } >成功</option>
				<option value="2" ${vmsSms.status == 2 ? "selected" : "" } >失败</option>
			</select>
			<label class="default-one-lable">内容</label>
			<input id="content" name="content" value="${vmsSms.content }" class="default-one-input"/>
			<label class="default-one-lable">操作人</label>
			<input id="userName" name="userName" value="${vmsSms.userName }" class="default-one-input"/>
			<button class="default-one-btn btn-green" onclick="doSearch()">查询</button>
			<button class="default-one-btn-top" onClick="doClear()">重置</button>
		</form>
			
	</div>
	<div class="tabcenter">
    	<div>
			<span class="span-list">查询列表</span>
			<span style="padding-right:5px">
			<a class="default-one-btn pull-right" onclick="exportSms()" >导出</a>
			</span>
	   </div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th style="width:5%">序号</th>
					<th style="width:7%">操作人</th>
					<th style="width:10%">手机号码</th>
					<th style="width:10%">状态</th>
					<th style="width:40%;">内容</th>
					<th style="width:5%">次数</th>
					<th style="width:15%">时间</th>
					<!-- <th>操作</th> -->
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
								<c:out value="${status.index+1 }"/>
							</td>
							<td>
								<c:out value="${e.userName}"/>
							</td>
							<td>
								<c:out value="${e.phoneno}"/>
							</td>
							<td>
								<c:choose>
									<c:when test="${e.status == 1 }">
										成功
									</c:when>
									<c:when test="${e.status == 2 }">
										失败
									</c:when>
								</c:choose>
							</td>
							<td>
								<div class="out"><c:out value="${e.content}"/></div>
							</td>
							<td style="text-align: center; !important;">
								<c:out value="${e.sendcount}"/>
							</td>
							<td>
								<fmt:formatDate value="${e.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<%-- <td>
								<a class="site-button-method" id="${e.id}" data-method="smsDetail">查看</a>
							</td> --%>
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
  	 
  	//导出日志
  	function exportSms() {
  		var logSize = $("#totalRows").val();
  		if (logSize > 60000) {
  			layer.msg('短信日志支持最多可导出60000条',{time: 2000, icon:6});
  			return;
  		}
  		layer.confirm('确定要导出信息吗？', { title:'提示信息',btn: ['确定', '取消'],icon: 3
	  		},function(index, layero){//确定按钮回执函数
	      		var vmsSms = $('#frm').serialize();
	      		window.location.href='${sys_ctx}/sms/exportSms.action?vmsSms'+vmsSms;
	      		layer.close(index);
	  		},function(index){//取消按钮回执函数
		});
 	}
	  layui.use('layer', function(){ 
			var $ = layui.jquery, layer = layui.layer; 
			  //触发事件
			  var active = {
				  //查看
				  smsDetail: function(){
			    	var that = this; 
					var id = that.id;//获取该用户主键id
				      parent.layer.open({
				        type: 2 
				        ,id:'addEqu'
				        ,title: '查看'
				        ,area: ['500px','340px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,offset: 'c'
				        ,content: '${sys_ctx}/sms/smsDetail.action?id='+id
				        ,end: function(){
				        
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
