<%@ page contentType="text/html; charset=utf-8"%>
<%
String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>问卷调查管理列表页面</title>


<jsp:include page="../include/include_tools.jsp"/>
<script src="js/surveyList.js"></script>	
<script type="text/javascript">
var site_id = "<%=site_id%>";
var div_height = 0;	

var current_date = SurveyRPC.getCurrentDate();
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();	
	showList();
	showTurnPage();
});


</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>	
			<td class="fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="fnAddSurvey('')" value="添加" />
				<input id="btn1" name="btn1" type="button" onclick="publicSelectSinglCheckbox(table,'id','showSurveyAttr(\'bath\')')" value="属性设置" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'id','fnUpdateSurvey()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'id','batchPublishSurvey(1)')" value="发布" />	
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'id','batchPublishSurvey(-1)')" value="撤销" />			
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','batchDelSurveyHandl()')" value="删除" />
				<!-- <input id="btn1" name="btn1" type="button" onclick="fnSearch()" value="查询" /> -->	
				<span class="blank3"></span>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
				<input id="searchkey" type="text" class="input_text" value=""  /><input id="btnSearch" type="button" class="btn x2" value="搜索" onclick="roleSearchHandl(this)"/>
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
			<input id="btn1" name="btn1" type="button" onclick="fnAddSurvey('')" value="添加" />
			<input id="btn1" name="btn1" type="button" onclick="publicSelectSinglCheckbox(table,'id','showSurveyAttr(\'bath\')')" value="属性设置" />
			<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox(table,'id','updateRecommend(1)')" value="推荐" />
			<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox(table,'id','updateRecommend(0)')" value="不推荐" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'id','fnUpdateSurvey()');" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'id','batchPublishSurvey(1)')" value="发布" />	
			<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'id','batchPublishSurvey(-1)')" value="撤销" />	
			<input id="btn1" name="btn1" type="button" onclick="publicSelectSinglCheckbox(table,'id','showAnswer()')" value="查看问卷" />
			<input id="btn1" name="btn1" type="button" onclick="publicSelectSinglCheckbox(table,'id','openSubject()')" value="数据统计" />
			<input id="btn1" name="btn1" type="button" onclick="publicSelectSinglCheckbox(table,'id','openAnswer()')" value="所有答卷" />
			<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','batchDelSurveyHandl()')" value="删除" />
			<!-- <input id="btn1" name="btn1" type="button" onclick="fnSearch()" value="查询" />		 -->	
		</td>
	</tr>
   </table>	
</div>
</body>
</html>
