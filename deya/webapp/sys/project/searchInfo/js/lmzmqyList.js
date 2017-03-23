var LmzmqyRPC = jsonrpc.LmzmqyRPC;
var LmzmqyBean = new Bean("com.deya.project.searchInfo.LmzmqyBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "lmzmqydiv";
var current_role_bean;
var is_button_click = true; //是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadLmzmqyList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("qymc", "企业名称", "150px", "", "", ""));
    colsList.add(setTitleClos("scjydd", "生产经营地点", "170px", "", "", ""));
    colsList.add(setTitleClos("ybsz", "一般树种 ", "150px", "", "", ""));
    colsList.add(setTitleClos("zxsz", "珍稀树种 ", "150px", "", "", ""));
    colsList.add(setTitleClos("scjyxkz", "生产经营许可证 ", "130px", "", "", ""));
    colsList.add(setTitleClos("dh", "电话 ", "120px", "", "", ""));

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

    beanList = LmzmqyRPC.getLmzmqyList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("qymc").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateLmzmqyPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).qymc + '</a>');
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){
    var m = new Map();

    tp.total = LmzmqyRPC.getLmzmqyCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdateLmzmqyPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    OpenModalWindow("林木种苗企业信息", "/sys/project/searchInfo/viewLmzmqy.jsp?id=" + c_id + "&topnum="+curTabIndex, 485, 483);
}


//删除信息
function deleteLmzmqy() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (LmzmqyRPC.deleteLmzmqy(m)) {
        msgAlert("林木种苗企业信息" + WCMLang.Delete_success);
        reloadLmzmqyList();
    }
    else {
        msgWargin("林木种苗企业信息" + WCMLang.Delete_fail);
    }
}

function updateLmzmqyData() {
    if(!standard_checkInputInfo("lmzmqy_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(LmzmqyBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#lmzmqy_table").autoBind(bean);
        bool = LmzmqyRPC.updateLmzmqy(bean);
    }
    else {
        $("#lmzmqy_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = LmzmqyRPC.insertLmzmqy(bean);
    }
    if (bool) {
        msgAlert("林木种苗企业信息保存成功");
        getCurrentFrameObj(topnum).reloadLmzmqyList();
        CloseModalWindow();
    } else {
        msgWargin("林木种苗企业信息保存失败，请重新操作");
    }
}

function addInfo() {
    OpenModalWindow("林木种苗企业信息", "/sys/project/searchInfo/viewLmzmqy.jsp?topnum="+curTabIndex, 485, 483);
}


function changeStatus(value) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", value);
    m.put("ids", selectIDS);

    if (LmzmqyRPC.changeStatus(m)) {
        msgAlert("林木种苗企业信息状态设置成功");
        reloadLmzmqyList();
    } else {
        msgWargin("林木种苗企业信息状态设置失败，请重新操作");
    }
}

