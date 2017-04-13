var UserManRPC = jsonrpc.UserManRPC;
var UserBean = new Bean("com.deya.wcm.bean.org.user.UserBean",true);
var UserRegisterBean = new Bean("com.deya.wcm.bean.org.user.UserRegisterBean",true);

var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "register_table_list";
var con_m = new Map();
var dept_m = new Map();
var updateUserRegBean;

function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("username","帐号","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("dept_name","部门","120px","","",""));
	colsList.add(setTitleClos("user_realname","真实姓名","120px","","",""));
	colsList.add(setTitleClos("register_stu","帐号状态","100px","","",""));
	colsList.add(setTitleClos("actionCol","操作","120px","","",""));
	
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
		sortCol = "id";
		sortType = "desc";
	}
		
	con_m.put("start_num", tp.getStart());	
	con_m.put("page_size", tp.pageSize);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		con_m.put("con_name", table.con_name);
		con_m.put("con_value", table.con_value);
	}

	beanList = UserManRPC.getAllUserRegisterForDB(con_m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	

	dept_m = UserManRPC.getUserDeptList(beanList);// 取得部门名称
	dept_m = Map.toJSMap(dept_m);
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	table.getCol("register_stu").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).register_status == -1)
			{
				$(this).text("待开通");	
			}
			if(beanList.get(i-1).register_status == 0)
			{
				$(this).text("已开通");	
			}
			if(beanList.get(i-1).register_status == 1)
			{
				$(this).text("停用");	
			}
		}
	});
	
	// 添加部门信息
	table.getCol("dept_name").each(function(i)
	{
		if(i>0)
		{
			var mKey = beanList.get(i-1).user_id;
			$(this).text(dept_m.get(mKey));
		}
	});

	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	
	tp.total = UserManRPC.getUserRegisterCount(con_m);			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

// 开通账户操作
function activitingUserPage()
{
	var selectIDS = table.getSelecteCheckboxValue("register_id");
	if(UserManRPC.updateRegisterStatus(0,selectIDS))
	{
		reloadRoleList();
	}else
	{
		msgAlert("开通失败，请重试！");
		return;
	}
}

// 停用账户操作
function stopUserPage()
{
	var selectIDS = table.getSelecteCheckboxValue("register_id");
	if(UserManRPC.updateRegisterStatus(1,selectIDS))
	{
		reloadRoleList();
	}else
	{
		msgAlert("停用失败，请重试！");
		return;
	}
}

// 删除帐号操作
function delUserPage()
{
	var selectIDS = table.getSelecteCheckboxValue("register_id");
	if(UserManRPC.deleteRegisterByRID(selectIDS))
	{
		reloadRoleList();
	}else
	{
		msgAlert("删除失败，请重试！");
		return;
	}
}

// 用户修改操作
function updateUserPage()
{
	var userId = table.getSelecteCheckboxValue("user_id");
	OpenModalWindow("用户修改","/sys/org/user/user_update.jsp?user_id="+userId,465,210);
}

// 初始化用户修改页面
function initUpdatePage(paraUserId)
{
	if(paraUserId != null && paraUserId!=null && paraUserId != "")
	{
		var reg_list = UserManRPC.getUserRegisterListByUserID(paraUserId);
		if(reg_list != null && reg_list.size() > 0)
		{
			updateUserRegBean  = reg_list.get(0);
			$("#register_table").autoFill(updateUserRegBean);
		}
	}
}

// 用户列表刷新
function reloadRoleList()
{
	showList();	
	showTurnPage();
}

//排序触发事件
function userSortHandl(val)
{
	table.sortCol = val.substring(0,val.indexOf(","));
	table.sortType = val.substring(val.indexOf(",")+1);	
	reoloadUserList();
}

// 搜索事件 
function userSearchHandl(obj)
{
	var conn_value = $(obj).parent().find("#searchkey").val();
	if(conn_value.trim() == "" ||  conn_value == null)
	{
		msgAlert("请输入搜索条件");
		return;
	}
	table.con_name = $(obj).parent().find("#searchFields").val(); 
	changSearchCondition(table.con_name,conn_value);

	reloadRoleList();
}	

// 搜索方式改变
function changSearchCondition(conType,value)
{
	con_m.remove("con_name");
	con_m.remove("con_value");
	initSearchCondition(conType);
	
	if("con_registerid" == conType)
	{
		table.con_name = "username";
		table.con_value = "%" +value+ "%";
	}else if("con_deptname" == conType)
	{
		table.con_name = "dept_name";
		table.con_value = value;
	}
	else if("con_realname" == conType)
	{
		table.con_name = "user_realname";
		table.con_value = value;
	}
}

// 判断页面类型(如待开通帐号,开通帐号，停用帐号等页面)
function setPageStatus(rtype)
{
	if(rtype == "wait"){
		con_m.put("register_status", -1);
	}
	if(rtype == "already"){
		con_m.put("register_status", 0);
	}
	if(rtype == "stop"){
		con_m.put("register_status", 1);
	}
}

// 参数为字符串分别为 "con_registerid"---帐号
// "con_deptname"---部门
// "con_realname"---真实姓名
function initSearchCondition(condition)
{
	con_m.remove("con_registerid");
	con_m.remove("con_deptname");
	con_m.remove("con_realname");
	if("con_registerid" == condition)
	{
		con_m.put("con_registerid","selected");
	}else if("con_deptname" == condition)
	{
		con_m.put("con_deptname","selected");
	}
	else if("con_realname" == condition)
	{
		con_m.put("con_realname","selected");
	}
}

