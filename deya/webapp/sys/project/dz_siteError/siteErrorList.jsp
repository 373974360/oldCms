<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>站点纠错信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="js/siteErrorList.js"></script>
    <script type="text/javascript">
        var id = request.getParameter("id");
        var snum = 0;
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            initTabAndStatus();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            initTable();
            reloadSiteErrorList();
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
                    <li class="list_tab">
                        <div class="tab_left">
                            <div class="tab_right">已处理</div>
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
                <input id="btnUpdate" type="button" class="btn x2" value="修改" onclick="updateRecord(table,'id','openUpdateSiteErrorPage(id)');"/>
                <input id="btnPublish" type="button" class="btn x2" value="发布" onclick="updateRecord(table,'id','savePublishFlag(1)');"/>
                <input id="btnHandle" type="button" class="btn x2" value="已处理" onclick="updateRecord(table,'id','savePublishFlag(2)');"/>
                <input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','deleteSiteError()');"
                       value="删除"/>
            </td>
        </tr>
    </table>
</div>
</body>
</html>