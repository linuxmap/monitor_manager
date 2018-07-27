<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户列表-1</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%> 
	<!--列表公共样式  -->
	<link rel="stylesheet" href="${sys_ctx}/css/default-table.css" type="text/css"></link>
	<link rel="stylesheet" href="${sys_ctx}/css/default-page.css" type="text/css"></link>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.core.min.js"></script>
	<link rel="stylesheet" href="${sys_ctx}/plug/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">  
	<link rel="stylesheet" href="${sys_ctx}/css/tree/treeStyle.css" type="text/css">  
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.exhide.min.js"></script>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.excheck.min.js"></script>
	<style>
		input:focus::-webkit-input-placeholder{
            text-indent: -999em;
            z-index: -20;
	     }
	    .layui-layer-btn {
		    text-align: center !important;
		}
		.default-one-input{
			    width: 120px;
		    height: 24px;
		    font-size: 12px;
		    border-radius: 3px;
		    padding-left: 5px;
		    border: 1px solid #a2a7a6;
		    line-height: 24px;
		}
		.table>thead>tr>th {
		    padding: 8px 3px!important;
		    line-height: 1.42857143;
		    vertical-align: top;
		    border-top: 1px solid #ddd;
		    font-size: 12px;
		    background: #3997f4;
		    color: #fff;
		    border-bottom: 0;
		}
	</style>
	<!--[if lt IE 9]>
		<style>
		input{
				border: 0 !important;
			}
		</style>
	<![endif]-->
  </head>
  <body class="default-one-body">
  	<div class="table-top">
		<form action="${sys_ctx}/assetIntegration/queryAssets.action" name="frm" id="frm" method="post">
			<input type="hidden" id="funtId" name="funtId" value="${funtId }" />
			<input type="hidden" id="orgId" name="orgId" value="${vmsAsset.orgId}" /> 
			<input type="hidden" id="orgName" name="orgName" value="${vmsAsset.orgName}" /> 
			<input type="hidden" name="rows" id="rows" value='${myPage.number}' />
			<input type="hidden" name="page" id="page" value='${myPage.intPage}' />
			<p class="tab-p" style=width:800px;>
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
				<input type="text" class="default-one-input" id="" name="keywords" value="${vmsAsset.keywords}">
				<label class="default-one-lable">是否可见</label> 
				<select name="visibleFlagX" class="default-one-input">
					<option value="" <c:if test="${empty vmsAsset.visibleFlagX }">selected="selected"</c:if>>全部</option>
					<option value="true" <c:if test="${true == vmsAsset.visibleFlagX }">selected="selected"</c:if>>可见</option>
					<option value="false" <c:if test="${false == vmsAsset.visibleFlagX }">selected="selected"</c:if>>不可见</option> 
				</select>
				<!-- <label class="default-one-lable">包含子组织</label> 
				<input type="checkbox" class="default-one-input" id="" name="" value=""> -->
			</p>
			<button class="default-one-btn btn-green" onclick="doSearch()">查询</button>
		</form>
	</div>
	<div class="tabcenter">
		<input type="hidden" id="assetIdsAlone"/>
		<input type="hidden" id="assetArrayMix"/>
		<input type="hidden" id="parOrgIdMix"/>
		<input type="hidden" id="orgIdsToBeAssociate"/><!-- 用于  将组织及其所有设备关联到 的隐藏域传值，将选择的组织节点数组关联到单选框选择的组织节点下 -->
		<input type="hidden" id="orgParIdsToBeAssociate"/><!-- 用于  将组织及其所有设备关联到 的隐藏域传值，将选择的组织节点的父id数组关联到单选框选择的组织节点下 -->
    	<div>
			<span class="span-list">查询列表</span>
			<span class="pull-right" style="line-height: 40px;">
				<c:if test="${fn:contains(buttonList,'associateAsset')}">
					<span name="associateAsset" style="text-align:center;">
						<a class="default-one-btn site-button-method font-color"  data-method="associateAsset" >设备关联到</a>
					</span>
				</c:if>
				<c:if test="${fn:contains(buttonList,'disAssociateAsset')}">
					<span name="disAssociateAsset" style="text-align:center;">
						<a class="default-one-btn site-button-method font-color"  data-method="disAssociateAsset" >取消设备关联</a>
					</span>
				</c:if>
				<c:if test="${fn:contains(buttonList,'associateOrgAsset')}">
					<span id="associateOrgAsset" name="associateOrgAsset" style="text-align:center;">
						<a class="default-one-btn site-button-method font-color"  data-method="associateOrgAsset" >将组织及其所有设备关联到</a>
					</span>
				</c:if>
				<c:if test="${fn:contains(buttonList,'setBatchAvailable')}">
					<span name="setBatchAvailable" style="text-align:center;">
						<a class="default-one-btn site-button-method font-color"  data-method="setBatchAvailable" >批量设置设备可见性</a>
					</span>
				</c:if>
			</span>
	   </div>
		<table class="table table-hover">
			<thead>
				<tr class="tr_center">
					<th>
						<nobr>
							<input id="checkAll" title="全选" name="checkAll" type="checkbox"
								onclick="checkAll(this,'form','checknotall')"  style="border: 0 !important;"/>
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
					<th>是否可见</th>
					<th>数据来源</th>
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
								<input type="checkbox" name="check" value="<c:out value='${u.assetId}'/>"  style="border: 0 !important;"/>
								<input type="hidden" id="${u.assetId}_${u.orgId }" name="sourceFlag" value="${u.sourceFlagX }"/>
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
							<td class="forsearch">
								<c:if test="${u.visibleFlagX}"><c:out value="可见"/></c:if>
								<c:if test="${!empty u.visibleFlagX && !u.visibleFlagX}"><c:out value="不可见"/></c:if>
								<c:if test="${empty u.visibleFlagX}"><c:out value="未绑定"/></c:if>
							</td>
							<td class="forsearch">
								<c:choose>
									<c:when test="${1 == u.sourceFlagX }"><c:out value="原始数据"/></c:when>
									<c:when test="${2 == u.sourceFlagX }"><c:out value="复制数据"/></c:when>
									<c:otherwise><c:out value="未完整更新"/></c:otherwise>
								</c:choose>
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
  		
  	 // pageFuntAutyBtn('${sys_ctx}/getFuntBtnList.action',parent.funtId);//控制按钮权限
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
				  //设备关联到
				  associateAsset: function(){
					  var assetidArr = [];
			    	  $("input[name='check']:checked").each(function(){
			    		 assetidArr.push($(this).val());
			    	  });
			    	  if(assetidArr == '' || assetidArr == null){
			    	  	layer.msg('请选择设备！',{time: 2000, icon:6});
			    	  	return;
			    	  }
			    	  var assetIds = assetidArr.join(",");
			    	  $("#assetIdsAlone").val(assetIds);
				      layer.open({
				         type : 2
				        ,id: 'associateAsset'
				        ,title : '设备关联到'
				        ,area : ['300px', '480px']
				        ,shade : 0.2
				        ,maxmin : false
				        ,offset : ''
				        ,content : '${sys_ctx}/assetIntegration/openAssociateAssetOrg.action'
				        ,success : function(layero,index){
				        	
				        }
				        ,end: function(){
				        	if(parent.page_sign==1){refresh();}//刷新页面
				        }
				      });
			    },
			  	//取消关联
			    disAssociateAsset: function(){
			    	var assetIds = [];
			    	$("input[name='check']:checked").each(function(){
			    		assetIds.push($(this).val());
			    	});
			    	var orgId = $('#orgId').val();
				    if(!orgId) {
				    	layer.msg('请选择组织节点！',{time: 2000, icon:5});
			    		return;
				    }
					if(assetIds != '' && assetIds != null) {
				    	//验证数据来源，如果是原始数据，则return，复制数据则继续
				    	var a = false; 
				    	for(var i=0; i<assetIds.length; i++){
				    		var hiddenId = assetIds[i]+"_"+orgId;
				    		var sourceF = $("#"+hiddenId).val();
				    		if(sourceF == 1){
				    			a = true;
				    			break;
				    		}
				    	}
				    	if(a){
				    		layer.msg("请勿取消原始数据",{time: 2000, icon:6});
							return;
				    	}
						layer.confirm('确定要取消关联吗？', {title:'提示信息',
					  		  btn: ['确定', '取消'],icon: 3
					  		}, function(index, layero){//确定按钮回执函数
					  			$.post(
					  					'${sys_ctx}/assetIntegration/cancelAssociation.action',
					  					{'assetIds':assetIds, 'orgId':orgId},
					  					function(data){
							  				if(data.success=="true"){
							  					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
												//parent.layer.close(index);  // 关闭layer
												location.reload(); //刷新父的父页面
						  					} else {
						  						layer.msg(data.msg, {title:'提示信息',icon: 5});
						  					}
					  					},
					  					'json'
					  				   );
					  		}, function(index){//取消按钮回执函数
					  		});
					} else {
						layer.msg('请选择设备！',{time: 2000, icon:6});
					}
			    },
			    //将组织及其所有设备关联到
			    associateOrgAsset: function(){
					  //对于组织结构的节点，采用勾选框来获取数据，不使用被选中的组织，这样太过模糊
			    	  //获取父页面选中的节点
					  var nodes = window.parent.$.fn.zTree.getZTreeObj("tree").getCheckedNodes(true);
					  var orgIds = [];
					  var orgParIds = [];
					  if (nodes && nodes.length > 0) {
						  for (var i=0; i<nodes.length; i++) {
							  orgIds.push(nodes[i].id);
							  orgParIds.push(nodes[i].pId);
						  }
					  } else {
					  	  layer.msg('请勾选组织节点！',{time: 2000, icon:6});
						  return;
					  }
					  var orgArrayMix = orgIds.join(",");
					  var orgParIdArrayMix = orgParIds.join(",");
					  //给要投奔的组织的隐藏域赋值
					  $("#orgIdsToBeAssociate").val(orgArrayMix);
					  $("#orgParIdsToBeAssociate").val(orgParIdArrayMix);
				      layer.open({
				         type: 2 
				        ,id:'associateAsset'
				        ,title: '设备关联到'
				        ,area: ['300px', '480px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,offset: ''
				        ,content: '${sys_ctx}/assetIntegration/openAssociateAssetOrgMix.action'
				        ,end: function(){
				        	window.parent.location.reload();
				        }
				      });
			    },
			    //批量设置设备可见性
			    setBatchAvailable: function(){
			    	var orgId = $('#orgId').val();
				    if(!orgId) {
				    	layer.msg('请勾选组织节点！',{time: 2000, icon:6});
			    		return;
				    }
			    	var assetIds = [];
			    	$("input[name='check']:checked").each(function(){
			    		assetIds.push($(this).val());
			    	});
			   		if(assetIds != '' && assetIds != null) {
				   		layer.open({
							title: ['批量设置设备可见性', 'background-color:#d0e3f0; color:#888889;'],
							anim: 'scale',
							content: '<span style="padding:0px 10px 0px 10px;">可见</span><input type="radio" name="visibleOrg" value="1" style="width:20px;height:20px;border-radius:3px;margin-top:0;"/><span style="padding:0px 10px 0px 10px;">不可见</span><input type="radio" name="visibleOrg" value="0" style="width:20px;height:20px;border-radius:3px;margin-top:0;"/>',
							btn: ['确认','取消'],
							yes: function(index) {
								var visibleOrg = $("input[name='visibleOrg']:checked").val();
								if(!visibleOrg){
									layer.msg("未勾选",{time: 2000, icon:6});
									return;
								}
								$.post(
				  					'${sys_ctx}/assetIntegration/setAssetVisible.action',
				  					 {'assetIds':assetIds, 'visibleOrg':visibleOrg ,'orgId':orgId},
				  					function(data){
						  				if(data.success=="true"){
						  					window.location.reload();
					  					} else {
					  						layer.msg(data.msg, {title:'提示信息',icon: 5});
					  					}
				  					},
				  					'json'
					  		    );
							},
							no: function(index){
								document.getElementById("typeName").blur();
								layer.close(index);
							},
						});
			   		} else {
						layer.msg('请先勾选设备！',{time: 2000, icon:6});
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
