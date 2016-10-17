<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>品种分类信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="js/productTypeList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">

        var id = request.getParameter("id");
        var topnum = request.getParameter("topnum");

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            var typeList = MarketTypeRPC.getAllMarketTypeList();
            typeList = List.toJSList(typeList);
            for (var i = 0; i < typeList.size(); i++) {
                var selObj = $("#marketId");
                var value = typeList.get(i).id;
                var text = typeList.get(i).marketName;
                selObj.append("<option value='" + value + "'>" + text + "</option>");
            }

            if (id != null && id.trim() != "") {
                defaultBean = ProductTypeRPC.getProductTypeBean(id);

                if (defaultBean) {
                    $("#productTypediv").autoFill(defaultBean);
                }
            }
        });
    </SCRIPT>
</head>

<body>
<span class="blank5"></span>

<div style="width:475px;">
    <div id="productTypediv">
        <input type="hidden" id="id" name="id" value=""/>
        <table class="table_form" border="0" cellpadding="0" cellspacing="0" id="productType_table"
               name="productType_table">
            <tr>
                <td colspan="6" style="font-size:14px; text-align:center;"><B>品种分类信息</B></td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>市场分类：</th>
                <td colspan="4" style="text-align:left;">
                    <select id="marketId" name="marketId" style="width:90px;"></select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>分类名称：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="typeName" name="typeName" class="width250"  value="" onblur="checkInputValue('typeName',false,300,'分类名称','')"/>
                </td>
            </tr>
            <tr>
                <th>备注：</th>
                <td colspan="4" style="text-align:left;">
                    <textarea rows="5" style="width: 250px"  id="comments" name="comments"></textarea>
                </td>
            </tr>
            <tr>
                <th>状态：</th>
                <td colspan="4" style="text-align:left;">
                    <select id="status" name="status" style="width:90px;">
                        <option value="1" selected="selected">正常</option>
                        <option value="0">禁用</option>
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
                <input name="btn1" type="button" onclick="updateProductTypeData()" value="保存"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();"
                       value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</div>
</body>
</html>
