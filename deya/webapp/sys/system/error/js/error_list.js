var ErrorReportBean = new Bean("com.deya.wcm.bean.system.error.ErrorReportBean",true);
var tp = new TurnPage();
var beanList = null;
var con_m = new Map();
var table = new Table();	


function reloadList()
{   
	
	showList();	
	showTurnPage();
	
}

function initTable(){
	var colsMap = new Map();
	var colsList = new List();	
	colsList.add(setTitleClos("id","ID","60px","","",""));	
	colsList.add(setTitleClos("info_title","标题","","","",""));	
	colsList.add(setTitleClos("err_name","提交人姓名","120px","","",""));	
	colsList.add(setTitleClos("err_type","纠错类型","180px","","",""));
	colsList.add(setTitleClos("err_state","状态","80px","","",""));
	colsList.add(setTitleClos("err_time","提交时间","120px","","",""));
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
	con_m.put("site_id", siteId);
	con_m.put("start_num", tp.getStart());	
	con_m.put("page_size", tp.pageSize);
	//alert(con_m);
	beanList = jsonrpc.ErrorReportRPC.getErrorReportList(con_m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	//alert(toJSON(beanList));
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");


	table.getCol("info_title").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{
			$(this).html('<a href="'+beanList.get(i-1).info_url+'" target="_blank">'+beanList.get(i-1).info_title+'</a>');
		} 
	});
	

	table.getCol("err_state").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
		if(i>0)
		{
			var err_state = beanList.get(i-1).err_state;
			if(err_state == '1'){
				err_state = "未处理";
			}else if(err_state == '2'){
				err_state = "不予处理";
			}else if(err_state == '3'){
				err_state = "已处理";
			}
			$(this).html(err_state);
		} 
	});

	//Init_InfoTable(table.table_name);
}

function showTurnPage(){
	tp.total = jsonrpc.ErrorReportRPC.getErrorReportListCount(con_m);		
	tp.show("turn","");	 
	tp.onPageChange = showList;
}


//删除
function deleteFun()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	if(jsonrpc.ErrorReportRPC.deleteErrorReports(selectIDS))
	//if(true)
	{  
		msgAlert("信息"+WCMLang.Delete_success);
		reloadList();
	}else
	{
		msgWargin("信息"+WCMLang.Delete_fail);
	}
}

//操作
function operateFun()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	OpenModalWindow("操作信息","/sys/system/error/error_opt.jsp?id="+selectIDS,700,450);
}

//查看
function viewFun()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	var selectIDS = table.getSelecteCheckboxValue("id");
	OpenModalWindow("查看详细信息","/sys/system/error/error_view.jsp?id="+selectIDS,700,450);
}

function initViewData(){
	var id = request.getParameter("id");
	//alert(melogid); 
	if(id!=null && id!=''){
		var bean = jsonrpc.ErrorReportRPC.getErrorReportById(id);
		if(bean!=null){
			$("#info_id").html(bean.info_id);
			//$("#memberid").html(Eacuse.memberid); 
			$("#info_title").html(bean.info_title);
			$("#info_url").html('<a href="'+bean.info_url+'" target="_blank">'+bean.info_url+'</a>');
			$("#err_type").html(bean.err_type);
			$("#err_content").html(bean.err_content);
			$("#err_name").html(bean.err_name);
			$("#err_name_tel").html(bean.err_name_tel);
			$("#err_name_ip").html(bean.err_name_ip);
			$("#err_time").html(bean.err_time);
			var err_state = bean.err_state;
			if(err_state == '1'){
				err_state = "未处理";
			}else if(err_state == '2'){
				err_state = "不予处理";
				$("#err_noteTr").show();
				$("#err_note").html(bean.err_note);
				$("#o_timeTr").show();
				$("#o_time").html(bean.o_time);
			}else if(err_state == '3'){
				err_state = "已处理";
				$("#err_noteTr").show();
				$("#err_note").html(bean.err_note);
				$("#o_timeTr").show();
				$("#o_time").html(bean.o_time);
			} 
			$("#err_state").html(err_state);
		}
	}
}

function initViewDataOpt(){
	var id = request.getParameter("id");
	//alert(melogid); 
	if(id!=null && id!=''){
		var bean = jsonrpc.ErrorReportRPC.getErrorReportById(id);
		if(bean!=null){
			$("#id").val(bean.id);
			$("#info_id").html(bean.info_id);
			//$("#memberid").html(Eacuse.memberid); 
			$("#info_title").html(bean.info_title);
			$("#info_url").html('<a href="'+bean.info_url+'" target="_blank">'+bean.info_url+'</a>');
			$("#err_type").html(bean.err_type);
			$("#err_content").html(bean.err_content);
			$("#err_name").html(bean.err_name);
			$("#err_name_tel").html(bean.err_name_tel);
			$("#err_name_ip").html(bean.err_name_ip);
			$("#err_time").html(bean.err_time);
			var err_state = bean.err_state;
			if(err_state == '1'){
				err_state = "未处理";
			}else if(err_state == '2'){
				err_state = "不予处理";
			}else if(err_state == '3'){
				err_state = "已处理";
			} 
			$("#err_state").html(err_state);
			$("#err_note").val(bean.err_note);
		}
	}
}

function handleFun(){
	ErrorReportBean.id = $("#id").val();
	ErrorReportBean.err_note = $("#err_note").val();
	ErrorReportBean.err_state = "3";
	if(jsonrpc.ErrorReportRPC.updateErrorReportById(ErrorReportBean)){
		msgAlert("操作成功！");
		CloseModalWindow();
		getCurrentFrameObj().reloadList();
	}else{
		msgAlert("操作失败！");
	}
}
function noHandleFun(){
	ErrorReportBean.id = $("#id").val();
	ErrorReportBean.err_note = $("#err_note").val();
	ErrorReportBean.err_state = "2";
	if(jsonrpc.ErrorReportRPC.updateErrorReportById(ErrorReportBean)){
		msgAlert("操作成功！");
		CloseModalWindow();
		getCurrentFrameObj().reloadList();
	}else{
		msgAlert("操作失败！");
	}
}

