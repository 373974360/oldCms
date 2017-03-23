var MarketTypeRPC = jsonrpc.MarketTypeRPC;
var PriceInfoRPC = jsonrpc.PriceInfoRPC;
var ProductRPC = jsonrpc.ProductRPC;
var ProductTypeRPC = jsonrpc.ProductTypeRPC;
var PriceInfoBean = new Bean("com.deya.project.priceMonitor.PriceInfoBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "priceInfodiv";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;
var m = new Map();

function reloadPriceInfoList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("productName", "蔬果品名", "120px", "", "", ""));
    colsList.add(setTitleClos("typeName", "分类名称", "120px", "", "", ""));
    colsList.add(setTitleClos("marketName", "市场分类", "170px", "", "", ""));
    colsList.add(setTitleClos("price", "价格 ", "80px", "", "", ""));
    colsList.add(setTitleClos("location", "产地 ", "120px", "", "", ""));
    colsList.add(setTitleClos("landings", "上市量 ", "80px", "", "", ""));
    colsList.add(setTitleClos("tradings", "交易量 ", "80px", "", "", ""));
    colsList.add(setTitleClos("comments", "备注 ", "120px", "", "", ""));
    colsList.add(setTitleClos("scName", "市场名称 ", "120px", "", "", ""));
    colsList.add(setTitleClos("addTime", "添加时间 ", "120px", "", "", ""));
    colsList.add(setTitleClos("username", "添加人 ", "120px", "", "", ""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id
}

function showList() {

    m.put("start_num", tp.getStart());
    m.put("page_size", tp.pageSize);
    m.put("orderby", "addTime desc");

    beanList = PriceInfoRPC.getPriceInfoList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("productName").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdatePriceInfoPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).productName + '</a>');
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){

    tp.total = PriceInfoRPC.getPriceInfoCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdatePriceInfoPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    OpenModalWindow("价格监测信息", "/sys/project/priceMonitor/viewPriceInfo.jsp?id=" + c_id + "&topnum="+curTabIndex, 485, 450);
}


//删除信息
function deletePriceInfo() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (PriceInfoRPC.deletePriceInfo(m)) {
        msgAlert("价格监测信息" + WCMLang.Delete_success);
        reloadPriceInfoList();
    }
    else {
        msgWargin("价格监测信息" + WCMLang.Delete_fail);
    }
}

function updatePriceInfoData() {
    if(!standard_checkInputInfo("priceInfo_table"))
    {
        return;
    }
    var isAdd = false;

    var bean = BeanUtil.getCopy(PriceInfoBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        bean.addUser = LoginUserBean.user_id;
        if(bean.marketId == 2)
        {
            bean.marketId = 1;
        }
        $("#priceInfo_table").autoBind(bean);
        bool = PriceInfoRPC.updatePriceInfo(bean);
    }
    else {
        isAdd = true;
        $("#priceInfo_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bean.addUser = LoginUserBean.user_id;
        if(bean.marketId == 2)
        {
            bean.marketId = 1;
        }
        bool = PriceInfoRPC.insertPriceInfo(bean);
    }
    if (bool) {
        msgAlert("价格监测信息保存成功");
        getCurrentFrameObj(topnum).reloadPriceInfoList();
        CloseModalWindow();
        if(isAdd)
        {
            getCurrentFrameObj(topnum).addInfo();
        }
    } else {
        msgWargin("价格监测信息保存失败，请重新操作");
    }
}

function addInfo() {
    OpenModalWindow("价格监测信息", "/sys/project/priceMonitor/viewPriceInfo.jsp?topnum="+curTabIndex, 485, 450);
}


function searchInfo()
{
    var startTime = $("#collectDate").val();
    var endTime = $("#collectDate").val();
    var scName = $("#scName").val();
    m.put("startTime",startTime);
    m.put("endTime",endTime);
    if(scName != ""){
        m.put("scName",scName);
    }
    reloadPriceInfoList();
}

function exportInfo(){
    var m = new Map();
    var exportDate = $("#exportDate").val();
    if(exportDate != ""){
        m.put("exportDate",exportDate);
        var url = PriceInfoRPC.exportInfoOutExcel(m);
        location.href=url;
    }
    else{
        alert("请选择导出时间！");
        return ;
    }
}