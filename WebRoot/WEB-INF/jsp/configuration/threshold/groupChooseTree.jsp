<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<%@include file="/WEB-INF/jsp/common/common.html"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>选择分组2</title>
	</head>
	<style>
		body{
			background: #fff;
			font-size: 12px;
		}
		.table-top{
			height:24px;
			
		}
		
		.table-center{
			width:100%;
			margin-top:10px;
		}
		.tabcenter{
			background: #fff;
			overflow: hidden;
		}
		.panel{
			padding: 0;
			margin-top: 10px;
		}
		.panel-body {
			height: 200px;
			overflow:auto;
		}
		.panel-default,.btn-default {
		    border-color: #a2a7a6;
		}
		
		#leftTree{
			 width:100%;
			 border-radius:3px;  
			 /* height:300px;  */
			 overflow: auto;
			 border:0!important;
		}
		.btn-group-vertical p{
			margin-bottom:5px
		}
		.center-bot{padding:0 10px}
		.btn-bottom{width:100%;text-align:center;}
		.row{margin-bottom:10px;margin-right:0; margin-left:0;}
		.form-control {height:28px;padding: 2px 10px;}
		 .btn-default{height:28px;font-size:12px;margin:0}
		 .for-label{
				 float:left;
				 width:110px;
				 height: 28px;
			    line-height: 28px;
			    margin-bottom: 0;
		    }
		 /* .col-xs-5{padding:0;width:310px;} */
		  .col-xs-2{margin-top:13%;}
		 .col-xs-9{margin:0;padding:0;width:200px;}
		 .big-begin{width:100%;padding-top:10px;margin:0 auto;}
		 #remove,#remove_all{background:#fff;color:#3997f4;border: 1px #399ef4 solid;}
		 #leftLi li {
		 	margin-top: 3px;
		 }
		 .editinput, .dblinput {
		 }
		 .editinput input, .addinput input[type='text'] {
		 	width: 40%;
		 }
		 .addbtn {
		 	margin-left: 15px;
		 	width: 82%;
		 	border: 1px dashed #aaa;
		 	text-align: center;
		 	padding: 3px;
		 	color: #aaa;
		 }
	</style>
  <body>
	<div class="big-begin">
		<input type="hidden" id="deviceTypeId" name="deviceTypeId" value="${deviceTypeId }"/>
		<input type="hidden" id="ids" name="ids" value="${ids }"/>
		<div class="table-center">
			<div class="tabcenter">
				<div class="center-bot">
				  <div class="row">
			       	 	<div class="panel panel-default">
						  <div class="panel-body">
						  	<ul id="leftLi" class="">
						  		<div class="addbtn"><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;新增告警分组</div>
						  		<li value="">
						  				<span class="addinput" style="display: none;">
						  					<input type="radio" value="" style="border: 0 !important;"/>
						  					<input class="nameinput" type="text" name="name" placeholder="名称" data-rule-required="true"/>
						  					<span class="glyphicon glyphicon-trash" style="display:inline-block;color:red; float:right;"></span>
						  				</span>
						  		</li>
						  		<c:forEach items="${groupList }" var="item">
						  			<li value="${item.groupId }" data-itemname="${item.name }">
						  				<input type="radio" value="${item.groupId }" name="groupradio" style="margin-top:0;border: 0 !important;"/>
						  				<span class="dblinput">
							  				<span style="display:inline-block;line-height:20px ">${item.name }</span>
							  				<span class="glyphicon glyphicon-trash remove" style="display:none;color:red; float:right; "></span>
						  				</span>
						  				<span class="editinput" style="display:none;">
						  					<input class="nameinput" type="text" value="${item.name }"/>
						  				</span>
						  			</li>
						  		</c:forEach>
						  	</ul>
						  </div>
						</div>
				  </div>
				  <div class="btn-bottom">
					  <button onclick="saveData()" type="button" class="save">确定</button>
					  <button type="button" onclick="laycls(0);" class="cancel">取消</button>
				  </div>
				</div>
			</div>
	</div>
  </div>	
