var DayCollectRPC = jsonrpc.DayCollectRPC;
var ProductTypeRPC = jsonrpc.ProductTypeRPC;
var ProductRPC = jsonrpc.ProductRPC;

var collect_map = null;
var m = new Map();

//点击统计btn时触发的函数
function searchBtn()
{
    if($("#lastDateStart").val() == "" || $("#lastDateEnd").val() == "" || $("#thisDateStart").val() == "" || $("#thisDateEnd").val() == "")
    {
        alert("请选择完整的统计时间！");
        return false;
    }
    else{
        showList();
        $("#outFileBtn").show();
    }
}

// 显示统计结果列表
function showList()
{
    m.put("lastDateStart",$("#lastDateStart").val());
    m.put("lastDateEnd",$("#lastDateEnd").val());
    m.put("thisDateStart",$("#thisDateStart").val());
    m.put("thisDateEnd",$("#thisDateEnd").val());
    collect_map = DayCollectRPC.getTenCollectList(m);
    collect_map = Map.toJSMap(collect_map);
    createTableList();
}

function createTableList()
{
    var dataHtmls = "";
    $("#result_table").removeClass("hidden");
    $("#result_table .data_info").remove();
    var typeList = ProductTypeRPC.getProductTypeByMarketId("1");
    typeList = List.toJSList(typeList);
    var pfList = collect_map.get("0");
    pfList = List.toJSList(pfList);
    var csList = collect_map.get("1");
    csList = List.toJSList(csList);

    for(var i = 0; i < typeList.size(); i++)
    {
        var typeInfo = typeList.get(i);
        var productList = ProductRPC.getAllProductListByTypeId(typeInfo.id);
        productList = List.toJSList(productList);
        for(var j = 0; j < productList.size(); j++)
        {
            var productInfo = productList.get(j);
            if(j == 0)
            {
                dataHtmls += "<tr class=\"data_info\"><td rowspan=\"" +productList.size() + "\">" + productInfo.typeName + "</td>";
            }
            else {
                dataHtmls += "<tr class=\"data_info\" >";
            }
            var isHavePf = false;
            if(pfList != null && pfList.size() > 0)
            {
                for(var k = 0; k < pfList.size(); k++)
                {
                    var tenInfo = pfList.get(k);
                    if(tenInfo.productId == productInfo.id)
                    {
                        dataHtmls += "<td>" + tenInfo.productName + "</td>";
                        dataHtmls += "<td>" + tenInfo.maxPrice + "</td>";
                        dataHtmls += "<td>" + tenInfo.minPrice + "</td>";
                        dataHtmls += "<td>" + tenInfo.avgPrice + "</td>";
                        try{
                            if(parseInt(tenInfo.rose) > 10)
                            {
                                dataHtmls += "<td><span style='color:red'>" + tenInfo.rose + "</span></td>";
                            }else if(parseInt(tenInfo.rose) < -10)
                            {
                                dataHtmls += "<td><span style='color:green'>" + tenInfo.rose + "</span></td>";
                            }else{
                                dataHtmls += "<td>" + tenInfo.rose + "</td>";
                            }
                        }catch(e){
                            dataHtmls += "<td>" + tenInfo.rose + "</td>";
                        }
                        try{
                            if(parseInt(tenInfo.chain) > 10)
                            {
                                dataHtmls += "<td><span style='color:red'>" + tenInfo.chain + "</span></td>";
                            }else if(parseInt(tenInfo.chain) < -10)
                            {
                                dataHtmls += "<td><span style='color:green'>" + tenInfo.chain + "</span></td>";
                            }else{
                                dataHtmls += "<td>" + tenInfo.chain + "</td>";
                            }
                        }catch(e){
                            dataHtmls += "<td>" + tenInfo.chain + "</td>";
                        }
                        isHavePf = true;
                    }
                }
                if(!isHavePf)
                {
                    dataHtmls += "<td>/</td>";
                    dataHtmls += "<td>/</td>";
                    dataHtmls += "<td>/</td>";
                    dataHtmls += "<td>/</td>";
                    dataHtmls += "<td>/</td>";
                    dataHtmls += "<td>/</td>";
                }
            }
            var isHaveCs = false;
            if(csList != null && csList.size() > 0)
            {
                for(var k = 0; k < csList.size(); k++)
                {
                    var tenInfo = csList.get(k);
                    if(tenInfo.productId == productInfo.id)
                    {
                        dataHtmls += "<td>" + tenInfo.maxPrice + "</td>";
                        dataHtmls += "<td>" + tenInfo.minPrice + "</td>";
                        dataHtmls += "<td>" + tenInfo.avgPrice + "</td>";
                        try{
                            if(parseInt(tenInfo.rose) > 10)
                            {
                                dataHtmls += "<td><span style='color:red'>" + tenInfo.rose + "</span></td>";
                            }else if(parseInt(tenInfo.rose) < -10)
                            {
                                dataHtmls += "<td><span style='color:green'>" + tenInfo.rose + "</span></td>";
                            }else{
                                dataHtmls += "<td>" + tenInfo.rose + "</td>";
                            }
                        }catch(e){
                            dataHtmls += "<td>" + tenInfo.rose + "</td>";
                        }
                        try{
                            if(parseInt(tenInfo.chain) > 10)
                            {
                                dataHtmls += "<td><span style='color:red'>" + tenInfo.chain + "</span></td>";
                            }else if(parseInt(tenInfo.chain) < -10)
                            {
                                dataHtmls += "<td><span style='color:green'>" + tenInfo.chain + "</span></td>";
                            }else{
                                dataHtmls += "<td>" + tenInfo.chain + "</td>";
                            }
                        }catch(e){
                            dataHtmls += "<td>" + tenInfo.chain + "</td>";
                        }
                        isHaveCs = true;
                    }
                }
                if(!isHaveCs)
                {
                    dataHtmls += "<td>/</td>";
                    dataHtmls += "<td>/</td>";
                    dataHtmls += "<td>/</td>";
                    dataHtmls += "<td>/</td>";
                    dataHtmls += "<td>/</td>";
                }
            }
            dataHtmls += "</tr>";
        }
    }
    $("#result_table").append(dataHtmls);
}


function dataToExcel() {
    $("#ExcelContent").val($("#resultDiv").html());
    $("#exportForm").submit();
}