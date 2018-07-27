<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>列表</title>
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
	</style>
  </head>
  <body class="default-one-body">
	<div class="table-top" >
		<form action="${sys_ctx}/dictionaryItem/queryDictionaryItem.action" name="frm" id="frm"
			method="post">
			<input type="hidden" name="rows" id="rows" value='${myPage.number}' />
			<input type="hidden" name="page" id="page" value='${myPage.intPage}' />
			<input type="hidden" name="dictionaryId" id="dictionaryId" value='${dictionaryItem.dictionaryId}' />
			<input type="hidden" name="dictionaryName" id="dictionaryName" value='${dictionaryName}' />
			<p class="tab-p">
				<label class="default-one-lable">名称</label> 
				<input type="text" class="default-one-input" id=itemName name="itemName" value="${dictionaryItem.itemName}">
				<input class="default-one-input" id="quickSearch" type="text" value=""  placeholder="快速检索" />
			</p>
			<button class="default-one-btn btn-green" onclick="doSearch()">查询</button>
			<button class="default-one-btn-top" onClick="doClear()" >重置</button>
		</form>
	</div>
	<div class="tabcenter">
    	<div>
			<span class="span-list">查询列表</span>
			<p class="pull-right">
				<span name="addSpan" style="text-align:center; display:none;">
					<button class="default-one-btn site-button-method font-color"  data-method="addDictionaryItem" >新增</button>
				</span>
			</p>
	   </div>
		<table class="table table-hover">
			<thead>
				<tr class="tr_center">
					<th >序号</th>
					<!-- <th >字典条目编码</th> -->
					<th >厂家名称</th>
					<th >是否有效</th>
					<th >操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty list}">
					<tr align="center">
						<td colspan="8" style="color: red;" bgcolor="#FFFFFF">没有查询到数据！</td>
					</tr>
				</c:if>
				<c:if test="${!empty list}">
					<c:forEach var="s" items="${list }" varStatus="status">
						<tr class="forsearch">
							<td> ${status.index+1} </td>
							<%-- <td class="forsearch">
								<c:out value="${s.vendorId}"/>
							</td> --%>
							<td class="forsearch">
								<c:out value="${s.name}"/>
							</td>
							<td class="forsearch">
								<c:if test="${s.flag == 1}">有效</c:if>
								<c:if test="${s.flag == 0}">无效</c:if>
							</td>
							<td>
								<a class="site-button-method" id="${s.vendorId}" data-method="viewDictionaryItem">查看&nbsp;</a>
								<span name="editSpan" style="text-align:center; display:none;">
									<a class="site-button-method" id="${s.vendorId}" data-method="updateDictionaryItem">|&nbsp;编辑&nbsp;</a>
								</span>
								<span name="copySpan" style="text-align:center; display:none;">
									<a class="site-button-method" id="${s.vendorId}" data-method="copyDictionaryItem">|&nbsp;复制&nbsp;</a>
								</span>
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
  
  	  pageFuntAutyBtn('${sys_ctx}/getFuntBtnList.action',parent.funtId);//控制按钮权限	  
  
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
	  var funtType = null;//判断是新增还是编辑
	  layui.use('layer', function(){ 
			var $ = layui.jquery, layer = layui.layer; 
			//触发事件
			  var active = {
				addDictionaryItem: function(){
				      funtType = "add";
				      var dictionaryId = $('#dictionaryId').val();
				      if(!dictionaryId) {
				    	  layer.msg('请选择功能树节点！',{time: 2000, icon:5});
			    		  return;
				      }
				      parent.layer.open({
				        type: 2 
				        ,id:'addDictionaryItem'
				        ,title: '新增'
				        ,area: ['480px', '370px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,offset: ''
				        ,content: '${sys_ctx}/dictionaryItem/toOpenFactoryItem.action?funtType='+funtType
				        ,end: function(){
				        	if(parent.page_sign==1){refresh();}//刷新页面
				        }
				      });
			    },
			    updateDictionaryItem: function(){
			    	var that = this; 
			    	funtType = "edit";
			    	var vendorId = that.id;//获取该条组织机构的主键id
				    layer.open({
				        type: 2 
				        ,id:'updateDictionaryItem'
				        ,title: '编辑'
				        ,area: ['480px', '370px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,offset: ''
				        ,content: '${sys_ctx}/dictionaryItem/toOpenFactoryItem.action?vendorId='+vendorId+'&funtType='+funtType
				        ,end: function(){
				        	if(parent.page_sign==1){refresh();}//刷新页面
				        }
				      });
			    },
			    viewDictionaryItem: function(){
			    	var that = this;
			    	funtType = "view";
			    	var vendorId = that.id;//获取该条组织机构的主键id
				   layer.open({
				        type: 2 
				        ,id:'viewDictionaryItem'
				        ,title: '查看'
				        ,area: ['370px', '260px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,offset: 'center'
				        ,content: '${sys_ctx}/dictionaryItem/toOpenFactoryItem.action?vendorId='+vendorId+'&funtType='+funtType
				        ,end: function(){
				        	if(parent.page_sign==1){refresh();}//刷新页面
				        }
				      });
			    },
			    copyDictionaryItem: function(){
			    	var that = this; 
			    	funtType = "copy";
			    	var vendorId = that.id;//获取该条组织机构的主键id
				    parent.layer.open({
				        type: 2 
				        ,id:'copyDictionaryItem'
				        ,title: '复制'
				        ,area: ['480px', '370px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,offset: ''
				        ,content: '${sys_ctx}/dictionaryItem/toOpenFactoryItem.action?vendorId='+vendorId+'&funtType='+funtType
				        ,end: function(){
				        	if(parent.page_sign==1){refresh();}//刷新页面
				        }
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
 
