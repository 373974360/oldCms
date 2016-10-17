<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>林木种苗企业信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="js/lmzmqyList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">

        var id = request.getParameter("id");
        var topnum = request.getParameter("topnum");

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            if (id != null && id.trim() != "") {
                defaultBean = LmzmqyRPC.getLmzmqyBean(id);

                if (defaultBean) {
                    $("#lmzmqydiv").autoFill(defaultBean);
                }
            }
        });
    </SCRIPT>
</head>

<body>
<span class="blank5"></span>

<div style="width:475px;">
    <div id="lmzmqydiv">
        <input type="hidden" id="id" name="id" value=""/>
        <table style="width:475px;" class="table_form" border="0" cellpadding="0" cellspacing="0" id="lmzmqy_table"
               name="lmzmqy_table">
            <tr>
                <td colspan="6" style="font-size:14px; text-align:center;"><B>林木种苗企业信息</B></td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>企业名称：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="qymc" name="qymc" class="width250"  value="" onblur="checkInputValue('qymc',false,300,'企业名称','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>电话：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="dh" name="dh" class="width250"  value="" onblur="checkInputValue('dh',false,300,'电话','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>生产经营地点：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="scjydd" name="scjydd" class="width250"  value="" onblur="checkInputValue('scjydd',false,300,'生产经营地点','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>一般树种：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="ybsz" name="ybsz" class="width250"  value="" onblur="checkInputValue('ybsz',false,300,'一般树种','')"/>
                </td>
            </tr>
            <tr>
                <th>珍稀树种：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="zxsz" name="zxsz" class="width250"  value="" onblur="checkInputValue('zxsz',true,300,'珍稀树种','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>生产经营许可证：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="scjyxkz" name="scjyxkz" class="width250"  value="" onblur="checkInputValue('scjyxkz',false,300,'生产经营许可证','')"/>
                </td>
            </tr>
            <tr>
                <th>邮编：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="yb" name="yb" class="width250"  value="" onblur="checkInputValue('yb',true,300,'邮编','')"/>
                </td>
            </tr>
            <tr>
                <th>备注：</th>
                <td colspan="4" style="text-align:left;">
                    <textarea rows="5" style="width: 250px"  id="bz" name="bz"></textarea>
                </td>
            </tr>
            <tr>
                <th>状态：</th>
                <td colspan="4" style="text-align:left;">
                    <select id="status" name="status"  style="width:90px;">
                        <option value="1" selected="selected">正常</option>
                        <option value="-1">删除</option>
                    </select>
                </td>
            </tr>
        </table>
    </div>
    <span class="blank12"></span>

    <div class="line2h"></div>
    <span class="blank12"></span>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="center" valign="middle">
                <input name="btn1" type="button" onclick="updateLmzmqyData()" value="保存"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();"
                       value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</div>
</body>
</html>
