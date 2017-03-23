<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>检验员信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="js/jyyList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">

        var id = request.getParameter("id");
        var topnum = request.getParameter("topnum");

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            if (id != null && id.trim() != "") {
                defaultBean = JyyRPC.getJyyBean(id);

                if (defaultBean) {
                    $("#jyydiv").autoFill(defaultBean);
                }
            }
        });
    </SCRIPT>
</head>

<body>
<span class="blank5"></span>

<div style="width:475px;">
    <div id="jyydiv">
        <input type="hidden" id="id" name="id" value=""/>
        <table style="width:475px;" class="table_form" border="0" cellpadding="0" cellspacing="0" id="jyy_table"
               name="jyy_table">
            <tr>
                <td colspan="6" style="font-size:14px; text-align:center;"><B>检验员信息</B></td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>姓名：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="xm" name="xm" class="width250"  value="" onblur="checkInputValue('xm',false,300,'姓名','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>性别：</th>
                <td colspan="4" style="text-align:left;">
                    <select id="xb" name="xb" style="width:90px;">
                        <option value="男">男</option>
                        <option value="女">女</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>工作单位：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="gzdw" name="gzdw" class="width250"  value="" onblur="checkInputValue('gzdw',false,300,'工作单位','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>检验范围：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="jyfw" name="jyfw" class="width250"  value="" onblur="checkInputValue('jyfw',false,300,'检验范围','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>证书号：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="zsh" name="zsh" class="width250"  value="" onblur="checkInputValue('zsh',false,300,'证书号','')"/>
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
                <input name="btn1" type="button" onclick="updateJyyData()" value="保存"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="CloseModalWindow();"
                       value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</div>
</body>
</html>
