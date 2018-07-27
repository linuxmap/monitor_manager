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
				<a href="${sys_ctx}/baseInfo/clientSoftWareManage.action">软件地址管理</a>
			</li>
			<li>
				<a class="action" href="${sys_ctx}/baseInfo/thirdPartyPlatform.action">第三方平台</a>
			</li>
		</ul>
	</div>
	<div class="center-tab">
		<div class="basic-center">
			<form id="fm" name="fm" action="" method="post">
				<div class="row modal_mar">
					<label for="name_title" class="col-xs-5 text-right padding-right-r">宇视平台开关：</label>
					<div class="col-xs-3">
						<input name="uniview" id="yep" class="" type="radio" value="True" style="border: 0 !important;" <c:if test="${uniview == 'True' }">checked="checked"</c:if> />
						<label for="yep">开启</label>
						<input name="uniview" id="never" class="" type="radio" value="False" style="border: 0 !important;" <c:if test="${uniview == 'False' }">checked="checked"</c:if> />
						<label for="never">关闭</label>
					</div>
				</div>
				<div class="row modal_mar">
					<label for="value_title" class="col-xs-5 text-right padding-right-r">讯对讲平台开关：</label>
					<div class="col-xs-3">
						<input name="recoder" id="yes" class="" type="radio" value="True" style="border: 0 !important;" <c:if test="${recoder == 'True' }">checked="checked"</c:if> />
						<label for="yes">开启</label>
						<input name="recoder" id="no" class="" type="radio" value="False" style="border: 0 !important;" <c:if test="${recoder == 'False' }">checked="checked"</c:if> />
						<label for="no">关闭</label>
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
    	var uniview = $("input[name='uniview']:checked").val();
    	var recoder = $("input[name='recoder']:checked").val();
    	if(!uniview || !recoder){
    		layer.alert("请填写完整信息");
    		return;
    	}
    	layer.confirm('确定要保存该配置吗？', {
	  		  btn: ['确定', '取消'],icon: 3 
	  		}, function(index, layero){//确定按钮回执函数
  	  			$.ajax({
  	  				async:true,
  	  				url:'${sys_ctx}/baseInfo/saveThirdPartyPlatform.action',
  	  				type:'POST',
  	  				cache:false,
  	  				data: {"uniview":uniview, "recoder":recoder},
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