var InfoAccessBean = new Bean("com.deya.wcm.bean.cms.count.InfoAccessBean",true);
var AccessCountRPC = jsonrpc.AccessCountRPC;

var beanList = null;
var con_m = new Map();

// 显示统计结果列表
function showList(){
	var start_day = $("#start_day").val();
	var end_day = $("#end_day").val();
	
	var mp = new Map();
	// 判断栏目选择信息
	var statu = $("#all_cat_ids").is(':checked');
	if(statu){
		
	}else{
		if(selected_ids == ""){
			msgWargin("请选择站点!");
			return;
		}else{
			//alert("selected_ids===="+selected_ids);
			mp.put("site_ids",selected_ids);
		}
	} 
	
	mp.put("start_day",start_day);
	mp.put("end_day",end_day + " 23:59:59");	
	
	beanList = AccessCountRPC.getAccessCountsBySite(mp);
	beanList = List.toJSList(beanList);
		//alert("beanList==="+beanList);
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
		listHeader.add("站点名称");
		listHeader.add("访问量");
		var url = AccessCountRPC.AccessCountsBySiteOutExcel(beanList,listHeader);
		location.href=url;  
	}
}

//------------------------------------
function createTableSite()
{
	$("#treeTableCount").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th width='50%'>站点名称</th>"+
    "   <th width='40%'>访问量</th>"+
    //"	<th width='10%'>详细</th>"+
   " </tr>" +
  "</thead>" +
  "<tbody>" ;
  if(beanList.size() != 0) 
  {
	for(var i=0;i<beanList.size();i++){
		treeHtmls += setTreeNodeUser(beanList.get(i));
	}
	treeHtmls += "</tbody>"+
	"<tfoot>" +
  	"<td colspan=\"3\"></td>"+ " </tfoot>";
 	} else {
		treeHtmls += "<tr><td colspan=\"2\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"2\"></td>"+" </tfoot>";
	}
	$("#treeTableCount").append(treeHtmls);
}

//填充每一行的具体数据
//添加数据的聚合,显示信息总数
function setTreeNodeUser(bean)
{			
	var treeHtml = "";
	var type_calss= "";
	treeHtml +="<tr><td>"+bean.site_name+"</td>";
	treeHtml +="<td>"+bean.count+"</td>";
	//treeHtml +="<td><a href='javascript:openAccessInfos(\""+bean.site_id+"\",\""+bean.site_name+"\")'>"+"查看"+"</a></td>";
	treeHtml+="</tr>";
	return treeHtml;
}

function openAccessInfos(site_id,site_name) { 
	OpenModalWindow("查看 "+site_name+" 详细信息","/sys/control/count/cmsSiteCateInfoAccess.jsp?site_id="+site_id,1000,600);
	
}