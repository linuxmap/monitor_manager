<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <head>
    <title>短线消息通知</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<link rel="stylesheet" href="${sys_ctx }/css/load.css" />
	<link rel="stylesheet" href="${sys_ctx }/css/from/systemConfig.css" />
	<style>
		input:focus::-webkit-input-placeholder{
            text-indent: -999em;
            z-index: -20;
	     }
	     .padding-right-r:{
			padding-right:0px !important;
		}
		
		textarea{
			width:100%;
			height:100px;
			padding:10px;	
			resize: none;
			border:1px #ccc solid !important;
		}
		input[type=checkbox]{
			margin:0;
		    margin-top: 1px\9;
		    line-height: normal;
		    vertical-align: middle;
		}
	</style>
  </head>
</head>
<body>
  	<div id="loading" style="display: none">
		<div id="loading-center">
			<div id="loading-center-absolute">
				<div class="object" id="object_one"></div>
				<div class="object" id="object_two"></div>
				<div class="object" id="object_three"></div>
			</div>
		</div>
	</div>
	<div class="center-tab">
		<div class="basic-center">
			<form id="fm" name="fm" action="" method="post">
				<div class="row modal_mar">
					<label for="name_title" class="col-xs-2 text-right padding-right-r">选择告警等级：</label>
					<div class="col-xs-10" style="margin-top: 4px;">
						<c:forEach var="model" items="${levelList }" varStatus="status">
						    <input type="checkbox" id="" name="levelIds" value="${model.levelId }" ${!empty model.checkAttr ? "checked" : "" } style="border:0 !important"/>${model.name }
						</c:forEach>
					</div>
				</div>
				<div class="row modal_mar">
					<label for="value_title" class="col-xs-2 text-right padding-right-r">待发送手机号：</label>
					<p style="color:red;line-height: 28px;">不同手机号用";"隔开</p>
				</div>
				<div class="row modal_mar">
					<label for="value_title" class="col-xs-2 text-right padding-right-r"></label>
					<div class="col-xs-10">
						<textarea rows="3" cols="40" name="phones" id="phones">${phoneValue }</textarea>
					</div>
				</div>
				<div class="row modal_mar">
					<label for="value_title" class="col-xs-2 text-right padding-right-r">短信内容：</label>
					<div class="col-xs-10">
						<textarea rows="3" cols="40" name="content">${contentValue }</textarea>
					</div>
				</div>
			</form>
			<div class="row modal_mar text-center btn_basic">
				<button class="save but_save"  onclick="phones()">保存</button>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
    layui.use('layer', function(){ 
		var layer = layui.layer; 
	});
	
	function phones(){
		try {
		if ($("#phones").text().length == 0) {
			save();
			return;
		}
			var c =$("#phones").text().match(/;/g).length;
			if(c<=200){
				save();
			}else{
				layer.msg('最大电话个数200',{time: 2000, icon:6});
			}
		} catch(err) {
			layer.msg('发送的手机号请以英文分号;结尾',{time: 2000, icon:6});
		}
	}
	
    function save() {
    	layer.confirm('确定要保存该配置吗？', {
	  		  btn: ['确定', '取消'],icon: 3 
	  		}, function(index, layero){//确定按钮回执函数
  	  			$.ajax({
  	  				async:true,
  	  				url:'${sys_ctx}/message/saveMessageInfo.action',
  	  				type:'POST',
  	  				cache:false,
  	  				data:$('#fm').serialize(),
  	  				dataType:'json',
  	  				success: function (data) {
  	  					if(data.success=="true"){
  	  						layer.msg('保存成功',{time: 2000, icon:6});
  	  					}
  	  				},
  	  				error: function(xhr){
  	  				}
  	  			});
  	  			layer.close(index);
	  		}, function(index){//取消按钮回执函数
	  		});
    }
  </script>
</html>