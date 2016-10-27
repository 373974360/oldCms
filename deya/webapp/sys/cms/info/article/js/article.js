var InfoBaseRPC = jsonrpc.InfoBaseRPC;
var ArticleRPC = jsonrpc.ArticleRPC;
var InfoBean = new Bean("com.deya.wcm.bean.cms.info.InfoBean",true);
var ArticleBean = new Bean("com.deya.wcm.bean.cms.info.ArticleBean",true);
var RelatedBean = new Bean("com.deya.wcm.bean.cms.info.RelatedInfoBean",true);

////
var AssistRPC = jsonrpc.AssistRPC;

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "info_article_table";
var infoIdGoble = 0;

function setInfoGoble(n){
	infoIdGoble = n;
}
function getInfoGoble(n){
	return infoIdGoble;
}

function reloadInfoDataList()
{
	showTurnPage();
	showList();	
}

function initTable(){
	var colsMap = new Map();
	var colsList = new List();	
	colsList.add(setTitleClos("info_id","ID","50px","","",""));	
	colsList.add(setTitleClos("title","标题","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("author","作者","100px","","",""));
	colsList.add(setTitleClos("info_status","信息状态","100px","","",""));
	colsList.add(setTitleClos("input_dtime","录入时间","120px","","",""));
	colsList.add(setTitleClos("released_dtime","发布时间","120px","","",""));
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
		sortCol = "id desc,released_dtime";
		sortType = "desc";
	}
	var m = new Map();
//	m.put("app_id", app);
	m.put("site_id", site_id);
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	
//	m.put("is_auto_up", app);
//	m.put("is_host", app);
//	m.put("info_status", app);
	m.put("final_status", "0");;
	m.put("cat_id", cid);
	
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}
	beanList = InfoBaseRPC.getInfoList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("title").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{	if(beanList.get(i-1).is_host == 2){
				$(this).html('<a href="javascript:openUpdatePage2('+beanList.get(i-1).info_id+')">'+beanList.get(i-1).title+'</a>');
			}else
				$(this).html('<a href="javascript:openUpdatePage('+beanList.get(i-1).info_id+')">'+beanList.get(i-1).title+'</a>');
		}
	});

	table.getCol("author").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{			
			var au = beanList.get(i-1).author;
			if(au == null || au == "null"){
				$(this).html("");
			}
		}
	});
	table.getCol("released_dtime").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{			
			var au = beanList.get(i-1).released_dtime;
			if(au == null || au == "null"){
				$(this).html("");
			}
		}
	});
	table.getCol("info_status").each(function(i){
		$(this).css({"text-align":"center"});	
		if(i>0)
		{			
			var st = beanList.get(i-1).info_status;
			switch(st){
			case 0:
				$(this).html("草稿");break;
			case 1:
				$(this).html("退稿");break;
			case 2:
				$(this).html("待审");break;
			case 3:
				$(this).html("撤稿");break;
			case 4:
				$(this).html("待发");break;
			case 8:
				$(this).html("发布");break;
			default:
				$(this).html("");break;
			}
		}
	});
	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	var m = new Map();
//	m.put("app_id", app);
	m.put("site_id", site_id);
	
//	m.put("is_auto_up", app);
//	m.put("is_host", app);
//	m.put("info_status", "");
	m.put("final_status", "0");
	m.put("cat_id", cid);
	
	if(table.con_value.trim() != "" && table.con_value != null){
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
		tp.total = InfoBaseRPC.getInfoCount(m);	
	}else{
		tp.total = InfoBaseRPC.getInfoCount(m);	
	}	
			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//打开相关信息选择窗口
function openLinkInfoDataPage(Info_id)
{	//OpenModalWindow
	top.OpenModalWindow("选择相关信息","/sys/cms/info/article/chooseInfoPage.jsp?info_id="+Info_id+"&site_id="+site_id,825,500);	
}

//打开相关信息选择窗口
function openFocusPage(Info_id)
{	//OpenModalWindow
	var tmp = "m";
	if(mFlag){
		tmp = "m";
	}else{
		tmp = "a";
	}
	top.OpenModalWindow("选择相关信息","/sys/cms/info/article/focusInfo.jsp?info_id="+Info_id+"&site_id="+site_id+"&t="+tmp,660,500);	
}

//打开添加窗口
function openAddInfoPage()
{
	window.location.href = "/sys/cms/info/article/infoAdd.jsp?cid="+cid+"&site_id="+site_id;
}

function openAddInfoPage1()
{
	window.location.href = "/sys/cms/info/article/articleAdd.jsp?cid="+cid+"&app="+app+"&site_id="+site_id;
}

