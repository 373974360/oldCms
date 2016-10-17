<%@ page contentType="text/html; charset=utf-8"%>
<%
	String tid = request.getParameter("tid");	
	String site_id = request.getParameter("site_id");
	String path = request.getParameter("path");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模板维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/templateCategory.js"></script>
<script type="text/javascript">

var tid = "<%=tid%>";
var site_id = "<%=site_id%>";
var path = "<%=path%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	formResouSet();
});

function formResouSet()
{
	if(path != "" && path != "null" && path != null)
	{	
		if(path.indexOf("\\") > -1)
			$("#file_name").val(path.substring(path.lastIndexOf("\\")+1));	
		else
			$("#file_name").val(path.substring(path.lastIndexOf("/")+1));
		$("#f_content").val(TemplateRPC.getResourcesFileContent(path,site_id));
		$("#addButton").click(updateResourcesFile);
	}	
}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="Template_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>文件名：</th>
			<td >
				<input id="file_name" name="file_name" type="text" class="width200" value=""  readOnly="readOnly"/>
			</td>
		</tr>		
		<tr>
			<th>文件内容：</th>
			<td colspan="3">
				<textarea id="f_content" name="f_content" style="width:585px;;height:300px;"></textarea>
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
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formResouSet()" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
