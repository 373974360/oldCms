// 显示条数  $size
//标题字数 $title_count
//显示时间 $time_format


//显示条数
ListCount = function(){
	this.name = "显示条数：";
	this.ename = "ListCount";
	this.value = 5;
	this.input_text = '<input type="text" id="ListCount_input" name="ListCount_input" value="5" style="width:50px" maxlength="2">';
	this.action = function(obj){
		if(obj.find(".transform_img_parent").length > 0)
		{//用于图片转场
			var p_obj = obj.find(".transform_img_parent");
			p_obj.find("ul").remove();
			var ul_str = "<ul>";
			for(var i=0;i<parseInt(this.value);i++)
			{
				if(i == 0)
					ul_str += "<li class='on'>"+(i+1)+"</li>";
				else
					ul_str += "<li>"+(i+1)+"</li>";
			}
			ul_str += "</ul>";		
			$(p_obj).children().eq(2).before(ul_str);
		}
		else
		{
			var li_obj = obj.find(" .module_body > div > ul > li");		
			var demo_li_count = li_obj.length;
			if(demo_li_count > this.value)
			{
				li_obj.slice(this.value).remove();
			}else
			{
				var subtract = this.value - demo_li_count;
				var first_obj = li_obj.eq(0);
				var parent_obj = first_obj.parent(); 
				for(var i=0;i<subtract;i++)
				{
					first_obj.clone().prependTo(parent_obj);
				}
			}
		}
	};
	this.replaceStr = function(str,vs)
	{		
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$size/ig,vs).replace(/<hrf>/ig,"\r");
	};
};

//显示更多
ShowMore = function(){
	this.name = "显示更多：";
	this.ename = "ShowMore";
	this.value = 1;
	this.input_text = '<input type="checkbox" id="ShowMore_input" name="ShowMore_input" value="1" checked="checked">';
	this.action = function(obj){
		if(this.value == 0)
			obj.find("span.more").hide();
		else
			obj.find("span.more").show();
	};
	this.replaceStr = function(str,vc)
	{
		return str;
	};
};

//标题字数
TitleCount = function(){
	this.name = "标题字数：";
	this.ename = "TitleCount";
	this.value = 20;
	this.input_text = '<input type="text" id="TitleCount_input" name="TitleCount_input" value="20" style="width:50px" maxlength="2">';
	this.action = function(obj){
		var a_obj = obj.find("li > a");
		var count = this.value;
		a_obj.each(function(){
			$(this).text($(this).text().subString2(count))
		});
	};
	this.replaceStr = function(str,vc)
	{		
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$title_count/ig,vc).replace(/<hrf>/ig,"\r");
	};
};

//显示时间
ShowTime = function(){
	this.name = "显示时间：";
	this.ename = "ShowTime";
	this.value = 1;
	this.input_text = '<input type="checkbox" id="ShowTime_input" name="ShowTime_input" value="1" checked="checked">';
	this.action = function(obj){	
		if(this.value == 0)
		{
			obj.find("li span").hide();
			obj.find(".info_time").hide();
		}
		else
		{
			obj.find("li span").show();
			obj.find(".info_time").show();//内容页里的时间
		}
	};
	this.replaceStr = function(str,vc,obj)
	{
		if(vc == 0)
		{	
			if(obj.find(".info_time").length > 0)
				return str.replace(/\r?\n/g,"<hrf>").replace(/<SPAN class=\"info_time\">.*?<\/SPAN>/ig,"").replace(/<hrf>/ig,"\r");
			else
				return str.replace(/\r?\n/g,"<hrf>").replace(/<SPAN>.*?<\/SPAN>/ig,"").replace(/<hrf>/ig,"\r");
		}else
			return str;
	};
};

//时间格式
TimeFormat = function()
{
	this.name = "时间格式：";
	this.ename = "TimeFormat";
	this.value = "MM-dd";
	this.input_text = '<input type="text" id="TimeFormat_input" name="TimeFormat_input" value="MM-dd">';
	this.action = function(obj){		
		var t = new Date().format(this.value);
		if(obj.find(".info_time").length > 0)
		{
			obj.find(".info_time").text("时间："+t);
		}
		else
		{
			obj.find("li span").each(function(){
				$(this).text("["+t+"]");
			});
		}
	};
	this.replaceStr = function(str,vc,obj)
	{		
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$time_format/ig,vc).replace(/<hrf>/ig,"\r");
	};
}

