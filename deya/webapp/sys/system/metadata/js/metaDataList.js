var MetaDataRPC = jsonrpc.MetaDataRPC;
var MetaDataBean = new Bean("com.deya.wcm.bean.system.metadata.MetaDataBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "metadata_table";;

function reloadMetaDataList()
{
	showTurnPage();
	showList();	
}

function locationMetaData()
{
	window.location.href = "metaDataList.jsp";
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("meta_id","ID","30px","","",""));	
	colsList.add(setTitleClos("meta_cname","中文名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("meta_ename","英文名称","100px","","",""));
	colsList.add(setTitleClos("meta_sname","短名","100px","","",""));
	colsList.add(setTitleClos("meta_datatype","数据类型","80px","","",""));	
	colsList.add(setTitleClos("is_core","类型","80px","","",""));
	
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
		sortCol = "meta_id";
		sortType = "desc";
	}
	
	var m = new Map();
	
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}

	beanList = MetaDataRPC.getMetaDataListForDB(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("meta_cname").each(function(i){
		if(i>0)
		{			
			$(this).css({"text-align":"left"});	
			$(this).html('<a href="javascript:addTab(true,\'/sys/system/metadata/metaData_view.jsp?meta_id='+beanList.get(i-1).meta_id+'\',\'元数据信息\')">'+beanList.get(i-1).meta_cname+'</a>');
		}
	});	

	table.getCol("is_core").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).is_core == 0)
				$(this).text("核心元数据");
			else
				$(this).text("业务元数据");
		}
	});	

	table.getCol("meta_datatype").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).meta_datatype == "string")
				$(this).text("字符串");
			if(beanList.get(i-1).meta_datatype == "datetime")
				$(this).text("日期型");
			if(beanList.get(i-1).meta_datatype == "number")
				$(this).text("数值型");
			if(beanList.get(i-1).meta_datatype == "mixed")
				$(this).text("复合型");
		}
	});	

	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	var m = new Map();

	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
		tp.total = MetaDataRPC.getMetaDataCountForDB(m);	
	}else
	{
		tp.total = MetaDataRPC.getMetaDataCount();	
	}	
			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//打开查看窗口
function openViewMetaDataPage(r_id)
{	
	window.location.href = "/sys/system/metadata/metaData_view.jsp?meta_id="+r_id;
}

//打开添加窗口
function openAddMetaDataPage()
{
	window.location.href = "/sys/system/metadata/metaData_add.jsp";
}

//打开修改窗口
function openUpdateMetaDataPage()
{
	var selectIDS = table.getSelecteCheckboxValue("meta_id");
	window.location.href = "/sys/system/metadata/metaData_add.jsp?meta_id="+selectIDS;
}

//添加元数据
function addMetaData()
{
	var bean = BeanUtil.getCopy(MetaDataBean);	
	$("#metadata_table").autoBind(bean);

	if(!standard_checkInputInfo("metadata_table"))
	{
		return;
	}

	if(MetaDataRPC.insertMetaData(bean))
	{
		msgAlert("元数据信息"+WCMLang.Add_success);
		locationMetaData();
	}
	else
	{
		msgWargin("元数据信息"+WCMLang.Add_fail);
	}
}
//修改元数据
function updateMetaData()
{
	var bean = BeanUtil.getCopy(MetaDataBean);	
	$("#metadata_table").autoBind(bean);

	if(!standard_checkInputInfo("metadata_table"))
	{
		return;
	}
  
	if(MetaDataRPC.updateMetaData(bean))
	{
		msgAlert("元数据信息"+WCMLang.Add_success);
		locationMetaData();
	}
	else
	{
		msgWargin("元数据信息"+WCMLang.Add_fail);
	}
}

//删除元数据
function deleteMetaData()
{
	var selectIDS = table.getSelecteCheckboxValue("meta_id");
	if(MetaDataRPC.deleteMetaData(selectIDS))
	{
		msgAlert("元数据信息"+WCMLang.Delete_success);
		reloadMetaDataList();
	}else
	{
		msgWargin("元数据信息"+WCMLang.Delete_fail);
	}
}

//搜索
function metaDataSearchHandl(obj)
{
	var con_value = $(obj).parent().find("#searchkey").val();
	if(con_value.trim() == "" ||  con_value == null)
	{
		msgAlert(WCMLang.Search_empty);
		return;
	}
	table.con_name = $(obj).parent().find("#searchFields").val(); 
	table.con_value = con_value;
	reloadMetaDataList();
}




