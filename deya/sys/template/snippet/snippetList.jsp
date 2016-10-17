<%@ page contentType="text/html; charset=utf-8"%>
<%
	String app = request.getParameter("app");
    String  siteid = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>节假日管理</title>


<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript"  src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/snippetList.js"></script>
<script type="text/javascript">
	var site_id='<%=siteid%>';
	var app='<%=app%>';
	var id;
	var appList;
	$(document).ready(function(){	
		initButtomStyle();
		init_FromTabsStyle();
		if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	     
	    //得到应用信息
	    //getAppList();
	      
		initTable(); 
		  
		reloadSnippetList();
	});

</script>
</head>
<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >	
	<tr>
			<td align="left" valign="middle" class="search_td fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="addSnippetRecord()" value="新建代码片段" /> 
				<input id="btn2" name="btn2" type="button" onclick="updateRecord(table,'id', 'updateRecord1()')" value="修改" />
				<span class="blank3"></span>
			</td>
		</tr>
</table>
<span class="blank3"></span>
<div id="table"></div>
<div id="turn"></div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >	
	<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="addSnippetRecord()" value="新建代码片段" /> 
				<input id="btn2" name="btn2" type="button" onclick="updateRecord(table,'id', 'updateRecord1()')" value="修改" />
				<span class="blank3"></span>
			</td>
		</tr>
</table>
</div>
</body>
</html>
