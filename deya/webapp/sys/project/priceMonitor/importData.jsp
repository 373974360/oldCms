<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>农产品价格数据导入</title>
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
        var scName = "";

        var defaultBean;
        $(document).ready(function () {
            initButtomStyle();
            init_input();
            publicUploadButtomAllFile2("uploadify",true,false,"",false,0,current_site_id,"fileSave");
            publicUploadButtomAllFile2("uploadify2",true,false,"",false,0,current_site_id,"fileSave2");
            publicUploadButtomAllFile2("uploadify3",true,false,"",false,0,current_site_id,"fileSave3");

            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

        });

        function fileSave(path)
        {
            scName = $("#scName").val();
            $("#path").val(path);

            if(jsonrpc.PriceInfoRPC.importPfLs(path,user_id,current_site_id,scName))
            {
                msgAlert("数据导入成功");
            }
            else
            {
                msgWargin("数据导入失败,请重新上传");
            }
        }

        function fileSave2(path)
        {
            scName = $("#scName").val();
            $("#path2").val(path);

            if(jsonrpc.PriceInfoRPC.importCdjg(path,user_id,current_site_id,scName))
            {
                msgAlert("数据导入成功");
            }
            else
            {
                msgWargin("数据导入失败,请重新上传");
            }
        }

        function fileSave3(path)
        {
            scName = $("#scName").val();
            $("#path3").val(path);

            if(jsonrpc.PriceInfoRPC.importNzjg(path,user_id,current_site_id,scName))
            {
                msgAlert("数据导入成功");
            }
            else
            {
                msgWargin("数据导入失败,请重新上传");
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

    </SCRIPT>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
    <div class="people_table_content">
        <table id="death_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>选择导入市场：</th>
                <td >
                    <select id="scName" name="scName">
                        <option value="北城批发市场">北城批发市场</option>
                        <option value="欣桥批发市场">欣桥批发市场</option>
                        <option value="摩尔农产品交易中心">摩尔农产品交易中心</option>
                        <option value="人人乐超市">人人乐超市</option>
                        <option value="人人家超市">人人家超市</option>
                        <option value="西勘综合市场">西勘综合市场</option>
                        <option value="文景农贸市场">文景农贸市场</option>
                        <option value="明珠巷综合市场">明珠巷综合市场</option>
                        <option value="龙首农副市场">龙首农副市场</option>
                        <option value="高陵农村信息站">高陵农村信息站</option>
                        <option value="未央农村信息站">未央农村信息站</option>
                        <option value="雁塔农村信息站">雁塔农村信息站</option>
                        <option value="周至农村信息站">周至农村信息站</option>
                        <option value="临潼农村信息站">临潼农村信息站</option>
                        <option value="长安农村信息站">长安农村信息站</option>
                        <option value="灞桥农村信息站">灞桥农村信息站</option>
                        <option value="户县农村信息站">户县农村信息站</option>
                        <option value="蓝田农村信息站">蓝田农村信息站</option>
                        <option value="阎良农村信息站">阎良农村信息站</option>
                        <option value="沣东新城农村信息站">沣东新城农村信息站</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>批发、零售数据导入：</th>
                <td ><div style="float:left;margin:auto;"><input id="path" name="path" type="text" style="width:250px;" value=""></div>
                    <div style="float:left">&#160;<input type="file" name="uploadify" id="uploadify"></div>
                    <a style="margin-left: 30px;;" href="/upload/CMSxaagri/201604/批发数据样表.xls">批发数据样表下载</a>
                    <a style="margin-left: 30px;;" href="/upload/CMSxaagri/201604/零售数据样表.xls">零售数据样表下载</a>
                </td>
            </tr>
            <tr>
                <th>产地数据导入：</th>
                <td ><div style="float:left;margin:auto;"><input id="path2" name="path2" type="text" style="width:250px;" value=""></div>
                    <div style="float:left">&#160;<input type="file" name="uploadify2" id="uploadify2"></div>
                    <a style="margin-left: 30px;;" href="/upload/CMSxaagri/201604/产地价格样表.xls">产地数据样表下载</a>
                </td>
            </tr>
            <tr>
                <th>农资数据导入：</th>
                <td ><div style="float:left;margin:auto;"><input id="path3" name="path3" type="text" style="width:250px;" value=""></div>
                    <div style="float:left">&#160;<input type="file" name="uploadify3" id="uploadify3"></div>
                    <a style="margin-left: 30px;;" href="/upload/CMSxaagri/201604/农资价格样表 .xls">农资数据样表下载</a>
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
                <input id="btn2" name="btn2" type="button" onclick="CloseModalWindow();" value="关闭" />
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</form>

</body>
</html>
