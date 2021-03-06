<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@page import="com.deya.util.DateUtil"%>
<%
String siteid = request.getParameter("site_id");
String app_id = request.getParameter("app_id");
if(app_id == null || app_id.trim().equals("")){
	app_id = "zwgk";
}

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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//CN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>依申请申请单处理方式统计</title>



<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
	<script type="text/javascript" src="/sys/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/ysqTypeCnt.js"></script>

<script type="text/javascript" src="js/swfobject.js"></script>
<script type="text/javascript" src="js/finger.js"></script>
<script type="text/javascript">

var site_id = "<%=siteid%>";
var app_id = "<%=app_id%>";
var str_sel_node = "";
var is_all_node = false;

<!-- 默认的时间段是每个月开始到当天 -->
var start_day = "<%=start_day%>";
var end_day = "<%=end_day%>";
var beanList = null;

var node_ids = ""; // 选中的节点
var is_all_node = false; // 是否全选节点
var all_node_ids = ""; // 全选时的node_ids值

$(document).ready(function(){

	initButtomStyle();
	init_input();
	
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
	
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
					value="<%=start_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true"/>
		         --
			    <input class="Wdate" type="text" name="end_day" id="end_day" size="11" style="height:16px;line-height:16px;" 
					 value="<%=end_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true"/>
			<span class="blank3"></span>
        </td>
        <td></td>
     </tr>
     <tr>
        <td align="left" valign="middle">
        	<span class="f_red">*</span>公开站点：
           
        </td>
        <td>
        	<input id="setAll" type="checkbox" onclick="setAllNode()" value="全选">
            <label align="left" valign="middle">全部节点</label>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="text" id="cat_tree" value="" style="width:190px; height:18px; overflow:hidden;" readonly="readonly"/>
            <input id="openPage" name="openPage" type="button" onclick="openPage()" value="选择节点" />		
         </td>
        <td align="right" valign="middle">
         		<div>
				 <input id="addButton" name="btn1" type="button" onclick="search()" value="统计" />	
				 <input type="button" id="outFileBtn" style="display:none" onclick="outFileBtn()" value="导出"/>
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
<div>
<iframe src="" id="chart" style="display:none" name="chart" width="100%"	height="300" frameborder="0"></iframe>
</div>
</div>
</body>
</html>