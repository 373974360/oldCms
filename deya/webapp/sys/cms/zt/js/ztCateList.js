var CategoryRPC = jsonrpc.CategoryRPC;
var ZTCategoryBean = new Bean("com.deya.wcm.bean.cms.category.ZTCategoryBean",true);

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "cateClass_table_list";

// 初始化Table
function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	colsList.add(setTitleClos("zt_cat_id","ID","30px","","",""));
	colsList.add(setTitleClos("zt_cat_name","专题分类名称","250px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("space","","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("cateClass_table");//里面参数为外层div的id
}

// 加载初始化Table和翻页信息
function reloadZTCateList()
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
	
	var beanList = CategoryRPC.getZTCategoryList(site_id);
	beanList =List.toJSList(beanList);
	tp.total = beanList.size();
	
	table.setBeanList(beanList,"td_list");
	table.show("cateClass_table");
	
	table.getCol("zt_cat_name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:top.OpenModalWindow(\'分类方法信息\',\'/sys/cms/zt/ztCate_add.jsp?type=update&id='+beanList.get(i-1).id +'\',440,130)">'+beanList.get(i-1).zt_cat_name+'</a>');	
		}
	});

	table.getCol("sort_col").each(function(i){	
		if(i>0)
		{
			$(this).html(getSortColStr());			
		}
	});
	Init_InfoTable(table.table_name);
}

// 添加翻页信息
function showTurnPage()
{
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

// 新建分类方式
function addZTCate()
{
	top.OpenModalWindow("新建分类方式","/sys/cms/zt/ztCate_add.jsp?type=add&id=",440,130);
}

// 修改分类方式
function updateZTCate()
{
	var id = table.getSelecteCheckboxValue("id");
	top.OpenModalWindow("修改分类方式","/sys/cms/zt/ztCate_add.jsp?type=update&id="+id,440,130);
}

// 删除分类方式
function deleteZTCate()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	if(CategoryRPC.getCategoryCountByClassID(selectIDS) > 1)
	{
		top.msgWargin("该分类下还存在目录，请先清空目录后再进行删除操作");
		return;
	}else
	{
		if(CategoryRPC.deleteZTCategory(selectIDS)){
			top.msgAlert("分类"+WCMLang.Delete_success);
			reloadZTCateList();
		}else
		{
			top.msgWargin("分类"+WCMLang.Delete_fail);
			return;
		}
		
	}	
}

function sortCategory()
{
	var selectIDS = table.getAllCheckboxValue("id");
	if(CategoryRPC.sortZTCategory(selectIDS))
	{
		top.msgAlert(WCMLang.Sort_success);
		reloadZTCateList();
	}else
	{
		top.msgWargin(WCMLang.Sort_fail);
	}
}
