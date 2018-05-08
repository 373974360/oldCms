package com.deya.wcm.services.system.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.template.TemplateEditBean;
import com.deya.wcm.bean.system.template.TemplateVerBean;
import com.deya.wcm.dao.system.template.TemplateVerDAO;

/**
 * 模板的版本控制类
 * @author 符江波
 * @date   2011-3-24 上午10:19:00
 */
public class TemplateVerManager {//因为在TemplateUtils类中做了对全部发布状态模板的缓存，这里就屏蔽了模板缓存
    //前台展示时候，取得的模板都是在template_map里边，key以tid_tver命名
    private static Map<String,TemplateVerBean> template_map = new HashMap<String, TemplateVerBean>();

    static{
        //reloadTemplate();
    }

    public static void reloadTemplate()
    {
        List<TemplateVerBean> template_list = TemplateVerDAO.getAllTemplateVerList();
        template_map.clear();
        if(template_list != null && template_list.size() > 0)
        {
            for(int i=0;i<template_list.size();i++){
                if(template_list.get(i).getT_status() == 0)continue;
                template_map.put(template_list.get(i).getT_id()+"_"+template_list.get(i).getSite_id()+"_"+template_list.get(i).getT_ver(), template_list.get(i));
            }
        }
    }

    /**
     * 根据ID得到模板信息
     * @param int t_id
     * @return TemplateVerBean
     * */
    public static TemplateVerBean getTemplateVerBean(int t_id, String ver, String site_id){
//		if(template_map.containsKey(t_id+"_"+site_id+"_"+ver)){
//			return template_map.get(t_id+"_"+site_id+"_"+ver);
//		}else{
//			TemplateVerBean tvb = TemplateVerDAO.getTemplateVerBean(t_id,site_id,ver);
//			if(tvb != null){
//				template_map.put(t_id+"_"+site_id+"_"+ver, tvb);
//				return tvb;
//			}else
//				return null;
//		}
        return TemplateVerDAO.getTemplateVerBean(t_id,site_id,ver);
    }

    /**
     * 根据ID得到简版的模板信息，用于列表显示，不包括content的内容
     * @param int t_id
     * @return TemplateVerBean
     * */
    public static TemplateVerBean getSimpleTemplateVerBean(int t_id, String ver, String site_id){
//		if(template_map.containsKey(t_id+"_"+site_id+"_"+ver)){
//			return template_map.get(t_id+"_"+site_id+"_"+ver);
//		}else{
//			TemplateVerBean tvb = TemplateVerDAO.getTemplateVerBean(t_id,site_id,ver);
//			if(tvb != null){
//				template_map.put(t_id+"_"+site_id+"_"+ver, tvb);
//				return tvb;
//			}else
//				return null;
//		}
        TemplateVerBean tb = TemplateVerDAO.getTemplateVerBean(t_id,site_id,ver);
        if(tb != null)
            tb.setT_content("");
        return tb;
    }



    public static TemplateVerBean getTemplateVerBeanByState(int t_id, String site_id){
        return TemplateVerDAO.getTemplateVerBeanByState(t_id, site_id);
    }

    public static TemplateVerBean getTemplateVerBeanForDB(int t_id, String site_id, String ver)
    {
        return TemplateVerDAO.getTemplateVerBean(t_id, site_id, site_id);
    }

    /**
     * 得到模板总数
     * @param
     * @return String
     * */
    public static String getTemplateVerCount(Map<String,String> con_map)
    {
        return TemplateVerDAO.getTemplateVerCount(con_map);
    }

    /**
     * 修改模板
     * @param author
     * @return boolean
     */
    public static boolean updateTemplateVer(TemplateVerBean template, SettingLogsBean stl){
        if(TemplateVerDAO.updateTemplateVer(template, stl)){
//			template_map.remove(template.getT_id()+"_"+template.getSite_id()+"_"+template.getT_ver());
//			template_map.put(template.getT_id()+"_"+template.getSite_id()+"_"+template.getT_ver(), template);
            return true;
        }
        return false;
    }

    /**
     * 添加模板
     * @param template
     * @return boolean
     */
    public static boolean addTemplateVer(TemplateVerBean template, SettingLogsBean stl){
        if(template == null){
            return false;
        }

        if(TemplateVerDAO.addTemplateVer(template,stl)){
            //template_map.put(template.getT_id()+"_"+template.getSite_id()+"_"+template.getT_ver(), template);
            return true;
        }
        return false;
    }

    /**
     * 模板列表
     * @return List<TemplateVerBean>
     */
    public static List<TemplateVerBean> getAllTemplateVerList()
    {
		/*Set<String> set = template_map.keySet();
		List<TemplateVerBean> list = new ArrayList<TemplateVerBean>();
		for(String i : set){
			list.add(template_map.get(Integer.parseInt(i)));
		}
		return list;*/
        return TemplateVerDAO.getAllTemplateVerList();
    }

    /**
     * 得到模板列表
     * @param con_map
     * @return List<TemplateVerBean>
     */
    public static List<TemplateVerBean> getTemplateVerListForDB(Map<String,String> con_map)
    {
        return TemplateVerDAO.getTemplateVerListForDB(con_map);
    }

    /**
     * 删除模板
     * @param t_id
     * @return boolean
     */
    public static boolean delTemplateVerByIds(String t_ids, String site_id, SettingLogsBean stl){
        if(TemplateVerDAO.delTemplateVer(t_ids, site_id, stl)){
			/*if(t_ids != null){
				for(String id : t_ids.split(",")){
					if(id == null || id.trim().length() == 0)continue;
					for(int i = 0; i<getTemplateVerNum(Integer.parseInt(id),site_id) + 1; i++){
						template_map.remove(id+"_"+site_id+"_"+i);
					}
				}
			}*/
            return true;
        }
        return false;
    }

    /**
     * 得到一个模板的最新版本号
     * @param t_id
     * @return String
     */
    public static int getTemplateVerNum(int t_id, String site_id){
        String num = TemplateVerDAO.getTemplateVerNum(t_id,site_id);
        if(num == null || num.equals("")){
            num = "0";
        }
        return Integer.parseInt(num);
    }

    public static boolean recoveryTemplateVer(int template_id, String site_id, String version, SettingLogsBean stl){
        TemplateVerBean tvb = getTemplateVerBean(template_id,version,site_id);
        return TemplateEditManager.updateTemplateEdit(TVBtoTEB(tvb),stl);
    }

    private static TemplateEditBean TVBtoTEB(TemplateVerBean teb){
        if(teb == null) return null;
        TemplateEditBean tvb = new TemplateEditBean();
        tvb.setT_id(teb.getT_id());
        tvb.setTcat_id(teb.getTcat_id());
        tvb.setT_ename(teb.getT_ename());
        tvb.setT_cname(teb.getT_cname());
        tvb.setT_path(teb.getT_path());
        tvb.setT_content(teb.getT_content());
        tvb.setT_ver(teb.getT_ver());
        tvb.setCreat_user(teb.getCreat_user());
        tvb.setCreat_dtime(teb.getCreat_dtime());
        tvb.setModify_user(teb.getModify_user());
        tvb.setModify_dtime(teb.getModify_dtime());
        tvb.setApp_id(teb.getApp_id());
        tvb.setSite_id(teb.getSite_id());
        tvb.setT_status("0");
        return tvb;
    }

    public static void main(String[] args) {
        System.out.println(getTemplateVerBean(124,"1","11111ddd"));
    }
}
