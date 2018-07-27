<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<%@include file="/WEB-INF/jsp/common/common.html"%>
<title>角色分配</title>
</head>
<style>
body {
	background: #fff;
	font-size: 12px;
}
.r1,.p2-h,.p1-h,.n1-h,.n2-h{
	background: none;
	border:none !important;
}
#rowsId,#pageId{
	width: 33px;
    margin: 0 10px !important;
    text-align: center;
    padding: 0;
}
.table-top {
	background: none;
	margin-bottom:20px;
}

.table-center {
	width: 100%;
	margin-top: 10px;
}

.tabcenter {
	background: #fff;
	overflow: hidden;
}

.panel {
	padding: 0;
	margin-top: 10px;
}

.panel-body {
	height:68%;
	overflow: auto;
	padding:0;
}

.panel-default,.btn-default {
	border-color: #a2a7a6;
}

#selectedObj,#leftTree {
	width: 100%;
	border-radius: 3px;
	/* height:300px;  */
	overflow: auto;
	border: 0 !important;
}

.btn-group-vertical p {
	margin-bottom: 5px
}

.center-bot {
	padding: 0 10px
}

.btn-bottom {
	width: 100%;
	text-align: center;
}

.row {
	margin-bottom: 10px;
	margin-right: 0;
	margin-left: 0;
}

.form-control {
	height: 28px;
	padding: 2px 10px;
}

.btn-default {
	height: 28px;
	font-size: 12px;
	margin: 0
}

.for-label {
	float: left;
	height: 28px;
	line-height: 28px;
	margin-bottom: 0;
}

.for-label1 {
	float: left;
	width: 110px;
	height: 28px;
	line-height: 28px;
	margin-bottom: 0;
}
/* .col-xs-5{padding:0;width:310px;} */
.n1,.n2{
	background: none;
	border:none !important;
}

.col-xs-9 {
	margin: 0;
	padding: 0;
	width: 200px;
}

.big-begin {
	width: 100%;
	padding-top: 10px;
	margin: 0 auto;
}

#remove,#remove_all {
	background: #fff;
	color: #3997f4;
	border: 1px #399ef4 solid;
}

#leftLi li {
	margin-top: 3px;
}

input {
	margin: 0 !important;
}

