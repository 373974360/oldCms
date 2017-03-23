var TemplateRPC = jsonrpc.TemplateRPC;
var TemplateVerBean = new Bean("com.deya.wcm.bean.system.template.TemplateVerBean",true);
var TemplateEditBean = new Bean("com.deya.wcm.bean.system.template.TemplateEditBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "table";

function reloadTemplateVerDataList()
{
	showTurnPage();
	showList();	
}

function initTable(){
	var colsMap = new Map();
	var colsList = new List();	
	colsList.add(setTitleClos("t_id","ID","50px","","",""));	
	colsList.add(setTitleClos("t_ename","模板英文名","120px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("t_cname","模板中文名","","","",""));
	colsList.add(setTitleClos("t_ver","版本号","60px","","",""));
	colsList.add(setTitleClos("t_status","发布状态","60px","","",""));
	colsList.add(setTitleClos("actionCol","操作","80px","","",""));
	//colsList.add(setTitleClos("creat_user","创建人","60px","","",""));
	colsList.add(setTitleClos("modify_dtime","创建时间","150px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){	
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "t_ver";
		sortType = "desc";
	}
	
	var m = new Map();
	//m.put("app_id", app);
	m.put("site_id", site_id);
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	m.put("t_id", t_id);
	beanList = TemplateRPC.getTemplateVerList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("t_cname").each(function(i){
		$(this).css({"text-align":"left"});
		if(i>0)
		{			
			//$(this).html('<a href="javascript:addTab(true,\'/sys/system/template/template_view.jsp?t_id='+beanList.get(i-1).t_id+'\',\'模板信息\')">'+beanList.get(i-1).t_cname+'</a>');
		}
	});
	table.getCol("t_ename").each(function(i){
		$(this).css({"text-align":"left"});	
	});
	
	table.getCol("t_status").each(function(i){
		if(i>0)
		{			
			//$(this).css({"text-align":"left"});	
			if(beanList.get(i-1).t_status == 1){
				$(this).html("<span style='font-color:red;'>已发布</span>");
			}else{
				$(this).html("未发布");
			}
		}
	});

	table.getCol("actionCol").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{	
			//$(this).parent().mouseover(changeListStyle);//该行的鼠标事件
			//$(this).parent().mouseout(changeListStyleU);
			$(this).html('<span onclick="recoveryTemplateVer(\''+beanList.get(i-1).t_id+'\',\''+beanList.get(i-1).site_id+'\',\''+beanList.get(i-1).t_ver+'\')" style="cursor:pointer;">恢复此版本&#160;</span>');
		}			
	});	
	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	var m = new Map();
	//m.put("app_id", app);
	m.put("site_id", site_id);
	m.put("t_id", t_id);
	if(table.con_value.trim() != "" && table.con_value != null){
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
		tp.total = TemplateRPC.getTemplateVerCount(m);	
	}else{
		tp.total = TemplateRPC.getTemplateVerCount(m);	
	}	
			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//历史版本恢复
function recoveryTemplateVer(templateId,siteId,ver){
	if(TemplateRPC.recoveryTemplateVer(templateId,siteId,ver))
	{
		msgAlert("模板恢复成功");
		CloseModalWindow();
		//window.location.reload();
		getCurrentFrameObj().reloadTemplateDataList();
		//window.location.href = "templateCategoryList.jsp?tid="+tc_id+"&site_id="+siteId;
	}
	else
	{
		msgWargin("模板恢复失败");
	}
}

function closePage(){
	CloseModalWindow();
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



