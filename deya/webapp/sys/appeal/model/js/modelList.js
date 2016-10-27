var SQModelRPC = jsonrpc.SQModelRPC;
var ModelBean = new Bean("com.deya.wcm.bean.appeal.model.ModelBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "model_table";
var m = new Map();

function reloadModelList()
{
	showTurnPage();
	showList();	
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("model_id","ID","40px","","",""));
	colsList.add(setTitleClos("model_cname","渠道名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("relevance_type","投递类型","80px","","",""));
	colsList.add(setTitleClos("is_sort","是否需要分拣","80px","","",""));
	colsList.add(setTitleClos("time_limit","办理时限","80px","","",""));
	colsList.add(setTitleClos("is_auto_publish","是否公开","80px","","",""));
	colsList.add(setTitleClos("actionCol","操作","140px","","",""));
	
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
		sortCol = "model_id";
		sortType = "desc";
	}	
	
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}

	beanList = SQModelRPC.getSQModelListForDB(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("actionCol").each(function(i){
		if(i>0)
		{			
			var html = "";
			html +='<span><a href="model_from.jsp?model_id='+beanList.get(i-1).model_id+'">扩展字段</a></span>&#160;&#160;&#160;&#160;';

			html +='<span><a href="addInfo.jsp?model_id='+beanList.get(i-1).model_id+'">信件录入</a></span>';

			$(this).html(html); 
		}		
	});
	table.getCol("model_cname").each(function(i){
		if(i>0)
		{			
			$(this).html('<a href="javascript:openUpdateModelPage('+beanList.get(i-1).model_id+')">'+beanList.get(i-1).model_cname+'</a>');
		}
		$(this).css("text-align","left");
	});	
	table.getCol("relevance_type").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).relevance_type == 0)
				$(this).html("部门");	
			else
				$(this).html("领导人");	
		}
	});	
	table.getCol("is_sort").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).is_sort == 1)
				$(this).html("手动分拣");
			else
				$(this).html("不分拣");
		}
	});
	table.getCol("is_auto_publish").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).is_auto_publish == 0)
				$(this).html("不公开");	
			else
				$(this).html("公开");	
		}
	});
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	
	tp.total = SQModelRPC.getSQModelCount(m);
			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

//打开查看窗口
function openViewModelPage(r_id)
{
	var height = 190;
	if(app_id == "system")
		height = 360;
	top.OpenModalWindow("查看业务","/sys/appeal/model/model_view.jsp?app="+app_id+"&model_id="+r_id,450,height);	
}

//打开添加窗口
function openAddModelPage()
{
	window.location.href = "/sys/appeal/model/model_add.jsp";	
}

//打开修改窗口
function openUpdateModelPage(m_ids)
{
	var selectIDS = "";
	if(m_ids == "" || m_ids == null)
		selectIDS = table.getSelecteCheckboxValue("model_id");
	else
		selectIDS = m_ids;
	window.location.href = "/sys/appeal/model/model_add.jsp?model_id="+selectIDS;
}

//得到所属应用的ID
function getAppIDS()
{
	var ids = "";
	$(":checkbox[checked]").each(function(){
		ids += ","+$(this).val();
	});
	if(ids != "")
		ids = ids.substring(1);

	return ids;
}

//添加业务
function insertModel()
{
	var bean = BeanUtil.getCopy(ModelBean);	
	$("#model_table").autoBind(bean);

	if(!standard_checkInputInfo("model_table"))
	{
		return;
	}
	if(!val.checkEmpty($("#dept_ids").val(),rele_type_name,"dept_names"))
	{
		return;
	}

	bean.model_id = SQModelRPC.getModelID();
	if(bean.remind_type == null)
		bean.remind_type = "";
	if(SQModelRPC.insertModel(bean,$("#dept_ids").val()))
	{
		top.msgAlert("业务"+WCMLang.Add_success);
		window.location.href = "modelList.jsp";
	}
	else
	{
		top.msgWargin("业务"+WCMLang.Add_fail);
	}
}
//修改业务
function updateModel()
{
	var bean = BeanUtil.getCopy(ModelBean);	
	$("#model_table").autoBind(bean);

	if(!standard_checkInputInfo("model_table"))
	{
		return;
	}	
	if(!val.checkEmpty($("#dept_ids").val(),rele_type_name,"dept_names"))
	{
		return;
	}
	bean.model_id = model_id;
	if(bean.remind_type == null)
		bean.remind_type = "";
	if(SQModelRPC.updateModel(bean,$("#dept_ids").val()))
	{
		top.msgAlert("业务"+WCMLang.Add_success);			
		window.location.href = "modelList.jsp";
	}
	else
	{
		top.msgWargin("业务"+WCMLang.Add_fail);
	}
}



//删除业务
function deleteModel()
{		
	var selectIDS = table.getSelecteCheckboxValue("model_id");
	if(SQModelRPC.deleteModel(selectIDS))
	{
		top.msgAlert("业务"+WCMLang.Delete_success);
		reloadModelList();
	}else
	{
		top.msgWargin("业务"+WCMLang.Delete_fail);
	}	
}



//保存业务用户
function saveModelUser(user_ids)
{
	var model_ids = table.getSelecteCheckboxValue("model_id");
	if(SQModelRPC.insertModelReleUserByModel(model_ids,user_ids))
	{
		top.msgAlert("业务与用户关联"+WCMLang.Add_success);		
		table.unChekcbox();
	}
	else
	{
		top.msgWargin("业务与用户关联"+WCMLang.Add_fail);
	}
}

//得到该业务下已有的用户ID字符串
function getSelectedUserIDS()
{	
	return SQModelRPC.getModelUserIDSByMID(table.getSelecteCheckboxValue("model_id"));
}


// add 2010-04-11  zhangqungang
//获取数据的 model_id
function getexportDataId()
{
	var selectIDS = table.getSelecteCheckboxValue("model_id");
	if(selectIDS == "" || selectIDS == "null")
	{
		top.msgAlert("请选择需要的操作记录");
	}else{
		exportData(selectIDS);
	}	
}

function exportData(modelId)
{
	top.OpenModalWindow("导出数据","/sys/appeal/model/exportData.jsp?model_id="+modelId+"",330,220);
}

function getexportData(m)
{
	datalist = jsonrpc.SQRPC.getexportData(m);
	datalist = List.toJSList(datalist);
	outFile();
}
function outFile()
{
	if(datalist.size()>0)
	{
		var listHeader = new List();
		listHeader.add("信件编码");
		listHeader.add("姓名");
		listHeader.add("性别");
		listHeader.add("手机");
		listHeader.add("身份证");
		listHeader.add("Email");
		listHeader.add("职业");
		listHeader.add("住址");
		listHeader.add("写信目的");
		listHeader.add("信件标题");
		listHeader.add("信件内容");
		listHeader.add("公开方式");
		var url = jsonrpc.SQRPC.CateAccessCountsOutExcel(datalist,listHeader);
		location.href=url; 
	}
}

//获取model_id
function getbatchIsNotOpenID()
{
	var selectIDS = table.getSelecteCheckboxValue("model_id");
	if(selectIDS == "" || selectIDS == "null")
	{
		top.msgAlert("请选择需要的操作记录");
	}else{
		batchIsNotOpen(selectIDS);
	}	
}

function batchIsNotOpen(model_ids)
{
	top.OpenModalWindow("批量不公开","/sys/appeal/model/bachIsNotOpen.jsp?model_id="+model_ids+"",400,150);
}

