var CwmzyyRPC = jsonrpc.CwmzyyRPC;
var CwmzyyBean = new Bean("com.deya.project.searchInfo.CwmzyyBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "cwmzyydiv";
var current_role_bean;
var is_button_click = true; //是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadCwmzyyList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("qy", "区域", "80px", "", "", ""));
    colsList.add(setTitleClos("md", "名单", "170px", "", "", ""));
    colsList.add(setTitleClos("dz", "地址 ", "230px", "", "", ""));
    colsList.add(setTitleClos("fr", "法人 ", "100px", "", "", ""));
    colsList.add(setTitleClos("dh", "电话 ", "130px", "", "", ""));

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

    beanList = CwmzyyRPC.getCwmzyyList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("md").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateCwmzyyPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).md + '</a>');
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){
    var m = new Map();

    tp.total = CwmzyyRPC.getCwmzyyCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdateCwmzyyPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    OpenModalWindow("宠物医院及门诊名单", "/sys/project/searchInfo/viewCwmzyy.jsp?id=" + c_id + "&topnum="+curTabIndex, 485, 433);
}


//删除信息
function deleteCwmzyy() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (CwmzyyRPC.deleteCwmzyy(m)) {
        msgAlert("宠物医院及门诊名单" + WCMLang.Delete_success);
        reloadCwmzyyList();
    }
    else {
        msgWargin("宠物医院及门诊名单" + WCMLang.Delete_fail);
    }
}

function updateCwmzyyData() {
    if(!standard_checkInputInfo("cwmzyy_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(CwmzyyBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#cwmzyy_table").autoBind(bean);
        bool = CwmzyyRPC.updateCwmzyy(bean);
    }
    else {
        $("#cwmzyy_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = CwmzyyRPC.insertCwmzyy(bean);
    }
    if (bool) {
        msgAlert("宠物医院及门诊名单保存成功");
        getCurrentFrameObj(topnum).reloadCwmzyyList();
        CloseModalWindow();
    } else {
        msgWargin("宠物医院及门诊名单保存失败，请重新操作");
    }
}

function addInfo() {
    OpenModalWindow("宠物医院及门诊名单", "/sys/project/searchInfo/viewCwmzyy.jsp?topnum="+curTabIndex, 485, 433);
}


function changeStatus(value) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", value);
    m.put("ids", selectIDS);

    if (CwmzyyRPC.changeStatus(m)) {
        msgAlert("宠物医院及门诊名单状态设置成功");
        reloadCwmzyyList();
    } else {
        msgWargin("宠物医院及门诊名单状态设置失败，请重新操作");
    }
}

