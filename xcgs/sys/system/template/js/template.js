var TemplateRPC = jsonrpc.TemplateRPC;
var TemplateEditBean = new Bean("com.deya.wcm.bean.system.template.TemplateEditBean",true);
var WareRPC = jsonrpc.WareRPC;
var WareBean = new Bean("com.deya.wcm.bean.system.ware.WareBean", true);
var SnippetRPC = jsonrpc.SnippetRPC;
var SnippetBean = new Bean("com.deya.wcm.bean.template.snippet.SnippetBean",true);


var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "template_table";

function reloadTemplateDataList()
{
	showTurnPage();
	showList();	
}

function initTable(){
	var colsMap = new Map();
	var colsList = new List();	
	colsList.add(setTitleClos("t_id","ID","50px","","",""));	
	colsList.add(setTitleClos("t_cname","模板名称","200px","","textLeft",""));
	//colsList.add(setTitleClos("t_ename","模板英文名","120px","","textLeft",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("t_ver","版本号","60px","","",""));
	//colsList.add(setTitleClos("creat_user","创建人","60px","","",""));
	//colsList.add(setTitleClos("updown","上传/下载","100px","","",""));
	colsList.add(setTitleClos("actionCol","操作","140px","","",""));
	colsList.add(setTitleClos("creat_dtime","创建时间","120px","","",""));
	colsList.add(setTitleClos("blank_cell","&#160;","","","",""));
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("template_table");//里面参数为外层div的id
}

function showList(){	
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "t_id";
		sortType = "desc";
	}
	var m = new Map();
	//m.put("app_id", app);
	m.put("site_id", site_id);
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	m.put("tcat_id", tc_id);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}
	beanList = TemplateRPC.getTemplateEditList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("template_table");
	
	table.getCol("t_cname").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{			
			$(this).html('<a href="javascript:window.location.href = \'/sys/system/template/template_add.jsp?t_id='+beanList.get(i-1).t_id+'&app='+beanList.get(i-1).app_id+'&site_id='+beanList.get(i-1).site_id+'&tc_id='+beanList.get(i-1).tc_id+'\'">'+beanList.get(i-1).t_cname+'</a>');
		}
	});
	table.getCol("t_ename").each(function(i){
		$(this).css({"text-align":"left"});	
	});
	/*
    table.getCol("updown").each(function(i){
		if(i>0)
		{	
			$(this).css({"text-align":"center"});	
			var html = "";
		    // 2013-1-16ydc添加
			html += '<span onclick="downOneTemplate(\''+beanList.get(i-1).t_id+'\',\''+beanList.get(i-1).site_id+'\',\''+beanList.get(i-1).app_id+'\',\''+beanList.get(i-1).t_path+'\')" style="cursor:pointer;">下载&#160;</span>';
			// 2013-1-23ydc添加上传
			html += '<span onclick="openUploadPage(\''+beanList.get(i-1).t_id+'\',\''+beanList.get(i-1).site_id+'\',\''+beanList.get(i-1).t_cname+'\',\''+beanList.get(i-1).modify_dtime+'\',\''+beanList.get(i-1).t_ename+'\',\''+beanList.get(i-1).creat_dtime+'\',\''+beanList.get(i-1).id+'\',\''+beanList.get(i-1).modify_user+'\',\''+beanList.get(i-1).tcat_id+'\',\''+beanList.get(i-1).t_path+'\',\''+beanList.get(i-1).app_id+'\',\''+beanList.get(i-1).t_ver+'\',\''+beanList.get(i-1).creat_user+'\');" style="cursor:pointer;"> | 上传&#160;</span>';
			
			$(this).html(html);
		}			
	});
	*/
	table.getCol("actionCol").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{	
			$(this).css({"text-align":"left"});	
			var html = "";
			/*
			var flag = false;
			try{
				var verBean = TemplateRPC.getSimpleTemplateVerBean(beanList.get(i-1).t_id,beanList.get(i-1).t_ver,beanList.get(i-1).site_id);
				if(verBean != null && verBean.t_status == 1){
					flag = false;
				}else
					flag = true;
			}catch(e){}
			*/

			html += '<span onclick="deleteOneTemplateData(\''+beanList.get(i-1).t_id+'\',\''+beanList.get(i-1).site_id+'\')" style="cursor:pointer;">删除</span>&#160;&#160;&#160;';
			html += '<span onclick="openHistoryTemplateVer(\''+beanList.get(i-1).t_id+'\',\''+beanList.get(i-1).site_id+'\')" style="cursor:pointer;">历史版本</span>&#160;&#160;&#160;';
			if(beanList.get(i-1).t_status == 0){
				html += '<span onclick="publishOneTemplate(\''+beanList.get(i-1).t_id+'\',\''+beanList.get(i-1).site_id+'\',\''+beanList.get(i-1).app_id+'\')" style="cursor:pointer;">发布&#160;</span>';
			}                      
			$(this).html(html);
		}			
	});	
