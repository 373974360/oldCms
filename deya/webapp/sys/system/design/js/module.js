var attr_box = [["ListCount","显示条数"],["TitleCount","标题字数"],["ShowMore","显示更多"],["ShowTime","显示时间"],["TimeFormat","时间格式"],["ModuleTitle","模块标题"],["ModuleTitleUrl","链接地址"],["RowCount","每行显示个数"],["ImgWidth","图片宽度"],["ImgHeight","图片高度"],["ShowTitle","显示标题"],["IntroCount","简介字数"],["ShowIntroLink","全文链接"],["MenuCount","导航显示个数"],["PositionSymbol","链接符"],["PosShowAllPath","显示完整路径"],["PosHasLink","是否使用链接"],["PosJumpType","页面跳转方式"],["PosIndexPage","首页描述"],["TrancsImgTime","图片转场时间"],["MarqueeDirectionCrosswise","横向滚动方向"],["MarqueeDirectionLengthways","纵向滚动方向"],["MarqueeDivHeight","滚动区域高度"],["MarqueeSpeed","滚动速度"],["ShowSource","显示来源"],["ShowAuthor","显示作者"]];
var DesignRPC = jsonrpc.DesignRPC;
var DesignModuleBean = new Bean("com.deya.wcm.bean.system.design.DesignModuleBean",true);

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
	colsList.add(setTitleClos("module_name","模块名称","200px","","",""));　
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

	beanList = DesignRPC.getDesignModuleList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	

	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	

	table.getCol("module_name").each(function(i){
		if(i>0)
		{	
			$(this).html('<a href="javascript:openUpdateDesignModulePage('+beanList.get(i-1).module_id+')">'+beanList.get(i-1).module_name+'</a>');
		}
	});

	table.getCol("action_cell").each(function(i){
		if(i>0)
		{	
			$(this).html('<div style="float:left"><input type="file" name="uploadify_'+beanList.get(i-1).module_id+'" id="uploadify_'+beanList.get(i-1).module_id+'"/></div>');
			publicUploadDesignFileButtom("uploadify_"+beanList.get(i-1).module_id,beanList.get(i-1).module_ename,"savePicUrl");
		}
	});	
	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){
	tp.total = DesignRPC.getDesignModuleCount();
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//打开添加窗口
function openAddDesignModulePage()
{		
	top.addTab(true,"/sys/system/design/module_add.jsp?app_id="+app_id+"&top_index="+top.curTabIndex,"维护模块");
}

function openUpdateDesignModulePage(c_id)
{
	var id = "";
	if(c_id != "" && c_id != null)
	{
		id = c_id;
	}else
		id = table.getSelecteCheckboxValue("module_id");
	
	top.addTab(true,"/sys/system/design/module_add.jsp?app_id="+app_id+"&top_index="+top.curTabIndex+"&module_id="+id,"维护模块");
}

function getAttrIDS()
{
	var ids = "";	
	$(":checked[id='design_attr_checkbox']").each(function(i){
		if(i > 0)
			ids += ",";

		ids += $(this).val();
	});
	return ids;
}

function addModule()
{
	var bean = BeanUtil.getCopy(DesignModuleBean);
	$("#module_table").autoBind(bean);
	if(!standard_checkInputInfo("module_table"))
	{
		return;
	}
	bean.app_id = app_id;
	bean.site_id = site_id;
	bean.style_ids = getStyleIDS();
	bean.attr_ids = getAttrIDS();

	if(DesignRPC.insertDesignModule(bean))
	{
		top.msgAlert("模块"+WCMLang.Add_success);
		top.getCurrentFrameObj(top_index).reloadList();
		top.tab_colseOnclick(top.curTabIndex);
	}
	else
	{
		top.msgWargin("模块"+WCMLang.Add_fail);
	}
}

function updateModule()
{
	var bean = BeanUtil.getCopy(DesignModuleBean);
	$("#module_table").autoBind(bean);
	if(!standard_checkInputInfo("module_table"))
	{
		return;
	}
	bean.module_id = module_id;	
	bean.style_ids = getStyleIDS();
	bean.attr_ids = getAttrIDS();

	if(DesignRPC.updateDesignModule(bean))
	{
		top.msgAlert("模块"+WCMLang.Add_success);
		top.getCurrentFrameObj(top_index).reloadList();
		top.tab_colseOnclick(top.curTabIndex);
	}
	else
	{
		top.msgWargin("模块"+WCMLang.Add_fail);
	}
}

function deleteDesignModule()
{
	var selectIDS = table.getSelecteCheckboxValue("module_id");
	if(DesignRPC.deleteDesignModule(selectIDS))
	{
		top.msgAlert("模块"+WCMLang.Delete_success);
		reloadList();
	}else
	{
		top.msgWargin("模块"+WCMLang.Delete_fail);
	}
}

function getStyleIDS()
{
	var ids = "";
	$("#style_list :checked").each(function(){		
		ids += ","+$(this).val();
	});

	if(ids != null && ids != "")
		ids = ids.substring(1);

	return ids;
}