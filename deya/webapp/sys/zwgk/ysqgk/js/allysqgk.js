var YsqgkRPC = jsonrpc.YsqgkRPC;
var GKNodeRPC = jsonrpc.GKNodeRPC;

var YsqgkConfigBean = new Bean("com.deya.wcm.bean.zwgk.ysqgk.YsqgkConfigBean",true);
var YsqgkBean = new Bean("com.deya.wcm.bean.zwgk.ysqgk.YsqgkBean",true);
var YsqgkListBean = new Bean("com.deya.wcm.bean.zwgk.ysqgk.YsqgkListBean",true);
var user_id=top.LoginUserBean.user_id;

var orderFeilds = null;//排序 
var highSearchString = null;

var final_status = 0;
var do_state = 0;

var ysq_type  = -1;
var put_dtime = 0;

var tp = new TurnPage();
var current_page_num = 1;
var is_save_first_page = false;//操作成功后是否保存在第一页

var val = new Validator();

var table = new Table();	
	table.table_name = "YsqgkConfig_table";
var info_table = new Table();	
	info_table.table_name = "ysq_info";
var list_table = new Table();	
	list_table.table_name = "listInfo_table";
var ysqgk_infos_table = new Table();
	ysqgk_infos_table.table_name= "ysqgk_infos_table";
	    
function getV(input_id){
	return KE.html(input_id);
}
function setV(input_id,v){
	KE.html(input_id,v);
}

//添加依申请公开业务
function addYsqgkConfig()
{
	var bean = BeanUtil.getCopy(YsqgkConfigBean);
	$("#YsqgkConfig_table").autoBind(bean);
	if(!standard_checkInputInfo("YsqgkConfig_table"))
	{
		return;
	}
	if(YsqgkRPC.insertYsqgkConfig(bean))
	{
		top.msgAlert("依申请公开业务"+WCMLang.Add_success+"!");
	}
	else
	{
		top.msgWargin("依申请公开业务"+WCMLang.Add_fail+"!");
	}
}

//依申请公开信息处理开始
function addYsqgkInfo(node_id)
{	
	var ysqgk_bean = BeanUtil.getCopy(YsqgkBean);
	$("#ysq_info").autoBind(ysqgk_bean);
	if(!standard_checkInputInfo("ysq_info"))
	{
		return;
	}
	ysqgk_bean.node_id = node_id; 
	ysqgk_bean.node_name = GKNodeRPC.getGKNodeBeanByNodeID(node_id).node_fullname;
	if($("#gk_index").val() == ""){
		top.msgAlert("索引号不能为空!");
		return;
	}else{
		ysqgk_bean.gk_index = $("#gk_index").val(); 
	}
	if(getV("content") == ""){
		top.msgAlert("内容不能为空!");
		return;
	}else{
		ysqgk_bean.reply_content= getV("content");
	}
	if(YsqgkRPC.insertYsqgkInfo(ysqgk_bean))
	{
		top.msgAlert("依申请公开信息"+WCMLang.Add_success+"!");
		window.location.href = "/sys/zwgk/ysqgk/operate/ysqgk_list.jsp?app_id=zwgk&site_id="+node_id;
	}
	else
	{
		top.msgWargin("依申请公开信息"+WCMLang.Add_fail+"!");
	}
}

