var GuestBookRPC = jsonrpc.GuestBookRPC;
var GuestBookClass = new Bean("com.deya.wcm.bean.appCom.guestbook.GuestBookClass",true);

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
	colsList.add(setTitleClos("class_id","ID","30px","","",""));
	colsList.add(setTitleClos("name","分类名称","300px","","",""));//英文名，显示名，宽，高，样式名，点击事件　	
	colsList.add(setTitleClos("user_sort_col","排序","100px","","",""));	
	colsList.add(setTitleClos("space_col"," ","","","",""));

	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){
	beanList = GuestBookRPC.getGuestBookClassList(site_id,cat_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	

	tp.total = beanList.size();
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("name").each(function(i){
		if(i>0)
		{			
			$(this).html('<a href="javascript:openUpdateGuestBookClassPage('+beanList.get(i-1).class_id+')">'+beanList.get(i-1).name+'</a>');
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

//打开添加窗口
function openAddGuestBookClassPage()
{
	top.OpenModalWindow("维护类别","/sys/appCom/guestbook/add_gbClass.jsp?cat_id="+cat_id+"&site_id="+site_id+"&="+top.curTabIndex,550,250);
}

//打开修改窗口
function openUpdateGuestBookClassPage(s_id)
{
	var id = "";
	if(s_id != null)
		id = s_id;
	else
		id = table.getSelecteCheckboxValue("class_id");
	top.OpenModalWindow("维护类别","/sys/appCom/guestbook/add_gbClass.jsp?class_id="+id,550,250);
}

function insertGuestbookClass()
{
	var bean = BeanUtil.getCopy(GuestBookClass);	
	$("#guestbook_table").autoBind(bean);

	if(!standard_checkInputInfo("guestbook_table"))
	{
		return;
	}
	bean.site_id = site_id;
	bean.cat_id = cat_id;
	if(GuestBookRPC.insertGuestBookClass(bean))
	{
		top.msgAlert("留言类别"+WCMLang.Add_success);
		top.getCurrentFrameObj().reloadList();
		top.CloseModalWindow();
	}
	else
	{
		top.msgWargin("留言类别"+WCMLang.Add_fail);
	}
}

function updateGuestbookClass()
{
	var bean = BeanUtil.getCopy(GuestBookClass);	
	$("#guestbook_table").autoBind(bean);

	if(!standard_checkInputInfo("guestbook_table"))
	{
		return;
	}

	bean.class_id = class_id;
	if(GuestBookRPC.updateGuestBookClass(bean))
	{
		top.msgAlert("留言类别"+WCMLang.Add_success);
		top.getCurrentFrameObj().reloadList();
		top.CloseModalWindow();
	}
	else
	{
		top.msgWargin("留言类别"+WCMLang.Add_fail);
	}
}

function deleteGuestBookClass()
{
	var selectIDS = table.getSelecteCheckboxValue("class_id");
	var m = new Map();
	m.put("class_ids",selectIDS);
	
	if(GuestBookRPC.deleteGuestBookClass(m))
	{
		top.msgAlert("留言类别"+WCMLang.Delete_success);
		reloadList();
	}else
	{
		top.msgWargin("留言类别"+WCMLang.Delete_fail);
	}
}


function publishGuestBookClass(publish_status)
{
	var selectIDS = table.getSelecteCheckboxValue("class_id");
	var m = new Map();
	m.put("class_ids",selectIDS);
	m.put("publish_status",publish_status);
	if(GuestBookRPC.publishGuestBookClass(m))
	{
		top.msgAlert("留言类别"+WCMLang.Publish_success);
		reloadList();
	}else
	{
		top.msgWargin("留言类别"+WCMLang.Publish_fail);
	}
}

function sortGuestBookClass()
{
	var selectIDS = table.getAllCheckboxValue("class_id");
	if(GuestBookRPC.sortGuestBookClass(selectIDS))
	{
		top.msgAlert(WCMLang.Sort_success);
		reloadList();
	}else
	{
		top.msgWargin(WCMLang.Sort_fail);
	}
}
