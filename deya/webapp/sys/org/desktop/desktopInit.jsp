<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.deya.wcm.bean.appeal.sq.SQBean,com.deya.wcm.bean.cms.info.InfoBean,com.deya.wcm.bean.org.desktop.DeskTopBean" %>
<%@page import="com.deya.wcm.services.appeal.sq.SQManager,com.deya.wcm.services.cms.info.InfoBaseRPC,com.deya.wcm.services.control.site.SiteRPC" %>
<%@page import="com.deya.wcm.services.org.user.UserLogin,com.deya.wcm.services.org.user.UserManRPC,com.deya.wcm.services.zwgk.node.GKNodeRPC" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.deya.wcm.services.zwgk.ysqgk.YsqgkInfoManager" %>
<%@ page import="com.deya.wcm.bean.zwgk.ysqgk.YsqgkListBean" %>
<%@ page import="static com.deya.wcm.services.search.util.tongyinci.InitCiku.list" %>
<%@ page import="com.deya.project.dz_jyhgl.InfoUpdateResultBean" %>
<%@ page import="com.deya.project.dz_jyhgl.InfoUpdateResultManager" %>
<%
    int user_id = UserLogin.getUserBySession(request).getUser_id();
    List<DeskTopBean> desk_list = UserManRPC.getUserDesktop(user_id);
    Map<String, String> sq_con_map = new HashMap<String, String>();
    String str = "";
    if (desk_list != null && desk_list.size() > 0) {
        for (DeskTopBean det : desk_list) {
            sq_con_map.clear();
            sq_con_map.put("user_id", user_id + "");
            sq_con_map.put("info_status", "2");
            sq_con_map.put("final_status", "0");
            sq_con_map.put("start_num", "0");
            sq_con_map.put("page_size", "5");
            if ("sq".equals(det.getApp_type())) {
                sq_con_map.put("sort_name", "sq_id");
                sq_con_map.put("sort_type", "desc");

                sq_con_map.remove("sq_status");
                sq_con_map.remove("sq_flag");
                sq_con_map.remove("prove_type");
                sq_con_map.remove("is_back");
                sq_con_map.remove("page_type");
                sq_con_map.remove("limit_flag");

                String title = "";
                String url = "";
                switch (Integer.parseInt(det.getK_v())) {
                    case 0:
                        title = "首接件";
                        url = "/sys/appeal/sq/sqList.jsp?sq_flag=0&prove_type=1&sq_status=0,1&is_back=0";
                        sq_con_map.put("sq_status", "0,1");//流程处理状态
                        sq_con_map.put("sq_flag", "0");//信件标识
                        sq_con_map.put("prove_type", "1");//信件原始办理类型
                        sq_con_map.put("is_back", "0");//回退标识;
                        break;
                    case 1:
                        title = "转办件";
                        url = "/sys/appeal/sq/sqList.jsp?sq_flag=0&prove_type=2,3,4,5&sq_status=0,1&is_back=0";
                        sq_con_map.put("sq_status", "0,1");//流程处理状态
                        sq_con_map.put("sq_flag", "0");//信件标识
                        sq_con_map.put("prove_type", "2,3,4,5");//信件原始办理类型
                        sq_con_map.put("is_back", "0");//回退标识
                        break;
                    case 2:
                        url = "/sys/appeal/sq/sqList.jsp?sq_status=2&page_type=dsj";
                        title = "待审件";
                        sq_con_map.put("sq_status", "2");//流程处理状态
                        sq_con_map.put("page_type", "dsj");//信件标识
                        break;
                    case 3:
                        url = "/sys/appeal/sq/sqList.jsp?sq_status=0,1&limit_flag=2&is_back=0&page_type=dsj";
                        title = "延期待审";
                        sq_con_map.put("sq_status", "0,1");//流程处理状态
                        sq_con_map.put("page_type", "dsj");//信件标识
                        sq_con_map.put("is_back", "0");//回退标识
                        sq_con_map.put("limit_flag", "2");//延时申请标识
                        break;
                }
                List<SQBean> sq_list = SQManager.getSqList(sq_con_map, user_id);
                String count = SQManager.getSqCount(sq_con_map, user_id);

                str += "<div class=\"sq_box new_sq_box\" ><div class=\"sq_title_box\" ><div class=\"sq_title2\">" + title + "</div>";
                str += "<div class=\"sq_title_right2\">总数：" + count + "条　<span class=\"cursor\"><span class=\"cursor\" onclick=\"openSQList('" + url + "','" + title + "')\">更多</span></span></div>";
                str += "</div>";
                str += "<div class=\"sq_box_content\">";
                str += "<table id=\"sq_table\" class=\"table_view_desk\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ><tbody>";

                if (sq_list != null) {
                    for (SQBean sq : sq_list) {
                        String d_time = sq.getSq_dtime();
                        str += "<tr><td width=\"260px\"><a href=\"javascript:openSQPage(" + sq.getSq_id() + "," + det.getK_v() + ")\" title=\"" + sq.getSq_title2() + "\">" + splitString(sq.getSq_title2(), 25) + "</a></td>";
                        str += "<td>" + d_time.substring(5, d_time.length() - 3) + "</td></tr>";

                    }
                }
                str += "</tbody></table></div></div>";
            }
            if ("cms".equals(det.getApp_type())) {

                sq_con_map.put("sort_name", "ci.info_id");
                sq_con_map.put("sort_type", "desc");
                sq_con_map.put("site_id", det.getK_v());
                sq_con_map.put("app_id", "cms");
                String opt_ids = UserLogin.getOptIDSByUserAPPSite(user_id+"", "cms", det.getK_v());
                if(opt_ids.indexOf("531") > -1){
                    sq_con_map.put("dept_id",UserLogin.getUserBySession(request).getDept_id()+"");
                }
                Map<String, Object> return_map = InfoBaseRPC.getWaitVerifyInfoList(sq_con_map);
                String info_count = return_map.get("info_count") + "";
                List<InfoBean> list = (List<InfoBean>) return_map.get("info_List");
                str += "<div class=\"sq_box new_sq_box\" ><div class=\"sq_title_box\" ><div class=\"sq_title2\">" + SiteRPC.getSiteBeanBySiteID(det.getK_v()).getSite_name() + "</div>";
                str += "<div class=\"sq_title_right2\">待审总数：" + info_count + "条　<span class=\"cursor\" onclick=\"openInfoList('cms','" + det.getK_v() + "')\">更多</span></div>";
                str += "</div>";
                str += "<div class=\"sq_box_content\">";
                str += "<table id=\"sq_table\" class=\"table_view_desk\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ><tbody>";
                if (list != null) {
                    for (InfoBean ib : list) {
                        String d_time = ib.getOpt_dtime();
                        str += "<tr><td width=\"260px\"><a href=\"javascript:openViewPage(" + ib.getInfo_id() + ",'" + ib.getSite_id() + "')\" title=\"" + ib.getTitle() + "\">" + splitString(ib.getTitle(), 20) + "</a></td>";
                        str += "<td>" + d_time.substring(5, d_time.length() - 3) + "</td></tr>";
                    }
                }
                str += "</tbody></table></div></div>";
            }
            if ("ysqgk".equals(det.getApp_type())) {
                sq_con_map.put("sort_name", "ysq_id");
                sq_con_map.put("sort_type", "desc");
                sq_con_map.put("do_state", "0");
                sq_con_map.put("final_status", "0");
                List<YsqgkListBean> result = YsqgkInfoManager.getYsqgkLists(sq_con_map);

                String info_count = "0";

                if(result != null && result.size() != 0){
                    info_count = String.valueOf(result.size());
                }

                str += "<div class=\"sq_box new_sq_box\" ><div class=\"sq_title_box\" ><div class=\"sq_title2\">依申请公开</div>";
                str += "<div class=\"sq_title_right2\">总数：" + info_count + "条　<span class=\"cursor\" onclick=\"openYsqgkList()\">更多</span></div>";
                str += "</div>";
                str += "<div class=\"sq_box_content\">";
                str += "<table id=\"sq_table\" class=\"table_view_desk\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ><tbody>";

                if (result != null) {
                    for (YsqgkListBean ysqBean : result) {
                        String d_time = ysqBean.getPut_dtime();
                        str += "<tr><td width=\"260px\">" + splitString(ysqBean.getName(), 20) + "</a></td>";
                        str += "<td>" + d_time.substring(5, d_time.length() - 3) + "</td></tr>";
                    }
                }
                str += "</tbody></table></div></div>";
            }
            if ("info_update".equals(det.getApp_type())) {
                sq_con_map.put("site_id", det.getK_v());
                sq_con_map.put("update_desc", "不合格");
                List<InfoUpdateResultBean> result = InfoUpdateResultManager.getInfoUpdateResultList(sq_con_map);
                str += "<div class=\"sq_box new_sq_box\" ><div class=\"sq_title_box\" ><div class=\"sq_title2\">栏目脱更提醒</div>";
                str += "<div class=\"sq_title_right2\"></div>";
                str += "</div>";
                str += "<div class=\"sq_box_content\">";
                str += "<table id=\"sq_table\" class=\"table_view_desk\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ><tbody>";
                if (result != null) {
                    str += "<tr><td width=\"260px\">栏目名称</td>";
                    str += "<td>监测周期</td>";
                    str += "<td>开始时间</td>";
                    str += "<td>截止时间</td>";
                    str += "<td>应更新数量</td>";
                    str += "<td>实际更新数量</td>";
                    str += "<td>是否合格</td></tr>";
                    for (InfoUpdateResultBean bean : result) {
                        str += "<tr><td width=\"260px\">" + bean.getCat_name() + "</td>";
                        str += "<td>" + bean.getGz_day() + "</td>";
                        str += "<td>" + bean.getEnd_update_time() + "</td>";
                        str += "<td>" + bean.getCheck_time() + "</td>";
                        str += "<td>" + bean.getGz_count() + "</td>";
                        str += "<td>" + bean.getUpdate_count() + "</td>";
                        str += "<td>" + bean.getUpdate_desc() + "</td></tr>";
                    }
                }
                str += "</tbody></table></div></div>";
            }
        }
    }
