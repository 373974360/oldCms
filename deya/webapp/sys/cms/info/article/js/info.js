var InfoBaseRPC = jsonrpc.InfoBaseRPC;
var GKInfoRPC = jsonrpc.GKInfoRPC;
var ArticleRPC = jsonrpc.ArticleRPC;
var SiteRPC = jsonrpc.SiteRPC;
var ModelRPC = jsonrpc.ModelRPC;
var CategoryRPC = jsonrpc.CategoryRPC;
var UserManRPC = jsonrpc.UserManRPC;
var MemberManRPC = jsonrpc.MemberManRPC;
var InfoBean = new Bean("com.deya.wcm.bean.cms.info.InfoBean", true);
var ArticleBean = new Bean("com.deya.wcm.bean.cms.info.ArticleBean", true);
var RelatedBean = new Bean("com.deya.wcm.bean.cms.info.RelatedInfoBean", true);
var model_map = ModelRPC.getModelMap();
model_map = Map.toJSMap(model_map);

////
var AssistRPC = jsonrpc.AssistRPC;

var selectBean = null;//当前选中项对象

var serarch_con = "";//查询条件
var search_steps = "";
var tj = "";
var tp = new TurnPage();
var beanList = null;
var table = new Table();
table.table_name = "info_article_table";
var infoIdGoble = 0;
var infoStatus = "8";
var finalStatus = "0";
var stepID = "100";
var subNode = false;
var current_page_num = 1;
var is_save_first_page = false;//操作成功后是否保存在第一页
var highSearchString = "";//高级搜索的字符串
var is_my_info = false;//只显示自己所添加的审核中的信息

var sso_method_cookie_value;

function setInfoGoble(n) {
    infoIdGoble = n;
}
function getInfoGoble(n) {
    return infoIdGoble;
}

function reloadInfoDataList() {
    initTable();
    if (is_save_first_page == true)
        current_page_num = 1;

    tp.curr_page = current_page_num;
    showTurnPage();
    showList();
    is_save_first_page = false;
}

