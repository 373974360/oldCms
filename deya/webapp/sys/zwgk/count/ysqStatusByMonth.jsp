<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="com.deya.util.Encode"%>
<%
String siteid = request.getParameter("site_id");
String app_id = request.getParameter("app_id");
if(app_id == null || app_id.trim().equals("")){
	app_id = "zwgk";
}
String node_name_site = request.getParameter("node_name");
if(node_name_site == null || node_name_site.trim().equals("")){
	node_name_site = "";
}
node_name_site = Encode.iso_8859_1ToUtf8(node_name_site);

	String start_day = request.getParameter("start_day");
	String end_day = request.getParameter("end_day");
	String type = request.getParameter("type");

   String now = DateUtil.getCurrentDate();
   String now1 = now.substring(0,7)+"-01";
   if(start_day == null || "".equals(start_day)) {
   		start_day = now1;
   }
   
   if(end_day == null || "".equals(end_day)) {
   		end_day = now;
   }
   
   //2010-03-05 12:34:45
   if(end_day.length()>10){
	   end_day = end_day.substring(0,10);
   }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//CN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>申请单状态统计</title>



<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
	<script type="text/javascript" src="/sys/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/ysqStatusByMonth.js"></script>

<script type="text/javascript" src="js/swfobject.js"></script>
<script type="text/javascript" src="js/finger.js"></script>
<script type="text/javascript">

var site_id = "<%=siteid%>";
var app_id = "<%=app_id%>";
var type = "<%=type%>";
var node_name_site = "<%=node_name_site%>";

var str_sel_node = "";
var is_all_node = false;

<!-- 默认的时间段是每个月开始到当天 -->
var start_day = "<%=start_day%>";
var end_day = "<%=end_day%>";
var beanList = null;

var node_ids = "<%=siteid%>"; // 选中的节点

$(document).ready(function(){

	initButtomStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
	
	var node_name = jsonrpc.GKNodeRPC.getNodeNameByNodeID(site_id);
	$("#node_name").val(node_name);
	
	if(type == "back")
	{
		//alert(node_name_site);
		$("#node_name").val(node_name_site);
		search();
		$("#backBtn").show();
		//$("#backBtn1").show();
		$("#backBtn1").hide();
		$("#addButton").hide();
	} else {
		$("#backBtn").hide();
		$("#backBtn1").hide();
	}
});

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
        <td></td>
     </tr>
    <tr>
    	<td align="left" valign="middle">
        	<span class="f_red">*</span>公开站点：
        </td>
        <td>
        	<input type="text" id="node_name" value="" style="width:190px; height:18px; overflow:hidden;" readonly="readonly"/>
        </td>
        <td align="right" valign="middle">
         		<div>
				 <input id="addButton" name="btn1" type="button" onclick="search()" value="统计" />	
				 <input type="button" id="outFileBtn" style="display:none" onclick="outFileBtn()" value="导出"/>
                 <input id="backBtn" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="关闭" />
                 </div>
				 <span class="blank3"></span>
		</td>
    </tr>
</table>
<span class="blank3"></span>
</div>
<form style="height:auto; width:auto;">
<table id="countList" class="treeTableCSS table_border" border="0" cellpadding="0" cellspacing="0">
</table>
</form>
<span class="blank9"></span>
 <input id="backBtn1" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="关闭" />
<div>
<iframe src="" id="chart" style="display:none" name="chart" width="100%"	height="300" frameborder="0"></iframe>
</div>
</div>
</body>
</html>