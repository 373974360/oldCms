<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%
	String site_id = request.getParameter("site_id");
	if(site_id == null || "".equals(site_id) || "null".equals(site_id))
		site_id = "";
	String start_day = request.getParameter("start_day");
	String end_day = request.getParameter("end_day");

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
<title>按点击量统计信息</title>

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
<script type="text/javascript" src="js/cmsCountListHits.js"></script>
<script type="text/javascript" src="/sys/js/open-flash-chart/js/json2.js"></script>
<script type="text/javascript" src="/sys/js/open-flash-chart/js/swfobject.js"></script>
<script type="text/javascript">
var site_id = "<%=site_id%>";
var tjfs = 0;

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
});

</script>
</head>
<body>
<table style="width:100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>
        <td width="75px">
            <span class="f_red">*</span>统计方式:
        </td>
        <td width="270px">
            <div>
                <input id="infoHits" type="checkbox" class="checkBox_mid" onclick="allSelected(1)" style="margin-right:5px;"/>按信息点击量&nbsp;&nbsp;
                <input id="catHits" type="checkbox" class="checkBox_mid" onclick="allSelected(2)" style="margin-right:5px;"/>按栏目点击量&nbsp;&nbsp;
            </div>
        </td>
    	<td width="75px">
        	<span class="f_red">*</span>时间范围:
        </td>
    	<td width="250px">
        	<div style="height:auto; border:#A00 2px;">
            <div id="defauleTime">
            <input class="Wdate" type="text" name="start_day" id="start_day" size="11" style="height:16px;line-height:16px;" 
					value="<%=start_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true" />
		         --
			    <input class="Wdate" type="text" name="end_day" id="end_day" size="11" style="height:16px;line-height:16px;" 
					 value="<%=end_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true"/>
            </div>
            </div>
        </td>
        <td>
            <div>
                <input type="button" id="searchBtn"  onclick="showList()" value="统计"/>
                <input class="hidden" type="button" id="outFileBtn"  onclick="outFileBtn()" value="导出"/>
            </div>
        </td>
    </tr>
</table>
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
       		<table id="countList" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0" width="100%">
       		</table>
       </div>
       </td>
     </tr>
</table>

</body>
</html>
