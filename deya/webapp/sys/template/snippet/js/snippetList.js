var SnippetRPC = jsonrpc.SnippetRPC;
var SnippetBean = new Bean("com.deya.wcm.bean.template.snippet.SnippetBean",true);

var val= new Validator();

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
	table.table_name = "app_snippet_table";
var con_m = new Map();

function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("sni_name","名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("handl","操作","","","",""));
	
	//colsList.add(setTitleClos("sni_content","片断内容","","","",""));
	
	//colsList.add(setTitleClos("app_id","所属应用","","","",""));
	//colsList.add(setTitleClos("site_id","站点ID","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}
function reloadSnippetList()
{
	showList();
	showTurnPage();
}
function showList(){
			
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "id";
		sortType = "desc";
	}
	beanList = SnippetRPC.getSnippetList();
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	 
	table.getCol("sni_name").each(function(i){	
		if(i>0)
		{
			
			$(this).html('<a href="javascript:openUpdateSnippetPage('+beanList.get(i-1).id+')">'+beanList.get(i-1).sni_name+'</a>');	
		}
	});
	
	table.getCol("handl").each(function(i){	
		if(i>0)
		{
			if(beanList.get(i-1).app_id==app)
			{
				$(this).html('<a href="javascript:deleteSnippet('+beanList.get(i-1).id+')">删除</a>');	
			}
		}
	});
//	table.getCol("app_id").each(function(i){	
//		if(i>0)
//		{			
//			$(this).html(getAppInfoByID(beanList.get(i-1).app_id));			
//		}
//	});
	Init_InfoTable(table.table_name);
}
function showTurnPage(){
	
	tp.total = SnippetRPC.getSnippetCount();			
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}
// 保存排序
/*function sortSnippetSort()
{
	var selectIDS = table.getAllCheckboxValue("sni_id");
	if(SnippetRPC.saveSnippetSort(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}*/
// 添加代码片段信息
function addSnippetRecord()
{
	OpenModalWindow("新建代码片段信息","/sys/template/snippet/snippet_add.jsp?type=add&app="+app+"&site_id="+site_id,450,239);
}
function openUpdateSnippetPage(id)
{
	OpenModalWindow("修改代码片段信息","/sys/template/snippet/snippet_add.jsp?type=update&id="+id,450,239);
}
// 修改代码片断信息
function updateRecord1()
{  
	var id = table.getSelecteCheckboxValue("id");
	OpenModalWindow("修改代码片段信息","/sys/template/snippet/snippet_add.jsp?type=update&id="+id,450,239);
}

// 删除代码片断信息
function deleteSnippet(id)
{
	var mp = new Map();
	mp.put("id", id);
	if(SnippetRPC.deleteSnippet(mp))
	{
		msgAlert("代码片段信息"+WCMLang.Delete_success);
		reloadSnippetList();
	}
	else
	{
		msgWargin("代码片段信息"+WCMLang.Delete_fail);
	}
}
//添加代码片断信息-保存事件
function addSnippet()
{  
	var addBean = BeanUtil.getCopy(SnippetBean);
	$("#app_snippet_table").autoBind(addBean);
	if(!standard_checkInputInfo("app_snippet_table"))
	{
		return;
	}
	
	addBean.id = SnippetRPC.getInsertID();
	addBean.sni_id = addBean.id;
	
	if(SnippetRPC.insertSnippet(addBean))
	{
		msgAlert("代码片段信息"+WCMLang.Add_success);
		getCurrentFrameObj().reloadSnippetList();//获取父窗口
		CloseModalWindow();
	}else{
		msgWargin("代码片段信息"+WCMLang.Add_fail);
		return;
	}
}
// 修改代码片断信息-保存事件
function updateSnippet()
{
	var updateBean = BeanUtil.getCopy(SnippetBean);
	$("#app_snippet_table").autoBind(updateBean);
	if(!standard_checkInputInfo("app_snippet_table"))
	{
		return;
	}	
	updateBean.id = id;
	if(SnippetRPC.updateSnippet(updateBean))
	{
		msgAlert("代码片段信息"+WCMLang.Set_success);
		getCurrentFrameObj().reloadSnippetList();
		CloseModalWindow();
	}
	else
	{
		msgWargin("代码片段信息"+WCMLang.Set_fail);
		return;
	}
}
//得到应用信息
function getAppList()
{
	appList = SnippetRPC.getAppList();
	appList = List.toJSList(appList);
}

function getAppInfoByID(app_id)
{
	for(var i=0;i<appList.size();i++)
	{
		if(appList.get(i).app_id == app_id)
			return appList.get(i).app_name;
	}
}
