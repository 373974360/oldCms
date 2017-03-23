<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>价格监测信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="js/priceInfoList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">

        var id = request.getParameter("id");
        var topnum = request.getParameter("topnum");

        var marketId = 0;
        var typeId = 0;
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
                if(i == 0)
                {
                    marketId = value;
                }
            }
            if(id != null && id.trim() != "")
            {
                defaultBean = PriceInfoRPC.getPriceInfoBean(id);
                if (defaultBean) {
                    marketId = defaultBean.marketId;
                    typeId =defaultBean.typeId;
                }
            }
            changeMarket(marketId);
            if (defaultBean) {
                $("#priceInfodiv").autoFill(defaultBean);
            }

        });

        function changeMarket(val)
        {
            if(val == 2)
            {
                val = 1;
            }
            var selObj = $("#typeId");
            var ptList = ProductTypeRPC.getProductTypeByMarketId(val);
            ptList = List.toJSList(ptList);
            selObj.empty();
            for (var i = 0; i < ptList.size(); i++) {
                var value = ptList.get(i).id;
                var text = ptList.get(i).typeName;
                selObj.append("<option value='" + value + "'>" + text + "</option>");
                if(i == 0 && typeId == 0)
                {
                    typeId = value;
                }
            }
            changeType(typeId);
        }

        function changeType(val)
        {
            var selObj = $("#productId");
            selObj.empty();
            var productList = ProductRPC.getAllProductListByTypeId(val);
            productList = List.toJSList(productList);
            for (var i = 0; i < productList.size(); i++) {
                var value = productList.get(i).id;
                var text = productList.get(i).productName;
                selObj.append("<option value='" + value + "'>" + text + "</option>");
            }
        }

    </SCRIPT>
</head>

<body>
<span class="blank5"></span>

<div style="width:475px;">
    <div id="priceInfodiv">
        <input type="hidden" id="id" name="id" value=""/>
        <table style="width:475px;" class="table_form" border="0" cellpadding="0" cellspacing="0" id="priceInfo_table"
               name="priceInfo_table">
            <tr>
                <td colspan="6" style="font-size:14px; text-align:center;"><B>价格监测信息</B></td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>市场分类：</th>
                <td colspan="4" style="text-align:left;">
                    <select id="marketId" name="marketId" style="width:90px;" onchange="changeMarket(this.value)"></select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>品种分类：</th>
                <td colspan="4" style="text-align:left;">
                    <select id="typeId" name="typeId" onchange="changeType(this.value)" style="width:90px;"></select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>蔬果名称：</th>
                <td colspan="4" style="text-align:left;">
                    <select id="productId" name="productId" style="width:90px;"></select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>价格：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="price" name="price" class="width200"  value="" onblur="checkInputValue('price',false,300,'价格','')"/>(单位：元/公斤 元/升)
                </td>
            </tr>
            <tr>
                <th>产地：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="location" name="location" class="width200"  value="" onblur="checkInputValue('location',true,300,'产地','')"/>
                </td>
            </tr>
            <tr>
                <th>上市量：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="landings" name="landings" class="width200"  value="" onblur="checkInputValue('landings',true,300,'上市量','')"/>
                </td>
            </tr>
            <tr>
                <th>交易量：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="tradings" name="tradings" class="width200"  value="" onblur="checkInputValue('tradings',true,300,'交易量','')"/>
                </td>
            </tr>
            <tr>
                <th>备注：</th>
                <td colspan="4" style="text-align:left;">
                    <textarea rows="5" style="width: 250px"  id="comments" name="comments"></textarea>
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
                <input name="btn1" type="button" onclick="updatePriceInfoData()" value="保存"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="CloseModalWindow();"
                       value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</div>
</body>
</html>
