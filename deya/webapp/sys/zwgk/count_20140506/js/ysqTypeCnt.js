
var node_ids = ""; // 选中的节点
var is_all_node = false; // 是否全选节点
var all_node_ids = ""; // 全选时的node_ids值

var pieChartDataList = new List(); // pie图的data列表
var pieJsonData = ""; // 点击选中的pie图的data数据

// 打开选择节点的弹出窗口
function openPage() {
	OpenModalWindow("选择公开节点","/sys/zwgk/count/nodeSelected.jsp",725,525);
}

// 设置选中的节点名称
function setNodeName(node_name)
{
	$("#cat_tree").val(node_name);
}

// 点击取得全部节点触发
function setAllNode() {
	is_all_node = $("#setAll").is(':checked');
	if(is_all_node) {
		$("#cat_tree").hide();
		$("#openPage").hide();
	} else {
		$("#cat_tree").show();
		$("#openPage").show();
	}	
}

function search()
{
	var mp = new Map();
	start_day = $("#start_day").val();
	end_day = $("#end_day").val();
	if(start_day > end_day){
	   msgWargin("结束时间不能在开始时间前!");
	   return ;
	}
	if(start_day=="" || start_day==null || start_day=="null")
	{
		msgWargin("开始时间不能为空!");
		return;
	}
	if(end_day=="" || end_day==null || end_day=="null")
	{
		msgWargin("结束时间不能为空!");
		return;
	}
	end_day = end_day + " 23:59:59";
	mp.put("start_day", start_day);
	mp.put("end_day",end_day);
	if(!is_all_node)
	{
		if(node_ids == "") {
			msgWargin("公开节点不能为空,请选择公开节点!");
			return;
		}
		mp.put("node_id",node_ids);
	} 
	
	createTable(mp);
	// 添加chart信息
}


function createTable(mp)
{
	$("#countList").empty();
	beanList = jsonrpc.GKCountRPC.getYSQTypeCount(mp); //  取得依申请公开申请单状态统计
	beanList = List.toJSList(beanList);
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th>节点名称</th>		" +
    "   <th>总计</th>       " +
    "   <th>全部公开</th>   " +
    "	<th>部分公开</th> " +
    "	<th>不予公开</th>     " +
	"   <th>非本单位信息</th>       " +
    "   <th>信息不存在</th>   " +
    "	<th>图表</th>           " +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	if(beanList.size() != 0) {
		initPieChartList(beanList);
		for(var i=0;i<beanList.size();i++){
			treeHtmls += setTreeNode(beanList.get(i),i);
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
 	 	"<td colspan=\"8\"></td>"+ " </tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"8\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"8\"></td>"+" </tfoot>";
	}
	$("#countList").append(treeHtmls);
	iniTreeTable("countList");
	
	$("#outFileBtn").show();
	
}

function outFileBtn(){
	if(beanList.size() != 0) {
	    //beanListResult = List.toJSList(beanListResult);
		var listHeader = new List();
		listHeader.add("节点名称");
		listHeader.add("总计");
		listHeader.add("全部公开");
		listHeader.add("部分公开");
		listHeader.add("不予公开"); 
		listHeader.add("非本单位信息");
		listHeader.add("信息不存在");
		var url = jsonrpc.GKCountRPC.ysqgkTypeInfoOutExcel(beanList,listHeader);
		//alert(url); 
		location.href=url;  
	}
	//alert(beanListResult);
}

// 填充数据
function setTreeNode(bean,index)
{			
	var treeHtml = "";
	var type_calss= "";
	treeHtml+="<tr> <td><span class='file'>"+bean.node_name+"</span></td>";
    treeHtml += "<td>"+ bean.type_total +"</td>";
    treeHtml += "<td>"+ bean.qbgk_cnt +"</td>";
    treeHtml += "<td>"+ bean.bfgk_cnt +"</td>";
    treeHtml += "<td>"+ bean.bygk_cnt +"</td>";
	treeHtml += "<td>"+ bean.fbdwxx_cnt +"</td>";
    treeHtml += "<td>"+ bean.xxbcz_cnt +"</td>";
    treeHtml +="<td><a href='javascript:openPieChart("+index+")'>"+"查看"+"</a></td>";
	treeHtml+="</tr>";
	return treeHtml;
}

// *****************生成pie图的js代码段******************************
// str+="<td><span onclick='openPieChart("+index+")'>"+"---"+"</span></td>"
function openPieChart(index) {
	pieJsonData = 	pieChartDataList.get(index);
	OpenModalWindow("饼状图","/sys/zwgk/count/chart.html",475,470);
	
}

// 处理图表的json数据
function initPieChartList(beanList){
	pieChartDataList.clear();
	
	for(var i=0;i<beanList.size();i++){
		var strData = createPieData(beanList.get(i));
		pieChartDataList.add(strData);
	}
}

// 生成pie的json字符串
function createPieData(bean) {
	var ret =  '{ "title": { "text": "' + bean.node_name +'详细统计信息"}, ' +
	'"elements":[{ "tip":"#label# : #val# <br>所占信息比例 : #percent#","type":"pie", "start-angle": 180,"colours": '+
	  '["#d01f3c","#356aa0","#C79810","#73880A","#D15600","#6BBA70"],'+
      '"alpha":     0.6, "stroke": 6, "animate":  1, "values" : [' 
	ret += '{"value":'+bean.qbgk_cnt+',"label":"全部公开"},{"value":'+bean.bfgk_cnt+',"label":"部分公开"},{"value":'+
			bean.bygk_cnt+',"label":"不予公开"},{"value":'+bean.fbdwxx_cnt+',"label":"非本单位信息"},{"value":'+
			bean.xxbcz_cnt+',"label":"信息不存在"}';
	
	ret += "]}]}";
	return ret;
}

