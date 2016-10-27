<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>访谈模型管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script src="js/subjectList.js"></script>	
<script src="js/public.js"></script>
<script type="text/javascript">

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	showTurnPage();
	showList();
});


</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>	
			<td>
				<input id="btn1" name="btn1" type="button" onclick="fnAddSubject()" value="添加" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'id','fnUpdateSubject()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'id','bathSubjectSubmits()')" value="提交" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectSinglCheckbox(table,'id','fnActorManager()')" value="嘉宾资料管理" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectSinglCheckbox(table,'id','fnReleInfoManager()')" value="相关信息管理" />				
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','batchDelSubjects()');" value="删除" />		
				<input id="btn3" name="btn3" type="button" onclick="openSearchWin()" value="查询" />	
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
			<input id="btn1" name="btn1" type="button" onclick="fnAddSubject()" value="添加" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'id','fnUpdateSubject()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'id','bathSubjectSubmits()')" value="提交" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectSinglCheckbox(table,'id','fnActorManager()')" value="嘉宾资料管理" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectSinglCheckbox(table,'id','fnReleInfoManager()')" value="相关信息管理" />				
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','batchDelSubjects()');" value="删除" />		
				<input id="btn3" name="btn3" type="button" onclick="openSearchWin()" value="查询" />				
		</td>
	</tr>
   </table>	
</div>
</body>
</html>