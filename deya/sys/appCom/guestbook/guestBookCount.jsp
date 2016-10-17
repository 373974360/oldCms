<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>留言统计页面</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/guestBookCount.js"></script>
<script type="text/javascript">
	
var gbs_id = "${param.gbs_id}";
var site_id = "${param.site_id}";

$(document).ready(function(){
	initButtomStyle();	
	init_input();
});


</script>
</head>
<body>

<div>
<table class="table_option fromTabs" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" >
			<span class="f_red">*</span>时间范围：
				<input class="Wdate" type="text" name="s" id="s" size="11" style="height:16px;line-height:16px;" value="${now1}" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">
		         --
			    <input class="Wdate" type="text" name="e" id="e" size="11" style="height:16px;line-height:16px;
"  value="${now}" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">
			<span class="blank3"></span>
		</td>
			<td align="right" valign="middle">
				 <input id="addButton" name="btn1" type="button" onclick="funOK()" value="统计" />	
				 <input id="userAddReset" name="btn1" type="button" onclick="reset()" value="重置" />
				 <span class="blank3"></span>
			</td>
		</tr>		
</table>
</div>
<span class="blank3"></span>
<div id="table"></div><!-- 列表DIV -->
</body>
</html>
