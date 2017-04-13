var CalendarRPC = jsonrpc.CalendarRPC;
var CalendarBean = new Bean("com.deya.wcm.bean.appeal.calendar.CalendarBean",true);

var val= new Validator();

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
	table.table_name = "app_calendar_table";
var con_m = new Map();

function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("ca_name","名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("ca_type","性质","","","",""));　
	colsList.add(setTitleClos("ca_flag","上班的标识","","","",""));　
	colsList.add(setTitleClos("start_dtime","开始时间","","","",""));　
	colsList.add(setTitleClos("end_dtime","结束时间","","","",""));　
	//colsList.add(setTitleClos("sort_id","排序","100px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}
function reloadCalendarList()
{
	showList();
	showTurnPage();
}
function showList(){
			
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "ca_id";
		sortType = "desc";
	}
	beanList = CalendarRPC.getCalendarList();
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("ca_name").each(function(i){	
		if(i>0)
		{
			$(this).html('<a href="javascript:openUpdateCalendarPage('+beanList.get(i-1).ca_id+')">'+beanList.get(i-1).ca_name+'</a>');	
		}
	});
	table.getCol("ca_flag").each(function(i){
		if(i>0)
		{	// 0：休假 1：补假
		    var ca_flag = beanList.get(i-1).ca_flag;
			if(ca_flag=='0'){
				ca_flag = '休假';
			}else if(ca_flag=='1'){
				ca_flag = '补假';
			}
			$(this).html(ca_flag);
		}				
	});
	table.getCol("type").each(function(i){
		if(i>0)
		{	// 0: 其他   1: 元旦  2: 春节 3: 清明节  4: 劳动节  5: 端午节  6: 中秋节   7: 国庆节  
		    var ca_type = beanList.get(i-1).ca_type;
			if(ca_type=='1'){
				ca_type = '元旦';
			}else if(ca_type=='2'){
				ca_type = '春节';
			}else if(ca_type=='3'){
				ca_type = '清明节';
			}else if(ca_type=='4'){
				ca_type = '劳动节';
			}else if(ca_type=='5'){
				ca_type = '端午节';
			}else if(ca_type=='6'){
				ca_type = '中秋节';
			}else if(ca_type=='7'){
				ca_type = '国庆节';
			}else if(ca_type=='0'){
				ca_type = '其他';
			}
			$(this).html(ca_type);
		}				
	});
/*	table.getCol("sort_id").each(function(i){	
		if(i>0)
		{			
			$(this).html(getSortColStr());			
		}
	});*/
	Init_InfoTable(table.table_name);
}
function showTurnPage(){
	
	tp.total = CalendarRPC.getCalendarCount();			
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}
// 保存排序
/*function sortCalendarSort()
{
	var selectIDS = table.getAllCheckboxValue("ca_id");
	if(CalendarRPC.saveCalendarSort(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}*/
// 添加节假日信息
function addCalendarRecord()
{
	OpenModalWindow("新建节假日信息","/sys/appeal/calendar/calendar_add.jsp?type=add",450,239);
}
function openUpdateCalendarPage(ca_id)
{
	OpenModalWindow("修改节假日信息","/sys/appeal/calendar/calendar_add.jsp?type=update&ca_id="+ca_id,450,239);
}
// 修改节假日信息
function updateRecord1()
{  
	var ca_id = table.getSelecteCheckboxValue("ca_id");
	OpenModalWindow("修改节假日信息","/sys/appeal/calendar/calendar_add.jsp?type=update&ca_id="+ca_id,450,239);
}

// 删除节假日信息
function deleteCalendar()
{
	var ca_id = table.getSelecteCheckboxValue("ca_id");
	var mp = new Map();
	mp.put("ca_id", ca_id);
	if(CalendarRPC.deleteCalendar(mp))
	{
		msgAlert("节假日信息"+WCMLang.Delete_success);
		reloadCalendarList();
	}
	else
	{
		msgWargin("节假日信息"+WCMLang.Delete_fail);
	}
}
//添加节假日信息-保存事件
function addCalendar()
{  
	var addBean = BeanUtil.getCopy(CalendarBean);
	$("#app_calendar_table").autoBind(addBean);
	if(!standard_checkInputInfo("app_calendar_table"))
	{
		return;
	}

	if(judgeDate(addBean.end_dtime,addBean.start_dtime))
	{
		msgWargin("结束时间不能小于开始时间，请重新设置结束时间");
		return;
	}

	addBean.ca_id = CalendarRPC.getInsertID();
	if(CalendarRPC.insertCalendar(addBean))
	{
		msgAlert("节假日信息"+WCMLang.Add_success);
		getCurrentFrameObj().reloadCalendarList();
		CloseModalWindow();
	}else{
		msgWargin("节假日信息"+WCMLang.Add_fail);
		return;
	}
}
// 修改节假日信息-保存事件
function updateCalendar()
{
	var updateBean = BeanUtil.getCopy(CalendarBean);
	$("#app_calendar_table").autoBind(updateBean);
	if(!standard_checkInputInfo("app_calendar_table"))
	{
		return;
	}	
	if(judgeDate(updateBean.end_dtime,updateBean.start_dtime))
	{
		msgWargin("结束时间不能小于开始时间，请重新设置结束时间");
		return;
	}
	updateBean.ca_id = ca_id;
	if(CalendarRPC.updateCalendar(updateBean))
	{
		msgAlert("节假日信息"+WCMLang.Set_success);
		getCurrentFrameObj().reloadCalendarList();
		CloseModalWindow();
	}
	else
	{
		msgWargin("节假日信息"+WCMLang.Set_fail);
		return;
	}
}
//节点排序
/*function sortCalendar()
{
	var selectIDS = table.getAllCheckboxValue("pur_id");
	
	if(CalendarRPC.saveCalendarSort(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
		reloadCalendarList();	
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}*/