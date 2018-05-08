package com.deya.wcm.services.session;

/**
 * 	<listener> 
        <listener-class> 
            com.deya.wcm.services.session.OnlineCounterListener 
        </listener-class> 
    </listener>
 */

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
public class OnlineCounterListener implements HttpSessionListener, ServletRequestListener{ 
	
	HttpServletRequest request;
	
    public void sessionCreated(HttpSessionEvent hse) { 
    	
    	//ServletContext是上下文，下边这个语句是获取上下文
        //获取了上下文，就等于获取了application，因为上下文相当于application
         ServletContext application = hse.getSession().getServletContext();
         String site_id = com.deya.wcm.services.control.domain.SiteDomainManager.getSiteIDByDomain(request.getLocalName());
         
         OnlineCounter.raise(site_id); 
    }  
    public void sessionDestroyed(HttpSessionEvent hse) { 
    	 String site_id = com.deya.wcm.services.control.domain.SiteDomainManager.getSiteIDByDomain(request.getLocalName());
         OnlineCounter.reduce(site_id); 
    }
	@Override
	public void requestDestroyed(ServletRequestEvent servletrequestevent) {
		 // TODO Auto-generated method stub
	}
	@Override
	public void requestInitialized(ServletRequestEvent servletrequestevent) {
		 request = (HttpServletRequest)servletrequestevent.getServletRequest();
	} 
} 
