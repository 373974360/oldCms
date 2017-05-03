var cms_json_data;
var current_site_id;
var current_app_id;
var init_top_menu_id; //用于得到上次用户所打开的应用功能节点ID
var init_site_menu_id;//用于得到上次用户所打开的站点节点ID
var init_site_id;
var app_cookie_name = "";//系统应用菜单cookie健值名称 和登录用户ID关联
var site_cookie_name = "";//站点 菜单cookie健值名称
//首页框架所用到的功能js包括菜单，弹出窗口等js
var child_jData = null;
var myPlatform_jsonData;//我的平台树字符串
var defalut_menu_li;//默认的第一级菜单节点
//初始化顶级功能菜单
var sso_method_cookie_name = "sso_method_cookie_name";
function initTopMenu() {
    $.each(jsonData, function (i, item) {
        initTopNextLeve(jsonData[i].children);
    });
}

function initTopNextLeve(nextJsonData) {
    $.each(nextJsonData, function (i, item) {
        app_cookie_name = LoginUserBean.user_id + "_wcm_current_app_id";
        site_cookie_name = LoginUserBean.user_id + "_wcm_current_site_id";

        var cookie_app_id = request.getCookie(app_cookie_name);
        init_site_id = request.getCookie(site_cookie_name);

        var li = creatTopMenuLi(item);
        $(li).attr("level", "0");
        if (cookie_app_id != "" && cookie_app_id != null) {
            if (cookie_app_id == nextJsonData[i].attributes.app_id)
                init_top_menu_id = nextJsonData[i].id;
        } else {
            if (init_top_menu_id == "" || init_top_menu_id == null) {
                init_top_menu_id = nextJsonData[i].id;
            }
        }

        //防止事件冒泡，只在最外层bind事件即可
        $(li).bind('click', function (event) {
            reloadBefore();
            current_app_id = $(this).data("attributes").app_id;
            if (current_app_id != "org") {
                current_site_id = "";
            }

            request.setCookie(app_cookie_name, current_app_id);
            request.setCookie(site_cookie_name, "");
            if ($(this).data("attributes").handls != "" && $(this).data("attributes").handls != null) {
                cms_json_data = $(this).data("children");
                eval($(this).data("attributes").handls);

            } else {
                $("#system_name").html($(this).text());

                for (var i = 0; i < nextJsonData.length; i++) {
                    if (nextJsonData[i].id == $(this).attr("id")) {
                        child_jData = nextJsonData[i].children;
                        iniMenu();
                        //iniMenuOnMouseOver();
                    }
                }
            }//点击关闭一级导航
            //$("#top_menu > li").removeClass("iehover");
            reloadAfter();
        });
        $("#top_menu").append(li);
    });
}

//触发用户最后选择的应用节点
function initLastSelectMenu() {
    //if (init_top_menu_id != "")
        $("#top_menu li[id='" + 6 + "']").click();
    $("#main_nav > ul li[id='350']").click();

}

//初始化一、二级横向菜单
var isChangeTreeStatus = false;//判断是否改变了左侧的树
function iniMenu() {
    $("#menu").empty();
    try {
        if (myPlatform_jsonData != null)
            iniMenuHandl(myPlatform_jsonData, false);
        iniMenuHandl(child_jData, true);
        defalut_menu_li.click();
    } catch (e) {
    }
    ;
}

function iniMenuHandl(jData, if_flag) {
    var is_zwgk = false;
    $.each(jData, function (i, item) {
        var li = creatMenuLi(item);
        //id为8的是信息公开的节点ID，这里需要特殊处理一下
        if ($(this).attr("id") == 8) {
            $(this).addClass("hidden");
            if (item.children != null) {
                cms_json_data = item.children;
                createZWGKMenu();
                is_zwgk = true;
            }
        } else {
            $(li).attr("level", "0");
            //防止事件冒泡，只在最外层bind事件即可
            $(li).bind('click', function (event) {
                reloadBefore();
                current_app_id = $(this).data("attributes").app_id;
                var selectLi = null;
                if ($(event.target).attr("level") == "1") {
                    selectLi = $(event.target).parent().parent();
                    treeNodeSelected($(event.target).attr("id"));
                }
                else {
                    selectLi = $(event.target);
                }
                if ($(selectLi).data("children") != undefined) {
                    if (curTreeID != $(selectLi).attr("id") || isChangeTreeStatus == true) {
                        curTreeID = $(selectLi).attr("id");
                        setLeftMenuTreeJsonData($(selectLi).data("children"));
                        isChangeTreeStatus = false;
                    }
                    switch_flip = 2;
                    $("#area_cut").click();
                } else {
                    $('#leftMenuTree').empty();
                    switch_flip = 1;
                    $("#area_cut").click();
                }

                $("#menu>li").removeClass("focused");
                $(selectLi).addClass("focused");
                if ($(event.target).data("attributes").handls != "" && $(event.target).data("attributes").handls != null) {
                    switch_flip = 2;
                    $("#area_cut").click();
                    eval($(event.target).data("attributes").handls);
                }
                if ($(event.target).data("attributes").url != null && $(event.target).data("attributes").url != "")
                    setMainIframeUrl($(event.target).data("attributes").url, $(event.target).data("title"));
                else
                    setMainIframeUrl("/sys/space.html", "信息列表");

                curTreeID = $(selectLi).attr("id");
                $("#nav_title").text($(this).html().replace(/\r?\n/g, "").replace(/(.*?)<.*/, "$1"));
                reloadAfter();
            });

            if (item.children != undefined && item.children.length > 0) {
                var subul = document.createElement("ul")
                $.each(item.children, function (i, subitem) {
                    var subli = creatMenuLi(subitem);
                    $(subli).attr("level", "1");
                    $(subul).append(subli);
                });
                $(li).append(subul);
            }
            $("#menu").append(li);
            //默认选中项
            if (if_flag == true && i == 0) {
                defalut_menu_li = $(li);
            }

        }
    });

    if (is_zwgk) {
        if ($("#cms_ul_cur > li").length == 1) {
            if ($("#menu > li").length == 1) {
                $("#cms_ul_cur li:first").click();
            }
        }
    }
}