function initTable(node_id)
{
	var colsMap = new Map();
	var colsList = new List();	
    
	colsList.add(setTitleClos("ysq_code","申请单编号","","","",""));
	colsList.add(setTitleClos("ysq_type","类型","150px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("name","申请人","100px","","",""));
	colsList.add(setTitleClos("node_name","申请部门","100px","","",""));
	colsList.add(setTitleClos("put_dtime","提交时间","150px","","",""));
	colsList.add(setTitleClos("publish_state","发布状态","80px","","",""));
	colsList.add(setTitleClos("is_third","征询第三方","70px","","",""));
	colsList.add(setTitleClos("timeout_flag","是否超时","60px","","",""));
	colsList.add(setTitleClos("is_extend","是否暂缓","60px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("listInfo_table");//里面参数为外层div的id
}

function showList(){
	var sortCol = table.sortCol;
	var sortType = table.sortType;
	
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "put_dtime";
	    sortType ="desc";
	}
	var m = new Map();
	
	m.put("app_id", "zwgk");
	
	if(node_id != null){
		m.put("node_id", node_id);
    }
	m.put("highSearchString", highSearchString); 
	
	if(do_state != null || do_state != "null"){
		m.put("do_state", do_state);
    }
	if(final_status != null || final_status != "null"){
		m.put("final_status",final_status);
	}
	//搜索信息开始
    if(ysq_type != -1){
    	m.put("ysq_type", ysq_type);
    }
	if(put_dtime != 0){
		m.put("put_dtime", put_dtime);
    }
	//搜索信息结	
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);

	if(table.con_value.trim() != "" && table.con_value != null){
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);		
	}
	beanList = YsqgkRPC.getYsqgkLists(m);//第一个参数为站点ID，暂时默认为空
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("listInfo_table");
	
	table.getCol("ysq_code").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{	
			$(this).css("padding-left","20px");
			if(do_state == 0 || do_state == 1 || do_state == -1){
				$(this).html('<a href="javascript:openInfoPage('+beanList.get(i-1).ysq_id+',node_id)">'+beanList.get(i-1).ysq_code+'</a>');
			}else{
				$(this).html('<a href="javascript:openReplyInfoPage('+beanList.get(i-1).ysq_id+',node_id)">'+beanList.get(i-1).ysq_code+'</a>');
			}
		}
	});
	table.getCol("ysq_type").each(function(i){		
		if(i>0)
		{			
			var ysq_type=beanList.get(i-1).ysq_type;
			if(ysq_type == 0){
				$(this).text("公民");	
			}else{
				$(this).text("法人或者其他组织");	
			}			
		}
	});
	table.getCol("put_dtime").each(function(i){		
		if(i>0)
		{			
			var put_dtime = beanList.get(i-1).put_dtime;
			if(put_dtime.length > 10){
				put_dtime = put_dtime.substring(0,10);
				$(this).text(put_dtime);	
			}else{
				$(this).text(put_dtime);	
			}			
		}
	});
	table.getCol("publish_state").each(function(i){		
		if(i>0)
		{			
			var ysq_type=beanList.get(i-1).publish_state;
			if(ysq_type ==0){
				$(this).text("未发布");	
			}else{
				$(this).text("已发布");	
			}			
		}
	});
	table.getCol("is_third").each(function(i){		
		if(i>0)
		{			
			var ysq_type=beanList.get(i-1).is_third;
			if(ysq_type ==0){
				$(this).text("不征询");	
			}else{
				$(this).text("征询");	
			}			
		}
	});
	table.getCol("timeout_flag").each(function(i){		
		if(i>0)
		{			
			var ysq_type=beanList.get(i-1).timeout_flag;
			if(ysq_type ==0){
				$(this).text("正常");	
			}else{
				$(this).text("超时");	
			}			
		}
	});
	table.getCol("is_extend").each(function(i){		
		if(i>0)
		{			
			var ysq_type=beanList.get(i-1).timeout_flag;
			if(ysq_type ==0){
				$(this).text("否");	
			}else{
				$(this).text("是");	
			}			
		}
	});
	current_page_num = tp.curr_page;
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	

	var m = new Map();
	m.put("app_id", "zwgk");
	if(node_id != null){
		m.put("node_id", node_id);
    }
	m.put("highSearchString", highSearchString); 
	
	if(do_state != null){
		m.put("do_state", do_state);
    }
	if(final_status != null){
		m.put("final_status",final_status);
	}
	
	//搜索信息开始
    if(ysq_type != -1){
    	m.put("ysq_type", ysq_type);
    }
	if(put_dtime != 0){
		m.put("put_dtime", put_dtime);
    }
	//搜索信息结
			
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	
	if(table.con_value.trim() != "" && table.con_value != null){
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);		
	}
	tp.total = YsqgkRPC.getYsqgkListsCount(m);	
	tp.show("turn","");	
	tp.onPageChange = showList;		
}

