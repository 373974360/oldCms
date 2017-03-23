var DayCollectRPC = jsonrpc.DayCollectRPC;
var ProductTypeRPC = jsonrpc.ProductTypeRPC;
var ProductRPC = jsonrpc.ProductRPC;

var beanList = null;
var collectDate = "";
var collect_map = new Map();


//点击统计btn时触发的函数
function searchBtn()
{
    collectDate = $("#collectDate").val();
    if(collectDate == "")
    {
        alert("请选择时间！");
        return ;
    }
    else{
        showList();
        $("#outFileBtn").show();
    }
}

// 显示统计结果列表
function showList()
{
    if(DayCollectRPC.collectByDay(collectDate))
    {
        collect_map = DayCollectRPC.getDayCollect(collectDate);
        collect_map = Map.toJSMap(collect_map);
        createTableList();
    }
}
//导出
function  outFileBtn()
{
    if(type_radio == "mr"){
        if(beanList.size() != 0) {
            var listHeader = new List();
            listHeader.add("栏目名称");
            listHeader.add("访问量");
            var url = AccessCountRPC.CateAccessCountsOutExcel(beanList,listHeader);
            location.href=url;
        }
    }else if(type_radio == "cat_name"){
        if(beanList.size() != 0){
            var listHeader = new List();
            listHeader.add("栏目名称");
            listHeader.add("访问量");
            var url = AccessCountRPC.CateAccessOrderCountsOutExcel(beanList,listHeader);
            location.href=url;
        }
    }else if(type_radio == "info_name"){
        if(beanList.size() != 0){
            var listHeader = new List();
            listHeader.add("信息标题");
            listHeader.add("访问量");
            var url = AccessCountRPC.InfoAccessOrderCountsOutExcel(beanList,listHeader);
            location.href=url;
        }
    }

}

function dataToExcel() {
    $("#ExcelContent").val($("#resultDiv").html());
    $("#exportForm").submit();
}

function createTableList()
{
    var dataHtmls = "";
    $("#showDate").html(collectDate.replace("-","年").replace("-","月") + "日");
    $("#result_table").removeClass("hidden");
    $("#result_table .data_info").remove();
    var typeList = ProductTypeRPC.getProductTypeByMarketId("1");
    typeList = List.toJSList(typeList);

    for(var i = 0; i < typeList.size(); i++)
    {
        var typeInfo = typeList.get(i);
        var collectList = collect_map.get(typeInfo.id);
        collectList = List.toJSList(collectList);
        for(var j = 0; j < collectList.size(); j++)
        {
            var collectInfo = collectList.get(j);
            if(j == 0)
            {
                dataHtmls += "<tr class=\"data_info\"><td rowspan=\"" +collectList.size() + "\">" + collectInfo.typeName + "</td>";
            }
            else {
                dataHtmls += "<tr class=\"data_info\" >";
            }
            dataHtmls += "<td>" + collectInfo.productName + "</td>";
            dataHtmls += "<td>" + collectInfo.lastPrice + "</td>";
            if(parseInt(collectInfo.todayRose) > 10)
            {
                dataHtmls += "<td><span style='color:red'>" + collectInfo.todayPrice + "</span></td>";
            }else if(parseInt(collectInfo.todayRose) < -10)
            {
                dataHtmls += "<td><span style='color:green'>" + collectInfo.todayPrice + "</span></td>";
            }else{
                dataHtmls += "<td>" + collectInfo.todayPrice + "</td>";
            }
            dataHtmls += "<td>" + collectInfo.lastSellPrice + "</td>";
            if(parseInt(collectInfo.todaySellRose) > 10)
            {
                dataHtmls += "<td><span style='color:red'>" + collectInfo.todaySellPrice + "</span></td>";
            }else if(parseInt(collectInfo.todaySellRose) < -10)
            {
                dataHtmls += "<td><span style='color:green'>" + collectInfo.todaySellPrice + "</span></td>";
            }else{
                dataHtmls += "<td>" + collectInfo.todaySellPrice + "</td>";
            }
            dataHtmls += "<td>" + collectInfo.comments + "</td>";
            dataHtmls += "</tr>";
        }
    }
    $("#result_table").append(dataHtmls);
}
