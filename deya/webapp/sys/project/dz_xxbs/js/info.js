var DeptRPC = jsonrpc.DeptRPC;
var XxbsRPC = jsonrpc.XxbsRPC;


var deptBean = DeptRPC.getDeptBeanByID(LoginUserBean.dept_id);
var infoStatus = "1";
var tp = new TurnPage();
var table = new Table();
var current_page_num = 1;
var is_save_first_page = false;//操作成功后是否保存在第一页
var beanList = null;


function reloadInfoDataList() {
    initTable();
    if (is_save_first_page == true)
        current_page_num = 1;

    tp.curr_page = current_page_num;
    showTurnPage();
    showList();
    is_save_first_page = false;
}

function initTable() {
    var colsMap = new Map();
    var colsList = new List();
    colsList.add(setTitleClos("title", "标题", "", "", "", ""));//英文名，显示名，宽，高，样式名，点击事件
    if (infoStatus == 2) {
        colsList.add(setTitleClos("cat_name", "采用栏目", "", "", "", ""));
    } else {
        colsList.add(setTitleClos("actions", "管理操作", "90px", "", "", ""));
    }
    colsList.add(setTitleClos("do_dept_name", "报送单位", "60px", "", "", ""));
    colsList.add(setTitleClos("editor", "报送人", "60px", "", "", ""));
    colsList.add(setTitleClos("released_dtime", "报送时间", "120px", "", "", ""));
    if (infoStatus == 3) {
        colsList.add(setTitleClos("auto_desc", "退稿意见", "", "", "", ""));
    }
    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id

}

function showList() {
    var sortCol = table.sortCol;
    if (sortCol == "" || sortCol == null) {
        sortCol = "1";
    }
    var m = new Map();
    m.put("dept_id", deptBean.dept_id + '');
    m.put("user_id", LoginUserBean.user_id + '');
    m.put("orderByFields", sortCol);
    if (table.con_value.trim() != "" && table.con_value != null) {
        m.put("search", table.con_value);
    }
    m.put("info_status", infoStatus + '');
    m.put("start_num", tp.getStart() + '');
    m.put("page_size", tp.pageSize + '');

    beanList = XxbsRPC.getXxbsInfoList(m);
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    var isChangeDelete = false;
    //判断是否是站点管理员或超级管理员
    if (top.isSiteManager(app, site_id)) {
        isChangeDelete = true;
    }

    table.getCol("title").each(function (i) {
        $(this).css({"text-align": "left"});
        if (i > 0) {
            var title_flag = "";
            var title_end_str = "";
            $(this).css("padding-left", "20px");
            $(this).html('<a href="javascript:openViewPage(' + beanList.get(i - 1).id + ')">' + title_flag + beanList.get(i - 1).title + '</a>' + title_end_str);
        }
    });
    table.getCol("actions").each(function (i) {
        if (i > 0) {
            $(this).css({"text-align": "center"});
            var nm = parseInt(infoStatus);
            var str = "<ul class=\"optUL\">";
            switch (nm) {
                case 1:
                    str += "<li id='537' class='ico_edit'><a title='修改' href='javascript:openUpdateInfoPage(" + beanList.get(i - 1).id + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    str += "<li id='538' class='ico_delete'><a title='删除' href='javascript:doDelete(" + (i - 1) + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    break;
                case 3:
                    str += "<li id='537' class='ico_edit'><a title='修改' href='javascript:openUpdateInfoPage(" + beanList.get(i - 1).id + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    str += "<li id='538' class='ico_delete' ><a title='删除' href='javascript:doDelete(" + (i - 1) + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    break;
            }
            $(this).html(str + "</ul>");
        }
    });
    setUserOperateLI(table.table_name);
    current_page_num = tp.curr_page;
    Init_InfoTable(table.table_name);
}

function showTurnPage() {
    var sortCol = table.sortCol;
    if (sortCol == "" || sortCol == null) {
        sortCol = "1";
    }
    var m = new Map();
    m.put("dept_id", deptBean.dept_id + '');
    m.put("user_id", LoginUserBean.user_id + '');
    m.put("orderByFields", sortCol);
    if (table.con_value.trim() != "" && table.con_value != null) {
        m.put("search", table.con_value);
    }
    m.put("info_status", infoStatus + '');
    tp.total = XxbsRPC.getXxbsInfoCount(m);
    tp.show("turn", "");
    tp.onPageChange = showList;
}

function searchInfo() {
    var keywords = $("#searchkey").val();
    if (keywords.trim() == "" || keywords == null) {
        top.msgAlert(WCMLang.Search_empty);
        return;
    }
    table.con_value = keywords;
    table.sortCol = $("#orderByFields").val();

    reloadInfoDataList();
}

function openAddInfoPage() {
    top.addTab(true, "/sys/project/dz_xxbs/info_add.jsp?topnum=" + top.curTabIndex, "添加信息");
}

