package com.deya.wcm.servlet.sso;

import com.deya.license.tools.CryptoTools;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.org.user.UserLogin;
import com.deya.wcm.services.org.user.UserRegisterManager;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description:
 * @User: like
 * @Date: 2016/9/9 10:35
 * @Version: 1.0
 * @Created with IntelliJ IDEA.
 */
public class SsoLogin extends HttpServlet {

    private static final long serialVersionUID = 3131099767169123925L;

    private static String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String callback = request.getParameter("callback");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        String user_name = request.getParameter("user_name");
        if(StringUtils.isNotBlank(user_name)){
            CryptoTools ct = new CryptoTools();
            user_name = ct.decode(user_name);
            LoginUserBean lub = UserRegisterManager.getLoginUserBeanByUname(user_name);
            if(null != lub){
                lub.setIp(request.getRemoteAddr());
                UserLogin.setWCmSession(lub,request);
                LogManager.insertLoginLog(lub);
                response.getWriter().print(callback + "({\"state\":200,\"data\":null})");
            }else{
                response.getWriter().print(callback + "({\"state\":500,\"data\":\"验证失败！\"})");
            }
        }else{
            response.getWriter().print(callback + "({\"state\":500,\"data\":\"验证失败！\"})");
        }
    }


    public static void main(String[] args) {
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += (int) (Math.random() * 10);
        }
        CryptoTools ct = new CryptoTools();
        String encode = ct.encode("373974360@qq.com");
        System.out.println(encode);
    }
}