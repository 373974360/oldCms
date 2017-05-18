var LouYuRPC = jsonrpc.LouYuRPC;
var LouPanRPC = jsonrpc.LouPanRPC;

var louyuBena = new Bean("com.deya.project.dz_house.LouYuBean", true);

var val = new Validator();
var tp = new TurnPage();
var beanList = null;
var table = new Table();
table.table_name = "table";
var search = "";
var lpcode="";
/********** 显示table begin*************/
function reloadLouYuList() {
    showList();
    showTurnPage();
}
function initTable() {
    table = new Table();
    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("ldh", "楼栋号", "80px", "", "", ""));//英文名，显示名，宽，高，样式名，点击事件　
    colsList.add(setTitleClos("dys", "单元数", "80px", "", "", ""));
    colsList.add(setTitleClos("fjzs", "房间数", "80px", "", "", ""));
    colsList.add(setTitleClos("jzmj", "建筑面积", "80px", "", "", ""));
    colsList.add(setTitleClos("zdmj", "占地面积", "80px", "", "", ""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id
}
function showList() {
    start = tp.getStart();
    var pageSize = tp.pageSize;

    var sortCol = table.sortCol;
    var sortType = table.sortType;
    if (sortCol == "" || sortCol == null) {
        sortCol = "id";
        sortType = "desc";
    }
    beanList = LouYuRPC.getLouyuList(search,lpcode, start, pageSize);
    beanList = List.toJSList(beanList);//把list转成JS的List对象
    //alert(toJSON(beanList.get(0)));//然后就可以调用get(),add()等方法了
    //alert(beanList);
    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show();
    Init_InfoTable(table.table_name);
}
//分页
function showTurnPage() {
    tp.total = LouYuRPC.getLouYuCount(lpcode,search);
    tp.show("turn", "");
    tp.onPageChange = showList;
}
/********** 显示table end*************/
//添加
function insertLouYuPage() {
    if(lpcode==0||lpcode==null){
        top.msgWargin("请选中楼盘添加楼宇信息");
    }else{
        window.location.href = 'add_louyu.jsp?lpcode='+lpcode;
    }
}
function insertLouYu() {
    var bean = BeanUtil.getCopy(louyuBena);
    $("#louyu").autoBind(bean);

    var result = true;
    if (!standard_checkInputInfo("louyu")) {
        result = false;
    }
    if (!result) {
        return;
    }
    if (bean.id.trim() == 0) {
        if (LouYuRPC.insertLouYu(bean)) {
            top.msgAlert("楼宇信息" + WCMLang.Add_success);
            window.location.href = 'louyuList.jsp?lpcode='+lpcode;
        } else {
            top.msgWargin("楼宇信息" + WCMLang.Add_fail);
        }
    } else {
        if (LouYuRPC.updateLouYu(bean)) {
            top.msgAlert("楼宇信息" + WCMLang.Add_success);
            window.location.href = 'louyuList.jsp?lpcode='+lpcode;
        } else {
            top.msgWargin("楼宇信息" + WCMLang.Add_fail);
        }
    }
}
//修改
function updateLouYuPage() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    window.location.href = "add_louyu.jsp?id=" + selectIDS+"&lpcode="+lpcode;
}
//搜索
function searchHandl(obj) {
    var con_value = $(obj).parent().find("#searchkey").val();
    if (con_value.trim() == "" || con_value == null) {
        top.msgAlert(WCMLang.Search_empty);
        return;
    }
    search = con_value;
    showList();
    showTurnPage();
}


