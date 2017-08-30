package com.sso;

import com.deya.util.CryptoTools;
import com.deya.util.DateUtil;
import com.deya.util.RandomStrg;
import com.deya.util.jconfig.JconfigUtilContainer;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description: 同步银海组织机构跟用户
 * @User: program
 * @Date: 2016/11/29
 */
public class SyncOrg {

    private static String wsdlUrl = "";
    private static String targetNamespace = "http://service.deliverdata2oa.oa.subSystem.yinhai.com/";
    private static String methodName = "doSync";
    private static String paramName = "paramXml";
    private static String syncName = "dept";
    private static String reqident = "";
    private static String forgcode = "";
    private static String torgcode = "";
    private static String certcode = "";
    private static boolean isAdd = false;

    public static void initParams() {
        wsdlUrl = JconfigUtilContainer.bashConfig().getProperty("syncOrgOrUserUrl", "", "sso");
        reqident = JconfigUtilContainer.bashConfig().getProperty("reqident", "", "sso");
        ;
        forgcode = JconfigUtilContainer.bashConfig().getProperty("forgcode", "", "sso");
        ;
        torgcode = JconfigUtilContainer.bashConfig().getProperty("torgcode", "", "sso");
        ;
        certcode = JconfigUtilContainer.bashConfig().getProperty("certcode", "", "sso");
        ;
    }

    public static String getparamValue(Integer type) {
        StringBuilder _xmlstr = new StringBuilder();
        _xmlstr.append("<![CDATA[<data><reqident>").append(reqident).append("</reqident>");
        if (syncName.equals("dept")) {
            _xmlstr.append("<txcode>SYSNC_DEPART</txcode>");
        } else {
            _xmlstr.append("<txcode>SYSNC_USER</txcode>");
        }
        _xmlstr.append("<txdate/>");
        _xmlstr.append("<txtime/>");
        _xmlstr.append("<forgcode>" + forgcode + "</forgcode>");
        _xmlstr.append("<torgcode>" + torgcode + "</torgcode>");
        _xmlstr.append("<reqfilerows/>");
        _xmlstr.append("<reqtxfile/>");
        _xmlstr.append("<reqfilemny/>");
        _xmlstr.append("<txchannel>0</txchannel>");
        _xmlstr.append("<enccode/>");
        _xmlstr.append("<certcode>" + certcode + "</certcode>");
        if (type == 1) {
            _xmlstr.append("<type>1</type>");
            _xmlstr.append("<maxid/>");
            isAdd = false;
        }
        if (type == 0) {
            isAdd = true;
            _xmlstr.append("<type>0</type>");
            if (syncName.equals("dept")) {
                int maxId = DeptManager.getMaxDeptId();
                _xmlstr.append("<maxid>" + maxId + "</maxid>");
            }
            if (syncName.equals("user")) {
                int maxId = UserManager.getMaxUserId();
                _xmlstr.append("<maxid>" + maxId + "</maxid>");
            }
        }
        _xmlstr.append("<startno>1</startno>");
        _xmlstr.append("<pagesize>500</pagesize>");
        _xmlstr.append("</data>]]>");
        return _xmlstr.toString();
    }

