var DesignRPC = jsonrpc.DesignRPC;
var DesignFrameBean = new Bean("com.deya.wcm.bean.system.design.DesignFrameBean",true);


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
	colsList.add(setTitleClos("frame_name","外框样式","200px","","",""));　
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

	beanList = DesignRPC.getDesignFrameList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();

	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	

	table.getCol("frame_name").each(function(i){
		if(i>0)
		{	
			$(this).html('<a href="javascript:openUpdateDesignFramePage('+beanList.get(i-1).frame_id+')">'+beanList.get(i-1).frame_name+'</a>');
		}
	});	

	table.getCol("action_cell").each(function(i){
		if(i>0)
		{	
			$(this).html('<div style="float:left"><input type="file" name="uploadify_'+beanList.get(i-1).frame_id+'" id="uploadify_'+beanList.get(i-1).frame_id+'"/></div>');
			publicUploadDesignFileButtom("uploadify_"+beanList.get(i-1).frame_id,beanList.get(i-1).frame_ename,"savePicUrl");
		}
	});	
	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	tp.total = DesignRPC.getDesignFrameCount();			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//打开添加窗口
function openAddDesignFramePage()
{		
	addTab(true,"/sys/system/design/frame_add.jsp?app_id="+app_id+"&top_index="+curTabIndex,"修改外框样式");
}

function openUpdateDesignFramePage(c_id)
{
	var id = "";
	if(c_id != "" && c_id != null)
	{
		id = c_id;
	}else
		id = table.getSelecteCheckboxValue("frame_id");
	
	addTab(true,"/sys/system/design/frame_add.jsp?app_id="+app_id+"&top_index="+curTabIndex+"&frame_id="+id,"修改外框样式");
}


function addFrame()
{
	var bean = BeanUtil.getCopy(DesignFrameBean);
	$("#frame_table").autoBind(bean);
	if(!standard_checkInputInfo("frame_table"))
	{
		return;
	}
	bean.app_id = app_id;
	bean.site_id = site_id;

	if(DesignRPC.insertDesignFrame(bean))
	{
		msgAlert("外框样式"+WCMLang.Add_success);
		getCurrentFrameObj(top_index).reloadList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("外框样式"+WCMLang.Add_fail);
	}
}

function updateFrame()
{
	var bean = BeanUtil.getCopy(DesignFrameBean);
	$("#frame_table").autoBind(bean);
	if(!standard_checkInputInfo("frame_table"))
	{
		return;
	}
	bean.frame_id = frame_id;	

	if(DesignRPC.updateDesignFrame(bean))
	{
		msgAlert("外框样式"+WCMLang.Add_success);
		getCurrentFrameObj(top_index).reloadList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("外框样式"+WCMLang.Add_fail);
	}
}

function deleteDesignFrame()
{
	var selectIDS = table.getSelecteCheckboxValue("frame_id");
	if(DesignRPC.deleteDesignFrame(selectIDS))
	{
		msgAlert("外框样式"+WCMLang.Delete_success);
		reloadList();
	}else
	{
		msgWargin("外框样式"+WCMLang.Delete_fail);
	}
}