function openUpdateInfoPage(id) {
    top.addTab(true, "/sys/project/dz_xxbs/info_add.jsp?topnum=" + top.curTabIndex + "&id=" + id, "修改信息");
}

function openViewPage(id) {
    top.addTab(true, "/sys/project/dz_xxbs/info_view.jsp?topnum=" + top.curTabIndex + "&id=" + id+"&site_id="+site_id, "信息预览");
}

function setUserOperateLI(table_name) {
    $("#" + table_name + " li[id]").hide();
    $("#" + table_name + " li[id]").each(function () {
        var o_id = "," + $(this).attr("id") + ",";
        if (opt_ids.indexOf(o_id) > -1)
            $(this).show();
    });
}

function initTabAndStatus() {
    $(".fromTabs > li").each(function () {
        $(this).hover(
            function () {
                $(this).addClass("list_tab_over");
            },
            function () {
                $(this).removeClass("list_tab_over");
            }
        );

        $(this).click(
            function () {
                $(".fromTabs > li").removeClass("list_tab_cur");
                $(this).addClass("list_tab_cur");
                $(".infoListTable").addClass("hidden");

                if ($(this).attr("num") != "" && $(this).attr("num") != null) {
                    $("#listTable_" + $(this).attr("num")).removeClass("hidden");
                    changeStatus(parseInt($(this).attr("num")));
                }
                else {
                    $("#listTable_" + $(this).index()).removeClass("hidden");
                    changeStatus($(this).index());
                }
            }
        );
    });
}

function changeStatus(num) {
    current_page_num = 1;
    snum = num;
    infoStatus = "";
    table.sortCol = "";
    $("#orderByFields option").eq(0).attr("selected", true);
    clickLabelHandl(num);
    reloadInfoDataList();
}

//点击标签时重置属性
function clickLabelHandl(num) {
    switch (num) {
        case 0:
            infoStatus = "1";
            break;
        case 1:
            infoStatus = "2";
            break;
        case 2:
            infoStatus = "3";
            break;
    }
}

function updateInfoStatus(status) {
    var selectList = table.getSelecteBeans();
    if (XxbsRPC.updateInfoStatus(selectList, status)) {
        top.msgAlert("信息" + WCMLang.Delete_success);
        reloadInfoDataList();
    } else {
        top.msgWargin("信息" + WCMLang.Delete_fail);
    }
}

//单条信息逻辑删除
function doDelete(num) {
    top.msgConfirm(WCMLang.Delete_confirm, "doDeleteHandl(" + num + ")");
}

function doDeleteHandl(num) {
    var list = new List();
    list.add(beanList.get(num));
    if (XxbsRPC.deleteXxbs(list)) {
        top.msgAlert("信息" + WCMLang.Delete_success);
        reloadInfoDataList();
    } else {
        top.msgWargin("信息" + WCMLang.Delete_fail);
    }
}


function deleteInfoData() {
    var selectList = table.getSelecteBeans();
    if (XxbsRPC.deleteXxbs(selectList)) {
        top.msgAlert("信息" + WCMLang.Delete_success);
        reloadInfoDataList();
    } else {
        top.msgWargin("信息" + WCMLang.Delete_fail);
    }
}

//送审
function toPass() {
    top.msgConfirm("确认将选择的信息送审吗？", "toPassHandl()");
}

function toPassHandl() {
    var selectList = table.getSelecteBeans();
    if (XxbsRPC.updateInfoStatus(selectList, "1")) {
        top.msgAlert("信息送审成功");
        reloadInfoDataList();
    } else {
        top.msgWargin("信息送审成功");
    }
}

//退稿
function noPass(desc) {
    var selectList;
    if (noPassList != null)
        selectList = noPassList;
    else
        selectList = table.getSelecteBeans();

    if (XxbsRPC.updateNoPassStatus(selectList, desc)) {
        top.msgAlert("退回操作成功");
    } else {
        top.msgWargin("退回操作失败");
    }
    noPassList = null;
    reloadInfoDataList();
}

var noPassList;

function noPassDesc() {
    noPassList = table.getSelecteBeans();
    top.OpenModalWindow("退稿意见", "/sys/project/dz_xxbs/noPassDesc.jsp", 520, 235);
}


//采用
function openWindowForMov() {
    top.OpenModalWindow("信息采用", "/sys/project/dz_xxbs/infoMove.jsp?site_id=" + site_id + "&app_id=" + app, 400, 460);
}
function moveInfoHandl(cat_id) {
    var new_list = new List();
    var beans = new List();
    beans = table.getSelecteBeans();
    if (beans != null) {
        for (var i = beans.size() - 1; i >= 0; i--) {
            new_list.add(beans.get(i));
        }
    }
    if(XxbsRPC.infoPass(new_list, cat_id, app, site_id)){
        top.msgAlert("采用成功");
    }else{
        top.msgWargin("采用失败");
    }
    reloadInfoDataList();
}