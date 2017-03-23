var AppCatalogRPC = jsonrpc.AppCatalogRPC;
var AppCatalogBean = new Bean("com.deya.wcm.bean.zwgk.appcatalog.AppCatalogBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var page_type = "";
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "cata_table";;

function reloadCatalogList()
{
	showList();	
	showTurnPage();	
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("cata_id","ID","30px","","",""));
	colsList.add(setTitleClos("cata_cname","分类名称","200px","","",""));//英文名，显示名，宽，高，样式名，点击事件
	if(page_type == "list")
		colsList.add(setTitleClos("action_col","目录管理","60px","","",""));
	colsList.add(setTitleClos("action_col2","操作","60px","","",""));
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("spanCol"," ","","","",""));
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){	
	
	beanList = AppCatalogRPC.getChildCatalogList(cata_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("cata_cname").each(function(i){
		$(this).css("text-align","left")
		if(i>0)
		{			
			$(this).html('<a href="javascript:openUpdateCatalogPage('+beanList.get(i-1).cata_id+')">'+beanList.get(i-1).cata_cname+'</a>');
		}
	});	

	table.getCol("action_col").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openCatalogAction('+beanList.get(i-1).cata_id+')">目录管理</a>');
		}
	});

	table.getCol("action_col2").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openSelectRegulation('+beanList.get(i-1).cata_id+')">规则设置</a>');
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

function showTurnPage(){
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

function openCatalogAction(c_id)
{
	addTab(true,"/sys/zwgk/catalog/catalogMain.jsp?top_index="+curTabIndex+"&parentID="+c_id,"目录管理");
}

function openAddCatalogPage()
{
	addTab(true,"/sys/zwgk/catalog/catalog_add.jsp?top_index="+curTabIndex+"&parentID="+cata_id,"维护目录");
}
//打开规则选择窗口
function openSelectRegulation(c_id)
{
	OpenModalWindow("信息汇聚规则设置","/sys/zwgk/catalog/set_regulation.jsp?cata_id="+c_id,860,600);
}

function openUpdateCatalogPage(cid)
{
	var id;
	if(cid != "" && cid != null)
		id = cid;
	else
		id = table.getSelecteCheckboxValue("cata_id");

	addTab(true,"/sys/zwgk/catalog/catalog_add.jsp?top_index="+curTabIndex+"&parentID="+cata_id+"&cata_id="+id,"维护目录");
}

//添加目录
function addCatalog()
{
	var bean = BeanUtil.getCopy(AppCatalogBean);	
	$("#catalog_table").autoBind(bean);

	if(!standard_checkInputInfo("catalog_table"))
	{		
		return;
	}

	bean.cata_id = AppCatalogRPC.getNewAppCatalogID();
	bean.id = bean.cata_id;
	bean.parent_id = parent_id;
	
	if(AppCatalogRPC.insertGKAppCatelog(bean))
	{	
		msgAlert("目录信息"+WCMLang.Add_success);
		getCurrentFrameObj(top_index).reloadCatalogList();
		if(page_type == "")
		{
			try{
				getCurrentFrameObj(top_index).insertCatalogTree(bean.cata_id,bean.cata_cname);
			}catch(e){}
		}
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("目录信息"+WCMLang.Add_fail);
	}
}

function updateCatalog()
{
	var bean = BeanUtil.getCopy(AppCatalogBean);	
	$("#catalog_table").autoBind(bean);

	if(!standard_checkInputInfo("catalog_table"))
	{		
		return;
	}

	bean.cata_id = cata_id;
		
	if(AppCatalogRPC.updateGKAppCatelog(bean))
	{	
		msgAlert("目录信息"+WCMLang.Add_success);
		getCurrentFrameObj(top_index).reloadCatalogList();
		if(page_type == "")
		{
			getCurrentFrameObj(top_index).updateCatalogTree(bean.cata_id,bean.cata_cname);
		}
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("目录信息"+WCMLang.Add_fail);
	}
}

function sortCatalog()
{
	var selectIDS = table.getAllCheckboxValue("cata_id");
	if(AppCatalogRPC.sortGKAppCatelog(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
		//showCatalogTree();
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}

function deleteCatalog()
{
	var selectIDS = table.getSelecteCheckboxValue("cata_id");

	if(AppCatalogRPC.deleteGKAppCatelog(selectIDS))
	{
		msgAlert("目录信息"+WCMLang.Delete_success);
		reloadCatalogList();
		deleteTreeNode(selectIDS);
	}else
	{
		msgWargin("目录信息"+WCMLang.Delete_fail);
	}
}


//拷贝共享目录
function copyShareCategory(c_id)
{
	if(AppCatalogRPC.copyShareCategory(cata_id,c_id))
	{
		msgAlert("目录"+WCMLang.Add_success);
		reloadCatalogList();
		showCatalogTree();
	}else
	{
		msgWargin("目录"+WCMLang.Add_fail);
		return;
	}
} 

//移动目录
function moveCatalog(parent_id)
{
	if(parent_id != "" && parent_id != null)
	{
		var selectIDS = table.getSelecteCheckboxValue("cata_id");			
		if(AppCatalogRPC.moveGKAppCatelog(parent_id,selectIDS)){
			msgAlert("目录"+WCMLang.Move_success);
			cata_id = root_cata_id;
			reloadCatalogList();
			showCatalogTree();
		}else
		{
			msgWargin("目录"+WCMLang.Move_fail);
			return;
		}
	}
}

function showCatalogTree()
{
	json_data = eval(AppCatalogRPC.getAppCatalogTree(cata_id));	
	
	setLeftMenuTreeJsonData(json_data);
	initCatalogTree();
	treeNodeSelected(cata_id);
}

function initCatalogTree()
{
	$('#leftMenuTree').tree({
		onClick:function(node){			 
			changeCatalogListTable(node.id);           
		}
	});

}

//点击树节点,修改目录列表数据
function changeCatalogListTable(c_id){
	cata_id = c_id;
	reloadCatalogList();
}

//添加树节点
function insertCatalogTree(id,name)
{
	insertTreeNode(eval('[{"id":'+id+',"text":\"'+name+'\","attributes":{}}]'));	
}

//修改树节点
function updateCatalogTree(id,name)
{
	$("div[node-id='"+id+"'] .tree-title").text(name);
}