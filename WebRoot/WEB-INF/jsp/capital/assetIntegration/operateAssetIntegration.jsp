<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>操作设备</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<style>
		table{
			font-size:12px;
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
			margin-right:10px !important;
			width: 100px;
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
		    width:160px;
		}
		td{
    		padding: 5px 0;
		}
		select{
			line-height: 30px;
		    height: 28px;
		    border-radius: 3px;
		    width: 160px;
		    padding-left:5px
		}
		.btn-foot{
		    display: inline-block;
		    width: 100%;
		    text-align: center;
		    margin-top: 16px;
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
 	 </style>
  </head>
  
  <body>
  	<div id="dlg" align="center" style="padding:10px 20px;" >
        <form id="fm" method="post">
  			<input name="orgId" id="orgId" value="${vmsAsset.orgId}" type="hidden" />                     	            
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
						<button type="button" class="default-one-btn" id="addType">添加设备类型</button>
                    </td>
					<td>
						<label>所属组织：</label><input type="text" id="orgName" value="${orgName }" readonly style="background-color: #EBEBEA;cursor:hand;" data-rule-required="true"/>
					</td>
			    </tr>
                <tr >
                    <td><label><span style="color: red;">*</span>厂商：</label>
                    	<select name="vendorId" id="vendorId" class="default-one-input" onchange="getProdctOptions(this.value);"  data-rule-required="true">
								 <option value="">请选择</option>
								 <c:forEach items="${vendorList }" var="vendor">
									<option value="${vendor.vendorId }" <c:if test="${vendor.vendorId == vmsAsset.vendorId }">selected="selected"</c:if>>${vendor.name }</option>
								 </c:forEach>
						</select>
						<button type="button" class="default-one-btn" id="addVendor">添加厂商</button>
                    </td>
                    <td><label><span style="color: red;">*</span>产品：</label>
                    	<select name="productId" id="productId" class="default-one-input" onchange="getModelOptions(this.value);"  data-rule-required="true">
						</select>
						<button type="button" class="default-one-btn" id="addProduct">添加产品</button>
                    </td>
                </tr>
                <tr >
                    <td><label><span style="color: red;">*</span>设备型号：</label>
                    	<select name="modelId" id="modelId" class="default-one-input" onchange=""  data-rule-required="true">
						</select>
						<button type="button" class="default-one-btn" id="addModel">添加型号</button>
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
                    	<input type="text" id="status" value="${vmsAsset.status }" name="status" title="请输入真实姓名"/>
                    </td>
                </tr>
                <tr >
                    <td><label>保修时间：</label>
                    	<input type="text" id="guaranteetime" name="guaranteetime" value="${vmsAsset.guaranteetime}" title="请输入保修时间">
                    </td>
                    <td><label>采购时间：</label>
                    	<input type="text" id="buytime" value="${vmsAsset.buytime }" name="buytime" title="请输入采购时间"/>
                    </td>
                </tr>
                <tr >
                    <td><label>工程商：</label>
                    	<input type="text" id="setupprovider" name="setupprovider" value="${vmsAsset.setupprovider }" title="请输入工程商">
                    </td>
                    <td><label>安装时间：</label>
                    	<input type="text" id="setuptime" value="${vmsAsset.setuptime }" name="setuptime" title="请输入安装时间"/>
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
                </tr>
                <tr >
                    <td><label>关联图片：</label>
                    	<input type="text" id="pictureurl" name="pictureurl" value="${vmsAsset.pictureurl}" >
                    </td>
                </tr>
                
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
                
                <!-- 登录信息 -->
                <tr class="controlLogin">
                    <td><label>通过其他资源登录：</label><!-- 未选中的checkbox和禁用的控件不是有效控件，不会被POST -->
                    	<!-- 生成这样的表单，当checkbox未选中的时候，提交的是hidden表单。值0就被提交到服务器了。当checkbox都选中的时候，hidden和checkbox表单都被提交了，但是因为它们的name是一样的，所以hidden的值被 -->
                    	<input type="checkbox" id="chooseLoginResource2" checked="checked" value="1" name="loginSource" onclick="chooseLoginResource();"/>
                    </td>
                    <td id="currentLoginList"><label>选择其他资源：</label>
                    	<select name="deviceloginCameraId" id="deviceloginCameraId" class="default-one-input" >
								 <c:forEach items="${deviceLoginList }" var="deviceLogin">
									<option value="${deviceLogin.deviceloginId }">${deviceLogin.deviceNameDisplay }</option>
								 </c:forEach>
						</select>
                    </td>
                </tr>
                <tr class="loginChunk" style="display: none;">
                    <td><label>IP地址：</label>
                    	<input type="text" id="ip" name="ip" value="${vmsDeviceLogin.ip}" title="请输入IP地址">
                    </td>
                    <td><label>端口：</label>
                    	<input type="text" id="port" value="${vmsDeviceLogin.port }" name="port" title="请输入端口"/>
                    </td>
                </tr>
                <tr class="loginChunk" style="display: none;">
                    <td><label>用户名：</label>
                    	<input type="text" id="loginuser" name="loginuser" value="${vmsDeviceLogin.loginuser }" title="请输入用户名">
                    </td>
                    <td><label>密码：</label>
                    	<input type="text" id="loginpwd" value="${vmsDeviceLogin.loginpwd }" name="loginpwd" title="请输入密码"/>
                    </td>
                </tr>
                <tr class="loginChunk" style="display: none;">
                    <td><label>连接方式：</label>
                    	<select name="connecttype" id="connecttype" class="default-one-input" >
                    			 <option value="">请选择</option>
								 <option value="1" <c:if test="${vmsDeviceLogin.connectvalue  == 1}">selected="selected"</c:if>>SDK</option>
								 <option value="2" <c:if test="${vmsDeviceLogin.connectvalue  == 2}">selected="selected"</c:if>>Onvif</option>
								 <option value="3" <c:if test="${vmsDeviceLogin.connectvalue  == 3}">selected="selected"</c:if>>GB28181</option>
						</select>
                    </td>
                    <td><label>连接值：</label>
                    	<input type="text" id="connectvalue" value="${vmsDeviceLogin.connectvalue }" name="connectvalue" title="请输入连接值"/>
                    </td>
                </tr>
                <tr class="loginChunk" style="display: none;">
                    <td><label>通道数量：</label>
                    	<input type="text" id="channelcount" name="channelcount" value="${vmsDeviceLogin.channelcount}" title="请输入通道数量" />
                    </td>
                </tr>                     		                         
            </table>
      
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
	
	//集中处理下拉框
	var productJ = '${productJ}';//产品
	var procuctArray = eval(productJ);
	var modelJ = '${modelJ}';//型号
	var modelArray = eval(modelJ);
	var typeJ = '${typeJ}';//设备类型
	var typeArray = eval(typeJ);
	
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
	//产品下拉框控制
	function getProdctOptions(vendorId){
		if(vendorId){
			//清空productId的select
			$("#productId").empty();
			$("#modelId").empty();
			$("#productId").append("<option value=''>请选择</option>");
			for(var i=0; i<procuctArray.length; i++){
				if(procuctArray[i].vendorId == vendorId){
					var sel = "<option value='"+procuctArray[i].productId+"'>"+procuctArray[i].name+"</option>";
					$("#productId").append(sel);
				}
			}
		}else{
			$("#productId").empty();
			$("#modelId").empty();
		}
	}
	
	//设备型号下拉框控制
	function getModelOptions(productId){
		if(productId){
			//清空productId的select
			$("#modelId").empty();
			$("#modelId").append("<option value=''>请选择</option>");
			for(var i=0; i<modelArray.length; i++){
				if(modelArray[i].productId == productId){
					var sel = "<option value='"+modelArray[i].modelId+"'>"+modelArray[i].name+"</option>";
					$("#modelId").append(sel);
				}
			}
		}else{
			$("#modelId").empty();
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
	   //添加设备类型
	   $("#addType").on("click",function(){
	   		layer.open({
				title: ['添加设备类型', 'background-color:#d0e3f0; color:#888889;'],
				anim: 'scale',
				content: '类型名称：<input type="text" style="width:150px;height:30px;padding-left:10px;border-radius:3px;" id="typeName"  onblur="validateTypeName()"></br>是否需要登录信息:<input type="checkbox" style="width:150px;height:30px;padding-left:10px;border-radius:3px;" id="loginflag" >',
				btn: ['确认','取消'],
				yes: function(index) {
					var typeName = $("#typeName").val();
					//判断名字是否为空
					if(!typeName){
						layer.msg('名称不能为空！',{time: 2000, icon:5});
						return;
					}
					if(!typePass) {
			  			layer.msg('类型名称重复，请确认！',{time: 2000, icon:5});
			  			return;
			  		}
			  		//是否需要登录信息
			  		var loginCheck = $("#loginflag").prop("checked");
			  		var loginflag = 0;
			  		if(loginCheck){
			  			loginflag = 1;
			  		}
					$.ajax({
						type: "POST",
						url: '<%=basePath%>deviceType/addDeviceType.action',
				    	data: {'name':typeName, 'loginflag':loginflag },
						dataType:'json',
						cache: false,
						success: function(data){
							if(data.success){
								layer.open({
											title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
											anim: 'scale',
											content: '保存成功！',
											btn: ['确认'],
											yes: function(index) {
												layer.close(index);
											},
										});
								//在网页上展示出来
								var html = "<option value='"+data.msg+"'>"+typeName+"</option>";
								$("#devicetypeId").append(html);
							}else{
								var err = data.message;
								layer.open({
									title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
									anim: 'scale',
									content: '保存失败，错误信息为：' + err + '！',
									btn: ['确认'],
									yes: function(index) {
										layer.close(index);
									},
								});
							 }
						},
						error: function(data){
							layer.open({
								title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
								anim: 'scale',
								content: '请检查网络！',
								btn: ['确认'],
								yes: function(index) {
									layer.close(index);
								},
							});
						}
					});
					layer.close(index);
				},
				no: function(index){
					document.getElementById("typeName").blur();
					layer.close(index);
				},
			});
	   });
	 
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
	   //添加厂商
	   $("#addVendor").on("click",function(){
	   		layer.open({
				title: ['添加厂商', 'background-color:#d0e3f0; color:#888889;'],
				anim: 'scale',
				content: '厂商名称：<input type="text" style="width:150px;height:30px;padding-left:10px;border-radius:3px;" id="vendorName" onblur="validateVendorName()">',
				btn: ['确认','取消'],
				yes: function(index) {
					var vendorName = $("#vendorName").val();
					//判断名字是否为空
					if(!vendorName){
						layer.msg('名称不能为空！',{time: 2000, icon:5});
						return;
					}
					if(!vendorPass) {
			  			layer.msg('厂商名称重复，请确认！',{time: 2000, icon:5});
			  			return;
			  		}
					$.ajax({
						type: "POST",
						url: '<%=basePath%>deviceType/addVendor.action',
				    	data: {'name':vendorName },
						dataType:'json',
						cache: false,
						success: function(data){
							if(data.success){
								layer.open({
											title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
											anim: 'scale',
											content: '保存成功！',
											btn: ['确认'],
											yes: function(index) {
												layer.close(index);
											},
										});
								//在网页上展示出来
								var html = "<option value='"+data.msg+"'>"+vendorName+"</option>";
								$("#vendorId").append(html);
							}else{
								var err = data.message;
								layer.open({
									title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
									anim: 'scale',
									content: '保存失败，错误信息为：' + err + '！',
									btn: ['确认'],
									yes: function(index) {
										layer.close(index);
									},
								});
							 }
						},
						error: function(data){
							layer.open({
								title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
								anim: 'scale',
								content: '请检查网络！',
								btn: ['确认'],
								yes: function(index) {
									layer.close(index);
								},
							});
						}
					});
					layer.close(index);
				},
				no: function(index){
					document.getElementById("typeName").blur();
					layer.close(index);
				},
			});
	   });
	   
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
	   //添加产品
	   $("#addProduct").on("click",function(){
	   		//获取厂商
	   		var vendorId = $("#vendorId").val();
	   		var vendorName = $("#vendorId").find("option:selected").text();
	   		if(vendorId){
		   		layer.open({
					title: ['添加产品', 'background-color:#d0e3f0; color:#888889;'],
					anim: 'scale',
					content: '厂商：<input readonly="readonly" type="text" style="width:150px;height:30px;padding-left:10px;border-radius:3px;" value='+vendorName+'></br>产品名称：<input type="text" style="width:150px;height:30px;padding-left:10px;border-radius:3px;" id="productName" onblur="validateProductName()">',
					btn: ['确认','取消'],
					yes: function(index) {
						var productName = $("#productName").val();
						//判断名字是否为空
						if(!productName){
							layer.msg('名称不能为空！',{time: 2000, icon:5});
							return;
						}
						if(!productPass) {
				  			layer.msg('产品名称重复，请确认！',{time: 2000, icon:5});
				  			return;
				  		}
						$.ajax({
							type: "POST",
							url: '<%=basePath%>product/addProduct.action',
					    	data: {'name':productName, 'vendorId':vendorId},
							dataType:'json',
							cache: false,
							success: function(data){
								if(data.success){
									layer.open({
												title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
												anim: 'scale',
												content: '保存成功！',
												btn: ['确认'],
												yes: function(index) {
													layer.close(index);
												},
											});
									//在网页上展示出来
									var html = "<option value='"+data.msg+"'>"+productName+"</option>";
									$("#productId").append(html);
								}else{
									var err = data.message;
									layer.open({
										title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
										anim: 'scale',
										content: '保存失败，错误信息为：' + err + '！',
										btn: ['确认'],
										yes: function(index) {
											layer.close(index);
										},
									});
								 }
							},
							error: function(data){
								layer.open({
									title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
									anim: 'scale',
									content: '请检查网络！',
									btn: ['确认'],
									yes: function(index) {
										layer.close(index);
									},
								});
							}
						});
						layer.close(index);
					},
					no: function(index){
						document.getElementById("typeName").blur();
						layer.close(index);
					},
				});
	   		}else{
	   			layer.alert("请选择厂商");
	   		}
	   });
	   
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
	   //添加设备型号
	   $("#addModel").on("click",function(){
	   		//获取厂商
	   		var vendorId = $("#vendorId").val();
	   		var vendorName = $("#vendorId").find("option:selected").text();
	   		//获取产品
	   		var productId = $("#productId").val();
	   		var productName = $("#productId").find("option:selected").text();
	   		if(vendorId && productId){
		   		layer.open({
					title: ['添加厂商', 'background-color:#d0e3f0; color:#888889;'],
					anim: 'scale',
					content: '厂商：<input readonly="readonly" type="text" style="width:150px;height:30px;padding-left:10px;border-radius:3px;" value='+vendorName+'></br>产品名称：<input readonly="readonly" type="text" style="width:150px;height:30px;padding-left:10px;border-radius:3px;" value='+productName+'></br>型号名称：<input type="text" style="width:150px;height:30px;padding-left:10px;border-radius:3px;" id="modelName" onblur="validateModelName()">',
					btn: ['确认','取消'],
					yes: function(index) {
						var modelName = $("#modelName").val();
						//判断名字是否为空
						if(!modelName){
							layer.msg('名称不能为空！',{time: 2000, icon:5});
							return;
						}
						if(!modelPass) {
				  			layer.msg('产品名称重复，请确认！',{time: 2000, icon:5});
				  			return;
				  		}
						$.ajax({
							type: "POST",
							url: '<%=basePath%>vmsModel/addModel.action',
					    	data: {'name':modelName ,'productId':productId },
							dataType:'json',
							cache: false,
							success: function(data){
								if(data.success){
									layer.open({
												title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
												anim: 'scale',
												content: '保存成功！',
												btn: ['确认'],
												yes: function(index) {
													layer.close(index);
												},
											});
									//在网页上展示出来
									var html = "<option value='"+data.msg+"'>"+modelName+"</option>";
									$("#modelId").append(html);
								}else{
									var err = data.message;
									layer.open({
										title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
										anim: 'scale',
										content: '保存失败，错误信息为：' + err + '！',
										btn: ['确认'],
										yes: function(index) {
											layer.close(index);
										},
									});
								 }
							},
							error: function(data){
								layer.open({
									title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
									anim: 'scale',
									content: '请检查网络！',
									btn: ['确认'],
									yes: function(index) {
										layer.close(index);
									},
								});
							}
						});
						layer.close(index);
					},
					no: function(index){
						document.getElementById("typeName").blur();
						layer.close(index);
					},
				});
	   		}else{
	   			layer.alert("请选择产品");
	   		}
	   });
	
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
			url:'${sys_ctx}/assetIntegration/validateAssetName.action',
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
  		var url = '${sys_ctx}/assetIntegration/addAsset.action';
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
