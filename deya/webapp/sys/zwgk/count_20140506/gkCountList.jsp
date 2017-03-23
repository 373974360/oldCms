<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@page import="com.deya.util.DateUtil"%>
<%
String site_id = request.getParameter("site_id");
String app_id = request.getParameter("app_id");

String start_day = request.getParameter("start_day");
String end_day = request.getParameter("end_day");
String type = request.getParameter("type");

if(app_id == null || app_id.trim().equals("")){
	app_id = "zwgk";
}

   String now = DateUtil.getCurrentDate();
   String now1 = now.substring(0,7)+"-01";
   if(start_day == null || "".equals(start_day)) {
   		start_day = now1;
   }
   if(end_day == null || "".equals(end_day)) {
   		end_day = now;
   }
   
   if(end_day.length()>10){
	   end_day = end_day.substring(0,10);
   }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//CN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>政务公开应用栏目工作量统计</title>



<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
<script type="text/javascript" src="js/gkCountList.js"></script>
<script type="text/javascript">

var site_id = "<%=site_id%>";
var type = "<%=type%>";
var start_day = "<%=start_day%>";
var end_day = "<%=end_day%>";

$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	$("#backBtn").hide();
	$("#backBtn1").hide();
	
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	$("#countList").hide();
	
	if(type == "back")
	{
		$("#underline").addClass("line2h");
		$("#backBtn").show();
		$("#backBtn1").show();
		search();
	}
});

function ChangeStatus(o)
{
	if (o.parentNode)
	{
		if (o.parentNode.parentNode.parentNode.className == "Opened")
		{
			o.parentNode.parentNode.parentNode.className = "Closed";
		}
		else
		{
			o.parentNode.parentNode.parentNode.className = "Opened";
		}
	}
}

</script>
</head>
<body>
<div>
<table class="table_option fromTabs" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" width="80px">
			<span class="f_red">*</span>时间范围：
		</td>
        <td>
				<input class="Wdate" type="text" name="start_day" id="start_day" size="11" style="height:16px;line-height:16px;" 
				value="<%=start_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">
		         --
			    <input class="Wdate" type="text" name="end_day" id="end_day" size="11" style="height:16px;line-height:16px;"  
			value="<%=end_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">
			<span class="blank3"></span>
		</td>
			<td align="right" valign="middle">
				 <input id="addButton" name="btn1" type="button" onclick="search()" value="统计" />	
				 <input type="button" id="outFileBtn" style="display:none" onclick="outFileBtn()" value="导出"/>
				 <input id="backBtn" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex)" value="关闭" />
				 <span class="blank3"></span>
			</td>
		</tr>
</table>
<span class="blank3"></span>
</div>
<iframe src="" id="countList" name="countList" width="100%"	height="380" frameborder="0"  scrolling="auto">

</iframe>
	<span class="blank12"></span>
<div id="underline"></div>
<span class="blank3"></span>

        <input id="backBtn1" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex)" value="关闭" />
</div>
</body>
</html>