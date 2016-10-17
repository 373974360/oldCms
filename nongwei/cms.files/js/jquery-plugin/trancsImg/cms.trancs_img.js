/*
* 图片转场js
* 参数一:间隔时间 1000 为1秒
* 参数二:0
* 参数三:0
* 参数四:0
* 调用方法 $("#ai2").setTransformImg(4000,0,0,0);
*/
$.fn.setTransformImg = function(step_time,t,n,count){
		var div_id = $(this).attr("id");
		var c_obj = $(this);
		count=$(this).find(".transform_img_parent_list a").length;

		var ul_str = "<ul>";
		for(var i=0;i<count;i++)
		{
			if(i == 0)
				ul_str += "<li class='on'>"+(i+1)+"</li>";
			else
				ul_str += "<li>"+(i+1)+"</li>";
		}
		ul_str += "</ul>";		
		$(c_obj).children().eq(2).before(ul_str);//添加 1,2,3,4的li对象
		$(this).find(".transform_img_parent_list a:not(:first-child)").hide();
		$(this).find(".transform_img_parent_info").html($(this).find(".transform_img_parent_list a:first-child").find("img").attr('alt'));
		$(this).find(".transform_img_parent_info").click(function(){window.open($(c_obj).find(".transform_img_parent_list a:first-child").attr('href'), "_blank")});
		$(this).find("li").click(function() {
			var i = $(this).text() - 1;//获取Li元素内的值，即1，2，3，4
			n = i;
			if (i >= count) return;
			$(c_obj).find(".transform_img_parent_info").html($(c_obj).find(".transform_img_parent_list a").eq(i).find("img").attr('alt'));
			$(c_obj).find(".transform_img_parent_info").unbind().click(function(){window.open($(c_obj).find(".transform_img_parent_list a").eq(i).attr('href'), "_blank")})
			$(c_obj).find(".transform_img_parent_list a").filter(":visible").fadeOut(500).parent().children().eq(i).fadeIn(1000);
			document.getElementById(div_id).style.background="";
			$(this).toggleClass("on");
			$(this).siblings().removeAttr("class");
		});
		t = setInterval(function(){
			n = n >=(count - 1) ? 0 : ++n;
			$(c_obj).find("li").eq(n).trigger('click');
		}, step_time);
		/*
		$("#"+this).hover(function(){clearInterval(t)}, function(){t = setInterval(function(){
			n = n >=(count - 1) ? 0 : ++n;
			$(c_obj).find("li").eq(n).trigger('click');
		}, step_time);});*/
	};

/*
 <div id="ai1" class="transform_img_parent" style="width:200px; height:200px;">	
	<div class="transform_img_parent_bg"></div>  
	<div class="transform_img_parent_info"></div>     
   <div class="transform_img_parent_list">
        <a href="http://www.baidu.com" target="_blank"><img src="imgs/p1.jpg" width="200px" height="200px" title="橡树小屋的blog" alt="橡树小屋的blog" /></a>
        <a href="#" target="_blank" style="display:none"><img src="imgs/p5.jpg"  width="200px" height="200px" title="11111111" alt="11111111" /></a>
        <a href="#" target="_blank" style="display:none"><img src="imgs/p3.jpg"  width="200px" height="200px" title="22222222" alt="22222222" /></a>
        <a href="#" target="_blank" style="display:none"><img src="imgs/p4.jpg"  width="200px" height="200px" title="33333333" alt="33333333" /></a>
	</div>
</div>
*/