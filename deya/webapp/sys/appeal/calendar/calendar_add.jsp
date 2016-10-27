<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>节假日信息维护</title>

<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="js/calendarList.js"></script>
<script type="text/javascript"  src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
<script type="text/javascript">
var ca_id = "<%=request.getParameter("ca_id")%>";
var type = "<%=request.getParameter("type")%>";
var defaultBean;
$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	
	if(ca_id != "" && ca_id != "null" && ca_id != null)
	{
	    defaultBean = CalendarRPC.getCalendarBean(ca_id);
	    if(defaultBean)
		{
			$("#app_calendar_table").autoFill(defaultBean);
		}
		$("#saveBtn").click(updateCalendar);
	}
	else
	{ 
		$("#saveBtn").click(addCalendar);
	}
});
</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="app_calendar_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th ><span class="f_red">*</span>节假日名称：</th>
			<td class="width250">
				<input id="ca_name" class="width300" name="ca_name" type="text" style="cursor: pointer;" value="" onblur="checkInputValue('ca_name',false,60,'节假日名称','')"/>
			</td>
		</tr>
		<tr>
			<th class="content_td">节假日性质：</th>
			<td class="content_td">
				<select name="ca_type" id="ca_type" style="width:105px">
					<option value="1" selected="selected">元旦</option>
					<option value="2">春节</option>
					<option value="3">清明节</option>
					<option value="4">劳动节</option>
					<option value="5">端午节</option>
					<option value="6">中秋节</option>
					<option value="7">国庆节</option>
					<option value="0">其他</option>
				</select>
			</td>
		</tr>
		<tr>
			<th class="content_td">节假日标识：</th>
			<td class="content_td">
				
					<input type="radio" id="ca_flag" name="ca_flag" value="0" checked="checked"/>
					<label for="ca_flag">休假 </label>
					<span style="padding: 0.5em"></span>
					<input type="radio" id="ca_flag1" name="ca_flag" value="1"/>
					<label for="ca_flag1">补假</label>
				
				<!-- 
				 select name="ca_flag" id="ca_flag" style="width:105px">
					<option value="0" selected="selected">休假</option>
					<option value="1">补假</option>
				</select>
				 -->
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>开始时间：</th>
			<td >
				<input id="start_dtime" name="start_dtime" type="text" style="height:16px" class="Wdate width100" size="11" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"  onblur="checkInputValue('start_dtime',false,11,'开始时间','')"/>
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>结束时间：</th>
			<td>
			    <input id="end_dtime" name="end_dtime" type="text" style="height:16px" class="Wdate width100" size="11" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})"  onblur="checkInputValue('end_dtime',false,11,'结束时间','')"/>
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
			<input id="saveBtn" name="saveBtn" type="button" onclick="" value="保存" />
			<input id="resetBtn" name="resetBtn" type="button" onclick="formReSet('app_calendar_table',ca_id);" value="重置" />
			<input id="viewCancel" name="btn1" type="button" onclick="top.CloseModalWindow()" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>