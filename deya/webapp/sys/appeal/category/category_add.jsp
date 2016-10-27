<%@ page contentType="text/html; charset=utf-8"%>
<%
	String parent_id = request.getParameter("parentID");
	String cat_id = request.getParameter("cat_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>诉求内容分类</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/categoryList.js"></script>
<script type="text/javascript">
var parent_id = "<%=parent_id%>";
var cat_id = "<%=cat_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	
	if(cat_id != "" && cat_id != "null" && cat_id != null)
	{		
		defaultBean = AppealCategoryRPC.getCategoryBean(cat_id);
		if(defaultBean)
		{
			$("#appCategory_table").autoFill(defaultBean);					
		}
		$("#addButton").click(updateCategory);
	}
	else
	{
		$("#addButton").click(addCategory);
	}
});

</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="appCategory_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>分类名称：</th>
			<td class="width250">
				<input id="cat_cname" name="cat_cname" type="text" class="width300" value="" onblur="checkInputValue('cat_cname',false,80,'分类名称','')"/>
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('appCategory_table',cat_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>