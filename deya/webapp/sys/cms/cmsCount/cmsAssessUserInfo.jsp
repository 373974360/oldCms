<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%
	String site_id = request.getParameter("site_id");
	String app_id = request.getParameter("app_id");
	String cat_id = request.getParameter("cat_id");
	String input_user = request.getParameter("input_user");
	if(cat_id == null || "".equals(cat_id) || "null".equals(cat_id))
		cat_id = "0";
	if(site_id == null || "".equals(site_id) || "null".equals(site_id))
		site_id = "";
	String start_day = request.getParameter("start_day");
	String end_day = request.getParameter("end_day");
	
	String selectedId = request.getParameter("selectedId");
	String is_host = request.getParameter("is_host");

   String now = DateUtil.getCurrentDate();
   String now1 = now.substring(0,7)+"-01";
   if(start_day == null || "".equals(start_day)) {
   		start_day = now1;
   }
   if(end_day == null || "".equals(end_day)) {
   		end_day = now;
   }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看详细信息</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<style type="text/css">
.checkBox_mid{ vertical-align:middle; margin-right:10px;};
#v_type{height:50px;}
.checkBox_text{ vertical-align:text-top}
.span_left{ margin-left:14px;}
</style>
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../../js/indexjs/indexList.js"></script>
<script type="text/javascript" src="/sys/js/open-flash-chart/js/json2.js"></script>
<script type="text/javascript" src="/sys/js/open-flash-chart/js/swfobject.js"></script>
<script type="text/javascript" src="js/cmsAssessUserInfo.js"></script>
<script type="text/javascript">
var cat_type = "0";
var site_id = "<%=site_id%>";
var app_id = "<%=app_id%>";//应用ID
var cat_id = "<%=cat_id%>";
var class_id = 0;
var jsonData;

var input_user = "<%=input_user%>";
var selected_ids = "<%=selectedId%>"; //查询语句用的的字段,会去除站点根节点.
var is_host = "<%=is_host%>";
var start_day = "<%=start_day%>";
var end_day = "<%=end_day%>";

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	//alert(getCurrentFrameObj().$("#start_day").val());
	//得到父页面中设置的参数
	showList();
});
</script>
</head>
<body>
<span class="blank3"></span>
<table width="100%">
    <tr valign="top" >
    	<td>
    	<div id="chart">
       	
        </div>
        </td>
    </tr>
    <tr valign="top">
     	<td>
       <div id="count">
       		<table id="countList" class="treeTableCSS table_border" border="0" cellpadding="0" cellspacing="0">
       		</table>
       </div>
       </td>
     </tr>
</table>
</body>
</html>