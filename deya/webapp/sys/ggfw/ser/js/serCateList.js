var SerRPC = jsonrpc.SerRPC;
var SerCategoryBean = new Bean("com.deya.wcm.bean.zwgk.ser.SerCategoryBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "table";;

function loadSerCategoryTable()
{
	showList();	
	showTurnPage();	
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("ser_id","ID","30px","","",""));
	colsList.add(setTitleClos("cat_name","主题名称","300px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("cat_type","节点类型","100px","","",""));
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("spanCol"," ","","","",""));
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){	
	
	beanList = SerRPC.getChildSerCategoryList(ser_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size()
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("cat_name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openAddPage('+beanList.get(i-1).ser_id+')">'+beanList.get(i-1).cat_name+'</a>');
		}
	});	

	table.getCol("cat_type").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).cat_type == "sub")
				$(this).html('枝节点');
			if(beanList.get(i-1).cat_type == "leaf")
				$(this).html('叶节点');
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

function openAddPage(s_id)
{
	if(s_id != null)
		addTab(true,"/sys/ggfw/ser/serCate_add.jsp?parent_id="+ser_id+"&top_index="+curTabIndex+"&s_id="+s_id,"维护场景导航");
	else
		addTab(true,"/sys/ggfw/ser/serCate_add.jsp?parent_id="+ser_id+"&top_index="+curTabIndex,"维护场景导航");
}

function openUpdatePage()
{
	var selectIDS = table.getSelecteCheckboxValue("ser_id");
	openAddPage(selectIDS);
}


function addSerCate()
{
	var bean = BeanUtil.getCopy(SerCategoryBean);
	$("#add_table").autoBind(bean);
	
	if(!standard_checkInputInfo("add_table"))
	{
		return;
	}

	bean.parent_id = parent_id;
	bean.ser_id = SerRPC.getNewID();
	bean.id = bean.ser_id;
	if(bean.cat_type != "leaf")
		bean.cat_type = "sub";

	if(SerRPC.insertSerCategory(bean))
	{
		msgAlert("场景式服务主题"+WCMLang.Add_success);
		if(bean.cat_type != "leaf")
		{
			getCurrentFrameObj(top_index).insertCategoryTree(bean.ser_id,bean.cat_name);
			getCurrentFrameObj(top_index).loadSerCategoryTable();
		}
		else
		{
			getCurrentFrameObj(top_index).reloadSerCateTree();
			getCurrentFrameObj(top_index).treeNodeSelected(parent_id);
			getCurrentFrameObj(top_index).loadSerCategoryTable();
		}
		
		tab_colseOnclick(curTabIndex);
	}else
	{
		msgWargin("场景式服务主题"+WCMLang.Add_fail);
	}
}

function updateSerCate()
{
	var bean = BeanUtil.getCopy(SerCategoryBean);
	$("#add_table").autoBind(bean);
	
	if(!standard_checkInputInfo("add_table"))
	{
		return;
	}	
	bean.ser_id = ser_id;	
	if(bean.cat_type != "leaf")
		bean.cat_type = "sub";

	if(SerRPC.updateSerCategory(bean))
	{
		msgAlert("场景式服务主题"+WCMLang.Add_success);
		getCurrentFrameObj(top_index).updateCategoryTree(bean.ser_id,bean.cat_name);
		getCurrentFrameObj(top_index).loadSerCategoryTable();
		tab_colseOnclick(curTabIndex);
	}else
	{
		msgWargin("场景式服务主题"+WCMLang.Add_fail);
	}
}

function deleteSerCategory()
{
	var selectIDS = table.getSelecteCheckboxValue("ser_id");

	if(SerRPC.deleteSerCategory(selectIDS))
	{
		msgAlert("场景式服务主题"+WCMLang.Delete_success);
		loadSerCategoryTable();
		deleteTreeNode(selectIDS);
	}else
	{
		msgWargin("场景式服务主题"+WCMLang.Delete_fail);
	}
}

function sortSerCategory()
{
	var temp_ser_id = ser_id;
	var selectIDS = table.getAllCheckboxValue("ser_id");
	if(SerRPC.sortSerCategory(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
		reloadSerCateTree();
		treeNodeSelected(temp_ser_id);
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}

//添加树节点
function insertCategoryTree(id,c_name)
{
	insertTreeNode(eval('[{"id":'+id+',"text":\"'+c_name+'\","attributes":{}}]'));	
}

//修改树节点
function updateCategoryTree(id,category_name)
{
	updateTreeNode(id,category_name);
}

function selectInfoUrl()
{
	OpenModalWindow("选择信息","/sys/cms/info/article/selectInfo.jsp?top_index="+curTabIndex+"&app_id="+app_id,800,530);
}

function insertContentUrl(url)
{
	$("#url").val(url);
}