    /**
     * 用http方式调用webservices
     */
    public static List syncOrgDeptOrUser(String name, int type) {
        initParams();
        System.out.println("***********************同步" + syncName + " 开始***" + DateUtil.getCurrentDateTime() + "***********************");
        //服务的地址
        URL wsUrl = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            syncName = name;
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
                String soap = getSoapStr(type);
                os.write(soap.getBytes());
                is = conn.getInputStream();
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String s = sb.toString();
                System.out.println("*****************返回报文(未处理)************" + s);
                s = s.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"");
                System.out.println("*****************返回报文(处理过)************" + s);
                closeConnect(conn, is, os);
                List result = getResult(s);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            closeConnect(conn, is, os);
        } finally {
            closeConnect(conn, is, os);
        }
        System.out.println("***********************同步" + syncName + "结束****" + DateUtil.getCurrentDateTime() + "****************************");
        return null;
    }

    public static String getSoapStr(Integer type) {
        StringBuilder _xmlstr = new StringBuilder();
        _xmlstr.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ser=\"");
        _xmlstr.append(targetNamespace).append("\">");
        _xmlstr.append("<soapenv:Header/><soapenv:Body>");
        _xmlstr.append("<ser:").append(methodName).append(">");
        _xmlstr.append("<").append(paramName).append(">");
        _xmlstr.append(getparamValue(type));
        _xmlstr.append("</").append(paramName).append(">");
        _xmlstr.append("</ser:").append(methodName).append(">");
        _xmlstr.append("</soapenv:Body> </soapenv:Envelope>");
        return _xmlstr.toString();
    }

    public static List getResult(String s) {
        try {
            s = s.substring(s.indexOf("<?xml"), s.indexOf("</return>"));
            Document xmlDoc = DocumentHelper.parseText(s);
            Element rootElement = xmlDoc.getRootElement();
            if (syncName.equals("dept")) {
                Iterator departs = rootElement.elementIterator("depart");
                List<DeptBean> deptBeanList = new ArrayList<DeptBean>();
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
                        if (orgid != null && !"".equals(orgid) && !"null".equals(orgid)) {
                            deptBean.setDept_id(Integer.parseInt(orgid));
                        }
                        if (orgname != null && !"".equals(orgname) && !"null".equals(orgname)) {
                            deptBean.setDept_name(orgname);
                        }
                        if (orgpid != null && !"".equals(orgpid) && !"null".equals(orgpid)) {
                            deptBean.setParent_id(Integer.parseInt(orgpid));
                        }
                        if (orgtype != null && !"".equals(orgtype) && !"null".equals(orgtype)) {
                            deptBean.setDept_code(orgtype);
                        }
                        if (orgnamepath != null && !"".equals(orgnamepath) && !"null".equals(orgnamepath)) {
                            deptBean.setAddress(orgnamepath);
                        }
                        if (orgidpath != null && !"".equals(orgidpath) && !"null".equals(orgidpath)) {
                            deptBean.setArea_code(orgidpath);
                            deptBean.setTree_position("$" + orgidpath.replaceAll("\\/\\/", "\\$").replaceAll("\\/", "\\$") + "$");
                        }
                        if (deptBean.getDept_id() == 1) {
                            deptBean.setParent_id(0);
                        }
                        deptBeanList.add(deptBean);
                    }
                    if (deptBeanList.size() > 0) {
                        DeptManager.inserSynctDept(deptBeanList, isAdd);
                    }
                }
                return deptBeanList;
            } else {
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
                        /*userBean.setAddress("");
                        userBean.setAge(20+"");
                        userBean.setBirthday("");
                        userBean.setColleges("");
                        userBean.setDegree("");
                        userBean.setEmail("");
                        userBean.setFunctions("");
                        userBean.setGraduation_time("");
                        userBean.setHealth("");
                        userBean.setIdcard("");
                        userBean.setIs_marriage(0);
                        userBean.setIs_publish(1);
                        userBean.setNation("");
                        userBean.setNatives("");
                        userBean.setPhone("");
                        userBean.setPhoto("");
                        userBean.setPolitics_status("");
                        userBean.setPostcode("");
                        userBean.setProfessional("");
                        userBean.setResume("");
                        userBean.setSex(0);
                        userBean.setSort(999);
                        userBean.setSummary("");
                        userBean.setTel("");
                        userBean.setTo_work_time("");
                        userBean.setUser_aliasname("");
                        userBean.setUser_memo("");
                        userBean.setUser_photo("");
                        userBean.setUser_realname("");
                        userBean.setUser_status(0);
                        userBean.setUserlevel_value(1);
                        userBean.setWork_desc("");*/
                        UserRegisterBean userRegisterBean = new UserRegisterBean();
                        /*userRegisterBean.setUser_realname("");
                        userRegisterBean.setPassword("");
                        userRegisterBean.setRegister_id(0);
                        userRegisterBean.setRegister_status(0);
                        userRegisterBean.setUser_id(0);
                        userRegisterBean.setUsername("");*/
                        if (userid != null && !"".equals(userid) && !"null".equals(userid)) {
                            userBean.setUser_id(Integer.parseInt(userid));
                            userRegisterBean.setUser_id(Integer.parseInt(userid));
                            userRegisterBean.setRegister_id(Integer.parseInt(userid));
                        } else {
                            System.out.println("用户ID不能为空！！！");
                            break;
                        }
                        if (name != null && !"".equals(name) && !"null".equals(name)) {
                            userBean.setUser_realname(name);
                            userRegisterBean.setUser_realname(name);
                        } else {
                            userBean.setUser_realname("");
                            userRegisterBean.setUser_realname("");
                        }
                        if (sex != null && !"".equals(sex) && !"null".equals(sex)) {
                            if ("男".equals(sex)) {
                                userBean.setSex(1);
                            } else if ("女".equals(sex)) {
                                userBean.setSex(0);
                            } else {
                                userBean.setSex(Integer.parseInt(sex));
                            }
                        }
                        if (tel != null && !"".equals(tel) && !"null".equals(tel)) {
                            userBean.setTel(tel);
                        } else {
                            userBean.setTel("");
                        }
                        if (directorgid != null && !"".equals(directorgid) && !"null".equals(directorgid)) {
                            userBean.setDept_id(Integer.parseInt(directorgid));
                        } else {
                            userBean.setDept_id(1);
                        }
                        if (loginid != null && !"".equals(loginid) && !"null".equals(loginid)) {
                            userRegisterBean.setUsername(loginid);
                        } else {
                            System.out.println("登录名不能为空！！！");
                            break;
                        }
                        CryptoTools ct = new CryptoTools();
                        userRegisterBean.setPassword(ct.encode("111111"));
                        userRegisterBean.setRegister_status(0);
                        userBeanList.add(userBean);
                        userRegisterBeanList.add(userRegisterBean);
                    }
                    System.out.println(userBeanList.size() + "***************");
                    System.out.println(userRegisterBeanList.size() + "********************");
                    //userBeanList = userBeanList.subList(0,1);
                    //userRegisterBeanList = userRegisterBeanList.subList(0,1);
                    if (userBeanList.size() > 0) {
                        UserManager.insertSyncUser(userBeanList, userRegisterBeanList, isAdd);
                    }
                }
                return userBeanList;
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnect(HttpURLConnection conn, InputStream is, OutputStream os) {
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
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("1//24779/24781/25794");
    }

}
