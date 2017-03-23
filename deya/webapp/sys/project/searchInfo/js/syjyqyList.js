var SyjyqyRPC = jsonrpc.SyjyqyRPC;
var SyjyqyBean = new Bean("com.deya.project.searchInfo.SyjyqyBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "syjyqydiv";
var current_role_bean;
var is_button_click = true; //是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadSyjyqyList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("qx", "区县", "80px", "", "", ""));
    colsList.add(setTitleClos("qymc", "企业名称", "170px", "", "", ""));
    colsList.add(setTitleClos("fr", "法人 ", "80px", "", "", ""));
    colsList.add(setTitleClos("jydz", "经营地址 ", "230px", "", "", ""));
    colsList.add(setTitleClos("lxdh", "联系电话 ", "120px", "", "", ""));

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

    beanList = SyjyqyRPC.getSyjyqyList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("qymc").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateSyjyqyPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).qymc + '</a>');
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){
    var m = new Map();

    tp.total = SyjyqyRPC.getSyjyqyCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdateSyjyqyPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    OpenModalWindow("通过兽药GSP兽药经营企业信息", "/sys/project/searchInfo/viewSyjyqy.jsp?id=" + c_id + "&topnum="+curTabIndex, 485, 433);
}


//删除信息
function deleteSyjyqy() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (SyjyqyRPC.deleteSyjyqy(m)) {
        msgAlert("通过兽药GSP兽药经营企业信息" + WCMLang.Delete_success);
        reloadSyjyqyList();
    }
    else {
        msgWargin("通过兽药GSP兽药经营企业信息" + WCMLang.Delete_fail);
    }
}

function updateSyjyqyData() {
    if(!standard_checkInputInfo("syjyqy_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(SyjyqyBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#syjyqy_table").autoBind(bean);
        bool = SyjyqyRPC.updateSyjyqy(bean);
    }
    else {
        $("#syjyqy_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = SyjyqyRPC.insertSyjyqy(bean);
    }
    if (bool) {
        msgAlert("通过兽药GSP兽药经营企业信息保存成功");
        getCurrentFrameObj(topnum).reloadSyjyqyList();
        CloseModalWindow();
    } else {
        msgWargin("通过兽药GSP兽药经营企业信息保存失败，请重新操作");
    }
}

function addInfo() {
    OpenModalWindow("通过兽药GSP兽药经营企业信息", "/sys/project/searchInfo/viewSyjyqy.jsp?topnum="+curTabIndex, 485, 433);
}

function changeStatus(value) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", value);
    m.put("ids", selectIDS);
    if (SyjyqyRPC.changeStatus(m)) {
        msgAlert("通过兽药GSP兽药经营企业信息状态设置成功");
        reloadSyjyqyList();
    } else {
        msgWargin("通过兽药GSP兽药经营企业信息状态设置失败，请重新操作");
    }
}