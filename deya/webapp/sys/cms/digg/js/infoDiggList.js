var DiggRPC = jsonrpc.DiggRPC;
var InfoDiggBean = new Bean("com.deya.wcm.bean.cms.digg.InfoDiggBean",true);

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
var con_m = new Map();
var model_lt;
var cat_lt;
table.table_name = "infoDigg_table_list";

// 初始化Table
function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("title","标题","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("supports","顶","30px","","",""));
	colsList.add(setTitleClos("againsts","踩","30px","","",""));
	colsList.add(setTitleClos("cat_id","栏目","120px","","",""));
	colsList.add(setTitleClos("model_id","类型","50px","","",""));
	colsList.add(setTitleClos("released_dtime","发布时间","120px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("infoDigg_table");//里面参数为外层div的id
}

// 加载初始化Table和翻页信息
function reloadDiggList()
{
	con_m.put("site_id",site_id);
	showList();
	showTurnPage();
}

// 填充Table数据
function showList()
{
	var sortCol = table.sortCol;
	var sortType = table.sortType;	
	
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "id";
		sortType = "desc";
	}
	con_m.put("sortCol", sortCol);
	con_m.put("sortType", sortType);
	con_m.put("start_num", tp.getStart());	
	con_m.put("page_size", tp.pageSize);
	
	var beanList = DiggRPC.getInfoDiggList(con_m);
	tp.total = DiggRPC.getInfoDiggCnt(con_m);
	
	beanList =List.toJSList(beanList);
	
	table.setBeanList(beanList,"td_list");
	table.show("infoDigg_table");
	// 类型替换
	table.getCol("model_id").each(function(i){
		if(i>0)
		{
			var tem_model_id = beanList.get(i-1).model_id;
			
			for(var n = 0; n < model_lt.size(); n++)
			{
				if(tem_model_id == model_lt.get(n).model_id)
				{
					$(this).text(model_lt.get(n).model_name);
				}
			}
		}
	});
	
	// 栏目替换
	table.getCol("cat_id").each(function(i){
		if(i>0)
		{
			var tem_cat_id = beanList.get(i-1).cat_id;
			var cat_bean = jsonrpc.CategoryRPC.getCategoryBean(tem_cat_id);
			$(this).text(cat_bean.cat_cname);
		}
	});
	Init_InfoTable(table.table_name);
}

// 添加翻页信息
function showTurnPage()
{
	tp.show("turn","");	
	tp.onPageChange = showList;
}

// 顶最多
function searchBySupposts()
{
	table.sortCol = "supports";
	table.sortType = "desc";
	reloadDiggList();
}

// 踩最多
function searchByAgainsts()
{
	table.sortCol = "againsts";
	table.sortType = "desc";
	reloadDiggList();
}

// 刷新操作
function clearPara()
{
	table.sortCol = "";
	table.sortType = "";
	con_m.clear();
	reloadDiggList();
}

// 搜索操作
function searchList()
{
	con_m.clear();
	var start_day = $("#start_day").val();
	var end_day = $("#end_day").val();
	
	var sel_model = $("#sel_model").val(); // 模型

	if(start_day > end_day)
	{
		msgAlert("开始日期不能大于结束日期");
		return;
	}
	if(start_day != "")
	{
		con_m.put("start_day",start_day+" 00:00:00");
	}
	if(end_day != "")
	{
		con_m.put("end_day",end_day+" 23:59:59");
	}
	if(sel_model != "")
	{
		con_m.put("model_id",sel_model);
	}
	if(selected_cat_ids != "")	
	{
		con_m.put("cat_id",selected_cat_ids);
	}
	reloadDiggList();
}

