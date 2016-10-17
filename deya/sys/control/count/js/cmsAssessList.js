var CategoryRPC = jsonrpc.CategoryRPC;
var CategoryBean = new Bean("com.deya.wcm.bean.cms.category.CategoryBean",true);
var CmsCountRPC = jsonrpc.CmsCountRPC;

var beanList = null;
var con_m = new Map();

var isContainRef = false; // 初始化目录树标记
var isContainLink = false; // 初始化"查看方式"改变事件标记

var is_host = "0"; // 默认只统计主信息

var chartJsonData = ""; // bar图时的json字符串

var mp = new Map();
// 显示统计结果列表
function showList(){
	
	var start_day = $("#start_day").val();
	var end_day = $("#end_day").val();
	
	// 判断栏目选择信息
	var statu = $("#all_cat_ids").is(':checked');
	if(statu) {
		mp.remove("site_ids");
	}else{
		if(selected_ids== ""){
			top.msgWargin("请选择站点!");
			return;
		}else{
			mp.put("site_ids",selected_ids);
		}
	} 
	
	mp.put("start_day",start_day);
	mp.put("end_day",end_day + " 23:59:59");
	
	//alert(mp);
	//return;
	
	var v = $(":radio[name='info_scope'][checked]").val();
	if(v=='dept'){
		beanList = jsonrpc.SiteCountRPC.getSiteCountListByInput(mp);
		beanList = List.toJSList(beanList);
		createTable();
	}else if(v=='user'){
		beanList = jsonrpc.SiteCountRPC.getSiteCountListByInputUser(mp);
		beanList = List.toJSList(beanList);
		createTableUser();
	}
	
	$("#outFileBtn").show();
	
}


//处理全选事件
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

//导出
function  outFileBtn(){
	var v = $(":radio[name='info_scope'][checked]").val();
	if(v=='dept'){
		var listHeader = new List();
		listHeader.add("部门");
		listHeader.add("全部信息");
		listHeader.add("主信息");
		listHeader.add("引用信息"); 
		listHeader.add("链接信息"); 
		listHeader.add("已发信息"); 
		listHeader.add("发稿率");
		var url = jsonrpc.SiteCountRPC.orgTreeInfoOutExcel(listHeader,mp);
		//alert(url);
		location.href=url;  	
	}else if(v=='user'){
		if(beanList.size() != 0) {
			beanList = List.toJSList(beanList);
			var listHeader = new List();
			listHeader.add("人员"); 
			listHeader.add("部门");
			listHeader.add("全部信息");
			listHeader.add("主信息");
			listHeader.add("引用信息"); 
			listHeader.add("链接信息"); 
			listHeader.add("已发信息"); 
			listHeader.add("发稿率"); 
			var url = jsonrpc.SiteCountRPC.siteInfoOutExcelByUser(beanList,listHeader);
			//alert(url);
			location.href=url;  
		}
	}
}

//按部门统计
function createTable()
{
	$("#treeTableCount").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th width='30%'>部门</th>		" +
    "   <th width='10%'>全部信息</th>       " +
    "   <th width='10%'>主信息</th>   " +
    "	<th width='10%'>引用信息</th> " +
    "	<th width='10%'>链接信息</th>     " +
    "	<th width='10%'>已发信息</th>           " +
    "	<th width='10%'>发稿率</th>           " +
    "	<th width='10%'>详细</th>           " +
   " </tr>" +
  "</thead>" +
  "<tbody>" ;
  if(beanList.size() != 0) {
	for(var i=0;i<beanList.size();i++){
		treeHtmls += setTreeNode(beanList.get(i), "");
	}
	treeHtmls += "</tbody>"+
	"<tfoot>" +
  	"<td colspan=\"8\"></td>"+ " </tfoot>";
 	} else {
		treeHtmls += "<tr><td colspan=\"8\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"8\"></td>"+" </tfoot>";
	}
	$("#treeTableCount").append(treeHtmls);
	iniTreeTable("treeTableCount");
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
		treeHtml+="<tr id='node-"+bean.dept_id+"' "+type_calss+" > <td><span class='file'>"+bean.dept_name+"</span></td>";
        treeHtml += setHandlList(bean);
		treeHtml+="</tr>";
	}
	else 
	{
		var type_calss = "";
		if(parent_id !=""){  
		      type_calss = "class='child-of-node-"+parent_id+"'"
		}   
		treeHtml+="<tr id='node-"+bean.dept_id+"' "+type_calss+" > <td><span class='folder'>"+bean.dept_name+"</span></td>";
		treeHtml += setHandlList(bean);
		treeHtml+="</tr>";
		var child_list = bean.child_list;
		  child_list = List.toJSList(child_list);
		  if(child_list.size() > 0)
		  { 
			 for(var i=0;i<child_list.size();i++)
			 {						
				treeHtml += setTreeNode(child_list.get(i), bean.dept_id+"");
			 }
		  }
	}
	
	return treeHtml;
}


// 组织统计的数据
function setHandlList(bean)
{
	var str = "";
	//index = index-1;
	if(bean)
	{
		str+="<td>"+bean.inputCount +"</td>";
		str+="<td>"+bean.hostInfoCount +"</td>";
		str+="<td>"+bean.refInfoCount +"</td>";
		str+="<td>"+bean.linkInfoCount +"</td>";
		str+="<td>"+bean.releasedCount +"</td>";
		str+="<td>"+bean.releaseRate +"</td>";
		str+="<td><a href='javascript:openPieChart(\""+bean.dept_id+"\",\""+bean.dept_name+"\")'>"+"查看"+"</a></td>";
	}
	return str;
}

function openPieChart(dept_id,dept_name) { 
	//top.getCurrentFrameObj().pieJsonData = 	pieChartDataList.get(index);
	top.OpenModalWindow("查看 "+dept_name+" 详细信息","/sys/control/count/cmsDeptUserInfo.jsp?dept_id="+dept_id,1000,600);
	
}


//------------------------------------
//按人员统计
function createTableUser()
{
	$("#treeTableCount").empty();
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
		treeHtmls += setTreeNodeUser(beanList.get(i));
	}
	treeHtmls += "</tbody>"+
	"<tfoot>" +
  	"<td colspan=\"8\"></td>"+ " </tfoot>";
 	} else {
		treeHtmls += "<tr><td colspan=\"8\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"8\"></td>"+" </tfoot>";
	}
	$("#treeTableCount").append(treeHtmls);
}

//填充每一行的具体数据
//添加数据的聚合,显示信息总数
function setTreeNodeUser(bean)
{			
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