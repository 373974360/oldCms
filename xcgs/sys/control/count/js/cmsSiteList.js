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
	
	var start_day = $("#start_day").val();
	var end_day = $("#end_day").val();
	
	var mp = new Map();
	// 判断栏目选择信息
	var statu = $("#all_cat_ids").is(':checked');
	if(statu) {
		
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
	beanList = jsonrpc.SiteCountRPC.getSiteCountListBySite(mp);
	beanList = List.toJSList(beanList);
	createTableSite();
	
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
	if(beanList.size() != 0) {
		var listHeader = new List();
		listHeader.add("录入员");
		listHeader.add("全部信息");
		listHeader.add("已发信息");
		listHeader.add("发稿率");
		var url = CmsCountRPC.cmsWorkInfoOutExcel(beanList,listHeader);
		//alert(url);
		location.href=url;  
	}
}


//------------------------------------
function createTableSite()
{
	$("#treeTableCount").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th width='20%'>站点名称</th>		" +
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
	treeHtml +="<tr> <td>"+bean.site_name+"</td>";
	treeHtml +="<td>"+bean.inputCount +"</td>";
	treeHtml +="<td>"+bean.hostInfoCount +"</td>";
	treeHtml +="<td>"+bean.refInfoCount +"</td>";
	treeHtml +="<td>"+bean.linkInfoCount +"</td>";
	treeHtml +="<td>"+bean.releasedCount +"</td>";
	treeHtml +="<td>"+bean.releaseRate +"</td>";
	treeHtml+="<td><a href='javascript:openCateInfo(\""+bean.site_id+"\",\""+bean.site_name+"\")'>"+"查看"+"</a></td>";
	treeHtml+="</tr>";
	return treeHtml;
}

function openCateInfo(site_id,site_name) { 
	//top.getCurrentFrameObj().pieJsonData = 	pieChartDataList.get(index);
	top.OpenModalWindow("查看 "+site_name+" 详细信息","/sys/control/count/cmsSiteCateInfo.jsp?site_id="+site_id,1000,600);
	
}