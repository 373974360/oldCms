var GKvisitorCountRPC = jsonrpc.GKvisitorCountRPC;

var pieChartDataList = new List();

//**************************************所有站点公开信息统计***********************************************/
// 取得所有公开站点的信息数量
function createTableAllSite()
{
	// 初始化Table
	// TODO 添加节点名称
	//alert(node_ids);
	beanList = GKvisitorCountRPC.getAllSiteGKCountList(start_day, end_day,node_ids);
	beanList = List.toJSList(beanList);
	
	//传给父页面list
	parent.beanListResult = beanList;
	
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th>节点名称</th>		" +
    "	<th>访问量</th>     " +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	if(beanList.size() != 0) {		
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
	}
	return str;
}

// 跳转到站点下的栏目详细统计页面
function redirectURL(bean_site_id,site_name)
{
	top.addTab(true ,"/sys/zwgk/count/gkVisitorCount.jsp?start_day="+start_day+"&end_day="+end_day+"&site_id="
		+bean_site_id,site_name+"栏目统计");
}
