<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>站点纠错信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="js/siteErrorList.js"></script>
    <style>
        input[type="text"] {
            width: 60%;
            height: 100%;
        }

        input[type="password"] {
            width: 60%;
            height: 100%;
        }

        table tr, td {
            height: 24px;
            line-height: 24px;
        }

        table tr {
            text-align: center;
            vertical-align: middle;
        }

        .table_form td {
            text-align: center;
        }

        select {
            height: 24px;
            line-height: 24px;
            width: 285px;
        }
    </style>
    <SCRIPT LANGUAGE="JavaScript">

        var topnum = request.getParameter("topnum");
        var id = request.getParameter("id");

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            var map = new Map();
            map.put("status", "0");
            map.put("sort_name", "id");
            map.put("sort_type", "desc");
            var typeList = SiteErrorRPC.getAllErrorTypeList(map);
            var siteList = SiteErrorRPC.getAllErrorSiteList(map);
            typeList = List.toJSList(typeList);
            siteList = List.toJSList(siteList);
            for (var i = 0; i < typeList.size(); i++) {
                var selObj = $("#typeId");
                var value = typeList.get(i).id;
                var text = typeList.get(i).typeName;
                selObj.append("<option value='" + value + "'>" + text + "</option>");
            }
            for (var i = 0; i < siteList.size(); i++) {
                var selObj = $("#siteId");
                var value = siteList.get(i).id;
                var text = siteList.get(i).siteName;
                selObj.append("<option value='" + value + "'>" + text + "</option>");
            }

            if (id != null && id.trim() != "") {
                defaultBean = SiteErrorRPC.getSiteErrorBean(id);

                if (defaultBean) {
                    $("#siteErrordiv").autoFill(defaultBean);
                }
            }
        });
    </SCRIPT>
</head>

<body>
<span class="blank12"></span>

<div style="width:555px;">
    <div id="siteErrordiv">
        <input type="hidden" id="id" name="id" value=""/>
        <table style="width:555px;" class="table_form" border="0" cellpadding="0" cellspacing="0" id="siteError_table"
               name="siteError_table">
            <tr>
                <td colspan="6" style="font-size:14px; text-align:center;"><B>站点纠错信息</B></td>
            </tr>
            <tr>
                <td>分类名称</td>
                <td colspan="4" style="text-align:left;">
                    <select id="typeId" name="typeId"></select>
                </td>
            </tr>
            <tr>
                <td><span class="f_red">*</span>提交站点</td>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="submitSiteName" name="submitSiteName" class="width200"  value="" onblur="checkInputValue('submitSiteName',false,300,'提交站点','')"/>
                </td>
            </tr>
            <tr>
                <td><span class="f_red">*</span>提交人</td>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="submitUser" name="submitUser" class="width200" value="" onblur="checkInputValue('submitUser',false,20,'提交人','')"/>
                </td>
            </tr>
            <tr>
                <td><span class="f_red">*</span>联系方式</td>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="submitUserPhone" name="submitUserPhone" class="width200" value="" onblur="checkInputValue('submitUserPhone',false,20,'联系方式','')"/>
                </td>
            </tr>
            <tr>
                <td><span class="f_red">*</span>电子邮箱</td>
                <td colspan="4" style="text-align:left;"><input type="text" id="submitUserEmail" name="submitUserEmail" value="" class="width200" onblur="checkInputValue('submitUserEmail',false,50,'电子邮箱','')"/>
                </td>
            </tr>
            <tr>
                <td>错误描述</td>
                <td colspan="4" style="text-align:left;">
                    <textarea id="submitDescription" name="submitDescription" rows="5" cols="61"></textarea>
                </td>
            </tr>
            <tr>
                <td><span class="f_red">*</span>错误地址</td>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="errorUrl" name="errorUrl" class="width200" value="" onblur="checkInputValue('errorUrl',false,100,'错误地址','')"/>
                </td>
            </tr>
            <tr>
                <td>站点名称</td>
                <td colspan="4" style="text-align:left;">
                    <select id="siteId" name="siteId"></select>
                </td>
            </tr>
            <tr>
                <td><span class="f_red">*</span>发布标题</td>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="errorTitle" name="errorTitle" class="width200" value="" onblur="checkInputValue('errorTitle',false,500,'发布标题','')"/>
                </td>
            </tr>
            <tr>
                <td><span class="f_red">*</span>错误栏目</td>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="errorCatName" name="errorCatName" class="width200" value="" onblur="checkInputValue('errorCatName',false,200,'错误栏目','')"/>
                </td>
            </tr>
            <tr>
                <td><span class="f_red">*</span>发布描述</td>
                <td colspan="4" style="text-align:left;">
                    <textarea id="errorDescription" name="errorDescription" rows="5" cols="61"></textarea>
                </td>
            </tr>
            <tr>
                <td>添加时间</td>
                <td colspan="4" style="text-align:left;">
                    <input id="addTime" name="addTime" type="text" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>状态</td>
                <td colspan="4" style="text-align:left;">
                    <select id="status" name="status">
                        <option value="0">未发布，未处理</option>
                        <option value="1">已发布，处理中</option>
                        <option value="2">已发布，已处理</option>
                    </select>
                </td>
            </tr>
        </table>
    </div>
    <span class="blank12"></span>

    <div class="line2h"></div>
    <span class="blank3"></span>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="center" valign="middle">
                <input name="btn1" type="button" onclick="updateSiteErrorData()" value="保存"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex);"
                       value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</div>
</body>
</html>
