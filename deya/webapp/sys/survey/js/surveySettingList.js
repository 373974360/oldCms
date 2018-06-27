var SurveySettingRPC = jsonrpc.SurveySettingRPC;
// var SurveySettingVo = new Bean("com.yinhai.model.SurveySettingVo",true);
var val = new Validator();
var beanList = null;
var table = new Table();
var searchMap = new Map();
table.table_name = "table";

/********** 显示table begin*************/

function reloadSettingList() {
    showList();
}

function initTable() {
    table = new Table();
    var colsList = new List();

    colsList.add(setTitleClos("ywlsh", "ID", "20px", "", "", ""));
    colsList.add(setTitleClos("question_title", "问卷名称", "120px", "", "", ""));//英文名，显示名，宽，高，样式名，点击事件　
    colsList.add(setTitleClos("question_desc", "问卷描述", "130px", "", "", ""));
    colsList.add(setTitleClos("create_date", "创建时间", "60px", "", "", ""));
    colsList.add(setTitleClos("creator", "创建人", "50px", "", "", ""));
    colsList.add(setTitleClos("start_date", "问卷起始时间", "90px", "", "", ""));
    colsList.add(setTitleClos("end_date", "问卷结束时间", "90px", "", "", ""));
    colsList.add(setTitleClos("question_file_name", "问卷附件名称", "90px", "", "", ""));
    colsList.add(setTitleClos("state", "状态", "50px", "", "", ""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort = false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id
}

function showList() {
    // searchMap.put("state","0");
    beanList = SurveySettingRPC.getSurveySettingList(searchMap);
    beanList = List.toJSList(beanList);//把list转成JS的List对象
    curr_bean = null;
    table.setBeanList(beanList, "td_list");//设置列表内容的样式
    table.show();
    table.getCol("question_title").each(function (i) {
        if (i > 0) {
            $(this).css({"text-align": "left"});
            $(this).html('<span onclick="showUpdatePage(\'' + beanList.get(i - 1).ywlsh + '\')" style="cursor:pointer">' + $(this).html() + '</span>');
        }
    });

    table.getCol("create_date").each(function (i) {
        if (i > 0) {
            $(this).html(cutTimes(beanList.get(i - 1).create_date));
        }
    });

    table.getCol("start_date").each(function (i) {
        if (i > 0) {
            $(this).html(cutTimes(beanList.get(i - 1).start_date));
        }
    });

    table.getCol("end_date").each(function (i) {
        if (i > 0) {
            $(this).html(cutTimes(beanList.get(i - 1).end_date));
        }
    });

    table.getCol("question_file_name").each(function (i) {
        if (i > 0) {
            var question_file_name = beanList.get(i - 1).question_file_name;
            var html = '';
            if(question_file_name != null && question_file_name != ""){
                var files = question_file_name.split("|");
                for(var j = 0; j < files.length; j++){
                    html += "<a href='/upload/sftpFiles/" + files[j] + "' >" + files[j] + "</a>";
                }
            }
            $(this).html(html);
        }
    });



    table.getCol("state").each(function (i) {//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
        if (i > 0) {
            if (beanList.get(i - 1).state == 0)
                $(this).html("未配置");
            if (beanList.get(i - 1).state == 1)
                $(this).html("已配置");
        }
    });

    Init_InfoTable(table.table_name);
}

/********** 显示table end*************/



/**********************添加操作　开始*************************************/
function fnAddSurvey()
{
    var ywlsh = table.getSelecteCheckboxValue("ywlsh");//业务流水号
    var file_path = table.getSelecteCheckboxValue("question_file_name");//附件
    var wjbh = table.getSelecteCheckboxValue("question_code");//问卷编号
    var fbqd = table.getSelecteCheckboxValue("source");//发布渠道
    var curinfo = table.getSelecteCheckboxValue("curinfo");//审核流程
    var depname = table.getSelecteCheckboxValue("depname");//发起部门
    var creator = table.getSelecteCheckboxValue("creator");//发起人
    var create_date = table.getSelecteCheckboxValue("create_date");//发起时间
    var start_date = table.getSelecteCheckboxValue("start_date");//起始时间
    var end_date = table.getSelecteCheckboxValue("end_date");//结束时间

    //var ywlsh = "451";//业务流水号
    //var file_path = "filepath/123.xls";//附件
    //var wjbh = "BH89757";//问卷编号
    //var fbqd = "线下发布、门户、网厅、APP";//发布渠道
    //var curinfo = "[{result=同意, curname=超级用户, curdepname=办公室, stepname=办公室负责人审核, nextstepname=分管领导审核, remark=非官方顶呱呱反对发个, curdate=2018-06-25 21:06:37, curloginid=118001}, {result=null, curname=超级用户, curdepname=办公室, stepname=创建问卷, nextstepname=办公室负责人审核, remark=null, curdate=2018-06-25 21:05:57, curloginid=118001}, {result=hhhhhhhhhhhhhhhhhhhhhh, curname=超级用户, curdepname=办公室, stepname=分管领导审核, nextstepname=结束, remark=非官方顶呱呱反对发个|, curdate=2018-06-25 21:07:02, curloginid=118001}]";//审核流程
    //var depname = "办公室";//发起部门
    //var creator = "超级用户";//发起人
    //var create_date = "2018-06-25 21:05:49.0";//发起时间
    if(ywlsh == "" || ywlsh == "")
    {
        parent.msgWargin("请选择要配置的问卷");
        return;
    }
    if(ywlsh.split(",").length > 1)
    {
        parent.msgWargin("只能选择一条记录进行推送操作");
        return;
    }
    window.location.href = "add_survey.jsp?site_id="+site_id+"&ywlsh="+ywlsh+"&file_path="+file_path+"&wjbh="+wjbh+"&fbqd="+fbqd+"&curinfo="+curinfo+"&depname="+depname+"&creator="+creator+"&create_date="+create_date+"&start_date="+start_date+"&end_date="+end_date;
}
/**********************添加操作　结束*************************************/




