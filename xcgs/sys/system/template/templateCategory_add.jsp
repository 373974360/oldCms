<%@ page contentType="text/html; charset=utf-8"%>
<%
	String pid = request.getParameter("pid");
	String tc_id = request.getParameter("tcat_id");
	String app_id = request.getParameter("app");
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模板目录维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/templateCategory.js"></script>
<script type="text/javascript">

var pid = "<%=pid%>";
var tc_id = "<%=tc_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(tc_id != "" && tc_id != "null" && tc_id != null)
	{		
		defaultBean = TemplateRPC.getTemplateCategoryById(tc_id,'<%=site_id%>','<%=app_id%>');
		if(defaultBean)
		{
			$("#templateCategory_table").autoFill(defaultBean);					
		}
		$("#addButton").click(updateTemplateCategoryData);
	}
	else
	{
		//$("#app_id").val("<%=app_id%>");
		//$("#site_id").val("<%=site_id%>");
		$("#addButton").click(addTemplateCategoryData);
	}
});


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="templateCategory_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>目录中文名：</th>
			<td >
				<input id="tcat_cname" name="tcat_cname" type="text" class="width200" value="" onblur="checkInputValue('tcat_cname',false,60,'模板目录中文名','')"/>
			</td>
		</tr>
		<tr class="hidden">
			<th><span class="f_red">*</span>目录英文名：</th>
			<td >
				<input id="tcat_ename" name="tcat_ename" type="text" class="width200" value="" />
			</td>
		</tr>	
		<tr class="hidden">
			<th><span class="f_red">*</span>目录路径：</th>
			<td >
				<input id="tcat_position" name="tcat_position" type="text" class="width200" value="/" />
			</td>
		</tr>
		<tr>
			<th>备注：</th>
			<td colspan="3">
				<textarea id="tcat_memo" name="tcat_memo" style="width:585px;;height:100px;" onblur="checkInputValue('tcat_memo',true,1000,'备注','')"></textarea>		
				<input id="tcat_id" name="t_id" type="hidden" class="width200" value="0"/>
				<input id="tclass_id" name="t_ver" type="hidden" class="width200" value="0"/>
				<input id="parent_id" name="parent_id" type="hidden" class="width200" value="<%=pid%>"/>
				<input id="app_id" name="app_id" type="hidden" class="width200" value="<%=app_id%>"/>
				<input id="site_id" name="site_id" type="hidden" class="width200" value="<%=site_id%>"/>
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('templateCategory_table',tcat_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="closeTemplateCategoryPage();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
