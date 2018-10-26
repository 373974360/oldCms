<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <title>信件录入</title>

    <link type="text/css" rel="stylesheet" href="../../styles/sq_modle.css"/>
    <link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
    <link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css"/>
    <script language="javascript" src="../../js/jquery.uploadify.js"></script>
    <script language="javascript" src="../../js/uploadFile/swfobject.js"></script>
    <script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../js/indexjs/tools.js"></script>
    <script type="text/javascript" src="js/modelList.js"></script>
    <script type="text/javascript">
        var AppealModelRPC = jsonrpc.AppealModelRPC;
        var ModelRPC = jsonrpc.ModelRPC
        var SQRPC = jsonrpc.SQRPC;
        var SQBean = new Bean("com.deya.wcm.bean.appeal.sq.SQBean", true);
        var SQAttachment = new Bean("com.deya.wcm.bean.appeal.sq.SQAttachment", true);
        var local_IP = "";

        var sq_custom_list;//用于存储扩展字段的列表
        var model_id = "<%=request.getParameter("model_id")%>";
        var site_id = jsonrpc.SiteRPC.getSiteIDByAppID("appeal");
        $(document).ready(function () {
            initButtomStyle();
            init_input();
            initUeditor("sq_content");
            iniSQbox();
            getDealDeptListByMID();

            getCPFormListByModel();//得到扩展字段及其内容

            //loadSQFormUpload();
            //上传文档
            //publicUploadDOC("uploadify",true,false,"",0,'',site_id,"");

            //得到诉求目的列表
            getPurposeList();
            //得到特征标记列表
            getTagList();

            showSelectDiv("cat_name", "cat_tree_div", 300, "leftMenu");
            showCategoryTree();  //填充树状数据
            showSelectDiv("area_name", "area_tree_div", 300, "areaMenu");
            showAreaTree();

            local_IP = "<%=request.getRemoteAddr()%>";

        });


        function getCPFormListByModel() {
            var from_list = jsonrpc.SQModelRPC.getCPFormListByModel(model_id);
            from_list = List.toJSList(from_list);
            if (from_list != null && from_list.size() > 0) {
                var str = "";
                for (var i = 0; i < from_list.size(); i++) {
                    str += '<tr><th>' + from_list.get(i).field_cname + '：</th><td><input id="' + from_list.get(i).field_ename + '" name="' + from_list.get(i).field_ename + '" type="text" class="width200"/></td></tr>';
                }
                $("#sq_title_info_tr").after(str);
            }
        }

        function getDealDeptListByMID() {
            var dept_list = AppealModelRPC.getAppealDeptList(model_id);
            dept_list = List.toJSList(dept_list);
            var str = "";
            if (dept_list != null && dept_list.size() > 0) {
                for (var i = 0; i < dept_list.size(); i++) {
                    if (i == 0) {
                        str += '<li style="width:120px;overflow:hidden;display:block;height:20px;padding-right:5px" title="' + dept_list.get(i).dept_name + '"><input type="radio" name="do_dept" value="' + dept_list.get(i).dept_id + '" checked><label>' + dept_list.get(i).dept_name + '</label></li>';
                    } else {
                        str += '<li style="width:120px;overflow:hidden;display:block;height:20px;padding-right:5px" title="' + dept_list.get(i).dept_name + '"><input type="radio" name="do_dept" value="' + dept_list.get(i).dept_id + '"><label>' + dept_list.get(i).dept_name + '</label></li>';
                    }
                }
            }
            $("#dept_list").html(str);
        }

        function getPurposeList() {
            var p_list = jsonrpc.PurposeRPC.getPurposeList();
            p_list = List.toJSList(p_list);
            var str = "";
            if (p_list != null && p_list.size() > 0) {
                for (var i = 0; i < p_list.size(); i++) {
                    if (i == 0) {
                        str += '<li style="width:100px;overflow:hidden;display:block;height:20px;padding-right:5px" title="' + p_list.get(i).pur_name + '"><input type="radio" name="pur_id" value="' + p_list.get(i).pur_id + '" checked><label>' + p_list.get(i).pur_name + '</label></li>';
                    } else {
                        str += '<li style="width:100px;overflow:hidden;display:block;height:20px;padding-right:5px" title="' + p_list.get(i).pur_name + '"><input type="radio" name="pur_id" value="' + p_list.get(i).pur_id + '"><label>' + p_list.get(i).pur_name + '</label></li>';
                    }
                }
            }
            $("#purpose_list").html(str);

        }

        //显示地区分类树
        function showAreaTree() {
            json_data = eval(jsonrpc.AreaRPC.getAreaTreeJsonStr());
            setAppointTreeJsonData("areaTree", json_data);
            initAreaTree();
        }

        //初始化地区分类树点击事件
        function initAreaTree() {
            $('#areaTree').tree({
                onClick: function (node) {
                    selectedArea(node.id, node.text);
                }
            });
        }

        //点击树节点,修改菜单列表数据  (发生地区)
        function selectedArea(id, text) {
            $("#area_name").val(text);
            $("#area_id").val(id);
            $("#area_tree_div").hide();
        }

        //显示内容分类树
        function showCategoryTree() {
            json_data = eval(jsonrpc.AppealCategoryRPC.getCategoryTreeJsonStr());
            setLeftMenuTreeJsonData(json_data);
            initMenuTree();
        }

        //初始化内容分类树点击事件
        function initMenuTree() {
            $('#leftMenuTree').tree({
                onClick: function (node) {
                    selectedCat(node.id, node.text);
                }
            });
        }

        //点击树节点,修改菜单列表数据 (内容分类)
        function selectedCat(id, text) {
            $("#cat_name").val(text);
            $("#cat_id").val(id);
            $("#cat_tree_div").hide();
        }

        //得到特征标记列表
        function getTagList() {
            var t_list = jsonrpc.AssistRPC.getTagListByAPPSite("appeal", "");
            t_list = List.toJSList(t_list);
            if (t_list != null && t_list.size() > 0) {
                for (var i = 0; i < t_list.size(); i++) {
                    $("#tag_list").append('<li><input type="checkbox" id="tag_id" name="tag_id" value="' + t_list.get(i).tag_id + '"><label>' + t_list.get(i).tag_name + '</label></li>');
                }
            }
        }

        function SubmitLetterInfo() {
            var sq = BeanUtil.getCopy(SQBean);

            $("#sq_info_table").autoBind(sq);
            sq.me_id = "0";
            sq.model_id = model_id;
            sq.submit_name = "";

            var pur_id = $(":radio[name='pur_id']:checked").val();
            if (pur_id == "") {
                sq.pur_id = "0";
            } else {
                sq.pur_id = pur_id;
            }
            if ($("#area_id").val() == "") {
                sq.area_id = "0";
            } else {
                sq.area_id = $("#area_id").val();
            }

            if ($("#cat_id").val() == "") {
                sq.cat_id = "0";
            } else {
                sq.cat_id = $("#cat_id").val();
            }

            if ($(":radio[name='is_open']:checked").val() == "") {
                sq.is_open = "1";
            } else {
                sq.is_open = $(":radio[name='is_open']:checked").val();
            }
            if ($("#sq_realname").val() == "") {
                top.msgAlert("姓名不能为空!");
                return;
            } else {
                sq.sq_realname = $("#sq_realname").val();
            }
            sq.sq_sex = $(":radio[id='sq_sex']:checked").val() + "";
            if ($("#sq_age").val() == "") {
                sq.sq_age = 0;
            } else {
                sq.sq_age = $("#sq_age").val()
            }
            sq.sq_tel = $("#sq_tel").val();
            sq.sq_phone = $("#sq_phone").val();
            sq.sq_card_id = $("#sq_card_id").val() + "";
            sq.sq_email = $("#sq_email").val();
            sq.sq_vocation = $('#sq_vocation').val();
            sq.sq_address = $('#sq_address').val();
            sq.sq_ip = local_IP + "";
            sq.cat_id = "10455";

            if (getV("sq_content") == "") {
                top.msgAlert("信件内容不能为空!");
                return;
            } else {
                sq.sq_content = getV("sq_content");
                sq.sq_content2 = getV("sq_content");
            }
            if ($("#sq_title").val() == "") {
                top.msgAlert("信件标题不能为空!");
                return;
            } else {
                sq.sq_title = $("#sq_title").val();
                sq.sq_title2 = $("#sq_title").val();
            }
            if ($(":radio[name='do_dept']:checked").val() == "") {
                top.msgAlert("请选择收件单位或人!");
                return;
            } else {
                sq.do_dept = $(":radio[name='do_dept']:checked").val() + "";
            }
            var tag_ids = $(":checkbox[id='tag_id']").getCheckValues() + "";

            var sq_atta_path = "";
            var sq_atta_name = "";
            var submit_name = $(":radio[name='do_dept']:checked").parent().attr("title");
            sq.submit_name = submit_name;
            var SQAttBean = BeanUtil.getCopy(SQAttachment);

            if (sq_atta_path != "") {
                SQAttBean.att_name(sq_atta_path);
                SQAttBean.att_path(sq_atta_name);
            }
            if (SQRPC.insertSQ(sq, SQAttBean)) {
                top.msgAlert("操作成功!");
                window.location.href = "/sys/appeal/model/modelList.jsp";
            } else {
                top.msgWargin("操作失败!");
            }
        }

        function iniSQbox() {
            $(".sq_title_box").bind('click', function () {
                if ($(this).find(".sq_title_right").text() == "点击闭合") {
                    $(this).find(".sq_title").removeClass("sq_title_minus").addClass("sq_title_plus");
                    $(this).find(".sq_title_right").text("点击展开");
                    $(this).parent().find(".sq_box_content").hide(300);
                }
                else {
                    $(this).find(".sq_title").removeClass("sq_title_plus").addClass("sq_title_minus");
                    $(this).find(".sq_title_right").text("点击闭合");
                    $(this).parent().find(".sq_box_content").show(300);
                }
            })
        }

    </script>
