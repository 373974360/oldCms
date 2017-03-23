var GuestBookRPC = jsonrpc.GuestBookRPC;
var GuestBookCategory = new Bean("com.deya.wcm.bean.appCom.guestbook.GuestBookCategory",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "table";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件

function reloadList()
{
	showList();
	showTurnPage();		
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	colsList.add(setTitleClos("cat_id","ID","30px","","",""));
	colsList.add(setTitleClos("title","分类名称","300px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("publish_status","发布状态","70px","","",""));
	colsList.add(setTitleClos("user_sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("action_col","操作","200px","","",""));
	colsList.add(setTitleClos("space_col"," ","","","",""));

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
		sortCol = "role_id";
		sortType = "desc";
	}
	
	var m = new Map();	
	m.put("site_id", site_id);
	m.put("app_id", app_id);
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}

	beanList = GuestBookRPC.getGuestBookCategoryList(site_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	

	tp.total = beanList.size();
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("title").each(function(i){
		if(i>0)
		{			
			$(this).html('<a href="javascript:openUpdateGuestBookCategoryPage('+beanList.get(i-1).cat_id+')">'+beanList.get(i-1).title+'</a>');
		}
	});	

	table.getCol("publish_status").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).publish_status == 0)
				$(this).html('未发布');
			else
				$(this).html('已发布');
		}
	});	

	table.getCol("action_col").each(function(i){
		if(i>0)
		{			
			//$(this).html('<a href="javascript:openGuestBookPage('+beanList.get(i-1).cat_id+')">类别管理</a>&#160;&#160;<a href="javascript:openGuestBookReleUser('+beanList.get(i-1).cat_id+')">设置管理员</a>&#160;&#160;<a href="javascript:openAddGuestBookSubPage('+beanList.get(i-1).cat_id+')">新建主题</a>');
			$(this).html('<a href="javascript:openGuestBookPage('+beanList.get(i-1).cat_id+')">类别管理</a>&#160;&#160;<a href="javascript:openAddGuestBookSubPage('+beanList.get(i-1).cat_id+')">新建主题</a>');
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

//打开主题添加窗口
function openAddGuestBookSubPage(cat_id)
{
	addTab(true,"/sys/appCom/guestbook/add_gbSub.jsp?cat_id="+cat_id+"&site_id="+site_id+"&topnum="+curTabIndex,"新建主题");
}

function showTurnPage(){					
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

//打开添加窗口
function openAddGuestBookCategoryPage()
{
	addTab(true,"/sys/appCom/guestbook/add_gbCategory.jsp?site_id="+site_id+"&topnum="+curTabIndex,"新建分类");
}

//打开修改窗口
function openUpdateGuestBookCategoryPage(s_id)
{
	var id = "";
	if(s_id != null)
		id = s_id;
	else
		id = table.getSelecteCheckboxValue("cat_id");
	addTab(true,"/sys/appCom/guestbook/add_gbCategory.jsp?site_id="+site_id+"&topnum="+curTabIndex+"&cat_id="+id,"新建分类");
}

//打开查看类别管理窗口
function openGuestBookPage(s_id)
{
	addTab(true,"/sys/appCom/guestbook/gbClassList.jsp?site_id="+site_id+"&topnum="+curTabIndex+"&cat_id="+s_id,"类别管理");
}

//打开设置管理员窗口
var cur_cat_id=0;
function openGuestBookReleUser(c_id)
{
	cur_cat_id = c_id;
	openSelectOnlySiteUserPage('选择用户','insertGuestBookReleUser',site_id)
}

function getSelectedUserIDS()
{
	return GuestBookRPC.getUserIDForGBCat(cur_cat_id,site_id)
}

function insertGuestBookReleUser(user_ids)
{
	if(GuestBookRPC.insertGuestBookReleUser(cur_cat_id,user_ids,site_id))
	{
		msgAlert("留言分类管理员"+WCMLang.Add_success);
	}
	else
	{
		msgWargin("留言分类管理员"+WCMLang.Add_fail);
	}
}

function insertGuestbookCategory()
{
	var bean = BeanUtil.getCopy(GuestBookCategory);	
	$("#guestbook_table").autoBind(bean);

	if(!standard_checkInputInfo("guestbook_table"))
	{
		return;
	}
	bean.site_id = site_id;
	if(GuestBookRPC.insertGuestBookCategory(bean))
	{
		msgAlert("留言分类"+WCMLang.Add_success);
		getCurrentFrameObj(topnum).reloadList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("留言分类"+WCMLang.Add_fail);
	}
}

function updateGuestbookCategory()
{
	var bean = BeanUtil.getCopy(GuestBookCategory);	
	$("#guestbook_table").autoBind(bean);

	if(!standard_checkInputInfo("guestbook_table"))
	{
		return;
	}
	//bean.site_id = site_id;
	bean.cat_id = cat_id;
	if(GuestBookRPC.updateGuestBookCategory(bean))
	{
		msgAlert("留言分类"+WCMLang.Add_success);
		getCurrentFrameObj(topnum).reloadList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("留言分类"+WCMLang.Add_fail);
	}
}

function deleteGuestBookCategory()
{
	var selectIDS = table.getSelecteCheckboxValue("cat_id");
	var m = new Map();
	m.put("cat_ids",selectIDS);
	
	if(GuestBookRPC.deleteGuestBookCategory(m))
	{
		msgAlert("留言分类"+WCMLang.Delete_success);
		reloadList();
	}else
	{
		msgWargin("留言分类"+WCMLang.Delete_fail);
	}
}


function publishGuestBookCategory(publish_status)
{
	var selectIDS = table.getSelecteCheckboxValue("cat_id");
	var m = new Map();
	m.put("cat_ids",selectIDS);
	m.put("publish_status",publish_status);
	if(GuestBookRPC.publishGuestBookCategory(m))
	{
		msgAlert("留言分类"+WCMLang.Publish_success);
		reloadList();
	}else
	{
		msgWargin("留言分类"+WCMLang.Publish_fail);
	}
}

function sortGuestBookCategory()
{
	var selectIDS = table.getAllCheckboxValue("cat_id");
	if(GuestBookRPC.sortGuestBookCategory(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
		reloadList();
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}
