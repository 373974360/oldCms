var CommonLangRPC = jsonrpc.CommonLangRPC;
var CommonLangBean = new Bean("com.deya.wcm.bean.appeal.lang.CommonLangBean",true);

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "commonLang_table_list";
var con_m = new Map();

// 初始化Table
function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("ph_title","常用语短名","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("ph_type","类型","120px","","",""));
	if(ph_type != "all")
	{
		colsList.add(setTitleClos("sort_col","排序","120px","","",""));
	}
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList()
{
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "id";
		sortType = "desc";
	}
	
	con_m.put("start_num", tp.getStart());	
	con_m.put("page_size", tp.pageSize);
	con_m.put("type", ph_type);// 区分全部和其他类型。排序方式不同
	
	if(ph_type == "all")
	{
		con_m.remove("ph_type");
	}
	else
	{
		con_m.put("ph_type",ph_type);// 检索条件
	}
	beanList = CommonLangRPC.getCommonLangList(con_m);
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("ph_title").each(function(i){	
		if(i>0)
		{
			var id = beanList.get(i-1).ph_id;
			$(this).html('<a href="/sys/appeal/lang/commonLang_add.jsp?type=update&ph_id='+id+'&ph_type='+ph_type+'">'+beanList.get(i-1).ph_title+'</a>');
		}
	});
	
	table.getCol("ph_type").each(function(i){	
		if(i>0)
		{
			var id = beanList.get(i-1).ph_type;	
			var viewType = "";
			switch(id)
			{
				case 0	:viewType="受理";break;
				case 1	:viewType="回复";break;
				case 2	:viewType="转办";break;
				case 3	:viewType="交办";break;
				case 4	:viewType="呈办";break;
				case 5	:viewType="重复件";break;
				case 6	:viewType="";break;
				case 7	:viewType="不予受理";break;
				case 8	:viewType="申请延时";break;
				case 9	:viewType="延时通过";break;
				case 10	:viewType="延时打回";break;
				case 11	:viewType="审核通过";break;
				case 12	:viewType="审核打回";break;
				case 13	:viewType="督办";break;
			}
			$(this).html(viewType);
		}
	});

	table.getCol("sort_col").each(function(i){	
		// 当类型为全部时，不显示排序列
		if(i>0)
		{			
			$(this).html(getSortColStr());	
		}
	});

	Init_InfoTable(table.table_name);
}

function showTurnPage()
{	
	tp = new TurnPage();
	tp.total = CommonLangRPC.getCommonLangCnt(con_m);
	if(ph_type == "all")
	{
		tp.show("turn","");	
	}
	else
	{
		tp.show("turn","simple");
	}
	tp.onPageChange = showList;
}

function reloadCommonLangList()
{
	initTable();
	showList();
	showTurnPage();
}

// 添加常用语
function addCommonLang()
{
	window.location.href  = "/sys/appeal/lang/commonLang_add.jsp?type=add&ph_id=&ph_type="+ph_type;
}

// 修改常用语
function updateCommonLang()
{
	var id = table.getSelecteCheckboxValue("ph_id");
	window.location.href  = "/sys/appeal/lang/commonLang_add.jsp?type=update&ph_id="+id+"&ph_type="+ph_type;
}

// 删除常用语
function deleteCommonLang()
{
	var ids = table.getSelecteCheckboxValue("ph_id");
	var del_m = new Map();
	del_m.put("ph_id",ids);
	if(CommonLangRPC.deleteCommonLang(del_m))
	{
		top.msgAlert("常用语"+WCMLang.Delete_success);
		reloadCommonLangList();
	}
	else
	{
		top.msgWargin("常用语"+WCMLang.Delete_fail);
	}
}

// 保存排序
function saveSort()
{
	var ids = table.getAllCheckboxValue("ph_id");
	if(CommonLangRPC.saveSort(ids))
	{
		top.msgAlert(WCMLang.Sort_success);
	}
	else
	{
		top.msgWargin(WCMLang.Sort_fail);
	}
}



