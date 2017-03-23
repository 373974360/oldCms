var AssistRPC = jsonrpc.AssistRPC;
var HotWordBean = new Bean("com.deya.wcm.bean.system.assist.HotWordBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "hotword_table";

function reloadHotDataList()
{
	showTurnPage();
	showList();	
}

function initTable(){
	var colsMap = new Map();
	var colsList = new List();	
	//colsList.add(setTitleClos("hot_id","ID","30px","","",""));	
	colsList.add(setTitleClos("hot_name","热词名称","80px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("hot_url","关联地址","","","",""));
	//colsList.add(setTitleClos("app_id","应用ID","80px","","",""));	
	//colsList.add(setTitleClos("site_id","站点ID","80px","","",""));
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
		sortCol = "hot_id";
		sortType = "desc";
	}
	var m = new Map();
	m.put("app_id", app);
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
	beanList = AssistRPC.getHotWordList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("hot_name").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{			
			$(this).html('<a href="javascript:openUpdatePage('+beanList.get(i-1).hot_id+')">'+beanList.get(i-1).hot_name+'</a>');
		}
	});
	
	table.getCol("hot_url").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{			
			var tm = beanList.get(i-1).hot_url;
			if(tm == null || tm == "null" || tm == "NULL"){
				$(this).html("");
			}
			//$(this).html('<a href="javascript:addTab(true,\'/sys/system/assist/hotwords/hotWord_view.jsp?hot_id='+beanList.get(i-1).hot_id+'\',\'元数据信息\')">'+beanList.get(i-1).hot_name+'</a>');
		}
	});	

	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	var m = new Map();
	m.put("app_id", app);
	m.put("site_id", site_id);
	if(table.con_value.trim() != "" && table.con_value != null){
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
		tp.total = AssistRPC.getHotWordCount(m);	
	}else{
		tp.total = AssistRPC.getHotWordCount(m);	
	}	
			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//打开查看窗口
function openViewHotDataPage(hot_id)
{	//OpenModalWindow
	OpenModalWindow("热词查看","/sys/system/assist/hotwords/hotWord_view.jsp?hot_id="+hot_id,360,165);
}

//打开添加窗口
function openAddHotPage()
{
	OpenModalWindow("维护热词","/sys/system/assist/hotwords/hotWord_add.jsp?app="+app+"&site_id="+site_id,360,165);
}

//打开修改窗口
function openUpdateHotDataPage()
{
	var selectIDS = table.getSelecteCheckboxValue("hot_id");
	OpenModalWindow("维护热词","/sys/system/assist/hotwords/hotWord_add.jsp?hot_id="+selectIDS,360,165);
}

function openUpdatePage(hotid){
	OpenModalWindow("维护热词","/sys/system/assist/hotwords/hotWord_add.jsp?hot_id="+hotid,360,165);
}

//添加热词
function addHotData()
{
	var bean = BeanUtil.getCopy(HotWordBean);	
	$("#hotword_table").autoBind(bean);

	if(!standard_checkInputInfo("hotword_table"))
	{
		return;
	}

	if(AssistRPC.addHotWordById(bean))
	{
		msgAlert("热词"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadHotDataList();
	}
	else
	{
		msgWargin("热词"+WCMLang.Add_fail);
	}
}
//修改热词
function updateHotData()
{
	var bean = BeanUtil.getCopy(HotWordBean);	
	$("#hotword_table").autoBind(bean);

	if(!standard_checkInputInfo("hotword_table"))
	{
		return;
	}
  
	if(AssistRPC.updateHotWordById(bean))
	{
		msgAlert("热词"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadHotDataList();
	}
	else
	{
		msgWargin("热词"+WCMLang.Add_fail);
	}
}

//删除热词
function deleteHotData()
{
	var selectIDS = table.getSelecteCheckboxValue("hot_id");
	if(AssistRPC.delHotWordById(selectIDS))
	{
		msgAlert("热词"+WCMLang.Delete_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadHotDataList();
	}else
	{
		msgWargin("热词"+WCMLang.Delete_fail);
	}
}

function closePage(){
	CloseModalWindow();
}

//搜索
function hotDataSearchHandl(obj)
{
	var con_value = $(obj).parent().find("#searchkey").val();
	if(con_value.trim() == "" ||  con_value == null)
	{
		msgAlert(WCMLang.Search_empty);
		return;
	}
	table.con_name = $(obj).parent().find("#searchFields").val(); 
	table.con_value = con_value;
	reloadHotDataList();
}




