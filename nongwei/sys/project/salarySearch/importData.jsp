<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>工资数据导入</title>
    <meta name="generator" content="featon-Builder"/>
    <meta name="author" content="featon"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/main.css"/>
    <link type="text/css" rel="stylesheet" href="/sys/styles/sub.css"/>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../js/jquery.uploadify.js"></script>
    <script type="text/javascript" src="../../js/uploadFile/swfobject.js"></script>
    <script type="text/javascript" src="../../js/uploadTools.js"></script>
    <SCRIPT LANGUAGE="JavaScript">

        var topnum = request.getParameter("topnum");

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();
            publicUploadButtomAllFile2("uploadify",true,false,"",false,0,top.current_site_id,"fileSave");

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

        });

        function fileSave(path)
        {
            $("#path").val(path);

            var importDate = $("#importDate").val();

            if(jsonrpc.SalaryRPC.importSalaryData(path,top.current_site_id,importDate))
            {
                top.msgAlert("数据导入成功");
            }
            else
            {
                top.msgWargin("数据导入失败,请重新上传");
            }
        }

        function publicUploadButtomAllFile2(input_name,is_auto,is_multi,selectAfterHandl,is_press,press_osition,site_id,uploadAfterHandl)
        {
            var imgDomain = MateInfoRPC.getImgDomain(site_id);
            $("#"+input_name).uploadify({//初始化函数
                'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
                'script' :''+imgDomain+'/servlet/UploadFileIfy',//后台处理的请求
                'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
                'buttonImg': '/sys/js/uploadFile/upst.gif',
                'folder' :'folder',//您想将文件保存到的路径
                'queueID' :'fileQueue',//与下面的上传文件列表id对应
                'queueSizeLimit' :50,//上传文件的数量
                //'scriptData' :{'is_press':0,'press_osition':5,'site_id':site_id},//向后台传的数据
                'fileDesc' :"xls",//上传文件类型说明
                'fileExt' :"*.xls;", //控制可上传文件的扩展名,启用本项时需同时声明fileDesc
                'method':'get',//如果向后台传输数据，必须是get
                'sizeLimit':31457280,//文件上传的大小限制，单位是字节
                'auto' :is_auto,//是否自动上传
                'multi' :is_multi,
                'simUploadLimit' :1,//同时上传文件的数量
                'buttonText' :'BROWSE',//浏览按钮图片
                'onSelect':function(event,queueID,fileObj){//选择完后触发的事件
                    if(is_auto)
                    {
                        if(is_press_bak != null)
                        {
                            is_press = is_press_bak;
                        }
                        if(press_osition_bak != null)
                            press_osition = press_osition_bak;

                        $("#"+input_name).uploadifySettings('scriptData',{'is_press':is_press,'press_osition':press_osition,'site_id':site_id,'sid':MateInfoRPC.getUploadSecretKey()});
                    }else
                    {
                        if(selectAfterHandl != "" && selectAfterHandl != null)
                            eval(selectAfterHandl+"()");
                    }
                    if(fileObj.size > 10000000){
                        alert("附件过大，不允许上传！");
                        return;
                    }
                },
                'onError':function(event,queueID,fileObj,errorObj){
                    alert("文件:" +fileObj.name + "上传失败！");
                },
                'onComplete': function(event,queueID,fileObj,response,data){//当上传完成后的回调函数，ajax方式哦~~
                    var att_path = "";
                    var att_ename = "";
                    var objPath =  jQuery.parseJSON(response);
                    att_path = objPath.att_path;
                    att_ename = objPath.att_ename;

                    pic_url =  att_path + att_ename;

                    if(uploadAfterHandl != "" && uploadAfterHandl != null)
                    {
                        if(uploadAfterHandl.indexOf("(") == -1)
                            eval(""+uploadAfterHandl+"('"+pic_url+"')");
                        else
                        {
                            uploadAfterHandl = uploadAfterHandl.replace("pic_url",pic_url);

                            eval(uploadAfterHandl);
                        }
                    }
                },
                'onAllComplete': function(event,data){

                }
            });
        }

        function confirmDelete(){
            var deleteMonth = $("#deleteDate").val();
            if(deleteMonth != ""){
                top.msgConfirm(WCMLang.Delete_confirm,"deleteData()");
            }
        }

        function deleteData(){
            var deleteMonth = $("#deleteDate").val();
            var m = new Map();
            m.put("salaryDate",deleteMonth);
            m.put("site_id",top.current_site_id);
            if(jsonrpc.SalaryRPC.deleteSalaryData(m))
            {
                top.msgAlert("数据删除成功");
            }
            else
            {
                top.msgWargin("数据删除失败！");
            }
        }

    </SCRIPT>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
    <div class="people_table_content">
        <table id="death_tabl" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>删除数据时间：</th>
                <td ><input class="Wdate" type="text" name="deleteDate" id="deleteDate" size="11" style="width:90px;height:16px;line-height:16px;"
                            value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})" readonly="true" />
                </td>
                <td ><input type="button" value="删除" onclick="confirmDelete()"/> </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="people_table_content">
        <table id="death_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>导入工资时间：</th>
                <td ><input class="Wdate" type="text" name="importDate" id="importDate" size="11" style="width:90px;height:16px;line-height:16px;"
                            value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})" readonly="true" />
                </td>
            </tr>
            <tr>
                <th>导入工资数据：</th>
                <td ><div style="float:left;margin:auto;"><input id="path" name="path" type="text" style="width:250px;" value=""></div>
                    <div style="float:left">&#160;<input type="file" name="uploadify" id="uploadify"></div>
                </td>
            </tr>
            <tr>
                <td id="fileQueue" colspan="2"></td>
            </tr>
            </tbody>
        </table>
    </div>
    <span class="blank12"></span>
    <div class="line2h"></div>
    <span class="blank3"></span>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle" style="text-indent:100px;">
                <input id="btn2" name="btn2" type="button" onclick="top.CloseModalWindow();" value="关闭" />
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</form>

</body>
</html>
