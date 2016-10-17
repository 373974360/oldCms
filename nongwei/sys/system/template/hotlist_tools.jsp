<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表生成工具</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/tem_tools.js"></script>
<script type="text/javascript" src="js/hot_list.js"></script>
<script type="text/javascript">

var site_id = top.current_site_id;
var app_id = "cms";
var CategoryRPC = jsonrpc.CategoryRPC;
var GKNodeRPC = jsonrpc.GKNodeRPC;

$(document).ready(function(){
	initButtomStyle();
	init_input();	
	init_FromTabsStyle();
	
	setModelInfoList();
	setFomatDate();
	getItemByAppID();
	iniOpt();
	initMenuTree2();
	changeCategoryTree("cms");
});

function changeApp(val)
{	
	app_id = val;
	$("#weight_tr").hide();	
	$("#order_name1_tr").hide();
	$("#model_id_tr").hide();
	$("#intro_count_tr").hide();	
	$("#status_tr").hide();
	$("#order_name").empty();	
	if(val == "cms" || val == "zwgk" || val == "ggfw")
	{
		$("#model_id").parent().parent().find("th").text("内容模型：");	
		$("#weight_tr").show();
		$("#order_name1_tr").show();
		setModelInfoList();
		$(".tab_right").eq(1).text("目录");
		$("#model_id_tr").show();		
		$("#order_name").addOptionsSingl("ci.released_dtime","发布时间");
		$("#order_name").addOptionsSingl("ci.hits","总点击数");
		$("#order_name").addOptionsSingl("ci.day_hits","日点击数");
		$("#order_name").addOptionsSingl("ci.week_hits","周点击数");
		$("#order_name").addOptionsSingl("ci.month_hits","月点击数");
	}
	if(val == "appeal")
	{		
		$("#model_id option").eq(0).attr("selected","true");
		$(".tab_right").eq(1).text("诉求业务");
		$("#order_name").addOptionsSingl("sq_dtime","提交时间");
		$("#order_name").addOptionsSingl("sq_id","信件ID");
		$("#cat_tree_div1").css("width","600px");					
		$("#cat_tree_div2").hide();
		
	}
	if(val == "interview")
	{
		$("#model_id option").eq(0).attr("selected","true");
		$("#status_tr").show();
		$("#intro_count_tr").show();
		$("#order_name").addOptionsSingl("sub.id","访谈ID");
		$("#order_name").addOptionsSingl("sub.start_time","访谈时间");
		$("#cat_tree_div1").css("width","600px");					
		$("#cat_tree_div2").hide();
		$(".tab_right").eq(1).text("访谈分类");
	}
	if(val == "survey")
	{		
		$("#model_id option").eq(0).attr("selected","true");
		$("#intro_count_tr").show();
		$("#order_name").addOptionsSingl("sub.id","调查ID");
		$("#order_name").addOptionsSingl("sub.start_time","调查时间");
		$(".tab_right").eq(1).text("调查分类");
	}
	getItemByAppID();
	changeCategoryTree(val);//得到栏目树
}

//初始树点击事件
function initMenuTree(div_name)
{
	$("#"+div_name+" .tree-icon").after('<SPAN class="tree-checkbox tree-checkbox0"></SPAN>');
	$("#"+div_name+" .tree-checkbox").click(function(){
		if($(this).attr("class").indexOf("tree-checkbox0") > -1)
		{
			$(this).parent().parent().find(".tree-checkbox").removeClass("tree-checkbox0");
			$(this).parent().parent().find(".tree-checkbox").removeClass("tree-checkbox1");
			$(this).parent().parent().find(".tree-checkbox").addClass("tree-checkbox3");

			$(this).removeClass("tree-checkbox3");
			$(this).addClass("tree-checkbox1");
		}
		else
		{
			if($(this).attr("class").indexOf("tree-checkbox1") > -1)
			{
				$(this).parent().parent().find(".tree-checkbox").removeClass("tree-checkbox3");		
				$(this).parent().parent().find(".tree-checkbox").addClass("tree-checkbox0");

				$(this).removeClass("tree-checkbox1");
				$(this).addClass("tree-checkbox0");
			}
		}
	});
}

function initMenuTree2()
{
	$('#leftMenuTree').tree({
		onClick:function(node){
			if(node.iconCls == "icon-gknode")
				clickZWGKTreeEvent(node);
		}
	});	
}

