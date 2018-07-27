<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改密码</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%> 
	<script type='text/javascript' src='${sys_ctx }/js/md5/md5.min.js'></script>
	<style>
		p {
		    margin-top:16%;
		    padding-left: 35%;
		}
		label{
			font-size: 12px;
			font-weight: 100;
		}
		.form-horizontal{
			padding: 20px;
		}
		#save{
			background: #399efc;
		    border: 0;
		    border-radius: 3px;
		    line-height: 30px;
		    padding: 6px 22px;
		    color: #fff;
		   /*  margin: 0 20px; */
		}
		#cancel{
			background: #fff;
		    border: 1px #399efc solid ;
		    border-radius: 3px;
		    line-height: 30px;
		    padding: 6px 20px;
		    color: #399efc;
		    margin: 0 20px;
		}
	</style>
  </head>
  
  <body>
  	<form class="form-horizontal" id="fm" name="fm">
	<div class="form-group">
	    <label align="right" class="col-xs-3 control-label"><span style="color: red;">*</span>旧密码</label>
	    <div style="padding-left: 0;" class="col-xs-9">
	      <input type="password" class="form-control" name="oldPwd" id="oldPwd" data-rule-required="true" />
	    </div>
    </div>
    <div class="form-group">
	    <label align="right" class="col-xs-3 control-label"><span style="color: red;">*</span>新密码</label>
	    <div style="padding-left: 0;" class="col-xs-9">
	      <input type="password" class="form-control" name="newPwd" id="newPwd" data-rule-required="true"/>
	    </div>
    </div>
    <div class="form-group">
	    <label align="right" class="col-xs-3 control-label"><span style="color: red;">*</span>重新输入密码</label>
	    <div style="padding-left: 0;" class="col-xs-9">
	      <input type="password" class="form-control" name="repeatNewPwd" id="repeatNewPwd" data-rule-required="true" data-rule-equalTo="#newPwd" />
	    </div>
    </div>
    <p>
    	<a id="save" role="button" onclick="save()">保存</a>
		<a id="cancel" role="button" onclick="cancel()">取消</a>
    </p>
    </form>
  </body>
  <script type="text/javascript">
  	var layer;
	$(function(){
		layui.use('layer', function(){ 
			layer = layui.layer; 
		});
	});
	
	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		parent.layer.close(index); //再执行关闭 
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
  		
  		layer.confirm('确定要修改吗？修改成功后退出登录', {
	  		  btn: ['确定', '取消'],icon: 3 
	  		}, function(index, layero){//确定按钮回执函数
	  			var oldPwd = $.trim($("#oldPwd").val());
	  			var newPwd = $.trim($("#newPwd").val());
	  			var repeatNewPwd = $.trim($("#repeatNewPwd").val());
	  			//$("#oldPwd").val(md5(oldPwd));//加密处理
	  			//$("#newPwd").val(md5(newPwd));//加密处理
	  			//$("#repeatNewPwd").val(md5(repeatNewPwd));//加密处理
	  			$.ajax({
	  				async:true,
	  				url:'${sys_ctx}/user/modifyPwd.action',
	  				type:"POST",
	  				cache:false,
	  				data:$('#fm').serialize(),
	  				dataType:'json',
	  				success: function (data) {
	  					if(data.success=="true"){
	  						window.location.href="${sys_ctx}/loginOut.action";
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
