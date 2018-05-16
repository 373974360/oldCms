<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>信息搜索</title>
    <jsp:include page="../include/include_info_tools.jsp"/>

    <script type="text/javascript">

        $(document).ready(function () {
            initButtomStyle();
            init_input();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
                $("html").css("overflowY", "scroll");
            getAllModelList();
            getAllInuptUserID();
        });

        //得到所有内容模型列表
        function getAllModelList() {
            var modelBeanList = jsonrpc.ModelRPC.getCANModelListByAppID(getCurrentFrameObj().app);
            modelBeanList = List.toJSList(modelBeanList);
            $("#model_id").addOptions(modelBeanList, "model_id", "model_name");
        }

        //得到所有录入人列表
        function getAllInuptUserID() {
            var m = new Map();
            m.put("site_id", getCurrentFrameObj().site_id);
            var user_list = InfoBaseRPC.getAllInuptUserID(m);
            user_list = List.toJSList(user_list);
            $("#input_user").addOptions(user_list, "user_id", "user_realname");
        }

        function related_ok() {
            var search_con = "";
            var orderByFields = $("#orderByFields :selected").val();
            var model_id = $("#model_id :selected").val();
            if (model_id != "" && model_id != null) {
                search_con += " and ci.model_id = " + model_id;
            }
            var keyWord = $("#keyWord").val();
            if (keyWord != "" && keyWord != null) {
                search_con += " and (ci.title like '%" + keyWord + "%' or ci.subtitle like '%" + keyWord + "%' or ci.tags like '%" + keyWord + "%') ";
            }
            var input_user = $("#input_user :selected").val();
            if (input_user != "" && input_user != null) {
                search_con += " and ci.input_user = " + input_user;
            }
            var start_time = $("#start_time").val();
            var end_time = $("#end_time").val();
            if (start_time != "" && start_time != null) {
                search_con += " and ci.input_dtime > '" + start_time + " 00:00:00'";
                if (end_time != "" && end_time != null) {
                    if (judgeDate(end_time, start_time)) {
                        msgWargin("结束时间不能小于开始时间");
                        return;
                    }
                }
            }
            if (end_time != "" && end_time != null) {
                search_con += " and ci.input_dtime < '" + end_time + " 23:59:59'";
            }
            var start_weight = $("#start_weight").val();
            var end_weight = $("#end_weight").val();
            if (start_weight != "" && start_weight != null) {
                search_con += " and ci.weight > " + start_weight;
                if (end_weight != "" && end_weight != null) {
                    if (end_weight < start_weight) {
                        msgWargin("权重结束值不能小于开始值");
                        return;
                    }
                }
            }

            var p_start_time = $("#p_start_time").val();
            var p_end_time = $("#p_end_time").val();
            if (p_start_time != "" && p_start_time != null) {
                search_con += " and ci.released_dtime > '" + p_start_time + " 00:00:00'";
                if (p_end_time != "" && p_end_time != null) {
                    if (judgeDate(p_end_time, p_start_time)) {
                        msgWargin("结束时间不能小于开始时间");
                        return;
                    }
                }
            }
            if (p_end_time != "" && p_end_time != null) {
                search_con += " and ci.released_dtime < '" + p_end_time + " 23:59:59'";
            }
            var start_weight = $("#start_weight").val();
            var end_weight = $("#end_weight").val();
            if (start_weight != "" && start_weight != null) {
                search_con += " and ci.weight > " + start_weight;
                if (end_weight != "" && end_weight != null) {
                    if (end_weight < start_weight) {
                        msgWargin("权重结束值不能小于开始值");
                        return;
                    }
                }
            }

            if (end_weight != "" && end_weight != null) {
                search_con += " and ci.weight < " + end_weight;
            }
            var author = $("#author").val();
            if (author != "" && author != null) {
                search_con += " and ci.author like '%" + author + "%' ";
            }
            var source = $("#source").val();
            if (source != "" && source != null) {
                search_con += " and ci.source like '%" + source + "%' ";
            }
            getCurrentFrameObj().highSearchHandl(search_con, orderByFields);
            CloseModalWindow();
        }

        function related_cancel() {
            CloseModalWindow();
        }
    </script>
</head>
<body>
<span class="blank3"></span>
<form id="form1" name="form1" action="#" method="post">
    <table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
            <th>内容模型：</th>
            <td>
                <select id="model_id" style="width:154px">
                    <option value="">全部</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>关键字：</th>
            <td><!-- 搜标题和副标题 -->
                <input type="text" id="keyWord" style="width:150px">
            </td>
        </tr>
        <tr>
            <th>录入人：</th>
            <td>
                <select id="input_user" style="width:154px">
                    <option value="">全部</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>录入时间：</th>
            <td>
                <input id="start_time" name="start_time" type="text"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"
                       style="width:80px"/>
                -&nbsp;<input id="end_time" name="end_time" type="text"
                              onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})"
                              readonly="readonly" style="width:80px"/>
            </td>
        </tr>
        <tr>
            <th>发布时间：</th>
            <td>
                <input id="p_start_time" name="p_start_time" type="text"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"
                       style="width:80px"/>
                -&nbsp;<input id="p_end_time" name="p_end_time" type="text"
                              onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})"
                              readonly="readonly" style="width:80px"/>
            </td>
        </tr>
        <tr>
            <th>权重：</th>
            <td>
                <input id="start_weight" name="start_weight" type="text" style="width:80px" maxlength="3"
                       onkeypress="var k=event.keyCode; return k>=48&&k<=57"
                       onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false"
                       style="ime-mode:Disabled"/>
                -&nbsp;<input id="end_weight" name="end_weight" type="text" style="width:80px" maxlength="3"
                              onkeypress="var k=event.keyCode; return k>=48&&k<=57"
                              onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false"
                              style="ime-mode:Disabled"/>
            </td>
        </tr>
        <tr>
            <th>作者：</th>
            <td>
                <input type="text" id="author" style="width:150px">
            </td>
        </tr>
        <tr>
            <th>来源：</th>
            <td>
                <input type="text" id="source" style="width:150px">
            </td>
        </tr>
        <tr>
            <th>排序方式：</th>
            <td>
                <select id="orderByFields" class="input_select" style="width:154px">
                    <option selected="selected" value="1">时间倒序</option>
                    <option value="2">时间正序</option>
                    <option value="3">权重倒序</option>
                    <option value="4">权重正序</option>
                </select>
            </td>
        </tr>
        </tbody>
    </table>
    <span class="blank12"></span>
    <div class="line2h"></div>
    <span class="blank3"></span>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle" style="text-indent:100px;">
                <input type="button" value="搜索" class="btn1" onclick="related_ok()"/>
                <input type="button" value="重置" class="btn1" onclick="form1.reset()"/>
                <input type="button" value="取消" class="btn1" onclick="related_cancel()"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</form>
</body>
</html>