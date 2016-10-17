<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>纠错分类信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css" />
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css" />
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="js/errorTypeList.js"></script>

    <SCRIPT LANGUAGE="JavaScript">

        var id = request.getParameter("id");
        var topnum = request.getParameter("topnum");
        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input()

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            if (id != null && id.trim() != "") {
                defaultBean = SiteErrorRPC.getErrorTypeBean(id);

                if (defaultBean) {
                    $("#ErrorType_table").autoFill(defaultBean);
                }
            }
        });
    </SCRIPT>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
    <table id="ErrorType_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
            <th><span class="f_red">*</span>分类名称：</th>
            <td >
                <input id="typeName" name="typeName" type="text" class="width200" value="" onblur="checkInputValue('typeName',false,20,'分类名称','')" />
                <input type="hidden" id="id" name="id" value="">
                <input type="hidden" id="status" name="status" value="">
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
                <input id="addButton" name="btn1" type="button" onclick="updateErrorTypeData()" value="保存" />
                <input id="userAddReset" name="btn1" type="button" onclick="formReSet('ErrorType_table',id)" value="重置" />
                <input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</form>
</body>
</html>
