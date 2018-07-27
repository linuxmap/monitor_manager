<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>组织机构维护-1</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.core.min.js"></script>
	<link rel="stylesheet" href="${sys_ctx}/plug/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" href="${sys_ctx}/css/tree/treeStyle.css" type="text/css">	
	<style>  		
  		.ztree li span.button.__1_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_fwq.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}   
  		.ztree li span.button.__2_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_NVR.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle} 
  		.ztree li span.button.__3_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_jhj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}         
  		.ztree li span.button.__4_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_jrwg.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
  		.ztree li span.button.__5_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_ccsb.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
  		.ztree li span.button.__6_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_bjzj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
  		.ztree li span.button.__7_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_bmq.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
  		.ztree li span.button.__8_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_jmq.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
  		.ztree li span.button.__9_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_lyq.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
  		.ztree li span.button.__10_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_db.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
  		.ztree li span.button.__11_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_cz.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}   
  		.ztree li span.button.__12_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_wrj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle} 
		.ztree li span.button.__13_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_sxj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}   
		.ztree li span.button.__14_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_sxj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}                     
  		/* class名前缀： _组织
  		**1.组织机构  2.区域 4.收藏夹
  		** */
  		.ztree li span.button._org_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
		.ztree li span.button._org_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._org_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._enormouswork_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._directory_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_scj_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._enormouswork_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._enormouswork_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		
  		
  		
  </style>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.exhide.min.js"></script>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.excheck.min.js"></script>
  </head>
  	<style>
	#cancel {
	    background: #fff;
	    border: 1px #399efc solid;
	    border-radius: 3px;
	    line-height: 30px;
	    padding: 0 22px;
	    color: #399efc;
	    margin: 0 20px;
	}
	#saveAssociateMix{
	    background: #399efc;
	    border: 0;
	    border-radius: 3px;
	    line-height: 30px;
	    padding: 0 22px;
	    color: #fff;
	    margin: 0 20px;
	}
	.btn-foot{
		text-align:center;
	}
	
	</style>
	
	<!--[if lt IE 9]>
		<style>
		.jiedian{
				height:40px;
				width:100%;
				background-image:url(${sys_ctx}/css/jianbian.png);
				background-repeat: no-repeat;
			}
		html{
			height:100%;
		}
		
		input{
			border:0 !important;
		}

		</style>
	<![endif]-->

  <body style="width:100%;height:380px;;overflow:hidden;">
     <div style="width:100%;height:100%;background:#fff" class="background">
		<div class="margin-top" style="margin-top:0px;border:none;">
			<ul id="tree" class="ztree scroll-tree" style="margin:10px;padding:10px;border:1px #ddd solid;height: 370px;overflow-x: hidden;"></ul>
		</div>
		<div class="btn-foot">
			<button id="saveAssociateMix" onclick="saveAssociateMix()">保存</button>
	        <button id="cancel" onclick="cancel()">取消</button>
		</div>
	</div>
  </body>
  <script type="text/javascript">
 	var setting = {
    	data: {
    		simpleData: {  //简单的数据源<pre class="html" name="code">           
             enable: true, 
             open:true, 
             idKey: "id",  //id和pid，树的目录级别  
             pIdKey: "pId"
            } 
    	},
 		check:{
 			enable: true,
 			 chkStyle: "radio",
 			radioType: "all"
 		},
		view: {  
	        selectedMulti: false  
	    },
		callback: {
			onClick: zTreeOnClick
		}
 	};
 	
 	var hiddenNodes=[]; //用于存储被隐藏的结点
	$("#search-bt").on("click", function() {
		filter();
	})
	//过滤ztree显示数据
	function filter(){
	    //显示上次搜索后背隐藏的结点
	    var zTreeObj = $.fn.zTree.getZTreeObj("tree");
	    zTreeObj.showNodes(hiddenNodes);
	
	    //查找不符合条件的叶子节点
	    function filterFunc(node){
	        var _keywords=$("#keyword").val();
	        if(node.isParent||node.name.indexOf(_keywords)!=-1) return false;
	        return true;        
	    };
	
	    //获取不符合条件的叶子结点
	    hiddenNodes=zTreeObj.getNodesByFilter(filterFunc);
	
	    //隐藏不符合条件的叶子结点
	    zTreeObj.hideNodes(hiddenNodes);
	};
 	function zTreeOnClick(event, treeId, treeNode) {
	 	
 	}
 	
	var page_sign = 0;//判定是否刷新列表
 	var treeObj = null;
	$(function(){
		loadTree();
  	});
	
	function loadTree() {
		$.ajax({
			async:false,
			type:"POST",
			url:"<%=path%>/org/queryOrgTree.action",
			cache: false,  
            dataType : "json",  
            success: function(data){  
            	if(data) {
            		var ztree = data.ower;
            		$.fn.zTree.init($("#tree"),setting,ztree);
            		treeObj = $.fn.zTree.getZTreeObj("tree");
            	}
         	}
		 });
	}
	
	layui.use('layer', function(){ 
		layer = layui.layer; 
	}); 
	
	//确定保存关联关系
	function saveAssociateMix(){
		//获取选中的节点
		var zTreeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = zTreeObj.getCheckedNodes(true);
		var orgId;
		if(nodes && nodes.length == 1){
			orgId = nodes[0].id;
			if (orgId == -5) {//虚拟节点不可选择
				layer.msg("该节点不可选择",{time: 2000, icon:6});
				return;
			}
		}else{
			layer.msg('请选择一个组织机构',{time: 2000, icon:5});
			return;
		}
		$orgIdsToBeAssociate = parent.$("#orgIdsToBeAssociate");
		$orgParIdsToBeAssociate = parent.$("#orgParIdsToBeAssociate");
		var orgIdsToBeAssociate = $orgIdsToBeAssociate.val();
		var orgParIdsToBeAssociate = $orgParIdsToBeAssociate.val();
		if(!orgIdsToBeAssociate){
			layer.msg('未携带组织机构',{time: 2000, icon:5});
			return;
		}
		var idsArr = orgIdsToBeAssociate.split(",");
		for (var k=0; k<idsArr.length; k++) {
			if (idsArr[k] == -5) {
				layer.msg('重大作业节点不可勾选',{time: 2000, icon:6});
				return;
			}
		}
		var url = '${sys_ctx}/assetIntegration/addOrgAssetMix.action';
		$.ajax({
			async:true,
			url:url,
			type:"POST",
			cache:false,
			data:{'orgIds' : orgIdsToBeAssociate,'orgParIds':orgParIdsToBeAssociate, 'parOrgId' : orgId},
			dataType:'json',
			success: function (data) {
				if(data.success=="true"){//刷新父页面的ztree树
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);  // 关闭layer
					window.parent.parent.location.reload(); //刷新父的父页面
				} else {
					layer.alert(data.msg, {title:'提示信息',icon: 5});
				}
			},
			error: function(xhr){
				
			}
		});
	}

  	function cancel() {
 		laycls(0);
  	}
  	
  	//关闭对话框
    function laycls(pagesign){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
		parent.layer.close(index); //再执行关闭 
    }
  
  </script>
</html>
