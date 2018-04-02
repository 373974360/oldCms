<%@ page import="java.util.List"%><%@ page import="com.deya.wcm.bean.org.user.UserBean"%><%@ page import="com.yinhai.sso.SyncOrg"%><%@ page contentType="application/json; charset=utf-8"%>
<%@ page language="java"%>
<%
    out.println("-------------------开始增量同步用户-----------------");
    List userList = SyncOrg.syncOrgDeptOrUser("user",0);
    if(userList != null && userList.size() > 0){
        out.println("-------------------总共有" + userList.size() + "个用户需要同步 -----------------");
        for (Object o : userList) {
            UserBean user = (UserBean)o;
            out.println("************同步用户："+ user.getUser_realname());
        }
    }
    out.println("-------------------增量同步用户成功-----------------");
%>
