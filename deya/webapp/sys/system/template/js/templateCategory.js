var TemplateRPC = jsonrpc.TemplateRPC;
var TemplateCategoryBean = new Bean("com.deya.wcm.bean.system.template.TemplateCategoryBean",true);
var SiteDomainRPC = jsonrpc.SiteDomainRPC;

var tc_selectBean = null;//当前选中项对象
var val=new Validator();
var tc_serarch_con = "";//查询条件
var tc_tp = new TurnPage();
var tc_beanList = null;
var tc_table = new Table();	
tc_table.table_name = "tamplateCategory_table";
var tf_beanList = null;
var tf_table = new Table();	
tf_table.table_name = "file_table";

function reloadTemplateCategoryDataList()
{
	showTemplateCategoryTurnPage();
	showTemplateCategoryList();	
}
/************************* 模板资源文件处理 开始 ****************************************/
var json_data;
//current_folder 在uploadtools目录中定义
function initTemplateFolder()
{
	json_data = eval(TemplateRPC.getFolderJSONStr(site_id));
	setLeftMenuTreeJsonData(json_data);
	$('#leftMenuTree').tree({
		//url: 'data/tree_data_tongji.json',
		//url: jsonData,
		onClick:function(node){
			if(node.attributes!=undefined){
				current_folder = node.attributes.url; 
				showTemplateFileList();	
				showTemplateFileTurnPage();
            }  
		}
	});
	$('#leftMenuTree div[node-id="0"]').click();
}

function initTemplateFileTable(){
	var colsMap = new Map();
	var colsList = new List();	
	colsList.add(setTitleClos("file_name","文件名","200px","","",""));	
	colsList.add(setTitleClos("file_type","文件类型","120px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("file_size","文件大小","100px","","",""));
	colsList.add(setTitleClos("actionCol","操作","70px","","",""));
	colsList.add(setTitleClos("span_col","&#160;","","","",""));
	tf_table.checkBox = false;
	tf_table.setColsList(colsList);
	tf_table.setAllColsList(colsList);				
	tf_table.enableSort=false;//禁用表头排序
	tf_table.onSortChange = showTemplateFileList;
	tf_table.show("file_table");//里面参数为外层div的id
}

function showTemplateFileTurnPage(){					
	tc_tp.show("file_turn","simple");	
}

function showTemplateFileList(){
	tf_beanList = TemplateRPC.getResourcesListBySiteID(current_folder);//第一个参数为站点ID，暂时默认为空	
	tf_beanList = List.toJSList(tf_beanList);//把list转成JS的List对象	
	curr_bean = null;		
	tf_table.setBeanList(tf_beanList,"td_list");//设置列表内容的样式
	tf_table.show("file_table");
	tc_tp.total = tf_beanList.size();	
	tf_table.getCol("file_name").each(function(i){	
		if(i>0)
		{	
			if(tf_beanList.get(i-1).file_name.indexOf(".js") > -1 || tf_beanList.get(i-1).file_name.indexOf(".css") > -1)
			{
				var p = tf_beanList.get(i-1).file_path;
				if(p.indexOf("\\") > -1)
				{
					p = p.replace(/\\/ig,"\\\\\\\\");
				}
				$(this).html('<a href="javascript:addTab(true,\'/sys/system/template/resouFile_update.jsp?tid='+tc_id+'&site_id='+site_id+'&path='+p+'\',\'修改资源\')">'+tf_beanList.get(i-1).file_name+'</a>');
			}
			else
			{
				var p = tf_beanList.get(i-1).file_path;
				if(p.indexOf("\\") > -1)
				{
					p = p.replace(/\\/ig,"/");					
				}
				p = p.substring(p.indexOf("/ROOT")+5);
				$(this).html('<a target="_blank" href="http://'+SiteDomainRPC.getDefaultSiteDomainBySiteID(site_id)+p+'">'+tf_beanList.get(i-1).file_name+'</a>');
			}
		}
	});

	tf_table.getCol("file_size").each(function(i){	
		if(i>0)
		{			
			$(this).css({"text-align":"right"});	
		}
	});

	tf_table.getCol("actionCol").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{	
			var p = tf_beanList.get(i-1).file_path;
			if(p.indexOf("\\") > -1)
			{
				p = p.replace(/\\/ig,"\\\\\\\\\\\\\\\\");
			}
			$(this).html('<a href="javascript:deleteTemplateResources(\''+p+'\')" >删除&#160;</a>');
		}			
	});	
	Init_InfoTable(tf_table.table_name);
}

