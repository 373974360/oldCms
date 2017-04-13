var CategoryRPC = jsonrpc.CategoryRPC;
var GKNodeRPC = jsonrpc.GKNodeRPC;
var DeptRPC = jsonrpc.DeptRPC;
var GKNodeBean = new Bean("com.deya.wcm.bean.zwgk.node.GKNodeBean",true);
var CategoryModel = new Bean("com.deya.wcm.bean.cms.category.CategoryModel",true);

var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "menu_table_list";;
var con_m = new Map();



function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("node_name","节点名称","","","",""));
	colsList.add(setTitleClos("node_id","节点ID","70px","","",""));
	colsList.add(setTitleClos("dept_id","所属机构","80px","","",""));	
	colsList.add(setTitleClos("dept_code","机构编码","80px","","",""));
	colsList.add(setTitleClos("is_apply","依申请公开","80px","","",""));
	colsList.add(setTitleClos("node_status","状态","40px","","",""));
	colsList.add(setTitleClos("action_col","目录管理","70px","","",""));
	colsList.add(setTitleClos("sort_col","排序","80px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){			

	beanList = GKNodeRPC.getGKNodeListByCateID(nodcat_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();			
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	
	
	table.getCol("node_name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openUpdateNodePage(\''+beanList.get(i-1).node_id+'\')">'+beanList.get(i-1).node_name+'</a>');
		}
	});	
	
	table.getCol("node_status").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).node_status == 1)
				$(this).text("暂停");
			else
				$(this).text("正常");
		}
	});

	table.getCol("is_apply").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).is_apply == 1)
				$(this).text("参与");
			else
				$(this).text("");
		}
	});

	table.getCol("dept_id").each(function(i){	
		if(i>0)
		{			
			var dept_bean = DeptRPC.getDeptBeanByID(beanList.get(i-1).dept_id);
			if(dept_bean != null)
				$(this).text(dept_bean.dept_name);
			else
				$(this).text("");
		}
	});

	table.getCol("action_col").each(function(i){	
		if(i>0)
		{			
			$(this).html('<a href="javascript:openCategoryPage(\''+beanList.get(i-1).node_id+'\')">目录管理</a>');			
		}
	});

	table.getCol("sort_col").each(function(i){	
		if(i>0)
		{			
			$(this).html(getSortColStr());			
		}
	});
}

function showTurnPage(){	
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

//打开栏目修改页面
function openCategoryPage(node_id)
{	
	addTab(true,"/sys/cms/category/categoryList.jsp?cat_type=0&app_id="+app_id+"&site_id="+node_id,"修改公开节点目录");
}

//得到该角色下已有的用户ID
var select_role_user_ids = "";
function getSelectedUserIDS()
{
	var selectIDS = table.getSelecteCheckboxValue("node_id");
	select_role_user_ids = jsonrpc.SiteRPC.getUsersBySiteId(app_id,selectIDS);	
	return select_role_user_ids;
}

//保存管理员
function saveUser(user_ids)
{
	var insert_user_ids = removeAlreadyStr(user_ids,select_role_user_ids);//从ID中分析出新加的用户，已有的就不再加了
	var delete_user_ids = removeNotAlreadyStr(user_ids,select_role_user_ids);//从ID中分析出需要删除的用户

	var selectIDS = table.getSelecteCheckboxValue("node_id");
	if(jsonrpc.SiteRPC.insertSiteUserManager(app_id,selectIDS,insert_user_ids,delete_user_ids))
	{  
		msgAlert("节点管理员"+WCMLang.Add_success);
		table.unChekcbox();
	}
	else
	{
		msgWargin("节点管理员"+WCMLang.Add_fail);
	}
}

function openAddNodePage()
{
	addTab(true,"/sys/zwgk/node/gkNode_add.jsp?top_index="+curTabIndex+"&nodcat_id="+nodcat_id,"修改公开节点");
}

function openUpdateNodePage(nc_id)
{
	var selectIDS = "";
	if(nc_id != null && nc_id != "")
		selectIDS = nc_id;
	else
		selectIDS = table.getSelecteCheckboxValue("node_id");
	addTab(true,"/sys/zwgk/node/gkNode_add.jsp?top_index="+curTabIndex+"&node_id="+selectIDS,"修改公开节点");
}



function deleteNode()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	
	if(GKNodeRPC.deleteGKNode(selectIDS))
	{
		msgAlert("节点信息"+WCMLang.Delete_success);
		loadMenuTable();
	}else
	{
		msgWargin("节点信息"+WCMLang.Delete_fail);
	}
}

