var CategoryRPC = jsonrpc.CategoryRPC;
var CategoryBean = new Bean("com.deya.wcm.bean.cms.category.CategoryBean",true);
var CmsCountRPC = jsonrpc.CmsCountRPC;

var beanList = null;
var con_m = new Map();

var isContainRef = false; // 初始化目录树标记
var isContainLink = false; // 初始化"查看方式"改变事件标记

//var is_host = "0"; // 默认只统计主信息
var user_name="";

var chartJsonData = ""; // bar图时的json字符串

// 显示统计结果列表
function showList(){
	
	//得到父页面中设置的参数 --- start
	//var start_day = top.getCurrentFrameObj().$("#start_day").val();
	//var end_day = top.getCurrentFrameObj().$("#end_day").val();
	
	var mp = new Map();
	/*
	// 不包含引用信息
	if(!top.getCurrentFrameObj().$("#ref_info").is(':checked')){
		// 不包含引用也不包含链接信息
		if(!top.getCurrentFrameObj().$("#link_info").is(':checked')){
			mp.put("is_host","0");
		}else{ // 不包含引用,包含连接
			mp.put("is_host","0,2");
		}
	} else {
		// 包含引用,不包含连接
		if(!top.getCurrentFrameObj().$("#link_info").is(':checked')){
			mp.put("is_host","0,1");
		}else{	// 包含引用和连接
			mp.remove("is_host");
		}
	}*/
	
	// 判断栏目选择信息
	//var statu = top.getCurrentFrameObj().$("#all_cat_ids").is(':checked');
	//if(statu) {
		
	//}else{
		//selected_ids = top.getCurrentFrameObj().selected_ids;
		if(selected_ids == ""){
			//top.msgWargin("目录信息不能为空!");
			//return;
		}else{
			mp.put("cat_ids",selected_ids);
		}
	//}
	mp.put("site_id",site_id);
	// mp.put("cat_id",cat_id);  // TODO
	mp.put("start_day",start_day);
	mp.put("end_day",end_day + " 23:59:59");
	mp.put("input_user",input_user);
	mp.put("user_id", input_user);
	mp.put("is_host", is_host);
	//alert(mp);
	//得到父页面中设置的参数 --- end
	//alert(LoginUserBean.user_id);
	/*  
	beanList = CmsCountRPC.getInputCountListByUserID(mp);
	beanList = List.toJSList(beanList);
	//alert(beanList);
	createTable();
	// chart处理
	setBarChart();
	*/
	beanList = CmsCountRPC.getInputCountListByUserIDCate(mp);
	beanList = List.toJSList(beanList);
	createTableCate();
}

