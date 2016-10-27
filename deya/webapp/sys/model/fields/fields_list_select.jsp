<%@ page contentType="text/html; charset=utf-8"%>
<%
	String appId = request.getParameter("appId");
	String siteId = request.getParameter("site_id");
	String ids = request.getParameter("ids");
	String model_id = request.getParameter("model_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>元数据集列表</title>


<jsp:include page="../../include/include_tools.jsp" />
  
<script type="text/javascript" src="js/fields_list_select.js"></script>
<script type="text/javascript">

	var appId = "<%=appId%>";
	var siteId = "<%=siteId%>";
    var ids = "<%=ids%>";
    var model_id = "<%=model_id%>";
	//con_m.put("sort_name","me_id");
	//con_m.put("sort_type","desc");

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	reloadList();
	
});

</script>
</head>

<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr> 
			<td align="right" valign="middle">
			    <input id="btn301" name="btn1" type="button" onclick="saveCateIDS()" value="确定" />
				<input id="btn303" name="btn3" type="button" onclick="javascript:top.CloseModalWindow();" value="关闭" />
			</td>
		</tr>
</table>
<span class="blank3"></span>

<div id="table"></div>
<div id="turn"></div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" style="display:none">
		<tr> 
			<td align="left" valign="middle">
			    <input id="btn301_2" name="btn1_2" type="button" onclick="openTabPage()" value="新建" />
				<input id="btn303_2" name="btn3_2" type="button" onclick="deleteRecord(table,'id','deleteInfo()');" value="删除" />
			</td>
		</tr>
</table>
</div>
</body>
</html>
