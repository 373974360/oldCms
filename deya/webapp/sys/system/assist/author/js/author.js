var AssistRPC = jsonrpc.AssistRPC;
var AuthorBean = new Bean("com.deya.wcm.bean.system.assist.AuthorBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "Author_table";

function reloadAuthorDataList()
{
	showTurnPage();
	showList();	
}

function initTable(){
	var colsMap = new Map();
	var colsList = new List();	
	colsList.add(setTitleClos("author_id","ID","50px","","",""));	
	colsList.add(setTitleClos("author_name","姓名","160px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("author_initial","首字母","60px","","",""));
	colsList.add(setTitleClos("author_url","URL","","","",""));
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
		sortCol = "author_id";
		sortType = "desc";
	}
	var m = new Map();
	m.put("app_id", app);
	m.put("site_id", site_id);
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}
	beanList = AssistRPC.getAuthorList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("author_name").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{			
			$(this).html('<a href="javascript:openUpdatePage('+beanList.get(i-1).author_id+')">'+beanList.get(i-1).author_name+'</a>');
		}
	});
	table.getCol("author_initial").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{			
			var tm = beanList.get(i-1).author_initial;
			if(tm == null || tm == "null" || tm == "NULL"){
				$(this).html("");
			}
		}
	});
	table.getCol("author_url").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{		
			var tm = beanList.get(i-1).author_url;
			if(tm == null || tm == "null" || tm == "NULL"){
				$(this).html("");
			}
		}
	});

	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	var m = new Map();
	m.put("app_id", app);
	m.put("site_id", site_id);
	if(table.con_value.trim() != "" && table.con_value != null){
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
		tp.total = AssistRPC.getAuthorCount(m);	
	}else{
		tp.total = AssistRPC.getAuthorCount(m);	
	}	
			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//打开查看窗口
function openViewAuthorDataPage(author_id)
{	//OpenModalWindow
	parent.OpenModalWindow("查看","/sys/system/assist/author/author_view.jsp?author_id="+author_id,380,195);
}

//打开添加窗口
function openAddAuthorPage()
{
    parent.OpenModalWindow("修改","/sys/system/assist/author/author_add.jsp?app="+app+"&site_id="+site_id,380,195);
}

//打开修改窗口
function openUpdateAuthorDataPage()
{
	var selectIDS = table.getSelecteCheckboxValue("author_id");
    parent.OpenModalWindow("修改","/sys/system/assist/author/author_add.jsp?author_id="+selectIDS,380,195);
}

function openUpdatePage(authorid)
{
    parent.OpenModalWindow("修改","/sys/system/assist/author/author_add.jsp?author_id="+authorid,380,195);
}

//添加Author
function addAuthorData()
{
	var bean = BeanUtil.getCopy(AuthorBean);	
	$("#Author_table").autoBind(bean);

	if(!standard_checkInputInfo("Author_table"))
	{
		return;
	}

	if(AssistRPC.addAuthorById(bean))
	{
        parent.msgAlert(""+WCMLang.Add_success);
        parent.CloseModalWindow();
        parent.getCurrentFrameObj().reloadAuthorDataList();
	}
	else
	{
        parent.msgWargin(""+WCMLang.Add_fail);
	}
}
//修改Author
function updateAuthorData()
{
	var bean = BeanUtil.getCopy(AuthorBean);	
	$("#Author_table").autoBind(bean);

	if(!standard_checkInputInfo("Author_table"))
	{
		return;
	}
  
	if(AssistRPC.updateAuthorById(bean))
	{
        parent.msgAlert(""+WCMLang.Add_success);
        parent.CloseModalWindow();
        parent.getCurrentFrameObj().reloadAuthorDataList();
	}
	else
	{
        parent.msgWargin(""+WCMLang.Add_fail);
	}
}

//删除Author
function deleteAuthorData()
{
	var selectIDS = table.getSelecteCheckboxValue("author_id");
	if(AssistRPC.delAuthorById(selectIDS))
	{
        parent.msgAlert(""+WCMLang.Delete_success);
        parent.CloseModalWindow();
        parent.getCurrentFrameObj().reloadAuthorDataList();
	}else
	{
        parent.msgWargin(""+WCMLang.Delete_fail);
	}
}

function closePage(){
    parent.CloseModalWindow();
}

//搜索
function authorDataSearchHandl(obj)
{
	alert("do search Authors");
//	var con_value = $(obj).parent().find("#searchkey").val();
//	if(con_value.trim() == "" ||  con_value == null)
//	{
//		msgAlert(WCMLang.Search_empty);
//		return;
//	}
//	table.con_name = $(obj).parent().find("#searchFields").val(); 
//	table.con_value = con_value;
//	reloadMetaDataList();
}




