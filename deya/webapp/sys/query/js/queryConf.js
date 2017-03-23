var QueryConfRPC = jsonrpc.QueryConfRPC;
var QueryDicRPC = jsonrpc.QueryDicRPC;
var QueryItemRPC = jsonrpc.QueryItemRPC;
var QueryConfBean = new Bean("com.deya.wcm.bean.query.QueryConfBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();

var tp = new TurnPage();
var beanList = null;

var QueryDicBeanList = null;

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
	
	colsList.add(setTitleClos("conf_id","业务ID","200px","","",""));
	colsList.add(setTitleClos("conf_title","业务名称","200px","","",""));
	//colsList.add(setTitleClos("conf_description","业务描述","","","",""));
	colsList.add(setTitleClos("actionCol","操作","","","",""));

	
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
	m.put("site_id", site_id);
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
	

	table.getCol("conf_id").each(function(i){
		if(i>0)
		{
			$(this).html(beanList.get(i-1).conf_id);
		}
	});

	table.getCol("conf_title").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openUpdatePage2('+beanList.get(i-1).conf_id+')">'+beanList.get(i-1).conf_title+'</a>');
		}
	});	
	
	/* table.getCol("conf_description").each(function(i){
		if(i>0)
		{
			$(this).text(beanList.get(i-1).conf_description.substring(0,20));
		}	
	}); */

	table.getCol("actionCol").each(function(i){

		if(i>0)
		{	
			var html = "";
			$(this).css({"text-align":"center"});	
			//$(this).html('<a href="query_dic.jsp?conf_id='+beanList.get(i-1).conf_id+'">字段设置</a>');

			html +='<span onclick="openSetPage(\''+beanList.get(i-1).conf_id+'\',\'1\',\''+site_id+'\')" style="cursor:pointer;">字段设置</span>&#160;&#160;&#160;';

			html +='<span onclick="openSetPage(\''+beanList.get(i-1).conf_id+'\',\'2\',\''+site_id+'\')" style="cursor:pointer;">导入Excel</span>&#160;&#160;&#160;';

			html +='<span onclick="openSetPage(\''+beanList.get(i-1).conf_id+'\',\'3\',\''+site_id+'\')" style="cursor:pointer;">信息列表</span>&#160;&#160;&#160;';
			
			$(this).html(html); 
		}		

	});

	Init_InfoTable(table.table_name);
}

function showTurnPage(){
	var m = new Map();
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("site_id", site_id);

	tp.total = QueryConfRPC.getQueryConfCount(m);
	tp.show("turn","");	
	tp.onPageChange = showList;
}

function openSetPage(conf_id,type,site_id)
{
	if(type ==1){
		window.location.href = "/sys/query/query_dic.jsp?conf_id="+conf_id+"&site_id="+site_id;
	}else if(type ==2){
		window.location.href = "/sys/query/query_upload.jsp?conf_id="+conf_id+"&site_id="+site_id;
	}else{
		window.location.href = "/sys/query/query_infoLists.jsp?conf_id="+conf_id+"&site_id="+site_id;
	}	
}


function openAddPage(site_id)
{
	window.location.href = "/sys/query/query_conf_add.jsp?site_id="+site_id;		
}

function openUpdatePage(site_id)
{
	var selectIDS = table.getSelecteCheckboxValue("conf_id");
	window.location.href = "/sys/query/query_conf_add.jsp?site_id="+site_id+"&conf_id="+selectIDS;
}

function openUpdatePage2(conf_id)
{
	window.location.href = "/sys/query/query_conf_add.jsp?site_id="+site_id+"&conf_id="+conf_id;
}

function addQueryConf(site_id)
{
	var bean = BeanUtil.getCopy(QueryConfBean);	
	$("#QueryConf_table").autoBind(bean);
	if(!standard_checkInputInfo("QueryConf_table"))
	{
		return;
	}
	bean.conf_id = QueryConfRPC.getQueryConfID();
	bean.app_id = "cms";
	bean.site_id = site_id;
	if(bean.t_content_id == ""){
		bean.t_content_id ="0";
	}
	
	if(QueryConfRPC.insertQueryConf(bean))
	{
		msgAlert("业务"+WCMLang.Add_success);
		window.location.href = "query_conf.jsp?site_id="+site_id;
	}
	else
	{
		msgWargin("业务"+WCMLang.Add_fail);
	}
}

function updateQueryConf(conf_id){
	var bean = BeanUtil.getCopy(QueryConfBean);	
	$("#QueryConf_table").autoBind(bean);
	if(!standard_checkInputInfo("QueryConf_table"))
	{
		return;
	}
	bean.conf_id = conf_id;
	if(QueryConfRPC.updateQueryConf(bean))
	{
		msgAlert("业务修改成功！");
		window.location.href = "query_conf.jsp?site_id="+site_id;;
	}
	else
	{
		msgWargin("业务修改失败！");
	}
}

function deleteQueryConf()
{
	var m = new Map();
	m.put("conf_id",table.getSelecteCheckboxValue("conf_id")+"");
	if(QueryConfRPC.deleteQueryConf(m))
	{
		msgAlert("业务删除成功！");
		reloadList();
	}else
	{
		msgWargin("业务删除失败！");
	}
}
