package com.sso;

import com.deya.wcm.services.org.user.UserLogin;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @User: program
 * @Date: 2016/11/28
 */
public class MyCasProxy extends Cas20ProxyReceivingTicketValidationFilter {

    @Override
    protected void onFailedValidation(HttpServletRequest request,
                                      HttpServletResponse response) {
        System.out.println("ticket 验证失败");
        super.onFailedValidation(request, response);
    }

    @Override
    protected void onSuccessfulValidation(HttpServletRequest request,
                                          HttpServletResponse response, Assertion assertion) {
        System.out.println("ticket 验证成功");
        request.getSession().setAttribute("ssoLogin", true);
        AttributePrincipal principal = assertion.getPrincipal();
        String name = principal.getName();
        boolean checkUserLogin = UserLogin.checkUserLogin(name, request);
        if(!checkUserLogin){
            System.out.println("SSO登录失败，系统中无此用户");
        }else{
            System.out.println("SSO登录成功，系统用户为" + name);
        }
        //调用本系统登录方法
        //System.out.println(AssertionHolder.getAssertion().getPrincipal());
        //	System.out.println(request.getRemoteUser());
        super.onSuccessfulValidation(request, response, assertion);
    }
}
