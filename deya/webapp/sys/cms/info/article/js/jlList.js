var UserInfoRPC = jsonrpc.UserInfoRPC;
var UserInfoBean = new Bean("com.deya.project.dz_recruit.UserInfoBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "jlListDiv";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadJlList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("name", "姓名", "100px", "", "", ""));
    colsList.add(setTitleClos("gender", "性别", "100px", "", "", ""));
    colsList.add(setTitleClos("csny", "出生年月 ", "100px", "", "", ""));
    colsList.add(setTitleClos("xl", "学历 ", "130px", "", "", ""));
    colsList.add(setTitleClos("byyx", "毕业院校 ", "130px", "", "", ""));
    colsList.add(setTitleClos("phone", "手机号码 ", "130px", "", "", ""));
    colsList.add(setTitleClos("email", "电子邮箱 ", "130px", "", "", ""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id
}

function showList() {

    beanList = UserInfoRPC.getUserInfoList(info_id,tp.getStart(),tp.pageSize);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("name").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUserInfoPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).name + '</a>');
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){

    tp.total = UserInfoRPC.getUserInfoCount(info_id);

    tp.show("turn","");
    tp.onPageChange = showList;
}


//打开修改窗口
function openUserInfoPage(id) {
    addTab(true,"/sys/cms/info/article/viewUserInfo.jsp?id=" + id +"&topnum="+curTabIndex,"个人简历");
}