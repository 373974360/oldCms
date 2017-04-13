var SiteConfigRPC = jsonrpc.SiteConfigRPC;
var SiteConfigBean = new Bean("com.deya.wcm.bean.control.SiteConfigBean",true);

var val=new Validator();
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "config_table";

//从环境中得到站点Id 
var site_id = "cicro"; 

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	  
	colsList.add(setTitleClos("config_key","属性名","200px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("config_value","属性值","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id

}

function reloadDataList()
{
	showList();	
	showTurnPage();
}

function showList(){			
	
	beanList = SiteConfigRPC.getConfigListBySiteID(site_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	 
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){  
	tp.total = beanList.size();
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

//打开添加页面
function openAddPage(){ 
	OpenModalWindow("修改配置","/sys/control/siteConfig/siteConfig_add.jsp",500,160);
}

//打开修改页面
function openUpdatePage(){
	var selectIDS = table.getSelecteCheckboxValue("config_id");
	OpenModalWindow("修改配置","/sys/control/siteConfig/siteConfig_add.jsp?config_id="+selectIDS,500,160);
}

//添加
function funAdd(){
	if(!standard_checkInputInfo(table.table_name))
	{
		return;
	}
	$("#"+table.table_name).autoBind(SiteConfigBean);

	SiteConfigBean.site_id=site_id;
    
	var SiteConfigBeanList = new List(); 
	SiteConfigBeanList.add(SiteConfigBean);
	
	if(SiteConfigRPC.insertSiteConfig(SiteConfigBeanList))
	{
		msgAlert("站点配置信息"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadDataList();
	}
	else
	{
		msgWargin("站点配置信息"+WCMLang.Add_fail);
	}
}


//修改
function funUpdate(){
	if(!standard_checkInputInfo(table.table_name))
	{
		return;
	}
	//alert(SiteServerBean.server_id);
	SiteConfigBean = defaultBean;
	$("#"+table.table_name).autoBind(SiteConfigBean);
	
	if(SiteConfigRPC.updateSiteConfig(SiteConfigBean))
	{
		msgAlert("站点配置信息"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadDataList();
	}
	else
	{
		msgWargin("站点配置信息"+WCMLang.Add_fail);
	}
}

//删除
function funDelete(){ 
	var selectIDS = table.getSelecteCheckboxValue("config_id");
	
	if(SiteConfigRPC.deleteSiteConfig(selectIDS)){
		msgAlert("站点配置信息"+WCMLang.Delete_success);
		getCurrentFrameObj().reloadDataList();
	}else
	{
		msgWargin("站点配置信息"+WCMLang.Delete_fail);
	}
}

