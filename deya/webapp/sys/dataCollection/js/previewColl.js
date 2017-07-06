var ArticleBean = new Bean("com.deya.wcm.bean.cms.info.ArticleBean",true);
var GKFtygsBean = new Bean("com.deya.wcm.bean.cms.info.GKFtygsBean",true);
var CollectionDataRPC = jsonrpc.CollectionDataRPC;
var CategoryRPC = jsonrpc.CategoryRPC;
var IndexRPC = jsonrpc.IndexRPC;
var GKInfoRPC = jsonrpc.GKInfoRPC;
var artis_user="1";   //文章是否被采用  1:采用   0:未采用
var rule_name="";
var sTime="";
var eTime="";
var SearchTitle="";

var table = new Table();
	table.table_name = "tableCollInfo";
var tp = new TurnPage();
var beanList = null;	
	
function reloadInfoDataList()
{
	showList();
	showTurnPage();
}
	
function initTable()
{
	var colsMap = new Map();
	var colsList = new List();	
	
//	colsList.add(setTitleClos("art_id","ID","","","",""));//英文名，显示名，宽，高，样式名，点击事件
	colsList.add(setTitleClos("art_title","标题","","","",""));
	colsList.add(setTitleClos("art_source","来源","100px","","",""));
	colsList.add(setTitleClos("pub_time","发布时间","","","",""));
	colsList.add(setTitleClos("title","所属规则","","","",""));
	colsList.add(setTitleClos("cat_name","所属栏目","","","",""));
	colsList.add(setTitleClos("rule_handle","操作","200px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("tableCollInfo");//里面参数为外层div的id
}

function showList()
{
	var sortCol = table.sortCol;
	var sortType = table.sortType;
	var m = new Map();
	
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "cart.coll_time";
	    sortType = "desc";
	}
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	
	if(artis_user != "none" && artis_user != ""){
		m.remove("artis_user");
		m.put("artis_user", artis_user);
	}
	if(rule_name!=null && rule_name!="" && rule_name!="-1"){
		m.put("rule_name",rule_name);
	}
	
	if(sTime!=null && sTime!=""){
		m.put("sTime",sTime+" 00:00:01");
	}
	if(eTime!=null && eTime!=""){
		m.put("eTime",eTime+" 23:59:59");
	}
	if(SearchTitle!=null && SearchTitle!=""){
		m.put("SearchTitle",SearchTitle);
	}
//	m.put("rule_id",rule_id);
	
	beanList = CollectionDataRPC.getCollDataList(m);
	beanList = List.toJSList(beanList);
	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("tableCollInfo");
	
	table.getCol("art_title").each(function(i){	
		if(i>0)
		{
			$(this).html(beanList.get(i-1).art_title);
		}
	});
	
	table.getCol("art_source").each(function(i){	
		if(i>0)
		{
			$(this).html(beanList.get(i-1).art_source);
		}
	});
	
	table.getCol("pub_time").each(function(i){	
		if(i>0)
		{
			$(this).html(beanList.get(i-1).art_pubTime);
		}
	});
	
//	table.getCol("cat_name").each(function(i){	
//		if(i>0)
//		{
//			var cat_name = CategoryRPC.getCategoryBean(beanList.get(i-1).cat_id).cat_cname;
//			$(this).html(cat_name);
//		}
//	});
	
	table.getCol("rule_handle").each(function(i){	
		if(i>0)
		{
			if(beanList.get(i-1).artis_user == 0){
				$(this).html("<span style='cursor:pointer;' onclick='updateCollInfoById("+beanList.get(i-1).id+")'>修改信息</span>");
			}else{
				$(this).html();
			}
		}
	});
	Init_InfoTable(table.table_name);
}

function updateCollInfoById(infoID)
{
	top.addTab(true,"/sys/dataCollection/updateCollInfo.jsp?tab_index="+top.curTabIndex+"&info_id="+infoID,"修改采集信息");
}

function showTurnPage()
{	
	var m = new Map();
	if(artis_user != "none" && artis_user != ""){
		m.remove("artis_user");
		m.put("artis_user", artis_user);
	}
	if(rule_name!=null && rule_name!=""){
		m.put("rule_name",rule_name);
	}
	if(sTime!=null && sTime!=""){
		m.put("sTime",sTime+" 00:00:01");
	}
	if(eTime!=null && eTime!=""){
		m.put("eTime",eTime+" 23:59:59");
	}
	if(SearchTitle!=null && SearchTitle!=""){
		m.put("SearchTitle",SearchTitle);
	}
//	m.put("rule_id",rule_id);
	CollectionDataRPC.getCollInfoListCount(showTurnPageResult,m);	
}

function showTurnPageResult(result,e)
{
    if(e != null){return;}
	tp.total = result;
	tp.show("turn","");	
	tp.onPageChange = showList;		
}

function initTabAndStatus()
{	
	$(".fromTabs > li").each(function(){	
		$(this).hover( //鼠标移入 移除时间
		  function () {
			$(this).addClass("list_tab_over");
		  },
		  function () {
			$(this).removeClass("list_tab_over");
		  }
		);
		
		$(this).click( //页签的单击事件
		  function () {
			$(".fromTabs > li").removeClass("list_tab_cur");
			$(this).addClass("list_tab_cur");
			$(".infoListTable").addClass("hidden");			
			
			if($(this).attr("num") != "" && $(this).attr("num") != null)
			{
				$("#listTable_"+$(this).attr("num")).removeClass("hidden");
				changeStatus(parseInt($(this).attr("num")));
			}
			else
			{
				$("#listTable_"+$(this).index()).removeClass("hidden");
				changeStatus($(this).index());
			}
		  }
		);
	});
}

function changeStatus(num)
{
	current_page_num = 1;
	snum = num;
	artis_user = "none";
	table.sortCol="";
	table.sortType = "";
	clickLabelHandl(num);
	reloadInfoDataList();
}

function clickLabelHandl(num)
{
	switch(num)
	{
		case 0:
			artis_user="1";
			$("#handleBtn").hide();
			break;
		case 1:
			artis_user="0";
			$("#handleBtn").show();
			break;
	}
}

function deleteCollInfoByid()
{
	var ids = table.getSelecteCheckboxValue("id");
	if(CollectionDataRPC.deleteCollInfoById(ids))
	{
		top.msgAlert("信息"+WCMLang.Delete_success);
		reloadInfoDataList();
	}else{
		top.msgWargin("信息"+WCMLang.Delete_fail);
	}
}

//采用并发布
var info_status="";
function useAndpublishInfo()
{
	info_status = "8";
	pubCollDataInfotoCat();
}

//采用待发布
function useNopublishInfo()
{
	info_status = "4";
	pubCollDataInfotoCat();
}

function pubCollDataInfotoCat()
{
	var article_bean = BeanUtil.getCopy(ArticleBean);
	var tygs_bean = BeanUtil.getCopy(GKFtygsBean);
	var ids = table.getSelecteCheckboxValue("id");
	
	var artBeanList = CollectionDataRPC.getcollBeanListByIds(ids);
		artBeanList = List.toJSList(artBeanList);
	
	var infoNextId = 0;
	for(var i=0;i<artBeanList.size();i++)
	{
		if(infoIdGoble == 0){
			infoNextId = InfoBaseRPC.getInfoId();
		}else{
			infoNextId = infoIdGoble;
		}
		if(artBeanList.get(i).model_id == "11")
		{
			article_bean.id = infoNextId;
			article_bean.info_id = infoNextId;
			article_bean.info_status = info_status;
			article_bean.info_content = artBeanList.get(i).art_content;
			article_bean.page_count = "1";
			article_bean.cat_id = artBeanList.get(i).cat_id;
            var site_id = CategoryRPC.getCategoryBean(artBeanList.get(i).cat_id).site_id;
			article_bean.title = artBeanList.get(i).art_title.replace(/\"/g,"＂");;
			article_bean.source = artBeanList.get(i).art_source;
			article_bean.info_keywords = artBeanList.get(i).art_keyWords;
            article_bean.doc_no = artBeanList.get(i).art_docNo;
			article_bean.gk_input_dept = artBeanList.get(i).gk_input_dept;
			article_bean.gk_signer_dtime = artBeanList.get(i).gk_signer_dtime;
			article_bean.app_id = "cms";
			article_bean.site_id = site_id;
			article_bean.model_id= "11";
			article_bean.released_dtime = artBeanList.get(i).art_pubTime;
			article_bean.is_am_tupage = 0;
			article_bean.input_user = top.LoginUserBean.user_id;
			article_bean.weight = 60;

			//索引码生成
			var index_map = IndexRPC.getIndex(site_id,tygs_bean.cat_id,"","");
			index_map = Map.toJSMap(index_map);
			if(index_map != null)
			{
				article_bean.gk_index = index_map.get("gk_index");
				article_bean.gk_year = index_map.get("gk_year");
				article_bean.gk_num = index_map.get("gk_num");
			}

			if(ModelUtilRPC.insert(article_bean,"article"))
			{	
				CollectionDataRPC.changeCollInfoStatus(artBeanList.get(i).id);
				reloadInfoDataList();
				top.msgAlert("操作成功!");	
			}else
			{
				top.msgWargin(article_bean.title+"操作失败!");
			}
		}
		if(artBeanList.get(i).model_id == "14")
		{
			tygs_bean.id = infoNextId;
			tygs_bean.info_id = infoNextId;
			tygs_bean.info_status = info_status;
			tygs_bean.gk_content = artBeanList.get(i).art_content;
			tygs_bean.page_count = "1";
			tygs_bean.cat_id = artBeanList.get(i).cat_id;
            var site_id = CategoryRPC.getCategoryBean(artBeanList.get(i).cat_id).site_id;
			tygs_bean.title = artBeanList.get(i).art_title.replace(/\"/g,"＂");;
			tygs_bean.source = artBeanList.get(i).art_source;
			tygs_bean.info_keywords = artBeanList.get(i).art_keyWords;
            tygs_bean.doc_no = artBeanList.get(i).art_docNo;
			tygs_bean.gk_input_dept = artBeanList.get(i).gk_input_dept;
			tygs_bean.gk_signer_dtime = artBeanList.get(i).gk_signer_dtime;
			tygs_bean.app_id = "zwgk";
			tygs_bean.site_id = site_id;
			tygs_bean.model_id= "14";
			tygs_bean.released_dtime = artBeanList.get(i).art_pubTime;
			tygs_bean.is_am_tupage = 0;
			tygs_bean.input_user = top.LoginUserBean.user_id;
			tygs_bean.weight = 60;
			
			//索引码生成
			var index_map = IndexRPC.getIndex(site_id,tygs_bean.cat_id,"","");
			index_map = Map.toJSMap(index_map);
			if(index_map != null)
			{
				tygs_bean.gk_index = index_map.get("gk_index");
				tygs_bean.gk_year = index_map.get("gk_year");
				tygs_bean.gk_num = index_map.get("gk_num");
			}
			
			if(ModelUtilRPC.insert(tygs_bean,"gkftygs"))
			{	
				CollectionDataRPC.changeCollInfoStatus(artBeanList.get(i).id);
				reloadInfoDataList();
				top.msgAlert("操作成功!");	
			}else
			{
				top.msgWargin(tygs_bean.title+"操作失败!");
			}
		}
	}
}

function showCollRules(){
	var collRuleList = CollectionDataRPC.getAllCollRuleBeanList();
	collRuleList = List.toJSList(collRuleList);
	$("#selectCollRule").append("<option value=\"-1\" selected=\"selected\">全部</option>")
	if(collRuleList!=null && collRuleList.size()>0){
		for(var i=0;i<collRuleList.size();i++){
			$("#selectCollRule").append("<option value=\""+collRuleList.get(i).title+"\">"+collRuleList.get(i).title+"</option>");
		}
	}
}

function changeCollRule(ruleName){
	rule_name = ruleName;
	reloadInfoDataList();
}

/**
 * 搜索信息
 * @return
 */
function searchCollInfo(){
	sTime = $("#beignTime").val();
	eTime = $("#endTime").val();
	SearchTitle = $("#searchCollTitle").val();
	
	if(sTime == "" && eTime == "" && SearchTitle == ""){
		top.msgAlert("请输入查询条件!");
	}else{
		if(sTime != "" && sTime != null && eTime != "" && eTime != null)
		{
			if(judgeDate(eTime,sTime))
			{
				top.msgWargin("结束时间不能小于开始时间 !");
				return;
			}
		}
		reloadInfoDataList();
	}
}