//打开修改窗口
function openUpdateInfoDataPage()
{
	var selectIDS = table.getSelecteCheckboxValue("info_id");
	if(selectIDS == ""){
		top.msgAlert("请选择要修改的信息");return;
	}else if(selectIDS.indexOf(",") != -1){
		top.msgAlert("一次只能修改一条信息");return;
	}else{
		var mtBean = InfoBaseRPC.getInfoById(selectIDS,site_id);
		if(mtBean.is_host == 2){
			openUpdatePage2(selectIDS);
		}else
			openUpdatePage(selectIDS);
	}
}

function openUpdatePage(Infoid)
{
	window.location.href = "/sys/cms/info/article/infoAdd.jsp?cid="+cid+"&info_id="+Infoid+"&site_id="+site_id;
}

function openUpdatePage2(Infoid)
{
	top.OpenModalWindow("修改信息","/sys/cms/info/article/infoAdd2.jsp?cid="+cid+"&info_id="+Infoid+"&site_id="+site_id,620,220);	
}

//添加Info
function addInfoData()
{
	var bean = BeanUtil.getCopy(ArticleBean);	
	$("#info_article_table").autoBind(bean);
	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}
	
	bean.info_content=getV();
	var st = $(":radio[name='info_status'][checked]").val();
	bean.info_status = (st == null ? "2" : st);
	var infoNextId = 0;
	if(infoIdGoble == 0){
		infoNextId = InfoBaseRPC.getInfoId();
	}else{
		infoNextId = infoIdGoble;
	}
	var no1 = infoNextId;
	bean.id = infoNextId;
	bean.info_id = infoNextId;
	if(ArticleRPC.addArticle(bean))
	{
		var catIds = showSelectIds1();
		var ztIds = showSelectIds2();
		if(catIds != "" || ztIds != ""){
			var ids = ((catIds+ztIds).substring(1)).split(",");
			for(var i=0; i<ids.length; i++){
				infoNextId = InfoBaseRPC.getInfoId();
				bean.id = infoNextId;
				bean.info_id = infoNextId;
				bean.cat_id = ids[i];
				bean.from_id = no1;
				bean.is_host = 2;
				if(bean.info_status == "8" || bean.info_status == 8){
					bean.content_url = "http://xxxxxxxxxxxx.com/jsp/info/xxxx.html";
				}
				InfoBaseRPC.addInfo(bean);
			}
		}
		updateRelatedInfoAsSort(no1,bean.site_id);
		top.msgAlert("信息"+WCMLang.Add_success);			
		//top.CloseModalWindow();
		//top.getCurrentFrameObj().reloadInfoDataList();
		window.location.href = "/sys/cms/info/article/articleDataList.jsp?cat_id="+cid+"&app="+bean.app_id+"&site_id="+bean.site_id;
	}
	else
	{
		top.msgAlert("信息"+WCMLang.Add_fail);
	}
}
//修改
function updateInfoData()
{
	var bean = BeanUtil.getCopy(ArticleBean);	
	$("#info_article_table").autoBind(bean);

	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}
  
	bean.info_content=getV();
	var st = $(":radio[name='info_status'][checked]").val();
	bean.info_status = (st == null ? "2" : st);
	if(bean.up_dtime != null && bean.up_dtime != "" && bean.up_dtime != "0"){
		bean.is_auto_up = 1;
	}
	if(bean.down_dtime != null && bean.down_dtime != "" && bean.down_dtime != "0"){
		bean.is_auto_down = 1;
	}
	if(ArticleRPC.updateArticle(bean))
	{
		updateRelatedInfoAsSort(bean.info_id,bean.site_id);
		top.msgAlert("信息"+WCMLang.Add_success);			
		window.location.href = "/sys/cms/info/article/articleDataList.jsp?cat_id="+bean.cat_id+"&app="+bean.app_id+"&site_id="+bean.site_id;
	}
	else
	{
		top.msgAlert("信息"+WCMLang.Add_fail);
	}
}


function closePage(){
	top.CloseModalWindow();
}

function showSelectDiv2(input_name,div_name,height,tree_div_name)
{
	$("input[id='"+input_name+"']").addClass("select_input_default");
	$("input[id='"+input_name+"']").hover(function(){			
		$(this).removeClass("select_input_default");
		$(this).addClass("select_input_selected");			
	},function(){
		$(this).removeClass("select_input_selected");
		$(this).addClass("select_input_default");			
	});

	$("input[id='"+input_name+"']").click(function(event){
		$("div.select_div").hide();//先关闭其它的div

		var thePos = $(this).position();
		$("#"+div_name).show();
		$("#"+div_name).css("background","#FFFFFF");
		$("#"+div_name).css("left",thePos.left);
		$("#"+div_name).css("top",thePos.top+19);
		$("#"+div_name).css("width",$(this).width()+3);
		$("#"+div_name).css("height",height+"px");
		if(tree_div_name != null)
			$("#"+tree_div_name).css("height",(height-12)+"px");
		else
			$("#leftMenu").css("height",(height-12)+"px");
		
		event.stopPropagation();
	});

	$("html").click(function(event){
		if($($(event.target)[0]).attr("class").indexOf("tree-") == -1)
		{
			$("div.select_div").hide();			
		}else
		{
			
			setAllIds();
		}
	});
}

