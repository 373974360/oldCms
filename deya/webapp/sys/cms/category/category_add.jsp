<%@ page contentType="text/html; charset=utf-8" %>
<%
    String cat_type = request.getParameter("cat_type");
    if (cat_type == null || "".equals(cat_type) || "null".equals(cat_type))
        cat_type = "0";
    String parent_id = request.getParameter("parentID");
    String id = request.getParameter("id");
    String site_id = request.getParameter("site_id");
    String app_id = request.getParameter("app_id");
    String top_index = request.getParameter("top_index");
    String class_id = request.getParameter("class_id");
    if (class_id == null || "".equals(class_id))
        class_id = "0";
    if (site_id == null || "".equals(site_id))
        site_id = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>目录修改</title>


    <link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
    <script id="src-dict" type="text/javascript" src="../../js/pinyin/pinyin.dict.src.js"></script>
    <script type="text/javascript" src="../../js/pinyin/pinyin.js"></script>
    <script type="text/javascript" src="js/categoryList.js"></script>
    <script type="text/javascript">
        var cat_type = "<%=cat_type%>";//目录类型：0：普通栏目1：专题2：服务应用目录
        var top_index = "<%=top_index%>";//父窗口ID
        var parent_id = <%=parent_id%>;//父节点ID
        var id = "<%=id%>";//栏目ID，用于修改
        var class_id = "<%=class_id%>";//分类方式ID
        var site_id = "CMScdgjj";//站点ID
        var app_id = "<%=app_id%>";//应用ID
        var is_update = false;//是否是修改
        var cat_id;
        var CategoryGetRegu = new Bean("com.deya.wcm.bean.cms.category.CategoryGetRegu", true);
        var defaultBean;
        var SiteRPC = jsonrpc.SiteRPC;

        $(document).ready(function () {
            setModelInfoList();
            initButtomStyle();

            if (app_id == "ggfw" || app_id == "cms") {
                $(".list_tab").eq(1).hide();
                $(".list_tab").eq(2).hide();
                $(".list_tab").eq(3).hide();
                $(".list_tab").eq(4).hide();
                $(".list_tab").eq(5).hide();
                $(".list_tab").eq(6).hide();
            }

            //公共服务不显示数据共享同步的东东
            if (app_id == "ggfw" || app_id == "zwgk") {
                $(".list_tab").eq(2).hide();
                $(".list_tab").eq(3).hide();
                $(".list_tab").eq(4).hide();
                $(".list_tab").eq(5).hide();
            }
            //信息公开的栏目不选择列表，首页模板，不生成静态页
            if (app_id == "zwgk") {
                $(".cms_page_templage").hide();
            }

            if (cat_type == "1") {//专题中的添加目录
                getZTCateList();
                $(".zt_tr").show();
            }
            if (cat_type == "2") {//服务目录，只显示归档选项
                $(".zt_gd").show();
            }
            getWorkFlowList();
            if (id != "" && id != "null" && id != null) {
                is_update = true;
                defaultBean = CategoryRPC.getCategoryBean(id);
                if (defaultBean) {
                    $("#category_table").autoFill(defaultBean);
                    parent_id = defaultBean.parent_id;
                    cat_id = defaultBean.cat_id;
                    if (defaultBean.cat_class_id != 0) {
                        var n_bean = CategoryRPC.getCategoryBean(defaultBean.cat_class_id);
                        if (n_bean != null && n_bean.cat_cname != "")
                            $("#cat_class_name").val(n_bean.cat_cname);
                    }
                    reloadTemplate();
                    changeWorkFlowHandl(defaultBean.wf_id);
                    checkedCategoryModelInfo(cat_id);//选中已关联的内容模型

                    changeLabShowList("listTable_1");
                    changeLabShowList("listTable_2");
                    changeLabShowList("listTable_3");
                    changeLabShowList("listTable_4");
                    changeLabShowList("listTable_5");
                    if (app_id == "zwgk")
                        changeLabShowList("listTable_6");
                    changeLabShowList("listTable_0");
                }
                $("#addButton").click(updateCategory);
            }
            else {
                if (parent_id != 0) {//有些数据需从父级继承过来
                    defaultBean = CategoryRPC.getCategoryBeanCatID(parent_id, site_id);
                    defaultBean.cat_cname = "";
                    defaultBean.parent_id = parent_id;
                    defaultBean.cat_ename = "";
                    defaultBean.cat_code = "";
                    defaultBean.cat_description = "";
                    defaultBean.cat_memo = "";
                    //defaultBean.template_index = "0";
                    defaultBean.is_mutilpage = "0";
                    defaultBean.jump_url = "";
                    $("#category_table").autoFill(defaultBean);
                    reloadTemplate();
                    checkedCategoryModelInfo(parent_id);//选中已关联的内容模型

                }
                $("#addButton").click(addCategory);
            }

            if (class_id == 0)//为0表示是从站点中添加的，要打开共享目录的窗口，否则打开选择基础目录的窗口
                $("#selectCateClassButton").click(function () {
                    openSelectSingleShareCateClass('选择类目', 'releCateClass', app_id)
                });
            else
                $("#selectCateClassButton").click(function () {
                    openSelectSingleCateClass('选择类目', 'releCateClass')
                });


            init_input();
            init_FromTabsStyle();


            //中文转换成拼音首字母缩写
            $("#cat_cname").blur(function () {
                if ($("#cat_ename").val() == "") {
                    var strs = new Array();
                    var result = "";
                    var cat_ename = pinyin(this.value, true, ",").toLowerCase();
                    strs = cat_ename.split(",");
                    for (i = 0; i < strs.length; i++) {
                        result += strs[i].substr(0, 1);
                    }
                    $("#cat_ename").val(result);
                }
            });
        });

        //得到专题列表
        function getZTCateList() {
            var list = CategoryRPC.getZTCategoryList(site_id);
            list = List.toJSList(list);
            $("#zt_cat_id").empty();
            $("#zt_cat_id").addOptions(list, "zt_cat_id", "zt_cat_name");
        }

        //添加目录
        function addCategory() {
            var bean = BeanUtil.getCopy(CategoryBean);
            $("#category_table").autoBind(bean);

            if (!standard_checkInputInfo("category_table")) {
                $("#fromTabs li:first-child .tab_right").click();
                return;
            }

            if (CategoryRPC.categoryIsExist(parent_id, $("#cat_ename").val(), app_id, site_id)) {
                parent.msgWargin("该英文名称已存在此目录下，请更换英文名称");
                return;
            }

            bean.cat_id = CategoryRPC.getNewCategoryID();
            bean.id = bean.cat_id;
            bean.cat_type = cat_type;
            bean.app_id = app_id;

            $("#addButton").attr("disabled", "disabled");

            //得到选择的内容模型
            var cat_model_list = getSelectCategoryModelList(bean.id);

            //处理获取规则列表
            var regu_list = returnCategoryReguList(bean.cat_id);
            if (regu_list != null && regu_list.size() > 0) {
                bean.hj_sql = CategoryRPC.insertCategoryRegu(regu_list, bean.cat_id, site_id, app_id);
            }

            if (CategoryRPC.insertCategory(bean, false)) {
                CategoryRPC.insertCategoryModel(cat_model_list);
                insertShared(bean.id);
                insertSyncCategory(bean.id);
                if (app_id == "zwgk")
                    insertToInfoCategory(bean.id);
                insertCategoryReleUser(bean.id);

                parent.msgAlert("目录信息" + WCMLang.Add_success);
                try {
                    if (cat_type == "0") {
                        parent.getCurrentFrameObj(top_index).changeCategoryListTable(parent_id);
                        parent.getCurrentFrameObj(top_index).insertCategoryTree(bean.cat_id, bean.cat_cname);
                    } else
                        parent.getCurrentFrameObj(top_index).reloadZTList();
                } catch (e) {

                }
                parent.tab_colseOnclick(parent.curTabIndex);
            }
            else {
                parent.msgWargin("目录信息" + WCMLang.Add_fail);
            }
        }

        //插入同步信息
        function insertSyncCategory(c_id) {
            var is_auto_publish = 0;
            if ($("#is_auto_publish").is(':checked')) {
                is_auto_publish = 1;
            }

            var cite_type = $("input[id='cite_type']:checked").val();

            var sync_list = new List();
            $("#cat_list li").each(function () {
                var sb = BeanUtil.getCopy(SyncBean);
                sb.s_site_id = site_id;
                sb.s_cat_id = c_id;
                sb.t_site_id = $(this).attr("site_id");
                sb.t_cat_id = $(this).attr("cat_id");
                sb.is_auto_publish = is_auto_publish;
                sb.cite_type = cite_type;
                sb.orientation = 0;
                sync_list.add(sb);
            });

            CategoryRPC.insertSync(sync_list, c_id, site_id, 0);// 0表示同步获取的
        }

        //插入推送信息
        function insertToInfoCategory(c_id) {
            var sync_list = new List();
            $("#toInfo_cat_list li").each(function () {
                var sb = BeanUtil.getCopy(SyncBean);
                sb.s_site_id = site_id;
                sb.s_cat_id = c_id;
                sb.t_site_id = $(this).attr("site_id");
                sb.t_cat_id = $(this).attr("cat_id");
                sb.is_auto_publish = 0;
                sb.cite_type = 0;
                sb.orientation = 1;
                sync_list.add(sb);
            });

            CategoryRPC.insertSync(sync_list, c_id, site_id, 1);// 0表示同步获取的
        }


        //插入共享和接收数据
        function insertShared(c_id) {
            //如果点开了共享标签,进行判断
            var csb = BeanUtil.getCopy(CategorySharedBean);
            csb.s_site_id = site_id;
            csb.cat_id = c_id;
            if (share_isClick == true) {
                csb.shared_type = 0;
                insertSharedHandl(csb, "share_table");
            }
            if (receive_isClick == true) {
                csb.shared_type = 1;
                insertSharedHandl(csb, "receive_table");
            }
        }

        //插入共享和接收数据
        function insertSharedHandl(csb, table_name) {
            if ($("#" + table_name + " :radio[checked]").val() == 1) {//1为全部共享
                csb.range_type = 1;
                if (is_update)
                    CategoryRPC.updateCategoryShared(csb);
                else
                    CategoryRPC.insertCategoryShared(csb);
            } else {//0为限制共享，先判断站点是否为空，如果为空，不进行保存
                csb.t_site_id = getSelectSiteIDS(table_name);
                csb.range_type = 0;
                if (is_update) {
                    CategoryRPC.updateCategoryShared(csb);
                }
                else {
                    if (csb.t_site_id != "") {
                        CategoryRPC.insertCategoryShared(csb);
                    }
                }
            }
        }

        //插入目录与人员的关联信息
        function insertCategoryReleUser(c_id) {
            var p_list = new List();
            $("#t_user_list li").each(function () {
                var crb = BeanUtil.getCopy(CategoryReleBean);
                crb.cat_id = c_id;
                crb.prv_id = $(this).attr("p_id");
                crb.priv_type = 0;
                crb.app_id = app_id;
                crb.site_id = site_id;
                p_list.add(crb);
            });

            $("#t_group_list li").each(function () {
                var crb = BeanUtil.getCopy(CategoryReleBean);
                crb.cat_id = c_id;
                crb.prv_id = $(this).attr("p_id");
                crb.priv_type = 1;
                crb.app_id = app_id;
                crb.site_id = site_id;
                p_list.add(crb);
            });

            CategoryRPC.insertCategoryReleUser(p_list, c_id, site_id);
        }

        function updateCategory() {
            var bean = BeanUtil.getCopy(CategoryBean);
            $("#category_table").autoBind(bean);
            if (!standard_checkInputInfo("category_table")) {
                return;
            }

            bean.id = id;
            bean.cat_id = cat_id;
            //处理获取规则列表
            var regu_list = returnCategoryReguList(cat_id);


            bean.hj_sql = CategoryRPC.insertCategoryRegu(regu_list, cat_id, site_id, app_id);


            if (defaultBean.cat_ename != bean.cat_ename) {
                if (CategoryRPC.categoryIsExist(parent_id, bean.cat_ename, app_id, site_id)) {
                    parent.msgWargin("该英文名称已存在此目录下，请更换英文名称");
                    return;
                }
            }

            //得到选择的内容模型
            var cat_model_list = getSelectCategoryModelList(cat_id);

            if (CategoryRPC.updateCategory(bean)) {
                CategoryRPC.updateCategoryModel(cat_model_list, cat_id, site_id);
                insertShared(cat_id);
                insertSyncCategory(cat_id);
                if (app_id == "zwgk")
                    insertToInfoCategory(cat_id);
                insertCategoryReleUser(cat_id);

                parent.msgAlert("目录信息" + WCMLang.Add_success);
                try {
                    if (cat_type == "0") {
                        parent.getCurrentFrameObj(top_index).changeCategoryListTable(parent_id);

                        parent.getCurrentFrameObj(top_index).updateCategoryTree(cat_id, bean.cat_cname);
                    } else
                        parent.getCurrentFrameObj(top_index).reloadZTList();
                } catch (e) {
                }
                parent.tab_colseOnclick(parent.curTabIndex);
            }
            else {
                parent.msgWargin("目录信息" + WCMLang.Add_fail);
            }
        }

        var opt_isClick = false;//设置是否被点击过
        var receive_isClick = false;
        var share_isClick = false;
        var sync_isClick = false;
        var regu_isClick = false;
        var toInfo_isClick = false;
        var node_json_data;
        function changeLabShowList(labname) {
            if (labname == "listTable_1") {
                if (!opt_isClick) {
                    getSiteUser();
                    if (is_update)
                        getCategoryReleUserInfo();
                }
                opt_isClick = true;
            }
            if (labname == "listTable_2") {
                if (!share_isClick) {
                    getSiteList("share_table");
                    if (is_update) {//如果是修改，取到保存过的值
                        getSharedSaveInfo("share_table", 0);
                    }
                    share_isClick = true;
                }
            }
            if (labname == "listTable_3") {
                if (!receive_isClick) {
                    getSiteList("receive_table");
                    if (is_update) {//如果是修改，取到保存过的值
                        getSharedSaveInfo("receive_table", 1);
                    }
                    receive_isClick = true;
                }
            }
            if (labname == "listTable_4") {
                if (!sync_isClick) {
                    getSharedList();
                    if (is_update)
                        getSyncCategory();
                }
                sync_isClick = true;
            }
            if (labname == "listTable_5") {
                if (!regu_isClick) {
                    initMenuTree("leftMenuTree2_regu", true);
                    node_json_data = eval(jsonrpc.GKNodeRPC.getGKNodeTreebyCateID(0));
                    getAllowSharedSite();
                    if (is_update) {
                        getSourceRegulationList();//得到已选过的规则
                    }
                }
                regu_isClick = true;
            }
            if (labname == "listTable_6") {
                if (!toInfo_isClick) {
                    getToInfoSiteList();
                    if (is_update)
                        getToInfoCategoryList();
                }
                toInfo_isClick = true;
            }
            $(".infoListTable").addClass("hidden");
            $("#" + labname).removeClass("hidden");
        }

        //得到推送规则中的可选站点列表
        function getToInfoSiteList() {
            //得到此公开节点关联的站点
            var GKNodeBean = jsonrpc.GKNodeRPC.getGKNodeBeanByNodeID(site_id);
            var r_site_id = GKNodeBean.rela_site_id;
            $("#toInfo_site_list").addOptionsSingl(r_site_id, SiteRPC.getSiteBeanBySiteID(r_site_id).site_name);
            getToInfoCategoryBySite(r_site_id);
            //得到公开系统关联的站点
            var main_site_id = SiteRPC.getSiteIDByAppID(app_id);
            if (main_site_id != r_site_id) {
                $("#toInfo_site_list").addOptionsSingl(main_site_id, SiteRPC.getSiteBeanBySiteID(main_site_id).site_name);
            }

        }

        //得到选过的推送规则
        function getToInfoCategoryList() {
            var syncl_list = CategoryRPC.getToInfoCategoryList(site_id, cat_id);
            syncl_list = List.toJSList(syncl_list);
            if (syncl_list != null && syncl_list.size() > 0) {
                $("#cite_type[value='" + syncl_list.get(0).cite_type + "']").attr("checked", true);
                $("#is_auto_publish[value='" + syncl_list.get(0).is_auto_publish + "']").attr("checked", true);
                for (var i = 0; i < syncl_list.size(); i++) {
                    var siteB = jsonrpc.SiteRPC.getSiteBeanBySiteID(syncl_list.get(i).t_site_id);
                    var catB = CategoryRPC.getCategoryBean(syncl_list.get(i).t_cat_id);
                    if (siteB != null && catB != null)
                        selectSharedCategory(siteB.site_id, siteB.site_name, catB.cat_id, catB.cat_cname, "toInfo_cat_list", "leftMenuTree_toInfo");
                }
            }
            defaultSelectSyncCategory("leftMenuTree_toInfo", "toInfo_cat_list", site_id);
        }

        //根据站点ID得到推送目录站点的所有栏目树
        function getToInfoCategoryBySite(s_site_id) {
            try {
                var json_data = eval(CategoryRPC.getCategoryTreeBySiteID(s_site_id));
                setAppointTreeJsonData("leftMenuTree_toInfo", json_data);
                initMenuClick(s_site_id, "leftMenuTree_toInfo", "toInfo_cat_list", "toInfo_site_list");
            } catch (e) {

            }
        }


        //得到站点与人员关联信息
        function getSiteUser() {
            var user_list = jsonrpc.SiteUserRPC.getSiteUserListBySiteAppID(app_id, site_id);
            user_list = List.toJSList(user_list);

            if (user_list != null && user_list.size() > 0) {
                for (var i = 0; i < user_list.size(); i++) {
                    $("#user_list").append('<li style="float:none;height:20px"><input type="checkbox" id="user_ids" value="' + user_list.get(i).user_id + '" onclick="selectedUserGroup(0,this,\'' + user_list.get(i).user_id + '\',\'' + user_list.get(i).user_realname + '\')"><label onclick="lableClickSite(this)">' + user_list.get(i).user_realname + '</label></li>');
                }
            }


            var group_list = jsonrpc.GroupManRPC.getGroupListByAppSiteID(app_id, site_id);
            group_list = List.toJSList(group_list);

            if (group_list != null && group_list.size() > 0) {
                for (var i = 0; i < group_list.size(); i++) {
                    $("#group_list").append('<li style="float:none;height:20px"><input type="checkbox" id="user_ids" value="' + group_list.get(i).group_id + '" onclick="selectedUserGroup(1,this,\'' + group_list.get(i).group_id + '\',\'' + group_list.get(i).group_name + '\')"><label onclick="lableClickSite(this)">' + group_list.get(i).group_name + '</label></li>');
                }
            }
            init_input();
        }

        function selectedUserGroup(priv_type, obj, p_id, p_name) {
            var div_name = "";
            if (priv_type == 0)
                div_name = "t_user_list";
            else
                div_name = "t_group_list";

            if ($(obj).is(':checked')) {
                $("#" + div_name).append('<li style="float:none;height:20px" p_id="' + p_id + '">' + p_name + '<img onclick="deleteUserGroup(' + priv_type + ',this,\'' + p_id + '\')" src="../../images/delete.png" width="15" height="15" alt="" align="right"/></li>');
            } else {
                $("#" + div_name + " li[p_id='" + p_id + "']").remove();
            }
        }

        function deleteUserGroup(priv_type, obj, id) {
            if (priv_type == 0) {
                $(obj).parent().remove();
                $("#user_list :checkbox[value='" + id + "']").attr("checked", "");
            }
            else {
                $(obj).parent().remove();
                $("#group_list :checkbox[value='" + id + "']").attr("checked", "");
            }
        }

        //获取关联人员信息
        function getCategoryReleUserInfo() {
            var p_list = CategoryRPC.getCategoryReleUserListByCatID(cat_id, site_id);
            p_list = List.toJSList(p_list);

            if (p_list != null && p_list.size() > 0) {
                for (var i = 0; i < p_list.size(); i++) {
                    var div_name = "";
                    if (p_list.get(i).priv_type == 0)
                        div_name = "user_list";
                    else
                        div_name = "group_list";

                    $("#" + div_name + " :checkbox[value='" + p_list.get(i).prv_id + "']").attr("checked", true).click().attr("checked", true);
                }
            }
        }

        //得到所有站点列表
        function getSiteList(table_name) {
            var site_list = getAllSiteList();
            if (site_list != null && site_list.size() > 0) {
                for (var i = 0; i < site_list.size(); i++) {
                    if (site_list.get(i).site_id != site_id)
                        $("#" + table_name + " .site_list").append('<li style="float:none;height:20px"><input type="checkbox" id="site_ids" value="' + site_list.get(i).site_id + '" onclick="selectedSite(this,\'' + site_list.get(i).site_id + '\',\'' + site_list.get(i).site_name + '\',\'' + table_name + '\')"><label onclick="lableClickSite(this)">' + site_list.get(i).site_name + '</label></li>');
                }
                init_input();
            }
        }

        //取得共享给本站点的站点列表
        function getSharedList() {
            var s_list = CategoryRPC.getAllowSharedSite(site_id);
            s_list = List.toJSList(s_list);

            $("#shared_site_list").addOptions(s_list, "site_id", "site_name");
            $("#shared_site_list option").eq(0).val(site_id);

            getSharedCategoryBySite(site_id);
        }

        //根据站点ID得到共享的栏目树节点
        function getSharedCategoryBySite(s_site_id) {
            if (s_site_id != "") {
                try {
                    var json_data;
                    if (s_site_id == site_id) {
                        //本站的取全部栏目
                        json_data = eval(CategoryRPC.getCategoryTreeBySiteID(site_id));
                    }
                    else {
                        json_data = eval(CategoryRPC.getSharedCategoryTreeBySiteID(s_site_id, site_id));
                    }
                    //setLeftMenuTreeJsonData(json_data);
                    setAppointTreeJsonData("leftMenuTree", json_data);
                    initMenuClick(s_site_id, "leftMenuTree", "cat_list", "shared_site_list");
                } catch (e) {
                }
            }
        }

        //初始加载树节点
        function initMenuClick(t_site_id, div_name, cat_list_id, select_id) {
            $("#" + div_name + " li").css("float", "none");
            var t_site_name = $("#" + select_id + " option:selected").text();

            $("#" + div_name + " .tree-file").after('<SPAN class="tree-checkbox tree-checkbox0"></SPAN>');
            $("#" + div_name + " .tree-checkbox").click(function () {
                if ($(this).attr("class").indexOf("tree-checkbox0") > -1) {
                    $(this).removeClass("tree-checkbox0");
                    $(this).addClass("tree-checkbox1");
                    selectSharedCategory(t_site_id, t_site_name, $(this).parent().attr("node-id"), $(this).parent().find(".tree-title").text(), cat_list_id, div_name);
                } else {
                    $(this).removeClass("tree-checkbox1");
                    $(this).addClass("tree-checkbox0");
                    //删除栏目列表中的数据
                    $("#" + cat_list_id + " li[cat_id='" + $(this).parent().attr("node-id") + "'][site_id='" + t_site_id + "']").remove();
                }
            });
            defaultSelectSyncCategory(div_name, cat_list_id, t_site_id);
            init_input();
        }

        //展开站点栏目树时默认选中已选 节点
        function defaultSelectSyncCategory(tree_div_id, cat_list_id, t_site_id) {
            $("#" + cat_list_id + " li").each(function () {
                if (t_site_id == $(this).attr("site_id"))
                    $("#" + tree_div_id + " div[node-id='" + $(this).attr("cat_id") + "'] .tree-checkbox").removeClass("tree-checkbox0").addClass("tree-checkbox1");
            });
        }

        //删除可同步的栏目
        function deleteSharedCategoryList(obj, shared_cat_id, tree_div_id) {
            $(obj).parent().parent().remove();
            $("#" + tree_div_id + " div[node-id='" + shared_cat_id + "'] .tree-checkbox").removeClass("tree-checkbox1").addClass("tree-checkbox0");
        }

        //得到选过的同步栏目
        function getSyncCategory() {
            var syncl_list = CategoryRPC.getSyncListBySiteCatID(site_id, cat_id);
            syncl_list = List.toJSList(syncl_list);
            if (syncl_list != null && syncl_list.size() > 0) {
                $("#cite_type[value='" + syncl_list.get(0).cite_type + "']").attr("checked", true);
                $("#is_auto_publish[value='" + syncl_list.get(0).is_auto_publish + "']").attr("checked", true);
                for (var i = 0; i < syncl_list.size(); i++) {
                    var siteB = jsonrpc.SiteRPC.getSiteBeanBySiteID(syncl_list.get(i).t_site_id);
                    var catB = CategoryRPC.getCategoryBean(syncl_list.get(i).t_cat_id);
                    if (siteB != null && catB != null)
                        selectSharedCategory(siteB.site_id, siteB.site_name, catB.cat_id, catB.cat_cname, "cat_list", "leftMenuTree");
                }
            }
            defaultSelectSyncCategory("leftMenuTree", "cat_list", site_id);
        }

        //选择可同步的栏目
        function selectSharedCategory(t_site_id, t_site_name, shared_cat_id, shared_cat_name, cat_list_id, tree_div_id) {
            $("#" + cat_list_id).append('<li style="float:none;height:20px" site_id="' + t_site_id + '" cat_id="' + shared_cat_id + '"><div class="width150 left">' + shared_cat_name + '</div><div class="width150 left">' + t_site_name + '</div><div class="width20 left"><img onclick="deleteSharedCategoryList(this,' + shared_cat_id + ',\'' + tree_div_id + '\')" src="../../images/delete.png" width="15" height="15" alt="" align="right"/></div></li>');
        }

        //设置站点不可选
        function disabledSiteChekced(isChecked, table_name) {
            $("#" + table_name + " :checkbox").attr("disabled", isChecked);
        }

        //选择站点
        function selectedSite(obj, site_id, site_name, table_name) {
            if ($(obj).is(':checked')) {
                //var selected_site_ids = ","+getSelectSiteIDS(table_name)+",";
                //if(selected_site_ids.indexOf(","+site_id+",") == -1)
                $("#" + table_name + " .t_site_list").append('<li style="float:none;height:20px" site_id="' + site_id + '">' + site_name + '<img onclick="deleteSiteList(this,\'' + table_name + '\',\'' + site_id + '\')" src="../../images/delete.png" width="15" height="15" alt="" align="right"/></li>');
            }
            else {
                $("#" + table_name + " li[site_id='" + site_id + "']").remove();
            }

        }

        //点击lable触发事件
        function lableClickSite(obj) {
            $(obj).prev().attr("checked", $(obj).prev().is(':checked') == false ? true : false);
            $(obj).prev().click();
        }

        //得到已选择的站点ID
        function getSelectSiteIDS(table_name) {
            var ids = "";
            $("#" + table_name + " .t_site_list li").each(function () {
                ids += "," + $(this).attr("site_id");
            });
            if (ids != "")
                ids = ids.substring(1);

            return ids;
        }

        //删除已选站点
        function deleteSiteList(obj, table_name, site_id) {
            $("#" + table_name + " :checkbox[value='" + site_id + "']").attr("checked", false);
            $(obj).parent().remove();
        }

        //取到保存过的共享信息
        function getSharedSaveInfo(table_name, shared_type) {
            var shared_list = CategoryRPC.getCategorySharedListBySCS(site_id, cat_id, shared_type);
            shared_list = List.toJSList(shared_list);
            if (shared_list != null && shared_list.size() > 0) {
                for (var i = 0; i < shared_list.size(); i++) {

                    $("#" + table_name + " :radio[value='" + shared_list.get(i).range_type + "']").attr("checked", true);
                    $("#" + table_name + " :radio[value='" + shared_list.get(i).range_type + "']").click();
                    if (shared_list.get(i).t_site_id != "" && shared_list.get(i).t_site_id != null) {
                        var obj = $("#" + table_name + " :checkbox[value='" + shared_list.get(i).t_site_id + "']");
                        obj.attr("checked", true);
                        obj.click();
                        obj.attr("checked", true);
                    }
                }
            } else
                $("#" + table_name + " :radio[value='0']").click();
        }

        function openTemplate(n1, n2) {
            var temp_site_id = "";
            if (app_id != "cms") {
                temp_site_id = jsonrpc.SiteRPC.getSiteIDByAppID(app_id);
            }
            else
                temp_site_id = site_id;
            openSelectTemplate(n1, n2, temp_site_id);
        }

        /*******************************获取规则 开始**************************************/
