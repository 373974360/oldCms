var SiteErrorRPC = jsonrpc.SiteErrorRPC;
var SiteErrorBean = new Bean("com.deya.project.dz_siteError.SiteErrorBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "SiteErrorDiv";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadSiteErrorList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("siteId", "站点名称", "170px", "", "", ""));
    colsList.add(setTitleClos("typeId", "错误类型", "150px", "", "", ""));
    colsList.add(setTitleClos("errorUrl", "错误地址 ", "130px", "", "", ""));
    colsList.add(setTitleClos("addTime", "提交时间 ", "130px", "", "", ""));
    colsList.add(setTitleClos("status", "处理状态 ", "130px", "", "", ""));

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
    m.put("status", status);
    m.put("sort_name", "id");
    m.put("sort_type", "desc");

    beanList = SiteErrorRPC.getSiteErrorList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("siteId").each(function (i) {
        if (i > 0) {
            if(beanList.get(i - 1).siteId != "" && beanList.get(i - 1).siteId != 0){
                $(this).html('<a href="javascript:openUpdateSiteErrorPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).siteName + '</a>');
            }
            else{
                $(this).html('<a href="javascript:openUpdateSiteErrorPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).submitSiteName + '</a>');
            }
        }
    });
    table.getCol("typeId").each(function (i) {
        if (i > 0) {
            if(beanList.get(i - 1).typeId != "" && beanList.get(i - 1).typeId != 0){
                $(this).html(beanList.get(i - 1).typeName);
            }
            else{
                $(this).html(beanList.get(i - 1).submitTypeName);
            }
        }
    });
    table.getCol("errorUrl").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="'+ beanList.get(i - 1).errorUrl + '">' + beanList.get(i - 1).errorUrl + '</a>');
        }
    });
    table.getCol("status").each(function (i) {
        if (i > 0) {
            if (beanList.get(i - 1).status == "0") {
                $(this).html("未发布，未处理");
            }
            if (beanList.get(i - 1).status == "1") {
                $(this).html("已发布，处理中");
            }
            if (beanList.get(i - 1).status == "2") {
                $(this).html("已发布，已处理");
            }
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){
    var m = new Map();
    m.put("status", status);

    tp.total = SiteErrorRPC.getSiteErrorCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

function initTabAndStatus()
{
    $(".fromTabs > li").each(function(){
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

                if($(this).attr("num") != "" && $(this).attr("num") != null)
                {
                    $("#listTable_"+$(this).attr("num")).removeClass("hidden");
                    changeStatus(parseInt($(this).attr("num")));
                }
                else
                {
                    $("#listTable_"+$(this).index()).removeClass("hidden");
                    changeStatus($(this).index());
                }
            }
        );
    });
}

function changeStatus(num){
    switch(num){
        case 0:
            status = "0";
            break;
        case 1:
            status = "1";
            break;
        case 2:
            status = "2";
            break;
    }
    snum = num;
    reloadSiteErrorList();
}

//打开修改窗口
function openUpdateSiteErrorPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    addTab(true,"/sys/project/dz_siteError/viewSiteError.jsp?id=" + c_id +"&topnum="+curTabIndex,"站点纠错信息");
}


//删除信息
function deleteSiteError() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("id", selectIDS);
    if (SiteErrorRPC.deleteSiteError(m)) {
        msgAlert("站点纠错信息" + WCMLang.Delete_success);
        reloadSiteErrorList();
    }
    else {
        msgWargin("站点纠错信息" + WCMLang.Delete_fail);
    }
}

function updateSiteErrorData() {
    if(!standard_checkInputInfo("siteError_table"))
    {
        return;
    }

    /*
    var phone=$.trim($("#phone").val());

    var reg = /^1[3|4|5|7|8][0-9]\d{4,8}$/;
    if(!reg.test(phone))
    {
        jQuery.simpleTips("phone","手机号码格式不对！",2000);
        return;
    }
    */

    var bean = BeanUtil.getCopy(SiteErrorBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#siteError_table").autoBind(bean);
        bool = SiteErrorRPC.updateSiteError(bean);
    }
    else {
        $("#siteError_table").autoBind(bean);
        bean.id = 0;
        bean.status = "0";
        bool = SiteErrorRPC.insertSiteError(bean);
    }
    if (bool) {
        msgAlert("站点纠错信息保存成功");
        getCurrentFrameObj(topnum).reloadSiteErrorList();
        CloseModalWindow();
    } else {
        msgWargin("站点纠错信息保存失败，请重新操作");
    }
}

function addInfo() {
    addTab(true,"/sys/project/dz_siteError/viewSiteError.jsp?topnum="+curTabIndex,"站点纠错信息");
}

function savePublishFlag(publish_flag) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", publish_flag);
    m.put("id", selectIDS);

    if (SiteErrorRPC.publishSiteError(m)) {
        msgAlert("站点纠错信息状态设置成功");
        reloadSiteErrorList();
    } else {
        msgWargin("站点纠错信息状态设置失败，请重新操作");
    }
}
