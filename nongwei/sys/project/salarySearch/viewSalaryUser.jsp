<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>人员信息</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="../../js/pinyin/pinyin.dict.src.js"></script>
    <script type="text/javascript" src="../../js/pinyin/pinyin.js"></script>
    <script type="text/javascript" src="js/salaryUserList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">

        var id = request.getParameter("id");
        var topnum = request.getParameter("topnum");

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            if (id != null && id.trim() != "") {
                defaultBean = SalaryUserRPC.getSalaryUserBean(id);

                if (defaultBean) {
                    $("#salaryUserdiv").autoFill(defaultBean);
                    $("#password2").val(defaultBean.password);
                }
            }
        });
    </SCRIPT>
</head>

<body>
<span class="blank5"></span>

<div style="width:475px;">
    <div id="salaryUserdiv">
        <input type="hidden" id="id" name="id" value=""/>
        <table style="width:475px;" class="table_form" border="0" cellpadding="0" cellspacing="0" id="salaryUser_table"
               name="salaryUser_table">
            <tr>
                <td colspan="6" style="font-size:14px; text-align:center;"><B>人员信息</B></td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>姓名：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="name" name="name" class="width250"  value="" onblur="checkInputValue('name',false,300,'姓名','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>部门名称：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="department" name="department" class="width250"  value="" onblur="checkInputValue('department',false,300,'部门名称','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>身份证号：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="text" id="identify" name="identify" class="width250"  value="" onblur="checkInputValue('identify',false,300,'身份证号','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>查询密码：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="password" id="password" name="password" class="width250"  value="" onblur="checkInputValue('password',false,300,'查询密码','')"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>确认密码：</th>
                <td colspan="4" style="text-align:left;">
                    <input type="password" id="password2" name="password2" class="width250"  value="" />
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
                <input name="btn1" type="button" onclick="updateSalaryUserData()" value="保存"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();"
                       value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</div>
</body>
</html>
