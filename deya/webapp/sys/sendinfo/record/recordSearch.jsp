<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>信息搜索</title>
		<jsp:include page="../../include/include_tools.jsp"/>
		<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
var SendInfoRPC = jsonrpc.SendInfoRPC;
var site_id = "${param.site_id}";
$(document).ready(function() {
	initButtomStyle();	
	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");

	getReceiveSiteListForRecord();
	getReceiveCateInfoList();
	init_input();
});

function getReceiveCateInfoList()
{
	var l = SendInfoRPC.getReceiveCateInfoList(site_id);
	l = List.toJSList(l);
	if(l != null && l.size() > 0)
	{
		for (var i = 0; i < l.size(); i++) {
			$("#cate_list").append('<div style="height:20px;padding-left:3px;padding-top:2px"><input style="vertical-align:middle" type="checkbox" id="c_id" name="c_id" value="'+l.get(i).cat_id+'"><label  >'+l.get(i).cat_cname+'</label></div>');
		}
	}
}

function getReceiveSiteListForRecord()
{
	var l = SendInfoRPC.getReceiveSiteListForRecord(site_id);
	l = List.toJSList(l);
	if(l != null && l.size() > 0)
	{
		for (var i = 0; i < l.size(); i++) {
			$("#send_site").append('<div style="height:20px;padding-left:3px;padding-top:2px"><input style="vertical-align:middle" type="checkbox" id="b_id" name="b_id" value="'+l.get(i).t_site_id+'"><label  >'+l.get(i).t_site_name+'</label></div>');
		}
	}

}
	
function related_ok(){
	var search_con = "";
	
	var t_site_ids = "";
	$(":checked[id='b_id']").each(function(){
		t_site_ids += ",'"+$(this).val()+"'";
	});
	if(t_site_ids != null && t_site_ids != "")
	{
		search_con += " and re.t_site_id in ("+t_site_ids.substring(1)+")";
	}
	
	var start_time = $("#start_time").val();
	var end_time = $("#end_time").val();
	if(start_time != "" && start_time != null)
	{
		search_con += " and re.send_dtime > '"+start_time+" 00:00:00'";
		if(end_time != "" && end_time != null)
		{
			if(judgeDate(end_time,start_time))
			{
				top.msgWargin("结束时间不能小于开始时间");
				return;
			}
		}
	}	
	if(end_time != "" && end_time != null)
	{
		search_con += " and re.send_dtime < '"+end_time+" 23:59:59'";
	}
	var adopt_status = $("#adopt_status option:selected").val();
	if(adopt_status != "" && adopt_status != null)
	{
		search_con += " and re.adopt_status = "+adopt_status;
	}
	top.getCurrentFrameObj().searchHandl(search_con);
	top.CloseModalWindow();
}


</script>
</head>
	<body>
		<span class="blank3"></span>
		<form id="form1" name="form1" action="#" method="post">		
		<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0">
			<tbody>
				<tr>
				 <th>接收站点：</th>
				 <td>
					<div id="send_site" style="width:250px;height:100px;overflow:auto;" class="border_color">
					
					</div>
				 </td>
				</tr>			
				<tr>
				 <th>时间：</th>
				 <td>
					从&nbsp;<input id="start_time" name="start_time" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly" style="width:80px"/>
					到&nbsp;<input id="end_time" name="end_time" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"  style="width:80px"/>
				 </td>
				</tr>
				<tr>
				 <th>采用状态：</th>
				 <td>
					<select id="adopt_status" style="width:150px">
						<option value=""></option>
						<option value="1">已采用</option>
						<option value="0">未采用</option>
					</select>
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
					<input type="button" value="搜索" class="btn1" onclick="related_ok()" />
					<input type="button" value="取消" class="btn1" onclick="top.CloseModalWindow();" />
				</td>
			</tr>
		</table>
		<span class="blank3"></span>
		</form>
	</body>
</html>