var DeptRPC = jsonrpc.DeptRPC;
var XxbsRPC = jsonrpc.XxbsRPC;


var deptBean = DeptRPC.getDeptBeanByID(LoginUserBean.dept_id);
var infoStatus = "1";
var tp = new TurnPage();
var table = new Table();
var current_page_num = 1;
var is_save_first_page = false;//操作成功后是否保存在第一页
var beanList = null;




function reloadInfoDataList()
{
    initTable();
    if(is_save_first_page == true)
        current_page_num = 1;

    tp.curr_page = current_page_num;
    showTurnPage();
    showList();
    is_save_first_page = false;
}

function initTable(){
    var colsMap = new Map();
    var colsList = new List();
    colsList.add(setTitleClos("title","标题","","","",""));//英文名，显示名，宽，高，样式名，点击事件
    colsList.add(setTitleClos("actions","管理操作","90px","","",""));
    colsList.add(setTitleClos("do_dept_name","报送单位","60px","","",""));
    colsList.add(setTitleClos("editor","报送人","60px","","",""));
    colsList.add(setTitleClos("released_dtime","报送时间","120px","","",""));
    if(infoStatus == 3)
        colsList.add(setTitleClos("auto_desc","退稿意见","","","",""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort=false;//禁用表头排序
    table.onSortChange = showList;
    table.show("table");//里面参数为外层div的id

}

function showList(){
    var sortCol = table.sortCol;
    if(sortCol == "" || sortCol == null)
    {
        sortCol = "1";
    }
    var m = new Map();
    m.put("dept_id",deptBean.dept_id+'');
    m.put("user_id",LoginUserBean.user_id+'');
    m.put("orderByFields",sortCol);
    if(table.con_value.trim() != "" && table.con_value != null){
        m.put("search", table.con_value);
    }
    m.put("info_status", infoStatus+'');
    m.put("start_num", tp.getStart()+'');
    m.put("page_size", tp.pageSize+'');

    beanList = XxbsRPC.getXxbsInfoList(m);
    beanList = List.toJSList(beanList);//把list转成JS的List对象

    table.setBeanList(beanList,"td_list");//设置列表内容的样式
    table.show("table");

    var isChangeDelete = false;
    //判断是否是站点管理员或超级管理员
    if(top.isSiteManager(app,site_id))
    {
        isChangeDelete = true;
    }

    table.getCol("title").each(function(i){
        $(this).css({"text-align":"left"});
        if(i>0)
        {
            var title_flag = "";
            var title_end_str = "";
            $(this).css("padding-left","20px");
            $(this).html('<a href="javascript:openViewPage('+beanList.get(i-1).info_id+')">'+title_flag+beanList.get(i-1).title+'</a>'+title_end_str);
        }
    });
    table.getCol("actions").each(function(i){
        if(i>0)
        {
            $(this).css({"text-align":"center"});
            var nm = parseInt(infoStatus);
            var str = "<ul class=\"optUL\">";
            switch(nm){
                case 1:
                    str += "<li id='539' class='ico_pass'><a title='采用' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    str += "<li id='540' class='ico_cancel'><a title='退稿' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    str += "<li id='537' class='ico_edit'><a title='修改' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    str += "<li id='538' class='ico_delete'><a title='删除' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    break;
                case 2:
                    str += "<li id='538' class='ico_delete' ><a title='删除' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    break;
                case 3:
                    str += "<li id='536' class='ico_submit'><a title='报送' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    str += "<li id='537' class='ico_edit'><a title='修改' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    str += "<li id='538' class='ico_delete' ><a title='删除' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                    break;
            }
            $(this).html(str+"</ul>");
        }
    });
    setUserOperateLI(table.table_name);
    current_page_num = tp.curr_page;
    Init_InfoTable(table.table_name);
}
function showTurnPage(){
    var sortCol = table.sortCol;
    if(sortCol == "" || sortCol == null)
    {
        sortCol = "1";
    }
    var m = new Map();
    m.put("dept_id",deptBean.dept_id+'');
    m.put("user_id",LoginUserBean.user_id+'');
    m.put("orderByFields",sortCol);
    if(table.con_value.trim() != "" && table.con_value != null){
        m.put("search", table.con_value);
    }
    m.put("info_status", infoStatus+'');
    tp.total = XxbsRPC.getXxbsInfoCount(m);
    tp.show("turn","");
    tp.onPageChange = showList;
}

function searchInfo(){
    var keywords = $("#searchkey").val();
    if(keywords.trim() == "" ||  keywords == null){
        top.msgAlert(WCMLang.Search_empty);
        return;
    }
    table.con_value = keywords;
    table.sortCol = $("#orderByFields").val();

    reloadInfoDataList();
}

function openAddInfoPage(){
    top.addTab(true,"/sys/project/dz_xxbs/info_add.jsp?topnum="+top.curTabIndex,"添加信息");
}

function setUserOperateLI(table_name)
{
    $("#"+table_name+" li[id]").hide();
    $("#"+table_name+" li[id]").each(function(){
        var o_id = ","+$(this).attr("id")+",";
        if(opt_ids.indexOf(o_id) > -1)
            $(this).show();
    });
}
function initTabAndStatus()
{
    $(".fromTabs > li").each(function(){
        $(this).hover(
            function () {
                $(this).addClass("list_tab_over");
            },
            function () {
                $(this).removeClass("list_tab_over");
            }
        );

        $(this).click(
            function () {
                $(".fromTabs > li").removeClass("list_tab_cur");
                $(this).addClass("list_tab_cur");
                $(".infoListTable").addClass("hidden");

                if($(this).attr("num") != "" && $(this).attr("num") != null)
                {
                    $("#listTable_"+$(this).attr("num")).removeClass("hidden");
                    changeStatus(parseInt($(this).attr("num")));
                }
                else
                {
                    $("#listTable_"+$(this).index()).removeClass("hidden");
                    changeStatus($(this).index());
                }
            }
        );
    });
}