//显示诉求统计数字
var appeal_sq_map = new Map();
function getAppealSQCount(f_type) {
    var show_0 = false;
    if (f_type == 0)
        show_0 = true;

    var children = $('#leftMenuTree').tree('getChildren');

    for (var i = 0; i < children.length; i++) {
        var url = children[i].attributes.url;

        if (children[i].text == "已处理信件" || children[i].text == "信件跟踪")
            break;

        if (url != "" && url.indexOf("/appeal/sq/sqList.jsp") > -1) {
            try {
                setUrlInMap(url);
                var count = jsonrpc.SQRPC.getSqCount(appeal_sq_map);
                if (count != 0 || show_0 == true) {
                    var texts = children[i].text;
                    if (texts.indexOf("(") > -1) {
                        texts = texts.substring(0, texts.indexOf("("))
                    }
                    children[i].text = texts + " (" + count + ")";
                    $('#leftMenuTree').tree('update', children[i]);
                }
                appeal_sq_map.clear();
            } catch (e) {
            }
        }
    }
}

function getAppealSQCountByURL(sq_url) {
    sq_url = sq_url.substring(sq_url.indexOf("/sys"));
    var children = $('#leftMenuTree').tree('getChildren');

    for (var i = 0; i < children.length; i++) {
        var url = children[i].attributes.url;

        if (url == sq_url && url.indexOf("/appeal/sq/sqList.jsp") > -1) {
            try {
                setUrlInMap(url);
                var count = jsonrpc.SQRPC.getSqCount(appeal_sq_map);

                if (count != 0) {
                    var texts = children[i].text;
                    if (texts.indexOf("(") > -1) {
                        texts = texts.substring(0, texts.indexOf("("))
                    }
                    children[i].text = texts + " (" + count + ")";
                    $('#leftMenuTree').tree('update', children[i]);
                } else {
                    children[i].text = children[i].text.replace(/\(\d*\)$/ig, "");
                    $('#leftMenuTree').tree('update', children[i]);
                }
                appeal_sq_map.clear();
                break;
            } catch (e) {
            }
        }
    }
}

function setUrlInMap(url) {
    var Plist = [];
    if (url.indexOf('?') > 0) {
        Plist = url.split('?')[1].split('&');
    } else if (url.indexOf('#') > 0) {
        Plist = url.split('#')[1].split('&');
    }
    if (url.length > 0) {
        for (var i = 0; i < Plist.length; i++) {
            var GetValue = Plist[i].split('=');
            appeal_sq_map.put(GetValue[0], GetValue[1]);
        }
    }
}

//点击站点时所触发的方法
function showSiteMenu(site_id) {
    current_site_id = site_id;
    request.setCookie(site_cookie_name, site_id);
}

function createSiteMenu() {
    $("#system_name").html('<ul id="cms_ul"><li class="focused"><span id="cms_ul_cur_name">内容管理系统</span><ul id="cms_ul_cur"></ul></li></ul>');
    $.each(cms_json_data, function (i, item) {
        var li = creatTopMenuLi(item);
        $(li).attr("level", "0");

        if (init_site_id != "" && init_site_id != null) {
            if (cms_json_data[i].attributes.handls.indexOf(init_site_id) > -1) {
                init_site_menu_id = cms_json_data[i].id;
            }
        } else {
            if (i == 0)
                init_site_menu_id = cms_json_data[i].id;
        }

        //防止事件冒泡，只在最外层bind事件即可
        $(li).bind('click', function (event) {
            reloadBefore();
            eval($(this).data("attributes").handls);
            child_jData = $(this).data("children");
            $("#cms_ul_cur_name").html($(this).data("title").subString2(8));
            iniMenu();
            //iniMenuOnMouseOver();
            reloadAfter();
        });
        $("#cms_ul_cur").append(li);
    });

    if (init_site_menu_id != "" && init_site_menu_id != null)
        $("#cms_ul_cur li[id='" + init_site_menu_id + "']").click();
    else
        $("#cms_ul_cur li[id='" + cms_json_data[0].id + "']").click();
}

