<%@ page import="java.util.List"%><%@ page import="com.deya.wcm.bean.org.dept.DeptBean"%><%@ page import="com.yinhai.sso.SyncOrg"%><%@ page contentType="application/json; charset=utf-8"%>
<%@ page language="java"%>
<%
    out.println("-------------------开始全量同步组织机构-----------------");
    List deptList = SyncOrg.syncOrgDeptOrUser("dept",1);
    if(deptList != null && deptList.size() > 0){
        out.println("-------------------总共有" + deptList.size() + "个组织机构需要同步 -----------------");
        for (Object o : deptList) {
            DeptBean deptBean = (DeptBean)o;
            out.println("************同步组织机构："+ deptBean.getDept_name());
        }
    }
    out.println("-------------------全量同步组织机构成功-----------------");
%>
