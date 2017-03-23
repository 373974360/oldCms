<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 

	<title>查看详细答案</title> 	
	<jsp:include page="../include/include_tools.jsp"/>
	<script src="js/statisticsViewDetail.js"></script>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var search_con = getCurrentFrameObj().con;
		var div_height = 0;		
		var s_id = request.getParameter("s_id");
		var item_id = request.getParameter("item_id");
		var item_value = request.getParameter("item_num");
		var subject_type = request.getParameter("s_type");
		var is_text = "true";

		$(document).ready(function () {	
			
			initTable();
			showTurnPage();
			showList();	
		}); 
			
		function reloadList(obj)
		{
			is_text = ""+$(obj).is(':checked');
		
			showTurnPage();
			showList();
		}
	//-->
	</SCRIPT>	
</head> 
<body background="#F5FAFE"> 
	<input type="hidden" id="handleId" name="handleId" value="H32001">
	<table border="0" cellpadding="0" cellspacing="0" width="99%" class="right_body">
	 <tr>
	  <td><input type="checkbox" value="1" id="choiceText" name="choiceText" checked="true" onclick="reloadList(this)">过滤空选项</td>
	 </tr>
	 <tr>
	  <td>	 
		<div id="table"></div>
	  </td>
	 </tr>
	 <tr><td height="6px"></td></tr>
	 <tr>
	  <td>	 
		<div id="turn"></div>
	  </td>
	 </tr>
    </table>
</body> 

</html> 

