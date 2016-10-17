var CollectionDataRPC = jsonrpc.CollectionDataRPC;
var RuleCategoryBean = new Bean("com.deya.wcm.dataCollection.bean.RuleCategoryBean", true);

var con_m = new Map();
var beanList = null;
var table = new Table();
table.table_name = "rule_category_table_list";
var tp = new TurnPage();

function loadRuleCategoryTable()
{
	showList();
	showTurnPage();
}

function initTable()
{
	var colsMap = new Map();	
	var colsList = new List();
	
//	colsList.add(setTitleClos("id","ID","20px","","",""));
	colsList.add(setTitleClos("rcat_name","分类名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("actionCol","操作","120px","","",""));
	colsList.add(setTitleClos("rcat_memo","分类描述","120px","","",""));
	colsList.add(setTitleClos("sort_col","排序","120px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);	
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList()
{
	beanList = CollectionDataRPC.getRuleCateList(id, con_m);
	beanList =  List.toJSList(beanList);
	tp.total = beanList.size();		
	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	
	
	table.getCol("rcat_name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:top.OpenModalWindow(\'新建采集规则分类\',\'/sys/dataCollection/ruleCategoryAdd.jsp?type=update&id='+beanList.get(i-1).id+'\',450,230)">'+beanList.get(i-1).rcat_name+'</a>');
		}
	});

	table.getCol("actionCol").each(function(i){	
		if(i>0)
		{
			$(this).html('<a href="javascript:selectSiteUser('+beanList.get(i-1).rcat_id+')">关联用户</a>');
		}
	});
	
	table.getCol("sort_col").each(function(i){	
		if(i>0)
		{
			$(this).html(getSortColStr());
		}
	});
	
	Init_InfoTable(table.table_name);
}

function showTurnPage()
{
	tp.show("turn","simple");
	tp.onPageChange = showList;
}

/********** 关联用户 开始 **********/
var temp_rcat_id = 0;
function selectSiteUser(rcat_id)
{
	temp_rcat_id = rcat_id;
	openSelectSiteUserPage("选择用户","insertRuleReleUserByCat",site_id)
}

var current_user_ids = "";
var current_group_ids = "";
//得到已选过的用户ID
function getSelectedUserIDS()
{
	current_user_ids = "";
	current_group_ids = "";
	var l = CollectionDataRPC.getRuleCatReleUserListByCat(temp_rcat_id,site_id);
	l = List.toJSList(l);
	if(l != null && l.size() > 0)
	{
		for(var i=0;i<l.size();i++)
		{
			if(l.get(i).priv_type == 0)
			{
				current_user_ids += ","+l.get(i).prv_id;				
			}else
			{
				current_group_ids += ","+l.get(i).prv_id;		
			}
		}

		if(current_user_ids != "" && current_user_ids != null)
			current_user_ids = current_user_ids.substring(1);

		if(current_group_ids != "" && current_group_ids != null)
			current_group_ids = current_group_ids.substring(1);
	}
	return current_user_ids;
}
//得到已选过的组ID
function getSelectedGroupIDS()
{
	return current_group_ids;
}


function insertRuleReleUserByCat(user_ids,group_ids)
{	
	if(CollectionDataRPC.insertRuleCatReleUserByCat(temp_rcat_id,user_ids,group_ids,app_id,site_id))
	{
		top.msgAlert("采集规则分类用户关联"+WCMLang.Add_success);
	}else
	{
		top.msgWargin("采集规则分类用户关联"+WCMLang.Add_fail);
	}
}
/********** 关联用户 结束 **********/

// 新建button事件
function openAddPage()
{
	top.OpenModalWindow("新建采集规则分类","/sys/dataCollection/ruleCategoryAdd.jsp?type=add&id="+id,450,230);
}

// 修改button事件
function openUpdatePage()
{
	var selectID = table.getSelecteCheckboxValue("id");
	top.OpenModalWindow("新建采集规则分类","/sys/dataCollection/ruleCategoryAdd.jsp?type=update&id="+selectID,450,230);
}

// 保存排序button事件
function saveSort()
{
	var ids = table.getAllCheckboxValue("id");;
	if(CollectionDataRPC.saveSort(ids))
	{
		top.msgAlert(WCMLang.Sort_success);
		showRuleCateTree();
	}
	else
	{
		top.msgWargin(WCMLang.Sort_fail);
	}
}

// 删除信息分类
function deleteRuleCategory()
{
	var ids = table.getSelecteCheckboxValue("rcat_id");
	con_m.put("id", ids);
	con_m.put("site_id", site_id);
	if(CollectionDataRPC.deleteRuleCategory(con_m))
	{
		top.msgAlert("信息分类"+WCMLang.Delete_success);
		deleteTreeNode(ids+"");
		loadRuleCategoryTable();
	}
	else
	{
		top.msgWargin("信息分类"+WCMLang.Delete_fail);
		showRuleCateTree();
	}
	con_m.remove("id");
}

// 修改左侧树
function updateRuleCateTree(id, name)
{
	updateTreeNode(id,name);
}

/******************************添加修改采集规则分类操作*************************************/

function saveAddRuleCategory()
{
	if(!checkAddPage())
	{
		return;
	}
	var addBean = BeanUtil.getCopy(RuleCategoryBean);
	$("#ruleCate_table").autoBind(addBean);
	
	addBean.site_id = top.getCurrentFrameObj().site_id;
	addBean.app_id = top.getCurrentFrameObj().app_id;
	addBean.parent_id = id;//top.getCurrentFrameObj().rcat_id;

	if(CollectionDataRPC.insertRuleCate(addBean))
	{
		top.msgAlert("采集规则分类"+WCMLang.Add_success);
		top.getCurrentFrameObj().showRuleCateTree();
		top.getCurrentFrameObj().loadRuleCategoryTable();
		top.CloseModalWindow();
	}
	else
	{
		top.msgWargin("采集规则分类"+WCMLang.Add_fail);
	}
}

function saveUpdateRuleCategory()
{
	if(!checkAddPage())
	{
		return;
	}
	var updateBean = BeanUtil.getCopy(defaultBean);
	$("#ruleCate_table").autoBind(updateBean);
	if(CollectionDataRPC.updateWareCate(updateBean))
	{
		top.msgAlert("采集规则分类"+WCMLang.Set_success);
		top.getCurrentFrameObj().updateRuleCateTree(updateBean.id, updateBean.wcat_name);
		top.getCurrentFrameObj().loadRuleCategoryTable();
		top.CloseModalWindow();
	}
	else
	{
		top.msgWargin("采集规则分类"+WCMLang.Set_fail);
	}
}

function checkAddPage()
{
	val.check_result = true;
	$("#wcat_name").blur();
	$("#wcat_memo").blur();
	
	if(!val.getResult()){		
		return false;
	}
	return true;
}

