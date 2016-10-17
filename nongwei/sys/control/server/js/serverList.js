var SiteServerRPC = jsonrpc.SiteServerRPC;
var SiteServerBean = new Bean("com.deya.wcm.bean.control.SiteServerBean",true);

var val=new Validator();
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "server_table";


function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	  
	colsList.add(setTitleClos("server_name","服务器名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("server_ip","服务器IP","160px","","",""));
	colsList.add(setTitleClos("server_type","服务器类型","160px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id

}

function reloadServerDataList()
{
	showList();	
	showTurnPage();
}

function showList(){			

	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "server_id";
		sortType = "asc";
	}
	
	var m = new Map();
	m.put("start_num", tp.getStart());	
	//m.put("page_size", tp.pageSize);
	m.put("page_size", '999');
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	
	beanList = SiteServerRPC.getSiteServerListByPage(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	 
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");

	table.getCol("server_name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openViewPage(\''+beanList.get(i-1).server_id+'\')">'+beanList.get(i-1).server_name+'</a>');
		}
	});
	table.getCol("server_type").each(function(i){
		if(i>0)
		{
			var server_type = beanList.get(i-1).server_type;
			if(server_type=='1'){
				server_type = "数据库服务器";
			}else if(server_type=='2'){
				server_type = "发布服务器";
			}else if(server_type=='3'){
				server_type = "访问服务器";
			}else if(server_type=='4'){
				server_type = "素材服务器";
			}
			$(this).html(server_type);
		}
	})
	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){  
	tp.total = List.toJSList(SiteServerRPC.getServerList()).size();
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

//打开添加页面
function openAddPage(){ 
	top.OpenModalWindow("维护服务器","/sys/control/server/server_add.jsp",500,260);
}


//添加
function funAdd(){
	if(!standard_checkInputInfo(table.table_name))
	{
		return;
	}
	$("#"+table.table_name).autoBind(SiteServerBean);
	//alert(SiteServerBean);
	
	if(SiteServerRPC.insertSiteServer(SiteServerBean))
	{
		top.msgAlert("服务器信息"+WCMLang.Add_success);
		top.CloseModalWindow();
		top.getCurrentFrameObj().reloadServerDataList();
	}
	else
	{
		top.msgWargin("服务器信息"+WCMLang.Add_fail);
	}
}


//打开修改页面
function openUpdatePage(){
	var selectIDS = table.getSelecteCheckboxValue("server_id");
	top.OpenModalWindow("维护服务器","/sys/control/server/server_add.jsp?server_id="+selectIDS,500,260);
}

//打开查看页面
function openViewPage(s_id)
{  
	top.OpenModalWindow("查看服务器信息","/sys/control/server/server_view.jsp?server_id="+s_id,500,260);
}

//修改
function funUpdate(){
	if(!standard_checkInputInfo(table.table_name))
	{
		return;
	}
	//alert(SiteServerBean.server_id);
	SiteServerBean = defaultBean;
	$("#"+table.table_name).autoBind(SiteServerBean);
	
	if(SiteServerRPC.updateSiteServer(SiteServerBean))
	{
		top.msgAlert("服务器信息"+WCMLang.Add_success);
		top.CloseModalWindow();
		top.getCurrentFrameObj().reloadServerDataList();
	}
	else
	{
		top.msgWargin("服务器信息"+WCMLang.Add_fail);
	}
}


//删除
function funDelete(){
	var selectIDS = table.getSelecteCheckboxValue("server_id");
	
	if(SiteServerRPC.deleteSiteServer(selectIDS)){
		top.msgAlert("服务器信息"+WCMLang.Delete_success);
		top.getCurrentFrameObj().reloadServerDataList(); 
	}else
	{
		top.msgWargin("服务器信息"+WCMLang.Delete_fail);
	}
}


