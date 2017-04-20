var CategoryRPC = jsonrpc.CategoryRPC;
var CategoryBean = new Bean("com.deya.wcm.bean.cms.category.CategoryBean",true);
var CmsCountRPC = jsonrpc.CmsCountRPC;

var beanList = null;
var con_m = new Map();

var isContainRef = false; // 初始化目录树标记
var isContainLink = false; // 初始化"查看方式"改变事件标记

var is_host = "0"; // 默认只统计主信息

var chartJsonData = ""; // bar图时的json字符串

// 显示统计结果列表
function showList(){
	
	start_day = $("#start_day").val();
	end_day = $("#end_day").val();
	
	var mp = new Map();
	// 不包含引用信息
	if(!$("#ref_info").is(':checked')){
		// 不包含引用也不包含链接信息
		if(!$("#link_info").is(':checked')){
			is_host = "0";
			mp.put("is_host","0");
		}else{ // 不包含引用,包含连接
			is_host = "0,2";
			mp.put("is_host","0,2");
		}
	} else {
		// 包含引用,不包含连接
		if(!$("#link_info").is(':checked')){
			is_host = "0,1";
			mp.put("is_host","0,1");
		}else{	// 包含引用和连接
			is_host = "0,1,2";
			mp.remove("is_host");
		}
	}
	
	// 判断栏目选择信息
	var statu = $("#all_cat_ids").is(':checked');
	if(statu) {
		
	}else{
		if(selected_ids== ""){
			msgWargin("目录信息不能为空!");
			return;
		}else{
			mp.put("cat_ids",selected_ids);
		}
	}
	
	mp.put("site_id",site_id);
	// mp.put("cat_id",cat_id);  // TODO
	mp.put("start_day",start_day + " 00:00:00");
	mp.put("end_day",end_day + " 23:59:59");
	
	beanList = CmsCountRPC.getInputCountList(mp);
	beanList = List.toJSList(beanList);
	
	createTable();
	
	// chart处理
	setBarChart();
	
	$("#outFileBtn").show();  
}


//导出
function  outFileBtn(){
	if(beanList.size() != 0) {
		var listHeader = new List();
		listHeader.add("录入员");
		listHeader.add("全部信息");
		listHeader.add("已发信息");
		listHeader.add("图片信息");
		listHeader.add("发稿率");
		var url = CmsCountRPC.cmsWorkInfoOutExcel(beanList,listHeader);
		location.href=url;  
	}
}

