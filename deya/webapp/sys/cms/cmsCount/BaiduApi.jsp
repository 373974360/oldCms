<%@ page import="com.deya.wcm.services.cms.count.BaiDuAccessCount" %>
<%@ page import="org.json.JSONObject" %>
<%@page contentType="text/html; charset=utf-8" %>
<%
    String result="";
    String method = request.getParameter("method");

    JSONObject header = new JSONObject();
    header.put("username", "15529333653");//用户名
    header.put("password", "WANGmin521!!!");//用户密码
    header.put("token", "3a7a0002dd24966d09351632b64f2a5b");//申请到的token
    header.put("account_type", "1");
    String urlStr = "https://api.baidu.com/json/tongji/v1/ReportService/getData";
    String charset = "utf-8";

    JSONObject body = new JSONObject();
    body.put("siteId", "12787364");
    body.put("method", method);//需要获取的数据
    //今日流量
    if(method.equals("overview/getOutline")){
        body.put("metrics", "pv_count,visitor_count,ip_count,bounce_ratio,avg_visit_time");//指标,数据单位
    }
    //top 10搜索词
    if(method.equals("overview/getWord")){
        body.put("indicators",request.getParameter("indicators"));
        body.put("start_date",request.getParameter("start_date"));
        body.put("end_date",request.getParameter("end_date"));
        body.put("start_date2",request.getParameter("start_date2"));
        body.put("end_date2",request.getParameter("end_date2"));
        body.put("metrics", "simple_searchword_title,pv_count,ratio");//指标,数据单位
    }
    //top 10受访页面
    if(method.equals("overview/getVisitPage")){
        body.put("indicators",request.getParameter("indicators"));
        body.put("start_date",request.getParameter("start_date"));
        body.put("end_date",request.getParameter("end_date"));
        body.put("start_date2",request.getParameter("start_date2"));
        body.put("end_date2",request.getParameter("end_date2"));
        body.put("metrics", "simple_visitpage_title,pv_count,ratio");//指标,数据单位
    }
    //top 10来源网站
    if(method.equals("overview/getSourceSite")){
        body.put("indicators",request.getParameter("indicators"));
        body.put("start_date",request.getParameter("start_date"));
        body.put("end_date",request.getParameter("end_date"));
        body.put("start_date2",request.getParameter("start_date2"));
        body.put("end_date2",request.getParameter("end_date2"));
        body.put("metrics", "simple_link_title,pv_count,ratio");//指标,数据单位
    }
    //top 10 入口页面
    if(method.equals("overview/getLandingPage")){
        body.put("indicators",request.getParameter("indicators"));
        body.put("start_date",request.getParameter("start_date"));
        body.put("end_date",request.getParameter("end_date"));
        body.put("start_date2",request.getParameter("start_date2"));
        body.put("end_date2",request.getParameter("end_date2"));
        body.put("metrics", "simple_landingpage_title,pv_count,ratio");//指标,数据单位
    }
    //新老访客
    if(method.equals("overview/getVisitorType")){
        body.put("indicators",request.getParameter("indicators"));
        body.put("start_date",request.getParameter("start_date"));
        body.put("end_date",request.getParameter("end_date"));
        body.put("start_date2",request.getParameter("start_date2"));
        body.put("end_date2",request.getParameter("end_date2"));
        body.put("metrics", "");//指标,数据单位
    }
    //地域分布
    if(method.equals("overview/getDistrictRpt")){
        body.put("indicators",request.getParameter("indicators"));
        body.put("start_date",request.getParameter("start_date"));
        body.put("end_date",request.getParameter("end_date"));
        body.put("start_date2",request.getParameter("start_date2"));
        body.put("end_date2",request.getParameter("end_date2"));
        body.put("metrics", "pv_count,visitor_count,ip_count,bounce_ratio,avg_visit_time,trans_count");//指标,数据单位
    }
    //趋势图
    if(method.equals("overview/getTimeTrendRpt")){
        body.put("indicators",request.getParameter("indicators"));
        body.put("start_date",request.getParameter("start_date"));
        body.put("end_date",request.getParameter("end_date"));
        body.put("start_date2",request.getParameter("start_date2"));
        body.put("end_date2",request.getParameter("end_date2"));
        body.put("metrics", "simple_date_title,time,pv_count");//指标,数据单位
    }
    //年龄分布
    if(method.equals("overview/getAge")){
        body.put("metrics", "");//指标,数据单位
    }

    JSONObject params = new JSONObject();
    params.put("header", header);
    params.put("body", body);
    try {
        byte[] res = BaiDuAccessCount.post(urlStr, params.toString(), charset);
        result = new String(res);
    }catch (Exception e){
        e.printStackTrace();
    }


    String callback = request.getParameter("callback");
    if(callback != null && !"".equals(callback)){
        out.println(callback + "(" + result + ")");
    }else{
        out.println(result);
    }
%>
