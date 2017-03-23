var MarketTypeRPC = jsonrpc.MarketTypeRPC;
var ProductTypeRPC = jsonrpc.ProductTypeRPC;
var ProductTypeBean = new Bean("com.deya.project.priceMonitor.ProductTypeBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "productTypediv";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadProductTypeList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("typeName", "分类名称", "170px", "", "", ""));
    colsList.add(setTitleClos("marketName", "市场分类", "170px", "", "", ""));
    colsList.add(setTitleClos("addTime", "添加时间", "150px", "", "", ""));
    colsList.add(setTitleClos("comments", "备注 ", "230px", "", "", ""));
    colsList.add(setTitleClos("status", "状态 ", "130px", "", "", ""));

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
    m.put("sort_name", "id");
    m.put("sort_type", "desc");

    beanList = ProductTypeRPC.getProductTypeList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("typeName").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateProductTypePage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).typeName + '</a>');
        }
    });

    table.getCol("status").each(function (i) {
        if (i > 0) {
            if (beanList.get(i - 1).status == "0") {
                $(this).html("禁用");
            }
            if (beanList.get(i - 1).status == "1") {
                $(this).html("正常");
            }
        }
    });

    Init_InfoTable(table.table_name);
}

function showTurnPage(){
    var m = new Map();

    tp.total = ProductTypeRPC.getProductTypeCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdateProductTypePage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    OpenModalWindow("品种分类信息", "/sys/project/priceMonitor/viewProductType.jsp?id=" + c_id + "&topnum="+curTabIndex, 485, 338);
}


//删除信息
function deleteProductType() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (ProductTypeRPC.deleteProductType(m)) {
        msgAlert("品种分类信息" + WCMLang.Delete_success);
        reloadProductTypeList();
    }
    else {
        msgWargin("品种分类信息" + WCMLang.Delete_fail);
    }
}

function updateProductTypeData() {
    if(!standard_checkInputInfo("productType_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(ProductTypeBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#productType_table").autoBind(bean);
        bool = ProductTypeRPC.updateProductType(bean);
    }
    else {
        $("#productType_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = ProductTypeRPC.insertProductType(bean);
    }
    if (bool) {
        msgAlert("品种分类信息保存成功");
        getCurrentFrameObj(topnum).reloadProductTypeList();
        CloseModalWindow();
    } else {
        msgWargin("品种分类信息保存失败，请重新操作");
    }
}

function addInfo() {
    OpenModalWindow("品种分类信息", "/sys/project/priceMonitor/viewProductType.jsp?topnum="+curTabIndex, 485, 338);
}

function changeStatus(value) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", value);
    m.put("ids", selectIDS);

    if (ProductTypeRPC.changeStatus(m)) {
        msgAlert("品种分类信息状态设置成功");
        reloadProductTypeList();
    } else {
        msgWargin("品种分类信息状态设置失败，请重新操作");
    }
}
