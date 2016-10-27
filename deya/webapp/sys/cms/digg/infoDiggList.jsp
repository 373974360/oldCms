<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%
String siteid = request.getParameter("site_id");
String app_id = request.getParameter("app_id");
if(app_id == null || app_id.trim().equals("")){
	app_id = "cms";
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//CN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>DIGG统计</title>



<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/infoDiggList.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript">


var site_id = "<%=siteid%>";
var app = "<%=app_id%>";
var cat_jdata; 
var selected_cat_ids = ""; // 选中的

$(document).ready(function(){

	initButtomStyle();
	init_input();
	
	showSelectDiv("cat_tree","cat_tree_div1",300, "leftMenuTree");
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initSearchPara();
	
	initTable();
	reloadDiggList();
});

function getCatAndModelInfo()
{
	model_lt = jsonrpc.ModelRPC.getCANModelListByAppID(app);
	model_lt = List.toJSList(model_lt);
	
	cat_lt = jsonrpc.CategoryRPC.getCategoryListBySiteID(site_id);
	cat_lt = List.toJSList(cat_lt);
	
	cat_jdata = eval(jsonrpc.CategoryRPC.getCategoryTreeBySiteID(site_id));
}

function initSearchPara()
{
	getCatAndModelInfo();
	
	setLeftMenuTreeJsonData(cat_jdata);
	addtreeEvent();
	
	for(var i=0; i<model_lt.size(); i++)
	{
		$("#sel_model").append("<option value=\""+model_lt.get(i).model_id+"\">"+model_lt.get(i).model_name+"</option> ");	
	}
}

function addtreeEvent()
{
	$('#leftMenuTree').tree({
		//url: 'data/tree_data_tongji.json',
		//url: jsonData,
		onClick:function(node){
			$("#cat_tree").val(node.text);
			selected_cat_ids = "" +node.id;     
		}
	});
}

</script>
</head>
<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>	
		<td align="left" class="fromTabs width180" style="">
			<input id="btn1" name="btn1" type="button" onclick="searchBySupposts()" value="顶最多" />
			<input id="btn2" name="btn2" type="button" onclick="searchByAgainsts()" value="踩最多" />
			<input id="btn3" name="btn3" type="button" onclick="clearPara()" value="刷新" />
		<span class="blank3"></span>
		</td>
		<td align="right" valign="middle" class="fromTabs">
			检索
			<input id="start_day" type="text" style="height:18px" class="Wdate" size="11" value="" 
				onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" />至
			<input id="end_day" type="text" style="height:18px" class="Wdate" size="11" value="" 
				onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
			<span>栏目：</span>
			<!--<select id="sel_cate" class="input_select">
				<option selected="selected" value="">ALL</option>
			</select>-->
				<input type="text" id="cat_tree" value="" style="width:150px; height:18px; overflow:hidden;" readonly="readonly"/>
				<div id="cat_tree_div1" class="select_div tip hidden border_color" style="width:150px; height:300px; overflow:hidden;border:1px #7f9db9 solid;" >
					<div id="leftMenuBox">
						<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
							<ul id="leftMenuTree" class="easyui-tree" animate="true" style="width:176px; height:280px;">
							</ul>
						</div>
					</div>
				</div>
			<span>类型：</span>
			<select id="sel_model" class="input_select">
				<option selected="selected" value="">全部</option>
			</select>
			<input id="btnSearch" type="button" value="搜索" onclick="searchList()"/>
			<span class="blank3"></span>
		</td>		
	</tr>
</table>
<span class="blank3"></span>
</div>
<div id="infoDigg_table"></div>
<div id="turn"></div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>	<!--
		<td align="left" valign="middle" id="dept_search" class="search_td" >
			<input id="btnAddCateClass" name="btnAddCateClass" type="button" onclick="addCateClass()" value="新建分类方法" />
			<input id="btnUpdateCateClass" 
				name="btnUpdateCateClass" type="button" onclick="updateRecord( table, 'class_id', 'updateCateClass()')" value="修改" />
					
			<input id="btnDeleteCateClass" 
				name="btnDeleteCateClass" type="button" onclick="publicSelectSinglCheckbox( table, 'class_id', 'deleteCateClass()')" value="删除" /><span class="blank3"></span>
		</td>		
		-->
	</tr>
</table>
</div>
</body>
</html>