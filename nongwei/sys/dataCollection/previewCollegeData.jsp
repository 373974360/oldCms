<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String snum = request.getParameter("snum");
String siteid = request.getParameter("site_id");
String app_id = request.getParameter("app_id");

if(app_id == null || app_id.equals("null")){
	app_id = "0";
}
if(snum == null || snum.trim().equals("") || snum.trim().equals("null") ){
	snum = "0";
}	
%>
<html>
<head>
<title>采集信息列表</title>
<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript" src="../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../cms/info/article/js/public.js"></script>
<script type="text/javascript" src="js/previewColl.js"></script>
<script type="text/javascript">
var snum = "<%=snum%>";
var app_id = "<%=app_id%>";
var site_id = "<%=siteid%>";

$(document).ready(function(){
	initTable();
	initButtomStyle();
	initTabAndStatus();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	$(".list_tab").eq(snum).click();
	showCollRules();
});
</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" > 
	<tr> 
		<td align="left" width="350"> 
			<ul class="fromTabs"> 
				<li class="list_tab list_tab_cur"> 
					<div class="tab_left"> 
						<div class="tab_right">已采用</div> 
					</div> 
				</li> 
				<li class="list_tab"> 
					<div class="tab_left"> 
						<div class="tab_right">未采用</div> 
					</div> 
				</li> 
             </ul> 
		</td>
		<td class="fromTabs" width="60">规则名称:</td> 
		<td align="right" class="fromTabs" width="70"> 
			<select id="selectCollRule" name="selectCollRule" class="input_select width150" onchange="changeCollRule(this.value)"> 

			</select> 
		</td> 
		<td align="right" class="fromTabs" width="60">新闻标题:</td>
		<td align="left" class="fromTabs" width="360px;">
			<input id="searchCollTitle" type="text" class="input_text" style="width:100px;" value=""  />
			&nbsp;采集时间：&nbsp;<input type="text" id="beignTime" name="beignTime"  size="11" class="input_text" value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">至&nbsp;<input type="text" id="endTime" name="endTime"  size="11" class="input_text" value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">
			<input id="btn" type="button" value="搜索" onclick="searchCollInfo()"/>
		</td>
	</tr> 
</table> 
	<span class="blank3"></span>
	<div id="tableCollInfo"></div>
	<div id="turn"></div>
<div id="handleBtn" style="display:none;">
	<table class="table_option" border="0" cellpadding="0" cellspacing="0"> 
		<tr> 
			<td align="left" valign="middle"> 
				<input id="userAndPub" type="button" name="btn1" onclick="publicSelectCheckbox(table,'id','useAndpublishInfo()')" value="采用并发布" />
				<input id="userNoPub" type="button" name="btn1" onclick="publicSelectCheckbox(table,'id','useNopublishInfo()')" value="采用待发" />
				<input id="deleteCollInfo" name="btn1" type="button" onclick="deleteRecord(table,'id','deleteCollInfoByid()');" value="删除" />
				<input id="userAddCancel" type="button" name="btn1" onclick="top.tab_colseOnclick(top.curTabIndex);" value="取消">
			</td> 
		</tr> 
	</table> 
</div>
</div>
</body>
</html>
