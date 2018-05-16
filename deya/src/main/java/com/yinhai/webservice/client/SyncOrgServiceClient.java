package com.yinhai.webservice.client;

import com.deya.util.CryptoTools;
import com.deya.util.DateUtil;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.bean.org.user.UserRegisterBean;
import com.deya.wcm.services.org.dept.DeptManager;
import com.deya.wcm.services.org.user.UserManager;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description: 同步银海组织机构跟用户
 * @User: program
 * @Date: 2016/11/29
 */
public class SyncOrgServiceClient {
    private static String syncName = "dept";
    private static boolean isAdd = false;

    public static String getparamValue(Integer type) {
        StringBuilder _xmlstr = new StringBuilder();
        _xmlstr.append("<txdate/>");
        _xmlstr.append("<txtime/>");
        _xmlstr.append("<reqfilerows/>");
        _xmlstr.append("<reqtxfile/>");
        _xmlstr.append("<reqfilemny/>");
        _xmlstr.append("<txchannel>0</txchannel>");
        _xmlstr.append("<enccode/>");
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
        return _xmlstr.toString();
    }

    /**
     * 用http方式调用webservices
     */
    public static List syncOrgDeptOrUser(String name, int type) {
        System.out.println("***********************同步" + name + " 开始***" + DateUtil.getCurrentDateTime() + "***********************");
        String s;
        syncName = name;
        if (name.equals("dept")) {
            s = WebServiceClientUtil.doHttpPost("sso", "SYSNC_DEPART", getparamValue(type));
        } else {
            s = WebServiceClientUtil.doHttpPost("sso", "SYSNC_USER", getparamValue(type));
        }
        s = s.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"");
        List result = getResult(s);
        System.out.println("***********************同步" + name + "结束****" + DateUtil.getCurrentDateTime() + "****************************");
        return result;
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

    public static void main(String[] args) {
        System.out.println("1//24779/24781/25794");
    }

}
