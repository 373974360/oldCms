<%@ page contentType="text/html; charset=utf-8"%>
<%
	String app_id = request.getParameter("app");
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>留言板主题管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/guestBookSubList.js"></script>
<script type="text/javascript">
var cat_map = new Map();
var app_id = "<%=app_id%>";
var site_id = "<%=site_id%>";
if(site_id == null || site_id == "null")
	site_id = "";

$(document).ready(function(){	
	initButtomStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	getGbCategoryList();
	initTable();
	reloadList();
	
});

function getGbCategoryList()
{
	var list = GuestBookRPC.getGuestBookCategoryList(site_id);
	list = List.toJSList(list);
	if(list != null && list.size() > 0)
	{
		for(var i=0;i<list.size();i++)
		{
			$("#cat_id").addOptionsSingl(list.get(i).cat_id,list.get(i).title);
			cat_map.put(list.get(i).cat_id,list.get(i).title);
		}		
	}
}



</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td class="fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="openAddGuestBookSubPage();" value="新建主题" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'gbs_id','openUpdateGuestBookSubPage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'gbs_id','publishGuestBookSub(1)')" value="发布" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'gbs_id','publishGuestBookSub(0)')" value="撤销" />
				<input id="btn3" name="btn3" type="button" onclick="deleteSinglRecord(table,'gbs_id','deleteGuestBookSub()');" value="删除" />	
				<span class="blank3"></span>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
				<select class="width150" id="cat_id" name="cat_id" > 
					<option value="">请选择分类</option>
				</select>
				<input id="searchkey" type="text" class="input_text" value=""  />
				<input id="btnSearch" type="button" class="btn x2" value="搜索" onclick="selectSubList()"/>
				<span class="blank3"></span>
			</td>		
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div><!-- 列表DIV -->
	<div id="turn"></div><!-- 翻页DIV -->
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn1" name="btn1" type="button" onclick="openAddGuestBookSubPage();" value="新建主题" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'gbs_id','openUpdateGuestBookSubPage()');" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'gbs_id','publishGuestBookSub(1)')" value="发布" />
			<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'gbs_id','publishGuestBookSub(0)')" value="撤销" />
			<input id="btn3" name="btn3" type="button" onclick="deleteSinglRecord(table,'gbs_id','deleteGuestBookSub()');" value="删除" />
		</td>
	</tr>
   </table>	
</div>
</body>
</html>