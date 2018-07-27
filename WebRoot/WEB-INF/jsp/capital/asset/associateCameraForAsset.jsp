<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>设备管理-关联设备</title>
<base href="<%=basePath%>">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<%@include file="/WEB-INF/jsp/common/common.html"%>
<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.core.min.js"></script>
<link rel="stylesheet" href="${sys_ctx}/plug/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">  
<link rel="stylesheet" href="${sys_ctx}/css/tree/treeStyle.css" type="text/css">  
<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.exhide.min.js"></script>
<script type="text/javascript" src="${sys_ctx}/plug/ztree/js/jquery.ztree.excheck.min.js"></script>
</head>
  <style type="text/css">
	.layui-colla-item{position:relative;}
	.label-1{
			position: absolute;
		    z-index: 9;
		    top: 10px;
		    left: 45px;
	    }
    td {
		margin: 0;
		padding: 0;
	}

	body,html{height:100%;overflow: hidden;}
		.left-user{width:200px; height:100%;  float:left;}
		.right-user{overflow-x:hidden; height:100%;padding-bottom:50px}
		.Privilege-p{
			display: -webkit-flex;
			display: flex;
			justify-content: flex-start;
			flex-wrap: wrap;	
			border-bottom: 0;
		}
		.Privilege-p p{
			margin:8px;
			text-align: center;
		}
		input{
			display: inline-block;
			width:20px;
			height:15px;
		}
		label{
			padding-bottom: 2px;
			font-weight:500;
			margin-bottom: 0 !important;
		}
		.title-h2{
			padding-left:10px;
		}
		.panel-default{
			border-color:transparent;
		}
		.f_right{
			text-align:right;
			margin-bottom: 2px!important;
		}
		.panel{
			margin-bottom:0;
		}
		.list-group-item {
			border:0;
		    border-top: 1px solid #ddd;
		}
		.layui-table td{padding:0;}
		.panel-body p{padding:8px;}
		.btn-group{margin-bottom:2px}
		input[type=checkbox], input[type=radio] {
		    margin:0;
		    margin-top: 1px\9;
		    line-height: normal;
		    vertical-align: middle;
		}
		.list-group-item.active, .list-group-item.active:focus, .list-group-item.active:hover {
		    z-index: 2;
		    color: #fff;
		    background-color: #3997f4;
		    border-color: #3997f4;
		}
		#roles{
			overflow-y: auto;
		}
		#tree{
		    border: 1px #ccc solid;
		    padding: 10px;
		    overflow: auto;
		    width: 230px;
		    height: 350px;
		    float: left;
		}
		#choosedUl{
		    border: 1px #ccc solid;
		    padding: 10px;
		    overflow: auto;
		    width: 230px;
		    height: 350px;
		    float: right;
		    
		}
		.background{
		    width: 100%;
    		height: 90%;
    		background:#fff;
		}
		.button-box{
		       height: 100px;
			    width: 97px;
			    float: left;
			    padding: 15px;
			    margin-top: 120px;
		}
		 .checboxs{
			height: 350px;
		 }
		 #button_xzhong{
		 	margin-bottom:20px;
		 }
		 
		 #save_botton_box{
		 	text-align: center;
    		margin-top: 20px;
		 }
		 .left-box{
		     width: 230px;
		    height: 350px;
		    float: left;
		 }
		 
		 
		 .ccl{
		 	    line-height: 0;
			    margin: 0;
			    width: 16px;
			    height: 16px;
			    display: inline-block;
			    vertical-align: middle;
			    border: 0 none;
			    cursor: pointer;
			    outline: none;
			    background-color: transparent;
			    background-repeat: no-repeat;
			    background-attachment: scroll;
			    background-image: url(./img/ccs1_01.jpg);
		 
		 }
  		/*权限范围ICON配置
  		**class名前缀：  __设备
  		**1.服务器   2.NVR设备  3。交换机 4。接入网关设备
  		**5.存储设备  6.报警主机 7.编码器 8.解码器 9.路由器
  		**10.单兵 11.车载 12.无人机 13.视频平台  14.摄像机
  		**  */
  		
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
  		.ztree li span.button._1_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_zzjg.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
		.ztree li span.button._1_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_zzjg.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._1_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_zzjg.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}     
  		.ztree li span.button._2_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_qy.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
  		.ztree li span.button._4_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icon_scj.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}        
  		
  		.ztree li span.button._org_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}  
		.ztree li span.button._org_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._org_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zzjg_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._enormouswork_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._directory_ico_docu{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_scj_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._enormouswork_ico_open{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ztree li span.button._enormouswork_ico_close{margin-right:2px; background: url(${sys_ctx}/plug/ztree/css/zTreeStyle/img/diy/icons_zdzy_visual.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}    
  		.ccl_icon{
  				line-height: 0;
			    margin: 0;
			    width: 16px;
			    height: 16px;
			    display: inline-block;
			    vertical-align: middle;
			    border: 0 none;
			    cursor: pointer;
			    outline: none;
			    background-color: transparent;
			    background-repeat: no-repeat;
			    background-attachment: scroll;
  				background-image: url(./img/icon_sxj.png);
  		
  		}
  		
  		.active{
  			/* background: #0081ec; */
  				line-height: 0;
			    margin: 0;
			    width: 16px;
			    height: 16px;
			    display: inline-block;
			    vertical-align: middle;
			    border: 0 none;
			    cursor: pointer;
			    outline: none;
			    background-color: transparent;
			    background-repeat: no-repeat;
			    background-attachment: scroll;
			    background-image: url(./img/ccs1_03.jpg);
  		}
  </style>
<body>
  	<div style="width:100%;height:100%;padding:20px" class="background">
  		<!-- 隐藏域设备id -->
  		<input type="hidden" id="assetId" value="${assetId }"/>
  		<div class="checboxs">
  			<div class="left-box">
	  			<div>所有摄像机:</div>
				<ul id="tree" class="ztree scroll-tree"></ul>
  			</div>
			<div class="button-box">
				<button type="button" class="default-one-btn" id="button_xzhong" >选中</button>
				<button type="button" class="default-one-btn" id="button_remove" onclick="l_remove()">移除</button>
			</div>
			<div>
				<div>已选摄像机:</div>
				<ul id="choosedUl" class="ztree scroll-tree">
					<c:forEach items="${choosedCameras }" var="choosedAsset">
						<li id="li_${choosedAsset.assetId }"><span class="ccl"></span><span class="ccl_icon"></span>${choosedAsset.name }</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<p id="save_botton_box"> 
			<button type="button" style='margin-right:20px' class="default-one-btn _1_ico_close" id="save">确定</button>
			<button type="button"style='margin-left:20px' class="default-one-btn-top" id="reset">重置</button>
		</p> 
	</div>
</body>
<script type="text/javascript">
//初始化layer插件
layui.use('layer', function() {
	layer = layui.layer; 
});
  
//定义ztree变量
var zTree;
var treeObj;
var zTreeNodes;
$(function(){
	//初始化ztree树
	var setting = {
			//采用异步加载
			view : {
				selectedMulti : false,
				showLine : true  //是否显示节点间的连线
			},
			check: {
				enable : false
			},
			data : {
				simpleData : {
					enable : true
				},
				key: {
					name :"name",
					title : "name"
				}
			},
			callback : {
				onExpand : expandNode //展开事件
			},
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "ps", "N": "ps" }
			}
	};
	var allNodes ={};
	var zn = '${zTreeNodes}';
	zTreeNodes = eval(zn);
	zTree = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
	treeObj = $.fn.zTree.getZTreeObj("tree");
	var node =  treeObj.getNodeByParam("id", 0, null);
	//treeObj.expandAll(true);//默认折叠
	if (node) {
		var node = treeObj.getNodes();
		for (var i=0;i<node.lenght;i++) {
			allNodes.push(nodes[i]);
		}
	}
});
//节点展开事件 展开时定义组织节点的 子节点是否全被选中 属性 ，用allBranchCheckd来定义
function expandNode(event, treeId, treeNode) {
	var node = treeObj.getNodeByTId(treeNode.tId);
	//自定义属性，标记该节点是否被展开过，如果没有展开过，则标记为已展开，否则不走后台
	if (!node.leftclicked) {
		node.leftclicked = true;
	} else {
		return;
	}
	//ajax异步获取组织下边的设备
	$.ajax({
		type: "POST",
		async:false,
		url: "${sys_ctx}/asset/asyncGetChildrenByOrgId.action",
		data:{
			parentId : treeNode.id
		},
		dataType:"json",
		success: function(data){
			if(data.success == "true"){
				//添加新节点
				if (data.ower && data.ower.length>0) {
					newNode = treeObj.addNodes(node, data.ower);
					//设置allBranchCheckd属性
					if (node.children) {
						var childrenNodes = node.children;
						var checkChildCount = 0;
						for (var j=0; j<childrenNodes.length; j++) {
							if (childrenNodes[j].checked == true) {
								checkChildCount++;
							}
						}
						//防止展开时父节点的选择被失误取消
						if (checkChildCount > 0) {
							node.checked = true;
							treeObj.updateNode(node);
						}
						//如果角色该组织下的设备权限则需要把组织的勾选去除
						if (checkChildCount==0) {
							node.checked = false;
							treeObj.updateNode(node);
						}
						/* if (checkChildCount == childrenNodes.length) {
							//若所有子节点被选中，则父节点属性设置为true
							node.allBranchCheckd = true;
						} */
					}
				}
			}
		},
		error:function(event, XMLHttpRequest, ajaxOptions, thrownError){
			layer.msg('请求失败！',{time: 2000, icon:6});
		}
	});
}
 	
var funtId = '${funtId}';//该页面funtId
var page_sign = 0;//判定是否刷新列表

//保存
$("#save").on("click",function(){
	//设备的id
	var assetId = $("#assetId").val();
	if (!assetId) {
		layer.msg("未找到设备参数",{time: 2000, icon:6});
		return;
	}
	//获取选中的的id
	var len = $("#choosedUl li").length;
	var cameraIdArray = new Array();
	if (len > 0) {
		var ul = document.getElementById('choosedUl');
		var lis = ul.getElementsByTagName('li');
		for(var i=0;i<lis.length;i++){
			var strId = lis[i].id;// li_
			cameraIdArray.push(strId.substring(3,strId.length));
		}
	} else {
		cameraIdArray.push("");
	}
	var url = '${sys_ctx}/asset/saveAssetCameraRelation.action';
	$.ajax({
		async:true,
		url:url,
		type:"POST",
		cache:false,
		data:{'assetId':assetId, 'cameraIdArray':cameraIdArray},
		dataType:'json',
		success: function (data) {
			if(data.success=="true"){
				laycls(0);
			} else {
				layer.alert(data.msg, {title:'提示信息',icon: 5});
			}
		},
		error: function(xhr){
			
		}
	});
	
});
//关闭对话框
function laycls(pagesign){
	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	parent.page_sign = pagesign;//必须定义--控制父页面刷新：0、不刷新；1、刷新
	parent.layer.close(index); //再执行关闭 
}
//重置，将所有选择框取消选择
$("#reset").on("click",function() {
	$("#choosedUl").empty();
});

//点击选中加入右边
$("#button_xzhong").click(function(){
	var right_node =new Array();
	var treeObj1 = $.fn.zTree.getZTreeObj("tree");
	var nodes1 = treeObj1.getCheckedNodes(true);
	if (nodes1.length > 0) {
		for (var i=0; i<nodes1.length; i++) {
			if (nodes1[i].type == 2) {
				right_node.push(nodes1[i]);
			}
		}
	}
	//添加到右侧
	if (right_node.length > 0) {
		for (var k=0; k<right_node.length; k++) {
			insertLi(right_node[k].dbassetId,right_node[k].name);
		}
	}
});

//追加li 有去重判断
function insertLi(eleId,name) {
	var liElement = '<li id="li_' + eleId + '"><span class="ccl"></span><span class="ccl_icon"></span>' + name + '</li>';
	var len = $("#choosedUl li").length;
	if (len > 0) {
		var ul = document.getElementById('choosedUl');
		var lis = ul.getElementsByTagName('li');
		var existing  = new Array();
		for (var k=0; k<lis.length; k++) {
			existing.push(lis[k].id);
		}
		if (!isInArray(existing,eleId)) {
			$("#choosedUl").append(liElement);
		}
	} else {
		$("#choosedUl").append(liElement);
	}
}
//验证数据是否存在该元素
function isInArray(arr,value){
	value = "li_"+value;
    for(var i = 0; i < arr.length; i++){
        if(value === arr[i]){
            return true;
        }
    }
    return false;
}
	// 点击li添加 active
$("#choosedUl").on("click","li",function(){  
		debugger;
    //只需要找到你点击的是哪个ul里面的就行
	if($(this).children().hasClass("active")){
		$(this).children(":first").removeClass("active").addClass("ccl");
		$(this).removeClass("act");
	}else{
		$(this).children(":first").removeClass("ccl").addClass("active");
		$(this).addClass("act");
	}
 });
 //删除选中
  function l_remove(){
  		$("#choosedUl li").remove("li[class=act]");
  }
	
</script>
</html>
