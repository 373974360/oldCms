var DayCollectRPC = jsonrpc.DayCollectRPC;
var ProductTypeRPC = jsonrpc.ProductTypeRPC;
var ProductRPC = jsonrpc.ProductRPC;

var collect_List = null;
var m = new Map();
var rose = 0;


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
    collect_List = DayCollectRPC.getWeekCollect(m);
    collect_List = List.toJSList(collect_List);
    createTableList();
}

function createTableList()
{
    var dataHtmls = "";
    var totleRose = 0;
    $("#showDate").html($("#thisDateEnd").val());
    $("#result_table").removeClass("hidden");
    $("#result_table .data_info").remove();

    for(var i = 0; i < collect_List.size(); i++)
    {
        var wcb = collect_List.get(i);
        dataHtmls += "<tr class=\"data_info\" >";
        dataHtmls += "<td>" + wcb.productName + "</td>";
        dataHtmls += "<td>元/公斤</td>";
        dataHtmls += "<td>" + wcb.thisPrice + "</td>";
        dataHtmls += "<td>" + wcb.lastPrice + "</td>";
        try{
            if(parseInt(wcb.rose) > 10)
            {
                dataHtmls += "<td><span style='color:red'>" + wcb.rose + "</span></td>";
            }else if(parseInt(wcb.rose) < -10)
            {
                dataHtmls += "<td><span style='color:green'>" + wcb.rose + "</span></td>";
            }else{
                dataHtmls += "<td>" + wcb.rose + "</td>";
            }
        }catch(e){
            dataHtmls += "<td>" + wcb.rose + "</td>";
        }
        dataHtmls += "</tr>";
        if(wcb.rose != "-")
        {
            totleRose += parseFloat(wcb.rose);
        }
    }
    dataHtmls += "<tr class=\"data_info\" >";
    dataHtmls += "<td>平均值</td>";
    dataHtmls += "<td colspan='3'>&nbsp;</td>";
    dataHtmls += "<td>" + (totleRose / collect_List.size()).toFixed(2) + "</td>";
    dataHtmls += "</tr>";
    $("#result_table").append(dataHtmls);
}

function dataToExcel() {
    $("#ExcelContent").val($("#resultDiv").html());
    $("#exportForm").submit();
}