function createZWGKMenu() {
    $("#system_name").html('<ul id="cms_ul"><li class="focused"><span id="cms_ul_cur_name">信息公开系统</span><ul id="cms_ul_cur"></ul></li></ul>');

    $.each(cms_json_data, function (i, item) {
        var li = creatTopMenuLi(item);
        $(li).attr("level", "0");

        if (init_site_id != "" && init_site_id != null) {
            if (cms_json_data[i].attributes.handls.indexOf(init_site_id) > -1) {
                init_site_menu_id = cms_json_data[i].id;
            }
        } else {
            if (i == 0)
                init_site_menu_id = cms_json_data[i].id;
        }

        //防止事件冒泡，只在最外层bind事件即可
        $(li).bind('click', function (event) {
            reloadBefore();
            eval($(this).data("attributes").handls);
            child_jData = $(this).data("children");
            $("#cms_ul_cur_name").html($(this).data("title").subString2(8));
            iniMenu();
            //iniMenuOnMouseOver();
            reloadAfter();
        });
        $("#cms_ul_cur").append(li);
    });


}


//创建横向菜单Li对像，并绑定子节点json数据
function creatTopMenuLi(jsonData) {
    try {
        var li = document.createElement("li")
        $(li).attr("id", jsonData.id);
        $(li).attr("title", jsonData.text);
        $(li).text(jsonData.text.subString2(10));
        $(li).data("title", jsonData.text);
        $(li).data("attributes", jsonData.attributes);

        if (jsonData.children != undefined && jsonData.children.length > 0) {
            $(li).data("children", jsonData.children);
        }
        return $(li);
    } catch (e) {
    }
    ;
}

//创建横向菜单Li对像，并绑定子节点json数据
function creatMenuLi(jsonData) {
    var li = document.createElement("li")
    $(li).attr("id", jsonData.id);
    //$(li).html(jsonData.text);
    $(li).text(jsonData.text);
    $(li).data("title", jsonData.text);
    $(li).data("attributes", jsonData.attributes);
    if (jsonData.children != undefined && jsonData.children.length > 0) {
        $(li).data("children", jsonData.children);
    }

    return $(li);
}

//左边栏目树对像赋值json格式数据
function setLeftMenuTreeJsonData(jsonData) {
    isChangeTreeStatus = true;
    if (jsonData != undefined && jsonData.length > 0) {
        $('#leftMenuTree').empty();
        $('#leftMenuTree').tree('append', {
            parent: (null),
            data: jsonData
        });
    }
}

//向指定的树中添加节点
function setAppointTreeJsonData(tree_div, jsonData) {
    isChangeTreeStatus = true;
    if (jsonData != undefined && jsonData.length > 0) {
        $('#' + tree_div).empty();
        $('#' + tree_div).tree('append', {
            parent: (null),
            data: jsonData
        });
    }
}

//默认选中左侧树某节点
function treeNodeSelected(nodid) {
    $("div[node-id]").removeClass("tree-node-selected");
    $("div[node-id='" + nodid + "']").addClass("tree-node-selected");
    //$('#leftMenuTree').tree('remove', $('#leftMenuTree').tree('select',1));
}

//插入树节点
function insertTreeNode(nodeData) {
    var node = $('#leftMenuTree').tree('getSelected');

    $('#leftMenuTree').tree('append', {
        parent: node.target,
        data: nodeData
    });
    $('#leftMenuTree').tree('expand', node.target);
}

//修改树节点
function updateTreeNode(node_id, node_text) {
    $("div[node-id='" + node_id + "'] .tree-title").text(node_text);
}

//删除树节点
function deleteTreeNode(node_id) {
    var tempArr = node_id.split(",");
    for (var i = 0; i < tempArr.length; i++) {
        $("div[node-id='" + tempArr[i] + "']").parent().remove();
    }
}

//初始化左边栏树对像
function iniLeftMenuTree() {
    $('#leftMenuTree').tree({
        //url: 'data/tree_data_tongji.json',
        //url: jsonData,
        onClick: function (node) {
            if (node.attributes != undefined) {
                setMainIframeUrl(node.attributes.url, node.text.replace(/\(\d*\)$/ig, ""), node.attributes.handls);
            }
        }
    });
}

//得到选中的树节点
function getLeftMenuChecked() {
    var ids = "";
    $('#leftMenuTree .tree-checkbox1').each(function (i) {
        if (i > 0)
            ids += ",";
        ids += $(this).parent().attr("node-id");
    });
    return ids;
}

//得到选中的所有子节点
function getLeftMenuCheckedLeafNode() {
    var ids = "";
    $('#leftMenuTree span.tree-file+span.tree-checkbox1').each(function (i) {
        if (i > 0)
            ids += ",";
        ids += $(this).parent().attr("node-id");
    });
    return ids;
}

