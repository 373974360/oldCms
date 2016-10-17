<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建目录</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript">
function returnValueHandl()
{
	var fn = $("#folder_name").val();
	if(fn != "" && fn != null)
	{
		top.top.getCurrentFrameObj().addTemplateResourcesFolder(fn);
		top.CloseModalWindow();
	}else
		top.msgWargin("请输入目录名称");
}

</script>
</head>

<body>
<span class="blank3"></span>
<form id="form1" name="form1" action="#" method="post">
<div>
<table id="Template_table" class="" border="0" cellpadding="0" cellspacing="0" width="98%">
	<tbody>
		<tr>			
			<th><span class="f_red">*</span>目录名称：</th>
			<td>
				<input id="folder_name" name="folder_name" type="text" class="width200" value="" maxlength="30"/>
			</td>
		</tr>
	</tbody>
</table>
</div>

<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="returnValueHandl()" value="保存" />	
			<input id="cancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />		
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
