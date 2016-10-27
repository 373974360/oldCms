var SearchRPC = jsonrpc.SearchRPC;
var AuthorBean = new Bean("com.deya.wcm.bean.system.assist.AuthorBean",true);

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "Author_table";
  
function reloadList()
{
	showTurnPage();
	showList();	
}

function initTable(){
	var colsMap = new Map();
	var colsList = new List();	
	colsList.add(setTitleClos("site_id","站点ID","50px","","",""));	
	colsList.add(setTitleClos("site_name","站点名称","","","",""));
	colsList.add(setTitleClos("site_domain","站点域名","150px","","",""));
	colsList.add(setTitleClos("state","状态","150px","","",""));
	colsList.add(setTitleClos("actionCol","操作","150px","","",""));
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
		sortCol = "";
		sortType = "";
	}
	var m = new Map();
	//m.put("app_id", app);
	//m.put("site_id", site_id);
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}
	beanList = SearchRPC.getSiteListByMap(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	//alert(toJSON(beanList));
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");

	table.getCol("actionCol").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		//alert(0);
		if(i>0)
		{
			var site_id = beanList.get(i-1).site_id;
			$(this).html('<a href="javascript:doCreateConfirm(\''+site_id+'\');">创建索引</a>&nbsp;&nbsp;&nbsp;<a href="javascript:doDeleteConfirm(\''+site_id+'\');">删除索引</a>');
		} 
	});
	
	table.getCol("state").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		//alert(0);
		if(i>0)
		{
			var site_id = beanList.get(i-1).site_id;
			var state = beanList.get(i-1).state;
			if(state=='1'){
				state = "<span id='"+site_id+"'>已建索引</span>";
			}else{
				state = "<span id='"+site_id+"'><font color='red'>未建索引</font></span>";
			}
			$(this).html(state);
			//$(this).attr("title",state);
		}
	});
	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	var m = new Map();
	//m.put("app_id", app);
	//m.put("site_id", site_id);
	if(table.con_value.trim() != "" && table.con_value != null){
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
		tp.total = SearchRPC.getSiteListByMapCount();	
	}else{
		tp.total = SearchRPC.getSiteListByMapCount();	
	}	
			
	tp.show("turn","");	
	tp.onPageChange = showList;
}




