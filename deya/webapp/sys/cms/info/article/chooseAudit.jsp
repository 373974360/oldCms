<%@ page contentType="text/html; charset=utf-8" %>
<%
    String app_id = request.getParameter("app_id");
    String siteid = request.getParameter("site_id");
    String cid = request.getParameter("cat_id");
    String num = request.getParameter("num");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>选择审核步骤</title>
    <jsp:include page="../include/include_info_tools.jsp"/>

    <script type="text/javascript">
        var site_id = "<%=siteid%>";
        var app = "<%=app_id%>";
        var cid = "<%=cid%>";
        var num = "<%=num%>";
        var auditHtml = "";
        var info_step_id = 0;
        var info_status = 2;
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
                $("html").css("overflowY", "scroll");
            initAuditList();
        });

        function initAuditList() {
            //得到该栏目所使用的流程ID
            var wf_id = jsonrpc.CategoryRPC.getWFIDByCatID(cid, site_id);

            if (wf_id == 0) {
                //没有审核流程
                $("#li_ds").remove();//删除待审
                $("#opt_303 :radio").attr("checked", true);
                $("#opt_303").show();
            } else {
                auditHtml = "";
                var step_id = getMaxStepIDByUserID(wf_id, app, site_id);
                var workFlowBean = jsonrpc.WorkFlowRPC.getWorkFlowBean(wf_id);
                var workStepList = workFlowBean.workFlowStep_list;
                workStepList = List.toJSList(workStepList);
                for (var i = 0; i < workStepList.size(); i++) {
                    if (workStepList.get(i).step_id > step_id) {
                        if (workStepList.get(i).required == 1) {
                            $("#audit_tr").removeClass("hidden");
                            var html = '<input id="auditChecked" name="auditStep" type="radio" checked="checked" onclick="setStepId(' + (workStepList.get(i).step_id - 1) + ')"/><label for="e">' + workStepList.get(i).step_name + '</label>&nbsp;&nbsp;';
                            $("#audit_list").append(html);
                            $("#auditChecked").click();
                            break;
                        }
                        else {
                            $("#audit_tr").removeClass("hidden");
                            var html = '<input name="auditStep" type="radio" onclick="setStepId(' + (workStepList.get(i).step_id - 1) + ')"/><label for="e">' + workStepList.get(i).step_name + '</label>&nbsp;&nbsp;';
                            $("#audit_list").append(html);
                        }
                    }
                }
                if (getCurrentFrameObj().opt_ids.indexOf("302") > -1) {
                    $("#audit_tr").removeClass("hidden");
                    info_step_id = 100;
                    info_status = 8;
                    $("#audit_list input").removeAttr("checked");
                    var html = '<input name="auditStep" type="radio" onclick="setStepId(100)" checked="checked"/><label for="e">发布</label>&nbsp;&nbsp;';
                    $("#audit_list").append(html);
                }
                /*
                 step_id = getMaxStepIDByUserID(wf_id,app_id,site_id);
                 $("#wf_id").val(wf_id);
                 $("#step_id").val(step_id);
                 if(step_id == 100)
                 {//如果登录人是终审人，不要待审按钮 不然，后台不好更改状态逻辑，（如步骤ID为100，却又是待审状态）
                 $("#li_ds").remove();//删除待审
                 $("#opt_303 :radio").attr("checked",true);//将终审通过选中
                 }
                 */
            }
        }

        function setStepId(value) {
            info_step_id = value;
            info_status = 2;
            if (value == 100) {
                info_status = 8;
            }
        }

        function related_ok() {
            var list = new List();
            var auto_desc = $("#auto_desc").val();
            if (num != null && num != "null" && num != "") {
                list.add(getCurrentFrameObj().beanList.get(num));
            }
            else {
                var info_list = getCurrentFrameObj().table.getSelecteBeans();
                info_list = List.toJSList(info_list);
                for (var i = 0; i < info_list.size(); i++) {
                    var infoBean = info_list.get(i);
                    infoBean.step_id = info_step_id;
                    list.add(infoBean);
                }
            }
            if (info_status == 8) {
                if (InfoBaseRPC.updateInfoStatus(list, "8")) {
                    parent.msgAlert("发布操作成功");
                    parent.CloseModalWindow();
                    parent.getCurrentFrameObj().reloadInfoDataList();
                }
                else {
                    parent.msgWargin("发布操作失败");
                }
            }
            else {
                if (InfoBaseRPC.passInfoStatus(list,auto_desc)) {
                    parent.msgAlert("审核操作成功");
                    parent.CloseModalWindow();
                    parent.getCurrentFrameObj().reloadInfoDataList();
                } else {
                    parent.msgWargin("审核操作失败");
                }
            }

        }

        function related_cancel() {
            parent.CloseModalWindow();
        }

    </script>
</head>
<body>
<span class="blank3"></span>
<form id="form1" name="form1" action="#" method="post">
    <table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0">
        <tbody>
        <tr id="audit_tr" class="hidden">
            <th>选择审核步骤：</th>
            <td id="audit_list">

            </td>
        </tr>
        </tbody>
    </table>
    <span class="blank12"></span>
    <table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0">
        <tbody>
        <tr id="auto_desc_tr">
            <th style="vertical-align:top;">审核意见：</th>
            <td>
                <textarea id="auto_desc" name="auto_desc" style="width:380px;;height:130px;"></textarea>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="line2h"></div>
    <span class="blank12"></span>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle" style="text-indent:100px;">
                <input type="button" value="确定" class="btn1" onclick="related_ok()"/>
                <input type="button" value="取消" class="btn1" onclick="related_cancel()"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</form>
</body>
</html>