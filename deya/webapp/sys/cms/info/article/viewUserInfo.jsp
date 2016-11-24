<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>个人简历</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../../include/include_tools.jsp"/>

    <style>
        input[type="text"] {
            width: 230px;
            height: 100%;
        }

        textarea {
            width: 607px;
            height: 100px;
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
            text-align: right;
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
            var UserInfoRPC = jsonrpc.UserInfoRPC;

            if (id != null && id.trim() != "") {
                defaultBean = UserInfoRPC.getUserInfoBean(id,null);

                if (defaultBean) {
                    $("#jldiv").autoFill(defaultBean);
                }
            }
        });
    </SCRIPT>
</head>

<body>
<span class="blank12"></span>

<div style="width:555px;">
    <div id="jldiv">
        <input type="hidden" id="id" name="id" value=""/>
        <table style="width:750px;" class="table_form" border="0" cellpadding="0" cellspacing="0" id="jl_table"
               name="jl_table">
            <tr>
                <td colspan="6" style="font-size:14px; text-align:center;"><B>个人简历</B></td>
            </tr>
            <tr>
                <td>姓名：</td>
                <td style="text-align:left;">
                    <input type="text" id="name" name="name"/>
                </td>
                <td>性别：</td>
                <td style="text-align:left;">
                    <input type="text" id="gender" name="gender"/>
                </td>
            </tr>
            <tr>
                <td>出生年月：</td>
                <td style="text-align:left;">
                    <input type="text" id="csny" name="csny"/>
                </td>
                <td>身份证号：</td>
                <td style="text-align:left;">
                    <input type="text" id="sfzh" name="sfzh"/>
                </td>
            </tr>
            <tr>
                <td>联系方式：</td>
                <td style="text-align:left;">
                    <input type="text" id="phone" name="phone"/>
                </td>
                <td>电子邮箱：</td>
                <td style="text-align:left;">
                    <input type="text" id="email" name="email"/>
                </td>
            </tr>
            <tr>
                <td>民族：</td>
                <td style="text-align:left;">
                    <input type="text" id="mz" name="mz"/>
                </td>
                <td>政治面貌：</td>
                <td style="text-align:left;">
                    <input type="text" id="zzmm" name="zzmm"/>
                </td>
            </tr>
            <tr>
                <td>户籍：</td>
                <td style="text-align:left;">
                    <input type="text" id="hj" name="hj"/>
                </td>
                <td>婚姻状况：</td>
                <td style="text-align:left;">
                    <input type="text" id="hyzk" name="hyzk"/>
                </td>
            </tr>
            <tr>
                <td>毕业院校：</td>
                <td style="text-align:left;">
                    <input type="text" id="byyx" name="byyx"/>
                </td>
                <td>学历：</td>
                <td style="text-align:left;">
                    <input type="text" id="xl" name="xl"/>
                </td>
            </tr>
            <tr>
                <td>所学专业：</td>
                <td style="text-align:left;">
                    <input type="text" id="sxzy" name="sxzy"/>
                </td>
                <td>毕业时间：</td>
                <td style="text-align:left;">
                    <input type="text" id="bysj" name="bysj"/>
                </td>
            </tr>
            <tr>
                <td>工作经验：</td>
                <td colspan="3" style="text-align:left;">
                    <textarea type="text" id="gzjy" name="gzjy"></textarea>
                </td>
            </tr>
            <tr>
                <td>自我评价：</td>
                <td colspan="3" style="text-align:left;">
                    <textarea type="text" id="zwpj" name="zwpj"></textarea>
                </td>
            </tr>
        </table>
    </div>
    <span class="blank12"></span>

    <div class="line2h"></div>
    <span class="blank3"></span>
</div>
</body>
</html>
