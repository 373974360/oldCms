var LogSettingRPC = jsonrpc.LogSettingRPC;
var SettingLogsBean = new Bean("com.deya.wcm.bean.logs.SettingLogsBean",true);
var LogManager = new Bean("com.deya.wcm.services.Log.LogManager",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "csLog_table";;
var m =new Map();

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
							//英文名，显示名，宽，高，样式名，点击事件　
	//colsList.add(setTitleClos("audit_id","ID","30px","","",""));	
	colsList.add(setTitleClos("user_name","用户","100px","","",""));
	colsList.add(setTitleClos("audit_des","操作描述","","","",""));
	colsList.add(setTitleClos("audit_time","时间","120px","","",""));
	colsList.add(setTitleClos("ip","IP","100px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("csLog_table");//里面参数为外层div的id
}

function reloadLogSettingList()
{
	showList();	
	showTurnPage();	
}

function showList(){
	
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "audit_time";
		sortType = "desc";
	}	

	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);

		
	beanList = LogSettingRPC.getLogSetting(m);//	
	beanList = List.toJSList(beanList);//把list转成JS的List对象
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	table.getCol("audit_des").each(function(i){	
		if(i>0)
		{			
			$(this).css({"text-align":"left"});		
		}
	});
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	
	tp.total = LogSettingRPC.getLogSettingCount(m);
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//搜索
function roleSearchHandl(obj)
{
	tp.curr_page = 1;
	var start_day = $(obj).parent().find("#start_day").val();
	if(start_day.trim() != "" &&  start_day != null)
	{
		m.put("start_day", start_day);
	}else
	{
		m.remove("start_day");
	}

	var end_day = $(obj).parent().find("#end_day").val();
	if(end_day.trim() != "" &&  end_day != null)
	{
		m.put("end_day", end_day);
	}else
	{
		m.remove("end_day");
	}

	if(start_day.trim() != "" && end_day.trim() != "")
	{
		if(!judgeDate(start_day,end_day))
		{
			parent.msgWargin("结束时间不能早于开始时间，请重新选择结束时间");
			return;
		}
	}

	var con_value = $(obj).parent().find("#searchkey").val();	
	if(con_value.trim() != "" &&  con_value != null)
	{
		m.put("con_name", $(obj).parent().find("#searchFields").val());
		m.put("con_value", con_value);
	}else
	{
		m.remove("con_name");
		m.remove("con_value");
	}

	if(con_value.trim() == "" && start_day.trim() == "" && end_day.trim() == "")
	{
        parent.msgWargin(WCMLang.Search_empty);
		return;
	}
	reloadLogSettingList();
}

//排序触发事件
function csLogSortHandl(val)
{
	table.sortCol = val.substring(0,val.indexOf(","));
	table.sortType = val.substring(val.indexOf(",")+1);	
	reloadLogSettingList();
}

//打开查看窗口
function openViewWorkFlowPage(r_id)
{	
	window.location.href = "/sys/";
}

//打开添加窗口
function openAddWorkFlowPage()
{
	window.location.href = "/sys/";
}

//打开审计日志的修改窗口
function openUpdateLogSettingPage()
{
	var selectIDS = table.getSelecteCheckboxValue("audit_id");
	window.location.href = "/sys/";
}

//删除审计日志记录
function deleteLogSetting()
{
	var selectIDS = table.getSelecteCheckboxValue("audit_id");
	if(LogManager.deleteLogSetting(selectIDS))
	{
        parent.msgAlert("审计日志"+WCMLang.Delete_success);
		reloadLogSettingList();
	}else
	{
        parent.msgWargin("审计日志"+WCMLang.Delete_fail);
	}
}