%>
<%!
    public static String splitString(String s, int num) {
        if (s.length() > num)
            return s.substring(0, num);
        else
            return s;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>我的桌面</title>


    <link type="text/css" rel="stylesheet" href="../../styles/sq.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css"/>
    <style>
        .sq_title2 {
            width: 170px;
            height: 22px;
            float: left;
            font-weight: bold;
            color: #666666;
            line-height: 22px;
        }

        .sq_title_right2 {
            width: 160px;
            height: 22px;
            line-height: 22px;
            float: right;
            font-weight: bold;
            color: #666666;
            text-align: right;
            padding-right: 10px;
        }

        .new_sq_box {
            width: 45%;
            float: left;
            margin-right: 9px;
            margin-bottom: 9px
        }

        .table_view_desk {
            margin: 0;
            border-collapse: collapse;
        }

        .table_view_desk td {
            text-align: left;
            color: #32609E;
            border-collapse: collapse;
            vertical-align: middle;
            vertical-align: middle;
            padding: 4px 4px;
        }
    </style>
    <script type="text/javascript">
        var SiteRPC = jsonrpc.SiteRPC;
        var SQRPC = jsonrpc.SQRPC;
        var UserManRPC = jsonrpc.UserManRPC;
        var InfoBaseRPC = jsonrpc.InfoBaseRPC;
        var GKNodeRPC = jsonrpc.GKNodeRPC;

        var user_id = top.LoginUserBean.user_id;

        $(document).ready(function () {
            initButtomStyle();
            init_input();

        });


        function openSQList(url, title) {
            top.addTab(true, url, title);
        }

        function openSQPage(sq_id) {
            top.addTab(true, "/sys/appeal/sq/sq_add.jsp?sq_id=" + sq_id + "&top_index=" + top.curTabIndex, "信件管理");
        }

        function openInfoList(app_id, site_id) {
            top.addTab(true, "/sys/cms/info/article/verifyInfoList.jsp?app_id=" + app_id + "&site_id=" + site_id, "待审信息");
        }

        function openViewPage(i_id, site_id) {
            top.addTab(true, "/sys/cms/info/article/infoView.jsp?info_id=" + i_id + "&site_id=" + site_id + "&topnum=" + top.curTabIndex, "查看信息");
        }
        function openYsqgkList(){
            top.addTab(true, "/sys/zwgk/ysqgk/operate/ysqgk_allLists.jsp?app_id=zwgk&topnum=" + top.curTabIndex, "依申请公开信息");
        }
    </script>
</head>

<body>
<div id="desktop_div">
    <span class="blank6"></span>
    <%=str%>
</div>
<span class="blank3"></span>
</body>
</html>
