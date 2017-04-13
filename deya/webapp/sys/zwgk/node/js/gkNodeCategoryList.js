var GKNodeRPC = jsonrpc.GKNodeRPC;
var GKNodeCategory = new Bean("com.deya.wcm.bean.zwgk.node.GKNodeCategory",true);

var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "menu_table_list";;
var con_m = new Map();



function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	colsList.add(setTitleClos("nodcat_id","ID","30px","","",""));
	colsList.add(setTitleClos("nodcat_name","节点分类名称","200px","","",""));
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("space_col"," ","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){			

	beanList = GKNodeRPC.getChildCategoryList(nodcat_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();			
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	
	
	table.getCol("nodcat_name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openUpdateCatePage('+beanList.get(i-1).nodcat_id+')">'+beanList.get(i-1).nodcat_name+'</a>');
		}
	});		

	table.getCol("sort_col").each(function(i){	
		if(i>0)
		{			
			$(this).html(getSortColStr());			
		}
	});
}

function showTurnPage(){	
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

function openAddCatePage()
{
	OpenModalWindow("修改公开节点分类","/sys/zwgk/node/gkCate_add.jsp",450,135);
}


//添加树节点
function insertMenuTree(id,name)
{
	insertTreeNode(eval('[{"id":'+id+',"text":\"'+name+'\","attributes":{}}]'));	
}

function openUpdateCatePage(nc_id)
{
	var selectIDS = "";
	if(nc_id != null && nc_id != "")
		selectIDS = nc_id;
	else
		selectIDS = table.getSelecteCheckboxValue("nodcat_id");
	OpenModalWindow("修改公开节点分类","/sys/zwgk/node/gkCate_add.jsp?nodcat_id="+selectIDS,450,135);
}

//修改树节点
function updateMenuTree(id,name)
{
	updateTreeNode(id,name);
}

function deleteNodeCate()
{
	var selectIDS = table.getSelecteCheckboxValue("nodcat_id");
	var tempA = selectIDS.split(",");
	var msg = "";
	var msg2 = "";
	for(var i=0;i<tempA.length;i++)
	{
		if(GKNodeRPC.hasNodeByCatID(tempA[i]))
		{
			msg += ","+getNodCatNameByList(tempA[i]);
		}
		if(GKNodeRPC.hasChildNodeByCategory(tempA[i]))
		{
			msg2 += ","+getNodCatNameByList(tempA[i]);
		}
	}
	if(msg2 != "" && msg2.length > 0)
	{
		msgWargin("分类 "+msg2.substring(1)+" 下还包含有子分类，请先清空子分类再删除！");
		return;
	}
	if(msg != "" && msg.length > 0)
	{
		msgWargin("分类 "+msg.substring(1)+" 下还包含有节点信息，请先清空节点再删除！");
		return;
	}
	
	if(GKNodeRPC.deleteGKNodeCategory(selectIDS))
	{
		msgAlert("公开节点分类"+WCMLang.Delete_success);
		loadMenuTable();
		showMenuTree();
	}else
	{
		msgWargin("公开节点分类"+WCMLang.Delete_fail);
	}
}

function sortNodeCate()
{
	var selectIDS = table.getAllCheckboxValue("nodcat_id");
	if(GKNodeRPC.sortGKNodeCategory(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
		showMenuTree();
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}

//移动分类
function moveGKNodeCategory(parent_id)
{
	if(parent_id != "" && parent_id != null)
	{
		var selectIDS = table.getSelecteCheckboxValue("nodcat_id");
		if(GKNodeRPC.moveGKNodeCategory(selectIDS,parent_id))
		{
			msgAlert(WCMLang.Move_success);
			showMenuTree();
			loadMenuTable();
		}else
		{
			msgWargin(WCMLang.Move_fail);
		}
	}
}

function getNodCatNameByList(nodcat_id)
{
	for(var i=0;i<beanList.size();i++)
	{
		if(beanList.get(i).nodcat_id == nodcat_id)
			return beanList.get(i).nodcat_name;
	}
}