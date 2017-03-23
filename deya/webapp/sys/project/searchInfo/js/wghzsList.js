var WghzsRPC = jsonrpc.WghzsRPC;
var WghzsBean = new Bean("com.deya.project.searchInfo.WghzsBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "wghzsdiv";
var current_role_bean;
var is_button_click = true; //是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadWghzsList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("sqrqc", "申请人全称", "150px", "", "", ""));
    colsList.add(setTitleClos("cpmc", "产品名称", "170px", "", "", ""));
    colsList.add(setTitleClos("shb", "商标 ", "230px", "", "", ""));
    colsList.add(setTitleClos("zsbh", "证书编号 ", "130px", "", "", ""));
    colsList.add(setTitleClos("zsyxq", "证书有效期 ", "130px", "", "", ""));

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

    beanList = WghzsRPC.getWghzsList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("sqrqc").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateWghzsPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).sqrqc + '</a>');
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

    tp.total = WghzsRPC.getWghzsCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdateWghzsPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    OpenModalWindow("无公害证书信息", "/sys/project/searchInfo/viewWghzs.jsp?id=" + c_id + "&topnum="+curTabIndex, 485, 473);
}


//删除信息
function deleteWghzs() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (WghzsRPC.deleteWghzs(m)) {
        msgAlert("无公害证书信息" + WCMLang.Delete_success);
        reloadWghzsList();
    }
    else {
        msgWargin("无公害证书信息" + WCMLang.Delete_fail);
    }
}

function updateWghzsData() {
    if(!standard_checkInputInfo("wghzs_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(WghzsBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#wghzs_table").autoBind(bean);
        bool = WghzsRPC.updateWghzs(bean);
    }
    else {
        $("#wghzs_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = WghzsRPC.insertWghzs(bean);
    }
    if (bool) {
        msgAlert("无公害证书信息保存成功");
        getCurrentFrameObj(topnum).reloadWghzsList();
        CloseModalWindow();
    } else {
        msgWargin("无公害证书信息保存失败，请重新操作");
    }
}

function addInfo() {
    OpenModalWindow("无公害证书信息", "/sys/project/searchInfo/viewWghzs.jsp?topnum="+curTabIndex, 485, 473);
}


function changeStatus(value) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", value);
    m.put("ids", selectIDS);

    if (WghzsRPC.changeStatus(m)) {
        msgAlert("无公害证书信息状态设置成功");
        reloadWghzsList();
    } else {
        msgWargin("无公害证书信息状态设置失败，请重新操作");
    }
}

