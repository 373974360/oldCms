package com.deya.project.dz_jyhgl;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.OutExcel;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.cms.category.CateCurPositionBean;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.services.appeal.count.CountUtil;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.category.CategoryUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InfoUpdateTimerImpl implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String currTime = DateUtil.getCurrentDate()+" 00:00:00";
        System.out.println("定时检查栏目更新情况开始*****" + currTime);
        List<InfoUpdateBean> infoUpdateList = InfoUpdateManager.getStartInfoUpdateList(currTime);
        if(!infoUpdateList.isEmpty()){
            for(InfoUpdateBean infoUpdateBean:infoUpdateList){
                List<InfoUpdateResultBean> checkResultList = new ArrayList<>();
                //设置检测起始时间
                String checkStartTime = DateUtil.getDateBefore(currTime,infoUpdateBean.getGz_day())+" 00:00:00";
                if(infoUpdateBean.getGz_type()<=2){
                    List<CategoryBean> categoryBeanList = getCatIds(infoUpdateBean);//获取该规则下配置的所有末级栏目
                    if(!categoryBeanList.isEmpty()){
                        if(infoUpdateBean.getGz_type()==1){//首页
                            String cat_ids = InfoUpdateManager.getInfoUpdateCategoryByGzId(infoUpdateBean.getGz_id());
                            //检查起点时间 为首页所有栏目最后一条信息的时间
                            InfoUpdateResultBean resultBean = new InfoUpdateResultBean();
                            int count = InfoUpdateManager.getInfoUpdateCountByTimeAndCatId(checkStartTime,currTime,cat_ids);
                            resultBean.setGz_id(infoUpdateBean.getGz_id());
                            resultBean.setCat_id(0);
                            resultBean.setCat_name("网站首页");
                            resultBean.setEnd_update_time(checkStartTime);
                            resultBean.setCheck_time(currTime);
                            resultBean.setGz_day(infoUpdateBean.getGz_day());
                            resultBean.setGz_count(infoUpdateBean.getGz_count());
                            resultBean.setUpdate_count(count);
                            if(count<infoUpdateBean.getGz_count()){
                                resultBean.setUpdate_desc("不合格");
                            }else{
                                resultBean.setUpdate_desc("合格");
                            }
                            checkResultList.add(resultBean);
                        }else if(infoUpdateBean.getGz_type()==2){//列表页
                            for(CategoryBean categoryBean:categoryBeanList){
                                InfoUpdateResultBean resultBean = new InfoUpdateResultBean();
                                int count = InfoUpdateManager.getInfoUpdateCountByTimeAndCatId(checkStartTime,currTime,categoryBean.getCat_id()+"");
                                resultBean.setGz_id(infoUpdateBean.getGz_id());
                                resultBean.setCat_id(categoryBean.getCat_id());
                                resultBean.setCat_name(getCategoryPosition(categoryBean.getCat_id(),infoUpdateBean.getSite_id()));
                                resultBean.setEnd_update_time(checkStartTime);
                                resultBean.setCheck_time(currTime);
                                resultBean.setGz_day(infoUpdateBean.getGz_day());
                                resultBean.setGz_count(infoUpdateBean.getGz_count());
                                resultBean.setUpdate_count(count);
                                if(count<infoUpdateBean.getGz_count()){
                                    resultBean.setUpdate_desc("不合格");
                                }else{
                                    resultBean.setUpdate_desc("合格");
                                }
                                checkResultList.add(resultBean);
                            }
                        }
                    }else{
                        System.out.println("定时检查栏目更新情况开始*****" + infoUpdateBean.getGz_name()+":暂未配置栏目!");
                    }
                }else{
                    if(infoUpdateBean.getGz_type()==3){//诉求
                        int count = InfoUpdateManager.getSqPublishCount(checkStartTime,currTime);
                        InfoUpdateResultBean resultBean = new InfoUpdateResultBean();
                        resultBean.setGz_id(infoUpdateBean.getGz_id());
                        resultBean.setCat_id(0);
                        resultBean.setCat_name("诉求系统");
                        resultBean.setEnd_update_time(checkStartTime);
                        resultBean.setCheck_time(currTime);
                        resultBean.setGz_day(infoUpdateBean.getGz_day());
                        resultBean.setGz_count(infoUpdateBean.getGz_count());
                        resultBean.setUpdate_count(count);
                        if(count<infoUpdateBean.getGz_count()){
                            resultBean.setUpdate_desc("不合格");
                        }else{
                            resultBean.setUpdate_desc("合格");
                        }
                        checkResultList.add(resultBean);
                    }else if(infoUpdateBean.getGz_type()==4){//调查
                        int count = InfoUpdateManager.getSurveyPublishCount(checkStartTime,currTime,infoUpdateBean.getSite_id());
                        InfoUpdateResultBean resultBean = new InfoUpdateResultBean();
                        resultBean.setGz_id(infoUpdateBean.getGz_id());
                        resultBean.setCat_id(0);
                        resultBean.setCat_name("调查系统");
                        resultBean.setEnd_update_time(checkStartTime);
                        resultBean.setCheck_time(currTime);
                        resultBean.setGz_day(infoUpdateBean.getGz_day());
                        resultBean.setGz_count(infoUpdateBean.getGz_count());
                        resultBean.setUpdate_count(count);
                        if(count<infoUpdateBean.getGz_count()){
                            resultBean.setUpdate_desc("不合格");
                        }else{
                            resultBean.setUpdate_desc("合格");
                        }
                        checkResultList.add(resultBean);
                    }else if(infoUpdateBean.getGz_type()==5){//访谈
                        int count = InfoUpdateManager.getSubjectApplyCount(checkStartTime,currTime,infoUpdateBean.getSite_id());
                        InfoUpdateResultBean resultBean = new InfoUpdateResultBean();
                        resultBean.setGz_id(infoUpdateBean.getGz_id());
                        resultBean.setCat_id(0);
                        resultBean.setCat_name("访谈系统");
                        resultBean.setEnd_update_time(checkStartTime);
                        resultBean.setCheck_time(currTime);
                        resultBean.setGz_day(infoUpdateBean.getGz_day());
                        resultBean.setGz_count(infoUpdateBean.getGz_count());
                        resultBean.setUpdate_count(count);
                        if(count<infoUpdateBean.getGz_count()){
                            resultBean.setUpdate_desc("不合格");
                        }else{
                            resultBean.setUpdate_desc("合格");
                        }
                        checkResultList.add(resultBean);
                    }
                }
                toInsert(checkResultList);
                infoUpdateBean.setGz_time(currTime);
                InfoUpdateManager.updateInfoUpdate(infoUpdateBean);
            }
        }else{
            System.out.println("定时检查栏目更新情况开始*****暂无任务" + currTime);
        }
    }

    private static void toInsert(List<InfoUpdateResultBean> checkResultList){
        InfoUpdateResultManager.clearInfoUpdateResult();
        if(!checkResultList.isEmpty()){
            for(InfoUpdateResultBean bean:checkResultList){
                InfoUpdateResultManager.insertInfoUpdateResult(bean);
            }
        }
    }

    private static List<CategoryBean> getCatIds(InfoUpdateBean infoUpdateBean){
        List<CategoryBean> catList = new ArrayList<>();
        List<InfoUpdateCategoryBean> infoUpdateCategoryBeanList = InfoUpdateManager.getStartInfoUpdateCategoryList(infoUpdateBean.getGz_id());
        if(!infoUpdateCategoryBeanList.isEmpty()){
            for(InfoUpdateCategoryBean infoUpdateCategoryBean:infoUpdateCategoryBeanList){
                getCatIds(infoUpdateCategoryBean.getCat_id(),infoUpdateBean.getSite_id(),catList);
            }
        }
        return catList;
    }

    private static List<CategoryBean> getCatIds(int cat_id,String site_id,List<CategoryBean> catList){
        List<CategoryBean> categoryBeanList = CategoryManager.getChildCategoryList(cat_id,site_id);
        if(!categoryBeanList.isEmpty()){//有子栏目
            for(CategoryBean categoryBean:categoryBeanList){
                List<CategoryBean> childCategoryBeanList = CategoryManager.getChildCategoryList(categoryBean.getCat_id(),site_id);
                if(!childCategoryBeanList.isEmpty()){//有子栏目
                    getCatIds(categoryBean.getCat_id(),site_id,catList);
                }else{
                    catList.add(categoryBean);
                }
            }
        }else{//无子栏目
            catList.add(CategoryManager.getCategoryBeanCatID(cat_id));
        }
        return catList;
    }

    private static String getCategoryPosition(int cat_id,String site_id){
        String result = "";
        List<CateCurPositionBean> list = CategoryUtil.getCategoryPosition(cat_id+"",site_id,"list");
        if(!list.isEmpty()){
            for(CateCurPositionBean cateCurPositionBean:list){
                result += cateCurPositionBean.getCat_cname()+">>>";
            }
        }
        result = result.replace("首页>>>","");
        result = result.substring(0,result.length()-3);
        return result;
    }
}
