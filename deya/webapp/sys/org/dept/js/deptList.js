var DeptRPC = jsonrpc.DeptRPC;
var DeptBean = new Bean("com.deya.wcm.bean.org.dept.DeptBean",true);

var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "dept_table_list";


function reoloadDeptList()
{
	initDeptTable();
	showDeptList();
	showDeptTurnPage();
	Init_InfoTable(table.table_name);
}

function initDeptTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("dept_id","ID","20px","","",""));
	colsList.add(setTitleClos("dept_name","部门名称","200px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("actionCol","操作","80px","","",""));
	colsList.add(setTitleClos("tel","电话","80px","","",""));	
	colsList.add(setTitleClos("area_code","地区代码","60px","","",""));
	colsList.add(setTitleClos("dept_code","机构代码","100px","","",""));
	colsList.add(setTitleClos("is_publish","发布状态","70px","","",""));
	colsList.add(setTitleClos("dept_sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("blank_col","&#160;","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showDeptList;
	table.show("dept_table");//里面参数为外层div的id
}

function showDeptList(){
	
	var sortCol = table.sortCol;
	var sortType = table.sortType;	
	
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "id";
		sortType = "desc";
	}

	if(table.con_value.trim() != "" && table.con_value != null)
	{
		var m = new Map();
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
		m.put("dept_id", dept_id);	
		beanList = DeptRPC.getChildDeptListForDB(m);
	}
	else
		beanList = DeptRPC.getChildDeptListByID(dept_id);	
	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show();			
	
	tp.total = beanList.size();

	table.getCol("dept_name").each(function(i){	
		if(i>0)
		{
			$(this).html('<a href="javascript:addTab(true,\'/sys/org/dept/dept_view.jsp?dept_id='+beanList.get(i-1).dept_id+'\',\'部门信息\')">'+beanList.get(i-1).dept_name+'</a>');
		}
	});

	table.getCol("actionCol").each(function(i){	
		if(i>0)
		{
			$(this).html('<span onclick="addDeptManager('+beanList.get(i-1).dept_id+')" style="cursor:pointer">设置管理员</span>');			
		}
	});//addDeptManager方法在deptManager.js中

	user_table.getCol("is_publish").each(function(i){
		if(i>0)
		{
			if(user_beanList.get(i-1).is_publish == 0)			
				$(this).html("&#160;");				
			if(user_beanList.get(i-1).is_publish == 1)
				$(this).text("已发布");
		}
	});

	table.getCol("dept_sort_col").each(function(i){	
		if(i>0)
		{
			$(this).html(getSortColStr());			
		}
	});
}

function showDeptTurnPage(){				
	tp.show("turn","simple");	
	tp.onPageChange = showDeptList;
}

function openAddDeptPage()
{
	window.location.href = "dept_add.jsp?parent_id="+dept_id;
}


function addDept()
{
	var bean = BeanUtil.getCopy(DeptBean);
	$("#dept_table").autoBind(bean);		
		
	if(!standard_checkInputInfo("dept_table"))
	{
		return;
	}

	bean.parent_id = parseInt(parent_id);	
	bean.dept_id = DeptRPC.getDeptID();
	if(bean.dept_id == 0)
	{
		msgAlert("部门信息"+WCMLang.Add_success);
		return;
	}
	
	if(DeptRPC.insertDept(bean))
	{
		msgAlert("部门信息"+WCMLang.Add_success);
		insertDeptTree(bean.dept_id,bean.dept_name);		
		window.location.href = "deptList.jsp?deptID="+parent_id+"&labNum=2";
	}
	else
	{
		msgWargin("部门信息"+WCMLang.Add_fail);
	}	
}

function openUpdateDeptPage()
{
	var selectIDS = table.getSelecteCheckboxValue("dept_id");
	window.location.href = "dept_add.jsp?dept_id="+selectIDS+"&parent_id="+dept_id;
}

function updateDept()
{
	var bean = BeanUtil.getCopy(DeptBean);
	$("#dept_table").autoBind(bean);		
		
	if(!standard_checkInputInfo("dept_table"))
	{
		return;
	}
	bean.dept_id = dept_id;
	if(DeptRPC.updateDept(bean))
	{
		msgAlert("部门信息"+WCMLang.Add_success);
		updateDeptTree(bean.dept_id,bean.dept_name);		
		window.location.href = "deptList.jsp?deptID="+parent_id+"&labNum=2";
	}
	else
	{
		msgWargin("部门信息"+WCMLang.Add_fail);
	}
}

//删除部门
function deleteDeptHandl()
{
	var selectIDS = table.getSelecteCheckboxValue("dept_id");
	if(DeptRPC.deleteDept(selectIDS))
	{
		deleteDeptTree(selectIDS);
		msgAlert("部门信息"+WCMLang.Delete_success);
		reoloadDeptList();
	}else
	{
		msgWargin("部门信息"+WCMLang.Delete_fail);
	}
}

//添加树节点
function insertDeptTree(id,dept_name)
{
	insertTreeNode(eval('[{"id":'+id+',"text":\"'+dept_name+'\","attributes":{"url":\"'+DeptRPC.getDeptListPage()+'?deptID='+id+'\"}}]'));
}

//修改树节点
function updateDeptTree(id,dept_name)
{
	updateTreeNode(id,dept_name);
}

//删除树节点
function deleteDeptTree(ids)
{
	deleteTreeNode(ids);
}

//保存部门排序
function saveDeptSort()
{
	var dept_ids = table.getAllCheckboxValue("dept_id");
	if(dept_ids != "" && dept_ids != null)
	{
		if(DeptRPC.saveDeptSort(dept_ids))
		{
			msgAlert(WCMLang.Sort_success);
			getDeptTreeJsonData();
			treeNodeSelected(dept_id);
			reoloadDeptList();
		}
		else
		{
			msgWargin(WCMLang.Sort_fail);
			return;
		}
	}
}

//搜索事件 
function deptSearchHandl(obj)
{
	var conn_value = $(obj).parent().find("#searchkey").val();
	if(conn_value.trim() == "" ||  conn_value == null)
	{
		msgAlert(WCMLang.Search_empty);
		return;
	}
	table.con_name = $(obj).parent().find("#searchFields").val(); 
	table.con_value = conn_value;
	reoloadDeptList();
}

//移动部门
function moveDept(parent_id)
{
	if(dept_id != "" && dept_id != null)
	{
		var selectIDS = table.getSelecteCheckboxValue("dept_id");			
		if(DeptRPC.moveDept(parent_id,selectIDS)){
			msgAlert("部门"+WCMLang.Move_success);
			reoloadDeptList();
			getDeptTreeJsonData();
		}else
		{
			msgWargin("部门"+WCMLang.Move_fail);
			return;
		}
	}
}

//得到部门级别
function getDeptLevelList()
{
	var level_list = DeptRPC.getDeptLevelList();
	level_list = List.toJSList(level_list);
	$("#deptlevel_value").addOptions(level_list,"deptlevel_value","deptlevel_name");
}