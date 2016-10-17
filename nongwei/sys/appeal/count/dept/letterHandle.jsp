<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.DateUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
   String now = DateUtil.getCurrentDate();
   String now1 = now.substring(0,7)+"-01";
   pageContext.setAttribute("now1",now1);
   pageContext.setAttribute("now",now);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>按诉求目的-警示状态</title> 


<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/letterHandle.js"></script>
<script type="text/javascript">
 
$(document).ready(function(){
	//得到所有的业务
	setBusiness();
	initButtomStyle();
	init_input();
	$("label").unbind("click").click(function(){
		
		var b_id = $(this).prev("input").attr("id");
		if($("[id='"+b_id+"']").is(':checked')){
			 $("[id='"+b_id+"']").removeAttr("checked");
		 }else{
			$("[id='"+b_id+"']").attr("checked",'true');
		 }
		 setAllState();
	});
});
</script>
</head>
<body>
<form id="form1" name="form1" action="" method="get">
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
		<td align="right" valign="middle" width="200"> 
				 <input id="addButton" name="btn1" type="button" onclick="funOK()" value="统计" />	
				 <input id="userAddReset" name="btn1" type="button" onclick="reset()" value="重置" />
				 <input id="excel_out" name="btn1" type="button"  onclick="" value="导出" />
				 <span class="blank3"></span>
		</td>
	</tr>
	<tr>
			<td align="left" valign="middle" colspan="2">
				<ul class="inputUL" id="b_tr"> 
					<li><label class="f_red">*</label>递交渠道：&nbsp;&nbsp;<input type="checkbox" name="all" id="all" value="1" onclick="fnAll()" style="vertical-align:middle"><b><label onclick="fnAllSet()">全选</label></b></li>
				</ul>
				<span class="blank3"></span>
			</td>
	</tr>
	<!--tr>
    	    <td class="search_td fromTabs" colspan="2">
        		<label class="f_red">*</label>统计方式：&nbsp;&nbsp;
				<input id="countType"  type="radio" name="countType" value="dept" checked="checked"/>
				<span class="checkBox_text">按部门</span>
				<input id="countType"  type="radio" name="countType" value="user"/>
				<span class="checkBox_text">按人员</span>
            </td>
	</tr-->
</table>

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
<!--table id="buttonTable" class="table_option" border="0" cellpadding="0" cellspacing="0" style="display:none">
	<tr>	
		<td>
			<input id="excel_out" name="btn1" type="button"  onclick="" value="导出" />
			<span class="blank3"></span>
		</td>  	
	</tr>
</table-->
</form>
</body>
</html>
