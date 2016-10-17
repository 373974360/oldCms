var DeptRPC = jsonrpc.DeptRPC;
var DeptLevelBean = new Bean("com.deya.wcm.bean.org.dept.DeptLevelBean",true);

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "deptLevel_table_list";

// 初始化Table
function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("deptlevel_name","部门级别名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("deptlevel_value","部门级别数值","120px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("level_table");//里面参数为外层div的id
}

// 加载初始化Table和翻页信息
function reloadDeptLevelList()
{
	showList();
	showTurnPage();
}

// 填充Table数据
function showList()
{
		var sortCol = table.sortCol;
	var sortType = table.sortType;	
	
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "id";
		sortType = "desc";
	}
	
	var beanList = DeptRPC.getDeptLevelList();
	beanList =List.toJSList(beanList);
	tp.total = beanList.size();
	
	table.setBeanList(beanList,"td_list");
	table.show("level_table");
	
	table.getCol("deptlevel_name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:top.OpenModalWindow(\'部门级别信息\',\'/sys/org/deptLevel/deptLevel_view.jsp?deptlevel_id='+beanList.get(i-1).deptlevel_id +'\',450,245)">'+beanList.get(i-1).deptlevel_name+'</a>');	
		}
	});
	
	Init_InfoTable(table.table_name);
}

function showTurnPage()
{
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

// 新建部门级别
function addDeptLevel()
{
	top.OpenModalWindow("新建部门级别","/sys/org/deptLevel/deptLevel_add.jsp?type=add&deptlevel_id=",460,245);
}

// 更新部门级别
function updateLevel()
{
	var deptlevel_id = table.getSelecteCheckboxValue("deptlevel_id");
	top.OpenModalWindow("修改部门级别","/sys/org/deptLevel/deptLevel_add.jsp?type=update&deptlevel_id="+deptlevel_id,430,260);
}

// 删除部门级别
function deleteLevel()
{
	var deptlevel_ids = table.getSelecteCheckboxValue("deptlevel_id");
	/*
	if(DeptRPC.haveExistDept(deptlevel_ids)!= 0)
	{
		top.msgWargin("选中的部门级别中关联有部门"+"<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"请取消关联后再删除！");
		return;
	}*/
	
	if(!DeptRPC.deleteDeptLevel(deptlevel_ids))
	{
		top.msgAlert("部门级别"+WCMLang.Delete_fail);
		return;
	}
	top.msgAlert("部门级别"+WCMLang.Delete_success);
	reloadDeptLevelList();
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