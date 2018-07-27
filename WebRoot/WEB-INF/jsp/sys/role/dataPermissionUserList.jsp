<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户列表3</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%> 
	<!--列表公共样式  -->
	<link rel="stylesheet" href="${sys_ctx}/css/default-table.css" type="text/css"></link>
	<link rel="stylesheet" href="${sys_ctx}/css/default-page.css" type="text/css"></link>
	<style>
		input:focus::-webkit-input-placeholder{
            text-indent: -999em;
            z-index: -20;
	     }
	    .layui-layer-btn {
		    text-align: center !important;
		}
		#containBranch{
			width:16px;
			height:18px;
			margin-top:0;
		}
		#undoallocate,#alreadyallocate{
			width:16px;
			height:18px;
			margin-top:0;
			margin-left: 5px;
		}
	</style>
  </head>
  <body class="default-one-body">
  	<div class="table-top">
		<form action="${sys_ctx}/user/queryUsersForData.action" name="frm" id="frm" method="post">
			<input type="hidden" id="orgId" name="orgId" value="${sysUser.orgId}" /> 
			<input type="hidden" id="orgName" name="orgNameDisplay" value="${sysUser.orgName}" />
			<input type="hidden" id="globalRoleId" name="globalRoleId" value="${globalRoleId}" />
			<input type="hidden" name="rows" id="rows" value='${myPage.number}' />
			<input type="hidden" name="page" id="page" value='${myPage.intPage}' />
			<!-- 排序 -->
			<input type="hidden" id="orderLoginName" name="orderLoginName" value="${sysUser.orderLoginName}" /> 
			<input type="hidden" name="orderUserName" id="orderUserName" value='${sysUser.orderUserName}' />
			<input type="hidden" name="orderOrgName" id="orderOrgName" value='${sysUser.orderOrgName}' />
			<p class="tab-p" style="width: 680px;">
				<label class="default-one-lable" ><input type="radio" id="alreadyallocate" name="allocateFLag" value="1" ${allocateFLag eq 1 ? "checked" : "" } style="border:0 !important"/>已分配</label>
				<label class="default-one-lable" ><input type="radio" id="undoallocate" name="allocateFLag" value="0" ${allocateFLag eq 0 ? "checked" : "" } style="border:0 !important"/>未分配</label>
				<input type="checkbox" class="default-one-input" style="border:0 !important"  id="containBranch" value="1"  ${containBranch eq 1 ? "checked" : "" } name="containBranch" >
				<label class="default-one-lable" for="containBranch">包含子组织</label>
				<label class="default-one-lable">用户名</label>
				<input type="text" class="default-one-input" id="fuzzyLoginName" name="fuzzyLoginName" value="${sysUser.fuzzyLoginName}">
				<label class="default-one-lable">姓名</label> 
				<input type="text" class="default-one-input" id="fuzzyUserName" name="fuzzyUserName" value="${sysUser.fuzzyUserName}">
				<input class="default-one-input" id="quickSearch" type="text" value=""  placeholder="按enter快速检索" style="display: none;"/>
				<button class="default-one-btn btn-green" onclick="doSearch()">查询</button>
				<button class="default-one-btn-top" onClick="doClear()" >重置</button>
			</p>
		</form>
	</div>
	<div class="tabcenter">
    	<div>
			<span class="span-list">查询列表</span>
			<p class="pull-right" style="line-height: 40px;">
				<span name="allocateSpan" style="text-align:center;display: ${allocateDis }">
					<a class="default-one-btn site-button-method font-color"  data-method="batchAllocateUser" >分配权限</a>
				</span>
				<span name="cancelAllocateSpan" style="text-align:center;display: ${cancelAllocateDis }">
					<a class="default-one-btn site-button-method font-color"  data-method="batchCancelAllocateUser" >取消权限</a>
				</span>
			</p>
	   </div>
		<table class="table table-hover">
			<thead>
				<tr class="tr_center">
					<th>
						<nobr>
							<input id="checkAll" title="全选" name="checkAll" type="checkbox"
								onclick="checkAll(this,'form','checknotall')" style="border:0 !important"/>
						</nobr>
					</th>
					<th>序号</th>
					<th><span onclick="orderByLoginName()">用户名</span></th>
					<th><span onclick="orderByUserName()">姓名</span></th>
					<th><span>所属组织</span></th><!-- <span class="glyphicon glyphicon-chevron-up up-btn"></span> --> <!--  onclick="orderByOrgName()" -->
					<th>手机号</th>
					<th style="display: ${personalizeStyle }">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty list}">
					<tr align="center">
						<td colspan="8" style="color: red;" bgcolor="#FFFFFF">没有查询到数据！</td>
					</tr>
				</c:if>
				<c:if test="${!empty list}">
					<c:forEach var="u" items="${list }" varStatus="status">
						<tr class="forsearch">
							<td class="forsearch">
								<input type="checkbox" name="check" value="<c:out value='${u.userId}'  />" style="border:0 !important"/>
							</td>
							<td> ${status.index+1} </td>
							<td class="forsearch">
								<c:out value="${u.loginName}"/>
							</td>
							<td class="forsearch">
								<c:out value="${u.userName}"/>
							</td>
							<td class="forsearch">
								<c:out value="${u.orgName}"/>
							</td>
							<td class="forsearch">
								<c:out value="${u.phone}"/>
							</td>
							<td style="display: ${personalizeStyle }">
								<a class="site-button-method" id="${u.userId}" data-method="personalizePermissionConfig">数据权限配置&nbsp;</a>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<%@ include file="../../common/page.html"%>
	</div>
  </body>
  <script type="text/javascript">
  	  //控制按钮权限
  	  //pageFuntAutyBtn('${sys_ctx}/getFuntBtnList.action',parent.funtId);
  	  //执行查询操作
	  function doSearch() {
			setPage(); 
			frm.submit(); 	
	  }
	  //重置查询条件
  	  function doClear() {
  			$("#fuzzyLoginName").val('');
  			$("#fuzzyUserName").val('');
  			doSearch();
  	  }
  	  //已分配用户
  	  function selectedUserSerach() {
  	  	$("#undoallocate").unbind("click");
  	  	doSearch();
  	  }
  	  //未分配用户
  	  function tobeSelectingUserSerach() {
  	  	$("#alreadyallocate").unbind("click");
  	  	doSearch();
  	  }
  	  //绑定未分配点击事件 只一次
  	  $("#undoallocate").one("click",tobeSelectingUserSerach);
  	  //绑定已分配点击事件 只一次
  	  $("#alreadyallocate").one("click",selectedUserSerach);
  	  //排序来回切换，三个里边按一个生效
  	  //用户名排序
  	  function orderByLoginName() {
  	  	var orderLoginName = $("#orderLoginName").val();
  	  	if (orderLoginName) {
  	  		if (orderLoginName == 1) {
  	  			$("#orderLoginName").val(2);
  	  		} else if (orderLoginName == 2) {
  	  			$("#orderLoginName").val(1);
  	  		}
  	  	} else {
  	  		$("#orderLoginName").val(1);//默认
  	  	}
  	  	$("#orderUserName").val('');
  	  	$("#orderOrgName").val('');
  	  	//子部门取消勾选
  	  	//$("#containBranch").prop("checked",false);
  	  	doSearch();
  	  }
  	  //姓名排序
  	  function orderByUserName() {
  	  	var orderUserName = $("#orderUserName").val();
  	  	if (orderUserName) {
  	  		if (orderUserName == 1) {
  	  			$("#orderUserName").val(2);
  	  		} else if (orderUserName == 2) {
  	  			$("#orderUserName").val(1);
  	  		}
  	  	} else {
  	  		$("#orderUserName").val(1);//默认
  	  	}
  	  	$("#orderLoginName").val('');
  	  	$("#orderOrgName").val('');
  	  	doSearch();
  	  }
  	  //组织机构排序
  	  function orderByOrgName() {
  	  	var orderOrgName = $("#orderOrgName").val();
  	  	if (orderOrgName) {
  	  		if (orderOrgName == 1) {
  	  			$("#orderOrgName").val(2);
  	  		} else if (orderOrgName == 2) {
  	  			$("#orderOrgName").val(1);
  	  		}
  	  	} else {
  	  		$("#orderOrgName").val(1);//默认
  	  	}
  	  	$("#orderUserName").val('');
  	  	$("#orderLoginName").val('');
  	  	doSearch();
  	  }
  	  
  	  //按enter键当前页面检索，discard该方法
  	/*   $("#quickSearch").on("keyup", function() {
  	  		
  			var vals = document.getElementById("quickSearch").value;
  			$("tr.forsearch").css('display','none');
  			$("tr").find("td.forsearch").each(function() {
	  			 var tdvalue = $(this).text();
	  			 var td = $(this);
	  			 if((tdvalue.toLowerCase()).indexOf((vals.toLowerCase()))>-1){
	  			 	td.parent("tr").css('display','');
	  			 }
  			 });
  	  }); */
  	  
  	  //全选
  	  function checkAll(obj) {
	     var all  =  document.getElementsByName("check");  
	     for (var i=0; i<all.length; i++) {  
	         all[i].checked  =  obj.checked;  
	     }  
	     if (document.getElementById("checknotall")) {
	    	 document.getElementById("checknotall").checked=false;
	     }
	  };
	  
	  
	  layui.use('layer', function() { 
			var $ = layui.jquery, layer = layui.layer; 
			//触发事件
			  var active = {
			    //个性化数据权限配置
			    personalizePermissionConfig: function(){
			    	var that = this; 
			    	var userId = that.id;//获取该条的主键id
			    	var roleId = $("#globalRoleId").val();
			    	if (!roleId) {
			    		layer.msg('请选中角色！',{time: 2000, icon:6});
			    	}
			    	var personalizeUrl = '${sys_ctx}/role/personalizePermissionConfig.action?userId='+userId+'&roleId='+roleId;
			    	parent.layer.open({
				        type: 2 
				        ,id:'viewUser'
				        ,title: '数据配置'
				        ,area: ['300px', '500px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,content: personalizeUrl
				        ,end: function(){
				        	
						}
				      });
			    },
			    //分配权限
			    batchAllocateUser: function(){
			    	var userIds = [];
			    	$("input[name='check']:checked").each(function(){
			    		userIds.push($(this).val());
			    	});
			    	var roleId = $("#globalRoleId").val();
			    	if (!roleId) {
			    		layer.msg('请选中角色！',{time: 2000, icon:6});
			    	}
					if(userIds != '' && userIds != null) {
						layer.confirm('确定要分配吗？', {title:'提示信息',
					  		  btn: ['确定', '取消'],icon: 3
					  		}, function(index, layero){//确定按钮回执函数
					  			$.post('${sys_ctx}/role/batchAllocateUser.action',{'userIds':userIds, 'roleId':roleId},
					  					function(data){
							  				if (data.success=="true") {
							  					//分配完进入到已分配页面
							  					//$("#alreadyallocate").prop("checked",true);
							  					doSearch();//刷新本页面
						  					} else {
						  						layer.alert(data.msg, {title:'提示信息',icon: 5});
						  					}
					  					},'json');
					  		}, function(index){//取消按钮回执函数
					  		});
					} else {
						layer.msg('请先选择要分配的用户！',{time: 2000, icon:6});
					}
			    },
				//取消权限
				batchCancelAllocateUser: function(){
			    	var userIds = [];
			    	var deleteSuperFlag = false;
			    	$("input[name='check']:checked").each(function(){
			    		var userId = $(this).val();
			    		if (userId == 1) {
			    			deleteSuperFlag = true;
			    		}
			    		userIds.push($(this).val());
			    	});
			    	if (deleteSuperFlag) {
		    			layer.msg('系统默认账户不可删除',{time: 2000, icon:6});
			    		return;
			    	}
			    	var roleId = $("#globalRoleId").val();
			    	if (!roleId) {
			    		layer.msg('请选中角色！',{time: 2000, icon:6});
			    	}
					if(userIds != '' && userIds != null) {
						layer.confirm('确定要取消吗？', {title:'提示信息',
					  		  btn: ['确定', '取消'],icon: 3
					  		}, function(index, layero){//确定按钮回执函数
					  			$.post('${sys_ctx}/role/batchCancelAllocateUser.action',{'userIds':userIds, 'roleId':roleId},
					  					function(data){
							  				if (data.success=="true") {
							  					window.location.reload(); //刷新本页面
						  					} else {
						  						layer.alert(data.msg, {title:'提示信息',icon: 5});
						  					}
					  					},'json');
					  		}, function(index){//取消按钮回执函数
					  		});
					} else {
						layer.msg('请先选择要取消的用户！',{time: 2000, icon:6});
					}
			    }
			 };
			
			 $('.site-button-method').on('click', function(){
				 var othis = $(this), method = othis.data('method');
				 active[method] ? active[method].call(this, othis) : '';
			 });
		});
	  function refresh() {
		  doSearch();
	  }
	  
  </script>
</html>