function setMainIframeUrl(url, title, handls) {
    if (url == "" || url == null) return;

    if (current_site_id != "" && current_site_id != null) {
        if (url.indexOf("site_id=") > -1) {

        }
        else {
            if (url.indexOf("?") > -1) {
                url += "&site_id=" + current_site_id;
            } else
                url += "?site_id=" + current_site_id;
        }
    }
    var curIframe = null;
    var curTab = null;
    var index = 0;
    try{
        index = curTabIndex;
    }catch(e){
        index = parent.curTabIndex;
    }
    if ($("#iframe_" + index).length == 0) {
        curIframe = parent.$("#iframe_" + index);
    } else {
        curIframe = $("#iframe_" + index);
    }
    if ($("#tab_" + index).length == 0) {
        curTab = parent.$("#tab_" + index);
    } else {
        curTab = $("#tab_" + index);
    }

    if (handls != "" && handls != null) {
        eval(handls);
    }
    //else
    //{	//alert(handls)
    $(curIframe).attr("src", url);
    if ($("#tab_" + index + " .tabs-title").length == 0) {
        parent.$("#tab_" + index + " .tabs-title").text(title);
    } else {
        $("#tab_" + index + " .tabs-title").text(title);
    }
    showTabsScroller();
    //}
}

//窗体中各元素位置及大小自适应
//bannerHeight为隐藏Banner时用
function init_page() {
    //alert($("#takeup").attr("class"));
    //var minWidth = 1003;//窗口区域最小宽度
    var minWidth = 779;//窗口区域最小宽度
    var window_height = $(window).height();
    var banner_height = $("#banner").height()
    var area_nav_height = $("#area_nav").height();
    var area_nav_spaceline_height = $("#area_nav_spaceline").height();
    var copyright_height = $("#copyright").height();

    var area_frame_tabs_height = $("#area_frame_tabs").height();
    var area_frame_local_height = $("#area_frame_local").height();
    var area_frame_buttom_height = $("#area_frame_buttom").height();

    //alert("========="+$("#takeup").attr("class"));
    if ($("#takeup").attr("class") == "takeDown") {
        banner_height = 0;
    }
    //alert(banner_height);
    var area_main_height = window_height - 40;
    $("#area_main").css("height", area_main_height);
    $("#area_left").css("height", area_main_height);

    $("#area_leftContent").css("height", area_main_height);
    $("#leftMenu").css("height", area_main_height);//53为cicro product’logo height
    $("#area_right").css("height", area_main_height);

    var area_frame_work_height = area_main_height - area_frame_tabs_height - area_frame_local_height - area_frame_buttom_height;

    $("#area_frame_work").css("height", area_frame_work_height);
    $("#mainFrame").css("height", area_frame_work_height);

    setTabsheaderWidth();

    if ($(window).width() < minWidth) {
        $("#area_nav").css("width", minWidth);
        $(".banaerContent").css("width", minWidth);
        $("#area_main_table").css("width", minWidth);
    }
    else {
        $("#area_nav").css("width", $(window).width() - 20);
        $(".banaerContent").css("width", $(window).width());
        $("#area_main_table").css("width", $(window).width());
    }
    //alert($(window).width());
}

function setTabsheaderWidth() {
    if (switch_flip % 2 == 0)
        $(".tabs-header").css("width", $(window).width() - 20);
    else
        $(".tabs-header").css("width", $(window).width() - 233);
}


//左侧菜单打开关闭
var switch_flip = 1;
function switchSysBar() {
    $("#area_left").toggle(switch_flip++ % 2 == 0);
    if (switch_flip % 2 == 0) {
        $("#switchSysBar_pic").attr("title", "打开左栏")
    }
    else {
        $("#switchSysBar_pic").attr("title", "关闭左栏")
    }
}

/*
 //兼容了IE6的一级下拉菜单效果
 function iniMenuOnMouseOver()
 {
 $("#top_menu > li").hover(
 function () {
 $(this).addClass("iehover");
 },
 function () {
 $(this).removeClass("iehover");
 }
 )


 $("#top_menu > li > ul > li").each(function(){

 if($(this).attr("levelTop")!="levelTop")
 {
 $(this).hover(
 function () {
 $(this).addClass("iehover");
 },
 function () {
 $(this).removeClass("iehover");
 }
 );
 }

 })

 $("#menu li").each(function(){
 $(this).hover(
 function () {
 $(this).addClass("iehover");
 },
 function () {
 $(this).removeClass("iehover");
 }
 );
 });

 $("#cms_ul li").each(function(){
 $(this).hover(
 function () {
 $(this).addClass("iehover");
 },
 function () {
 $(this).removeClass("iehover");
 }
 );
 })
 }
 */

