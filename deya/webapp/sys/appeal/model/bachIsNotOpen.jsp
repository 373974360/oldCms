<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String modelId = request.getParameter("model_id");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'bachIsNotOpen.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
<link rel="stylesheet" type="text/css" href="styles.css">
-->
<link type="text/css" rel="stylesheet" href="/sys/styles/main.css" />
<link type="text/css" rel="stylesheet" href="/sys/styles/sub.css" />
<script type="text/javascript" src="/sys/js/jquery.js"></script>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/sys/js/extend.js"></script>
<script type="text/javascript" src="/sys/js/java.js"></script>
<script type="text/javascript" src="/sys/js/jsonrpc.js"></script>

<script type="text/javascript">
var model_id = "<%= modelId %>";
var m = new Map();

function batchIsNotOpenOk()
{
	if(model_id != null)
	{
		m.put("model_id",model_id);
	}
	getSearchTime();
	
	var bnopenType = jsonrpc.SQRPC.batchIsNotOpenOk(m);
	if(bnopenType)
	{
		top.msgAlert("操作成功");
		top.CloseModalWindow();
	}else{
		top.msgAlert("操作失败!");
	}
}

//搜索时间
function getSearchTime()
{
	beignTime = $("#beignTime").val();
	endTime = $("#endTime").val();

	if(beignTime !="" && beignTime != null && endTime !="" && endTime !=null){
		if(!judgeDate(beignTime,endTime)){
			top.msgAlert("结束时间不能小于起始时间!");
			return;
		}
	}
	if(beignTime !=""){
		m.put("beignTime",beignTime);
	}
	if(endTime !=""){
		m.put("endTime",endTime);
	}
}

</script>

</head>
  
<body>
  
<table class="table_form">
	<tr>
		<th>请选择日期:</th>
		<td colspan="4">
			<input type="text" id="beignTime" name="beignTime"  size="11" class="input_text" value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">至&nbsp;<input type="text" id="endTime" name="endTime"  size="11" class="input_text" value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">
	    </td>
	</tr>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option">
	<tr>
		<td colspan="2">
			<input type="button" id="btn_batchIsNoOpen" value="确定" width="20px;" onclick="batchIsNotOpenOk()"  />
		</td>
	</tr>
</table>

</body>
</html>