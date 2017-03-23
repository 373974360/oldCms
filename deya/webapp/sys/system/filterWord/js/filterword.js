var FilterWordRPC = jsonrpc.FilterWordRPC;
var FilterWordBean = new Bean("com.deya.wcm.bean.system.filterWord.FilterWordBean",true);


var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "filterWord_table";;

function show()
{
	showList();	
	showTurnPage();	
}
/* 刷新页面 */
function locationFilterWord()
{
	window.location.href = "filterWordList.jsp?app_id="+app_id+"&site_id="+site;
}

/* 初始化表格 */
function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();
	
	//英文名，显示名，宽，高，样式名，点击事件
	colsList.add(setTitleClos("pattern","过滤词","200px","","",""));　
	colsList.add(setTitleClos("replacement","替换词","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

//根据条件排序后分页展示数据
function showList(){
	var m = new Map();
	var sortCol = table.sortCol;
	var sortType = table.sortType;
	
	//分页
	m.put("start_num",tp.getStart());
	m.put("page_size",tp.pageSize);
	
	//排序
	if(sortCol == "" || sortCol == null){
		sortCol = "filterword_id"
		sortType = "desc" 
	}
	m.put("sort_name",sortCol);
	m.put("sort_type",sortType);
	
	//条件
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}

	beanList = FilterWordRPC.getAllFilterWord(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();

	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("pattern").each(function(i){
		if(i>0)
		{			
			$(this).css({"text-align":"left"});	
			$(this).html('<a href="javascript:OpenModalWindow(\'维护过滤词\',\'/sys/system/filterWord/filterWord_add.jsp?fw_id=' + beanList.get(i-1).filterword_id + '\',450,190);">'+beanList.get(i-1).pattern+'</a>');
		}
	});	
	table.getCol("replacement").each(function(i){
			$(this).css({"text-align":"left"});	
	});
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
				
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

//打开查看窗口
function openViewFilterWordPage(fw_id)
{	
	OpenModalWindow("维护过滤词","/sys/system/filterWord/filterWord_add.jsp?fw_id="+fw_id,450,190);
}

//打开添加窗口
function openAddFilterWordPage(app_id,site_id)
{
	OpenModalWindow("维护过滤词","/sys/system/filterWord/filterWord_add.jsp?app_id="+app_id+"&site_id="+site_id,450,190);
}

//打开修改窗口
function openUpdateFilterWordPage()
{
	var selectIDS = table.getSelecteCheckboxValue("filterword_id");
	OpenModalWindow("维护过滤词","/sys/system/filterWord/filterWord_add.jsp?fw_id="+selectIDS,450,190);
}

//添加过滤词
function addFilterWord()
{
	var bean = BeanUtil.getCopy(FilterWordBean);

	$("#filterWord_table").autoBind(bean);
	
	if(!standard_checkInputInfo("filterWord_table"))
	{
		return;
	}
	bean.filterword_id=0;
	if(FilterWordRPC.insertFilterWord(bean))
	{
		msgAlert("过滤词信息"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().show()
	}
	else
	{
		msgWargin("过滤词信息"+WCMLang.Add_fail);
	}
}
//修改过滤词
function updateFilterWord()
{
	var bean = BeanUtil.getCopy(FilterWordBean);	
	$("#filterWord_table").autoBind(bean);

	if(!standard_checkInputInfo("filterWord_table"))
	{
		return;
	}

	if(FilterWordRPC.updateFilterWord(bean))
	{
		msgAlert("过滤词信息"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().show()
	}
	else
	{
		msgWargin("过滤词信息"+WCMLang.Add_fail);
	}
}

//删除过滤词
function deleteFilterWord()
{
	var selectIDS = table.getSelecteCheckboxValue("filterword_id");
	if(FilterWordRPC.deleteFilterWord(selectIDS))
	{
		msgAlert("过滤词信息"+WCMLang.Delete_success);
		getCurrentFrameObj().show()
	}else
	{
		msgWargin("过滤词信息"+WCMLang.Delete_fail);
	}
}

function showbean(fw_id)
{
	if(fw_id != "" && fw_id != "null" && fw_id != null)
	{
		selectBean = FilterWordRPC.getFilterWordBean(fw_id);
		if(selectBean)
		{
			$("#filterWord_table").autoFill(selectBean);	
		}
	}	
}