//添加目录
function openTemplateResourcesFolder()
{
	OpenModalWindow("新建目录","/sys/system/template/add_folder.jsp",350,120);
}

function addTemplateResourcesFolder(folder_name)
{
	var path = "";
	if(current_folder.indexOf("\\") > -1)
	{
		path = current_folder+"\\\\"+folder_name;
	}else
		path = current_folder+"/"+folder_name;

	if(TemplateRPC.addTemplateResourcesFolder(path))
	{
		msgAlert("资源目录"+WCMLang.Add_success);
		insertTreeNode(eval('[{"id":'+getRandom()+',"text":\"'+folder_name+'\","attributes":{\"url\":\"'+path+'\"}}]'));
	}
	else
	{
		msgWargin("资源目录"+WCMLang.Add_fail);
	}

}

function deleteTemplateResources(file_path)
{
	msgConfirm(WCMLang.Delete_confirm,"deleteTemplateResourcesHandl('"+file_path+"')");
}

function deleteTemplateResourcesHandl(file_path)
{
	if(TemplateRPC.deleteTemplateResources(file_path,site_id))
	{
		msgAlert("资源文件"+WCMLang.Delete_success);
		showTemplateFileList();
		showTemplateFileTurnPage();
	}
	else
	{
		msgWargin("资源文件"+WCMLang.Delete_fail);
	}
}

function updateResourcesFile()
{
	if(TemplateRPC.updateResourcesFile(path,$("#f_content").val(),site_id))
	{
		msgAlert("资源文件"+WCMLang.Add_success);
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgAlert("资源文件"+WCMLang.Add_fail);
		return;
	}
}

function closeResouPage()
{
	window.location.href = "templateCategoryList.jsp?tid="+tid+"&site_id="+site_id+"&labNum=3";
}
/************************* 模板资源文件处理 结束 ****************************************/
function initTemplateCategoryTable(){
	var colsMap = new Map();
	var colsList = new List();	
	colsList.add(setTitleClos("tcat_id","ID","50px","","",""));	
	//colsList.add(setTitleClos("tcat_ename","模板目录英文名","120px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("tcat_cname","模板目录中文名","120px","","",""));
	//colsList.add(setTitleClos("tcat_position","模板目录路径","260px","","",""));
	colsList.add(setTitleClos("actionCol","操作","120px","","",""));
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("span_col","&#160;","","","",""));
	
	tc_table.setColsList(colsList);
	tc_table.setAllColsList(colsList);				
	tc_table.enableSort=false;//禁用表头排序
	tc_table.onSortChange = showTemplateCategoryList;
	tc_table.show("templateCategory_table");//里面参数为外层div的id
}

