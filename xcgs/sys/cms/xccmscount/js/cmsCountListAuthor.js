var CmsCountRPC = jsonrpc.CmsCountRPC;

var beanList = null;
var mp = new Map();

var start_day ="";
var end_day ="";

// 显示统计结果列表
function showList(){
	
	start_day = $("#start_day").val();
	end_day = $("#end_day").val();

	// 判断栏目选择信息
	var statu = $("#all_authors").is(':checked');
	if(statu) {
		
	}else{
		if($("#authorNames").val() == ""){
			top.msgWargin("作者信息不能为空!");
			return;
		}else{
			mp.put("author",$("#authorNames").val());
		}
	}
	
	mp.put("site_id",site_id);
    mp.put("info_status","8");
	mp.put("start_day",start_day +" 00:00:00");
	mp.put("end_day",end_day + " 23:59:59");
	
	beanList = CmsCountRPC.getAuthorListByReleaseDtime(mp);
	beanList = List.toJSList(beanList);
	
	createTableCate();

}

// 处理全选事件
function allSelected(){
	var statu = $("#all_authors").is(':checked');
	if(statu){
        $("#authorNames").val("");
		$("#authorNames").attr("disabled","disabled");
	}else{
		$("#authorNames").removeAttr("disabled");
	}
}

//生成内容表格
function createTableCate(){
	$("#countList").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th width='60%'>作者姓名</th>		" +
    "   <th width='40%' style='padding:0;'>总信息数</th>       " +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	if(beanList.size() != 0) {
		for(var i=0;i<beanList.size();i++){
			treeHtmls += setTreeNode(beanList.get(i));
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
 	 	"<td colspan=\"2\"></td>"+ " </tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"2\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"2\"></td>"+" </tfoot>";
	}
	$("#countList").append(treeHtmls);
	iniTreeTable("countList");
}

//组织树列表选项字符串
function setTreeNode(bean)
{
    if(bean)
    {
        var treeHtml = "";
        treeHtml+="<tr> <td><span class='file'>"+bean.user_name+"</span></td>";
        if(bean.inputCount > 0){
            treeHtml+="<td><a href=\"javascript:openUInfoList('"+bean.user_name+"')\" style=\"color:red;\">"+bean.inputCount+"</a></td>";
        }
        else {
            treeHtml+="<td>"+bean.inputCount+"</td>";
        }

    }
    treeHtml+="</tr>";
	return treeHtml;
}

function openUInfoList(author)
{
    top.OpenModalWindow("详细信息列表","/sys/cms/cmsCount/authorInfoList.jsp?site_id="+site_id+"&start_day="+start_day+"&end_day="+end_day+"&author="+author,1000,600);
}