package com.deya.project.dz_jyhgl;

import com.deya.wcm.bean.cms.count.CmsCountBean;
import com.deya.wcm.bean.cms.count.SiteCountBean;
import com.deya.wcm.dao.cms.count.CmsCountDAO;
import com.deya.wcm.services.cms.category.CategoryRPC;
import com.deya.wcm.services.cms.category.ZTCategoryManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.*;

public class KblmManager {
    public static List<SiteCountBean> getNullCategoryByCount(Map<String, String> mp){
        List<SiteCountBean> listResult = getInputCountListByCate(mp);
        return listResult;
    }
    /**
     * 取得所选栏目下的工作量统计信息
     * @param mp
     * @return	取得所选栏目下的工作量统计信息列表
     */
    public static List<SiteCountBean> getInputCountListByCate(Map<String, String> mp){
        List<SiteCountBean> resultList = new ArrayList<SiteCountBean>();//存放结果
        try{
            //System.out.println("---getInputCountListByCate---map = " + mp);
            mp.remove("info_status"); // 取得全部信息
            Map<String, CmsCountBean> all_m = CmsCountDAO.getInputCountListByCate(mp);

            mp.put("info_status", "8"); // 取得已发信息
            Map<String, CmsCountBean> m = CmsCountDAO.getInputCountListByCate(mp);
            mp.put("is_pic", "1"); // 取得已发信息 并且有图片
            Map<String, CmsCountBean> m1 = CmsCountDAO.getInputCountListByCate(mp);

            List<CmsCountBean> ret = new ArrayList<CmsCountBean>();
            for(String s : all_m.keySet()){
                CmsCountBean bean = all_m.get(s);
                bean.setInputCount(bean.getCount()); // 设置取得的全部信息
                if(m.containsKey(s)){
                    CmsCountBean temBean = m.get(s);
                    bean.setReleasedCount(temBean.getCount()); //设置取得的已发信息
                }

                if(m1.containsKey(s)){
                    CmsCountBean temBean = m1.get(s);
                    bean.setPicInfoCount(temBean.getCount()); //设置取得的已发信息
                }

                bean.setReleaseRate();  //设置发布率
                ret.add(all_m.get(s));
            }

            //得到用户自己的栏目管理权限树
            String site_id = (String)mp.get("site_id");
            String input_users = (String)mp.get("input_user");
            if(input_users==null){
                input_users = "0";
            }
            int user_id = Integer.parseInt(input_users);
            //String treeStr = CategoryRPC.getInfoCategoryTreeByUserID(site_id,user_id);
            String treeStr = CategoryRPC.getCategoryTreeBySiteID(site_id);
            //System.out.println("---CmsCountManager--getInputCountListByCate---catetreeStr = " + treeStr);
            //添加 专题栏目的统计        add by 2012-11-28
            String ztTreeStr = getZTCategoryTreeStrWidthZTName(site_id);
            String allStr = treeStr.substring(0,treeStr.length()-1)+","+ztTreeStr+"]";
            //System.out.println("---CmsCountManager--getInputCountListByCate---treeStr   allStr	==	"+allStr);
            JSONArray jsonArray = JSONArray.fromObject(allStr);
            //JSONArray jsonArray = JSONArray.fromObject(treeStr);
            Iterator it = jsonArray.iterator();
            while(it.hasNext()){
                JSONObject jsonObject = (JSONObject)it.next();
                SiteCountBean  siteCountBean = doOutTreeBeanByCate(jsonObject,ret);
                resultList.add(siteCountBean);
            }
            return resultList;
        }catch (Exception e) {
            e.printStackTrace();
            return resultList;
        }
    }

    /**
     * 根据站点ID得到有权限管理的专题分类树
     * @param String site_id
     * @return String
     * */
    public static String getZTCategoryTreeStrWidthZTName(String site_id)
    {
        String roo_name = "专题栏目";
        String cate_str = ZTCategoryManager.getZTCategoryTreeJsonStr(site_id);
        if(cate_str != null && !"[]".equals(cate_str))
            return "{\"id\":0,\"text\":\""+roo_name+"\",\"attributes\":{\"url\":\"\",\"handl\":\"\"},\"state\":\"'closed'\",\"children\":"+cate_str+"}";
        else
            return "";
    }

    //getInputCountListByCate  -- 调用的递归方法用
    public static SiteCountBean doOutTreeBeanByCate(JSONObject jsonObject,List<CmsCountBean> list){
        SiteCountBean siteCountBean = new SiteCountBean();
        try{
            String str = jsonObject.toString();
            JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("children"));
            if(!jsonArray.toString().equals("[null]") && jsonArray!=null && !"".equals(jsonArray) && jsonArray.size()>0){//有子节点
                int cat_id = Integer.valueOf(String.valueOf(jsonObject.get("id")));

                siteCountBean.setCat_id(cat_id);
                siteCountBean.setIs_leaf(false);
                siteCountBean.setCat_name(String.valueOf(jsonObject.get("text")));
                int inputCount = 0;
                int releasedCount = 0; //发布信息条数
                int picReleasedCount = 0; //发布信息条数 并且有图片
                Iterator it = jsonArray.iterator();
                List<SiteCountBean> childList = new ArrayList<SiteCountBean>();//存放结果
                while(it.hasNext()){
                    Object object = it.next();
                    if(object!=null){
                        JSONObject jsonObject2 = (JSONObject)object;
                        if(jsonObject2!=null){
                            SiteCountBean countBean = doOutTreeBeanByCate(jsonObject2,list);
                            if(countBean!=null){
                                inputCount += countBean.getInputCount();
                                releasedCount += countBean.getReleasedCount();
                                picReleasedCount += countBean.getPicReleasedCount();
                                childList.add(countBean);
                            }
                        }
                    }
                }
                siteCountBean.setInputCount(inputCount);
                siteCountBean.setReleasedCount(releasedCount);
                siteCountBean.setPicReleasedCount(picReleasedCount);
                siteCountBean.setReleaseRate();
                siteCountBean.setChild_list(childList);

            }else{//没有子节点
                int cat_id = Integer.valueOf(String.valueOf(jsonObject.get("id")));

                siteCountBean.setCat_id(cat_id);
                siteCountBean.setIs_leaf(true);
                siteCountBean.setCat_name(String.valueOf(jsonObject.get("text")));
                int inputCount = 0;
                int releasedCount = 0; //发布信息条数
                int picReleasedCount = 0; //发布信息条数 并且有图片
                for(CmsCountBean cmsCountBean : list){
                    if(cat_id==cmsCountBean.getCat_id()){
                        inputCount += cmsCountBean.getInputCount();
                        releasedCount += cmsCountBean.getReleasedCount();
                        picReleasedCount += cmsCountBean.getPicInfoCount();
                        break;
                    }
                }
                siteCountBean.setInputCount(inputCount);
                siteCountBean.setReleasedCount(releasedCount);
                siteCountBean.setPicReleasedCount(picReleasedCount);
                siteCountBean.setReleaseRate();
            }

            return siteCountBean;
        }catch (Exception e) {
            e.printStackTrace();
            return siteCountBean;
        }
    }
}