//Banner区域收缩展开效果(cookie)
function iniBannerTake() {
    //alert("$.cookie('CS_Server_BannerTake')=="+$.cookie('CS_Server_BannerTake'));
    if ($.cookie('CS_Server_BannerTake') == null) return;

    if ($.cookie('CS_Server_BannerTake') == "takeDown") {
        $("#takeup").removeClass("takeUp");
        $("#takeup").addClass("takeDown");
        $("#banner").hide();
    }
    if ($.cookie('CS_Server_BannerTake') == "takeUp") {
        $("#takeup").removeClass("takeDown");
        $("#takeup").addClass("takeUp");
        $("#banner").show();
    }
}

//Banner区域收缩展开效果
function iniBannerTakeUpDown() {
    $("#takeup").click(function () {
        if ($(this).attr("class") == "takeUp") {
            $(this).removeClass("takeUp");
            $(this).addClass("takeDown");
            $.cookie('CS_Server_BannerTake', 'takeDown');
            $("#banner").hide("blind", null, 200, init_page);
        }
        else {
            $(this).removeClass("takeDown");
            $(this).addClass("takeUp");
            $.cookie('CS_Server_BannerTake', 'takeUp');
            $("#banner").show("blind", null, 200, init_page);

        }
    });
}

var tabIndex = 100;
/*isColseBar是否要关闭按钮
 @param value_name 要提取的ID属性名称
 @param handl_name 成功后要执行的函数名
 */
function addTab(isColseBar, page_path, titles) {
    var index = tabIndex;
    if ($(".tabs").length == 0){
        index = parent.tabIndex;
    }
    var strColseBar = "";
    if (isColseBar) {
        strColseBar = "<a href=\"javascript:void(0)\" class=\"tabs-close\" onclick=\"tab_colseOnclick('" + index + "');\"></a>";
    }
    var tmpLi = "<li id=\"tab_" + index + "\"  class=\"\"><div><div><a href=\"javascript:void(0)\" class=\"tabs-inner\" onclick=\"tabOnclick('" + index + "');\"><span class=\"tabs-title tabs-closable\">信息列表</span><span class=\"tabs-icon\"></span></a>" + strColseBar + "</div></div></li>";
    if ($(".tabs").length == 0) {
        parent.$(".tabs").append(tmpLi);

        var tmpIframe = "<iframe id=\"iframe_" + index + "\" frameborder=\"0\" scrolling=\"auto\" onload=\"\" src=\"\" width=\"100%\" height=\"100%\"></iframe>"
        parent.$("#mainFrame > iframe").hide();
        parent.$("#mainFrame").append(tmpIframe);


        showTabsScroller();

        tabOnclick(index);
        if (page_path != "" && page_path != null)
            setMainIframeUrl(page_path, titles);
        else
            setMainIframeUrl("/sys/space.html", "信息列表");
        parent.tabIndex++;
    } else {
        $(".tabs").append(tmpLi);

        var tmpIframe = "<iframe id=\"iframe_" + tabIndex + "\" frameborder=\"0\" scrolling=\"auto\" onload=\"\" src=\"\" width=\"100%\" height=\"100%\"></iframe>"
        $("#mainFrame > iframe").hide();
        $("#mainFrame").append(tmpIframe);


        showTabsScroller();

        tabOnclick(tabIndex);
        if (page_path != "" && page_path != null)
            setMainIframeUrl(page_path, titles);
        else
            setMainIframeUrl("/sys/space.html", "信息列表");
        tabIndex++;
    }

}


//刷新主页签左右滚动按钮及添加新页签按钮
function showTabsScroller() {
    var tabsWidth = 0;
    var vp = null;
    if($(".tabs > li").length == 0){
        vp = parent.$(".tabs > li").last();
    }else{
        vp = $(".tabs > li").last();
    }
    var offset = vp.offset();
    var leftAreaWidht = 0;
    if (switch_flip % 2 == 0) {
        leftAreaWidht = 25;
    }
    else {
        leftAreaWidht = 233;
    }
    tabsWidth = offset.left - leftAreaWidht + vp.width() + 15;

    if($(".tabs-header").length == 0){
        var tWidth = parent.$(".tabs-header").width()
        //alert(tabsWidth+"==="+tWidth);
        if (tabsWidth >= tWidth) {
            parent.$(".tabs-scroller-left").show();
            parent.$(".tabs-scroller-right").show();
            parent.$("#tab_add").css("left", tWidth - 30);
        }
        else {
            parent.$(".tabs-scroller-left").hide();
            parent.$(".tabs-scroller-right").hide();
            parent.$("#tab_add").css("left", tabsWidth);
        }
    }else{
        var tWidth = $(".tabs-header").width()
        //alert(tabsWidth+"==="+tWidth);
        if (tabsWidth >= tWidth) {
            $(".tabs-scroller-left").show();
            $(".tabs-scroller-right").show();
            $("#tab_add").css("left", tWidth - 30);
        }
        else {
            $(".tabs-scroller-left").hide();
            $(".tabs-scroller-right").hide();
            $("#tab_add").css("left", tabsWidth);
        }
    }


}

