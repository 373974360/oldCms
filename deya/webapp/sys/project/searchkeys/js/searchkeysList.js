var bean = new Bean("com.deya.project.searchkeys.SearchKey",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
var con_m = new Map();
    table.table_name = "form_table";

function reloadList()
{
	showList();	
	showTurnPage();	
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();		
	
	colsList.add(setTitleClos("title","关键字","200px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	//colsList.add(setTitleClos("action_Col","操作","","","",""));
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("action","操作","120px","","",""));
	colsList.add(setTitleClos("blank_col","&#160;","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

var updateType = false;
function showList(){	
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "";
		sortType = "";
	}
	con_m.put("start_num", tp.getStart());	
	con_m.put("page_size", tp.pageSize);
	con_m.put("site_id", site_id);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		con_m.put("con_name", table.con_name);
		con_m.put("con_value", table.con_value);
	}

    if(!updateType){
		 //加载图片
		var picStr = '<span class="blank9"></span><img style="display:" id="img" width="85" height="13"  style="vertical-align:top;" src="/sys/images/loading3.gif" /><span class="blank24"></span>';
		$("#table").html(picStr);
	}
	updateType = false;
	setTimeout(function(){
		jsonrpc.SearchKeyRPC.getSearchKeysList(showListResult,con_m);//第一个参数为站点ID，暂时默认为空
	},12);
}

function showListResult(result,e){

    if(e != null){return;}

	beanList = List.toJSList(result);//把list转成JS的List对象	
	//alert(toJSON(beanList));
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("title").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		//alert(0);
		if(i>0)
		{
			var title = beanList.get(i-1).title;
			$(this).html('<a href="javascript:openUpdatePage2('+beanList.get(i-1).id+')">'+title+'</a>');
		} 
	});
	
	table.getCol("actionCol").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		//alert(0);
		if(i>0)
		{
			$(this).html('<a href="javascript:openUpdatePage2('+beanList.get(i-1).id+')">修改</a>'+"&nbsp;&nbsp;&nbsp;"+'<a href="javascript:msgConfirm(\'确定要进行该操作吗？\',\'deleteData2('+beanList.get(i-1).id+')\');">删除</a>');
		} 
	});
	
	table.getCol("sort_col").each(function(i){	
		if(i>0)
		{
			$(this).html(getSortColStr());			
		}
	});
	
	table.getCol("action").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		//alert(0);
		if(i>0)
		{
			$(this).html('<a href="javascript:openUpdatePage2('+beanList.get(i-1).id+')">修改</a>'+"&nbsp;&nbsp;&nbsp;"+'<a href="javascript:msgConfirm(\'确定要进行该操作吗？\',\'deleteData2('+beanList.get(i-1).id+')\');">删除</a>');
		} 
	});
	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){
	setTimeout(function(){
		jsonrpc.SearchKeyRPC.getSearchKeysListCount(showTurnPageResult,con_m);
	},12);
}

function showTurnPageResult(result,e){
    if(e != null){return;}
	tp.total = result;
    tp.show("turn","");	 
	tp.onPageChange = showList;
}


//添加会员分类信息
function addInfoPage()
{
	OpenModalWindow("新建关键词","/sys/project/searchkeys/searchkeyList_add.jsp?site_id="+site_id,460,245);
}


//添加数据
function addData(){
	var bean = BeanUtil.getCopy(bean);
    $("#form_table").autoBind(bean);
	if(!standard_checkInputInfo("form_table"))
	{
		return;
	}
	bean.title = $("#title").val();
	bean.type =  $("input[name='type']:checked").val();
	bean.site_id = site_id;
	jsonrpc.SearchKeyRPC.addSearchKey(addDataResult,bean);
}  

function addDataResult(result,e){
    if(e != null){return;}
	if(result){
		msgAlert("信息添加成功！");
		getCurrentFrameObj().reloadList();
		CloseModalWindow();
	}else{
		msgWargin("信息添加失败！");
	}
}


//修改
function openUpdatePage2(selectIDS){
	//addTab(true,'/sys/searchkeys/searchkeyList_add.jsp?id='+selectIDS+'&tab_index='+curTabIndex,'修改');
	OpenModalWindow("修改关键词","/sys/project/searchkeys/searchkeyList_add.jsp?site_id="+site_id+"&id="+selectIDS,460,245);
	//OpenModalWindow("修改","/sys/nongye/member/member_card_add.jsp?card_id="+selectIDS,900,640);
}


function updateData(){
	var bean = BeanUtil.getCopy(bean);
    $("#form_table").autoBind(bean);
	if(!standard_checkInputInfo("form_table"))
	{
		return;
	}
	bean.id = id;
	bean.title = $("#title").val();
	bean.type =  $("input[name='type']:checked").val();
	bean.site_id = site_id;
	jsonrpc.SearchKeyRPC.updateSearchKeyById(updateDataResult,bean);
}

function updateDataResult(result,e){
    if(e != null){return;}
	if(result){  
		msgAlert("信息修改成功!");
		getCurrentFrameObj().reloadList();
		CloseModalWindow();
	}else{  
		msgWargin("信息修改失败！");
	}
}


//保存排序
function saveModelSort()
{
	var model_ids = table.getAllCheckboxValue("id");
	if(model_ids != "" && model_ids != null)
	{
		if(jsonrpc.SearchKeyRPC.sortSearchKeys(model_ids))
		{
			msgAlert(WCMLang.Sort_success);
			reloadList();
		}
		else
		{
			msgWargin(WCMLang.Sort_fail);
			return;
		}
	}
}


//多个删除
function deleteData()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	deleteData2(selectIDS);
}

//单个删除
function deleteData2(m_id)
{
	//var selectIDS = table.getSelecteCheckboxValue("dengji_id");
	jsonrpc.SearchKeyRPC.deleteSearchKeys(deleteDataResult,m_id);
}


function deleteDataResult(result,e){
    if(e != null){return;}
    if(result)
	{
		msgAlert("信息删除成功");
		reloadList();
	}else
	{
		msgWargin("信息删除失败");
	}
}