<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>接收配置</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/recordList.js"></script>
<script type="text/javascript">

var site_id = "${param.site_id}"

$(document).ready(function(){
	reloadBefore();
	initButtomStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initTable();
	reloadList();
	reloadAfter();
});

function openSearchPage()
{
	top.OpenModalWindow("报送记录搜索","/sys/sendinfo/record/recordSearch.jsp?site_id="+site_id,400,260);	
}

function searchHandl(con)
{
	m.put("conn", con);
	reloadList();
}
</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td class="fromTabs">				
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','deleteRecordInfo()');" value="删除" />	
				&nbsp;&nbsp;
				<input id="btnSearch" name="btn6" type="button" onclick="openSearchPage()" value="搜索" />
				<span class="blank3"></span>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
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
			<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','deleteRecordInfo()');" value="删除" />	
			&nbsp;&nbsp;
			<input id="btnSearch" name="btn6" type="button" onclick="openSearchPage()" value="搜索" />
		</td>
	</tr>
   </table>	
</div>
</body>
</html>