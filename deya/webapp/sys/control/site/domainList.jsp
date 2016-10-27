<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>站点注册</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/domainList.js"></script>
<script type="text/javascript">

var jsonData;
var chold_jData;
var site_id = "${param.site_id}"; //站群ID 要初始化进去的数据
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initTable();
	reloadDomainList();
});
</script>
</head>

<body>
<table style="width:100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>	 
	 <td valign="top">
	   <div>
       <table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="fromTabs" align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="openAddSiteDomainPage();" value="添加" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'domain_id','openUpdateSiteDomainPage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectSinglCheckbox(table,'domain_id','setDefault()')" value="设为默认" />
				<input id="btn7" name="btn7" type="button" onclick="deleteSinglRecord(table,'domain_id','deleteSiteDomain()');" value="删除" />
			</td>
		</tr> 
	</table>
    <span class="blank3"></span>		
		<div id="table"></div>
		<div id="turn"></div>
		<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="openAddSiteDomainPage();" value="添加" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'domain_id','openUpdateSiteDomainPage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectSinglCheckbox(table,'domain_id','setDefault()')" value="设为默认" />
				<input id="btn7" name="btn7" type="button" onclick="deleteRecord(table,'domain_id','deleteSiteDomain()');" value="删除" />
			</td>
		</tr> 
	</table>
     </div>
	 </td>
	</tr>
</table>
</body>
</html>
