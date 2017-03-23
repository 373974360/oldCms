var SiteGroupRPC = jsonrpc.SiteGroupRPC;
var SiteGroupBean = new Bean("com.deya.wcm.bean.control.SiteGroupBean",true);

var val=new Validator();
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "group_table";;
var con_m = new Map();


function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	  
	colsList.add(setTitleClos("sgroup_id","站群ID","60px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("sgroup_name","站群名称","","","",""));
	colsList.add(setTitleClos("sgroup_ip","站群IP","100px","","",""));
	colsList.add(setTitleClos("sgroup_prot","站群端口","60px","","",""));
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id

}

function showList(){			

	beanList = SiteGroupRPC.getSGroupChildListByID(sgroup_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("sgroup_name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openViewPage(\''+beanList.get(i-1).sgroup_id+'\')">'+beanList.get(i-1).sgroup_name+'</a>');
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
	//tp.total = MenuRPC.getChildMenuCount(menu_id);
	tp.total = beanList.size();
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

function openAddGroupPage()
{
	OpenModalWindow("维护站群","/sys/control/group/group_add.jsp?parentID="+sgroup_id,500,300);
}

function openUpdateGroupPage()
{
	var selectIDS = table.getSelecteCheckboxValue("sgroup_id");
	OpenModalWindow("维护站群","/sys/control/group/group_add.jsp?parentID="+sgroup_id+"&sgroup_id="+selectIDS,500,300);
}

function openViewPage(s_id)
{
	OpenModalWindow("查看站群","/sys/control/group/group_view.jsp?sgroup_id="+s_id,500,300);
}

//添加
function addGroup(){
	if(!standard_checkInputInfo(table.table_name))
	{
		return;
	}
	$("#"+table.table_name).autoBind(SiteGroupBean);

	SiteGroupBean.sgroup_sort=SiteGroupRPC.getSGroupSortByID(SiteGroupBean.parent_id)+1;
	//alert(SiteGroupBean.sgroup_sort);
	
	if(SiteGroupRPC.insertSiteGroup(SiteGroupBean))
	{
		msgAlert("站群信息"+WCMLang.Add_success);
		getCurrentFrameObj().changeMemuListTable(SiteGroupBean.parent_id);
		getCurrentFrameObj().insertMenuTree(SiteGroupBean.sgroup_id,SiteGroupBean.sgroup_name);
		//getCurrentFrameObj().treeNodeSelected(SiteGroupBean.parent_id);
		CloseModalWindow();
	}
	else
	{
		msgWargin("站群信息"+WCMLang.Add_fail);
	}
}

//添加树节点
function insertMenuTree(id,dept_name)
{
	insertTreeNode(eval('[{"id":\"'+id+'\","text":\"'+dept_name+'\","attributes":{}}]'));	
}

//删除
function deleteGroup()
{
	var selectIDS = table.getSelecteCheckboxValue("sgroup_id");
	//判断是否有下级节点
	if(SiteGroupRPC.getSGroupSortByID(selectIDS)!='0'){
		msgWargin("该站群下面有节点，不能删除");
		return;
	}
	
	if(SiteGroupRPC.deleteSiteGroup(selectIDS))
	{
		msgAlert("站群信息"+WCMLang.Delete_success);
		getCurrentFrameObj().changeMemuListTable(sgroup_id);
		getCurrentFrameObj().deleteTreeNode(selectIDS);
	}else
	{ 
		msgWargin("站群信息"+WCMLang.Delete_fail);
	}
}

//排序
function funSort()
{
	var selectIDS = table.getAllCheckboxValue("sgroup_id");
	if(SiteGroupRPC.saveSGroupSort(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
		showMenuTree(); 
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}

//修改
function updateGroup(){
	if(!standard_checkInputInfo(table.table_name))
	{
		return;
	}
	$("#"+table.table_name).autoBind(SiteGroupBean);
	SiteGroupBean.sgroup_id=sgroup_id;
 	
	if(SiteGroupRPC.updateSiteGroup(SiteGroupBean))
	{
		msgAlert("站群信息"+WCMLang.Add_success);
		getCurrentFrameObj().changeMemuListTable(SiteGroupBean.parent_id);
		getCurrentFrameObj().updateMenuTree(SiteGroupBean.sgroup_id,SiteGroupBean.sgroup_name);
		CloseModalWindow();
	}
	else
	{
		msgWargin("站群信息"+WCMLang.Add_fail);
	}
}

//修改树节点
function updateMenuTree(id,menu_name)
{
	updateTreeNode(id,menu_name);
}

