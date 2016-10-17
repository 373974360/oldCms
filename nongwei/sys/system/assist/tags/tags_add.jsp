<%@ page contentType="text/html; charset=utf-8"%>
<%
	String tag_id = request.getParameter("tag_id");
	String app_id = request.getParameter("app");
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Tag维护</title>


<jsp:include page="../../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/tags.js"></script>
<script type="text/javascript">

var tag_id = "<%=tag_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(tag_id != "" && tag_id != "null" && tag_id != null)
	{		
		defaultBean = AssistRPC.getTagsById(tag_id);
		if(defaultBean)
		{
			$("#Tags_table").autoFill(defaultBean);					
		}
		$("#tag_id").val(tag_id);
		$("#addButton").click(updateTagData);
	}
	else
	{
		$("#app_id").val("<%=app_id%>");
		$("#site_id").val("<%=site_id%>");
		$("#addButton").click(addTagData);
	}
});

function checkInt(input_name,id){
	var Val = $("#"+id).val();
	var myRegExp = /^([0-9]+)$/;
	var bVal = (myRegExp.test(Val));
	if (!bVal) {
		jQuery.simpleTips(input_name,"只能输入数字",2000);
		$("#"+id).focus();
		return;
	}
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="Tags_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>Tag名称：</th>
			<td class="width250">
				<input id="tag_name" name="tag_name" type="text" class="width200" value="" onblur="checkInputValue('tag_name',false,80,'Tag名称','')"/>
				<input type="hidden" name="tag_id" id="tag_id" value="0" />
			</td>
		</tr>
		<tr>
			<th>Tag颜色：</th>
			<td >
				<input id="tag_color" name="tag_color" type="text" class="width200" value=""/>
			</td>
		</tr>	
		<tr>
			<th>字号大小：</th>
			<td >
				<input id="font_size" name="font_size" type="text" class="width200" value="12" onblur="checkInt('font_size','font_size')"/>
			</td>
		</tr>	
		<tr>
			<th>热度及关联次数：</th>
			<td >
				<input id="tag_times" name="tag_times" type="text" class="width200" value="0" maxlength="5" onblur="checkInt('tag_times','tag_times')"/>
				<input type="hidden" name="app_id" id="app_id" value="0"/>
				<input type="hidden" name="site_id" id="site_id" value="0"/>
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('Tags_table',tag_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="closePage();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
