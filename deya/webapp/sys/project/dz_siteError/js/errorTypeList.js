var SiteErrorRPC = jsonrpc.SiteErrorRPC;
var ErrorTypeBean = new Bean("com.deya.project.dz_siteError.ErrorTypeBean", true);
var selectBean = null;//当前选中项对象
var val = new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();
table.table_name = "ErrorTypeDiv";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件
var m = new Map();

function reloadErrorTypeList() {
    showList();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("typeName", "分类名称", "170px", "", "", ""));
    colsList.add(setTitleClos("sort_col","排序","120px","","",""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id
}

function showList() {

    m.put("status", "0");
    m.put("sort_name", "sort");
    m.put("sort_type", "asc");

    beanList = SiteErrorRPC.getAllErrorTypeList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("typeName").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateErrorTypePage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).typeName + '</a>');
        }
    });
    table.getCol("sort_col").each(function(i){
        if(i>0)
        {
            $(this).html(getSortColStr());
        }
    });

    Init_InfoTable(table.table_name);
}

//打开修改窗口
function openUpdateErrorTypePage(id) {
    var c_id;
    if (id != null) {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }

    OpenModalWindow("纠错分类", "/sys/project/dz_siteError/viewErrorType.jsp?id=" + c_id +"&topnum="+curTabIndex, 385, 210);

}


//删除信息
function deleteErrorType() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("id", selectIDS);
    if (SiteErrorRPC.deleteErrorType(m)) {
        msgAlert("纠错分类" + WCMLang.Delete_success);
        reloadErrorTypeList();
    }
    else {
        msgWargin("纠错分类" + WCMLang.Delete_fail);
    }
}

function updateErrorTypeData() {
    if(!standard_checkInputInfo("ErrorType_table"))
    {
        return;
    }
    var bean = BeanUtil.getCopy(ErrorTypeBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#ErrorType_table").autoBind(bean);
        bool = SiteErrorRPC.updateErrorType(bean);
    }
    else {
        $("#ErrorType_table").autoBind(bean);
        bean.id = 0;
        bean.status = "0";
        bean.sort = 999;
        bool = SiteErrorRPC.insertErrorType(bean);
    }
    if (bool) {
        msgAlert("纠错分类保存成功");
        getCurrentFrameObj(topnum).reloadErrorTypeList();
        CloseModalWindow();

    } else {
        msgWargin("纠错分类保存失败，请重新操作");
    }

}

function addInfo() {
    OpenModalWindow("纠错分类", "/sys/project/dz_siteError/viewErrorType.jsp?topnum="+curTabIndex, 385, 210);
}


// 保存排序button事件
function saveSort()
{
    var ids = table.getAllCheckboxValue("id");;
    if(SiteErrorRPC.saveErrorTypeSort(ids))
    {
        msgAlert(WCMLang.Sort_success);
        reloadErrorTypeList();
    }
    else
    {
        msgWargin(WCMLang.Sort_fail);
    }
}