var InfoBaseRPC = jsonrpc.InfoBaseRPC;
var ModelRPC = jsonrpc.ModelRPC;
var GKNodeRPC = jsonrpc.GKNodeRPC;
var UserManRPC = jsonrpc.UserManRPC;
var SiteRPC = jsonrpc.SiteRPC;
var selectBean = null;//当前选中项对象

var serarch_con = "";//查询条件
var search_steps = "";
var tj = "";
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "info_article_table";
var infoIdGoble = 0;
var infoStatus = "8";
var finalStatus = "0";
var stepID = "100";
var subNode = false;
var current_page_num = 1;
var is_save_first_page = false;//操作成功后是否保存在第一页
var highSearchString = "";//高级搜索的字符串

function setInfoGoble(n){
	infoIdGoble = n;
}
function getInfoGoble(n){
	return infoIdGoble;
}

function reloadInfoDataList()
{
	if(is_save_first_page == true)
		current_page_num = 1;

	tp.curr_page = current_page_num;
	showTurnPage();
	showList();	
	is_save_first_page = false;
}

function initTable(){
	var colsMap = new Map();
	var colsList = new List();	
	
	colsList.add(setTitleClos("title","标题","","","",""));//英文名，显示名，宽，高，样式名，点击事件
	colsList.add(setTitleClos("site_id","所属节点","","","",""));
	colsList.add(setTitleClos("actions","管理操作","90px","","",""));
	colsList.add(setTitleClos("weight","权重","30px","","",""));
	colsList.add(setTitleClos("input_user","录入人","60px","","",""));
	colsList.add(setTitleClos("opt_time","发布时间","100px","","",""));
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
		sortCol = "ci.id desc,ci.released_dtime";
		sortType = "desc";
	}
	var m = new Map();		
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	m.put("cat_id", cid);
	
	if(infoStatus != "none" && infoStatus != ""){
		m.remove("info_status");
		m.put("info_status", infoStatus);
	}	
	
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}
	
	beanList = InfoBaseRPC.getGKZNInfoList(m);//第一个参数为站点ID，暂时默认为空	
		
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("title").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{	
			var model_ename = ModelRPC.getModelEName(beanList.get(i-1).model_id);
			$(this).addClass("ico_"+model_ename);
			$(this).css("padding-left","20px");
			$(this).html('<a href="javascript:openViewPage('+beanList.get(i-1).info_id+',\''+beanList.get(i-1).site_id+'\')">'+beanList.get(i-1).title+'</a>');
		}
	});

	table.getCol("site_id").each(function(i){		
		if(i>0)
		{					
			$(this).text(GKNodeRPC.getNodeNameByNodeID(beanList.get(i-1).site_id));			
		}
	});

	table.getCol("input_user").each(function(i){		
		if(i>0)
		{					
			$(this).text(UserManRPC.getUserRealName(beanList.get(i-1).input_user));			
		}
	});

	table.getCol("opt_time").each(function(i){
			var nm = parseInt(infoStatus);
		if(i>0)
		{	
			if(nm == 8)
			{
				var t = beanList.get(i-1).released_dtime;
				if(t != "")
					$(this).text(t.substring(0,t.length-3));
				else
					$(this).text("");
			}else
			{
				var t = beanList.get(i-1).opt_dtime;
				if(t != "")
					$(this).text(t.substring(0,t.length-3));
				else
					$(this).text("");
			}
		}else
		{			
			var str = "";
			switch(nm){
				case 0:str = "更新时间";break;
				case 1:str = "提交时间";break;
				case 2:str = "提交时间";break;				
				default:str = "发布时间";break;
			}
			$(this).text(str);
		}
		
	});

	table.getCol("actions").each(function(i){
		if(i>0)
		{	
			$(this).css({"text-align":"center"});
			var nm = parseInt(infoStatus);	
			var str = "<ul class=\"optUL\">";			
			switch(nm){			
			case 3:
				str += "<li id='302' class='ico_publish'><a  title='发布' href='javascript:doPublish("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li><li id='300' class='ico_edit' ><a  title='修改' href='javascript:openUpdatePage("+beanList.get(i-1).info_id+","+beanList.get(i-1).cat_id+",\""+beanList.get(i-1).site_id+"\")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";break;
			case 4:
				str += "<li id='302' class='ico_publish'><a title='发布' href='javascript:doPublish("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li><li id='300' class='ico_edit' ><a  title='修改'href='javascript:openUpdatePage("+beanList.get(i-1).info_id+","+beanList.get(i-1).cat_id+",\""+beanList.get(i-1).site_id+"\")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";break;
			case 8:
				str += "<li class='ico_view'><a href='javascript:doView("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li><li id='300' class='ico_edit' ><a  title='修改' href='javascript:openUpdatePage("+beanList.get(i-1).info_id+","+beanList.get(i-1).cat_id+",\""+beanList.get(i-1).site_id+"\")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li><li id='307' class='ico_cancel'><a  title='撤销' href='javascript:doCancel("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";break;			
			}
			$(this).html(str+"</ul>");
		}
	});
	current_page_num = tp.curr_page;	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	var m = new Map();	
	
	if(infoStatus != "none" && infoStatus != ""){
		m.remove("info_status");
		m.put("info_status", infoStatus);
	}	
	m.put("cat_id", cid);	
	if(table.con_value.trim() != "" && table.con_value != null){
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);		
	}	
	tp.total = InfoBaseRPC.getGKZNInfoCount(m);
	tp.show("turn","");	
	tp.onPageChange = showList;		
}

