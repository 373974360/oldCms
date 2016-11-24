<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>简历信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <jsp:include page="../../../include/include_tools.jsp"/>
    <script type="text/javascript" src="js/jlList.js"></script>
    <script type="text/javascript">
        var info_id = request.getParameter("info_id");
        var snum = 0;
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            initTable();
            reloadJlList();
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

</div>
</body>
</html>