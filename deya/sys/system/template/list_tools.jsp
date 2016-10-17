<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表生成工具</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/tem_tools.js"></script>
<script type="text/javascript" src="js/create_list.js"></script>
<script type="text/javascript">

var site_id = top.current_site_id;
var app_id = "cms";

$(document).ready(function(){
	initButtomStyle();
	init_input();	
	
	setModelInfoList();
	setFomatDate();
	getItemByAppID();
	iniOpt();

});

function changeApp(val)
{	
	app_id = val;
	$("#weight_tr").hide();	
	$("#order_name1_tr").hide();
	$("#intro_count_tr").hide();	
	$("#status_tr").hide();
	$("#order_name").empty();	
	if(val == "cms" || val == "zwgk" || val == "ggfw")
	{
		$("#model_id").parent().parent().find("th").text("内容模型：");	
		$("#weight_tr").show();
		$("#order_name1_tr").show();
		setModelInfoList();		
				
		$("#order_name").addOptionsSingl("ci.released_dtime","发布时间");
		$("#order_name").addOptionsSingl("ci.hits","总点击数");
		$("#order_name").addOptionsSingl("ci.day_hits","日点击数");
		$("#order_name").addOptionsSingl("ci.week_hits","周点击数");
		$("#order_name").addOptionsSingl("ci.month_hits","月点击数");
	}
	if(val == "appeal")
	{
		$("#model_id").parent().parent().find("th").text("诉求业务：");	
		setSQModelList();		
		$("#order_name").addOptionsSingl("sq_dtime","提交时间");
		$("#order_name").addOptionsSingl("sq_id","信件ID");
	}
	if(val == "interview")
	{
		$("#model_id").parent().parent().find("th").text("访谈分类：");	
		$("#status_tr").show();
		$("#intro_count_tr").show();
		setInterviewSubCategory();
		$("#order_name").addOptionsSingl("sub.id","访谈ID");
		$("#order_name").addOptionsSingl("sub.start_time","访谈时间");
	}
	if(val == "survey")
	{		
		$("#model_id").parent().parent().find("th").text("调查分类：");	
		$("#intro_count_tr").show();
		setSurveyCategory();
		$("#order_name").addOptionsSingl("sub.id","调查ID");
		$("#order_name").addOptionsSingl("sub.start_time","调查时间");
	}
	getItemByAppID();
}

//访谈分类
function setInterviewSubCategory()
{
	$("#model_id").empty();
	var list = jsonrpc.SubjectRPC.getSubCategoryAllName(site_id);
	list = List.toJSList(list);	
	$("#model_id").addOptionsSingl("","　　");	
	$("#model_id").addOptions(list,"category_id","category_name");
}

//调查分类
function setSurveyCategory()
{
	$("#model_id").empty();
	var list = jsonrpc.SurveyCategoryRPC.getAllSurveyCategoryName(site_id);
	list = List.toJSList(list);	
	$("#model_id").addOptionsSingl("","　　");
	$("#model_id").addOptions(list,"category_id","c_name");
}

