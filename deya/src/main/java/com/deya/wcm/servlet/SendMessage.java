package com.deya.wcm.servlet;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;

public class SendMessage extends HttpServlet {
    private static final long serialVersionUID = 3131099767169123925L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String info = null;
        String phone = request.getParameter("phone");
        String code = "";
        for (int i = 0; i < 6; i++){
            code += (int) (Math.random() * 10);
        }
        /*
        request.getSession().setMaxInactiveInterval(300);
        request.getSession().setAttribute(no, code);
        try{
            HttpClient httpclient = new HttpClient();
            PostMethod post = new PostMethod("http://sx.ums86.com:8899/sms/Api/Send.do");//
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
            post.addParameter("SpCode", "218968");
            post.addParameter("LoginName", "wn_xxh");
            post.addParameter("Password", "wn1234");
            post.addParameter("MessageContent", "您的信件验证码为：" + code + "，感谢您对我们工作的支持！");
            post.addParameter("UserNumber", no);
            post.addParameter("SerialNumber", "");
            post.addParameter("ScheduleTime", "");
            post.addParameter("whitevalid", "1");
            post.addParameter("f", "1");
            httpclient.executeMethod(post);
            info = new String(post.getResponseBody(),"gbk");
            System.out.println(info);
            String result = info.substring(info.indexOf("=")+1, info.indexOf("&"));
            if(result != null && 0 == Integer.parseInt(result))
            {
                response.getWriter().print(code);
            }
            else
            {
                response.getWriter().print("0");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        */
        try {
            HttpClient httpclient = new HttpClient();
            PostMethod post = new PostMethod("http://hprpt2.eucp.b2m.cn:8080/sdkproxy/sendsms.action");
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
            post.addParameter("cdkey", "0SDK-EBB-6699-RGULL");
            post.addParameter("password", "453182");
            post.addParameter("phone", phone);
            post.addParameter("message", "【亿美软通】您的验证码为：" + code + "，感谢您对我们工作的支持！");
            httpclient.executeMethod(post);
            info = new String(post.getResponseBody(), "utf-8");
            System.out.println(info);
            if (info.indexOf("<error>0</error>") > 0) {
                response.getWriter().print(code);
            } else {
                response.getWriter().print("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public static void main(String[] args) {
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += (int) (Math.random() * 10);
        }
        System.out.println(code);
    }

}

/* Location:           C:\Users\Administrator\Desktop\wcm\shared\classes.zip
 * Qualified Name:     classes.com.cicro.wcm.servlet.CreateImage
 * JD-Core Version:    0.6.2
 */