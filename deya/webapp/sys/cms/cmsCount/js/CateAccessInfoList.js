var InfoAccessBean = new Bean("com.deya.wcm.bean.cms.count.InfoAccessBean",true);
var AccessCountRPC = jsonrpc.AccessCountRPC;

var beanList = null;
var con_m = new Map();

// 显示统计结果列表
function showList(){	
	//得到父页面中设置的参数 --- start
	var start_day = top.getCurrentFrameObj().$("#start_day").val();
	var end_day = top.getCurrentFrameObj().$("#end_day").val();
	//得到父页面中设置的参数 --- end
	
	var mp = new Map();	
	mp.put("start_day",start_day);
	mp.put("end_day",end_day + " 23:59:59");
	mp.put("site_id",site_id);
	mp.put("cat_id",cat_id);

	beanList = AccessCountRPC.getAccessInfoLists(mp);
	beanList = List.toJSList(beanList);
	createTable();
}
// 生成内容表格
function createTable()
{
	$("#treeTableCount").empty();
	var treeHtmls = "<thead>" +
    "<tr>" +
    "   <th width='80%'>信息标题</th>"+
    "   <th width='20%'>访问量</th>"+
    "</tr>" +
    "</thead>" +
    "<tbody>" ;
    if(beanList.size() != 0)
    {
		for(var i=0;i<beanList.size();i++)
		{
			treeHtmls += setHandlList(beanList.get(i));
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
	  	"<td colspan=\"2\"></td>"+ " </tfoot>";
 	}else{
		treeHtmls += "<tr><td colspan=\"2\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"2\"></td>"+" </tfoot>";
	}
	$("#treeTableCount").append(treeHtmls);
	iniTreeTable("treeTableCount");
}

// 组织统计的数据
function setHandlList(bean)
{
	var str = "<tr>";
	if(bean)
	{
	    var infob = jsonrpc.InfoBaseRPC.getInfoById(bean.info_id+"",site_id);
	    if(infob !="" || infob != null || infob != "null")
	    {
	    	str+="<td><a href="+infob.content_url+" target=\"_blank\">"+infob.title+"</a></td>";
	    	str+="<td>"+bean.icount+"</td>";
	    }	
	}
	str+="</tr>";
	return str;
}
