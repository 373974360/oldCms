function saveHotList()
{
	if(app_id == "cms")
	{
		getCurrentFrameObj().editAreaLoader.replaceSelection(saveCMSHotList());
	}
	if(app_id == "ggfw")
	{
		getCurrentFrameObj().editAreaLoader.replaceSelection(saveGGFWHotList());
	}
	if(app_id == "zwgk")
	{
		var ss = saveGKHotList();
		if(ss == null || ss == "")
			return;
		getCurrentFrameObj().editAreaLoader.replaceSelection(ss);
	}
	if(app_id == "appeal")
	{
		var ss = saveSQHotList();
		if(ss == null || ss == "")
			return;
		getCurrentFrameObj().editAreaLoader.replaceSelection(ss);
	}
	if(app_id == "interview")
	{
		getCurrentFrameObj().editAreaLoader.replaceSelection(saveInterViewHotList());
	}
	if(app_id == "survey")
	{
		getCurrentFrameObj().editAreaLoader.replaceSelection(saveSurveyHotList());
	}
	CloseModalWindow();
}

//得到栏目ID
function getCategoryIDS(div_name)
{
	var ids = "";
	$("#"+div_name+" .tree-checkbox1").each(function(){		
		ids += ","+$(this).parent().attr("node-id");
	});
	if(ids != "")
		ids = ids.substring(1);

	return ids;
}

//诉求业务
function setSQModelList()
{
	$("#leftMenuTree").empty();
	var list = jsonrpc.SQModelRPC.getAllSQModelList();
	list = List.toJSList(list);	
	if(list != null && list.size() > 0)
	{
		for(var i=0;i<list.size();i++)
		{
			setListInLeftMenu(list.get(i).model_id,list.get(i).model_cname);
		}
	}
	init_input();	
}

//访谈分类
function setInterviewSubCategory()
{
	var list = jsonrpc.SubjectRPC.getSubCategoryAllName(site_id);
	list = List.toJSList(list);	
	if(list != null && list.size() > 0)
	{
		for(var i=0;i<list.size();i++)
		{
			setListInLeftMenu(list.get(i).category_id,list.get(i).category_name);
		}
	}
	init_input();	
}

//调查分类
function setSurveyCategory()
{
	$("#model_id").empty();
	var list = jsonrpc.SurveyCategoryRPC.getAllSurveyCategoryName(site_id);
	list = List.toJSList(list);	
	if(list != null && list.size() > 0)
	{
		for(var i=0;i<list.size();i++)
		{
			setListInLeftMenu(list.get(i).category_id,list.get(i).c_name);
		}
	}
	init_input();
}

function setListInLeftMenu(id,text)
{
	$("#leftMenuTree").append('<li style="margin:3px 0"><input type="checkbox" id="model_id" value="'+id+'"><label >'+text+'</label></li>');
}

function saveCMSHotList()
{
	var title_count = $("#title_count").val();
	var format_date = $("#format_date").val();
	var str = infoPublicStr();
	var cat_ids = getCategoryIDS("leftMenuTree");
	if(cat_ids != "")
	{
		str = ";cat_id="+cat_ids+str;
	}
	if($("#is_show_thumb").is(':checked'))
	{
		str = ";is_show_thumb=true"+str;
	}

	var list_html = "";	
	var item_str = "";
	//var title_str = "";
	$("#select_item_list > li").each(function(){
		var item_name = $(this).text();
		var item_id = $(this).attr("item_id");
		//title_str += '<li>'+item_name+'</li>\n';
		switch(item_id)
		{
			case "title" : item_str += '	<li><a href="$r.content_url" target="_blank" title="${r.title}">${FormatUtil.cutString($r.title,'+title_count+',"...")}</a></li>\n';
						  break;
			case "released_dtime" : item_str += '	<li>${FormatUtil.formatDate($r.released_dtime,"'+format_date+'")}</li>\n';
						  break;
			case "thumb_url" : item_str += '	<li><img src="${r.thumb_url}"></li>\n';
						  break;
			default : item_str += '	<li>${r.'+item_id+'}</li>\n';
						  break;
		}		
	});
	list_html += '<!-- ------------------信息热点列表　开始---------------- -->\n';
	list_html += '<ul>\n';
	list_html += '#foreach( $r in $InfoUtilData.getInfoHotList("site_id=$site_id'+str+'"))\n';
	list_html += item_str;
	list_html += '#end\n';
	list_html += '</ul>\n';
	list_html += '<!-- ------------------信息热点列表　结束---------------- -->\n';
	return list_html;
}

