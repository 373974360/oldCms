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
	//mp.put("cat_id",cat_id);
	mp.put("cat_position","$"+cat_id+"$");

	b_start_day = $("#start_day").val();
	b_end_day = $("#end_day").val()+" 23:59:59";

	mp.put("start_day",b_start_day);
	mp.put("end_day",b_end_day);
    mp.put(v_type,"bycat");

	beanList = top.jsonrpc.CmsCountSourceRPC.getInfoCountListBySource(mp);
	beanList = List.toJSList(beanList);
	
	createTable(beanList);


	// chart处理
	setBarChart();
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
		    listHeader.add("信息来源");
			listHeader.add("全部信息");
		    listHeader.add("主信息");
			listHeader.add("引用信息"); 
		    listHeader.add("链接信息"); 
			//alert(beanList);
		var url = jsonrpc.CmsCountSourceRPC.cmsInfoOutExcel(beanList,listHeader);
		location.href=url;  
	}
}

// 生成统计页table
function createTable(beanList)
{
	$("#countList").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th>信息来源</th>		" +
    "   <th>全部信息</th>     " +
    "	<th>主信息</th>   " +
	"	<th>引用信息</th>   " +
    "	<th>链接信息</th>   " +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	if(beanList.size() != 0) {
		for(var i=0;i<beanList.size();i++){
			treeHtmls += stuffTable(beanList.get(i));
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
 	 	"<td colspan=\"5\"></td>"+ "</tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"5\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"3\"></td>"+" </tfoot>";
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
		treeHtml+="<td>"+ bean.allCount+"</td>";
		treeHtml+="<td>"+ bean.hostInfoCount+"</td>";
		treeHtml+="<td>"+ bean.refInfoCount+"</td>";
		treeHtml+="<td>"+ bean.linkInfoCount+"</td>";
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


// 设置柱状图显示
function setBarChart()
{
	 var wid = document.body.clientWidth - 212;
	chartJsonData = "";
	if(beanList.size() == 0)
	{
		//alert('空');
		chartJsonData = '{"title": { "text": "站点信息统计"}}';
		//swfobject.embedSWF("/sys/js/open-flash-chart/open-flash-chart.swf", "chart", wid, "400", "9.0.0"); 
		return "";
	}
	var cnt_name_list = ""; // 统计名称列
	var total_cnt_list = ""; // 信息总数
	var z_cnt_list = ""; // 主信息数目
	var y_cnt_list = ""; // 引用信息数目
	var l_cnt_list = ""; // 连接信息数目
	
	var max_cnt = 10;
	
	// 根据类型设置不同的列名称,如:按栏目,按时间
	//var v_type = $(":radio[name='viewClass'][checked]").val();
	for(var i=0; i<beanList.size(); i++){
		var bean = beanList.get(i);
		cnt_name_list +=',"' + bean.cat_name +'"';
		total_cnt_list +=',' +  bean.allCount +'';
		max_cnt = bean.allCount>max_cnt ? bean.allCount+10: max_cnt;
		z_cnt_list +=',' +  bean.hostInfoCount +'';
		y_cnt_list +=',' +  bean.refInfoCount  +'';
		l_cnt_list +=',' +  bean.linkInfoCount +'';
	}
	
	cnt_name_list =   cnt_name_list.substring(1);
	total_cnt_list =   total_cnt_list.substring(1);
	z_cnt_list   =     z_cnt_list.substring(1);
	y_cnt_list   =     y_cnt_list.substring(1);
	l_cnt_list   =     l_cnt_list.substring(1);
	
	  var steps = Math.floor(max_cnt/20);
	  if(steps>10){
        var m_n = steps%10;
        steps = steps - m_n;
	  }
	
    var strclass = "";
    if(beanList.size()>5){
		strclass = '"rotate":"45","vertical":"true",';
	}
//alert(strclass);
	chartJsonData = '{   '+     
		'"title": { "text": "站点信息统计"},'+
		 '"y_legend":{'+
    		'"text": "信息数目",'+
    		'"style": "{color: #736AFF; font-size: 12px;}"'+
  		'},'+
  		'"x_legend":{'+
    	'	"text": "信息来源",'+
    	'	"style": "{color: #736AFF; font-size: 12px;}"'+
  		'},   "x_axis":{ "labels": {'+strclass+'"labels": [' + cnt_name_list +
		']}}, "y_axis":{ "max": '+  max_cnt  +',  "min": 0,"steps": '+steps+'  },'+
		'"elements": [{ "type": "bar","colour":"#d01f3c" ,"values": ['+total_cnt_list+'] , "text" : "信息总数"},' + 
		'{ "type": "bar","colour":"#356aa0" ,"values": ['+z_cnt_list+'] , "text" : "主信息数目"},' + 
		'{ "type": "bar","colour":"#C79810" ,"values": ['+y_cnt_list+'] , "text" : "引用信息数目"},' + 
		'{ "type": "bar","colour":"#73880A" ,"values": ['+l_cnt_list+'] , "text" : "连接信息数目"}' +
		']}';
	swfobject.embedSWF("/sys/js/open-flash-chart/open_flash_chart.swf", "chart", wid, "400", "9.0.0",null, null,{wmode:'transparent'}); 
}


function open_flash_chart_data() {
	return chartJsonData;
} 