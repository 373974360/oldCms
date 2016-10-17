var QueryConfRPC = jsonrpc.QueryConfRPC;
var QueryConfBean = new Bean("com.deya.wcm.bean.query.QueryConfBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "QueryConf_table";

function reloadList()
{	
	showTurnPage();
	showList();
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("conf_title","业务名称","200px","","",""));
	colsList.add(setTitleClos("conf_description","业务描述","","","",""));
	//colsList.add(setTitleClos("App_id","所属应用","80px","","",""));	

	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){	
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "conf_id";
		sortType = "desc";
	}
	var m = new Map();

	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}

	beanList = QueryConfRPC.getQueryConfLists(m);
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("conf_title").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openUpdatePage2('+beanList.get(i-1).conf_id+')">'+beanList.get(i-1).conf_title+'</a>');
		}
	});	
	
	table.getCol("conf_description").each(function(i){
		if(i>0)
		{
			$(this).text(beanList.get(i-1).conf_description.substring(0,20));
		}	
	});

	table.getCol("app_id").each(function(i){
		if(i>0)
		{
			$(this).text(beanList.get(i-1).app_id);
		}	
	});

	Init_InfoTable(table.table_name);
}

function showTurnPage(){
	var m = new Map();
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);

	tp.total = QueryConfRPC.getQueryConfCount(m);
	tp.show("turn","");	
	tp.onPageChange = showList;
}

function openAddPage(site_id)
{
	window.location.href = "/sys/query/query_conf_add.jsp?site_id="+site_id;		
}

function openUpdatePage(site_id)
{
	var selectIDS = "";
	if(conf_id == "" || conf_id == null)
		selectIDS = table.getSelecteCheckboxValue("conf_id");
	else
		selectIDS = conf_id;
	window.location.href = "/sys/query/query_conf_add.jsp?site_id="+site_id+"&conf_id="+selectIDS;
}

function openUpdatePage2(conf_id)
{
	window.location.href = "/sys/query/query_conf_add.jsp?conf_id="+conf_id;
}

function addQueryConf(){
	alert("	addQueryConf ");

	var bean = BeanUtil.getCopy(QueryConfBean);	
	$("#QueryConf_table").autoBind(bean);
	if(!standard_checkInputInfo("QueryConf_table"))
	{
		return;
	}
	bean.conf_id = QueryConfRPC.getQueryConfID();
	bean.app_id = "cms";
	bean.site_id = $("#site_id").val();

	alert("bean.conf_id ========"+bean.conf_id);
	alert("bean.conf_title ========"+bean.conf_title);
	alert("bean.conf_description ========"+bean.conf_description);
	alert("bean.t_list_id ========"+bean.t_list_id);
	alert("bean.t_content_id ========"+bean.t_list_id);
	alert("bean.site_id ========"+bean.site_id);


	
	if(QueryConfRPC.insertQueryConf(bean))
	{
		top.msgAlert("业务"+WCMLang.Add_success);
		window.location.href = "query_conf.jsp";
	}
	else
	{
		top.msgWargin("业务"+WCMLang.Add_fail);
	}
}

function updateQueryConf(){
	alert("	updateQueryConf	");
}


function deleteQueryConf()
{
	var selectIDS = table.getSelecteCheckboxValue("conf_id");
	if(QueryConfRPC.deleteQueryConf(selectIDS))
	{
		top.msgAlert("业务删除成功！");
		reloadList();
	}else
	{
		top.msgWargin("业务删除失败！");
	}
}