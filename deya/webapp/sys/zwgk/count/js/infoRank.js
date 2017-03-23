var chartJsonData = "";
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
		//getAllNode_ids();
	} else {
		//node_ids = "";
		//$("#cat_tree").val("");
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
	end_day = end_day + " 23:59:59"
	mp.put("start_day", start_day);
	mp.put("end_day",end_day);
	if(!is_all_node)
	{
		if(node_ids == "") {
			msgWargin("公开节点不能为空,请选择公开节点!");
			return;
		} 
		mp.put("site_ids",node_ids);
	} 
	
	var num = $("#num").val();
	mp.put("num", num);
	
	//alert(mp);
	$("#chartContainer").empty();
	// 添加table内容
	createTable(mp);
	setReportChart();
	
	$("#outFileBtn").show();
}


function outFileBtn(){
	if(beanList.size() != 0) {
	    //beanListResult = List.toJSList(beanListResult);
		var listHeader = new List();
		listHeader.add("节点名称");
		listHeader.add("信息总数");
		listHeader.add("主动公开数目");
		listHeader.add("依申请公开数目");
		listHeader.add("不公开数目"); 
		var url = jsonrpc.GKCountRPC.gkInfoOutExcel(beanList,listHeader);
		//alert(url); 
		location.href=url;  
	}
	//alert(beanListResult);
}

function createTable(mp)
{
	$("#countList").empty();
	beanList = jsonrpc.GKCountRPC.GKInfoCountRank(mp); // 取得节点信息量排行
	beanList = List.toJSList(beanList);
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th>节点名称</th>		" +
    "   <th>信息总数</th>       " +
    "   <th>主动公开数目</th>   " +
    "	<th>依申请公开数目</th> " +
    "	<th>不公开数目</th>     " +
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
 	 	"<td colspan=\"6\"></td>"+ " </tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"6\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"6\"></td>"+" </tfoot>";
	}
	$("#countList").append(treeHtmls);
	iniTreeTable("countList");
	
}

// 填充数据
function setTreeNode(bean,index)
{			
	var treeHtml = "";
	var type_calss= "";
	treeHtml+="<tr> <td><span class='file'>"+bean.site_name+"</span></td>";
    treeHtml += "<td>"+ bean.info_count +"</td>";
    treeHtml += "<td>"+ bean.z_count +"</td>";
    treeHtml += "<td>"+ bean.y_count +"</td>";
    treeHtml += "<td>"+ bean.b_count +"</td>";
    treeHtml +="<td><a href='javascript:openPieChart("+index+")'>"+"查看"+"</a></td>";
	treeHtml+="</tr>";
	return treeHtml;
}


// 设置柱状图显示
function setReportChart()
{
	chartJsonData = "";
	if(beanList.size() == 0)
	{
		chartJsonData = '{"title": { "text": "公开信息量排行"}}';
		//swfobject.embedSWF("swf/open-flash-chart.swf", "chartContainer", "750", "600", "9.0.0"); 
		return "";
	}
	var site_name_list = ""; // 站点名称列
	var total_cnt_list = ""; // 信息总数
	var z_cnt_list = ""; // 主动公开数目
	var y_cnt_list = ""; // 依申请公开数目
	var b_cnt_list = ""; // 不公开数目
	
	var max_cnt = 10;
	for(var i=0; i<beanList.size(); i++){
		var bean = beanList.get(i);
		site_name_list +=',"' + bean.site_name +'"';
		total_cnt_list +=',' +  bean.info_count +'';
		max_cnt = bean.info_count>max_cnt ? bean.info_count+10: max_cnt;
		z_cnt_list +=',' +  bean.z_count +'';
		y_cnt_list +=',' +  bean.y_count +'';
		b_cnt_list +=',' +  bean.b_count +'';
	}
	
	var steps = Math.floor(max_cnt/20);
	if(steps>10){
      var m_n = steps%10;
      steps = steps - m_n;
	}

	
	site_name_list =   site_name_list.substring(1);
	total_cnt_list =   total_cnt_list.substring(1);
	z_cnt_list   =     z_cnt_list.substring(1);
	y_cnt_list   =     y_cnt_list.substring(1);
	b_cnt_list   =     b_cnt_list.substring(1);
	
	chartJsonData = '{   '+     
		'"title": { "text": "公开信息量排行"},'+
		 '"y_legend":{'+
    		'"text": "Count",'+
    		'"style": "{color: #736AFF; font-size: 12px;}"'+
  		'},'+
  		'"x_legend":{'+
    	'	"text": "公开站点",'+
    	'	"style": "{color: #736AFF; font-size: 12px;}"'+
  		'},   "x_axis":{ "labels": {"labels": [' + site_name_list +
		']}}, "y_axis":{ "max": '+  max_cnt  +',  "min": 0 ,"steps": '+steps+' },'+
		'"elements": [{ "type": "bar","colour":"#d01f3c" ,"values": ['+total_cnt_list+'] , "text" : "信息总数"},' + 
		'{ "type": "bar","colour":"#356aa0" ,"values": ['+z_cnt_list+'] , "text" : "主动公开"},' + 
		'{ "type": "bar","colour":"#C79810" ,"values": ['+y_cnt_list+'] , "text" : "依申请公开"},' + 
		'{ "type": "bar","colour":"#73880A" ,"values": ['+b_cnt_list+'] , "text" : "不予公开"}' +
		']}';

	 var wid = document.body.clientWidth;
	swfobject.embedSWF("/sys/js/open-flash-chart/open-flash-chart.swf", "chartContainer", wid, "300", "9.0.0",null, null,{wmode:'transparent'}); 
}

function open_flash_chart_data() { 
	return	chartJsonData;
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
	var ret =  '{ "title": { "text": "' + bean.site_name +'详细统计信息"}, ' +
	'"elements":[{ "tip":"#label# : #val# <br>所占信息比例 : #percent#","type":"pie", "start-angle": 180,"colours": '+
	  '["#d01f3c","#356aa0","#C79810","#73880A","#D15600","#6BBA70"],'+
      '"alpha":     0.6, "stroke": 6, "animate":  1, "values" : [' 
	ret += '{"value":'+bean.z_count+',"label":"主动公开信息"},{"value":'+bean.y_count+',"label":"依申请公开信息"},{"value":'+bean.b_count+',"label":"不公开信息"}';
	
	ret += "]}]}";
	return ret;
}

