var LouPanRPC = jsonrpc.LouPanRPC;

var loupanBena = new Bean("com.deya.project.dz_house.LouPanBean", true);

var val = new Validator();
var tp = new TurnPage();
var beanList = null;
var table = new Table();
table.table_name = "table";
var search = "";

/********** 显示table begin*************/
function reloadLoupanList() {
    showList();
    showTurnPage();
}
function initTable() {
    table = new Table();
    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("name", "楼盘名称", "100px", "", "", ""));//英文名，显示名，宽，高，样式名，点击事件　
    colsList.add(setTitleClos("tel", "联系电话", "100px", "", "", ""));
    colsList.add(setTitleClos("address", "地理位置", "200px", "", "", ""));
    colsList.add(setTitleClos("opentime", "开盘时间", "80px", "", "", ""));
    colsList.add(setTitleClos("jzjg", "建筑结构", "80px", "", "", ""));
    colsList.add(setTitleClos("developers", "开发商", "200px", "", "", ""));
    colsList.add(setTitleClos("property", "物业公司", "200px", "", "", ""));
    colsList.add(setTitleClos("propertytype", "物业类型", "80px", "", "", ""));
    colsList.add(setTitleClos("lhl", "绿化率(%)", "80px", "", "", ""));
    colsList.add(setTitleClos("rjl", "容积率(%)", "80px", "", "", ""));
    colsList.add(setTitleClos("jzmj", "建筑面积(㎡)", "80px", "", "", ""));
    colsList.add(setTitleClos("zdmj", "占地面积(㎡)", "80px", "", "", ""));
    colsList.add(setTitleClos("jzlx", "建筑类型", "80px", "", "", ""));
    colsList.add(setTitleClos("yszh", "预售证号", "300px", "", "", ""));

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
    beanList = LouPanRPC.getLouPanList(search, start, pageSize);
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
    tp.total = LouPanRPC.getLouPanCount(search);
    tp.show("turn", "");
    tp.onPageChange = showList;
}
/********** 显示table end*************/
//添加
function insertLouPanPage() {
    window.location.href = 'add_loupan.jsp';
}
function insertLouPan() {
    var bean = BeanUtil.getCopy(loupanBena);
    $("#loupan").autoBind(bean);

    var result = true;
    if (!standard_checkInputInfo("loupan")) {
        result = false;
    }
    if (!result) {
        return;
    }
    if (bean.id.trim() == 0) {
        if (LouPanRPC.insertLouPan(bean)) {
            top.msgAlert("楼盘信息" + WCMLang.Add_success);
            window.location.href = 'loupanList.jsp';
        } else {
            top.msgWargin("楼盘信息" + WCMLang.Add_fail);
        }
    } else {
        if (LouPanRPC.updateLouPan(bean)) {
            top.msgAlert("楼盘信息" + WCMLang.Add_success);
            window.location.href = 'loupanList.jsp';
        } else {
            top.msgWargin("楼盘信息" + WCMLang.Add_fail);
        }
    }
}
//修改
function updateLouPanPage() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    window.location.href = "add_loupan.jsp?id=" + selectIDS;
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

//删除楼盘信息
function deleteLouPan(){
    var selectIDS = table.getSelecteCheckboxValue("id");
    var tempA = selectIDS.split(",")
    for(var i=0;i<tempA.length;i++){
        if(LouPanRPC.deleteLouPan(tempA[i]) == -1){
            top.msgWargin("该楼盘下有楼宇信息<br>请先删除楼宇信息");
            return;
        }
        if(LouPanRPC.deleteLouPan(tempA[i]) == 0){
            top.msgWargin("楼盘信息不存在");
            return;
        }
        if(LouPanRPC.deleteLouPan(tempA[i]) == 1){
            reloadLoupanList();
        }
    }
    if(selectIDS == ""){
        top.msgAlert("请选择要删除的记录");
        return;
    }
}


