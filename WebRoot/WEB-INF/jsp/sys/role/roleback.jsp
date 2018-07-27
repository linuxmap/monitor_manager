<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>管理端</title>
<base href="<%=basePath%>">
<%@include file="/WEB-INF/jsp/common/common.html"%>
  <style type="text/css">
	.layui-colla-item{position:relative;}
	.label-1{
			position: absolute;
		    z-index: 9;
		    top: 10px;
		    left: 45px;
	    }
    td {
		margin: 0;
		padding: 0;
	}

	body,html{height:100%;overflow: hidden;}
		.left-user{width:200px; height:100%;  float:left;}
		.right-user{overflow-x:hidden; height:100%;padding-bottom:50px}
		.Privilege-p{
			display: -webkit-flex;
			display: flex;
			justify-content: flex-start;
			flex-wrap: wrap;	
			border-bottom: 0;
		}
		.Privilege-p p{
			margin:8px;
			text-align: center;
			display: inline-block;
		}
		input{
			display: inline-block;
			width:15px;
			height:15px;
		}
		label{
			padding-bottom: 2px;
			font-weight:500;
			margin-bottom: 0 !important;
		}
		.title-h2{
			padding-left:10px;
		}
		.panel-default{
			border-color:transparent;
		}
		.f_right{
			text-align:right;
			margin-bottom: 2px!important;
		}
		.panel{
			margin-bottom:0;
		}
		.list-group-item {
			border:0;
		    border-top: 1px solid #ddd;
		}
		.layui-table td{padding:0;}
		.panel-body p{padding:8px;}
		.btn-group{margin-bottom:2px}
		input[type=checkbox], input[type=radio] {
		    margin:0;
		    margin-top: 1px\9;
		    line-height: normal;
		    vertical-align: middle;
		}
		.list-group-item.active, .list-group-item.active:focus, .list-group-item.active:hover {
		    z-index: 2;
		    color: #fff;
		    background-color: #3997f4;
		    border-color: #3997f4;
		}
		#roles{
			overflow-y: auto;
		}
</style>
<!--[if lt IE 9]>
		<style>
			input[type=checkbox], input[type=radio] {
			    margin:0;
			    line-height: normal;
			    vertical-align: middle;
			}
			input{
				border:0 !important;
			}
			p{
				display:block;
				width:100px
			}
			.right-user {
			 	overflow: auto;
			    overflow-x: hidden;
			    height: 96%;
			    padding-bottom: 60px;
			}
		</style>
	<![endif]-->


  <body>
    <div class="panel panel-default f_right" >
    		<!-- 角色id -->
    		<input type="hidden" id="roleId" name="" />
			<button type="button" class="default-one-btn" id="save">保存</button>
			<button type="button" class="default-one-btn-top" id="reset">重置</button>
		</div>
	<div class="layui-collapse right-user" >
		<c:forEach items="${roleMenuList}" var="menu1">
			<div class="layui-colla-item">
				<h2 class="layui-colla-title">
				<lable class="label-1"> <input type="checkbox" style="border:0 !important" data-id="${menu1.id }" >${menu1.funtName } </lable>
				</h2>
				<div class="layui-colla-content layui-show">
					
					<c:forEach items="${menu1.subMenuList}" var="menu2">
					<table class="layui-table">
		    		 <colgroup>
					    <col width="20%">
					    <col width="80%">
					    <col>
					  </colgroup>
						<c:if test="${not empty menu2.subMenuList}">
							<tr>
								<td class="panel-body">
									<p>
										 <input type="checkbox"  style="border:0 !important" data-id="${menu2.id }" ><label>${menu2.funtName }</label>
									</p>
								</td>
								<td class="Privilege-p">
									<c:forEach items="${menu2.subMenuList}" var="menu3">
										 <p><input type="checkbox" style="border:0 !important" data-id="${menu3.id }" /><label>${menu3.funtName }</label></p>
									</c:forEach>
								</td>
							</tr>
						</c:if>
						<c:if test="${ empty menu2.subMenuList}">
							<tr>
								<td class="Privilege-p">
									<p>
									 	<input type="checkbox" style="border:0 !important" data-id="${menu2.id }" ><label>${menu2.funtName }&nbsp;&nbsp;</label>
									</p>
								</td>
							</tr>
						</c:if>
						</table>
					</c:forEach>
				</div>
			</div>
		</c:forEach>
	</div>
	
	<script type="text/javascript">
	var dataRoleid;
	var roleId;
	layui.use('layer', function(){ 
			layer = layui.layer; 
		}); 
		//保存
	    $("#save").on("click",function(){
		    $('#roles li', window.parent.document).each(function(){
	    		if($(this).hasClass("active")){
	    			var choosedLi = $(this);
				   	dataRoleid = choosedLi.attr("data-roleid");
				    roleId=dataRoleid;
	    		}
	    	})
	    	if (!roleId) {
	    		layer.alert("请选择角色");
	    		return;
	    	}
	    	var list = [];
	    	var CheckedList = $("input:checked");
	    	if (null != CheckedList && CheckedList.length>0) {
		    	for (var i = CheckedList.length - 1; i >= 0; i--) {
		    		var itemChecked = CheckedList[i];
		    		var item = $(itemChecked).attr("data-id");
		    		list.push(item);
		    	}
	    	} else {
	    		list.push(-1);
	    	}
	    	
 			$.ajax({
				type: "POST",
				url: '<%=basePath%>role/updateDoubleFuntByRoleId.action',
		    	data: {
		    		'funtIds':list,
		    		'roleId':roleId,
		    		'funtGroup':1
		    		},
				dataType:'json',
				cache: false,
				success: function(data){
					if(data.success){
						layer.msg(data.msg,{time: 2000, icon:6});
					}else{
						var err = data.message;
						layer.alert("保存失败，错误信息："+err, {title:'提示信息',icon: 5});
					 }
				},
				error: function(data){
					layer.msg('请检查网络！',{time: 2000, icon:5});
				}
			});

	  	});
	  	
	  	//重置，将所有选择框取消选择
	  	$("#reset").on("click",function(){
	  	  		$("input[type='checkbox']").removeAttr("checked");
	  	});
	
	</script>
  </body>
</html>
