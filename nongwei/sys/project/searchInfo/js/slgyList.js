var SlgyRPC = jsonrpc.SlgyRPC;
var SlgyBean = new Bean("com.deya.project.searchInfo.SlgyBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "slgydiv";
var current_role_bean;
var is_button_click = true; //是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadSlgyList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("gymc", "公园名称", "150px", "", "", ""));
    colsList.add(setTitleClos("zdmj", "占地面积", "170px", "", "", ""));
    colsList.add(setTitleClos("gyjb", "公园级别 ", "230px", "", "", ""));
    colsList.add(setTitleClos("zgbmmc", "主管部门名称 ", "130px", "", "", ""));
    colsList.add(setTitleClos("gydz", "公园地址 ", "130px", "", "", ""));
    colsList.add(setTitleClos("lxdh", "联系电话 ", "130px", "", "", ""));

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

    beanList = SlgyRPC.getSlgyList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("gymc").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateSlgyPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).gymc + '</a>');
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){
    var m = new Map();

    tp.total = SlgyRPC.getSlgyCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdateSlgyPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    top.OpenModalWindow("森林公园信息", "/sys/project/searchInfo/viewSlgy.jsp?id=" + c_id + "&topnum="+top.curTabIndex, 485, 453);
}


//删除信息
function deleteSlgy() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (SlgyRPC.deleteSlgy(m)) {
        top.msgAlert("森林公园信息" + WCMLang.Delete_success);
        reloadSlgyList();
    }
    else {
        top.msgWargin("森林公园信息" + WCMLang.Delete_fail);
    }
}

function updateSlgyData() {
    if(!standard_checkInputInfo("slgy_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(SlgyBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#slgy_table").autoBind(bean);
        bool = SlgyRPC.updateSlgy(bean);
    }
    else {
        $("#slgy_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = SlgyRPC.insertSlgy(bean);
    }
    if (bool) {
        top.msgAlert("森林公园信息保存成功");
        top.getCurrentFrameObj(topnum).reloadSlgyList();
        top.CloseModalWindow();
    } else {
        top.msgWargin("森林公园信息保存失败，请重新操作");
    }
}

function addInfo() {
    top.OpenModalWindow("森林公园信息", "/sys/project/searchInfo/viewSlgy.jsp?topnum="+top.curTabIndex, 485, 453);
}


function changeStatus(value) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", value);
    m.put("ids", selectIDS);

    if (SlgyRPC.changeStatus(m)) {
        top.msgAlert("森林公园信息状态设置成功");
        reloadSlgyList();
    } else {
        top.msgWargin("森林公园信息状态设置失败，请重新操作");
    }
}

