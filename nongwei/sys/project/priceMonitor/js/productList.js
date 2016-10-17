var MarketTypeRPC = jsonrpc.MarketTypeRPC;
var ProductRPC = jsonrpc.ProductRPC;
var ProductTypeRPC = jsonrpc.ProductTypeRPC;
var ProductBean = new Bean("com.deya.project.priceMonitor.ProductBean", true);
var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "productdiv";
var current_role_bean;
var is_button_click = true; //是否是点击的按钮触发事件
var current_page_num = 1;
var status = 0;

function reloadProductList() {
    showList();
    showTurnPage();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("productName", "产品名称", "150px", "", "", ""));
    colsList.add(setTitleClos("marketName", "市场分类", "170px", "", "", ""));
    colsList.add(setTitleClos("typeName", "分类名称", "170px", "", "", ""));
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

    beanList = ProductRPC.getProductList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("productName").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateProductPage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).productName + '</a>');
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

    tp.total = ProductRPC.getProductCount(m);

    tp.show("turn","");
    tp.onPageChange = showList;
}

//打开修改窗口
function openUpdateProductPage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    top.OpenModalWindow("蔬果产品信息", "/sys/project/priceMonitor/viewProduct.jsp?id=" + c_id + "&topnum="+top.curTabIndex, 485, 363);
}


//删除信息
function deleteProduct() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (ProductRPC.deleteProduct(m)) {
        top.msgAlert("蔬果产品信息" + WCMLang.Delete_success);
        reloadProductList();
    }
    else {
        top.msgWargin("蔬果产品信息" + WCMLang.Delete_fail);
    }
}

function updateProductData() {
    if(!standard_checkInputInfo("product_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(ProductBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#product_table").autoBind(bean);
        if(bean.marketId == 2)
        {
            bean.marketId = 1;
        }
        bool = ProductRPC.updateProduct(bean);
    }
    else {
        $("#product_table").autoBind(bean);
        if(bean.marketId == 2)
        {
            bean.marketId = 1;
        }
        bean.id = 0;
        bean.status = "1";
        bool = ProductRPC.insertProduct(bean);
    }
    if (bool) {
        top.msgAlert("蔬果产品信息保存成功");
        top.getCurrentFrameObj(topnum).reloadProductList();
        top.CloseModalWindow();
    } else {
        top.msgWargin("蔬果产品信息保存失败，请重新操作");
    }
}

function addInfo() {
    top.OpenModalWindow("蔬果产品信息", "/sys/project/priceMonitor/viewProduct.jsp?topnum="+top.curTabIndex, 485, 363);
}


function changeStatus(value) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("status", value);
    m.put("ids", selectIDS);

    if (ProductRPC.changeStatus(m)) {
        top.msgAlert("蔬果产品信息状态设置成功");
        reloadProductList();
    } else {
        top.msgWargin("蔬果产品信息状态设置失败，请重新操作");
    }
}

