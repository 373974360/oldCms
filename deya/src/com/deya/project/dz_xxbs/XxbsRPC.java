package com.deya.project.dz_xxbs;

import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.info.ArticleBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.cms.info.ModelUtil;
import com.deya.wcm.services.org.dept.DeptManager;
import com.deya.wcm.services.org.user.UserLogin;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XxbsRPC {

    public static List<XxbsBean> getXxbsInfoList(Map<String, String> map) {
        map.put("dept_ids",getDeptIds(map.get("dept_id")));
        return XxbsManager.getXxbsInfoList(map);
    }

    public static int getXxbsInfoCount(Map<String, String> map) {
        map.put("dept_ids",getDeptIds(map.get("dept_id")));
        return Integer.parseInt(XxbsManager.getXxbsInfoCount(map));
    }

    public static XxbsBean getXxbs(String id) {
        return XxbsManager.getXxbs(id);
    }

    public static boolean insertXxbs(XxbsBean xxbs, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return XxbsManager.insertXxbs(xxbs, stl);
        }
        return false;
    }

    public static boolean updateXxbs(XxbsBean xxbs, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return XxbsManager.updateXxbs(xxbs, stl);
        }
        return false;
    }

    public static boolean deleteXxbs(List<XxbsBean> list, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return XxbsManager.deleteXxbs(list, stl);
        }
        return false;
    }

    public static boolean updateInfoStatus(List<XxbsBean> list, String status, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return XxbsManager.updateInfoStatus(list, status, stl);
        }
        return false;
    }
    public static boolean updateNoPassStatus(List<XxbsBean> list,String desc, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return XxbsManager.updateNoPassStatus(list, desc, stl);
        }
        return false;
    }
    public static boolean infoPass(List<XxbsBean> list,String cat_id,String app,String site_id, HttpServletRequest request){
        boolean result = true;
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            if(list!=null&&list.size()>0){
                for(XxbsBean b:list){
                    ArticleBean article = new ArticleBean();

                    article.setModel_id(11);
                    article.setApp_id(app);
                    article.setSite_id(site_id);

                    int id = InfoBaseManager.getNextInfoId();
                    article.setId(id);
                    article.setInfo_id(id);
                    article.setCat_id(Integer.parseInt(cat_id));

                    article.setTitle(b.getTitle());//信息标题
                    article.setThumb_url(b.getThumb_url());//标题图片
                    article.setContent_url(b.getContent_url());
                    article.setAuthor(b.getAuthor());
                    article.setDescription(b.getDescription());
                    article.setInfo_keywords(b.getKeywords());
                    article.setEditor(b.getEditor());
                    article.setReleased_dtime(b.getReleased_dtime());
                    article.setInfo_content(b.getContents());
                    article.setInfo_status(8);
                    article.setWeight(60);
                    article.setInput_user(b.getInput_user());
                    article.setInput_dtime(b.getInput_dtime());
                    article.setModify_dtime(b.getModify_dtime());

                    LoginUserBean userBean = UserLogin.getUserBySession(request);
                    article.setModify_user(userBean.getUser_id());
                    article.setModify_user_name(userBean.getUser_name());

                    if(!ModelUtil.insert(article,"article",null)) {
                        result = false;
                        break;
                    }
                }
            }
            if(result){
                CategoryBean catbean = CategoryManager.getCategoryBeanCatID(Integer.parseInt(cat_id),site_id);
                XxbsDAO.infoPass(list,catbean.getCat_id()+"",catbean.getCat_cname(),stl);
            }
        }else{
            result = false;
        }
        return result;
    }

    public static String getDeptIds(String deptId){
        String deptids = DeptManager.getAllChildDeptIDSByID(deptId);
        if(StringUtils.isNotEmpty(deptids)){
            deptids+=","+deptId;
        }else{
            deptids = deptId;
        }
        return deptids;
    }

    public static List<Map<String,String>> getXxbsDeptCount(Map<String, String> map) {
        List<Map<String,String>> resultList = new ArrayList<>();
        String deptids = getDeptIds(map.get("dept_id"));
        String[] arrDeptIds = deptids.split(",");
        for(String id:arrDeptIds){
            if(Integer.parseInt(id)>2){
                map.put("do_dept",id);
                Map<String,String> resMap = new HashMap<>();
                DeptBean dept = DeptManager.getDeptBeanByID(id);
                resMap.put("deptName",dept.getDept_name());

                map.put("info_status","1");
                int count_a = Integer.parseInt(XxbsManager.getXxbsDeptCount(map));
                resMap.put("deptCount_a",count_a+"");

                map.put("info_status","2");
                int count_b = Integer.parseInt(XxbsManager.getXxbsDeptCount(map));
                resMap.put("deptCount_b",count_b+"");

                map.put("info_status","3");
                int count_c = Integer.parseInt(XxbsManager.getXxbsDeptCount(map));
                resMap.put("deptCount_c",count_c+"");

                int count_d = count_a+count_b+count_c;
                resMap.put("deptCount_d",count_d+"");
                resultList.add(resMap);
            }
        }
        return resultList;
    }

}
