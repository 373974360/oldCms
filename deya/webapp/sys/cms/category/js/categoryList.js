var CategoryRPC = jsonrpc.CategoryRPC;
var OperateRPC = jsonrpc.OperateRPC;
var CategoryBean = new Bean("com.deya.wcm.bean.cms.category.CategoryBean",true);
var CategorySharedBean = new Bean("com.deya.wcm.bean.cms.category.CategorySharedBean",true);
var SyncBean = new Bean("com.deya.wcm.bean.cms.category.SyncBean",true);
var CategoryReleBean = new Bean("com.deya.wcm.bean.cms.category.CategoryReleBean",true);
var CategoryModel = new Bean("com.deya.wcm.bean.cms.category.CategoryModel",true);

var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "cat_table_list";;
var con_m = new Map();

function loadCategoryTable()
{	
	showList();
	showTurnPage();	
	Init_InfoTable(table.table_name);	
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("cat_id","ID","30px","","",""));
	colsList.add(setTitleClos("cat_cname","目录名称","200px","","",""));//英文名，显示名，宽，高，样式名，点击事件
	colsList.add(setTitleClos("cat_ename","英文名称","100px","","",""));
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("span_col","&#160;","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){
	if(cat_id == 0)
		beanList = CategoryRPC.getCategoryListBySiteID(site_id);//第一个参数为站点ID，暂时默认为空	
	else
		beanList = CategoryRPC.getChildCategoryList(cat_id,site_id);
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	tp.total = beanList.size();
	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	
	
	table.getCol("cat_cname").each(function(i){
		$(this).css("text-align","left");
		if(i>0)
		{
			$(this).html('<a href="javascript:openUpdateCategoryPage('+beanList.get(i-1).id+')">'+beanList.get(i-1).cat_cname+'</a>');
		}
	});		

	table.getCol("sort_col").each(function(i){	
		if(i>0)
		{
			$(this).html(getSortColStr());
		}
	});	
}

function openAddCategoryPage()
{
	addTab(true,"/sys/cms/category/category_add.jsp?top_index="+parent.curTabIndex+"&site_id="+site_id+"&app_id="+app_id+"&class_id="+class_id+"&parentID="+cat_id,"维护目录");
}

function openUpdateCategoryPage(cid)
{
	var id;
	if(cid != "" && cid != null)
		id = cid;
	else
		id = table.getSelecteCheckboxValue("id");
	
	if(cate_type == "share")
		addTab(true,"/sys/cms/category/category_share_add.jsp?app_id="+app_id+"&site_id="+site_id+"&class_id="+class_id+"&top_index="+parent.curTabIndex+"&id="+id,"维护目录");
	else
		addTab(true,"/sys/cms/category/category_add.jsp?app_id="+app_id+"&site_id="+site_id+"&class_id="+class_id+"&top_index="+parent.curTabIndex+"&id="+id,"维护目录");
}

