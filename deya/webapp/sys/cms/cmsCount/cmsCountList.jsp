<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.deya.util.DateUtil" %>
<%@page import="java.util.Date" %>
<%
    String site_id = request.getParameter("site_id");
    String app_id = request.getParameter("app_id");
    String cat_id = request.getParameter("cat_id");
    if (cat_id == null || "".equals(cat_id) || "null".equals(cat_id))
        cat_id = "0";
    if (site_id == null || "".equals(site_id) || "null".equals(site_id))
        site_id = "";
    String start_day = request.getParameter("start_day");
    String end_day = request.getParameter("end_day");

    String now = DateUtil.getCurrentDate();
    String now1 = now.substring(0, 7) + "-01";
    if (start_day == null || "".equals(start_day)) {
        start_day = now1;
    }
    if (end_day == null || "".equals(end_day)) {
        end_day = now;
    }
    int cur_year = DateUtil.getYear(new Date());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>目录管理</title>


    <link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
    <style type="text/css">
        .checkBox_mid {
            vertical-align: middle
        }

        ;
        #v_type {
            height: 50px;
        }
    </style>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
    <script type="text/javascript" src="../../js/indexjs/tools.js"></script>
    <script type="text/javascript" src="js/cmsCountList.js"></script>
    <script type="text/javascript" src="/sys/js/open-flash-chart/js/json2.js"></script>
    <script type="text/javascript" src="/sys/js/open-flash-chart/js/swfobject.js"></script>
    <script type="text/javascript">
        var cat_type = "0";
        var site_id = "CMScqgjj";
        var app_id = "<%=app_id%>";//应用ID
        var cat_id = "<%=cat_id%>";
        //var p_id = "0";
        var class_id = 0;
        var jsonData;
        var chold_jData;
        var zt_jdata;

        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");
            //得到权限信息

            // showCategoryTree();
            setSearchInfo();
            // initCategoryTree();
            // setLeftTreeHeight();
        });

        function showCategoryTree() {
            var new_jdata = "";

            if (cat_type == "0") {
                json_data = CategoryRPC.getCategoryTreeBySiteID(site_id);
            } else {
                json_data = CategoryRPC.getCategoryTreeByCategoryID(cat_id, site_id);
            }

            zt_jdata = "{\"id\":-1,\"iconCls\":\"icon-category\",\"text\":\"专题栏目\",\"attributes\":{\"url\":\"\",\"handl\":\"\"},\"children\":" + jsonrpc.CategoryRPC.getZTCategoryTreeJsonStr(site_id) + "}";

            if (zt_jdata != "" && zt_jdata != null) {
                json_data = json_data.substring(1, json_data.length - 1);
                zt_jdata = zt_jdata.substring(0, zt_jdata.length);
                new_jdata = eval("[" + json_data + "," + zt_jdata + "]");
            } else {
                new_jdata = eval(json_data);
            }
            setLeftMenuTreeJsonData(new_jdata);
            treeNodeSelected(cat_id);
        }

        function setCountType() {
            if ($("#count_type").val() == "1") {
                window.location.href = "/sys/cms/cmsCount/cmsCountListCate.jsp?site_id=" + site_id;
            }
        }
    </script>
</head>
<body>
<table style="width:100%" border="0" cellpadding="0" cellspacing="0">
    <tr style="line-height: 30px">
        <td colspan="3" class="search_td fromTabs">
            <div style="height:auto; border:#A00 0px;">
                <div id="defauleTime" style="float:left;">
                    统计方式:
                    <select id="count_type" onchange="setCountType()">
                        <option value="0">默认</option>
                        <option value="1">按栏目统计</option>
                    </select>
                </div>
            </div>
        </td>
    </tr>
    <tr style="line-height: 30px">
        <td colspan="3" class="search_td fromTabs">
            <div style="height:auto; border:#A00 0px;">
                <div style="float:left;">
                    <span class="f_red">*</span>时间范围:
                    <select id="time_year" onchange="setViewType(this.value)">
                        <%
                            for (int i = 5; i > 0; i--) {
                        %>
                        <option value="<%=cur_year+i%>"><%=cur_year + i%>
                        </option>
                        <%
                            }
                        %>
                        <option value="<%=cur_year%>" selected="selected"><%=cur_year%>
                        </option>
                        <%
                            for (int j = 1; j <= 5; j++) {
                        %>
                        <option value="<%=cur_year-j%>"><%=cur_year - j%>
                        </option>
                        <%
                            }
                        %>
                    </select>年
                    <select id="time_month" onchange="setViewType(this.value)">
                        <option value="0" selected="selected">全部</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                    </select>月
                </div>
            </div>
        </td>
    </tr>
    <tr style="line-height: 30px">
        <td colspan="3" class="search_td fromTabs">
            <div style="height:auto; border:#A00 0px;">
                <div style="float:left;">
                    <input id="isInput" type="checkbox" onclick="showTimeInput()" class="checkBox_mid"/>&nbsp;输入时间段
                    <input class="" type="text" name="start_day" id="start_day" size="11"
                           style="height:16px;line-height:16px;"
                           value="<%=start_day%>"
                           onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true"
                           disabled="disabled" onchange="setSearchInfo()"/>
                    --
                    <input class="" type="text" name="end_day" id="end_day" size="11"
                           style="height:16px;line-height:16px;"
                           value="<%=end_day%>"
                           onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true"
                           disabled="disabled" onchange="setSearchInfo()"/>

                    <input type="button" id="searchBtn" onclick="searchBtn()" value="统计"/>
                    <input type="button" id="outFileBtn" style="display:none" onclick="outFileBtn()" value="导出"/>
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <%--<td width="200px" valign="top">
            <div id="leftMenuBox">
                <div id="leftMenu" class="contentBox6 textLeft width200" style="overflow:auto">
                    <ul id="leftMenuTree" class="easyui-tree" animate="true">
                    </ul>
                </div>
            </div>
        </td>
        <td class="width10"></td>--%>
        <td valign="top">
            <table width="100%">
                <tr valign="top">
                    <td>
                        <div style="border:0px red solid">
                            <table width="100%">
                                <tbody>
                                <tr class="hidden">
                                    <td width="60px">查看方式:</td>
                                    <td>
                                        <input id="bycat" type="radio" name="viewClass" checked="checked" value="bycat">按栏目
                                        <input id="byday" type="radio" name="viewClass" value="byday"
                                               disabled="disabled">按日
                                        <input id="bymonth" type="radio" name="viewClass" value="bymonth">按月
                                    </td>
                                </tr>

                                <tr>
                                    <td width="60px">时间范围:</td>
                                    <td><span id='s_day'></span>至<span id='e_day'></span></td>
                                </tr>

                                <tr>
                                    <td>统计情况:</td>
                                    <td>全部信息:(<span id="allInfo">0</span>)&nbsp;主信息:(<span id="hostInfo">0</span>)&nbsp;引用信息:(<span
                                            id="refInfo">0</span>)&nbsp;链接信息:(<span id="linkInfo">0</span>)
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr valign="top">
                    <td>
                        <div id="chart">

                        </div>
                    </td>
                </tr>
                <tr valign="top">
                    <td>
                        <div id="count">
                            <table id="countList" class="treeTableCSS table_border" border="0" cellpadding="0"
                                   cellspacing="0">
                            </table>
                        </div>
                    </td>
                </tr>
                <tfoot>
                <tr>
                    <td colspan="3"></td>
                    <td></td>
                </tr>
                </tfoot>
            </table>
        </td>
    </tr>
</table>

</body>
</html>
