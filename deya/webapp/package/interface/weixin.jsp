<%@ page import="com.deya.wcm.services.media.Weixin"%>
<%@ page import="java.security.MessageDigest"%>
<%@ page import="java.util.Formatter"%><%@ page import="com.deya.util.FormatUtil"%>
<%@ page contentType="application/json; charset=utf-8"%>
<%
    String url = FormatUtil.formatNullString(request.getParameter("content_url"));//正文地址
    String site_id = FormatUtil.formatNullString(request.getParameter("site_id"));//正文地址
    String result = sign(url,site_id);
    String callback = request.getParameter("callback");
    if(callback != null && !"".equals(callback)){
        out.println(callback + "(" + result + ")");
    }else{
        out.println(result);
    }
%>
<%!
    public static String sign(String url,String site_id) {
        Weixin.initParams("media.properties",site_id);
        String reslut = "";
        String nonce_str = Weixin.create_nonce_str();
        String timestamp = Weixin.create_timestamp();
        String string1;
        String signature = "";
        String jsapi_ticket = Weixin.getTicket();
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        try{
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }catch (Exception e){
            e.printStackTrace();
        }
        reslut = "{\"url\":\""+url+"\",\"jsapi_ticket\":\""+jsapi_ticket+"\",\"nonce_str\":\""+nonce_str+"\",\"timestamp\":\""+timestamp+"\",\"signature\":\""+signature+"\",\"appid\":\""+Weixin.appid+"\"}";
        return reslut;
    }
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
%>