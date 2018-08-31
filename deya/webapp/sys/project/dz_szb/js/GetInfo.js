/****** 取信息　开始 ******/
var GKNodeRPC = jsonrpc.GKNodeRPC;
var InfoBaseRPC = jsonrpc.InfoBaseRPC;
var count = 0;
var total = 0;
var pageSize = 20;
var pageNum = 1;
var serarch_con = "";//查询条件
var info_list;
var site_type = "";//选择的站点类型
var info_map =  new Map();//存储info对象
var node_name_map =  new Map();//存储公开对象
var m = new Map();
m.put("step_id","100");
m.put("info_status", "8");
m.put("final_status", "0");
//m.put("is_host", "0");//获取的信息只能是主信息，不能是引用和链接型的

function setScrollHandl()
{
	$('#scroll_div').scroll(function(){
		var o = $('#scroll_div');
		if (o.scrollTop()+o.height() > o.get(0).scrollHeight - 90)
		{
			if (window.loading ) return;
			if (window.show_more_lock) return;
			if (count >= total) return;

			pageNum++;
			getInfoList();
		}
	});
}

function getInfoCount()
{
    m.put("user_id", top.LoginUserBean.user_id+"");
	// if(cat_id != ""){
	// 	m.put("cat_ids", cat_id);
	// }
	if(app_id != ""){
		m.put("app_id", app_id);
	}
	if(site_id != ""){
		m.put("site_id", s_site_id);
	}

	if(serarch_con != ""){
		m.put("searchString", serarch_con);
	}
	if(isNaN(site_type) == false && site_type != "")
	{
		m.remove("site_id");
		m.remove("cat_ids");
		m.put("shared_cat_ids",cat_id);
		total = jsonrpc.GKInfoRPC.getGKInfoCount(m);
	}
	else
	{
		if(site_type.indexOf("shared_") > -1)
		{
			//取共享目录的数据
			m.remove("site_id");
			m.remove("cat_ids");
			m.put("shared_cat_ids",cat_id);
			total = InfoBaseRPC.getInfoCount(m);
		}else
			total = InfoBaseRPC.getInfoCount(m);
	}
	$("#checked_count").html( count>total ? total:count +"/"+total);
}

function getInfoList()
{
	m.remove("shared_cat_ids");
    m.put("user_id", top.LoginUserBean.user_id+"");
	m.put("page_size", pageSize);
	m.put("start_num", pageSize*(pageNum-1)+"");
	m.put("sort_name", "ci.released_dtime desc,ci.id");
	m.put("sort_type", "desc");


	if(app_id != ""){
		m.put("app_id", app_id);
	}
	if(site_id != ""){
		m.put("site_id", s_site_id);
	}
	m.put("start_num", pageSize*(pageNum-1)+"");

    info_list = InfoBaseRPC.getInfoList(m);
	info_list = List.toJSList(info_list);
	if(info_list != null && info_list.size() > 0)
	{
		count += info_list.size();
		var str = "";

		for(var i=0;i<info_list.size();i++)
		{
			info_map.put(info_list.get(i).info_id,info_list.get(i));
			var node_name = "";
			var n_name = "";
			if(info_list.get(i).site_id.indexOf("GK") > -1)
			{
				n_name = "";
				if(node_name_map.containsKey(info_list.get(i).site_id))
				{
					n_name = node_name_map.get(info_list.get(i).site_id);
				}else
				{
					n_name = GKNodeRPC.getNodeNameByNodeID(info_list.get(i).site_id);
					node_name_map.put(info_list.get(i).site_id,n_name);
				}
				node_name = "<span style='color:red'>["+n_name+"]</span>";
			}

			str += '<li><input type="'+input_type+'" name="infoList" value="'+info_list.get(i).info_id+'" />'+node_name+'<label url="'+info_list.get(i).content_url+'">'+info_list.get(i).title+'</label>&#160;&#160;&#160;&#160;<span>'+info_list.get(i).released_dtime.substring(5,16)+'</span></li>';
		}
		$("#data").append(str);
		init_input();
	}
	$("#checked_count").html( count>total ? total:count +"/"+total);
}

/*******取信息　结束 *****/

//得到共享信息的站点
function getAllowSharedSite()
{
	//$("#tsArea").addOptionsSingl("zwgk","信息公开系统");
	//共享目录的树
	var sharedCategoryList = CategoryRPC.getAllCateClassList();
	sharedCategoryList =List.toJSList(sharedCategoryList);
	for(var i=0;i<sharedCategoryList.size();i++)
	{
		if(sharedCategoryList.get(i).class_type == 1)
		{
			$("#tsArea").addOptionsSingl("shared_"+sharedCategoryList.get(i).class_id,sharedCategoryList.get(i).class_cname);
		}
		/*
		else
			$("#tsArea").addOptionsSingl(sharedCategoryList.get(i).class_id,sharedCategoryList.get(i).class_cname);
		*/
	}
}


