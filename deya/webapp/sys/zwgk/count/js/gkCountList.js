var GKCountRPC = jsonrpc.GKCountRPC;
var GKCountBean = new Bean("com.deya.wcm.bean.zwgk.count.GKCountBean",true);

var beanList = null;

var search_flg = false;
var start_day;
var end_day;

var pieJsonData = "";

function search()
{
	start_day = $("#start_day").val();
	end_day = $("#end_day").val();
	if(start_day > end_day){
	   msgWargin("结束时间不能在开始时间前");
	   return ;
	}
	if(start_day=="" || start_day==null || start_day=="null")
	{
		msgWargin("开始时间不能为空");
		return;
	}
	if(end_day=="" || end_day==null || end_day=="null")
	{
		msgWargin("结束时间不能为空");
		return;
	}
	$("#countList").show();
	
	$("#countList").removeAttr("src");
	$("#countList").attr("src","treetable.jsp?start_day="+start_day+"&end_day="+end_day+"&site_id="+site_id+"&type=cate");
    
	$("#outFileBtn").show();
}

//var beanListResult = null;
var site_id = '';
var startDate = '';
var endDate = '';
function outFileBtn(){
	//if(beanListResult.size() != 0) {
	    //beanListResult = List.toJSList(beanListResult);
	    //alert(beanListResult); 
		var listHeader = new List();
		listHeader.add("公开栏目");
		listHeader.add("信息总数");
		listHeader.add("主动公开数目");
		listHeader.add("依申请公开数目");
		listHeader.add("不公开数目");  
		var url = GKCountRPC.gkTreeInfoOutExcel(listHeader,site_id,startDate,endDate);
		//alert(url);
		location.href=url;  
	//}
	//alert(beanListResult);
}
