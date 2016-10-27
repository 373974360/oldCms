<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String pro_type = request.getParameter("pro_type");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>已办结的诉求列表</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/sqList.js"></script>
<script type="text/javascript">
var sq_status = "";
var m = new Map();
var pro_type = "<%=pro_type%>";
var table_type = "transact"
if(pro_type != "" && pro_type != "null")
{
	m.put("pro_type",pro_type);
}
var p_list;//业务列表
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	if(pro_type == 2 || pro_type == 3 || pro_type == 4)
		getOptIDSByUser();
	//得到业务列表
	getModelListSByUserID();
	getPurposeList();
	initTable();
	reloadSQList("transact");	
});

function dateSearch(vals)
{
	m.put("search_date",vals);	
	
	reloadSQList("transact");
}

function modelSearch(vals)
{
	m.put("model_id",vals);
	m.remove("search_date");
	reloadSQList("transact");
}

function pcSearch()
{
	var s_val = $("#searchkey").val().trim();
	val.checkEmpty(s_val,"查询条件","searchkey");	
	if(!val.getResult()){		
		return;
	}	
	$("#searchFields option").each(function(){
		if($(this).attr("selected") == true)
		{
			m.put($(this).val(),s_val);
		}else
			m.remove($(this).val());
	});
	m.remove("search_date");
	m.remove("tag_ids");
	reloadSQList("transact");
}

function sortSQList(val)
{
	table.sortCol = val.substring(0,val.indexOf(","));
	table.sortType = val.substring(val.indexOf(",")+1);	
	reloadSQList("transact");
}
</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td class="fromTabs">
				渠道：
				<select id="model_id" class="input_select width70" onchange="modelSearch(this.value)">
					<option selected="selected" value="">全部</option>
				</select>
				<select id="submit_names" class="input_select width70" onchange="deptSearch(this.value)">
					<option selected="selected" value="">全部</option>
				</select>
				目地：
				<select id="pur_id" class="input_select width45" onchange="purSearch(this.value)">
					<option selected="selected" value="">全部</option>
				</select>
				状态：
				<select id="sq_status" class="input_select width55" onchange="statusSearch(this.value)">
					<option selected="selected" value="">全部</option>
					<option value="0">待处理</option>
					<option value="1">处理中</option>
					<option value="2">待审核</option>
					<option value="3">已办结</option>
				</select>
				时间：
				<select id="sq_dtime" class="input_select width60" onchange="dateSearch(this.value)">
					<option value="all">全部</option>
					<option value="day">当天</option>
					<option value="week">一周内</option>
					<option value="month">一月内</option>
					<option value="year">一年内</option>
				</select>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
				<select id="searchFields" class="width70">
					<option selected="selected" value="sq_title2">信件标题</option>
					<option value="sq_realname">来信人</option>
					<option value="submit_name">收信单位</option>
					<option value="sq_content2">信件内容</option>
					<option value="sq_code">信件编码</option>
					<option value="tag">特征标记</option>
				</select>
				<select id="tag_select" class="input_select hidden" onchange="pcSearchForTag(this.value)">
					<option value=""></option>
				</select>
				<input id="searchkey" type="text" class="" value="" style="width:70px" /><input id="btnSearch" type="button" value="搜索" onclick="pcSearch()"/>
				<select id="orderByFields" class="input_select" onchange="sortSQList(this.value)">
					<option selected="selected" value="sq_id,desc">时间倒序</option>
					<option value="sq_id,asc">时间顺序</option>
				</select>
				<span class="blank3"></span>
			</td>		
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div><!-- 列表DIV -->
	<div id="turn"></div><!-- 翻页DIV -->
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">	
			<input id="btn211" name="btn3" type="button" class="hidden" onclick="publicSelectSinglCheckbox(table,'sq_id','withdrawSQForProcess()')" value="信件收回" />
		</td>
	</tr>
   </table>	
</div>
</body>
</html>