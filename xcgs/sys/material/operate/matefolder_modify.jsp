<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>素材库自定义目录维护</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css"/>
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../js/mateList.js"></script>
<script type="text/javascript">
var site_id ="<%=request.getParameter("site_id")%>";
var f_id = "<%=request.getParameter("f_id")%>";
var user_id = top.LoginUserBean.user_id;
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(f_id =="" || f_id == "0")
	{	
	    return;
	}else{
		defaultBean = MateFolderRPC.getMateFolderBean(f_id);
	}
	if(defaultBean)
	{
		$("#MateFolder_table").autoFill(defaultBean);					
	}
	$("#addButton").click(updateMateFolder);
});
</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="MateFolder_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>目录名称：</th>
			<td class="width250">
				<input id="cname" name="cname" type="text" class="width250" value="" onblur="checkInputValue('cname',false,80,'目录名称','')"/>
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('MateFolder_table',f_id)" value="重置"/>	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
<span class="blank3"></span>
</form>
</body>
</html>