<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.services.cms.info.InfoBaseManager"%>
<%
	String info_id = request.getParameter("info_id");
	InfoBean infoBean = InfoBaseManager.getInfoById(info_id);
	String model_id = infoBean.getModel_id()+"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=8"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>在线预览</title>
	<link type="text/css" rel="stylesheet" href="../../../js/view/demo.css"/>
	<script type="text/javascript" src="../../../js/jquery.js"></script>
	<script type="text/javascript" src="../../../js/jsonrpc.js"></script>
	<script type="text/javascript">
        var ModelUtilRPC = jsonrpc.ModelUtilRPC;
        var theme_list_open = false;
        var infoid = "<%=info_id%>";
        $(document).ready(function () {
            var linkBean = ModelUtilRPC.select(infoid,"CMScqgjj","link");
            if(linkBean.cat_id==10058||linkBean.cat_id==10062||linkBean.cat_id==10063){
                if(linkBean.cat_id==10058){
                    $("#iframe-wrap").html("<p style='color:red;margin-top:50px;'>备注：首页横幅图必须为 宽776 高78</p><img src='"+linkBean.thumb_url+"' width='776' height='78'/>");
				}else{
                    $("#iframe-wrap").html("<img src='"+linkBean.thumb_url+"' style='margin-top:50px;'/>");
				}
			}else{
                var url = "/info/contentView.jsp?info_id=<%=info_id%>";
                $("#iframe").attr("src",url);
                function fixHeight() {
                    var headerHeight = $("#switcher").height();
                    $("#iframe").attr("height", $(window).height()-84 + "px");
                }
                $(window).resize(function () {
                    fixHeight();
                }).resize();
                //响应式预览
                $('.icon-monitor').addClass('active');
                $(".icon-mobile-3").click(function () {
                    var tm_id=523;
                    if("<%=model_id%>"=="10"){
                        tm_id = 531;
					}
                    if("<%=model_id%>"=="13"){
                        tm_id = 530;
                    }
                    url = "/info/iList.jsp?info_id=<%=info_id%>&tm_id="+tm_id;
                    $("#by").css("overflow-y", "auto");
                    $('#iframe-wrap').removeClass().addClass('mobile-width-3');
                    $('.icon-tablet,.icon-mobile-1,.icon-monitor,.icon-mobile-2,.icon-mobile-3').removeClass('active');
                    $(this).addClass('active');
                    $("#iframe").attr("src",url);
                    return false;
                });

                $(".icon-mobile-2").click(function () {
                    var tm_id=523;
                    if("<%=model_id%>"=="10"){
                        tm_id = 531;
                    }
                    if("<%=model_id%>"=="13"){
                        tm_id = 530;
                    }
                    url = "/info/iList.jsp?info_id=<%=info_id%>&tm_id="+tm_id;
                    $("#by").css("overflow-y", "auto");
                    $('#iframe-wrap').removeClass().addClass('mobile-width-2');
                    $('.icon-tablet,.icon-mobile-1,.icon-monitor,.icon-mobile-2,.icon-mobile-3').removeClass('active');
                    $(this).addClass('active');
                    $("#iframe").attr("src",url);
                    return false;
                });

                $(".icon-mobile-1").click(function () {
                    var tm_id=26;
                    if("<%=model_id%>"=="10"){
                        tm_id = 533;
                    }
                    if("<%=model_id%>"=="13"){
                        tm_id = 532;
                    }
                    url = "/info/iList.jsp?info_id=<%=info_id%>&tm_id="+tm_id;
                    $("#by").css("overflow-y", "auto");
                    $('#iframe-wrap').removeClass().addClass('mobile-width');
                    $('.icon-tablet,.icon-mobile,.icon-monitor,.icon-mobile-2,.icon-mobile-3').removeClass('active');
                    $(this).addClass('active');
                    $("#iframe").attr("src",url);
                    return false;
                });

                $(".icon-tablet").click(function () {
                    var tm_id=26;
                    if("<%=model_id%>"=="10"){
                        tm_id = 533;
                    }
                    if("<%=model_id%>"=="13"){
                        tm_id = 532;
                    }
                    url = "/info/iList.jsp?info_id=<%=info_id%>&tm_id="+tm_id;
                    $("#by").css("overflow-y", "auto");
                    $('#iframe-wrap').removeClass().addClass('tablet-width');
                    $('.icon-tablet,.icon-mobile-1,.icon-monitor,.icon-mobile-2,.icon-mobile-3').removeClass('active');
                    $(this).addClass('active');
                    $("#iframe").attr("src",url);
                    return false;
                });

                $(".icon-monitor").click(function () {
                    url = "/info/contentView.jsp?info_id=<%=info_id%>";
                    $("#by").css("overflow-y", "hidden");
                    $('#iframe-wrap').removeClass().addClass('full-width');
                    $('.icon-tablet,.icon-mobile-1,.icon-monitor,.icon-mobile-2,.icon-mobile-3').removeClass('active');
                    $(this).addClass('active');
                    $("#iframe").attr("src",url);
                    return false;
                });
			}
        });
	</script>

	<script type="text/javascript">
        function Responsive($a) {
            if ($a == true) $("#Device").css("opacity", "100");
            if ($a == false) $("#Device").css("opacity", "0");
            $('#iframe-wrap').removeClass().addClass('full-width');
            $('.icon-tablet,.icon-mobile-1,.icon-monitor,.icon-mobile-2,.icon-mobile-3').removeClass('active');
            $(this).addClass('active');
            return false;
        };
	</script>
</head>
<body id="by" style="overflow-y: hidden">
<div id="switcher">
	<div class="center">
		<ul>
			<li class="logoTop">在线预览！</li>
			<div id="Device">
				<li class="device-monitor"><a href="javascript:"><div class="icon-monitor"></div></a></li>
				<li class="device-mobile"><a href="javascript:"><div class="icon-tablet"> </div></a></li>
				<li class="device-mobile"><a href="javascript:"><div class="icon-mobile-1"> </div></a></li>
				<li class="device-mobile-2"><a href="javascript:"><div class="icon-mobile-2"> </div></a></li>
				<li class="device-mobile-3"><a href="javascript:"><div class="icon-mobile-3"> </div></a></li>
			</div>
		</ul>
	</div>
</div>
<div id="iframe-wrap">
	<iframe id="iframe" src="" frameborder="0"   width="100%"> </iframe>
</div>
<div id="footer-notice" class="kj_bottom">
	<div style=" width:980px; margin:0 auto">
		<div class="clear"></div>
	</div>
</div>
</body>
</html>