function initTable() {
    var colsMap = new Map();
    var colsList = new List();
    //标题	管理操作	点击量	权重	发布人	发布时间
    //colsList.add(setTitleClos("info_id","ID","50px","","",""));
    //alert($(window).width());
    if (app == "zwgk" && gk_article == false)
        colsList.add(setTitleClos("gk_index", "索引码", "150px", "", "", ""));
    colsList.add(setTitleClos("title", "标题", "", "", "", ""));//英文名，显示名，宽，高，样式名，点击事件
    if (app != "zwgk")
        colsList.add(setTitleClos("istop", "置顶", "30px", "", "", ""));
    colsList.add(setTitleClos("actions", "管理操作", "90px", "", "", ""));
    colsList.add(setTitleClos("weight", "权重", "30px", "", "", ""));
    colsList.add(setTitleClos("input_user_name", "录入人", "60px", "", "", ""));
    colsList.add(setTitleClos("modify_user_name", "审核人", "60px", "", "", ""));
    colsList.add(setTitleClos("opt_time", "发布时间", "120px", "", "", ""));
    if (infoStatus == 1)
        colsList.add(setTitleClos("auto_desc", "退稿意见", "", "", "", ""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id

}

//在信息列表页中 可以直接修改权重 -- start
function setInfoWeight(weight_o, info_id) {
    var weight_n = $("#info_" + info_id).val();
    if (weight_n != weight_o) {
        //alert(weight_o+"---"+info_id+"----"+weight_n);
        jsonrpc.InfoBaseRPC.updateInfoWeight(updateInfoWeightResult, info_id, weight_n);
    }
}
function updateInfoWeightResult(result, e) {
    if (e != null) {
        return;
    }
}

//列表页信息置顶
function setInfoIsTop(info_id, value) {
    var istop = "";
    if (value == 1) {
        istop = 0;
        $("#info_" + info_id).attr("checked", "");
    } else {
        istop = 1;
        $("#info_" + info_id).attr("checked", true);
    }
    jsonrpc.InfoBaseRPC.setInfoTop(setInfoTopResult, istop, info_id);
}
function setInfoTopResult(result, e) {
    if (e != null) {
        return;
    }
    showList();
}

//在信息列表页中 可以直接修改权重 -- end

function showList() {
    var sortCol = table.sortCol;
    var sortType = table.sortType;
    if (sortCol == "" || sortCol == null) {
        sortCol = "ci.released_dtime desc,ci.id";
        sortType = "desc";
    }
    var m = new Map();
    m.put("app_id", app);
    m.put("site_id", site_id);
    m.put("start_num", tp.getStart());
    m.put("page_size", tp.pageSize);
    m.put("sort_name", sortCol);
    m.put("sort_type", sortType);
    m.put("cat_id", cid);
    m.put("user_id", LoginUserBean.user_id + "");
    m.put("highSearchString", highSearchString);
//	m.put("is_auto_up", app);
//	M.PUT("IS_HOST", APP);

    if (is_my_info) {
        m.put("input_user", LoginUserBean.user_id + "");
    }

    if (stepID != "" && stepID != "100") {
        m.put("step_id", stepID);
    }

    if (infoStatus != "none" && infoStatus != "") {
        m.remove("info_status");
        m.put("info_status", infoStatus);
    }
    if (finalStatus != "none") {
        m.put("final_status", finalStatus);
    } else {
        m.put("final_status", "0");
    }

    if (tj != "") {
        m.put("modelString", tj);
    }

    if (serarch_con != "") {
        m.put("searchString", serarch_con);
    }

    if (search_steps != "") {
        m.put("search_steps", search_steps);
        m.remove("cat_id");
    } else {
        if (subNode) {
            m.remove("cat_id");
            m.put("cat_ids", cid);
        }
    }

    if (infoStatus == 2 && (cid == 10024 || cid == 10025 || cid == 10026 || cid == 10027 || cid == 10028)) {
        var mcat_id = MemberManRPC.getMCatIDByUser(LoginUserBean.user_id, site_id);
        if (mcat_id != "" && mcat_id != 0) {
            var str = mcat_id.split(",");
            if (str.length <= 1) {
                var memberCatBean = MemberManRPC.getMemberCategoryByID(mcat_id);
                m.put("con_value", memberCatBean.mcat_name.substring(0, 2));
            }
        }
    }

    if (table.con_value.trim() != "" && table.con_value != null) {
        m.put("con_name", table.con_name);
        m.put("con_value", table.con_value);
    }
    if (app == "cms" || gk_article == true || app == "ggfw")
        beanList = InfoBaseRPC.getInfoList(m);//第一个参数为站点ID，暂时默认为空
    else
        beanList = GKInfoRPC.getGKInfoList(m);//第一个参数为站点ID，暂时默认为空
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    //在信息列表页中 可以直接修改权重 -- start
    table.getCol("weight").each(function (i) {
        //$(this).css({"text-align":"left"});
        if (i > 0) {
            var weight_o = beanList.get(i - 1).weight;
            var info_id_o = beanList.get(i - 1).info_id;
            $(this).html('<input type="text" name="weight" id="info_' + info_id_o + '" value="' + weight_o + '" style="width:20px" onblur="setInfoWeight(' + weight_o + ',' + info_id_o + ')"/>');
        }
    });
    //在信息列表页中 可以直接修改权重 -- end

    table.getCol("istop").each(function (i) {
        if (i > 0) {
            var istop = beanList.get(i - 1).istop;
            var strTop = "<ul>";
            if (istop == 1) {
                strTop += "<li><input type='checkbox' name='infoistopcheckbox' checked='true' onclick='setInfoIsTop(" + beanList.get(i - 1).info_id + ",1)'></li>";
            } else {
                strTop += "<li><input type='checkbox' name='infoistopcheckbox' onclick='setInfoIsTop(" + beanList.get(i - 1).info_id + ",0)'></li>";
            }
            $(this).html(strTop + "</ul>");
        }
    });

    table.getCol("title").each(function (i) {
        $(this).css({"text-align": "left"});
        if (i > 0) {
            var title_flag = "";
            var title_end_str = "";
            if (beanList.get(i - 1).is_host == 1) {
                title_flag = "<span class='f_red'>[引]</span>";
            }
            if (beanList.get(i - 1).is_host == 2) {
                title_flag = "<span class='f_red'>[链]</span>";
            }

            if (beanList.get(i - 1).pre_title != "") {
                title_flag += "<span >[" + beanList.get(i - 1).pre_title + "]</span>";
            }

            if (beanList.get(i - 1).is_pic == 1)
                title_end_str = "(图)";

            //var model_ename = ModelRPC.getModelEName(beanList.get(i-1).model_id);
            $(this).addClass(model_map.get(beanList.get(i - 1).model_id).model_icon);
            $(this).css("padding-left", "20px");
            $(this).html('<a href="javascript:openViewPage(' + beanList.get(i - 1).content_url + ')">' + title_flag + beanList.get(i - 1).title + '</a>' + title_end_str);
        }
    });

    /*
     table.getCol("input_user").each(function(i){
     if(i>0)
     {
     if(beanList.get(i-1).input_user != 0)
     $(this).text(UserManRPC.getUserRealName(beanList.get(i-1).input_user));
     else
     $(this).text("");
     }
     });

     table.getCol("modify_user").each(function(i){
     if(i>0)
     {
     if(beanList.get(i-1).input_user != 0)
     $(this).text(UserManRPC.getUserRealName(beanList.get(i-1).modify_user));
     else
     $(this).text("");
     }
     });
     */

    table.getCol("opt_time").each(function (i) {
        var nm = parseInt(infoStatus);
        if (i > 0) {
            if (nm == 8) {
                var t = beanList.get(i - 1).released_dtime;
                if (t != "")
                    $(this).text(t.substring(0, t.length - 3));
                else
                    $(this).text("");
            } else {
                var t = beanList.get(i - 1).opt_dtime;
                if (t != "")
                    $(this).text(t.substring(0, t.length - 3));
                else
                    $(this).text("");
            }
        } else {
            var str = "";
            switch (nm) {
                case 0:
                    str = "更新时间";
                    break;
                case 1:
                    str = "送审时间";
                    break;
                case 2:
                    str = "送审时间";
                    break;
                case 3:
                    str = "撤销时间";
                    break;
                case 4:
                    str = "审核时间";
                    break;
                case 8:
                    str = "发布时间";
                    break;
                case 9:
                    str = "删除时间";
                    break;
                default:
                    str = "发布时间";
                    break;
            }
            $(this).text(str);
        }

    });

    table.getCol("actions").each(function (i) {
        if (i > 0) {
            $(this).css({"text-align": "center"});
            var nm = parseInt(infoStatus);
            var str = "<ul class=\"optUL\">";
            sso_method_cookie_value = request.getCookie(sso_method_cookie_name);
            if (sso_method_cookie_value != null && sso_method_cookie_value != "") {
                //添加信息
                if ("addInfo" == sso_method_cookie_value) {
                    str += "<li class='ico_view'><a title='预览' href='javascript:doView(" + (i - 1) + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                }
                //修改信息
                if ("modifyInfo" == sso_method_cookie_value) {
                    str += "<li class='ico_view'><a title='预览' href='javascript:doView(" + (i - 1) + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    str += "<li id='315' class='ico_edit'><a title='修改' href='javascript:openUpdatePage(" + beanList.get(i - 1).info_id + "," + beanList.get(i - 1).model_id + "," + beanList.get(i - 1).is_host + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                }
                //删除信息
                if ("delInfo" == sso_method_cookie_value) {
                    str += "<li id='332' class='ico_delete' ><a title='删除' href='javascript:doDelete(" + (i - 1) + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                }
                //撤销信息
                if ("unPublishInfo" == sso_method_cookie_value) {
                    str += "<li id='307' class='ico_cancel'><a  title='撤销' href='javascript:doCancel(" + (i - 1) + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                }
                //已撤销信息
                if ("unPublishedInfo" == sso_method_cookie_value) {
                    str += "<li id='302' class='ico_publish'><a  title='发布' href='javascript:doPublish("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    str += "<li id='315' class='ico_edit' ><a  title='修改' href='javascript:openUpdatePage("+beanList.get(i-1).info_id+","+beanList.get(i-1).model_id+","+beanList.get(i-1).is_host+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    str += "<li id='332' class='ico_delete' ><a title='删除' href='javascript:doDelete("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                }
                //审核信息
                if ("checkInfo" == sso_method_cookie_value) {
                    str += "<li id='303' class='ico_pass'><a title='通过' href='javascript:doPass(" + (i - 1) + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li><li id='303' class='ico_nopass'><a  title='退稿' href='javascript:noPassDesc(" + beanList.get(i - 1).info_id + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                }
                //退稿信息
                if ("noPassInfo" == sso_method_cookie_value) {
                    str += "<li id='315' class='ico_edit'><a title='修改' href='javascript:openUpdatePage(" + beanList.get(i - 1).info_id + "," + beanList.get(i - 1).model_id + "," + beanList.get(i - 1).is_host + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                }
                //草稿信息
                if ("drafgInfo" == sso_method_cookie_value) {
                    str += "<li id='315' class='ico_edit'><a title='修改' href='javascript:openUpdatePage(" + beanList.get(i - 1).info_id + "," + beanList.get(i - 1).model_id + "," + beanList.get(i - 1).is_host + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                }
                //回收站信息
                if ("rubbyInfo" == sso_method_cookie_value) {
                    str += "<li id='308' class='ico_reduce'><a title='还原' href='javascript:doReduce(" + (i - 1) + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    str += "<li id='315' class='ico_edit' ><a title='修改' href='javascript:openUpdatePage(" + beanList.get(i - 1).info_id + "," + beanList.get(i - 1).model_id + "," + beanList.get(i - 1).is_host + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    str += "<li id='309' class='ico_Clear' ><a title='彻底删除' href='javascript:doClearOneInfo(" + (i - 1) + ")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                }
            }
            /*
             switch(nm){
             case 0:
             str += "<li class='ico_submit'><a title='送审' href='javascript:doSubmitPage("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='315' class='ico_edit'><a title='修改' href='javascript:openUpdatePage("+beanList.get(i-1).info_id+","+beanList.get(i-1).model_id+","+beanList.get(i-1).is_host+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='332' class='ico_delete' ><a title='删除' href='javascript:doDelete("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";break;
             case 1:
             str += "<li class='ico_submit'><a title='送审'  href='javascript:doSubmitPage("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='315' class='ico_edit' ><a  title='修改' href='javascript:openUpdatePage("+beanList.get(i-1).info_id+","+beanList.get(i-1).model_id+","+beanList.get(i-1).is_host+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='332' class='ico_delete' ><a title='删除' href='javascript:doDelete("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li> ";break;
             case 2:
             if(!is_my_info)
             {
             str += "<li id='303' class='ico_pass'><a title='通过' href='javascript:doPass("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li><li id='303' class='ico_nopass'><a  title='退稿' href='javascript:noPassDesc("+beanList.get(i-1).info_id+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             }
             str += "<li id='315' class='ico_edit'><a title='修改' href='javascript:openUpdatePage("+beanList.get(i-1).info_id+","+beanList.get(i-1).model_id+","+beanList.get(i-1).is_host+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='332' class='ico_delete' ><a title='删除' href='javascript:doDelete("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";break;
             case 3:
             str += "<li id='302' class='ico_publish'><a  title='发布' href='javascript:doPublish("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='315' class='ico_edit' ><a  title='修改' href='javascript:openUpdatePage("+beanList.get(i-1).info_id+","+beanList.get(i-1).model_id+","+beanList.get(i-1).is_host+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='332' class='ico_delete' ><a title='删除' href='javascript:doDelete("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";break;
             case 4:
             str += "<li id='302' class='ico_publish'><a title='发布' href='javascript:doPublish("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='315' class='ico_edit' ><a  title='修改'href='javascript:openUpdatePage("+beanList.get(i-1).info_id+","+beanList.get(i-1).model_id+","+beanList.get(i-1).is_host+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='332' class='ico_delete' ><a title='删除' href='javascript:doDelete("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";break;
             case 8:
             str += "<li class='ico_view'><a title='预览' href='javascript:doView("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='315' class='ico_edit' ><a  title='修改' href='javascript:openUpdatePage("+beanList.get(i-1).info_id+","+beanList.get(i-1).model_id+","+beanList.get(i-1).is_host+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='307' class='ico_cancel'><a  title='撤销' href='javascript:doCancel("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li><li id='332' class='ico_delete' ><a title='删除' href='javascript:doDelete("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";break;
             case 9:
             str += "<li id='308' class='ico_reduce'><a title='还原' href='javascript:doReduce("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='315' class='ico_edit' ><a  title='修改' href='javascript:openUpdatePage("+beanList.get(i-1).info_id+","+beanList.get(i-1).model_id+","+beanList.get(i-1).is_host+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='309' class='ico_Clear' ><a  title='彻底删除' href='javascript:doClearOneInfo("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";break;
             default:
             str += "<li id='308' class='ico_reduce'><a title='还原' href='javascript:doReduce("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='315' class='ico_edit' ><a title='修改' href='javascript:openUpdatePage("+beanList.get(i-1).info_id+","+beanList.get(i-1).model_id+","+beanList.get(i-1).is_host+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
             str += "<li id='309' class='ico_Clear' ><a title='彻底删除' href='javascript:doClearOneInfo("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";break;
             }
             */
            $(this).html(str + "</ul>");
        }
    });
    current_page_num = tp.curr_page;
    if (gk_article == false && app != "ggfw")//特殊的公开栏目不使用权限判断
        setUserOperateLI(table.table_name);

    Init_InfoTable(table.table_name);
}

function setUserOperateLI(table_name) {
    $("#" + table_name + " li[id]").hide();
    $("#" + table_name + " li[id]").each(function () {
        var o_id = "," + $(this).attr("id") + ",";
        if (opt_ids.indexOf(o_id) > -1)
            $(this).show();
    });
}

function showTurnPage() {
    var m = new Map();
    m.put("app_id", app);
    m.put("site_id", site_id);
    m.put("cat_id", cid);
    m.put("highSearchString", highSearchString);
    m.put("user_id", LoginUserBean.user_id + "");
//	m.put("is_auto_up", app);
//	m.put("is_host", app);

    if (is_my_info) {
        m.put("input_user", LoginUserBean.user_id + "");
    }
    if (stepID != "" && stepID != "100") {
        m.put("step_id", stepID);
    }

    if (infoStatus != "none" && infoStatus != "") {
        m.remove("info_status");
        m.put("info_status", infoStatus);
    }
    if (finalStatus != "none") {
        m.put("final_status", finalStatus);
    } else {
        m.put("final_status", "0");
    }

    if (tj != "") {
        m.put("modelString", tj);
    }

    if (serarch_con != "") {
        m.put("searchString", serarch_con);
    }

    if (search_steps != "") {
        m.put("search_steps", search_steps);
        m.put("cat_id", cid);
    } else {
        if (subNode) {
            m.put("cat_id", cid);
            m.put("cat_ids", cid);
        }
    }

    if (table.con_value.trim() != "" && table.con_value != null) {
        m.put("con_name", table.con_name);
        m.put("con_value", table.con_value);
    }
    if (app == "cms" || gk_article == true || app == "ggfw") {
        tp.total = InfoBaseRPC.getInfoCount(m);
    } else if (app == "zwgk") {
        tp.total = GKInfoRPC.getGKInfoCount(m);
    }


    tp.show("turn", "");
    tp.onPageChange = showList;
}

function initTabAndStatus() {
    $(".fromTabs > li").each(function () {
        $(this).hover(
            function () {
                $(this).addClass("list_tab_over");
            },
            function () {
                $(this).removeClass("list_tab_over");
            }
        );

        $(this).click(
            function () {
                $(".fromTabs > li").removeClass("list_tab_cur");
                $(this).addClass("list_tab_cur");
                $(".infoListTable").addClass("hidden");

                if ($(this).attr("num") != "" && $(this).attr("num") != null) {
                    $("#listTable_" + $(this).attr("num")).removeClass("hidden");
                    changeStatus(parseInt($(this).attr("num")));
                }
                else {
                    $("#listTable_" + $(this).index()).removeClass("hidden");
                    changeStatus($(this).index());
                }
            }
        );
    });
}

function changeStatus(num) {
    current_page_num = 1;
    snum = num;
    infoStatus = "none";
    finalStatus = "none";
    highSearchString = "";
    table.sortCol = "";
    table.sortType = "";
    $("#orderByFields option").eq(0).attr("selected", true);
    clickLabelHandl(num);
    reloadInfoDataList();
}

//点击标签时重置属性
function clickLabelHandl(num) {
    is_my_info = false;
    switch (num) {
        case 0:
            infoStatus = "8";
            stepID = "100";
            finalStatus = "0";
            search_steps = "";
            break;
        /*
         case 1:
         infoStatus = "4";
         stepID = "100";
         finalStatus = "0";
         search_steps = "";
         break;
         */
        case 1:
            infoStatus = "3";
            stepID = "100";
            finalStatus = "0";
            search_steps = "";
            break;
        case 2:
            infoStatus = "2";
            stepID = "0";
            getTheLeafNodes();
            finalStatus = "0";
            break;
        case 3:
            infoStatus = "2";
            stepID = "0";
            getTheLeafNodes();
            finalStatus = "0";
            is_my_info = true;
            break;
        case 4:
            infoStatus = "1";
            stepID = "0";
            finalStatus = "0";
            search_steps = "";
            break;
        case 5:
            infoStatus = "0";
            stepID = "";
            finalStatus = "0";
            search_steps = "";
            break;
        case 6:
            infoStatus = "";
            finalStatus = "-1";
            search_steps = "";
            stepID = "";
            break;
    }
}

function showModels() {
    var modelBeanList = CategoryRPC.getCateReleModelBeanList(cid, site_id);
    modelBeanList = List.toJSList(modelBeanList);

    $("#pageGoNum").append("<option value=\"-1\" selected=\"selected\"  >全部</option> ");
    var is_first = false;
    for (var i = 0; i < modelBeanList.size(); i++) {
        if (modelBeanList.get(i).app_id == app || app == "ggfw") {
            $("#pageGoNum").append("<option value=\"" + modelBeanList.get(i).model_id + "\">" + modelBeanList.get(i).model_name + "</option> ");
            if (subNode == false) {
                $("#addLabList").append("<li class=\"" + modelBeanList.get(i).model_icon + "\" value=\"" + modelBeanList.get(i).model_id + "\" >" + modelBeanList.get(i).model_name + "</li> ");
                if (!is_first) {
                    $(".x_add").attr("value", modelBeanList.get(i).model_id);
                    is_first = true;
                }
            }
        }
    }
    //addLabList
}

//得到有权限的所有叶子节点
function getTreeIdsOfLeavies() {
    var temp = "";
    $("#leftMenuTree li").each(function (i) {
        var t = $(this).find("span.tree-file").parent().attr("node-id");
        if (t != null && t != "" && temp.indexOf(t) == -1) {
            temp += "," + t;
        }
    });
    if (temp.indexOf(",") != -1) {
        temp = temp.substring(1);
    }
    return temp;
}

function isSubNode(node_id) {
    //判断节点是否是叶节点
    if ($('#leftMenuTree .tree-node-selected').find('.tree-folder').length == 0) {
        subNode = false;
    } else {
        subNode = true;//叶节点
    }
}

function getTheLeafNodes() {
    var catIds = getCurrentNodeChileLeftNodeIDS();
    if (catIds == null) {
        search_steps = "";
    } else {
        search_steps = catIds;
    }
}

function getAddPagebyModel(model_id) {
    var add_page = jsonrpc.ModelRPC.getModelAddPage(model_id);
    if (add_page == "" || add_page == null)
        add_page = "m_article.jsp";
    return add_page;
}

//打开添加窗口
function openAddInfoPage(model_id) {
    if (model_id == 0)
        model_id = 11;
    is_save_first_page = true;
    if (app == "ggfw") {
        //window.location.href = "/sys/cms/info/article/m_link.jsp?cid=" + cid + "&site_id=" + site_id + "&app_id=" + app + "&model=12&topnum=" + window.parent.curTabIndex;
        parent.addTab(true, "/sys/cms/info/article/m_link.jsp?cid=" + cid + "&site_id=" + site_id + "&app_id=" + app + "&model=12&topnum=" + parent.curTabIndex, "添加信息");
    }
    else {
        if (gk_article == true) {
            //window.location.href = "/sys/cms/info/article/m_gk_gkzn.jsp?cid=" + cid + "&site_id=" + site_id + "&app_id=" + app + "&model=" + model_id + "&topnum=" + window.parent.curTabIndex;
            parent.addTab(true, "/sys/cms/info/article/m_gk_gkzn.jsp?cid=" + cid + "&site_id=" + site_id + "&app_id=" + app + "&model=" + model_id + "&topnum=" + parent.curTabIndex, "添加信息");
        }
        else
            var model = getAddPagebyModel(model_id);
        //window.location.href= "/sys/cms/info/article/" + getAddPagebyModel(model_id) + "?cid=" + cid + "&site_id=" + site_id + "&app_id=" + app + "&model=" + model_id + "&topnum=" + window.parent.curTabIndex;
        parent.addTab(true, "/sys/cms/info/article/" + getAddPagebyModel(model_id) + "?cid=" + cid + "&site_id=" + site_id + "&app_id=" + app + "&model=" + model_id + "&topnum=" + parent.curTabIndex, "添加信息");
    }
}

//打开修改窗口
function openUpdatePage(Infoid, model_id, is_host) {
    if (is_host == 1) {
        //引用信息只修改信息主表内容
        //window.location.href = "/sys/cms/info/article/update_info.jsp?cid=" + cid + "&info_id=" + Infoid + "&site_id=" + site_id + "&app_id=" + app + "&model=" + model_id + "&topnum=" + window.parent.curTabIndex;
        parent.addTab(true, "/sys/cms/info/article/update_info.jsp?cid=" + cid + "&info_id=" + Infoid + "&site_id=" + site_id + "&app_id=" + app + "&model=" + model_id + "&topnum=" + parent.curTabIndex, "修改信息");
    }
    else {
        if (gk_article == true) {
            //window.location.href = "/sys/cms/info/article/m_gk_gkzn.jsp?cid=" + cid + "&info_id=" + Infoid + "&site_id=" + site_id + "&app_id=" + app + "&model=" + model_id + "&topnum=" + window.parent.curTabIndex;
            parent.addTab(true, "/sys/cms/info/article/m_gk_gkzn.jsp?cid=" + cid + "&info_id=" + Infoid + "&site_id=" + site_id + "&app_id=" + app + "&model=" + model_id + "&topnum=" + parent.curTabIndex, "修改信息");
        } else
            var model = getAddPagebyModel(model_id);
        //window.location.href = "/sys/cms/info/article/" + model + "?cid=" + cid + "&info_id=" + Infoid + "&site_id=" + site_id + "&app_id=" + app + "&model=" + model_id + "&topnum=" + window.parent.curTabIndex;
        parent.addTab(true, "/sys/cms/info/article/" + getAddPagebyModel(model_id) + "?cid=" + cid + "&info_id=" + Infoid + "&site_id=" + site_id + "&app_id=" + app + "&model=" + model_id + "&topnum=" + parent.curTabIndex, "修改信息");
    }
}

function openViewPage(i_id) {
    //window.location.href = "/sys/cms/info/article/infoView.jsp?info_id="+i_id+"&site_id="+site_id+"&snum="+snum;
    //parent.addTab(true, "/sys/cms/info/article/infoView.jsp?info_id=" + i_id + "&site_id=" + site_id + "&topnum=" + parent.curTabIndex, "查看信息");
    if(i_id.indexOf("http://")>=0 || i_id.indexOf("https://")>=0){
        parent.addTab(true, i_id, "查看信息");
    }else{
        parent.addTab(true, "http://www.cdzfgjj.gov.cn" + i_id, "查看信息");
    }

}

//生成静态页面
function createStaticContentHtml() {
    var selectList = table.getSelecteBeans();
    if (InfoBaseRPC.createContentHTML(selectList)) {
        parent.msgAlert("静态页生成成功");
    } else {
        parent.msgWargin("静态页生成失败,请重新生成");
    }
}

//信息发布
function publishInfo() {
    var selectList = table.getSelecteBeans();
    if (InfoBaseRPC.updateInfoStatus(selectList, "8")) {
        parent.msgAlert("信息发布成功");
        reloadInfoDataList();
    } else {
        parent.msgWargin("信息发布失败");
    }
}

//信息撤销
function cancleInfo() {
    var selectList = table.getSelecteBeans();
    if (InfoBaseRPC.updateInfoStatus(selectList, "3")) {
        parent.msgAlert("信息撤销成功");
        reloadInfoDataList();
    } else {
        parent.msgWargin("信息撤销失败");
    }
}

//信息归档
function backInfo() {
    var selectIDS = table.getSelecteCheckboxValue("info_id");
    if (InfoBaseRPC.backInfo(selectIDS)) {
        parent.msgAlert("信息归档成功");
        reloadInfoDataList();
    } else {
        parent.msgWargin("信息归档失败");
    }
}
/*
 //信息还原
 function goBackInfo(){
 var selectList = table.getSelecteBeans();
 if(InfoBaseRPC.goBackInfo(selectList)){
 msgAlert("信息归档成功");
 reloadInfoDataList();
 }else{
 msgWargin("信息归档失败");
 }
 }*/

//信息删除，逻辑删
function deleteInfoData() {
    var selectList = table.getSelecteBeans();

    if (InfoBaseRPC.deleteInfo(selectList)) {
        parent.msgAlert("信息" + WCMLang.Delete_success);
        reloadInfoDataList();
    } else {
        parent.msgWargin("信息" + WCMLang.Delete_fail);
    }
}

//以模型为条件过滤
function changeFactor() {
    var cf = $("#pageGoNum").val();
    if (cf == "-1") {
        tj = "";
    } else
        tj = " ci.model_id=" + cf;
    //alert(tj);
    reloadInfoDataList();
}

//以日期为条件过滤
function changeFactor2() {
    var cf = $("#searchTimes").val();
    if (cf == "0b") {
        serarch_con = "";
    } else {
        serarch_con = " searchType_" + cf;
    }
    //
    reloadInfoDataList();
}

//搜索
function searchInfo() {
    var keywords = $("#searchkey").val();
    if (keywords.trim() == "" || keywords == null) {
        parent.msgAlert(WCMLang.Search_empty);
        return;
    }
    var orderFeilds = $("#orderByFields").val();
    table.con_name = "ci.title";
    table.con_value = keywords;

    var sortC = "";
    var sortT = "";

    if (orderFeilds == "1") {
        sortC = "ci.released_dtime";
        sortT = "desc";
    } else if (orderFeilds == "2") {
        sortC = "ci.released_dtime";
        sortT = "asc";
    } else if (orderFeilds == "3") {
        sortC = "ci.weight";
        sortT = "desc";
    } else if (orderFeilds == "4") {
        sortC = "ci.weight";
        sortT = "asc";
    }

    table.sortCol = sortC;
    table.sortType = sortT;

    reloadInfoDataList();
}

//时间排序
function changeTimeSort(val) {
    changeTimeSortHandl(val);
    reloadInfoDataList();
}

function changeTimeSortHandl(val) {
    table.sortCol = "";
    table.sortType = "";

    switch (val) {
        case "1":
            table.sortCol = "ci.id desc,ci.released_dtime";
            table.sortType = "desc";
            break;
        case "2":
            table.sortCol = "ci.released_dtime";
            table.sortType = "asc";
            break;
        case "3":
            table.sortCol = "ci.weight desc,ci.info_id";
            table.sortType = "desc";
            break;
        case "4":
            table.sortCol = "ci.weight asc,ci.info_id";
            table.sortType = "desc";
            break;
    }
}


//推送
function openWindowForPush() {
    parent.OpenModalWindow("信息推送", "/sys/cms/info/article/infoTS.jsp?site_id=" + site_id + "&app_id=" + app, 400, 480);
}

//转移
function openWindowForMov() {
    parent.OpenModalWindow("信息转移", "/sys/cms/info/article/infoMove.jsp?site_id=" + site_id + "&app_id=" + app, 400, 460);
}

/*function moveInfoHandl(cat_id)
 {
 return InfoBaseRPC.MoveInfo(table.getSelecteBeans(),cat_id,app,site_id);
 }*/

function moveInfoHandl(cat_id) {
    var new_list = new List();
    var beans = new List();
    beans = table.getSelecteBeans();
    if (beans != null) {
        for (var i = beans.size() - 1; i >= 0; i--) {
            new_list.add(beans.get(i));
        }
    }
    return InfoBaseRPC.MoveInfo(new_list, cat_id, app, site_id);
}

//送审
function toPass() {

    //打开选择审核步骤页面
    parent.OpenModalWindow("选择审核步骤", "/sys/cms/info/article/chooseAudit.jsp?cat_id=" + cid + "&site_id=" + site_id + "&app_id=" + app, 400, 200);
    /*
     var selectList = table.getSelecteBeans();
     if(InfoBaseRPC.passInfoStatus(selectList,LoginUserBean.user_id)){
     msgAlert("信息送审成功");
     }else{
     msgWargin("信息送审失败");
     }
     reloadInfoDataList();
     */
}

//通过
function onPass() {

    //打开选择审核步骤页面
    parent.OpenModalWindow("选择审核步骤", "/sys/cms/info/article/chooseAudit.jsp?cat_id=" + cid + "&site_id=" + site_id + "&app_id=" + app, 400, 200);
    /*
     var selectList = table.getSelecteBeans();
     if(InfoBaseRPC.passInfoStatus(selectList,LoginUserBean.user_id)){
     msgAlert("审核操作成功");
     }else{
     msgWargin("审核操作失败");
     }
     reloadInfoDataList();
     */
}

//不通过
function noPass(desc) {
    var selectIDS = "";
    if (temp_info_id != "" && temp_info_id != null)
        selectIDS = temp_info_id;
    else
        selectIDS = table.getSelecteCheckboxValue("info_id");

    if (InfoBaseRPC.noPassInfoStatus(selectIDS, desc)) {
        parent.msgAlert("退回操作成功");
    } else {
        parent.msgWargin("退回操作失败");
    }
    temp_info_id = null;
    reloadInfoDataList();
}

var temp_info_id;
function noPassDesc(id) {
    if (id != null && id != "")
        temp_info_id = id;
    parent.OpenModalWindow("退稿意见", "/sys/cms/info/article/noPassDesc.jsp", 520, 235);
}

//单条信息通过
function doPass(num) {
    //打开选择审核步骤页面
    parent.OpenModalWindow("选择审核步骤", "/sys/cms/info/article/chooseAudit.jsp?cat_id=" + cid + "&site_id=" + site_id + "&app_id=" + app + "&num=" + num, 400, 200);
    /*
     var list = new List();
     list.add(beanList.get(num));
     if(InfoBaseRPC.passInfoStatus(list,LoginUserBean.user_id)){
     msgAlert("审核操作成功");
     }else{
     msgWargin("审核操作失败");
     }
     reloadInfoDataList();
     */
}

//单条信息不通过
function doNoPass(id) {

    if (InfoBaseRPC.noPassInfoStatus(id)) {
        parent.msgAlert("退回操作成功");
    } else {
        parent.msgWargin("退回操作失败");
    }
    reloadInfoDataList();
}

//还原
function rebackInfo() {
    var selectList = table.getSelecteBeans();
    if (InfoBaseRPC.goBackInfo(selectList)) {
        parent.msgAlert("还原操作成功");
        reloadInfoDataList();
    } else {
        parent.msgWargin("还原操作失败");
    }
}

//彻底删除
function realDelInfo() {
    var selectList = table.getSelecteBeans();

    if (InfoBaseRPC.realDeleteInfo(selectList)) {
        parent.msgAlert("信息" + WCMLang.Delete_success);
        reloadInfoDataList();
    } else {
        parent.msgWargin("信息" + WCMLang.Delete_fail);
    }
}

//查找选中节点下的所有叶子节点
function findNodesById(id) {
    var ids = "";
    $("div[node-id='" + id + "']").parent().find("li").each(function () {
        if ($(this).find("div span.tree-file") != null && $(this).find("div span.tree-file") != "") {
            var id = $(this).find("div span.tree-file").parent().attr("node-id");
            if (ids.indexOf(id) == -1) {
                ids += "," + id;
            }
        }
    });
    if (ids == "") {
        return id;
    }
    return ids;
}

//清空回收站
function clearAllInfo() {
    var cIds = getCurrentNodeChileLeftNodeIDS();
    cIds = cIds.substring(1);

    var temp_site_id = site_id;
    if (cid == 12) {
        temp_site_id = "GK";//此步是用于信息公开的公开指引
        cIds = 12;
    }

    if (InfoBaseRPC.clearTrach(cIds, app, temp_site_id)) {
        parent.msgAlert("回收站清空成功");
        reloadInfoDataList();
    } else {
        parent.msgWargin("回收站清空失败");
    }
}

//单条信息预览
function doView(num) {
    var url = beanList.get(num).content_url;

    if (url.indexOf("http://") > -1) {
        window.open(url);
    }
    else {
        //window.open(SiteRPC.getDefaultSiteDomainBySiteID(getRealSiteIDByApp(beanList.get(num).app_id,beanList.get(num).site_id))+url);
        window.open("http://www.cdzfgjj.gov.cn" + url);
    }
}


//根据应用ID判断使用哪个站点ID
function getRealSiteIDByApp(t_app_id, t_site_id) {
    if (t_app_id == "cms")
        return t_site_id;
    else
        return SiteRPC.getSiteIDByAppID(t_app_id);
}

//单条信息提交送审
function doSubmitPage(num) {
    //打开选择审核步骤页面
    parent.OpenModalWindow("选择审核步骤", "/sys/cms/info/article/chooseAudit.jsp?cat_id=" + cid + "&site_id=" + site_id + "&app_id=" + app + "&num=" + num, 400, 200);
    /*
     var list = new List();
     list.add(beanList.get(num));

     if(InfoBaseRPC.passInfoStatus(list,LoginUserBean.user_id)){
     msgAlert("送审操作成功");
     reloadInfoDataList();
     }else{
     msgWargin("送审操作失败");
     }
     */
}

//单条信息发布
function doPublish(num) {
    var list = new List();
    list.add(beanList.get(num));
    if (InfoBaseRPC.updateInfoStatus(list, "8")) {
        parent.msgAlert("信息发布成功");
        reloadInfoDataList();
    } else {
        parent.msgWargin("信息发布失败");
    }
}

//单条信息撤销
function doCancel(num) {
    var list = new List();
    list.add(beanList.get(num));
    if (InfoBaseRPC.updateInfoStatus(list, "3")) {
        parent.msgAlert("信息撤销成功");
        reloadInfoDataList();
    } else {
        parent.msgWargin("信息撤销失败");
    }
}

//单条信息彻底删除
function doClearOneInfo(num) {
    var list = new List();
    list.add(beanList.get(num));
    if (InfoBaseRPC.realDeleteInfo(list)) {
        parent.msgAlert("信息" + WCMLang.Delete_success);
        reloadInfoDataList();
    } else {
        parent.msgWargin("信息" + WCMLang.Delete_fail);
    }
}

//单条信息还原
function doReduce(num) {
    var list = new List();
    list.add(beanList.get(num));
    if (InfoBaseRPC.goBackInfo(list)) {
        parent.msgAlert("还原操作成功");
        reloadInfoDataList();
    } else {
        parent.msgWargin("还原操作失败");
    }
}

//单条信息逻辑删除
function doDelete(num) {
    parent.msgConfirm(WCMLang.Delete_confirm, "doDeleteHandl(" + num + ")");
}

function doDeleteHandl(num) {
    var list = new List();
    list.add(beanList.get(num));
    if (InfoBaseRPC.deleteInfo(list)) {
        parent.msgAlert("信息" + WCMLang.Delete_success);
        reloadInfoDataList();
    } else {
        parent.msgWargin("信息" + WCMLang.Delete_fail);
    }
}

//获取
function getInfoFromOtherCat() {
    //打开页面
    is_save_first_page = true;
    parent.OpenModalWindow("信息获取", "/sys/cms/info/article/infoGet.jsp?cat_id=" + cid + "&site_id=" + site_id + "&app_id=" + app, 800, 530);
}

//打开高级搜索页面
function openHighSearchPage() {
    if (app == "cms")
        parent.OpenModalWindow("高级搜索", "/sys/cms/info/article/infoSearch.jsp", 350, 360);
    if (app == "zwgk")
        parent.OpenModalWindow("高级搜索", "/sys/cms/info/article/gkInfoSearch.jsp", 590, 510);
}

//高级搜索处理
function highSearchHandl(search_cons, lab_num, order_type_num) {
    lab_num = parseInt(lab_num);
    highSearchString = search_cons;//给搜索字符串付值
    changeTimeSortHandl(order_type_num);//得到排序方式

    current_page_num = 1;
    snum = lab_num;
    infoStatus = "none";
    finalStatus = "none";

    $("#orderByFields option").eq(0).attr("selected", true);

    $(".fromTabs > li").removeClass("list_tab_cur");
    $(".fromTabs > li").eq(lab_num).addClass("list_tab_cur");
    $(".infoListTable").addClass("hidden");
    $("#listTable_" + lab_num).removeClass("hidden");

    clickLabelHandl(lab_num);
    reloadInfoDataList();
}

//根据获取规则得到指定的信息
function getAllocateInfo() {
    is_save_first_page = true;
    parent.OpenModalWindow("信息获取", "/sys/cms/info/article/getAllocateInfo.jsp?cat_id=" + cid + "&site_id=" + site_id + "&app_id=" + app, 800, 530);
}
