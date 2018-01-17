var DeptRPC = jsonrpc.DeptRPC;
var SiteRPC = jsonrpc.SiteRPC;

//公用初始加载方法
function reloadPublicInfo()
{
    rela_site_id = site_id;
    imgDomain = jsonrpc.MateInfoRPC.getImgDomain(rela_site_id);
    var deptBean = DeptRPC.getDeptBeanByID(LoginUserBean.dept_id);
    $("#do_dept").val(deptBean.dept_id);
    $("#do_dept_name").val(deptBean.dept_name);
    $("#editor").val(LoginUserBean.user_realname);
    $("#input_user").val(LoginUserBean.user_id);
}


function getReleSiteID() {
    var temp_site_id = site_id;
    if (app_id != "cms")
        temp_site_id = jsonrpc.SiteRPC.getSiteIDByAppID(app_id);
    return temp_site_id;
}


//
function getContentThumb() {
    $("#thumburlList").empty();
    var c = getV(contentId); //内容
    var re = new RegExp(/<img.*?src.*?=.*?(.*?)>/ig);
    var r = c.match(re);
    if (r != null && r.length > 0) {
        if (r.length > 1) {
            var strUrl = "";
            for (var i = 0; i < r.length; i++) {
                var src = r[i].replace(/<img[\s]*.*?src=\"(.*?)[\"](.*)/ig, "$1");
                strUrl += "<li style='float:left;margin-right:5px;'><div style='width:110px;height:130px;float:center;'><table>";
                strUrl += "<tr><td><img src=" + src + " height='100' width='100'></td></tr>";
                strUrl += "<tr><td style='padding-left:50px;'><input type='radio' name='radio' onchange='addthumb_url(this.value)' value='" + src + "'></td></tr>";
                if (i > 0 && i % 4 == 0) {
                    strUrl += "</table></div></li></br>";
                } else {
                    strUrl += "</table></div></li>";
                }
            }
            $("#thumburlList").append(strUrl);
        } else {
            var src = r[0].replace(/<img[\s]*.*?src=\"(.*?)[\"](.*)/ig, "$1");
            $("#thumb_url").val(src);
        }
    }
}