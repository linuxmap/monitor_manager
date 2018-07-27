<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>组织机构列表</title>
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
	<!--[if lt IE 9]>
		<style>
			
		</style>
	<![endif]-->
  </head>
  <body class="default-one-body">
	<div class="table-top">
		<form action="${sys_ctx}/org/queryOrgs.action" name="frm" id="frm" method="post">
			<input type="hidden" id="funtId" name="funtId" value="${funtId }" />
			<input type="hidden" name="rows" id="rows" value='${myPage.number}' />
			<input type="hidden" name="page" id="page" value='${myPage.intPage}' />
			<input type="hidden" id="orgParId" name="parentId" value="${parentId }" /> 
			<p class="tab-p" style="width: 290px;">
				<input type="checkbox" class="default-one-input" style="border:0 !important"  id="containBranch" value="1"  ${containBranch eq 1 ? "checked" : "" } name="containBranch" >
				<label class="default-one-lable" for="containBranch">包含子组织</label>			
				<label class="default-one-lable">组织机构</label> 
				<input type="text" class="default-one-input" id="keywords" placeholder="名称或拼音首字母" name="keywords" value="${sysOrg.keywords}">
			</p>
			<button class="default-one-btn btn-green" onclick="doSearch()">查询</button>
			<button class="default-one-btn-top" onClick="doClear()">重置</button>
		</form>
	</div>
	<div class="tabcenter">
    	<div>
			<span class="span-list">查询列表</span>
			<div class="pull-right"  style="line-height: 40px;">
				<c:if test="${fn:contains(buttonList,'addSpan')}">
					<span name="addSpan" style="text-align:center;">
						<a class="default-one-btn site-button-method font-color"  data-method="addOrg" >新增</a>
					</span>
				</c:if>
				<c:if test="${fn:contains(buttonList,'delSpan')}">
					<span name="delSpan" style="text-align:center;">
						<a class="default-one-btn site-button-method font-color" data-method="delOrg" >批量删除</a>
					</span>
				</c:if>
			</div>
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
					<th>组织机构名</th>
					<th>组织机构编码</th>
					<th>节点顺序</th>
					<th>备注</th>
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
					<c:forEach var="s" items="${list }" varStatus="status">
						<tr class="forsearch">
							<td class="forsearch">
								<input type="checkbox" name="check" value="<c:out value='${s.orgId}'/>" style="border:0 !important"/>
							</td>
							<td> ${status.index+1} </td>
							<td class="forsearch">
								<c:out value="${s.orgName}"/>
							</td>
							<td class="forsearch">
								<c:out value="${s.pinyin}"/>
							</td>
							<td class="forsearch">
								<c:out value="${s.orderNum}"/>
							</td>
							<td class="forsearch">
								<c:out value="${s.description}"/>
							</td>
							<td>
								<a class="site-button-method" id="${s.orgId}" data-method="viewOrg">查看&nbsp;</a>
								<c:if test="${fn:contains(buttonList,'editSpan')}">
									<span name="editSpan" style="text-align:center;">
										<a class="site-button-method" id="${s.orgId}" data-method="modifyOrg">|&nbsp;编辑&nbsp;</a>
									</span>
								</c:if>
								<c:if test="${fn:contains(buttonList,'copySpan')}">
									<span name="copySpan" style="text-align:center;">
										<a class="site-button-method" id="${s.orgId}" data-method="copyOrg">|&nbsp;复制&nbsp;</a>
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
	  //pageFuntAutyBtn('${sys_ctx}/getFuntBtnList.action',parent.funtId);//控制按钮权限 注释掉
	  function doSearch() {
			setPage();   	
			frm.submit(); 	
	  }
  	  function doClear() {
  			clearForm("frm");
  			doSearch();
  	  }
  	  
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
				  addOrg: function(){
					  funtType = "add";
				      var orgParId = "-1";//跟节点orParId为-1
				      var orgLevel = 1;//跟节点orgLevel为0
				      
				      if(parent.treeObj && parent.treeObj.getNodes() && parent.treeObj.getNodes().length>0) {
				    	  if(parent.treeObj.getSelectedNodes().length<=0) {
				    		  layer.msg('请选择功能树节点！',{time: 2000, icon:5});
				    		  return;
				    	  }
				    	  orgParId = $('#orgParId').val();
				    	  //var orgParLevel = $('#orgParLevel').val();
					      //orgLevel = parseInt(orgParLevel);//子节点level等于父节点level+1
				      }
				      var orgParName = $('#orgParName').val();
				      layer.open({
				        type: 2 
				        ,id:'addOrg'
				        ,title: '新增'
				        ,area: ['520px', '470px']
				        ,shade: 0.2
				      /*   ,move: false */
				        ,maxmin: false
				     /*    ,offset:  ['50px', '50px'] */
				        ,content: '${sys_ctx}/org/openOperateOrg.action?parentId='+orgParId+'&orgLevel='+orgLevel+'&funtType='+funtType
				        ,end: function(){
				        	doSearch(); //刷新父页面
				        }
				      });
			    }, 
			    modifyOrg: function(){
			    	var that = this; 
			    	funtType = "edit";
			    	var id = that.id;//获取该条组织机构的主键id
			    	layer.open({
				        type: 2 
				        ,id:'modifyOrg'
				        ,title: '编辑'
				        ,area: ['480px', '450px']
				        ,shade: 0.2
				        ,maxmin: false
				       /*  ,move: false
				        ,offset:  ['50px', '50px'] */
				        ,content: '${sys_ctx}/org/openOperateOrg.action?orgId='+id+'&funtType='+funtType
				        ,end: function(){
				        	doSearch(); //刷新父页面
				        }
				      });
			    },
			    viewOrg: function(){
			    	var that = this; 
			    	funtType = "view";
			    	var id = that.id;//获取该条组织机构的主键id
			    	layer.open({
				         type: 2 
				        ,id:'viewOrg'
				        ,title: '查看'
				        ,area: ['480px', '450px']
				        ,shade: 0.2
				        ,maxmin: false
				  /*       ,move: false
				        ,offset:  ['50px', '50px'] */
				        ,content: '${sys_ctx}/org/openOperateOrg.action?orgId='+id+'&funtType='+funtType
				        ,end: function(){
				        	//子页面处理刷新
				        }
				      });
				   
			    },
			    copyOrg: function(){
			    	var that = this; 
			    	funtType = "copy";
			    	var id = that.id;//获取该条组织机构的主键id
			    	layer.open({
				        type: 2 
				        ,id:'copyOrg'
				        ,title: '复制'
				        ,area: ['480px', '450px']
				        ,shade: 0.2
				        ,maxmin: false
				     /*    ,move: false
				        ,offset:  ['50px', '50px'] */
				        ,content: '${sys_ctx}/org/openOperateOrg.action?orgId='+id+'&funtType='+funtType
				        ,end: function(){
				        	//子页面处理刷新
				        }
				      });
			    },
			    delOrg: function(){
			    	var orgIds = [];
			    	$("input[name='check']:checked").each(function(){
			    		orgIds.push($(this).val());
			    	});
					if(orgIds != '' && orgIds != null) {
						layer.confirm('确定要删除该信息吗？', { title:'提示信息',
					  		  btn: ['确定', '取消'],icon: 3
					  		}, function(index, layero){//确定按钮回执函数
					  			$.post('${sys_ctx}/org/deleteBatch.action',{'orgIds':orgIds},
					  					function(data){
							  				if(data.success=="true"){
							  					parent.location.reload();
						  					} else {
						  						layer.alert(data.msg, {title:'提示信息',icon: 5});
						  					}
					  					},'json');
					  		}, function(index){//取消按钮回执函数
					  		});
					} else {
						layer.msg('请先选择要删除的内容！',{time: 2000, icon:5});
					}
			    }
			
			 };
			
			  $('.site-button-method').on('click', function(){
				    var othis = $(this), method = othis.data('method');
				    active[method] ? active[method].call(this, othis) : '';
			  });
		});
	  function refresh() {
		  //重新加载tree,并设置当前选中的对象
		  parent.loadTree();
		  var node = parent.treeObj.getNodeByParam("id", '${sysOrg.parentId}', null);
		  if(node)
		  	parent.treeObj.selectNode(node);
		  //自定义action
		  $("#frm").attr("action","${sys_ctx}/org/queryOrgs.action");
		  setPage();   	
		  frm.submit();	  
	  }
	  
  </script>
 </html>
 
