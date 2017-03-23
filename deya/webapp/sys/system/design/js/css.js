var DesignRPC = jsonrpc.DesignRPC;
var DesignCSSBean = new Bean("com.deya.wcm.bean.system.design.DesignCSSBean",true);


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
	colsList.add(setTitleClos("css_name","风格名称","200px","","",""));　
	colsList.add(setTitleClos("css_ename","英文名称","200px","","",""));
	colsList.add(setTitleClos("weight","权重","50px","","",""));
	colsList.add(setTitleClos("action_cell","操作","100px","","",""));
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

	beanList = DesignRPC.getDesignCssList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();

	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	

	table.getCol("css_name").each(function(i){
		if(i>0)
		{						
			$(this).html('<a href="javascript:openUpdateDesignCssPage('+beanList.get(i-1).css_id+')">'+beanList.get(i-1).css_name+'</a>');
		}
	});	

	table.getCol("action_cell").each(function(i){
		if(i>0)
		{						
			$(this).html('<div style="float:left"><input type="file" name="uploadify_'+beanList.get(i-1).css_id+'" id="uploadify_'+beanList.get(i-1).css_id+'"/></div>');
			publicUploadDesignFileButtom("uploadify_"+beanList.get(i-1).css_id,beanList.get(i-1).css_ename,"savePicUrl");
		}
	});	
	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	tp.total = DesignRPC.getDesignCssCount();				
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//打开添加窗口
function openAddDesignCssPage()
{	
	OpenModalWindow("维护主题风格","/sys/system/design/css_add.jsp?app_id="+app_id,480,230);
}

function openUpdateDesignCssPage(c_id)
{
	var id = "";
	if(c_id != "" && c_id != null)
	{
		id = c_id;
	}else
		id = table.getSelecteCheckboxValue("css_id");

	OpenModalWindow("维护主题风格","/sys/system/design/css_add.jsp?app_id="+app_id+"&css_id="+id,480,230);
}


function addCss()
{
	var bean = BeanUtil.getCopy(DesignCSSBean);
	$("#css_table").autoBind(bean);
	if(!standard_checkInputInfo("css_table"))
	{
		return;
	}
	bean.app_id = app_id;
	bean.site_id = site_id;

	if(DesignRPC.nameIsExist("cs_design_css","css_ename",bean.css_ename))
	{
		msgWargin("该英文名称已存在,请重新输入");
		return;
	}

	if(DesignRPC.insertDesignCss(bean))
	{
		msgAlert("主题风格"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadList();
	}
	else
	{
		msgWargin("主题风格"+WCMLang.Add_fail);
	}
}

function updateCss()
{
	var bean = BeanUtil.getCopy(DesignCSSBean);
	$("#css_table").autoBind(bean);
	if(!standard_checkInputInfo("css_table"))
	{
		return;
	}
	bean.css_id = css_id;	

	if(DesignRPC.updateDesignCss(bean))
	{
		msgAlert("主题风格"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadList();
	}
	else
	{
		msgWargin("主题风格"+WCMLang.Add_fail);
	}
}

function deleteDesignCss()
{
	var selectIDS = table.getSelecteCheckboxValue("css_id");
	if(DesignRPC.deleteDesignCss(selectIDS))
	{
		msgAlert("主题风格"+WCMLang.Delete_success);
		reloadList();
	}else
	{
		msgWargin("主题风格"+WCMLang.Delete_fail);
	}
}