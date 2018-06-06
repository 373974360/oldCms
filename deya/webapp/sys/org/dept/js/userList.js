var UserManRPC = jsonrpc.UserManRPC;
var UserBean = new Bean("com.deya.wcm.bean.org.user.UserBean",true);
var UserRegisterBean = new Bean("com.deya.wcm.bean.org.user.UserRegisterBean",true);

var val=new Validator();
var serarch_con = "";//查询条件
var user_tp = new TurnPage();
var user_beanList = null;
var user_table = new Table();	
user_table.table_name = "user_table_list";

function reoloadUserList()
{	
	initUserTable();
	showUserList();	
	showUserTurnPage();
	Init_InfoTable(user_table.table_name);
}

function initUserTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("user_id","ID","20px","","",""));
	colsList.add(setTitleClos("user_realname","真实姓名","150px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("functions","职务","60px","","",""));
	colsList.add(setTitleClos("phone","移动电话","100px","","",""));
	colsList.add(setTitleClos("userlevel_value","级别数值","70px","","",""));	
	colsList.add(setTitleClos("user_status","用户状态","70px","","",""));
	colsList.add(setTitleClos("is_publish","发布状态","70px","","",""));
	colsList.add(setTitleClos("user_sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("blank_col","&#160;","","","",""));
	
	user_table.setColsList(colsList);
	user_table.setAllColsList(colsList);				
	user_table.enableSort=false;//禁用表头排序
	user_table.onSortChange = reoloadUserList;
	user_table.show("user_table");//里面参数为外层div的id
}

function showUserList(){			
	var sortCol = user_table.sortCol;
	var sortType = user_table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "sort";
		sortType = "asc";
	}
	
	var m = new Map();
	m.put("dept_id", dept_id);
	m.put("start_num", user_tp.getStart());	
	m.put("page_size", user_tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	if(user_table.con_value.trim() != "" && user_table.con_value != null)
	{
		m.put("con_name", user_table.con_name);
		m.put("con_value", user_table.con_value);
	}

	user_beanList = UserManRPC.getUserListByDeptIDForDB(m);//第一个参数为站点ID，暂时默认为空	
	user_beanList = List.toJSList(user_beanList);//把list转成JS的List对象
	//alert(user_beanList.size());
	user_tp.total = user_beanList.size();
	curr_bean = null;		
	user_table.setBeanList(user_beanList,"td_list");//设置列表内容的样式
	user_table.show("user_table");	
	
	user_table.getCol("user_realname").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:top.addTab(true,\'/sys/org/dept/user_view.jsp?user_id='+user_beanList.get(i-1).user_id+'\',\'用户信息\')">'+user_beanList.get(i-1).user_realname+'</a>');
		}
	});
	
	user_table.getCol("sex").each(function(i){
		if(i>0)
		{
			if(user_beanList.get(i-1).sex == 0)
			{
				$(this).text("女");	
			}
			else
				$(this).text("男");
		}
	});
	user_table.getCol("user_status").each(function(i){
		if(i>0)
		{
			if(user_beanList.get(i-1).user_status == 0)			
				$(this).text("正常");				
			if(user_beanList.get(i-1).user_status == 1)
				$(this).text("停用");
		}
	});
	user_table.getCol("is_publish").each(function(i){
		if(i>0)
		{
			if(user_beanList.get(i-1).is_publish == 0)			
				$(this).html("&#160;");				
			if(user_beanList.get(i-1).is_publish == 1)
				$(this).text("已发布");
		}
	});

	user_table.getCol("user_sort_col").each(function(i){
		if(i>0)
		{
			$(this).html(getSortColStr());			
		}
	});
}

