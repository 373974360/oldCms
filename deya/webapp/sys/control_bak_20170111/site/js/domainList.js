var SiteRPC = jsonrpc.SiteRPC;
var SiteDomainRPC = jsonrpc.SiteDomainRPC;
var DeptRPC = jsonrpc.DeptRPC;
var SiteDomainBean = new Bean("com.deya.wcm.bean.control.SiteDomainBean",true);

var val=new Validator();
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "site_table";;
var con_m = new Map();

function reloadDomainList()
{
	showList();
	showTurnPage();
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	  
	colsList.add(setTitleClos("site_domain","站点域名","200px","","",""));
	colsList.add(setTitleClos("is_host","主域名","160px","","",""));
	colsList.add(setTitleClos("is_default","默认域名","60px","","",""));
	colsList.add(setTitleClos("spanCol","&#160;","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id

}

function showList(){			

	beanList = SiteDomainRPC.getDomainListBySiteID(site_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table")
	
	table.getCol("site_domain").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openUpdateSiteDomainPage(\''+beanList.get(i-1).domain_id+'\')">'+beanList.get(i-1).site_domain+'</a>');
		}
	});	
	
	table.getCol("is_host").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).is_host == 1)
				$(this).html('是');
			else
				$(this).html('&#160;');
		}
	});	
	
	table.getCol("is_default").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).is_default == 1)
				$(this).html('是');
			else
				$(this).html('&#160;');
		}
	});	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){
	//tp.total = MenuRPC.getChildMenuCount(menu_id);
	tp.total = beanList.size();
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

function openAddSiteDomainPage()
{  
	OpenModalWindow("修改域名","/sys/control/site/domain_add.jsp?site_id="+site_id,460,130);
}

function openUpdateSiteDomainPage(d_id)
{  	
	var selectIDS = "";
	if(d_id != null && d_id != "")
	{
		selectIDS = d_id;
	}else
		selectIDS = table.getSelecteCheckboxValue("domain_id");
	OpenModalWindow("修改域名","/sys/control/site/domain_add.jsp?site_id="+site_id+"&domain_id="+selectIDS,460,130);
}

//添加
function insertSiteDomain(){
	var bean = BeanUtil.getCopy(SiteDomainBean);	
	$("#site_table").autoBind(bean);
	
	if(!standard_checkInputInfo(table.table_name))
	{
		return;
	}
	
	//判断该域名是不是已经存在
	if(SiteDomainRPC.siteDomainISExist(bean.site_domain)){
		msgWargin("站点域名已经存在");
		return;
	}
	bean.site_id = site_id;
	if(SiteDomainRPC.insertSiteDomain(bean))
	{
		msgAlert("域名"+WCMLang.Add_success);
		getCurrentFrameObj().reloadDomainList();
		CloseModalWindow();
	}
	else
	{
		msgWargin("域名"+WCMLang.Add_fail);
	}

}

function updateSiteDomain()
{
	var bean = BeanUtil.getCopy(SiteDomainBean);	
	$("#site_table").autoBind(bean);
	if(!standard_checkInputInfo(table.table_name))
	{
		return;
	}
	if(bean.site_domain == defaultBean.site_domain)
	{
		msgAlert("域名"+WCMLang.Add_success);
		CloseModalWindow();
		return;
	}
	if(SiteDomainRPC.siteDomainISExist(bean.site_domain)){
		msgWargin("站点域名已经存在");
		return;
	}
	bean.site_id = site_id;
	bean.domain_id = domain_id;
	if(SiteDomainRPC.updateSiteDomain(bean))
	{
		msgAlert("域名"+WCMLang.Add_success);
		getCurrentFrameObj().reloadDomainList();
		CloseModalWindow();
	}
	else
	{
		msgWargin("域名"+WCMLang.Add_fail);
	}
}

function setDefault()
{
	var selectIDS = table.getSelecteCheckboxValue("domain_id");
	if(SiteDomainRPC.updateSDomainStatus(selectIDS,site_id))
	{
		msgAlert("默认域名"+WCMLang.Set_success);
		reloadDomainList();
	}
	else
	{
		msgWargin("默认域名"+WCMLang.Set_fail);
	}
}


function deleteSiteDomain()
{
	var selectIDS = "";
	var list = table.getSelecteBeans();
	for(var i=0;i<list.size();i++)
	{
		if(list.get(i).is_host == 1)
		{
			msgWargin("主域名不能删除,请重新选择删除项");
			return
		}
		selectIDS += ","+list.get(i).domain_id;
	}
	selectIDS = selectIDS.substring(1);

	var selectIDS = table.getSelecteCheckboxValue("domain_id");
	if(SiteDomainRPC.deleteSiteDomain(selectIDS,site_id))
	{
		msgAlert("域名"+WCMLang.Delete_success);
		reloadDomainList();
	}
	else
	{
		msgWargin("域名"+WCMLang.Delete_fail);
	}
}
