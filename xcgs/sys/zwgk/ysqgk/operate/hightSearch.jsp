<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>高级搜索</title>
<meta http-equiv="X-UA-Compatible" content="IE=8"/>
<meta name="generator" content="cicro-Builder"/>


<link type="text/css" rel="stylesheet" href="../../../styles/themes/default/tree.css"/>
<link type="text/css" rel="stylesheet" href="../../../styles/sq.css"/>
<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../js/ysqgk.js"></script>
<script type="text/javascript">
var search_con = "";

$(document).ready(function() 
{
	initButtomStyle();
	init_input();

	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");

	$("#lab_num option[value="+top.getCurrentFrameObj().snum+"]").attr("selected",true);	
});
	
function related_ok()
{	
	var do_state = $("#do_state :selected").val();
	if(do_state != "" && do_state != null && do_state != -1)
	{
		search_con += " and do_state = "+do_state+" and  final_status = 0 ";
	}else{
		search_con += " and  final_status = -1 ";
	}

	if($("#is_third").attr('checked'))
	{
		search_con += " and is_third = 1 ";
	}
	if($("#is_extend").attr('checked'))
	{
		search_con += " and is_extend = 1 ";
	}
	if($("#publish_state").attr('checked'))
	{
		search_con += " and do_state = 1 ";
	}
    var reply_type = $("#reply_type :selected").val(); 
    if(reply_type != "" && reply_type != 0)
	{
		search_con += " and reply_type = "+reply_type;
	}
	
    var ysq_code = $("#ysq_code").val(); 
    if(ysq_code != "" && ysq_code != null)
	{
		search_con += " and ysq_code like '%"+ysq_code+"%' ";
	}
    
    var name = $("#name").val(); 
    if(name != "" && name != null)
	{
		search_con += " and name = '"+name+"' ";
	}
    var content = $("#content").val(); 
    if(content != "" && content != null)
	{
		search_con += " and content like '%"+content+"%' ";
	}
    var description = $("#description").val(); 
    if(description != "" && description != null)
	{
		search_con += " and description like '%"+description+"%' ";
	}

	//时间
	var put_dtime_s = $("#put_dtime_s").val();
	var put_dtime_e = $("#put_dtime_e").val();

	if(put_dtime_s != "" && put_dtime_s != null)
	{
		search_con += " and put_dtime > '"+put_dtime_s+" 00:00:00'";
		if(put_dtime_e != "" && put_dtime_e != null)
		{
			if(judgeDate(put_dtime_e,put_dtime_s))
			{
				top.msgWargin("申请结束时间不能小于开始时间!");
				return;
			}
		}
	}	
	if(put_dtime_e != "" && put_dtime_e != null)
	{
		search_con += " and put_dtime < '"+put_dtime_e+" 23:59:59'";
	}
	//受理时间
	var accept_dtime_s = $("#accept_dtime_s").val();
	var accept_dtime_e = $("#accept_dtime_e").val();

	if(accept_dtime_s != "" && accept_dtime_s != null)
	{
		search_con += " and accept_dtime > '"+accept_dtime_s+" 00:00:00'";
		if(accept_dtime_e != "" && accept_dtime_e != null)
		{
			if(judgeDate(accept_dtime_e,accept_dtime_s))
			{
				top.msgWargin("受理结束时间不能小于开始时间!");
				return;
			}
		}
	}	
	if(accept_dtime_e != "" && accept_dtime_e != null)
	{
		search_con += " and accept_dtime < '"+accept_dtime_e+" 23:59:59'";
	}
	//处理时间
	var reply_dtime_s = $("#reply_dtime_s").val();
	var reply_dtime_e = $("#reply_dtime_e").val();

	if(reply_dtime_s != "" && reply_dtime_s != null)
	{
		search_con += " and reply_dtime > '"+reply_dtime_s+" 00:00:00'";
		if(reply_dtime_e != "" && reply_dtime_e != null)
		{
			if(judgeDate(reply_dtime_e,reply_dtime_s))
			{
				top.msgWargin("处理结束时间不能小于开始时间!");
				return;
			}
		}
	}	
	if(reply_dtime_e != "" && reply_dtime_e != null)
	{
		search_con += " and reply_dtime < '"+reply_dtime_e+" 23:59:59'";
	}

	//排序处理
	var orderByFields = $("#orderByFields :selected").val();
    top.getCurrentFrameObj().highSearchHandl(search_con,do_state,orderByFields);
	top.CloseModalWindow();
}
function related_cancel(){
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
					 <th>申请单状态：</th>
					 <td>
						<select id="do_state"  style="width:154px">
							<option value="0" selected>未受理</option>
							<option value="1">已受理</option>
							<option value="2">已回复</option>
							<option value="-1">无效申请</option>
						</select>
					 </td>
				</tr>
				<tr>
					 <td colspan="2">
					   <table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0" width="400">
					      <tr>
					      	 <td width="20"></td>
					         <td width="75">征询第三方:</td>
					         <td width="35"><input id="is_third" name="is_third" type="checkbox" value="1"/><label for="e">是</label></li></td>
					         <td width="80">延长答复期限:</td>
					         <td width="35"><input id="is_extend" name="is_extend" type="checkbox" value="1"/><label for="e">是</td>
					         <td width="35">发布:</td>
					         <td width="35"><input id="publish_state" name="publish_state" type="checkbox" value="1"/><label for="e">是</td>
					         <td></td>
					      </tr>
					   </table>
					 </td>
				</tr>
				<tr>
					 <th>回复方式：</th>
					 <td>
						<select id="reply_type" name="reply_type" style="width:154px">
							<option value="0" selected>请选择</option>
							<option value="1">全部公开</option>
							<option value="2">部分公开</option>
							<option value="3">不予公开</option>
							<option value="4">非本单位公开信息</option>
							<option value="5">信息不存在</option>
						</select>
					 </td>
				</tr>
				<tr>
					 <th>申请单编号：</th>
					 <td>
						<input type="ysq_code" id="ysq_code" style="width:150px" value="">
					 </td>
				</tr>
				<tr>
				 <th>申请人：</th>
				 <td>
					<input type="name" id="name" style="width:150px" value="">
				 </td>
				</tr>
				<tr>
				 <th>申请时间：</th>
				 <td>
					从&nbsp;<input id="put_dtime_s" name="put_dtime_s" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly" style="width:80px"/>
					到&nbsp;<input id="put_dtime_e" name="put_dtime_e" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"  style="width:80px"/>
				 </td>
				</tr>
				<tr>
				 <th>受理时间：</th>
				 <td>
					从&nbsp;<input id="accept_dtime_s" name="accept_dtime_s" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly" style="width:80px"/>
					到&nbsp;<input id="accept_dtime_e" name="accept_dtime_e" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"  style="width:80px"/>
				 </td>
				</tr>
				<tr>
				 <th>处理时间：</th>
				 <td>
					从&nbsp;<input id="reply_dtime_s" name="reply_dtime_s" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly" style="width:80px"/>
					到&nbsp;<input id="reply_dtime_e" name="reply_dtime_e" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"  style="width:80px"/>
				 </td>
				</tr>
				<tr>
					<th>所需内容描述：</th>
					<td>
					 <textarea id="content"  name="content" style="width:320px;height:70px;" cols="10" rows="10"></textarea>
					</td>
				</tr>
				<tr>
					<th>用途描述：</th>
					<td>
					 <textarea id="description" name="description" style="width:320px;height:60px;" cols="100" rows="10"></textarea>
					</td>
				</tr>
				<tr>
				 <th>排序方式：</th>
				 <td>
					<select id="orderByFields" class="input_select" style="width:154px"> 
						<option selected="selected" value="1">申请时间倒序</option> 
						<option value="2">申请时间正序</option> 
						<option value="3">受理时间倒序</option> 
						<option value="4">受理时间正序</option> 
						<option value="5">处理时间倒序</option> 
						<option value="6">处理时间正序</option> 
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
					<input type="button" value="重置" class="btn1" onclick="form1.reset()" />
					<input type="button" value="取消" class="btn1" onclick="related_cancel()" />
				</td>
			</tr>
		</table>
		<span class="blank3"></span>
		</form>
	</body>
</html>