function saveGGFWHotList()
{
	var title_count = $("#title_count").val();
	var format_date = $("#format_date").val();
	var str = infoPublicStr();
	var cat_ids = getCategoryIDS("leftMenuTree");
	if(cat_ids != "")
	{
		str = ";cat_id="+cat_ids+str;
	}
	if($("#is_show_thumb").is(':checked'))
	{
		str = ";is_show_thumb=true"+str;
	}

	var list_html = "";	
	var item_str = "";
	//var title_str = "";
	$("#select_item_list > li").each(function(){
		var item_name = $(this).text();
		var item_id = $(this).attr("item_id");
		//title_str += '<li>'+item_name+'</li>\n';
		switch(item_id)
		{
			case "title" : item_str += '	<li><a href="$r.content_url" target="_blank" title="${r.title}">${FormatUtil.cutString($r.title,'+title_count+',"...")}</a></li>\n';
						  break;
			case "released_dtime" : item_str += '	<li>${FormatUtil.formatDate($r.released_dtime,"'+format_date+'")}</li>\n';
						  break;
			case "thumb_url" : item_str += '	<li><img src="${r.thumb_url}"></li>\n';
						  break;
			default : item_str += '	<li>${r.'+item_id+'}</li>\n';
						  break;
		}		
	});
	list_html += '<!-- ------------------服务信息热点列表　开始---------------- -->\n';
	list_html += '<ul>\n';
	list_html += '#foreach( $r in $InfoUtilData.getFWInfoHotList("site_id=$site_id'+str+'"))\n';
	list_html += item_str;
	list_html += '#end\n';
	list_html += '</ul>\n';
	list_html += '<!-- ------------------服务信息热点列表　结束---------------- -->\n';
	return list_html;
}

function saveGKHotList()
{
	var title_count = $("#title_count").val();
	var format_date = $("#format_date").val();
	var str = infoPublicStr();	
	var node = $('#leftMenuTree').tree('getSelected');
	if(node != null && node.iconCls == "icon-gknode")
	{
		str = ";node_id="+node.attributes.t_node_id+str;
	}
	else
	{
		$(".list_tab").eq(1).click();
		msgWargin("请选择信息公开节点");
		return;
	}	

	var cat_ids = getCategoryIDS("leftMenuTree2");
	if(cat_ids != "")
	{
		str = ";cat_id="+cat_ids+str;
	}
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
	list_html += '<!-- ------------------信息公开热点列表　开始---------------- -->\n';
	list_html += '<ul>\n';
	list_html += '#foreach( $r in $InfoUtilData.getGKInfoHotList("site_id=$site_id'+str+'"))\n';
	list_html += item_str;
	list_html += '#end\n';
	list_html += '</ul>\n';
	list_html += '<!-- ------------------信息公开热点列表　结束---------------- -->\n';
	return list_html;
}

function saveSQHotList()
{
	var title_count = $("#title_count").val();
	var format_date = $("#format_date").val();
	var str = infoPublicStr();
	var list_html = "";	
	var item_str = "";
	var model_id = "";
	$("#leftMenuTree :checked").each(function(){
		model_id += ","+$(this).val();
	});
	if(model_id == "" || model_id == null)
	{
		$(".list_tab").eq(1).click();
		msgWargin("请选择诉求业务");
		return;
	}else
	{
		model_id = model_id.substring(1);
		str = "model_id="+model_id+str;
	}

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
	list_html += '#foreach( $r in $AppealData.getAppealHotList("'+str+'"))\n';
	list_html += item_str;
	list_html += '#end\n';
	list_html += '</ul>\n';
	list_html += '<!-- ------------------诉求信件列表　结束---------------- -->\n';
	return list_html;
}

function saveInterViewHotList()
{
	var title_count = $("#title_count").val();
	var intro_count = $("#intro_count").val();
	var format_date = $("#format_date").val();
	var str = infoPublicStr();
	var list_html = "";	
	var item_str = "";
	var cat_id = "";
	$("#leftMenuTree :checked").each(function(){
		cat_id += ","+$(this).val();
	});
	if(cat_id == "" || cat_id == null)
	{
		
	}else
	{
		cat_id = cat_id.substring(1);
		str = ";cat_id="+cat_id+str;
	}
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
		
	list_html += '<!-- ------------------访谈热点列表　开始---------------- -->\n';
	list_html += '<ul>\n';
	list_html += '#foreach( $r in $InterViewData.getInterViewRecommendList("site_id=$site_id;'+str.substring(1)+'"))\n';
	list_html += item_str;
	list_html += '#end\n';
	list_html += '</ul>\n';
	list_html += '<!-- ------------------访谈热点列表　结束---------------- -->\n';
	return list_html;
}

function saveSurveyHotList()
{
	var title_count = $("#title_count").val();
	var intro_count = $("#intro_count").val();
	var format_date = $("#format_date").val();
	var str = infoPublicStr();
	var list_html = "";	
	var item_str = "";
	var cat_id = "";
	$("#leftMenuTree :checked").each(function(){
		cat_id += ","+$(this).val();
	});
	if(cat_id == "" || cat_id == null)
	{
		
	}else
	{
		cat_id = cat_id.substring(1);
		str = ";cat_id="+cat_id+str;
	}
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
	
	list_html += '<!-- ------------------信息列表　开始---------------- -->\n';
	list_html += '<ul>\n';
	list_html += '#foreach( $r in $SurveyData.getSurveyRecommendList("site_id=$site_id;'+str.substring(1)+'"))\n';	
	list_html += item_str;
	list_html += '#end\n';
	list_html += '</ul>\n';
	list_html += '<!-- ------------------信息列表　结束---------------- -->\n';
	return list_html;
}
