var AssistRPC = jsonrpc.AssistRPC;
var SourceBean = new Bean("com.deya.wcm.bean.system.assist.SourceBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "Source_table";

function reloadSourceDataList()
{
	showTurnPage();
	showList();	
}

function initTable(){
	var colsMap = new Map();
	var colsList = new List();	
	//colsList.add(setTitleClos("source_id","ID","50px","","",""));	
	colsList.add(setTitleClos("source_name","来源名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("source_initial","首字母","60px","","",""));
	colsList.add(setTitleClos("source_url","来源URL","260px","","",""));	
	colsList.add(setTitleClos("logo_path","来源LOGO路径","260px","","",""));
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
		sortCol = "source_id";
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
	beanList = AssistRPC.getSourceList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("source_name").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{			
			$(this).html('<a href="javascript:openUpdatePage('+beanList.get(i-1).source_id+')">'+beanList.get(i-1).source_name+'</a>');
		}
	});
	
	table.getCol("source_url").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{		
			var tm = beanList.get(i-1).source_url;
			if(tm == null || tm == "null" || tm == "NULL"){
				$(this).html("");
			}
		}
	});
	
	table.getCol("logo_path").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{			
			var tm = beanList.get(i-1).logo_path;
			if(tm == null || tm == "null" || tm == "NULL"){
				$(this).html("");
			}
		}
	});
	
	table.getCol("source_initial").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{			
			var tm = beanList.get(i-1).source_initial;
			if(tm == null || tm == "null" || tm == "NULL"){
				$(this).html("");
			}
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
		tp.total = AssistRPC.getSourceCount(m);	
	}else{
		tp.total = AssistRPC.getSourceCount(m);	
	}	
			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//打开查看窗口
function openViewSourceDataPage(source_id)
{	//OpenModalWindow
	parent.OpenModalWindow("来源查看","/sys/system/assist/source/source_view.jsp?source_id="+source_id,380,215);
}

//打开添加窗口
function openAddSourcePage()
{
    parent.OpenModalWindow("修改来源","/sys/system/assist/source/source_add.jsp?app="+app+"&site_id="+site_id,380,215);
}

//打开修改窗口
function openUpdateSourceDataPage()
{
	var selectIDS = table.getSelecteCheckboxValue("source_id");
    parent.OpenModalWindow("修改来源","/sys/system/assist/source/source_add.jsp?source_id="+selectIDS,380,215);
}

function openUpdatePage(sourceid){
    parent.OpenModalWindow("修改来源","/sys/system/assist/source/source_add.jsp?source_id="+sourceid,380,215);
}

//添加Source
function addSourceData()
{
	var bean = BeanUtil.getCopy(SourceBean);	
	$("#Source_table").autoBind(bean);

	if(!standard_checkInputInfo("Source_table"))
	{
		return;
	}

	if(AssistRPC.addSourceById(bean))
	{
        parent.msgAlert("来源"+WCMLang.Add_success);
        parent.CloseModalWindow();
        parent.getCurrentFrameObj().reloadSourceDataList();
	}
	else
	{
        parent.msgWargin("来源"+WCMLang.Add_fail);
	}
}
//修改Source
function updateSourceData()
{
	var bean = BeanUtil.getCopy(SourceBean);	
	$("#Source_table").autoBind(bean);

	if(!standard_checkInputInfo("Source_table"))
	{
		return;
	}
  
	if(AssistRPC.updateSourceById(bean))
	{
        parent.msgAlert("来源"+WCMLang.Add_success);
        parent.CloseModalWindow();
        parent.getCurrentFrameObj().reloadSourceDataList();
	}
	else
	{
        parent.msgWargin("来源"+WCMLang.Add_fail);
	}
}

//删除Source
function deleteSourceData()
{
	var selectIDS = table.getSelecteCheckboxValue("source_id");
	if(AssistRPC.delSourceById(selectIDS))
	{
        parent.msgAlert("来源"+WCMLang.Delete_success);
        parent.CloseModalWindow();
        parent.getCurrentFrameObj().reloadSourceDataList();
	}else
	{
        parent.msgWargin("来源"+WCMLang.Delete_fail);
	}
}

function closePage(){
    parent.CloseModalWindow();
}

//搜索
function sourceDataSearchHandl(obj)
{
	alert("do search Sources");
//	var con_value = $(obj).parent().find("#searchkey").val();
//	if(con_value.trim() == "" ||  con_value == null)
//	{
//		msgAlert(WCMLang.Search_empty);
//		return;
//	}
//	table.con_name = $(obj).parent().find("#searchFields").val(); 
//	table.con_value = con_value;
//	reloadMetaDataList();
}




