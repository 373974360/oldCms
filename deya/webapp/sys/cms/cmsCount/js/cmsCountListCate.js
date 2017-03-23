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
	
	// 不包含引用信息
	if(!$("#ref_info").is(':checked')){
		// 不包含引用也不包含链接信息
		if(!$("#link_info").is(':checked')){
			mp.put("is_host","0");
		}else{ // 不包含引用,包含连接
			mp.put("is_host","0,2");
		}
	} else {
		// 包含引用,不包含连接
		if(!$("#link_info").is(':checked')){
			mp.put("is_host","0,1");
		}else{	// 包含引用和连接
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
	mp.put("start_day",start_day);
	mp.put("end_day",end_day + " 23:59:59");
	
	beanList = CmsCountRPC.getInputCountListByCate(mp);
	beanList = List.toJSList(beanList);
	
	createTableCate();
	
	// chart处理
	//setBarChart();
	
	$("#outFileBtn").show();  
}


//导出
function  outFileBtn(){
	if(beanList.size() != 0) {
		var listHeader = new List();
		listHeader.add("栏目");
		listHeader.add("全部信息");
		listHeader.add("已发信息");
		listHeader.add("图片信息");
		listHeader.add("发稿率");
		var url = jsonrpc.SiteCountRPC.getInputCountListByCateOutExcel(listHeader,mp);
		//alert(url);
		location.href=url;  
	}
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

//生成内容表格
function createTableCate(){
	$("#countList").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th width='40%'>栏目</th>		" +
    "   <th width='20%'>全部信息</th>       " +
    "   <th width='20%'>已发信息</th>   " +
    "   <th width='10%'>图片信息</th>   " +
    "	<th width='10%'>发稿率</th> " +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	if(beanList.size() != 0) {
		for(var i=0;i<beanList.size();i++){
			treeHtmls += setTreeNode(beanList.get(i), "");
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
 	 	"<td colspan=\"5\"></td>"+ " </tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"5\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"5\"></td>"+" </tfoot>";
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


// 组织统计的数据
function setHandlList(bean)
{
	var str = "";
	if(bean)
	{
		str+="<td>"+bean.inputCount +"</td>";
		str+="<td>"+bean.releasedCount +"</td>";
		str+="<td>"+bean.picReleasedCount +"</td>";
		str+="<td>"+bean.releaseRate +"</td>";
	}
	return str;
}