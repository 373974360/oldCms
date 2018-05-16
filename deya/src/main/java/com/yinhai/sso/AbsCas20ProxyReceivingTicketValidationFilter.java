package com.yinhai.sso;

import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbsCas20ProxyReceivingTicketValidationFilter extends Cas20ProxyReceivingTicketValidationFilter {
    protected void onSuccessfulValidation(HttpServletRequest request, HttpServletResponse response, Assertion assertion) {
        registLoalUserSession(request, response, assertion);
    }

    protected abstract void registLoalUserSession(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Assertion paramAssertion);

    protected abstract void doLogoutCurrentSystem(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);

}