<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>字段管理</title>
<meta name="generator" content="cicro-Builder"/>
<meta name="author" content="cicro"/>
<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript">
var conf_id =<%=request.getParameter("conf_id")%>;
var defaultBean;
var num = 0;
var QueryDicRPC = jsonrpc.QueryDicRPC;
var QueryDicBean = new Bean("com.deya.wcm.bean.query.QueryDicBean",true);
var val = new Validator();

$(document).ready(function(){
	initButtomStyle();
	if(conf_id != "" && conf_id != "null" && conf_id != null)
	{		
		var dictList = QueryDicRPC.getDicListByConf_id(conf_id);
		    dictList = List.toJSList(dictList);//把list转成JS的List对象
		if(dictList != null && dictList.size() > 0)
			setDictList(dictList);
		else
			addDict();
		$("#addButton").click(insertQueryDicBean);		
	}
});

function addDict()
{	
	var htmlStr = '<tr>';
		htmlStr += '<td class="width40">';
		htmlStr += '<span class="c_index">'+(++num)+'：</span>';
		htmlStr += '</td>';
		htmlStr += '<td class="width250">';
		htmlStr += '<input id="field_cname_'+num+'" name="field_cname" class="width250" onblur="checkInputValue(\'field_cname_'+num+'\',false,80,\'中文名称\',\'\')"/>';		
                htmlStr += '</td>';
		
		htmlStr += '<td align="center"  style="text-align:center;width:90px;">';
		htmlStr += '<input  name="is_selected" type="checkbox" onclick="selectSelected()"/>';
		htmlStr += '</td>';

		htmlStr += '<td align="center"  style="text-align:center;width:90px;">';
		htmlStr += '<input  name="is_query" type="checkbox" onclick="selectQuery()"/>';
		htmlStr += '</td>';

		htmlStr += '<td align="center"  style="text-align:center;width:90px;">';
		htmlStr += '<input  name="is_result" type="checkbox" onclick="selectResult()"/>';
		htmlStr += '</td>';

		htmlStr += '<td>';
		htmlStr += '<ul class="optUL">';
		htmlStr += '<li class="ico_up" title="上移" onclick="moveUp(this)"></li>';
		htmlStr += '<li class="ico_down" title="下移" onclick="moveDown(this)"></li>';
		htmlStr += '<li class="ico_delete" title="删除" onclick="deleteTr(this)"></li>';
		htmlStr += '</ul>';
		htmlStr += '</td>';
		htmlStr += '</tr>';;
		$("#from_table").append(htmlStr);
		init_input();
		resetNum();
}

function setDictList(dictList)
{	
	if(dictList != null && dictList.size() > 0){
		var str = "";
		    num = dictList.size();
		for(var i=1; i<= dictList.size(); i++)
		{
			var is_se ="";
			var is_qu ="";
			var is_re ="";

			if(dictList.get(i-1).is_selected == 1){
			 	is_se =" checked ";
			}
			if(dictList.get(i-1).is_query == 1){
				is_qu =" checked ";
			}
			if(dictList.get(i-1).is_result == 1){
				is_re =" checked ";
			}
			str += '<tr>'
				+'<td class="width40">'
				+'<span class="c_index">'+(i)+'：</span>'
				+'</td>'
				
				+'<td class="width250">'
				+'<input type="text" id="field_cname_'+i+'" name="field_cname" class="width250"  value="'+dictList.get(i-1).field_cname+'" onblur="checkInputValue(\'field_cname_'+i+'\',false,80,\'中文名称\',\'\')"/>'
				+'</td>'
				
				+'<td align="center" style="text-align:center;width:90px;">'
				+'<input name="is_selected" type="checkbox" '+is_se+' onclick="selectSelected()"/>'
				+'</td>'

				+'<td align="center" style="text-align:center;width:90px;">'
				+'<input name="is_query" type="checkbox"  '+is_qu+' onclick="selectQuery()"/>'
				+'</td>'

				+'<td align="center" style="text-align:center;width:90px;">'
				+'<input name="is_result" type="checkbox"  '+is_re+' onclick="selectResult()"/>'
				+'</td>'

				+'<td>'
				+'<ul class="optUL">'
				+'<li class="ico_up" title="上移" onclick="moveUp(this)"></li>'
				+'<li class="ico_down" title="下移" onclick="moveDown(this)"></li>'
				+'<li class="ico_delete" title="删除" onclick="deleteTr(this)"></li>'
				+'</ul>'
				+'</td>'
				+'</tr>';
		}
		$("#from_table").html(str);
		init_input();
		resetNum();
	}	
}

