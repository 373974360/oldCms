var RoleRPC = jsonrpc.RoleRPC;
var RoleBean = new Bean("com.deya.wcm.bean.org.role.RoleBean",true);
var RoleUserBean = new Bean("com.deya.wcm.bean.org.role.RoleUserBean",true);
var RoleUGroupBean = new Bean("com.deya.wcm.bean.org.role.RoleUGroupBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "role_table";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件

function reloadRoleList()
{
	showTurnPage();
	showList();	
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("role_name","角色名称","200px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("action_col","管理操作","150px","","",""));
	colsList.add(setTitleClos("space_col"," ","","","",""));

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
		sortCol = "role_id";
		sortType = "desc";
	}
	
	var m = new Map();
	if(site_id != "" && site_id != null)
		m.put("site_id", site_id);
	m.put("app_id", app_id);
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}

	beanList = RoleRPC.getRoleListForDB(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("role_name").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).app_id == app_id)
				$(this).html('<a href="javascript:openViewRolePage('+beanList.get(i-1).role_id+')">'+beanList.get(i-1).role_name+'</a>');
		}
	});	

	table.getCol("action_col").each(function(i){
		if(i>0)
		{
			var str = '<a href="javascript:openReleUserHandl('+(i-1)+')">关联用户</a>&#160;&#160;';
			/*
			if(beanList.get(i-1).app_id != app_id)
			{
				str += '<a href="javascript:openReleOperateHandl('+(i-1)+',false)">查看权限</a>';
			}else
				str += '<a href="javascript:openReleOperateHandl('+(i-1)+',true)">关联权限</a>';
			*/
			if(beanList.get(i-1).app_id == app_id){
                str += '<a href="javascript:openReleOperateHandl('+(i-1)+',true)">关联权限</a>';
            }else{
                str += '<a href="javascript:openReleOperateHandl('+(i-1)+',false)">查看权限</a>';
            }

			$(this).html(str);
		}
	});	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	var m = new Map();
	m.put("app_id", app_id);
	if(site_id != "" && site_id != null)
		m.put("site_id", site_id);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);			
	}	
	tp.total = RoleRPC.getRoleCountForDB(m);
			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//打开查看窗口
function openViewRolePage(r_id)
{
	var height = 190;
	if(app_id == "system"|| app_id == "control")
		height = 360;
	top.OpenModalWindow("维护角色","/sys/org/role/role_add.jsp?app="+app_id+"&role_id="+r_id,450,height);
}

//打开添加窗口
function openAddRolePage()
{
	var height = 190;
	if(app_id == "system" || app_id == "control")
		height = 360;
	top.OpenModalWindow("维护角色","/sys/org/role/role_add.jsp?app="+app_id+"&site_id="+site_id,450,height);	
}

//打开修改窗口
function openUpdateRolePage()
{
	var role_names = isSystemRole();	

	if(role_names != "")
	{
		top.msgWargin(role_names.substring(1)+" 角色为系统角色,不允许修改");
		return;
	}else
	{
		var height = 190;
		if(app_id == "system" || app_id == "control")
			height = 360;
		var selectIDS = table.getSelecteCheckboxValue("role_id");
		top.OpenModalWindow("维护角色","/sys/org/role/role_add.jsp?app="+app_id+"&role_id="+selectIDS,450,height);
	}
}

//得到所属应用的ID
function getAppIDS()
{
	var ids = "";
	$(":checkbox[checked]").each(function(){
		ids += ","+$(this).val();
	});
	if(ids != "")
		ids = ids.substring(1);

	return ids;
}

