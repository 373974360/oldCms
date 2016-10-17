<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>诉求目的信息维护</title>

<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="js/purposeList.js"></script>
<script type="text/javascript">
var pur_id = "<%=request.getParameter("pur_id")%>";
var type = "<%=request.getParameter("type")%>";
var defaultBean;
$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	
	if(pur_id != "" && pur_id != "null" && pur_id != null)
	{
	    defaultBean = PurposeRPC.getPurposeByID(pur_id);
	    if(defaultBean)
		{
			$("#app_purpose_table").autoFill(defaultBean);
		}
		$("#saveBtn").click(updatePurpose);
	}
	else
	{
		$("#saveBtn").click(addPurpose);
	}
});
</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="app_purpose_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>诉求目的：</th>
			<td >
				<input id="pur_name" name="pur_name" type="text" class="width300" value="" onblur="checkInputValue('pur_name',false,60,'诉求目的','')"/>
			</td>
		</tr>
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="saveBtn" name="saveBtn" type="button" onclick="" value="保存" />
			<input id="resetBtn" name="resetBtn" type="button" onclick="formReSet('app_purpose_table',pur_name)" value="重置" />
			<input id="viewCancel" name="btn1" type="button" onclick="top.CloseModalWindow()" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>