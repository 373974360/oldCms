var SiteErrorRPC = jsonrpc.SiteErrorRPC;
var ErrorHandleUserBean = new Bean("com.deya.project.dz_siteError.ErrorHandleUserBean", true);
var selectBean = null;//当前选中项对象
var val = new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();
table.table_name = "errorHandleUserDiv";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件
var m = new Map();

function reloadErrorHandleUserList() {
    showList();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("userName", "姓名", "170px", "", "", ""));
    colsList.add(setTitleClos("phone", "联系电话", "150px", "", "", ""));
    colsList.add(setTitleClos("isSendMsg", "是否发送短信 ", "130px", "", "", ""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id
}

function showList() {

    m.put("status", "0");
    m.put("sort_name", "id");
    m.put("sort_type", "desc");

    beanList = SiteErrorRPC.getAllErrorHandleUserList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("userName").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateErrorHandleUserPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).userName + '</a>');
        }
    });
    table.getCol("isSendMsg").each(function (i) {
        if (i > 0) {
            if (beanList.get(i - 1).isSendMsg == "1") {
                $(this).html("发送");
            }
            else {
                $(this).html("不发送");
            }
        }
    });

    Init_InfoTable(table.table_name);
}

//打开修改窗口
function openUpdateErrorHandleUserPage(id) {
    var c_id;
    if (id != null) {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }

    OpenModalWindow("纠错处理用户", "/sys/project/dz_siteError/viewErrorHandleUser.jsp?id=" + c_id +"&topnum="+curTabIndex, 385, 210);

}


//删除信息
function deleteErrorHandleUser() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("id", selectIDS);
    if (SiteErrorRPC.deleteErrorHandleUser(m)) {
        msgAlert("纠错处理用户" + WCMLang.Delete_success);
        reloadErrorHandleUserList();
    }
    else {
        msgWargin("纠错处理用户" + WCMLang.Delete_fail);
    }
}

function updateErrorHandleUserData() {
    if(!standard_checkInputInfo("errorHandleUser_table"))
    {
        return;
    }
    var phone=$.trim($("#phone").val());

    var reg = /^1[3|4|5|7|8][0-9]\d{4,8}$/;
    if(!reg.test(phone))
    {
        jQuery.simpleTips("phone","手机号码格式不对！",2000);
        return;
    }
    var bean = BeanUtil.getCopy(ErrorHandleUserBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#errorHandleUser_table").autoBind(bean);
        bool = SiteErrorRPC.updateErrorHandleUser(bean);
    }
    else {
        $("#errorHandleUser_table").autoBind(bean);
        bean.id = 0;
        bean.status = "0";
        bool = SiteErrorRPC.insertErrorHandleUser(bean);
    }
    if (bool) {
        msgAlert("纠错处理用户保存成功");
        getCurrentFrameObj(topnum).reloadErrorHandleUserList();
        CloseModalWindow();

    } else {
        msgWargin("纠错处理用户保存失败，请重新操作");
    }

}

function addInfo() {
    OpenModalWindow("纠错处理用户", "/sys/project/dz_siteError/viewErrorHandleUser.jsp?topnum="+curTabIndex, 385, 210);
}