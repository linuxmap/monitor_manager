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
		<title>选择监控项</title>
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
		
		#selectedObj,#leftTree{
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
		 input{
		 	margin:0 !important;
		 }
	</style>
	<!--[if lt IE 9]>
		<style>
		input{
			border:0 !important;
		}
		</style>
	<![endif]-->
  <body>
	<div class="big-begin">
		<input type="hidden" id="deviceTypeId" name="deviceTypeId" value="${deviceTypeId }"/>
		<div class="table-center">
			<div class="tabcenter">
				<div class="center-bot">
				  <div class="row">
				  	<div class="col-xs-5">
				  		<div class="row">
						    <div class="for-label">请选监控项&nbsp;共<span id="leftNum" style="color:red">${fn:length(deviceItemList) }</span>个</div>
							<div class="input-group col-xs-12">
							      <input id="keyword" type="text" class="form-control" placeholder="输入监控项名称" />
							      	<span class="input-group-btn">
							        <button data-method="search-btn" class="btn btn-default left-btn" type="button">搜索</button>
							      </span>
							 </div>
						  </div>
			       	 	<div><input id="checLeftAll" type="checkbox" onclick="checLeftAll(this)"/>可选监控项 &nbsp;<input type="button" class="btn btn-primary btn-xs" onclick="deleteBatchItem()" value="批量删除监控项"/></div>
			       	 	<div class="panel panel-default">
						  <div class="panel-body">
						  		<div class="addbtn"><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;新增监控项</div>
						  		<li value="">
						  				<span class="addinput" style="display: none;">
						  					<input type="checkbox" value=""/>
						  					<input class="nameinput" type="text" name="name" placeholder="名称" data-rule-required="true"/>
						  					<input class="uintinput" type="text" name="unit" placeholder="单位" data-rule-required="true"/>
						  					<span class="glyphicon glyphicon-trash" style="display:inline-block;color:red; float:right;"></span>
						  				</span>
						  		</li>
						  	<ul id="leftLi" class="">
						  		<c:forEach items="${deviceItemList }" var="item">
						  			<li value="${item.id }" data-itemname="${item.name }">
						  				<input type="checkbox" value="${item.id }"/>
						  				<span class="dblinput">
							  				<span style="display:inline-block; ">${item.name }</span>
							  				<span style="display:inline-block; ">${item.unit }</span>
							  				<span class="glyphicon glyphicon-trash remove" style="display:none;color:red; float:right; "></span>
						  				</span>
						  				<span class="editinput" style="display:none;">
						  					<input class="nameinput" type="text" value="${item.name }"/>
						  					<input class="uintinput" type="text" value="${item.unit }"/>
						  				</span>
						  			</li>
						  		</c:forEach>
						  	</ul>
						  </div>
						</div>
					    
			         </div>
			         <div class="col-xs-2">
						<div class="panel panel-default" style="border:0; padding-top:50px;">
						  <div class="" style="padding:0;text-align:center">
						  	<div  class="btn-group-vertical" role="group" aria-label="">
								<p><a class="btn btn-primary btn-xs btn-block" role="button" href="javascript:;" id="addRight">添加 </a></p>
							    <p><a class="btn btn-primary btn-xs btn-block" role="button" href="javascript:;" id="add_all">添加所有 </a></p>
							    <p><a class="btn btn-primary btn-xs btn-block" role="button" href="javascript:;" id="removeLeft">删除 </a></p>
							    <p><a class="btn btn-primary btn-xs btn-block" role="button" href="javascript:;" id="remove_all">删除所有 </a></p>
							</div>
						  </div>
						</div>
					  </div>
					
					  <div class="col-xs-5">
						<div class="row">
							<div class="for-label">已选监控项&nbsp;共<span id="selectedNum" style="color:red"></span>个</div>
							<div class="input-group col-xs-12">
						     <input id="" type="text" class="form-control" placeholder="输入监控项名称" />
							      	<span class="input-group-btn">
							        <button data-method="search-btn" class="btn btn-default" type="button">搜索</button>
							      </span>
						     </div>
						</div>
						<div><input id="checRightAll" type="checkbox" onclick="checRightAll(this)"/>已选监控项</div>
						<div class="panel panel-default">
						  <div class="panel-body">
						     <ul  multiple="multiple" id="selectedObj"></ul>
						  </div>
						</div>
					  </div>
				  </div>
			
				  <input type="hidden" name="meetingRoomIds" id="meetingRoomIds" />
				  <div class="btn-bottom">
				  	  <button onclick="saveGroup()" type="button" class="save">添加到分组</button>
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
	
  	//左边全选
	function checLeftAll(obj){
	   var ifcheck = obj.checked;
		$("#leftLi li").each(function(){
			var id = $(this).val();
			if(id){//过滤无监控项id的li
				$(this).children("input:first").prop("checked",ifcheck);
			}
		});
	}
	
	//全选右边
	function checRightAll(obj){
		var ifcheck = obj.checked;
		$("#selectedObj li").each(function(){
			var id = $(this).val();
			if(id){//过滤无监控项id的li
				$(this).children("input:first").prop("checked",ifcheck);
			}
		});
	}
	
	//批量删除监控项
	function deleteBatchItem(){
		var itemArr = [];
		//获取被选中的li
		$("#leftLi li").each(function(){
			var id = $(this).val();
			var flag = $(this).children("input:first").prop("checked");
			if(id && flag){//获取被选中的监控项
				itemArr.push(id);
			}
		});
		if(itemArr.length == 0){
			layer.msg("请先选择监控项",{time: 2000, icon:6});
			return;
		}
		layer.confirm('确定要删除选中项吗？', {btn: ['确定', '取消'],icon: 3 }, function(index, layero){//确定按钮回执函数
	    	$.ajax({
				async:false,
				url:'${sys_ctx}/deviceItem/deleteBatchDeviceItem.action',
				type:"POST",
				cache:false,
				data:{'itemArr':itemArr},
				dataType:'json',
				success: function (data) {
					if(data.success=="true"){
						$("#leftLi li").each(function(){
							var cId = $(this).val();
							if($.inArray(cId, itemArr) != -1){
								$(this).remove();
							}
						});
				    	selectedRightNum();
						selectedLeftNum();
						layer.close(index);
					} else {
						layer.msg('删除监控项失败',{time: 2000, icon:5});
					}
				},
				error: function(xhr){
					
				}
			});
	  	});
	}
	
	$(function(){
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
		
		//给右侧搜索框绑定enter搜索事件
		var $kwinp = $('#kw');
		$kwinp.bind('keydown', function (e) {
			var key = e.which;
			if(key == 13) {
				filterRight();
			}
		});
		function filterRight(){
			var kw = $("#kw").val();//获取input值并去掉前后空格
		    var selects = $("#selectedObj option");
		    $.each(selects,function(){
		    	var text = $(this).text();
		    	if (text.indexOf(kw) != -1) {
		    		$(this).css({"background":"blue"});
		        }else{
		        	$(this).css({"background":"#fff"});
		        }
		  	 });
		}
		
		//移到右边
		$('#addRight').click(function(){
			//获取被选中的li
			$("#leftLi li").each(function(){
				var id = $(this).val();
				var flag = $(this).children("input:first").prop("checked");
				if(id && flag){//过滤无监控项id的li
					//获取input对象的id name即可
					var name = $(this).attr("data-itemname");
					var alreadyIn = false;
					// 过滤右侧已有的名字
					var rightliList = $("#selectedObj .dblinput").find("span");
					for(var i = 0; i < rightliList.length; i ++) {
						var _name = $(rightliList[i]).text();
						if(_name == name) {
							alreadyIn = true;
							continue;
						}
					}
					//追加
					if(!alreadyIn) {
						var newLi = '<li value=" '+ id +' "data-itemname=" '+ name +' "><input type="checkbox" value="' + id + '"/><span class="dblinput"> <span style="display:inline-block; ">' + name
				    					 + '</span>'
				    					 + '</span>'
				    					 + '</li>';
						if ($("#selectedObj li[value='" + id + "']").length == 0) {//错误
							$("#selectedObj").append(newLi);
						}
						$(this).remove();//左边清除
					}
				}
			});
			selectedRightNum();
			selectedLeftNum();
		});
		
		//移到左边
		$('#removeLeft').click(function(){
			$("#selectedObj li").each(function(){
				var id = $(this).val();
				var flag = $(this).children("input:first").prop("checked");
				if(id && flag){//过滤无监控项id的li
					$(this).remove();
					$(this).children("input:first").prop("checked",false);
					$("#leftLi").append($(this));//prepend  右边需要删除该li
				}
			});
			
			selectedRightNum();
			selectedLeftNum();
		});
		
		//全部移到右边
		$('#add_all').click(function(){
			$("#leftLi li").each(function(){
				var id = $(this).val();
				$(this).children("input:first").prop("checked",true);
				if(id){//过滤无监控项id的li
					//获取input对象的id name即可
					var name = $(this).attr("data-itemname");
					//追加
					var newLi = '<li value=" '+ id +'"  data-itemname=" '+ name +' "><input type="checkbox" value="' + id + '"/>' + name
				    					 + '</li>';
					if ($("#selectedObj li[value='" + id + "']").length == 0) {//错误
						$("#selectedObj").append(newLi);
					}
				}
			});
			//左边需要清空
			$("#leftLi").empty();
			
			selectedRightNum();
			selectedLeftNum();
		});
		
		//清空所有选项
		$('#remove_all').click(function(){
			$("#checRightAll").prop("checked",false)
			
			$("#selectedObj li").each(function(){
				var id = $(this).val();
				$(this).children("input:first").prop("checked",true);
				if(id){//过滤无监控项id的li
					//获取input对象的id name即可
					var name = $(this).attr("data-itemname");
					//追加
					var newLi = '<li value=" '+ id +'"  data-itemname=" '+ name +' "><input type="checkbox" value="' + id + '"/>' + name
				    					 + '</li>';
					if ($("#leftLi li[value='" + id + "']").length == 0) {//错误
						$("#leftLi").append(newLi);
					}
				}
			});
			//右边需要清空
			$("#selectedObj").empty();
			
			selectedRightNum();
			selectedLeftNum();
		});
	});
	//计算右侧选中个数，公用方法
	function selectedRightNum(){
		var selectedOptions = $("#selectedObj li");
		$("#selectedNum").html(selectedOptions.length);
	}
	//计算右侧选中个数，公用方法
	function selectedLeftNum(){
		var selectedOptions = $("#leftLi li");
		$("#leftNum").html(selectedOptions.length);
	}
	
	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		var typeId = $("#deviceTypeId").val();
		parent.typeId = typeId;
		parent.layer.close(index); //再执行关闭 
    }
    
    //设置分组，进入在监控项中的分组功能
    function saveGroup(){
    	var deviceTypeId = $("#deviceTypeId").val();
    	var idArr=[];
    	if($("#selectedObj li").length == 0 || !deviceTypeId){
    		layer.msg("请选择监控项再分组",{time: 2000, icon:6});
    		return;
    	}
    	$("#selectedObj li").each(function () {
             var id = $(this).val(); //获取单个id
             idArr.push(id);
        });
    	var ids = idArr.join(",");
        var url = '${sys_ctx}/threshold/configGroupFromItem.action?deviceTypeId='+deviceTypeId+'&ids='+ids;
        window.location.href = url;
    }
	
	//保存数据
    function saveData(){
		var deviceTypeId = $("#deviceTypeId").val();
    	var idArr=[];
    	//var nameArr=[];
    	if($("#selectedObj li").length == 0 || !deviceTypeId){
    		layer.msg("请选择监控项",{time: 2000, icon:6});
    		return;
    	}
    	$("#selectedObj li").each(function () {
             var id = $(this).val(); //获取单个id
             //var name = $(this).text(); //获取名称
             idArr.push(id);
             //nameArr.push(name);
        });
    	var ids = idArr.join(",");
    	//var names = nameArr.join(",");
        //eval("parent.fillSelected(idArr,nameArr)");
        //存入到后台
        var url = '${sys_ctx}/deviceProperty/saveDeviceTypeAndItem.action';
        layer.confirm('确定要保存该信息吗？', {btn: ['确定', '取消'],icon: 3 }, function(index, layero){//确定按钮回执函数
  			$.ajax({
  				async:true,
  				url : url,
  				type : "POST",
  				cache : false,
  				data : {'typeId':deviceTypeId, 'ids':ids},
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
   		//parent.layer.close(index);
            
    }
	
    /*----------------------------右边搜索-----------------------------------  */
   /*  function getContent(){
	    var kw = $("#kw").val();//获取input值并去掉前后空格
	    var selects = $("#selectedObj option");
	    $.each(selects,function(){
	    	var text = $(this).text();
	    	if (text.indexOf(kw) != -1) {
	    		$(this).css({"background":"blue"});
	        }else{
	        	$(this).css({"background":"#fff"});
	        }
	  	 });
    } */
    //搜索事件
    $("button[data-method='search-btn']").on("click", function () {
    	var event = event || window.event;
    	var target = $(event.target);
    	var val = target.parent().siblings('input').val();
    	if(target.hasClass('left-btn')) {
    		var liList = $('#leftLi li');
    	} else {
    		var liList = $('#selectedObj li');
    	}
    	for(var i = 0; i < liList.length; i ++) {
    		var _name = $(liList[i]).attr("data-itemname");
    		if(_name && _name.indexOf(val) == -1) {
    			$(liList[i]).hide();
    		} else {
    			$(liList[i]).show();
    		}
    	}
    });
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
    });
    $("#leftLi").on('blur', '.editinput .uintinput', function (event) {
    	var event = event || window.event;
    	var target = $(event.currentTarget).parent();
    	var name = $(target.children()[0]).val();
    	var unit = $(target.children()[1]).val();
    	if (name == '' && unit == '') {
	    	target.hide();
	    	target.siblings('.dblinput').show();
    		return;
    	}else if (name == '') {
    		$(target.children()[0]).focus();
    		return;
    	}
    	//保存修改的监控项
    	var itemId = $(event.currentTarget).parents("li").attr("value");
    	$.ajax({
			async:false,
			url:'${sys_ctx}/deviceItem/modifyDeviceItemInConfig.action',
			type:"POST",
			cache:false,
			data:{'id':itemId,'name':name,'unit':unit},
			dataType:'json',
			success: function (data) {
				if(data.success=="true"){
					$(target.siblings('.dblinput').children()[0]).text(name);
					$(target.siblings('.dblinput').children()[1]).text(unit);
					target.hide();
	    			target.siblings('.dblinput').show();
	    			layer.msg("修改成功",{time: 2000, icon:6});
				} else {
					layer.msg('修改监控项失败',{time: 2000, icon:5});
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
	    	var itemId = $(event.currentTarget).parents("li").attr("value");
	    	layer.confirm('确定要删除该信息吗？', {btn: ['确定', '取消'],icon: 3 }, function(index, layero){//确定按钮回执函数
		    	$.ajax({
					async:false,
					url:'${sys_ctx}/deviceItem/deleteDeviceItemInConfig.action',
					type:"POST",
					cache:false,
					data:{'id':itemId},
					dataType:'json',
					success: function (data) {
						if(data.success=="true"){
					    	//前台删除监控项li
					    	$(event.currentTarget).parents("li").remove();
					    	selectedRightNum();
							selectedLeftNum();
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
    		return;
    	}
    	$(target.children()[2]).focus();
    });
    $(".addinput .uintinput").on('blur',  function (event) {
    	var event = event || window.event;
    	var target = $(event.currentTarget).parent();
    	var name = $(target.children()[1]).val();
    	var unit = $(target.children()[2]).val();
    	if (name == '' && unit == '') {
    		$(".addinput").hide();
    		return;
    	}else if (name == '') {
    		$(target.children()[1]).focus();
    		return;
    	}
    	$.ajax({
			async:false,
			url:'${sys_ctx}/deviceItem/addDeviceItemInConfig.action',
			type:"POST",
			cache:false,
			data:{'name':name,'unit':unit},
			dataType:'json',
			success: function (data) {
				if(data.success=="true"){
					var itemId = data.msg;//回调获取所增加监控项的id主键
			    	// 新增内容ajax回调
			    	var content = '<li value=" '+ itemId +' "> <input type="checkbox" value="' + itemId + '"/><span class="dblinput"> <span style="display:inline-block; ">' + name
			    					 + '</span> <span style="display:inline-block; ">' + unit + '</span><span class="glyphicon glyphicon-trash remove" style="display:none;color:red; float:right; "></span>'
			    					 + '</span> <span class="editinput" style="display:none;">'
			    					 + '<input class="nameinput" type="text" value="' + name + '"/>'
			    					 + '<input class="uintinput" type="text" value="' + unit + '"/>'
			    					 + '</span>'
			    					 + '</li>';
			    	//target.parent().after(content);
			    	$("#leftLi").prepend(content);
			    	$(target.children()[1]).val('');
			    	$(target.children()[2]).val('');
			    	$(".addinput").hide();
			    	selectedRightNum();
					selectedLeftNum();
			    	/* target.siblings('.dblinput').show(); */
				} else {
					layer.msg('添加监控项失败',{time: 2000, icon:5});
				}
			},
			error: function(xhr){
				isPass = false;
			}
		});
   });
   
</script>
