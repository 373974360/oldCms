package com.deya.wcm.filter;

import com.deya.util.Javascript;
import com.deya.util.jspFilterHandl;
import com.deya.wcm.services.org.user.SessionManager;
import com.deya.wcm.services.org.user.UserLogin;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManagerLoginFilter implements javax.servlet.Filter {
    private FilterConfig config;

    public static boolean isContains(String container, String[] regx) {
        boolean result = false;

        for (int i = 0; i < regx.length; i++) {
            if (container.indexOf(regx[i]) != -1) {
                return true;
            }
        }
        return result;
    }

    public void setFilterConfig(FilterConfig config) {
        this.config = config;
    }

    public FilterConfig getFilterConfig() {
        return config;
    }

    public void doFilter(ServletRequest _request, ServletResponse _response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) _request;
        HttpServletResponse response = (HttpServletResponse) _response;
        String requestUri = request.getRequestURI();
        String loginPage = config.getInitParameter("loginPage");
        String notFilterPage = config.getInitParameter("notFilterPage");
        String[] notArr = notFilterPage.split(";");
        request.setCharacterEncoding("utf-8");
        request.getParameterNames();
        ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest && requestUri.contains("JSON-RPC")) {
            requestWrapper = new MyHttpServletRequestWrapper(request);
        }
        if(null == requestWrapper) {
            requestWrapper = request;
        }
        if (isContains(request.getRequestURI(), notArr)) {
            chain.doFilter(requestWrapper, response);
            return;
        }
        //对前台的jsp进行xss漏洞过滤
        if (!jspFilterHandl.isRightParam(requestWrapper, requestUri)) {
            System.out.println("*******************验证参数错误，跳转到首页或者后台登录页！**********************");
            if (requestUri.startsWith("/sys") || requestUri.startsWith("/manager") || requestUri.startsWith("/admin")) {
                SessionManager.remove(request, "cicro_wcm_user");
                response.sendRedirect(loginPage);
            }else{
                response.setContentType("text/html; charset=utf-8");
                response.sendRedirect("/");
            }
        } else {
            if (requestUri.startsWith("/sys") || requestUri.startsWith("/manager") || requestUri.startsWith("/admin")) {
                if (UserLogin.checkLoginBySession(request)) {
                    chain.doFilter(requestWrapper, response);
                } else {
                    response.setContentType("text/html; charset=utf-8");
                    if (requestUri.contains("/sys/JSON-RPC")) {
                        response.getWriter().write("top.location.href='" + loginPage + "'");
                    } else {
                        response.getWriter().write(Javascript.location(loginPage, "top"));
                    }
                }
            } else {
                chain.doFilter(requestWrapper, response);
            }
        }
    }


    public void destroy() {
        this.config = null;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }
}
