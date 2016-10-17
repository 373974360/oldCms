var MarketTypeRPC = jsonrpc.MarketTypeRPC;
var MarketTypeBean = new Bean("com.deya.project.priceMonitor.MarketTypeBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "marketTypediv";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadMarketTypeList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("marketName", "分类名称", "170px", "", "", ""));
    colsList.add(setTitleClos("addTime", "添加时间", "150px", "", "", ""));
    colsList.add(setTitleClos("comments", "备注 ", "230px", "", "", ""));
    colsList.add(setTitleClos("status", "状态 ", "130px", "", "", ""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id
}

function showList() {

    var m = new Map();
    m.put("start_num", tp.getStart());
    m.put("page_size", tp.pageSize);
    m.put("sort_name", "id");
    m.put("sort_type", "desc");

    beanList = MarketTypeRPC.getMarketTypeList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("marketName").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateMarketTypePage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).marketName + '</a>');
        }
    });

    table.getCol("status").each(function (i) {
        if (i > 0) {
            if (beanList.get(i - 1).status == "0") {
                $(this).html("禁用");
            }
            if (beanList.get(i - 1).status == "1") {
                $(this).html("正常");
            }
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){
    var m = new Map();

    tp.total = MarketTypeRPC.getMarketTypeCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdateMarketTypePage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    top.OpenModalWindow("市场分类信息", "/sys/project/priceMonitor/viewMarketType.jsp?id=" + c_id + "&topnum="+top.curTabIndex, 485, 303);
}


//删除信息
function deleteMarketType() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (MarketTypeRPC.deleteMarketType(m)) {
        top.msgAlert("市场分类信息" + WCMLang.Delete_success);
        reloadMarketTypeList();
    }
    else {
        top.msgWargin("市场分类信息" + WCMLang.Delete_fail);
    }
}

function updateMarketTypeData() {
    if(!standard_checkInputInfo("marketType_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(MarketTypeBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#marketType_table").autoBind(bean);
        bool = MarketTypeRPC.updateMarketType(bean);
    }
    else {
        $("#marketType_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = MarketTypeRPC.insertMarketType(bean);
    }
    if (bool) {
        top.msgAlert("市场分类信息保存成功");
        top.getCurrentFrameObj(topnum).reloadMarketTypeList();
        top.CloseModalWindow();
    } else {
        top.msgWargin("市场分类信息保存失败，请重新操作");
    }
}

function addInfo() {
    top.OpenModalWindow("市场分类信息", "/sys/project/priceMonitor/viewMarketType.jsp?topnum="+top.curTabIndex, 485, 303);
}

function changeStatus(value) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", value);
    m.put("ids", selectIDS);

    if (MarketTypeRPC.changeStatus(m)) {
        top.msgAlert("市场分类信息状态设置成功");
        reloadMarketTypeList();
    } else {
        top.msgWargin("市场分类信息状态设置失败，请重新操作");
    }
}
