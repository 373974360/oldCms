
var node_ids = ""; // 选中的节点
var is_all_node = false; // 是否全选节点
var all_node_ids = ""; // 全选时的node_ids值
var pieChartDataList = new List(); // pie图的data列表
var pieJsonData = ""; // 点击选中的pie图的data数据

// 打开选择节点的弹出窗口
function openPage() {
	top.OpenModalWindow("选择公开节点","/sys/zwgk/count/nodeSelected.jsp",725,525);
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
		// node_ids = "";
		// $("#cat_tree").val("");
		$("#cat_tree").show();
		$("#openPage").show();
	}	
}

// 点击取得全部节点时,取得所有节点的node_id
function getAllNode_ids()
{
	if(all_node_ids == ""){
		var lt_node = jsonrpc.GKNodeRPC.getAllGKNodeList(); // 取得所有的公开节点列表;
		lt_node = List.toJSList(lt_node);
		for(var i=0;i<lt_node.size();i++) {
			var bean = lt_node.get(i);
			all_node_ids += ",'" + bean.node_id + "'";
		}
		all_node_ids = all_node_ids.substring(1);
	}
	node_ids = all_node_ids;
}


function search()
{
	var mp = new Map();
	start_day = $("#start_day").val();
	end_day = $("#end_day").val();
	if(start_day > end_day){
	   top.msgWargin("结束时间不能在开始时间前!");
	   return ;
	}
	if(start_day=="" || start_day==null || start_day=="null")
	{
		top.msgWargin("开始时间不能为空!");
		return;
	}
	if(end_day=="" || end_day==null || end_day=="null")
	{
		top.msgWargin("结束时间不能为空!");
		return;
	}
	end_day = end_day + " 23:59:59";
	mp.put("start_day", start_day);
	mp.put("end_day",end_day);
	if(!is_all_node)
	{
		if(node_ids == "") {
			top.msgWargin("公开节点不能为空,请选择公开节点!");
			return;
		}
		mp.put("node_id",node_ids);
	} 
	
	createTable(mp);
	// 添加chart信息
	
	$("#outFileBtn").show();
}


function outFileBtn(){
	if(beanList.size() != 0) {
	    //beanListResult = List.toJSList(beanListResult);
		var listHeader = new List();
		listHeader.add("节点名称");
		listHeader.add("信息总数");
		listHeader.add("待处理");
		listHeader.add("已处理");
		listHeader.add("已回复"); 
		listHeader.add("置为无效");
		listHeader.add("征询第三方");
		listHeader.add("暂缓处理");
		listHeader.add("超期");
		var url = jsonrpc.GKCountRPC.ysqgkInfoOutExcel(beanList,listHeader);
		//alert(url); 
		location.href=url;  
	}
	//alert(beanListResult);
}


function createTable(mp)
{
	$("#countList").empty();
	beanList = jsonrpc.GKCountRPC.getYSQStateCnt(mp); //  取得依申请公开申请单状态统计
	beanList = List.toJSList(beanList);
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th>节点名称</th>		" +
    "   <th>信息总数</th>       " +
    "   <th>待处理</th>   " +
    "	<th>已处理</th> " +
    "	<th>已回复</th>     " +
	"   <th>置为无效</th>       " +
    "   <th>征询第三方</th>   " +
    "	<th>暂缓处理</th> " +
    "	<th>超期</th>     " +
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
 	 	"<td colspan=\"10\"></td>"+ "</tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"10\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"10\"></td>"+" </tfoot>";
	}
	$("#countList").append(treeHtmls);
	iniTreeTable("countList");
	addOMouseClass();
	
}

function addOMouseClass()
{
	$("span.file").each(function(i){
	$(this).hover(
 		 function () {
   			 $(this).css("color","red");
 		 },
  		function () {
    		$(this).css("color","#000000");
  		}
		)
	});
}

