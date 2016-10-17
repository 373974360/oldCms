var SiteErrorRPC = jsonrpc.SiteErrorRPC;
var ErrorSiteBean = new Bean("com.deya.project.dz_siteError.ErrorSiteBean", true);
var selectBean = null;//当前选中项对象
var val = new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();
table.table_name = "ErrorSiteDiv";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件
var m = new Map();

function reloadErrorSiteList() {
    showList();
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("siteName", "站点名称", "170px", "", "", ""));
    colsList.add(setTitleClos("url", "网址", "150px", "", "", ""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id
}

function showList() {

    m.put("status", "0");
    m.put("sort_name", "id");
    m.put("sort_type", "desc");

    beanList = SiteErrorRPC.getAllErrorSiteList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("siteName").each(function (i) {
        if (i > 0) {
            $(this).html('<a href="javascript:openUpdateErrorSitePage(' + beanList.get(i - 1).id + ')">' + beanList.get(i - 1).siteName + '</a>');
        }
    });
    table.getCol("url").each(function (i) {
        if (i > 0) {
            $(this).html("<a href='" + beanList.get(i - 1).url + "' target='_blank'>" + beanList.get(i - 1).url + "</a>");
        }
    });

    Init_InfoTable(table.table_name);
}

//打开修改窗口
function openUpdateErrorSitePage(id) {
    var c_id;
    if (id != null) {
        c_id = id;
    } else {
        c_id = table.getSelecteCheckboxValue("id");
    }

    top.OpenModalWindow("纠错站点", "/sys/project/dz_siteError/viewErrorSite.jsp?id=" + c_id +"&topnum="+top.curTabIndex, 385, 210);

}


//删除信息
function deleteErrorSite() {
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("id", selectIDS);
    if (SiteErrorRPC.deleteErrorSite(m)) {
        top.msgAlert("纠错站点" + WCMLang.Delete_success);
        reloadErrorSiteList();
    }
    else {
        top.msgWargin("纠错站点" + WCMLang.Delete_fail);
    }
}

function updateErrorSiteData() {
    if(!standard_checkInputInfo("ErrorSite_table"))
    {
        return;
    }
    var url=$.trim($("#url").val());
    var strRegex="^((https|http|ftp|rtsp|mms)?://)"
        + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
        + "|" // 允许IP和DOMAIN（域名）
        + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
        + "[a-z]{2,6})" // first level domain- .com or .museum
        + "(:[0-9]{1,4})?" // 端口- :80
        + "((/?)|" // a slash isn't required if there is no file name
        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
    var re=new RegExp(strRegex);
    if(!re.test(url))
    {
        jQuery.simpleTips("url","网站链接不符合格式！",2000);
        return;
    }
    var bean = BeanUtil.getCopy(ErrorSiteBean);
    var bool = false;
    if (id != null && id != "") {
        bean.id = id;
        $("#ErrorSite_table").autoBind(bean);
        bool = SiteErrorRPC.updateErrorSite(bean);
    }
    else {
        $("#ErrorSite_table").autoBind(bean);
        bean.id = 0;
        bean.status = "0";
        bool = SiteErrorRPC.insertErrorSite(bean);
    }
    if (bool) {
        top.msgAlert("纠错站点保存成功");
        top.getCurrentFrameObj(topnum).reloadErrorSiteList();
        top.CloseModalWindow();

    } else {
        top.msgWargin("纠错站点保存失败，请重新操作");
    }

}

function addInfo() {
    top.OpenModalWindow("纠错站点", "/sys/project/dz_siteError/viewErrorSite.jsp?topnum="+top.curTabIndex, 385, 210);
}