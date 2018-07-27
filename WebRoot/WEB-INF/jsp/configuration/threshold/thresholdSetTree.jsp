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
<%@include file="/WEB-INF/jsp/common/common.html"%> 
<title>告警阈值设置</title>
	<!--列表公共样式  -->
	<link rel="stylesheet" href="${sys_ctx}/css/default-table.css" type="text/css"></link>
	<link rel="stylesheet" href="${sys_ctx}/css/default-page.css" type="text/css"></link>
<link rel="stylesheet" href="${sys_ctx}/css/from/operateOrg.css" type="text/css"></link>
	<style type="text/css">
		label{
			margin-right:10px;
			margin-top: 5px;
		}
		#icon-color {
			background: url('${sys_ctx}/img/icon_color.png');
			background-size: contain;
		}
		#colorpickerField1 {
			width: 100px;
		}
		/* .table {
			display: inline-block;
			width: 100%;
		}
		.table tbody {
			display:block;
			height: 300px;
			overflow: auto;
			width: 100%;
		}
		.table thead {
			display:block;
			width: 100%;
		}
		.table td , .table th{
		width: 100px;
		} */
		.table .tbody-container {
			display:inline-block;
			height: 100px;
			overflow: auto;
			width: 100%;
		}
	</style>
</head>
	<body>
	<div class="tabcenter">
		<div class="table-top">
			<!-- <form action="" name="thrshold" id="thrshold" method="post"> -->
				<!-- <div id="container"> -->
					<table class="table table-hover">
						<thead>
							<tr class="tr_center">
								<th>序号</th>
								<th>告警名称</th>
								<th>表达式</th>
								<th>告警等级</th>
								<th>排序</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							 <c:if test="${empty thredholdList}">
								<tr align="center">
									<td colspan="8" style="color: red;" bgcolor="#FFFFFF">没有查询到数据！</td>
								</tr>
							</c:if>
							 <c:forEach items="${thredholdList }" var="model" varStatus="status">
								 <tr class="forsearch">
									 <td class="forsearch">${status.index+1 }</td>
									 <td class="forsearch">${model.name }</td>
									 <td class="forsearch">${model.expression }</td>
									 <td class="forsearch">${model.alarmLevelName }</td>
									 <td class="forsearch">${model.orderNumber }</td>
									 <td class="forsearch">
									 	<span name="resetSpan" style="text-align:center;">
											<a class="site-button-method" onclick="editThreshold('${model.id }','${model.name }','${model.expression }','${model.alarmLevel }','${model.orderNumber }')">编辑</a>
											<a class="site-button-method" id="${model.id }" onclick="deleteThreshold('${model.id }')">删除</a>
										</span>
									 </td>
								 </tr>
							 </c:forEach>
							 <tr class="forsearch" >
								<td class="forsearch">&nbsp;<input type="hidden" value="${devicePropertyId }" name="devicePropertyId" id="devicePropertyId"/></td>
								<td  class="forsearch">
					                <input type="text" name="name" value="" id="thresholdName" placeholder="告警名称"/>
								</td>
								<td  class="forsearch">
					                <input type="text" name="expression" value="" id="expression" placeholder="表达式"/>
								</td>
								<td  class="forsearch">
									<select id="alarmLevel" name="alarmLevel">
							      	    <c:forEach items="${alarmList }" var="alarm"  varStatus="state">
								      		<option value="${alarm.levelId }">
								      			${alarm.name }
								      		</option>
							      		</c:forEach>
					                </select>
								</td>
								<td  class="forsearch">
					                <input type="text" name="orderNumber" value="" id="orderNumber" placeholder="排序" data-rule-required="true"/>
								</td>
								<td class="forsearch" style="vertical-align: middle;">
									<input type="hidden" name="thresholdId" id="thresholdId"/>
									<span name="save" style="text-align:center;">
						      			<a id=modify onclick="modify()" style="display: none;">保存修改</a>
						      			<a id="save" onclick="save()">新增</a>
						      		</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			<!-- </form> -->
		<!-- </div> -->
		</div>
	</body>
