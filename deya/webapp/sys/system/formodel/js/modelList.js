var ModelRPC = jsonrpc.ModelRPC;
var ModelBean = new Bean("com.deya.wcm.bean.system.formodel.ModelBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "model_table";

//系统默认的内容模型
var sysMap = new Map();
sysMap.put("cs_info_article","cs_info_article");
sysMap.put("cs_info_pic","cs_info_pic");
sysMap.put("infoLink","infoLink");
sysMap.put("cs_info_video","cs_info_video");
sysMap.put("cs_gk_f_tygs","cs_gk_f_tygs");
sysMap.put("cs_gk_f_flgw","cs_gk_f_flgw");
sysMap.put("cs_gk_f_ldcy","cs_gk_f_ldcy");
sysMap.put("cs_gk_f_xsjg","cs_gk_f_xsjg");
sysMap.put("cs_gk_f_jggk","cs_gk_f_jggk");
sysMap.put("cs_gk_f_xzzf","cs_gk_f_xzzf");
sysMap.put("cs_gk_f_bszn","cs_gk_f_bszn");
sysMap.put("cs_info_video","cs_info_video");
sysMap.put("cs_info_pic","cs_info_pic");


function reloadModelList()
{
	showList();	
	showTurnPage();	
}

function locationModel()
{
	window.location.href = "modelList.jsp";
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();		
	
	colsList.add(setTitleClos("model_name","中文名称","200px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	//colsList.add(setTitleClos("action_Col","操作","","","",""));
	colsList.add(setTitleClos("model_ename","英文名称","100px","","",""));
	colsList.add(setTitleClos("table_name","对应表名","100px","","",""));
	colsList.add(setTitleClos("app_id","所属应用","80px","","",""));	
	colsList.add(setTitleClos("disabled","模型状态","80px","","",""));
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("action","操作","120px","","",""));
	colsList.add(setTitleClos("blank_col","&#160;","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){		

	beanList = ModelRPC.getModelList();//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	/*
	table.getCol("model_name").each(function(i){
		if(i>0)
		{			
			$(this).html('<a target="_self" href="/sys/system/formodel/model_add.jsp?model_id='+beanList.get(i-1).model_id+'">'+beanList.get(i-1).model_name+'</a>');			
		}
	});*/	

	table.getCol("action_Col").each(function(i){
		if(i>0)
		{			
			$(this).html('<a href="javascript:addTab(true,\'/sys/system/formodel/fieldList.jsp?model_id='+beanList.get(i-1).model_id+'\',\'内容模型字段\')">字段修改</a>');
		}
	});	
	
	table.getCol("action").each(function(i){
		if(i>0)
		{	
			//alert(beanList.get(i-1).model_type);
			if(beanList.get(i-1).model_type == "0"){
				$(this).html("系统默认");
			}else if(beanList.get(i-1).model_type == "1"){
				$(this).html('<a href="javascript:addTab(true,\'/sys/model/form/form_list.jsp?model_id='+beanList.get(i-1).model_id+'\',\'内容模型字段\')">字段修改</a>');
			}else if(beanList.get(i-1).model_type == "2"){
				//$(this).html("第三方");
				$(this).html('<a href="javascript:addTab(true,\'/sys/model/form/form_list3.jsp?model_id='+beanList.get(i-1).model_id+'\',\'内容模型字段\')">资源库字段设置</a>');
			}
		}
	});

	table.getCol("app_id").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).app_id == "cms")
				$(this).text("内容管理");
			if(beanList.get(i-1).app_id == "infodir")
				$(this).text("信息目录");
			if(beanList.get(i-1).app_id == "zwgk")
				$(this).text("信息公开");
		}
	});	

	table.getCol("disabled").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).disabled == "0")
				$(this).text("启用");
			else
				$(this).text("停用");
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

function showTurnPage(){					
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

//打开查看窗口
function openViewmodelPage(r_id)
{	
	window.location.href = "/sys/system/formodel/model_view.jsp?model_id="+r_id;
}

//打开添加窗口
function openAddModelPage()
{
	window.location.href = "/sys/system/formodel/model_add.jsp";
}

//打开修改窗口
function openUpdateModelPage()
{
	var selectIDS = table.getSelecteCheckboxValue("model_id");
	window.location.href = "/sys/system/formodel/model_add.jsp?model_id="+selectIDS;
}

//添加内容模型
function addModel()
{
	var bean = BeanUtil.getCopy(ModelBean);	
	$("#model_table").autoBind(bean);

	bean.app_id = $("#app_id").val();
    if(bean.app_id=='zwgk'){
    	bean.add_page="m_gkarticle_custom.jsp";
	}
	bean.model_type="1";
	if(!standard_checkInputInfo("model_table"))
	{
		return;
	}

	if(ModelRPC.insertModel(bean))
	{
		msgAlert("内容模型信息"+WCMLang.Add_success);
		locationModel();
	}
	else
	{
		msgWargin("内容模型信息"+WCMLang.Add_fail);
	}
}
//修改内容模型
function updateModel()
{
	var bean = BeanUtil.getCopy(ModelBean);	
	$("#model_table").autoBind(bean);
	bean.app_id = $("#app_id").val();
    if(bean.app_id=='zwgk'){
    	bean.add_page="m_gkarticle_custom.jsp";
	}
	
	//alert(bean.app_id);
	if(!standard_checkInputInfo("model_table"))
	{
		return;
	}
  
	if(ModelRPC.updateModel(bean))
	{
		msgAlert("内容模型信息"+WCMLang.Add_success);
		locationModel();
	}
	else
	{
		msgWargin("内容模型信息"+WCMLang.Add_fail);
	}
}

//保存排序
function saveModelSort()
{
	var model_ids = table.getAllCheckboxValue("model_id");
	if(model_ids != "" && model_ids != null)
	{
		if(ModelRPC.saveModelSort(model_ids))
		{
			msgAlert(WCMLang.Sort_success);
			reloadModelList();
		}
		else
		{
			msgWargin(WCMLang.Sort_fail);
			return;
		}
	}
}

function updateModelDisabled(disabled_flag)
{
	var selectIDS = table.getSelecteCheckboxValue("model_id");
	if(ModelRPC.updateModelDisabled(selectIDS,disabled_flag))
	{
		msgAlert("模型状态"+WCMLang.Set_success);
		reloadModelList();
	}else
	{
		msgWargin("模型状态"+WCMLang.Set_fail);
		return;
	}
}

//隐藏事件，点击6次后显示控制按钮
var count = 0;
function clickTurnEvent()
{
	count +=1;
	if(count > 7)
	{
		$(".system").show();
	}
	
}

//删除
function deleteInfo()
{
	var selectIDS = table.getSelecteCheckboxValue("model_id");
	//alert(selectIDS);
	if(jsonrpc.ModelRPC.deleteModel(selectIDS))
	//if(true)
	{
		msgAlert("信息"+WCMLang.Delete_success);
		reloadModelList();
	}else
	{
		msgWargin("信息"+WCMLang.Delete_fail);
	}
}