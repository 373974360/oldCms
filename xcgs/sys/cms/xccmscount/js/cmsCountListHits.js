var CmsCountRPC = jsonrpc.CmsCountRPC;

var beanList = null;
var mp = new Map();
var start_day ="";
var end_day ="";

// 显示统计结果列表
function showList(){
	
	start_day = $("#start_day").val();
	end_day = $("#end_day").val();

	mp.put("site_id",site_id);
    mp.put("info_status","8");
	mp.put("start_day",start_day +" 00:00:00");
	mp.put("end_day",end_day + " 23:59:59");
    mp.put("orderby","ci.hits desc")

    if(tjfs == 1)
    {
        beanList = CmsCountRPC.getInfoHitsByReleaseDtime(mp);
        beanList = List.toJSList(beanList);
        createTableInfo();
    }
	else {
        beanList = CmsCountRPC.getCatHitsByReleaseDtime(mp);
        beanList = List.toJSList(beanList);
        createTableCate();
    }
}

// 处理全选事件
function allSelected(value){
    tjfs = value;
	var statu = $("#infoHits").is(':checked');
    var statu2 = $("#catHits").is(':checked');
	if(statu){
		$("#catHits").attr("disabled","disabled");
	}else{
        if(statu2)
        {
            $("#infoHits").attr("disabled","disabled");
        }
        else{
            $("#infoHits").removeAttr("disabled");
            $("#catHits").removeAttr("disabled");
        }
	}
}

//生成内容表格
function createTableInfo(){
	$("#countList").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th width='8%'>序号</th>" +
    "   <th width='13%'>日期</th>" +
    "   <th width='25%'>标题</th>" +
    "   <th width='13%'>栏目</th>" +
    "   <th width='8%'>作者</th>" +
    "   <th width='17%'>部门</th>" +
    "   <th width='8%'>评级</th>" +
    "   <th width='8%'>点击量</th>" +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	if(beanList.size() != 0) {
		for(var i=0;i<beanList.size();i++){
            var bean = beanList.get(i);
            treeHtmls += "<tr> <td>" + (i + 1) + "</td>";
            treeHtmls += "<td>"+bean.released_dtime+"</td>";
            treeHtmls += "<td><a href=\"/info/contentView.jsp?info_id="+bean.info_id+")\" >"+bean.title+"</a></td>";
            treeHtmls += "<td>"+bean.cat_cname+"</td>";
            treeHtmls += "<td>"+bean.author+"</td>";
            treeHtmls += "<td>"+bean.source+"</td>";
            treeHtmls += "<td>"+bean.info_level+"</td>";
            treeHtmls += "<td>"+bean.hits+"</td>";
            treeHtmls +="</tr>";
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
 	 	"<td colspan=\"2\"></td>"+ " </tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"2\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"2\"></td>"+" </tfoot>";
	}
	$("#countList").append(treeHtmls);
	iniTreeTable("countList");
    $("#outFileBtn").removeClass("hidden");
}

//导出
function  outFileBtn(){
    if(tjfs == 1)
    {
        if(beanList.size() != 0) {
            var listHeader = new List();
            listHeader.add("序号");
            listHeader.add("发布时间");
            listHeader.add("标题");
            listHeader.add("栏目名称");
            listHeader.add("作者");
            listHeader.add("部门");
            listHeader.add("评级");
            listHeader.add("点击量");
            var url = CmsCountRPC.getInfoHitsOutExcel(listHeader,mp,"信息点击量统计表");
            location.href=url;
        }
    }
    else{
        if(beanList.size() != 0) {
            var listHeader = new List();
            listHeader.add("序号");
            listHeader.add("栏目名称");
            listHeader.add("总点击量");
            var url = CmsCountRPC.getCatHitsOutExcel(listHeader,mp,"栏目点击量统计表");
            location.href=url;
        }
    }
}

//生成内容表格
function createTableCate(){
    $("#countList").empty();
    var treeHtmls = "<thead>" +
        "<tr> " +
        "   <th width='8%'>序号</th>		" +
        "   <th width='60%'>栏目名称</th>		" +
        "   <th width='32%' style='padding:0;'>总点击量</th>       " +
        " </tr>" +
        "</thead>" +
        "<tbody>" ;
    if(beanList.size() != 0) {
        for(var i=0;i<beanList.size();i++){
            var bean = beanList.get(i);
            treeHtmls += "<tr> <td>" + (i + 1) + "</td>";
            treeHtmls += "<td>"+bean.cat_name+"</td>";
            treeHtmls += "<td>"+bean.inputCount+"</td>";
        }
        treeHtmls += "</tbody>"+
            "<tfoot>" +
            "<td colspan=\"2\"></td>"+ " </tfoot>";
    } else {
        treeHtmls += "<tr><td colspan=\"2\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"2\"></td>"+" </tfoot>";
    }
    $("#countList").append(treeHtmls);
    iniTreeTable("countList");
    $("#outFileBtn").removeClass("hidden");
}
