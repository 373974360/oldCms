var GKCountRPC = jsonrpc.GKCountRPC;
var GKCountBean = new Bean("com.deya.wcm.bean.zwgk.count.GKCountBean",true);
var node_ids = "";
var num = "";
var chartJsonData; // 柱状图data数据保存

// ......
function createTable(mp)
{
	$("#countList").empty();
	beanList = GKCountRPC.GKWorkLoadRank(mp);
	beanList = List.toJSList(beanList);
	
	// 初始化Table
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th>节点名称</th>		" +
    "   <th>录入人姓名</th>       " +
    "   <th>录入信息总数</th>   " +
    "	<th>已发信息条数</th> " +
    "	<th>百分比</th>     " +
    //"	<th>图表</th>           " +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	if(beanList.size() != 0) {
		for(var i=0;i<beanList.size();i++){
			treeHtmls += setTreeNode(beanList.get(i));
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
	  	"<td colspan=\"6\"></td>"+
		 " </tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"6\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"6\"></td>"+" </tfoot>";
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
		listHeader.add("录入人姓名");
		listHeader.add("录入信息总数");
		listHeader.add("已发信息条数");
		listHeader.add("百分比"); 
		var url = jsonrpc.GKCountRPC.gkWorkInfoOutExcel(beanList,listHeader);
		//alert(url); 
		location.href=url;  
	}
	//alert(beanListResult);
}

// 填充数据
function setTreeNode(list)
{			
	list = List.toJSList(list);
	var treeHtml = "";
	var type_calss= "";
	treeHtml+="<tr> <td><span class='file'>"+list.get(0)+"</span></td>";  //站点名称
    treeHtml += "<td>"+list.get(1) +"</td>"; //录入人姓名
    treeHtml += "<td>"+list.get(2)+"</td>"; //录入信息总数
    treeHtml += "<td>"+list.get(3)+"</td>"; //已发条数
    treeHtml += "<td>"+list.get(4)+"%"+"</td>";  //百分比
    //treeHtml += "<td>"+"-"+"</td>";
	treeHtml+="</tr>";
	return treeHtml;
}




function search()
{
	var mp = new Map();
	var s_day = $("#start_day").val();
	var e_day = $("#end_day").val();
	var num = $("#num").val();
	
	if(s_day==null || s_day=="") {
		msgWargin("开始时间不能为空!");
		return;
	}
	if(e_day==null || e_day=="") {
		msgWargin("结束时间不能为空!");
		return;
	}
	e_day = e_day + " 23:59:59";
	if(s_day > e_day){
	   msgWargin("结束时间不能在开始时间前!");
	   return ;
	}
	mp.put("start_day", s_day);
	mp.put("end_day",e_day);
	mp.put("num",num);
	mp.put("sortCol","record_count");
	
	if(!is_all_node)
	{
		if(node_ids == "") {
			msgWargin("公开节点不能为空!请选择公开节点");
			return ;
		}
		mp.put("site_id",node_ids);
	} 
	$("#chart").attr("src","");
	createTable(mp);
	
	// 设置统计图表
	setReportChart();
}


// 设置柱状图显示
function setReportChart()
{
	chartJsonData = "";
	if(beanList.size() == 0)
	{
		chartJsonData = '{"title": { "text": "工作量排行信息"}}';
		//swfobject.embedSWF("swf/open-flash-chart.swf", "chartContainer", "750", "600", "9.0.0"); 
		return "";
	}
	var user_site_list = ""; // 用户-站点名称列
	var total_list = ""; // 录入信息条数
	var pub_list = ""; // 已发信息条数
	
	var max_cnt = 10;
	for(var i=0; i<beanList.size(); i++){
		var bean = beanList.get(i);
		bean = List.toJSList(bean);
		user_site_list +=',"' + bean.get(1)+'-'+bean.get(0) +'"';
		total_list +=',' +  bean.get(2) ;
		max_cnt = bean.get(2) > max_cnt ? parseInt(bean.get(2))+10: max_cnt;	
		pub_list +=',' +  bean.get(3) ;
	}
	
	var steps = Math.floor(max_cnt/20);
	if(steps>10){
      var m_n = steps%10;
      steps = steps - m_n;
	}
	
	user_site_list =   user_site_list.substring(1); // 用户信息总数,字符串列表
	total_list   =     total_list.substring(1); // 录入信息总数,字符串列表
	pub_list   =     pub_list.substring(1); // 已发条数,字符串列表
	
	chartJsonData = '{   '+     
		'"title": { "text": "工作量排行信息"},'+
		 '"y_legend":{'+
    		'"text": "Count",'+
    		'"style": "{color: #736AFF; font-size: 12px;}"'+
  		'},'+
  		'"x_legend":{'+
    	'	"text": "录入人-站点",'+
    	'	"style": "{color: #736AFF; font-size: 12px;}"'+
  		'},   "x_axis":{ "labels": {"labels": [' + user_site_list +']}},'+
		' "y_axis":{ "max":' +  max_cnt  +',  "min": 0 ,"steps": '+steps+' },' +
		' "elements": [{ "type": "bar","colour":"#d01f3c" ,"values": ['+total_list+'] , "text" : "录入信息总数"},' + 
		'{ "type": "bar","colour":"#356aa0" ,"values": ['+pub_list+'] , "text" : "已发条数"}' + 
		
		']}';

	 var wid = document.body.clientWidth;
	swfobject.embedSWF("/sys/js/open-flash-chart/open-flash-chart.swf", "chartContainer", wid, "300", "9.0.0",null, null,{wmode:'transparent'});
}

function open_flash_chart_data() { 
		return	chartJsonData;
	} 