function tabOnclick(index) {
    //alert(index);
    //alert($("#tab_"+index).attr("id"));
    if($(".tabs > li").length == 0){
        parent.$(".tabs > li").removeClass("tabs-selected");
        parent.$("#tab_" + index).addClass("tabs-selected");

        parent.$("#mainFrame > iframe").hide();
        parent.$("#iframe_" + index).show();

        parent.curTabIndex = index;
    }else{
        $(".tabs > li").removeClass("tabs-selected");
        $("#tab_" + index).addClass("tabs-selected");

        $("#mainFrame > iframe").hide();
        $("#iframe_" + index).show();

        curTabIndex = index;
    }


}

function tab_colseOnclick(index) {
    var preLiIndex = null;
    if($(".tabs > li").length == 0){
        parent.$(".tabs > li").each(function (i) {
            if (parent.$(this).attr("id") == ("tab_" + index)) {
                return false;
            }
            preLiIndex = parent.$(this).attr("id").substring(4, parent.$(this).attr("id").length);
        });
        if (index == parent.curTabIndex) {
            tabOnclick(preLiIndex);
        }
        showTabsScroller();
        parent.$("#tab_" + index).detach();
        parent.$("#iframe_" + index).detach();

    }else{
        $(".tabs > li").each(function (i) {
            if ($(this).attr("id") == ("tab_" + index)) {
                return false;
            }
            preLiIndex = $(this).attr("id").substring(4, $(this).attr("id").length);
        });
        if (index == curTabIndex) {
            tabOnclick(preLiIndex);
        }
        showTabsScroller();
        $("#tab_" + index).detach();
        $("#iframe_" + index).detach();


    }

}


function OpenModalWindow(title, url, width, height) {
    if ($('#sysDialog_Frame').length == 0) {
        parent.$('#sysDialog_Frame').attr("src", url);
        parent.$("#sysDialog").dialog({
            resizable: false,
            width: width,
            height: height,
            modal: true,
            title: title
        });
    } else {
        $('#sysDialog_Frame').attr("src", url);
        $("#sysDialog").dialog({
            resizable: false,
            width: width,
            height: height,
            modal: true,
            title: title
        });
    }

}
function CloseModalWindow() {
    if ($("#sysDialog").length == 0) {
        parent.$("#sysDialog").dialog('close');
        parent.$('#sysDialog_Frame').attr("src", "/sys/spaceIframe.html");
    } else {
        $("#sysDialog").dialog('close');
        $('#sysDialog_Frame').attr("src", "/sys/spaceIframe.html");
    }
}

/******************** 提示框操作　开始  *************************/
function msgAlert(msg) {
    msgHandl("alert", msg);
    if ($("#msgAlert").length == 0) {
        parent.$("#msgAlert").oneTime(1300, function () {
            parent.$("#msgAlert").fadeOut(300, function () {
                parent.$("#msgAlert").dialog('close');
            });
        });
    } else {
        $("#msgAlert").oneTime(1300, function () {
            $(this).fadeOut(300, function () {
                $(this).dialog('close');
            });
        });
    }
}

function msgError(msg) {
    msgHandl("error", msg);
}

function msgWargin(msg) {
    msgHandl("wargin", msg);
}

function msgHandl(type, msg) {
    var ms = "提示信息";
    if ($("#msgAlert div:first").length == 0) {
        parent.$("#msgAlert div:first").removeAttr("class");
        if (type == "alert") {
            ms = "提示信息";
            //$("#msgAlert").attr("title","提示信息");
            parent.$("#msgAlert div:first").addClass("alert_img");
        }
        if (type == "error") {
            ms = "错误";
            //$("#msgAlert").attr("title","错误");
            parent.$("#msgAlert div:first").addClass("error_img");
        }
        if (type == "wargin") {
            ms = "警告";
            //$("#msgAlert").attr("title","警告");
            parent.$("#msgAlert div:first").addClass("wargin_img");
        }
        parent.$("#msgAlert .textCenter").show();
        parent.$("#msgAlert div:first").addClass("msg_left_div");
        //首先解除操作按钮上的绑定事件，要不然使用click会累加事件的
        parent.$("#msgAlert input").unbind('click');
        //给操作按钮加上点击事件
        parent.$("#msgAlert input").click(function () {
            try{
                $("#msgAlert").dialog('close');
            }catch (e){
                parent.$("#msgAlert").dialog('close');
            }
        });
        //加入描述语句
        parent.$("#msgAlert td").html(msg);
        parent.$("#msgAlert").dialog({
            resizable: false,
            width: 340,
            height: 213,
            modal: true,
            title: ms
        });
    } else {
        $("#msgAlert div:first").removeAttr("class");
        if (type == "alert") {
            ms = "提示信息";
            //$("#msgAlert").attr("title","提示信息");
            $("#msgAlert div:first").addClass("alert_img");
        }
        if (type == "error") {
            ms = "错误";
            //$("#msgAlert").attr("title","错误");
            $("#msgAlert div:first").addClass("error_img");
        }
        if (type == "wargin") {
            ms = "警告";
            //$("#msgAlert").attr("title","警告");
            $("#msgAlert div:first").addClass("wargin_img");
        }
        $("#msgAlert .textCenter").show();
        $("#msgAlert div:first").addClass("msg_left_div");
        //首先解除操作按钮上的绑定事件，要不然使用click会累加事件的
        $("#msgAlert input").unbind('click');
        //给操作按钮加上点击事件
        $("#msgAlert input").click(function () {
            $("#msgAlert").dialog('close');
        });
        //加入描述语句
        $("#msgAlert td").html(msg);
        $("#msgAlert").dialog({
            resizable: false,
            width: 340,
            height: 213,
            modal: true,
            title: ms
        });
    }

}

