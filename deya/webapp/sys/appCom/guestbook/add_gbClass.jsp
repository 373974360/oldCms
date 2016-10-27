<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>留言分类</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/gbClassList.js"></script>
<script type="text/javascript">
var class_id = "${param.class_id}";
var cat_id = "${param.cat_id}";
var site_id = "${param.site_id}";
var topnum = "${param.topnum}";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
	if(class_id != "" && class_id != null)
	{
		defaultBean = GuestBookRPC.getGuestBookClassBean(class_id);
		if(defaultBean)
		{
			$("#guestbook_table").autoFill(defaultBean);
		}
		
		$("#addButton").click(updateGuestbookClass);
	}else
		$("#addButton").click(insertGuestbookClass);
});


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="guestbook_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>类别名称：</th>
			<td class="width200">
				<input id="name" name="name" type="text" class="width200" value="" onblur="checkInputValue('name',false,80,'类别名称','')"/>
			</td>			
		</tr>		
		<tr>
			<th style="vertical-align:top;">描述：</th>
			<td  colspan="3">
				<textarea id="description" name="description" style="width:385px;;height:100px;" onblur="checkInputValue('description',true,900,'描述','')"></textarea>		
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('guestbook_table',class_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
