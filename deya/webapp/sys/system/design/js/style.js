var DesignRPC = jsonrpc.DesignRPC;
var DesignStyleBean = new Bean("com.deya.wcm.bean.system.design.DesignStyleBean",true);


var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "filterWord_table";;

function reloadList()
{
	showList();	
	showTurnPage();	
}

/* 初始化表格 */
function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();
	
	//英文名，显示名，宽，高，样式名，点击事件
	colsList.add(setTitleClos("style_name","内容样式名称","200px","","",""));　
	colsList.add(setTitleClos("class_name","内容样式类名","200px","","",""));　
	colsList.add(setTitleClos("weight","权重","50px","","",""));
	colsList.add(setTitleClos("spac_cell","&nbsp;","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

//根据条件排序后分页展示数据
function showList(){
	var m = new Map();
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);

	beanList = DesignRPC.getDesignStyleList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();

	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	

	table.getCol("style_name").each(function(i){
		if(i>0)
		{	
			$(this).html('<a href="javascript:openUpdateDesignStylePage('+beanList.get(i-1).style_id+')">'+beanList.get(i-1).style_name+'</a>');
		}
	});	

	table.getCol("action_cell").each(function(i){
		if(i>0)
		{	
			$(this).html('<div style="float:left"><input type="file" name="uploadify_'+beanList.get(i-1).style_id+'" id="uploadify_'+beanList.get(i-1).style_id+'"/></div>');
			publicUploadDesignFileButtom("uploadify_"+beanList.get(i-1).style_id,beanList.get(i-1).style_ename,"savePicUrl");
		}
	});	
	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	tp.total = DesignRPC.getDesignStyleCount();				
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//打开添加窗口
function openAddDesignStylePage()
{	
	OpenModalWindow("维护内容样式","/sys/system/design/style_add.jsp?app_id="+app_id,480,230);
}

function openUpdateDesignStylePage(c_id)
{
	var id = "";
	if(c_id != "" && c_id != null)
	{
		id = c_id;
	}else
		id = table.getSelecteCheckboxValue("style_id");	
	
	OpenModalWindow("维护内容样式","/sys/system/design/style_add.jsp?app_id="+app_id+"&style_id="+id,480,230);
}


function addStyle()
{
	var bean = BeanUtil.getCopy(DesignStyleBean);
	$("#style_table").autoBind(bean);
	if(!standard_checkInputInfo("style_table"))
	{
		return;
	}
	bean.app_id = app_id;
	bean.site_id = site_id;

	if(DesignRPC.insertDesignStyle(bean))
	{
		msgAlert("内容样式"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadList();
	}
	else
	{
		msgWargin("内容样式"+WCMLang.Add_fail);
	}
}

function updateStyle()
{
	var bean = BeanUtil.getCopy(DesignStyleBean);
	$("#style_table").autoBind(bean);
	if(!standard_checkInputInfo("style_table"))
	{
		return;
	}
	bean.style_id = style_id;	

	if(DesignRPC.updateDesignStyle(bean))
	{
		msgAlert("内容样式"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadList();
	}
	else
	{
		msgWargin("内容样式"+WCMLang.Add_fail);
	}
}

function deleteDesignStyle()
{
	var selectIDS = table.getSelecteCheckboxValue("style_id");
	if(DesignRPC.deleteDesignStyle(selectIDS))
	{
		msgAlert("内容样式"+WCMLang.Delete_success);
		reloadList();
	}else
	{
		msgWargin("内容样式"+WCMLang.Delete_fail);
	}
}