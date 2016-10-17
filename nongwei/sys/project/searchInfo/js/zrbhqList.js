var ZrbhqRPC = jsonrpc.ZrbhqRPC;
var ZrbhqTypeRPC = jsonrpc.ZrbhqTypeRPC;
var ZrbhqBean = new Bean("com.deya.project.searchInfo.ZrbhqBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "zrbhqdiv";
var current_role_bean;
var is_button_click = true; //是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadZrbhqList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("bhqmc", "保护区名称", "150px", "", "", ""));
    colsList.add(setTitleClos("jb", "级别", "170px", "", "", ""));
    colsList.add(setTitleClos("wz", "位置 ", "230px", "", "", ""));
    colsList.add(setTitleClos("mj", "面积（公顷） ", "130px", "", "", ""));
    colsList.add(setTitleClos("txdz", "通讯地址 ", "230px", "", "", ""));
    colsList.add(setTitleClos("lxdh", "联系电话 ", "230px", "", "", ""));

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

    beanList = ZrbhqRPC.getZrbhqList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("bhqmc").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateZrbhqPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).bhqmc + '</a>');
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

    tp.total = ZrbhqRPC.getZrbhqCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdateZrbhqPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    top.OpenModalWindow("自然保护区信息", "/sys/project/searchInfo/viewZrbhq.jsp?id=" + c_id + "&topnum="+top.curTabIndex, 485, 536);
}


//删除信息
function deleteZrbhq() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (ZrbhqRPC.deleteZrbhq(m)) {
        top.msgAlert("自然保护区信息" + WCMLang.Delete_success);
        reloadZrbhqList();
    }
    else {
        top.msgWargin("自然保护区信息" + WCMLang.Delete_fail);
    }
}

function updateZrbhqData() {
    if(!standard_checkInputInfo("zrbhq_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(ZrbhqBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#zrbhq_table").autoBind(bean);
        bool = ZrbhqRPC.updateZrbhq(bean);
    }
    else {
        $("#zrbhq_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = ZrbhqRPC.insertZrbhq(bean);
    }
    if (bool) {
        top.msgAlert("自然保护区信息保存成功");
        top.getCurrentFrameObj(topnum).reloadZrbhqList();
        top.CloseModalWindow();
    } else {
        top.msgWargin("自然保护区信息保存失败，请重新操作");
    }
}

function addInfo() {
    top.OpenModalWindow("自然保护区信息", "/sys/project/searchInfo/viewZrbhq.jsp?topnum="+top.curTabIndex, 485, 536);
}


function changeStatus(value) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", value);
    m.put("ids", selectIDS);

    if (ZrbhqRPC.changeStatus(m)) {
        top.msgAlert("自然保护区信息状态设置成功");
        reloadZrbhqList();
    } else {
        top.msgWargin("自然保护区信息状态设置失败，请重新操作");
    }
}