//模块标题
ModuleTitle = function()
{
	this.name = "模块标题：";
	this.ename = "ModuleTitle";
	this.value = "";
	this.input_text = '<input type="text" id="ModuleTitle_input" name="ModuleTitle_input" value="" maxlength="80">';
	this.action = function(obj){		
		if(this.value != "")
		{
			obj.find("h2 a").text(this.value);
		}
	};
	this.replaceStr = function(str,vc,obj)
	{
		return str;
	};
}

//模块标题链接地址
ModuleTitleUrl = function()
{
	this.name = "链接地址：";
	this.ename = "ModuleTitleUrl";
	this.value = "";
	this.input_text = '<input type="text" id="ModuleTitleUrl_input" name="ModuleTitleUrl_input" value="" maxlength="200">';
	this.action = function(obj){		
		if(this.value != "")
		{
			obj.find("h1 a").attr("href",this.value);
			obj.find(".more a").attr("href",this.value);
		}
	};
	this.replaceStr = function(str,vc,obj)
	{
		return str;
	};
}

//每行显示列数，一般用于多行多列
RowCount = function()
{
	this.name = "每行显示个数：";
	this.ename = "RowCount";
	this.value = 3;
	this.input_text = '<input type="text" id="RowCount_input" name="RowCount_input" value="3" style="width:50px" maxlength="2">';
	this.action = function(obj){
		if(this.value != "" && this.value != 0)
		{
			var table_obj = obj.find("table");	
			var td_str = "";
			//去除掉多余的td,为的是能取到td对象的字符串
			table_obj.find("td").each(function(i){
				if(i > 0)
					$(this).remove();
			})
			td_str = table_obj.find("tr:first").html();

				
			var attr_str = obj.find(".module_body div:first").attr("attr_str");//这里需要取到总的显示条数
			var count = 12;
			if(attr_str != "" && attr_str != null)
			{
				var tempA = attr_str.split(",");
				for(var i=0;i<tempA.length;i++)
				{
					if(tempA[i].indexOf("ListCount") > -1)
						count = parseInt(tempA[i].substring(10));
				}
			}
			var rc = parseInt(this.value);
			table_obj.empty();
			table_obj.append("<tr></tr>");
			for(var i=1;i<count+1;i++)
			{
				table_obj.find("tr:last").append(td_str);
				if(i%rc == 0)
					table_obj.append("<tr></tr>");
			}
		}		
	};
	this.replaceStr = function(str,vc)
	{		
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$row_count/ig,vc).replace(/<hrf>/ig,"\r");
	};
}

//图片宽度
ImgWidth = function()
{
	this.name = "图片宽度：";
	this.ename = "ImgWidth";
	this.value = 120;
	this.input_text = '<input type="text" id="ImgWidth_input" name="ImgWidth_input" value="120" maxlength="4">';
	this.action = function(obj){
		obj.find("img").attr("width",this.value);
		
		if(obj.find(".transform_img_parent").length > 0)
		{
			obj.find(".transform_img_parent").css("width",this.value+"px");
		}
	};
	this.replaceStr = function(str,vc,obj)
	{		
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$img_width/ig,vc+"px").replace(/<hrf>/ig,"\r");
	};
}

//图片高度
ImgHeight = function()
{
	this.name = "图片高度：";
	this.ename = "ImgHeight";
	this.value = 100;
	this.input_text = '<input type="text" id="ImgHeight_input" name="ImgHeight_input" value="100" maxlength="4">';
	this.action = function(obj){		
		obj.find("img").attr("height",this.value);		
		if(obj.find(".transform_img_parent").length > 0)
		{
			obj.find(".transform_img_parent").css("height",this.value+"px");
		}
		if(obj.find("#marquee_div").length > 0)
		{
			obj.find("#marquee_div").css("height",this.value+"px");			
		}
		if(obj.find("#marquee_div_title").length > 0)
		{
			obj.find("#marquee_div_title").css("height",(parseInt(this.value)+30)+"px");

		}
	};
	this.replaceStr = function(str,vc,obj)
	{
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$img_height/ig,vc+"px").replace(/<hrf>/ig,"\r");
	};
}

//是否显示标题
ShowTitle = function()
{
	this.name = "显示标题：";
	this.ename = "ShowTitle";
	this.value = 1;
	this.input_text = '<input type="checkbox" id="ShowTitle_input" name="ShowTitle_input" value="1" checked="checked">';
	this.action = function(obj){	
		if(this.value == 0)
			obj.find("ul li > a").parent().hide();
		else
			obj.find("ul li > a").parent().show();
	};
	this.replaceStr = function(str,vc,obj)
	{
		if(vc == 0)
		{			
			return str.replace(/\r?\n/g,"<hrf>").replace(/<li><a.*?<\/li>/ig,"").replace(/<hrf>/ig,"\r");
		}else
			return str;
	};
}

