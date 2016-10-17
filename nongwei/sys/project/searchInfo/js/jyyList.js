var JyyRPC = jsonrpc.JyyRPC;
var JyyBean = new Bean("com.deya.project.searchInfo.JyyBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "jyydiv";
var current_role_bean;
var is_button_click = true; //是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadJyyList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("xm", "姓名", "100px", "", "", ""));
    colsList.add(setTitleClos("xb", "性别", "100px", "", "", ""));
    colsList.add(setTitleClos("gzdw", "工作单位 ", "230px", "", "", ""));
    colsList.add(setTitleClos("jyfw", "检验范围 ", "200px", "", "", ""));
    colsList.add(setTitleClos("zsh", "证书号 ", "150px", "", "", ""));

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

    beanList = JyyRPC.getJyyList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("xm").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateJyyPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).xm + '</a>');
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){
    var m = new Map();

    tp.total = JyyRPC.getJyyCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdateJyyPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    top.OpenModalWindow("检验员信息", "/sys/project/searchInfo/viewJyy.jsp?id=" + c_id + "&topnum="+top.curTabIndex, 485, 433);
}


//删除信息
function deleteJyy() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (JyyRPC.deleteJyy(m)) {
        top.msgAlert("检验员信息" + WCMLang.Delete_success);
        reloadJyyList();
    }
    else {
        top.msgWargin("检验员信息" + WCMLang.Delete_fail);
    }
}

function updateJyyData() {
    if(!standard_checkInputInfo("jyy_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(JyyBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#jyy_table").autoBind(bean);
        bool = JyyRPC.updateJyy(bean);
    }
    else {
        $("#jyy_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = JyyRPC.insertJyy(bean);
    }
    if (bool) {
        top.msgAlert("检验员信息保存成功");
        top.getCurrentFrameObj(topnum).reloadJyyList();
        top.CloseModalWindow();
    } else {
        top.msgWargin("检验员信息保存失败，请重新操作");
    }
}

function addInfo() {
    top.OpenModalWindow("检验员信息", "/sys/project/searchInfo/viewJyy.jsp?topnum="+top.curTabIndex, 485, 433);
}


function changeStatus(value) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", value);
    m.put("ids", selectIDS);

    if (JyyRPC.changeStatus(m)) {
        top.msgAlert("检验员信息状态设置成功");
        reloadJyyList();
    } else {
        top.msgWargin("检验员信息状态设置失败，请重新操作");
    }
}

