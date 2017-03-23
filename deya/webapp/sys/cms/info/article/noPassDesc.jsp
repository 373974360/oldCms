<%@ page contentType="text/html; charset=utf-8"%>
<%
	String app_id = request.getParameter("app_id");
	String siteid = request.getParameter("site_id");	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>信息推送</title>
		<jsp:include page="../include/include_info_tools.jsp"/>

		<script type="text/javascript">
var site_id = "<%=siteid%>";
var app = "<%=app_id%>";

$(document).ready(function() {
	initButtomStyle();
	init_FromTabsStyle();
	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");	
});
	


function related_cancel(){
	CloseModalWindow();
}

function related_ok(){
	getCurrentFrameObj().noPass($("#auto_desc").val());
	related_cancel();
}

</script>
</head>
	<body>
		<span class="blank3"></span>
		<form id="form1" name="form1" action="#" method="post">		
		<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0">
			<tbody>
				<tr id="auto_desc_tr">
					<th style="vertical-align:top;">退稿意见：</th>
					<td>
						<textarea id="auto_desc" name="auto_desc" style="width:380px;;height:130px;"></textarea>
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
					<input type="button" value="确定" class="btn1" onclick="related_ok()" />
					<input type="button" value="取消" class="btn1" onclick="related_cancel()" />
				</td>
			</tr>
		</table>
		<span class="blank3"></span>
		</form>
	</body>
</html>