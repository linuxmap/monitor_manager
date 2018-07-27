/*****************公共js文件 *****************/	


	/**
	 * 页面功能按钮权限限制
	 * @param url地址
	 * @param 页面菜单编号
	 */
	function pageFuntAutyBtn(url, funtId){
		$.ajax({
			async:false,//同步进行
			url: url,
			type:"POST",
			cache:false,
			data:{'funtId': funtId},
			dataType:'json',
			success: function (result) {
				if(result) {
					var data = result.data;
					if(data) {
						var array = data.split(",");
	 					for (var i = 0; i < array.length; i++) {
	 						$("[name='"+array[i]+"']").css("display","");
	 					}	
					}
				}
			},
			error: function(xhr){
			}
		});
	}
	
	
	/**
	 * 设置form表单标签不可用
	 * @param form id值
	 */
	function setFormDisable(form){
		$("#"+form+" select,input,textarea").attr("readonly","true");
		 var b = document.getElementsByTagName("select");   
        for   (var   i=0;   i<b.length;   i++)   
        {   
              b[i].disabled=true;   
        }   
		
	}
