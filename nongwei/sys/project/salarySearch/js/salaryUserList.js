var SalaryUserRPC = jsonrpc.SalaryUserRPC;
var SalaryUserBean = new Bean("com.deya.project.salarySearch.SalaryUserBean", true);
var selectBean = null;//当前选中项对象
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "salaryUserTable";
var is_button_click = true; //是否是点击的按钮触发事件
var status = 0;
var m = new Map();

function reloadSalaryUserList() {
    showList();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("name", "姓名", "150px", "", "", ""));
    colsList.add(setTitleClos("department", "单位名称", "170px", "", "", ""));
    colsList.add(setTitleClos("identify", "身份证号", "170px", "", "", ""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id
}

function showList() {

    beanList = SalaryUserRPC.getSalaryUserList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("name").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateSalaryUserPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).name + '</a>');
        }
    });

    Init_InfoTable(table.table_name);
}

//打开修改窗口
function openUpdateSalaryUserPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    top.OpenModalWindow("人员信息", "/sys/project/salarySearch/viewSalaryUser.jsp?id=" + c_id + "&topnum="+top.curTabIndex, 485, 373);
}


//删除信息
function deleteSalaryUser() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (SalaryUserRPC.deleteSalaryUser(m)) {
        top.msgAlert("人员信息" + WCMLang.Delete_success);
        reloadSalaryUserList();
    }
    else {
        top.msgWargin("人员信息" + WCMLang.Delete_fail);
    }
}

function updateSalaryUserData() {
    if(!standard_checkInputInfo("salaryUser_table"))
    {
        return;
    }

    if($("#password").val() != $("#password2").val())
    {
        val.addError("password2", "两次输入密码不同，请重新输入！");
        $("#password2").focus();
        return;
    }

    var bean = BeanUtil.getCopy(SalaryUserBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        bean.status = "1";
        $("#salaryUser_table").autoBind(bean);
        bool = SalaryUserRPC.updateSalaryUser(bean);
    }
    else {
        $("#salaryUser_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = SalaryUserRPC.insertSalaryUser(bean);
    }
    if (bool) {
        top.msgAlert("人员信息保存成功");
        top.getCurrentFrameObj(topnum).reloadSalaryUserList();
        top.CloseModalWindow();
    } else {
        top.msgWargin("人员信息保存失败，请重新操作");
    }
}

function addInfo() {
    top.OpenModalWindow("人员信息", "/sys/project/salarySearch/viewSalaryUser.jsp?topnum="+top.curTabIndex, 485, 373);
}
