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
<title>警告阈值设置</title>
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
		.tabtop{
			display:block;
			width:280px;
			float: left
		}
		select{
		    padding:3px 0px 3px 10px;
		}
	</style>

</head>
	<body>
	<div class="tabcenter">
		<div class="table-top">
				<div class="tabtop">
                   	<label><span style="color: red;">*</span>选择设备类型:</label>
					<select name="deviceProperty.id" id="deviceTypeId" class="default-one-input" onchange="chooseDeviceType(this.value)" style="height:28px !important; margin-right:20px;padding: 5">
						 <option value="">请选择</option>
						 <c:forEach items="${deviceTypeList }" var="deviceType">
							<option value="${deviceType.id }" <c:if test="${deviceType.id == deviceTypeId }">selected="selected"</c:if>>${deviceType.name }</option>
						 </c:forEach>
					</select>
					<!-- <p class="tab-p" style="padding-top:5px;">
						<button class="default-one-btn-top pull-right" onClick="doClear()" >重置</button>
						<button class="default-one-btn btn-green pull-right" onclick="doSearch()">查询</button>
					</p> -->
				</div>
			<form action="${sys_ctx}/threshold/addThresholdBefore.action" name="frm" id="frm" method="post">
				<input type="hidden" name="rows" id="rows" value='${myPage.number}' />
			  	<input type="hidden" name="page" id="page" value='${myPage.intPage}' />
				<input type="text" name="keywords" value="${deviceProperty.keywords }"/>
				<input type="hidden" name="devicetypeId" value="${deviceProperty.devicetypeId }"/>
				<input type="hidden" name="funtId" value="${funtId }"/>
				<button class="default-one-btn btn-green " onclick="doSearch()">查询</button>
				
			</form>
		</div>
	</div>
	<div class="tabcenter">
		<div class="tabcenter">
			<span class="span-list">查询列表</span>
			<span class="pull-right" style="line-height: 40px;">
				<span name="addItemSpan" style="text-align:center;display: none;">
			      	<a  id="chooseItem" class="default-one-btn" onclick="chooseItem();">添加监控项</a>
			    </span>
			    <span name="setGroupSpan" style="text-align:center;display: none;">
			      	<a id="configGroup" class="default-one-btn" onclick="configGroup()">设置分组</a>
			    </span>
			    <span name="delSpan" style="text-align:center;display: none;">
			      	<a id="delConfig" class="default-one-btn" onclick="delConfig()">删除</a>
			    </span>
			</span>
	   </div>
		<table class="table table-hover">
		  <thead>
		    <tr>
		      <th>
				<nobr>
					<input id="checkAll" title="全选" name="checkAll" type="checkbox" onclick="checkAll(this,'form','checknotall')" style="border: 0 !important;" />
				</nobr>
			  </th>
		      <th>序号</th>
		      <th>监控项名称</th>
		      <th>单位</th>
		      <th>告警分组</th>
		      <th>操作</th>
		    </tr>
		  </thead>
		  	<tbody id="itemBody">
			  	<c:if test="${empty devicePropertyList}">
					<tr align="">
						<td colspan="12" style="color: red;" bgcolor="#FFFFFF">没有查询到数据！</td>
					</tr>
				</c:if>
				<c:if test="${!empty devicePropertyList}">
					<c:forEach var="model" items="${devicePropertyList }" varStatus="status">
						<tr class="forsearch" id="itemTr">
							<td >
								<input type="checkbox" name="check" value="<c:out value='${model.id}'/>" style="border: 0 !important;" />
							</td>
							<td > ${status.index+1} </td>
							<td> ${model.deviceItemName}</td>
						    <td> ${model.deviceItemUnit}</td>
						    <td> ${model.groupName}</td>
					      	<td >
						      	<span name="thresholdSetSpan" style="text-align:center;display: none;">
						      		<a title="阀值配置" id="thresholdSet" onclick="thresholdSet('${model.id}','${model.deviceitemId}')">&nbsp;设置阀值&nbsp;</a>
						      	</span>
						      	<%-- <span name="thresholdEdit" style="text-align:center;">
						      		<a title="编辑" id="thresholdEdit" onclick="thresholdEdit('${model.id}')">|&nbsp;编辑</a>
						      	</span> --%>
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
    //控制按钮权限
  	var funtId = '${funtId}';
	pageFuntAutyBtn('${sys_ctx}/getFuntBtnList.action',funtId);//控制按钮权限
	layui.use('layer', function() {
		layer = layui.layer;
	});
	function doSearch() {
			setPage(); 
			frm.submit(); 	
	  }	
	function cancel() {
  		laycls(0);
  	}
  	var page_sign = null;
  	var typeId = null;
  	
  	//选择监控项  携带设备类型的id
  	function chooseItem(){
  		var deviceTypeId = $("#deviceTypeId").val();
  		if(!deviceTypeId){
  			layer.msg("请选择设备类型",{time: 2000, icon:6});
  			return false;
  		}
  		layer.open({
			type: 2,
			title:"选择监控项",
			area:['600px','450px'],
		    content: '${sys_ctx}/threshold/chooseItem.action?deviceTypeId='+deviceTypeId,
		    end: function(){
	        	if(page_sign==1){refresh(typeId,funtId);}//刷新页面
	        }
		});
	};
	
	//设置分组
	function configGroup(){
		var deviceTypeId = $("#deviceTypeId").val();
  		if(!deviceTypeId){
  			layer.msg("请选择设备类型",{time: 2000, icon:6});
  			return false;
  		}
  		//勾选监控项
  		var choosedId = $("#itemBody").find("tr").length;
  		if(choosedId == 0){
  			layer.msg("该设备类型无监控项",{time: 2000, icon:6});
  			return;
  		}
  		var idArr = [];
  		$("#itemBody").find("tr").each(function(){
  			var tdArr = $(this).children();
  			var itemId = tdArr.eq(0).find('input').val();
  			var flag = tdArr.eq(0).find('input').prop("checked");
  			if(flag){
	  			idArr.push(itemId);
  			}
  		});
  		if(idArr.length == 0){
  			layer.msg("请选中监控项",{time: 2000, icon:6});
  			return;
  		}
  		var ids = idArr.join(",");
  		layer.open({
			type: 2,
			title:"设置分组",
			area:['300px','350px'],
		    content: '${sys_ctx}/threshold/configGroup.action?deviceTypeId='+deviceTypeId+'&ids='+ids,
		    end: function(){
	        	window.location.reload();
	        }
		});
	}
	
	//删除设备类型对应的监控项
	function delConfig(){
		var dpIds = [];
		var deviceTypeId = $("#deviceTypeId").val();
  		if(!deviceTypeId){
  			layer.msg("请选择设备类型",{time: 2000, icon:6});
  			return false;
  		}
  		//获取列表下的复选框
  		var all = document.getElementsByName("check"); 
	    for(var i=0; i<all.length;  i++){
	    	var obj = all[i];
	        if (obj.checked) {
	        	dpIds.push(obj.value);
	        }
	    }
  		if(dpIds != '' && dpIds != null) {
			layer.confirm('确定要删除该信息吗？', {title:'提示信息',
			  		  btn: ['确定', '取消'],icon: 3
			  		}, function(index, layero){//确定按钮回执函数
			  			$.post('${sys_ctx}/deviceProperty/deleteBatch.action',{'dpIds':dpIds},
			  					function(data){
					  				if(data.success=="true"){
					  					layer.msg(data.msg,{time: 2000, icon:6});
					  					refresh(deviceTypeId,funtId);
				  					} else {
				  						layer.alert(data.msg, {title:'提示信息',icon: 5});
				  					}
			  					},'json');
			  		}, function(index){//取消按钮回执函数
			  	});
			} else {
				layer.msg('请先选择要删除的内容！',{time: 2000, icon:5});
			} 			
  		}
	
	//设置阀值
	function thresholdSet(dpid,deviceitemId){
		var deviceTypeId = $("#deviceTypeId").val();
  		if(!deviceTypeId){
  			layer.msg("未选择设备类型",{time: 2000, icon:6});
  			return false;
  		}
  		layer.open({
			type: 2,
			title:"设置阀值",
			area:['1000px','300px'],
		    content: '${sys_ctx}/threshold/thresholdSet.action?deviceTypeId='+deviceTypeId+'&deviceitemId='+deviceitemId+'&devicePropertyId='+dpid,
		    end: function(){
	        	if(page_sign==1){refresh();}//刷新页面
	        }
		});
	}
	
	//编辑阀值
	function thresholdEdit(dpid){
		var deviceTypeId = $("#deviceTypeId").val();
  		if(!deviceTypeId){
  			layer.msg("未选择设备类型",{time: 2000, icon:6});
  			return false;
  		}
  		layer.open({
			type: 2,
			title:"设置阀值",
			area:['700px','230px'],
		    content: '${sys_ctx}/threshold/thresholdEditBefore.action?devicePropertyId='+dpid,
		    end: function(){
	        	if(page_sign==1){refresh();}//刷新页面
	        }
		});
	}
	
  	//全选
	function checkAll(obj){
	    var all = document.getElementsByName("check");  
	    for(var i=0; i<all.length;  i++){  
	        all[i].checked  =  obj.checked;  
	    }  
	    if(document.getElementById("checknotall"))
	   	    document.getElementById("checknotall").checked=false;
	}
  	
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
	
	
	
	//保存告警级别
	function save() {
  		$('#fm').validate({
  			showErrors: function (errorMap, errorList) {
                $.each(errorList, function (i, v) {
                    //在此处用了layer的方法,显示效果更美观
                    layer.tips(v.message, v.element, { time: 2000,tips: [3, '#F69805'] });
                    return false;
                });  
            },
            /* 失去焦点时不验证 */
            onfocusout: false
  		});
  		//验证不通过
  		if(!$('#fm').valid()){
  			return;
  		}
  		
  		if(!isPass) {//验证组织机构名未通过
  			layer.msg('级别名称重复，请确认！',{time: 2000, icon:5});
  			return;
  		}
  		
  		layer.confirm('确定要保存该信息吗？', {
	  		  btn: ['确定', '取消'],icon: 3 
	  		}, function(index, layero){//确定按钮回执函数
	  			$.ajax({
	  				async:true,
	  				url : '${sys_ctx}/alarm/addAlarmLevel.action',
	  				type : "POST",
	  				cache : false,
	  				data : $('#fm').serialize(),
	  				dataType : 'json',
	  				success : function (data) {
	  					if(data.success=="true"){
	  						laycls(1);
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
  	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		parent.layer.close(index); //再执行关闭 
    }
    
    //刷新页面
    function refresh(typeId,funtId){
    	window.location.href = "${sys_ctx}/threshold/addThresholdBefore.action?deviceTypeId="+typeId+"&funtId="+funtId;
    }
    
    function chooseDeviceType(typeId){
    	if(typeId){
	    	window.location.href = "${sys_ctx}/threshold/addThresholdBefore.action?deviceTypeId="+typeId+"&funtId="+funtId;
    	}else{
    		window.location.href = "${sys_ctx}/threshold/addThresholdBefore.action?funtId="+funtId;
    	}
    }
</script>
</html>
