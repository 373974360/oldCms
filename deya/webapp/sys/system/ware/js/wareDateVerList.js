var WareRPC = jsonrpc.WareRPC;
var WareBean = new Bean("com.deya.wcm.bean.system.ware.WareBean",true);
var WareVerBean = new Bean("com.deya.wcm.bean.system.ware.WareVerBean",true);

var table = new Table();	
table.table_name = "";

var tp = new TurnPage();
var beanList = null;


function raloadWareVerList()
{
	showturnPage();
	showList();
}

//初始化table
function initTable()
{
	var colsMap = new Map();
	var colsList = new List();
	
	colsList.add(setTitleClos("wareid","ID","50px","","",""));	
	colsList.add(setTitleClos("warename","标签名称","120px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("ware_ver","版本号","60px","","",""));
	colsList.add(setTitleClos("actionCol","操作","80px","","",""));
	colsList.add(setTitleClos("create_dtime","创建时间","150px","","",""));

	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("ware_table");//里面参数为外层div的id
}


//加载数据
function showList()
{
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "ware_ver";
		sortType = "desc";
	}
	
	var m = new Map();
	m.put("site_id", site_id);
	m.put("ware_id", ware_id);
	m.put("app_id",app_id);
	
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol); //排序字段
	m.put("sort_type", sortType); //排序方式
	
	beanList = WareRPC.getWareVerList(m);
	beanList = List.toJSList(beanList);
	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("ware_table");
	
	table.getCol("wareid").each(function(i){
		if(i>0)
		{
			$(this).html(beanList.get(i-1).ware_id);
		}
	});
	
	table.getCol("warename").each(function(i){
		if(i>0)
		{
			$(this).html(beanList.get(i-1).ware_name);
		}
	});
	
	table.getCol("ware_ver").each(function(i){
		if(i>0)
		{
			$(this).html(beanList.get(i-1).ware_ver);
		}
	});
	
	table.getCol("actionCol").each(function(i){
		if(i>0)
		{
			var str = '<span onclick="recoveryWareVer(\''+beanList.get(i-1).ware_id+'\',\''+beanList.get(i-1).site_id+'\',\''+beanList.get(i-1).ware_ver+'\')" style="cursor:pointer;">恢复此版本</span>&#160;&#160;&#160;';
			$(this).html(str);
		}
	});
	
	table.getCol("create_dtime").each(function(i){
		if(i>0)
		{
			
			$(this).html(beanList.get(i-1).creat_dtime);
		}
	});

}

//翻页
function showturnPage()
{
	tp.total = WareRPC.getWareVerListCount(ware_id);
			
	tp.show("ware_turn","");	
	tp.onPageChange = showList;
}

function recoveryWareVer(wareid,siteid,verNum)
{
	if(WareRPC.recoveryWareVer(wareid,siteid,verNum))
	{
		msgAlert("恢复成功");
		CloseModalWindow();
		getCurrentFrameObj().loadWareTable();
	}else{
		msgAlert("恢复失败");
		CloseModalWindow();
		getCurrentFrameObj().loadWareTable();
	}
}

