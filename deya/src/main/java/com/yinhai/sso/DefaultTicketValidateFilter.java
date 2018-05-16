package com.yinhai.sso;

import org.jasig.cas.client.validation.Assertion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultTicketValidateFilter extends AbsCas20ProxyReceivingTicketValidationFilter {
    protected void registLoalUserSession(HttpServletRequest request, HttpServletResponse response, Assertion assertion) {
    }

    protected boolean isNeedAuthUrl(HttpServletRequest request, String url) {
        return false;
    }

    protected void doLogoutCurrentSystem(HttpServletRequest request, HttpServletResponse response) {
    }
}