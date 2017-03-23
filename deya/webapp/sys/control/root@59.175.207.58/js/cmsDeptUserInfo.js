var CategoryRPC = jsonrpc.CategoryRPC;
var CategoryBean = new Bean("com.deya.wcm.bean.cms.category.CategoryBean",true);
var CmsCountRPC = jsonrpc.CmsCountRPC;

var beanList = null;
var con_m = new Map();

var chartJsonData = ""; // bar图时的json字符串

// 显示统计结果列表
function showList(){
	
	//得到父页面中设置的参数 --- start
	var start_day = getCurrentFrameObj().$("#start_day").val();
	var end_day = getCurrentFrameObj().$("#end_day").val();
	
	var mp = new Map();
	// 判断栏目选择信息
	var statu = getCurrentFrameObj().$("#all_cat_ids").is(':checked');
	if(statu) {
		mp.remove("site_ids");
	}else{
		if(getCurrentFrameObj().selected_ids== ""){
			msgWargin("请选择站点!");
			return;
		}else{
			mp.put("site_ids",getCurrentFrameObj().selected_ids);
		}
	} 
	
	mp.put("start_day",start_day);
	mp.put("end_day",end_day + " 23:59:59");
	mp.put("dept_id", dept_id);
	//得到父页面中设置的参数 --- end
	
	beanList = jsonrpc.SiteCountRPC.getSiteCountListByInputUser(mp);
	beanList = List.toJSList(beanList);
	//alert(beanList);

	createTable();
	
	// chart处理
	setBarChart();
}

// 生成内容表格
function createTable(){
	$("#countList").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th width='15%'>人员</th>		" +
    "   <th width='15%'>部门</th>		" +
    "   <th width='10%'>全部信息</th>       " +
    "   <th width='10%'>主信息</th>   " +
    "	<th width='10%'>引用信息</th> " +
    "	<th width='10%'>链接信息</th>     " +
    "	<th width='10%'>已发信息</th>           " +
    "	<th width='10%'>发稿率</th>           " +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	if(beanList.size() != 0) {
		for(var i=0;i<beanList.size();i++){
			treeHtmls += stuffTable(beanList.get(i));
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
 	 	"<td colspan=\"8\"></td>"+ " </tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"8\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"8\"></td>"+" </tfoot>";
	}
	$("#countList").append(treeHtmls);
	//iniTreeTable("countList");
}

// 填充每一列的内容
function stuffTable(bean){
	var treeHtml = "";
	var type_calss= "";
	treeHtml +="<tr> <td>"+bean.user_name+"</td>";
	treeHtml += "<td>"+ bean.dept_name +"</td>";
	treeHtml +="<td>"+bean.inputCount +"</td>";
	treeHtml +="<td>"+bean.hostInfoCount +"</td>";
	treeHtml +="<td>"+bean.refInfoCount +"</td>";
	treeHtml +="<td>"+bean.linkInfoCount +"</td>";
	treeHtml +="<td>"+bean.releasedCount +"</td>";
	treeHtml +="<td>"+bean.releaseRate +"</td>";
	treeHtml+="</tr>";
	return treeHtml;
}


// 生成柱状图
function setBarChart(){
	var wid = document.body.clientWidth - 20;
	chartJsonData = "";
	if(beanList.size() == 0)
	{
		chartJsonData = '{"title": { "text": "工作量考核"}}';
		//swfobject.embedSWF("/sys/js/open-flash-chart/open-flash-chart.swf", "chart", wid, "400", "9.0.0"); 
		return "";
	}
	var cnt_name_list = ""; // 统计人员名称列
	var total_cnt_list = ""; // 信息总数
	var z_cnt_list = ""; // 主信息数目
	var y_cnt_list = ""; // 引用信息
	var l_cnt_list = ""; // 链接信息
	var p_cnt_list = ""; // 已发信息
	
	var max_cnt = 10; // 设置Y轴的标度
	
	for(var i=0; i<beanList.size(); i++){
		var bean = beanList.get(i);
		
		cnt_name_list +=',"' + bean.user_name.replace(/\"/g,'\\"') +'"';
		
		total_cnt_list +=',' +  bean.inputCount +'';
		max_cnt = bean.inputCount>max_cnt ? bean.inputCount+10: max_cnt;
		
		z_cnt_list +=',' +  bean.hostInfoCount +'';
		y_cnt_list +=',' +  bean.refInfoCount +'';
		l_cnt_list +=',' +  bean.linkInfoCount +'';
		p_cnt_list +=',' +  bean.releasedCount +'';
	
	}
	
	var steps = Math.floor(max_cnt/20);
	if(steps>10){
      var m_n = steps%10;
      steps = steps - m_n;
	}
	
	cnt_name_list =   cnt_name_list.substring(1);
	total_cnt_list =   total_cnt_list.substring(1);
	z_cnt_list =   z_cnt_list.substring(1);
	y_cnt_list =   y_cnt_list.substring(1);
	l_cnt_list =   l_cnt_list.substring(1);
	p_cnt_list   =     p_cnt_list.substring(1);
	
	chartJsonData = '{   '+     
		'"title": { "text": "人员信息统计"},'+
		 '"y_legend":{'+
    		'"text": "Count",'+
    		'"style": "{color: #736AFF; font-size: 12px;}"'+
  		'},'+
  		'"x_legend":{'+
    	'	"text": "站内栏目",'+
    	'	"style": "{color: #736AFF; font-size: 12px;}"'+ 
  		'},   "x_axis":{ "labels": {"labels": [' + cnt_name_list +
		']}}, "y_axis":{ "max": '+  max_cnt  +',  "min": 0,"steps": '+steps+'   },'+
		'"elements": [{ "type": "bar","colour":"#d01f3c" ,"values": ['+total_cnt_list+'] , "text" : "信息总数","tip":"#key# #val#"},' + 
		'{ "type": "bar","colour":"#9966FF" ,"values": ['+z_cnt_list+'] , "text" : "主信息","tip":"#key# #val#"},' +
		'{ "type": "bar","colour":"#99CC66" ,"values": ['+y_cnt_list+'] , "text" : "引用信息","tip":"#key# #val#"},' +
		'{ "type": "bar","colour":"#666600" ,"values": ['+l_cnt_list+'] , "text" : "链接信息","tip":"#key# #val#"},' +
		'{ "type": "bar","colour":"#356aa0" ,"values": ['+p_cnt_list+'] , "text" : "已发信息数目","tip":"#key# #val#"}' +
		']}';
	swfobject.embedSWF("/sys/js/open-flash-chart/open-flash-chart.swf", "chart", wid, "400", "9.0.0",null, null,{wmode:'transparent'});
}

function open_flash_chart_data() {
	return chartJsonData;
}