function showUserTurnPage(){	
	/*
	if(user_table.con_value.trim() != "" && user_table.con_value != null)
	{
		var m = new Map();
		m.put("con_name", user_table.con_name);
		m.put("con_value", user_table.con_value);
		m.put("dept_id", dept_id);	
		user_tp.total = UserManRPC.getUserCountByDeptIDForDB(m);
	}
	else
	{
		user_tp.total = UserManRPC.getUserCountByDeptID(dept_id);
	}	*/
	user_tp.show("user_turn","simple");	
	user_tp.onPageChange = reoloadUserList;
}

function openAddUserPage()
{
	window.location.href = "user_add.jsp?dept_id="+dept_id;
}
var isAddReg = false;
function checkUserInfo()
{
	
	isFocus = true;
	val.check_result = true;
	$("#user_table input[type=text]").each(function(){
		$(this).blur();
	});
	$("#user_table input[type=password]").each(function(){
		$(this).blur();
	});
	$("#user_table textarea").each(function(){
		$(this).blur();
	});
	
	if($("#isAddReg").is(':checked'))
	{
		isAddReg = true;
		$("#reg_table input[type=text]").each(function(){
			$(this).blur();
		});		
	}

	if(!val.getResult()){		
		return false;
	}	
	
	if($("#isAddReg").is(':checked'))
	{		
		if($("#username").val().length < 2)
		{
			val.showError("username","帐号名称不能少于2个字符");
			return false;
		}
        if($("#password").val().length < 6)
        {
            val.showError("password","密码不能少于6个字符");
            return false;
        }
        else
        {
            //校验密码级别
            var level = $("#password").val().replace(/^(?:(?=.{4})(?=.*([a-z])|.)(?=.*([A-Z])|.)(?=.*(\d)|.)(?=.*(\W)|.).*|.*)$/, "$1$2$3$4").length;
            if(level < 2)
            {
                val.showError("password","密码设置太简单，请重新设置，建议6位以上字母+数字，区分大小写！");
                return false;
            }
        }
        if($("#password").val() != $("#check_password").val())
        {
            val.showError("password","两次输入的密码不一致");
            return false;
        }
	}

	return true;
}

//添加人员
function addUser()
{
	
	var bean = BeanUtil.getCopy(UserBean);	
	$("#user_table").autoBind(bean);
	var reg_bean = BeanUtil.getCopy(UserRegisterBean);
	$("#reg_table").autoBind(reg_bean);
	
	if(!checkUserInfo())
	{
		return;
	}

	//判断帐号名是否存在
	if(isAddReg == true)
	{
		if(UserManRPC.registerISExist(reg_bean.username,0))
		{
			top.msgWargin(WCMLang.register_iSExist);
			return;
		}
	}	
	bean.dept_id = dept_id;
	if(UserManRPC.insertUser(bean,reg_bean,isAddReg))
	{
		reloadUserPageList();
	}
	else
	{
		top.msgWargin("用户信息"+WCMLang.Add_fail);
	}
}

//修改人员
function updateUser()
{
	var bean = BeanUtil.getCopy(UserBean);	
	$("#user_table").autoBind(bean);

	if(!checkUserInfo())
	{
		return;
	}		
	bean.user_id = user_id;
	if(UserManRPC.updateUser(bean))
	{
		reloadUserPageList();
	}
	else
	{
		top.msgWargin("用户信息"+WCMLang.Add_fail);
	}
}

//修改人员状态
function setUserStatus(status)
{
	var selectIDS = user_table.getSelecteCheckboxValue("user_id");
	if(UserManRPC.updateUserStatus(status,selectIDS))
	{
		top.msgAlert("用户状态"+WCMLang.Set_success);
		reoloadUserList();
	}else
	{
		top.msgWargin("用户状态"+WCMLang.Set_fail);
		return;
	}	
}

//删除人员
function deleteUser()
{
	var selectIDS = user_table.getSelecteCheckboxValue("user_id");
	if(UserManRPC.deleteUser(selectIDS))
	{
		top.msgAlert("用户信息"+WCMLang.Delete_success);
		reoloadUserList();
	}else
	{
		top.msgWargin("用户信息"+WCMLang.Delete_fail);
	}
}

