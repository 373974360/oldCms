package com.yinhai.webservice.client;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.services.org.user.UserManager;
import com.deya.wcm.services.cms.info.InfoDesktop;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.cms.workflow.WorkFlowBean;
import com.deya.wcm.services.cms.workflow.WorkFlowManager;
import com.deya.wcm.bean.cms.workflow.WorkFlowStepBean;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/1/21
 * Time: 15:06
 * Description: 信息代办推送
 * Version: v1.0
 */


public class DaiBanServiceClient {
    private static String buscode = "";//业务登记号
    private static String busname = "";//业务名称
    private static String stepcode = "";//当前环节代码
    private static String stepname = "";//当前环节名称
    private static String resultname = "";//环节状态名称
    private static String apvaladdress = "";//审批地址
    private static String remark = "";//业务摘要
    private static String curcode = "";//上一环节受理人编号
    private static String curname = "";//上一环节受理人名称
    private static String curtime = "";//上一环节受理时间
    private static String sysnumber = "mh";//系统编号

    public static int doService() {
        List<UserBean> userList = UserManager.getUserList();
        Map<String,String> con_map = new HashMap<>();
        con_map.put("app_id","cms");
        con_map.put("final_status","0");
        con_map.put("info_status","2");
        con_map.put("page_size","100000");
        con_map.put("site_id","CMScqgjj");
        con_map.put("sort_name","ci.released_dtime desc,ci.id");
        con_map.put("sort_type","desc");
        con_map.put("start_num","0");
        StringBuilder _xmlstr = new StringBuilder();
        if(userList!=null && !userList.isEmpty()){
            for(UserBean user:userList){
                con_map.put("user_id",user.getUser_id()+"");
                Map<String,Object> resultMap = InfoDesktop.getWaitVerifyInfoList(con_map);
                List<InfoBean> infoList = (List<InfoBean>) resultMap.get("info_List");
                if(infoList!=null && !infoList.isEmpty()){
                    for(InfoBean info:infoList){
                        _xmlstr.append("<list>");
                        _xmlstr.append("<buscode>").append(info.getInfo_id()).append("</buscode>");
                        _xmlstr.append("<loginid>").append(user.getUser_id()).append("</loginid>");
                        _xmlstr.append("<busname>").append(info.getTitle()).append("</busname>");
                        if(info.getWf_id()==0){
                            info.setWf_id(1);
                        }
                        WorkFlowBean flowBean = WorkFlowManager.getWorkFlowBean(info.getWf_id());
                        List<WorkFlowStepBean> stepList = flowBean.getWorkFlowStep_list();
                        for(WorkFlowStepBean stepBean:stepList){
                            if(info.getStep_id()==stepBean.getStep_id()-1){
                                _xmlstr.append("<stepcode>").append(stepBean.getStep_id()).append("</stepcode>");
                                _xmlstr.append("<stepname>").append(stepBean.getStep_name()).append("</stepname>");
                            }
                        }
                        _xmlstr.append("<resultname>").append("待审批").append("</resultname>");
                        _xmlstr.append("<apvaladdress>").append("/sys/index.jsp?menuUrl=/sys/cms/info/article/verifyInfoList.jsp").append("</apvaladdress>");
                        _xmlstr.append("<remark>").append("待审批信息").append("</remark>");
                        _xmlstr.append("<curcode>").append(info.getModify_user()).append("</curcode>");
                        _xmlstr.append("<curname>").append(UserManager.getUserRealName(info.getModify_user()+"")).append("</curname>");
                        _xmlstr.append("<curtime>").append(info.getOpt_dtime()).append("</curtime>");
                        _xmlstr.append("<sysnumber>").append("mh").append("</sysnumber>");
                        _xmlstr.append("</list>");
                    }
                }
            }
        }
       String s = WebServiceClientUtil.doHttpPost("trader", "NLC108", _xmlstr.toString());
        int result = getResult(s);
        return result;
    }

    public static int getResult(String s) {
        String textTrim = "0";
        try {
            s = s.substring(s.indexOf("<data>"), s.indexOf("</return>"));
            Document xmlDoc = DocumentHelper.parseText(s);
            System.out.println(s + "----------------------------------");
            Element rootElement = xmlDoc.getRootElement();
            Element rescode = rootElement.element("rescode");
            textTrim = rescode.getTextTrim();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(textTrim);
    }
}
