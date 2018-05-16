package com.yinhai.sso;

import com.deya.wcm.services.org.user.UserLogin;
import com.yinhai.webservice.client.SyncOrgServiceClient;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Ta3Cas20TicketValidationFilter extends DefaultTicketValidateFilter {
    protected void registLoalUserSession(HttpServletRequest request, HttpServletResponse response, Assertion assertion) {
        System.out.println("ticket 验证成功");
        request.getSession().setAttribute("ssoLogin", true);
        AttributePrincipal principal = assertion.getPrincipal();
        String name = principal.getName();
        boolean checkUserLogin = UserLogin.checkUserLogin(name, request);
        if (!checkUserLogin) {
            System.out.println("----------子系统中无此用户，先同步用户--------");
            SyncOrgServiceClient.syncOrgDeptOrUser("user", 1);
            SyncOrgServiceClient.syncOrgDeptOrUser("dept", 1);
            checkUserLogin = UserLogin.checkUserLogin(name, request);
            if (!checkUserLogin) {
                System.out.println("----------同步之后还是无此用户，登录失败--------");
            } else {
                System.out.println("SSO登录成功，系统用户为" + name);
            }
        } else {
            System.out.println("SSO登录成功，系统用户为" + name);
        }
    }

    protected void doLogoutCurrentSystem(HttpServletRequest request, HttpServletResponse response) {
        UserLogin.loginOut(request);
    }
}