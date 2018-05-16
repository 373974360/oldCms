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

function fnUpdateSurveySetting()
{
    var ywlsh = table.getSelecteCheckboxValue("ywlsh");
    searchMap.put("ywlsh",ywlsh);
    searchMap.put("state","1");
    if(SurveySettingRPC.updateSurveySettingState(searchMap))
    {
        parent.msgAlert("修改调查配置状态成功");
        showList();
    }
    else
        parent.msgWargin("修改调查配置状态成功失败，请重试");
}



