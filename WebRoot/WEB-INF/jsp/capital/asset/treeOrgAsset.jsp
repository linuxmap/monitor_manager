<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>用户管理1</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="X-UA-Compatible" content="IE=8"/>
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
		.ztree li span.button.__14_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_fwq.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
		.ztree li span.button.__15_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_sxj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}                     
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
  		
  		/*隐藏 */
  		
  		.ztree li span.button._1_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
		.ztree li span.button._1_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._1_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
  		.ztree li span.button._2_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_qy.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
  		.ztree li span.button._4_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_scj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}        
  </style>
	<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.exhide.min.js"></script>
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
			SELECT{padding: 3px 0px 3px 10px;}
		</style>
	<![endif]-->
  </head>
  
  <body style="width:100%;height:100%;overflow:hidden;">
    <div style="width:100%;height:100%;" class="background">
		<div style="width:20%;height:100%;float:left;" class="margin-top">
			<div class="jiedian">
				<div class="jd-title">组织机构</div>
			</div>
			<div class="search-bar" style="padding-left:30px;margin-top:10px;">
	            <input id="keyword" type="text" class="default-one-input" placeholder="请输入..." style="width:50%"/>
	            <button id="search-bt" class="default-one-btn btn-green" style="vertical-align:middle;">搜索</button>
	           <!--  <span class="glyphicon glyphicon-search"></span> -->
	        </div>	
			<ul id="tree" class="ztree scroll-tree" style="padding-top:10px important;"></ul>
		</div>
		<div  style="width:80%;height:100%;float: right" >
			<iframe name="userList" id="userList" frameborder="0" style="width:100%;height:100%;" ></iframe>
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
		view: {  
	        selectedMulti: false  
	    },
		callback: {
			onClick: zTreeOnClick
		}
 	};
 	
 	var hiddenNodes=[]; //用于存储被隐藏的结点
	$("#search-bt").on("click", function() {
			    var zTree = $.fn.zTree.getZTreeObj("tree");
		        //显示隐藏的节点
		        nodes = zTree.getNodesByParam("isHidden", true);
		        zTree.showNodes(nodes);
		        
		        var root = zTree.getNodeByParam("level", "0");
		        
		        var hiddenNodes = new Array();
		        //筛选出要隐藏的企业
		        var inputStr = $('#keyword').val().replace(/(^\s+)|(\s+$)/g,"");
		        filterNodes(root, inputStr, hiddenNodes);
		        zTree.hideNodes(hiddenNodes);
		
	});
	function filterNodes(node, inputStr, filterResult){
	        if(node != null){
	        debugger;
	            //自身是否符合搜索条件
	            var selfMatch = node.name.indexOf(inputStr) > -1;
	            //子节点是否有满足的条件的节点
	            var childMatch = false;
	            
	            var children = node.children;
	            if(children != undefined){
	                for(index in children){
	                    childMatch = filterNodes(children[index], inputStr, filterResult) || childMatch;
	                }
	            }
	            
	            //自身不满足搜索条件 且其子节点不包含有满足条件的节点
	            if(!selfMatch && !childMatch){
	                filterResult.push(node);
	            }
	            
	            return selfMatch || childMatch;
	        }else{
	            return true;
	        }
	    }
 	var funtId = '${funtId}';//该页面funtId
	//过滤ztree显示数据
 	function zTreeOnClick(event, treeId, treeNode) {
	 	if(treeNode){
	    	var url = "<%=path%>/asset/queryAssets.action?orgId="+treeNode.id+"&orgName="+treeNode.name +"&funtId="+funtId;
	    	url = encodeURI(url);
	    	window.open(url,"userList");  
	 	}
 	}
 	
 	var page_sign = 0;//判定是否刷新列表
 	var treeObj = null;
 	$(function(){
 		var winH =$(window).height()-223+"px";
		$("#tree").height(winH);
  		loadTree();
  	});
	function loadTree() {
  		$.ajax({
			type:"POST",
			url:"<%=path%>/org/queryOrgTreeForAsset.action",
			cache: false,  
            dataType : "json",  
            success: function(data){
            	if(data) {
            		var ztree = data.ower;
            		$.fn.zTree.init($("#tree"),setting,eval(ztree));
            		treeObj = $.fn.zTree.getZTreeObj("tree");
            		var node = treeObj.getNodeByParam('level', 0);//获取level为0的点
            		$("#orgId").val(node.id);//给子页面部门隐藏域赋值
					treeObj.selectNode(node);//选择点
					treeObj.setting.callback.onClick(null, treeObj.setting.treeId, node);//调用事件
            	}
         	}
		 });
  	};
  
	layui.use('layer', function(){ 
		layer = layui.layer; 
	});
	
  </script>
</html>