</body>
</html>
<script type="text/javascript">
	layui.use('layer', function() {
		layer = layui.layer;
	});
	var page_sign = 0;//判定是否刷新列表
	
	$(function(){
		$("#checLeftAll").change(function() { 
			var ifcheck = $("#checLeftAll").prop("checekd");
			if(ifcheck){
				console.log(ifcheck)
				$("#leftLi li").each(function(){
					var id = $(this).val();
					if(id){//过滤无监控项id的li
						$(this).children("input:first").prop("checked",true);
					}
				});
			}
		});
		
		//给左侧搜索框绑定enter搜索事件
		var $inp = $('#keyword');
		$inp.bind('keydown', function (e) {
			var key = e.which;
			if(key == 13) {
				filter();
			}
		});
		
		function filter(){
			
		};
		
	});
	//计算右侧选中个数，公用方法
	function selectedNum(){
		var selectedOptions = $("#selectedObj option");
		$("#selectedNum").html(selectedOptions.length);
	}
	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		var typeId = $("#deviceTypeId").val();
		parent.typeId = typeId;
		console.log(typeId);
		parent.layer.close(index); //再执行关闭 
    }
	
	//保存数据
    function saveData(){
		var deviceTypeId = $("#deviceTypeId").val();
		var ids = $("#ids").val();
    	var groupId;
    	//获取被选中的li
		$("#leftLi li").each(function(){
			var id = $(this).val();
			var flag = $(this).children("input:first").prop("checked");
			if(id && flag){//过滤无监控项id的li
				groupId = id;
			}
		});
		if(!groupId){
			layer.alert("请选择分组");
			return;
		}
    	
        //存入到后台
        var url = '${sys_ctx}/deviceProperty/updateDeviceTypeAndItem.action';
        layer.confirm('确定要保存该信息吗？', {btn: ['确定', '取消'],icon: 3 }, function(index, layero){//确定按钮回执函数
        	$.post(url,{'typeId':deviceTypeId,'ids':ids, 'groupId':groupId},
  					function(data){
		  				if(data.success=="true"){
		  					parent.layer.close(index);  // 关闭layer
	  					} else {
	  						layer.alert(data.msg, {title:'提示信息',icon: 5});
	  					}
  					},'json');
  		}, function(index){//取消按钮回执函数
  		});
   		//parent.layer.close(index);
            
    }
    /*----------------------------右边搜索-----------------------------------  */
    // dbl事件
    $("#leftLi").on('dblclick', '.dblinput', function (event) {
    	var event = event || window.event;
    	var target = $(event.currentTarget);
    	target.hide();
    	target.siblings('.editinput').show();
    	target.siblings('.editinput').children()[0].focus();
    });
     $("#leftLi").on('mouseover', 'li', function (event) {
    	var event = event || window.event;
    	var target = $(event.currentTarget);
    	target.find('.remove').show();
    }).on('mouseout', 'li', function (event) {
    	var event = event || window.event;
    	var target = $(event.currentTarget);
    	target.find('.remove').hide();
    });
    
    
    //编辑监控项
    $("#leftLi").on('blur', '.editinput .nameinput', function (event) {
    	var event = event || window.event;
    	var target = $(event.currentTarget).parent();
    	$(target.children()[1]).focus();
    	var name = $(target.children()[0]).val();
    	if (name == '') {
	    	target.hide();
	    	target.siblings('.dblinput').show();
    		return;
    	}
    	//保存修改的监控项
    	var groupId = $(event.currentTarget).parents("li").attr("value");
    	$.ajax({
			async:false,
			url:'${sys_ctx}/group/modifyGroup.action',
			type:"POST",
			cache:false,
			data:{'groupId':groupId,'name':name},
			dataType:'json',
			success: function (data) {
				if(data.success=="true"){
					$(target.siblings('.dblinput').children()[0]).text(name);
					target.hide();
	    			target.siblings('.dblinput').show();
			    	layer.alert("修改成功");
				} else {
					layer.msg('删除监控项失败',{time: 2000, icon:5});
				}
			},
			error: function(xhr){
				isPass = false;
			}
		});
    });
    
    // 点击新增按钮，弹出新增框
    $(".addbtn").on('click', function () {
    	$(".addinput").show();
    	$(".addinput").children()[1].focus();
    })
    //删除监控项
    $("#leftLi").on('click', '.remove', function (event) {
	    	var event = event || window.event;
	    	var groupId = $(event.currentTarget).parents("li").attr("value");
	    	layer.confirm('确定要删除该信息吗？', {btn: ['确定', '取消'],icon: 3 }, function(index, layero){//确定按钮回执函数
		    	$.ajax({
					async:false,
					url:'${sys_ctx}/group/deleteGroup.action',
					type:"POST",
					cache:false,
					data:{'groupId':groupId},
					dataType:'json',
					success: function (data) {
						if(data.success=="true"){
					    	//前台删除监控项li
					    	$(event.currentTarget).parents("li").remove();
					    	layer.close(index);
						} else {
							layer.msg('删除监控项失败',{time: 2000, icon:5});
						}
					},
					error: function(xhr){
						
					}
				});
		  	});
    });
    //鼠标离开时新增监控项
    $(".addinput .nameinput").on('blur',  function (event) {
    	var event = event || window.event;
    	var target = $(event.currentTarget).parent();
    	var name = $(target.children()[1]).val();
    	if(name == ''){
    		$(".addinput").hide();
    		return;//不写名字直接返回
    	}
    	//执行后台操作
    	$.ajax({
			async:false,
			url:'${sys_ctx}/group/addGroup.action',
			type:"POST",
			cache:false,
			data:{'name':name},
			dataType:'json',
			success: function (data) {
				if(data.success=="true"){
					var groupId = data.msg;//回调获取所增加监控项的id主键
			    	// 新增内容ajax回调
			    	var content = '<li value=" '+ groupId +' "> <input type="radio" name="groupradio" value="' + groupId + '"/><span class="dblinput"> <span style="display:inline-block; ">' + name
			    					 + '</span><span class="glyphicon glyphicon-trash remove" style="display:none;color:red; float:right; "></span>'
			    					 + '</span> <span class="editinput" style="display:none;">'
			    					 + '<input class="nameinput" type="text" value="' + name + '"/>'
			    					 + '</span>'
			    					 + '</li>';
			    	target.parent().after(content);
			    	$(target.children()[1]).val('');
			    	$(".addinput").hide();
			    	/* target.siblings('.dblinput').show(); */
				} else {
					layer.msg('添加监控项失败',{time: 2000, icon:5});
				}
			},
			error: function(xhr){
				isPass = false;
			}
		});
    	$(target.children()[2]).focus();
    });
</script>
