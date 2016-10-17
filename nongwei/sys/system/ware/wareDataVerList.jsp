<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String wareid = request.getParameter("wareid");
String siteid = request.getParameter("siteid");
if(siteid == null)
{
	siteid = "0";
}
String appid = request.getParameter("appid");
if(appid == null)
{
	appid="0";	
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>信息标签管理</title>
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/wareDateVerList.js"></script>
<script type="text/javascript">
var ware_id = '<%=wareid %>';
var site_id = '<%=siteid %>';
var app_id = '<%=appid %>';

$(document).ready(function(){
	//alert("ware_id===="+ware_id+"   site_id="+site_id+"  app_id="+app_id);
	initButtomStyle();
	init_FromTabsStyle();

	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) 
		$("html").css("overflowY","scroll");
	initTable();
	raloadWareVerList();
	
});

</script>
</head>

<body>
  <div>
	<span class="blank3"></span>
	<div id="ware_table"></div>
	<div id="ware_turn"></div>
	<span class="blank12"></span>
	<div class="line2h"></div>
	<span class="blank3"></span>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="center" valign="middle">
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />			
		</td>
	</tr>
   </table>	
</div>
</body>
</html>