function showTurnPage(){
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

//添加树节点
function insertCategoryTree(id,dept_name)
{
	insertTreeNode(eval('[{"id":'+id+',"text":\"'+dept_name+'\","attributes":{}}]'));	
}

//修改树节点
function updateCategoryTree(id,category_name)
{
	updateTreeNode(id,category_name);
}

function deleteCategory()
{
	var selectIDS = table.getSelecteCheckboxValue("cat_id");

	if(CategoryRPC.deleteCategory(selectIDS,site_id,app_id))
	{
		msgAlert("目录信息"+WCMLang.Delete_success);
		changeCategoryListTable(cat_id);
		deleteTreeNode(selectIDS);
	}else
	{
		msgWargin("目录信息"+WCMLang.Delete_fail);
	}
}

function sortCategory()
{
	var selectIDS = table.getAllCheckboxValue("id");
	if(CategoryRPC.sortCategory(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
		showCategoryTree();
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}

//得到权限信息Map
var opt_map = new Map();
function getOperateMap()
{
	opt_map = OperateRPC.getOptMap();
	opt_map = Map.toJSMap(opt_map);
}

function getOperateInfoByID(opt_id)
{
	if(opt_map.get(opt_id) != null)
		return opt_map.get(opt_id).opt_name;
	else
		return "";
}

//移动目录
function moveCategory(parent_id)
{
	if(parent_id != "" && parent_id != null)
	{
		var selectIDS = table.getSelecteCheckboxValue("cat_id");			
		if(CategoryRPC.moveCategory(parent_id,selectIDS,site_id)){
			msgAlert("目录"+WCMLang.Move_success);
			loadCategoryTable()
			showCategoryTree();
		}else
		{
			msgWargin("目录"+WCMLang.Move_fail);
			return;
		}
	}
}

function openSelectCategory(title,handl_name)
{
	OpenModalWindow(title,"/sys/cms/category/select_menu.jsp?handl_name="+handl_name,610,510);
}

function initCategoryTree()
{
	$('#leftMenuTree').tree({
		onClick:function(node){			 
			changeCategoryListTable(node.id);           
		}
	});

}

//点击树节点,修改目录列表数据
function changeCategoryListTable(c_id){
	cat_id = c_id;
	loadCategoryTable();
}

//关联分类方式类目
function releCateClass(c_id,c_name)
{
	$("#cat_class_name").val(c_name);
	$("#cat_class_id").val(c_id);
}

//拷贝基础目录
function copyBasisCategory(c_id,c_name)
{
	if(CategoryRPC.copyBasisCategory(cat_id,c_id,site_id))
	{
		msgAlert("目录"+WCMLang.Add_success);
		loadCategoryTable()
		showCategoryTree();
	}else
	{
		msgWargin("目录"+WCMLang.Add_fail);
		return;
	}
}

//拷贝共享目录
function copyShareCategory(c_id)
{
	if(CategoryRPC.copyShareCategory(cat_id,c_id,app_id,site_id))
	{
		msgAlert("目录"+WCMLang.Add_success);
		loadCategoryTable()
		showCategoryTree();
	}else
	{
		msgWargin("目录"+WCMLang.Add_fail);
		return;
	}
} 


function reloadTemplate()
{
	if(app_id == "cms")
	{
		$("#template_index_name").val(getTemplateName(defaultBean.template_index));
		$("#template_list_name").val(getTemplateName(defaultBean.template_list));
	}else
	{
		$("#template_index_name").val(getTemplateNameByReleApp(defaultBean.template_index));
		$("#template_list_name").val(getTemplateNameByReleApp(defaultBean.template_list));
	}
}

//列出内容模型
function setModelInfoList()
{
	var temp_site_id = "";
	if(app_id != "cms")
	{
		temp_site_id = jsonrpc.SiteRPC.getSiteIDByAppID(app_id);
	}
	else
		temp_site_id = site_id;

	if(app_id == "ggfw")
	{
		$("#model_select_table").append('<tr><td colspan="3">以下内容模型只关联模板，不添加此模型数据</td></tr>');		
		setModelInfoListHandl(jsonrpc.ModelRPC.getCANModelListByAppID("cms"),temp_site_id,"a");
		setModelInfoListHandl(jsonrpc.ModelRPC.getCANModelListByAppID("zwgk"),temp_site_id,"b");
	}else
	{
		setModelInfoListHandl(jsonrpc.ModelRPC.getCANModelListByAppID(app_id),temp_site_id,"a");	
		if(app_id == "cms"){
			$("#model_select_table").append('<tr><td colspan="3">以下内容模型为信息公开系统的，在此只关联模板，不添加此模型数据</td></tr>');
			if( site_id != "" && site_id != null)
				setModelInfoListHandl(jsonrpc.ModelRPC.getCANModelListByAppID("zwgk"),temp_site_id,"b");//站点下面选择公开的模板时，用自身的站点ID
			else
				setModelInfoListHandl(jsonrpc.ModelRPC.getCANModelListByAppID("zwgk"),jsonrpc.SiteRPC.getSiteIDByAppID("zwgk"),"b");
		}	
	}
}

function setModelInfoListHandl(list,temp_site_id,flag)
{
	var checked = "";
	list = List.toJSList(list);
	var str = "";
	if(list != null && list.size()> 0)
	{
		for(var i=0;i<list.size();i++)
		{
			checked = "";
			if(list.get(i).model_ename == "link" || list.get(i).model_ename == "article" || list.get(i).model_ename == "video" || list.get(i).model_ename == "pic")
				checked = 'checked="checked"';
			str += '<tr>';
			str += '<td style="text-align:center;"><input type="checkbox" id="model_id" name="model_id" value="'+list.get(i).model_id+'" '+checked+' onclick="choseModel(this)"></td>';
			str += '<td>'+list.get(i).model_name+'</td>';
			str += '<td>';
			if(list.get(i).model_ename != "link")
				str += '<input id="template_content_name_'+ flag + i+'" name="template_content_name" type="text" class="width200" value="" readOnly="readOnly"/><input type="hidden" id="template_content_'+ flag + i+'" name="template_id_hidden" value="0"/><input type="button" value="选择" onclick="openSelectTemplate(\'template_content_'+ flag + i+'\',\'template_content_name_'+ flag + i+'\',\''+temp_site_id+'\')"/>&#160;<input type="button" value="清空" onclick="clearTemplate(this)"/>';
			str += '</td>';
			str += '<td style="text-align:center;"><input type="checkbox" id="is_add" name="is_add" value="'+list.get(i).is_add+'" '+checked+'></td>';
			str += '</tr>';
		}
		$("#model_select_table").append(str);
	}	
	//'+list.get(i).template_show+'  '+getTemplateName(list.get(i).template_show)+'
}

//清空已选模板
function clearTemplate(obj)
{
	var p_obj = $(obj).parent();
	$(p_obj).find("input[name]").val("");	
	$(p_obj).find("input[name='template_id_hidden']").val("0");	
}

//得到选择的内容模型
function getSelectCategoryModelList(c_id)
{
	var list = new List();	
	$("#model_select_table :checked").each(function(){
		if($(this).attr("id") == "model_id")
		{
			var bean = BeanUtil.getCopy(CategoryModel);	
			bean.cat_id = c_id;
			bean.app_id = app_id;
			bean.site_id = site_id;
			bean.model_id = $(this).val();			
			bean.template_content = $(this).parent().parent().find("input[type='hidden']").val();
			if($(this).parent().parent().find("#is_add").is(':checked'))
			{
				bean.isAdd = 1;
			}
			else
			{
				bean.isAdd = 0;
			}
			if(bean.template_content == "" || bean.template_content == null)
				bean.template_content = 0;
			//alert(bean.template_content);
			list.add(bean);
		}
	});
	return list;
}

//选中已关联的内容模型
function checkedCategoryModelInfo(cat_id)
{
	$("#model_select_table :checked").removeAttr("checked");
	var cm_list = CategoryRPC.getCategoryReleModelList(cat_id,site_id);		
	if(cm_list != null)
	{
		cm_list = List.toJSList(cm_list);
		for(var i=0;i<cm_list.size();i++)
		{
			var c_obj = $("#model_select_table :checkbox[value='"+cm_list.get(i).model_id+"']");
			$(c_obj).attr("checked",true);
			if(cm_list.get(i).isAdd == 1)
			{
				$(c_obj).parent().parent().find("#is_add").attr("checked",true);
			}
			if(app_id != "cms")
				$(c_obj).parent().parent().find("input[name='template_content_name']").val(getTemplateNameByReleApp(cm_list.get(i).template_content));
			else
				$(c_obj).parent().parent().find("input[name='template_content_name']").val(getTemplateName(cm_list.get(i).template_content));
			$(c_obj).parent().parent().find(":hidden").val(cm_list.get(i).template_content);
		}
	}
}

//选择工作流事件，如果使用流程，放出终审后自动发布
function changeWorkFlowHandl(wf_id)
{
	if(wf_id == 0)
	{
		$("#wf_publish_tr").hide();
		$("#wf_publish_tr :radio").eq(1).click();
	}else
	{
		$("#wf_publish_tr").show();
	}
}


//在关联模板的时候，默认选中模板的时候，把是否添加也选中
function choseModel(obj)
{
	if($(obj).is(':checked'))
	{
		$(obj).parent().parent().find("#is_add").attr("checked",true);
	}
	else
	{
		$(obj).parent().parent().find("#is_add").attr("checked",false);
	}
}