//打开修改窗口
function openUpdatePage(info_id,cat_id,site_ids)
{
	top.addTab(true,"/sys/cms/info/article/m_gk_gkzn.jsp?info_id="+info_id+"&cid="+cat_id+"&site_id="+site_ids+"&app_id="+app+"&topnum="+top.curTabIndex,"维护信息");	
}

function initTabAndStatus()
{	
	$(".fromTabs > li").each(function(){	
		$(this).hover(
		  function () {
			$(this).addClass("list_tab_over");
		  },
		  function () {
			$(this).removeClass("list_tab_over");
		  }
		);
		
		$(this).click(
		  function () {
			$(".fromTabs > li").removeClass("list_tab_cur");
			$(this).addClass("list_tab_cur");
			$(".infoListTable").addClass("hidden");			
			$("#listTable_"+$(this).index()).removeClass("hidden");
			changeStatus($(this).index());
		  }
		);
	});
}

function changeStatus(num){	
	current_page_num = 1;
	snum = num;
	infoStatus = "none";
	finalStatus = "none";
	highSearchString = "";	
	table.sortCol="";
	table.sortType = "";
	$("#orderByFields option").eq(0).attr("selected",true);	
	clickLabelHandl(num);
	reloadInfoDataList();
}

//点击标签时重置属性
function clickLabelHandl(num)
{
	switch(num){
	case 0:
		infoStatus = "8";
		stepID = "100";
		finalStatus = "0";
		search_steps = "";
		break;
	case 1:
		infoStatus = "4";
		stepID = "100";
		finalStatus = "0";
		search_steps = "";
		break;
	case 2:
		infoStatus = "3";
		stepID = "100";
		finalStatus = "0";
		search_steps = "";
		break;	
	}	
}

function openViewPage(i_id,site_ids)
{
	//window.location.href = "/sys/cms/info/article/infoView.jsp?info_id="+i_id+"&site_id="+site_id+"&snum="+snum;
	top.addTab(true,"/sys/cms/info/article/infoView.jsp?info_id="+i_id+"&site_id="+site_ids+"&topnum="+top.curTabIndex,"查看信息");
}

//生成静态页面
function createStaticContentHtml(){
	var selectList = table.getSelecteBeans();
	if(InfoBaseRPC.createContentHTML(selectList)){
		top.msgAlert("静态页生成成功");		
	}else{
		top.msgWargin("静态页生成失败");
	}
}

//信息发布
function publishInfo(){
	var selectList = table.getSelecteBeans();
	if(InfoBaseRPC.updateInfoStatus(selectList,"8")){
		top.msgAlert("信息发布成功");
		reloadInfoDataList();
	}else{
		top.msgWargin("信息发布失败");
	}
}

//信息撤销
function cancleInfo(){
	var selectList = table.getSelecteBeans();
	if(InfoBaseRPC.updateInfoStatus(selectList,"3")){
		top.msgAlert("信息撤销成功");
		reloadInfoDataList();
	}else{
		top.msgWargin("信息撤销失败");
	}
}

//搜索
function searchInfo()
{
	var keywords = $("#searchkey").val();
	if(keywords.trim() == "" ||  keywords == null){
		top.msgAlert(WCMLang.Search_empty);
		return;
	}
	var orderFeilds = $("#orderByFields").val();
	table.con_name = "ci.title";
	table.con_value = keywords;
	
	var sortC = "";
	var sortT = "";
	
	if(orderFeilds == "1"){
		sortC = "ci.released_dtime";
		sortT = "desc";
	}else if(orderFeilds == "2"){
		sortC = "ci.released_dtime";
		sortT = "asc";
	}else if(orderFeilds == "3"){
		sortC = "ci.weight";
		sortT = "desc";
	}else if(orderFeilds == "4"){
		sortC = "ci.weight";
		sortT = "asc";
	}
	
	table.sortCol = sortC;
	table.sortType = sortT;
	
	reloadInfoDataList();
}

//时间排序
function changeTimeSort(val)
{
	changeTimeSortHandl(val);
	reloadInfoDataList();
}

function changeTimeSortHandl(val)
{
	table.sortCol = "";
	table.sortType = "";

	switch(val)
	{
		case "1": table.sortCol="ci.id desc,ci.released_dtime";
				table.sortType = "desc";
				break;
		case "2":table.sortCol="ci.released_dtime";
				table.sortType = "asc";
				break;
		case "3": table.sortCol="ci.weight desc,ci.info_id";
				table.sortType = "desc";
				break;
		case "4": table.sortCol="ci.weight asc,ci.info_id";
				table.sortType = "desc";
				break;
	}	
}

//单条信息预览
function doView(num){
	var url = beanList.get(num).content_url;
	
	if(url.indexOf("http://") > -1)
	{		
		window.open(url);
	}
	else
	{		
		window.open(SiteRPC.getSiteDomain(getRealSiteIDByApp(beanList.get(num).app_id,beanList.get(num).site_id))+url);
	}	
}

//根据应用ID判断使用哪个站点ID
function getRealSiteIDByApp(t_app_id,t_site_id)
{
	if(t_app_id == "cms")
		return t_site_id;
	else
		return SiteRPC.getSiteIDByAppID(t_app_id);
}


//单条信息发布
function doPublish(num){
	var list = new List();
	list.add(beanList.get(num));
	if(InfoBaseRPC.updateInfoStatus(list,"8")){
		top.msgAlert("信息发布成功");
		reloadInfoDataList();
	}else{
		top.msgWargin("信息发布失败");
	}
}

//单条信息撤销
function doCancel(num){
	var list = new List();
	list.add(beanList.get(num));
	if(InfoBaseRPC.updateInfoStatus(list,"3")){
		top.msgAlert("信息撤销成功");
		reloadInfoDataList();
	}else{
		top.msgWargin("信息撤销失败");
	}
}