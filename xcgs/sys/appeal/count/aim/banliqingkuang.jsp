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
	//得到所有的业务
	setBusiness();
});

</script>
</head>

<body>
<form id="form1" name="form1" action="banliqingkuangR.jsp" method="get">
<table class="table_option fromTabs" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><span class="f_red">*</span>时间范围：
				<input class="Wdate" type="text" name="s" id="s" size="8" value="${now}" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})" readonly="true">
		         --
			    <input class="Wdate" type="text" name="e" id="e" size="8"  value="${now}" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})" readonly="true">
                <span class="f_red">*</span>业务类型：
				<input type="checkbox" name="all" id="all" value="1" onclick="fnAll()" style="vertical-align:middle"><b>全选</b>&nbsp;&nbsp;&nbsp;&nbsp;
                <span id="b_tr"></span>
				<!--
				<input type="text" readonly="true" id="b_name" name="b_name" style="width:320px" value="----请选择统计业务----" onclick="setBIdsHtml()">
				<input type="hidden" id="b_ids" name="b_ids" style="width:300px" value="">
			    -->
			</td>
			<td align="left" valign="middle" style="text-indent:100px;">
				<input id="addButton" name="btn1" type="button" onclick="funOK()" value="统计" />	
				<input id="userAddReset" name="btn1" type="button" onclick="reset()" value="重置" />
				<span class="blank3"></span>
		</td>
	</tr>
</table>
<!--
<table id="sgroup_table" class="table_option fromTabs" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr> 
			<td><span class="f_red">*</span>时间范围：
				<input class="Wdate" type="text" name="s" id="s" size="8" value="${now}" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})" readonly="true">
		         --
			    <input class="Wdate" type="text" name="e" id="e" size="8"  value="${now}" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})" readonly="true">
                <span class="f_red">*</span>业务类型：
				<input type="text" readonly="true" id="b_name" name="b_name" style="width:320px" value="----请选择统计业务----" onclick="setBIdsHtml()">
				<input type="hidden" id="b_ids" name="b_ids" style="width:300px" value="">
			</td>
			<td align="left" valign="middle" style="text-indent:100px;">
				<input id="addButton" name="btn1" type="button" onclick="funOK()" value="统计" />	
				<input id="userAddReset" name="btn1" type="button" onclick="reset()" value="重置" />
			</td>
		</tr> 
	</tbody>
</table>
-->
<table id="iframeTable" style="display:none" class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		   <iframe src="" id="countList" name="countList" width="100%"	height="380" frameborder="0"  scrolling="yes"></iframe>
		</td>
	</tr>
</table>
<span class="blank3"></span>
<span class="blank3"></span>
<div id="line2h" class="line2h" style="display:none"></div>
<span class="blank3"></span>
<table id="buttonTable" class="table_option" border="0" cellpadding="0" cellspacing="0" style="display:none">
	<tr>	
		<td>
		    <!--
			<input id="addButton" name="btn1" type="button" onclick="window.location.href='banliqingkuang.jsp'" value="统计条件" />	
			-->
			<input id="excel_out" name="btn1" type="button"  onclick="" value="导出" />
			<span class="blank3"></span>
		</td>  	
	</tr>
</table>
</form>
</body>
</html>
