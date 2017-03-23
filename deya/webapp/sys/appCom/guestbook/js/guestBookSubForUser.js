var GuestBookRPC = jsonrpc.GuestBookRPC;
var GuestBookSub = new Bean("com.deya.wcm.bean.appCom.guestbook.GuestBookSub",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "table";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件
var m = new Map();

function reloadList()
{
	showList();
	showTurnPage();		
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	colsList.add(setTitleClos("gbs_id","ID","30px","","",""));
	colsList.add(setTitleClos("title","留言主题","300px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("cat_id","所属分类","120px","","",""));
	colsList.add(setTitleClos("publish_status","发布状态","70px","","",""));
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
		sortCol = "gbs_id";
		sortType = "desc";
	}
	m.put("user_id", user_id);	
	m.put("site_id", site_id);
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}

	beanList = GuestBookRPC.getGuestBookSubListForDB(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	

	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("title").each(function(i){
		if(i>0)
		{			
			$(this).html('<a href="javascript:openUpdateGuestBookSubPage('+beanList.get(i-1).gbs_id+')">'+beanList.get(i-1).title+'</a>');
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

	table.getCol("cat_id").each(function(i){
		if(i>0)
		{
			if(cat_map.containsKey(beanList.get(i-1).cat_id+""))
			{
				$(this).text(cat_map.get(beanList.get(i-1).cat_id+""));
			}else
				$(this).text("　");
		}
	});	

	table.getCol("action_col").each(function(i){
		if(i>0)
		{			
			$(this).html('<a href="javascript:openGuestBookPage('+beanList.get(i-1).gbs_id+','+beanList.get(i-1).cat_id+')">查看留言</a>&#160;&#160;&#160;<a href="javascript:openGuestBookCountPage('+beanList.get(i-1).gbs_id+','+beanList.get(i-1).cat_id+')">留言统计</a>');
		}
	});	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){					
	tp.total = GuestBookRPC.getGuestBookSubCount(m);
	tp.show("turn","");
	tp.onPageChange = showList;
}


//打开查看留言窗口
function openGuestBookPage(s_id,cat_id)
{
	addTab(true,"/sys/appCom/guestbook/guestBookList.jsp?site_id="+site_id+"&topnum="+curTabIndex+"&gbs_id="+s_id+"&cat_id="+cat_id,"查看留言");
}

//打开统计窗口
function openGuestBookCountPage(s_id,cat_id)
{
	addTab(true,"/sys/appCom/guestbook/guestBookCount.jsp?site_id="+site_id+"&gbs_id="+s_id+"&cat_id="+cat_id,"留言统计");
}

