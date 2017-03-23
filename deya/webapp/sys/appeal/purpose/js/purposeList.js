var PurposeRPC = jsonrpc.PurposeRPC;
var PurposeBean = new Bean("com.deya.wcm.bean.appeal.purpose.PurposeBean",true);

var val= new Validator();

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
	table.table_name = "app_purpose_table";
var con_m = new Map();

function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("pur_name","名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("sort_id","排序","100px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}
function reloadPurposeList()
{
	showList();
	showTurnPage();
}
function showList(){
			
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "pur_id";
		sortType = "desc";
	}
	beanList = PurposeRPC.getPurposeList();
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("pur_name").each(function(i){	
		if(i>0)
		{
			$(this).html('<a href="javascript:openUpdatePurposePage2('+beanList.get(i-1).pur_id+')">'+beanList.get(i-1).pur_name+'</a>');	
		}
	});
	table.getCol("sort_id").each(function(i){	
		if(i>0)
		{			
			$(this).html(getSortColStr());			
		}
	});
	Init_InfoTable(table.table_name);
}
function showTurnPage(){
	
	tp.total = PurposeRPC.getPurposeCount();			
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}
// 保存诉求目的排序
function sortPurposeSort()
{
	var selectIDS = table.getAllCheckboxValue("pur_id");
	if(PurposeRPC.savePurposeSort(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}
// 添加诉求目的信息
function addPurposeRecord()
{
	OpenModalWindow("新建诉求目的信息","/sys/appeal/purpose/purpose_add.jsp?type=add",460,145);
}

function openUpdatePurposePage2(pur_id)
{
	OpenModalWindow("维护诉求目的信息","/sys/appeal/purpose/purpose_add.jsp?type=update&pur_id="+pur_id,450,145);
}
//修改诉求目的信息
function updatePurposePage()
{
	var selectIDS = table.getSelecteCheckboxValue("pur_id");
	OpenModalWindow("维护诉求目的信息","/sys/appeal/purpose/purpose_add.jsp?type=update&pur_id="+selectIDS,450,145);
}
// 删除诉求目的信息
function deletePurpose()
{
	var pur_id = table.getSelecteCheckboxValue("pur_id");
	var mp = new Map();
		mp.put("pur_id", pur_id);
	if(PurposeRPC.deletePurpose(mp))
	{
		msgAlert("诉求目的信息"+WCMLang.Delete_success);
		reloadPurposeList();
	}
	else
	{
		msgWargin("诉求目的信息"+WCMLang.Delete_fail);
	}
}
//添加诉求目的信息-保存事件
function addPurpose()
{
	var addBean = BeanUtil.getCopy(PurposeBean);
		$("#app_purpose_table").autoBind(addBean);

	if(!standard_checkInputInfo("app_purpose_table"))
	{
		return;
	}	
	addBean.pur_id = PurposeRPC.getAppPurposeID();
	if(PurposeRPC.insertPurpose(addBean))
	{
		msgAlert("诉求目的信息"+WCMLang.Add_success);
		getCurrentFrameObj().reloadPurposeList();
		CloseModalWindow();
	}
	else
	{
		msgWargin("诉求目的信息"+WCMLang.Add_fail);
		return;
	}
}
// 修改诉求目的信息-保存事件
function updatePurpose()
{
	var updateBean = BeanUtil.getCopy(PurposeBean);
		$("#app_purpose_table").autoBind(updateBean);
	if(!standard_checkInputInfo("app_purpose_table"))
	{
		return;
	}	
	updateBean.pur_id = pur_id;
	if(PurposeRPC.updatePurpose(updateBean))
	{
		msgAlert("诉求目的信息"+WCMLang.Set_success);
		getCurrentFrameObj().reloadPurposeList();
		CloseModalWindow();
	}
	else
	{
		msgWargin("诉求目的信息"+WCMLang.Set_fail);
		return;
	}
}
//节点排序
function sortPurpose()
{
	var selectIDS = table.getAllCheckboxValue("pur_id");
	
	if(PurposeRPC.savePurposeSort(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
		reloadPurposeList();	
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}