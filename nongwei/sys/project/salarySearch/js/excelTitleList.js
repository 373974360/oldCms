var ExcelTitleRPC = jsonrpc.ExcelTitleRPC;
var ExcelTitleBean = new Bean("com.deya.project.salarySearch.ExcelTitleBean", true);
var selectBean = null;//当前选中项对象
var val = new Validator();
var beanList = null;
var table = new Table();
table.table_name = "excelTitleTable";
var is_button_click = true; //是否是点击的按钮触发事件
var status = 0;
var m = new Map();

function reloadExcelTitleList() {
    showList();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("cname", "中文名称", "150px", "", "", ""));
    colsList.add(setTitleClos("ename", "英文名称", "170px", "", "", ""));
    colsList.add(setTitleClos("typeId", "表头分类", "170px", "", "", ""));
    colsList.add(setTitleClos("isShow", "是否显示 ", "230px", "", "", ""));
    colsList.add(setTitleClos("sort_col","操作","180px","","",""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id
}

function showList() {

    beanList = ExcelTitleRPC.getExcelTitleList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("cname").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateExcelTitlePage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).cname + '</a>');
        }
    });

    table.getCol("typeId").each(function (i) {
        if (i > 0) {
            if (beanList.get(i - 1).typeId == "1") {
                $(this).html("工资表头");
            }
            if (beanList.get(i - 1).typeId == "2") {
                $(this).html("津贴表头");
            }
        }
    });

    table.getCol("isShow").each(function (i) {
        if (i > 0) {
            if (beanList.get(i - 1).isShow == "0") {
                $(this).html("隐藏");
            }
            if (beanList.get(i - 1).isShow == "1") {
                $(this).html("显示");
            }
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
function openUpdateExcelTitlePage(id) {
    var c_id;
    if (id != null && id != '') {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }
    top.OpenModalWindow("Excel表头信息", "/sys/project/salarySearch/viewExcelTitle.jsp?id=" + c_id + "&topnum="+top.curTabIndex, 485, 373);
}


//删除信息
function deleteExcelTitle() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    if (ExcelTitleRPC.deleteExcelTitle(m)) {
        top.msgAlert("Excel表头信息" + WCMLang.Delete_success);
        reloadExcelTitleList();
    }
    else {
        top.msgWargin("Excel表头信息" + WCMLang.Delete_fail);
    }
}

function updateExcelTitleData() {
    if(!standard_checkInputInfo("excelTitle_table"))
    {
        return;
    }

    var bean = BeanUtil.getCopy(ExcelTitleBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        bean.status = "1";
        $("#excelTitle_table").autoBind(bean);
        bool = ExcelTitleRPC.updateExcelTitle(bean);
    }
    else {
        $("#excelTitle_table").autoBind(bean);
        bean.id = 0;
        bean.status = "1";
        bool = ExcelTitleRPC.insertExcelTitle(bean);
    }
    if (bool) {
        top.msgAlert("Excel表头信息保存成功");
        top.getCurrentFrameObj(topnum).reloadExcelTitleList();
        top.CloseModalWindow();
    } else {
        top.msgWargin("Excel表头信息保存失败，请重新操作");
    }
}

function addInfo() {
    top.OpenModalWindow("Excel表头信息", "/sys/project/salarySearch/viewExcelTitle.jsp?topnum="+top.curTabIndex, 485, 373);
}

// 保存排序button事件
function saveSort()
{
    var ids = table.getAllCheckboxValue("id");
    if(ExcelTitleRPC.savaExcelTitleSort(ids))
    {
        top.msgAlert(WCMLang.Sort_success);
        reloadExcelTitleList();
    }
    else
    {
        top.msgWargin(WCMLang.Sort_fail);
    }
}

function changeType(typeId)
{
    if(typeId != "")
    {
        m.put("typeId",typeId);
    }
    else {
        m.remove("typeId");
    }
    showList();
}

function changeIsShow(isShow)
{
    if(isShow != "")
    {
        m.put("isShow",isShow);
    }
    else {
        m.remove("isShow");
    }
    showList();
}


//删除信息
function updateIsShow(isShow) {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("ids", selectIDS);
    m.put("isShow", isShow);
    if (ExcelTitleRPC.updateIsShow(m)) {
        top.msgAlert("Excel表头信息保存成功");
        reloadExcelTitleList();
    }
    else {
        top.msgWargin("Excel表头信息保存失败！");
    }
}

