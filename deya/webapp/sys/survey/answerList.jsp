<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>问卷调查管理列表页面</title>


<jsp:include page="../include/include_tools.jsp"/>
<script src="js/answerList.js"></script>	
<script type="text/javascript">
var div_height = 0;		
var s_id = request.getParameter("sid");
$(document).ready(function () {		
	initTable();
	initButtomStyle();
	searchHandl("");			
}); 

function searchHandl(str)
{
	con = str;
	showTurnPage();
	showList();	
}	

function goOtherPage()
{
	window.location.href = "statisticsSubject.jsp?sid="+s_id;
}

function openSearch()
{
	top.OpenModalWindow("数据统计查询","/sys/survey/search_statistics.jsp?s_id="+s_id,800,490);
}


</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>	
			<td class="fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="goOtherPage()" value="数据统计" />
				<input id="btn1" name="btn1" type="button" onclick="openSearch()" value="查询" />	
				<span class="blank3"></span>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
				
			</td>		
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div><!-- 列表DIV -->
	<div id="turn"></div><!-- 翻页DIV -->
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn1" name="btn1" type="button" onclick="goOtherPage()" value="数据统计" />
				<input id="btn1" name="btn1" type="button" onclick="openSearch()" value="查询" />
		</td>
	</tr>
   </table>	
</div>
</body>
</html>