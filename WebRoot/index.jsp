<%@page import="zst.extend.util.PropertiesUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String meetingPath =request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<%
String sysName = PropertiesUtil.getPropertiesValue("sysConfig.properties", "sys_name");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><%=sysName %></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<%@include file="/WEB-INF/jsp/common/common.html"%>
	<link rel="stylesheet" href="${sys_ctx}/plug/layui/css/ly-layui.css" />
	<link rel="stylesheet" href="${sys_ctx}/css/index.css" />
	<style>
		.nav-sidebar{
			position: relative;
		}
		.jiaoxing{
			width:40px;
			height:40px;
			position:absolute;
			/* background: url("./img/jiaobiao_03.png"); *//*需要时放开  */
			/*z-index:99999;*/
		    top: 0;
		    right: -100px;
		}
		.shenglue{
		       width: 49px;
			    height: 49px;
			/*     background: url(./img/more.png); *//*需要时放开  */
			    color: #fff;
			    border: 0;
			}
		.dw{
			    position: absolute !important;
			    right: -21px;
			    z-index: 999;
			    top: 62px;
		}
		.chart{height:61px;width:61px;border-radius:50px;background:url("./img/new.png");position: fixed; bottom: 20;right:20px;z-index:99999;}
		.chart:hover{height:61px;width:61px;border-radius:61px;background:url("./img/newhover.png");position: fixed; bottom: 20;right:20px;z-index:99999}
		.red{background: red;border-radius:10px;height:10px;width:10px;position: absolute;right: 13px;top:9px;}
		.more{
			width: 30px;
		    border-radius: 3px;
		    background: rgba(0,0,0,0.3);
		    position: absolute;
		    top: 30px;
		    z-index: -1;
		    left: 26px;
		    padding-left:3px;
		    padding-ring:3px;
		    padding-bottom:5px;
			display: none
			}
		.more ul li{
			width:24px;
			height:24px;
			background: red;
			margin-top:5px;
		}	
			/* 常用功能css代码 */
		.left-mar {
		    margin: 5px 0;
		    height: 40px;
		    /* overflow: hidden; */
		}
		.left-mar li:hover,a:hover{
			background: transparent;
		}
		.well{
			border:0;
		}
		.layui-colla-title {
		    border-bottom: 0.5px solid #eee;
		}
		.layui-colla-item{
			border:0;
		}
		.layui-collapse {
			border: 0;
		}
		.nav>li>a {
		    position: relative;
		    display: block;
		    padding: 18px 15px;
		}
		.nav > li > a:hover,
		.nav > li > a:focus {
		  text-decoration: none;
		  background-color: transparent;
		}
		
		.nav>.act .nav_one {
		  background:url('./img/uup_sel.png') no-repeat center center;
		}

		.nav>.act .nav_two {
		  background:url('./img/client_sel.png') no-repeat center center;
		}
		
		.nav>.act .nav_three {
		  background:url('./img/Common functions_control_sel.png') no-repeat center center;
		}
		
		.nav>.act .nav_four {
		  background:url('./img/Common functions_dispatch_sel.png') no-repeat center center;
		}
		.nav>.act .nav_five {
		  background:url('./img/Common functions_table_sel.png') no-repeat center center;
		}
		.nav > li >.nav_one{
			 background:url('./img/uup_nor.png') no-repeat center center;
		}
		.nav > li >.nav_two{
			 background:url('./img/client_nor.png') no-repeat center center;
		}
		.nav > li >.nav_three{
			 background:url('./img/Common functions_control_nor.png') no-repeat center center;
		}
		.nav > li >.nav_four{
			 background:url('./img/Common functions_dispatch_nor.png') no-repeat center center;
		}
		.nav > li >.nav_five{
			 background:url('./img/Common functions_table_nor.png') no-repeat center center;
		}
		
		/* 首页弹窗 */
		#chart-window{display:none;height:300px;width:380px;border:1px #ddd solid;border-radius:3px;position: absolute;z-index: 99999;background:#fff;right:20px;bottom:10px}
		.window-title{height:40px;line-height: 40px;color:#fff;width:100%;background:#000;}
		.text-l{float:left;display: block;margin-left: 10px;}
		.text-r{float:right;display: block;width: 40px;text-align: center;}
		.box-list{height:210px;}
		.text-foot{border-top:1px #ddd solid}
		.l-btn{width:50%;height:33px;line-height: 33px;border-right:1px #ddd solid;float: left;text-align: center;margin: 6px 0}
		.r-btn{width: 50% !important;height:33px;line-height: 33px;text-align: center;float: right;margin: 6px 0;}
		.box-list a{line-height:30px;height:30px;border-bottom:1px #ddd solid;display: block;}
		.box-list img{margin-left:60px;vertical-align: middle;width: 16px;}
		#mages{margin-left:20px;}
		#date{margin-left:20px;}
		#time{margin-left:20px;}
		iframe html{
			height:100%;
		}
	</style>
	 <!--[if lt IE 9]>
 	 <style>
			.alldl{
			    height: 100%;
			    overflow: auto;
			}
		</style>
	<![endif]-->
	
	<script type="text/javascript">
		function changeFrameHeight(){
		    var ifm= document.getElementById("centerFrm"); 
		   	//ifm.height=$("#page-content").height();
		    ifm.height=document.documentElement.clientHeight - 60+"px";
		}
		window.onresize=function(){  
		     changeFrameHeight();  
		} ;
		function a(){
			$("#chart-window").show(300);
		}
		function closes(){
			$("#chart-window").hide(300);
		}
		function tagel(){
			$(".more").toggle(100);
		}
		
		/*当前时间  */
	function toDouble(str){
       if(str<10){
           str='0'+str;
       }
       return str;
    }      
    function showTime(){
        oDiv=document.getElementById('timer')
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
       var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                + " " + toDouble(date.getHours()) + seperator2 + toDouble(date.getMinutes())
                + seperator2 + toDouble(date.getSeconds());
      
       oDiv.innerHTML=currentdate
     }
	 window.onload=function(){
       setInterval(showTime,1000);
    }
    
    
	</script>
  </head>
  <body >
  	<!-- <div class="chart" onclick="a()" >
		<span class="red"></span>
			消息提示框
	</div> -->
	
	<!-- <div id="chart-window">
		<div class="window-title">	
			<span class="text-l">系统消息（4）</span>
			<span class="text-r" onclick="closes()">X</span>
		</div>
		<div class="box-list">
			<a><img src="./img/txt.png"/><span id="mages">您有一条待审批会议</span><span id="date">07-22</span><span id="time">22:30</span></a>
		</div>
		<div class="text-foot">
			<div class="l-btn">全部忽略</div>
			<div class="r-btn">全部查看</div>
		</div>
	</div> -->
  	<div class="top">
		<div class="navbar">
			<div class="navbar-inner">
				<div class="container-fluid">
					<dl class="pull-left topdl">
						<dt class="pull-left">
							<img class="img-circle" src="img/login_logo.png?random=<%=Math.random() %>"/>
						</dt>
						<dd class="pull-left"><%=sysName %></dd>
					</dl>
					<ul class="nav pull-right topnav">
						<li>
							<a style="border-right:1px solid #ccc;cursor: hand;" class="glyphicon glyphicon-home dd site-button-method" title="首页" data-method="backIndex"></a>
						</li>
						<!-- 修改密码 -->
						<li style="display: ${!empty modifyPasswordShow ? modifyPasswordShow : 'none' };">
							<a style="border-right:1px solid #ccc;cursor: hand;" class="glyphicon glyphicon-lock dd site-button-method" data-method="modifyPwd" title="修改密码"></a>
						</li>
						<li>
							<!-- 退出系统 -->
							<a style="cursor: hand;" class="glyphicon glyphicon-log-out dd site-button-method" data-method="loginOut" title="退出系统"></a>
						</li>
					</ul>
				</div>
			</div>
		</div>	
	</div>
	<div class="container-fluid center">	 
	    <div class="nav nav-sidebar left">
	    <div class="jiaoxing"></div>
	    	<div class="page-sidebar bb">
			  <div style="margin-bottom:1px;" class="panel">
			  <div class="panel-body">
			    <dl class="dl-pading">
				<dt class="pull-left"><img class="img-circle" src="img/ingde.png"/></dt>
				<dd class="pull-left margin-dd">
					<h6>${user.userName }</h6>
					<span id="timer" style="margin-top:10px;display: block;"></span>
				</dd>
			    </dl>
			  </div>
			</div>
			<div class="panel">
			  <div style="padding:0" class="panel-body">
			    <ul class="nav nav-pills nav-justified left-mar">
	 				<li title="启动客户端">
	 					<c:if test="${LoginWayKey == 0 }"><!-- AD域登录 -->
							<a class="nav_two" onclick="startupClient('zstvmc://videomonitoringclient/login?username=${user.loginName }&password=${user.loginPwd }&from=ad')" href="javascript:void(0)"></a>
	 					</c:if>
	 					<c:if test="${LoginWayKey == 1 }"><!-- 管理端登录 -->
							<a class="nav_two" onclick="startupClient('zstvmc://videomonitoringclient/login?username=${user.loginName }&password=${user.loginPwd }&from=encrypt')" href="javascript:void(0)"></a>
	 					</c:if>
					</li>
				</ul>
			  </div>
			</div>
			<div class="page-sidebar-menu" style="padding-bottom: 40px;">
				<div class="layui-collapse alldl" style="padding-bottom: 10px;">
					<c:forEach items="${firstFuntList }" var="firstFunt">
						<div class="layui-colla-item">
							<h2 class="layui-colla-title">${firstFunt.funtName }</h2>
							<div class="layui-colla-content">
							 <div class="well">
							 	<c:set var="secondFuntList" value="${secondFuntMap[firstFunt.id] }"></c:set><!--获取对应的二级节点  -->
							 	<c:set var="size" value="${fn:length(secondFuntList)}"></c:set><!--获取二级节点长度  -->
							 	<c:set var="index" value="0" /><!--二级节点对应的下标  -->
							 	<c:forEach items="${secondFuntList}" var="secondFunt" step="3"><!--控制循环的row的次数  -->
							 		<div class="row">
								 		<c:forEach begin="0" end="2">
								 			<c:if test="${index < size }"><!--二级节点下标 < 长度时,循环显示对应二级节点-->
									 			<dl class="col-xs-4">
									 				<c:set var="secondFunt" value="${secondFuntList[index] }"></c:set>
											  		<dt class="${secondFunt.className }">
											  			<a onclick="openUrl('${secondFunt.funtUrl}','${secondFunt.id }')" id="${secondFunt.id }" style="cursor: hand;"></a>
											  		</dt>
											  		<dd>${secondFuntList[index].funtName }</dd>
											  		<c:set var="index" value="${index+1}" />
										  		</dl>
								 			</c:if>
								 		</c:forEach>
							 		</div>
							 	</c:forEach>
							 </div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		  </div>
	    </div>
	    <div id="page-content" class="page-content right">
	    	<img class="fixed-top" src="img/Nav_icon_min.png" />
	    	<div class="fold">
				<img style="display: none;" class="fixed-top0" src="img/Nav_icon_max.png" />
			</div>
			<div class="embed-responsive embed-responsive-4by3">
				<!--<a href="<%=path %>/jsp/sys/org/treeOrg.jsp?random=<%=Math.random() %>">组织机构</a>
	  			<a href="<%=path %>/jsp/sys/user/treeOrgUser.jsp?random=<%=Math.random() %>">用户管理</a>
	  			<a href="<%=path %>/jsp/sys/dictionary/treeDictionary.jsp?random=<%=Math.random() %>">字典管理</a>
	  			<a href="<%=path %>/jsp/sys/funt/funcTreeEdit.jsp?random=<%=Math.random() %>">菜单管理</a>
	  			<a href="<%=path %>/role/getRoleFunt.action">权限管理</a>
	  			<a href="<%=path %>/controlEqu/openControlEqu.action">中控关联设备</a>
	  			<a href="<%=path %>/audioEqu/openAudioEqu.action">音频处理器关联设备</a>
	  			<a href="<%=path %>/jsp/sys/meetingRoom/treeOrgMeetingRoom.jsp?random=<%=Math.random() %>">会场管理</a>-->
				<iframe class="" id="centerFrm" name="centerFrm" onload="changeFrameHeight()" frameborder="no" border="0" style="margin-left:1%;width:99%"></iframe>
			</div>
	    </div>
	</div>

  </body>
  <script type="text/javascript">
//从后台获取登录方式，普通登录或者AD域登录  0;//ad域登录 ,1;//平台登录
var loginWayKey = '${LoginWayKey }';
  	$(".left-mar").delegate("a","click",function(){
	  	$(".alldl dl dt").parent("dl").removeClass("active");
	  	$(this).parent().addClass("act");
	  	$(this).parent().siblings().removeClass("act");
	  })
	  //注意：折叠面板 依赖 element 模块，否则无法进行功能性操作
	  layui.use('element', function(){
	      var element = layui.element();
	    });
	  
	  $(document).ready(function() {
			$(".vvv").mouseover(function (){  
	            $("#ul-hidden").show();  
	        }).mouseout(function (){  
	            $("#ul-hidden").hide();  
	        });  
			$("#centerFrm").attr("src","./welcome.jsp") ;
		});
		var array=$(".alldl dl dt");
		var selarr=[];
		$(".alldl dl dt").click(function(){
		 $(".left-mar a") .parent().removeClass("act");
			if($.inArray(this, selarr)!=-1){//存在
				$(this).parent("dl").addClass("active");
				selarr=[];
			}else{
				$(this).parent("dl").addClass("active");
				for(var i in array){
				    if(array[i] == this){
				    	array.splice(i,1);
				    	selarr=[];
				    	selarr.push(this);
				    	break;
					}
				}
				for(var i=0;i<array.length;i++){
					$(array[i]).parent("dl").removeClass("active");
				}
				array.push(this);
			}
		})
	   
	  $(function(){
		 
		  /**展开收缩列**/
	      var ss=$(".left").width()
	      var imgW=$(".fixed-top").width()
	      var rr=$(".fixed-top0").width()
	      $(".fixed-top").click(function(){  
	          $(".left").css({"display":"none"}); 
	          $(".fixed-top0").css({left:"-2px"})
	           $(".fixed-top0").delay("fast").fadeIn();
	           $(".fixed-top").delay("fast").fadeOut();
	          $(".fold").css({"background":"#f3f3f3","display":"block"})
	           $("#botnav").css("position","static")
	      });  
	      $(".fixed-top0").click(function(){  
	          $(".left").css({"display":"block"}); 
	           $(".fixed-top").css({left:"272px"})
	          $(".fixed-top").delay("fast").fadeIn();
	          $(".fixed-top0").delay("fast").fadeOut();
	           $(".fold").css("display","none")
	            $("#botnav").css("position","absolute") 
	      }); 
	  });  
	  
	  //打开指定的Url 
	  function openUrl(funtUrl,id){
		  funtUrl = funtUrl.replace("###",'<%=session.getId()%>');
		  if(-1 == funtUrl.indexOf("?")){
			  funtUrl = funtUrl+"?funtId="+id;
		  } else {
			  funtUrl = funtUrl+"&funtId="+id;
		  }
		  window.open(funtUrl,"centerFrm");
	  }
	  var page_sign = 0;//判断是否需要重新登录(1为是0位否)
	  layui.use('layer', function(){ 
			var $ = layui.jquery, layer = layui.layer; 
			//触发事件
			  var active = {
				  backIndex: function() {
					  window.location.href="${sys_ctx}/login.action";
				  },
				  //修改密码loginOut
				  modifyPwd: function(){
				      layer.open({
				        type: 2 
				        ,id:'modifyPwd'
				        ,title: '修改密码'
				        ,area: ['480px', '330px']
				        ,shade: 0.2
				        ,maxmin: false
				        ,offset: ''
				        ,content: ['${sys_ctx}/user/openModifyPwd.action','no']
				        ,end: function(){
				        	if(page_sign==1) {
				        		window.location.href="${sys_ctx}/loginOut.action";
				        	}
				        }
				      });
			      },
			  	  //退出系统
loginOut: function(){
	layer.confirm('确定要退出系统吗？', {
		btn: ['确定', '取消'],icon: 3 
	}, function(index, layero){//确定按钮回执函数
		if (loginWayKey == 0) {
			//AD域方式登录
			var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
			var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE浏览器
			var isChrome = userAgent.indexOf("Chrome") > -1 && userAgent.indexOf("Safari") > -1; //判断Chrome浏览器
			if (isIE) {
				open(location, '_self').close();
			}
			if (isChrome) {
				window.location.href="about:blank";
				window.close();
			}
		} else {
			//普通登录退出系统
			window.location.href="${sys_ctx}/loginOut.action";
		}
	}, function(index){//取消按钮回执函数
	
	});
}
			
			 };
			
			  $('.site-button-method').on('click', function(){
				    var othis = $(this), method = othis.data('method');
				    active[method] ? active[method].call(this, othis) : '';
			  });
		});
	  
	  //启动客户端
	  function startupClient(url) {
	  	window.location.href = url;
	  }
  </script>
</html>
