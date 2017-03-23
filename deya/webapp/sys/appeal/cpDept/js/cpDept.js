var CpDeptRPC = jsonrpc.CpDeptRPC;
var CpDeptBean = new Bean("com.deya.wcm.bean.appeal.cpDept.CpDeptBean",true);


var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "cpDept_table";;

//节点有变化时，重新载入  列表    
function reloadCpDeptList()
{
	//showCpDeptTree();
	showList();	
	showTurnPage();	
}

/* 初始化表格 */
function initTable(){
	
	var colsMap = new Map();
	var colsList = new List();	
	                         //英文名，显示名，宽，高，样式名，点击事件
	colsList.add(setTitleClos("dept_id","ID","30px","","",""));　
	colsList.add(setTitleClos("dept_name","机构名称","200px","","",""));　
	colsList.add(setTitleClos("dept_level","深度/级别","100px","","",""));
	colsList.add(setTitleClos("dept_sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("span_col","&#160;","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

//展示树形
function showCpDeptTree()
{
	json_data = eval(CpDeptRPC.getDeptTreeJsonStr(1));
	setLeftMenuTreeJsonData(json_data);
	initMenuTree();//不同页面中不同的树形点击事件
	//选中树节点
	treeNodeSelected(dept_id);
}

//根据节点 展示子节点列表
function showList(){	
	
	//根据根的节点ID得到所有子节点（第一级节点）列表
	beanList = CpDeptRPC.getChildDeptList(dept_id);
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	tp.total = beanList.size();

	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("dept_name").each(function(i){
		if(i>0)
		{			
			$(this).css({"text-align":"left"});	
			$(this).html('<a href="javascript:OpenModalWindow(\'修改注册机构\',\'/sys/appeal/cpDept/cpDept_add.jsp?dept_id='+beanList.get(i-1).dept_id+'\',450,190);">'+beanList.get(i-1).dept_name+'</a>');
		}
	});	
	
	table.getCol("dept_sort_col").each(function(i){	
		if(i>0)
		{
			$(this).html(getSortColStr());			
		}
	});
	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
				
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}


//打开添加窗口
function openAddCpDeptPage()
{
	OpenModalWindow("注册新机构","/sys/appeal/cpDept/cpDept_add.jsp?p_id="+dept_id,450,190);
}

//打开修改窗口
function openUpdateCpDeptPage()
{
	var selectIDS = table.getSelecteCheckboxValue("dept_id");
	OpenModalWindow("修改注册机构","/sys/appeal/cpDept/cpDept_add.jsp?dept_id="+selectIDS,450,190);
}

//添加注册机构
function addCpDept()
{
	var bean = BeanUtil.getCopy(CpDeptBean);	
	$("#cpDept_table").autoBind(bean);

	if(!standard_checkInputInfo("cpDept_table"))
	{
		return;
	}

	bean.dept_id = 0;
	if(CpDeptRPC.insertCpdept(bean))
	{
		msgAlert("注册机构信息"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadCpDeptList();
		getCurrentFrameObj().showCpDeptTree();
	}
	else
	{
		msgWargin("注册机构信息"+WCMLang.Add_fail);
	}
}
//修改注册机构
function updateCpDept()
{
	var bean = BeanUtil.getCopy(CpDeptBean);	
	$("#cpDept_table").autoBind(bean);

	if(!standard_checkInputInfo("cpDept_table"))
	{
		return;
	}

	if(CpDeptRPC.updateCpDept(bean))
	{
		msgAlert("注册机构信息"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadCpDeptList();
		getCurrentFrameObj().showCpDeptTree();
	}
	else
	{
		msgWargin("注册机构信息"+WCMLang.Add_fail);
	}
}

//删除注册机构
function deleteCpDept()
{
	var selectIDS = table.getSelecteCheckboxValue("dept_id");
	if(CpDeptRPC.deleteCpdept(selectIDS))
	{
		msgAlert("注册机构信息"+WCMLang.Delete_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadCpDeptList();
		getCurrentFrameObj().showCpDeptTree();
	}else
	{
		msgWargin("注册机构信息"+WCMLang.Delete_fail);
	}
}

//保存部门排序
function saveDeptSort()
{
	var dept_ids = table.getAllCheckboxValue("dept_id");
	if(dept_ids != "" && dept_ids != null)
	{
		if(CpDeptRPC.saveDeptSort(dept_ids))
		{
			msgAlert(WCMLang.Sort_success);
			getCurrentFrameObj().showCpDeptTree();
			treeNodeSelected(dept_id);
			reloadCpDeptList();
		}
		else
		{
			msgWargin(WCMLang.Sort_fail);
			return;
		}
	}
}

// 修改时展现部门
function showCpDeptBean(dept_id)
{
	if(dept_id != "" && dept_id != "null" && dept_id != null)
	{
		defaultBean = CpDeptRPC.getCpDeptBean(dept_id);
		if(defaultBean)
		{
			$("#cpDept_table").autoFill(defaultBean);	
		}
	}	
}

////添加注册机构树节点
//function insertDeptTree(id,dept_name)
//{
//	insertTreeNode(eval('[{"id":'+id+',"text":\"'+dept_name+'\","attributes":{}}]'));	
//}



