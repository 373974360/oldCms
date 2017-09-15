var CollectionDataRPC = jsonrpc.CollectionDataRPC;

var con_m = new Map();
var table = new Table();	
table.table_name = "rule_table";

var beanList = null;
var tp = new TurnPage();
var m = new Map();


//加载初始化Table和翻页信息
function loadRuleTable()
{
	showList();
	//showTurnPage();    //分页处理
}

// 初始化Table
function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("rule_id","ID","20px","","","")); //英文名，显示名，宽，高，样式名，点击事件
	colsList.add(setTitleClos("rule_name","采集名称","","","",""));
	colsList.add(setTitleClos("rule_status","状态","30px","","",""));
//	colsList.add(setTitleClos("start_time","开始时间","","","",""));
//	colsList.add(setTitleClos("end_time","结束时间","","","",""));
	colsList.add(setTitleClos("rule_handle","操作","300px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("rule_table");//里面参数为外层div的id
    Init_InfoTable(table.table_name);
}

function showList()
{
	var sortCol = table.sortCol;
	var sortType = table.sortType;
	
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "id";
	    sortType = "desc";
	}
	m.put("start_num", tp.getStart());	
	m.put("page_size", "100");
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
    m.put("rcat_id", id);
    m.put("site_id", site_id);
	
	beanList = CollectionDataRPC.getRuleList(m);
	beanList =  List.toJSList(beanList);
	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("rule_table");
	
	table.getCol("rule_id").each(function(i){	
		if(i>0)
		{
			$(this).html(beanList.get(i-1).rule_id);
		}
	});
	
	table.getCol("rule_name").each(function(i){	
		if(i>0)
		{
			$(this).html(beanList.get(i-1).title);
		}
	});
	
	table.getCol("rule_status").each(function(i){	
		if(i>0)
		{
			var id = beanList.get(i-1).id;
			$(this).html("<span id='collstate_"+id+"'>停止</span>");
		}
	});
	
	
	table.getCol("rule_handle").each(function(i){	
		if(i>0)
		{	
			var ruleid = beanList.get(i-1).id;
			
			var str = "<div style='display:inline'>" +
					  "<span onclick='startCollection("+ruleid+")' " +
					  "style='cursor:pointer;'>开始</span></div>&#160;&#160;&#160;";
			$(this).html(str);
		}
	});
	
	Init_InfoTable(table.table_name);
}

/**
 * 开始采集
 * @param rule_id 规则ID
 * @param index 所在行的索引值
 * @return
 */
function startCollection(rule_id){
	var pageType="ruleList";
	$("#collstate_"+rule_id).html("<font color='red'>开始</font>");  //点击开始修改采集状态
	window.location.href ="/sys/dataCollection/collProgress.jsp?rule_id="+rule_id+"&pageType="+pageType;
	
}

/**
 * 列表的分页
 * @return
 */
function showTurnPage()
{	
	CollectionDataRPC.getRuleListCount(showTurnPageResult);	
}
function showTurnPageResult(result,e)
{
    if(e != null){return;}
	tp.total = result;
	tp.show("rule_turn","");	
	tp.onPageChange = showList;		
}

/**
 * 添加采集规则
 * @return
 */
function openaddRuleTabPage()
{
	top.addTab(true,"/sys/dataCollection/add_rule.jsp?rcat_id="+id+"&tab_index="+top.curTabIndex,"添加规则");
}

/**
 * 删除采集规则
 * @return
 */
function deleteRuleByid()
{
	var ids = table.getSelecteCheckboxValue("id");
	if(CollectionDataRPC.deleteRuleById(ids))
	{
		top.msgAlert("信息"+WCMLang.Delete_success);
		loadRuleTable();
	}else{
		top.msgWargin("信息"+WCMLang.Delete_fail);
	}
}

/**
 * 修改采集规则
 * @return
 */
function updateRuleById()
{
	var id = table.getSelecteCheckboxValue("id");
	top.addTab(true,"/sys/dataCollection/add_rule.jsp?type=update&id="+id+"+&tab_index="+top.curTabIndex,"修改规则");
}