function sortNode()
{
	var selectIDS = table.getAllCheckboxValue("id");
	if(GKNodeRPC.sortGKNode(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}

//修改节点状态
function updateNodeStatus(status)
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	if(GKNodeRPC.updateGKNodeStatus(selectIDS,status))
	{
		msgAlert("节点状态"+WCMLang.Set_success);
		loadMenuTable();
	}else
	{
		msgWargin("节点状态"+WCMLang.Set_fail);
	}
}

//移动节点
function moveGKNode(nodcat_id)
{
	if(nodcat_id != "" && nodcat_id != null)
	{
		var selectIDS = table.getSelecteCheckboxValue("id");
		if(GKNodeRPC.moveGKNode(selectIDS,nodcat_id))
		{
			msgAlert(WCMLang.Move_success);
			loadMenuTable();
		}else
		{
			msgWargin(WCMLang.Move_fail);
		}
	}
}

//选择模板
function openTemplate(n1,n2)
{		
	openSelectTemplate(n1,n2,jsonrpc.SiteRPC.getSiteIDByAppID(app_id));
}

function saveTemplateReleModel()
{
	CategoryRPC.deleteCategoryModel("10,11,12",node_id) && CategoryRPC.insertCategoryModel(getTemplateReleModelList());	
}

//保存特殊栏目和模板的对应关系
function saveZWGKConfig()
{

	if(CategoryRPC.insertCategoryModel(getTemplateReleModelList())){		
			msgAlert(WCMLang.Add_success);
	}else{		
			msgAlert(WCMLang.Add_fail);
	}
}

function updateZWGKConfig()
{

	if(jsonrpc.SiteRPC.insertSiteReleApp($("#rela_site_id").val(),app_id)  && CategoryRPC.deleteCategoryModel("10,11,12",node_id) && CategoryRPC.insertCategoryModel(getTemplateReleModelList()) && CategoryRPC.updateBaseCategoryTemplate($("#template_list").val()) && CategoryRPC.updateGKZNCateTemplate($("#gkzy_list_template_id").val(),$("#gkzn_list_template_id").val(),$("#gknb_list_template_id").val())){		
			msgAlert(WCMLang.Add_success);
	}else{		
			msgAlert(WCMLang.Add_fail);
	}
}

function getTemplateReleModelList()
{
	var list = new List();
	if($("#gkzy_template_id").val() != "" && $("#gkzy_template_id").val() != null)
	{
		list.add(getCategoryModelBean(12,$("#gkzy_template_id").val()));
	}
	if($("#gkzn_template_id").val() != "" && $("#gkzn_template_id").val() != null)
	{
		list.add(getCategoryModelBean(10,$("#gkzn_template_id").val()));
	}
	if($("#gknb_template_id").val() != "" && $("#gknb_template_id").val() != null)
	{
		list.add(getCategoryModelBean(11,$("#gknb_template_id").val()));
	}
	return list;
}

//获取关联对象
function getCategoryModelBean(cat_id,template_content)
{
	var bean = BeanUtil.getCopy(CategoryModel);	
	bean.cat_id = cat_id;
	bean.app_id = app_id;
	bean.site_id = node_id;
	bean.model_id = 11;			
	bean.template_content = template_content;
	return bean;
}
//初始得到公开指引等模板ID
function initZWGKConfig()
{	
	var gkzy_template_id = CategoryRPC.getTemplateID(12,node_id,11);
	if(gkzy_template_id != "" && gkzy_template_id != null && gkzy_template_id != "null")
	{
		$("#gkzy_template_id").val(gkzy_template_id);
		$("#gkzy_template_name").val(getTemplateNameByReleApp(gkzy_template_id));
	}

	var gkzn_template_id = CategoryRPC.getTemplateID(10,node_id,11);
	if(gkzn_template_id != "" && gkzn_template_id != null && gkzn_template_id != "null")
	{
		$("#gkzn_template_id").val(gkzn_template_id);
		$("#gkzn_template_name").val(getTemplateNameByReleApp(gkzn_template_id));
	}

	var gknb_template_id = CategoryRPC.getTemplateID(11,node_id,11);
	if(gknb_template_id != "" && gkzn_template_id != null && gknb_template_id != "null")
	{
		$("#gknb_template_id").val(gknb_template_id);
		$("#gknb_template_name").val(getTemplateNameByReleApp(gknb_template_id));
	}

	var gkzy_list_template_id = CategoryRPC.getCategoryBean(12).template_list;
	if(gkzy_list_template_id != "" && gkzy_list_template_id != null && gkzy_list_template_id != "0")
	{
		$("#gkzy_list_template_id").val(gkzy_list_template_id);
		$("#gkzy_list_template_name").val(getTemplateNameByReleApp(gkzy_list_template_id));
	}

	var gkzn_list_template_id = CategoryRPC.getCategoryBean(10).template_list;
	if(gkzn_list_template_id != "" && gkzn_list_template_id != null && gkzn_list_template_id != "0")
	{
		$("#gkzn_list_template_id").val(gkzn_list_template_id);
		$("#gkzn_list_template_name").val(getTemplateNameByReleApp(gkzn_list_template_id));
	}

	var gknb_list_template_id = CategoryRPC.getCategoryBean(11).template_list;
	if(gknb_list_template_id != "" && gkzn_list_template_id != null && gknb_list_template_id != "0")
	{
		$("#gknb_list_template_id").val(gknb_list_template_id);
		$("#gknb_list_template_name").val(getTemplateNameByReleApp(gknb_list_template_id));
	}

	var template_list = CategoryRPC.getBaseCategoryTemplateListID();
	if(template_list != "" && template_list != null && template_list != "null")
	{
		$("#template_list").val(template_list);
		$("#template_list_name").val(getTemplateNameByReleApp(template_list));
	}
}

function getRelaSite(ids,names)
{
	$("#rela_site_name").val(names);
	$("#rela_site_id").val(ids);
}