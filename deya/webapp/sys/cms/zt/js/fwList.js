var CategoryRPC = jsonrpc.CategoryRPC;
var ZTCategoryBean = new Bean("com.deya.wcm.bean.cms.category.ZTCategoryBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "zt_table";;

function reloadZTList()
{
	showTurnPage();
	showList();	
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	colsList.add(setTitleClos("cat_id","ID","30px","","",""));
	colsList.add(setTitleClos("cat_cname","分类名称","200px","","",""));//英文名，显示名，宽，高，样式名，点击事件　	
	colsList.add(setTitleClos("is_archive","归档状态","80px","","",""));
	colsList.add(setTitleClos("cat_handl","目录管理","100px","","",""));
	colsList.add(setTitleClos("spanCol"," ","","","",""));
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
	
	var m = new Map();	
	m.put("app_id", app_id);
	m.put("cat_type", "2");	
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	m.put("parent_id", "0");
	if(serrch_cat_id != "")
		m.put("zt_cat_id", serrch_cat_id);
	if(table.con_value.trim() != "" && table.con_value != null)
	{		
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}

	beanList = CategoryRPC.getCategoryListBySiteAndType(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	

	table.getCol("cat_cname").each(function(i){
		$(this).css("text-align","left")
		if(i>0)
		{			
			$(this).html('<a href="javascript:openUpdateCategoryPage('+beanList.get(i-1).id+')">'+beanList.get(i-1).cat_cname+'</a>');
		}
	});	
	
	table.getCol("cat_handl").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:addTab(true,\'/sys/cms/category/categoryList.jsp?cat_type=2&site_id='+site_id+'&app_id='+app_id+'&cat_id='+beanList.get(i-1).cat_id+'\',\'目录管理\')">目录管理</a>');
		}
	});
	table.getCol("is_archive").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).is_archive == 1)
				$(this).text("已归档");
			else
				$(this).text("");
		}
	});
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	var m = new Map();
	m.put("app_id", app_id);
	m.put("cat_type", "2");	
	m.put("parent_id", "0");
	if(serrch_cat_id != "")
		m.put("zt_cat_id", serrch_cat_id);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);			
	}	
	tp.total = CategoryRPC.getCategoryCountBySiteAndType(m);
			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

function openAddCategoryPage()
{
	addTab(true,"/sys/cms/category/category_add.jsp?site_id="+site_id+"&app_id="+app_id+"&top_index="+curTabIndex+"&&parentID=0&cat_type=2","维护目录");
}

function openUpdateCategoryPage(cid)
{
	var id;
	if(cid != "" && cid != null)
		id = cid;
	else
		id = table.getSelecteCheckboxValue("id");
	addTab(true,"/sys/cms/category/category_add.jsp?site_id="+site_id+"&app_id="+app_id+"&top_index="+curTabIndex+"&id="+id+"&cat_type=2","维护目录");
}

function deleteCategory()
{
	var selectIDS = table.getSelecteCheckboxValue("cat_id");

	if(CategoryRPC.deleteCategory(selectIDS,site_id,app_id))
	{
		msgAlert("目录信息"+WCMLang.Delete_success);
		reloadZTList();
	}else
	{
		msgWargin("目录信息"+WCMLang.Delete_fail);
	}
}


function searchHandl(obj)
{
	var con_value = $(obj).parent().find("#searchkey").val();
	if(con_value.trim() == "" ||  con_value == null)
	{
		msgAlert(WCMLang.Search_empty);
		return;
	}
	table.con_name = $(obj).parent().find("#searchFields").val(); 
	table.con_value = con_value;
	reloadZTList();
}


//修改归档状态
function updateArchiveStatus(flag)
{
	var selectIDS = table.getSelecteCheckboxValue("id");

	if(CategoryRPC.updateCategoryArchiveStatus(selectIDS,flag))
	{
		msgAlert(WCMLang.ArchiveStatus_success);
		reloadZTList();
	}else
	{
		msgWargin(WCMLang.ArchiveStatus_fail);
	}
}
