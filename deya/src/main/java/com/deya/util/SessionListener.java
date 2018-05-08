package com.deya.util;

import com.deya.wcm.bean.org.user.LoginUserBean;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

/**
 * @Description:
 * @User: like
 * @Date: 2016/9/5 11:13
 * @Version: 1.0
 * @Created with IntelliJ IDEA.
 */
public class SessionListener implements HttpSessionListener {

    public static HashMap sessionMap = new HashMap();

    public void sessionCreated(HttpSessionEvent hse) {
        HttpSession session = hse.getSession();
    }
    public void sessionDestroyed(HttpSessionEvent hse) {
        HttpSession session = hse.getSession();
        this.DelSession(session);
    }
    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
            // 删除单一登录中记录的变量
            if(session.getAttribute("cicro_wcm_user")!=null){
                LoginUserBean loginUserBean =  (LoginUserBean)session.getAttribute("cicro_wcm_user");
                SessionListener.sessionMap.remove(loginUserBean.getUser_id());
            }
        }
    }
}
