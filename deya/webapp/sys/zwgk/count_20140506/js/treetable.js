var GKCountRPC = jsonrpc.GKCountRPC;

var pieChartDataList = new List();

//**************************************所有站点公开信息统计***********************************************/
// 取得所有公开站点的信息数量
function createTableAllSite()
{
	// 初始化Table
	// TODO 添加节点名称
	//alert(node_ids);
	beanList = GKCountRPC.getAllSiteGKCountList(start_day, end_day,node_ids);
	beanList = List.toJSList(beanList);
	
	//传给父页面list
	parent.beanListResult = beanList;
	
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
			treeHtmls += setTreeNodeAllSite(beanList.get(i), "",i);
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
 	 	"<td colspan=\"6\"></td>"+ " </tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"6\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"6\"></td>"+" </tfoot>";
	}
	$("#treeTableCount").append(treeHtmls);
	iniTreeTable("treeTableCount");
	addOMouseClass();


}

function addOMouseClass()
{
	$("span.file,span.folder").each(function(i){
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

//组织树列表选项字符串
function setTreeNodeAllSite(bean, parent_id,index)
{
	var treeHtml = "";
	
	if(bean.is_leaf)
	{		
		var type_calss= "";
		if(parent_id !=""){  
		      type_calss = "class='child-of-node-"+parent_id+"'"
		}
		treeHtml+="<tr id='node-"+bean.site_id+"' "+type_calss+" > <td><span class='file' ><a href=\"javascript:redirectURL('"+bean.site_id+"','"+bean.site_name+"')\">"+bean.site_name+"</a></span></td>";
        treeHtml += setHandlListAllSite(bean, index);
		treeHtml+="</tr>";
	}
	else 
	{
		var type_calss = "";
		if(parent_id !=""){  
		      type_calss = "class='child-of-node-"+parent_id+"'"
		}   
		treeHtml+="<tr id='node-"+bean.site_id+"' "+type_calss+" > <td><span class='folder' onclick=\"redirectURL('"+bean.site_id+"','"+bean.site_name+"')\">"+bean.site_name+"</span></td>";
		treeHtml += setHandlListAllSite(bean, index);
		treeHtml+="</tr>";
		var child_list = bean.child_list;
		  child_list = List.toJSList(child_list);
		  if(child_list.size() > 0)
		  { 
			 for(var i=0;i<child_list.size();i++)
			 {						
				treeHtml += setTreeNodeAllSite(child_list.get(i), bean.site_id+"",i);
			 }
		  }
	}
	
	return treeHtml;
}


// 组织统计的数据
function setHandlListAllSite(bean, index)
{
	var str = "";
	if(bean)
	{
		str+="<td>"+bean.info_count +"</td>";
		str+="<td>"+bean.z_count +"</td>";
		str+="<td>"+bean.y_count +"</td>";
		str+="<td>"+bean.b_count +"</td>";
		str+="<td><a href='javascript:openPieChart("+index+")'>"+"查看"+"</a></td>";
	}
	return str;
}

// 跳转到站点下的栏目详细统计页面
function redirectURL(bean_site_id,site_name)
{
	top.addTab(true ,"/sys/zwgk/count/gkCountList.jsp?start_day="+start_day+"&end_day="+end_day+"&site_id="
		+bean_site_id+"&app_id=\"\"&type=back",site_name+"栏目统计");
}
//**************************************所有站点公开信息统计***********************************************/

/********************************** 根据站点取得栏目工作量统计 ***************************************/
function createTable()
{
	// 初始化Table
	if(end_day.length<=10){
		end_day = end_day+" 59:59:59";
	}
	beanList = GKCountRPC.getGKCountListByDate(site_id, start_day, end_day);
	beanList = List.toJSList(beanList);
	//alert(beanList);
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th>公开栏目</th>		" +
    "   <th>信息总数</th>       " +
    "   <th>主动公开数目</th>   " +
    "	<th>依申请公开数目</th> " +
    "	<th>不公开数目</th>     " +
    "	<th>图表</th>           " +
   " </tr>" +
  "</thead>" +
  "<tbody>" ;
  if(beanList.size() != 0) {
	  
	//传给父页面list
	//parent.beanListResult = beanList;
	parent.site_id = site_id;
	parent.startDate = start_day;
	parent.endDate = end_day;
	  
	 // 清理pie图的json字符串 缓存
	pieChartDataList.clear();
	for(var i=0;i<beanList.size();i++){
		treeHtmls += setTreeNode(beanList.get(i), "");
	}
	treeHtmls += "</tbody>"+
	"<tfoot>" +
  	"<td colspan=\"6\"></td>"+ " </tfoot>";
 	} else {
		treeHtmls += "<tr><td colspan=\"6\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"6\"></td>"+" </tfoot>";
	}
	$("#treeTableCount").append(treeHtmls);
	iniTreeTable("treeTableCount");
}


//组织树列表选项字符串
function setTreeNode(bean, parent_id)
{			
	var index = pieChartDataList.size() + 1;
	var treeHtml = "";
	
	if(bean.is_leaf)
	{		
		var type_calss= "";
		if(parent_id !=""){  
		      type_calss = "class='child-of-node-"+parent_id+"'"
		}
		treeHtml+="<tr id='node-"+bean.cat_id+"' "+type_calss+" > <td><span class='file'>"+bean.cat_name+"</span></td>";
        treeHtml += setHandlList(bean, index++);
		treeHtml+="</tr>";
	}
	else 
	{
		var type_calss = "";
		if(parent_id !=""){  
		      type_calss = "class='child-of-node-"+parent_id+"'"
		}   
		treeHtml+="<tr id='node-"+bean.cat_id+"' "+type_calss+" > <td><span class='folder'>"+bean.cat_name+"</span></td>";
		treeHtml += setHandlList(bean, index++);
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


// 组织统计的数据
function setHandlList(bean,index)
{
	var str = "";
	index = index-1;
	if(bean)
	{
		addPieChartList(bean);
		str+="<td>"+bean.info_count +"</td>";
		str+="<td>"+bean.z_count +"</td>";
		str+="<td>"+bean.y_count +"</td>";
		str+="<td>"+bean.b_count +"</td>";
		str+="<td><a href='javascript:openPieChart("+index+")'>"+"查看"+"</a></td>";
	}
	return str;
}

// *****************生成pie图的js代码段******************************
function openPieChart(index) {
	top.getCurrentFrameObj().pieJsonData = 	pieChartDataList.get(index);
	top.OpenModalWindow("饼状图","/sys/zwgk/count/chart.html",475,470);
	
}

// 处理图表的json数据
function initPieChartList(beanList){
	pieChartDataList.clear();
	
	for(var i=0;i<beanList.size();i++){
		var strData = createPieData(beanList.get(i));
		pieChartDataList.add(strData);
	}
}

// 单独添加一条json数据
function addPieChartList(bean) {
	var strData = createPieData(bean);
	pieChartDataList.add(strData);
}

// 生成pie的json字符串
function createPieData(bean) {
	var show_name = bean.cat_name;
	if(show_name == "" || show_name == null){
		show_name = bean.site_name;
	}
	var ret =  '{ "title": { "text": "' + show_name +'详细统计信息"}, ' +
	'"elements":[{ "tip":"#label# : #val# <br>所占信息比例 : #percent#","type":"pie", "start-angle": 180,"colours": '+
	  '["#d01f3c","#356aa0","#C79810","#73880A","#D15600","#6BBA70"],'+
      '"alpha":     0.6, "stroke": 6, "animate":  1, "values" : [' 
	ret += '{"value":'+bean.z_count+',"label":"主动公开信息"},{"value":'+bean.y_count+',"label":"依申请公开信息"},{"value":'+bean.b_count+',"label":"不公开信息"}';
	
	ret += "]}]}";
	return ret;
}
