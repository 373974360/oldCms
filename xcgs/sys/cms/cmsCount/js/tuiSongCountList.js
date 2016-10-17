var CategoryRPC = jsonrpc.CategoryRPC;
var CategoryBean = new Bean("com.deya.wcm.bean.cms.category.CategoryBean",true);
var TuisongCountRPC = jsonrpc.TuisongCountRPC;

var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var con_m = new Map();
var b_start_day;	    // 临时保存的统计开始时间
var b_end_day;		    // 临时保存的统计结束时间

var initTree = false;   // 初始化目录树标记
var initType = false;   // 初始化"查看方式"改变事件标记

// 显示统计结果列表
function showList(){

	var mp = new Map();
	var v_type="";

	mp.put("site_id",site_id);
	mp.put("cat_id",cat_id);

	b_start_day = $("#start_day").val();
	b_end_day = $("#end_day").val()+" 23:59:59";

	mp.put("start_day",b_start_day);
	mp.put("end_day",b_end_day);
    mp.put(v_type,"bycat");

	beanList = TuisongCountRPC.getTuisongInfoCountByCat(mp);
	beanList = List.toJSList(beanList);
	
	createTable(beanList);
}

// 动态处理时间,等信息
function setInfoTotal(beanList){
	var h_cnt = 0;
	var r_cnt = 0;
	var l_cnt = 0;
	var cnt = 0;
	if(beanList.size() != 0) {
		for(var i=0;i<beanList.size();i++){
			h_cnt += beanList.get(i).hostInfoCount;
			r_cnt += beanList.get(i).refInfoCount;
			l_cnt += beanList.get(i).linkInfoCount;
		}
		cnt = h_cnt + r_cnt + l_cnt;
		
		$("#allInfo").html(cnt);
		$("#hostInfo").html(h_cnt);
		$("#refInfo").html(r_cnt);
		$("#linkInfo").html(l_cnt);
	}
}

// 点击统计btn时触发的函数
function searchBtn(){
	initCategoryTree();
	showList();
	$("#outFileBtn").show();
}

//导出
function  outFileBtn(){
	if(beanList.size() != 0)
	{	
		var listHeader = new List();
		    listHeader.add("栏目");
		    listHeader.add("主信息");
		    listHeader.add("推送信息"); 
		var url = TuisongCountRPC.tuiSongInfoOutExcel(beanList,listHeader);
		location.href=url;  
	}
}

// 生成统计页table
function createTable(beanList)
{
	$("#countList").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th>栏目</th>		" +
    "   <th>主信息</th>     " +
    "	<th>推送信息</th>   " +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	if(beanList.size() != 0) {
		for(var i=0;i<beanList.size();i++){
			treeHtmls += stuffTable(beanList.get(i));
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
 	 	"<td colspan=\"3\"></td>"+ "</tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"3\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"3\"></td>"+" </tfoot>";
	}
	$("#countList").append(treeHtmls);
	iniTreeTable("countList");	
}

// 填充每一行的具体数据
// 添加数据的聚合,显示信息总数
function stuffTable(bean)
{			
	var treeHtml = "";
	var type_calss= "";
		treeHtml+="<tr> <td><span class='file'>"+bean.cat_name+"</span></td>";
		treeHtml+="<td>"+ bean.is_host+"</td>";
		treeHtml+="<td>"+ bean.isNot_host+"</td>";
		treeHtml+="</tr>";
	return treeHtml;
}

// 取得每个月的最后一天 参数为一个代表月份的数值(1-12,如果这个取值无效,返回0),一个为年份
function lastDayOfMonth(month,year){
	if(1> month || 12 < month){
		return 0;
	}else if(2 == month){
		if(isLeapYear(year)){
			return 29;
		}else{
			return 28;
		}
	}else if(4==month || 6==month || 9==month || 11==month){
		return 30;
	}else{
		return 31;
	}
}

// 判断闰年 参数为表示年份的数值
function isLeapYear(date)
{
	return (0==date%4&&((date%100!=0)||(date%400==0)));
}

// 添加目录树的点击事件
function initCategoryTree()
{
	if(initTree){
		return;
	}else{
		initTree = true;
	}
	$('#leftMenuTree').tree({
		onClick:function(node){			 
			changeCategoryListTable(node.id);           
		}
	});
}

//点击树节点,修改目录列表数据
function changeCategoryListTable(c_id){
	cat_id = c_id+"";
	showList();
}