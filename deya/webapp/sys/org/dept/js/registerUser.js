var UserManRPC = jsonrpc.UserManRPC;
var UserBean = new Bean("com.deya.wcm.bean.org.user.UserBean",true);

var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "register_table_list";;
var con_m = new Map();



function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("username","帐号","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
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
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}

	beanList = UserManRPC.getAllUserRegisterForDB(con_m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	
	
	table.getCol("register_status").each(function(i){
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
}

function showTurnPage(){	
	
	tp.total = UserManRPC.getUserRegisterCount(con_m);			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

function openAddUserPage()
{
	window.location.href = "registerUser_add.jsp?rtype="+rtype;
}

function addUser()
{
	var bean = BeanUtil.getCopy(UserBean);
	$("#table").autoBind(bean);
	/*
	var val=new Validator();
	val.checkEmpty(bean.dept_name,"部门名称");	
	val.checkStrLength(bean.dept_name,"部门名称",80);
	val.checkStrLength(bean.dept_fullname,"部门名称",80);
	val.checkStrLength(bean.area_code,"地区代码",80);
	val.checkStrLength(bean.dept_code,"机构代码",80);

	val.checkTelephone(bean.tel,"电话",true);
	val.checkStrLength(bean.tel,"电话",80);
	val.checkFax(bean.fax,"传真",true);
	val.checkStrLength(bean.fax,"传真",80);
	val.checkEmail(bean.email,"Email",true);
	val.checkStrLength(bean.email,"Email",80);

	val.checkStrLength(bean.functions,"部门职能",150);
	val.checkStrLength(bean.dept_memo,"备注",330);	

	if(!val.getResult()){
		val.showError("error_div");
		return;
	}		
	*/
	bean.dept_id = dept_id;
	if(UserManRPC.insertUser(bean))
	{
		msgAlert("添加成功");
		window.location.href = "deptList.jsp?deptID="+dept_id+"&labNum=2";;
	}
	else
	{
		msgAlert("添加失败，请重新保存");
	}
}

//置为管理员
function setUserToAdmin()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	
	if(selectIDS == "" || selectIDS == null)
	{
		msgAlert("请选择要设置为管理员的人员");
		return;
	}else
	{
		if(DeptRPC.insertDetpManager(selectIDS,dept_id))
		{
			msgAlert("设置成功");
		}else
		{
			msgAlert("设置失败，请重新设置");
			return;
		}
	}
}

//排序触发事件
function userSortHandl(val)
{
	table.sortCol = val.substring(0,val.indexOf(","));
	table.sortType = val.substring(val.indexOf(",")+1);	
	reoloadUserList();
}

//搜索事件 
function userSearchHandl(obj)
{
	var conn_value = $(obj).parent().find("#searchkey").val();
	if(conn_value.trim() == "" ||  conn_value == null)
	{
		msgAlert("请输入搜索条件");
		return;
	}
	table.con_name = $(obj).parent().find("#searchFields").val(); 
	table.con_value = conn_value;
	reoloadUserList();
}	