//openHistoryTemplateVer
	
	Init_InfoTable(table.table_name);
}
  
//打开上传窗口       2013-1-23ydc添加上传
 function openUploadPage(t_id,site_id,t_cname,modify_dtime,t_ename,creat_dtime,id,modify_user,tcat_id,t_path,app_id,t_ver,creat_user)
{ 
	var height = 210;
	var url ="/sys/system/template/upload.jsp?t_id="+t_id+"&site_id="+site_id+"&modify_dtime="+modify_dtime+"&t_ename="+t_ename+"&creat_dtime="+creat_dtime+"&id="+id+"&modify_user="+modify_user+"&tcat_id="+tcat_id+"&t_path="+t_path+"&t_cname="+t_cname+"&app_id="+app_id+"&t_ver="+t_ver+"&creat_user="+creat_user;
		url=encodeURI(url=encodeURI(url)); 
		 
	top.OpenModalWindow("上传模板",url,450,height);
} 
 
function showTurnPage(){	
	var m = new Map();
	//m.put("app_id", app);
	m.put("site_id", site_id);
	m.put("tcat_id", tc_id);
	if(table.con_value.trim() != "" && table.con_value != null){
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
		tp.total = TemplateRPC.getTemplateEditCount(m);	
	}else{
		tp.total = TemplateRPC.getTemplateEditCount(m);	
	}	
			
	tp.show("template_turn","");	
	tp.onPageChange = showList;
}

//历史回溯控制
function openHistoryTemplateVer(templateId,siteId){
	//alert(templateId+"|"+siteId);
	top.OpenModalWindow("模板历史版本","/sys/system/template/templateDataVerList.jsp?tc_id="+tc_id+"&t_id="+templateId+"&site_id="+siteId,1000,530);
	//window.location.href = "/sys/system/template/templateDataVerList.jsp?t_id="+templateId+"&site_id="+siteId;
}


//打开查看窗口
function openViewTemplateDataPage(t_id)
{	
	window.location.href = "/sys/system/template/template_view.jsp?t_id="+t_id+"&tc_id="+tc_id;
}

//打开添加窗口
function openAddTemplatePage()
{
	if(tc_id == 0)
	{
		top.msgWargin("请先选择模板分类");
		return;
	}
	window.location.href = "/sys/system/template/template_add.jsp?app="+app+"&site_id="+site_id+"&tc_id="+tc_id;
}

//打开修改窗口
function openUpdateTemplateDataPage()
{
	var selectIDS = table.getSelecteCheckboxValue("t_id");
	window.location.href = "/sys/system/template/template_add.jsp?t_id="+selectIDS+"&app="+app+"&site_id="+site_id+"&tc_id="+tc_id;
}

//添加Template
function addTemplateData()
{
	$("#t_content").val(editAreaLoader.getValue());
	var bean = BeanUtil.getCopy(TemplateEditBean);	
	$("#Template_table").autoBind(bean);
	bean.t_content = $("#t_content").val();
	bean.t_status = 0;
	if(!standard_checkInputInfo("Template_table"))
	{
		return;
	}
	if(TemplateRPC.addTemplateEdit(bean))
	{
		top.msgAlert("模板"+WCMLang.Add_success);			
		//top.CloseModalWindow();
		//top.getCurrentFrameObj().reloadTemplateDataList();
		window.location.href = "templateCategoryList.jsp?tid="+bean.tcat_id+"&site_id="+bean.site_id;
	}
	else
	{
		top.msgWargin("模板"+WCMLang.Add_fail);
	}
}
//修改Template
function updateTemplateData()
{
   
	$("#t_content").val(editAreaLoader.getValue());
	var bean = BeanUtil.getCopy(TemplateEditBean);	
	$("#Template_table").autoBind(bean);
	bean.t_content = $("#t_content").val();
	bean.t_status = 0;
	if(!standard_checkInputInfo("Template_table"))
	{
		return;
	}
	 
	if(TemplateRPC.updateTemplateEditById(bean))
	{ 
		top.msgAlert("模板"+WCMLang.Add_success);			
		//top.CloseModalWindow();
		//top.getCurrentFrameObj().reloadTemplateDataList();
		window.location.href = "/sys/system/template/templateCategoryList.jsp?tid="+bean.tcat_id+"&site_id="+bean.site_id;
	}
	else
	{ 
		top.msgWargin("模板"+WCMLang.Add_fail);
	}
}