// 生成内容表格
function createTable(){
	$("#countList").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th width='30%'>栏目</th>		" +
    "   <th width='20%'>全部信息</th>       " +
    "   <th width='15%'>已发信息</th>   " +
    "   <th width='15%'>待审信息</th>   " +
    "   <th width='15%'>退稿信息</th>   " +
    "   <th width='15%'>草稿信息</th>   " +
    "   <th width='15%'>图片信息</th>   " +
    "	<th width='20%'><nobr>发稿率</nobr></th> " +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	if(beanList.size() != 0) {
		for(var i=0;i<beanList.size();i++){
			treeHtmls += stuffTable(beanList.get(i));
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
 	 	"<td colspan=\"4\"></td>"+ " </tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"4\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"4\"></td>"+" </tfoot>";
	}
	$("#countList").append(treeHtmls);
	//iniTreeTable("countList");
}

// 填充每一列的内容
function stuffTable(bean){

	var treeHtml = "";
	var type_calss= "";

	treeHtml+="<tr> <td><span class='file'>"+ bean.cat_name +"</span></td>";
    treeHtml += "<td>"+ bean.inputCount +"</td>";
    treeHtml += "<td>"+ bean.releasedCount +"</td>";
    treeHtml += "<td>"+ bean.checkCount +"</td>";
    treeHtml += "<td>"+ bean.backCount +"</td>";
    treeHtml += "<td>"+ bean.caogaoCount +"</td>";
    treeHtml += "<td>"+ bean.picReleasedCount +"</td>";
    treeHtml += "<td>"+ bean.releaseRate +"%</td>";
    // treeHtml += "<td  align='left'><span onclick='openPieChart("+bean.input_user+")'>"+ "查看"+"</span></td>";
	treeHtml+="</tr>";

	return treeHtml;
}

function openPieChart(input_user) {
	//top.getCurrentFrameObj().pieJsonData = 	pieChartDataList.get(index);
	top.OpenModalWindow("查看详细信息","/sys/zwgk/count/chart.html",475,470);
	
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
	var wid = document.body.clientWidth - 112;
	chartJsonData = "";
	if(beanList.size() == 0)
	{
		chartJsonData = '{"title": { "text": "工作量考核"}}';
		swfobject.embedSWF("/sys/js/open-flash-chart/open-flash-chart.swf", "chart", wid, "400", "9.0.0"); 
		return "";
	}
	var cnt_name_list = ""; // 统计名称列
	var total_cnt_list = ""; // 信息总数
	var p_cnt_list = ""; // 发布信息数目
	
	var max_cnt = 10; // 设置Y轴的标度
	
	for(var i=0; i<beanList.size(); i++){
		var bean = beanList.get(i);
		
		cnt_name_list +=',"' + bean.cat_name.replace(/\"/g,'\\"') +'"';
		
		total_cnt_list +=',' +  bean.inputCount +'';
		max_cnt = bean.inputCount>max_cnt ? bean.inputCount+10: max_cnt;
		
		p_cnt_list +=',' +  bean.releasedCount +'';
	
	}
	
	var steps = Math.floor(max_cnt/20);
	if(steps>10){
      var m_n = steps%10;
      steps = steps - m_n;
	}
	
	cnt_name_list =   cnt_name_list.substring(1);
	total_cnt_list =   total_cnt_list.substring(1);
	p_cnt_list   =     p_cnt_list.substring(1);
	
	chartJsonData = '{   '+     
		'"title": { "text": "站点信息统计"},'+
		 '"y_legend":{'+
    		'"text": "Count",'+
    		'"style": "{color: #736AFF; font-size: 12px;}"'+
  		'},'+
  		'"x_legend":{'+
    	'	"text": "站内栏目",'+
    	'	"style": "{color: #736AFF; font-size: 12px;}"'+
  		'},   "x_axis":{ "labels": {"labels": [' + cnt_name_list +
		']}}, "y_axis":{ "max": '+  max_cnt  +',  "min": 0,"steps": '+steps+'   },'+
		'"elements": [{ "type": "bar","colour":"#d01f3c" ,"values": ['+total_cnt_list+'] , "text" : "信息总数"},' + 
		'{ "type": "bar","colour":"#356aa0" ,"values": ['+p_cnt_list+'] , "text" : "已发信息数目"}' +
		']}';
	swfobject.embedSWF("/sys/js/open-flash-chart/open-flash-chart.swf", "chart", wid, "400", "9.0.0",null, null,{wmode:'transparent'});
}

function open_flash_chart_data() {
	return chartJsonData;
}



//生成内容表格
function createTableCate(){
	$("#countList").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th width='30%'>栏目</th>		" +
    "   <th width='10%'>全部信息</th>       " +
    "   <th width='10%'>已发信息</th>   " +
    "   <th width='10%'>待审信息</th>   " +
    "   <th width='10%'>退稿信息</th>   " +
    "   <th width='10%'>草稿信息</th>   " +
    "   <th width='10%'>图片信息</th>   " +
    "	<th width='10%'>发稿率</th> " +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	//alert(beanList.size());
	if(beanList.size() != 0) {
		for(var i=0;i<beanList.size();i++){
			if(i==0){
				treeHtmls += setTreeNode(beanList.get(i), "");
			}else
			if(i==1){
				treeHtmls += setTreeNodeZt(beanList.get(i), "");
			}
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
 	 	"<td colspan=\"4\"></td>"+ " </tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"4\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"4\"></td>"+" </tfoot>";
	}
	$("#countList").append(treeHtmls);
	iniTreeTable("countList");
}

//组织树列表选项字符串
function setTreeNode(bean, parent_id)
{			
	//var index = pieChartDataList.size() + 1;
	var treeHtml = "";
	
	if(bean.is_leaf)
	{		
		var type_calss= "";
		if(parent_id !=""){  
		      type_calss = "class='child-of-node-"+parent_id+"'"
		}
		//alert("parent_id=="+parent_id+":::is_leaf==" + bean.cat_id);
		treeHtml+="<tr id='node-"+bean.cat_id+"' "+type_calss+" > <td><span class='file'>"+bean.cat_name+"</span></td>";
        treeHtml += setHandlList(bean);
		treeHtml+="</tr>";
	}
	else 
	{
		var type_calss = "";
		if(parent_id !=""){  
		      type_calss = "class='child-of-node-"+parent_id+"'"
		}
		//alert("parent_id=="+parent_id+":::not is_leaf==" + bean.cat_id);
		treeHtml+="<tr id='node-"+bean.cat_id+"' "+type_calss+" > <td><span class='folder'>"+bean.cat_name+"</span></td>";
		treeHtml += setHandlList(bean);
		treeHtml+="</tr>";
		var child_list = bean.child_list;
		  child_list = List.toJSList(child_list);
		  if(child_list.size() > 0)
		  { 
			 for(var i=0;i<child_list.size();i++)
			 {						
				treeHtml += setTreeNode(child_list.get(i), bean.cat_id+"");
			 }
		  }
	}
	return treeHtml;
}


//组织树列表选项字符串
function setTreeNodeZt(bean, parent_id)
{			
	//var index = pieChartDataList.size() + 1;
	var treeHtml = "";
	
	if(bean.is_leaf)
	{		
		var type_calss= "";
		if(parent_id !=""){  
		      type_calss = "class='child-of-nodeZt-"+parent_id+"'"
		}
		//alert("parent_id=="+parent_id+":::is_leaf==" + bean.cat_id);
		treeHtml+="<tr id='nodeZt-"+bean.cat_id+"' "+type_calss+" > <td><span class='file'>"+bean.cat_name+"</span></td>";
        treeHtml += setHandlList(bean);
		treeHtml+="</tr>";
	}
	else 
	{
		var type_calss = "";
		if(parent_id !=""){  
		      type_calss = "class='child-of-nodeZt-"+parent_id+"'"
		}
		//alert("parent_id=="+parent_id+":::not is_leaf==" + bean.cat_id);
		treeHtml+="<tr id='nodeZt-"+bean.cat_id+"' "+type_calss+" > <td><span class='folder'>"+bean.cat_name+"</span></td>";
		treeHtml += setHandlList(bean);
		treeHtml+="</tr>";
		var child_list = bean.child_list;
		  child_list = List.toJSList(child_list);
		  if(child_list.size() > 0)
		  { 
			 for(var i=0;i<child_list.size();i++)
			 {						
				treeHtml += setTreeNodeZt(child_list.get(i), bean.cat_id+"");
			 }
		  }
	}
	
	return treeHtml;
}


// 组织统计的数据
function setHandlList(bean)
{
	var str = "";
	if(bean)
	{
		//cat_id;releasedCount 发布信息条数;picReleasedCount;input_user  用户id
		if(bean.is_leaf)
		{
			str+="<td><a href=\"javascript:openUInfoList("+bean.cat_id+")\"><div style='color:red;'>"+bean.inputCount+"</div></a></td>";
			str+="<td>"+bean.releasedCount+"</td>";
            str+="<td>"+bean.checkCount+"</td>";
            str+="<td>"+bean.backCount+"</td>";
            str+="<td>"+bean.caogaoCount+"</td>";
            str+="<td>"+bean.picReleasedCount+"</td>";
			str+="<td>"+bean.releaseRate +"</td>";
		}else{
			str+="<td>"+bean.inputCount+"</td>";
			str+="<td>"+bean.releasedCount+"</td>";
            str+="<td>"+bean.checkCount+"</td>";
            str+="<td>"+bean.backCount+"</td>";
            str+="<td>"+bean.caogaoCount+"</td>";
            str+="<td>"+bean.picReleasedCount+"</td>";
			str+="<td>"+bean.releaseRate+"</td>";
		}
	}
	return str;
}

function openUInfoList(cat_id)
{
  top.OpenModalWindow("详细信息列表","/sys/cms/cmsCount/UserInfoList.jsp?site_id="+site_id+"&input_user="+input_user+"&start_day="+start_day+"&end_day="+end_day+"&is_host="+is_host+"&cat_id="+cat_id,1000,600);
}