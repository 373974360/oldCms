<%@ page contentType="text/html; charset=utf-8"%>
<%
String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>维护报名分类</title>

<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/zjzfType.js"></script>
<SCRIPT LANGUAGE="JavaScript"><!--
var id = request.getParameter("id");
if(id == "" || id == null)
{
	window.close();
}

var defaultBean = ZjzfTypeBean;
$(document).ready(function () {				
	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
				
	if(id != null && id.trim() != "")
	{
		defaultBean = ZjzfTypeRPC.getZjzfTypeBean(id);

		if(defaultBean)
		{
			$("#zjzfType").autoFill(defaultBean);
		}
		
		$("#addButton").click(saveZjzfBean);
	}
	else
	{
		$("#addButton").click(saveZjzfBean);
	}
}); 		
</SCRIPT>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<div id="zjzfType">
 <!-- 隐含字段区域　结束 -->
<table id="jcxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>分类名称：</th>
			<td>
				<input id="name" name="name" type="text" class="width200" value="" onblur="checkInputValue('c_name',false,80,'分类名称','')"/>
				<input type="hidden" id="id" name="id" value="0"/>
			</td>			
		</tr>		
		<tr>
			<th style="vertical-align:top;">开始时间：</th>
			<td colspan="3">
				<input id="start_time" name="start_time" class="Wdate width150 input_text input_text_focus" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})"/>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">结束时间：</th>
			<td colspan="3">
				<input id="end_time" name="end_time" class="Wdate width150 input_text input_text_focus" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})"/>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">备注：</th>
			<td colspan="3">
				<textarea id="remark" name="remark" style="width:300px;height:50px;"></textarea>
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
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('saveZjzfBean',id);reloadTemplate();" value="重置" />
			<input id="userAddCancel" name="btn1" type="button" onclick="window.location.href='zjzfType.jsp'" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body>
</html>
