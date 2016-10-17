<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>绿色食品证书信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="js/lsspzsList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">

        var id = request.getParameter("id");
        var topnum = request.getParameter("topnum");

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            if (id != null && id.trim() != "") {
                defaultBean = LsspzsRPC.getLsspzsBean(id);

                if (defaultBean) {
                    $("#lsspzsdiv").autoFill(defaultBean);
                }
            }
        });
    </SCRIPT>
</head>

<body>
<span class="blank5"></span>

<div style="width:475px;">
    <div id="lsspzsdiv">
        <input type="hidden" id="id" name="id" value=""/>
        <table style="width:475px;" class="table_form" border="0" cellpadding="0" cellspacing="0" id="lsspzs_table"
               name="lsspzs_table">
            <tr>
                <td colspan="6" style="font-size:14px; text-align:center;"><B>绿色食品证书信息</B></td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>生产商：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="scs" name="scs" class="width250"  value="" onblur="checkInputValue('scs',false,300,'生产商','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>产品名称：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="cpmc" name="cpmc" class="width250"  value="" onblur="checkInputValue('cpmc',false,300,'产品名称','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>产品编号：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="cpbh" name="cpbh" class="width250"  value="" onblur="checkInputValue('cpbh',false,300,'产品编号','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>企业信息码：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="qyxxm" name="qyxxm" class="width250"  value="" onblur="checkInputValue('qyxxm',false,300,'企业信息码','')"/>
                </td>
            </tr>
            <tr>
                <th>核准产量（吨）：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="hzcl" name="hzcl" class="width250"  value="" onblur="checkInputValue('hzcl',true,300,'核准产量（吨）','')"/>
                </td>
            </tr>
            <tr>
                <th>面积（亩）：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="mj" name="mj" class="width250"  value="" onblur="checkInputValue('mj',true,300,'面积（亩）','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>许可期限：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="xkqx" name="xkqx" class="width250"  value="" onblur="checkInputValue('xkqx',false,300,'许可期限','')"/>
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
                <input name="btn1" type="button" onclick="updateLsspzsData()" value="保存"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();"
                       value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</div>
</body>
</html>