//搜索
function searchInfo()
{
	var selectval = $('#searchType').val();
	var keywords =  $("#searchkey").val();
	if(selectval ==0){
		top.msgAlert("	请选择类别	");
		return;	
	}
	if(keywords.trim() == "" ||  keywords == null){
		top.msgAlert(WCMLang.Search_empty);
		return;
	}
	if(selectval == 1)
	{
		table.con_name = "ysq_code";
		table.con_value = keywords;	
	}
	if(selectval == 2){
		table.con_name = "content";
		table.con_value = keywords;
	}
	reloadInfoDataList(); 
}

function changeStatus(num){
	current_page_num = 1;
	snum = num;

	final_status = 0;
    do_state = 0;
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
	case 0://正常未受理信息
		do_state = "0";
		final_status = "0";
		break;
	case 1://正常已受理信息
		do_state = "1";
		final_status = "0";
		break;
	case 2://正常已处理信息
		do_state = "2";
		final_status = "0";
		break;
	case 3://无效信息
		do_state = "-1";
		final_status = "0";
		break;
	case 4://回收站信息
		do_state = null;
		final_status = "-1";
		break;
	}	
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

//置为无效件
function setWuxiao()
{		
	top.msgConfirm("确定要将此信件置为无效？","insertProcess(2)");
}
//过程处理
function insertProcess(pro_type,node_id,ysq_id)
{
	//首先判断是否有上传的附件，并且附件对象为null
//	if(fileCount > 0 && attBean == null)
//	{
//		$("#uploadify").uploadifySettings('scriptData',{'app_id':'appeal','sid':jsonrpc.MateInfoRPC.getUploadSecretKey()});
//		//执行上传程序
//		_pro_type = pro_type;
//		jQuery('#uploadify').uploadifyUpload();
//		return;
//	}
    //alert("pro_type===="+pro_type);
	var dealMap = new Map();
	var deal_content = getV("deal_content");
	
	    dealMap.put("ysq_id",ysq_id+"");
	    dealMap.put("is_other",$("#is_other").val());
	    dealMap.put("is_third",$("#is_third").val());
	    dealMap.put("weight",$("#weight").val());
	    dealMap.put("is_extend","0");
	    dealMap.put("supervise_flag","0");
	if(pro_type == 0)
	{
		if(deal_content == "")
		{
				top.msgWargin("受理内容不能为空!");
				return;
		}else{
				dealMap.put("accept_content",deal_content);	
				dealMap.put("accept_user",user_id);
				
				dealMap.put("final_status","0");
				dealMap.put("do_state","1");
				dealMap.put("dealtype","0");

				dealMap.put("reply_content","");
				dealMap.put("reply_user","0");
				dealMap.put("reply_type","1");
				dealMap.put("publish_state","0");
				dealMap.put("is_mail","0");	   
		}	 
	}else if(pro_type == 1)
	{
		if(deal_content == "")
		{
				top.msgWargin("回复内容不能为空!");
				return;
		}else{
				dealMap.put("reply_content",deal_content);
				dealMap.put("reply_user",user_id+"");
				dealMap.put("reply_type",$("#reply_type :selected").val());
				dealMap.put("publish_state",$("input[name='publish_state']:checked").val());
				dealMap.put("is_third",$("input[name='is_third']:checked").val());
				dealMap.put("is_extend",$("input[name='is_extend']:checked").val());			
				dealMap.put("is_mail",$("input[name='is_mail']:checked").val());
              
                dealMap.put("final_status","0");
				dealMap.put("do_state","2");
				dealMap.put("dealtype","1");
				dealMap.put("final_status","0");
		}
	}else if(pro_type == 2){
			   	dealMap.put("do_state","-1");//置为无效状态	
		       	dealMap.put("dealtype","2");

		       	dealMap.put("accept_content","无效信息");	
				dealMap.put("accept_user",user_id+"");
		       	dealMap.put("final_status","0");
		       	dealMap.put("is_mail","0");	
		       	dealMap.put("reply_content","");
				dealMap.put("reply_user","");
				dealMap.put("reply_type","");
				dealMap.put("publish_state","0");		       		       	
	}
	if(YsqgkRPC.updateStatus(dealMap))
	{
		top.msgAlert("操作成功!");
		window.location.href="/sys/zwgk/ysqgk/operate/ysqgk_list.jsp?app_id=zwgk&site_id="+node_id;
		reloadInfoDataList();
	}else
	{
		top.msgAlert("操作失败!");
	}
}