//获取规则，切换目录类型
        function choose_regu() {
            var node_ids = "";
            var node_names = "";
            var cat_ids = "";
            var cat_names = "";
            if (choose_type == "zwgk") {
                var temp_ids = getLeafTreeNode("leftMenuTree2_regu");
                if (temp_ids == "|") {
                    parent.msgWargin("请选择信息公开目录");
                    return;
                }
                cat_ids = temp_ids.split("|")[0];
                cat_names = temp_ids.split("|")[1];
                node_ids = $('#leftMenuTree_regu').tree('getSelected').attributes.t_node_id;
                node_names = $('#leftMenuTree_regu').tree('getSelected').text;

                setIDsInDivList(1, cat_ids, cat_names, node_ids, node_names);
            } else {
                var temp_ids = getLeafTreeNode("leftMenuTree_regu");
                if (temp_ids == "|") {
                    parent.msgWargin("请选择共享目录");
                    return;
                }
                cat_ids = temp_ids.split("|")[0];
                cat_names = temp_ids.split("|")[1];

                var temp_nodes = getLeafTreeNode("leftMenuTree2_regu");
                if (temp_ids != "|") {
                    node_ids = temp_nodes.split("|")[0];
                    node_names = temp_nodes.split("|")[1];
                }
                setIDsInDivList(0, cat_ids, cat_names, node_ids, node_names);
            }
        }

        function setIDsInDivList(regu_type, cat_ids, cat_names, node_ids, node_names) {
            if (regu_type == 0) {
                $("#data_cat").append('<li style="float:none;height:20px" cat_ids="' + cat_ids + '" node_ids="' + node_ids + '"><img onclick="deleteLi(this)" src="../../images/delete.png" width="15" height="15" alt=""/>&nbsp;' + cat_names + '&nbsp;&nbsp;&nbsp;&nbsp;' + node_names + '</li>');
            } else {
                $("#data_node").append('<li style="float:none;height:20px" cat_ids="' + cat_ids + '" node_ids="' + node_ids + '"><img onclick="deleteLi(this)" src="../../images/delete.png" width="15" height="15" alt=""/>&nbsp;' + node_names + '&nbsp;&nbsp;&nbsp;&nbsp;' + cat_names + '</li>');
            }
        }

        function deleteLi(obj) {
            $(obj).parent().remove();
        }

        function getLeafTreeNode(div_name) {
            var ids = "";
            var texts = "";
            $('#' + div_name + ' span.tree-file+span.tree-checkbox1').each(function (i) {
                if (i > 0) {
                    ids += ",";
                    texts += ",";
                }
                if (div_name == "leftMenuTree2_regu" && choose_type != "zwgk") {
                    var node = $('#' + div_name).tree('find', $(this).parent().attr("node-id"));
                    ids += node.attributes.t_node_id;
                }
                else
                    ids += $(this).parent().attr("node-id");
                texts += $(this).parent().text();
            });
            return ids + "|" + texts;
        }

        function getSourceRegulationList() {
            var l = CategoryRPC.getCatagoryReguList(cat_id, site_id);
            l = List.toJSList(l);
            if (l != null && l.size() > 0) {
                for (var i = 0; i < l.size(); i++)
                    setIDsInDivList(l.get(i).regu_type, l.get(i).cat_ids, l.get(i).cat_id_names, l.get(i).site_ids, l.get(i).site_id_names);
            }
        }
        //得到共享信息的站点
        function getAllowSharedSite() {
            $("#tsArea").addOptionsSingl("zwgk", "信息公开系统");
            //共享目录的树
            var sharedCategoryList = CategoryRPC.getCateClassListByApp(app_id);
            sharedCategoryList = List.toJSList(sharedCategoryList);
            for (var i = 0; i < sharedCategoryList.size(); i++) {
                if (sharedCategoryList.get(i).class_type == 1) {
                    $("#tsArea").addOptionsSingl(sharedCategoryList.get(i).class_id, sharedCategoryList.get(i).class_cname);
                }
            }
        }

        function changeCategoryId(val) {
            if (val == "") return;
            choose_type = val;
            if (val == "zwgk") {
                $("#leftMenuTree2_regu").empty();
                initMenuTree("leftMenuTree_regu", false);
                setAppointTreeJsonData("leftMenuTree_regu", node_json_data);
                initZWGKTree();
            }
            else {
                initMenuTree("leftMenuTree_regu", true);
                setAppointTreeJsonData("leftMenuTree_regu", eval(CategoryRPC.getCategoryTreeByClassID(val)));
                setAppointTreeJsonData("leftMenuTree2_regu", node_json_data);
            }
        }

        function initZWGKTree() {
            $('#leftMenuTree_regu').tree({
                onClick: function (node) {
                    if (node.iconCls == "icon-gknode") {
                        s_site_id = node.attributes.t_node_id;
                        try {
                            var jdata = eval(CategoryRPC.getCategoryTreeBySiteID(node.attributes.t_node_id));
                            jdata = getTreeObjFirstNode(jdata);
                            $("#leftMenuTree2_regu").empty();

                            setAppointTreeJsonData("leftMenuTree2_regu", jdata);
                        } catch (e) {
                        }
                        ;
                    }
                }
            });
        }

        //得到树中的第一个节点的所有子节点,为了不显示根节点
        function getTreeObjFirstNode(cat_jdata) {
            if (cat_jdata != null) {
                return cat_jdata[0].children;
            }
        }

        function initMenuTree(div_name, is_checkbox) {
            $('#' + div_name).tree({
                checkbox: is_checkbox
            });
        }

        //得到规则
        function returnCategoryReguList(c_id) {
            var reg_list = new List();
            $("#data_cat li").each(function () {
                var bean = BeanUtil.getCopy(CategoryGetRegu);
                bean.cat_id = c_id;
                bean.site_ids = $(this).attr("node_ids");
                bean.cat_ids = $(this).attr("cat_ids");
                bean.regu_type = 0;
                reg_list.add(bean);
            });

            $("#data_node li").each(function () {
                var bean = BeanUtil.getCopy(CategoryGetRegu);
                bean.cat_id = c_id;
                bean.site_ids = $(this).attr("node_ids");
                bean.cat_ids = $(this).attr("cat_ids");
                bean.regu_type = 1;
                reg_list.add(bean);
            });
            return reg_list;
        }
        /*******************************获取规则 结束**************************************/

        function closeTab() {
            parent.tab_colseOnclick(parent.curTabIndex);
        }

    </script>
