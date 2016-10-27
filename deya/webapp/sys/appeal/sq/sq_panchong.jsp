<%@ page contentType="text/html; charset=utf-8"%>
<%
	String top_index = request.getParameter("top_index");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信件预判重列表</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />


<link type="text/css" rel="stylesheet" href="../../styles/sq.css" />
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/sqList.js"></script>
<script type="text/javascript">

var top_index = "<%=top_index%>";
var defaultBean;
var sq_realname;
var sq_title2;
var sq_id;
var accept_dtime;
var model_bean;
var is_admin;
var current_dept_id;
var m = new Map();

$(document).ready(function(){
	initData();	

	initButtomStyle();
	init_input();
	init_editer_min("pro_note");
	//得到重复列表
	getReduplicateSQList();
	iniSQbox();
	//iniDoList();
});

function initData()
{
	is_admin = top.getCurrentFrameObj(top_index).is_admin;
	current_dept_id = top.getCurrentFrameObj(top_index).current_dept_id;
	defaultBean = top.getCurrentFrameObj(top_index).defaultBean;
	model_bean = top.getCurrentFrameObj(top_index).model_bean;
	sq_realname = defaultBean.sq_realname;
	sq_title2 = defaultBean.sq_title2;
	sq_id = defaultBean.sq_id;
	accept_dtime = defaultBean.accept_dtime;
	
	m.put("sq_realname",sq_realname);
	m.put("sq_title2",sq_title2);
	m.put("sq_id", sq_id);

	getCommonLangListByType(5);
}

