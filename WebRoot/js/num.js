 function demo(obj,tip){
	var a=document.getElementsByTagName("a")
	for(var i=0;i<a.length;i++){
		a[i].style.background="";
		a[i].style.color="#000";
	}
	obj.style.background="#0d85f6";
	obj.style.color="#fff";
    if(tip==1){
        var con=document.getElementById('content').value;
        document.getElementById('content').value=con+obj.innerHTML;
    }else if(tip==2){
        document.getElementById('content').value="";
    }else if(tip==3){
    	obj.style.background="";
		obj.style.color="";
        var con=document.getElementById('content').value;
        document.getElementById('content').value=con.slice(0,-1);
    }
}
 /*==============DVD部分==============*/
function demo1(obj,tip){
	var a=document.getElementsByTagName("a")
	for(var i=0;i<a.length;i++){
		a[i].style.background="";
		a[i].style.color="#000";
	}
	obj.style.background="#0d85f6";
	obj.style.color="#fff";
    if(tip==1){
        var con=document.getElementById('dvd-content').value;
        document.getElementById('dvd-content').value=con+obj.innerHTML;
    }else if(tip==2){
        document.getElementById('dvd-content').value="";
    }else if(tip==3){
    	obj.style.background="";
		obj.style.color="";
        var con=document.getElementById('dvd-content').value;
        document.getElementById('dvd-content').value=con.slice(0,-1);
    }
}
function control(){
	 $(".river").undelegate();  
	  $(".river").delegate("a","click",function(){
	  	$(".river a").removeClass("sel")
	  	var num=$(this).attr('class')
	 	if ($(this).hasClass(num)) {
           $(this).addClass("sel")
      	}
	});
}
/*=================高清摄像机部分===============*/
 function camera(){
	 $(".camera-river").undelegate();  
	  $(".camera-river").delegate("a","click",function(){
	  	$(".camera-river a").removeClass("sel")
	  	var num=$(this).attr('class')
	 	if ($(this).hasClass(num)) {
           $(this).addClass("sel")
      	}
	});
}  
/*=====================DVD部分=============*/
function dvdriver(){
	 $(".dvd-river").undelegate();  
	  $(".dvd-river").delegate("a","click",function(){
	  	$(".dvd-river a").removeClass("sel")
	  	var num=$(this).attr('class')
	 	if ($(this).hasClass(num)) {
           $(this).addClass("sel")
      	}
	});
}