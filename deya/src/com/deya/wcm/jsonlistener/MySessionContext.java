package com.deya.wcm.jsonlistener;

import com.deya.util.SessionListener;
import com.deya.wcm.bean.org.user.LoginUserBean;

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
            // 删除单一登录中记录的变量
            if(session.getAttribute("cicro_wcm_user")!=null){
                LoginUserBean loginUserBean =  (LoginUserBean)session.getAttribute("cicro_wcm_user");
                SessionListener.sessionMap.remove(loginUserBean.getUser_id());
            }
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
