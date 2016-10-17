<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.DateUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
   String now = DateUtil.getCurrentDate();
   now = now.substring(0,7);
   pageContext.setAttribute("now",now);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>按诉求目的-办理情况</title> 


<jsp:include page="../../../include/include_tools.jsp"/>

<script type="text/javascript" src="../../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/banliqingkuang.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	initButtomStyle();
	init_input();
	//initData();
});

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="banliqingkuangR.jsp" method="get">
<table id="sgroup_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr> 
			<th><span class="f_red">*</span>时间范围：</th>
			<td colspan="3">
				<input class="Wdate" type="text" name="s" id="s" size="10" value="${now}" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})" readonly="true">
		         --
			    <input class="Wdate" type="text" name="e" id="e" size="10"  value="${now}" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})" readonly="true">
			</td>
		</tr> 
		<tr>
			<th><span class="f_red">*</span>业务类型：</th>
			<td colspan="3">
				<input type="text" readonly="true" id="b_name" name="b_name" style="width:320px" value="----请选择统计业务----" onclick="setBIdsHtml()">
				<input type="hidden" id="b_ids" name="b_ids" style="width:300px" value="">
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
			<input id="addButton" name="btn1" type="button" onclick="funOK()" value="统计" />	
			<input id="userAddReset" name="btn1" type="button" onclick="reset()" value="重置" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
