<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>信息搜索</title>
		<jsp:include page="../include/include_info_tools.jsp"/>
		<script type="text/javascript" src="js/gkPublic.js"></script>

		<script type="text/javascript">

$(document).ready(function() {
	initButtomStyle();
	init_input();
	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");

	$("#lab_num option[value="+top.getCurrentFrameObj().snum+"]").attr("selected",true);
	
	getAllInuptUserID();

	getDataList("gk_type","gk_gkfs");
	getDataList("gk_way","gk_gkxs");
	getDataList("gk_time_limit","gk_gkshixian");
	getDataList("gk_range","gk_gkfanwei");
	getDataList("gk_validity","gk_validity");

	getCateClassList("topic_id_td","ztfl");
	getCateClassList("theme_id_td","tcfl");
	getCateClassList("serve_id_td","fwdxfl");
	
	noSelected();
});

function noSelected()
{
	$("select").each(function(){
		$(this).find("option").first().attr("selected",true);
	})
	
	
	
}

//得到所有录入人列表
function getAllInuptUserID()
{
	var m = new Map();
	var cids = top.getCurrentNodeChileLeftNodeIDS();
	
	cids = cids.substring(1);
	if(cids.indexOf(",") > -1)
		m.put("cat_ids", cids);
	else
		m.put("cat_id", cids);

	m.put("site_id", top.getCurrentFrameObj().site_id);
	var user_list = InfoBaseRPC.getAllInuptUserID(m);
	user_list = List.toJSList(user_list);
	$("#input_user").addOptions(user_list,"user_id","user_realname");
}
	
function related_ok(){
	var search_con = "";
	var lab_num = $("#lab_num :selected").val();
	var orderByFields = $("#orderByFields :selected").val();
	var gk_validity = $("#gk_validity :selected").val();
	if(gk_validity != "" && gk_validity != null)
	{
		search_con += " and gk.gk_validity = '"+gk_validity+"'";
	}
	var gk_type = $("#gk_type :selected").val();
	if(gk_type != "" && gk_type != null)
	{
		search_con += " and gk.gk_type = '"+gk_type+"'";
	}
	var gk_way = $("#gk_way :selected").val();
	if(gk_way != "" && gk_way != null)
	{
		search_con += " and gk.gk_way = '"+gk_way+"'";
	}
	var gk_time_limit = $("#gk_time_limit :selected").val();
	if(gk_time_limit != "" && gk_time_limit != null)
	{
		search_con += " and gk.gk_time_limit = '"+gk_time_limit+"'";
	}
	var gk_range = $("#gk_range :selected").val();
	if(gk_range != "" && gk_range != null)
	{
		search_con += " and gk.gk_range = '"+gk_range+"'";
	}
	var topic_key = $("#topic_key").val();
	if(topic_key != "" && topic_key != null)
	{
		search_con += " and gk.topic_key like '%"+topic_key+"%' ";
	}
	var place_key = $("#place_key").val();
	if(place_key != "" && place_key != null)
	{
		search_con += " and gk.place_key like '%"+place_key+"%' ";
	}
	var generate_start_time = $("#generate_start_time").val();
	var generate_end_time = $("#generate_end_time").val();
	if(generate_start_time != "" && generate_start_time != null)
	{
		search_con += " and gk.generate_dtime > '"+generate_start_time+" 00:00:00'";
		if(generate_end_time != "" && generate_end_time != null)
		{
			if(judgeDate(generate_end_time,generate_start_time))
			{
				top.msgWargin("生成日期结束时间不能小于开始时间");
				return;
			}
		}
	}	
	if(generate_end_time != "" && generate_end_time != null)
	{
		search_con += " and gk.generate_dtime < '"+generate_end_time+" 23:59:59'";
	}

	var effect_start_time = $("#effect_start_time").val();
	var effect_end_time = $("#effect_end_time").val();
	if(effect_start_time != "" && effect_start_time != null)
	{
		search_con += " and gk.effect_dtime > '"+effect_start_time+" 00:00:00'";
		if(effect_end_time != "" && effect_end_time != null)
		{
			if(judgeDate(effect_end_time,effect_start_time))
			{
				top.msgWargin("生效日期结束时间不能小于开始时间");
				return;
			}
		}
	}	
	if(effect_end_time != "" && effect_end_time != null)
	{
		search_con += " and gk.effect_dtime < '"+effect_end_time+" 23:59:59'";
	}

	var aboli_start_time = $("#aboli_start_time").val();
	var aboli_end_time = $("#aboli_end_time").val();
	if(aboli_start_time != "" && aboli_start_time != null)
	{
		search_con += " and gk.aboli_dtime > '"+aboli_start_time+" 00:00:00'";
		if(aboli_end_time != "" && aboli_end_time != null)
		{
			if(judgeDate(aboli_end_time,aboli_start_time))
			{
				top.msgWargin("废止日期结束时间不能小于开始时间");
				return;
			}
		}
	}	
	if(aboli_end_time != "" && aboli_end_time != null)
	{
		search_con += " and gk.aboli_dtime < '"+aboli_end_time+" 23:59:59'";
	}
	var keyWord = $("#keyWord").val();
	if(keyWord != "" && keyWord != null)
	{
		search_con += " and (ci.title like '%"+keyWord+"%' or ci.subtitle like '%"+keyWord+"%') ";
	}
	var doc_no = $("#doc_no").val();
	if(doc_no != "" && doc_no != null)
	{
		search_con += " and gk.doc_no = '"+doc_no+"' ";
	}
	var gk_index = $("#gk_index").val();
	if(gk_index != "" && gk_index != null)
	{
		search_con += " and gk.gk_index = '"+gk_index+"' ";
	}
	var input_user = $("#input_user :selected").val(); 
	if(input_user != "" && input_user != null)
	{
		search_con += " and ci.input_user = "+input_user;
	}
	var topic_id = $("#topic_id_td select").last().find("option[selected=true]").val();
	if(topic_id != "" && topic_id != null)
	{
		search_con += " and gk.topic_id = "+topic_id+"";
	}
	var theme_id = $("#theme_id_td select").last().find("option[selected=true]").val();
	if(theme_id != "" && theme_id != null)
	{
		search_con += " and gk.theme_id = "+theme_id+"";
	}
	var serve_id = $("#serve_id_td select").last().find("option[selected=true]").val();
	if(serve_id != "" && serve_id != null)
	{
		search_con += " and gk.serve_id = "+serve_id+"";
	}
	
	top.getCurrentFrameObj().highSearchHandl(search_con,lab_num,orderByFields);
	top.CloseModalWindow();
}

