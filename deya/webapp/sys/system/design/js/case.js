var DesignRPC = jsonrpc.DesignRPC;
var DesignCaseBean = new Bean("com.deya.wcm.bean.system.design.DesignCaseBean",true);


var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "filterWord_table";;

function reloadList()
{
	showList();	
	showTurnPage();	
}

/* 初始化表格 */
function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();
	
	//英文名，显示名，宽，高，样式名，点击事件
	colsList.add(setTitleClos("case_name","方案套餐名称","200px","","",""));　
	colsList.add(setTitleClos("weight","权重","50px","","",""));
	colsList.add(setTitleClos("action_cell","操作","50px","","",""));
	colsList.add(setTitleClos("spac_cell","&nbsp;","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

//根据条件排序后分页展示数据
function showList(){
	var m = new Map();
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);

	beanList = DesignRPC.getDesignCaseList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();

	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	

	table.getCol("case_name").each(function(i){
		if(i>0)
		{	
			$(this).html('<a href="javascript:openUpdateDesignCasePage('+beanList.get(i-1).case_id+')">'+beanList.get(i-1).case_name+'</a>');
		}
	});	

	table.getCol("action_cell").each(function(i){
		if(i>0)
		{	
			$(this).html('<a target="_blank" href="operate/designPage.jsp">设计方案</a>');			
		}
	});	
	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	tp.total = DesignRPC.getDesignCaseCount();			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//打开添加窗口
function openAddDesignCasePage()
{		
	addTab(true,"/sys/system/design/case_add.jsp?app_id="+app_id+"&top_index="+curTabIndex,"维护方案套餐");
}

function openUpdateDesignCasePage(c_id)
{
	var id = "";
	if(c_id != "" && c_id != null)
	{
		id = c_id;
	}else
		id = table.getSelecteCheckboxValue("case_id");
	
	addTab(true,"/sys/system/design/case_add.jsp?app_id="+app_id+"&top_index="+curTabIndex+"&case_id="+id,"维护方案套餐");
}


function addCase()
{
	var bean = BeanUtil.getCopy(DesignCaseBean);
	$("#case_table").autoBind(bean);
	if(!standard_checkInputInfo("case_table"))
	{
		return;
	}
	bean.app_id = app_id;
	bean.site_id = site_id;

	if(DesignRPC.insertDesignCase(bean))
	{
		msgAlert("方案套餐"+WCMLang.Add_success);
		getCurrentFrameObj(top_index).reloadList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("方案套餐"+WCMLang.Add_fail);
	}
}

function updateCase()
{
	var bean = BeanUtil.getCopy(DesignCaseBean);
	$("#case_table").autoBind(bean);
	if(!standard_checkInputInfo("case_table"))
	{
		return;
	}
	bean.case_id = case_id;	

	if(DesignRPC.updateDesignCase(bean))
	{
		msgAlert("方案套餐"+WCMLang.Add_success);
		getCurrentFrameObj(top_index).reloadList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("方案套餐"+WCMLang.Add_fail);
	}
}

function deleteDesignCase()
{
	var selectIDS = table.getSelecteCheckboxValue("case_id");
	if(DesignRPC.deleteDesignCase(selectIDS))
	{
		msgAlert("方案套餐"+WCMLang.Delete_success);
		reloadList();
	}else
	{
		msgWargin("方案套餐"+WCMLang.Delete_fail);
	}
}

function reloadCssList()
{
	var m = new Map();
	m.put("start_num", 0);	
	m.put("page_size", 999);

	var cssList = DesignRPC.getDesignCssList(m);
	cssList = List.toJSList(cssList);
	$("#css_id").addOptions(cssList,"css_id","css_name");
}