// 生成内容表格
function createTable(){
	$("#countList").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th>录入员</th>		" +
    "   <th>全部信息</th>       " +
    "   <th>已发信息</th>   " +
    "   <th>图片信息</th>   " +
    "	<th>发稿率</th> " +
    "	<th>详细</th>     " +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	if(beanList.size() != 0) {
		for(var i=0;i<beanList.size();i++){
			treeHtmls += stuffTable(beanList.get(i));
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
 	 	"<td colspan=\"5\"></td>"+ " </tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"5\" width=\"1032\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"5\"></td>"+" </tfoot>";
	}
	$("#countList").append(treeHtmls);
	iniTreeTable("countList");
}

// 填充每一列的内容
function stuffTable(bean){
	var treeHtml = "";
	var type_calss= "";
	treeHtml+="<tr> <td><span class='file'>"+ bean.user_name +"</span></td>";
    treeHtml += "<td>"+ bean.inputCount +"</td>";
    treeHtml += "<td>"+ bean.releasedCount +"</td>";
    treeHtml += "<td>"+ bean.picInfoCount +"</td>";
    if(bean.releaseRate == "�"){
        treeHtml += "<td>-</td>";
	}else{
        treeHtml += "<td>"+ bean.releaseRate +"%</td>";
	}
    treeHtml += "<td><a href=\"javascript:openPieChart("+bean.input_user+",'"+bean.user_name+"')\">"+ "查看"+"</a></td>";
   // treeHtml += "<td  align='left'><span onclick='openPieChart("+bean.input_user+")'>"+ "查看"+"</span></td>";
	treeHtml+="</tr>";
	return treeHtml;
} 

function openPieChart(input_user,user_name){
	//getCurrentFrameObj().pieJsonData = 	pieChartDataList.get(index);
	//OpenModalWindow("查看详细信息---"+user_name,"/sys/cms/cmsCount/cmsAssessUserInfo.jsp?site_id="+site_id+"&input_user="+input_user,1000,600);
	addTab(true,"/sys/cms/cmsCount/cmsAssessUserInfo.jsp?site_id="+site_id+"&input_user="+input_user+"&start_day="+start_day+"&end_day="+end_day+"&selectedId="+selected_ids+"&is_host="+is_host,"查看详细信息---"+user_name);
}

// 处理全选事件
function allSelected(){
	var statu = $("#all_cat_ids").is(':checked');
	if(statu){
		$("#getCateIDS").attr("disabled","disabled");
		$("#selectedCateNames").attr("disabled","disabled");
	}else{
		$("#getCateIDS").removeAttr("disabled");
		$("#selectedCateNames").removeAttr("disabled");
	}
}

// 生成柱状图
function setBarChart(){
	var wid = "100%";
	chartJsonData = "";
	if(beanList.size() == 0)
	{
		chartJsonData = '{"title": { "text": "工作量考核"}}';
		//swfobject.embedSWF("/sys/js/open-flash-chart/open-flash-chart.swf", "chart", wid, "400", "9.0.0"); 
		return "";
	}
	var cnt_name_list = ""; // 统计名称列
	var total_cnt_list = ""; // 信息总数
	var p_cnt_list = ""; // 发布信息数目
	var pic_cnt_list = ""; // 发布信息数目 并且有图片的信息
	
	var max_cnt = 10; // 设置Y轴的标度
	
	for(var i=0; i<beanList.size(); i++){
		var bean = beanList.get(i);
		
		cnt_name_list +=',"' + bean.user_name +'"';
		
		total_cnt_list +=',' +  bean.inputCount +'';
		max_cnt = bean.inputCount>max_cnt ? bean.inputCount+10: max_cnt;
		
		p_cnt_list +=',' +  bean.releasedCount +'';
	
		pic_cnt_list +=',' +  bean.picInfoCount +'';
	}
	
	var steps = Math.floor(max_cnt/20);
	if(steps>10){
      var m_n = steps%10;
      steps = steps - m_n;
	}
	
	cnt_name_list =   cnt_name_list.substring(1);
	total_cnt_list =   total_cnt_list.substring(1);
	p_cnt_list   =     p_cnt_list.substring(1);
	pic_cnt_list   =     pic_cnt_list.substring(1);
	
	chartJsonData = '{   '+     
		'"title": { "text": "站点信息统计"},'+
		 '"y_legend":{'+
    		'"text": "Count",'+
    		'"style": "{color: #736AFF; font-size: 12px;}"'+
  		'},'+
  		'"x_legend":{'+
    	'	"text": "录入员",'+
    	'	"style": "{color: #736AFF; font-size: 12px;}"'+
  		'},   "x_axis":{ "labels": {"labels": [' + cnt_name_list +
		']}}, "y_axis":{ "max": '+  max_cnt  +',  "min": 0,"steps": '+steps+'   },'+
		'"elements": [{ "type": "bar","colour":"#d01f3c" ,"values": ['+total_cnt_list+'] , "text" : "信息总数"},' + 
		'{ "type": "bar","colour":"#356aa0" ,"values": ['+p_cnt_list+'] , "text" : "已发信息数目"},' +
		'{ "type": "bar","colour":"#996600" ,"values": ['+pic_cnt_list+'] , "text" : "图片信息"}' +
		']}';
	swfobject.embedSWF("/sys/js/open-flash-chart/open-flash-chart.swf", "chart", wid, "400", "9.0.0",null, null,{wmode:'transparent'});
}

function open_flash_chart_data() {
	return chartJsonData;
}