<%@ page contentType="text/html; charset=utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>诉求满意度指标配置</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />




<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/satisfactionList.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	initButtomStyle();
	init_input();
	iniOpt();
	reloadSatisfactionList();
});
function addStep()
{	
	 	var allNum=100; 
	    $(".valueWidth").each(function(){
			 allNum = allNum - parseInt($(this).val())
		});
		if(allNum <0){
			alert("分值合计已超过100,请修改！");
			return;
		}
	    var trLength = $("#add_zbstep_table tr").length+1;
		var tmpTR = "";
		tmpTR  = "<tr>"
		tmpTR +="	<td><span class=\"c_index\">第"+trLength+"项：</span></td>"
		tmpTR +="	<td><input id=\"sat_item"+trLength+"\" name=\"sat_item"+trLength+"\" type=\"text\" class=\"itemWidth\" value=\"选项名称\" /></td>"
		tmpTR +="	<td>分值：</td>"
		tmpTR +="	<td><input id=\"sat_score"+trLength+"\" name=\"sat_score"+trLength+"\" type=\"text\" class=\"valueWidth\" value=\""+allNum+"\" /></td>"
		tmpTR +="	<td>"
		tmpTR +="		<ul class=\"optUL\">"
		tmpTR +="			<li class=\"opt_up ico_up\" title=\"上移\"></li>"
		tmpTR +="			<li class=\"opt_down ico_down\" title=\"下移\"></li>"
		tmpTR +="			<li class=\"opt_delete ico_delete\" title=\"删除\"></li>"
		tmpTR +="		</ul>"
		tmpTR +="	</td>"
		tmpTR +="</tr>"

		$("#add_zbstep_table").append(tmpTR);
		init_input();
		resetNum();
}
//重排序号
function resetNum()
{
	var ic = 1;
	$("#add_zbstep_table tr").each(function(){
		$(this).find(".c_index").text("第" + (ic++) + "项：");
	});
	$("#add_zbstep_table tr").find(".opt_up").addClass("ico_up");
	$("#add_zbstep_table tr").find(".opt_down").addClass("ico_down");
	
	//首行、未行去除移动图标
	$("#add_zbstep_table tr").first().find(".opt_up").removeClass("ico_up");
	$("#add_zbstep_table tr").last().find(".opt_down").removeClass("ico_down");

}
//删除
function deleteTr(obj)
{
	$(obj).remove();
	resetNum();
}
//上移
function moveUpTr(obj)
{
	if($(obj).index==0) return;
	$($(obj).prev()).before($(obj));
	resetNum();
}
//下移
function moveDownTr(obj)
{
	if($(obj).index==$("#add_zbstep_table tr").length-1) return;
	$($(obj).next()).after($(obj));
	resetNum();
}
//上、下移、删除事件绑定
function iniOpt()
{
	$(".opt_up").live("click",function(){
		var tmpObj = $(this).parent().parent().parent();
		moveUpTr(tmpObj);
	});
	$(".opt_down").live("click",function(){
		var tmpObj = $(this).parent().parent().parent();
		moveDownTr(tmpObj);
	});
	$(".opt_delete").live("click",function(){
		var tmpObj = $(this).parent().parent().parent();
		deleteTr(tmpObj);
	});	
}
function saveAndCheck(){
	var total=0;
	$(".valueWidth").each(function(){
		total +=  parseInt($(this).val())
	});
	if(total > 100)
	{
		alert("分值合计不能超过100,请修改！");
		return;
	}else if(total < 100){
		alert("分值合计不足100,请修改！");
		return;
	}else{
		addSatisfaction();
	}
}
</script>
<style>
.itemWidth{ width:280px;}
.valueWidth{ width:50px;}
</style>
</head>
<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right" class="fromTabs width100" style="">
			<input id="btnAdd" name="btnAdd" type="button" onclick="javascript:addStep();" value="添加选项" />
		<span class="blank3"></span>
		</td>
		<td align="left" valign="middle" class="fromTabs textIndent2em">
			注意：分值合计必须等于100
			<span class="blank3"></span>
		</td>
	</tr>
</table>
</div>
<span class="blank3"></span>
<div class="textLeft" style="padding-left:80px;">
<table id="add_zbstep_table" class="table_option" style="width:500px; margin:0 0;" border="0" cellpadding="0" cellspacing="0" >
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
			<input id="btn1" name="btn1" type="button" onclick="saveAndCheck()" value="保存" />
			<input id="btn3" name="btn3" type="button" onclick="locationSatisfaction();" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
