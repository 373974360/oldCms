<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Excel表头信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="../../js/pinyin/pinyin.dict.src.js"></script>
    <script type="text/javascript" src="../../js/pinyin/pinyin.js"></script>
    <script type="text/javascript" src="js/excelTitleList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">

        var id = request.getParameter("id");
        var topnum = request.getParameter("topnum");

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            if (id != null && id.trim() != "") {
                defaultBean = ExcelTitleRPC.getExcelTitleBean(id);

                if (defaultBean) {
                    $("#excelTitlediv").autoFill(defaultBean);
                }
            }

            //中文转换成拼音首字母缩写
            $("#cname").blur(function(){
                if($("#ename").val() == "")
                {
                    var strs= new Array();
                    var result="";
                    var ename = pinyin(this.value, true, ",").toLowerCase();
                    strs = ename.split(",");
                    for (i=0;i<strs.length ;i++ )
                    {
                        result += strs[i].substr(0,1);
                    }
                    $("#ename").val(result);
                }
            });
        });
    </SCRIPT>
</head>

<body>
<span class="blank5"></span>

<div style="width:475px;">
    <div id="excelTitlediv">
        <input type="hidden" id="id" name="id" value=""/>
        <table style="width:475px;" class="table_form" border="0" cellpadding="0" cellspacing="0" id="excelTitle_table"
               name="excelTitle_table">
            <tr>
                <td colspan="6" style="font-size:14px; text-align:center;"><B>Excel表头信息</B></td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>表头分类：</th>
                <td colspan="4" style="text-align:left;">
                    <select id="typeId" name="typeId" style="width:90px;">
                        <option value="1">工资表头</option>
                        <option value="2">津贴表头</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>中文名称：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="cname" name="cname" class="width250"  value="" onblur="checkInputValue('cname',false,300,'中文名称','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>英文名称：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="ename" name="ename" class="width250"  value="" onblur="checkInputValue('ename',false,300,'英文名称','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>是否显示：</th>
                <td colspan="4" style="text-align:left;">
                    <select id="isShow" name="isShow" style="width:90px;">
                        <option value="1">显示</option>
                        <option value="0">隐藏</option>
                    </select>
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
                <input name="btn1" type="button" onclick="updateExcelTitleData()" value="保存"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();"
                       value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</div>
</body>
</html>
