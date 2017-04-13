var OperateRPC = jsonrpc.OperateRPC;
var OperateBean = new Bean("com.deya.wcm.bean.org.operate.OperateBean",true);

var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "Operate_table_list";;
var con_m = new Map();

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("opt_id","ID","20px","","",""));
	colsList.add(setTitleClos("opt_name","权限名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　	
	colsList.add(setTitleClos("app_id","所属应用","120px","","",""));
	//colsList.add(setTitleClos("controller","控制器","120px","","",""));
	//colsList.add(setTitleClos("action","动作","120px","","",""));
	colsList.add(setTitleClos("opt_flag","权限标识","120px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){			

	beanList = OperateRPC.getChildOptList(opt_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	
	
	table.getCol("opt_name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openViewMengPage('+beanList.get(i-1).opt_id+')">'+beanList.get(i-1).opt_name+'</a>');
		}
	});	

	table.getCol("app_id").each(function(i){	
		if(i>0)
		{			
			$(this).html(getAppInfoByID(beanList.get(i-1).app_id));			
		}
	});

	table.getCol("opt_flag").each(function(i){	
		if(i>0)
		{			
			$(this).css({"text-align":"left"});		
		}
	});
}

function openViewMengPage(o_id)
{
	OpenModalWindow("查看权限信息","/sys/org/operate/operate_view.jsp?opt_id="+o_id,500,308);
}

function showTurnPage(){	
	
	tp.total = OperateRPC.getChildOptCount(opt_id);			
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

function openAddOperatePage()
{
	OpenModalWindow("修改权限","/sys/org/operate/operate_add.jsp?parentID="+opt_id,500,308);
}

function openUpdateOperatePage()
{
	var selectIDS = table.getSelecteCheckboxValue("opt_id");
	OpenModalWindow("修改权限","/sys/org/operate/operate_add.jsp?parentID="+opt_id+"&opt_id="+selectIDS,500,308);
}

function addOperate()
{
	var bean = BeanUtil.getCopy(OperateBean);	
	$("#opt_table").autoBind(bean);

	if(!standard_checkInputInfo("opt_table"))
	{
		return;
	}

	bean.opt_id = OperateRPC.getOperateID();
	bean.parent_id = parent_id;
	if(OperateRPC.insertOperate(bean))
	{
		msgAlert("权限信息"+WCMLang.Add_success);
		getCurrentFrameObj().changeOperateListTable(parent_id);
		getCurrentFrameObj().insertOperateTree(bean.opt_id,bean.opt_name);
		CloseModalWindow();
	}
	else
	{
		msgWargin("权限信息"+WCMLang.Add_fail);
	}
}

//添加树节点
function insertOperateTree(id,dept_name)
{
	insertTreeNode(eval('[{"id":'+id+',"text":\"'+dept_name+'\","attributes":{}}]'));	
}


function updateOperate()
{
	var bean = BeanUtil.getCopy(OperateBean);	
	$("#opt_table").autoBind(bean);
	if(!standard_checkInputInfo("opt_table"))
	{
		return;
	}

	bean.opt_id = opt_id;
	
	if(OperateRPC.updateOperate(bean))
	{
		msgAlert("权限信息"+WCMLang.Add_success);
		getCurrentFrameObj().changeOperateListTable(parent_id);
		getCurrentFrameObj().updateOperateTree(bean.opt_id,bean.opt_name);
		CloseModalWindow();
	}
	else
	{
		msgWargin("权限信息"+WCMLang.Add_fail);
	}
}

//修改树节点
function updateOperateTree(id,opt_name)
{
	updateTreeNode(id,opt_name);
}

function deleteOperate()
{
	var selectIDS = table.getSelecteCheckboxValue("opt_id");
	if(OperateRPC.deleteOperate(selectIDS))
	{
		msgAlert("权限信息"+WCMLang.Delete_success);
		getCurrentFrameObj().changeOperateListTable(opt_id);
		getCurrentFrameObj().deleteTreeNode(selectIDS);
	}else
	{
		msgWargin("权限信息"+WCMLang.Delete_fail);
	}
}


//得到应用信息
function getAppList()
{
	appList = OperateRPC.getAppList();
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

function moveOperate(parent_id)
{
	if(parent_id != "" && parent_id != null)
	{
		var selectIDS = table.getSelecteCheckboxValue("opt_id");			
		if(OperateRPC.moveOperate(parent_id,selectIDS)){
			msgAlert("权限"+WCMLang.Move_success);
			loadOperateTable()
			showOperateTree();
		}else
		{
			msgWargin("权限"+WCMLang.Move_fail);
			return;
		}
	}
}

//打开权限选择窗口
function openSelectSinglOperatePage(title,handl_name)
{
	OpenModalWindow(title,"/sys/org/operate/select_singl_operate.jsp?handl_name="+handl_name,450,510);
}