function changeCategoryTree(val)
{
	var cat_jdata = "";	
	$("#leftMenuTree").empty();
	$("#is_show_thumb_tr").hide();
	switch(val)
	{		
		case "cms" :$("#cat_tree_div1").css("width","600px");
					$("#cat_tree_div2").hide();					
					cat_jdata = eval(CategoryRPC.getCategoryTreeBySiteID(site_id)); 
					cat_jdata = getTreeObjFirstNode(cat_jdata);
					$("#page_type_td").show();
					$("#tree_table").show();
					setLeftMenuTreeJsonData(cat_jdata);
					initMenuTree("leftMenuTree");
					$("#is_show_thumb_tr").show();
					break;
		case "ggfw" :$("#cat_tree_div1").css("width","600px");
					$("#cat_tree_div2").hide();		
					cat_jdata = eval(CategoryRPC.getAllFWTreeJSONStr());
					$("#page_type_td").show();
					$("#tree_table").show();
					setLeftMenuTreeJsonData(cat_jdata);
					initMenuTree("leftMenuTree");
					$("#is_show_thumb_tr").show();
					break;
		case "zwgk" :$("#cat_tree_div1").css("width","300px");					
					$("#cat_tree_div2").show();
					cat_jdata = eval(GKNodeRPC.getGKNodeTreebyCateID(0));					
					setLeftMenuTreeJsonData(cat_jdata);					
					break;
		case "appeal" :$("#cat_tree_div1").css("width","600px");					
					$("#cat_tree_div2").hide();
					setSQModelList();					
					break;
		case "interview" :$("#cat_tree_div1").css("width","600px");					
					$("#cat_tree_div2").hide();
					setInterviewSubCategory();					
					break;
		case "survey" :$("#cat_tree_div1").css("width","600px");					
					$("#cat_tree_div2").hide();
					setSurveyCategory();					
					break;
	}
	
}

//得到树中的第一个节点的所有子节点,为了不显示根节点
function getTreeObjFirstNode(cat_jdata)
{
	if(cat_jdata != null)
	{
		return cat_jdata[0].children;
	}
}

//点击树事件
function clickZWGKTreeEvent(node)
{
	var type = $("#app_id :selected").val();
	if(type == "zwgk")
	{
		var cat_jdata = eval(CategoryRPC.getCategoryTreeBySiteID(node.attributes.t_node_id));
		cat_jdata = getTreeObjFirstNode(cat_jdata);
		$("#leftMenuTree2").empty();
		setAppointTreeJsonData("leftMenuTree2",cat_jdata);
		initMenuTree("leftMenuTree2");
	}
}

</script>
<style>
#format_selectList li{cursor:default;width:150px;margin:3px 0}
</style>
</head>

<body>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" align="left">
	<tr>
		<td align="left" class="fromTabs width10" style="">				
			<span class="blank3"></span>
		</td>
		<td align="left" width="100%">
			<ul class="fromTabs">
				<li class="list_tab list_tab_cur">
					<div class="tab_left">
						<div class="tab_right">属性</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right">目录</div>
					</div>
				</li>				
			</ul>
		</td> 	
		
	</tr>
</table>
<span class="blank3"></span>
<form id="form1" name="form1" action="#" method="post">
<div class="infoListTable" id="listTable_0">
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
							<input type="text" id="page_size" name="page_size" class="width40"  value="5" maxlength="3" onkeypress="var k=event.keyCode; return k>=48&&k<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="ime-mode:Disabled"/>
						</td>
					</tr>
					<tr id="is_show_thumb_tr">
						<th>展示缩略图：</th>
						<td >
							<input type="checkbox" id="is_show_thumb" name="is_show_thumb" />
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
</div>
<div class="infoListTable hidden" id="listTable_1">
<table id="tree_table" class="" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
		 <td style="text-align:center" align="center">
			<div id="cat_tree_div1" class="border_color" style="margin:0 auto;width:300px; height:312px; overflow:hidden;" >
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
						<ul id="leftMenuTree" class="easyui-tree" animate="true" style="width:382px; height:312px;">
						</ul>
					</div>
				</div>
			</div>
		 </td>
		 <td class="width10"></td>
		 <td style="text-align:center" align="center">
			<div id="cat_tree_div2" class="border_color hidden" style="margin:0 auto;width:300px; height:312px; overflow:hidden;" >
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
						<ul id="leftMenuTree2" class="easyui-tree" animate="true" style="width:382px; height:312px;">
						</ul>
					</div>
				</div>
			</div>
		 </td>
		</tr>
	</tbody>
</table>
</div>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="saveHotList()" value="插入" />	
			<input id="userAddReset" name="btn1" type="button" onclick="resetForm()" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
