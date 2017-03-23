var YjcpzsRPC = jsonrpc.YjcpzsRPC;
var YjcpzsBean = new Bean("com.deya.project.searchInfo.YjcpzsBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "yjcpzsdiv";
var current_role_bean;
var is_button_click = true; //是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadYjcpzsList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("cyrmc", "持有人名称", "120px", "", "", ""));
    colsList.add(setTitleClos("dz", "地址", "170px", "", "", ""));
    colsList.add(setTitleClos("jdmc", "基地（加工厂）名称 ", "150px", "", "", ""));
    colsList.add(setTitleClos("jddz", "基地（加工厂）地址 ", "150px", "", "", ""));
    colsList.add(setTitleClos("cpmc", "产品名称 ", "100px", "", "", ""));
    colsList.add(setTitleClos("cl", "产量", "80px", "", "", ""));
    colsList.add(setTitleClos("yxqx", "有效期限", "80px", "", "", ""));

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

    beanList = YjcpzsRPC.getYjcpzsList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("cyrmc").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateYjcpzsPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).cyrmc + '</a>');
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){
    var m = new Map();

    tp.total = YjcpzsRPC.getYjcpzsCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdateYjcpzsPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    OpenModalWindow("有机产品认证证书信息", "/sys/project/searchInfo/viewYjcpzs.jsp?id=" + c_id + "&topnum="+curTabIndex, 605, 533);
}


//删除信息
function deleteYjcpzs() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (YjcpzsRPC.deleteYjcpzs(m)) {
        msgAlert("有机产品认证证书信息" + WCMLang.Delete_success);
        reloadYjcpzsList();
    }
    else {
        msgWargin("有机产品认证证书信息" + WCMLang.Delete_fail);
    }
}

function updateYjcpzsData() {
    if(!standard_checkInputInfo("yjcpzs_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(YjcpzsBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#yjcpzs_table").autoBind(bean);
        bool = YjcpzsRPC.updateYjcpzs(bean);
    }
    else {
        $("#yjcpzs_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = YjcpzsRPC.insertYjcpzs(bean);
    }
    if (bool) {
        msgAlert("有机产品认证证书信息保存成功");
        getCurrentFrameObj(topnum).reloadYjcpzsList();
        CloseModalWindow();
    } else {
        msgWargin("有机产品认证证书信息保存失败，请重新操作");
    }
}

function addInfo() {
    OpenModalWindow("有机产品认证证书信息", "/sys/project/searchInfo/viewYjcpzs.jsp?topnum="+curTabIndex, 605, 533);
}


function changeStatus(value) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", value);
    m.put("ids", selectIDS);

    if (YjcpzsRPC.changeStatus(m)) {
        msgAlert("有机产品认证证书信息状态设置成功");
        reloadYjcpzsList();
    } else {
        msgWargin("有机产品认证证书信息状态设置失败，请重新操作");
    }
}

