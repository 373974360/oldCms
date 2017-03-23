var SiteRPC = jsonrpc.SiteRPC;
var SiteGroupRPC = jsonrpc.SiteGroupRPC;
var SiteDomainRPC = jsonrpc.SiteDomainRPC;
var SiteServerRPC = jsonrpc.SiteServerRPC;
var DeptRPC = jsonrpc.DeptRPC;
var SiteBean = new Bean("com.deya.wcm.bean.control.SiteBean",true);

var val=new Validator();
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "site_table";;
var con_m = new Map();


function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	  
	//colsList.add(setTitleClos("site_id","站点ID","60px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("site_name","站点名称","","","",""));
	colsList.add(setTitleClos("site_domain","站点域名","160px","","",""));
	colsList.add(setTitleClos("domain_col","域名管理","160px","","",""));
	colsList.add(setTitleClos("site_status","站点状态","60px","","",""));
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id

}

function showList(){			

	beanList = SiteRPC.getSiteChildListByID(site_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table")
	
	
	table.getCol("site_name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openViewPage(\''+beanList.get(i-1).site_id+'\')">'+beanList.get(i-1).site_name+'</a>');
		}
	});	
	
	table.getCol("site_domain").each(function(i){	
		if(i>0)
		{			
			$(this).html(SiteDomainRPC.getSiteDomainBySiteID(beanList.get(i-1).site_id));			
		}
	});

	table.getCol("domain_col").each(function(i){	
		if(i>0)
		{			
			$(this).html('<a href="javascript:addTab(true,\'/sys/control/site/domainList.jsp?site_id='+beanList.get(i-1).site_id+'\',\'域名管理\');">域名管理</a>');
		}
	});

	table.getCol("site_status").each(function(i){	
		if(i>0)
		{			
			var site_status = beanList.get(i-1).site_status;
			if(site_status=='0'){
				site_status = '正常';
			}else if(site_status=='1'){
				site_status = '暂停';
			}
			$(this).html(site_status);	
			//$(this).attr("title","1");
		}
	});
	table.getCol("sort_col").each(function(i){	
		if(i>0)
		{			
			$(this).html(getSortColStr());			
		}
	});
	
}

