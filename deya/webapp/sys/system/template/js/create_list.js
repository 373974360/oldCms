//保存创建
function saveCreateList()
{
	if(app_id == "cms")
	{
		top.getCurrentFrameObj().editAreaLoader.replaceSelection(saveCMSList());
	}
	if(app_id == "zwgk")
	{
		top.getCurrentFrameObj().editAreaLoader.replaceSelection(saveZWGKList());
	}
	if(app_id == "ggfw")
	{
		top.getCurrentFrameObj().editAreaLoader.replaceSelection(saveGGFWList());
	}
	if(app_id == "appeal")
	{
		top.getCurrentFrameObj().editAreaLoader.replaceSelection(saveAppealList());
	}
	if(app_id == "interview")
	{
		top.getCurrentFrameObj().editAreaLoader.replaceSelection(saveInterviewList());
	}
	if(app_id == "survey")
	{
		top.getCurrentFrameObj().editAreaLoader.replaceSelection(saveSurveyList());
	}
	top.CloseModalWindow();
}

function saveCMSList()
{
	var title_count = $("#title_count").val();
	var format_date = $("#format_date").val();
	var str = infoPublicStr();
	var list_html = "";	
	var item_str = "";
	//var title_str = "";
	$("#select_item_list > li").each(function(){
		var item_name = $(this).text();
		var item_id = $(this).attr("item_id");
		//title_str += '<li>'+item_name+'</li>\n';
		if(item_id == "title")
		{
			item_str += '	<li><a href="$r.content_url" target="_blank" title="${r.title}">${FormatUtil.cutString($r.title,'+title_count+',"...")}</a></li>\n';
		}else
		{
			if(item_id == "released_dtime")
			{
				item_str += '	<li>${FormatUtil.formatDate($r.released_dtime,"'+format_date+'")}</li>\n';
			}else
				item_str += '	<li>${r.'+item_id+'}</li>\n';
		}
		
	});

	//list_html = '<ul>\n';
	//list_html += title_str;
	//list_html += '</ul>\n';
	list_html += '<!-- ------------------信息列表　开始---------------- -->\n';
	list_html += '<ul>\n';
	list_html += '#foreach( $r in $InfoUtilData.getInfoList("site_id=$site_id;cat_id=$cat_id;cur_page=$cur_page'+str+'"))\n';
	list_html += item_str;
	list_html += '#end\n';
	list_html += '</ul>\n';
	list_html += '\n';
	list_html += '#set($turnpage=$InfoUtilData.getInfoCount("site_id=$site_id;cat_id=$cat_id;cur_page=$cur_page'+str+'"))\n';
	list_html += '<div>\n'
	list_html += '	<ul class="page">\n'
	list_html += '		<li><a href="?site_id=$site_id&cat_id=$cat_id&cur_page=$turnpage.page_count">未页</a></li>\n'
	list_html += '		<li><a href="?site_id=$site_id&cat_id=$cat_id&cur_page=$turnpage.next_num">下一页</a></li>\n'
	list_html += '		<li><a href="?site_id=$site_id&cat_id=$cat_id&cur_page=$turnpage.prev_num">上一页</a></li>\n'
	list_html += '		<li><a href="?site_id=$site_id&cat_id=$cat_id&cur_page=1">首页</a></li>\n'
	list_html += '		<li>共${turnpage.count}条记录 共${turnpage.page_count}页 当前${turnpage.cur_page}页</li>\n'
	list_html += '	</ul>\n'
	list_html += '</div> \n';
	list_html += '<!-- ------------------信息列表　结束---------------- -->\n';
	return list_html;
}

