var LtqyxxRPC = jsonrpc.LtqyxxRPC;
var LtqyxxBean = new Bean("com.deya.project.searchInfo.LtqyxxBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "ltqyxxdiv";
var current_role_bean;
var is_button_click = true; //是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadLtqyxxList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("dwmc", "单位名称", "150px", "", "", ""));
    colsList.add(setTitleClos("zyyw", "主营业务", "170px", "", "", ""));
    colsList.add(setTitleClos("dz", "地址 ", "230px", "", "", ""));
    colsList.add(setTitleClos("dh", "电话 ", "130px", "", "", ""));
    colsList.add(setTitleClos("yb", "邮编 ", "130px", "", "", ""));

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

    beanList = LtqyxxRPC.getLtqyxxList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("dwmc").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateLtqyxxPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).dwmc + '</a>');
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){
    var m = new Map();

    tp.total = LtqyxxRPC.getLtqyxxCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdateLtqyxxPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    top.OpenModalWindow("龙头企业信息", "/sys/project/searchInfo/viewLtqyxx.jsp?id=" + c_id + "&topnum="+top.curTabIndex, 485, 433);
}


//删除信息
function deleteLtqyxx() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (LtqyxxRPC.deleteLtqyxx(m)) {
        top.msgAlert("龙头企业信息" + WCMLang.Delete_success);
        reloadLtqyxxList();
    }
    else {
        top.msgWargin("龙头企业信息" + WCMLang.Delete_fail);
    }
}

function updateLtqyxxData() {
    if(!standard_checkInputInfo("ltqyxx_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(LtqyxxBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#ltqyxx_table").autoBind(bean);
        bool = LtqyxxRPC.updateLtqyxx(bean);
    }
    else {
        $("#ltqyxx_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = LtqyxxRPC.insertLtqyxx(bean);
    }
    if (bool) {
        top.msgAlert("龙头企业信息保存成功");
        top.getCurrentFrameObj(topnum).reloadLtqyxxList();
        top.CloseModalWindow();
    } else {
        top.msgWargin("龙头企业信息保存失败，请重新操作");
    }
}

function addInfo() {
    top.OpenModalWindow("龙头企业信息", "/sys/project/searchInfo/viewLtqyxx.jsp?topnum="+top.curTabIndex, 485, 433);
}

function changeStatus(value) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", value);
    m.put("ids", selectIDS);

    if (LtqyxxRPC.changeStatus(m)) {
        top.msgAlert("龙头企业信息状态设置成功");
        reloadLtqyxxList();
    } else {
        top.msgWargin("龙头企业信息状态设置失败，请重新操作");
    }
}