function setAllIds(){
	var catIds = showSelectIds1("s");
	var ztIds = showSelectIds2("s");
	$("#cat_tree").val(catIds);
	$("#zt_tree").val(ztIds);
}

function showRelatedInfos(infoid){
	$("#relateInfos").html("");
	var rm = new Map();
	rm.put("info_id", infoid);
	rm.put("sort_name", "sort_id");
	rm.put("sort_type", "asc");
	var relatedInfoList = InfoBaseRPC.getRelatedInfoList(rm);
	relatedInfoList = List.toJSList(relatedInfoList);
	for(var i=0; i<relatedInfoList.size(); i++){
		$("#relateInfos").append("<tr><td style=\"width:500px;\"><span id=\""+relatedInfoList.get(i).related_info_id+"\" style='color:#000;'>"+relatedInfoList.get(i).title+"</span></td><td style=\"width:150px;\"><table class=\"cionclass\"><tr><td><div class=\"ico_up\" style=\"width:22px;height:16px;\" title=\"上移\" onclick=\"moveUp(this)\"></div></td><td><div class=\"ico_down\"  style=\"width:22px;height:16px;\" title=\"下移\" onclick=\"moveDown(this)\"></div></td><td><div class=\"ico_delete\"  style=\"width:22px;height:16px;\" title=\"删除\" onclick=\"deleteTr(this)\"></div></td></tr></table></td><td></td></tr>");
	}
	resetNum();
}

function moveUp(o){

	$(o).parent().parent().parent().parent().parent().parent().insertBefore($(o).parent().parent().parent().parent().parent().parent().prev());
	resetNum();
}

function moveDown(o){
	$(o).parent().parent().parent().parent().parent().parent().insertAfter($(o).parent().parent().parent().parent().parent().parent().next());
	resetNum();
}

function deleteTr(o){
	$(o).parent().parent().parent().parent().parent().parent().remove();
	var reid = $(o).parent().parent().parent().parent().parent().parent().find("span").attr("id");
	var maps = new Map();
	maps.put("related_info_ids", reid);
	InfoBaseRPC.deleteRelatedInfo(maps);
	resetNum();
}


//重排
function resetNum(){
	$("#relateInfos tr").each(function(){
		$("#relateInfos tr div[title='上移']").addClass("ico_up");
		$("#relateInfos tr div[title='下移']").addClass("ico_down");
	});
	$("#relateInfos tr .ico_up").first().removeClass("ico_up");
	$("#relateInfos tr .ico_down").last().removeClass("ico_down");
}

function publishInfo(){
	var selectIDS = table.getSelecteCheckboxValue("info_id");
	if(InfoBaseRPC.updateInfoStatus(selectIDS,"8")){
		top.msgAlert("信息发布成功");
		top.getCurrentFrameObj().reloadInfoDataList();
	}else{
		top.msgAlert("信息发布失败");
	}
}

function cancleInfo(){
	var selectIDS = table.getSelecteCheckboxValue("info_id");
	if(InfoBaseRPC.updateInfoStatus(selectIDS,"3")){
		top.msgAlert("信息撤销成功");
		top.getCurrentFrameObj().reloadInfoDataList();
	}else{
		top.msgAlert("信息撤销失败");
	}
}

function backInfo(){
	var selectIDS = table.getSelecteCheckboxValue("info_id");
	if(InfoBaseRPC.backInfo(selectIDS)){
		top.msgAlert("信息归档成功");
		top.getCurrentFrameObj().reloadInfoDataList();
	}else{
		top.msgAlert("信息归档失败");
	}
}

function goBackInfo(){
	var selectIDS = table.getSelecteCheckboxValue("info_id");
	//alert("goBackInfo ids = "+selectIDS);
	InfoBaseRPC.goBackInfo(selectIDS);
}

function deleteInfoData(){
	var selectIDS = table.getSelecteCheckboxValue("info_id");
	if(InfoBaseRPC.deleteInfo(selectIDS)){
		top.msgAlert("信息删除成功");
		top.getCurrentFrameObj().reloadInfoDataList();
	}else{
		top.msgAlert("信息删除失败");
	}
}

//搜索
function authorDataSearchHandl(obj)
{
	alert("do search Authors");
//	var con_value = $(obj).parent().find("#searchkey").val();
//	if(con_value.trim() == "" ||  con_value == null)
//	{
//		top.msgAlert(WCMLang.Search_empty);
//		return;
//	}
//	table.con_name = $(obj).parent().find("#searchFields").val(); 
//	table.con_value = con_value;
//	reloadMetaDataList();
}