//删除
function deleteTr(obj){
	$(obj).remove();
	resetNum();
}

function moveUp(o){
	$(o).parent().parent().parent().insertBefore($(o).parent().parent().parent().prev());
	resetNum();
}

function moveDown(o){
	$(o).parent().parent().parent().insertAfter($(o).parent().parent().parent().next());
	resetNum();
}

function deleteTr(o){
	$(o).parent().parent().parent().remove();
	resetNum();
}

function selectSelected(){
    $("#from_table :checkbox[name='is_selected'][checked]").each(function(i){
			$(this).attr("checked","checked");
	});
}
function selectQuery(){
	
	$("#from_table :checkbox[name='is_query'][checked]").each(function(i){
			$(this).attr("checked","checked");
	});
}
function selectResult(){
	$("#from_table :checkbox[name='is_result'][checked]").each(function(i){
			$(this).attr("checked","checked");
	});
}

//重排序号
function resetNum(){
	var ic = 1;
	$("#from_table tr").each(function(){
		$("#from_table tr li[title='上移']").addClass("ico_up");
		$("#from_table tr li[title='下移']").addClass("ico_down");
		$(this).find(".c_index").text((ic++) + "：");
	});
	$("#from_table tr .ico_up").first().removeClass("ico_up");
	$("#from_table tr .ico_down").last().removeClass("ico_down");
}

function insertQueryDicBean()
{
	var list = new List();
	var e_name_map = new Map();
	var is_checked = true;
	if(!standard_checkInputInfo("from_table"))
	{
		return;
	}
	$("#from_table tr").each(function(i){
		var qdc = BeanUtil.getCopy(QueryDicBean);
			qdc.dic_id = i+1;
			qdc.conf_id = conf_id;
			qdc.sort_id = i+1;

		var ename = $(this).find("input[name='field_cname']").val();
		if(e_name_map.containsKey(ename))
		{
			jQuery.simpleTips("field_ename_"+i,"名称　"+ename+" 的数据已经存在",2000);
			is_checked = false;
			return false;
		}
		e_name_map.put(ename,"");

		qdc.field_cname =$(this).find("input[name='field_cname']").val();
		qdc.is_selected =($(this).find(":checkbox[name='is_selected'][checked]").is(':checked') ? "1" : "0");
		qdc.is_query =($(this).find(":checkbox[name='is_query'][checked]").is(':checked') ? "1" : "0");
		qdc.is_result =($(this).find(":checkbox[name='is_result'][checked]").is(':checked') ? "1" : "0");
	
		list.add(qdc);		
	});
	if(is_checked)
	{
		if(QueryDicRPC.insertQueryDicBean(conf_id,list))
		{
			top.msgAlert("字段"+WCMLang.Add_success);
			window.history.go(-1);
		}else
		{
			top.msgWargin("字段"+WCMLang.Add_fail);
		}
	}
}
</script>
</head>
<body>
<span class="blank6"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<td align="right" class="fromTabs width100" style="">
				<input id="btnAdd" name="btnAdd" type="button"
				onclick="javascript:addDict();" value="添加字段项" />
				<span class="blank3"></span>
			</td>			
		</tr>		
	</tbody>
</table>
<div class="textLeft" style="padding-left:10px;">
	<table id="add_zbstep_table" class="table_option" style="width:680px;margin:0px 0px;" border="0" cellpadding="0" cellspacing="0">
		<span class="blank3"></span>
		<tr>
			<td class="width40" style="height:18px;">
				序号
			</td>
			<td class="width250" style="height:18px;width:250px;">
				中文名称
			</td>
			<td class="width90" style="height:18px;width:90px;text-align:center;">
				是否必选项
			</td>
			<td class="width90" style="height:18px;width:90px;text-align:center;">
				是否查询项
			</td>
			<td class="width90" style="height:18px;width:90px;text-align:center;">
				是否结果项
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
		</tr>
	</table>
	<table id="from_table" class="table_option" style="width:680px;margin:0px 0px;" border="0" cellpadding="0"
		cellspacing="0">
	</table>
</div>
<!--隔线开始-->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!--隔线结束-->
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />
			<input id="userAddReset" name="btn1" type="button" onclick="window.location.reload()" value="重置" />
			<input id="btn2" name="btn2" type="button" onclick="window.history.go(-1)" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
