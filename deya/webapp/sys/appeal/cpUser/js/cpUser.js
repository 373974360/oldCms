var CpDeptRPC = jsonrpc.CpDeptRPC;
var CpDeptBean = new Bean("com.deya.wcm.bean.appeal.cpDept.CpDeptBean",true);

var CpUserRPC = jsonrpc.CpUserRPC;
var CpUserBean = new Bean("com.deya.wcm.bean.appeal.cpUser.CpUserBean",true);
var RoleUserBean = new Bean("com.deya.wcm.bean.org.role.RoleUserBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "cpUser_table";

//展示树形
function showCpDeptTree()
{
	json_data = eval(CpDeptRPC.getDeptTreeJsonStr(1));
	setLeftMenuTreeJsonData(json_data);
	initMenuTree();
}

//重新载入 树&列表  
function reloadCpUserList()
{	
	showList();	
	showTurnPage();	
	getAllUserMap();//刷新时得到所有用户集合，用于在添加时间判断是否加过
}

//得到诉求系统中所有的用户
var user_map = new Map();
function getAllUserMap()
{
	user_map = CpUserRPC.getAllReleUserMap();
	user_map = Map.toJSMap(user_map);
}


/* 初始化表格 */
function initTable(){
	
	var colsMap = new Map();
	var colsList = new List();	
	                         //英文名，显示名，宽，高，样式名，点击事件
	colsList.add(setTitleClos("user_realname","真实姓名","100px","","",""));
	colsList.add(setTitleClos("accnumber","用户账号","100px","","",""));
	colsList.add(setTitleClos("role_names","关联角色","250px","","",""));　
	colsList.add(setTitleClos("model_names","关联业务","","","",""));

	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

//展示部门树形
function showCpDeptTree()
{
	json_data = eval(CpDeptRPC.getDeptTreeJsonStr(1));
	setLeftMenuTreeJsonData(json_data);
	initMenuTree();
}

//根据部门节点   展示该部门的用户列表（不含子部门用户）
function showList(){
	var m = new Map();	
	
	//根据部门得到该部门的节点（第一级节点）列表
	beanList = CpUserRPC.getUserListByDept(dept_id);
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	tp.total = beanList.size();

	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	

	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
				
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

//得到该部门下已有的用户ID字符串
function getSelectedUserIDS()
{	
	return "";
}

//得到选中人员已关联过的业务
function getSelectedModelID()
{
	var selectedBean = table.getSelecteBeans();	
	return selectedBean.get(0).model_ids;
}

//保存用户与业务的关联
function saveModelUser(model_ids)
{
	var user_ids = table.getSelecteCheckboxValue("user_id");
	if(jsonrpc.SQModelRPC.insertModelReleUser(model_ids,user_ids))
	{
		msgAlert("用户与业务关联"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadCpUserList();
		table.unChekcbox();
	}
	else
	{
		msgWargin("用户与业务关联"+WCMLang.Add_fail);
	}
}

//得到已选过的角色
function getCurrentSelectedRoleID()
{
	var selectedBean = table.getSelecteBeans();	
	return selectedBean.get(0).role_ids;
}

//关联角色
function saveRoleUser(role_ids)
{
	var user_id = table.getSelecteCheckboxValue("user_id");
	var bean = BeanUtil.getCopy(RoleUserBean);	
	bean.role_id = role_ids;
	bean.app_id = "appeal";
	bean.site_id = "";
	bean.user_id = user_id;

	if(jsonrpc.RoleRPC.insertRoleUserByUser(bean))
	{
		msgAlert("角色用户关联"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadCpUserList();
		table.unChekcbox();
	}else
	{
		msgWargin("角色用户关联"+WCMLang.Add_fail);
	}
}

//保存  用户与机构的关联
function saveDeptUser(user_ids)
{
	if(user_ids != "" && user_ids != null)
	{
		//判断选的用户是否被添加过
		if(userISExist(user_ids) == false)
			return;

		if(CpUserRPC.insertCpUser(dept_id,user_ids))
		{
			msgAlert("用户添加"+WCMLang.Add_success);
			CloseModalWindow();
			getCurrentFrameObj().reloadCpUserList();
			table.unChekcbox();
		}else
		{
			msgWargin("用户添加"+WCMLang.Add_fail);
		}
	}
}

function userISExist(user_ids)
{
	var names = CpUserRPC.userISExist(user_ids);
	if(names != "" && names != null)
	{
		msgWargin(names+" 这些用户已经添加过，请重新选择");
		return false;
	}else
		return true;
}

//删除  用户与机构的关联
function deleteCpDept()
{
	var selectIDS = table.getSelecteCheckboxValue("user_id");
	if(CpUserRPC.deleteCpUser(dept_id,selectIDS))
	{
		msgAlert("用户与机构关联"+WCMLang.Delete_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadCpUserList();
	}else
	{
		msgWargin("用户与机构关联"+WCMLang.Delete_fail);
	}
}

//修改  用户与机构的关联
function updateRole()
{
	var bean = BeanUtil.getCopy(RoleBean);	
	$("#cpUser_table").autoBind(bean);

	if(!standard_checkInputInfo("cpUser_table"))
	{
		return;
	}
	bean.a_app_id = getAppIDS();
	bean.app_id = app_id;
	bean.role_id = role_id;
	if(RoleRPC.updateRole(bean))
	{
		msgAlert("用户与机构关联"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadCpUserList();
	}
	else
	{
		msgWargin("用户与机构关联"+WCMLang.Add_fail);
	}
}


