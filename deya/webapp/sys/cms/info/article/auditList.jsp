<%@ page contentType="text/html; charset=utf-8" %>
<%
    String info_id = request.getParameter("info_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>审核过程信息</title>
    <jsp:include page="../include/include_info_tools.jsp"/>

    <script type="text/javascript">
        var info_id = "<%=info_id%>";
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
                $("html").css("overflowY", "scroll");
            initAuditList();
        });

        function initAuditList() {
            //得到该栏目所使用的流程ID
            var auditList = jsonrpc.InfoBaseRPC.getAllInfoWorkStepByInfoId(info_id, null);
            auditList = List.toJSList(auditList);
            for (var i = 0; i < auditList.size(); i++) {
                var bean = auditList.get(i);
                var auditStatus = bean.pass_status == 1 ? "通过" : "不通过";
                var html = '<p>审核人：' + bean.user_name +'&nbsp;&nbsp;&nbsp;&nbsp;审核状态：' + auditStatus + '&nbsp;&nbsp;&nbsp;&nbsp;审核时间：' + bean.work_time + '<p/><p>审核意见：' + bean.description + '</p><span class="blank12"></span><div class="line2h"></div>';
                $("#auditList").append(html);
            }
        }

        function related_cancel() {
            parent.CloseModalWindow();
        }

    </script>
    <style>
        #auditList{padding:8px;}
        #auditList p{line-height: 30px;}
    </style>
</head>
<body>
<span class="blank3"></span>
<span class="blank12"></span>
<div id="auditList">

</div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td align="center" valign="middle">
            <input type="button" value="关闭" class="btn1" onclick="related_cancel()"/>
        </td>
    </tr>
</table>
</body>

</html>