//简介字数
IntroCount = function()
{
	this.name = "简介字数：";
	this.ename = "IntroCount";
	this.value = 80;
	this.input_text = '<input type="text" id="IntroCount_input" name="IntroCount_input" value="80" style="width:50px" maxlength="4">';
	this.action = function(obj){
		var a_obj = obj.find(".intro_li");
		var count = this.value;
		a_obj.each(function(){
			$(this).text($(this).text().subString2(count));
			$(this).append('<span><a href="#">【全文】</a></span>');
		});
	};
	this.replaceStr = function(str,vc)
	{		
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$intro_count/ig,vc).replace(/<hrf>/ig,"\r");
	};
}

//是否显示全文链接（简介后面的）
ShowIntroLink = function()
{
	this.name = "全文链接：";
	this.ename = "ShowIntroLink";
	this.value = 1;
	this.input_text = '<input type="checkbox" id="ShowIntroLink_input" name="ShowIntroLink_input" value="1" checked="checked">显示【全文】';
	this.action = function(obj){	
		if(this.value == 0)
			obj.find("ul li span a").parent().hide();
		else
			obj.find("ul li span a").parent().show();
	};
	this.replaceStr = function(str,vc,obj)
	{
		if(vc == 0)
		{			
			return str.replace(/\r?\n/g,"<hrf>").replace(/<span><a.*?<\/span>/ig,"").replace(/<hrf>/ig,"\r");
		}else
			return str;
	};
}

//导航显示数目
MenuCount = function()
{
	this.name = "导航显示个数：";
	this.ename = "MenuCount";
	this.value = 6;
	this.input_text = '<input type="text" id="MenuCount_input" name="MenuCount_input" value="6" style="width:50px" maxlength="2">';
	this.action = function(obj){
		var li_obj = obj.find("li");		
		var demo_li_count = li_obj.length;		
		if(demo_li_count > this.value)
		{
			li_obj.slice(this.value).remove();
		}else
		{
			var subtract = this.value - demo_li_count;
			var first_obj = li_obj.eq(0);
			var parent_obj = first_obj.parent(); 
			for(var i=0;i<subtract;i++)
			{
				first_obj.clone().prependTo(parent_obj);
			}
		}
	};
	this.replaceStr = function(str,vc)
	{
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$menu_count/ig,parseInt(vc)+1).replace(/<hrf>/ig,"\r");
	};
}

//当前位置链接符
PositionSymbol = function()
{
	this.name = "链接符：";
	this.ename = "PositionSymbol";
	this.value = ">>";
	this.input_text = '<input type="text" id="PositionSymbol_input" name="PositionSymbol_input" value=">>" maxlength="4">';
	this.action = function(obj){
		obj.find(".position_symbol").text(this.value);
	};
	this.replaceStr = function(str,vc,obj)
	{	
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$position_symbol/ig,vc).replace(/<hrf>/ig,"\r");
	};
}

//当前位置 显示完整路径
PosShowAllPath = function()
{
	this.name = "显示完整路径：";
	this.ename = "PosShowAllPath";
	this.value = 1;
	this.input_text = '<input type="checkbox" id="PosShowAllPath_input" name="PosShowAllPath_input" value="1" checked="checked">';
	this.action = function(obj){	
		if(this.value == 0)
		{
			obj.find(".position_symbol").parent().children().hide();
			obj.find(".position_symbol").parent().find(":last-child").show();
		}
		else
			obj.find(".position_symbol").parent().children().show();
	};
	this.replaceStr = function(str,vc,obj)
	{	
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$position_all_path/ig,vc).replace(/<hrf>/ig,"\r");
	};
}

//当前位置 是否使用链接
PosHasLink = function()
{
	this.name = "是否使用链接：";
	this.ename = "PosHasLink";
	this.value = 1;
	this.input_text = '<input type="checkbox" id="PosHasLink_input" name="PosHasLink_input" value="1" checked="checked">';
	this.action = function(obj){
		if(this.value == 0)
		{
			obj.find(".module_body a").each(function(){
				$(this).replaceWith("<span>"+$(this).text()+"</span>");
			});
		}
		else
		{
			obj.find(".module_body span:not(.position_symbol)").each(function(){
				$(this).replaceWith("<a href='#'>"+$(this).text()+"</a>");
			});
		}
	};
	this.replaceStr = function(str,vc,obj)
	{
		if(vc == 0)
		{
			return str.replace(/\r?\n/g,"<hrf>").replace(/<a.*?>|<\/a>/ig,"").replace(/<hrf>/ig,"\r");
		}else
			return str;
	};
}

