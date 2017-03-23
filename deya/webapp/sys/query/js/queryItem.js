var QueryItemRPC = jsonrpc.QueryItemRPC;
var QueryDicRPC  =  jsonrpc.QueryDicRPC;

var QueryItemBean = new Bean("com.deya.wcm.bean.query.QueryItemBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();

var tp = new TurnPage();
var beanList = null;

var QueryDicBeanList = null;
var DicBeanList = null;

var QueryItemBeanList = null;

var table = new Table();	
    table.table_name = "QueryItem_table";

function reloadList()
{	
	showTurnPage();
	showList();
}

function initTable(){

	var colsMap = new Map();	
	var colsList = new List();
	
	DicBeanList = getQueryDicBeanList(conf_id);
	if(DicBeanList != "")
	{
	  for(var i=0;i<DicBeanList.size();i++)
	  {
		colsList.add(setTitleClos("item_"+DicBeanList.get(i).dic_id,DicBeanList.get(i).field_cname,"","","",""));
	  }
	}
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function getQueryDicBeanList(conf_id){
	QueryDicBeanList = QueryDicRPC.getDicListByConf_id(conf_id);
	QueryDicBeanList = List.toJSList(QueryDicBeanList);//把list转成JS的List对象
	if(QueryDicBeanList != null && QueryDicBeanList.size() > 0)
	{				
		return QueryDicBeanList;
	}else{
		return "";
	}
}

function showList()
{
	var m = new Map();
	m.put("start_num",tp.getStart());	
	m.put("page_size",tp.pageSize);
	m.put("conf_id",conf_id);
	m.put("site_id",site_id);
	m.put("cell_count",tp.getStart()+tp.pageSize);

	beanList = QueryItemRPC.getQueryItemLists(m);
	beanList = List.toJSList(beanList);//把list转成JS的List对象	

	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");

	var Lists = getQueryDicBeanList(conf_id);
	if(Lists != "")
	{
		  for(var i=0;i<Lists.size();i++)
		  {
			 table.getCol("item_"+Lists.get(i).dic_id).each(function(j){
				if(j>0)
				{
					var m2 = beanList.list[j-1];
					    m2 = Map.toJSMap(m2);
					$(this).text(m2.get("ITEM_"+Lists.get(i).dic_id));
				}
			});
		  }
	}
	Init_InfoTable(table.table_name);
}

function showTurnPage(){
	var m = new Map();
	m.put("start_num",tp.getStart());	
	m.put("page_size",tp.pageSize);

	m.put("site_id",site_id);
	m.put("conf_id",conf_id);
    
	m.put("cell_count",tp.getStart()+tp.pageSize);

	tp.total = QueryItemRPC.getQueryItemCounts(m);
	tp.show("turn","");	
	tp.onPageChange = showList;
}

function openSetPage(conf_id,type,site_id)
{
	if(type ==1){
		window.location.href = "/sys/query/query_dic.jsp?conf_id="+conf_id+"&site_id="+site_id;
	}else if(type ==2){
		window.location.href = "/sys/query/query_upload.jsp?conf_id="+conf_id+"&site_id="+site_id;
	}else{
		window.location.href = "/sys/query/query_infoLists.jsp?conf_id="+conf_id+"&site_id="+site_id;
	}	
}

function openAddPage(site_id,conf_id)
{
	window.location.href = "/sys/query/query_infoAdd.jsp?site_id="+site_id+"&conf_id="+conf_id;	
}

function openUpdatePage(site_id,conf_id)
{
	var selectIDS="";
	var m ="";
	var beanList = table.getSelecteBeans();
	if(beanList.size() ==0)
	{
		msgAlert("请选择一条记录！");
		return;
	}else if(beanList.size()>1)
	{
		msgAlert("只能选择一条记录修改！");
		return;
	}else{
		m = beanList.list[0];
	    m = Map.toJSMap(m);
		selectIDS = m.get("ITEM_ID").value;
	}
	window.location.href = "/sys/query/query_infoModify.jsp?site_id="+site_id+"&conf_id="+conf_id+"&item_id="+selectIDS;
}

function deleteQueryItems(site_id,conf_id)
{
    var selectIDS="";
	var map ="";
	var beanList = table.getSelecteBeans();
	if(beanList.size() ==0)
	{
		msgAlert("请选择一条记录！");
		return;
	}else{
		for(var i=0;i<beanList.size();i++)
		{
			 var map = beanList.list[i];
			     map = Map.toJSMap(map);
			 selectIDS += map.get("ITEM_ID").value+",";
		}
	}
		selectIDS = selectIDS.substring(0,selectIDS.length -1);
    
	var m = new Map();
	m.put("item_id",selectIDS);
	m.put("conf_id",conf_id);

	if(QueryItemRPC.deleteQueryItem(m))
	{
		msgAlert("信息删除成功！");
		reloadList();
	}else
	{
		msgWargin("信息删除失败！");
	}
}

function deleteItemsByConfId(conf_id)
{
	if(QueryItemRPC.deleteQueryItemByConf_id(conf_id))
	{
		msgAlert("信息清除成功！");
		reloadList();
	}else
	{
		msgWargin("信息清除失败！");
	}
}