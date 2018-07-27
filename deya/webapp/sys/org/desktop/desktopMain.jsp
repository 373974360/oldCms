<%@ page contentType="text/html; charset=utf-8" %>
<%
    String sq_id = request.getParameter("sq_id");
    String top_index = request.getParameter("top_index");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>桌面设置</title>


    <link type="text/css" rel="stylesheet" href="../../styles/sq.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css"/>

    <script type="text/javascript">
        var UserManRPC = jsonrpc.UserManRPC;
        var user_id = top.LoginUserBean.user_id;
        var apps = UserManRPC.getAppForLicense();
        $(document).ready(function () {
            initButtomStyle();

            if (apps.indexOf("appeal") > -1) {
                iniSQbox();
                $("#sq_box").show();
            }
            initSiteListHandel("cms", "cms_table");

            init_input();

            selectChecked();
        });

        //选中已选过的
        function selectChecked() {
            var l = UserManRPC.getUserDesktop(user_id);

            if (l != null) {
                l = List.toJSList(l);
                for (var i = 0; i < l.size(); i++) {
                    $("#" + l.get(i).app_type + "_table input[value='" + l.get(i).k_v + "']").attr("checked", "true");
                }
            }
        }

        function iniSQbox() {
            $(".sq_title_box").bind('click', function () {
                if ($(this).find(".sq_title_right").text() == "点击闭合") {
                    $(this).find(".sq_title").removeClass("sq_title_minus").addClass("sq_title_plus");
                    $(this).find(".sq_title_right").text("点击展开");
                    $(this).parent().find(".sq_box_content").hide(300);
                }
                else {
                    $(this).find(".sq_title").removeClass("sq_title_plus").addClass("sq_title_minus");
                    $(this).find(".sq_title_right").text("点击闭合");
                    $(this).parent().find(".sq_box_content").show(300);
                }
            })
        }

        //得到当前用户有权限管理的站点
        function initSiteListHandel(app_name, table_name) {
            var list = UserManRPC.getAllUserSiteObjList(user_id, app_name);
            if (list != null) {
                list = List.toJSList(list);
                var str = "<tr><td><ul>";
                for (var i = 0; i < list.size(); i++) {
                    if (i > 0 && i % 3 == 0) {
                        str += '</ul></td></tr><tr><td><ul>';
                    }
                    if (app_name == "cms")
                        str += '<li><input type="checkbox" id="' + app_name + '_checkbox" value="' + list.get(i).site_id + '"><label>' + list.get(i).site_name + '</label></li>';
                    if (app_name == "zwgk")
                        str += '<li><input type="checkbox" id="' + app_name + '_checkbox" value="' + list.get(i).node_id + '"><label>' + list.get(i).node_name + '</label></li>';
                }

                if (str.substring(str.length - 5) == "</li>")
                    str += '</ul></td></tr>';
                str += '<tr><td><span class="pointer" onclick="selectAll(\'' + table_name + '\')">全选</span>&nbsp;|&nbsp;<span class="pointer" onclick="concelAll(\'' + table_name + '\')">反选</span></td></tr>';
                $("#" + app_name + "_table tbody").append(str);
            }
        }

        function selectAll(table_name) {
            $("#" + table_name + " :checkbox").check("on");
        }

        function concelAll(table_name) {
            $("#" + table_name + " :checkbox").check("fan");
        }

        //保存设置
        function saveDesktop() {
            var DeskTopBean = new Bean("com.deya.wcm.bean.org.desktop.DeskTopBean", true);
            var desk_list = new List();

            $("#sq_table :checked").each(function () {
                var bean = BeanUtil.getCopy(DeskTopBean);
                bean.user_id = user_id;
                bean.app_type = "sq";
                bean.k_v = $(this).val();
                desk_list.add(bean);
            });

            $("#cms_table :checked").each(function () {
                var bean = BeanUtil.getCopy(DeskTopBean);
                bean.user_id = user_id;
                bean.app_type = "cms";
                bean.k_v = $(this).val();
                desk_list.add(bean);
            });

            $("#ysqgk_table :checked").each(function () {
                var bean = BeanUtil.getCopy(DeskTopBean);
                bean.user_id = user_id;
                bean.app_type = "ysqgk";
                bean.k_v = $(this).val();
                desk_list.add(bean);
            });

            if (UserManRPC.insertUserDesktop(user_id, desk_list)) {
                top.msgAlert(WCMLang.Set_success);
            }
            else {
                top.msgWargin(WCMLang.Set_fail);
            }
        }
    </script>
    <style>
        #desktop_div li {
            width: 150px
        }

    </style>
</head>

<body>
<div id="desktop_div">
    <span class="blank6"></span>
    <!--来信人信息-->
    <div class="sq_box hidden" id="sq_box">
        <div class="sq_title_box">
            <div class="sq_title sq_title_minus">诉求管理</div>
            <div class="sq_title_right">点击闭合</div>
        </div>
        <div class="sq_box_content">
            <table id="sq_table" class="table_view" border="0" cellpadding="0" cellspacing="0">
                <tbody>
                <tr>
                    <td>
                        <ul>
                            <li><input type="checkbox" value="0"><label>首接件</label></li>
                            <li><input type="checkbox" value="1"><label>转办件</label></li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>
                        <ul>
                            <li><input type="checkbox" value="2"><label>待审件</label></li>
                            <li><input type="checkbox" value="3"><label>延期待审</label></li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td><span class="pointer" onclick="selectAll('sq_table')">全选</span>&nbsp;|&nbsp;<span
                            class="pointer" onclick="concelAll('sq_table')">反选</span></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>


    <span class="blank6"></span>
    <!--信件信息-->
    <div class="sq_box">
        <div class="sq_title_box">
            <div class="sq_title sq_title_minus">网站待审信息</div>
            <div class="sq_title_right">点击闭合</div>
        </div>
        <div class="sq_box_content">
            <table id="cms_table" class="table_view" border="0" cellpadding="0" cellspacing="0">
                <tbody>

                </tbody>
            </table>
        </div>
    </div>

    <span class="blank6"></span>
    <!--相关信件-->
    <div class="sq_box" id="zwgk_box">
        <div class="sq_title_box">
            <div class="sq_title sq_title_minus">依申请公开</div>
            <div class="sq_title_right">点击闭合</div>
        </div>
        <div class="sq_box_content">
            <table id="ysqgk_table" class="table_view" border="0" cellpadding="0" cellspacing="0">
                <tbody id="xg_sq_list">
                <tr>
                    <td>
                        <ul>
                            <li><input type="checkbox" value="0" class="input_checkbox"><label>依申请公开</label></li>
                        </ul>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td align="left" valign="middle" style="text-indent:100px;">
            <input id="userAddButton" name="btn1" type="button" onclick="saveDesktop()" value="保存"/>
            <input id="userAddReset" name="btn1" type="button" onclick="window.location.reload()" value="重置"/>
        </td>
    </tr>
</table>
<span class="blank3"></span>
</body>
</html>