//当前位置 页面跳转方式
PosJumpType = function()
{
	this.name = "是否产生链接：";
	this.ename = "PosJumpType";
	this.value = "_blank";
	this.input_text = '<input type="radio" id="PosJumpType_input" name="PosJumpType_input" value="_blank" checked="checked">新窗口　<input type="radio" id="PosJumpType_input" name="PosJumpType_input" value="_self">当前窗口';
	this.action = function(obj){
		obj.find("a").attr("target",this.value);
	};
	this.replaceStr = function(str,vc,obj)
	{
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$position_jump_type/ig,vc).replace(/<hrf>/ig,"\r");
	};
}

//当前位置 首页描述
PosIndexPage = function()
{
	this.name = "首页描述：";
	this.ename = "PosIndexPage";
	this.value = "";
	this.input_text = '<input type="text" id="PosIndexPage_input" name="PosIndexPage_input" value="" maxlength="">';
	this.action = function(obj){
		var v = "首页";
		if(this.value != "" && this.value != null)
		{
			v = this.value;
		}
		var a_boj = obj.find(".module_body a");
		
		if(a_boj.length > 1)
		{
			a_boj.eq(0).text(v);
		}else
		{//没有链接的话，外层是span
			var span_boj = obj.find(".module_body span");
			if(span_boj.length > 1)
			{
				span_boj.eq(0).text(v);
			}
		}
	};
	this.replaceStr = function(str,vc,obj)
	{
		var v = "首页";
		if(vc != "" && vc != null)
		{
			v = vc;
		}
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$position_index_page/ig,v).replace(/<hrf>/ig,"\r");
	};
}

//转场图片个数
/*
TrancsImgCount = function()
{
	this.name = "转场图片个数：";
	this.ename = "TrancsImgCount";
	this.value = 5;
	this.input_text = '<input type="text" id="TrancsImgCount_input" name="TrancsImgCount_input" value="5" style="width:50px" maxlength="2">';
	this.action = function(obj){
		var p_obj = obj.find(".transform_img_parent");
		p_obj.find("ul").remove();
		var ul_str = "<ul>";
		for(var i=0;i<parseInt(this.value);i++)
		{
			if(i == 0)
				ul_str += "<li class='on'>"+(i+1)+"</li>";
			else
				ul_str += "<li>"+(i+1)+"</li>";
		}
		ul_str += "</ul>";		
		$(p_obj).children().eq(2).before(ul_str);
	};
	this.replaceStr = function(str,vs)
	{		
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$size/ig,vs).replace(/<hrf>/ig,"\r");
	};
}*/

//转场时间
TrancsImgTime = function()
{
	this.name = "图片转场时间：";
	this.ename = "TrancsImgTime";
	this.value = 4;
	this.input_text = '<input type="text" id="TrancsImgTime_input" name="TrancsImgTime_input" value="4" style="width:50px" maxlength="2">秒';
	this.action = function(obj){
		
	};
	this.replaceStr = function(str,vs)
	{		//这里还需要替换外层div的ID,如果一个页面放多个转场效果,div的ID不能一致
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$trancsimg_time/ig,parseInt(vs)*1000).replace(/\$trancsimg_div_id/ig,"ti_div_"+getRandom()).replace(/<hrf>/ig,"\r");
	};
}

//滚动方向 横向的
MarqueeDirectionCrosswise = function()
{
	this.name = "滚动方向：";
	this.ename = "MarqueeDirectionCrosswise";
	this.value = "left";
	this.input_text = '<input type="radio" id="MarqueeDirectionCrosswise_input" name="MarqueeDirectionCrosswise_input" value="left" checked="checked">向左　<input type="radio" id="MarqueeDirectionCrosswise_input" name="MarqueeDirectionCrosswise_input" value="right">向右　';
	this.action = function(obj){
		//不需要操作，这里设置一下滚动区域的宽度
		var t_obj = obj.find("ul").parent();
		t_obj.css("width",t_obj.width());
	};
	this.replaceStr = function(str,vc,obj)
	{
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$marquee_direction/ig,vc).replace(/<hrf>/ig,"\r");
	};
}

