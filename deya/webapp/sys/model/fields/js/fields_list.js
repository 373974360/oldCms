var FieldsRPC = jsonrpc.FieldsRPC;
var Fields = new Bean("com.deya.wcm.services.model.Fields",true);

var tp = new TurnPage();
var beanList = null;
var con_m = new Map();
var table = new Table();

function reloadList()
{   
	
	showTurnPage();
	showList();	
}

function initTable(){
	var colsMap = new Map();
	var colsList = new List();	
	colsList.add(setTitleClos("id","ID","80px","","",""));	
	colsList.add(setTitleClos("field_enname","字段名","120px","","",""));	
	colsList.add(setTitleClos("field_cnname","字段别名","","","",""));
	colsList.add(setTitleClos("field_type","字段类型","160px","","",""));
	colsList.add(setTitleClos("is_sys","字段级别","150px","","",""));
	colsList.add(setTitleClos("is_null","是否必填","80px","","",""));
	colsList.add(setTitleClos("is_display","是否显示","80px","","",""));
	//colsList.add(setTitleClos("actionCol","操作","150px","","",""));  
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
		sortCol = "";
		sortType = "";
	}
	//var m = new Map();
//	m.put("app_id", app);
//	m.put("site_id", site_id);
	con_m.put("start_num", tp.getStart());	
	con_m.put("page_size", tp.pageSize);
	beanList = FieldsRPC.getFieldsList(con_m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	//alert(toJSON(beanList));
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");

	table.getCol("field_enname").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{
			$(this).html('<a href="javascript:openTabPageUpdate('+beanList.get(i-1).id+')">'+beanList.get(i-1).field_enname+'</a>');
		} 
	});
	
	table.getCol("field_cnname").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{
			$(this).html('<a href="javascript:openTabPageUpdate('+beanList.get(i-1).id+')">'+beanList.get(i-1).field_cnname+'</a>');
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
	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){
	tp.total = jsonrpc.FieldsRPC.getFieldsListCount(con_m);		
	tp.show("turn","");	 
	tp.onPageChange = showList;
}


function openTabPage() 
{  
	//OpenModalWindow("维护站点","/sys/control/site/site_add.jsp?parentID="+site_id,500,360);
	//alert('/sys/member2/onlinebook/bookList_add.jsp?parentID='+site_id+"&tab_index="+curTabIndex);
	addTab(true,'/sys/model/fields/fields_add.jsp?tab_index='+curTabIndex,'添加元数据');
}

function openTabPageUpdate(id) 
{    
	//OpenModalWindow("维护站点","/sys/control/site/site_add.jsp?parentID="+site_id,500,360);
	//alert('/sys/member2/onlinebook/bookList_add.jsp?parentID='+site_id+"&tab_index="+curTabIndex);
	addTab(true,'/sys/model/fields/fields_add.jsp?tab_index='+curTabIndex+"&id="+id+"&type=mod",'修改元数据');
}


//删除
function deleteInfo()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	if(jsonrpc.FieldsRPC.deleteFields(selectIDS))
	//if(true)
	{
		msgAlert("信息"+WCMLang.Delete_success);
		reloadList();
	}else
	{
		//msgWargin("信息"+WCMLang.Delete_fail);
		msgWargin("该元数据已被关联，不能删除");
	}
}



