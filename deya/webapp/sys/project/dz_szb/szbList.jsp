<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>电子报管理</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script src="http://momentjs.com/downloads/moment.js"></script>
    <script type="text/javascript" src="js/szbList.js"></script>
    <script type="text/javascript">
        var id = request.getParameter("id");
        var snum = 0;
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            initTabAndStatus();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            initTable();
            reloadSzbList();
        });

    </script>
</head>

<body>
<div>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0" >
        <tr>
            <td align="left" width="" colspan="3">
                <ul class="fromTabs">
                    <li class="list_tab list_tab_cur">
                        <div class="tab_left">
                            <div class="tab_right">未发布</div>
                        </div>
                    </li>
                    <li class="list_tab">
                        <div class="tab_left">
                            <div class="tab_right">已发布</div>
                        </div>
                    </li>
                </ul>
            </td>
        </tr>
    </table>
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
                <input id="btnUpdate" type="button" class="btn x2" value="修改" onclick="updateRecord(table,'id','openUpdateSzbPage(id)');"/>
                <input id="btnPublish" type="button" class="btn x2" value="发布" onclick="updateRecord(table,'id','savePublishFlag(1)');"/>
                <input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','deleteSzb()');"
                       value="删除"/>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
