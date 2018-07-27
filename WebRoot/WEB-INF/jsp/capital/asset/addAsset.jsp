<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>操作设备-新增</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<style>
		table{
			font-size:12px;
			width:100%
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
    		display: inline-block!important;
		}
		input{
			border-radius: 3px;
		    padding-left: 10px;
		    height: 28px;
		    width:222px;
		    line-height: 28px;
		}
		td{
    		padding: 5px 0;
		}
		#addVendor,#addModel{
			width:104px;
		}
		#addProduct{
			width:102px;
		}

		.default-one-input{
			line-height: 30px;
		    height: 28px;
		    border-radius: 3px;
		    width: 110px;
		    padding-left:5px
		}
		#deviceloginCameraId{
			width: 230px;
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
		#connecttype{
			width:221px !important;
			}
		.form-control {
		    display: block;
		    width: 220px;
		    height: 28px;
		    /* position: relative; */
		    padding: 6px 12px;
		    font-size: 14px;
		    /* line-height: 1.42857143; */
		    color: #555;
		    background-color: #fff;
		    background-image: none;
		    border: 1px solid #ccc;
		    float: left;
		    border-radius: 4px;
		    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
		    box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
		    -webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
		    -o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
		    transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
			}
		.need_hide{
		    position: absolute;
		    right: 22px;
		    top: 12px;
		}	
		#status,#zoom{
			width:221px;
		}	
		legend {
		    padding: 0 5px;
		    width: inherit;
		    border-bottom: 0;
		    font-size: 14px;
		}
		.glyphicon {
    position: relative;
    top: 1px;
    left: -22px;
    display: inline-block;
    font-family: 'Glyphicons Halflings';
    font-style: normal;
    font-weight: 400;
    line-height: 1;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}
 	 </style>
 	 <!--[if lt IE 9]>
 	 <style>
			SELECT{padding: 3px 0px 3px 10px;}
		</style>
	<![endif]-->
  </head>
  <body style="margin-bottom:20px;">
  	<div id="dlg"style="padding:10px 20px;" >
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
										<option value="${deviceType.id }" data-loginflag="${deviceType.loginflag }" <c:if test="${deviceType.id == vmsAsset.devicetypeId }">selected="selected"</c:if>>${deviceType.name }</option>
									 </c:forEach>
							</select>
							<input type="hidden" id="loginFlag" name="loginFlag"/>
							<button type="button" class="default-one-btn" id="addType">添加设备类型</button>
	                    </td>
						<td>
							<label>所属组织：</label><input type="text" id="orgName" value="${vmsAsset.orgName }" readonly style="background-color: #EBEBEA;cursor:hand;" data-rule-required="true"/>
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
	                    	<select name="status" id="status" class="default-one-input" data-rule-required="true">
									 <option value="1">运行</option>
									 <option value="2">损坏</option>
									 <option value="3">维修</option>
									 <option value="4">报废</option>
									 <option value="5">库存</option>
									 <option value="6">待安装</option>
									 <option value="7">已安装</option>
									 <option value="8">已调试</option>
							</select>
	                    </td>
	                </tr>
	                <tr >
	                    <td style="position: relative;"><label>保修时间：</label>
	                    	<input class="form-control clear" id="guaranteetime" value="${vmsAsset.guaranteetime }" name="guaranteetime" placeholder="请选择日期" />
							<a href="javascript:void(0);" class="need_hide remove">
		                        <span class="glyphicon glyphicon-remove"></span>
		                    </a>
	                    </td>
	                    <td style="position: relative;"><label>采购时间：</label>
	                    	<input class="form-control clear" id="buytime" value="${vmsAsset.buytime }" name="buytime" placeholder="请选择日期" />
							<a href="javascript:void(0);" class="need_hide remove">
		                        <span class="glyphicon glyphicon-remove"></span>
		                    </a>
	                    </td>
	                </tr>
	                <tr >
	                    <td><label>工程商：</label>
	                    	<input type="text" id="setupprovider" name="setupprovider" value="${vmsAsset.setupprovider }" title="请输入工程商">
	                    </td>
	                    <td style="position: relative;"><label>安装时间：</label>
	                    	<input class="form-control clear" id="setuptime" value="${vmsAsset.setuptime }" name="setuptime" placeholder="请选择日期" />
							<a href="javascript:void(0);" class="need_hide remove">
		                        <span class="glyphicon glyphicon-remove"></span>
		                    </a>
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
	                    <td style="visibility: hidden;"><label>关联图片：</label>
	                    	<input type="text" id="pictureurl" name="pictureurl" value="${vmsAsset.pictureurl}" >
	                    </td>
	                </tr>
	          </table>
	      </fieldset> 
	      <fieldset class="bor-form"> 
  				<legend>地图信息</legend>           
         		 <table style="width:100%">      
                <!-- camera地图坐标 -->
	                <tr>
	                    <td><label>经度：</label>
	                    	<input type="text" id="longitude" name="longitude" value="${vmsCamera.longitude}" title="请输入经度">
	                    </td>
	                    <td><label>纬度：</label>
	                    	<input type="text" id="latitude" value="${vmsCamera.latitude }" name="latitude" title="请输入纬度"/>
	                    </td>
	                </tr>
	                <tr >
	                    <td><label>缩放级别：</label>
	                    	<select name="zoom" id="zoom" class="default-one-input"  data-rule-required="true">
							</select>
	                    </td>
	                </tr>
	            </table>
	       </fieldset>
           <table><!-- 打开新增页面时改元素隐藏  -->   
                <!-- 登录信息 -->
                <tr class="controlLogin" style="display: none;">
                    <td style="width:150px"><label>通过其他资源登录：</label><!-- 未选中的checkbox和禁用的控件不是有效控件，不会被POST -->
                    	<!-- 生成这样的表单，当checkbox未选中的时候，提交的是hidden表单。值0就被提交到服务器了。当checkbox都选中的时候，hidden和checkbox表单都被提交了，但是因为它们的name是一样的，所以hidden的值被 -->
                    	<input type="checkbox" id="chooseLoginResource2" value="1" name="loginSource" checked="checked" onclick="chooseLoginResource();" style="width:20px;height:18px;border:0 !important;"/>
                    </td>
                    <td id="currentLoginList"><label class="currentLoginList" style="margin-left:240px;">选择其他资源：</label>
                    	<select name="deviceloginCameraId" id="deviceloginCameraId" class="default-one-input currentLoginList" >
								 <c:forEach items="${deviceLoginList }" var="deviceLogin">
									<option value="${deviceLogin.deviceloginId }">${deviceLogin.deviceNameDisplay }</option>
								 </c:forEach>
						</select>
                    </td>
                </tr>
            </table>
            <fieldset class="bor-form loginChunk" style="display: none;"><!-- 打开新增页面时改元素隐藏  -->
  				<legend>登录信息</legend>    
	                <table>
		                <tr>
		                    <td><label>IP地址：</label>
		                    	<input type="text" id="ip" name="ip" value="${vmsDeviceLogin.ip}" title="请输入IP地址">
		                    </td>
		                    <td  style="padding-left:18px;"><label>端口：</label>
		                    	<input type="text" id="port" value="${vmsDeviceLogin.port }" name="port" title="请输入端口"/>
		                    </td>
		                </tr>
		                <tr>
		                    <td><label>用户名：</label>
		                    	<input type="text" id="loginuser" name="loginuser" value="${vmsDeviceLogin.loginuser }" title="请输入用户名">
		                    </td>
		                    <td  style="padding-left:18px;"><label>密码：</label>
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
		                    <td  style="padding-left:18px;"><label>连接值：</label>
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
		
		//初始化加载缩放级别
		var $zoom = $("#zoom");
		for(var i=1; i<22; i++){
			var option = "<option value="+i+">"+i+"</option>";
			$zoom.append(option);
		}
	});
	
	layui.use('layer', function(){ 
		var layer = layui.layer; 
	});
	
	//清空某个输入框
	function remove(){
		var $len=$(".need_hide")
		for(var i=0;i< $len.length ;i++){
			 (function(i){
				$len.eq(i).click(function(){
					$(this).prev().val('');
				});
			  })(i)
		}
	}
	remove();//清除某个输入框的值
	
	//处理日期
	layui.use('laydate',function(){
		laydate = layui.laydate;
		//时间的设置和校验
		var start = {
			/* min : laydate.now(0,'YYYY年MM月DD日 hh:mm:ss')//自定义最小时间获取日期的当前时间
			, */max : '2099-06-16 23:59:59'
			,format : 'YYYY-MM-DD hh:MM:ss'
			,istime : true
			,choose : function(datas){
					
			}
		};
		//保修 采购 安装 的 时间点击触发
		document.getElementById('guaranteetime').onclick = function(){
			start.elem = this;
			laydate(start);//渲染到页面中
		}
		document.getElementById('setuptime').onclick = function(){
			start.elem = this;
			laydate(start);//渲染到页面中
		}
		document.getElementById('buytime').onclick = function(){
			start.elem = this;
			laydate(start);//渲染到页面中
		}
	});
	//格式化时间
	Date.prototype.Format = function(fmt) {
		var o = {
			"M+" : this.getMonth() +1,
			"d+" : this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
		};
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    	if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
	//集中处理下拉框
	var productJ = '${productJ}';//产品
	var procuctArray = eval(productJ);
	var modelJ = '${modelJ}';//型号
	var modelArray = eval(modelJ);
	var typeJ = '${typeJ}';//设备类型
	var typeArray = eval(typeJ);
	
	//选择设备类型显示或者隐藏登录表单  loginflag为1时显示，0时隐藏
	function showLoginForm(typeId) {
	//alert(data-loginflag);
		if (typeId) {
			var loginflag = $("#devicetypeId").find("option:selected").attr("data-loginflag");
			if (loginflag == 1) {//需要登录信息
				$(".controlLogin").show();
				$("#loginFlag").val(1);
			} else if (loginflag == 0) {//不需要登录信息
				//都隐藏
				$(".controlLogin").hide();
				$(".loginChunk").hide();
				$("#loginFlag").val(0);
			}
		} else {
			//没有选择
			$(".controlLogin").hide();
			$(".loginChunk").hide();
			$("#loginFlag").val(0);
		}
		$("#vendorId").find("option[value='']").prop("selected",true);
		$("#productId").find("option[value='']").prop("selected",true);
		$("#modelId").find("option[value='']").prop("selected",true);
	}	
	//产品下拉框控制  选择厂商
	function getProdctOptions(vendorId){
		var devicetypeId = $("#devicetypeId").val();
		if (!devicetypeId) {
			$("#vendorId").find("option[value='']").prop("selected",true);
			layer.msg('请选择设备类型！',{time: 2000, icon:6});
			return;
		}
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
	
	//设备型号下拉框控制  从后台数据选择设备类型 选择产品
	function getModelOptions(productId){
		var devicetypeId = $("#devicetypeId").val();
		if (!devicetypeId) {
			$("#vendorId").find("option[value='']").prop("selected",true);
			$("#productId").find("option[value='']").prop("selected",true);
			layer.msg('请选择设备类型！',{time: 2000, icon:6});
			return;
		}
		if(productId){
			//清空productId的select
			$("#modelId").empty();
			$("#modelId").append("<option value=''>请选择</option>");
			//从后台根据设备类型和产品查询出设备型号
			$.ajax({
				async:false,
				url:'${sys_ctx}/vmsModel/selectModelByDeviceProduct.action',
				type:"POST",
				cache:false,
				data:{'devicetypeId':devicetypeId, 'productId' : productId},
				dataType:'json',
				success: function (data) {
					if(data.success=="true"){
						var modelArray = data.ower;
						if (modelArray) {
							for(var i=0; i<modelArray.length; i++) {
								var sel = "<option value='"+modelArray[i].modelId+"'>"+modelArray[i].name+"</option>";
								$("#modelId").append(sel);
							}
						}
					} else {
						
					}
				},
				error: function(xhr){
				}
			});
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
				content: '<lable>类型名称：</lable><input type="text" style="width:195px;height:30px;border-radius:3px;" id="typeName"  onblur="validateTypeName()"></br> <lable style="margin-top:20px;display:inline-block;line-height: 28px;height:28px;">需要登录信息：</lable><input type="checkbox" style="border: 0 !important;" style="width:20px;height:20px;padding-left:10px;border-radius:3px;margin:0;" id="loginflag" >',
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
								layer.msg('添加成功！',{time: 2000, icon:6});
								//在网页上展示出来
								var html = "<option value='"+data.msg+"' data-loginflag='"+loginflag+"'>"+typeName+"</option>";
								$("#devicetypeId").append(html);
								//添加成功后将选择选择为空
								$("#devicetypeId").find("option[value='']").prop("selected",true);
								//将登录信息重新设置为隐藏
								$(".controlLogin").hide();
								$(".loginChunk").hide();
								$("#loginFlag").val(0);
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
					url:'${sys_ctx}/vmsvendor/validateVendorName.action',
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
						url: '<%=basePath%>vmsvendor/addVendor.action',
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
					content: '<lable style="width: 70px;text-align: right;display:inline-block;line-height: 28px;height:28px;">厂商：</lable><input readonly="readonly" type="text" style="width:150px;height:30px;padding-left:10px;border-radius:3px;" value='+vendorName+'></br><lable style="margin-top:20px;display:inline-block;line-height: 28px;height:28px;">产品名称：</lable><input type="text" style="width:150px;height:30px;padding-left:10px;border-radius:3px;" id="productName" onblur="validateProductName()">',
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
	   			layer.msg("请选择厂商",{time: 2000, icon:5});
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
	   		//获取设备类型
	   		var devicetypeId = $("#devicetypeId").val();
	   		//获取厂商
	   		var vendorId = $("#vendorId").val();
	   		var vendorName = $("#vendorId").find("option:selected").text();
	   		//获取产品
	   		var productId = $("#productId").val();
	   		var productName = $("#productId").find("option:selected").text();
	   		if(vendorId && productId){
		   		layer.open({
					title: ['添加型号', 'background-color:#d0e3f0; color:#888889;'],
					anim: 'scale',
					content: '<lable style="width: 70px;text-align: right;display:inline-block;line-height: 28px;height:28px;">厂商：</lable><input readonly="readonly" type="text" style="width:150px;height:30px;padding-left:10px;border-radius:3px;" value='+vendorName+'></br><lable style="margin-top:20px;display:inline-block;line-height: 28px;height:28px;">产品名称：</lable><input readonly="readonly" type="text" style="width:150px;height:30px;padding-left:10px;border-radius:3px;" value='+productName+'></br><lable style="margin-top:20px;display:inline-block;line-height: 28px;height:28px;">型号名称：</lable><input type="text" style="width:150px;height:30px;padding-left:10px;border-radius:3px;" id="modelName" onblur="validateModelName()">',
					btn: ['确认','取消'],
					yes: function(index) {
						var modelName = $("#modelName").val();
						if(!devicetypeId){
							layer.msg('设备类型不能为空！',{time: 2000, icon:5});
							return;
						}
						//判断名字是否为空
						if(!modelName){
							layer.msg('名称不能为空！',{time: 2000, icon:5});
							return;
						}
						if(!modelPass) {
				  			layer.msg('产品型号重复，请确认！',{time: 2000, icon:5});
				  			return;
				  		}
						$.ajax({
							type: "POST",
							url: '<%=basePath%>vmsModel/addModel.action',
					    	data: {'name':modelName ,'productId':productId, 'devicetypeId':devicetypeId},
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
	   			layer.msg("请选择产品",{time: 2000, icon:5});
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
	//保存js
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
  		var loginFlag = $("#loginFlag").val();
  		var check = $("#chooseLoginResource2").prop("checked");
  		if (loginFlag==1 && !check) {
  			//验证
  			funIP();
  			var port =$("#port").val();
  			var loginuser =$("#loginuser").val();
  			var loginpwd =$("#loginpwd").val();
  			var connecttype =$("#connecttype").val();
  			var channelcount =$("#channelcount").val();
  			if(port==""){
  				layer.msg('端口号不能为空！',{time: 2000, icon:5,offset: ['100px', '450px']});
  				return;
  			}
  			if(loginuser==""){
  				layer.msg('用户名不能为空！',{time: 2000, icon:5,offset: ['100px', '450px']});
  				return;
  			}
  			if(loginpwd==""){
  				layer.msg('密码不能为空！',{time: 2000, icon:5,offset: ['100px', '450px']});
  				return;
  			}
  			if(connecttype==""){
  				layer.msg('请选择连接方式！',{time: 2000, icon:5,offset: ['100px', '450px']});
  				return;
  			}
  			if(channelcount==""){
  				layer.msg('通道数量不能为空！',{time: 2000, icon:5,offset: ['100px', '450px']});
  				return;
  			}
  		}
  		if(!assetNamePass) {//验证用户名未通过
			layer.msg('设备名称不能重复，请确认！',{time: 2000, icon:5,offset: ['100px', '450px']});
  			return;
  		}
  		var url = '${sys_ctx}/asset/addAsset.action';
  		layer.confirm('确定要保存该信息吗？', {
	  		btn: ['确定', '取消'],title:'提示信息',icon: 3,offset: ['100px', '450px']
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
	  						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);  // 关闭layer
							window.parent.location.reload(); //刷新父页面
	  					} else {
	  						layer.alert(data.msg, {title:'提示信息',icon: 5,offset: ['100px', '450px']});
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
  	/*IP验证  */
  	function funIP() {  
		    var ip =$("#ip").val();  
		    if(ip==""){
		    	layer.msg("IP不能为 空",{time: 2000, icon:5,offset: ['100px', '450px']});
		    }else{
		      if(!isValidIP(ip)){  
		         layer.msg("请填写正确IP",{time: 2000, icon:5,offset: ['100px', '450px']});
		    	}	
		    }
		   
		}
  	function isValidIP(ip){     
	    var reg =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/  ;   
	    return reg.test(ip);     
	}    
  </script>
</html>
