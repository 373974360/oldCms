var SQModelRPC = jsonrpc.SQModelRPC;
var SQRPC = jsonrpc.SQRPC;
var CpUserRPC = jsonrpc.CpUserRPC;
var SiteRPC = jsonrpc.SiteRPC;
var SQBean = new Bean("com.deya.wcm.bean.appeal.sq.SQBean", true);
var ProcessBean = new Bean("com.deya.wcm.bean.appeal.sq.ProcessBean", true);
var SQCustom = new Bean("com.deya.wcm.bean.appeal.sq.SQCustom", true);

var site_id = SiteRPC.getSiteIDByAppID('appeal');
var selectBean = null;//当前选中项对象
var val = new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();
table.table_name = "sq_table";
var pur_map = new Map();

function reloadSQList(show_type) {

    showTurnPage();
    showList(show_type);
}

function updateTopTreeCount() {
    top.getAppealSQCountByURL(window.location.href);
}

function initTable() {

    var colsMap = new Map();
    var colsList = new List();

    colsList.add(setTitleClos("sq_title2", "信件标题", "", "", "", ""));//英文名，显示名，宽，高，样式名，点击事件　
    //colsList.add(setTitleClos("sq_realname","来信人","100px","","",""));
    if (sq_status == 3)
        colsList.add(setTitleClos("over_dtime", "办结时间", "80px", "", "", ""));
    colsList.add(setTitleClos("sq_ip", "写信人IP", "52px", "", "", ""));
    colsList.add(setTitleClos("do_dept_name", "处理部门", "52px", "", "", ""));
    colsList.add(setTitleClos("sq_status", "信件状态", "52px", "", "", ""));
    colsList.add(setTitleClos("model_id", "递交渠道", "52px", "", "", ""));
    colsList.add(setTitleClos("pur_name", "诉求目地", "52px", "", "", ""));
    colsList.add(setTitleClos("submit_name", "收信单位", "52px", "", "", ""));
    colsList.add(setTitleClos("sq_dtime", "来信时间", "80px", "", "", ""));
    colsList.add(setTitleClos("xj_flag", "信件标识", "70px", "", "", ""));
    colsList.add(setTitleClos("publish_status", "发布", "40px", "", "", ""));
    colsList.add(setTitleClos("weight", "权重", "30px", "", "", ""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id
}

function showList(show_type) {

    var sortCol = table.sortCol;
    var sortType = table.sortType;
    if (sortCol == "" || sortCol == null) {
        sortCol = "sq_dtime";
        sortType = "desc";
    }

    m.put("start_num", tp.getStart());
    m.put("page_size", tp.pageSize);
    m.put("sort_name", sortCol);
    m.put("sort_type", sortType);
    if (table.con_value.trim() != "" && table.con_value != null) {
        m.put("con_name", table.con_name);
        m.put("con_value", table.con_value);
    }
    if (table_type == "transact")//判断是否显示已办理列表
        beanList = SQRPC.getTransactSQList(m);//第一个参数为站点ID，暂时默认为空
    else
        beanList = SQRPC.getSqList(m);
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show("table");

    table.getCol("sq_title2").each(function (i) {
        if (i > 0) {
            if (show_type == "transact")
                $(this).html('<a href="javascript:openSQManager2(' + beanList.get(i - 1).sq_id + ')">' + beanList.get(i - 1).sq_title2 + '</a>');
            else
                $(this).html('<a href="javascript:openSQManager(' + beanList.get(i - 1).sq_id + ')">' + beanList.get(i - 1).sq_title2 + '</a>');
        }
    });

    table.getCol("sq_dtime").each(function (i) {
        if (i > 0) {
            $(this).text(beanList.get(i - 1).sq_dtime.substring(0, 10));
        }
    });

    table.getCol("sq_ip").each(function (i) {
        if (i > 0) {
            $(this).text(beanList.get(i-1).sq_ip)
        }
    })

    table.getCol("over_dtime").each(function (i) {
        if (i > 0) {
            $(this).text(beanList.get(i - 1).over_dtime.substring(0, 10));
        }
    });

    table.getCol("pur_name").each(function (i) {
        if (i > 0) {
            $(this).text(pur_map.get(beanList.get(i - 1).pur_id));
        }
    });

    table.getCol("sq_status").each(function (i) {
        if (i > 0) {
            $(this).html(getSQState(beanList.get(i - 1).sq_status));
        }
    });

    table.getCol("publish_status").each(function (i) {
        if (i > 0) {
            if (beanList.get(i - 1).publish_status == 0)
                $(this).html('未发布');
            if (beanList.get(i - 1).publish_status == 1)
                $(this).html('已发布');
        }
    });

    table.getCol("model_id").each(function (i) {
        if (i > 0) {
            $(this).text(getModelName(beanList.get(i - 1).model_id));
        }
    });

    table.getCol("xj_flag").each(function (i) {
        if (i > 0) {
            var str = '<ul class="optUL">';
            if (beanList.get(i - 1).supervise_flag == 1)
                str += '<li class="ico_duban" title="督办"></li>';
            if (beanList.get(i - 1).alarm_flag == 1)
                str += '<li class="ico_warn" title="预警"></li>';
            if (beanList.get(i - 1).alarm_flag == 2)
                str += '<li class="ico_yello" title="黄牌"></li>';
            if (beanList.get(i - 1).alarm_flag == 3)
                str += '<li class="ico_red" title="红牌"></li>';
            if (beanList.get(i - 1).timeout_flag == 1)
                str += '<li class="ico_out" title="超期"></li>';
            str += '</ul>';
            $(this).html(str);
        }

    });
    Init_InfoTable(table.table_name);
}

function getSQState(n) {
    if (n == 0)
        return "待处理";
    if (n == 1)
        return "处理中";
    if (n == 2)
        return "待审核";
    if (n == 3)
        return "已办结";
}

//循环业务列表得到业务名称
function getModelName(m_id) {
    if (p_list != null && p_list.size() > 0) {
        for (var i = 0; i < p_list.size(); i++) {
            try {
                if (p_list.get(i).model_id != null && p_list.get(i).model_id == m_id)
                    return p_list.get(i).model_cname;
            } catch (e) {
            }
        }
    }
    return "";
}

function showTurnPage() {
    if (table_type == "transact")//判断是否显示已办理列表
        tp.total = SQRPC.getTransactSQCount(m);
    else
        tp.total = SQRPC.getSqCount(m);
    tp.show("turn", "");
    tp.onPageChange = showList;
}

function openSQManager(s_id) {
    top.addTab(true, "/sys/appeal/sq/sq_add.jsp?sq_id=" + s_id + "&site_id=" + site_id + "&top_index=" + top.curTabIndex, "信件管理");
    //window.location.href = "/sys/appeal/sq/sq_add.jsp?sq_id="+s_id;
}

function openSQManager2(s_id) {
    top.addTab(true, "/sys/appeal/sq/sq_add2.jsp?sq_id=" + s_id + "&site_id=" + site_id + "&top_index=" + top.curTabIndex, "信件管理");
    //window.location.href = "/sys/appeal/sq/sq_add.jsp?sq_id="+s_id;
}

//置为重复件操作
var initial_sq_id = "";
function setChongfu() {
    initial_sq_id = $(":radio[id='initial_sq_id']:checked").val();

    if ($(":radio[id='initial_sq_id']:checked").length == 0) {
        top.msgAlert("请选择重复信件");
        return;
    }
    insertProcess(5);
}

var _pro_type;//定义一个全局参数
//过程处理
function insertProcess(pro_type) {
    var pro_note = getV("pro_note");
    /*
     if(pro_note.trim() == "" && pro_note.length == 0 && pro_type != 6)
     {
     var msg = $("#pro_title_th").text();
     msg = msg.substring(0,msg.length-1);
     top.msgWargin(msg+"项不能为空");
     return;
     }*/

    //首先判断是否有上传的附件，并且附件对象为null
    if (fileCount > 0 && attBean == null) {
        $("#uploadify").uploadifySettings('scriptData', {
            'app_id': 'appeal',
            'sid': jsonrpc.MateInfoRPC.getUploadSecretKey()
        });
        //执行上传程序
        _pro_type = pro_type;
        jQuery('#uploadify').uploadifyUpload();
        return;
    }

    var sqMap = new Map();//用于存储需修改诉求主表的信息
    sqMap.put("sq_id", sq_id);
    sqMap.put("model_id", defaultBean.model_id + "");
    sqMap.put("pro_type", pro_type + "");
    if (defaultBean.do_dept == 0) {
        sqMap.put("do_dept", current_dept_id);//受理时，判断处理部门是否为空，如果为空，写入当前部门人员所在的部门
    }

    if (defaultBean.accept_dtime == "") {
        sqMap.put("accept_dtime", "");
    }
    switch (pro_type) {
        case 0:
            sqMap.put("sq_status", 1 + "");//受理时，流程处理状态改为处理中
            sqMap.put("is_back", 0);//回退标识 0：正常
            break;
        case 1:
            if (model_bean.wf_id > 0 && is_admin == false)////判断是否需要审核(流程ID大于0，判断是否是管理员，如果是不要审核，一步到位)
            {
                //根据人员ID得到下一步的步骤ID
                var step_id = jsonrpc.WorkFlowRPC.getNextStepIDByUserID(model_bean.wf_id, top.LoginUserBean.user_id, "appeal", "");
                if (step_id == 100) {//办结完了
                    sqMap.put("over_dtime", $("#over_dtime").val());//办结时间
                    sqMap.put("sq_status", 3 + "");
                    sqMap.put("step_id", 100);//流程状态码 100：终审通过
                } else {
                    sqMap.put("step_id", step_id);//记录下一步的步骤
                    sqMap.put("sq_status", 2 + "");//待审核
                }
            } else {
                sqMap.put("over_dtime",  $("#over_dtime").val());//办结时间
                sqMap.put("sq_status", 3 + "");
                sqMap.put("step_id", 100);//流程状态码 100：终审通过
            }
            sqMap.put("is_back", 0);//回退标识 0：正常
            sqMap.put("publish_status", $(":radio[id='publish_status']:checked").val() + "");//发布标识
            sqMap.put("publish_dtime", "");
            sqMap.put("is_open", $(":radio[id='is_open_r']:checked").val());
            sqMap.put("sq_reply", pro_note);
            sqMap.put("custom_dept_name", $("#custom_dept_name").val());
            break;
        case 2:
            sqMap.put("sq_status", 1 + "");//转办时，流程处理状态改为处理中
            sqMap.put("do_dept", $(":radio[id='do_dept']:checked").val());//设置转办部门
            sqMap.put("prove_type", 2 + "");//修改　信件原始办理类型 转办 交办
            sqMap.put("is_back", 0);//回退标识 0：正常
            if (sqMap.get("do_dept") == null || sqMap.get("do_dept") == "") {
                top.msgWargin("请选择转办单位");
                return;
            }
            break;
        case 3:
            sqMap.put("sq_status", 1 + "");//交办时，流程处理状态改为处理中
            sqMap.put("do_dept", $(":r:checkedpt']:checked").val());
            sqMap.put("prove_type", 3 + "");//修改　信件原始办理类型 转办 交办
            sqMap.put("is_back", 0);//回退标识 0：正常
            if (sqMap.get("do_dept") == null || sqMap.get("do_dept") == "") {
                top.msgWargin("请选择交办单位");
                return;
            }
            break;
        case 4:
            sqMap.put("sq_status", 1 + "");//呈办时，流程处理状态改为处理中
            sqMap.put("do_dept", $(":radio[id='do_dept']:checked").val());
            sqMap.put("prove_type", 4 + "");//修改　信件原始办理类型 转办 交办
            sqMap.put("is_back", 0);//回退标识 0：正常
            if (sqMap.get("do_dept") == null || sqMap.get("do_dept") == "") {
                top.msgWargin("请选择呈办单位");
                return;
            }
            break;
        case 5:
            sqMap.put("sq_flag", 1);//1：重复信件
            sqMap.put("initial_sq_id", initial_sq_id);

            if (model_bean.wf_id > 0 && is_admin == false)//判断是否需要审核(还要判断是否是管理员，如果是不要审核，一步到位)，如果要审核，流程处理状态　改为2：待审核　否则改为3：已办结
            {
                var step_id = jsonrpc.WorkFlowRPC.getNextStepIDByUserID(model_bean.wf_id, top.LoginUserBean.user_id, "appeal", "");
                if (step_id == 100) {
                    sqMap.put("sq_status", 3 + "");//已办结
                    sqMap.put("over_dtime", "");//办结时间
                    sqMap.put("step_id", 100);//流程状态码 100：终审通过
                    sqMap.put("is_back", 0);//回退标识 0：正常
                } else {
                    sqMap.put("sq_status", 2 + "");
                    sqMap.put("step_id", step_id);
                }
            }
            else {
                sqMap.put("sq_status", 3 + "");//已办结
                sqMap.put("over_dtime", "");//办结时间
                sqMap.put("step_id", 100);//流程状态码 100：终审通过
            }
            sqMap.put("is_back", 0);//回退标识 0：正常
            sqMap.put("sq_reply", pro_note);
            break;
        case 6:
            sqMap.put("sq_flag", -1);//置为无效状态
            sqMap.put("sq_status", 3 + "");//办结
            sqMap.put("step_id", 100);//流程状态码 100：终审通过
            sqMap.put("is_back", 0);//回退标识 0：正常
            break;
        case 7:
            sqMap.put("sq_flag", 2);//不予受理时，信件标识改为2
            if (model_bean.wf_id > 0 && is_admin == false)//判断是否需要审核(还要判断是否是管理员，如果是不要审核，一步到位)，如果要审核，流程处理状态　改为2：待审核　否则改为3：已办结
            {
                var step_id = jsonrpc.WorkFlowRPC.getNextStepIDByUserID(model_bean.wf_id, top.LoginUserBean.user_id, "appeal", "");
                if (step_id == 100) {
                    sqMap.put("over_dtime", "");//办结时间
                    sqMap.put("sq_status", 3 + "");
                    sqMap.put("step_id", 100);//流程状态码 100：终审通过
                    sqMap.put("is_back", 0);//回退标识 0：正常
                } else {
                    sqMap.put("sq_status", 2 + "");
                    sqMap.put("step_id", step_id);
                }
            } else {
                sqMap.put("over_dtime", "");//办结时间
                sqMap.put("sq_status", 3 + "");
                sqMap.put("step_id", 100);//流程状态码 100：终审通过
                sqMap.put("is_back", 0);//回退标识 0：正常
            }
            sqMap.put("is_back", 0);//回退标识 0：正常
            sqMap.put("sq_reply", pro_note);
            break;
        case 100://内容审核　根据人员ID得到下一步的步骤ID
            var step_id = jsonrpc.WorkFlowRPC.getNextStepIDByUserID(model_bean.wf_id, top.LoginUserBean.user_id, "appeal", "");

            if (is_admin == false && step_id != 100)//内容审核 如果不是管理员 且不是最后一级审核
            {
                if ($(":radio[id='auto_status']:checked").val() == 12) {
                    //审核打回
                    sqMap.put("is_back", 1);//退回标识
                    sqMap.put("sq_status", 1 + "");//处理中
                    sqMap.put("sq_flag", 0);//信件标识 信件标识都改为正常
                    sqMap.put("step_id", 0);

                } else {
                    sqMap.put("step_id", step_id);//流程步骤　
                    sqMap.put("sq_status", 2 + "");//待审核
                }

            } else {//以下是管理员和最后一级审核员的操作
                if ($(":radio[id='auto_status']:checked").val() == 12)//不通过
                {
                    sqMap.put("is_back", 1);//退回标识
                    sqMap.put("sq_status", 1 + "");//处理中
                    sqMap.put("sq_flag", 0);//信件标识 信件标识都改为正常
                    sqMap.put("step_id", 0);
                } else {
                    sqMap.put("over_dtime", "");//办结时间
                    sqMap.put("sq_status", 3 + "");
                    sqMap.put("step_id", 100);//流程状态码 100：终审通过
                    sqMap.put("is_back", 0);//回退标识 0：正常
                }
            }
            sqMap.put("is_open", $(":radio[id='is_open_r']:checked").val());
            sqMap.put("publish_status", $(":radio[id='publish_status']:checked").val() + "");
            sqMap.put("publish_dtime", "");
            break;
        case 8:
            sqMap.put("limit_flag", 2);//延时申请标识 2：申请延时
            sqMap.put("is_back", 0);
            break;
        case 101:
            if ($(":radio[id='auto_status']:checked").val() == 9) //延期审核
            {//审核通过
                sqMap.put("limit_flag", 1);
                sqMap.put("is_back", 0);//回退标识 0：正常

                if (!val.checkEmpty($("#limit_data").val(), "延期天数", "limit_data") || !val.checkInt($("#limit_data").val(), "延期天数", "limit_data")) {
                    return;
                }

                sqMap.put("time_limit", parseInt(defaultBean.time_limit) + parseInt($("#limit_data").val()));
                sqMap.put("timeout_flag", 0);//延期审核　超期标识改为　0
                sqMap.put("alarm_flag", 0);//延期审核　预警标识改为　0
            }
            else {
                sqMap.put("is_back", 1);
            }
            break;
        case 13:
            sqMap.put("supervise_flag", $(":radio[id=supervise_flag]:checked").val());//督办状态
            break;
        case 102:
            sqMap.put("publish_status", $(":radio[id='publish_status']:checked").val() + "");//发布标识
            sqMap.put("is_open", $(":radio[id='is_open_r']:checked").val());
            sqMap.put("sq_title2", $("#sq_title2_r").val());
            sqMap.put("sq_content2", getV("sq_content2_r"));
            sqMap.put("sq_reply", pro_note);
            sqMap.put("weight", $("#weight").val());
            sqMap.put("publish_dtime", "");
            sqMap.remove("do_dept");//发布时不修改操作部门
            break;
        default:
            break;
    }

    var is_submit = false;
    if (pro_type != 102)//102为发布，发布不写过程表
    {
        var bean = BeanUtil.getCopy(ProcessBean);

        if (pro_type == 100 || pro_type == 101)//审核时取审核状态
        {
            bean.pro_type = $(":radio[id='auto_status']:checked").val();
        } else
            bean.pro_type = pro_type;
        bean.pro_note = pro_note;
        bean.sq_id = sq_id;
        bean.do_dept = current_dept_id;//过程表中的处理部门
        bean.old_dept_id = defaultBean.do_dept;//原处理部门
        bean.old_sq_status = defaultBean.sq_status;//上步流程处理状态
        bean.old_prove_type = defaultBean.prove_type;//上步信件原始办理类型
        if (pro_type == 2 || pro_type == 3 || pro_type == 4) {
            //转交呈时记录下一步的提交部门名称
            bean.to_dept_name = CpDeptRPC.getCpDeptBean(sqMap.get("do_dept")).dept_name;
        }
        is_submit = (SQRPC.updateStatus(sqMap) && SQRPC.insertProcess(bean, attBean));

    } else {
        is_submit = SQRPC.updateStatus(sqMap);
    }

    if (is_submit) {
        top.msgAlert(WCMLang.Add_success);
        if (pro_type != 5) {
            try {
                top.getCurrentFrameObj(top_index).reloadSQList();
                top.getCurrentFrameObj(top_index).updateTopTreeCount();
            } catch (e) {
            }
        }
        else {
            try {
                top.getCurrentFrameObj(top_index).getProcessList();
                top.getCurrentFrameObj(top.getCurrentFrameObj(top_index).top_index).updateTopTreeCount();
                top.getCurrentFrameObj(top.getCurrentFrameObj(top_index).top_index).reloadSQList();
            } catch (e) {
            }
        }
        top.tab_colseOnclick(top.curTabIndex);
    } else {
        top.msgAlert(WCMLang.Add_fail);
    }
}
//得到业务列表
function getModelList() {
    p_list = SQModelRPC.getAllSQModelList();
    p_list = List.toJSList(p_list);

    $("#model_id").addOptions(p_list, "model_id", "model_cname");
}

//根据人员得到业务列表
function getModelListSByUserID() {
    p_list = SQModelRPC.getModelListSByUserID(top.LoginUserBean.user_id);
    p_list = List.toJSList(p_list);
    $("#model_id").addOptions(p_list, "model_id", "model_cname");

    var ids = "";
    $("#model_id option").each(function (i) {
        if (i > 0) {
            ids += "," + $(this).val();
        }
    })
    if (ids != "")
        ids = ids.substring(1);

    $("#model_id option[value='']").val(ids);
}

//取得常用语信息
var CommonLangRPC = jsonrpc.CommonLangRPC;
var lang_map = new Map();
function getCommonLangListByType(pro_type) {
    if (pro_type < 100) {
        var lang_list = CommonLangRPC.getCommonLangListByType(pro_type);
        lang_list = List.toJSList(lang_list);
        $("#quick_content").empty();
        $("#quick_content").addOptionsSingl("", "可选快速回复");
        if (lang_list != null && lang_list.size() > 0) {
            for (var i = 0; i < lang_list.size(); i++) {
                $("#quick_content").addOptionsSingl(lang_list.get(i).ph_id, lang_list.get(i).ph_title);
                lang_map.put(lang_list.get(i).ph_id, lang_list.get(i).ph_content);
            }
        }
    }
}

//根据常用语的选择，展现内容
function setSelectedCommonLang(c_type) {
    var content = "";
    if (c_type != "") {
        var content = lang_map.get(c_type);
        content = content.replace(/\{sq_code\}/ig, defaultBean.sq_code).replace(/\{sq_realname\}/ig, defaultBean.sq_realname).replace(/\{sq_email\}/ig, defaultBean.sq_email).replace(/\{sq_title2\}/ig, defaultBean.sq_title2).replace(/\{submit_name\}/ig, defaultBean.submit_name).replace(/\{sq_dtime\}/ig, defaultBean.sq_dtime).replace(/\{model_cname\}/ig, model_bean.model_cname).replace(/\{dept_name\}/ig, getDoDept()).replace(/\{dtime\}/ig, getCurrentDateTime());

    }
    setV("pro_note", content);
}

//得到处理部门名称
var CpDeptRPC = jsonrpc.CpDeptRPC;
function getDoDept() {
    if (defaultBean.do_dept == 0) {
        return CpDeptRPC.getCpDeptBean(current_dept_id).dept_name;
    }
    else {
        return CpDeptRPC.getCpDeptBean(defaultBean.do_dept).dept_name;
    }
}

var attBean = null;
var fileCount = 0;
function initUPLoad() {
    var sizeLimit = SQRPC.getAppealFileSize();

    $("#uploadify").uploadify({//初始化函数
        'uploader': '/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
        'script': '/servlet/PeculiarUploadFile',//后台处理的请求
        'cancelImg': '/sys/js/uploadFile/cancel.png',//取消按钮图片
        'buttonImg': '/sys/js/uploadFile/upst.gif',
        'folder': 'uploads',//您想将文件保存到的路径
        'queueID': 'fileQueue',//与下面的上传文件列表id对应
        'queueSizeLimit': 100,//上传文件的数量
        //'scriptData':{'app_id':'appeal','sid':getSessionID()},//向后台传的数据
        'fileDesc': 'txt,doc,docx,rar,zip',//上传文件类型说明
        'fileExt': '*.txt;*.doc;*.docx;*.rar;*.zip', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
        'method': 'get',//如果向后台传输数据，必须是get
        'sizeLimit': sizeLimit,//文件上传的大小限制，单位是字节
        'auto': true,//是否自动上传
        'multi': false,
        'simUploadLimit': 2,//同时上传文件的数量
        'buttonText': 'BROWSE',//浏览按钮图片
        'auto': false,//点击选择直接上传图片
        'onSelect': function (event, queueID, fileObj) {//选择完后触发的事件
            //$("#uploadify").uploadifySettings('scriptData',{'app_id':'appeal','sid':jsonrpc.MateInfoRPC.getUploadSecretKey()});
            if (fileObj.size > sizeLimit) {
                alert("附件太大");
                return;
            }
        },
        'onSelectOnce': function (event, data) {
            fileCount = data.fileCount;
        },
        'onCancel': function (event, queueId, fileObj, data) {
            fileCount = data.fileCount;
        },
        'onComplete': function (event, queueID, fileObj, serverData, response, data) {//当上传完成后的回调函数
            var s = serverData;
            attBean = BeanUtil.getCopy(attBean);
            attBean.att_name = fileObj.name;
            attBean.att_path = serverData;
            insertProcess(_pro_type);
        }
    });
}

function getSQAttInfo(s_id, relevance_type, a_name) {
    var att_list = SQRPC.getSQAttachmentList(s_id, relevance_type);
    att_list = List.toJSList(att_list);
    if (att_list != null && att_list.size() > 0) {
        for (var i = 0; i < att_list.size(); i++) {
            var str = '<a href="downAttInfo.jsp?sq_id=' + att_list.get(i).sq_id + '&atype=' + relevance_type + '" target="_blank">' + att_list.get(i).att_name + '</a>';
            if (a_name == "sq_att_td")
                $("#" + a_name).html(str);
            else
                $("#" + a_name + "_" + att_list.get(i).sq_id).html(str);
        }
    }
}

function deleteSQ() {
    var selectIDS = table.getSelecteCheckboxValue("sq_id");
    if (SQRPC.deleteSQ(selectIDS)) {
        top.msgAlert("信件" + WCMLang.Delete_success);
        reloadSQList();
    } else {
        top.msgWargin("信件" + WCMLang.Delete_fail);
    }
}

//切换搜索选项
function showTagSelect(val) {
    if (val == "tag") {
        $("#btnSearch").hide();
        $("#searchkey").hide();
        $("#tag_select").show();
    }
    else {
        $("#btnSearch").show();
        $("#searchkey").show();
        $("#tag_select").hide();
    }
}

//特征搜索
function pcSearchForTag(val) {
    if (val == "")
        return;
    m.remove("search_date");
    m.put("tag_ids", val);
    reloadSQList();
}

//加载特征标识列表
function reloadTagList() {
    var t_list = jsonrpc.AssistRPC.getTagListByAPPSite("appeal", "");
    t_list = List.toJSList(t_list);
    $("#tag_select").addOptions(t_list, "tag_id", "tag_name");
}


//得到诉求目地列表
function getPurposeList() {
    var l = jsonrpc.PurposeRPC.getPurposeList();
    l = List.toJSList(l);
    if (l != null && l.size() > 0) {
        $("#pur_id").addOptions(l, "pur_id", "pur_name");
        for (var i = 0; i < l.size(); i++)
            pur_map.put(l.get(i).pur_id, l.get(i).pur_name);
    }
}
//业务查询
function modelSearch(vals) {

    m.put("model_id", vals);
    m.remove("tag_ids");
    m.remove("search_date");
    m.remove("pur_id");
    m.remove("submit_name");
    m.remove("sq_status");
    if (sq_status != "" && sq_status != "null" && sq_status != null) {
        m.put("sq_status", sq_status);//流程处理状态
    }
    getAppealDeptList(vals);//得到业务中的部门或领导人信息
    reloadSQList();
}
//得到业务中的部门或领导人信息
function getAppealDeptList(vals) {
    $("#submit_names").empty();
    $("#submit_names").addOptionsSingl("", "全部");
    if (vals.indexOf(",") == -1) {
        var d_list = SQModelRPC.getAppealDeptList(vals);
        d_list = List.toJSList(d_list);
        $("#submit_names").addOptions(d_list, "dept_name", "dept_name");
    }
}
//部门查询
function deptSearch(val) {
    m.put("model_id", $("#model_id").val());
    m.put("submit_name", val);
    m.remove("search_date");
    m.remove("tag_ids");
    m.remove("pur_id");
    m.remove("sq_status");
    if (sq_status != "" && sq_status != "null" && sq_status != null) {
        m.put("sq_status", sq_status);//流程处理状态
    }
    reloadSQList();
}

//诉求目录查询
function purSearch(vals) {
    if (vals != "" && vals != null) {
        m.put("pur_id", vals);
    } else {
        m.remove("pur_id");
    }
    m.remove("search_date");
    m.remove("model_id");
    m.remove("sq_status");
    m.remove("tag_ids");
    if (sq_status != "" && sq_status != "null" && sq_status != null) {
        m.put("sq_status", sq_status);//流程处理状态
    }
    reloadSQList();
}

//信件状态查询
function statusSearch(vals) {
    if (vals != "" && vals != null) {
        m.put("sq_status", vals);
    } else {
        m.remove("sq_status");
    }
    m.remove("search_date");
    m.remove("model_id");
    m.remove("tag_ids");
    m.remove("pur_id");
    reloadSQList();
}


//得到该登录人所具有的权限ID，显示操作按钮
function getOptIDSByUser() {
    var opt_ids = jsonrpc.UserLoginRPC.getOptIDSByUserAPPSite(top.LoginUserBean.user_id, "appeal", "");
    if (opt_ids != "" && opt_ids != null) {
        var tempA = opt_ids.split(",");
        for (var i = 0; i < tempA.length; i++) {
            $("#btn" + tempA[i]).show();
        }
    }
}

//信件收回
function withdrawSQForProcess() {
    top.msgConfirm("确认收回此信件？", "withdrawSQForProHandl()");
}

function withdrawSQForProHandl() {
    var selectIDS = table.getSelecteCheckboxValue("sq_id");

    var return_val = SQRPC.withdrawSQForProcess(selectIDS, top.LoginUserBean.user_id);
    if (return_val == "true") {
        top.msgAlert("信件收回成功，请在待处理信件列表中重新处理");
        reloadSQList();
    }
    if (return_val == "false") {
        top.msgWargin("信件收回失败，请重新进行收回操作");
        return;
    }
    if (return_val == "process_is_worked") {
        top.msgWargin("该信件已被处理，不能进行收回操作");
        return;
    }
}