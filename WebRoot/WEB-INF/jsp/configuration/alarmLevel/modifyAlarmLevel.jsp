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
<title>编辑告警级别</title>
<link rel="stylesheet" href="${sys_ctx}/css/from/operateOrg.css" type="text/css"></link>
	<style type="text/css">
		label{
			margin-right:10px;
			margin-top: 5px;
			display: inline-block!important;
		}
		#icon-color {
			background: url('${sys_ctx}/img/icon_color.png');
			background-size: contain;
			display:none !important
		}
		#color {
			width: 200px;
		}
		.colorpicker{
			left: 36px !important;
    		top: 100px !important;
		}
		#colorSelector{
			display: inline-block;
		}
	</style>
<link rel="stylesheet" href="${sys_ctx}/plug/colorpicker/css/colorpicker.css" type="text/css"></link>
<script type="text/javascript" src="${sys_ctx}/plug/colorpicker/js/colorpicker.js"></script>
</head>
	<body>
		<form id="fm" name="fm">
			<input name="levelId" id="levelId" value="${alarmLevel.levelId}" type="hidden">
			<div class="tabcenter">
				<table style="width:100%">
				    <tr >
	                    <td><label><span style="color: red;">*</span>级别名称:</label><input type="text" id="name" name="name" value="${alarmLevel.name}" data-rule-required="true" title="请输入级别名称" onblur="validateNameForModify()"></input></td>		  		                    
	                </tr>
	                <tr >
	                    <td><label><span style="color: red;">*</span>级别编码:</label><input type="text" id="value" name="value" value="${alarmLevel.value}" data-rule-required="true" title="请输入级别编码" onblur="validateValueForModify()"></td>		                    
	                </tr>
	                <tr >
	                    <td><label>级别颜色:</label>
		                    <div id="colorSelector">
							  <input type="text" id="color" name="color" placeholder="等级颜色" aria-describedby="sizing-addon1" value="${alarmLevel.color}" style="background-color: ${alarmLevel.color};">
							  <span id="icon-color" style="width: 30px; height: 30px; display: inline-block; vertical-align: bottom;"></span>
							</div>
	                    </td>		                    
	                </tr>
		            <tr>
		             	<td><label>级别描述:</label><textarea id="description" name="description">${alarmLevel.description }</textarea>	
		             	</td>
		            </tr>
				</table>
			</div>
		</form>
	 <div class="btn-foot">
		 	<button id="save" onclick="save()">保存</button>
	        <button id="cancel" onclick="cancel()">取消</button>
     </div>
	</body>
<script type="text/javascript">
	layui.use('layer', function() {
		layer = layui.layer;
	});
		
	function cancel() {
  		laycls(0);
  	}
  	var isPass = true;//是否通过名称重复验证
  	//验证告警级别名称是否重复
	function validateNameForModify() {
		var name = $('#name').val();
		var levelId = $("#levelId").val();
		$.ajax({
			async:false,
			url:'${sys_ctx}/alarm/validateNameForModify.action',
			type:"POST",
			cache:false,
			data:{'levelId':levelId, 'name':name},
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
	
	
  	var isValuePass = true;//是否通过名称重复验证
  	//验证告警级别名称是否重复
	function validateValueForModify() {
		var value = $('#value').val();
		var levelId = $("#levelId").val();
		$.ajax({
			async:false,
			url:'${sys_ctx}/alarm/validateNameForModify.action',
			type:"POST",
			cache:false,
			data:{'levelId':levelId, 'value':value},
			dataType:'json',
			success: function (data) {
				if(data.success=="false"){
					isValuePass = false;
				} else {
					isValuePass = true;
				}
			},
			error: function(xhr){
				isValuePass = false;
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
  		
  		if(!isPass) {
  			layer.msg('级别名称重复，请确认！',{time: 2000, icon:5});
  			return;
  		}
  		if(!isValuePass) {
  			layer.msg('级别编码重复，请确认！',{time: 2000, icon:5});
  			return;
  		}
  		
  		layer.confirm('确定要保存该信息吗？', {
	  		  btn: ['确定', '取消'],icon: 3 
	  		}, function(index, layero){//确定按钮回执函数
	  			$.ajax({
	  				async:true,
	  				url : '${sys_ctx}/alarm/modifyAlarmLevel.action',
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
  	// 告警颜色选择框
  	
	var color = $('#color').val();
	this.color = color;
  	$('#colorSelector').ColorPicker({
		onShow: function (colpkr) {
			debugger
			$(colpkr).fadeIn(500);
			/* var left = $('#colorSelector').offset().left + 200;
			var top = $('#colorSelector').offset().top;
			console.log(left);
			console.log(top);
			$(colpkr).css("left", left);
			$(colpkr).css("top", top); */
			return false;
		},
		onBeforeShow: function () {
			var color = $('#color').val();
			this.color = color;
		},
		onHide: function (colpkr) {
			$(colpkr).fadeOut(500);
			return false;
		},
		color: color,
		onChange: function (hsb, hex, rgb) {
			$('#color').css('backgroundColor', '#' + hex);
			$('#color').val('#' + hex);
		},
		onSubmit: function(hsb, hex, rgb, el) {
			$(el).ColorPickerHide();
		},
	});
  	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		parent.layer.close(index); //再执行关闭 
    }
</script>
</html>