//删除Template
function deleteTemplateData()
{
	top.msgConfirm(WCMLang.Delete_confirm,"deleteTemplateDataHandl()");
}

function deleteTemplateDataHandl()
{
	var selectIDS = table.getSelecteCheckboxValue("t_id");

	if(TemplateRPC.delTemplateEditById(selectIDS,site_id))
	{
		top.msgAlert("模板"+WCMLang.Delete_success);
		//top.CloseModalWindow();
		top.getCurrentFrameObj().reloadTemplateDataList();
		//window.location.href = "/sys/system/template/templateCategoryList.jsp?tid="+tc_id+"&site_id="+bean.site_id;
	}else
	{
		top.msgWargin("模板"+WCMLang.Delete_fail);
	}
}

function deleteOneTemplateData(id,site_id){
	top.msgConfirm(WCMLang.Delete_confirm,"deleteOneTemplateInfo('"+id+"','"+site_id+"')");
}

//删除Template
function deleteOneTemplateInfo(id,site_id)
{
	if(TemplateRPC.delTemplateEditById(id,site_id))
	{
		top.msgAlert("模板"+WCMLang.Delete_success);
		//top.CloseModalWindow();
		top.getCurrentFrameObj().reloadTemplateDataList();
		//window.location.href = "/sys/system/template/templateCategoryList.jsp?tid="+tc_id+"&site_id="+site_id;
	}else
	{
		top.msgWargin("模板"+WCMLang.Delete_fail);
	}
}

function closePage(){
	//top.CloseModalWindow();
	var bean = BeanUtil.getCopy(TemplateEditBean);	
	$("#Template_table").autoBind(bean);
	window.location.href = "/sys/system/template/templateCategoryList.jsp?tid="+bean.tcat_id+"&site_id="+bean.site_id;
}


function publishOneTemplate(tid, siteid, appid){
	if(TemplateRPC.publish(tid, siteid, appid)){
		top.msgAlert("模板"+WCMLang.Publish_success);
		top.getCurrentFrameObj().reloadTemplateDataList();
	}
}
//2013-0-16ydc添加
function downOneTemplate(tid, siteid, appid,tpath){
	window.location.href = "/sys/system/template/downInfoFile.jsp?tid="+tid+"&site_id="+siteid+"&appid="+appid+"&tpath="+tpath;
}
function publishTemplate(){
	var selectIDS = table.getSelecteCheckboxValue("t_id");
	if(TemplateRPC.publish(selectIDS, site_id, app)){
		top.msgAlert("模板"+WCMLang.Publish_success);
		top.getCurrentFrameObj().reloadTemplateDataList();
	}
}

function cancelTemplate(){
	var selectIDS = table.getSelecteCheckboxValue("t_id");
	if(TemplateRPC.cancel(selectIDS, site_id, app)){
		top.msgAlert("模板撤销成功");
		top.getCurrentFrameObj().reloadTemplateDataList();
	}
}


//搜索
function authorDataSearchHandl(obj)
{
	alert("do search Templates");
//	var con_value = $(obj).parent().find("#searchkey").val();
//	if(con_value.trim() == "" ||  con_value == null)
//	{
//		top.msgAlert(WCMLang.Search_empty);
//		return;
//	}
//	table.con_name = $(obj).parent().find("#searchFields").val(); 
//	table.con_value = con_value;
//	reloadMetaDataList();
}

function showTEBean(bean){
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



