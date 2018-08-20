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
                List<CategoryBean> categoryBeanList = getCatIds(infoUpdateBean);//获取该规则下配置的所有末级栏目
                if(!categoryBeanList.isEmpty()){
                    List<Map<String,String>> checkResultList = new ArrayList<>();
                    if(infoUpdateBean.getGz_type()==1){//首页
                        String cat_ids = InfoUpdateManager.getInfoUpdateCategoryByGzId(infoUpdateBean.getGz_id());
                        //检查起点时间 为首页所有栏目最后一条信息的时间
                        String checkStartTime = InfoUpdateManager.getInfoMaxReleasedDtime(cat_ids);
                        if(StringUtils.isEmpty(checkStartTime)){
                            checkStartTime = currTime;
                        }
                        Map<String,String> map = new HashMap<>();
                        int count = InfoUpdateManager.getInfoUpdateCountByTimeAndCatId(checkStartTime,cat_ids);
                        if(count<infoUpdateBean.getGz_count()){
                            map.put("cat_name", "网站首页");
                            map.put("start_time",checkStartTime);
                            map.put("end_time",currTime);
                            map.put("gz_count",infoUpdateBean.getGz_count()+"");
                            map.put("info_count",count+"");
                            map.put("desc","补录信息");
                            checkResultList.add(map);
                        }
                    }else if(infoUpdateBean.getGz_type()==2){//列表页
                        for(CategoryBean categoryBean:categoryBeanList){
                            //检查起点时间 为该栏目发布的最后一条信息的时间
                            String checkStartTime = InfoUpdateManager.getInfoMaxReleasedDtime(categoryBean.getCat_id());
                            if(StringUtils.isEmpty(checkStartTime)){
                                checkStartTime = currTime;
                            }
                            Map<String,String> map = new HashMap<>();
                            int count = InfoUpdateManager.getInfoUpdateCountByTimeAndCatId(checkStartTime,categoryBean.getCat_id());
                            if(count<infoUpdateBean.getGz_count()){
                                map.put("cat_name", getCategoryPosition(categoryBean.getCat_id(),infoUpdateBean.getSite_id()));
                                map.put("start_time",checkStartTime);
                                map.put("end_time",currTime);
                                map.put("gz_count",infoUpdateBean.getGz_count()+"");
                                map.put("info_count",count+"");
                                map.put("desc","补录信息");
                                checkResultList.add(map);
                            }
                        }
                    }
                    createExcel(checkResultList,infoUpdateBean.getGz_id());
                }else{
                    System.out.println("定时检查栏目更新情况开始*****" + infoUpdateBean.getGz_name()+":暂未配置栏目!");
                }
                //更新检下次检查时间
                String nextTime = DateUtil.getDateTimeString(DateUtil.getDateAfter(currTime,infoUpdateBean.getGz_day()-2));
                infoUpdateBean.setGz_nexttime(nextTime);
                InfoUpdateManager.updateInfoUpdate(infoUpdateBean);
            }
        }else{
            System.out.println("定时检查栏目更新情况开始*****暂无任务" + currTime);
        }
    }

    private static void createExcel(List<Map<String,String>> checkResultList,int gz_id){
        //删除今天以前的文件夹
        String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
        String path = FormatUtil.formatPath(root_path + "/project/dz_jyhgl/result/"+gz_id+"/");
        CountUtil.deleteFile(path);
        //创建今天的文件夹和xls文件
        String nowDate = CountUtil.getNowDayDate();
        String fileTemp2 = FormatUtil.formatPath(path+ File.separator+nowDate);
        File file = new File(fileTemp2);
        if(!file.exists()){
            file.mkdirs();
        }
        String nowTime = CountUtil.getNowDayDateTime();
        String xls = nowTime + CountUtil.getEnglish(1)+".xls";
        String xlsFile = fileTemp2+File.separator+xls;

        String[] head = {"栏目","最后更新时间","截止时间","规定更新数量","实际更新数量","建议"};
        String[][] data = new String[checkResultList.size()][6];
        for(int i=0;i<checkResultList.size();i++){
            Map<String,String>  map = checkResultList.get(i);
            data[i][0] = map.get("cat_name");//栏目
            data[i][1] = map.get("start_time");//开始时间
            data[i][2] = map.get("end_time");//结束时间
            data[i][3] = map.get("gz_count");//规定更新数量
            data[i][4] = map.get("info_count");//实际更新数量
            data[i][5] = map.get("desc");//建议
        }
        OutExcel oe=new OutExcel("信息更新检查结果");
        oe.doOut(xlsFile,head,data);
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