//根据站点加载栏目树
function initCatTree(s_id){
	var cat_jdata = "";

	if(s_id == "zwgk" || isNaN(s_id) == false || s_id == "cms" || s_id.indexOf("shared_") > -1)
	{
		//$("#status_ul li").eq(0).hide();
		//$("#status_ul li").eq(1).attr("checked","true");
		if(isNaN(s_id) == false || s_id.indexOf("shared_") > -1)
		{//走共享目录
			$("#tree_td_2").hide();
			$("#scroll_div").css("width","575px");
			cat_jdata = eval(CategoryRPC.getCategoryTreeByClassID(s_id.replace("shared_","")));
			setLeftMenuTreeJsonData(cat_jdata);
			initTreeClick();
		}
		else
		{
			$("#tree_td_2").show();
			$("#scroll_div").css("width","397px");
			if(s_id == "cms")
			{
				cat_jdata = eval(CategoryRPC.getAllowSharedSiteJSONStr(site_id));
				setLeftMenuTreeJsonData(cat_jdata);
				initCMSTreeClick();
			}
			else
			{
				cat_jdata = eval(GKNodeRPC.getGKNodeTreebyCateID(0));
				setLeftMenuTreeJsonData(cat_jdata);
				initZWGKTree();
			}
		}
	}
	else
	{
		$("#status_ul li").eq(0).show();

		$("#tree_td_2").hide();
		$("#scroll_div").css("width","575px");
		if(s_id == site_id)
		{
			cat_jdata = eval(CategoryRPC.getCategoryTreeBySiteID(site_id));
		}else
		{
			if(site_id == "ggfw")
			{
				//公共服务取出对应站点的所有栏目，不按共享分
				cat_jdata = eval(CategoryRPC.getCategoryTreeBySiteID(s_id));
			}
			else
				cat_jdata = eval(CategoryRPC.getSharedCategoryTreeBySiteID(s_id,site_id));
		}
		setLeftMenuTreeJsonData(cat_jdata);
		initTreeClick();
	}
}

function initCMSTreeClick()
{
	$('#leftMenuTree').tree({
		onClick:function(node){
			s_site_id = node.attributes.real_site_id;
			var cat_jdata;
			if(s_site_id == site_id)
			{
				$("#leftMenuTree2").empty();
				$('#leftMenuTree2').tree({
					 checkbox: false,
					 url: '/servlet/Category?site_id=' + site_id + '&user_id=0&pid=0',
					 onBeforeExpand:function(node,param){
						 $('#leftMenuTree2').tree('options').url = '/servlet/Category?site_id=' + site_id + '&user_id=0&pid=' + node.id;	// change the url
						 //param.myattr = 'test';    // or change request parameter
					 },
					 onClick:function(node){
						 if(node.state == "open")
					     {
							 $(this).tree('collapse',node.target);
					     }
						 else
						 {
							 $(this).tree('expand',node.target);
						 }
						 if(node.id > 0)
						 {
							 clickCategoryNode(node.id);
						 }
					 }
				});

			}else
			{
				cat_jdata = eval(CategoryRPC.getSharedCategoryTreeBySiteID(s_site_id,site_id));
				$("#leftMenuTree2").empty();
				setAppointTreeJsonData("leftMenuTree2",cat_jdata);
				initTreeClick2();
			}
		}
	});
}

function initTreeClick2()
{
	$('#leftMenuTree2').tree({
		onClick:function(node){
			if(node.id > 0)
				clickCategoryNode(node.id);
		}
	});
}

function initZWGKTree()
{
	$('#leftMenuTree').tree({
		onClick:function(node){
			if(node.iconCls == "icon-gknode")
			{
				s_site_id = node.attributes.t_node_id;
				var jdata = eval(CategoryRPC.getCategoryTreeBySiteID(node.attributes.t_node_id));
				jdata = getTreeObjFirstNode(jdata);
				$("#leftMenuTree2").empty();
				setAppointTreeJsonData("leftMenuTree2",jdata);
			}
		}
	});
	$('#leftMenuTree2').tree({
		onClick:function(node){
			clickCategoryNode(node.id);
		}
	});
}

//得到树中的第一个节点的所有子节点,为了不显示根节点
function getTreeObjFirstNode(cat_jdata)
{
	if(cat_jdata != null)
	{
		return cat_jdata[0].children;
	}
}

//初始化树
function initTreeClick()
{
	$('#leftMenuTree').tree({
		onClick:function(node){
			if(node.id > 0)
				clickCategoryNode(node.id);
		}
	});
}

//点击栏目节点事件
function clickCategoryNode(id){
	cat_id=id+"";

	m.remove("con_name");
	m.put("con_value");
    m.put("cat_id", id);
	serarch_con = "";
	$("#searchTimes option").first().attr("selected",true);
	$("#data").empty();
	pageNum = 1;
	count = 0;

	getInfoList();
	getInfoCount();
}