function showTemplateCategoryList(){	
	var sortCol = tc_table.sortCol;
	var sortType = tc_table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "tcat_id";
		sortType = "desc";
	}
	var m = new Map();
	//m.put("app_id", app);
	m.put("site_id", site_id);
	m.put("start_num", tc_tp.getStart());	
	m.put("page_size", tc_tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	m.put("parent_id", parent_id);
	if(tc_table.con_value.trim() != "" && tc_table.con_value != null)
	{
		m.put("con_name", tc_table.con_name);
		m.put("con_value", tc_table.con_value);
	}
	tc_beanList = TemplateRPC.getTemplateCategoryList(m);//第一个参数为站点ID，暂时默认为空	
	tc_beanList = List.toJSList(tc_beanList);//把list转成JS的List对象	
	curr_bean = null;		
	tc_table.setBeanList(tc_beanList,"td_list");//设置列表内容的样式
	tc_table.show("templateCategory_table");
	
	tc_table.getCol("tcat_cname").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{			
			$(this).html('<a href="javascript:window.location.href = \'/sys/system/template/templateCategory_add.jsp?tcat_id='+tc_beanList.get(i-1).tcat_id+'&app='+tc_beanList.get(i-1).app_id+'&site_id='+tc_beanList.get(i-1).site_id+'&pid='+tc_beanList.get(i-1).parent_id+'\'">'+tc_beanList.get(i-1).tcat_cname+'</a>');
		}
	});
	tc_table.getCol("tcat_ename").each(function(i){
		$(this).css({"text-align":"left"});	
	});
	tc_table.getCol("tcat_position").each(function(i){
		$(this).css({"text-align":"left"});	
	});
	tc_table.getCol("tcat_memo").each(function(i){
		if(i>0)
		{			
			var tm = tc_beanList.get(i-1).tcat_memo;
			if(tm == null || tm == "null" || tm == "NULL"){
				$(this).html("");
			}
		}
	});
	
	tc_table.getCol("actionCol").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{	
			$(this).html('<span onclick="deleteOneTemplateCategoryData(\''+tc_beanList.get(i-1).tcat_id+'\',\''+tc_beanList.get(i-1).site_id+'\')" style="cursor:pointer;">删除&#160;</span>');
		}			
	});	

	tc_table.getCol("sort_col").each(function(i){	
		if(i>0)
		{
			$(this).html(getSortColStr());
		}
	});
	
	Init_InfoTable(tc_table.table_name);
}

//添加树节点
function insertTemplateCategoryTree(id,name)
{
	insertTreeNode(eval('[{"id":'+id+',"iconCls":"tree-file icon-templateFolder", "text":\"'+name+'\","attributes":{"url":\"/sys/system/template/templateCategoryList.jsp?tid='+id+'\"}}]'));
}
//修改树节点
function updateTemplateCategoryTree(id,dept_name)
{
	updateTreeNode(id,dept_name);
}

//删除树节点
function deleteTemplateCategoryTree(ids)
{
	deleteTreeNode(ids);
}

function showTemplateCategoryTurnPage(){	
	var m = new Map();
	//m.put("app_id", app);
	m.put("site_id", site_id);
	m.put("parent_id", parent_id);
	if(tc_table.con_value.trim() != "" && tc_table.con_value != null){
		m.put("con_name", tc_table.con_name);
		m.put("con_value", tc_table.con_value);
		tc_tp.total = TemplateRPC.getTemplateCategoryCount(m);	
	}else{
		tc_tp.total = TemplateRPC.getTemplateCategoryCount(m);	
	}	
			
	tc_tp.show("templateCategory_turn","simple");	
	tc_tp.onPageChange = showTemplateCategoryList;
}

//打开查看窗口
function openViewTemplateCategoryDataPage()
{	
	window.location.href = "/sys/system/template/templateCategory_view.jsp?pid="+parent_id;
}

//打开添加窗口
function openAddTemplateCategoryPage()
{
	window.location.href = "/sys/system/template/templateCategory_add.jsp?app="+app+"&site_id="+site_id+"&pid="+parent_id;
}

//打开修改窗口
function openUpdateTemplateCategoryDataPage()
{
	var selectIDS = tc_table.getSelecteCheckboxValue("tcat_id");
	window.location.href = "/sys/system/template/templateCategory_add.jsp?tcat_id="+selectIDS+"&app="+app+"&site_id="+site_id+"&pid="+parent_id;
}