//延时提示窗口
function waitMsgWindow() {
    if ($("#msgAlert div:first").length == 0) {
        parent.$("#msgAlert div:first").addClass("wait_img");
        parent.$("#msgAlert .textCenter").hide();
        parent.$("#msgAlert td").html("页面加载中，请稍后...");
        parent.$("#msgAlert").dialog({
            resizable: false,
            width: 340,
            height: 183,
            modal: true,
            title: '页面加载'
        });
    } else {
        $("#msgAlert div:first").addClass("wait_img");
        $("#msgAlert .textCenter").hide();
        $("#msgAlert td").html("页面加载中，请稍后...");
        $("#msgAlert").dialog({
            resizable: false,
            width: 340,
            height: 183,
            modal: true,
            title: '页面加载'
        });
    }

}

//关闭延时提示窗口
function closeWaitMsgWindow() {
    if ($("#msgAlert").length == 0) {
        parent.$("#msgAlert").dialog('close');
    } else {
        $("#msgAlert").dialog('close');
    }


}

//fun参数：用于用户点击确认按钮后执行的事件，其中iframe_1需要替换为当前所在iframe的名称，需要一个当前iframe的对象
function msgConfirm(msg, fun) {
    if ($("#msgConfirm input:first").length == 0) {
        //首先解除操作按钮上的绑定事件，要不然使用click会累加事件的
        parent.$("#msgConfirm input:first").unbind('click');
        parent.$("#msgConfirm input:last").unbind('click');
        //给操作按钮加上点击事件

        parent.$("#msgConfirm input:first").click(function () {
            parent.$("#msgConfirm").dialog('close');
            try{
                eval('parent.document.getElementById("iframe_' + parent.curTabIndex + '").contentWindow.' + fun);
            }catch (e){
                eval('parent.document.getElementById("iframe_1").contentWindow.' + fun);
            }
        });
        parent.$("#msgConfirm input:last").click(function () {
            parent.$("#msgConfirm").dialog('close');
        });
        //加入描述语句
        parent.$("#msgConfirm td").html(msg);
        parent.$("#msgConfirm").dialog({
            resizable: false,
            width: 340,
            height: 213,
            modal: true
        });
    } else {
        //首先解除操作按钮上的绑定事件，要不然使用click会累加事件的
        $("#msgConfirm input:first").unbind('click');
        $("#msgConfirm input:last").unbind('click');
        //给操作按钮加上点击事件

        $("#msgConfirm input:first").click(function () {
            $("#msgConfirm").dialog('close');
            eval('document.getElementById("iframe_' + curTabIndex + '").contentWindow.' + fun)
        });
        $("#msgConfirm input:last").click(function () {
            $("#msgConfirm").dialog('close');
        });
        //加入描述语句
        $("#msgConfirm td").html(msg);
        $("#msgConfirm").dialog({
            resizable: false,
            width: 340,
            height: 213,
            modal: true
        });
    }

}

/******************** 提示框操作　结束  *************************/

function loginOut() {
    if (UserLogin.loginOut())
        window.location.href = "/sys/login.jsp";
    else
        alert("注销失败");
}

//得到当前IFrame窗口对象
function getCurrentFrameObj(c_index) {
    /*if (c_index != "" && c_index != null)
        return document.getElementById("iframe_" + c_index).contentWindow;
    else{
        try {
            return document.getElementById("iframe_" + curTabIndex).contentWindow;
        } catch (e) {
            return parent.document.getElementById("iframe_" + parent.curTabIndex).contentWindow;
        }
    }*/
    var result = null;
    if (c_index != "" && c_index != null)
        try {
            result = document.getElementById("iframe_" + c_index).contentWindow;
        } catch (e) {
            result = parent.document.getElementById("iframe_" + c_index).contentWindow;
        }
    else{
        try {
            result = document.getElementById("iframe_" + curTabIndex).contentWindow;
        } catch (e) {
            result = parent.document.getElementById("iframe_" + parent.curTabIndex).contentWindow;
        }
    }
    return result;
}


//根据登录用户得到它能管理的模板分类树
function getTemplateCategoryTreeJsonData() {
    var TemplateRPCt = jsonrpc.TemplateRPC;
    var tc_jdata = eval(TemplateRPCt.templateCategoryListToJsonStr("0", current_site_id, current_app_id));
    setLeftMenuTreeJsonData(tc_jdata);
}

