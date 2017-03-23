<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String model_id = request.getParameter("model_id");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>业务管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript">
var model_id = <%=model_id%>;
var defaultBean;
var num = 0;
var SQModelRPC = jsonrpc.SQModelRPC;
var CPFrom = new Bean("com.deya.wcm.bean.appeal.model.CPFrom",true);
var val=new Validator();

$(document).ready(function(){
	initButtomStyle();
		
	if(model_id != "" && model_id != "null" && model_id != null)
	{		
		var dictList = SQModelRPC.getCPFormListByModel(model_id);
		dictList = List.toJSList(dictList);//把list转成JS的List对象
		if(dictList != null && dictList.size() > 0)
			setDictList(dictList);
		else
			addDict();
		$("#addButton").click(insertCPFrom);		
	}	
});

function addDict(){	
	var htmlStr = '<tr>';
	htmlStr += '<td class="width40">';
	htmlStr += '<span class="c_index">'+(++num)+'：</span>';
	htmlStr += '</td>';
	htmlStr += '<td class="width200">';
	htmlStr += '<input id="field_ename_'+num+'" name="field_ename" class="width200" maxlength="10" onblur="checkInputValue(\'field_ename_'+num+'\',false,20,\'英文名称\',\'checkLetter\')"/>';
	htmlStr += '</td>';
	htmlStr += '<td class="width200">';
	htmlStr += '<input id="field_cname_'+num+'" name="field_cname" class="width200" onblur="checkInputValue(\'field_cname_'+num+'\',false,20,\'中文名称\',\'\')"/>';
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

function setDictList(dictList){
		
	if(dictList != null && dictList.size() > 0){
		var str = "";
		num = dictList.size();
		for(var i=1; i<=dictList.size(); i++){			
			str += '<tr>'
				+'<td class="width40">'
				+'<span class="c_index">'+(i)+'：</span>'
				+'</td>'
				+'<td class="width200">'
				+'<input type="text" id="field_ename_'+i+'" name="field_ename" class="width200" value="'+dictList.get(i-1).field_ename+'" maxlength="10" onblur="checkInputValue(\'field_ename_'+i+'\',false,20,\'英文名称\',\'checkLetter\')"/>'
				+'</td>'
				+'<td class="width200">'
				+'<input type="text" id="field_cname_'+i+'" name="field_cname" class="width200"  value="'+dictList.get(i-1).field_cname+'" onblur="checkInputValue(\'field_cname_'+i+'\',false,20,\'中文名称\',\'\')"/>'
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

function insertCPFrom()
{
	var list = new List();
	var e_name_map = new Map();
	var is_checked = true;
	if(!standard_checkInputInfo("from_table"))
	{
		return;
	}
	$("#from_table tr").each(function(i){
		var cf = BeanUtil.getCopy(CPFrom);
		cf.field_id = i+1;
		cf.model_id = model_id;
		var ename = $(this).find("input[name='field_ename']").val();
		if(e_name_map.containsKey(ename))
		{
			jQuery.simpleTips("field_ename_"+i,"英文名称　"+ename+" 的数据已经存在",2000);
			is_checked = false;
			return false;
		}
		e_name_map.put(ename,"");
		cf.field_ename = ename;
		cf.field_cname = $(this).find("input[name='field_cname']").val();
		
		list.add(cf);		
	});
	if(is_checked)
	{
		if(SQModelRPC.insertCPFrom(model_id,list))
		{
			msgAlert("扩展字段"+WCMLang.Add_success);
			window.history.go(-1);
		}else
		{
			msgWargin("扩展字段"+WCMLang.Add_fail);
		}
	}
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<td align="right" class="fromTabs width100" style="">
				<input id="btnAdd" name="btnAdd" type="button"
					onclick="javascript:addDict();" value="添加数据项" />
				<span class="blank3"></span>
			</td>			
		</tr>		
	</tbody>
</table>
<div class="textLeft" style="padding-left: 10px;">
	<table id="add_zbstep_table" class="table_option"
		style="width: 500px; margin: 0 0;" border="0" cellpadding="0"
		cellspacing="0">
		<span class="blank3"></span>
		<tr>
			<td class="width40" style="height:18px;">
				序号
			</td>
			<td class="width200"  style="height:18px; padding-left:0px; width:205px;">
				英文名称
			</td>
			<td class="width100" style="height:18px; padding-left:5px; width:205px;">
				中文名称
			</td>			
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
	</table>
	<table id="from_table" class="table_option"
		style="width: 600px; margin: 0 0;" border="0" cellpadding="0"
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
