<%@page import="zst.extend.util.PropertiesUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
String sysName = PropertiesUtil.getPropertiesValue("sysConfig.properties", "sys_name");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>基础信息</title>
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
				<a class="action" href="${sys_ctx}/baseInfo/openSystemConfig.action">基础信息</a>
			</li>
			<li>
				<a href="${sys_ctx}/baseInfo/clientSoftWareManage.action">软件地址管理</a>
			</li>
			<li>
				<a href="${sys_ctx}/baseInfo/thirdPartyPlatform.action">第三方平台</a>
			</li>
		</ul>
	</div>
	<div class="center-tab">
		<div class="basic-center">
			<form id="fm" name="fm" action="${sys_ctx}/baseInfo/uploadLogoAndSysName.action" method="post" enctype="multipart/form-data">
				<div class="row modal_mar">
					<label for="meeting_title" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 text-right padding-right-r">系统名称：</label>
					<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
						<input id="sysName" class="form-control" type="text" name="sysName" value="<%=sysName %>"/>
					</div>
				</div>
				<div class="row modal_mar">
					<label for="meeting_title" class="col-xs-2 text-right padding-right-r">系统图标：</label>
					<div class="col-xs-10" style="position: relative;">
						<div class="big_img">
							<div class="hover-img" style="display: none;">
								<a href="####">删除</a>
							</div>
							<div id="result" class="thumbnail">
								<img class="img-circle" src="${sys_ctx }/img/login_logo.png?random=<%=Math.random() %>"/>
							</div>
						</div>
						<p class="img-alt">请上传小于2M的PNG、JPG、JGEG或GIF文件；最佳像素48×48</p>
						<span id="file1" class="btn btn-success btn-file" > 浏览图片
						       <span class="glyphicon glyphicon-picture" aria-hidden="true"></span>
						       <input id="file" onchange="readAsDataURL(this)" type="file" accept="image/gif,image/jpeg,image/jpg,image/png" name="logoImg"/>
						</span>
							 <input id="file2" onchange="readAsDataURL(this)" type="file" accept="image/gif,image/jpeg,image/jpg,image/png" name="logoImg" style="display:none"/>
					</div>
					<div class="row modal_mar text-center btn_basic">
						<input type="button" class="save but_save" value="保存" onclick="save()">
					</div>
				</div>
				
			</form>
		</div>
	</div>
  </body>
  <script type="text/javascript">
   $(document).ready(function(){
		 $(".big_img").mouseenter(function(){
		 	 $(".hover-img").css("display","block");
		 }); 
		  $(".big_img").mouseleave(function(){
		     $(".hover-img").css("display","none");
		  });
		
	 	 $(".hover-img a").click(function(){
	 	 	$(".thumbnail").html("")
	 	 	$(".hover-img").hide()
	 	 });
	 	if (document.all && document.querySelector && !document.addEventListener) {
	 		debugger;
			$("#file2").show();
			$("#file1").hide();
		}
	});
   
    layui.use('layer', function(){ 
		var layer = layui.layer; 
	});
    
	var result = document.getElementById("result");
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera; 
    if(typeof FileReader == 'undefined') {
        result.innerHTML = "抱歉，你的浏览器不支持图片上传预览";
    } 
    // 将文件以Data URL形式进行读入页面
    function readAsDataURL(target,id){
    	var fileSize = 0; 
    	var filemaxsize = 1024*2;//2M 
		if (isIE && !target.files) { 
			var filePath = target.value; 
			var fileSystem = new ActiveXObject("Scripting.FileSystemObject"); 
			if(!fileSystem.FileExists(filePath)){ 
				layer.msg('附件不存在，请重新输入！',{time: 2000, icon:5}); 
				return false; 
			} 
			var file = fileSystem.GetFile (filePath); 
			fileSize = file.Size; 
		} else { 
			fileSize = target.files[0].size; 
		} 
		
		var size = fileSize / 1024; 
		if(size>filemaxsize){ 
			layer.msg('附件大小不能大于"+filemaxsize/1024+"M！',{time: 2000, icon:5}); 
			target.value =""; 
			return false; 
		} 
		if(size<=0){ 
			layer.msg('附件大小不能为0M！',{time: 2000, icon:5}); 
			target.value =""; 
			return false; 
		} 

        // 检查是否为图像类型
        var simpleFile = document.getElementById("file").files[0];
        if(!/image\/\w+/.test(simpleFile.type)) {
            layer.msg('请确保文件类型为图像类型！',{time: 2000, icon:5});
            target.value ="";
            return false;
        }
        var reader = new FileReader();
        // 将文件以Data URL形式进行读入页面
        reader.readAsDataURL(simpleFile);
        reader.onload = function(e){
            result.innerHTML = '<img src="'+this.result+'" alt=""/><input type="text" style="visibility: hidden" value="'+this.result+'">';
           // result.innerHTML = '<input type="text" style-" value="'+this.result+'">';
        }
    }
    
    //确定上传
    function save() {
    	layer.confirm('确定要保存吗?保存后请刷新页面', { btn: ['确定', '取消'],title:'提示信息',icon: 3 }, 
    		function(index, layero){//确定按钮回执函数
				document.getElementById("fm").submit();
	  		}, function(index){//取消按钮回执函数
	  			
	  		});
    }

  </script>
</html>
