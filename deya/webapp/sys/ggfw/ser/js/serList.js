var SerRPC = jsonrpc.SerRPC;
var SiteRPC = jsonrpc.SiteRPC;
var DataDictRPC = jsonrpc.DataDictRPC;
var SerCategoryBean = new Bean("com.deya.wcm.bean.zwgk.ser.SerCategoryBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "ser_table";;

function reloadSerList()
{
	showList();	
	showTurnPage();	
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("ser_id","ID","30px","","",""));
	colsList.add(setTitleClos("cat_name","主题名称","250px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("publish_status","发布状态","100px","","",""));
	colsList.add(setTitleClos("actionCol","操作","","","",""));
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("spanCol"," ","","","",""));
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
		sortCol = "id";
		sortType = "desc";
	}
	

	beanList = SerRPC.getSerCategoryRootList();//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size()
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("cat_name").each(function(i){
		$(this).css("text-align","left");
		if(i>0)
		{
			$(this).html('<a href="javascript:openAddSerCategoryPage('+beanList.get(i-1).ser_id+')">'+beanList.get(i-1).cat_name+'</a>');
		}
	});	

	table.getCol("actionCol").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openXGXXPage('+beanList.get(i-1).xgwt_cat_id+')">相关信息</a>&#160;&#160;&#160;<a href="javascript:openCJWTPage('+beanList.get(i-1).cjwt_cat_id+')">常见问题</a>');
		}
	});

	table.getCol("publish_status").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).publish_status == 0)
				$(this).html('未发布');
			else
				$(this).html('已发布');
		}
	});	

	table.getCol("sort_col").each(function(i){	
		if(i>0)
		{
			$(this).html(getSortColStr());
		}
	});	
	
	Init_InfoTable(table.table_name);
}

function openXGXXPage(xgwt_cat_id)
{
	top.addTab(true,"/sys/cms/info/article/articleDataList.jsp?cat_id="+xgwt_cat_id+"&app_id=ggfw&site_id=ggfw","相关信息维护");
}

function openCJWTPage(cjwt_cat_id)
{
	top.addTab(true,"/sys/cms/info/article/articleDataList.jsp?cat_id="+cjwt_cat_id+"&app_id=ggfw&site_id=ggfw","常见问题维护");
}

function showTurnPage(){				
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

function openAddSerCategoryPage(ser_id)
{
	if(ser_id != null)
		top.addTab(true,"/sys/ggfw/ser/serTopic_add.jsp?top_index="+top.curTabIndex+"&ser_id="+ser_id,"维护场景主题");
	else
		top.addTab(true,"/sys/ggfw/ser/serTopic_add.jsp?top_index="+top.curTabIndex,"维护场景主题");
}

function openUpdateSerCategoryPage()
{
	var selectIDS = table.getSelecteCheckboxValue("ser_id");
	openAddSerCategoryPage(selectIDS);
}

function addSerTopic()
{
	var bean = BeanUtil.getCopy(SerCategoryBean);
	$("#add_table").autoBind(bean);
	
	if(!standard_checkInputInfo("add_table"))
	{
		return;
	}

	if(bean.res_flag == 1 && bean.dict_id == "")
	{
		top.msgWargin("请选择资源分类");
		return;
	}

	bean.parent_id = 0;
	bean.ser_id = SerRPC.getNewID();
	bean.id = bean.ser_id;
	bean.cat_type = "root";
	if(SerRPC.insertSerCategory(bean))
	{
		top.msgAlert("场景式服务主题"+WCMLang.Add_success);			
		top.getCurrentFrameObj(top_index).reloadSerList();
		top.tab_colseOnclick(top.curTabIndex);
	}else
	{
		top.msgWargin("场景式服务主题"+WCMLang.Add_fail);
	}
}

function updateSerTopic()
{
	var bean = BeanUtil.getCopy(SerCategoryBean);
	$("#add_table").autoBind(bean);
	
	if(!standard_checkInputInfo("add_table"))
	{
		return;
	}

	if(bean.res_flag == 1 && bean.dict_id == "")
	{
		top.msgWargin("请选择资源分类");
		return;
	}

	bean.ser_id = ser_id;
	bean.cat_type = "root";
	if(SerRPC.updateSerCategory(bean))
	{
		top.msgAlert("场景式服务主题"+WCMLang.Add_success);			
		top.getCurrentFrameObj(top_index).reloadSerList();
		top.tab_colseOnclick(top.curTabIndex);
	}else
	{
		top.msgWargin("场景式服务主题"+WCMLang.Add_fail);
	}
}

function deleteSerCategory()
{
	var selectIDS = table.getSelecteCheckboxValue("ser_id");

	if(SerRPC.deleteSerCategory(selectIDS))
	{
		top.msgAlert("场景式服务主题"+WCMLang.Delete_success);
		reloadSerList();
	}else
	{
		top.msgWargin("场景式服务主题"+WCMLang.Delete_fail);
	}
}

function sortSerCategory()
{
	var selectIDS = table.getAllCheckboxValue("ser_id");
	if(SerRPC.sortSerCategory(selectIDS))
	{
		top.msgAlert(WCMLang.Sort_success);
	}else
	{
		top.msgWargin(WCMLang.Sort_fail);
	}
}

function batchPublishSerCategory(publish_status)
{
	var selectIDS = table.getSelecteCheckboxValue("ser_id");
	if(SerRPC.updateSerCategoryStatus(selectIDS,publish_status))
	{
		top.msgAlert(WCMLang.Publish_success);		
		showList();	
	}
	else
		top.msgWargin(WCMLang.Publish_fail);
}

