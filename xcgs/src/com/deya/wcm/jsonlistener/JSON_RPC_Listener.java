package com.deya.wcm.jsonlistener;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.*;

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
public class JSON_RPC_Listener
        implements HttpSessionListener, ServletRequestListener {
    private static Set<RPC_Action> RPC_ACTIONS = new HashSet();
    private static Set<RPC_Action> BRPC_ACTIONS = new HashSet();
    private HttpServletRequest request;
    private static String JSON_RPC_BRIDGE_KEY = "JSONRPCBridge";

    static {
        try {
            String classList = FileOperation.readFileToString(JconfigUtilContainer.bashConfig().getProperty("path", "", "jsonrpcFile"));

            classList = classList.replaceAll("\r\n", "|").replaceAll("\r", "|")
                    .replaceAll("\n", "|");
            String[] tempA = classList.split("\\|");
            for (int i = 0; i < tempA.length; i++) {
                if ((tempA[i] != null) && (!"".equals(tempA[i]))) {
                    RPC_ACTIONS.add(new RPC_Action(tempA[i].substring(0,
                            tempA[i].indexOf(",")).trim(), tempA[i].substring(
                            tempA[i].indexOf(",") + 1).trim()));
                }
            }
            RPC_ACTIONS.add(new RPC_Action("JSONUtil", "com.deya.wcm.jsonlistener.JSONUtil"));
            String bclassList = FileOperation.readFileToString(JconfigUtilContainer.bashConfig().getProperty("path", "", "jsonrpcFile").replace("jsonrpc.properties", "bjsonrpc.properties"));
            bclassList = bclassList.replace("jsonrpc.properties", "bjsonrpc.properties");

            bclassList = bclassList.replaceAll("\r\n", "|").replaceAll("\r", "|").replaceAll("\n", "|");
            String[] btempA = bclassList.split("\\|");
            for (int i = 0; i < btempA.length; i++) {
                if ((btempA[i] != null) && (!"".equals(btempA[i]))) {
                    BRPC_ACTIONS.add(new RPC_Action(btempA[i].substring(0,
                            btempA[i].indexOf(",")).trim(), btempA[i].substring(
                            btempA[i].indexOf(",") + 1).trim()));
                }
            }
            BRPC_ACTIONS.add(new RPC_Action("JSONUtil", "com.deya.wcm.jsonlistener.JSONUtil"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestInitialized(ServletRequestEvent sre) {
        this.request = ((HttpServletRequest) sre.getServletRequest());
        HttpSession session = null;

        if(request != null)
        {
            session = request.getSession();
        }

        JSONRPCBridge bridge = new JSONRPCBridge();

        if (this.request.getRequestURI().endsWith("/sys/JSON-RPC")) {
            session.setAttribute(JSON_RPC_BRIDGE_KEY, bridge);
            bridge.registerObject("session", session);
            for (RPC_Action action : RPC_ACTIONS) {
                try {
                    String name = action.getName();
                    String className = action.getClassName();
                    Object obj = Class.forName(className).newInstance();
                    bridge.registerObject(name, obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (this.request.getRequestURI().endsWith("/JSON-RPC")) {
            session.setAttribute(JSON_RPC_BRIDGE_KEY, bridge);
            bridge.registerObject("session", session);
            for (RPC_Action action : BRPC_ACTIONS)
                try {
                    String name = action.getName();
                    String className = action.getClassName();
                    Object obj = Class.forName(className).newInstance();
                    bridge.registerObject(name, obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    public void requestDestroyed(ServletRequestEvent event) {
    }

    public void sessionCreated(HttpSessionEvent event) {
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        session.removeAttribute(JSON_RPC_BRIDGE_KEY);
    }
}

class RPC_Action {
    private String name;
    private String className;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public RPC_Action(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (
                this.className == null ? 0 : this.className.hashCode());
        result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RPC_Action other = (RPC_Action) obj;
        if (this.className == null) {
            if (other.className != null)
                return false;
        } else if (!this.className.equals(other.className))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        return true;
    }

    public String toString() {
        return "name : " + this.name + "\t className : " + this.className;
    }
}
