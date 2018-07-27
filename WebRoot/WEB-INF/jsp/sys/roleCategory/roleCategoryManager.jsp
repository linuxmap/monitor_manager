<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<base href="<%=basePath%>">
<%@include file="/WEB-INF/jsp/common/common.html"%>
<!-- <link rel="stylesheet" href="jquery-ui/jquery-ui.css">--> 
<title>权限管理5</title>
 	<!--[if lt IE 9]>
		<style>
		.jiedian{
				height:40px;
				width:100%;
				background-image:url(${sys_ctx}/css/jianbian.png);
				background-repeat: no-repeat;
			}
		</style>
	<![endif]-->
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
		.right-user{overflow-x:hidden; height:100%;padding-bottom:50px;overflow-y:hidden;}
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
			width:20px;
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
		.layui-layer-btn1{
		    margin-right: 6px;
		    padding: 5px 15px;
		    color: #3997f4;
		    border-radius: 3px;
		    background: #fff;
		    font-size: 12px!important;
		    border: 1px solid #3997f4;
		}
		/* tab切换 */
		.top ul {
		    width: 100%;
		    height: 42px;
		    border: 1px solid #dedede;
		    background: #f9f9f9;
		}
		.top ul li a {
		    display: block;
		    float: left;
		    padding: 0 20px;
		    height: 41px;
		    line-height: 40px;
		    color: #000;
		    font-weight: 500;
		    font-size: 14px;
		}
		.top ul li {
		    float: left;
		    border-right: 1px solid #dedede;
		}
		.action {
		    height: 41px!important;
		    border-bottom: 0;
		    background: #039dff;
		    color: #555!important;
		    border-top: 3px solid #3997f4;
		    font-weight: 600!important;
		    background: #fff;
		}
		.wh{
			width:28px;
			height:28px;
			border:none;
			margin-left:10px;
			margin-top:10px;
		}
		.bg-1{
			background: url(./img/new_nor.png);
		}
		.bg-1:hover{
			background: url(./img/new_sel.png);
		}
		.bg-2{
			background: url(./img/edit_nor.png);
		}
		.bg-2:hover{
			background: url(./img/edit_sel.png);
		}
		.bg-3{
			background: url(./img/copy_nor.png);
		}
		.bg-3:hover{
			background: url(./img/copy_sel.png);
		}
		.bg-4{
			background: url(./img/delete_nor.png);
		}
		.bg-4:hover{
			background: url(./img/delete_sel.png);
		}
		.jiedian {
		    margin-top: 2px;
		    padding: 10px;
		    background-image: -webkit-linear-gradient(to right, #dfeffe, #fff);
		    background-image: linear-gradient(to right, #dfeffe, #fff);
		}
		.jd-title {
		    height: 20px;
		    font-weight: 600px;
		    border-left: 4px #0077dd solid;
		    margin-left: 20px;
		    padding-left: 10px;
		}
		
		#id_box{
			width:100%;
			height:100%;
			/* IE滤镜 */
			filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#7F000000,endColorstr=#7F000000);
			position: absolute;
			z-index: 10000;
			/* CSS3 */
			background-color: rgba(0,0,0,.5);
		}
</style>
<script>
	$(function(){
		$("#roleCategories").height($(document).height()+"px");
	})
</script>
</head>

<body style="margin-top:10px">

	<div class="left-user">
	<div class="panel panel-default">
			<div class="jiedian">
				<div class="jd-title">角色类别管理</div>
			</div>
			<div class="btn-group">
					<button type="button" class="wh bg-1" title="新增" id="addRoleCategory"></button>
					<button type="button" class="wh bg-2" title="编辑" id="editRoleCategory"></button>
					<button type="button" class="wh bg-3" title="复制" id="copyRoleCategory"></button>
					<button type="button" class="wh bg-4" title="删除" id="deleteRoleCategory"></button>
			</div>
			<ul id="roleCategories">
				<c:forEach items="${roleCategoryList}" var="roleCategoty" varStatus="vs">
					<c:choose>
						<c:when test="${vs.index == 0}">
							<li class="list-group-item pointer active" data-categoryid="${roleCategoty.categoryId }" data-categoryname="${roleCategoty.name }" data-category-time="${roleCategoty.logouttime }">${roleCategoty.name }</li>
						</c:when>
						<c:otherwise>
							<li class="list-group-item pointer" data-categoryid="${roleCategoty.categoryId }" data-categoryname="${roleCategoty.name }" data-category-time="${roleCategoty.logouttime }">${roleCategoty.name }</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
	</div>
	
	<div class='right-user top' style="border-left:1px #ddd solid">
		<ul class="" id="roleType">
		  <li role="back"  class="action"><a id="toBackFrame">管理端</a></li>
		  <li role="client"><a id="toClientFrameTree">客户端</a></li>
		  <!-- <li role="range"><a id="toRoleRangeFrame">权限范围</a></li> -->
		</ul>
		<div id="id_box" style="display: none"></div>
		<iframe class=""  frameborder="no" border="0"  id="myFrame" name="myFrame" style="width:100%;height:100%;overflow-y:auto;" src="${sys_ctx}/roleCategory/toBackFrame.action" onload="changeFrameHeight()"></iframe>
	</div>
	
</body>
<script>
	//定义系统管理员角色在数据库中的id
	var sysAdminId = 5;
	//定义全局变量，标记当前的frame
	var currentFrame;
	var id;
	//注意：折叠面板 依赖 element 模块，否则无法进行功能性操作
 	layui.use('layer', function(){ 
		layer = layui.layer; 
	});
	layui.use(['element','layer'], function(){
	 	var element = layui.element();
	}); 
	// 调整右边栏高度
	function changeFrameHeight(){
	    var ifm= document.getElementById("myFrame"); 
	   	// ifm.height=document.documentElement.clientHeight;
	    ifm.height=document.documentElement.clientHeight - 150+"px";
	}
	// 点击切换Tab页签
	$('#roleType li').on('click', function(e) {
		e = e || window.event;
		var target = $(e.target);
   		id = target.attr('id');
   		currentFrame = id;
   		var url = '${sys_ctx}/roleCategory/' + id + '.action';
   		$("#roleCategories li").each(function(){
   			if($(this).hasClass("active")){
   				var choosedLi = $(this);
   				var dataCatgoryid = choosedLi.attr("data-categoryid");
   				//需求【系统管理员】的管理端和客户端功能权限都不可编辑，置灰。
   				/* if (dataCatgoryid == sysAdminId) {
   					//将子页面页面button置为不可编辑
   					$(window.frames["myFrame"].document).find("input[@type='check']").attr("disabled","disabled");
   				} else {
   					$(window.frames["myFrame"].document).find("input[@type='check']").attr("disabled","disabled");
   				} */
   				changeSrc(url,currentFrame,dataCatgoryid);
   			}else{
   				//layer.msg('请选择角色！',{time: 2000, icon:5});
   			}
   		});
	})
		function changeSrc(url,currentFrame,dataCatgoryid){
			document.getElementById("myFrame").src=url;
			setTimeout(function(){
				controlFrameClick(currentFrame,dataCatgoryid); 
			},1000)
				
		}
	 $(function() {
	 		$('#roleType li').each(function(){
	 			if($(this).hasClass("action")){
	 				id = $(".action a").attr('id');
			   		currentFrame = id;
			   		var url = '${sys_ctx}/roleCategory/' + id + '.action';
	 				$("#roleCategories li").each(function(){
			   			if($(this).hasClass("active")){
			   				var choosedLi = $(this);
			   				var dataRoleid = choosedLi.attr("data-categoryid");
			   				changeSrc(url,currentFrame,dataRoleid);
			   			}else{
			   				//layer.msg('请选择角色！',{time: 2000, icon:5});
			   			}
	 				})
	 			}
	 		})
	 		
	 	//判断首次加载超管理遮罩	
	 	if(sysAdminId==5){
	 	debugger;
	 		$("#id_box").show();
	 	}else{
	 		$("#id_box").hide();
	 	};
	 		
	    //第一级菜单可折叠，可全选，可全取消
	    $(".label-1").on("click",function(e){
	    	e = e||event;
	    	var target = $(e.target);
	    	if ("checkbox" == target.attr("type")) {
	    		if (target.is(":checked")) {
					$(this).siblings().find("input[type='checkbox']").prop("checked","checked");
					
	    		}else{
	    			target.removeAttr("checked");
	    			$(this).siblings().find("input[type='checkbox']").removeAttr("checked");
	    		}
	    	}else{
	    	}
	    	
	    });
	    
		//第二级菜单不可折叠，可全选，可全取消
		//第三级菜单可全选，可全取消
	    $(".panel-body").on("click",function(e){
	    	e = e||event;
	    	var target = $(e.target);
	    	if ("checkbox" == target.attr("type")) {
	    		if (target.is(":checked")) {
					target.parent().parent().siblings().find("input[type='checkbox']").prop("checked","checked");
	    		}else{
	    			target.removeAttr("checked");
	    			target.parent().parent().siblings().find("input[type='checkbox']").removeAttr("checked");
	    		}
	    	}else{
	    		return;
	    	}
	    });
	
	    });

		function refresh(){
			window.location.reload(true);
		}
		
	   //选中角色
	    $("#roleCategories").on("click",".list-group-item",function(e){
	    	debugger;
		    e = e||event;
	   		var target = $(e.target);
	   		target.addClass("active");
	   		target.siblings().removeClass("active");
	   		//根据选中的id，刷新右侧列表
	   		var categoryid = target.attr("data-categoryid");
	   		if (!categoryid) {
	   			layer.msg('未携带正确信息',{time: 2000, icon:6});
	   			return;
	   		}
	   		//给iframe中的id赋值
	   		$(window.frames["myFrame"].document).find("input[id='categoryId']").val(categoryid);
	   		//判断角色进入哪一个frame 权限范围
	   		controlFrameClick(currentFrame,categoryid);
	   		if(categoryid!=5){
	   			$("#id_box").hide();
			 	}else{
			 	$("#id_box").show();
			};
	   		
	   });
	   
	   //判断角色进入哪一个frame 权限范围
	   function controlFrameClick(currentFrame,categoryid){
	   		if("toRoleRangeFrame" == currentFrame){
		   		$.ajax({
					type: "POST",
					url: '<%=basePath%>role/getRangeByRoleId.action',
					//list即为选中的menuid
			    	data: {'roleId':categoryid},
					dataType:'json',
					cache: false,
					success: function(data){
						if(data.success){
							//获取frame的ztree树
							var tree = window.frames['myFrame'].treeObj;
							if (tree) {
								var nodes = tree.getNodes();//zTree.selectNode(node);
								tree.checkAllNodes(false);//取消所有的选中
								//请求数据成功，返回data.menulist
								var json = eval(data);
								//处理组织权限
								var orgIds = data.msg;
								if(orgIds){
									var orgArray = orgIds.split(",");
									for (var i=0;i<orgArray.length;i++){
										var orgId = orgArray[i];
										var node = tree.getNodeByParam("id",orgId);
										if (node) {
											tree.checkNode(node, true,false);//取消父子联动
											tree.updateNode(node);
										}
									}
								}
								//处理资产权限
								var assetIds = data.ower;
								if(assetIds){
									var assetArray = assetIds.split(",");
									for (var j=0;j<assetArray.length;j++){
										var uuid = assetArray[j];
										var anode = tree.getNodeByParam("id",uuid);
										if (anode) {
											tree.checkNode(anode, true,false);//取消父子联动
											tree.updateNode(anode);
										}
									}
								}
							}
						}else{
							var err = data.message;
							layer.msg("获取信息错误，错误信息："+err,{time: 2000, icon:5});
						 }
					},
					error: function(data){
						layer.msg("请检查网络！",{time: 2000, icon:5});
					}
				});
	   		} else if("toClientFrameTree" == currentFrame) {
				//客户端树形
				$.ajax({
					type: "POST",
					url: '<%=basePath%>roleCategory/getFuntByCategoryId.action',
					//list即为选中的menuid
			    	data: {'roleCategoryId':categoryid ,"funtGroup":2},
					dataType:'json',
					cache: false,
					success: function(data){
						if(data.success){
							//获取frame的ztree树
							var tree = window.frames['myFrame'].clientTreeObj;
							if (tree) {
								var nodes = tree.getNodes();//zTree.selectNode(node);
								tree.checkAllNodes(false);//取消所有的选中
								//请求数据成功，返回data.menulist
								var json = eval(data);
								//处理组织权限
								var funtIds = data.ower;
								if(funtIds){
									var funtArray = funtIds.split(",");
									for (var i=0;i<funtArray.length;i++){
										var funtId = funtArray[i];
										var node = tree.getNodeByParam("id",funtId);
										if (node) {
											tree.checkNode(node, true, false);//取消父子联动
											tree.updateNode(node);
										}
									}
								}
							}
						}else{
							var err = data.message;
							layer.msg("获取信息错误，错误信息："+err,{time: 2000, icon:5});
						 }
					},
					error: function(data){
						layer.msg("请检查网络！",{time: 2000, icon:5});
					}
				});
	   		} else {
	   			//默认管理端 使用else流程
		   		$.ajax({
					type: "POST",
					url: '<%=basePath%>roleCategory/getFuntByCategoryId.action',
					//list即为选中的menuid
			    	data: {'roleCategoryId':categoryid, "funtGroup":1},
					dataType:'json',
					cache: false,
					success: function(date){
						if(date.success){
							//请求数据成功，返回data.menulist
							var json = eval(date);
							var menuIds = date.ower;
							var funtIds = menuIds.split(",");
							$(window.frames["myFrame"].document).find("input[type='checkbox']").removeProp("checked");
							for (var i=0;i<funtIds.length;i++) {
								var item = funtIds[i];
								$(window.frames["myFrame"].document).find("input[type='checkbox'][data-id='"+item+"']").prop("checked","true");
							}
						}else{
							var err = data.message;
							layer.msg("获取信息错误，错误信息："+err,{time: 2000, icon:5});
						 }
					},
					error: function(data){
						layer.msg("请检查网络！",{time: 2000, icon:5});
					}
				});
	   		}
	   }
	   
	   //弹出框的select的js  参数1 ：弹出的下拉框的id 参数2：被选中的option的value
	   function getTimeoutSelect(logintimeSelectId,selected) {
	   		var selectArr = new Array();
	   		var timeValueArray = new Array(10,20,30,60,120,360,720,1440,-1);
	   		var timeNameArray = new Array('10分钟','20分钟','30分钟','1小时','2小时','6小时','12小时','24小时','不限期');
	   		selectArr.push('<select id="' + logintimeSelectId + '" data-rule-digits="true" style="width:150px;height:30px;padding-left:10px;border-radius:3px;">');
	   		var option;
	   		//console.log($.inArray(selected,timeValueArray));
	   		for (var i=0; i<timeValueArray.length; i++) {
	   			if (selected == timeValueArray[i]) {
					option = '<option selected="selected" value="' + timeValueArray[i] + '">' + timeNameArray[i] + '</option>';   			
	   			} else {
		   			option = '<option value="' + timeValueArray[i] + '">' + timeNameArray[i] + '</option>';
	   			}
	   			selectArr.push(option);
	   		}
	   		selectArr.push('</select>');
	   		return selectArr.join('');
	   }

		//添加角色
	   $("#addRoleCategory").on("click",function(){
	   		var timeoutAddSelect = getTimeoutSelect("newCategoryLoginPeriod",0);//新增时默认参数为0
	   		layer.open({
				title: ['新增', 'background-color:#d0e3f0; color:#888889;'],
				anim: 'scale',
				content: '<span style="margin-bottom:10px;display: block;width: 98px;float: left; line-height: 30px;">类型名称：</span>'+
						 '<input type="text" style="width:150px;height:30px;padding-left:10px;margin-bottom:10px;border-radius:3px;" id="newCategoryName">'+
						 '</br><span style="margin-bottom:10px;">限制登录时长：</span>'+
						  timeoutAddSelect,
				btn: ['确认','取消'],
				yes: function(index) {
					//点击确认后保存角色名称
					var name = $("#newCategoryName").val();
					var logouttime = $("#newCategoryLoginPeriod").val();
					//判断名字是否为空
					if(!name){
						layer.msg('名称不能为空！',{time: 2000, icon:5});
						return;
					}
					if(!logouttime || isNaN(logouttime)){
						layer.msg('请填写正确的格式！',{time: 2000, icon:5});
						return;
					}
					$.ajax({
						type: "POST",
						url: '<%=basePath%>roleCategory/addRoleCategory.action',
				    	data: {'name':name ,'logouttime':logouttime},
						dataType:'json',
						cache: false,
						success: function(data){
							if(data.success){
								layer.open({
											title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
											anim: 'scale',
											content: '保存成功！',
											btn: ['确认'],
											yes: function(index) {
												layer.close(index);
											},
										});
								//在网页上展示出来
								var html = "<li class='list-group-item pointer' data-categoryid='"+data.msg+"' data-category-time='"+data.ower+"'>" + name + "</li>";
								$("#roleCategories").append(html);
							}else{
								//data.result为false，保存失败
								var err = data.message;
								layer.open({
									title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
									anim: 'scale',
									content: '保存失败，错误信息为：' + err + '！',
									btn: ['确认'],
									yes: function(index) {
										layer.close(index);
									},
								});
							 }
						},
						error: function(data){
							//window.webkit.messageHandlers.loadSuccess.postMessage("error");
							layer.open({
								title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
								anim: 'scale',
								content: '请检查网络！',
								btn: ['确认'],
								yes: function(index) {
									layer.close(index);
								},
							});
						}
					
					});
					layer.close(index);
				},
				no: function(index){
					document.getElementById("newCategoryName").blur();
					layer.close(index);
				},
			});
	   });
	   
	   //进行编辑--更改角色类别名称
	   $("#editRoleCategory").on("click",function(){
	   		var pre_name = $("li.active").html();
	   		var selectRoleCategoryid = $("li.active").attr("data-categoryid");
	   		if (!selectRoleCategoryid) {
	   			layer.alert("请先选择角色类别");
	   			return;
	   		}
	   		var selectCategoryTime = $("li.active").attr("data-category-time");
	   		var timeoutEditSelect = getTimeoutSelect("editCategoryTime",selectCategoryTime);//编辑时的下拉框
	   		layer.open({
				title: ['编辑', 'background-color:#d0e3f0; color:#888889;'],
				anim: 'scale',
				content: '<span style="display: block;width: 112px;float: left; line-height: 30px;">类型名称:</span>'+
						 '<input type="text" value="'+pre_name+'" style="width:150px;height:30px;padding-left:10px;margin-bottom:10px;border-radius:3px;" id="editCategoryName">'+
						 '</br><span style="margin-bottom:10px;display: block;width: 112px;float: left; line-height: 30px;">限制登录时长:</span>'+
						 timeoutEditSelect,
				btn: ['确认','取消'],
				yes: function(index) {
					var categoryName = $("#editCategoryName").val();
					var categoryTime = $("#editCategoryTime").val();
					//判断名字是否为空
					if(!categoryName){
						layer.msg('名称不能为空！',{time: 2000, icon:5});
						return;
					}
					if(!categoryTime || isNaN(categoryTime)){
						layer.msg('请填写正确的格式！',{time: 2000, icon:5});
						return;
					}
					$.ajax({
						type: "POST",
						url: '<%=basePath%>roleCategory/updateByRoleCategoryId.action',
				    	data: {
				    		"categoryId" : selectRoleCategoryid,
				    		"name" : categoryName,
				    		"logouttime" : categoryTime
				    	},
						dataType:'json',
						cache: false,
						success: function(data){
							if(data.success){
								$("li.active").html(categoryName);
								//退出时长
								$("li.active").attr("data-category-time",categoryTime)
								layer.close(index);
							}else{
								var err = data.message;
								layer.msg("更改失败，请重试！",{time: 2000, icon:5});
							 }
						},
						error: function(data){
							layer.msg("请检查网络！",{time: 2000, icon:5});
						}
					});
				},
				no:function(index){
					document.getElementById("editCategoryName").blur();
					layer.close(index);
				},
			});
	   		//$("#editCategoryName").val(pre_name); 
	   		//$("#categoryName").val(categoryTime);
	   });
	   
	   //拷贝角色类型
	   $("#copyRoleCategory").on("click",function(){
	   		if ($("li.active").length == 0) {
	   			layer.open({
						title: ['拷贝', 'background-color:#d0e3f0; color:#888889;'],
						anim: 'scale',
						content: '请先选择一个角色！',
						btn: ['确认'],
						yes: function(index) {
							layer.close(index);
						},
					});
	   		} else {
		   		var copyRoleCategoryId = $("li.active").attr("data-categoryid");
		   		var selectCategoryTime = $("li.active").attr("data-category-time");
		   		var name = $("li.active").html();
		   		var realName = name + "副本";
		   		layer.confirm('确定要拷贝该信息吗？', {
		  		    	btn: ['确定', '取消'],title:'提示信息',icon: 3 
		  			}, function(index, layero) {//确定按钮回执函数
		   			$.ajax({
						type: "POST",
						url: '<%=basePath%>roleCategory/copyRoleCategory.action',
				    	data: {'copyRoleCategoryId':copyRoleCategoryId, 'realName' : realName },
						dataType:'json',
						cache: false,
						success: function(data){
							if(data.success){
								layer.open({
									title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
									anim: 'scale',
									content: '保存成功！',
									btn: ['确认'],
									yes: function(index) {
										layer.close(index);
									},
								});
								//在网页上展示出来
								var html = "<li class='list-group-item pointer' data-categoryid='"+data.ower+"' data-category-time='"+selectCategoryTime+"'>" + realName + "</li>";
								$("#roleCategories").append(html);
							}else{
								//data.result为false，保存失败
								var err = data.message;
								layer.open({
									title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
									anim: 'scale',
									content: '保存失败，错误信息为：' + err + '！',
									btn: ['确认'],
									yes: function(index) {
										layer.close(index);
									},
								});
							 }
						},
						error: function(data){
							//window.webkit.messageHandlers.loadSuccess.postMessage("error");
							layer.open({
								title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
								anim: 'scale',
								content: '请检查网络！',
								btn: ['确认'],
								yes: function(index) {
									layer.close(index);
								},
							});
						}
					});
				}, function(index){//取消按钮回执函数
		  				layer.close(index);
		  		});
	   		}
	   });
	   
	   //删除角色
	   $("#deleteRoleCategory").on("click",function(){
	   		if ($("li.active").length == 0) {
	   			layer.open({
						title: ['删除', 'background-color:#d0e3f0; color:#888889;'],
						anim: 'scale',
						content: '请先选择一个角色！',
						btn: ['确认'],
						yes: function(index) {
							layer.close(index);
						},
					});
	   		} else {
	   			var categoryId = $("li.active").attr("data-categoryid");
	   			if (!categoryId) {
	   				layer.msg('未携带完整信息',{time: 2000, icon:6});
	   				return;
	   			}
	   			if (categoryId == sysAdminId) {
	   				layer.msg('系统管理员不可删除',{time: 2000, icon:6});
	   				return;
	   			}
	   			var deleteAvailableFlag = false;//是否可以删除
	   			var relatedRole;
	   			$.ajax({
						type: "POST",
						async: false,
						url: '<%=basePath%>roleCategory/checkRelatedRole.action',
						data: {'categoryId':categoryId },
						dataType:'json',
						cache: false,
						success: function(data){
						if(data.success=="true"){
							//可以删除
							deleteAvailableFlag = true;
						}else{
							//不能删除
							deleteAvailableFlag = false;
							relatedRole = data.msg;
				   			if (!deleteAvailableFlag) {
				   				var msg = '该角色类型关联的角色【' + relatedRole + '】下有用户，请在菜单【数据权限管理】->【分配用户】中取消关联用户';
				   				layer.msg(msg,{time: 5000, icon:6});
				   				return;
				   			}
						}
					},
					error: function(data){
						layer.msg("请检查网络！",{time: 2000, icon:5});
					}
				});
				if (deleteAvailableFlag) {
			   	    layer.confirm('将删除该角色类型以及关联的角色，确定删除？', {
		  		    	btn: ['确定', '取消'],title:'提示信息',icon: 3 
		  			}, function(index, layero) {//确定按钮回执函数
			  			$.ajax({
							type: "POST",
							url: '<%=basePath%>roleCategory/deleteByRoleCategoryId.action',
							data: {
								'categoryId':categoryId
								},
							dataType:'json',
							cache: false,
							success: function(data){
							if(data.success=="true"){
								$("li.active").remove();
								layer.close(index);
							}else{
								var err = data.msg;
								layer.msg("删除失败，错误信息："+err,{time: 2000, icon:5});
							}
						},
						error: function(data){
							layer.msg("请检查网络！",{time: 2000, icon:5});
						}
						});
		  		
			  			}, function(index){//取消按钮回执函数
			  				layer.close(index);
		  			});
				}
	   		}
	   });
	   $("#roleType li").click(function(){
	   		$("#roleType li").eq($(this).index()).addClass("action").siblings().removeClass("action");
		
	   });
	//判断IE8浏览器
	<%-- function navigator(selectRoleid,roleName){
		
	var b_version=navigator.appVersion; 
	var version=b_version.split(";"); 
	var trim_Version=version[1].replace(/[ ]/g,""); 
 	if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE8.0") { 
		var host = location.host;
			console.log(host);
		}else{
			window.location.href = "<%=basePath%>role/toAllocateUserRole.action?roleId="+selectRoleid+"&roleName="+roleName;
		} 
	} --%>

</script>

</html>