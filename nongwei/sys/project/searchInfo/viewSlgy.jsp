<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>森林公园信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="js/slgyList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">

        var id = request.getParameter("id");
        var topnum = request.getParameter("topnum");

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            if (id != null && id.trim() != "") {
                defaultBean = SlgyRPC.getSlgyBean(id);

                if (defaultBean) {
                    $("#slgydiv").autoFill(defaultBean);
                }
            }
        });
    </SCRIPT>
</head>

<body>
<span class="blank5"></span>

<div style="width:475px;">
    <div id="slgydiv">
        <input type="hidden" id="id" name="id" value=""/>
        <table style="width:475px;" class="table_form" border="0" cellpadding="0" cellspacing="0" id="slgy_table"
               name="slgy_table">
            <tr>
                <td colspan="6" style="font-size:14px; text-align:center;"><B>森林公园信息</B></td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>公园名称：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="gymc" name="gymc" class="width250"  value="" onblur="checkInputValue('gymc',false,300,'名单','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>占地面积：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="zdmj" name="zdmj" class="width250"  value="" onblur="checkInputValue('zdmj',false,300,'地址','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>公园级别：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="gyjb" name="gyjb" class="width250"  value="" onblur="checkInputValue('gyjb',false,300,'法人','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>主管部门名称：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="zgbmmc" name="zgbmmc" class="width250"  value="" onblur="checkInputValue('zgbmmc',false,300,'电话','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>公园地址：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="gydz" name="gydz" class="width250"  value="" onblur="checkInputValue('gydz',false,300,'法人','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>联系电话：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="lxdh" name="lxdh" class="width250"  value="" onblur="checkInputValue('lxdh',false,300,'电话','')"/>
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
                <input name="btn1" type="button" onclick="updateSlgyData()" value="保存"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();"
                       value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</div>
</body>
</html>