// 填充数据
function setTreeNode(bean,index)
{			
	var treeHtml = "";
	var type_calss= "";
	
	treeHtml+="<tr> <td><span class='file'><a href=\"javascript:redirectURL('"+bean.node_id+"','"+bean.node_name+"')\">"+bean.node_name+"</a></span></td>";
    treeHtml += "<td>"+ bean.totalCnt +"</td>";
    treeHtml += "<td>"+ bean.unAcceptCnt +"</td>";
    treeHtml += "<td>"+ bean.acceptedCnt +"</td>";
    treeHtml += "<td>"+ bean.replyCnt +"</td>";
	treeHtml += "<td>"+ bean.invalidCnt +"</td>";
    treeHtml += "<td>"+ bean.thirdCnt +"</td>";
    treeHtml += "<td>"+ bean.delayCnt +"</td>";
    treeHtml += "<td>"+ bean.timeoutCnt +"</td>";
    treeHtml +="<td><a href='javascript:openPieChart("+index+")'>"+"查看"+"</a></td>";
	treeHtml+="</tr>";
	return treeHtml;
}

function redirectURL(node_id,node_name)
{
	top.addTab(true ,"/sys/zwgk/count/ysqStatusByMonth.jsp?start_day="+start_day+"&end_day="+end_day+"&site_id="
		+node_id+"&app_id=zwgk&type=back&node_name="+encodeURI(node_name),"申请单统计");
}

// 设置柱状图显示
function setReportChart()
{
	chartJsonData = null;
	if(beanList == null)
	{
		return "";
	}
	chartJsonData = "{\"config\":{\"yTitle\":\"信息条数\",\"categoryField\":\"录入人\",\"yField\":\"录入条数,已发条数\"},\"dataset\":[";
	for(var i=0; i<beanList.size(); i++){
		var list = List.toJSList(beanList.get(i));
		var strData = '{\"录入人\":\"'+ list.get(1)  +'\",\"录入条数\":\"'+ list.get(2) +'\",\"已发条数\":\"'+ list.get(3) +'\"},';
		chartJsonData += strData;
	}
	
	chartJsonData = chartJsonData.substring(0,chartJsonData.length-1) + "]}";
	
	//第一个参数是图表类型，包括：line,column,bar,pie,area,plot,bubble
	$("#chart").attr("src","chart.jsp");
}


//*****************生成pie图的js代码段******************************
//str+="<td><span onclick='openPieChart("+index+")'>"+"---"+"</span></td>"
function openPieChart(index) {
	pieJsonData = 	pieChartDataList.get(index);
	top.OpenModalWindow("饼状图","/sys/zwgk/count/chart.html",475,470);
	
}

//处理图表的json数据
function initPieChartList(beanList){
	pieChartDataList.clear();
	
	for(var i=0;i<beanList.size();i++){
		var strData = createPieData(beanList.get(i));
		pieChartDataList.add(strData);
	}
}

//生成pie的json字符串
function createPieData(bean) {
	var ret =  '{ "title": { "text": "' + bean.node_name +'详细统计信息"}, ' +
	'"elements":[{ "tip":"#label# : #val# <br>所占信息比例 : #percent#","type":"pie", "start-angle": 180,"colours": '+
	  '["#d01f3c","#356aa0","#C79810","#73880A","#D15600","#6BBA70"],'+
   '"alpha":     0.6, "stroke": 6, "animate":  1, "values" : [' 
	ret += '{"value":'+bean.unAcceptCnt+',"label":"待处理"},{"value":'+bean.acceptedCnt+',"label":"已处理"},{"value":'+bean.replyCnt+',"label":"已回复"}'
	       +',{"value":'+bean.invalidCnt+',"label":"置为无效"},{"value":'+bean.thirdCnt+',"label":"征询第三方"},{"value":'+bean.delayCnt+',"label":"暂缓处理"}'
	       +',{"value":'+bean.timeoutCnt+',"label":"超期"}';
	
	ret += "]}]}";
	return ret;
}