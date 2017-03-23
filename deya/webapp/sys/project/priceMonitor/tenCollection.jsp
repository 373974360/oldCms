<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>西安市主要农产品价格统计表</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/tenCollect.js"></script>
    <SCRIPT LANGUAGE="JavaScript">

        var topnum = request.getParameter("topnum");

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

        });

    </SCRIPT>
    <style type="text/css">
        .data_title td{text-align: center;}
        .data_info td{text-align: center;}
    </style>
</head>

<body>
<span class="blank5"></span>
<div style="height:30px;line-height:30px;">
    <span class="f_red">*</span>上期时间：&nbsp;
    <input class="Wdate" type="text" name="lastDateStart" id="lastDateStart" size="11" style="width:90px;height:16px;line-height:16px;"
           value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true" />&nbsp;-&nbsp;
    <input class="Wdate" type="text" name="lastDateEnd" id="lastDateEnd" size="11" style="width:90px;height:16px;line-height:16px;"
           value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true" />
    &nbsp;&nbsp;
    <span class="f_red">*</span>本期时间：&nbsp;
    <input class="Wdate" type="text" name="thisDateStart" id="thisDateStart" size="11" style="width:90px;height:16px;line-height:16px;"
           value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true" />&nbsp;-&nbsp;
    <input class="Wdate" type="text" name="thisDateEnd" id="thisDateEnd" size="11" style="width:90px;height:16px;line-height:16px;"
           value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true" />
    &nbsp;&nbsp;
    <input type="button" id="searchBtn"  onclick="searchBtn()" value="统计"/>
    <input type="button" id="outFileBtn" class="hidden" onclick="dataToExcel()" value="导出"/>
</div>
<span class="blank5"></span>
<div class="line2h"></div>
<span class="blank5"></span>
<div id="resultDiv" name="resultDiv">
<table width="800px" class="table_form hidden" border="1" cellpadding="0" cellspacing="0" id="result_table" name="result_table">
    <tr class="data_title">
        <td colspan="12" align="center">西安市主要农产品价格统计表</td>
    </tr>
    <tr class="data_title">
        <td colspan="12" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;西安市农业信息网络中心<span style="float: right; display: inline; margin-right: 20px;">（单位:  元/公斤   元/升）</td>
    </tr>
    <tr class="data_title">
        <td rowspan="2">类别</td>
        <td rowspan="2">名称</td>
        <td colspan="5">批发价格</td>
        <td colspan="5">超市价格</td>
    </tr>
    <tr class="data_title">
        <td>最高价</td>
        <td>最低价</td>
        <td>均价</td>
        <td>环比（%）</td>
        <td>同比（%）</td>
        <td>最高价</td>
        <td>最低价</td>
        <td>均价</td>
        <td>环比（%）</td>
        <td>同比（%）</td>
    </tr>

</table>
</div>
<span class="blank12"></span>
<form action="exportZB.jsp" method="post" id="exportForm" name="exportForm">
    <input type="hidden"  id="ExcelContent" name="ExcelContent" value=""/>
</form>
</body>
</html>