//保存
function related_ok(){
	doGet();
	top.CloseModalWindow();
}

//取消
function related_cancel(){
	top.CloseModalWindow();
}
//把选中的信息添加到表中
function doGet()
{
	var idsTmp = $(":checked[name='infostatus']").val();
	var isPublish = $("#isPublish").is(':checked');
	if($("#data :checked").length == 0)
	{
		related_cancel();
		return;
	}

	var i_list = new List();
	$("#data :checked").each(function(){
		i_list.add(info_map.get($(this).val()));
	});

	if(top.ModalWindowCallback) {
        top.ModalWindowCallback(i_list);
        top.ModalWindowCallback = null;
    }
}

//把选中的信息添加到表中
/*
function doGet(){
	var idsTmp = $(":checked[name='infostatus']").val();
	var isPublish = $("#isPublish").is(':checked');

	if($("#data :checked").length == 0)
	{
		related_cancel();
		return;
	}

	$("#data :checked").each(function(){

		var ib = info_map.get($(this).val());
		var model_ename = ModelRPC.getModelEName(ib.model_id);
		ib = ModelUtilRPC.select(ib.info_id,ib.site_id,model_ename);

		if(isPublish){
			ib.info_status = 8;
		}else{
			ib.info_status = 4;
			ib.step_id = 100;
		}
		if(idsTmp == "2"){
			//如果选择引用类型，直接用链接内容模型
			ib.from_id = ib.info_id;
			ib.is_host = 2;
			ib.model_id = ModelRPC.getModelBeanByEName("link").model_id;
			model_ename == "link";
			//判断获取的信息是否是同一站点的
			if(ib.site_id != site_id)
			{
				//判断内容页地址是否以/（相对路径）开始，如果是，需要加上原站点ID的域名
				if(ib.content_url.indexOf("/") == 0)
				{
					ib.content_url = SiteRPC.getSiteDomain(ib.site_id)+ib.content_url;
				}
			}
		}else
		{//这里付成空，需要重新生成
			ib.content_url = "";
		}

		if(idsTmp == "1")
		{//如果是引用的信息
			ib.from_id = ib.info_id;
			ib.is_host = 1;
		}
		ib.wf_id = CategoryRPC.getWFIDByCatID(cat_id_for_get,site_id);
		var id = InfoBaseRPC.getInfoId();
		ib.id = id;
		ib.info_id = id;
		ib.site_id = site_id;
		ib.app_id = app_id;
		ib.cat_id = cat_id_for_get;
		ib.input_user = top.LoginUserBean.user_id;//获取时录入人为当前操作人
		ModelUtilRPC.insert(ib,model_ename);
	});
	top.msgAlert("获取成功");
	top.getCurrentFrameObj().reloadInfoDataList();
}*/


//----------------------
//选择查询时间
function changeTimes(){
	var cf = $("#searchTimes").val();
	if(cf == "0b"){
		serarch_con = "";
	}else{
		serarch_con = " searchType_"+cf;
	}

	m.remove("con_name");
	m.put("con_value");
	$("#data").empty();
	pageNum = 1;
	count = 0;

	getInfoList();
	getInfoCount();
}

//切换站点事件
function changeSiteId(val){
	$("#leftMenuTree2").empty();
	site_type = val;
	$("#status_ul li").eq(0).show();
	$("#tree_td_2").show();
	$("#scroll_div").css("width","397px");
	s_site_id = site_id;
	$("#leftMenuTree2").empty();
	$('#leftMenuTree2').tree({
		 checkbox: false,
		 url: '/servlet/Category?site_id=' + site_id + '&user_id=0&pid=0',
		 onBeforeExpand:function(node,param){
			 $('#leftMenuTree2').tree('options').url = '/servlet/Category?site_id=' + site_id + '&user_id=0&pid=' + node.id;	// change the url
			 //param.myattr = 'test';    // or change request parameter
		 },
		 onClick:function(node){
			 if(node.state == "open")
		     {
				 $(this).tree('collapse',node.target);
		     }
			 else
			 {
				 $(this).tree('expand',node.target);
			 }
			 if(node.id > 0)
			 {
				 clickCategoryNode(node.id);
			 }
		 }
	});
	m.remove("con_name");
	m.put("con_value");
	serarch_con = "";
	$("#searchTimes option").first().attr("selected",true);
	$("#data").empty();
	pageNum = 1;
	count = 0;

	getInfoList();
	getInfoCount();
}

//点击搜索事件
function doSearchInfoForGet(){
	var words = $("#searchkey").val();
	if(words != "" && words != null)
	{
		m.put("con_name", "title");
		m.put("con_value", words);
	}

	$("#data").empty();
	pageNum = 1;
	count = 0;

	getInfoList();
	getInfoCount();
}
//