function saveGGFWList()
{
	var title_count = $("#title_count").val();
	var format_date = $("#format_date").val();
	var str = infoPublicStr();
	var list_html = "";	
	var item_str = "";
	//var title_str = "";
	$("#select_item_list > li").each(function(){
		var item_name = $(this).text();
		var item_id = $(this).attr("item_id");
		//title_str += '<li>'+item_name+'</li>\n';
		if(item_id == "title")
		{
			item_str += '	<li><a href="$r.content_url" target="_blank" title="${r.title}">${FormatUtil.cutString($r.title,'+title_count+',"...")}</a></li>\n';
		}else
		{
			if(item_id == "released_dtime")
			{
				item_str += '	<li>${FormatUtil.formatDate($r.released_dtime,"'+format_date+'")}</li>\n';
			}else
				item_str += '	<li>${r.'+item_id+'}</li>\n';
		}
		
	});

	//list_html = '<ul>\n';
	//list_html += title_str;
	//list_html += '</ul>\n';
	list_html += '<!-- ------------------服务信息列表　开始---------------- -->\n';
	list_html += '<ul>\n';
	list_html += '#foreach( $r in $InfoUtilData.getFWInfoList("site_id=$site_id;cat_id=$cat_id;cur_page=$cur_page'+str+'"))\n';
	list_html += item_str;
	list_html += '#end\n';
	list_html += '</ul>\n';
	list_html += '\n';
	list_html += '#set($turnpage=$InfoUtilData.getFWInfoCount("site_id=$site_id;cat_id=$cat_id;cur_page=$cur_page'+str+'"))\n';
	list_html += '<div>\n'
	list_html += '	<ul class="page">\n'
	list_html += '		<li><a href="?site_id=$site_id&cat_id=$cat_id&cur_page=$turnpage.page_count">未页</a></li>\n'
	list_html += '		<li><a href="?site_id=$site_id&cat_id=$cat_id&cur_page=$turnpage.next_num">下一页</a></li>\n'
	list_html += '		<li><a href="?site_id=$site_id&cat_id=$cat_id&cur_page=$turnpage.prev_num">上一页</a></li>\n'
	list_html += '		<li><a href="?site_id=$site_id&cat_id=$cat_id&cur_page=1">首页</a></li>\n'
	list_html += '		<li>共${turnpage.count}条记录 共${turnpage.page_count}页 当前${turnpage.cur_page}页</li>\n'
	list_html += '	</ul>\n'
	list_html += '</div> \n';
	list_html += '<!-- ------------------服务信息列表　结束---------------- -->\n';
	return list_html;
}

function saveZWGKList()
{
	var title_count = $("#title_count").val();
	var format_date = $("#format_date").val();
	var str = infoPublicStr();
	var list_html = "";	
	var item_str = "";
	//var title_str = "";
	$("#select_item_list > li").each(function(){
		var item_name = $(this).text();
		var item_id = $(this).attr("item_id");
		//title_str += '<li>'+item_name+'</li>\n';
		if(item_id == "title")
		{
			item_str += '	<li><a href="$r.content_url" target="_blank" title="${r.title}">${FormatUtil.cutString($r.title,'+title_count+',"...")}</a></li>\n';
		}else
		{
			if(item_id == "released_dtime")
			{
				item_str += '	<li>${FormatUtil.formatDate($r.released_dtime,"'+format_date+'")}</li>\n';
			}else
				item_str += '	<li>${r.'+item_id+'}</li>\n';
		}
		
	});

	//list_html = '<ul>\n';
	//list_html += title_str;
	//list_html += '</ul>\n';
	list_html += '<!-- ------------------公开信息列表　开始---------------- -->\n';
	list_html += '<ul>\n';
	list_html += '#foreach( $r in $InfoUtilData.getGKInfoList("node_id=$node_id;site_id=$site_id;cat_id=$cat_id;cur_page=$cur_page'+str+'"))\n';
	list_html += item_str;
	list_html += '#end\n';
	list_html += '</ul>\n';
	list_html += '\n';
	list_html += '#set($turnpage=$InfoUtilData.getGKInfoCount("node_id=$node_id;site_id=$site_id;cat_id=$cat_id;cur_page=$cur_page'+str+'"))\n';
	list_html += '<div>\n'
	list_html += '	<ul class="page">\n'
	list_html += '		<li><a href="?node_id=$node_id&site_id=$site_id&cat_id=$cat_id&cur_page=$turnpage.page_count">未页</a></li>\n'
	list_html += '		<li><a href="?node_id=$node_id&site_id=$site_id&cat_id=$cat_id&cur_page=$turnpage.next_num">下一页</a></li>\n'
	list_html += '		<li><a href="?node_id=$node_id&site_id=$site_id&cat_id=$cat_id&cur_page=$turnpage.prev_num">上一页</a></li>\n'
	list_html += '		<li><a href="?node_id=$node_id&site_id=$site_id&cat_id=$cat_id&cur_page=1">首页</a></li>\n'
	list_html += '		<li>共${turnpage.count}条记录 共${turnpage.page_count}页 当前${turnpage.cur_page}页</li>\n'
	list_html += '	</ul>\n'
	list_html += '</div> \n';
	list_html += '<!-- ------------------公开信息列表　结束---------------- -->\n';
	return list_html;
}

