package com.sso;

import com.deya.util.CryptoTools;
import com.deya.util.DateUtil;
import com.deya.util.RandomStrg;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.bean.org.user.UserRegisterBean;
import com.deya.wcm.services.org.dept.DeptManager;
import com.deya.wcm.services.org.user.UserManager;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description:
 * @User: program
 * @Date: 2016/11/29
 */
public class SyncOrg {

    private static String wsdlUrl = "http://118.112.188.111:6537/Portal/services/syncOrgOrUserService?wsdl";
    private static String targetNamespace = "http://service.deliverdata2oa.oa.subSystem.yinhai.com/";
    private static String methodName = "doSync";
    private static String paramName = "paramXml";
    private static String syncType = "dept";

    public static String getparamValue() {
        StringBuilder _xmlstr = new StringBuilder();
        _xmlstr.append("<![CDATA[<data><reqident>").append("TD96358447").append("</reqident>");
        if(syncType.equals("dept")){
            _xmlstr.append("<txcode>SYSNC_DEPART</txcode>");
        }else{
            _xmlstr.append("<txcode>SYSNC_USER</txcode>");
        }
        _xmlstr.append("<txdate/>");
        _xmlstr.append("<txtime/>");
        _xmlstr.append("<forgcode>3110102</forgcode>");
        _xmlstr.append("<torgcode>3110gjj</torgcode>");
        _xmlstr.append("<reqfilerows/>");
        _xmlstr.append("<reqtxfile/>");
        _xmlstr.append("<reqfilemny/>");
        _xmlstr.append("<txchannel>0</txchannel>");
        _xmlstr.append("<enccode/>");
        _xmlstr.append("<certcode>yhadmin</certcode>");
        _xmlstr.append("<type>1</type>");
        _xmlstr.append("<maxid/>");
        _xmlstr.append("<startno>1</startno>");
        _xmlstr.append("<pagesize>500</pagesize>");
        _xmlstr.append("</data>]]>");
        return _xmlstr.toString();
    }

    /**
     * 用http方式调用webservices
     */
    public static void syncOrgDeptOrUser(String type) {
        System.out.println("***********************同步" + syncType + "开始***"+ DateUtil.getCurrentDateTime()+"***********************");
        //服务的地址
        URL wsUrl = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            syncType = type;
            wsUrl = new URL(wsdlUrl);
            conn = (HttpURLConnection) wsUrl.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
                os = conn.getOutputStream();
                //请求体
                String soap = getSoapStr();
                os.write(soap.getBytes());
                is = conn.getInputStream();
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String s = sb.toString();
                s = s.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
                closeConnect(conn,is,os);
                getResult(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
            closeConnect(conn,is,os);
        } finally {
            closeConnect(conn,is,os);
        }
        System.out.println("***********************同步" + syncType + "结束****"+ DateUtil.getCurrentDateTime()+"****************************");
    }

