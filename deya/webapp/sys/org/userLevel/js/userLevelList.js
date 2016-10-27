var UserManRPC = jsonrpc.UserManRPC;
var UserLevelBean = new Bean("com.deya.wcm.bean.org.user.UserLevelBean",true);

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "userLevel_table_list";
var con_m = new Map();

// 初始化Table
function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("userlevel_name","用户级别名称","200px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("userlevel_value","用户级别数值","120px","","",""));
	colsList.add(setTitleClos("blank"," ","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("level_table");//里面参数为外层div的id
}

// 刷新表格及翻页标签
function reloaduserLevelList()
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
	
	var beanList = UserManRPC.getUserLevelList();
	beanList =List.toJSList(beanList);
	
	table.setBeanList(beanList,"td_list");
	table.show("level_table");
	
	table.getCol("userlevel_name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:top.OpenModalWindow(\'用户级别信息\',\'/sys/org/userLevel/userLevel_view.jsp?userlevel_id='+beanList.get(i-1).userlevel_id +'\',450,245)">'+beanList.get(i-1).userlevel_name+'</a>');	
		}
	});
	
	
	Init_InfoTable(table.table_name);
}

// 翻页填充
function showTurnPage()
{
	tp.total = UserManRPC.getUserLevelCount();
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

// 新建用户级别
function addUserLevel()
{
	top.OpenModalWindow("新建用户级别","/sys/org/userLevel/userLevel_add.jsp?type=add&userlevel_id=",460,245);
}

// 更新用户级别
function updateLevel()
{
	var userlevel_id = table.getSelecteCheckboxValue("userlevel_id");
	top.OpenModalWindow("修改用户级别","/sys/org/userLevel/userLevel_add.jsp?type=update&userlevel_id="+userlevel_id,430,260);
}

// 删除用户级别
function deleteLevel()
{
	var userlevel_ids = table.getSelecteCheckboxValue("userlevel_id");
	/*
	if(UserManRPC.haveExistUser(userlevel_ids)!= 0)
	{
		top.msgWargin("选中的用户级别中关联有用户"+"<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"请取消关联后再删除！");
		return;
	}*/
	
	if(!UserManRPC.deleteUserLevel(userlevel_ids))
	{
		top.msgWargin("用户级别"+WCMLang.Delete_fail);
		return;
	}
	top.msgAlert("用户级别"+WCMLang.Delete_success);
	reloaduserLevelList();
}

// 禁止非数字输入
function checkNumberKey()
{  
   var keyCode = event.keyCode;   
   if ((keyCode >= 48 && keyCode <= 57))   
   {   
       event.returnValue = true;
   } else {   
       event.returnValue = false;
   }   
}