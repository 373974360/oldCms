<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改审核流程回复内容</title>



<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript"  src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript">
var top_index = "${param.top_index}";
var pro_id = "${param.pro_id}";
init_editer_min("pro_note");
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
	var content = top.getCurrentFrameObj(top_index).getProNoteValue(pro_id);
	$("#pro_note").val(content);
	KE.html("pro_note",content);
});



function saveproNote()
{
	var SQRPC = jsonrpc.SQRPC;
	var m = new Map();
	var content = KE.html("pro_note");
	m.put("pro_note",content);
	m.put("pro_id",pro_id);
	if(SQRPC.updateProcessNote(m))
	{
		top.msgAlert("内容"+WCMLang.Add_success);
		top.getCurrentFrameObj(top_index).setProNoteValue(pro_id,content);
		top.CloseModalWindow();
	}else
		top.msgWargin("内容"+WCMLang.Add_fail);
}
</script>
</head>
<body>
<span class="blank12"></span>
<div>
	<table id="sq" width="98%">
		<tr>
			<td><textarea id="pro_note" name="pro_note" style="width:620px;height:200px"></textarea></td>
		</tr>
		<tr>
			<td><span class="blank12"></span></td>
		</tr>
		<tr>
			<td align="center">
				<input id="submitButton" name="btn1" type="button" onclick="javascript:saveproNote();" value="提交" />
				<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="关闭" />
			</td>
		</tr>
	</table>	
</div>
<span class="blank12"></span>
</body>
</html>