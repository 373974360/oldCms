var MessageRPC = jsonrpc.MessageRPC;
var MessageBean = new Bean("com.deya.project.dz_message.MessageBean",true);
var val = new Validator();

defaultBean

function addInfoData() {
    var bean = BeanUtil.getCopy(MessageBean);
    $("#info_article_table").autoBind(bean);
    bean.contents = getV(contentId);
    if(!standard_checkInputInfo("info_article_table"))
    {
        return;
    }
    if (MessageRPC.insertMessage(bean)) {
        top.msgAlert("信息" + WCMLang.Add_success);
        top.getCurrentFrameObj(topnum).reloadInfoDataList();
        top.tab_colseOnclick(top.curTabIndex);
    }
    else {
        top.msgWargin("信息" + WCMLang.Add_fail);
    }
}


function updateInfoData() {
    var bean = BeanUtil.getCopy(MessageBean);
    $("#info_article_table").autoBind(bean);
    bean.contents = getV(contentId);
    if(!standard_checkInputInfo("info_article_table"))
    {
        return;
    }
    if (MessageRPC.updateMessage(bean)) {
        top.msgAlert("信息" + WCMLang.Add_success);
        top.getCurrentFrameObj(topnum).reloadInfoDataList();
        top.tab_colseOnclick(top.curTabIndex);
    }
    else {
        top.msgWargin("信息" + WCMLang.Add_fail);
    }
}