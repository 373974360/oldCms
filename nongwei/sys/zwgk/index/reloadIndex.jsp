<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>重新生成索引码</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

var site_id = "${param.site_id}";
var SiteRPC = jsonrpc.SiteRPC;
$(document).ready(function(){	
	initButtomStyle();
	init_input();
		
});

function reloadIndex()
{
	var m = new Map();
	if(site_id != "" && site_id != null)
		m.put("site_id",site_id);
	if($("#start_time").val() != "")
		m.put("start_time",$("#start_time").val());
	if($("#end_time").val() != "")
		m.put("end_time",$("#end_time").val());
	if(jsonrpc.GKInfoRPC.reloadGKIndex(m))
	{
		top.msgAlert("重新生成索引码任务已完成");
	}else
	{
		top.msgWargin("索引码重置失败,请重新生成");
	}
}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="add_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>	
		<tr>			
			<td colspan="2">
				　　　　“索引码重置”的功能：是将本节点下所有公开信息的索引码将全部重新生成。<span class="blank12"></span>　　　　确定执行本操作后，请耐心等待，当系统给出“重新生成索引码任务已完成！”的提示信息后，再进行其他操作。
			</td>			
		</tr>
		<tr>	
			<th>时间范围：</th>
			<td><input class="Wdate" type="text" name="start_time" id="start_time" size="11" style="height:16px;line-height:16px;" 
					value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true" />
		         --
			    <input class="Wdate" type="text" name="end_time" id="end_time" size="11" style="height:16px;line-height:16px;" 
					 value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true"/></td>			
		</tr>
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="reloadIndex()" value="索引码重置" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
