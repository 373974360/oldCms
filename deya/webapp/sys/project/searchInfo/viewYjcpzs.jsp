<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>有机产品认证证书信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="js/yjcpzsList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">

        var id = request.getParameter("id");
        var topnum = request.getParameter("topnum");

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            if (id != null && id.trim() != "") {
                defaultBean = YjcpzsRPC.getYjcpzsBean(id);

                if (defaultBean) {
                    $("#yjcpzsdiv").autoFill(defaultBean);
                }
            }
        });
    </SCRIPT>
    <style type="text/css">
        .width250{width:375px;}
    </style>
</head>

<body>
<span class="blank5"></span>

<div style="width:588px;">
    <div id="yjcpzsdiv">
        <input type="hidden" id="id" name="id" value=""/>
        <table style="width:475px;" class="table_form" border="0" cellpadding="0" cellspacing="0" id="yjcpzs_table"
               name="yjcpzs_table">
            <tr>
                <td colspan="6" style="font-size:14px; text-align:center;"><B>有机产品认证证书信息</B></td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>持有人名称：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="cyrmc" name="cyrmc" class="width250"  value="" onblur="checkInputValue('cyrmc',false,300,'持有人名称','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>地址：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="dz" name="dz" class="width250"  value="" onblur="checkInputValue('dz',false,300,'地址','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>基地（加工厂）名称：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="jdmc" name="jdmc" class="width250"  value="" onblur="checkInputValue('jdmc',false,300,'基地（加工厂）名称','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>基地（加工厂）地址：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="jddz" name="jddz" class="width250"  value="" onblur="checkInputValue('jddz',false,300,'基地（加工厂）地址','')"/>
                </td>
            </tr>
            <tr>
                <th>面积(亩)：</th>
                <td style="text-align:left;">
                    <input type="text" id="mj" name="mj" class=""  value="" onblur="checkInputValue('mj',true,300,'面积(亩)','')"/>
                </td>
                <th><span class="f_red">*</span>产品名称：</th>
                <td style="text-align:left;">
                    <input type="text" id="cpmc" name="cpmc" class=""  value="" onblur="checkInputValue('cpmc',false,300,'产品名称','')"/>
                </td>
            </tr>
            <tr>
                <th>产品描述：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="cpms" name="cpms" class="width250"  value="" onblur="checkInputValue('cpms',true,300,'产品描述','')"/>
                </td>
            </tr>
            <tr>
                <th>生产规模：</th>
                <td  style="text-align:left;">
                    <input type="text" id="scgm" name="scgm" class=""  value="" onblur="checkInputValue('scgm',true,300,'生产规模','')"/>
                </td>
                <th>产量：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="cl" name="cl" class=""  value="" onblur="checkInputValue('cl',true,300,'产量','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>初次发证日期：</th>
                <td style="text-align:left;">
                    <input type="text" id="ccfzrq" name="ccfzrq" class=""  value="" onblur="checkInputValue('ccfzrq',false,300,'初次发证日期','')"/>
                </td>
                <th><span class="f_red">*</span>本次发证日期：</th>
                <td style="text-align:left;">
                    <input type="text" id="bcfzrq" name="bcfzrq" class=""  value="" onblur="checkInputValue('bcfzrq',false,300,'本次发证日期','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>有效期限：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="yxqx" name="yxqx" class="width250"  value="" onblur="checkInputValue('yxqx',false,300,'有效期限','')"/>
                </td>
            </tr>
            <tr>
                <th>备注：</th>
                <td colspan="4" style="text-align:left;">
                    <textarea rows="5" style="width: 375px"  id="bz" name="bz"></textarea>
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
                <input name="btn1" type="button" onclick="updateYjcpzsData()" value="保存"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="CloseModalWindow();"
                       value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</div>
</body>
</html>
