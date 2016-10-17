<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.net.URLDecoder"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String collUrl = request.getParameter("collURL");
//	   collUrl = new String(collUrl.getBytes("ISO-8859-1"),"utf-8");
	   collUrl = URLDecoder.decode(collUrl,"utf-8");
	   if(collUrl.contains("&lt;") && collUrl.contains("&gt;"))
	   {
		   collUrl = collUrl.replaceAll("&lt;","<").replaceAll("&gt;",">");
	   }if(collUrl.contains("amp;")){
		   collUrl = collUrl.replaceAll("amp;","");
	   }
String listStart = request.getParameter("ListStart");
	   listStart = new String(listStart.getBytes("ISO-8859-1"),"utf-8");
String listEnd = request.getParameter("ListEnd");
	   listEnd = new String(listEnd.getBytes("ISO-8859-1"),"utf-8");
	 
String encoding = request.getParameter("encode");
%>
<html>
<head>
<title>测试采集地址</title>
<link rel="stylesheet" type="text/css" href="../styles/themes/default/tree.css">
<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript" src="../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../js/jquery-easyui/jquery.easyui.min.js"></script>

<script type="text/javascript">
var collUrl = "<%=collUrl%>";
var lists = '<%=listStart%>';
var liste = '<%=listEnd%>';
var encode = "<%=encoding%>";

var CollectionDataRPC = jsonrpc.CollectionDataRPC;
var json_data;
$(document).ready(function(){
	initLoading();
	iniCollUrlTree();
});

function iniCollUrlTree()
{
	json_data = eval(CollectionDataRPC.getCollUrlJsonStr(collUrl,lists,liste,encode));
	setLeftMenuTreeJsonData(json_data);
}

function coloseModelWindow()
{
	top.CloseModalWindow();
}

function initLoading(){
	$("#leftMenuTree").html("<img src='../images/loading3.gif'/>");
}
</script>
</head>

<body>
<div id="leftMenuBox" align="center">
   <div id="leftMenu" class="contentBox6 textLeft border_color" style="width:700px;height:440px;overflow:auto;background:#ffffff">
	<ul id="leftMenuTree" class="easyui-tree" animate="true" >
		
	</ul>
   </div>
</div>

  <div>
	<span class="blank12"></span>
	<div class="line2h"></div>
	<span class="blank3"></span>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center" valign="middle">
				<input id="isOkBtn" name="isOkBtn" type="button" onclick="coloseModelWindow()" value="确定" />
			</td>
		</tr>
	</table>
 </div>
</body>
</html>