//根据登录用户得到它能管理的标签分类树
function getWareCategoryTreeJsonData() {
    var WareRPC = jsonrpc.WareRPC;
    var ware_jdata = eval(WareRPC.getJSONTreeBySiteUser(LoginUserBean.user_id, current_site_id));
    setLeftMenuTreeJsonData(ware_jdata);
}

function getInfoCategoryTreeJsonData() {
    var InfoBaseRPC = jsonrpc.InfoBaseRPC;
    var info_cdata = eval(InfoBaseRPC.getTempJsonDataTree());
    setLeftMenuTreeJsonData(info_cdata);
}

//得到专题树
function getZTCategoryTree() {
    var category_jdata = eval(jsonrpc.CategoryRPC.getZTCategoryTreeJsonStr(current_site_id, LoginUserBean.user_id));
    setLeftMenuTreeJsonData(category_jdata);
}

//根据登录用户得到它能管理的栏目树
function getInfoCategoryTreeByUserID() {
    $('#leftMenuTree').tree({
        checkbox: false,
        url: '/sys/cms/category/category.jsp?site_id=' + current_site_id + '&user_id=' + LoginUserBean.user_id + '&pid=0',
        onBeforeExpand: function (node, param) {
            $('#leftMenuTree').tree('options').url = '/sys/cms/category/category.jsp?site_id=' + current_site_id + '&user_id=' + LoginUserBean.user_id + '&pid=' + node.id;	// change the url
            //param.myattr = 'test';    // or change request parameter
        },
        onClick: function (node) {
            if (node.state == "open") {
                $(this).tree('collapse', node.target);
            }
            else {
                $(this).tree('expand', node.target);
            }
            if (node.attributes != undefined) {
                setMainIframeUrl(node.attributes.url, node.text.replace(/\(\d*\)$/ig, ""), node.attributes.handls);
            }
        }
    });
}

//返回值处理
function reloadLeftMenuTree(result, e) {
    setLeftMenuTreeJsonData(eval(result));
}

//根据登录用户得到它能管理的服务树
function getFWCategoryTreeByUserID() {
    var category_jdata = eval(jsonrpc.CategoryRPC.getFWCategoryTreeByUserID("ggfw", LoginUserBean.user_id));
    setLeftMenuTreeJsonData(category_jdata);
}

//得到接收信息的站点树
function getRecieveSiteJSONTree() {
    var category_jdata = eval(jsonrpc.SendInfoRPC.getRecieveSiteJSONTree(""));
    setLeftMenuTreeJsonData(category_jdata);
}

//得到接收统计的站点树
function getRecieveCountJSONTree(type) {
    var category_jdata = eval(jsonrpc.SendInfoRPC.getRecieveSiteJSONTree(type));
    setLeftMenuTreeJsonData(category_jdata);
}

//得到报送信息的站点树
function getSendSiteJSONTree() {
    var category_jdata = eval(jsonrpc.SendInfoRPC.getSendSiteJSONTree(""));
    setLeftMenuTreeJsonData(category_jdata);
}

function getSendCountJSONTree(type) {
    var category_jdata = eval(jsonrpc.SendInfoRPC.getSendSiteJSONTree(type));
    setLeftMenuTreeJsonData(category_jdata);
}


//得到场景式服务的主题树
function getSerCategoryRootJSONTree() {
    var category_jdata = eval(jsonrpc.SerRPC.getSerCategoryRootJSONTree());
    setLeftMenuTreeJsonData(category_jdata);
}

//得到当前节点的所有下级叶节点
function getCurrentNodeChileLeftNodeIDS() {
    var c_ids = getLeftMenuChecked();
    var ids = "";
    $('#leftMenuTree .tree-node-selected').parent().find('.tree-file').each(function () {
        ids += "," + $(this).parent().attr("node-id");
    });
    if (ids != "" && ids != null)
        c_ids = c_ids + ids;

    return c_ids;
}

//得到选中节点的第一个叶子节点ID
function getFirstChildNodeID() {
    return $('#leftMenuTree .tree-node-selected').parent().find('.tree-file').first().parent().attr("node-id");
}
//得到选中节点的第一个叶子节点名称
function getFirstChildNodeValue() {
    return $('#leftMenuTree .tree-node-selected').parent().find('.tree-file').first().parent().text();
}

//根据站点ID，应用ID，人员ID得到他所拥有的权限ID
var user_opt_ids = new Map();
function getOptIDSByUser(app_id, site_id) {
    if (user_opt_ids.containsKey(site_id)) {
        return user_opt_ids.get(site_id);
    } else {
        var opt_ids = UserLogin.getOptIDSByUserAPPSite(LoginUserBean.user_id, app_id, site_id);
        user_opt_ids.put(opt_ids);
        return opt_ids;
    }
}

//js加载之前处理事件
function reloadBefore() {
    waitMsgWindow();
}

//js加载之后处理事件
function reloadAfter() {
    closeWaitMsgWindow();
}