function saveAppealList()
{
	var title_count = $("#title_count").val();
	var format_date = $("#format_date").val();
	var str = infoPublicStr();
	var list_html = "";	
	var item_str = "";
	//var title_str = "";
	$("#select_item_list > li").each(function(){
		var item_name = $(this).text();
		var item_id = $(this).attr("item_id");
		//title_str += '<li>'+item_name+'</li>\n';
		
		switch(item_id)
		{
			case "sq_title2":item_str += '	<li><a href="/appeal/view.jsp?model_id=${r.model_id}&sq_id=${r.sq_id}" target="_blank" title="${r.sq_title2}">${FormatUtil.cutString($r.sq_title2,'+title_count+',"...")}</a></li>\n';
						 break;
			case "sq_dtime":item_str += '	<li>${FormatUtil.formatDate($r.sq_dtime,"'+format_date+'")}</li>\n';
						 break;
			case "sq_status":item_str += '	<li>#if($r.sq_status == 3) 已办结 #else 办理中 #end</li>\n';
						 break;
			default:item_str += '	<li>${r.'+item_id+'}</li>\n';
					break;
		}
		
	});
	
	list_html += '<!-- ------------------诉求信件列表　开始---------------- -->\n';
	list_html += '<ul>\n';
	list_html += '#foreach( $r in $AppealData.getAppealList("cur_page=$cur_page'+str+'"))\n';	
	list_html += item_str;	
	list_html += '#end\n';
	list_html += '</ul>\n';
	list_html += '\n';
	list_html += '#set($turnpage=$AppealData.getAppealCount("cur_page=$cur_page'+str+'"))\n';
	list_html += '<div>\n'
	list_html += '	<ul class="page">\n'
	list_html += '		<li><a href="?model_id=$model_id&cur_page=$turnpage.page_count">未页</a></li>\n'
	list_html += '		<li><a href="?model_id=$model_id&cur_page=$turnpage.next_num">下一页</a></li>\n'
	list_html += '		<li><a href="?model_id=$model_id&cur_page=$turnpage.prev_num">上一页</a></li>\n'
	list_html += '		<li><a href="?model_id=$model_id&cur_page=1">首页</a></li>\n'
	list_html += '		<li>共${turnpage.count}条记录 共${turnpage.page_count}页 当前${turnpage.cur_page}页</li>\n'
	list_html += '	</ul>\n'
	list_html += '</div> \n';
	list_html += '<!-- ------------------诉求信件列表　结束---------------- -->\n';
	return list_html;
}

function saveInterviewList()
{
	var title_count = $("#title_count").val();
	var intro_count = $("#intro_count").val();
	var format_date = $("#format_date").val();
	var str = infoPublicStr();
	var list_html = "";	
	var item_str = "";
	//var title_str = "";
	$("#select_item_list > li").each(function(){
		var item_name = $(this).text();
		var item_id = $(this).attr("item_id");
		//title_str += '<li>'+item_name+'</li>\n';
		
		switch(item_id)
		{
			case "sub_name":item_str += '	<li><a href="/interview/live.jsp?cat_id=${r.category_id}&sub_id=${r.sub_id}" target="_blank" title="${r.sub_name}">${FormatUtil.cutString($r.sub_name,'+title_count+',"...")}</a></li>\n';
						 break;
			case "s_for_pic":item_str += '	<li><a href="/interview/live.jsp?cat_id=${r.category_id}&sub_id=${r.sub_id}" target="_blank"><img src="#if($r.s_for_pic == \'\') ../images/demo.gif #else $r.s_for_pic #end" /></a></li>\n';
						 break;
			case "start_time":item_str += '	<li>${FormatUtil.formatDate($r.start_time,"'+format_date+'")}</li>\n';
						 break;
			case "intro":item_str += '	<li>${FormatUtil.cutString($r.intro,'+intro_count+',"...")}\n';
						 break;
			default:item_str += '	<li>${r.'+item_id+'}</li>\n';
					break;
		}
		
	});

	var status = $("#status :selected").val();
	if(status != "")
		str += ";status="+status;
	else
		str += ";status=$status";
	str = str.replace("model_id","cat_id");
	list_html += '<!-- ------------------在线访谈列表　开始---------------- -->\n';
	list_html += '<ul>\n';
	list_html += '#foreach( $r in $InterViewData.getInterViewList("site_id=$site_id;cur_page=$cur_page'+str+'"))\n';
	list_html += item_str;	
	list_html += '#end\n';
	list_html += '</ul>\n';
	list_html += '\n';
	list_html += '#set($turnpage=$InterViewData.getInterViewCount("site_id=$site_id;cur_page=$cur_page'+str+'"))\n';
	list_html += '<div>\n'
	list_html += '	<ul class="page">\n'
	list_html += '		<li><a href="?status=$status&cur_page=$turnpage.page_count">未页</a></li>\n'
	list_html += '		<li><a href="?status=$status&cur_page=$turnpage.next_num">下一页</a></li>\n'
	list_html += '		<li><a href="?status=$status&cur_page=$turnpage.prev_num">上一页</a></li>\n'
	list_html += '		<li><a href="?status=$status&cur_page=1">首页</a></li>\n'
	list_html += '		<li>共${turnpage.count}条记录 共${turnpage.page_count}页 当前${turnpage.cur_page}页</li>\n'
	list_html += '	</ul>\n'
	list_html += '</div> \n';
	list_html += '<!-- ------------------在线访谈列表　结束---------------- -->\n';
	return list_html;
}

