package com.deya.wcm.jsonlistener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/1/15
 * Time: 10:59
 * Description:
 * Version: v1.0
 */
public class MySessionListener implements HttpSessionListener {

    public static Map userMap = new HashMap();

    private   MySessionContext myc = MySessionContext.getInstance();

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        myc.AddSession(httpSessionEvent.getSession());
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        session.removeAttribute("cicro_wcm_user");
        myc.DelSession(session);

    }
}