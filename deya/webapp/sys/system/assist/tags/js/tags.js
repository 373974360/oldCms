var AssistRPC = jsonrpc.AssistRPC;
var TagsBean = new Bean("com.deya.wcm.bean.system.assist.TagsBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "Tags_table";
var app = "0";

function reloadTagDataList()
{
	showTurnPage();
	showList();	
}

function initTable(){
	var colsMap = new Map();
	var colsList = new List();	
	//colsList.add(setTitleClos("tag_id","ID","50px","","",""));	
	colsList.add(setTitleClos("tag_name","Tag名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("tag_color","颜色","80px","","",""));
	colsList.add(setTitleClos("font_size","字号大小","80px","","",""));	
	colsList.add(setTitleClos("tag_times","热度及关联次数","200px","","",""));
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
		sortCol = "tag_id";
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
	beanList = AssistRPC.getTagsList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("tag_name").each(function(i){
		$(this).css({"text-align":"left"});
		if(i>0)
		{			
			$(this).html('<a href="javascript:openUpdataPage('+beanList.get(i-1).tag_id+')">'+beanList.get(i-1).tag_name+'</a>');
		}
	});
	
	table.getCol("tag_color").each(function(i){
		$(this).css({"text-align":"left"});
		if(i>0)
		{		
			var tm = beanList.get(i-1).tag_color;
			if(tm == null || tm == "null" || tm == "NULL"){
				$(this).html("");
			}
		}
	});	
	table.getCol("font_size").each(function(i){
		$(this).css({"text-align":"left"});
		if(i>0)
		{		
			var tm = beanList.get(i-1).font_size;
			if(tm == null || tm == "null" || tm == "NULL"){
				$(this).html("");
			}
		}
	});	
	table.getCol("tag_times").each(function(i){
		$(this).css({"text-align":"left"});
		if(i>0)
		{		
			var tm = beanList.get(i-1).tag_times;
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
		tp.total = AssistRPC.getTagsCount(m);	
	}else{
		tp.total = AssistRPC.getTagsCount(m);	
	}	
			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//打开查看窗口
function openViewTagDataPage(tag_id)
{	//OpenModalWindow
	OpenModalWindow("Tag查看","/sys/system/assist/tags/tags_view.jsp?tag_id="+tag_id,385,215);
}

//打开添加窗口
function openAddTagPage()
{
	OpenModalWindow("修改Tag","/sys/system/assist/tags/tags_add.jsp?app="+app+"&site_id="+site_id,385,215);
}

//打开修改窗口
function openUpdateTagDataPage()
{
	var selectIDS = table.getSelecteCheckboxValue("tag_id");
	OpenModalWindow("修改Tag","/sys/system/assist/tags/tags_add.jsp?tag_id="+selectIDS,385,215);
}

function openUpdataPage(tagid){
	OpenModalWindow("修改Tag","/sys/system/assist/tags/tags_add.jsp?tag_id="+tagid,385,215);
}

//添加Tag
function addTagData()
{
	var bean = BeanUtil.getCopy(TagsBean);
	$("#Tags_table").autoBind(bean);
	if(bean.font_size == ""){
		bean.font_size = 12;
	}
	if(bean.tag_times == ""){
		bean.tag_times = 0;
	}
	if(!standard_checkInputInfo("Tags_table"))
	{
		return;
	}

	if(AssistRPC.addTagsById(bean))
	{
		msgAlert("Tag"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadTagDataList();
	}
	else
	{
		msgWargin("Tag"+WCMLang.Add_fail);
	}
}
//修改Tag
function updateTagData()
{
	var bean = BeanUtil.getCopy(TagsBean);	
	$("#Tags_table").autoBind(bean);

	if(!standard_checkInputInfo("Tags_table"))
	{
		return;
	}
  
	if(AssistRPC.updateTagsById(bean))
	{
		msgAlert("Tag"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadTagDataList();
	}
	else
	{
		msgWargin("Tag"+WCMLang.Add_fail);
	}
}

//删除Tag
function deleteTagData()
{
	var selectIDS = table.getSelecteCheckboxValue("tag_id");
	if(AssistRPC.delTagsById(selectIDS))
	{
		msgAlert("Tag"+WCMLang.Delete_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadTagDataList();
	}else
	{
		msgWargin("Tag"+WCMLang.Delete_fail);
	}
}

function closePage(){
	CloseModalWindow();
}

//搜索
function tagDataSearchHandl(obj)
{
	alert("do search Tagss");
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




