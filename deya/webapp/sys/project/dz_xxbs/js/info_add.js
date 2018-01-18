var XxbsRPC = jsonrpc.XxbsRPC;
var XxbsBean = new Bean("com.deya.project.dz_xxbs.XxbsBean",true);
var val = new Validator();

defaultBean

function addInfoData() {
    var bean = BeanUtil.getCopy(XxbsBean);
    $("#info_article_table").autoBind(bean);
    bean.contents = getV(contentId);
    if(!standard_checkInputInfo("info_article_table"))
    {
        return;
    }
    if (XxbsRPC.insertXxbs(bean)) {
        top.msgAlert("信息" + WCMLang.Add_success);
        top.getCurrentFrameObj(topnum).reloadInfoDataList();
        top.tab_colseOnclick(top.curTabIndex);
    }
    else {
        top.msgWargin("信息" + WCMLang.Add_fail);
    }
}


function updateInfoData() {
    var bean = BeanUtil.getCopy(XxbsBean);
    $("#info_article_table").autoBind(bean);
    bean.contents = getV(contentId);
    if(!standard_checkInputInfo("info_article_table"))
    {
        return;
    }
    if (XxbsRPC.updateXxbs(bean)) {
        top.msgAlert("信息" + WCMLang.Add_success);
        top.getCurrentFrameObj(topnum).reloadInfoDataList();
        top.tab_colseOnclick(top.curTabIndex);
    }
    else {
        top.msgWargin("信息" + WCMLang.Add_fail);
    }
}