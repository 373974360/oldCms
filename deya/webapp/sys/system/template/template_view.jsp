<%@ page contentType="text/html; charset=utf-8"%>
<%
String tc_id = request.getParameter("tc_id");
if(tc_id == null){
	tc_id = "0";
}
	String t_id = request.getParameter("t_id");
	String app_id = request.getParameter("app");
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模板查看</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/template.js"></script>
<script type="text/javascript">

var t_id = "<%=t_id%>";
var tcid = "<%=tc_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	if(t_id != "" && t_id != "null" && t_id != null)
	{		
		defaultBean = TemplateRPC.getTemplateEditById(t_id,'<%=site_id%>','<%=app_id%>');
		if(defaultBean)
		{
			$("#Template_table").autoFill(defaultBean);					
		}
		disabledWidget();
	}
	
});

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="Template_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>模板英文名：</th>
			<td >
				<input id="t_ename" name="t_ename" type="text" class="width200" value=""  onblur="checkInputValue('t_ename',false,10,'模板英文名','')"/>
			</td>
		</tr>	
		<tr>
			<th><span class="f_red">*</span>模板中文名：</th>
			<td >
				<input id="t_cname" name="t_cname" type="text" class="width200" value="" onblur="checkInputValue('t_cname',false,60,'模板中文名','')"/>
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>模板保存路径：</th>
			<td >
				<input id="t_path" name="t_path" type="text" class="width200" value="" onblur="checkInputValue('t_path',false,60,'保存路径','')"/>
			</td>
		</tr>
		<tr>
			<th>模板内容：</th>
			<td colspan="3">
				<textarea id="t_content" name="t_content" style="width:585px;;height:100px;" onblur="checkInputValue('t_content',true,1000,'模板描述','')"></textarea>		
				<input id="tcat_id" name="tcat_id" type="hidden" class="width200" value="<%=tc_id %>"/>
				<input id="t_id" name="t_id" type="hidden" class="width200" value="0"/>
				<input id="t_ver" name="t_ver" type="hidden" class="width200" value="0"/>
				<input id="creat_user" name="creat_user" type="hidden" class="width200" value="0"/>
				<input id="creat_dtime" name="creat_dtime" type="hidden" class="width200" value="0"/>
				<input id="modify_user" name="modify_user" type="hidden" class="width200" value="0"/>
				<input id="modify_dtime" name="modify_dtime" type="hidden" class="width200" value="0"/>
				<input id="app_id" name="app_id" type="hidden" class="width200" value="<%=app_id%>"/>
				<input id="site_id" name="site_id" type="hidden" class="width200" value="<%=site_id%>"/>
			</td>
		</tr>
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
</form>
</body>
</html>