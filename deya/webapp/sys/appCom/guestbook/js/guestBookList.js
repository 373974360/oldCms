var GuestBookRPC = jsonrpc.GuestBookRPC;
var GuestBookBean = new Bean("com.deya.wcm.bean.appCom.guestbook.GuestBookBean",true);

var tp = new TurnPage();
var beanList = null;
var con_m = new Map();

function reloadList()
{
	fillBean();
	showTurnPage();
}

function fillBean()
{
	var sortCol = tp.sortCol;
	var sortType = tp.sortType;
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "id";
		sortType = "desc";
	}
	con_m.put("sort_name", sortCol);
	con_m.put("sort_type", sortType);
	con_m.put("page_size", tp.pageSize);
	con_m.put("start_num", tp.getStart());

	con_m.put("gbs_id", gbs_id);
	
	beanList = GuestBookRPC.getGuestbookList(con_m);
	beanList = List.toJSList(beanList);//把list转成JS的List对象
		
	var strTable = "";
	$("#gb_tbody").empty();
	if( !beanList || beanList.size()==0)
	{
		strTable = '<tr><td colspan="3">暂无数据...</td><td></td></tr>';
		$("#gb_tbody").append(strTable);
		Init_InfoTable_page("gb_table");
		return;
	}	
	
	for(var n = 0; n < beanList.size(); n++)
	{
		var bean = beanList.get(n); 
		// 第一个TD
		strTable = '<tr id="tr_'+bean.id+'"><td align="center"><input id="checkbox_index" name="" class="inputHeadCol" type="checkbox" value='+n+' /></td>';
		// 第二个TD
		// 如果是匿名用户，无点击事件
		var nickname = bean.nickname;// 用户昵称
		if(nickname == "" || nickname == null)
		{
			nickname = "匿名";
		}
		// 添加评论标题header
		strTable += '<td align="left"><div class="lineHeight180"><div class="right"><span style="width:70px;display:block;float:left">'+nickname+'</span>  '; //todo 点击昵称
		strTable += '<span style="width:110px;display:block;float:left">'+bean.ip+'</span><span></span>';//todo 点击ip事件
		strTable += '<span>'+bean.add_time+'</span></div><div>';		
		strTable += '<span>'+bean.title+'</span>';//todo 点击标题事件
		//strTable += '<a href="javascript:void(0)" title="全部评论"><img src="../../images/dialog.gif" onclick="getAllGuestBookByItemID('+n+')" /></a>';//todo 点击全部评论事件
		strTable += '</div></div>';
		// 评论正文
		strTable += '<div><div id="cnt'+n+'" name="cnt'+n+'" style="width:585px;height:120px;overflow:auto" class="lineHeight140 border_color" >'+bean.content+'</div>';
		strTable += '</div><span class="blank3"></span></td>';
		
		// 第三个TD
		strTable += '<td align="left" valign="middle" class=""><ul class="comment_opt">';
		var pub_name = "未发布";
		var rep_name = "未回复";
		if(bean.publish_status == 1) pub_name = "已发布";
		if(bean.reply_status == 1) rep_name = "已回复";
		strTable += '<li>'+ pub_name +'</li>';
		strTable += '<li>'+ rep_name +'</li></ul></td>';

		strTable += '<td align="left" valign="middle" class=""><ul class="comment_opt">';
		
		strTable += '<li><img src="../../images/del.gif" onclick="msgConfirm(WCMLang.Delete_confirm,\'deleteGuestBook('+bean.id+')\')" class="pointer" title="删除"/></li>';
		strTable += '<li><img src="../../images/edit.gif" onclick="openUpdateGuestbookPage('+bean.id+');" class="pointer" title="编辑"/></li>';
		strTable += '<li><img src="../../images/view.png" onclick="viewGuestBook('+bean.id+');" class="pointer" title="查看"/></li></ul></td>';
			
		strTable += '<td class="">&#160;</td></tr>';
		$("#gb_tbody").append(strTable);
	}
	
	Init_InfoTable_page("gb_table");
}

function showTurnPage()
{
	tp.total = GuestBookRPC.getGuestbookCount(con_m);
	tp.show("turn","");	
	tp.onPageChange = fillBean;
}

