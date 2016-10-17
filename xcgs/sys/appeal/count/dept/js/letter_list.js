var LetterCountBean = new Bean("com.deya.wcm.bean.appeal.count.LetterCountBean",true);
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
	colsList.add(setTitleClos("sq_code","信件编码","120px","","",""));	
	colsList.add(setTitleClos("sq_title","信件标题","","","",""));	
	colsList.add(setTitleClos("sq_dtime","来信时间","120px","","",""));	
	colsList.add(setTitleClos("sq_status","处理状态","120px","","",""));
	colsList.add(setTitleClos("model_cname","递交渠道","120px","","",""));
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
	con_m.put("model_id", model_id);
	con_m.put("s", s);	
	con_m.put("e", e);
	
	
	if(sq_status!='all'){
		con_m.put("sq_status", sq_status);
	}
	if(sq_status=='wei'){
		con_m.remove("sq_status");
		con_m.put("type","wei");
	}

	if(do_dept !=""){
		con_m.put("do_dept",do_dept);
		con_m.put("user_id","");
		beanList = jsonrpc.CountServicesRPC.getListByModelIdAndDept(con_m);//第一个参数为站点ID，暂时默认为空	
	}else {
	    con_m.put("user_id",user_id);
		con_m.put("do_dept","");
		beanList = jsonrpc.CountServicesRPC.getListByModelIdAndUserId(con_m);//用户处理的信件
	}
	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	

	curr_bean = null;
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");

	table.getCol("sq_title").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{
			$(this).html(beanList.get(i-1).sq_title);
		}
	});
	
	table.getCol("sq_status").each(function(i){
		//$(this).css({"text-align":"left"});	
		if(i>0)
		{
			var sq_status = beanList.get(i-1).sq_status;
			if(sq_status=='0'){
				sq_status = '待处理';
			}else if(sq_status=='1'){
				sq_status = '处理中';
			}else if(sq_status=='2'){
				sq_status = '待审核';
			}else if(sq_status=='3'){
				sq_status = '已办结';
			}
			$(this).html(sq_status);
		}
	});
	
	//Init_InfoTable(table.table_name);
}

function showTurnPage(){

    if(do_dept !=""){
		tp.total = jsonrpc.CountServicesRPC.getListByModelIdAndDeptCount(con_m);	
	}else {
		tp.total = jsonrpc.CountServicesRPC.getListByModelIdAndUserCount(con_m);//用户处理的信件
	}

	tp.show("turn","simple");
	//tp.show("turn","");	 
	tp.onPageChange = showList;
}  
