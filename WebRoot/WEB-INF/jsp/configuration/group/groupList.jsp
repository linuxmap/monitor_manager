<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>告警级别配置</title>
	<%@include file="/WEB-INF/jsp/common/common.html"%> 
	<!--列表公共样式  -->
	<link rel="stylesheet" href="${sys_ctx}/css/default-table.css" type="text/css"></link>
	<link rel="stylesheet" href="${sys_ctx}/css/default-page.css" type="text/css"></link>
	<style>
		input:focus::-webkit-input-placeholder{
            text-indent: -999em;
            z-index: -20;
	     }
	     .top{
			width:100%;
			height:42px;
			position:absolute; 
			top:10px; left:0;
			padding:0 10px;
		}
		.action{
		    height: 41px!important;
		    border-bottom: 0;
		    color: #555!important;
		    border-top: 3px solid #3997f4;
		    font-weight: 600!important;
		    background: #fff;
		}
		.center-tab{
			width:100%; 
			position:absolute;
		    top:50px; 
		    bottom:0;
		}
		
		.top ul {
		    width: 100%;
		    height: 40px;
		    border: 1px solid #dedede;
		    background: #f9f9f9;
		}
		.top ul li {
		    float: left;
		    border-right: 1px solid #dedede;
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
	</style>
</head>
	<body>
		<div class="top" style="background: #fff">
			<ul>
				<li><!-- 可优化，动态接收参数 -->
					<a href="${sys_ctx}/alarm/queryAlarms.action?funtId=${funtId}">告警等级设置</a>
				</li>
				<li>
					<a class="action" href="${sys_ctx}/group/queryGroups.action?funtId=${funtId}">告警分组设置</a>
				</li>
			</ul>
		</div>
		<div class="table-top">
			<form action="${sys_ctx}/group/queryGroups.action?funtId=${funtId}" name="frm" id="frm" method="post">
				<input type="hidden" id="funtId" name="funtId" value="${funtId }" />
				<input type="hidden" name="rows" id="rows" value='${myPage.number}' />
				<input type="hidden" name="page" id="page" value='${myPage.intPage}' />
			</form>
		</div>
		<div class="center-tab">
			<div class="tabcenter">
				<div>
					<p class="pull-right" style="line-height: 40px;">
						<c:if test="${fn:contains(buttonList,'addGroupSpan')}">
							<span name="addGroupSpan" style="text-align:center;">
								<a id="add"  class="default-one-btn font-color">新增</a>
							</span>
						</c:if>
					</p>
				</div>
			<table class="table table-hover">
			  <thead>
			    <tr>
			      <th>序号</th>
			      <th>分组名称</th>
			      <th>操作</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:if test="${empty list}">
					<tr align="center">
						<td colspan="4" style="color: red;" bgcolor="#FFFFFF">没有查询到数据！</td>
					</tr>
				</c:if>
				<c:if test="${!empty list}">
					<c:forEach var="model" items="${list }" varStatus="status">
						<tr class="forsearch">
							<td> ${status.index+1} </td>
							<td class="forsearch"> ${model.name}</td>
					      	<td>
					      		<c:if test="${fn:contains(buttonList,'editGroupSpan')}">
							      	<span name="editGroupSpan" style="text-align:center;">
							      		<a  id="modify" onclick="modify('${model.groupId}')">&nbsp;编辑&nbsp;</a>
							      	</span>
							    </c:if>
							    <c:if test="${fn:contains(buttonList,'delGroupSpan')}">
							      	<span name="delGroupSpan" style="text-align:center;">
							      		<a id="del" onclick="del('${model.groupId}')">|&nbsp;删除</a>
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
	  </div>
	</body>
<script type="text/javascript">
	var funtId = '${funtId}';
	//pageFuntAutyBtn('${sys_ctx}/getFuntBtnList.action',funtId);//控制按钮权限

	function doSearch() {
			setPage(); 
			frm.submit(); 	
	}

 		var page_sign = 0;//判定是否刷新列表
		layui.use('layer', function(){
	  		var layer = layui.layer;
	  		$("#add").click(function(){
	  			layer.open({
	  				type: 2,
	  				title:"新增告警分组",
	  				area:['320px','200px'],
				    content: '${sys_ctx}/group/addGroupBefore.action'
				    ,end: function(){
					    if(page_sign==1){refresh();}//刷新页面
					}
				});
	  		});
		});
		
  		function modify(groupId){
	  		layer.open({
	  				type: 2,
	  				title:"编辑告警分组",
	  				area:['320px','200px'],
				    content: '${sys_ctx}/group/modifyGroupBefore.action?groupId='+groupId
				    ,end: function(){
			        	if(page_sign==1){refresh();}//刷新页面
			        }
				});
		};
  		
  		function del(groupId){
			  layer.confirm('确定要删除该信息吗？', { title:'提示信息',
					  		  btn: ['确定', '取消'],icon: 3
					  		}, function(index, layero){
			    //按钮【按钮一】的回调
			    	$.ajax({
		  				async:false,
		  				url:'${sys_ctx}/group/deleteGroup.action',
		  				type:"POST",
		  				cache:false,
		  				data:{'groupId':groupId},
		  				dataType:'json',
		  				success: function (data) {
		  					if(data.success=="true"){
		  					layer.alert(data.msg,{title:'提示信息',icon:1});
		  						refresh();
		  						
		  					} else {
		  						layer.alert(data.msg,{title:'提示信息',icon:5});
		  					}
		  				},
		  				error: function(xhr){
		  					
		  				}
  					});
			   }, function(index, layero){
			    //按钮【按钮二】的回调
			    
			    //return false 开启该代码可禁止点击该按钮关闭
			   }
			   
			);
  		};
  	
	   function refresh(){
	 	   window.location.reload(true);
	   }
</script>
</html>