//删除，置为无效信息，清除回收站，彻底删除
function deleteInfoData(type)
{
	var m = new Map();
		m.put("ysq_id",table.getSelecteCheckboxValue("ysq_id")+"");
	if(type == -1)
	{
		if(YsqgkRPC.setDeleteState(m))//置为无效信件
		{
			top.msgAlert("信息"+WCMLang.Delete_success);
			reloadInfoDataList();
		}else
		{
			top.msgWargin("信息"+WCMLang.Delete_fail);		
		}	
	}else if(type == -2)//彻底删除信息
	{
		if(YsqgkRPC.deleteYsqgkInfo(m))
		{
			top.msgAlert("信息"+WCMLang.Delete_success);
			reloadInfoDataList();
		}else
		{
			top.msgWargin("信息"+WCMLang.Delete_fail);		
		}	
	}else if(type == -3)//清除回收站信息
	{
		if(YsqgkRPC.clearDeleteYsqgkInfos())
		{
			top.msgAlert("信息"+WCMLang.Delete_success);
			reloadInfoDataList();
		}else
		{
			top.msgWargin("信息"+WCMLang.Delete_fail);		
		}	
	}
}

//还原信息
function reBackInfos(){
	
	var m = new Map(); 
	var id = table.getSelecteCheckboxValue("ysq_id");
	if(id.length > 0){
		m.put("ysq_id",id+"");
		if(YsqgkRPC.reBackInfos(m))
		{
			top.msgAlert("信息还原成功！");
			reloadInfoDataList();
		}else
		{
			top.msgWargin("信息还原失败！");		
		}	
	}else{
		top.msgAlert("请选择要操作的记录！");
		return;
	}
}

function portOut(){
	//top.msgAlert("	");
}

function openInfoPage(ysq_id,node_id)
{
	top.addTab(true,"/sys/zwgk/ysqgk/operate/ysqgk_info.jsp?ysq_id="+ysq_id+"&node_id="+node_id,"依申请公开");
}

function openReplyInfoPage(ysq_id,node_id)
{
	top.addTab(true,"/sys/zwgk/ysqgk/operate/ysqgk_Replayinfo.jsp?ysq_id="+ysq_id+"&node_id="+node_id,"依申请公开");
}

function openAddPage(node_id)
{
	top.addTab(true,"/sys/zwgk/ysqgk/operate/ysqgk_infoAdd.jsp?node_id="+node_id,"依申请公开");
}
//高级搜索页面
function openHighSearchPage(node_id)
{
	top.OpenModalWindow("高级搜索","/sys/zwgk/ysqgk/operate/hightSearch.jsp?node_id="+node_id,510,500);
}

function openUpdatePage(node_id)
{
	var ysq_id = table.getSelecteCheckboxValue("ysq_id");
	top.addTab(true,"/sys/zwgk/ysqgk/operate/ysqgk_infoUpdate.jsp?ysq_id="+ysq_id+"&node_id="+node_id,"依申请公开");	
}

