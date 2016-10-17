<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>价格监测信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="js/priceInfoList.js"></script>
    <script type="text/javascript">
        var id = request.getParameter("id");
        var snum = 0;
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            initTable();
            reloadPriceInfoList();
        });

    </script>
</head>

<body>
<div>
    <span class="blank3"></span>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0" >
        <tr>
            <td align="left" valign="middle" >
                添加时间：<input type="text" readonly="readonly" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" value="" style="width:90px;height:16px;line-height:16px;" size="11" id="collectDate" name="collectDate" class="Wdate input_text input_text_focus">
                市场名称：<select id="scName" name="scName">
                    <option value="">全部</option>
                    <option value="北城批发市场">北城批发市场</option>
                    <option value="欣桥批发市场">欣桥批发市场</option>
                    <option value="摩尔农产品交易中心">摩尔农产品交易中心</option>
                    <option value="人人乐超市">人人乐超市</option>
                    <option value="人人家超市">人人家超市</option>
                    <option value="西勘综合市场">西勘综合市场</option>
                    <option value="文景农贸市场">文景农贸市场</option>
                    <option value="明珠巷综合市场">明珠巷综合市场</option>
                    <option value="龙首农副市场">龙首农副市场</option>
                    <option value="高陵农村信息站">高陵农村信息站</option>
                    <option value="未央农村信息站">未央农村信息站</option>
                    <option value="雁塔农村信息站">雁塔农村信息站</option>
                    <option value="周至农村信息站">周至农村信息站</option>
                    <option value="临潼农村信息站">临潼农村信息站</option>
                    <option value="长安农村信息站">长安农村信息站</option>
                    <option value="灞桥农村信息站">灞桥农村信息站</option>
                    <option value="户县农村信息站">户县农村信息站</option>
                    <option value="蓝田农村信息站">蓝田农村信息站</option>
                    <option value="阎良农村信息站">阎良农村信息站</option>
                    <option value="沣东新城农村信息站">沣东新城农村信息站</option>
                </select>
                <input id="btn" type="button" value="搜索" onclick="searchInfo()"/>
            </td>
            <td align="right" valign="middle" >
                导出时间：<input type="text" readonly="readonly" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" value="" style="width:90px;height:16px;line-height:16px;" size="11" id="exportDate" name="exportDate" class="Wdate input_text input_text_focus">
                <input id="btnexp" type="button" value="导出" onclick="exportInfo()"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
    <div id="table"></div>
    <!-- 列表DIV -->
    <div id="turn"></div>
    <!-- 翻页DIV -->
    <span class="blank5"></span>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle">
                <input id="btnAdd" type="button" class="btn x2" value="添加" onclick="addInfo()"/>
                <input id="btnUpdate" type="button" class="btn x2" value="修改" onclick="updateRecord(table,'id','openUpdatePriceInfoPage(id)');"/>
                <input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','deletePriceInfo()');"
                       value="删除"/>
            </td>
        </tr>
    </table>
</div>
</body>
</html>