<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看统计详细信息</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<style type="text/css">
.checkBox_mid{ vertical-align:middle; margin-right:10px;};
#v_type{height:50px;}
.checkBox_text{ vertical-align:text-top}
.span_left{ margin-left:14px;}
</style>
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/CateAccessInfoList.js"></script>
<script type="text/javascript">
var site_id = "<%=request.getParameter("site_id")%>"; 
var cat_id = "<%=request.getParameter("cat_id")%>";
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	//得到父页面中设置的参数
	showList();
});
</script>
</head>
<body>
<span class="blank3"></span>
<table  width="100%">
    <tr valign="top">
     	<td>
		<table id="treeTableCount" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0">
		</table>
       </td>
     </tr>
</table>
<span class="blank3"></span>
<span class="blank3"></span>
</body>
</html>