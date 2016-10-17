<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Excel表头信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="js/excelTitleList.js"></script>
    <script type="text/javascript">
        var id = request.getParameter("id");
        var snum = 0;
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            initTable();
            reloadExcelTitleList();
        });

    </script>
</head>

<body>
<div>

    <table cellspacing="0" cellpadding="0" border="0" class="table_option">
        <tbody>
            <tr>
                <td >&nbsp;&nbsp;表头分类:&nbsp;
                    <select onchange="changeType(this.value)" class="input_select width90" name="selectType" id="selectType">
                        <option selected="selected" value="">全部</option>
                        <option value="1">工资表头</option>
                        <option value="2">津贴表头</option>
                    </select>
                    &nbsp;&nbsp;是否显示:&nbsp;
                    <select onchange="changeIsShow(this.value)" class="input_select width90" name="isShow" id="isShow">
                        <option selected="selected" value="">全部</option>
                        <option value="1">显示</option>
                        <option value="0">隐藏</option>
                    </select>
                </td>
            </tr>
        </tbody>
    </table>

    <span class="blank5"></span>

    <div id="table"></div>
    <!-- 列表DIV -->

    <span class="blank5"></span>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle">
                <input id="btnAdd" type="button" class="btn x2" value="添加" onclick="addInfo()"/>
                <input id="btnUpdate" type="button" class="btn x2" value="修改" onclick="updateRecord(table,'id','openUpdateExcelTitlePage(id)');"/>
                <input id="btnUnable" type="button" class="btn x2" value="显示" onclick="updateIsShow('1')"/>
                <input id="btnable" type="button" class="btn x2" value="隐藏" onclick="updateIsShow('0')"/>
                <input id="btn4" name="btn4" type="button" onclick="saveSort()" value="保存排序" />
                <input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','deleteExcelTitle()');"
                       value="删除"/>
            </td>
        </tr>
    </table>
</div>
</body>
</html>