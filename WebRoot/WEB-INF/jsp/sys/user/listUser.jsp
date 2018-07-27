<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户列表</title>
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
			width:18px;
			height:18px;
			margin-top:0;
		}
	</style>
  </head>
  <body class="default-one-body">
  	<div class="table-top">
		<form action="${sys_ctx}/user/queryUsers.action" name="frm" id="frm" method="post">
			<input type="hidden" id="funtId" name="funtId" value="${funtId }" />
			<input type="hidden" id="orgId" name="orgId" value="${sysUser.orgId}" /> 
			<input type="hidden" id="orgName" name="orgNameDisplay" value="${sysUser.orgName}" /> 
			<input type="hidden" name="rows" id="rows" value='${myPage.number}' />
			<input type="hidden" name="page" id="page" value='${myPage.intPage}' />
			<!-- 排序 -->
			<input type="hidden" id="orderLoginName" name="orderLoginName" value="${sysUser.orderLoginName}" /> 
			<input type="hidden" name="orderUserName" id="orderUserName" value='${sysUser.orderUserName}' />
			<input type="hidden" name="orderOrgName" id="orderOrgName" value='${sysUser.orderOrgName}' />
			<p class="tab-p" style="width: 430px;">
				<input type="checkbox" class="default-one-input" style="border:0 !important"  id="containBranch" value="1"  ${containBranch eq 1 ? "checked" : "" } name="containBranch" >
				<label class="default-one-lable" for="containBranch">包含子组织</label>
				<label class="default-one-lable">用户名</label> 
				<input type="text" class="default-one-input" id="fuzzyLoginName" name="fuzzyLoginName" value="${sysUser.fuzzyLoginName}">
				<label class="default-one-lable">姓名</label> 
				<input type="text" class="default-one-input" id="fuzzyUserName" name="fuzzyUserName" value="${sysUser.fuzzyUserName}">
				<input class="default-one-input" id="quickSearch" type="text" value=""  placeholder="按enter快速检索" style="display: none;"/>
			</p>
			<button class="default-one-btn btn-green" onclick="doSearch()">查询</button>
			<button class="default-one-btn-top" onClick="doClear()" >重置</button>
		</form>
	</div>
	<div class="tabcenter">
    	<div>
			<span class="span-list">查询列表</span>
			<p class="pull-right"  style="line-height: 40px;">
				<c:if test="${fn:contains(buttonList,'addSpan')}">
					<span name="addSpan" style="text-align:center;">
						<a class="default-one-btn site-button-method font-color"  data-method="addUser" >新增</a>
					</span>
				</c:if>
				<c:if test="${fn:contains(buttonList,'delSpan')}">
					<span name="delSpan" style="text-align:center;">
						<a class="default-one-btn site-button-method font-color"  data-method="delUser" >删除</a>
					</span>
				</c:if>
				<c:if test="${fn:contains(buttonList,'setRoleSpan')}">
					<span name="setRoleSpan" style="text-align:center;">
						<a class="default-one-btn site-button-method font-color"  data-method="setRole" >设置角色</a>
					</span>
				</c:if>
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
					<th><span onclick="orderByUserName()">姓名</span></th><!-- <span class="glyphicon glyphicon-chevron-up up-btn"></span> -->
					<th><span onclick="orderByOrgName()">所属组织</span></th><!-- <span class="glyphicon glyphicon-chevron-up up-btn"></span> -->
					<th>手机号</th>
					<!-- <th>创建人</th> -->
					<th>操作</th>
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
							<td class="forsearch" <c:if test="${!empty u.roleIdDb }">style="color: blue;"</c:if> id="change_${u.userId}" >
								<c:out value="${u.userName}"/>
							</td>
							<td class="forsearch">
								<c:out value="${u.orgName}"/>
							</td>
							<td class="forsearch">
								<c:out value="${u.phone}"/>
							</td>
							<td>
								<a class="site-button-method" id="${u.userId}" data-method="viewUser">查看&nbsp;</a>
								<c:if test="${fn:contains(buttonList,'editSpan') && u.authType!=1 }">
									<span name="editSpan" style="text-align:center;">
										<a class="site-button-method" id="${u.userId}" data-method="modifyUser">|&nbsp;编辑&nbsp;</a>
									</span>
								</c:if>
								<c:if test="${fn:contains(buttonList,'copySpan')}">
									<span name="copySpan" style="text-align:center;">
										<a class="site-button-method" id="${u.userId}" data-method="copyUser">|&nbsp;复制&nbsp;</a>
									</span>
								</c:if>
								<c:if test="${fn:contains(buttonList,'authSpan')}">
									<span name="authSpan" style="text-align:center;">
										<a class="site-button-method" id="${u.userId}" data-method="openAddRole">|&nbsp;分配角色&nbsp;</a>
									</span>
								</c:if>
								<c:if test="${fn:contains(buttonList,'resetSpan')}">
									<span name="resetSpan" style="text-align:center;">
										<a class="site-button-method" id="${u.userId}" data-method="resetPwd">|&nbsp;重置密码</a>
									</span>
								</c:if>
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
  			clearForm("frm");
  			doSearch();
  	  }
  	  
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
  	  $("#quickSearch").on("keyup", function() {
  			var vals = document.getElementById("quickSearch").value;
  			$("tr.forsearch").css('display','none');
  			$("tr").find("td.forsearch").each(function() {
	  			 var tdvalue = $(this).text();
	  			 var td = $(this);
	  			 if((tdvalue.toLowerCase()).indexOf((vals.toLowerCase()))>-1){
	  			 	td.parent("tr").css('display','');
	  			 }
  			 });
  	  });
  	  //全选
  	  function checkAll(obj) {
		     var all  =  document.getElementsByName("check");  
		     for (var i=0; i<all.length; i++) {  
		         all[i].checked  =  obj.checked;  
		     }  
		     if (document.getElementById("checknotall")) {
		    	 document.getElementById("checknotall").checked=false;
		     }
	  }
  	  
	  var funtType = "add";//判断是新增还是编辑
	  layui.use('layer', function() { 
			var $ = layui.jquery, layer = layui.layer; 
			//触发事件
			  var active = {
				  //添加用户
				  addUser: function() {
					  funtType = "add";
					  var orgId = $('#orgId').val();
				      if(!orgId) {
				    	  layer.msg('请选择组织节点！',{time: 2000, icon:5});
			    		  return;
				      }
				      var orgName = $('#orgName').val();
				      layer.open({
				        type: 2 
				        ,id:'addUser'
				        ,title: '新增'
				        ,area: ['700px', '440px']
				        ,shade: 0.2
				        ,maxmin: false
				         ,move: false
				        ,offset:  ['50px', '50px']
				        ,content: '${sys_ctx}/user/openOperateUser.action?orgId='+orgId+'&funtType='+funtType
				        ,end: function() {
				        	window.location.reload(); //刷新父页面
				        }
				      });
			    },
			    //修改用户
			    modifyUser: function(){
			    	var that = this; 
			    	funtType = "edit";
			    	var userId = that.id;//获取该用户主键id
			    	layer.open({
				        type: 2 
				        ,id:'modifyUser'
				        ,title: '编辑'
				        ,area: ['700px', '440px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,content: '${sys_ctx}/user/toModifyUser.action?userId='+userId+'&funtType='+funtType
				        ,end: function(){
				        	doSearch(); //刷新父页面
				        }
				      });
			    },
			    //查看
			    viewUser: function(){
			    	var that = this; 
			    	funtType = "view";
			    	var userId = that.id;//获取该条的主键id
			    	layer.open({
				        type: 2 
				        ,id:'viewUser'
				        ,title: '查看'
				        ,area: ['700px', '440px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,content: '${sys_ctx}/user/openOperateUser.action?userId='+userId+'&funtType='+funtType
				        ,end: function(){
				        	
						}
				      });
			    },
			  	//复制
			    copyUser: function(){
			    	var that = this; 
			    	funtType = "copy";
			    	var userId = that.id;//获取该条的主键id
			    	layer.open({
				        type: 2 
				        ,id:'copyUser'
				        ,title: '复制'
				        ,area: ['700px', '440px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,content: '${sys_ctx}/user/openOperateUser.action?userId='+userId+'&funtType='+funtType
				        ,end: function(){
				        	if(parent.page_sign==1){refresh();}//刷新页面
				        }
				      });
			    },
			    //删除用户(逻辑删除)
			    delUser: function(){
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
					if(userIds != '' && userIds != null) {
						layer.confirm('确定要删除该信息吗？', {title:'提示信息',
					  		  btn: ['确定', '取消'],icon: 3
					  		}, function(index, layero){//确定按钮回执函数
					  			$.post('${sys_ctx}/user/deleteBatch.action',{'userIds':userIds},
					  					function(data){
							  				if(data.success=="true"){
							  					window.location.reload(); //刷新本页面
						  					} else {
						  						layer.alert(data.msg, {title:'提示信息',icon: 5});
						  					}
					  					},'json');
					  		}, function(index){//取消按钮回执函数
					  		});
					} else {
						layer.msg('请先选择要删除的内容！',{time: 2000, icon:5});
					}
			    },
			    //打开授权页面
			    openAddRole: function(){
			    	var that = this; 
			    	var userId = that.id;//获取该用户名
			    	//var urlRoleConfig = '${sys_ctx}/user/openAddRole.action?userId='+userId;//进入的角色分配页面是左右列表页面 将此URL注释掉，后台代码仍然存在
			    	var urlRoleConfig = '${sys_ctx}/user/openAddRoleTree.action?userId='+userId;//进入的角色分配页面是树形页面
			    	layer.open({
				        type: 2 
				        ,id:'openAddRole'
				        ,title: '授权'
				        ,area: ['300px', '480px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,content: urlRoleConfig
				        ,end: function(){
				        	
				        }
				     });
			    },
			    //打开设置角色页面
			    setRole: function(){
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
		    			layer.msg('系统默认账户不可配置',{time: 2000, icon:6});
			    		return;
			    	}
					if(userIds != '' && userIds != null) {
						var userIds = userIds.join(",");
						var urlRoleConfig = '${sys_ctx}/user/openSetBatchRoleTree.action?userIds='+userIds;//进入的角色分配页面是树形页面
				    	layer.open({
					        type: 2 
					        ,id:'openAddRole'
					        ,title: '授权'
					        ,area: ['300px', '480px']
					        ,shade: 0.2
					        ,maxmin: false
					        ,content: urlRoleConfig
					        ,end: function(){
					        	
					        }
					     });
					} else {
						layer.msg('请先选择要设置的内容！',{time: 2000, icon:6});
					}
			    },
			    //重置密码
			    resetPwd: function(){
			    	var that = this; 
			    	var id = that.id;//获取主键
			    	layer.confirm('确定需要重置密码？(密码重置为123456)', {title:'提示信息',icon:3,
				  		  btn: ['确定', '取消'] 
				  		}, function(index, layero){//确定按钮回执函数
				  			$.post('${sys_ctx}/user/resetPwd.action',{'userId':id},
				  					function(data){
						  				if(data.success=="true"){
						  					layer.msg(data.msg,{time: 2000, icon:6});
					  					} else {
					  						layer.alert(data.msg, {title:'提示信息',icon: 5});
					  					}
				  					},'json');
				  		}, function(index){//取消按钮回执函数
				  		});
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