function related_cancel(){
	top.CloseModalWindow();
}

function getCheckedLeafNode(){
	
}



</script>
</head>
	<body>
		<span class="blank3"></span>
		<form id="form1" name="form1" action="#" method="post">		
		<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0">
			<tbody>
				<tr>
				 <th>信息状态：</th>
				 <td width="165px">
					<select id="lab_num" style="width:154px">
						<option value="0">已发</option>
						<option value="1">待发</option>
						<option value="2">已撤</option>
						<option value="3">待审</option>
						<option value="4">退稿</option>
						<option value="5">草稿</option>
						<option value="6">回收站</option>
					</select>
				 </td>
				 <th>信息有效性：</th>
				 <td class="width150">
					<select id="gk_validity" class="input_select" style="width:154px;">
						<option value=""></option>
					</select>
				 </td>
				</tr>
				<tr>
				 <th>公开方式：</th>
				 <td width="135">
					<select id="gk_type" class="gk_type" style="width:154px;" onchange="changeGKReason(this.value)">
						<option value=""></option>
					</select>
				 </td>
				 <th style="width:40px;">公开形式：</th>
				 <td width="135">
					<select id="gk_way" class="input_select" style="width:154px;">
						<option value=""></option>
					</select>
				 </td>
				</tr>
				<tr>
				 <th>公开时限：</th>
				 <td width="135">
					<select id="gk_time_limit" class="input_select" style="width:154px;">
						<option value=""></option>
					</select>
				 </td>
				 <th style="width:40px;">公开范围：</th>
				 <td width="135">
					<select id="gk_range" class="input_select" style="width:154px;">
						<option value=""></option>
					</select>
				 </td>
				</tr>
				<tr>
				 <th>主题关键词：</th>
				 <td >
					<input id="topic_key" name="topic_key" type="text" maxlength="80" value="" />
				 </td>
				 <th>位置关键词：</th>
				 <td>
					<input id="place_key" name="place_key" type="text" maxlength="80" value="" />
				 </td>
				</tr>
				<tr>
				 <th>生成日期：</th>
				 <td colspan="3">
					从&nbsp;<input id="generate_start_time" name="start_time" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly" style="width:80px"/>
					到&nbsp;<input id="generate_end_time" name="end_time" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"  style="width:80px"/>
				 </td>
				</tr>
				<tr>
				 <th>生效日期：</th>
				 <td colspan="3">
					从&nbsp;<input id="effect_start_time" name="start_time" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly" style="width:80px"/>
					到&nbsp;<input id="effect_end_time" name="end_time" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"  style="width:80px"/>
				 </td>
				</tr>
				<tr>
				 <th>废止日期：</th>
				 <td colspan="3">
					从&nbsp;<input id="aboli_start_time" name="start_time" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly" style="width:80px"/>
					到&nbsp;<input id="aboli_end_time" name="end_time" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"  style="width:80px"/>
				 </td>
				</tr>
				<tr>
				 <th>关键字：</th>
				 <td colspan="3"><!-- 搜标题和副标题 -->
					<input type="text" id="keyWord" class="width200">
				 </td>	
				</tr>
				<tr>
				 <th>文号：</th>
				 <td colspan="3"><!-- 搜标题和副标题 -->
					<input type="text" id="doc_no" class="width200">
				 </td>	
				</tr>
				<tr>
				 <th>索引号：</th>
				 <td colspan="3"><!-- 搜标题和副标题 -->
					<input type="text" id="gk_index" class="width200">
				 </td>	
				</tr>
				<tr>
				 <th>录入人：</th>
				 <td colspan="3">
					<select id="input_user" style="width:154px">
						<option value="">全部</option>
					</select>
				 </td>
				</tr>
				<tr>
					<th>主题分类：</th>
					<td colspan="3" id="topic_id_td">
						<select class="input_select" style="width:154px;" onchange="setChileListToSelect(0,this.value,'topic_id_td')">
							<option value=""></option>
						</select>               
					</td>
				</tr>
				<tr>
					<th>体裁分类：</th>
					<td colspan="3" id="theme_id_td">
						<select class="input_select" style="width:154px;" onchange="setChileListToSelect(0,this.value,'theme_id_td')">
							<option value=""></option>
						</select>
					</td>
				</tr>
				<tr>
					<th>服务对象分类：</th>
					<td colspan="3" id="serve_id_td">
						<select class="input_select" style="width:154px;" onchange="setChileListToSelect(0,this.value,'serve_id_td')">
							<option value=""></option>
						</select>
					   
					</td>
				</tr>
				<tr>
				 <th>排序方式：</th>
				 <td colspan="3">
					<select id="orderByFields" class="input_select" style="width:154px"> 
						<option selected="selected" value="1">时间倒序</option> 
						<option value="2">时间正序</option> 
						<option value="3">权重倒序</option> 
						<option value="4">权重正序</option>
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