<script type="text/javascript">

	layui.use('layer', function() {
		layer = layui.layer;
	});

	 //修改告警阀值
     function modify(){
     	  var thresholdId = $('#thresholdId').val();
	      var devicePropertyId = $('#devicePropertyId').val();
	      var thresholdName = $('#thresholdName').val();
	      var expression = $('#expression').val();
	      var alarmLevel = $('#alarmLevel').val();
	      var orderNumber = $('#orderNumber').val();
	      if(!thresholdId || !devicePropertyId || !thresholdName || !expression || !alarmLevel || !orderNumber){
	      	  return layer.alert('请填写完整!');
	      }
	      layer.confirm('确定要保存该信息吗？', {
	  		  btn: ['确定', '取消'],title:'提示信息',icon: 3 
	  		}, function(index, layero){//确定按钮回执函数
	  			$.ajax({
	  				async:true,
	  				url : '${sys_ctx}/threshold/updateThreshold.action',
	  				type : "POST",
	  				cache : false,
	  				data: {'id':thresholdId, 'devicePropertyId':devicePropertyId, 'name':thresholdName, 'expression':expression, 'alarmLevel':alarmLevel, 'orderNumber':orderNumber},
	  				dataType : 'json',
	  				success : function (data) {
	  					if(data.success=="true"){
	  						location.reload();
	  					} else {
	  						layer.alert(data.msg, {title:'提示信息',icon: 5});
	  					}
	  				},
	  				error: function(xhr){
	  					
	  				}
	  			});
	  		
	  		}, function(index){//取消按钮回执函数
	  		});
     }
		
	 //保存告警阀值
     function save(){
	      var devicePropertyId = $('#devicePropertyId').val();
	      var thresholdName = $('#thresholdName').val();
	      var expression = $('#expression').val();
	      var alarmLevel = $('#alarmLevel').val();
	      var orderNumber = $('#orderNumber').val();
	      if(!devicePropertyId || !thresholdName || !expression || !alarmLevel || !orderNumber){
	      	  return layer.alert('请填写完整!');
	      }
	      layer.confirm('确定要保存该信息吗？', {
	  		  btn: ['确定', '取消'],title:'提示信息',icon: 3 
	  		}, function(index, layero){//确定按钮回执函数
	  			$.ajax({
	  				async:true,
	  				url : '${sys_ctx}/threshold/addThreshold.action',
	  				type : "POST",
	  				cache : false,
	  				data: {'devicePropertyId':devicePropertyId, 'name':thresholdName, 'expression':expression, 'alarmLevel':alarmLevel, 'orderNumber':orderNumber},
	  				dataType : 'json',
	  				success : function (data) {
	  					if(data.success=="true"){
	  						location.reload();
	  					} else {
	  						layer.alert(data.msg, {title:'提示信息',icon: 5});
	  					}
	  				},
	  				error: function(xhr){
	  					
	  				}
	  			});
	  		
	  		}, function(index){//取消按钮回执函数
	  		});
     }
     
     //编辑告警阀值编辑框自动获取焦点
     function editThreshold(id,name,expression,alarmLevelName,orderNumber){
     	$("#thresholdId").val(id);
     	$("#thresholdName").focus();
     	$("#thresholdName").val(name);
     	$("#expression").val(expression);
     	$("#alarmLevel option[value='"+alarmLevelName+"']").attr("selected", true);
     	$("#orderNumber").val(orderNumber);
     	$("#modify").show();
     	$("#save").hide();
     }
     
     //删除告警阀值
     function deleteThreshold(id){
        layer.confirm('确定要删除该信息吗？', {
  		  btn: ['确定', '取消'],title:'提示信息',icon: 3 
  		}, function(index, layero){//确定按钮回执函数
  			$.ajax({
  				async:true,
  				url : '${sys_ctx}/threshold/deleteThreshold.action',
  				type : "POST",
  				cache : false,
  				data: {'id':id},
  				dataType : 'json',
  				success : function (data) {
  					if(data.success=="true"){
  						location.reload();
  					} else {
  						layer.alert(data.msg, {title:'提示信息',icon: 5});
  					}
  				},
  				error: function(xhr){
  					
  				}
  			});
  		
  		}, function(index){//取消按钮回执函数
  		});
     }
     
	function cancel() {
  		laycls(0);
  	}
  	var page_sign = null;
  	var typeId = null;
	
  	var isPass = true;//是否通过姓名重复验证
  	//验证告警级别名称是否重复
	function validateThresholdName() {
		var name = $('#name').val();
		$.ajax({
			async:false,
			url:'${sys_ctx}/alarm/validateThresholdName.action',
			type:"POST",
			cache:false,
			data:{'name':name},
			dataType:'json',
			success: function (data) {
				if(data.success=="false"){
					isPass = false;
				} else {
					isPass = true;
				}
			},
			error: function(xhr){
				isPass = false;
			}
		});
	}
	
  	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		parent.layer.close(index); //再执行关闭 
    }
</script>
</html>
