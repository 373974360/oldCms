var CommentManRPC = top.jsonrpc.CommentManRPC;
var SQRPC = top.jsonrpc.SQRPC;
var CommentBean = new Bean("com.deya.wcm.bean.system.comment.CommentBean",true);

var tp = new TurnPage();
var beanList = null;
var con_m = new Map();
// 新闻标题map
var title_m = new Map();// key=评论对象id，value=评论对象标题
// ip地址map
var ip_m = new Map();// key=ip,value=ip对应的地址

function reloadCommentList()
{
	fillBean();
	showTurnPage();
}

function fillBean()
{
	con_m.put("page_size", tp.pageSize);
	con_m.put("start_num", tp.getStart());
	beanList = CommentManRPC.getCommentList(con_m);
	beanList = List.toJSList(beanList);//把list转成JS的List对象
	
	/* 
	title_m = ;
	title_m = Map.toJSMap(title_m);
	*/
	var strTable = "";
	$("#cmt_tbody").empty();
	if( !beanList || beanList.size()==0)
	{
		strTable = '<tr><td colspan="3">暂无数据...</td><td></td></tr>';
		$("#cmt_tbody").append(strTable);
		Init_InfoTable_page("cmt_table");
		return;
	}

	//ip_m = CommentManRPC.getIPAdress(beanList);
	//ip_m = Map.toJSMap(ip_m);
	
	for(var n = 0; n < beanList.size(); n++)
	{
		var bean = beanList.get(n); 
		// 第一个TD
		strTable = '<tr id="tr_'+bean.cmt_id+'"><td align="center"><input id="checkbox_index" name="" class="inputHeadCol" type="checkbox" value='+n+' /></td>';
		// 第二个TD
		// 如果是匿名用户，无点击事件
		var member_onclick = "javascript:showMemberOrIpCommentList('me_nickname','"+bean.me_nickname+"')";//点击用户昵称触发的事件
		var nickname = bean.me_nickname;// 用户昵称
		if(bean.me_id == null || bean.me_id=="" || bean.me_id =="null")
		{
			nickname = "匿名";
			member_onclick = "javascript:void(0)";
		}
		// 添加评论标题header
		strTable += '<td align="left"><div class="lineHeight180"><div class="right"><a href="'+member_onclick+'"><span>'+nickname+'</span></a>  '; //todo 点击昵称
		strTable += '<a href="javascript:showMemberOrIpCommentList(\'cmt_ip\',\''+bean.cmt_ip+'\')"><span>'+bean.cmt_ip+'</span></a><span>('+bean.ip_addr+') </span><span>顶['+bean.support_num+']</span>';//todo 点击ip事件
		strTable += '<span>'+bean.add_dtime+'</span></div><div>';
		strTable += '<span><a target="_blank" href="/appeal/view.jsp?model_id='+bean.model_id+'&sq_id='+bean.item_id+'&dept_id='+bean.dept_id+'">'+bean.rele_title+'</a></span>';//todo 点击标题事件
		strTable += '<a href="javascript:void(0)" title="全部评论"><img src="../../images/dialog.gif" onclick="getAllCommentByItemID('+n+')" /></a></div></div>';//todo 点击全部评论事件
		// 评论正文
		strTable += '<div><textarea id="cnt'+n+'" name="cnt'+n+'" style="width:665px;height:80px" class="lineHeight140" readOnly="readOnly">'+bean.cmt_content+'</textarea>';
		strTable += '</div><span class="blank3"></span></td>';
		
		// 第三个TD
		strTable += '<td align="left" valign="middle" class=""><ul class="comment_opt">';
		// 只在审核页面显示通过图标
		if(type == "unchecked")
		{
			strTable += '<li><img class="ok_type" src="../../images/ok.gif"  onclick="checkComment('+n+');" /></li>';
		}
		strTable += '<li><img src="../../images/del.gif" onclick="parent.msgConfirm(WCMLang.Delete_confirm,\'deleteComment('+n+')\')"/></li>';
		strTable += '<li><img src="../../images/edit.gif" onclick="editOption('+n+');"/></li></ul></td>';
			
		strTable += '<td></td></tr>';
		$("#cmt_tbody").append(strTable);
	}
	
	Init_InfoTable_page("cmt_table");
}

function getSQBrowserPageUrl(sq_id)
{
	var sq_bean = SQRPC.getSQSimpleBean(sq_id);  //
	if(sq_bean != null)
	{
		return "/appeal/view.jsp?model_id="+sq_bean.model_id+"&sq_id="+sq_id+"&dept_id="+sq_bean.dept_id;
	}else
		return "";
}

function showTurnPage()
{
	tp.total = CommentManRPC.getCommentListCnt(con_m);
	tp.show("turn","");	
	tp.onPageChange = fillBean;
}

function showMemberOrIpCommentList(keys,val)
{
	con_m.put("search","search");
	con_m.put(keys, val);
	reloadCommentList();
}

// 编辑评论内容
function editOption(index)
{
	if($("#cnt"+index).attr("readOnly")== true || $("#cnt"+index).attr("readOnly") == "readOnly")
	{
		$("#cnt"+index).removeAttr("readOnly");
		return;
	}
	else
	{	
		var updateBean = beanList.get(index);
		updateBean.cmt_content = $("#cnt"+index).val();
		if(updateComment(updateBean))
		{
			$("#cnt"+index).attr("readOnly","readOnly");
			top.msgAlert("评论内容"+WCMLang.Set_success);
		}
		else
		{
			top.msgWargin("评论内容"+WCMLang.Set_fail);
		}
	}
}

