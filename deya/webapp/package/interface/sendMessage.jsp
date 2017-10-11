<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page import="com.deya.sms.SubMailMessageResultVo" %>
<%@ page import="com.deya.sms.SubMailMessageVo" %>
<%@ page import="com.deya.sms.VerifyCodeUtils" %>
<%@ page import="com.deya.util.jconfig.JconfigUtilContainer" %>
<%@ page import="com.google.common.collect.Maps" %>
<%@ page import="org.apache.commons.httpclient.HttpClient" %>
<%@ page import="org.apache.commons.httpclient.methods.PostMethod" %>
<%@ page import="org.apache.commons.httpclient.params.HttpMethodParams" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%
    String phone = request.getParameter("phone");
    if(phone != null && !"".equals(phone)){
        JSONObject jsonObject = new JSONObject();
        if(sendPhoneVerifyCode(request,phone)){
            jsonObject.put("result",true);
        }else{
            jsonObject.put("result",false);
        }
        response.getWriter().print(jsonObject);
    }
%>

<%!
    public static final String PHONE_VERIFY_CODE_SESSION_KEY = "phoneVerifyCode";

    private static final String SUBMAIL_SEND_URL = JconfigUtilContainer.bashConfig().getProperty("sendUrl", "", "sendMessage");

    //private static final String SUBMAIL_TEMPLATE_URL = JconfigUtilContainer.bashConfig().getProperty("templateUrl", "", "sendMessage");

    private static final String SUBMAIL_APPID = JconfigUtilContainer.bashConfig().getProperty("appId", "", "sendMessage");

    private static final String SUBMAIL_APPKEY = JconfigUtilContainer.bashConfig().getProperty("appKey", "", "sendMessage");

    private static final String PROJECT_ID = JconfigUtilContainer.bashConfig().getProperty("projectId", "", "sendMessage");

    public boolean sendSubMailMessage(String projectId, String multi) {
        String info;
        HttpClient httpclient = new HttpClient();
        PostMethod post = new PostMethod(SUBMAIL_SEND_URL);
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        post.addParameter("appid", SUBMAIL_APPID);
        post.addParameter("project", projectId);
        post.addParameter("multi", multi);
        post.addParameter("signature", SUBMAIL_APPKEY);
        try {
            httpclient.executeMethod(post);
            info = new String(post.getResponseBody(), "utf-8");
            System.out.println(info);
            JSONArray jsonArray = JSON.parseArray(info);
            for (int i = 0; i < jsonArray.size(); i++) {
                SubMailMessageResultVo messageResultVo = JSON.parseObject(jsonArray.get(i).toString(), SubMailMessageResultVo.class);
                if ("success".equals(messageResultVo.getStatus())) {
                    System.out.println("成功发送短信给[" + messageResultVo.getTo() + "]");
                } else {
                    System.out.println("发送短信给[" + messageResultVo.getTo() + "]失败");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean sendPhoneVerifyCode(HttpServletRequest request, String phone) {
        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(6, "0123456789");
        //存入会话session
        HttpSession session = request.getSession(true);

        session.setAttribute(PHONE_VERIFY_CODE_SESSION_KEY, verifyCode.toLowerCase());

        SubMailMessageVo subMailMessageVo = new SubMailMessageVo();
        Map<String, String> vars = Maps.newHashMap();
        vars.put("code", verifyCode);
        subMailMessageVo.setTo(phone);
        subMailMessageVo.setVars(vars);

        List<SubMailMessageVo> messageList = new ArrayList<SubMailMessageVo>();
        messageList.add(subMailMessageVo);

        return sendSubMailMessage(PROJECT_ID, JSON.toJSONString(messageList));
    }
%>
