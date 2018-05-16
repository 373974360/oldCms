<%@ page import="com.deya.wcm.bean.org.dept.DeptBean"%><%@ page import="com.yinhai.webservice.client.SyncOrgServiceClient"%><%@ page import="java.util.List"%><%@ page contentType="application/json; charset=utf-8"%>
<%@ page language="java"%>
<%
    out.println("-------------------开始增量同步组织机构-----------------");
    List deptList = SyncOrgServiceClient.syncOrgDeptOrUser("dept",0);
    if(deptList != null && deptList.size() > 0){
        out.println("-------------------总共有" + deptList.size() + "个组织机构需要同步 -----------------");
        for (Object o : deptList) {
            DeptBean deptBean = (DeptBean)o;
            out.println("************同步组织机构："+ deptBean.getDept_name());
        }
    }
    out.println("-------------------增量同步组织机构成功-----------------");
%>