</head>

<body>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" align="left">
    <tr>
        <td align="left" class="fromTabs width10" style="">

            <span class="blank3"></span>
        </td>
        <td align="left" width="100%">
            <ul class="fromTabs">
                <li class="list_tab list_tab_cur">
                    <div class="tab_left">
                        <div class="tab_right" onclick="changeLabShowList('listTable_0')">基本属性</div>
                    </div>
                </li>
                <li class="list_tab">
                    <div class="tab_left">
                        <div class="tab_right" onclick="changeLabShowList('listTable_1')">栏目权限</div>
                    </div>
                </li>
                <li class="list_tab">
                    <div class="tab_left">
                        <div class="tab_right" onclick="changeLabShowList('listTable_2')">共享设置</div>
                    </div>
                </li>
                <li class="list_tab">
                    <div class="tab_left">
                        <div class="tab_right" onclick="changeLabShowList('listTable_3')">接收设置</div>
                    </div>
                </li>
                <li class="list_tab">
                    <div class="tab_left">
                        <div class="tab_right" onclick="changeLabShowList('listTable_4')">同步属性</div>
                    </div>
                </li>
                <li class="list_tab" style="display:none">
                    <div class="tab_left">
                        <div class="tab_right" onclick="changeLabShowList('listTable_5')">获取规则</div>
                    </div>
                </li>
                <li class="list_tab">
                    <div class="tab_left">
                        <div class="tab_right" onclick="changeLabShowList('listTable_6')">推送规则</div>
                    </div>
                </li>
            </ul>
        </td>

    </tr>
