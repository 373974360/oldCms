<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>  
<title>业务管理</title>  
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
<link rel="stylesheet" type="text/css" href="styles.css">
-->
<%
	String modelid = request.getParameter("model_id");
%>
<link type="text/css" rel="stylesheet" href="/sys/styles/main.css" />
<link type="text/css" rel="stylesheet" href="/sys/styles/sub.css" />
<script type="text/javascript" src="/sys/js/jquery.js"></script>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/sys/js/extend.js"></script>
<script type="text/javascript" src="/sys/js/java.js"></script>
<script type="text/javascript" src="/sys/js/jsonrpc.js"></script>

<script type="text/javascript">
var m = new Map();
var beignTime = "";
var endTime = "";
var is_open="1";  //是否公开
var is_public="1" //是否发布
var model_id = "<%= modelid %>";


//导出数据(xlsx格式)
function startexportData()
{
	m.put("is_open",is_open);
	m.put("publish_status",is_public);
	getSearchTime();
	
	if(model_id != null)
	{
		m.put("model_id",model_id);
	}
	top.getCurrentFrameObj().getexportData(m);
	top.CloseModalWindow();
}

//获取是否公开的值
function getIsOpenRadioVal(isopen)
{
	is_open = isopen;
	if(isopen != "")
	{
		m.put("is_open",isopen);
	}
}

//获取是否发布的值
function getIsPublicRadioVal(ispublic)
{
	is_public = ispublic;
	if(ispublic != "")
	{
		m.put("publish_status",is_public);
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

//获取处理状态的值
function statusSearch(vals)
{
	if(vals != "" && vals != null)
	{		
		m.put("sq_status",vals);		
	}
}

</script>
</head>
  
<body>
  
<table class="table_form">
	<tr>
		<th>是否公开:</th>
		<td>
			<input type="radio" id="isOpen" onchange="getIsOpenRadioVal(this.value)" name="isOpenradio" value="1" checked="checked">是
			<input type="radio" id="isOpen" onchange="getIsOpenRadioVal(this.value)" name="isOpenradio" value="0">否
	    </td>
	</tr>
	<tr>
		<th>是否发布:</th>
		<td>
			<input type="radio" id="idPublic" onchange="getIsPublicRadioVal(this.value)" name="isPublicradio" value="1" checked="checked">是
			<input type="radio" id="idPublic" onchange="getIsPublicRadioVal(this.value)" name="isPublicradio" value="0">否
		</td>
	</tr>
	<tr>
		<th>处理状态:</th>
		<td><select id="sq_status" class="input_select width55" onchange="statusSearch(this.value)">
				<option selected="selected" value="">全部</option>
				<option value="0">待处理</option>
				<option value="1">处理中</option>
				<option value="2">待审核</option>
				<option value="3">已办结</option>
			</select>
		</td>
	</tr>
		
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
			<input type="button" id="btn_expor" value="确定" width="20px;" onclick="startexportData()"  />
		</td>
	</tr>
</table>

</body>
</html>