//修改信息
function updateYsqInfo(ysq_id,node_id)
{
	var updateBean = BeanUtil.getCopy(YsqgkBean);
		$("#ysqgk_infos_table").autoBind(updateBean);
//	if(!standard_checkInputInfo("ysqgk_infos_table"))
//	{
//		return;
//	}
	
	updateBean.ysq_id = ysq_id;
	if($("#gk_index").val() == ""){
		top.msgAlert("索引号不能为空!");
		return;
	}else{
		
		updateBean.gk_index = $("#gk_index").val(); 
	}
	if(getV("content") == ""){
		top.msgAlert("内容不能为空!");
		return;
	}else{
		updateBean.content= getV("content");
	}
	if(YsqgkRPC.updateYsqgkInfo(updateBean))
	{
		top.msgAlert("信息"+WCMLang.Set_success);
		//top.getCurrentFrameObj().reloadInfoDataList();
		reloadInfoDataList();
		top.CloseModalWindow();
	}
	else
	{
		top.msgWargin("信息"+WCMLang.Set_fail);
		return;
	}
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
		case "1": table.sortCol="put_dtime";
				  table.sortType = "desc";
				  break;
		case "2": table.sortCol="put_dtime";
				  table.sortType = "asc";
				  break;
		case "3": table.sortCol="accept_dtime";
				  table.sortType= "desc";
				  break;
		case "4": table.sortCol = "accept_dtime";
				  table.sortType = "asc";
				  break;
		case "5": table.sortCol = "reply_dtime";
				  table.sortType = "desc";
			      break;
		case "6": table.sortCol = "reply_dtime";
				  table.sortType = "asc";
				  break;
	}	
}
//按照时间段显示
function changePutTime(val)
{
	put_dtime = val;
	reloadInfoDataList();
}
//按照类型显示
function changeYsqType(val)
{
	ysq_type = val;
	reloadInfoDataList();	
}
//高级搜索处理
function highSearchHandl(search_cons,lab_num,orderByFields)
{
	lab_num = parseInt(lab_num);
	highSearchString = search_cons;//给搜索字符串付值
	changeTimeSortHandl(orderFeilds);//得到排序方式

	current_page_num = 1;
	snum = lab_num;
	
	final_status = null;
	do_state = null;
    
    ysq_type = -1;
    put_dtime = 0;
	
	$("#orderByFields option").eq(0).attr("selected",true);	
	
	$(".fromTabs > li").removeClass("list_tab_cur");
	$(".fromTabs > li").eq(lab_num).addClass("list_tab_cur");
	$(".infoListTable").addClass("hidden");			
	$("#listTable_"+lab_num).removeClass("hidden");

	//clickLabelHandl(lab_num);
	reloadInfoDataList();	
}


//取得常用语信息
var lang_map = new Map();
function getYsqgkPhrasaListByType(pro_type)
{
	if(pro_type < 100)
	{
		var lang_list = YsqgkRPC.getYsqgkPhrasaListByType(pro_type);
		lang_list = List.toJSList(lang_list);
		$("#quick_content").empty();
		$("#quick_content").addOptionsSingl("","可选快速回复");
		if(lang_list != null && lang_list.size() > 0)
		{
			for(var i=0;i<lang_list.size();i++)
			{
				$("#quick_content").addOptionsSingl(lang_list.get(i).gph_id,lang_list.get(i).gph_title);
				lang_map.put(lang_list.get(i).gph_id,lang_list.get(i).gph_content);
			}
		}
	}
}
//根据常用语的选择，展现内容
function setSelectedCommonLang(c_type)
{
	var content = "";
	if(c_type != "")
	{
		var content = lang_map.get(c_type);		
		    //content = content.replace(/\{sq_code\}/ig,defaultBean.sq_code).replace(/\{sq_realname\}/ig,defaultBean.sq_realname).replace(/\{sq_email\}/ig,defaultBean.sq_email).replace(/\{sq_title2\}/ig,defaultBean.sq_title2).replace(/\{submit_name\}/ig,defaultBean.submit_name).replace(/\{sq_dtime\}/ig,defaultBean.sq_dtime).replace(/\{model_cname\}/ig,model_bean.model_cname).replace(/\{dept_name\}/ig,getDoDept()).replace(/\{dtime\}/ig,getCurrentDateTime());
	}
	KE.util.focus("deal_content");
    KE.util.selection("deal_content");
	KE.util.insertHtml("deal_content",content);
}

//初始化信息
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

function iniSQbox()
{
	$(".sq_title_box").bind('click',function(){	
		if($(this).find(".sq_title_right").text()=="点击闭合")
		{
			$(this).find(".sq_title").removeClass("sq_title_minus").addClass("sq_title_plus");
			$(this).find(".sq_title_right").text("点击展开");
			$(this).parent().find(".sq_box_content").hide(300);
		}
		else
		{
			$(this).find(".sq_title").removeClass("sq_title_plus").addClass("sq_title_minus");
			$(this).find(".sq_title_right").text("点击闭合");
			$(this).parent().find(".sq_box_content").show(300);
		}
	})
}