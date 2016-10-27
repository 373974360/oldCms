var GroupManRPC = jsonrpc.GroupManRPC;
var GroupBean = new Bean("com.deya.wcm.bean.org.group.GroupBean",true);

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "group_table_list";
var con_m = new Map();
var current_group_id;
var is_button_click = true;//是否是点击的按钮触发事件

// 初始化表格
function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("group_name","名称","150px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("action_col","管理操作","150px","","",""));
	colsList.add(setTitleClos("role_names","所属角色","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	//table.show("group_table");//里面参数为外层div的id
}

// 刷新表格及翻页标签
function reloadGroupList()
{
	showTurnPage();
	showList();
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
	
	con_m.put("start_num", tp.getStart());	
	con_m.put("page_size", tp.pageSize);
	
	var beanList = GroupManRPC.getGroupListForDB(con_m);
	beanList =List.toJSList(beanList);
	
	table.setBeanList(beanList,"td_list");
	table.show("group_table");
	
	table.getCol("group_name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:top.addTab(true,\'/sys/org/group/group_view.jsp?appID='+appId+'&site_id='+siteId+'&groupId='+beanList.get(i-1).group_id+'\',\'用户组信息\')">'+beanList.get(i-1).group_name+'</a>');	
		}
	});
	
	table.getCol("action_col").each(function(i){	
		if(i>0)
		{		
			var str = '<a href="javascript:openReleUserHandl('+beanList.get(i-1).group_id+')">关联用户</a>';
			if(siteId != "")
			{
				str += '&#160;&#160;<a href="javascript:is_button_click = false;current_group_id='+beanList.get(i-1).group_id+';openSetOperate(\'权限管理\',\'setUserOperate\',\''+appId+'\',\''+siteId+'\',1,'+beanList.get(i-1).group_id+')">权限管理</a>';
			}else
			{
				str += '&#160;&#160;<a href="javascript:is_button_click = false;current_group_id='+beanList.get(i-1).group_id+';openSelectRolePage(\'选择角色\',\'saveGroupRole\',\''+appId+'\',\''+siteId+'\')">关联角色</a>';
			}
			$(this).html(str);	
		}
	});

	table.getCol("role_names").each(function(i){	
		if(i>0)
		{		
			$(this).css("text-align","left");
			$(this).html(GroupManRPC.getRolesByGroupID(beanList.get(i-1).group_id,beanList.get(i-1).app_id,beanList.get(i-1).site_id));
		}
	});
	
	Init_InfoTable(table.table_name);
}

// 翻页填充
function showTurnPage()
{
	tp.total = GroupManRPC.getGroupCount(con_m);
	tp.show("turn","");	
	tp.onPageChange = showList;
}

// 新建用户组
function addGroup()
{
	top.OpenModalWindow("用户组添加","/sys/org/group/group_add.jsp?appId="+appId+"&site_id="+siteId,420,220);
}

// 添加用户页面"保存"操作
function savaGroup()
{
	var bean = BeanUtil.getCopy(GroupBean);
	$("#addGroup_table").autoBind(bean);
	
	if(!GroupManRPC.insertGroup(bean))
	{
		top.msgWargin("用户组"+WCMLang.Add_fail);
		return;
	}
	top.msgAlert("用户组"+WCMLang.Add_success);
	top.getCurrentFrameObj().reloadGroupList();
	top.CloseModalWindow();
}

// 显示用户组信息
function viewGroup(groupId)
{
	top.OpenModalWindow("查看用户组信息","/sys/org/group/group_view.jsp?groupId="+groupId,450,190);	
}


// 修改用户组
function updateGroup()
{
	var groupId = table.getSelecteCheckboxValue("group_id");
	top.OpenModalWindow("用户组修改","/sys/org/group/group_update.jsp?groupId="+groupId,420,220);
}

// 删除用户组
function deleteGroup()
{
	var groupId = table.getSelecteCheckboxValue("group_id");
	if( !GroupManRPC.deleteGroup(groupId,siteId))
	{
		top.msgWargin("用户组"+WCMLang.Delete_fail);
		return;
	}
	parent.msgAlert("用户组"+WCMLang.Delete_success);
	reloadGroupList();
}

/*************************关联用户操作函数 ********************************/
// 取得group中已有的用户IDS
var select_user_ids = "";
function getSelectedUserIDS()
{	
	var groupId = getCurrentSelectedGroupID();
	
	select_user_ids = GroupManRPC.getUserIDSByGroupID(groupId);
	if(select_user_ids != "" && select_user_ids != null)
		select_user_ids = select_user_ids.substring(1,select_user_ids.length-1);
	
	return select_user_ids;
}

//保存角色用户
function saveGroupUser(user_ids)
{
	var insert_user_ids = removeAlreadyStr(user_ids,select_user_ids);//从ID中分析出新加的用户，已有的就不再加了
	var delete_user_ids = removeNotAlreadyStr(user_ids,select_user_ids);//从ID中分析出需要删除的用户

	selectBean = table.getSelecteBeans();
	var bean = BeanUtil.getCopy(GroupBean);	
	bean.group_id = getCurrentSelectedGroupID();	
	bean.app_id = appId;
	bean.site_id = siteId;
	bean.user_ids = insert_user_ids;
	
	if(GroupManRPC.insertGroupUser(bean,delete_user_ids))
	{
		top.msgAlert("用户组用户关联"+WCMLang.Add_success);
		table.unChekcbox();
	}else
	{
		top.msgWargin("用户组用户关联"+WCMLang.Add_fail);
	}
}


/*********************关联角色操作函数 *******************************/
// 取得group中已有角色IDS
function getCurrentSelectedRoleID()
{	
	var roleIDS = GroupManRPC.getRoleIDSByGroupID(getCurrentSelectedGroupID(),appId,siteId);
	return roleIDS;
}

// 保存选中的角色
function saveGroupRole(role_ids)
{
	selectBean = table.getSelecteBeans();
	var bean = BeanUtil.getCopy(GroupBean);	
	
	bean.group_id = getCurrentSelectedGroupID();		
	bean.app_id = appId;
	bean.site_id = siteId;
	
	if(GroupManRPC.insertRoleUserByUGroup(role_ids,bean))
	{
		top.msgAlert("用户组角色关联"+WCMLang.Add_success);		
		reloadGroupList();
	}else
	{
		top.msgWargin("用户组角色关联"+WCMLang.Add_fail);
	}
}

function openReleUserHandl(g_id)
{
	is_button_click = false;
	current_group_id = g_id;
	
	if(siteId != "")
	{
		openSelectOnlySiteUserPage('选择用户','saveGroupUser',siteId)
	}
	else
	{
		openSelectUserPage('选择用户','saveGroupUser');
	} 	
}

function getCurrentSelectedGroupID()
{
	var groupId;
	if(is_button_click)		
		groupId = table.getSelecteBeans().get(0).group_id;
	else
		groupId = current_group_id;

	return groupId;
}