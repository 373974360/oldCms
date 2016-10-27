var CpLeadRPC = jsonrpc.CpLeadRPC;
var CpLeadBean = new Bean("com.deya.wcm.bean.appeal.cpLead.CpLeadBean",true);


var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "cpLead_table";;

//重新载入 列表及分页 数据    
function reloadCpLeadList()
{
	showList();	
	showTurnPage();	
}

/* 初始化表格 */
function initTable(){
	
	var colsMap = new Map();
	var colsList = new List();	
	                         //英文名，显示名，宽，高，样式名，点击事件
	colsList.add(setTitleClos("lead_name","领导姓名","","","",""));
	colsList.add(setTitleClos("dept_name","所属部门","","","",""));
	colsList.add(setTitleClos("lead_job","职 务","","","",""));
	colsList.add(setTitleClos("lead_sort_col","排序","100px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}


//展示列表
function showList(){
	
	var m = new Map();
	var sortCol = table.sortCol;
	var sortType = table.sortType;
	
	//分页
	m.put("start_num",tp.getStart());
	m.put("page_size",tp.pageSize);
	
	//默认排序
	if(sortCol == "" || sortCol == null){
		sortCol = "sort_id"
		sortType = "asc" 
	}
	m.put("sort_name",sortCol);
	m.put("sort_type",sortType);
	
	//条件
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}
	
	beanList = CpLeadRPC.getCpLeadList(m);
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	tp.total = CpLeadRPC.getCpLeadCount(m);

	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("lead_name").each(function(i){
		if(i>0)
		{			
			$(this).css({"text-align":"left"});	
			$(this).html('<a href="/sys/appeal/cpLead/cpLead_add.jsp?lead_id='+beanList.get(i-1).lead_id+'" target="_self" >'+beanList.get(i-1).lead_name+'</a>');
		}
	});	
	
	table.getCol("dept_name").each(function(i){				
		$(this).css({"text-align":"left"});			
	});	
	
	table.getCol("lead_sort_col").each(function(i){	
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
function openAddCpLeadPage()
{
	window.location.href = "cpLead_add.jsp";
}

//打开修改窗口
function openUpdateCpLeadPage()
{
	var selectIDS = table.getSelecteCheckboxValue("lead_id");
	window.location.href = "cpLead_add.jsp?lead_id="+selectIDS;
}

//添加领导
function addCpLead()
{
	var bean = BeanUtil.getCopy(CpLeadBean);	
	$("#cpLead_table").autoBind(bean);

	if(!standard_checkInputInfo("cpLead_table"))
	{
		return;
	}
	bean.lead_id=0
	if(CpLeadRPC.insertCpLead(bean))
	{
		top.msgAlert("领导信息"+WCMLang.Add_success);
		window.location.href = "/sys/appeal/cpLead/cpLeadList.jsp";
		
	}
	else
	{
		top.msgWargin("领导信息"+WCMLang.Add_fail);
	}
}

//修改注册领导
function updateCpLead()
{
	var bean = BeanUtil.getCopy(CpLeadBean);	
	$("#cpLead_table").autoBind(bean);

	if(!standard_checkInputInfo("cpLead_table"))
	{
		return;
	}
	if(CpLeadRPC.updateCpLead(bean))
	{
		top.msgAlert("领导信息"+WCMLang.Add_success);	
		window.location.href = "/sys/appeal/cpLead/cpLeadList.jsp";
	}
	else
	{
		top.msgWargin("领导信息"+WCMLang.Add_fail);
	}
}
//往输入框里填写部门信息
function setCpDept(id,name){
	$("#dept_id").val(id);
	$("#dept_name").val(name);
}

//删除注册领导
function deleteCpLead()
{
	var selectIDS = table.getSelecteCheckboxValue("lead_id");
	if(CpLeadRPC.deleteCpLead(selectIDS))
	{
		top.msgAlert("领导信息"+WCMLang.Delete_success);
		window.location.href = "/sys/appeal/cpLead/cpLeadList.jsp";
	}else
	{
		top.msgWargin("领导信息"+WCMLang.Delete_fail);
	}
}

//保存部门排序
function saveCpleadSort()
{
	var lead_ids = table.getAllCheckboxValue("lead_id");
	if(lead_ids != "" && lead_ids != null)
	{
		if(CpLeadRPC.savesortCpLead(tp.getStart(),lead_ids))
		{
			top.msgAlert(WCMLang.Sort_success);
			top.CloseModalWindow();
			reloadCpLeadList();
		}
		else
		{
			top.msgWargin(WCMLang.Sort_fail);
			return;
		}
	}
}

