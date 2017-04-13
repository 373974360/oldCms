var MenuRPC = jsonrpc.MenuRPC;
var OperateRPC = jsonrpc.OperateRPC;
var MenuBean = new Bean("com.deya.wcm.bean.org.operate.MenuBean",true);

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
	
	colsList.add(setTitleClos("menu_name","菜单名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	//colsList.add(setTitleClos("opt_id","权限ID","40px","","",""));
	colsList.add(setTitleClos("opt_name","权限名称","100px","","",""));
	//colsList.add(setTitleClos("handls","执行函数","","","",""));
	colsList.add(setTitleClos("menu_url","链接地址","","","",""));
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){			

	beanList = MenuRPC.getChildMenuList(menu_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	
	
	table.getCol("menu_name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openViewMengPage('+beanList.get(i-1).menu_id+')">'+beanList.get(i-1).menu_name+'</a>');
		}
	});	

	table.getCol("opt_name").each(function(i){	
		if(i>0)
		{			
			$(this).html(getOperateInfoByID(beanList.get(i-1).opt_id));			
		}
	});

	table.getCol("sort_col").each(function(i){	
		if(i>0)
		{			
			$(this).html(getSortColStr());			
		}
	});

	table.getCol("menu_url").each(function(i){	
		if(i>0)
		{			
			$(this).css({"text-align":"left"});		
		}
	});
}

function openViewMengPage(m_id)
{
	OpenModalWindow("查看菜单信息","/sys/org/operate/menu_view.jsp?menu_id="+m_id,500,300);
}

function showTurnPage(){	
	
	tp.total = MenuRPC.getChildMenuCount(menu_id);			
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

function openAddMenuPage()
{
	OpenModalWindow("修改菜单","/sys/org/operate/menu_add.jsp?parentID="+menu_id,500,300);
}

function addMenu()
{
	var bean = BeanUtil.getCopy(MenuBean);	
	$("#menu_table").autoBind(bean);

	if(!standard_checkInputInfo("menu_table"))
	{
		return;
	}
	bean.menu_level = 0;
	bean.menu_id = MenuRPC.getMenuID();
	bean.parent_id = parent_id;
	if(MenuRPC.insertMenu(bean))
	{
		msgAlert("菜单信息"+WCMLang.Add_success);
		getCurrentFrameObj().changeMemuListTable(parent_id);
		getCurrentFrameObj().insertMenuTree(bean.menu_id,bean.menu_name);
		CloseModalWindow();
	}
	else
	{
		msgWargin("菜单信息"+WCMLang.Add_fail);
	}
}

//添加树节点
function insertMenuTree(id,dept_name)
{
	insertTreeNode(eval('[{"id":'+id+',"text":\"'+dept_name+'\","attributes":{}}]'));	
}

function openUpdateMenuPage()
{
	var selectIDS = table.getSelecteCheckboxValue("menu_id");
	OpenModalWindow("修改菜单","/sys/org/operate/menu_add.jsp?parentID="+menu_id+"&menu_id="+selectIDS,500,300);
}

function updateMenu()
{
	var bean = BeanUtil.getCopy(MenuBean);	
	$("#menu_table").autoBind(bean);
	if(!standard_checkInputInfo("menu_table"))
	{
		return;
	}

	bean.menu_id = menu_id;
	bean.menu_level = 0;
	if(MenuRPC.updateMenu(bean))
	{
		msgAlert("菜单信息"+WCMLang.Add_success);
		getCurrentFrameObj().changeMemuListTable(parent_id);
		getCurrentFrameObj().updateMenuTree(bean.menu_id,bean.menu_name);
		CloseModalWindow();
	}
	else
	{
		msgWargin("菜单信息"+WCMLang.Add_fail);
	}
}

//修改树节点
function updateMenuTree(id,menu_name)
{
	updateTreeNode(id,menu_name);
}

function deleteMenu()
{
	var selectIDS = table.getSelecteCheckboxValue("menu_id");
	if(MenuRPC.deleteMenu(selectIDS))
	{
		msgAlert("菜单信息"+WCMLang.Delete_success);
		getCurrentFrameObj().changeMemuListTable(menu_id);
		getCurrentFrameObj().deleteTreeNode(selectIDS);
	}else
	{
		msgWargin("菜单信息"+WCMLang.Delete_fail);
	}
}

function sortMenu()
{
	var selectIDS = table.getAllCheckboxValue("menu_id");
	if(MenuRPC.saveMenuSort(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
		showMenuTree();
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}

//得到权限信息Map
var opt_map = new Map();
function getOperateMap()
{
	opt_map = OperateRPC.getOptMap();
	opt_map = Map.toJSMap(opt_map);
}

function getOperateInfoByID(opt_id)
{
	if(opt_map.get(opt_id) != null)
		return opt_map.get(opt_id).opt_name;
	else
		return "";
}

//移动菜单
function moveMenu(parent_id)
{
	if(parent_id != "" && parent_id != null)
	{
		var selectIDS = table.getSelecteCheckboxValue("menu_id");			
		if(MenuRPC.moveMenu(parent_id,selectIDS)){
			msgAlert("菜单"+WCMLang.Move_success);
			loadMenuTable()
			showMenuTree();
		}else
		{
			msgWargin("菜单"+WCMLang.Move_fail);
			return;
		}
	}
}

function openSelectMenu(title,handl_name)
{
	OpenModalWindow(title,"/sys/org/operate/select_menu.jsp?handl_name="+handl_name,610,510);
}