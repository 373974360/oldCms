package com.deya.wcm.jsonlistener_old;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.metaparadigm.jsonrpc.JSONRPCBridge;
import com.deya.util.io.*;
import com.deya.util.jconfig.*;

/**
 * <p>
 * Title: JSONRPC接口类注册类
 * </p>
 * <p>
 * Description: 为提供给客户端程序访问的接口类进行注册
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009-06-24
 * </p>
 * <p>
 * Company: Cicro
 * </p>
 * 
 * @author zhuliang
 * @version 1.0
 */
public class JSON_RPC_Listener implements HttpSessionListener,ServletRequestListener {
	private static Set<RPC_Action> RPC_ACTIONS = new HashSet<RPC_Action>();
	 private HttpServletRequest request;
	private static String JSON_RPC_BRIDGE_KEY = "JSONRPCBridge";
	static {
		try {					
			String classList = FileOperation.readFileToString(JconfigUtilContainer.bashConfig().getProperty(
					"path", "", "jsonrpcFile"));

			classList = classList.replaceAll("\r\n", "|").replaceAll("\r", "|")
					.replaceAll("\n", "|");
			String tempA[] = classList.split("\\|");
			for (int i = 0; i < tempA.length; i++) {
				if (tempA[i] != null && !"".equals(tempA[i])) {
					RPC_ACTIONS.add(new RPC_Action(tempA[i].substring(0,
							tempA[i].indexOf(",")).trim(), tempA[i].substring(
							tempA[i].indexOf(",") + 1).trim()));
				}
			}
			RPC_ACTIONS.add(new RPC_Action("JSONUtil",
					"com.deya.wcm.jsonlistener.JSONUtil"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void requestInitialized(ServletRequestEvent sre){
        request=(HttpServletRequest)sre.getServletRequest();
    }
	
	public void requestDestroyed(ServletRequestEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

	public void sessionCreated(HttpSessionEvent event) {
		//System.out.println("++++++++++++++++++JSON_RPC_Listener sessionCreated++++++++++++++++++"+request.getHeader("referer"));		
		//System.out.println("++++++++++++++++++JSON_RPC_Listener sessionCreated++++++++++++++++++"+request.getRequestURI());
		if(request.getRequestURI().endsWith("/JSON-RPC"))
		{
			if(request.getHeader("referer") == null)
			{
				return;
			}
		}
		HttpSession session = event.getSession();
		JSONRPCBridge bridge = new JSONRPCBridge();
		session.setAttribute(JSON_RPC_BRIDGE_KEY, bridge);
		bridge.registerObject("session", session);
		for (RPC_Action action : RPC_ACTIONS) {
			try {
				String name = action.getName();
				String className = action.getClassName();
				Object obj = Class.forName(className).newInstance();
				bridge.registerObject(name, obj);
				// System.out.println("registerObject :"+action);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	public void sessionDestroyed(HttpSessionEvent event) {		
		HttpSession session = event.getSession();
		session.removeAttribute(JSON_RPC_BRIDGE_KEY);
	}

	private static class RPC_Action {
		private String name;

		private String className;

		public String getName() {
			return name;
		}

		@SuppressWarnings("unused")
		public void setName(String name) {
			this.name = name;
		}

		public String getClassName() {
			return className;
		}

		@SuppressWarnings("unused")
		public void setClassName(String className) {
			this.className = className;
		}

		public RPC_Action(String name, String className) {
			this.name = name;
			this.className = className;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((className == null) ? 0 : className.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final RPC_Action other = (RPC_Action) obj;
			if (className == null) {
				if (other.className != null)
					return false;
			} else if (!className.equals(other.className))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "name : " + name + "\t className : " + className;
		}

	}
}