// 通过审核
function checkComment(index)
{
	var updateBean = beanList.get(index);
	updateBean.cmt_status = 1;
	if(updateComment(updateBean))
	{
		//hideTableRow(updateBean.cmt_id);
		top.msgAlert("评论内容审核通过");
		reloadCommentList();
	}
	else
	{
		top.msgWargin("评论内容审核失败");
	}
}

// 批量通过审核
function batchCheckComment()
{
	var ids = getSelecteCheckboxValue();
	var updateBean = BeanUtil.getCopy(CommentBean);	
	
	updateBean.cmt_id = ids;
	updateBean.cmt_content = null;
	updateBean.cmt_status = 1;
	if(updateComment(updateBean))
	{
		//hideTableRow(ids);
		top.msgAlert("评论内容审核通过");
		reloadCommentList();
	}
	else
	{
		top.msgWargin("评论内容审核失败");
	}
}

// 删除评论
function deleteComment(index)
{
	var delBean = beanList.get(index);
	var del_m = new Map();
	del_m.put("con_name","cmt_id");
	del_m.put("con_value",delBean.cmt_id);
	setURLPara(del_m);
	
	if(CommentManRPC.deleteComment(del_m))
	{
		//hideTableRow(delBean.cmt_id);
		top.msgAlert("评论"+WCMLang.Delete_success);
		reloadCommentList();
	}
	else
	{
		top.msgWargin("评论"+WCMLang.Delete_fail);
	}
}

// 批量删除评论
function batchDeleteComment()
{
	var ids = getSelecteCheckboxValue();
	var del_m = new Map();
	del_m.put("con_name","cmt_id");
	del_m.put("con_value",ids);
	setURLPara(del_m);
		
	if(CommentManRPC.deleteComment(del_m))
	{	
		//hideTableRow(ids);
		top.msgAlert("评论"+WCMLang.Delete_success);
		reloadCommentList();
	}
	else
	{
		top.msgWargin("评论"+WCMLang.Delete_fail);
	}

}


// 修改评论内容
function updateComment(updateBean)
{
	if(CommentManRPC.updateComment(updateBean))
	{
		return true;
	}
	else
	{
		return false;
	}
}

// 刷新操作
function clearPara()
{
	$("#selectAll").attr("checked",false);
	con_m.clear();
	initPage();
	reloadCommentList();
}

// 搜索事件
function searchList()
{
	con_m.clear();
	initPage();
	
	var start_day = $("#start_day").val();
	var end_day = $("#end_day").val();
	var searchType = $("#searchFields").val();
	var key = $("#searchkey").val();
	if(start_day=="" && end_day == "" && key=="")
	{
		top.msgAlert("请至少选择一个条件");
		return;
	}
	if(start_day > end_day)
	{
		top.msgAlert("开始日期不能大于结束日期");
		return;
	}
	con_m.put("search","search");
	con_m.put(searchType, key);
	if(start_day != "")
	{
		con_m.put("start_day",start_day+" 00:00:00");
	}
	if(end_day != "")
	{
		con_m.put("end_day",end_day+" 23:59:59");
	}
	reloadCommentList();
}

//全部评论点击事件
function getAllCommentByItemID(index)
{
	con_m.clear();
	initPage();
	
	var delBean = beanList.get(index);
	con_m.put("item_id",delBean.item_id);
	
	reloadCommentList();
}
//******************************************一下为公共操作函数***************************************

// 添加URL传值参数--参数为Map类型
function setURLPara(map)
{
	if(app_id != null && app_id != "null" && app_id != "")
	{
		map.put("app_id",app_id);
	}
	if(site_id != null && site_id != "null" && site_id != "")
	{
		map.put("site_id",site_id);
	}
}

// 取得选中的评论ID
function getSelecteCheckboxValue()
{
	var ids = "";
	$("#cmt_tbody :checkbox").each(function()
	{
		if($(this).is(':checked'))
		{
			var index = $(this).val();
			ids += ","+beanList.get(index).cmt_id;
		}
	});
	if(ids != "")
	{
		ids = ids.substring(1);
	}
	
	return ids;
}

// 多选操作验证
function multiSelect(handl_name)
{
	var selectIDS = getSelecteCheckboxValue();
	if(selectIDS == "" || selectIDS == null)
	{
		parent.msgWargin(WCMLang.Select_empty);
		return;
	}else
	{
		eval(handl_name);
	}
}

// 删除操作验证(可多选)
function deleteSelect(handl_name)
{
	var selectIDS = getSelecteCheckboxValue();
	
	if(selectIDS == "" || selectIDS == null)
	{
		parent.msgWargin(WCMLang.Select_empty);
		return;
	}else
	{		
		parent.msgConfirm(WCMLang.Delete_confirm,handl_name);
	}
}
	
// 删除，通过后页面隐藏
function hideTableRow(ids)
{
	if(ids == "" || ids == null)
	{
		return;
	}else
	{
		var arrIDS = ids.split(",");
		for (var i=0; i< arrIDS.size(); i++)
		{
			$("#tr_"+arrIDS[i]).hide();
			tp.total = tp.total-1;
		}
		tp.show("turn","");	
	}
}