//刷新列表
function reloadUserPageList()
{
	top.msgAlert("用户信息"+WCMLang.Add_success);
	window.location.href = "deptList.jsp?deptID="+dept_id;
}

//打开修改窗口
function openUpdateUserPage()
{	
	var selectIDS = user_table.getSelecteCheckboxValue("user_id");
	window.location.href = "user_add.jsp?dept_id="+dept_id+"&user_id="+selectIDS;		
}

//打开帐号管理窗口
function openRegPage()
{	
	var selectIDS = user_table.getSelecteCheckboxValue("user_id");
	top.OpenModalWindow("维护帐号","/sys/org/dept/registerUser_update.jsp?user_id="+selectIDS,450,210);	
}

//置为管理员
function setUserToAdmin()
{
	var selectIDS = user_table.getSelecteCheckboxValue("user_id");	
	var old_main_user = DeptRPC.getManagerIDSByDeptID(dept_id);//得到原有的管理员ID
	if(old_main_user != "" && old_main_user != null)
	{
		var tempA = selectIDS.split(",");
		selectIDS = "";
		old_main_user = ","+old_main_user+",";
		for(var i=0;i<tempA.length;i++)
		{
			if(old_main_user.indexOf(","+tempA[i]+",") == -1)
			{
				selectIDS += ","+tempA[i];
			}
		}		
		if(selectIDS != "")
		{
			selectIDS = selectIDS.substring(1);
		}
		else
		{
			top.msgAlert("部门管理员"+WCMLang.Set_success);
			user_table.unChekcbox();
			return;
		}
	}
	if(DeptRPC.insertDetpManager(selectIDS,dept_id))
	{
		top.msgAlert("部门管理员"+WCMLang.Set_success);
		reoloadManagerList();
		user_table.unChekcbox();
	}else
	{
		top.msgWargin("部门管理员"+WCMLang.Set_fail);
		return;
	}	
}

//排序触发事件
function userSortHandl(val)
{
	user_table.sortCol = val.substring(0,val.indexOf(","));
	user_table.sortType = val.substring(val.indexOf(",")+1);	
	reoloadUserList();
}

//搜索事件 
function userSearchHandl(obj)
{
	var conn_value = $(obj).parent().find("#searchkey").val();
	if(conn_value.trim() == "" ||  conn_value == null)
	{
		top.msgAlert(WCMLang.Search_empty);
		return;
	}
	user_table.con_name = $(obj).parent().find("#searchFields").val(); 
	user_table.con_value = conn_value;
	reoloadUserList();
}	

//移动用户
function moveUser(dept_id)
{
	if(dept_id != "" && dept_id != null)
	{
		var selectIDS = user_table.getSelecteCheckboxValue("user_id");	
		var move_m = new Map();

		move_m.put("dept_id",dept_id);
		move_m.put("user_ids",selectIDS);
		if(UserManRPC.moveUser(move_m)){
			top.msgAlert("用户"+WCMLang.Move_success);
			reoloadUserList();
		}else
		{
			top.msgWargin("用户"+WCMLang.Move_fail);
			return;
		}
	}
}

function showRegTable(obj)
{
	if($(obj).is(':checked'))
		$("#reg_table").show();
	else
		$("#reg_table").hide();
}

//得到用户级别
function getUserLevelList()
{
	var level_list = UserManRPC.getUserLevelList();
	level_list = List.toJSList(level_list);
	$("#userlevel_value").addOptions(level_list,"userlevel_value","userlevel_name");
}

//保存用户排序
function saveUserSort()
{
	var user_ids = user_table.getAllCheckboxValue("user_id");
	if(user_ids != "" && user_ids != null)
	{
		if(UserManRPC.saveUserSort(user_ids))
		{
			top.msgAlert(WCMLang.Sort_success);			
		}
		else
		{
			top.msgWargin(WCMLang.Sort_fail);
			return;
		}
	}
}