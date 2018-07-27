<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>操作设备--查看</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<style>
		table{
			font-size:12px;
		}
		.bor-form {
		    border: 1px solid #ccc;
		    padding: 10px;
		    font-weight: 500;
		}
		textarea{
			resize: none;
		    padding: 5px;
		    border-radius: 3px;
		    float: left;
		    width: 489px;
    		height: 80px;
			} 
		label{
			 font-weight:500; 
			width: 120px;
   			text-align: right;
   			color:#000;
   			display: block;
    		float: left;
    		margin-bottom: 0;
    		margin-top: 5px;
		}
		input{
			border-radius: 3px;
		    padding-left: 10px;
		    height: 28px;
		    width:230px;
		    line-height: 28px;
		}
		select{
			line-height: 30px;
		    height: 28px;
		    border-radius: 3px;
		    width: 230px;
		    padding-left:5px;
		    padding:3px 0px 3px 10px;
		}
		td{
    		padding: 5px 0;
		}
		#chooseLoginResource2{
		    height: 20px;
    		width: 16px;
		}
		.btn-foot{
		    display: inline-block;
		    width: 100%;
		    text-align: center;
		    margin-top: 16px;
		    margin-bottom: 10px;
		}
		#save{
			background: #399efc;
		    border: 0;
		    border-radius: 3px;
		    line-height: 30px;
		    padding: 0 22px;
		    color: #fff;
		    margin: 0 20px;
		}
		#cancel{
			background: #fff;
		    border: 1px #399efc solid ;
		    border-radius: 3px;
		    line-height: 30px;
		    padding: 0 22px;
		    color: #399efc;
		    margin: 0 20px;
		}
		#close{
			background: #fff;
		    border: 1px #399efc solid ;
		    border-radius: 3px;
		    line-height: 30px;
		    padding: 0 22px;
		    color: #399efc;
		    margin: 0 20px;
		}
		.default-one-input {
		    width: 230px;
		    height: 28px;
		    font-size: 12px;
		    border-radius: 3px;
		    padding-left: 5px;
		    border: 1px solid #a2a7a6;
		    line-height: 28px;
		}
		legend {
		    padding: 0 5px;
		    width: inherit;
		    border-bottom: 0;
		    font-size: 14px;
		}
 	 </style>
  </head>
  
  <body>
  	<div id="dlg" style="padding:10px 20px;" >
        <form id="fm" method="post">
  			<input name="orgId" id="orgId" value="${vmsAsset.orgId}" type="hidden" />  
  			<fieldset class="bor-form"> 
  				<legend>基本信息</legend>                   	            
            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
            	<tr>
					<td><label><span style="color: red;">*</span>设备类型：</label>
                    	<select name="devicetypeId" id="devicetypeId" class="default-one-input" onchange="showLoginForm(this.value);"  data-rule-required="true">
								 <option value="">请选择</option>
								 <c:forEach items="${typeList }" var="deviceType">
									<option value="${deviceType.id }" <c:if test="${deviceType.id == vmsAsset.devicetypeId }">selected="selected"</c:if>>${deviceType.name }</option>
								 </c:forEach>
						</select>
						<input type="hidden" id="loginFlag" name="loginFlag"/>
                    </td>
					<td>
						<label>所属组织：</label><input type="text" id="orgName" value="${orgName }" readonly style="background-color: #EBEBEA;cursor:hand;" data-rule-required="true"/>
					</td>
			    </tr>
                <tr >
                    <td><label><span style="color: red;">*</span>厂商：</label>
                    	<select name="vendorId" id="vendorId" class="default-one-input" data-rule-required="true">
								 <option value="">请选择</option>
								 <c:forEach items="${vendorList }" var="vendor">
									<option value="${vendor.vendorId }" <c:if test="${vendor.vendorId == vmsAsset.vendorId }">selected="selected"</c:if>>${vendor.name }</option>
								 </c:forEach>
						</select>
                    </td>
                    <td><label><span style="color: red;">*</span>产品：</label>
                    	<select name="productId" id="productId" class="default-one-input" data-rule-required="true">
                    		 <option value="">请选择</option>
							 <c:forEach items="${productList }" var="product">
								<option value="${product.productId }" <c:if test="${product.productId == vmsAsset.productId }">selected="selected"</c:if>>${product.name }</option>
							 </c:forEach>
						</select>
                    </td>
                </tr>
                <tr >
                    <td><label><span style="color: red;">*</span>设备型号：</label>
                    	<select name="modelId" id="modelId" class="default-one-input"  data-rule-required="true">
                    		<option value="">请选择</option>
							 <c:forEach items="${modelList }" var="model">
								<option value="${model.modelId }" <c:if test="${model.modelId == vmsAsset.modelId }">selected="selected"</c:if>>${model.name }</option>
							 </c:forEach>
						</select>
                    </td>
                    <td><label><span style="color: red;">*</span>设备名称：</label>
                    	<input type="text" id="name" value="${vmsAsset.name }" name="name" title="请输入设备名称" onblur="validateAssetName();"  data-rule-required="true"/>
                    </td>		                    
                </tr>
                <tr >
                    <td><label>资产编号：</label>
                    	<input type="text" id="code" name="code" value="${vmsAsset.code}" title="请输入资产编号">
                    </td>
                    <td><label>资产状态：</label>
                    	<select name="status" id="status" class="default-one-input" data-rule-required="true">
									 <option value="1" <c:if test="${1 == vmsAsset.status }">selected="selected"</c:if> >运行</option>
									 <option value="2" <c:if test="${2 == vmsAsset.status }">selected="selected"</c:if> >损坏</option>
									 <option value="3" <c:if test="${3 == vmsAsset.status }">selected="selected"</c:if> >维修</option>
									 <option value="4" <c:if test="${4 == vmsAsset.status }">selected="selected"</c:if> >报废</option>
									 <option value="5" <c:if test="${5 == vmsAsset.status }">selected="selected"</c:if> >库存</option>
									 <option value="6" <c:if test="${6 == vmsAsset.status }">selected="selected"</c:if> >待安装</option>
									 <option value="7" <c:if test="${7 == vmsAsset.status }">selected="selected"</c:if> >已安装</option>
									 <option value="8" <c:if test="${8 == vmsAsset.status }">selected="selected"</c:if> >已调试</option>
							</select>
                    </td>
                </tr>
                <tr >
                    <td><label>保修时间：</label>
                    	
                    	<input type="text" id="guaranteetime" name="guaranteetime" value="<fmt:formatDate value="${vmsAsset.guaranteetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"  title="请输入保修时间">
                    </td>
                    <td><label>采购时间：</label>
                    	<input type="text" id="buytime" value="<fmt:formatDate value="${vmsAsset.buytime}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="buytime" title="请输入采购时间"/>
                    </td>
                </tr>
                <tr >
                    <td><label>工程商：</label>
                    	<input type="text" id="setupprovider" name="setupprovider" value="${vmsAsset.setupprovider }" title="请输入工程商">
                    </td>
                    <td><label>安装时间：</label>
                    	<input type="text" id="setuptime" value="<fmt:formatDate value="${vmsAsset.setuptime}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="setuptime" title="请输入安装时间"/>
                    </td>
                </tr>
                <tr >
                    <td><label>安装地点：</label>
                    	<input type="text" id="setupposition" name="setupposition" value="${vmsAsset.setupposition}" title="请输入安装地点"/>
                    </td>
                    <td><label>维护人：</label>
                    	<input type="text" id="maintainman" value="${vmsAsset.maintainman }" name="maintainman" title="请输入维护人"/>
                    </td>
                </tr>
                <tr >
                    <td><label>维修电话：</label>
                    	<input type="text" id="maintainphone" name="maintainphone" value="${vmsAsset.maintainphone}" title="请输入维修电话"/>
                    </td>
                    <td><label>排序：</label>
                    	<input type="text" id="ordernumber" value="${vmsAsset.ordernumber }" name="ordernumber" title="请输入排序数字"/>
                    </td>
                </tr>
                <tr >
                    <td><label>设备级别：</label>
                    	<input type="text" id="level" name="level" value="${vmsAsset.level}" title="请输入设备级别">
                    </td>
                     <%-- <td><label>关联图片：</label>
                    	<input type="text" id="pictureurl" name="pictureurl" value="${vmsAsset.pictureurl}" >
                    </td> --%>
                </tr>
                 </table>
	      </fieldset> 
	      <fieldset class="bor-form"> 
  				<legend>地图信息</legend>           
         		 <table style="width:100%">    
                <!-- camera地图坐标 -->
                <tr >
                    <td><label>经度：</label>
                    	<input type="text" id="longitude" name="longitude" value="${vmsCamera.longitude}" title="请输入经度">
                    </td>
                    <td><label>纬度：</label>
                    	<input type="text" id="latitude" value="${vmsCamera.latitude }" name="latitude" title="请输入纬度"/>
                    </td>
                </tr>
                <tr >
                    <td><label>缩放级别：</label>
                    	<input type="text" id="zoom" name="zoom" value="${vmsCamera.zoom}" >
                    </td>
                </tr>
                    </table>
	       </fieldset>
           <table> 
                <!-- 登录信息 -->
                <tr class="controlLogin">
                    <td><label>通过其他资源登录：</label><!-- 未选中的checkbox和禁用的控件不是有效控件，不会被POST -->
                    	<!-- 生成这样的表单，当checkbox未选中的时候，提交的是hidden表单。值0就被提交到服务器了。当checkbox都选中的时候，hidden和checkbox表单都被提交了，但是因为它们的name是一样的，所以hidden的值被 -->
                    	<input type="checkbox" style="border: 0 !important;" id="chooseLoginResource2" disabled="disabled" checked="checked" value="1" name="loginSource" onclick="chooseLoginResource();"/>
                    </td>
                    <td id="currentLoginList"><label>选择其他资源：</label>
                    	<select name="deviceloginCameraId" id="deviceloginCameraId" class="default-one-input" >
								 <c:forEach items="${deviceLoginList }" var="deviceLogin">
									<option value="${deviceLogin.deviceloginId }" <c:if test="${deviceLogin.deviceloginId  == vmsCamera.deviceloginCameraId }">selected="selected"</c:if> >${deviceLogin.deviceNameDisplay }</option>
								 </c:forEach>
						</select>
                    </td>
                </tr>
             </table>
             <fieldset class="bor-form loginChunk" style="display: none;"> 
  				<legend>登录信息</legend>    
	            <table>
	                <tr>
	                    <td><label>IP地址：</label>
	                    	<input type="text" id="ip" name="ip" value="${vmsDeviceLogin.ip}" title="请输入IP地址">
	                    </td>
	                    <td  style="padding-left: 38px;"><label>端口：</label>
	                    	<input type="text" id="port" value="${vmsDeviceLogin.port }" name="port" title="请输入端口"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td><label>用户名：</label>
	                    	<input type="text" id="loginuser" name="loginuser" value="${vmsDeviceLogin.loginuser }" title="请输入用户名">
	                    </td>
	                    <td  style="padding-left: 38px;"><label>密码：</label>
	                    	<input type="text" id="loginpwd" value="${vmsDeviceLogin.loginpwd }" name="loginpwd" title="请输入密码"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td><label>连接方式：</label>
	                    	<select name="connecttype" id="connecttype" class="default-one-input" >
	                    			 <option value="">请选择</option>
									 <option value="1" <c:if test="${vmsDeviceLogin.connectvalue  == 1}">selected="selected"</c:if>>SDK</option>
									 <option value="2" <c:if test="${vmsDeviceLogin.connectvalue  == 2}">selected="selected"</c:if>>Onvif</option>
									 <option value="3" <c:if test="${vmsDeviceLogin.connectvalue  == 3}">selected="selected"</c:if>>GB28181</option>
									 <option value="4" <c:if test="${vmsDeviceLogin.connectvalue  == 4}">selected="selected"</c:if>>OCX</option>
							</select>
	                    </td>
	                    <td style="padding-left: 38px;"><label>连接值：</label>
	                    	<input type="text" id="connectvalue" value="${vmsDeviceLogin.connectvalue }" name="connectvalue" title="请输入连接值"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td><label>通道数量：</label>
	                    	<input type="text" id="channelcount" name="channelcount" value="${vmsDeviceLogin.channelcount}" title="请输入通道数量" />
	                    </td>
	                </tr>                     		                         
            </table>
          </fieldset>
        </form>
    </div>
	 <div class="btn-foot">
	 	<c:if test="${funtType != 'view' }">
		 	<button id="save" onclick="save()">保存</button>
	        <button id="cancel" onclick="cancel()">取消</button>
	 	</c:if>
        <c:if test="${funtType == 'view' }">
        	<button id="close" onclick="cancel()">关闭</button>
        </c:if>
    </div>
  </body>
  <script type="text/javascript">
  	  //增加验证手机号、用户名
  	  $.validator.addMethod("isMobilephone",function(value,element){
	  	var mobilephoneReg = /^((\+86)|(86))?(1[3|4|5|8])\d{9}$/;
	  	return mobilephoneReg.test(value);
	  },"请输入正确的手机格式");
	  $.validator.addMethod("isUserid",function(value,element){
		  	var mobilephoneReg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$/;
		  	return mobilephoneReg.test(value);
	  },"只能输入数字、字母、下划线不能以下划线开头和结尾！");
  	  
  	var url;
	var funtType = '${funtType}';
	$(function(){
		//新增时赋值
		if(funtType == "add"){
		    url = '${sys_ctx}/user/addUser.action';
		} else if(funtType == "edit"){
			url = '${sys_ctx}/user/modifyUser.action';
		} else if(funtType == "view") {
			$("#orgName").css("background-color","");
			setFormDisable('fm');
		} else if(funtType == "copy") {
			url = '${sys_ctx}/user/addUser.action';
		}
	});
	
	layui.use('layer', function(){ 
		var layer = layui.layer; 
	});
	
	//选择设备类型显示或者隐藏登录表单  loginflag为1时显示，0时隐藏
	function showLoginForm(typeId){
		if(typeId){
			for(var i=0; i<typeArray.length; i++){
				if(typeArray[i].id == typeId){
					if(typeArray[i].loginflag == 1){//需要登录信息
						$(".loginChunk").show();
						$("#loginFlag").val(1);
				console.log(1);
					}else if(typeArray[i].loginflag == 0){//不需要登录信息
						$(".loginChunk").hide();
						$("#loginFlag").val(0);
						$(".controlLogin").hide();
						console.log(0);
					}
				}
			}
		}else{
			$(".loginChunk").hide();
		}
	}	
		//验证名称重复
		var typePass = true;
		function validateTypeName() {
			var name = $('#typeName').val();
			if(name){
				$.ajax({
					async:false,
					url:'${sys_ctx}/deviceType/validateTypeName.action',
					type:"POST",
					cache:false,
					data:{'name':name},
					dataType:'json',
					success: function (data) {
						if(data.success=="false"){
							typePass = false;
						} else {
							typePass = true;
						}
					},
					error: function(xhr){
						typePass = false;
					}
				});
			}else{
				typePass = false;
			}
		}
	 
		//验证厂商名称重复
		var vendorPass = true;
		function validateVendorName() {
			var name = $('#vendorName').val();
			if(name){
				$.ajax({
					async:false,
					url:'${sys_ctx}/deviceType/validateVendorName.action',
					type:"POST",
					cache:false,
					data:{'name':name},
					dataType:'json',
					success: function (data) {
						if(data.success=="false"){
							vendorPass = false;
						} else {
							vendorPass = true;
						}
					},
					error: function(xhr){
						vendorPass = false;
					}
				});
			}else{
				vendorPass = true;
			}
		}
	   
		//验证产品名称重复
		var productPass = true;
		function validateProductName() {
			var name = $('#productName').val();
			if(name){
				$.ajax({
					async:false,
					url:'${sys_ctx}/product/validateProductName.action',
					type:"POST",
					cache:false,
					data:{'name':name},
					dataType:'json',
					success: function (data) {
						if(data.success=="false"){
							productPass = false;
						} else {
							productPass = true;
						}
					},
					error: function(xhr){
						productPass = false;
					}
				});
			}else{
				productPass = false;
			}
		}
	   
		//验证设备型号名称重复
		var modelPass = true;
		function validateModelName() {
			var modelName = $('#modelName').val();
			if(modelName){
				$.ajax({
					async:false,
					url:'${sys_ctx}/vmsModel/validateModelName.action',
					type:"POST",
					cache:false,
					data:{'name':modelName},
					dataType:'json',
					success: function (data) {
						if(data.success=="false"){
							modelPass = false;
						} else {
							modelPass = true;
						}
					},
					error: function(xhr){
						modelPass = false;
					}
				});
			}else{
				modelPass = false;
			}
		}
	
    //关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		parent.layer.close(index); //再执行关闭 
    }
	
	var assetNamePass = true;//是否通过用户名重复验证
	//验证用户名是否重复
	function validateAssetName() {
		var name = $('#name').val();
		if(funtType == "edit") {//若是在修改的情况下，防止用户触发该事件重新把值改为打开时的值，但仍进行判断的情况
			if(name == '${vmsAsset.name}') {
				assetNamePass = true;
				return;
			}
		}
		
		$.ajax({
			async:false,
			url:'${sys_ctx}/asset/validateAssetName.action',
			type:"POST",
			cache:false,
			data:{'name':name},
			dataType:'json',
			success: function (data) {
				if(data.success=="false"){
					assetNamePass = false;
				} else {
					assetNamePass = true;
				}
			},
			error: function(xhr){
				isPass = false;
			}
		});
	}
	
	//动态展示登录源和其他登录源
	function chooseLoginResource(){
		var check = $("#chooseLoginResource2").prop("checked");
		if(check){
			$(".loginChunk").css("display","none");
			$("#currentLoginList").css("display","block");
		}else{
			$(".loginChunk").css("display","block");
			$("#currentLoginList").css("display","none");
		}
	}
	
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
  		if(!assetNamePass) {//验证用户名未通过
			layer.msg('设备名称不能重复，请确认！',{time: 2000, icon:5});
  			return;
  		}
  		var url = '${sys_ctx}/asset/addAsset.action';
  		layer.confirm('确定要保存该信息吗？', {
	  		  btn: ['确定', '取消'],title:'提示信息',icon: 3 
	  		}, function(index, layero){//确定按钮回执函数
	  			$.ajax({
	  				async:true,
	  				url:url,
	  				type:"POST",
	  				cache:false,
	  				data:$('#fm').serialize(),
	  				dataType:'json',
	  				success: function (data) {
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
  	
  	function cancel() {
  		laycls(0);
  	}
  </script>
</html>
