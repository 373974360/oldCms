<%@ page contentType="application/json; charset=utf-8"%>
<%@ page language="java" import="org.apache.commons.httpclient.HttpClient,org.apache.commons.httpclient.methods.PostMethod,org.apache.commons.httpclient.params.HttpMethodParams" %><%@ page import="org.apache.commons.httpclient.methods.GetMethod"%><%@ page import="net.sf.json.JSONObject"%>
<%
    String info = null;
    String code = request.getParameter("code");
    try {
        HttpClient httpclient = new HttpClient();
        GetMethod getMethod = new GetMethod("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx150ba558f04be079&secret=e61cc2ae457cded98a9a69ecf99ba802&code=" + code + "&grant_type=authorization_code");
        getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
        httpclient.executeMethod(getMethod);
        info = new String(getMethod.getResponseBody(), "utf-8");
        JSONObject jsonObject = JSONObject.fromObject(info);
        Object openid = jsonObject.get("openid");
        if(openid != null){
            Object wxOpenId = session.getAttribute("wxOpenId");
            if(wxOpenId == null){
                session.setAttribute("wxOpenId",openid.toString());
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