</script>
<style>
#format_selectList li{cursor:default;width:150px;margin:3px 0}
</style>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="Template_table" class="" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
		  <td width="200px" valign="top">
			<table id="Template_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<th>所属应用：</th>
						<td >
							<select id="app_id" onchange="changeApp(this.value)">
								<option value="cms" selected="true">内容管理</option>
								<option value="zwgk">信息公开</option>
								<option value="ggfw">公共服务</option>
								<option value="appeal">诉求系统</option>
								<option value="interview">访谈系统</option>
								<option value="survey">问卷调查</option>
							</select>
						</td>
					</tr>	
					<tr id="model_id_tr">
						<th>内容模型：</th>
						<td >
							<select id="model_id" style="width:140px">
								<option value="">全部　　</option>
							</select>
						</td>
					</tr>					
					<tr>
						<th>显示条数：</th>
						<td >
							<input type="text" id="page_size" name="page_size" class="width40"  value="15" maxlength="3" onkeypress="var k=event.keyCode; return k>=48&&k<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="ime-mode:Disabled"/>
						</td>
					</tr>
					<tr>
						<th>标题字数：</th>
						<td >
							<input type="text" id="title_count" name="title_count" class="width40"  value="28" maxlength="3" onkeypress="var k=event.keyCode; return k>=48&&k<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="ime-mode:Disabled"/>
						</td>
					</tr>
					<tr id="intro_count_tr" class="hidden">
						<th>简介字数：</th>
						<td >
							<input type="text" id="intro_count" name="intro_count" class="width40"  value="80" maxlength="3" onkeypress="var k=event.keyCode; return k>=48&&k<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="ime-mode:Disabled"/>
						</td>
					</tr>
					<tr id="status_tr" class="hidden">
						<th>访谈状态：</th>
						<td >
							<select id="status" >
								<option value="" selected="true"></option>
								<option value="0">历史状态</option>
								<option value="1">直播状态</option>
								<option value="2">历史状态</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>日期格式：</th>
						<td >
							<div id="a11">
								<input type="text" id="format_date" style="width:136px; height:18px; overflow:hidden;" value="yyyy-MM-dd"/>
								<div id="format_select" class="select_div tip hidden border_color selectDiv" style="width:134px; height:30px; overflow:hidden; " >
									<div id="leftMenuBox">
										<ul id="format_selectList" class="listLi"  style="width:134px; height:30px; text-align: left;">
											<li onclick="$('#format_date').val('yyyy-MM-dd hh:mm')" >yyyy-MM-dd hh:mm</li>
											<li onclick="$('#format_date').val('yyyy-MM-dd')" >yyyy-MM-dd</li>
											<li onclick="$('#format_date').val('MM-dd hh:mm')" >MM-dd hh:mm</li>
											<li onclick="$('#format_date').val('MM-dd')" >MM-dd</li>
											<li onclick="$('#format_date').val('MM/dd hh:mm')" >MM/dd hh:mm</li>
										</ul>
									</div>
								</div>
							</div>
						</td>
					</tr>
					<tr id="weight_tr">
						<th>权重：</th>
						<td >
							<input type="text" id="weight" name="weight" class="width30"  maxlength="3" onblur="checkInputValue('weight',true,2,'权重','checkNumber')"/><span id="weight_span" class="hidden">－<input type="text" id="weight_end" name="weight_end" class="width30" maxlength="3" onblur="checkInputValue('weight_end',true,2,'权重','checkNumber')"/></span>&nbsp;<input type="checkbox" id="weight_fw" onclick="changeWeightSpan(this)"><label>范围</label>
						</td>
					</tr>
					<tr>
						<th>排序方式：</th>
						<td >
							<select id="order_name">								
								<option value="ci.released_dtime">发布时间</option>
								<option value="ci.hits">总点击数</option>
								<option value="ci.day_hits">日点击数</option>
								<option value="ci.week_hits">周点击数</option>
								<option value="ci.month_hits">月点击数</option>
							</select>
							<select id="order_type">
								<option value="asc">升序</option>
								<option value="desc" selected="true">降序</option>								
							</select>
						</td>
					</tr>
					<tr id="order_name1_tr">
						<th></th>
						<td >
							<select id="order_name1">
								<option value=""></option>
								<option value="ci.released_dtime">发布时间</option>
								<option value="ci.hits">总点击数</option>
								<option value="ci.day_hits">日点击数</option>
								<option value="ci.week_hits">周点击数</option>
								<option value="ci.month_hits">月点击数</option>
							</select>
							<select id="order_type1">
								<option value="asc">升序</option>
								<option value="desc" selected="true">降序</option>								
							</select>
						</td>
					</tr>
				</tbody>
			</table>
		  </td>
		  <td width="360px">
			<table id="Template_table" class="" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td width="150px">
							<div style="width:150px;height:312px;overflow:auto;" class="border_color">
								<ul id="all_item_list" style="margin:10px">
								</ul>
							 </div>
						</td>
						<td width="10px"></td>
						<td width="200px">
							<div style="width:200px;height:312px;overflow:auto;" class="border_color">
								<ul id="select_item_list" style="margin:10px">
								</ul>
							 </div>
						</td>
					</tr>
				</tbody>
			</table>
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
			<input id="addButton" name="btn1" type="button" onclick="saveCreateList()" value="插入" />	
			<input id="userAddReset" name="btn1" type="button" onclick="resetForm()" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
