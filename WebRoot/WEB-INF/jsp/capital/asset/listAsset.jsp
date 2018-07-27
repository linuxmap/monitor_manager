<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户列表1</title>
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
		select{
		    padding:3px 0px 3px 10px;
		}
	</style>
  </head>
  <body class="default-one-body">
  	<div class="table-top">
		<form style="width:100%!important;"  action="${sys_ctx}/asset/queryAssets.action" name="frm" id="frm" method="post">
			<input type="hidden" id="funtId" name="funtId" value="${funtId }" />
			<input type="hidden" id="orgId" name="orgId" value="${vmsAsset.orgId}" />
			<input type="hidden" id="orgName" name="orgName" value="${vmsAsset.orgName}" /> 
			<input type="hidden" name="rows" id="rows" value='${myPage.number}' />
			<input type="hidden" name="page" id="page" value='${myPage.intPage}' />
			<p class="tab-p" style="width:800px !important;">
				<input type="checkbox" style="border:0 !important;width:18px;height:18px;margin-top:0" class="default-one-input" id="containBranch" value="1"  ${containBranch eq 1 ? "checked" : "" } name="containBranch" >
				<label class="default-one-lable" for="containBranch">包含子组织</label>
				<label class="default-one-lable">设备类型</label> 
				<select name="devicetypeId" id="" class="default-one-input" data-rule-required="true">
						 <option value="">请选择</option>
						 <c:forEach items="${typeList }" var="type">
							<option value="${type.id }" <c:if test="${type.id == vmsAsset.devicetypeId }">selected="selected"</c:if>>${type.name }</option>
						 </c:forEach>
				</select>
				<label class="default-one-lable">厂商</label> 
				<select name="vendorId" id="" class="default-one-input" data-rule-required="true">
						 <option value="">请选择</option>
						 <c:forEach items="${vendorList }" var="vendor">
							<option value="${vendor.vendorId }" <c:if test="${vendor.vendorId == vmsAsset.vendorId }">selected="selected"</c:if>>${vendor.name }</option>
						 </c:forEach>
				</select>
				<label class="default-one-lable">设备名称</label> 
				<input type="text" class="default-one-input" id="" name="keywords" placeholder="模糊查询" value="${vmsAsset.keywords}">
				<label class="default-one-lable">设备级别</label> 
				<input type="text" class="default-one-input" id="" name="level" value="${vmsAsset.level}">
				<!-- <label class="default-one-lable">包含子组织</label> 
				<input type="checkbox" class="default-one-input" id="" name="" value=""> -->
			</p>
			<button class="default-one-btn btn-green" onclick="doSearch()">查询</button>
		</form>
	</div>
	<div class="tabcenter">
    	<div>
			<span class="span-list">查询列表</span>
			<p class="pull-right" style="line-height: 40px;">
				<c:if test="${fn:contains(buttonList,'addSpan')}">
					<span name="addSpan" style="text-align:center;">
						<a class="default-one-btn site-button-method font-color"  data-method="addAsset" >新增</a>
					</span>
				</c:if>
				<c:if test="${fn:contains(buttonList,'delSpan')}">
					<span name="delSpan" style="text-align:center;">
						<a class="default-one-btn site-button-method font-color"  data-method="delAsset" >删除</a>
					</span>
				</c:if>
				<c:if test="${fn:contains(buttonList,'setBatchLevel')}">
					<span name="setBatchLevel" style="text-align:center;">
						<a class="default-one-btn site-button-method font-color"  data-method="setBatchLevel" >批量设置设备级别</a>
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
								onclick="checkAll(this,'form','checknotall')" style="border: 0 !important;" />
						</nobr>
					</th>
					<th>序号</th>
					<th>设备名称</th>
					<th>设备类型</th>
					<th>厂商</th>
					<th>产品名称</th>
					<th>设备型号</th>
					<th>资产编码</th>
					<th>资产状态</th>
					<th>设备级别</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty list}">
					<tr align="center">
						<td colspan="12" style="color: red;" bgcolor="#FFFFFF">没有查询到数据！</td>
					</tr>
				</c:if>
				<c:if test="${!empty list}">
					<c:forEach var="u" items="${list }" varStatus="status">
						<tr class="forsearch">
							<td class="forsearch">
								<input type="checkbox" name="check" value="<c:out value='${u.assetId}'/>" style="border: 0 !important;" />
							</td>
							<td> ${status.index+1} </td>
							<td class="forsearch">
								<c:out value="${u.name}"/>
							</td>
							<td class="forsearch">
								<c:out value="${u.deviceTypeName}"/>
							</td>
							<td class="forsearch">
								<c:out value="${u.vendorName}"/>
							</td>
							<td class="forsearch">
								<c:out value="${u.productName}"/>
							</td>
							<td class="forsearch">
								<c:out value="${u.modelName}"/>
							</td>
							<td class="forsearch">
								<c:out value="${u.code}"/>
							</td>
							<td class="forsearch">
								 <c:if test="${1 == u.status }"><c:out value="运行"/></c:if>
								 <c:if test="${2 == u.status }"><c:out value="损坏"/></c:if>
								 <c:if test="${3 == u.status }"><c:out value="维修"/></c:if>
								 <c:if test="${4 == u.status }"><c:out value="报废"/></c:if>
								 <c:if test="${5 == u.status }"><c:out value="库存"/></c:if>
								 <c:if test="${6 == u.status }"><c:out value="待安装"/></c:if>
								 <c:if test="${7 == u.status }"><c:out value="已安装"/></c:if>
								 <c:if test="${8 == u.status }"><c:out value="已调试"/></c:if>
							</td>
							<td class="forsearch">
								<c:out value="${u.level}"/>
							</td>
							<td>
								<c:if test="${fn:contains(buttonList,'detailSpan')}">
									<span name="detailSpan" style="text-align:center;">
										<a class="site-button-method" id="${u.assetId}" data-method="viewAsset">查看&nbsp;</a>
									</span>
								</c:if>
								<c:if test="${fn:contains(buttonList,'editSpan')}">
									<span name="editSpan" style="text-align:center;">
										<a class="site-button-method" id="${u.assetId}" data-method="modifyAsset">|&nbsp;编辑&nbsp;</a>
									</span>
								</c:if>
								<c:if test="${fn:contains(buttonList,'associateSpan')}">
									<span name="associateSpan" style="text-align:center;">
										<a class="site-button-method" id="${u.assetId}" data-method="associateCamera">|&nbsp;关联设备&nbsp;</a>
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
  		
  	  //pageFuntAutyBtn('${sys_ctx}/getFuntBtnList.action',parent.funtId);//控制按钮权限
  	
	  function doSearch() {
			setPage(); 
			frm.submit(); 	
	  }
  	  function doClear() {
  			clearForm("frm");
  			doSearch();
  	  }
  	  //快速检索
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
  	  function checkAll(obj){
		     var  All  =  document.getElementsByName("check");  
		     for  (var  i=0;  i<All.length;  i++){  
		         All[i].checked  =  obj.checked;  
		     }  
		     if(document.getElementById("checknotall"))
		    	 document.getElementById("checknotall").checked=false;
	  }
  	  
	  var funtType = "add";//判断是新增还是编辑
	  layui.use('layer', function(){ 
			var $ = layui.jquery, layer = layui.layer; 
			//触发事件
			  var active = {
				  //添加资产
				  addAsset: function(){
					  funtType = "add";
					  var orgId = $('#orgId').val();
				      if(!orgId) {
				    	  layer.msg('请选择组织节点！',{time: 2000, icon:5});
			    		  return;
				      }
				      var orgName = $('#orgName').val();
				      layer.open({
				        type: 2 
				        ,id:'addAsset'
				        ,title: '新增'
				        ,area: ['800px', '500px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,offset: ''
				        ,content: '${sys_ctx}/asset/openOperateAsset.action?orgId='+orgId+'&funtType='+funtType
				        ,end: function(){
				        	//交给子页面刷新
				        }
				      });
			    },
			    //修改资产
			    modifyAsset: function(){
			    	var that = this; 
			    	funtType = "edit";
			    	var assetId = that.id;//获取该用户主键id
			    	var orgId = $('#orgId').val();
			    	layer.open({
				        type: 2 
				        ,id:'modifyAsset'
				        ,title: '编辑'
				        ,area: ['800px', '500px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,offset: ''
				        ,content: '${sys_ctx}/asset/openEditAsset.action?assetId='+assetId+'&funtType='+funtType
				        ,end: function(){
				        	//交给子页面刷新
				        }
				      });
			    },
			    //查看
			    viewAsset: function(){
			    	var that = this; 
			    	var assetId = that.id;//获取该条的主键id
			    	var orgId = $('#orgId').val();
			    	layer.open({
				        type: 2 
				        ,id:'viewAsset'
				        ,title: '查看'
				        ,area: ['800px', '500px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,offset: ''
				        ,content: '${sys_ctx}/asset/detailAsset.action?assetId='+assetId
				        ,end: function(){
				        	//查看直接关闭，调用方法没有必要
						}
				      });
			    },
			    //关联设备
			    associateCamera: function(){
			    	var that = this; 
			    	var assetId = that.id;//获取该条的主键id
			    	layer.open({
				        type: 2 
				        ,id:'viewAsset'
				        ,title: '关联设备'
				        ,area: ['600px', '480px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,offset: ''
				        ,content: '${sys_ctx}/asset/toAssociateCamera.action?assetId='+assetId
				        ,end: function(){
				        	//查看直接关闭，调用方法没有必要
						}
				      });
			    },
			    //删除用户(逻辑删除)
			    delAsset: function(){
			    	//获取部门id
			    	var orgId = $("#orgId").val();
			    	if (!orgId) {
			    		layer.msg('未选择部门！',{time: 2000, icon:5});
			    		return;
			    	}
			    	var assetIds = [];
			    	$("input[name='check']:checked").each(function(){
			    		assetIds.push($(this).val());
			    	});
					if(assetIds != '' && assetIds != null) {
						//判断角色下是否有用户
						var deleteAvailableFlag = false;//是否可以删除
						$.ajax({
							type: "POST",
							async: false,//同步
							url: '<%=basePath%>asset/checkRelatedAssets.action',
							data: {'assetIds':assetIds , 'orgId':orgId},
							dataType:'json',
							cache: false,
							success: function(data){
								if(data.success=="true"){
									//可以删除
									deleteAvailableFlag = true;
								}else{
									//不能删除
									deleteAvailableFlag = false;
						   			if (!deleteAvailableFlag) {
						   				var msg = data.msg;
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
							layer.confirm('确定要删除该信息吗？', {title:'提示信息',
						  		  btn: ['确定', '取消'],icon: 3
						  		}, function(index, layero){//确定按钮回执函数
						  			$.post('${sys_ctx}/asset/deleteBatch.action',{'assetIds':assetIds, 'orgId':orgId},
						  					function(data){
								  				if(data.success=="true"){
								  					window.location.reload(); //刷新本页面
							  					} else {
							  						layer.alert(data.msg, {title:'提示信息',icon: 5});
							  					}
						  					},'json');
						  		}, function(index){//取消按钮回执函数
						  		});
						}
					} else {
						layer.msg('请选择设备！',{time: 2000, icon:5});
					}
			    },
			    //打开授权页面
			    setBatchLevel: function(){
			    	var assetIds = [];
			    	$("input[name='check']:checked").each(function(){
			    		assetIds.push($(this).val());
			    	});
			   		if(assetIds != '' && assetIds != null) {
				   		layer.open({
							title: ['批量设置级别', 'background-color:#d0e3f0; color:#888889;'],
							anim: 'scale',
							content: '<input type="text" id="levelName" style="width:100%;height:30px;padding-left:10px;border-radius:3px;" placeholder="输入级别名称"/>',
							btn: ['确认','取消'],
							yes: function(index) {
								var levelName = $("#levelName").val();
								//判断名字是否为空
								$.ajax({
									type: "POST",
									url: '<%=basePath%>asset/setLevelBatch.action',
							    	data: {'assetIds':assetIds, 'levelName':levelName},
									dataType:'json',
									cache: false,
									success: function(data){
										if(data.success){
											window.location.reload(); //刷新本页面
										}else{
											var err = data.message;
											layer.open({
												title: ['提示', 'background-color:#d0e3f0; color:#888889;'],
												anim: 'scale',
												content: '设置失败，错误信息为：' + err + '！',
												btn: ['确认'],
												
												yes: function(index) {
													layer.close(index);
												},
											});
										 }
									},
									error: function(data){
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
								document.getElementById("typeName").blur();
								layer.close(index);
							},
						});
			   		} else {
						layer.msg('请先选择设备！',{time: 2000, icon:5});
					}
			    },
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
