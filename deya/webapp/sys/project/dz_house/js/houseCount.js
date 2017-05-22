var LouPanRPC = jsonrpc.LouPanRPC;

var beanList = null;
var table = new Table();
table.table_name = "table";
var search = "";

/********** 显示table begin*************/
function reloadHouseCountList() {
    showList();
}
function initTable() {
    table = new Table();
    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("name", "楼盘名称", "100px", "", "", ""));//英文名，显示名，宽，高，样式名，点击事件　
    colsList.add(setTitleClos("nums", "房间总数", "100px", "", "", ""));
    colsList.add(setTitleClos("jzmj", "总建筑面积", "100px", "", "", ""));
    colsList.add(setTitleClos("symj", "总使用面积", "100px", "", "", ""));
    colsList.add(setTitleClos("ysnums", "已售房间总数", "150px", "", "", ""));
    colsList.add(setTitleClos("ysjzmj", "已售建筑面积", "150px", "", "", ""));
    colsList.add(setTitleClos("yssymj", "已售使用面积", "150px", "", "", ""));
    colsList.add(setTitleClos("wsnums", "未售房间总数", "150px", "", "", ""));
    colsList.add(setTitleClos("wsjzmj", "未售建筑面积", "150px", "", "", ""));
    colsList.add(setTitleClos("wssymj", "未售使用面积", "150px", "", "", ""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id
}
function showList() {
    var sortCol = table.sortCol;
    var sortType = table.sortType;
    if (sortCol == "codes" || sortCol == null) {
        sortCol = "";
        sortType = "desc";
    }
    beanList = LouPanRPC.getCountList();
    beanList = List.toJSList(beanList);//把list转成JS的List对象
    //alert(toJSON(beanList.get(0)));//然后就可以调用get(),add()等方法了
    //alert(beanList);
    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show();
    Init_InfoTable(table.table_name);
}
/********** 显示table end*************/



