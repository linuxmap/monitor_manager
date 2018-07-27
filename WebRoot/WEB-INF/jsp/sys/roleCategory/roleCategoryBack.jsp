<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>管理端2</title>
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
		#roleCategories{
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
  	<!--  -->
    <div class="panel panel-default f_right" >
    		<!-- 角色id -->
    		<input type="hidden" id="categoryId" name="" />
			<button type="button" class="default-one-btn" id="save">保存</button>
			<button type="button" class="default-one-btn-top" id="reset">重置</button>
		</div>
	<div class="layui-collapse right-user" style="height:97%">
		<c:forEach items="${roleMenuList}" var="menu1">
			<div class="layui-colla-item">
				<h2 class="layui-colla-title">
					<lable class="label-1"> <input type="checkbox"  style="border:0 !important" id="id_${menu1.id }" data-id="${menu1.id }" >${menu1.funtName } </lable>
				</h2>
				<div class="layui-colla-content layui-show" id="list${menu1.id }">
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
										 <input type="checkbox" id="id_${menu2.id }"  style="border:0 !important"   data-id="${menu2.id }"><label>${menu2.funtName }</label>
									</p>
								</td>
								<td class="Privilege-p" id="list${menu2.id}" >
									<c:forEach items="${menu2.subMenuList}" var="menu3">
										 <p><input type="checkbox" style="border:0 !important" id="id_${menu3.id}" data-id="${menu3.id }" /><label>${menu3.funtName }</label></p>
									</c:forEach>
								</td>
							</tr>
						</c:if>
						<c:if test="${ empty menu2.subMenuList}">
							<tr>
								<td class="Privilege-p">
									<p>
									 	<input type="checkbox" style="border:0 !important" id="id_${menu2.id}" data-id="${menu2.id}" ><label>${menu2.funtName }&nbsp;&nbsp;</label>
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
</body>
	<script type="text/javascript">
	var sysAdminId = 5;
	var roleCategoryId;
	layui.use('layer', function(){ 
			layer = layui.layer; 
		}); 
		//保存
	    $("#save").on("click",function() {
		    $('#roleCategories li', window.parent.document).each(function(){
	    		if($(this).hasClass("active")){
	    			var choosedLi = $(this);
				   	roleCategoryId = choosedLi.attr("data-categoryid");
	    		}
	    	})
	    	if (!roleCategoryId) {
	    		layer.alert("请选择角色类别");
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
				url: '<%=basePath%>roleCategory/updateDoubleFuntByCategoryId.action',
		    	data: {
		    		'funtIds':list,
		    		'roleCategoryId':roleCategoryId,
		    		'funtGroup':1
		    		},
				dataType:'json',
				cache: false,
				success: function(data){
					if(data.success){
						layer.msg(data.msg,{time: 2000, icon:6});
					}else{
						var err = data.msg;
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
	  	  
	  	    	// 全选
/* 	  	function checkAll(obj){
	
	 		var f1 = $(obj).data("id");
	 		console.log("1="+f1)
		    if(f1 == "2"){
		   		var All  = $("#list2").find("input");
		   		// console.log(All);
		   		 for  (var  i=0;  i<All.length;  i++){  
		         	All[i].checked  =  obj.checked;  
			     }  
		    }else if(f1==440){
		   		 var  ccc  = $("#list440").find("input");
		   		  for  (var  i=0;  i<ccc.length;  i++){  
		         	ccc[i].checked  =  obj.checked;  
			     }  
		    }else  if(f1==475){
		   		 var  ddd  = $("#list475").find("input");
		   		  for  (var  i=0;  i<ddd.length;  i++){  
		         	ddd[i].checked  =  obj.checked;  
			     }  
		    }
	  }; */
	  
	 // var check_list = ${menu2.id };
	  $("#id_2").click(function(){
	 // alert(111);
	 // 	debugger;
	  		var All  = $("#list2").find("input");
		   		// console.log(All);
		   for  (var  i=0;  i<All.length;  i++){  
		      All[i].checked  =  this.checked;  
			}  
	  });
	   $("#id_440").click(function(){
	   //alert(222);
	  		var ccc  = $("#list440").find("input");
		   		// console.log(All);
		   for  (var  i=0;  i<ccc.length;  i++){  
		      ccc[i].checked  =  this.checked;  
			}  
	  });
	   $("#id_475").click(function(){
	  		var aaa  = $("#list475").find("input");
		   		// console.log(All);
		   for  (var  i=0;  i<aaa.length;  i++){  
		      aaa[i].checked  =  this.checked;  
			}  
	  });
	  
	  
	   $("#id_3").click(function(){
	   $("#id_2").prop("checked",true);
	  		var aaa  = $("#list3").find("input");
		   		// console.log(All);
		   for  (var  i=0;  i<aaa.length;  i++){  
		      aaa[i].checked  =  this.checked;  
			}  
	  });
	  
	   $("#id_4").click(function(){
	  		var aaa  = $("#list4").find("input");
	  		$("#id_2").prop("checked",true);
		   		// console.log(All);
		   for  (var  i=0;  i<aaa.length;  i++){  
		      aaa[i].checked  =  this.checked;  
			}  
	  });
	  
	   $("#id_443").click(function(){
	   		$("#id_440").prop("checked",true);
	  		var aaa  = $("#list443").find("input");
		   		// console.log(All);
		   for  (var  i=0;  i<aaa.length;  i++){  
		      aaa[i].checked  =  this.checked;  
			}  
	  });
	  
	  $("#id_452").click(function(){
	  		$("#id_440").prop("checked",true);
	  		var aaa  = $("#list452").find("input");
		   		// console.log(All);
		   for  (var  i=0;  i<aaa.length;  i++){  
		      aaa[i].checked  =  this.checked;  
			}  
	  });
	  
	  $("#id_476").click(function(){
	  		$("#id_475").prop("checked",true);
	  		var aaa  = $("#list476").find("input");
		   		// console.log(All);
		   for  (var  i=0;  i<aaa.length;  i++){  
		      aaa[i].checked  =  this.checked;  
			}  
	  });
	  
	  $("#id_477").click(function(){
	  		$("#id_475").prop("checked",true);
	  		var aaa  = $("#list477").find("input");
		   		// console.log(All);
		   for  (var  i=0;  i<aaa.length;  i++){  
		      aaa[i].checked  =  this.checked;  
			}  
	  });
	  //系统管理
	   $("#list3").find("input").click(function(){
	  		$("#id_3").prop("checked",true);
	  		$("#id_2").prop("checked",true);
	  });
	  
	  $("#list4").find("input").click(function(){
	  		$("#id_4").prop("checked",true);
	  		$("#id_2").prop("checked",true);
	  });
	  
	   $("#id_5").click(function(){
	  		$("#id_2").prop("checked",true);
	  });
	  $("#id_6").click(function(){
	  		$("#id_2").prop("checked",true);
	  });
	  
	  $("#id_505").click(function(){
	  		$("#id_2").prop("checked",true);
	  });
	  $("#id_508").click(function(){
	  		$("#id_2").prop("checked",true);
	  });
	  
	  
	  
	  //配置管理
	  
	  $("#list443").find("input").click(function(){
	  		$("#id_443").prop("checked",true);
	  		$("#id_440").prop("checked",true);
	  });
	  
	  
	  
	  $("#list452").find("input").click(function(){
	  		$("#id_452").prop("checked",true);
	  		$("#id_440").prop("checked",true);
	  });
	  
	  $("#id_441").click(function(){
	  		$("#id_440").prop("checked",true);
	  });
	  
	   $("#id_509").click(function(){
	  		$("#id_440").prop("checked",true);
	  });
	  
	  
	  
	  //资源管理
	  
	   $("#list476").find("input").click(function(){
	  		$("#id_476").prop("checked",true);
	  		$("#id_475").prop("checked",true);
	  });
	  
	   $("#list477").find("input").click(function(){
	  		$("#id_477").prop("checked",true);
	  		$("#id_475").prop("checked",true);
	  });
	  
	 
	  
	</script>
</html>