.default-one-input {
    width: 90px !important;
    height: 24px;
    font-size: 12px;
    border-radius: 3px;
    padding-left: 5px;
    border: 1px solid #a2a7a6;
    line-height: 24px;
}
</style>
<body>
	<div class="big-begin">
	<span class="span-list" style="margin-left:20px;">分配人员页面:${ role.roleName }</span>
		<div class="table-center">
			<div class="tabcenter">
				<div class="center-bot">
					<div class="row">
						<div class="tabcenter" style="width:45%;float: left" >
							<div>
								<div class="table-top">
									<form action="${sys_ctx}/role/toAllocateUserRole.action" name="frm" id="frm" method="post">
										<input type="hidden" name="rows" id="rows" value='${myPage.number}' />
										<input type="hidden" name="page" id="page" value='${myPage.intPage}' />
										<input type="hidden" name="flag" id="flag" value="0"/>
										<input type="hidden" name="selectId" id="selectId" value="${selectId }"/>
										<input type="hidden" id="roleId" name="roleId" value="${ role.roleId }" />
										<p class="tab-p" style="margin-bottom: 10px;width:100%;text-align: right;">
											<label class="default-one-lable">姓名</label>
											<input type="text" class="default-one-input" id="fuzzyUserName" name="fuzzyUserName" value="${sysUser.fuzzyUserName}">
											<label class="default-one-lable">组织机构名称</label>
											<input type="text" class="default-one-input" id="fuzzyOrgName" name="fuzzyOrgName" value="${sysUser.fuzzyOrgName}">
											<button class="default-one-btn btn-green" onclick="doSearch()">查询</button>
											<button class="default-one-btn-top" onClick="doClear()">重置</button>
										</p>
									</form>
								</div>
								
							</div>
							<table class="table table-hover" style="border:1px #a2a7a6 solid">
								<thead id="newLi">
									<tr class="tr_center">
										<th>
											<nobr>
												<input id="checkLeftAll" title="全选" name="checkLeftAll" type="checkbox" onclick="checkLeftAll()" style="border: 0 !important;" />
											</nobr>
										</th>
										<th>序号</th>
										<th>用户名</th>
										<th>姓名</th>
										<th>所属组织</th>
										<th>手机号</th>
									</tr>
								</thead>
								<tbody id="leftBody">
									<c:if test="${empty list}">
										<tr align="center">
											<td colspan="8" style="color: red;" bgcolor="#FFFFFF">没有查询到数据！</td>
										</tr>
									</c:if>
									<c:if test="${!empty list}">
										<c:forEach var="u" items="${list }" varStatus="status">
											<tr class="leftTr" data-itemid="${u.userId}" data-itemname="${u.userName}">
												<td class="forsearch"><input type="checkbox" name="check" value="<c:out value='${u.userId}'/>"  style="border:0!important;"/></td>
												<td>${status.index+1} <input type="hidden" name="userName" value="<c:out value='${u.userName}'/>" /></td>
												<td class="forsearch"><c:out value="${u.loginName}" /></td>
												<td class="forsearch"><c:out value="${u.userName}" /></td>
												<td class="forsearch"><c:out value="${u.orgName}" /></td>
												<td class="forsearch"><c:out value="${u.phone}" /></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
							<%@ include file="../../common/page.html"%>
						</div>
						<div class="tabcenter" style="width:15%;float: left">
							<div class=" panel-default"
								style="border:0; padding-top:80px;">
								<div class="" style="padding:0;text-align:center">
									<div class="btn-group-vertical" role="group" aria-label="">
										<p>
											<a class="btn btn-primary btn-xs btn-block" role="button"
												href="javascript:;" id="addRight">添加 </a>
										</p>
										<p>
											<a class="btn btn-primary btn-xs btn-block" role="button"
												href="javascript:;" id="add_all">添加所有 </a>
										</p>
										<p>
											<a class="btn btn-primary btn-xs btn-block" role="button"
												href="javascript:;" id="removeLeft">删除 </a>
										</p>
										<p>
											<a class="btn btn-primary btn-xs btn-block" role="button"
												href="javascript:;" id="remove_all">删除所有 </a>
										</p>
									</div>
								</div>
							</div>
						</div>
						<div class=" tabcenter"style="width:40%">
							<div class="row" style="height: 42px;margin-bottom: 20px;">
								<div class="for-label col-xs-5" style="margin-top:20px;">
									已分配用户&nbsp;共<span id="selectedNum" style="color:red">${fn:length(selectedUserList)}</span>个
								</div>
								<div class="input-group col-xs-7" style="margin-top:20px;">
									<input id="kw" type="text" class="form-control" placeholder="输入用户名" />
									<span class="input-group-btn">
										<button data-method="search-btn" class="btn btn-default" type="button" onclick="filterRight()">搜索</button>
									</span>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-body">
									<table class="table table-hover" id="leftTable" >
										<thead>
											<tr class="tr_center">
												<th>
													<nobr>
														<input id="checkRightAll" title="全选" name="checkRightAll" type="checkbox" onclick="checkRightAll()"  style="border:0 !important"/>
													</nobr>
												</th>
												<th>序号</th>
												<th>用户名</th>
												<th>姓名<span class="glyphicon glyphicon-chevron-up up-btn"></span>
												</th>
												<th>所属组织
													<span class="glyphicon glyphicon-chevron-up up-btn"></span>
												</th>
												<th>手机号</th>
											</tr>
										</thead>
										<tbody id="rightBody">
										<c:if test="${!empty selectedUserList}">
											<c:forEach items="${selectedUserList }" var="u" varStatus="status">
												<tr class="forsearch rightTr" >
													<td class="forsearch"><input type="checkbox" name="check"  style="border:0 !important" value="<c:out value='${u.userId}'/>"/></td>
													<td>${status.index+1} </td>
													<td class="forsearch" ><c:out value="${u.loginName}" /></td>
													<td class="forsearch"><c:out value="${u.userName}" /></td>
													<td class="forsearch"><c:out value="${u.orgName}" /></td>
													<td class="forsearch"><c:out value="${u.phone}" /></td>
												</tr>
											</c:forEach>
										</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="btn-bottom">
						<button onclick="saveData()" type="button" class="save">确定</button>
						<button type="button" onclick="javascript:history.back(-1);"
							class="cancel">取消</button>
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
	
	function doSearch() {
		setPage(); 
		frm.submit(); 	
    }
 	function doClear() {
		clearForm("frm");
		doSearch();
 	}
 	
	//左侧全选
	function checkLeftAll(){
		var flag = $("#checkLeftAll").prop("checked");
		//获取右侧tr集合
		var rightTrList = $("#leftBody").children("tr");
		for (var i=0;i<rightTrList.length;i++) {
			var tdArr = rightTrList.eq(i).find("td");
			if(flag){
				tdArr.eq(0).find("input[type='checkbox']").prop("checked","checked");
			} else {
				tdArr.eq(0).find("input[type='checkbox']").removeAttr("checked");
			}
		}
	}
	//右侧全选
	function checkRightAll(){
		var flag = $("#checkRightAll").prop("checked");
		//获取右侧tr集合
		var rightTrList = $("#rightBody").children("tr");
		for (var i=0;i<rightTrList.length;i++) {
			var tdArr = rightTrList.eq(i).find("td");
			if(flag){
				tdArr.eq(0).find("input[type='checkbox']").prop("checked","checked");
			} else {
				tdArr.eq(0).find("input[type='checkbox']").removeAttr("checked");
			}
		}
	}
	function filterRight(){
		debugger;
			 var oTab=document.getElementById("leftTable");
       		 var oTex=document.getElementById("kw");
		    for(var i=0;i<oTab.tBodies[0].rows.length;i++) {
		    debugger;
                var sTab=oTab.tBodies[0].rows[i].cells[2].innerHTML.toLowerCase();//行中名字的值并转化成小写
                var sTab1=oTab.tBodies[0].rows[i].cells[3].innerHTML.toLowerCase();
                var sTab2=oTab.tBodies[0].rows[i].cells[4].innerHTML.toLowerCase();
                var sTex=oTex.value.toLowerCase();  //用户输入的值并转化成小写
                var arr=sTex.split(' ');//如果用户用空格隔开关键字
                oTab.tBodies[0].rows[i].style.background='';//把所有的背景都设置为空
                //如果刷选，把css换成block显示就行
                for(var j=0;j<arr.length;j++)
                {
                    if (sTab.search(arr[j])!=-1||sTab1.search(arr[j])!=-1||sTab2.search(arr[j])!=-1)  //调用search函数查找截取出来的字符数组，判断是否存在，
                    {
                        oTab.tBodies[0].rows[i].style.backgroundColor="#3997f4";//存在改变td的颜色
                    }
                    
                }
            }
		}
	$(function(){
		//给右侧搜索框绑定enter搜索事件
		var $kwinp = $('#kw');
		$kwinp.bind('keydown', function (e) {
			var key = e.which;
			if(key == 13) {
				filterRight();
			}
		});
		
		
		//移到右边
		$('#addRight').click(function(){
			//获取右侧tr集合,防止重复
			var rightTrList = $("#rightBody").children("tr");
		
			//遍历左table下的tr
			$(".leftTr").each(function(){
				var trChildren = $(this).children("td");
				var flag = trChildren.eq(0).children("input").prop("checked");
				var userId = trChildren.eq(0).children("input").val();
				var userName = trChildren.eq(1).html();
				var loginName = trChildren.eq(2).html();
				var userName = trChildren.eq(3).html();
				var orgName = trChildren.eq(4).html();
				var phone = trChildren.eq(5).html();
				if(userId && flag){
					var alreadyIn = false;
					//获取右侧已有的用户 
					for (var i=0;i<rightTrList.length;i++) {
						var tdArr = rightTrList.eq(i).find("td");
						var userIdC = tdArr.eq(0).find("input").val();
						if(userIdC == userId){//右侧tr已有重复元素
							alreadyIn = true;
							break;
						}
					}
					
					//追加
					if(!alreadyIn) {
					debugger;
					var index = $(".rightTr").length + 1;
				    	var newTr = '<tr class="forsearch">'
										+'<td class="forsearch"><input type="checkbox" name="check" value="'+ userId +'" /></td>'
										+'<td>'+  (index++) +'</td>'
										+'<td class="forsearch">'+ loginName +'</td>'
										+'<td class="forsearch">'+ userName +'</td>'
										+'<td class="forsearch">'+ orgName +'</td>'
										+'<td class="forsearch">'+ phone +'</td>'
										+'</tr>';				 
						$("#rightBody").append(newTr);
					}
				}
			});
			assignValueToSelectId();
			selectedRightNum();
		});
		
		//删除
		$('#removeLeft').click(function(){
			//获取右侧tr集合
			var rightTrList = $("#rightBody").children("tr");
			for (var i=0;i<rightTrList.length;i++) {
				var tdArr = rightTrList.eq(i).find("td");
				var flag = tdArr.eq(0).find("input").prop("checked");
				if(flag){
					//被勾选，移除tr
					rightTrList.eq(i).remove();
				}
			}
			assignValueToSelectId();
			selectedRightNum();
		});
		
		
		//全部移到右边
		$('#add_all').click(function(){
			//获取右侧tr集合,防止重复
			var rightTrList = $("#rightBody").children("tr");
		
			//遍历左table下的tr
			$(".leftTr").each(function(){
				var trChildren = $(this).children("td");
				var userId = trChildren.eq(0).children("input").val();
				var userName = trChildren.eq(1).html();
				var loginName = trChildren.eq(2).html();
				var userName = trChildren.eq(3).html();
				var orgName = trChildren.eq(4).html();
				var phone = trChildren.eq(5).html();
				if (userId) {
					var alreadyIn = false;
					//获取右侧已有的用户 
					for (var i=0;i<rightTrList.length;i++) {
						var tdArr = rightTrList.eq(i).find("td");
						var userIdC = tdArr.eq(0).find("input").val();
						if(userIdC == userId){//右侧tr已有重复元素
							alreadyIn = true;
							break;
						}
					}
					var index = $(".rightTr").length + 1;
					//追加
					if(!alreadyIn) {
				    	var newTr = '<tr class="forsearch">'
										+'<td class="forsearch"><input type="checkbox" name="check" value="'+ userId +'" /></td>'
										+'<td>'+  index +'</td>'
										+'<td class="forsearch">'+ loginName +'</td>'
										+'<td class="forsearch">'+ userName +'</td>'
										+'<td class="forsearch">'+ orgName +'</td>'
										+'<td class="forsearch">'+ phone +'</td>'
										+'</tr>';				 
						$("#rightBody").append(newTr);
					}
				}
			});
			assignValueToSelectId();
			selectedRightNum();
		});
		
		//清空所有选项
		$('#remove_all').click(function(){
			//获取右侧tr集合
			var rightTrList = $("#rightBody").children("tr");
			for (var i=0;i<rightTrList.length;i++) {
				var tdArr = rightTrList.eq(i).find("td");
				rightTrList.eq(i).remove();
			}
			assignValueToSelectId();
			selectedRightNum();
		});
		
	});
	//计算右侧选中个数，公用方法
	function selectedRightNum(){
		$("#selectedNum").html($("#rightBody").children("tr").length);
	}
	
	//给隐藏域selectId赋值
	function assignValueToSelectId(){
		var newRightTrList = $("#rightBody").children("tr");
		var hiddenUserArr = [];
		var newIds;
		for (var i=0;i<newRightTrList.length;i++) {
			var newtdArr = newRightTrList.eq(i).find("td");
			var newUserId = newtdArr.eq(0).find("input").val();
			hiddenUserArr.push(newUserId);
		}
		if(hiddenUserArr.length>0){
			newIds = hiddenUserArr.join(",");
			$("#selectId").val(newIds);
		} else {
			$("#selectId").val("fourButton");
		}
		
	}
    
	//保存数据
    function saveData(){
    	var roleId = $("#roleId").val();
    	if(!roleId){
    		layer.alert("未定义角色");
    		return;
    	}
    	var idArr = [];
    	//获取右侧tr集合
		var rightTrList = $("#rightBody").children("tr");
		if(rightTrList && rightTrList.length>0){
			for (var i=0;i<rightTrList.length;i++) {
				var tdArr = rightTrList.eq(i).find("td");
				var id = tdArr.eq(0).find("input").val();
	            idArr.push(id);
			}
		}
    	var userIds = idArr.join(",");
        //存入到后台
        var url = '${sys_ctx}/role/allocateRoleUsers.action';
        layer.confirm('确定要保存该信息吗？', {btn: ['确定', '取消'],icon: 3 }, function(index, layero){//确定按钮回执函数
  			$.ajax({
  				async:true,
  				url : url,
  				type : "POST",
  				cache : false,
  				data : {'roleId':roleId, 'userIds':userIds},
  				dataType : 'json',
  				success : function (data) {
  					if(data.success=="true"){
  						window.location.href = "${sys_ctx}/role/getRoleList.action";
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
	
    //异步搜索用户 回调展示到页面上
    $("button[data-method='ascSearchUser']").on("click",function(){
    	var keyword = $("#keyword").val();
    	if(keyword){
	    	$.ajax({
				type: "POST",
				url: '<%=basePath%>user/queryUsersByName.action',
										data : {
											'userName' : keyword
										},
										dataType : 'json',
										cache : false,
										success : function(data) {
											if (data.success == "true") {
												var userList = data.ower;
												if (userList) {
													for ( var i = 0; i < userList.length; i++) {
														var userId = userList[i].userId;
														var userName = userList[i].userName;
														//追加
														var newLi = '<li value=" '+ userId +'"  data-itemname=" '+ userName +' "><input type="checkbox" class="inputVal" value=" '
																+ userId
																+ ' " style="margin-right:10px !important;"/>'
																+ userName
																+ '</li>';
														if (userId != $(
																"#leftLi li")
																.val()) {//错误
															$("#leftLi")
																	.append(
																			newLi);
														}
													}
												}
											} else {
												var err = data.msg;
												layer.msg("删除角色失败，错误信息：" + err,
														{
															time : 2000,
															icon : 5
														});
											}
										},
										error : function(data) {
											layer.msg("请检查网络！", {
												time : 2000,
												icon : 5
											});
										}
									});
						} else {
							return;
						}
					});
	//搜索事件
	$("button[data-method='search-btn']").on("click", function() {
		var event = event || window.event;
		var target = $(event.target);
		var val = target.parent().siblings('input').val();
		if (target.hasClass('left-btn')) {
			var liList = $('#leftLi li');
		} else {
			var liList = $('#selectedObj li');
		}
		for ( var i = 0; i < liList.length; i++) {
			var _name = $(liList[i]).attr("data-itemname");
			if (_name && _name.indexOf(val) == -1) {
				$(liList[i]).hide();
			} else {
				$(liList[i]).show();
			}
		}
	});
	// dbl事件
	$("#leftLi").on('dblclick', '.dblinput', function(event) {
		var event = event || window.event;
		var target = $(event.currentTarget);
		target.hide();
		target.siblings('.editinput').show();
		target.siblings('.editinput').children()[0].focus();
	});
	$("#leftLi").on('mouseover', 'li', function(event) {
		var event = event || window.event;
		var target = $(event.currentTarget);
		target.find('.remove').show();
	}).on('mouseout', 'li', function(event) {
		var event = event || window.event;
		var target = $(event.currentTarget);
		target.find('.remove').hide();
	});
</script>