function showTurnPage(){
	//tp.total = MenuRPC.getChildMenuCount(menu_id);
	tp.total = beanList.size();
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

function openAddSitePage()
{  
	var create_flag = SiteRPC.isCreateSite();//创建站点时判断站点是否超出，可以创建返回true,不能创建返回站点总数用于提示
	if(create_flag == "true")
	{
		addTab(true,'/sys/control/site/site_add.jsp?parentID='+site_id+"&tab_index="+curTabIndex,'维护站点');
	}else
	{
		msgWargin("您所购站点数已创建完成，如需要增加站点，请联系厂商");
	}
}

function openUpdateSitePage()
{  
	//OpenModalWindow("维护站点","/sys/control/site/site_add.jsp?parentID="+site_id,500,360);
	var selectIDS = table.getSelecteCheckboxValue("site_id");
	addTab(true,'/sys/control/site/site_add.jsp?site_id='+selectIDS+'&parentID='+site_id+"&tab_index="+curTabIndex,'维护站点');
}

function openViewPage(s_id)
{  
	addTab(true,'/sys/control/site/site_view.jsp?site_id='+s_id+"&tab_index="+curTabIndex,'查看站点');
}


//添加
function addSite(){
	if(!standard_checkInputInfo(table.table_name))
	{
		return;
	}
	$("#"+table.table_name).autoBind(SiteBean);
	
	//得到该站群信息
	var SiteGroupBean = SiteGroupRPC.getSGroupRoot("0");
	//alert(SiteGroupBean.sgroup_id);
	
	//alert(SiteBean.server_id);
	//return;
	
	//判断站点标识是不是已经存在
	SiteBean.site_id=SiteGroupBean.sgroup_id+SiteBean.site_id;
	if(SiteRPC.siteIDISExist(SiteBean.site_id)){
		msgWargin("站点标识已经存在");
		return;
	}
	
	//判断该域名是不是已经存在
	if(SiteDomainRPC.siteDomainISExist(SiteBean.site_domain)){
		msgWargin("站点域名已经存在");
		return;
	}
	
	//得到站点的排序数字
	SiteBean.site_sort=SiteRPC.getSiteSortByID(SiteBean.parent_id)+1;
	SiteBean.sgroup_id = SiteGroupBean.sgroup_id;

	if(SiteRPC.insertSite(SiteBean))
	{
		msgAlert("站点信息"+WCMLang.Add_success);
		getCurrentFrameObj(tab_index).changeMemuListTable(SiteBean.parent_id);
		getCurrentFrameObj(tab_index).insertMenuTree(SiteBean.site_id,SiteBean.site_name);
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("站点信息"+WCMLang.Add_fail);
	}

}

//添加树节点
function insertMenuTree(id,dept_name)
{
	insertTreeNode(eval('[{"id":"'+id+'","text":\"'+dept_name+'\","attributes":{}}]'));	
}


//删除
function deleteSite(){
	var selectIDS = table.getSelecteCheckboxValue("site_id");
	//判断是否有下级节点
	if(SiteRPC.getSiteSortByID(selectIDS)!='0'){
		msgWargin("该站点下面有站点，不能删除");
		return;
	}
	
	if(SiteRPC.deleteSite(selectIDS)){
		msgAlert("站点信息"+WCMLang.Delete_success);
		changeMemuListTable(site_id);
		deleteTreeNode(selectIDS); 
	}else
	{
		msgWargin("站点信息"+WCMLang.Delete_fail);
	}
}

//排序
function funSort()
{
	var selectIDS = table.getAllCheckboxValue("site_id");
	if(SiteRPC.saveSiteSort(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
		showMenuTree(); 
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}


//暂停
function funPause()
{
	var selectIDS = table.getSelecteCheckboxValue("site_id");
	if(SiteRPC.updateSiteStatus(selectIDS,"1"))
	{
		msgAlert(WCMLang.Set_success);
		getCurrentFrameObj().changeMemuListTable(site_id);
		//showMenuTree(); 
	}else
	{
		msgWargin(WCMLang.Set_fail);
	}
}

//恢复
function funRestore()
{ 
	var selectIDS = table.getSelecteCheckboxValue("site_id");
	if(SiteRPC.updateSiteStatus(selectIDS,"0"))
	{
		msgAlert(WCMLang.Set_success);
		getCurrentFrameObj().changeMemuListTable(site_id);
		//showMenuTree(); 
	}else
	{
		msgWargin(WCMLang.Set_fail);
	}
}


//修改
function updateSite(){
	if(!standard_checkInputInfo(table.table_name))
	{
		return;
	}
	$("#"+table.table_name).autoBind(SiteBean);
	
	//alert(SiteBean.sgroup_id);
	//得到该站群信息
	var SiteGroupBean = SiteGroupRPC.getSGroupRoot("0");
	SiteBean.sgroup_id = SiteGroupBean.sgroup_id;
	 
	//判断站点标识是不是已经存在
	SiteBean.site_id=site_id;
	/*
	if(SiteRPC.siteIDISExist(SiteBean.site_id)){
		msgWargin("站点标识已经存在");
		return;
	}
	*/
	
	//判断该域名是不是已经存在
	if(defaultBean.site_domain!=SiteBean.site_domain){
		if(SiteDomainRPC.siteDomainISExist(SiteBean.site_domain)){
			msgWargin("站点域名已经存在");
			return;
		}
	}

	//得到站点的排序数字
	/*
	SiteBean.site_sort=SiteRPC.getSiteSortByID(SiteBean.parent_id)+1;
	SiteBean.sgroup_id = SiteGroupBean.sgroup_id;
	*/

	if(SiteRPC.updateSite(SiteBean))
	{
		msgAlert("站点信息"+WCMLang.Add_success);
		getCurrentFrameObj(tab_index).changeMemuListTable(SiteBean.parent_id);
		getCurrentFrameObj(tab_index).updateMenuTree(SiteBean.site_id,SiteBean.site_name);
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("站点信息"+WCMLang.Add_fail);
	}

}


//修改树节点
function updateMenuTree(id,menu_name)
{
	updateTreeNode(id,menu_name);
}


//初始化服务器下拉框的数据
function initServerList(){
	var serverList = SiteServerRPC.getServerList();
	serverList = List.toJSList(serverList);//把list转成JS的List对象
	if(serverList==null || serverList.size()==0){
		msgWargin("请先注册服务器");
		tab_colseOnclick(curTabIndex);
	} 
	for (var i = 0; i < serverList.size(); i++) {
		var emptyBean = serverList.get(i);
		$("#server_id").addOptionsSingl(emptyBean.server_id,emptyBean.server_name);
	}
}

//设置站点管理员
function saveUser(user_ids){
	var insert_user_ids = removeAlreadyStr(user_ids,select_role_user_ids);//从ID中分析出新加的用户，已有的就不再加了
	var delete_user_ids = removeNotAlreadyStr(user_ids,select_role_user_ids);//从ID中分析出需要删除的用户

	var selectIDS = table.getSelecteCheckboxValue("site_id");
	if(SiteRPC.insertSiteUserManager("cms",selectIDS,insert_user_ids,delete_user_ids))
	{  
		msgAlert("站点管理员"+WCMLang.Add_success);
		table.unChekcbox();
	}
	else
	{
		msgWargin("站点管理员"+WCMLang.Add_fail);
	}
}


//得到该角色下已有的用户ID
var select_role_user_ids = "";
function getSelectedUserIDS()
{
	select_role_user_ids = "";
	var selectIDS = table.getSelecteCheckboxValue("site_id");
	select_role_user_ids = SiteRPC.getUsersBySiteId("cms",selectIDS);	
	return select_role_user_ids;
}
