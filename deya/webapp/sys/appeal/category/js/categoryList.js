var AppealCategoryRPC = jsonrpc.AppealCategoryRPC;
var CategoryBean = new Bean("com.deya.wcm.bean.appeal.category.CategoryBean",true);

var val= new Validator();
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
	table.table_name = "appCategory_table";;
var con_m = new Map();
var user_id = LoginUserBean.user_id;

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("cat_cname","分类名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　	
	colsList.add(setTitleClos("sort_id","排序","100px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){			

	beanList = AppealCategoryRPC.getChildCategoryList(cat_id);//第一个参数为站点ID，暂时默认为空		
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	
	
	table.getCol("cat_cname").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openUpdateCategoryPage2('+beanList.get(i-1).cat_id+')">'+beanList.get(i-1).cat_cname+'</a>');
		}
	});	

	table.getCol("sort_id").each(function(i){	
		if(i>0)
		{			
			$(this).html(getSortColStr());		
		}
	});
}

function openUpdateCategoryPage2(cat_id)
{
	OpenModalWindow("修改分类信息","/sys/appeal/category/category_add.jsp?cat_id="+cat_id,450,140);
}

function showTurnPage(){	
	
	tp.total = AppealCategoryRPC.getChildCategoryCount(cat_id);			
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

function openAddCategoryPage()
{
	OpenModalWindow("修改分类","/sys/appeal/category/category_add.jsp?parentID="+cat_id,450,140);
}

function openUpdateCategoryPage()
{
	var selectIDS = table.getSelecteCheckboxValue("cat_id");
	OpenModalWindow("修改分类","/sys/appeal/category/category_add.jsp?parentID="+cat_id+"&cat_id="+selectIDS,450,140);
}
//添加内容分类
function addCategory()
{
	var bean = BeanUtil.getCopy(CategoryBean);	
	$("#appCategory_table").autoBind(bean);

	if(!standard_checkInputInfo("appCategory_table"))
	{
		return;
	}	
	bean.cat_id = AppealCategoryRPC.getCategoryID();
	bean.parent_id = parent_id;
	
	if(AppealCategoryRPC.insertCategory(bean))
	{	
		msgAlert("分类信息"+WCMLang.Add_success);
		getCurrentFrameObj().changeCategoryListTable(parent_id);
		getCurrentFrameObj().insertCategoryTree(bean.cat_id,bean.cat_cname);
		CloseModalWindow();
	}
	else
	{
		msgWargin("分类信息"+WCMLang.Add_fail);
	}
}
//添加树节点
function insertCategoryTree(cat_id,cat_cname)
{
	insertTreeNode(eval('[{"id":'+cat_id+',"text":\"'+cat_cname+'\"}]'));	
}

function updateCategory()
{
	var bean = BeanUtil.getCopy(CategoryBean);	
	$("#appCategory_table").autoBind(bean);
	if(!standard_checkInputInfo("appCategory_table"))
	{
		return;
	}
	bean.cat_id = cat_id;
	
	if(AppealCategoryRPC.updateCategory(bean))
	{
		msgAlert("分类信息"+WCMLang.Add_success);
		getCurrentFrameObj().changeCategoryListTable(parent_id);
		getCurrentFrameObj().updateCategoryTree(bean.cat_id,bean.cat_cname);
		CloseModalWindow();
	}
	else
	{
		msgWargin("分类信息"+WCMLang.Add_fail);
	}
}
//修改树节点
function updateCategoryTree(id,cname)
{
	updateTreeNode(id,cname);
}
function deleteCategory()
{
	var selectIDS = table.getSelecteCheckboxValue("cat_id");
	if(AppealCategoryRPC.deleteCategory(selectIDS))
	{
		msgAlert("分类信息"+WCMLang.Delete_success);
		getCurrentFrameObj().changeCategoryListTable(cat_id);
		getCurrentFrameObj().deleteTreeNode(selectIDS);
	}else
	{
		msgWargin("分类信息"+WCMLang.Delete_fail);
	}
}
function getAppInfoByID(cat_id)
{
	for(var i=0;i<appList.size();i++)
	{
		if(appList.get(i).cat_id == cat_id)
			return appList.get(i).cat_cname;
	}
}

//节点排序
function sortCategory()
{
	var selectIDS = table.getAllCheckboxValue("cat_id");
	if(AppealCategoryRPC.saveCategorySort(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
		showCategoryTree();	
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}

function moveCategory(parent_id)
{
	if(parent_id != "" && parent_id != null)
	{
		var selectIDS = table.getSelecteCheckboxValue("cat_id");
		
		if(AppealCategoryRPC.moveAppCate(parent_id,selectIDS)){
			msgAlert("分类"+WCMLang.Move_success);
			loadCategoryTable()
			showCategoryTree();
		}else
		{
			msgWargin("分类"+WCMLang.Move_fail);
			return;
		}
	}
}
