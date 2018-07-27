<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>告警阀值配置</title>
	<%@include file="/WEB-INF/jsp/common/common.html"%> 
	<!--列表公共样式  -->
	<link rel="stylesheet" href="${sys_ctx}/css/default-table.css" type="text/css"></link>
	<link rel="stylesheet" href="${sys_ctx}/css/default-page.css" type="text/css"></link>
	<style>
		input:focus::-webkit-input-placeholder{
            text-indent: -999em;
            z-index: -20;
	     }
	</style>
</head>
	<body>
		<div class="table-top">
			<form action="${sys_ctx}/threshold/queryThreshold.action" name="frm" id="frm" method="post">
				<input type="hidden" id="funtId" name="funtId" value="${funtId }" />
				<input type="hidden" name="rows" id="rows" value='${myPage.number}' />
				<input type="hidden" name="page" id="page" value='${myPage.intPage}' />
			</form>
		</div>
		<div class="tabcenter">
			<div class="tabcenter">
				<div>
					<span class="span-list">查询列表</span>
					<p class="pull-right">
						<c:if test="${fn:contains(buttonList,'addSpan')}">
							<span name="addSpan" style="text-align:center;">
								<button class="default-one-btn site-button-method font-color" id="add">新增</button>
							</span>
						</c:if>
					</p>
			   </div>
			<table class="table table-hover">
			  <thead>
			    <tr>
			      <th>序号</th>
			      <th>告警名称</th>
			      <th>设备类型</th>
			      <th>告警分组</th>
			      <th>监控项</th>
			      <th>表达式</th>
			      <th>单位</th>
			      <th>告警等级</th>
			      <th>级别编码</th>
			      <th>操作</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:if test="${empty list}">
					<tr align="center">
						<td colspan="4" style="color: red;" bgcolor="#FFFFFF">没有查询到数据！</td>
					</tr>
				</c:if>
				<c:if test="${!empty list}">
					<c:forEach var="model" items="${list }" varStatus="status">
						<tr class="forsearch">
							<td> ${status.index+1} </td>
							<td class="forsearch"> ${model.name}</td>
						    <td class="forsearch"> ${model.deviceType.name}</td>
						    <td class="forsearch"> ${model.group.name}</td>
						    <td class="forsearch"> ${model.deviceItem.name}</td>
						    <td class="forsearch"> ${model.expression}</td>
						    <td class="forsearch"> ${model.deviceItem.unit}</td>
						    <td class="forsearch"> ${model.level.name}</td>
						    <td class="forsearch"> ${model.level.value}</td>
					      	<td>
					      		<c:if test="${fn:contains(buttonList,'editSpan')}">
							      	<span name="editSpan" style="text-align:center;">
							      		<a  id="modify" onclick="modify('${model.id}')">&nbsp;编辑&nbsp;</a>
							      	</span>
							    </c:if>
							    <c:if test="${fn:contains(buttonList,'delSpan')}">
							      	<span name="delSpan" style="text-align:center;">
							      		<a id="del" onclick="del('${model.id}')">|&nbsp;删除</a>
							      	</span>
							    </c:if>
					      	</td>
						</tr>
					</c:forEach>
				</c:if>
			  </tbody>
			</table>
			<%@ include file="../../common/page.html"%>
		  </div>
	  </div>
	</body>
<script type="text/javascript">
	var funtId = '${funtId}';
	pageFuntAutyBtn('${sys_ctx}/getFuntBtnList.action',funtId);//控制按钮权限

	function doSearch() {
			setPage(); 
			frm.submit(); 	
	}

 		var page_sign = 0;//判定是否刷新列表
		layui.use('layer', function(){
	  		var layer = layui.layer;
	  		$("#add").click(function(){
	  			layer.open({
	  				type: 2,
	  				title:"新增告警配置",
	  				area:['700px','550px'],
				    content: '${sys_ctx}/threshold/addThresholdBefore.action'
				    ,end: function(){
					    if(page_sign==1){refresh();}//刷新页面
					}
				});
	  		});
		});
		
  		function modify(id){
	  		layer.open({
	  				type: 2,
	  				title:"编辑告警配置",
	  				area:['450px','400px'],
				    content: '${sys_ctx}/threshold/modifyThresholdBefore.action?id='+id
				    ,end: function(){
			        	if(page_sign==1){refresh();}//刷新页面
			        }
				});
		};
  		
  		function del(id){
			  layer.confirm('确定要删除该信息吗？', { title:'提示信息',
					  		  btn: ['确定', '取消'],icon: 3
					  		}, function(index, layero){
			    //按钮【按钮一】的回调
			    	$.ajax({
		  				async:false,
		  				url:'${sys_ctx}/threshold/deleteThreshold.action',
		  				type:"POST",
		  				cache:false,
		  				data:{'id':id},
		  				dataType:'json',
		  				success: function (data) {
		  					if(data.success=="true"){
		  					layer.alert(data.msg,{title:'提示信息',icon:1});
		  						refresh();
		  						
		  					} else {
		  						layer.alert(data.msg,{title:'提示信息',icon:5});
		  					}
		  				},
		  				error: function(xhr){
		  					
		  				}
  					});
			   }, function(index, layero){
			    //按钮【按钮二】的回调
			    
			    //return false 开启该代码可禁止点击该按钮关闭
			   }
			   
			);
  		};
  	
	   function refresh(){
	 	   window.location.reload(true);
	   }
</script>
</html>
