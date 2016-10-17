<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String app_id = request.getParameter("app_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生成静态内容页</title>


<link rel="stylesheet" type="text/css" href="../../../styles/themes/default/tree.css">
<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/createHtml.js"></script>
<script type="text/javascript">


var app_id = "<%=app_id%>";

var jsonData;

$(document).ready(function(){	
	initButtomStyle();	
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
			
});

function createContentHtml()
{
	
	var start_time = $("#start_time").val();
	var end_time = $("#end_time").val();
	if(start_time != "" && start_time != null)
	{		
		if(end_time != "" && end_time != null)
		{
			if(judgeDate(end_time,start_time))
			{
				top.msgWargin("结束时间不能小于开始时间");
				return;
			}
		}
	}

	
	var m = new Map();
	m.put("app_id",app_id);	
	if(start_time != "" && start_time != null)
		m.put("start_time",start_time);
	if(end_time != "" && end_time != null)
		m.put("end_time",end_time);

	$("#loadImg").show();
	$("#create_button").attr("disabled",true);
	if(InfoBaseRPC.batchPublishContentHtml(m))
	{
		top.msgAlert("页面生成成功");
		$("#loadImg").hide();
		$("#create_button").removeAttr("disabled");
		return;
	}else
	{
		top.msgWargin("页面生成失败,请重新生成");
		$("#loadImg").hide();
		$("#create_button").removeAttr("disabled");
		return;
	}
}

</script>
</head>

<body>
<table style="width:100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>	 
	 <td valign="top">
		<span class="blank12"></span>
		<table class="table_form" border="0" cellpadding="0" cellspacing="0" align="left">
		<tr>
		 <td style="width:70px">发布时间：</td>
		 <td>
			<input id="start_time" name="start_time" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly" style="width:120px"/>
				至&nbsp;<input id="end_time" name="end_time" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"  style="width:120px"/>
		 </td>
		</tr>
		<tr>
			<td align="left" valign="middle" colspan="2">
				<span class="blank12"></span>	
				<input id="create_button" name="btn1" type="button" onclick="createContentHtml();" value="生成静态页" />	
				<span class="hidden" id="loadImg">&nbsp;&nbsp;<img width="12px" height="12px" src="../../../images/loading.gif"/></span>
			</td>
		</tr> 
	</table>
	 </td>
	</tr>
</table>
</div>
</body>
</html>