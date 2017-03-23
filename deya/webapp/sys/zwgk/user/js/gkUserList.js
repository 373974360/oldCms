var SiteUserRPC = jsonrpc.SiteUserRPC;
var GKNodeRPC = jsonrpc.GKNodeRPC;
var DeptRPC = jsonrpc.DeptRPC;
var GKNodeBean = new Bean("com.deya.wcm.bean.zwgk.node.GKNodeBean",true);

var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "menu_table_list";;
var con_m = new Map();
var nameMap = new Map();


function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("user_name","用户名称","200px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("role_names","关联角色","","","",""));
	colsList.add(setTitleClos("dept_name","用户部门","200px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){			

	beanList = SiteUserRPC.getSiteUserList(node_id, app_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();			

	nameMap = SiteUserRPC.getSiteUserInfo(beanList);
	nameMap = Map.toJSMap(nameMap);

	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	
	
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
}

function showTurnPage(){	
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

//得到该角色下已有的用户ID
function getSelectedUserIDS()
{
	if(user_type=='admin')
		return jsonrpc.SiteRPC.getUsersBySiteId(app_id,node_id);
	if(user_type=='user')
	{	
		return table.getAllCheckboxValue("user_id");
	}
}

//保存管理员
function saveUser(ids)
{	
	var old_user_ids = table.getAllCheckboxValue("user_id");
	var insert_user_ids = removeAlreadyStr(ids,old_user_ids);//从ID中分析出新加的用户，已有的就不再加了
	var delete_user_ids = removeNotAlreadyStr(ids,old_user_ids);//从ID中分析出需要删除的用户

	if(jsonrpc.SiteRPC.insertSiteUserManager(app_id,node_id,insert_user_ids,delete_user_ids))
	{  
		loadMenuTable();
		msgAlert("节点管理员"+WCMLang.Add_success);
		table.unChekcbox();
	}
	else
	{
		msgWargin("节点管理员"+WCMLang.Add_fail);
	}
}

// 保存站点/节点用户信息
function saveSiteUser(user_ids)
{
	var old_user_ids = table.getAllCheckboxValue("user_id");
	//这里需要判断一下，把要添加的用户ID和原有的用户ID做比较，把新加的用户和要删除的用户分别提取出来，因为删除的用户必须也要删除他的角色关联，所以不能做全删全写的操作了

	var insert_user_ids = removeAlreadyStr(user_ids,old_user_ids);
	var delete_user_ids = removeNotAlreadyStr(user_ids,old_user_ids);
		
	if(SiteUserRPC.linkSiteUser(insert_user_ids,delete_user_ids, node_id, app_id))
	{
		loadMenuTable();
		msgAlert("站点用户关联"+WCMLang.Add_success);
		table.unChekcbox();
	}else
	{
		msgWargin("站点用户关联"+WCMLang.Add_fail);
	}
}

// 删除站点用户信息
function deleteSiteUser()
{
	var user_ids = table.getSelecteCheckboxValue("user_id");
	if( !SiteUserRPC.deleteSiteUser(user_ids, node_id, app_id))
	{
		msgWargin("站点用户"+WCMLang.Delete_fail);
		return;
	}
	msgAlert("站点用户"+WCMLang.Delete_success);
	loadMenuTable();
}

//关联角色
function saveRoleUser(role_ids)
{
	var user_id = table.getSelecteCheckboxValue("user_id");
	var RoleUserBean = new Bean("com.deya.wcm.bean.org.role.RoleUserBean",true);
	var bean = BeanUtil.getCopy(RoleUserBean);	
	bean.role_id = role_ids;
	bean.app_id = app_id;
	bean.site_id = node_id;
	bean.user_id = user_id;

	if(jsonrpc.RoleRPC.insertRoleUserByUser(bean))
	{
		msgAlert("角色用户关联"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().loadMenuTable();
		table.unChekcbox();
	}else
	{
		msgWargin("角色用户关联"+WCMLang.Add_fail);
	}
}

//得到已选过的角色
function getCurrentSelectedRoleID()
{
	var selectedBean = table.getSelecteBeans();	
	return jsonrpc.RoleRPC.getRoleIDSByUserAPP(selectedBean.get(0).user_id,app_id,node_id);	
}

