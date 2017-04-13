var DeptRPC = jsonrpc.DeptRPC;
var UserManRPC = jsonrpc.UserManRPC;
var UserBean = new Bean("com.deya.wcm.bean.org.user.UserBean",true);

var manager_tp = new TurnPage();
var manager_beanList = null;
var manager_table = new Table();	
manager_table.table_name = "manager_table_list";

function reoloadManagerList()
{	
	showManagerList();	
	showManagerTurnPage();
	Init_InfoTable(manager_table.table_name);
}

function initManagerTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("user_realname","真实姓名","150px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("dept_name","所属部门","100px","","",""));
	colsList.add(setTitleClos("functions","职务","60px","","",""));	　
	colsList.add(setTitleClos("phone","移动电话","100px","","",""));
	colsList.add(setTitleClos("tel","固定电话","100px","","",""));
	colsList.add(setTitleClos("manager_status","用户状态","100px","","",""));
	colsList.add(setTitleClos("blank_col","&#160;","","","",""));
	
	manager_table.setColsList(colsList);
	manager_table.setAllColsList(colsList);				
	manager_table.enableSort=false;//禁用表头排序
	manager_table.onSortChange = reoloadManagerList;
	manager_table.show("manager_table");//里面参数为外层div的id
}

function showManagerList(){
			
	var sortCol = manager_table.sortCol;
	var sortType = manager_table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "manager_id";
		sortType = "desc";
	}	

	manager_beanList = DeptRPC.getDeptManagerUserList(dept_id);//第一个参数为站点ID，暂时默认为空	
	manager_beanList = List.toJSList(manager_beanList);//把list转成JS的List对象	

	manager_tp.total = manager_beanList.size();

	curr_bean = null;		
	manager_table.setBeanList(manager_beanList,"td_list");//设置列表内容的样式
	manager_table.show("manager_table");	
	
	manager_table.getCol("user_realname").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:parent.addTab(true,\'/sys/org/dept/user_view.jsp?user_id='+manager_beanList.get(i-1).user_id+'\',\'用户信息\')">'+manager_beanList.get(i-1).user_realname+'</a>');
		}
	});
	
}
//显示管理员翻页列
function showManagerTurnPage(){		
	manager_tp.show("manager_turn","simple");	
	manager_tp.onPageChange = showManagerList;
}

//删除部门管理员
function deleteDeptManager()
{
	var selectIDS = manager_table.getSelecteCheckboxValue("user_id");
	if(DeptRPC.deleteDeptManager(selectIDS,dept_id))
	{
        parent.msgAlert("部门管理员"+WCMLang.Delete_success);
		reoloadManagerList();
	}else
	{
        parent.msgAlert("部门管理员"+WCMLang.Delete_fail);
		return;
	}
}

//打开部门管理员窗口
var selected_dept_id;
function addDeptManager(dept_ids)
{	
	if(dept_ids != "" && dept_ids != null)
		selected_dept_id = dept_ids;
	else
		selected_dept_id = dept_id;

	//OpenModalWindow("修改部门管理员","/sys/org/dept/deptMan_add.jsp?dept_id="+selected_dept_id,610,510);

	openSelectUserPage("选择部门管理员","saveManager");
}

//得到该部门下的管理员ID
var old_dept_manager = "";//原有的部门管理员,需要传到方法中,删除role_user关联表
function getSelectedUserIDS()
{	
	old_dept_manager = DeptRPC.getManagerIDSByDeptID(selected_dept_id);
	return old_dept_manager;
}

//保存部门管理员
function saveManager(user_ids)
{
	if(DeptRPC.updateDetpManager(user_ids,old_dept_manager,selected_dept_id))
	{
        parent.msgAlert("部门管理员"+WCMLang.Set_success);
        parent.getCurrentFrameObj().reoloadManagerList();
		
	}else{
        parent.msgWargin("部门管理员"+WCMLang.Set_fail);
	}
}