//得到重复列表
function getReduplicateSQList()
{
	$("#sq_list").empty();
	var list = SQRPC.getReduplicateSQList(m);

	list = List.toJSList(list);
	var str = "";
	if(list != null && list.size() > 0)
	{
		for(var i=0;i<list.size();i++)
		{
			str += '<tr class="trList">';
			str += '<td align="center"><input id="initial_sq_id" name="initial_sq_id" class="inputHeadCol" type="radio" value="'+list.get(i).sq_id+'"/></td>';
			str += '<td align="left">'+list.get(i).sq_title2+'</td>';
			str += '<td align="center">'+list.get(i).sq_realname+'</td>';
			str += '<td align="center">'+list.get(i).sq_dtime+'</td>			';
			str += '<td align="left"><a class="openContent" href="javascript:void(0)">展开详细</a></td>';
			str += '</tr>';
			str += '<tr class="trContent hidden">';
			str += '<td colspan="5">';
			str += '<table class="table_noborder" border="0" cellpadding="0" cellspacing="0">';
			str += '<tr>';
			str += '<th>姓名：</th>';
			str += '<td>'+list.get(i).sq_realname+'</td>';
			str += '<th >性别：</th>';
			str += '<td>'+(list.get(i).sq_code == 1 ? "男":"女")+'</td>';
			str += '<th >身份证：</th>';
			str += '<td>'+list.get(i).sq_card_id+'</td>';
			str += '<th >手机：</th>';
			str += '<td>'+list.get(i).sq_phone+'</td>';
			str += '</tr>';
			str += '<tr>';
			str += '<th>职业：</th>';
			str += '<td>'+list.get(i).sq_vocation+'</td>';
			str += '<th>年龄：</th>';
			str += '<td>'+(list.get(i).sq_age == 0 ? "":list.get(i).sq_age)+'</td>';
			str += '<th>邮箱：</th>';
			str += '<td>'+list.get(i).sq_email+'</td>';
			str += '<th>固话：</th>';
			str += '<td>'+list.get(i).sq_tel+'</td>';
			str += '</tr>';
			str += '<tr>';
			str += '<th>住址：</th>';
			str += '<td colspan="7">'+list.get(i).sq_address+'</td>';
			str += '</tr>';
            str += '<tr>';
            str += '<th>收信人：</th>';
            str += '<td>'+list.get(i).submit_name+'</td>';
            str += '<th>写信时间：</th>';
            str += '<td>'+list.get(i).sq_dtime+'</td>';
            str += '<th>递交渠道：</th>';
			str += '<td >'+jsonrpc.SQModelRPC.getModelBean(list.get(i).model_id).model_cname+'</td>';
            str += '<th>公开意愿：</th>';
            str += '<td>'+(list.get(i).is_open == 1 ? "公开":"不公开")+'</td>';
			str += '</tr>';
			str += '<tr>';
            str += '<th>诉求目的：</th>';
			var pur_name = "";
			var pur_obj = jsonrpc.PurposeRPC.getPurposeByID(list.get(i).pur_id);
			if(pur_obj != null)
				pur_name = pur_obj.pur_name;
            str += '<td>'+(list.get(i).pur_id == 0 ? "":pur_name)+'</td>';
            str += '<th>发生地区：</th>';
            str += '<td>'+(list.get(i).area_id == 0 ? "":jsonrpc.AreaRPC.getAreaBean(list.get(i).area_id).area_cname)+'</td>';
            str += '<th>内容分类：</th>';
            str += '<td>'+(list.get(i).cat_id == 0 ? "":jsonrpc.AppealCategoryRPC.getCategoryBean(list.get(i).cat_id).cat_cname)+'</td>';
            str += '<th></th>';
            str += '<td></td>';
			str += '</tr>';
			str += '<tr>';
			str += '<th>信件状态：</th>';
			str += '<td>'+getSQStatusName(list.get(i).sq_status)+'</td>';
			str += '<th>信件编码：</th>';
			str += '<td colspan="5">'+list.get(i).sq_code+'</td>';
			str += '</tr> ';

			var from_list = jsonrpc.SQModelRPC.getCPFormListByModel(list.get(i).model_id);
			from_list = List.toJSList(from_list);
			var sq_custom_list = SQRPC.getSQCustomList(list.get(i).sq_id);//得到扩展字段的内容
			sq_custom_list = List.toJSList(sq_custom_list);

			if(from_list != null && from_list.size() > 0)
			{				
				for(var j=0;j<from_list.size();j++)
				{
					str += '<tr><th>'+from_list.get(j).field_cname+'：</th><td colspan="5">';
					for(var k=0;k<sq_custom_list.size();k++)
					{
						if(from_list.get(j).field_ename == sq_custom_list.get(k).cu_key)
							str += sq_custom_list.get(k).cu_value;
					}
					str += '</td></tr>';
				}				
			}

			str += '</table>';
			str += '<table id="" class="table_noborder" border="0" cellpadding="0" cellspacing="0">';
			str += '<tr>';
			str += '<th width="80">信件内容：</th>';
			str += '<td colspan="7">'+list.get(i).sq_content2+'</td>';
			str += '</tr>';
			str += '<tr>';
			str += '<th width="80">信件回复：</th>';
			str += '<td colspan="7">'+list.get(i).sq_reply+'</td>';
			str += '</tr>';
			str += '<tr>';
			str += '<th>附件：</th>';
			str += '<td colspan="7" id="pro_attr_td_'+list.get(i).pro_id+'">';			
			str += '</td>';
			str += '</tr>		';
			str += '</table>';
			str += '</td>';
			str += '</tr>';

			getSQAttInfo(list.get(i).sq_id,"1","pro_attr_td");//得到诉求附件信息
		}
		$("#sq_list").append(str);
		iniDoList();
	}
}

function getSQStatusName(sq_status)
{
	switch(sq_status){
		case 0 : return "待处理";
		case 1 : return "处理中";
		case 2 : return "待审核";
		case 3 : return "已办结";
	}
}

function iniSQbox()
{
	$(".sq_title_box").bind('click',function(){	
		if($(this).find(".sq_title_right").text()=="点击闭合")
		{
			$(this).find(".sq_title").removeClass("sq_title_minus").addClass("sq_title_plus");
			$(this).find(".sq_title_right").text("点击展开");
			$(this).parent().find(".sq_box_content").hide(300);
		}
		else
		{
			$(this).find(".sq_title").removeClass("sq_title_plus").addClass("sq_title_minus");
			$(this).find(".sq_title_right").text("点击闭合");
			$(this).parent().find(".sq_box_content").show(300);
		}
	})
}

