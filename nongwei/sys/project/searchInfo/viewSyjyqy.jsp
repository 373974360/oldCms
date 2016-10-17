<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>通过兽药GSP兽药经营企业信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="js/syjyqyList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">

        var id = request.getParameter("id");
        var topnum = request.getParameter("topnum");

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");
            if (id != null && id.trim() != "") {
                defaultBean = SyjyqyRPC.getSyjyqyBean(id);
                if (defaultBean) {
                    $("#syjyqydiv").autoFill(defaultBean);
                }
            }
        });
    </SCRIPT>
</head>

<body>
<span class="blank5"></span>

<div style="width:475px;">
    <div id="syjyqydiv">
        <input type="hidden" id="id" name="id" value=""/>
        <table style="width:475px;" class="table_form" border="0" cellpadding="0" cellspacing="0" id="syjyqy_table"
               name="syjyqy_table">
            <tr>
                <td colspan="6" style="font-size:14px; text-align:center;"><B>通过兽药GSP兽药经营企业信息</B></td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>区县：</th>
                <td colspan="4" style="text-align:left;">
                    <select id="qx" name="qx" style="width:90px;">
                        <option value="西安市">西安市</option>
                        <option value="新城区">新城区</option>
                        <option value="碑林区">碑林区</option>
                        <option value="莲湖区">莲湖区</option>
                        <option value="雁塔区">雁塔区</option>
                        <option value="未央区">未央区</option>
                        <option value="灞桥区">灞桥区</option>
                        <option value="长安区">长安区</option>
                        <option value="阎良区">阎良区</option>
                        <option value="临潼区">临潼区</option>
                        <option value="高陵区">高陵区</option>
                        <option value="户县">户县</option>
                        <option value="周至县">周至县</option>
                        <option value="蓝田县">蓝田县</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>企业名称：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="qymc" name="qymc" class="width250"  value="" onblur="checkInputValue('qymc',false,300,'企业名称','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>经营地址：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="jydz" name="jydz" class="width250"  value="" onblur="checkInputValue('jydz',false,300,'经营地址','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>法人：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="fr" name="fr" class="width250"  value="" onblur="checkInputValue('fr',false,300,'法人','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>联系电话：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="lxdh" name="lxdh" class="width250"  value="" onblur="checkInputValue('lxdh',false,300,'联系电话','')"/>
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
                <input name="btn1" type="button" onclick="updateSyjyqyData()" value="保存"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();"
                       value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</div>
</body>
</html>
