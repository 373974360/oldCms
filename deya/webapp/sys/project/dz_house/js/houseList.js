var LouYuRPC = jsonrpc.LouYuRPC;
var HouseRPC = jsonrpc.HouseRPC;

var houseBena = new Bean("com.deya.project.dz_house.HouseBean", true);

var val = new Validator();
var tp = new TurnPage();
var beanList = null;
var table = new Table();
table.table_name = "table";
var search = "";
var code="";
/********** 显示table begin*************/
function reloadHouseList() {
    showList();
    showTurnPage();
}
function initTable() {
    table = new Table();
    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("fjh", "房间号", "80px", "", "", ""));//英文名，显示名，宽，高，样式名，点击事件　
    colsList.add(setTitleClos("szdy", "所在单元", "80px", "", "", ""));
    colsList.add(setTitleClos("szlc", "所在楼层", "80px", "", "", ""));
    colsList.add(setTitleClos("jzmj", "建筑面积", "80px", "", "", ""));
    colsList.add(setTitleClos("symj", "使用面积", "80px", "", "", ""));
    colsList.add(setTitleClos("cx", "房间朝向", "80px", "", "", ""));
    colsList.add(setTitleClos("fjlx", "房间类型", "80px", "", "", ""));
    colsList.add(setTitleClos("fjzt", "房间状态", "80px", "", "", ""));

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
    beanList = HouseRPC.getHouselist(search,code,start,pageSize);
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
    tp.total = HouseRPC.getHouseCount(search,code);
    tp.show("turn", "");
    tp.onPageChange = showList;
}
/********** 显示table end*************/
//添加
function insertHousePage() {
    if(code.length==4){
        window.location.href = 'add_house.jsp?code='+code;
    }else{
        top.msgWargin("请选中楼宇添加房间信息");
    }
}
function insertHouse() {
    var bean = BeanUtil.getCopy(houseBena);
    $("#house").autoBind(bean);

    var result = true;
    if (!standard_checkInputInfo("house")) {
        result = false;
    }
    if (!result) {
        return;
    }
    if (bean.id.trim() == 0) {
        if (HouseRPC.insertHouse(bean)) {
            top.msgAlert("房间信息" + WCMLang.Add_success);
            window.location.href = 'houseList.jsp?code='+code;
        } else {
            top.msgWargin("房间信息" + WCMLang.Add_fail);
        }
    } else {
        if (HouseRPC.updateHouse(bean)) {
            top.msgAlert("房间信息" + WCMLang.Add_success);
            window.location.href = 'houseList.jsp?code='+code;
        } else {
            top.msgWargin("房间信息" + WCMLang.Add_fail);
        }
    }
}
//修改
function updateHousePage() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    window.location.href = "add_house.jsp?id=" + selectIDS+"&code="+code;
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