function deleteGuestBook(id)
{
	if(GuestBookRPC.deleteGuestBook(id))
	{
		msgAlert("留言信息"+WCMLang.Delete_success);
		reloadList();
	}else
		msgWargin("留言信息"+WCMLang.Delete_fail);
}

function openUpdateGuestbookPage(id)
{
	addTab(true,"/sys/appCom/guestbook/reply_gb.jsp?site_id="+site_id+"&topnum="+curTabIndex+"&id="+id+"&gbs_id="+gbs_id+"&cat_id="+cat_id,"编辑留言");
}

function updateGuestbook()
{
	var bean = BeanUtil.getCopy(GuestBookBean);	
	$("#guestbook_table").autoBind(bean);
	bean.id = id;
	bean.content = KE.html("content");
	bean.reply_content = KE.html("reply_content");			

	if(GuestBookRPC.updateGuestBook(bean))
	{
		msgAlert("留言信息"+WCMLang.Add_success);
		getCurrentFrameObj(topnum).reloadList();
		tab_colseOnclick(curTabIndex);
	}else
		msgWargin("留言信息"+WCMLang.Add_fail);
}

function viewGuestBook(id)
{
	addTab(true,"/sys/appCom/guestbook/view_gb.jsp?site_id="+site_id+"&topnum="+curTabIndex+"&id="+id+"&gbs_id="+gbs_id+"&cat_id="+cat_id,"查看留言");
}

// 取得选中的评论ID
function getSelecteCheckboxValue()
{
	var ids = "";
	$("#gb_tbody :checkbox").each(function()
	{
		if($(this).is(':checked'))
		{
			var index = $(this).val();
			ids += ","+beanList.get(index).id;
		}
	});
	if(ids != "")
	{
		ids = ids.substring(1);
	}
	
	return ids;
}

// 删除操作验证(可多选)
function deleteSelect(handl_name)
{
	var selectIDS = getSelecteCheckboxValue();
	
	if(selectIDS == "" || selectIDS == null)
	{
		msgWargin(WCMLang.Select_empty);
		return;
	}else
	{		
		msgConfirm(WCMLang.Delete_confirm,handl_name);
	}
}
//批量删除
function batchDeleteGuestBook()
{	
	deleteGuestBook(getSelecteCheckboxValue());
}

// 多选操作验证
function multiSelect(handl_name)
{
	var selectIDS = getSelecteCheckboxValue();
	if(selectIDS == "" || selectIDS == null)
	{
		msgWargin(WCMLang.Select_empty);
		return;
	}else
	{
		eval(handl_name);
	}
}

function publishGuestBook(publish_status)
{
	var selectIDS = getSelecteCheckboxValue();
	if(GuestBookRPC.publishGuestBook(selectIDS,publish_status))
	{
		msgAlert("留言"+WCMLang.Publish_success);
		reloadList();
	}else
	{
		msgWargin("留言"+WCMLang.Publish_fail);
	}
}

function searchList()
{
	con_m.remove("start_day");
	con_m.remove("end_day");
	con_m.remove("publish_status");
	con_m.remove("reply_status");
	con_m.remove("keyword");

	var start_day = $("#start_day").val();
	if(start_day != "" && start_day != null)
		con_m.put("start_day", start_day);

	var end_day = $("#end_day").val();
	if(end_day != "" && end_day != null)
		con_m.put("end_day", end_day);

	var search_publish = $("#search_publish").val();
	if(search_publish != "" && search_publish != null)
		con_m.put("publish_status", search_publish);

	var search_reply = $("#search_reply").val();
	if(search_reply != "" && search_reply != null)
		con_m.put("reply_status", search_reply);

	var searchkey = $("#searchkey").val();
	if(searchkey != "" && searchkey != null)
		con_m.put("keyword", searchkey);

	reloadList();
}

function getGBClassList()
{
	var list = GuestBookRPC.getGuestBookClassList(site_id,cat_id);//第一个参数为站点ID，暂时默认为空	
	list = List.toJSList(list);//把list转成JS的List对象
	if(list != null && list.size() > 0)
	{
		$("#class_id").addOptions(list,"class_id","name");
		$(".class_tr").show();
	}
}