//滚动方向 纵向的
MarqueeDirectionLengthways = function()
{
	this.name = "滚动方向：";
	this.ename = "MarqueeDirectionCrosswise";
	this.value = "up";
	this.input_text = '<input type="radio" id="MarqueeDirectionCrosswise_input" name="MarqueeDirectionCrosswise_input" value="up" checked="checked">向上　<input type="radio" id="MarqueeDirectionCrosswise_input" name="MarqueeDirectionCrosswise_input" value="down">向下　';
	this.action = function(obj){
		
	};
	this.replaceStr = function(str,vc,obj)
	{
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$marquee_direction/ig,vc).replace(/<hrf>/ig,"\r");
	};
}

//滚动速度
MarqueeSpeed = function()
{
	this.name = "滚动速度：";
	this.ename = "MarqueeSpeed";
	this.value = 30;
	this.input_text = '<input type="text" id="MarqueeSpeed_input" name="MarqueeSpeed_input" value="30" style="width:50px" maxlength="3">';
	this.action = function(obj){
		
	};
	this.replaceStr = function(str,vs,obj)
	{
		//这里还需要替换外层div的ID,如果一个页面放多个滚动效果,div的ID不能一致		
		var attr_str = obj.attr("attr_str");//滚动方向
		var md = "left";	
		if(attr_str != "" && attr_str != null)
		{
			var tempA = attr_str.split(",");
			for(var i=0;i<tempA.length;i++)
			{
				if(tempA[i].indexOf("MarqueeDirectionCrosswise") > -1)
				{
					md = tempA[i].substring(26);
				}
				if(tempA[i].indexOf("MarqueeDirectionLengthways") > -1)
				{
					md = tempA[i].substring(27);
				}
			}
		}
		
		var r_num = getRandom();
		obj.parent().parent().parent().parent().parent().parent().parent().parent().append('&lt;script type="text/javascript"&gt;$("#mq_div_'+r_num+'").marqueeTools('+vs+',"'+md+'");&lt;/script&gt;');
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$marquee_speed/ig,vs).replace(/\$marquee_div_id/ig,"mq_div_"+r_num).replace(/<hrf>/ig,"\r");
	};
}

//滚动区域高度
MarqueeDivHeight = function()
{
	this.name = "滚动区域高度：";
	this.ename = "MarqueeDivHeight";
	this.value = 200;
	this.input_text = '<input type="text" id="MarqueeDivHeight_input" name="MarqueeDivHeight_input" value="200" maxlength="4">';
	this.action = function(obj){		
		obj.find("#marquee_div").css("height",this.value+"px");
	};
	this.replaceStr = function(str,vc,obj)
	{
		return str.replace(/\r?\n/g,"<hrf>").replace(/\$marquee_div_height/ig,vc+"px").replace(/<hrf>/ig,"\r");
	};
}

//显示来源
ShowSource = function(){
	this.name = "显示来源：";
	this.ename = "ShowSource";
	this.value = 1;
	this.input_text = '<input type="checkbox" id="ShowSource_input" name="ShowSource_input" value="1" checked="checked">';
	this.action = function(obj){	
		if(this.value == 0)
		{			
			obj.find(".info_source").hide();
		}
		else
		{			
			obj.find(".info_source").show();//内容页里的时间
		}
	};
	this.replaceStr = function(str,vc,obj)
	{
		if(vc == 0)
		{				
				return str.replace(/\r?\n/g,"<hrf>").replace(/<SPAN class=\"info_source\">.*?<\/SPAN>/ig,"").replace(/<hrf>/ig,"\r");			
		}else
			return str;
	};
};

//显示作者
ShowAuthor = function(){
	this.name = "显示作者：";
	this.ename = "ShowAuthor";
	this.value = 1;
	this.input_text = '<input type="checkbox" id="ShowAuthor_input" name="ShowAuthor_input" value="1" checked="checked">';
	this.action = function(obj){	
		if(this.value == 0)
		{			
			obj.find(".info_author").hide();
		}
		else
		{			
			obj.find(".info_author").show();//内容页里的时间
		}
	};
	this.replaceStr = function(str,vc,obj)
	{
		if(vc == 0)
		{				
				return str.replace(/\r?\n/g,"<hrf>").replace(/<SPAN class=\"info_author\">.*?<\/SPAN>/ig,"").replace(/<hrf>/ig,"\r");			
		}else
			return str;
	};
};