//添加Template
function addTemplateCategoryData()
{
	var bean = BeanUtil.getCopy(TemplateCategoryBean);	
	$("#templateCategory_table").autoBind(bean);

	if(!standard_checkInputInfo("templateCategory_table"))
	{
		return;
	}

	bean.tcat_id = TemplateRPC.getNewID();
	bean.id = bean.tcat_id;

	if(TemplateRPC.addTemplateCategory(bean))
	{
		msgAlert("模板"+WCMLang.Add_success);
		insertTemplateCategoryTree(bean.tcat_id,bean.tcat_cname);	
		//CloseModalWindow();
		//getCurrentFrameObj().reloadTemplateDataList();
		window.location.href = "/sys/system/template/templateCategoryList.jsp?tid="+bean.parent_id+"&labNum=2&site_id="+bean.site_id;
	}
	else
	{
		msgWargin("模板"+WCMLang.Add_fail);
	}
}
//修改Template
function updateTemplateCategoryData()
{
	var bean = BeanUtil.getCopy(TemplateCategoryBean);	
	$("#templateCategory_table").autoBind(bean);

	if(!standard_checkInputInfo("templateCategory_table"))
	{
		return;
	}

	if(TemplateRPC.updateTemplateCategoryById(bean))
	{
		msgAlert("模板"+WCMLang.Add_success);
		updateTemplateCategoryTree(bean.tcat_id,bean.tcat_cname);
		//CloseModalWindow();
		//getCurrentFrameObj().reloadTemplateDataList();
		window.location.href = "/sys/system/template/templateCategoryList.jsp?tid="+bean.parent_id+"&labNum=2&site_id="+bean.site_id;
	}
	else
	{
		msgWargin("模板"+WCMLang.Add_fail);
	}
}

//删除Template
function deleteTemplateCategoryData()
{
	var selectIDS = tc_table.getSelecteCheckboxValue("tcat_id");
	if(selectIDS == 1)
	{
		msgWargin("专题目录为系统目录，不能进行删除操作");
		return;
	}	

	if(TemplateRPC.delTemplateCategoryById(selectIDS,site_id))
	{
		msgAlert("模板"+WCMLang.Delete_success);
		//CloseModalWindow();
		deleteTemplateCategoryTree(selectIDS)
		getCurrentFrameObj().reloadTemplateCategoryDataList();
		//window.location.href = "/sys/system/template/templateCategoryList.jsp?tid="+parent_id+"&labNum=2&site_id="+bean.site_id;
	}else
	{
		msgWargin("模板"+WCMLang.Delete_fail);
	}
}

function deleteOneTemplateCategoryData(id,siteid){
	msgConfirm(WCMLang.Delete_confirm,"deleteOneTemplateCategoryInfo('"+id+"','"+siteid+"')");
}

function deleteOneTemplateCategoryInfo(id, siteid)
{
	var selectIDS = tc_table.getSelecteCheckboxValue("tcat_id");
	if(selectIDS == 1)
	{
		msgWargin("专题目录为系统目录，不能进行删除操作");
		return;
	}
	if(TemplateRPC.delTemplateCategoryById(id,siteid))
	{
		msgAlert("模板"+WCMLang.Delete_success);
		//CloseModalWindow();
		deleteTemplateCategoryTree(id)
		getCurrentFrameObj().reloadTemplateCategoryDataList();
		//window.location.href = "/sys/system/template/templateCategoryList.jsp?tid="+parent_id+"&labNum=2&site_id="+bean.site_id;
	}else
	{
		msgWargin("模板"+WCMLang.Delete_fail);
	}
}


function closeTemplateCategoryPage(){
	//CloseModalWindow();
	var bean = BeanUtil.getCopy(TemplateCategoryBean);	
	$("#templateCategory_table").autoBind(bean);
	window.location.href = "/sys/system/template/templateCategoryList.jsp?tid="+bean.parent_id+"&labNum=2&site_id="+bean.site_id;
}



function showTcBean(bean){
	alert("t_id="+bean.t_id);
	alert("tcat_id="+bean.tcat_id);
	alert("t_ename="+bean.t_ename);
	alert("t_cname="+bean.t_cname);
	alert("t_path="+bean.t_path);
	alert("t_content="+	bean.t_content);	
	alert("t_ver="+bean.t_ver);
	alert("creat_user="+bean.creat_user);
	alert("creat_dtime="+bean.creat_dtime);
	alert("modify_user="+bean.modify_user);
	alert("modify_dtime="+bean.modify_dtime);
	alert("app_id="+bean.app_id);
	alert("site_id="+bean.site_id);
}

function uploadReturnHandl()
{
	showTemplateFileList();
	showTemplateFileTurnPage();
}

function sortTemplateCategory()
{		
	var selectIDS = tc_table.getAllCheckboxValue("id");

	if(TemplateRPC.sortTemplateCategory(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
		reloadTemplateCategoryDataList();
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}