function saveSurveyList()
{
	var title_count = $("#title_count").val();
	var intro_count = $("#intro_count").val();
	var format_date = $("#format_date").val();
	var str = infoPublicStr();
	var list_html = "";	
	var item_str = "";
	//var title_str = "";
	$("#select_item_list > li").each(function(){
		var item_name = $(this).text();
		var item_id = $(this).attr("item_id");
		//title_str += '<li>'+item_name+'</li>\n';
		
		switch(item_id)
		{
			case "s_name":item_str += '	<li>#if($r.is_end == 0) <a href="/survey/view.jsp?s_id=${r.s_id}" target="_blank">	#else <a href="/survey/answerResult.jsp?s_id=${r.s_id}" target="_blank">#end <span class="f14B">${FormatUtil.cutString($r.s_name,'+title_count+',"...")}</span></li>\n';
						 break;
			case "is_end":item_str += '	<li>#if($r.is_end == 0) <a href="view.jsp?s_id=${r.s_id}" target="_blank">参与调查</a> #else <a href="answerResult.jsp?s_id=${r.s_id}" target="_blank">查看结果</a> #end</a></li>\n';
						 break;
			case "start_time":item_str += '	<li>${FormatUtil.formatDate($r.start_time,"'+format_date+'")}</li>\n';
						 break;
			case "end_time":item_str += '	<li>${FormatUtil.formatDate($r.end_time,"'+format_date+'")}</li>\n';
						 break;
			case "description":item_str += '	<li>${FormatUtil.cutString($r.intro,'+intro_count+',"...")}\n';
						 break;
			default:item_str += '	<li>${r.'+item_id+'}</li>\n';
					break;
		}
		
	});	
	str = str.replace("model_id","cat_id");
	list_html += '<!-- ------------------在线调查列表　开始---------------- -->\n';
	list_html += '<ul>\n';
	list_html += '#foreach( $r in $SurveyData.getSurveyList("site_id=$site_id;cur_page=$cur_page'+str+'"))\n';
	list_html += item_str;
	list_html += '#end\n';
	list_html += '</ul>\n';
	list_html += '\n';
	list_html += '#set($turnpage=$SurveyData.getSurveyCount("site_id=$site_id;cur_page=$cur_page'+str+'"))\n';
	list_html += '<div>\n'
	list_html += '	<ul class="page">\n'
	list_html += '		<li><a href="?cur_page=$turnpage.page_count">未页</a></li>\n'
	list_html += '		<li><a href="?cur_page=$turnpage.next_num">下一页</a></li>\n'
	list_html += '		<li><a href="?cur_page=$turnpage.prev_num">上一页</a></li>\n'
	list_html += '		<li><a href="?cur_page=1">首页</a></li>\n'
	list_html += '		<li>共${turnpage.count}条记录 共${turnpage.page_count}页 当前${turnpage.cur_page}页</li>\n'
	list_html += '	</ul>\n'
	list_html += '</div> \n';
	list_html += '<!-- ------------------在线调查列表　结束---------------- -->\n';
	return list_html;
}

