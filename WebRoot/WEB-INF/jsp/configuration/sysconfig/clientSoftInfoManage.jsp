<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <head>
    <title>客户端软件配置信息</title>
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
  	<div class="top">
		<ul>
			<li>
				<a href="${sys_ctx}/baseInfo/openSystemConfig.action">基础信息</a>
			</li>
			<li>
				<a class="action" href="${sys_ctx}/baseInfo/clientSoftWareManage.action">软件地址管理</a>
			</li>
			<li>
				<a href="${sys_ctx}/baseInfo/thirdPartyPlatform.action">第三方平台</a>
			</li>
		</ul>
	</div>
	<div class="center-tab">
		<div class="basic-center">
			<form id="fm" name="fm" action="" method="post">
				<div class="row modal_mar">
					<label for="name_title" class="col-xs-2 text-right padding-right-r">软件本地磁盘路径：</label>
					<div class="col-xs-10">
						<input id="localFilePath" class="form-control" type="text" value="${filePath }"/>
					</div>
				</div>
				<div class="row modal_mar">
					<label for="value_title" class="col-xs-2 text-right padding-right-r">客户端软件名称：</label>
					<div class="col-xs-10">
						<input id="localFileName" class="form-control" type="text" value="${fileName }"/>
					</div>
				</div>
			</form>
			<div class="row modal_mar text-center btn_basic">
				<button class="save but_save"  onclick="save()">保存</button>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
    layui.use('layer', function(){ 
		var layer = layui.layer; 
	});
	
    function save() {
    	var filePath = $("#localFilePath").val();
    	var fileName = $("#localFileName").val();
    	if(!filePath || !fileName){
    		layer.alert("请填写完整信息");
    		return;
    	}
    	layer.confirm('确定要保存该配置吗？', {
	  		  btn: ['确定', '取消'],icon: 3 
	  		}, function(index, layero){//确定按钮回执函数
  	  			$.ajax({
  	  				async:true,
  	  				url:'${sys_ctx}/baseInfo/saveClientSoftInfo.action',
  	  				type:'POST',
  	  				cache:false,
  	  				data: {"filePath":filePath, "fileName":fileName},
  	  				dataType:'json',
  	  				success: function (data) {
  	  					if(data.success=="true"){
  	  						layer.alert("修改成功");
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