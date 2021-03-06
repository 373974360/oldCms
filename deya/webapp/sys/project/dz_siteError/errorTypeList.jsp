﻿<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>纠错分类</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="js/errorTypeList.js"></script>
    <script type="text/javascript">
        var id = request.getParameter("id");
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            initTable();
            reloadErrorTypeList();
        });

    </script>
</head>

<body>
<div>
    <span class="blank3"></span>

    <div id="table"></div>
    <!-- 列表DIV -->
    <div id="turn"></div>
    <!-- 翻页DIV -->
    <span class="blank5"></span>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle">
                <input id="btnAdd" type="button" class="btn x2" value="添加" onclick="addInfo()"/>
                <input id="btnUpdate" type="button" class="btn x2" value="修改" onclick="updateRecord(table,'id','openUpdateErrorTypePage()');"/>
                <input id="btnSort" type="button" class="btn x2" value="保存排序" onclick="saveSort()"/>
                <input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','deleteErrorType()');"
                       value="删除"/>
            </td>
        </tr>
    </table>
</div>
</body>
</html>