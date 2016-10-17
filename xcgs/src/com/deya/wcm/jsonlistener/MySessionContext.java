package com.deya.wcm.jsonlistener;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/1/15
 * Time: 10:58
 * Description:
 * Version: v1.0
 */
public class MySessionContext {

    private static MySessionContext instance;
    private static HashMap mymap = new HashMap();

    private MySessionContext() {
        mymap = new HashMap();
    }

    public static MySessionContext getInstance() {
        if (instance == null) {
            instance = new MySessionContext();
        }
        return instance;
    }

    public static synchronized void AddSession(HttpSession session) {
        if (session != null) {
            mymap.put(session.getId(), session);
        }
    }

    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
            mymap.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null)
            return null;
        return (HttpSession) mymap.get(session_id);
    }

    public static synchronized HashMap getAllSession() {
        return  mymap;
    }
}