</head>
<body>
<div id="sq_info_table">
    <span class="blank3"></span>
    <div class="sq_box">
        <div class="sq_title_box">
            <div class="sq_title sq_title_minus">处理单位</div>
            <div class="sq_title_right">点击闭合</div>
        </div>
        <div class="sq_box_content">
            <table class="table_view" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <th><span class="f_red">*</span>收件单位/人：</th>
                    <td>
                        <ul id="dept_list" width="100%"></ul>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <span class="blank6"></span>
    <div class="sq_box">
        <div class="sq_title_box">
            <div class="sq_title sq_title_minus">信件特征</div>
            <div class="sq_title_right">点击闭合</div>
        </div>
        <div class="sq_box_content">
            <table class="table_view" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <th>诉求目的：</th>
                    <td>
                        <ul id="purpose_list" width="100%"></ul>
                    </td>
                </tr>
                <tr>
                    <th>内容分类：</th>
                    <td>
                        <input type="text" id="cat_name" value="" class="width200" readOnly="readOnly"/>
                        <div id="cat_tree_div" class="select_div tip hidden border_color">
                            <div id="leftMenuBox">
                                <div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
                                    <ul id="leftMenuTree" class="easyui-tree tree_list_none" animate="true">
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>发生地区：</th>
                    <td>
                        <input type="text" id="area_name" value="" class="width200" readOnly="readOnly"/><input
                            type="hidden" id="area_id" value=""/>
                        <div id="area_tree_div" class="select_div tip hidden border_color">
                            <div id="areaBox">
                                <div id="areaMenu" class="contentBox6 textLeft" style="overflow:auto">
                                    <ul id="areaTree" class="easyui-tree tree_list_none" animate="true">
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>特征标记：</th>
                    <td>
                        <ul id="tag_list">
                        </ul>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <span class="blank6"></span>
    <div class="sq_box">
        <div class="sq_title_box">
            <div class="sq_title sq_title_minus">写信人信息</div>
            <div class="sq_title_right">点击闭合</div>
        </div>
        <div class="sq_box_content">
            <table class="table_view" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <th><span class="f_red">*</span>姓名：</th>
                    <td><input id="sq_realname" name="sq_realname" type="text" value=""/>
                        <div class="cError"></div>
                    </td>
                    <th>性别：</th>
                    <td>
                        <ul>
                            <li><input id="sq_sex" name="sq_sex" type="radio" value="1" checked="checked"/>
                                <label for="d">男</label>
                            </li>
                            <li><input id="sq_sex" name="sq_sex" type="radio" value="0"/>
                                <label for="e">女</label>
                            </li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <th>年龄：</th>
                    <td><input id="sq_age" name="sq_age" type="text" value=""/></td>
                    <th>固定电话：</th>
                    <td><input id="sq_tel" name="sq_tel" type="text" value=""/></td>
                </tr>
                <tr>
                    <th>手机：</th>
                    <td><input id="sq_phone" name="sq_phone" type="text" value=""/>
                        <div class="cError"></div>
                    </td>
                    <th>身份证：</th>
                    <td><input id="sq_card_id" name="sq_card_id" type="text" value=""/></td>
                </tr>
                <tr>
                    <th>Email：</th>
                    <td><input id="sq_email" name="sq_email" type="text" value=""/>
                        <div class="cError"></div>
                    </td>
                    <th>职业：</th>
                    <td>
                        <select id="sq_vocation" name="sq_vocation">
                            <option value="工人">工人</option>
                            <option value="教师">教师</option>
                            <option value="农民">农民</option>
                            <option value="医生">医生</option>
                            <option value="现役军人">现役军人</option>
                            <option value="公务员">公务员</option>
                            <option value="学生">学生</option>
                            <option value="个体经营者">个体经营者</option>
                            <option value="企业管理人员">企业管理人员</option>
                            <option value="专业技术人员">专业技术人员</option>
                            <option value="事业单位工作人员">事业单位工作人员</option>
                            <option value="律师">律师</option>
                            <option value="文体人员">文体人员</option>
                            <option value="自由职业者">自由职业者</option>
                            <option value="无业人员">无业人员</option>
                            <option value="退（离）休人员">退（离）休人员</option>
                            <option value="其它">其它</option>
                        </select>
                    </td>
                </tr>
            </table>
            <table class="table_view" border="0" cellpadding="0" cellspacing="0">
                <tr id="sq_title_info_tr">
                    <th>住址：</th>
                    <td><input id="sq_address" name="sq_address" type="text" style="width:500px;"/></td>
                </tr>
            </table>
        </div>
    </div>
    <span class="blank6"></span>
    <div class="sq_box">
        <div class="sq_title_box">
            <div class="sq_title sq_title_minus">信件信息</div>
            <div class="sq_title_right">点击闭合</div>
        </div>
        <div class="sq_box_content">
            <table class="table_form" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <th><span class="f_red">*</span>信件标题：</th>
                    <td><input id="sq_title" name="sq_title" type="text" style="width:500px;" value=""/>
                        <div class="cError"></div>
                    </td>
                </tr>
                <tr>
                    <th><span class="f_red">*</span>信件内容：</th>
                    <td>
                        <script id="sq_content" type="text/plain" style="width:620px;height:200px;"></script>
                    </td>
                </tr>
                <tr>
                    <th><span class="f_red">*</span>公开意愿：</th>
                    <td>
                        <ul>
                            <li><input id="d4" name="is_open" type="radio" value="1" checked="checked"/>
                                <label for="d4">同意</label>
                            </li>
                            <li><input id="e4" name="is_open" type="radio" value="0"/><label for="e4">不同意</label></li>
                        </ul>
                        如果您选择"不同意",我们可能将对您的写信内容及办理结果不进行公示!
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <span class="blank12"></span>
    <div style="text-indent:40px;">
        <input id="btnOK" name="btnOK" type="submit" onclick="SubmitLetterInfo()" value="提交"/>
        <input id="btnReSet" name="btnReSet" type="button" onclick="window.history.go(-1)" value="取消"/>
    </div>
    <span class="blank12"></span>
    <div class="line2h"></div>
    <span class="blank3"></span>
</div>
</body>
</html>
