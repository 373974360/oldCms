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
	//con_m.put("page_size", tp.pageSize);
	con_m.put("page_size",9999999);
	beanList = FieldsRPC.getFieldsList(con_m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	//alert(toJSON(beanList));
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");

	/*
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
	*/
	
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
			
			var strIds = ","+ids+",";
			var strId = ","+beanList.get(i-1).id+",";
			//alert(ids+":"+beanList.get(i-1).id+"---------------"+(strIds.indexOf(strId)));
			if(strIds.indexOf(strId) > -1)
			{   //alert($(this).parent().html());
				$(this).parent().find("input").attr("checked","true");
			}
			
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
	//tp.total = jsonrpc.FieldsRPC.getFieldsListCount(con_m);		
	tp.show("turn","simple");	 
	tp.onPageChange = showList;
}


//保存
function saveCateIDS(){
	var selectIDS = table.getSelecteCheckboxValue("id");
	getCurrentFrameObj().setFormFields(selectIDS);
	CloseModalWindow();
}