function iniDoList()
{	
	$(".openAllContent").unbind("click").bind('click',function(){	
		if($(this).text()=="详细内容:全部展开")
		{
			$(this).text("详细内容:全部闭合");
			$(".trContent").show(300);
			$(".openContent").text("闭合详细");
		}
		else
		{
			$(this).text("详细内容:全部展开");
			$(".trContent").hide(300);
			$(".openContent").text("展开详细");
		}	
	})
	
	$(".openContent").bind('click',function(){
		var tmpObj = $(this).parent().parent().next(".trContent");
		
		if($(this).text()=="展开详细")
		{
			$(this).text("闭合详细");
			$(tmpObj).show(300);
		}
		else
		{
			$(this).text("展开详细");
			$(tmpObj).hide(300);
		}
	})
}


function doVoid(id)
{
	$(".sq_box_do").hide();
	$("#"+id).show(5,function(){$(document).scrollTop($(document.body).height());});
	//alert($(document.body).height());
	
}

function pcSearch()
{
	var s_val = $("#searchkey").val();
	val.checkEmpty(s_val,"查询条件","searchkey");	
	if(!val.getResult()){		
		return;
	}
	m.clear();
	m.put($("#searchFields2").val(),s_val);
	m.put("sq_id", sq_id);	
	getReduplicateSQList();
}

function pcSimpleSearch(vals)
{
	m.clear();
	if(vals == "titleAndUser")
	{		
		m.put("sq_realname",sq_realname);
		m.put("sq_title2",sq_title2);		
	}
	if(vals == "sq_realname")
	{
		m.put("sq_realname",sq_realname);
	}
	if(vals == "sq_title2")
	{
		m.put("sq_title2",sq_title2);
	}
	m.put("sq_id", sq_id);
	getReduplicateSQList();
}
</script>
</head>

<body>
<table class="table_option fromTabs" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<span >匹配方式：</span>
            <select id="searchFields" class="width110" onchange="pcSimpleSearch(this.value)">
				<option selected="selected" value="titleAndUser">来信人和标题</option>
				<option value="sq_realname">来信人</option>
				<option value="sq_title2">标题</option>
			</select>     
			<span class="blank3"></span>
		</td>
		<td align="right" valign="middle">
			<select id="searchFields2" class="width80">
				<option selected="selected" value="sq_title2_l">信件标题</option>
				<option value="sq_realname_l">来信人</option>
				<option value="sq_content2">信件内容</option>
			</select>
			<input id="searchkey" type="text" class="" value=""  /><input id="btnSearch" type="button" value="搜索" onclick="pcSearch()"/>			
			<span class="blank3"></span>
		</td>
	</tr>
</table>
<span class="blank3"></span>
<div class="sq_box_content">
<table id="infoList" class="table_dolist" border="0" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<td width="40" align="center">选择</td>
			<td align="left">信件标题</td>
			<td width="100" align="center">来信人</td>
			<td width="120" align="center">来信时间</td>
			<td align="left"><a class="openAllContent" href="javascript:void(0)">详细内容:全部展开</a></td>
		</tr>
	</thead>
	<tbody id="sq_list">    	
	</tbody>
</table>
</div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn2" name="btn2" type="button" onclick="doVoid('do_1');" value="置为重复件" />
			<input id="btn2" name="btn2" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank6"></span>
<!--回复-->
<div id="do_1" class="sq_box_do hidden">
	<div class="sq_title_box" >
		<div class="sq_title sq_title_minus">置为重复件</div>
	</div>
	<div class="sq_box_content">
	<table id="" class="table_view" border="0" cellpadding="0" cellspacing="0">
		<tbody> 				              
			<tr>
				<th id="pro_title_th">重复理由：</th>
				<td>
					<select id="quick_content" style="width:200px;" onchange="setSelectedCommonLang(this.value)" >

					</select>
					<span class="blank3"></span>
					<textarea id="pro_note" name="pro_note" style="width:620px;height:200px"></textarea>
				</td>
			</tr>			
			<tr>
				<th></th>
				<td>
					<input id="submitButton" name="btn1" type="button" onclick="setChongfu()" value="提交" />
					<input id="btn2" name="btn2" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="取消" />
				</td>
			</tr>
		</tbody>
	</table>
	</div>
</div>


</body>
</html>
