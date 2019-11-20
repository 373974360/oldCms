<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@ page import="com.deya.wcm.services.control.domain.SiteDomainManager" %>
<%
    String site_id = request.getParameter("site_id");
    String site_doman = SiteDomainManager.getSiteDomainBySiteID(site_id);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>查看统计详细信息</title>


    <link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
    <style type="text/css">
        .checkBox_mid{ vertical-align:middle; margin-right:10px;};
        #v_type{height:50px;}
        .checkBox_text{ vertical-align:text-top}
        .span_left{ margin-left:14px;}
    </style>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
    <script type="text/javascript" src="../../js/indexjs/tools.js"></script>
    <script type="text/javascript">
        var CmsCountRPC = jsonrpc.CmsCountRPC;

        var mp = new Map();
        var beanList = null;

        var site_id = "<%=site_id%>";
        var cat_id = <%=request.getParameter("cat_id")%>;
        var start_day = "<%=request.getParameter("start_day")%>";
        var end_day = "<%=request.getParameter("end_day")%>";
        var input_user = <%=request.getParameter("input_user")%>;
        var is_host = "<%=request.getParameter("is_host")%>";

        $(document).ready(function(){
            initButtomStyle();
            init_FromTabsStyle();
            if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
            //得到父页面中设置的参数
            showList();
        });

        // 显示统计结果列表
        function showList()
        {
            mp.put("start_day",start_day+" 00:00:00");
            mp.put("end_day",end_day +" 23:59:59");
            mp.put("site_id",site_id);
            mp.put("cat_id",cat_id);
            mp.put("input_user",input_user);
            mp.put("is_host",is_host);

            beanList = CmsCountRPC.getInfoListByUserIDTimeType(mp);
            beanList = List.toJSList(beanList);
            createTable();
        }
        // 生成内容表格
        function createTable()
        {
            $("#treeTableCount").empty();
            var treeHtmls = "<thead>" +
                "<tr>" +
                "   <th width='30%'>信息标题</th>"+
                "   <th width='15%'>来源</th>"+
                "   <th width='30%'>信息网址</th>"+
                "   <th width='15%'>添加时间</th>"+
                "   <th width='10%'>信息状态</th>"+
                "</tr>" +
                "</thead>" +
                "<tbody>" ;
            if(beanList.size() != 0)
            {
                for(var i=0;i<beanList.size();i++)
                {
                    treeHtmls += setHandlList(beanList.get(i));
                }
                treeHtmls += "</tbody>"+
                    "<tfoot>" +
                    "<td colspan=\"2\"></td>"+ " </tfoot>";
            }else{
                treeHtmls += "<tr><td colspan=\"2\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"2\"></td>"+" </tfoot>";
            }
            $("#treeTableCount").append(treeHtmls);
            iniTreeTable("treeTableCount");
        }

        // 组织统计的数据
        function setHandlList(bean)
        {
            var str = "<tr>";
            if(bean != null)
            {
                var url = bean.content_url;
                if(url.substring(0,4)!="http"){
                    url = "http://"+"<%=site_doman%>"+url;
                }
                str+="<td><a href='"+url+"' target=\"_blank\">"+bean.title+"</a></td>";
                str+="<td>"+bean.source+"</td>";
                str+="<td>"+url+"</td>";
                str+="<td>"+bean.input_dtime+"</td>";
                var status = "";
                if(bean.info_status == "8")
                {
                    status = "已发";
                }
                if(bean.info_status == "4")
                {
                    status = "待发";
                }
                if(bean.info_status == "2")
                {
                    status = "待审";
                }
                if(bean.info_status == "1")
                {
                    status = "退稿";
                }
                if(bean.info_status == "0")
                {
                    status = "草稿";
                }
                str+="<td>"+status+"</td>";
            }
            str+="</tr>";
            return str;
        }

        function downFile(){
            window.open(CmsCountRPC.getInfoListByUserIDTimeTypeExcel(mp));
        }
    </script>
</head>
<body>
<span class="blank3"></span>
<input id="btnExcel" type="button" value="Excel导出" onclick="downFile()"/>
<table width="100%">
    <tr valign="top">
        <td>
            <table id="treeTableCount" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0">
            </table>
        </td>
    </tr>
</table>
<span class="blank3"></span>
<span class="blank3"></span>
</body>
</html>