//添加角色
function addRole()
{
	var bean = BeanUtil.getCopy(RoleBean);	
	$("#role_table").autoBind(bean);

	if(!standard_checkInputInfo("role_table"))
	{
		return;
	}

	bean.a_app_id = getAppIDS();
	bean.app_id = app_id;
	bean.site_id = top.getCurrentFrameObj().site_id;
	if(RoleRPC.insertRole(bean))
	{
		top.msgAlert("角色信息"+WCMLang.Add_success);		
		top.CloseModalWindow();
		top.getCurrentFrameObj().reloadTurnPage();
		top.getCurrentFrameObj().reloadRoleList();
	}
	else
	{
		top.msgWargin("角色信息"+WCMLang.Add_fail);
	}
}
//修改角色
function updateRole()
{
	var bean = BeanUtil.getCopy(RoleBean);	
	$("#role_table").autoBind(bean);

	if(!standard_checkInputInfo("role_table"))
	{
		return;
	}
	bean.a_app_id = getAppIDS();
	bean.app_id = app_id;
	bean.role_id = role_id;
	bean.site_id = top.getCurrentFrameObj().site_id;
	if(RoleRPC.updateRole(bean))
	{
		top.msgAlert("角色信息"+WCMLang.Add_success);			
		top.CloseModalWindow();
		top.getCurrentFrameObj().reloadRoleList();
	}
	else
	{
		top.msgWargin("角色信息"+WCMLang.Add_fail);
	}
}

//判断是否是系统角色
function isSystemRole()
{
	var role_names = "";
	var selectBeanList = table.getSelecteBeans();
	for(var i=0;i<selectBeanList.size();i++)
	{
		/*
		if(selectBeanList.get(i).app_id != app_id)
		{
			role_names += ","+selectBeanList.get(i).role_name;
		}*/
		//判断ID小于100的不能删除		
		if(selectBeanList.get(i).role_id < 100 || selectBeanList.get(i).app_id != app_id)
		{
			role_names += ","+selectBeanList.get(i).role_name;
		}
	}
	return role_names;
}

//删除角色
function deleteRole()
{	
	var role_names = isSystemRole();	

	if(role_names != "")
	{
		top.msgWargin(role_names.substring(1)+" 角色为系统角色,不允许删除");
		return;
	}
	else
	{
		var selectIDS = table.getSelecteCheckboxValue("role_id");

		var delCheckStr = RoleRPC.deleteRoleBeforeChecked(selectIDS,app_id,site_id);
		if(delCheckStr != "" && delCheckStr != null)
		{	
			delCheckStr = delCheckStr.substring(0,delCheckStr.length-1);
			delCheckStr = delCheckStr.replace("user","用户").replace("group","用户组").replace("workflow","工作流")
			top.msgWargin("该角色还关联 "+delCheckStr+" 信息,请先删除与其关联关系");
			return;
		}
		
		if(RoleRPC.deleteRole(selectIDS))
		{
			top.msgAlert("角色信息"+WCMLang.Delete_success);
			reloadRoleList();
		}else
		{
			top.msgWargin("角色信息"+WCMLang.Delete_fail);
		}
	}
}

//搜索
function roleSearchHandl(obj)
{
	var con_value = $(obj).parent().find("#searchkey").val();
	if(con_value.trim() == "" ||  con_value == null)
	{
		top.msgAlert(WCMLang.Search_empty);
		return;
	}
	table.con_name = "role_name"; 
	table.con_value = con_value;
	reloadRoleList();
}

//得到该角色下已有的用户ID
var select_role_user_ids = "";
function getSelectedUserIDS()
{
	var r_id = "";
	select_role_user_ids = "";	
	
	r_id = getCurrentSelectedBean().role_id;

	select_role_user_ids = RoleRPC.getUsersByRole(r_id,app_id,site_id);
	return select_role_user_ids;
}

var select_role_group_ids = "";
function getSelectedGroupIDS()
{
	var r_id = "";
	select_role_group_ids = "";	
	
	r_id = getCurrentSelectedBean().role_id;

	select_role_group_ids = RoleRPC.getGroupsByRole(r_id,app_id,site_id);
	return select_role_group_ids;
}