    public static String getSoapStr() {
        StringBuilder _xmlstr = new StringBuilder();
        _xmlstr.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ser=\"");
        _xmlstr.append(targetNamespace).append("\">");
        _xmlstr.append("<soapenv:Header/><soapenv:Body>");
        _xmlstr.append("<ser:").append(methodName).append(">");
        _xmlstr.append("<").append(paramName).append(">");
        _xmlstr.append(getparamValue());
        _xmlstr.append("</").append(paramName).append(">");
        _xmlstr.append("</ser:").append(methodName).append(">");
        _xmlstr.append("</soapenv:Body> </soapenv:Envelope>");
        return _xmlstr.toString();
    }

    public static void getResult(String s) {
        try {
            s = s.substring(s.indexOf("<?xml"), s.indexOf("</return>"));
            Document xmlDoc = DocumentHelper.parseText(s);
            Element rootElement = xmlDoc.getRootElement();
            if(syncType.equals("dept")){
                Iterator departs = rootElement.elementIterator("depart");
                List<DeptBean> deptBeanList = new ArrayList<>();
                if (departs != null) {
                    while (departs.hasNext()) {
                        Element depart = (Element) departs.next();
                        //System.out.println(depart.element("rowno").getTextTrim());
                        //System.out.println(depart.element("costomno").getTextTrim());
                        //System.out.println(depart.element("costomnopath").getTextTrim());
                        //System.out.println(depart.element("effective").getTextTrim());
                        String orgid = depart.element("orgid").getTextTrim();
                        String orgname = depart.element("orgname").getTextTrim();
                        String orgpid = depart.element("orgpid").getTextTrim();
                        String orgtype = depart.element("orgtype").getTextTrim();
                        String orgnamepath = depart.element("orgnamepath").getTextTrim();
                        String orgidpath = depart.element("orgidpath").getTextTrim();

                        DeptBean deptBean = new DeptBean();
                        if(orgid != null && !"".equals(orgid) && !"null".equals(orgid)){
                            deptBean.setDept_id(Integer.parseInt(orgid));
                        }
                        if(orgname != null && !"".equals(orgname) && !"null".equals(orgname)){
                            deptBean.setDept_name(orgname);
                        }
                        if(orgpid != null && !"".equals(orgpid) && !"null".equals(orgpid)){
                            deptBean.setParent_id(Integer.parseInt(orgpid));
                        }
                        if(orgtype != null && !"".equals(orgtype) && !"null".equals(orgtype)){
                            deptBean.setDept_code(orgtype);
                        }
                        if(orgnamepath != null && !"".equals(orgnamepath) && !"null".equals(orgnamepath)){
                            deptBean.setAddress(orgnamepath);
                        }
                        if(orgidpath != null && !"".equals(orgidpath) && !"null".equals(orgidpath)){
                            deptBean.setArea_code(orgidpath);
                        }
                        if(deptBean.getDept_id() == 1){
                            deptBean.setParent_id(0);
                        }
                        deptBeanList.add(deptBean);
                    }
                    DeptManager.inserSynctDept(deptBeanList);
                }
            }else{
                Iterator users = rootElement.elementIterator("user");
                List<UserBean> userBeanList = new ArrayList<UserBean>();
                List<UserRegisterBean> userRegisterBeanList = new ArrayList<UserRegisterBean>();
                if (users != null) {
                    while (users.hasNext()) {
                        Element user = (Element) users.next();
                        //System.out.println(depart.element("rowno").getTextTrim());
                        //System.out.println(depart.element("costomno").getTextTrim());
                        //System.out.println(depart.element("costomnopath").getTextTrim());
                        //System.out.println(depart.element("effective").getTextTrim());
                        String userid = user.element("userid").getTextTrim();
                        String name = user.element("name").getTextTrim();
                        String sex = user.element("sex").getTextTrim();
                        String loginid = user.element("loginid").getTextTrim();
                        String tel = user.element("tel").getTextTrim();
                        String directorgid = user.element("directorgid").getTextTrim();

                        UserBean userBean = new UserBean();
                        UserRegisterBean userRegisterBean = new UserRegisterBean();
                        if(userid != null && !"".equals(userid) && !"null".equals(userid)){
                            userBean.setUser_id(Integer.parseInt(userid));
                            userRegisterBean.setUser_id(Integer.parseInt(userid));
                            userRegisterBean.setRegister_id(Integer.parseInt(userid));
                        }
                        if(name != null && !"".equals(name) && !"null".equals(name)){
                            userBean.setUser_realname(name);
                            userRegisterBean.setUser_realname(name);
                        }
                        if(sex != null && !"".equals(sex) && !"null".equals(sex)){
                            if("男".equals(sex)){
                                userBean.setSex(1);
                            }else if("女".equals(sex)){
                                userBean.setSex(0);
                            }else{
                                userBean.setSex(Integer.parseInt(sex));
                            }
                        }
                        if(tel != null && !"".equals(tel) && !"null".equals(tel)){
                            userBean.setTel(tel);
                        }
                        if(directorgid != null && !"".equals(directorgid) && !"null".equals(directorgid)){
                            userBean.setDept_id(Integer.parseInt(directorgid));
                        }
                        if(loginid != null && !"".equals(loginid) && !"null".equals(loginid)){
                            userRegisterBean.setUsername(loginid);
                        }
                        CryptoTools ct = new CryptoTools();
                        userRegisterBean.setPassword(ct.encode(RandomStrg.getRandomStr(null,"10")));
                        userRegisterBean.setRegister_status(0);
                        userBeanList.add(userBean);
                        userRegisterBeanList.add(userRegisterBean);
                    }
                    UserManager.insertSyncUser(userBeanList,userRegisterBeanList);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnect(HttpURLConnection conn,InputStream is,OutputStream os){
        try {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
