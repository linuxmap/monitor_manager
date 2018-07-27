<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>操作组织机构</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<link rel="stylesheet" href="${sys_ctx}/css/from/operateOrg.css" type="text/css"></link>
	<style type="text/css">
		label{
		margin-right:10px;
		margin-top: 5px;
		}
		#beizhu{
			/* float:left;
			padding-left:70px;
			width:170px;
			
		    margin-bottom: 55px;
		    border: 1px red solid;
		}
	</style>
	<!--[if lt IE 9]>
		<style>
		select{padding:3px 0px 3px 10px}
		#longitude,#latitude,#orderNum,#description,#zoom,#orgName{box-sizing: border-box;}
		input[type=text], textarea, select, input {
	    -webkit-transition: all 0.30s ease-in-out;
	    -moz-transition: all 0.30s ease-in-out;
	    -ms-transition: all 0.30s ease-in-out;
	    -o-transition: all 0.30s ease-in-out;
	    outline: none;
	    border: 1px solid #a2a7a6!important;
	    font-size: 12px;
	    padding-left: 10px;
	}
		</style>
	<![endif]-->
	
	
  </head>
  <body>
  	<div id="dlg" align="center" style="padding:10px 20px;" >
        <form id="fm" method="post">
        	<input name="orgId" id="id" value="${sysOrg.orgId}" type="hidden">
            <input name="parentId" id="parentId" value="${sysOrg.parentId}" type="hidden" >                	            
            <input name="orgLevel" id="orgLevel" value="${sysOrg.orgLevel}" type="hidden" >
            
            <!-- 组织机构信息变动的隐藏域 -->
            <input name="oldObjName" id="oldObjName" value="${sysOrg.orgName}" type="hidden">
            <input name="oldLevel" id="oldLevel" value="${sysOrg.zoom}" type="hidden" >               	            
            <input name="oldLongitude" id="oldLongitude" value="${sysOrg.longitude}" type="hidden" >
            <input name="oldLatitude" id="oldLatitude" value="${sysOrg.latitude}" type="hidden">
            <input name="oldOrderNum" id="oldOrderNum" value="${sysOrg.orderNum}" type="hidden" >                	            
            <input name="oldDescription" id="oldDescription" value="${sysOrg.description}" type="hidden" >
                               	            
            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                <tr >
                    <td><label><span style="color: red;">*</span>组织机构：</label><input type="text" id="orgName" maxlength="12" name="orgName" value="${sysOrg.orgName}" data-rule-required="true" title="请输入组织机构名"></input></td>		  		                    
                </tr>
                <tr >
                    <td><label>上级组织机构：</label><input type="text" id="orgParName" value="${orgParName}" ></td>		                    
                </tr>
                <tr >
                    <td>
                    	<label>缩放级别：</label>
						 <select id="zoom" name="zoom">
							<c:forEach var="i" begin="1" step="1" end="18">
								<option <c:if test="${sysOrg.zoom == i}">selected="selected"</c:if> value="${i }">${i }</option>
							</c:forEach> 
						</select>
                    </td>		                    
                </tr>
                <tr >
                    <td><label>经度：</label><input type="text" id="longitude"  name="longitude" value="${sysOrg.longitude}" number="true"></td>		                    
                </tr>
                <tr >
                    <td><label>纬度：</label><input type="text" id="latitude"  name="latitude" value="${sysOrg.latitude}" number="true"></td>		                    
                </tr>
                <tr >
                    <td><label>节点排序：</label><input type="text" id="orderNum"  name="orderNum" value="${sysOrg.orderNum}" data-rule-digits="true"></td>		                    
                </tr>
	            <tr>
	             	<td><label id="beizhu">备注：</label><textarea id="description" name="description">${sysOrg.description }</textarea>	
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
    var url;
	var funtType = '${funtType}';	
	$(function(){
		$('#orgParName').attr("disabled",true);
		//新增时赋值
		if(funtType == "add"){
		    url = '${sys_ctx}/org/addOrg.action';
		}else if(funtType == "edit"){
			url = '${sys_ctx}/org/modifyOrg.action';
		} else if(funtType == "view") {
			$('#orgParName').attr("disabled",false);
			setFormDisable('fm');
		} else if(funtType == "copy") {
			url = '${sys_ctx}/org/addOrg.action';
		}	
		
		layui.use('layer', function(){ 
			var layer = layui.layer; 
  		});
	});
  
	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		parent.layer.close(index); //再执行关闭 
    }
	
	var isPass = true;//是否通过姓名重复验证
	//验证组织机构名是否重复
	function validateOrgName() {
		var orgName = $('#orgName').val();
		if(funtType == "edit") {//若是在修改的情况下，防止用户触发该事件重新把值改为打开时的值，但仍进行判断的情况
			if(orgName == '${sysOrg.orgName}') {
				isPass = true;
				return;
			}
		}
		$.ajax({
			async:false,
			url:'${sys_ctx}/org/validateOrgName.action',
			type:"POST",
			cache:false,
			data:{'orgName':orgName},
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
	//保存角js
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
  		/* if(funtType == "add" && !isPass) {//验证组织机构名未通过
  			layer.msg('组织机构名重复，请确认！',{time: 2000, icon:5});
  			return;
  		} */
  		var description = $("#description").val();
  		if (description && description.length>=100) {
  			layer.msg('备注少于100字符',{time: 2000, icon:6});
  			return;
  		}
  		layer.confirm('确定要保存该信息吗？', {
	  		  btn: ['确定', '取消'],icon: 3 
	  		}, function(index, layero){//确定按钮回执函数
	  			$.ajax({
	  				async:true,
	  				url : url,
	  				type : "POST",
	  				cache : false,
	  				data : $('#fm').serialize(),
	  				dataType : 'json',
	  				success : function (data) {
	  					if(data.success=="true"){
	  						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);  // 关闭layer
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
