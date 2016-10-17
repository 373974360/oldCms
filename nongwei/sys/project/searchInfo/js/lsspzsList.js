var LsspzsRPC = jsonrpc.LsspzsRPC;
var LsspzsBean = new Bean("com.deya.project.searchInfo.LsspzsBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "lsspzsdiv";
var current_role_bean;
var is_button_click = true; //是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadLsspzsList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("scs", "生产商", "150px", "", "", ""));
    colsList.add(setTitleClos("cpmc", "产品名称", "170px", "", "", ""));
    colsList.add(setTitleClos("cpbh", "产品编号", "230px", "", "", ""));
    colsList.add(setTitleClos("qyxxm", "企业信息码 ", "130px", "", "", ""));
    colsList.add(setTitleClos("hzcl", "核准产量（吨）", "130px", "", "", ""));
    colsList.add(setTitleClos("xkqx", "许可期限", "130px", "", "", ""));

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

    beanList = LsspzsRPC.getLsspzsList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("scs").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateLsspzsPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).scs + '</a>');
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){
    var m = new Map();

    tp.total = LsspzsRPC.getLsspzsCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdateLsspzsPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    top.OpenModalWindow("绿色食品证书信息", "/sys/project/searchInfo/viewLsspzs.jsp?id=" + c_id + "&topnum="+top.curTabIndex, 485, 483);
}


//删除信息
function deleteLsspzs() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (LsspzsRPC.deleteLsspzs(m)) {
        top.msgAlert("绿色食品证书信息" + WCMLang.Delete_success);
        reloadLsspzsList();
    }
    else {
        top.msgWargin("绿色食品证书信息" + WCMLang.Delete_fail);
    }
}

function updateLsspzsData() {
    if(!standard_checkInputInfo("lsspzs_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(LsspzsBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#lsspzs_table").autoBind(bean);
        bool = LsspzsRPC.updateLsspzs(bean);
    }
    else {
        $("#lsspzs_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = LsspzsRPC.insertLsspzs(bean);
    }
    if (bool) {
        top.msgAlert("绿色食品证书信息保存成功");
        top.getCurrentFrameObj(topnum).reloadLsspzsList();
        top.CloseModalWindow();
    } else {
        top.msgWargin("绿色食品证书信息保存失败，请重新操作");
    }
}

function addInfo() {
    top.OpenModalWindow("绿色食品证书信息", "/sys/project/searchInfo/viewLsspzs.jsp?topnum="+top.curTabIndex, 485, 483);
}


function changeStatus(value) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", value);
    m.put("ids", selectIDS);

    if (LsspzsRPC.changeStatus(m)) {
        top.msgAlert("绿色食品证书信息状态设置成功");
        reloadLsspzsList();
    } else {
        top.msgWargin("绿色食品证书信息状态设置失败，请重新操作");
    }
}

