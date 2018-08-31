var SzbRPC = jsonrpc.SzbRPC;
var SzbBean = new Bean("com.deya.project.dz_szb.SzbBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "SzbDiv";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadSzbList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("id", "ID", "50px", "", "", ""));
    colsList.add(setTitleClos("title", "标题", "", "", "", ""));
    colsList.add(setTitleClos("createTime.time", "创建时间", "160px", "", "", ""));
    colsList.add(setTitleClos("status", "状态 ", "130px", "", "", ""));
    colsList.add(setTitleClos("preview", "预览 ", "130px", "", "", ""));

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

    beanList = SzbRPC.getSzbList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("status").each(function (i) {
        if (i > 0) {
            if (beanList.get(i - 1).status == "0") {
                $(this).html("未发布");
            }
            if (beanList.get(i - 1).status == "1") {
                $(this).html("已发布");
            }
        }
    });
    table.getCol("preview").each(function (i) {
        if (i > 0) {
            $(this).html('<a target="_blank" href="/sys/project/dz_szb/preview.jsp?id=' + beanList.get(i - 1).id + '" >预览</a>');
        }
    });
    table.getCol("createTime.time").each(function (i) {
        if (i > 0) {
            $(this).html(moment(beanList.get(i - 1).createTime.time).format('YYYY-MM-DD hh:mm:ss'));
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){
    var m = new Map();
    m.put("status", status);

    tp.total = SzbRPC.getSzbCount(m);

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
    }
    snum = num;
    reloadSzbList();
}

//打开修改窗口
function openUpdateSzbPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    top.addTab(true,"/sys/project/dz_szb/viewSzb.jsp?id=" + c_id +"&topnum="+top.curTabIndex,"数字报信息");
}


//删除信息
function deleteSzb() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("id", selectIDS);
    if (SzbRPC.deleteSzb(m)) {
            top.msgAlert("数字报信息" + WCMLang.Delete_success);
        reloadSzbList();                    
    }
    else {
        top.msgWargin("数字报信息" + WCMLang.Delete_fail);
    }
}

function updateSzbData() {
    if(!standard_checkInputInfo("siteError_table"))
    {
        return;
    }
    var bean = BeanUtil.getCopy(SzbBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#siteError_table").autoBind(bean);
        bool = SzbRPC.updateSzb(bean);
    }
    else {
        $("#siteError_table").autoBind(bean);
        bean.id = 0;
        bean.status = "0";
        bool = SzbRPC.insertSzb(bean);
    }
    if (bool) {
        top.msgAlert("数字报信息保存成功");
        top.getCurrentFrameObj(topnum).reloadSzbList();
        top.CloseModalWindow();
    } else {
        top.msgWargin("数字报信息保存失败，请重新操作");
    }
}

function addInfo() {
    top.addTab(true,"/sys/project/dz_szb/viewSzb.jsp?topnum="+top.curTabIndex,"数字报信息");
}

function savePublishFlag(publish_flag) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", publish_flag);
    m.put("id", selectIDS);

    if (SzbRPC.publishSzb(m)) {
        top.msgAlert("数字报信息发布成功");
        reloadSzbList();
    } else {
        top.msgWargin("数字报信息状态设置失败，请重新操作");
    }
}
