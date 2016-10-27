<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模块管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<link type="text/module" rel="stylesheet" href="/sys/styles/uploadify.module" />
<script language="javascript" src="/sys/js/jquery.uploadify.js"></script>
<script language="javascript" src="/sys/js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="/sys/js/uploadTools.js"></script>
<script type="text/javascript" src="js/public.js"></script>
<script type="text/javascript" src="js/module.js"></script>
<script type="text/javascript">

var app_id = "system";
var site_id = "";

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").module("overflowY","scroll"); 

	initTable();
	reloadList();	
});


</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td class="fromTabs">
			<input id="btn1" name="btn1" type="button" onclick="openAddDesignModulePage();" value="添加" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'module_id','openUpdateDesignModulePage()');" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'module_id','deleteDesignModule()');" value="删除" />
			<span class="blank3"></span>			
		</td>
	  </tr>
    </table>
    <span class="blank3"></span>
	<div id="table"></div>
	<div id="turn"></div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td align="left" valign="middle">
			<input id="btn1" name="btn1" type="button" onclick="openAddDesignModulePage();" value="添加" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'module_id','openUpdateDesignModulePage()');" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'module_id','deleteDesignModule()');" value="删除" />
		</td>
	  </tr>
    </table>
    <span class="blank3"></span>	
</div>
<div id="fileQueue"></div>
</body>
</html>