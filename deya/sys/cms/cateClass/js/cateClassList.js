var CategoryRPC = jsonrpc.CategoryRPC;
var CateClassBean = new Bean("com.deya.wcm.bean.cms.category.CateClassBean",true);

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "cateClass_table_list";

// 初始化Table
function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("class_cname","分类名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("cat_id","目录ID","80px","","",""));
	colsList.add(setTitleClos("cat_handl","目录管理","100px","","",""));
	colsList.add(setTitleClos("class_ename","英文名称","100px","","",""));
	//colsList.add(setTitleClos("class_codo","域代码","120px","","",""));
	colsList.add(setTitleClos("class_type","目录分类类型","120px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("cateClass_table");//里面参数为外层div的id
}

// 加载初始化Table和翻页信息
function reloadCateClassList()
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
	
	var beanList = CategoryRPC.getAllCateClassList();
	beanList =List.toJSList(beanList);
	tp.total = beanList.size();
	
	table.setBeanList(beanList,"td_list");
	table.show("cateClass_table");
	
	table.getCol("class_cname").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:top.OpenModalWindow(\'分类方法信息\',\'/sys/cms/cateClass/cateClass_add.jsp?type=update&class_id='+beanList.get(i-1).class_id +'\',460,385)">'+beanList.get(i-1).class_cname+'</a>');	
		}
	});

	table.getCol("cat_id").each(function(i){
		if(i>0)
		{
			$(this).html(CategoryRPC.getCategoryBeanByClassID(beanList.get(i-1).class_id).cat_id);	
		}
	});

	table.getCol("cat_handl").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:top.addTab(true,\'/sys/cms/category/categoryShareList.jsp?class_id='+beanList.get(i-1).class_id+'\',\'目录管理\')">目录管理</a>');	
		}
	});
	
	table.getCol("class_type").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).class_type == 0)
			{
				$(this).text("基础分类法");
			}else if(beanList.get(i-1).class_type == 1)
			{
				$(this).text("共享目录");
			}else
			{
				$(this).text ("");
			}
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

// 新建分类
function addCateClass()
{
	top.OpenModalWindow("新建分类","/sys/cms/cateClass/cateClass_add.jsp?type=add&class_id=",460,385);
}

// 修改分类
function updateCateClass()
{
	var class_id = table.getSelecteCheckboxValue("class_id");
	top.OpenModalWindow("修改分类","/sys/cms/cateClass/cateClass_add.jsp?type=update&class_id="+class_id,460,385);
}

// 删除分类
function deleteCateClass()
{
	var selectIDS = table.getSelecteCheckboxValue("class_id");
	if(CategoryRPC.getCategoryCountByClassID(selectIDS) > 1)
	{
		top.msgWargin("该分类下还存在目录，请先清空目录后再进行删除操作");
		return;
	}else
	{
		if(CategoryRPC.deleteCateClass(selectIDS)){
			top.msgAlert("分类"+WCMLang.Delete_success);
			reloadCateClassList();
		}else
		{
			top.msgWargin("分类"+WCMLang.Delete_fail);
			return;
		}
		
	}
	
}

// 得到应用列表
function getAppList()
{
	var app_list = CategoryRPC.getAppBeanList();
	app_list = List.toJSList(app_list);
	app_list = List.toJSList(app_list);
	if(app_list != null && app_list.size() > 0)
	{
		for(var i=0;i<app_list.size();i++)
		{			
			$("#app_list").append('<li style="float:none;height:20px"><input type="checkbox" id="app_ids" value="'+app_list.get(i).app_id+'"><label>'+app_list.get(i).app_name+'</label></li>');
		}
	}
}

function getAppIDS()
{
	var app_ids = "";
	$(":checkbox[checked]").each(function(){
		app_ids += ","+$(this).val();
	});
	if(app_ids != "")
		app_ids = app_ids.substring(1);
	return app_ids;
}