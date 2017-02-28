package com.deya.wcm.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deya.util.Javascript;
import com.deya.util.jspFilterHandl;
import com.deya.wcm.services.org.user.UserLogin;

public class ManagerLoginFilter implements javax.servlet.Filter{
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
        //System.out.println("doFilter------------"+request.getSession().getId());
        String requestUri = request.getRequestURI();
        //System.out.println("requestUri--------------" + requestUri);
        String loginPage = config.getInitParameter("loginPage");
        String notFilterPage = config.getInitParameter("notFilterPage");
        String[] notArr = notFilterPage.split(";");

        request.setCharacterEncoding("utf-8");

        if (isContains(request.getRequestURI(), notArr)) {
            chain.doFilter(request, response);
            return;
        }

        //对前台的jsp进行xss漏洞过滤
        //if(!requestUri.startsWith("/sys"))
        if(requestUri.indexOf("/sys/") < 0)
        {
            if(!jspFilterHandl.isRightParam(request,requestUri))
            {
                response.setContentType("text/html; charset=utf-8");
                response.sendRedirect("/");
                return;
            }else
            {
                chain.doFilter(request, response);
            }
        }else
        {
            if(UserLogin.checkLoginBySession(request))
            {
                chain.doFilter(request, response);
            }
            else
            {
                //response.sendRedirect(loginPage);
                response.setContentType("text/html; charset=utf-8");
                if(requestUri.indexOf("/sys/JSON-RPC") >= 0)
                {
                    response.getWriter().write("top.location.href='" + loginPage +"'");
                }
                else{
                    response.getWriter().write(Javascript.location(loginPage,"top"));
                }
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
