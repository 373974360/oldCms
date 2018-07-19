<%@ page contentType="text/html; charset=utf-8" %>
<%
    String app_id = request.getParameter("app_id");
    String siteid = request.getParameter("site_id");
    String cid = request.getParameter("cat_id");
    String num = request.getParameter("num");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>选择撤回步骤</title>
    <jsp:include page="../include/include_info_tools.jsp"/>

    <script type="text/javascript">
        var num = "<%=num%>";
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
                $("html").css("overflowY", "scroll");


            var step_id = jsonrpc.WorkFlowRPC.getMaxStepIDByUserID(getCurrentFrameObj().beanList.get(num).wf_id,getCurrentFrameObj().beanList.get(num).input_user,"cms","CMScqgjj");
            $("#step_id").val(step_id);
        });

        function related_ok() {
            var backStep = $("input[name='backStep']:checked").val();
            var info_id = getCurrentFrameObj().beanList.get(num).info_id;
            if (InfoBaseRPC.backPassInfoStatus(info_id,backStep)) {
                parent.msgAlert("信息撤回操作成功");
                parent.CloseModalWindow();
                parent.getCurrentFrameObj().reloadInfoDataList();
            }
            else {
                parent.msgWargin("信息撤回操作失败");
            }
        }

        function related_cancel() {
            parent.CloseModalWindow();
        }

    </script>
</head>
<body>
<span class="blank3"></span>
<form id="form1" name="form1" action="#" method="post">
    <table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0">
        <tbody>
        <tr id="back_tr">
            <th>选择撤回步骤：</th>
            <td id="back_list">
                <input id="step_id" name="backStep" type="radio" checked="checked"/><label for="e">发起人</label>&nbsp;&nbsp;
                <input name="backStep" type="radio" value="-1"/><label for="e">草稿</label>&nbsp;&nbsp;
            </td>
        </tr>
        </tbody>
    </table>
    <span class="blank12"></span>
    <div class="line2h"></div>
    <span class="blank12"></span>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle" style="text-indent:100px;">
                <input type="button" value="确定" class="btn1" onclick="related_ok()"/>
                <input type="button" value="取消" class="btn1" onclick="related_cancel()"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</form>
</body>
</html>