//保存角色用户,用户组关联
function saveRoleUserGroup(user_ids,group_ids)
{
	var insert_user_ids = removeAlreadyStr(user_ids,select_role_user_ids);//从ID中分析出新加的用户，已有的就不再加了
	var delete_user_ids = removeNotAlreadyStr(user_ids,select_role_user_ids);//从ID中分析出需要删除的用户

	var insert_group_ids = removeAlreadyStr(group_ids,select_role_group_ids);//从ID中分析出新加的用户，已有的就不再加了
	var delete_group_ids = removeNotAlreadyStr(group_ids,select_role_group_ids);//从ID中分析出需要删除的用户


	var bean = BeanUtil.getCopy(RoleUserBean);	
	var group_bean = BeanUtil.getCopy(RoleUGroupBean);
	
	bean.role_id = getCurrentSelectedBean().role_id;
	group_bean.role_id = getCurrentSelectedBean().role_id;	
		
	bean.app_id = app_id;
	bean.site_id = site_id;
	bean.user_id = insert_user_ids;	
	
	group_bean.app_id = app_id;
	group_bean.site_id = site_id;
	group_bean.group_id = insert_group_ids;


	if(RoleRPC.insertRoleUserByRole(bean,delete_user_ids) && RoleRPC.insertRoleUGroupByRole(group_bean,delete_group_ids))
	{
		top.msgAlert("角色用户关联"+WCMLang.Add_success);
		table.unChekcbox();
	}else
	{
		top.msgWargin("角色用户关联"+WCMLang.Add_fail);
	}
}

//保存角色用户
function saveRoleUser(user_ids)
{
	var insert_user_ids = removeAlreadyStr(user_ids,select_role_user_ids);//从ID中分析出新加的用户，已有的就不再加了
	var delete_user_ids = removeNotAlreadyStr(user_ids,select_role_user_ids);//从ID中分析出需要删除的用户
	
	var bean = BeanUtil.getCopy(RoleUserBean);	
	bean.role_id = getCurrentSelectedBean().role_id;
	bean.app_id = app_id;
	bean.site_id = site_id;
	bean.user_id = insert_user_ids;	

	if(RoleRPC.insertRoleUserByRole(bean,delete_user_ids))
	{
		top.msgAlert("角色用户关联"+WCMLang.Add_success);
		table.unChekcbox();
	}else
	{
		top.msgWargin("角色用户关联"+WCMLang.Add_fail);
	}
}

//得到已选择过的权限ID
function getSelectedRoleIDS()
{
	var role_id = "";
	
	role_id = getCurrentSelectedBean().role_id;

	return RoleRPC.getOptIDSByRoleID(role_id);
}

//保存角色写权限关联
function saveRoleOperate(opt_ids)
{
	var role_id = "";
	
	role_id = getCurrentSelectedBean().role_id;

	if(RoleRPC.insertOptReleRole(role_id,opt_ids))
	{
		top.msgAlert("角色权限关联"+WCMLang.Add_success);
		table.unChekcbox();
	}else{
		top.msgWargin("角色权限关联"+WCMLang.Add_fail);
	}
}

function openReleUserHandl(num)
{
	is_button_click = false;
	current_role_bean = beanList.get(num);
	if(app_id == "appeal")
	{		
		openSelectSQUserPage('选择用户','saveRoleUser');			
	}else
	{
		if(site_id != "")
		{
			openSelectSiteUserPage('选择用户','saveRoleUserGroup',site_id);				
		}
		else
		{
			openSelectUserPage('选择用户','saveRoleUser');
		}
	}
}

function openReleOperateHandl(num,update_status)
{
	is_button_click = false;
	current_role_bean = beanList.get(num);
	openSelectOperatePage('选择权限','saveRoleOperate',update_status);
}

function getCurrentSelectedBean()
{	
	if(is_button_click)		
		return table.getSelecteBeans().get(0);
	else
		return current_role_bean;	
}