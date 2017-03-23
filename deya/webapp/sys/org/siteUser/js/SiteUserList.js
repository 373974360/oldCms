var SiteUserRPC = jsonrpc.SiteUserRPC;
var SiteUserBean = new Bean("com.deya.wcm.bean.org.siteuser.SiteUserBean",true);
var UserBean = new Bean("com.deya.wcm.bean.org.user.UserBean",true);

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "siteUser_list";
var con_m = new Map();
var nameMap = new Map();


// 初始化表格
function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("user_name","用户名称","120px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("dept_name","用户部门","300px","","",""));
	colsList.add(setTitleClos("action_col","管理操作","100px","","",""));
	colsList.add(setTitleClos("role_names","所属角色","","","",""));
//	colsList.add(setTitleClos("app_name","应用","120px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	//table.show("group_table");//里面参数为外层div的id
}

// 刷新表格及翻页标签
function reloadSiteUserList()
{
	showList();
	showTurnPage();
}

// 填充表格数据
function showList()
{
	var sortCol = table.sortCol;
	var sortType = table.sortType;	
	
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "id";
		sortType = "desc";
	}

	var beanList = SiteUserRPC.getSiteUserList(siteId, appId);
	beanList =List.toJSList(beanList);
	
	nameMap = SiteUserRPC.getSiteUserInfo(beanList);
	nameMap = Map.toJSMap(nameMap);
	
	tp.total = beanList.size();
	table.setBeanList(beanList,"td_list");
	table.show("siteUser_table");
	
	table.getCol("user_name").each(function(i){
		if(i>0)
		{
			var key = beanList.get(i-1).user_id +"_"+ beanList.get(i-1).app_id;
			var nameList = nameMap.get(key);
			nameList = List.toJSList(nameList);
			var name = nameList.get(0);
			$(this).html('<a href="javascript:addTab(true,\'/sys/org/dept/user_view.jsp?user_id='+beanList.get(i-1).user_id+'\',\'用户信息\')">'+name+'</a>');
		}
	});
	
	table.getCol("dept_name").each(function(i){	
		if(i>0)
		{		
			var key = beanList.get(i-1).user_id +"_"+ beanList.get(i-1).app_id;
			var nameList = nameMap.get(key);		
			nameList = List.toJSList(nameList);
			var name = nameList.get(1);
				$(this).text(name);		
		}
	});
	
	table.getCol("action_col").each(function(i){	
		if(i>0)
		{		
			$(this).html('<a href="javascript:openSetOperate(\'权限管理\',\'setUserOperate\',\''+appId+'\',\''+siteId+'\',0,'+beanList.get(i-1).user_id+')">权限管理</a>');	
		}
	});

	table.getCol("role_names").each(function(i){	
		if(i>0)
		{		
			$(this).css("text-align","left");
		}
	});
	Init_InfoTable(table.table_name);
}

// 翻页填充
function showTurnPage()
{
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

/*************************关联用户操作函数 ********************************/
// 取得group中已有的用户IDS
function getSelectedUserIDS()
{
	var userIDS = SiteUserRPC.getUserIDS(siteId, appId);
	return userIDS;
}

// 保存站点用户信息
function saveSiteUser(user_ids)
{
	var old_user_ids = table.getAllCheckboxValue("user_id");
	//这里需要判断一下，把要添加的用户ID和原有的用户ID做比较，把新加的用户和要删除的用户分别提取出来，因为删除的用户必须也要删除他的角色关联，所以不能做全删全写的操作了

	var insert_user_ids = removeAlreadyStr(user_ids,old_user_ids);
	var delete_user_ids = removeNotAlreadyStr(user_ids,old_user_ids);

	if(SiteUserRPC.linkSiteUser(insert_user_ids,delete_user_ids, siteId, appId))
	{
		reloadSiteUserList();
		msgAlert("站点用户关联"+WCMLang.Add_success);
		table.unChekcbox();
	}else
	{
		msgWargin("站点用户关联"+WCMLang.Add_fail);
	}
}

/***************************删除站点用户********************************/
// 删除站点用户信息
function deleteSiteUser()
{
	var user_ids = table.getSelecteCheckboxValue("user_id");
	if( !SiteUserRPC.deleteSiteUser(user_ids, siteId, appId))
	{
		msgWargin("站点用户"+WCMLang.Delete_fail);
		return;
	}
	msgAlert("站点用户"+WCMLang.Delete_success);
	reloadSiteUserList();
}

//关联角色
function saveRoleUser(role_ids)
{
	var user_id = table.getSelecteCheckboxValue("user_id");
	var RoleUserBean = new Bean("com.deya.wcm.bean.org.role.RoleUserBean",true);
	var bean = BeanUtil.getCopy(RoleUserBean);	
	bean.role_id = role_ids;
	bean.app_id = appId;
	bean.site_id = siteId;
	bean.user_id = user_id;

	if(jsonrpc.RoleRPC.insertRoleUserByUser(bean))
	{
		msgAlert("角色用户关联"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadSiteUserList();
		table.unChekcbox();
	}else
	{
		msgWargin("角色用户关联"+WCMLang.Add_fail);
	}
}

//得到已选过的角色
function getCurrentSelectedRoleID(user_id)
{	
	return jsonrpc.RoleRPC.getRoleIDSByUserAPP(user_id,appId,siteId);	
}

function setUserOperate()
{
	getCurrentFrameObj().reloadSiteUserList();
}

