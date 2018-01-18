var DeptRPC = jsonrpc.DeptRPC;
var SiteRPC = jsonrpc.SiteRPC;
var defaultBean;

//公用初始加载方法
function reloadPublicInfo()
{
    rela_site_id = site_id;
    imgDomain = jsonrpc.MateInfoRPC.getImgDomain(rela_site_id);
    $("#input_user").val(LoginUserBean.user_id);
}
//修改信息时页面加载方法
function reloadPublicUpdateInfo()
{
    $("#modify_user").val(LoginUserBean.user_id);
}