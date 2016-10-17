﻿<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>龙头企业信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="js/ltqyxxList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">

        var id = request.getParameter("id");
        var topnum = request.getParameter("topnum");

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            if (id != null && id.trim() != "") {
                defaultBean = LtqyxxRPC.getLtqyxxBean(id);

                if (defaultBean) {
                    $("#ltqyxxdiv").autoFill(defaultBean);
                }
            }
        });
    </SCRIPT>
</head>

<body>
<span class="blank5"></span>

<div style="width:475px;">
    <div id="ltqyxxdiv">
        <input type="hidden" id="id" name="id" value=""/>
        <table style="width:475px;" class="table_form" border="0" cellpadding="0" cellspacing="0" id="ltqyxx_table"
               name="ltqyxx_table">
            <tr>
                <td colspan="6" style="font-size:14px; text-align:center;"><B>龙头企业信息</B></td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>单位名称：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="dwmc" name="dwmc" class="width250"  value="" onblur="checkInputValue('dwmc',false,300,'单位名称','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>主营业务：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="zyyw" name="zyyw" class="width250"  value="" onblur="checkInputValue('zyyw',false,300,'主营业务','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>地址：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="dz" name="dz" class="width250"  value="" onblur="checkInputValue('dz',false,300,'地址','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>电话：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="dh" name="dh" class="width250"  value="" onblur="checkInputValue('dh',false,300,'电话','')"/>
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
                <input name="btn1" type="button" onclick="updateLtqyxxData()" value="保存"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</div>
</body>
</html>
