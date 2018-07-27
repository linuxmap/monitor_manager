<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>字典条目1</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<link rel="stylesheet" href="${sys_ctx}/css/from/operateDictionary.css" type="text/css"></link>
	<!--[if lt IE 9]>
		<style>
		.td_right{
				text-align: right;
			}
			SELECT{padding: 3px 0px 3px 10px;}
		</style>
	<![endif]-->
  </head>
  <body>
  	<div id="dlg" align="center" style="padding:10px 0;">
        <form id="fm" method="post">
        	<input type="hidden" id="vendorId"  name="vendorId" value="${vmsVendor.vendorId}"/>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                <tr >
                    <td  class="td_right"><span style="color: red;">*</span>厂家名称：</td>
                    <td><input type="text" id="name" name="name" value="${vmsVendor.name}" title="请输入厂家名称" data-rule-required="true"/></td>		                    
                </tr>
                 <tr >
                    <td class="td_right">是否有效：</td>
                    <td>
                      <select name="flag" id="flag">   
				        <option value="1" ${'1' == vmsVendor.flag ? "selected" : "" }>有效</option>   
				        <option value="0" ${'0' == vmsVendor.flag ? "selected" : "" }>无效</option>   
				      </select>   
     				</td>		                    
                </tr>
                <tr >
                    <td class="td_right"><span style="color: red;">*</span>排序：</td>
                    <td><input type="text" id="orderNumber" name="orderNumber" value="${vmsVendor.orderNumber}" title="请输入排列序号" data-rule-required="true" data-rule-digits="true"/></td>		                    
                </tr>                      	                         		                         
            </table>
        </form>
    </div>
    <div class="btn-foot">
        <c:if test="${funtType != 'view' }">
		 	<button id="save" onclick="save()">保存</button>
	        <button id="cancel" onclick="cancel()">取消</button>
	 	</c:if>
        <c:if test="${funtType == 'view' }">
        	<button id="close" onclick="cancel()">关闭</button>
        </c:if>
    </div>
  </body>
  <script type="text/javascript">
    var url;
	var funtType = '${funtType}';	
	var layer;
	$(function(){
		//新增时赋值
		if(funtType == "add"){
		    url = '${sys_ctx}/dictionaryItem/addFactoryItem.action';
			$('#itemId').attr("disabled",false);
		} else if(funtType == "edit"){
			url = '${sys_ctx}/dictionaryItem/updateFactoryItem.action';
		    $('#itemId').attr("disabled",true); 
		} else if(funtType == "view") {
			setFormDisable('fm');
			$('#dictionaryName').attr("disabled",false);
		} else if(funtType == "copy") {
			url = '${sys_ctx}/dictionaryItem/addFactoryItem.action';
			$('#itemId').attr("disabled",false);
		}	
		
		layui.use('layer', function(){ 
			layer = layui.layer; 
  		});
	});
  
	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		parent.layer.close(index); //再执行关闭 
    }
	
    var isPass = true;//是否通过验证
	//验证是否重复
	function validateItemId() {
		var itemId = $('#itemId').val();
		if(funtType == "edit") {//若是在修改的情况下，防止用户触发该事件重新把值改为打开时的值，但仍进行判断的情况
			if(itemId == '${dictionaryItem.itemId}') {
				isPass = true;
				return;
			}
		}
		
		$.ajax({
			async:false,
			url:'${sys_ctx}/dictionaryItem/validateItemId.action',
			type:"POST",
			cache:false,
			data:{'itemId':itemId},
			dataType:'json',
			success: function (data) {
				if(data.success=="false"){
					isPass = false;
				} else {
					isPass = true;
				}
			},
			error: function(xhr){
				isPass = false;
			}
		});
	}
	
  	function save() {
  		$('#fm').validate({
  			showErrors: function (errorMap, errorList) {
                $.each(errorList, function (i, v) {
                    //在此处用了layer的方法,显示效果更美观
                    layer.tips(v.message, v.element, { time: 2000,tips: [3, '#F69805'] });
                    return false;
                });  
            },
            /* 失去焦点时不验证 */
            onfocusout: false
  		});
  		//验证不通过
  		if(!$('#fm').valid()){
  			return;
  		}
  		
  		if(!isPass) {//验证字典条目编码未通过
			layer.msg('字典条目编码不能重复，请确认！',{time: 2000, icon:5});
  			return;
  		}
  		
  		layer.confirm('确定要保存该信息吗？', {
	  		  btn: ['确定', '取消'],title:'提示信息',icon: 3 
	  		}, function(index, layero){//确定按钮回执函数
	  			$.ajax({
	  				async:false,
	  				url:url,
	  				type:"POST",
	  				cache:false,
	  				data:$('#fm').serialize(),
	  				dataType:'json',
	  				success: function (data) {
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
  		
  	}	
  	function cancel() {
  		laycls(0);
  	}
  </script>
</html>
