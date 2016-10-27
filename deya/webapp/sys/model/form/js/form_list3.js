var Fields = new Bean("com.deya.wcm.services.model.Fields",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();
var ids = "";
var con_m = new Map();

function reloadList()
{
	ids="";
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
	
	//colsList.add(setTitleClos("id","ID","80px","","",""));	
	colsList.add(setTitleClos("field_enname","字段名","120px","","",""));	
	colsList.add(setTitleClos("field_cnname","字段别名","","","",""));
	colsList.add(setTitleClos("field_type","字段类型","160px","","",""));
	colsList.add(setTitleClos("is_sys","字段级别","150px","","",""));
	colsList.add(setTitleClos("is_null","是否必填","80px","","",""));
	colsList.add(setTitleClos("is_display","是否显示","80px","","",""));
	colsList.add(setTitleClos("field_flag","对应信息列表头字段","200px","","",""));
	colsList.add(setTitleClos("actions","操作","150px","","",""));
	colsList.add(setTitleClos("blank_col","&#160;","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){		
	con_m.put("model_id", model_id)
	beanList = jsonrpc.FormRPC.getFormFieldsListByModelIdN3(con_m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	//alert(beanList);
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
	
	table.getCol("field_flag").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{
			var field_flag = beanList.get(i-1).field_flag;
			//alert(field_flag);
			if(field_flag=="title"){
				field_flag = "  标题";
			}else{
				field_flag = "";
			}
	       var field_flag2 = beanList.get(i-1).field_flag2;
			if(field_flag2=="publish_time"){
				field_flag2 = "   发布时间";
			}else{
				field_flag2 = "";
			}
			$(this).html(field_flag + field_flag2);
		} 
	});
	
	table.getCol("actions").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{
			var strHtml = "";
			var field_flag = beanList.get(i-1).field_flag;
			if(field_flag=="title"){
				strHtml += '<a href="javascript:setFieldFlagOut('+beanList.get(i-1).id+',\'title\')">取消标题</a>';
			}else{
				strHtml += '<a href="javascript:setFieldFlag('+beanList.get(i-1).id+',\'title\')">设置标题</a>';
			}
			var field_flag2 = beanList.get(i-1).field_flag2;
			if(field_flag2=="publish_time"){
				strHtml += '&#160;&#160;&#160;<a href="javascript:setFieldFlagOut('+beanList.get(i-1).id+',\'publish_time\')">取消发布时间</a>';
			}else{
				strHtml += '&#160;&#160;&#160;<a href="javascript:setFieldFlag('+beanList.get(i-1).id+',\'publish_time\')">设置发布时间</a>';
			}
			$(this).html(strHtml);
		} 
	});
	
	table.getCol("field_type").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{
			var field_type = beanList.get(i-1).field_type;
			if(field_type=='0'){
				field_type = "单行文本";
			}else if(field_type=='1'){
				field_type = "多行文本";
			}else if(field_type=='2'){
				field_type = "多行文本（支持HTML）";
			}else if(field_type=='3'){
				field_type = "选项";
			}else if(field_type=='4'){
				field_type = "数字";
			}else if(field_type=='5'){
				field_type = "日期和时间";
			}else if(field_type=='6'){
				field_type = "文件";
			}
			$(this).html(field_type);
		} 
	});
	
	
	table.getCol("is_sys").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{
			var is_sys = beanList.get(i-1).is_sys;
			if(is_sys=='0'){
				is_sys='系统';
			}else if(is_sys=='1'){
				is_sys='自定义';
			}
			$(this).html(is_sys);
		} 
	});
	
	table.getCol("is_null").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{
			var is_null = beanList.get(i-1).is_null;
			if(is_null=='0'){
				is_null='否';
			}else if(is_null=='1'){
				is_null='是';
			}
			$(this).html(is_null);
		} 
	});
	
	table.getCol("is_display").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{
			var is_display = beanList.get(i-1).is_display;
			if(is_display=='0'){
				is_display='否';
			}else if(is_display=='1'){
				is_display='是';
			}
			$(this).html(is_display);
		} 
	});
	
	table.getCol("sort_col").each(function(i){	
		if(i>0)
		{
			$(this).html(getSortColStr());	 
			ids += beanList.get(i-1).from_id +","
			//alert(ids);
		}
	});

	if(ids!=""){
		ids = ids.substring(0,ids.length);
	}
	//ids="14,21,22";
	Init_InfoTable(table.table_name);
}

function showTurnPage(){					
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}


function openFieldsListPage(){
   // alert(ids);
	top.OpenModalWindow("选择元数据","/sys/model/fields/fields_list_select.jsp?model_id="+model_id+"&ids="+ids,1000,600);
}



//子窗口点击确定后的事件
function setFormFields(cids){
	if(cids!=''){
		var map = new Map();
		map.put("ids", ids);//原来的id
		map.put("cids",cids);//新的id
		map.put("model_id",model_id);//表单的id
		jsonrpc.FormRPC.updateForm(map);
		reloadList();
	}
}


//保存排序
function saveFormSort()
{
	var model_ids = table.getAllCheckboxValue("id");
	if(model_ids != "" && model_ids != null)
	{
		if(jsonrpc.FormRPC.saveFormSort(model_ids))
		{
			top.msgAlert(WCMLang.Sort_success);
			reloadList(); 
		}
		else
		{
			top.msgWargin(WCMLang.Sort_fail);
			return;
		}
	}
}


function openTabPageUpdate(id) 
{    
	//top.OpenModalWindow("维护站点","/sys/control/site/site_add.jsp?parentID="+site_id,500,360);
	//alert('/sys/member2/onlinebook/bookList_add.jsp?parentID='+site_id+"&tab_index="+top.curTabIndex);
	top.addTab(true,'/sys/model/form/form_fields_add.jsp?tab_index='+top.curTabIndex+"&id="+id+"&type=mod",'修改字段');
}


//删除
function deleteInfo()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	if(jsonrpc.FormRPC.deleteFormFields(selectIDS,model_id))
	//if(true)
	{
		top.msgAlert("信息"+WCMLang.Delete_success);
		reloadList();
	}else
	{
		top.msgWargin("信息"+WCMLang.Delete_fail);
	}
}



function setFieldFlag(id,flag)
{
	if(jsonrpc.FormRPC.updateFormFieldFlag(flag,"set",id))
	//if(true)
	{
		top.msgAlert("操作成功");
		reloadList();
	}else
	{
		top.msgWargin("操作失败");
	}
}

function setFieldFlagOut(id,flag)
{
	if(jsonrpc.FormRPC.updateFormFieldFlag(flag,"out",id))
	//if(true)
	{
		top.msgAlert("操作成功");
		reloadList();
	}else
	{
		top.msgWargin("操作失败");
	}
}