</table>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
    <div class="infoListTable" id="listTable_0">
        <table id="category_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr class="hidden zt_tr">
                <th>专题分类：</th>
                <td>
                    <select id="zt_cat_id" name="zt_cat_id" class="width305">
                        <option value="0"></option>
                    </select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>目录名称：</th>
                <td>
                    <input id="cat_cname" name="cat_cname" type="text" class="width300" value=""
                           onblur="checkInputValue('cat_cname',false,80,'目录名称','')"/>
                    <input type="hidden" id="class_id" name="class_id" value="<%=class_id%>">
                    <input type="hidden" id="parent_id" name="parent_id" value="<%=parent_id%>">
                    <input type="hidden" id="site_id" name="site_id" value="<%=site_id%>">
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>英文名称：</th>
                <td>
                    <input id="cat_ename" name="cat_ename" type="text" class="width300"
                           onblur="checkInputValue('cat_ename',false,15,'英文名称','checkLower')"/>
                </td>
            </tr>
            <tr class="hidden">
                <th>类目编号：</th>
                <td>
                    <input id="cat_code" name="cat_code" type="text" class="width300"
                           onblur="checkInputValue('cat_code',true,20,'类目编号','')"/>
                </td>
            </tr>
            <tr>
                <th>工作流：</th>
                <td>
                    <select id="wf_id" name="wf_id" class="width305" onchange="changeWorkFlowHandl(this.value)">
                        <option value="0">无</option>
                    </select>
                </td>
            </tr>
            <tr id="wf_publish_tr" class="hidden">
                <th>终审后自动发布：</th>
                <td>
                    <input id="is_wf_publish" name="is_wf_publish" type="radio" value="1"/><label>是</label>
                    <input id="is_wf_publish" name="is_wf_publish" type="radio" value="0"
                           checked="true"/><label>否</label>
                </td>
            </tr>
            <tr>
                <th style="vertical-align:top;">内容模型：</th>
                <td>
                    <table border="0" cellpadding="0" cellspacing="0" align="left" id="model_select_table"
                           width="635px">
                        <td width="30px">选择</td>
                        <td width="100px" align="center">内容</td>
                        <td width="" align="center">内容页模板</td>
                        <td width="" align="center">是否添加</td>
                    </table>
                </td>
            </tr>
            <tr class="cms_page_templage">
                <th>栏目首页模板：</th>
                <td>
                    <input id="template_index_name" name="template_index_name" type="text" class="width200" value=""
                           readOnly="readOnly"/>
                    <input type="hidden" id="template_index" name="template_id_hidden" value="0"/><input type="button"
                                                                                                         value="选择"
                                                                                                         onclick="openTemplate('template_index','template_index_name')"/>&#160;<input
                        type="button" value="清空" onclick="clearTemplate(this)"/>
                </td>
            </tr>
            <tr>
                <th>列表页模板：</th>
                <td>
                    <input id="template_list_name" name="template_list_name" type="text" class="width200" value=""
                           readOnly="readOnly"/>
                    <input type="hidden" id="template_list" name="template_id_hidden" value="0"/><input type="button"
                                                                                                        value="选择"
                                                                                                        onclick="openTemplate('template_list','template_list_name')"/>&#160;<input
                        type="button" value="清空" onclick="clearTemplate(this)"/>
                </td>
            </tr>
            <tr class="cms_page_templage">
                <th>生成栏目首页：</th>
                <td>
                    <input id="is_generate_index" name="is_generate_index" type="radio" value="1"/><label>是</label>
                    <input id="is_generate_index" name="is_generate_index" type="radio" value="0"
                           checked="true"/><label>否</label>
                </td>
            </tr>
            <tr class="hidden">
                <th>允许用户投稿：</th>
                <td>
                    <input id="is_allow_submit" name="is_allow_submit" type="radio" value="1"/><label>是</label>
                    <input id="is_allow_submit" name="is_allow_submit" type="radio" value="0"
                           checked="true"/><label>否</label>
                </td>
            </tr>
            <tr class="hidden">
                <th>允许评论：</th>
                <td>
                    <input id="is_allow_comment" name="is_allow_comment" type="radio" value="1"/><label>是</label>
                    <input id="is_allow_comment" name="is_allow_comment" type="radio" value="0"
                           checked="true"/><label>否</label>
                </td>
            </tr>
            <tr class="hidden">
                <th>评论需要审核：</th>
                <td>
                    <input id="is_comment_checked" name="is_comment_checked" type="radio" value="1"/><label>是</label>
                    <input id="is_comment_checked" name="is_comment_checked" type="radio" value="0"
                           checked="true"/><label>否</label>
                </td>
            </tr>
            <tr class="hidden">
                <th>前台导航显示：</th>
                <td>
                    <input id="is_show" name="is_show" type="radio" value="1" checked="true"/><label>是</label>
                    <input id="is_show" name="is_show" type="radio" value="0"/><label>否</label>
                </td>
            </tr>
            <tr class="hidden">
                <th>前台栏目树显示：</th>
                <td>
                    <input id="is_show_tree" name="is_show_tree" type="radio" value="1" checked="true"/><label>是</label>
                    <input id="is_show_tree" name="is_show_tree" type="radio" value="0"/><label>否</label>
                </td>
            </tr>
            <tr class="hidden zt_tr zt_gd">
                <th>是否归档：</th>
                <td>
                    <input id="is_archive" name="is_archive" type="radio" value="1"/><label>是</label>
                    <input id="is_archive" name="is_archive" type="radio" value="0" checked="true"/><label>否</label>
                </td>
            </tr>
            <tr>
                <th>单页信息目录：</th>
                <td>
                    <input id="is_mutilpage" name="is_mutilpage" type="radio" value="1"/><label>是</label>
                    <input id="is_mutilpage" name="is_mutilpage" type="radio" value="0" checked="true"/><label>否</label>
                </td>
            </tr>
            <tr>
                <th>目录跳转地址：</th>
                <td>
                    <input id="jump_url" name="jump_url" type="text" class="width300"
                           onblur="checkInputValue('jump_url',true,200,'类目编号','')"/>
                </td>
            </tr>
            <tr class="hidden">
                <th>关联分类法类目：</th>
                <td>
                    <input id="cat_class_name" name="cat_class_name" type="text" class="width300" readOnly="readOnly"/>
                    <input id="cat_class_id" name="cat_class_id" type="hidden" value="0"/>
                    <input id="selectCateClassButton" name="btn1" type="button" onclick="" value="选择类目"/>
                </td>
            </tr>
            <tr>
                <th style="vertical-align:top;">关键词：</th>
                <td colspan="3">
                    <textarea id="cat_keywords" name="cat_keywords" style="width:585px;height:50px;"
                              onblur="checkInputValue('cat_keywords',true,8000,'关键词','')"></textarea>
                </td>
            </tr>
            <tr>
                <th style="vertical-align:top;">描述：</th>
                <td colspan="3">
                    <textarea id="cat_description" name="cat_description" style="width:585px;height:50px;"
                              onblur="checkInputValue('cat_description',true,8000,'描述','')"></textarea>
                </td>
            </tr>
            <tr>
                <th style="vertical-align:top;">备注：</th>
                <td colspan="3">
                    <textarea id="cat_memo" name="cat_memo" style="width:585px;height:50px;"
                              onblur="checkInputValue('cat_memo',true,8000,'备注','')"></textarea>
                </td>
            </tr>

            </tbody>
        </table>
    </div>

    <div class="infoListTable hidden" id="listTable_1">
        <table id="opt_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th></th>
                <td>
                    <table class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:530px"
                           align="left">
                        <tbody>
                        <tr>
                            <th style="text-align:left">可选用户：</th>
                            <th></th>
                            <th style="text-align:left">已选用户：</th>
                        </tr>
                        <tr>
                            <td>
                                <div style="width:250px;height:220px;overflow:auto;" class="border_color">
                                    <ul id="user_list" style="margin:10px">
                                    </ul>
                                </div>
                            </td>
                            <td>
                                >>
                            </td>
                            <td>
                                <div style="width:250px;height:220px;overflow:auto;" class="border_color">
                                    <ul id="t_user_list" style="margin:10px">
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th style="text-align:left">可选用户组：</th>
                            <th></th>
                            <th style="text-align:left">已选用户组：</th>
                        </tr>
                        <tr>
                            <td>
                                <div style="width:250px;height:220px;overflow:auto;" class="border_color">
                                    <ul id="group_list" style="margin:10px">
                                    </ul>
                                </div>
                            </td>
                            <td>
                                >>
                            </td>
                            <td>
                                <div style="width:250px;height:220px;overflow:auto;" class="border_color">
                                    <ul id="t_group_list" style="margin:10px">
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="infoListTable hidden" id="listTable_2">
        <table id="share_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>共享范围：</th>
                <td>
                    <input id="is_shared" name="is_shared" type="radio" value="1"
                           onclick="disabledSiteChekced(true,'share_table')"/><label>全部共享</label>
                    <input id="is_shared" name="is_shared" type="radio" value="0" checked="true"
                           onclick="disabledSiteChekced(false,'share_table')"/><label>限制共享</label>
                </td>
            </tr>
            <tr>
                <th></th>
                <td>
                    <table class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:530px"
                           align="left">
                        <tbody>
                        <tr>
                            <th style="text-align:left">可选站点：</th>
                            <th></th>
                            <th style="text-align:left">已选站点：</th>
                        </tr>
                        <tr>
                            <td>
                                <div style="width:250px;height:260px;overflow:auto;" class="border_color">
                                    <ul id="site_list" style="margin:10px" class="site_list">
                                    </ul>
                                </div>
                            </td>
                            <td>
                                >>
                            </td>
                            <td>
                                <div style="width:250px;height:260px;overflow:auto;" class="border_color">
                                    <ul id="t_site_list" style="margin:10px" class="t_site_list">
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="infoListTable hidden" id="listTable_3">
        <table id="receive_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>接收范围：</th>
                <td>
                    <input id="is_shared_1" name="is_shared_1" type="radio" value="1"
                           onclick="disabledSiteChekced(true,'receive_table')"/><label>全部接收</label>
                    <input id="is_shared_1" name="is_shared_1" type="radio" value="0" checked="true"
                           onclick="disabledSiteChekced(false,'receive_table')"/><label>限制接收</label>
                </td>
            </tr>
            <tr>
                <th></th>
                <td>
                    <table class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:530px"
                           align="left">
                        <tbody>
                        <tr>
                            <th style="text-align:left">可选站点：</th>
                            <th></th>
                            <th style="text-align:left">已选站点：</th>
                        </tr>
                        <tr>
                            <td>
                                <div style="width:250px;height:260px;overflow:auto;" class="border_color">
                                    <ul id="site_list" style="margin:10px" class="site_list">
                                    </ul>
                                </div>
                            </td>
                            <td>
                                >>
                            </td>
                            <td>
                                <div style="width:250px;height:260px;overflow:auto;" class="border_color">
                                    <ul id="t_site_list" style="margin:10px" class="t_site_list">
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="infoListTable hidden" id="listTable_4">
        <table id="sync_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>选择站点：</th>
                <td>
                    <select id="shared_site_list" class="width300" onchange="getSharedCategoryBySite(this.value)">
                        <option value="">当前站点</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th></th>
                <td>
                    <table class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:530px"
                           align="left">
                        <tbody>
                        <tr>
                            <th style="text-align:left">可选栏目：</th>
                            <th></th>
                            <th style="text-align:left">已选栏目：</th>
                        </tr>
                        <tr>
                            <td>
                                <div id="leftMenuBox" style="width:250px;height:260px;overflow:auto;"
                                     class="border_color">
                                    <div id="leftMenu" class="contentBox6 textLeft no_zhehang" style="overflow:auto">
                                        <ul id="leftMenuTree" class="easyui-tree no_zhehang" animate="true">
                                        </ul>
                                    </div>
                                </div>
                            </td>
                            <td>
                                >>
                            </td>
                            <td>
                                <div style="width:360px;height:260px;overflow:auto;" class="border_color">
                                    <ul id="cat_list" style="margin:10px">
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr class="hidden">
                <th>同步方式：</th>
                <td>
                    <input id="cite_type" name="cite_type" type="radio" value="0"/><label>克隆</label>
                    <input id="cite_type" name="cite_type" type="radio" value="2" checked="true"/><label>链接</label>
                </td>
            </tr>
            <tr>
                <th>同步结果：</th>
                <td>
                    <input id="is_auto_publish" name="is_auto_publish" type="checkbox" value="1"/><label>直接发布</label>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="infoListTable hidden" id="listTable_5">
        <table id="table_id" width="100%" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th style="width:80px">获取范围：</th>
                <td width="334px">
                    <select class="width150" name="tsArea" id="tsArea" onchange="changeCategoryId(this.value)">
                        <option value=""></option>
                    </select>
                </td>
                <td>公开节点目录</td>
            </tr>
            </tbody>
        </table>
        <table class="" border="0" cellpadding="2" cellspacing="0" align="left">
            <tr>
                <td style="width:176px;padding-left:22px" valign="top" rowspan="2">
                    <div id="cat_tree_div1" class="select_div border_color"
                         style="width:176px; height:410px; overflow:scroll;border:1px #7f9db9 solid;">
                        <div id="leftMenuBox">
                            <div id="leftMenu" class="contentBox6 textLeft" style="height:300px;">
                                <ul id="leftMenuTree_regu" class="easyui-tree" animate="true"
                                    style="width:176px;overflow:hidden">
                                </ul>
                                <span class="blank3"></span>
                            </div>
                        </div>
                    </div>
                </td>
                <td id="tree_td_2" style="width:176px;" valign="top" rowspan="2">
                    <div id="cat_tree_div2" class="select_div border_color"
                         style="width:176px; height:410px; overflow:scroll;border:1px #7f9db9 solid;">
                        <div id="leftMenuBox">
                            <div id="leftMenu2" class="contentBox6 textLeft" style="height:300px;">
                                <ul id="leftMenuTree2_regu" class="easyui-tree" animate="true"
                                    style="width:176px;overflow:hidden">
                                </ul>
                                <span class="blank3"></span>
                            </div>
                        </div>
                    </div>
                </td>
                <td>
                    <input type="button" value="添加" class="btn1" onclick="choose_regu()"/>
                </td>
                <td valign="top">
                    <div id="scroll_div" style="width:397px;height:194px;overflow:auto;background:#ffffff"
                         class="border_color">
                        <ul id="data_node" class="txt_list" style="width:1000px;">

                        </ul>
                    </div>
                    <br/>
                    <span style="color:#32609E">共享目录</span>
                    <div id="scroll_div"
                         style="width:397px;height:180px;overflow:auto;background:#ffffff;padding-top:6px"
                         class="border_color">
                        <ul id="data_cat" class="txt_list" style="width:1000px;">

                        </ul>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div class="infoListTable hidden" id="listTable_6">
        <table id="sync_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>选择站点：</th>
                <td>
                    <select id="toInfo_site_list" class="width300"
                            onchange="getToInfoCategoryBySite(this.value)"></select>
                </td>
            </tr>
            <tr>
                <th></th>
                <td>
                    <table class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:530px"
                           align="left">
                        <tbody>
                        <tr>
                            <th style="text-align:left">可选栏目：</th>
                            <th></th>
                            <th style="text-align:left">已选栏目：</th>
                        </tr>
                        <tr>
                            <td>
                                <div id="leftMenuBox" style="width:250px;height:260px;overflow:auto;"
                                     class="border_color">
                                    <div id="leftMenu" class="contentBox6 textLeft no_zhehang" style="overflow:auto">
                                        <ul id="leftMenuTree_toInfo" class="easyui-tree no_zhehang" animate="true">
                                        </ul>
                                    </div>
                                </div>
                            </td>
                            <td>
                                >>
                            </td>
                            <td>
                                <div style="width:360px;height:260px;overflow:auto;" class="border_color">
                                    <ul id="toInfo_cat_list" style="margin:10px">
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <span class="blank12"></span>
    <div class="line2h"></div>
    <span class="blank3"></span>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle" style="text-indent:100px;">
                <input id="addButton" name="btn1" type="button" onclick="" value="保存"/>
                <input id="userAddReset" name="btn1" type="button" onclick="window.location.reload()" value="重置"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="closeTab()"
                       value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</form>
</body>
</html>
