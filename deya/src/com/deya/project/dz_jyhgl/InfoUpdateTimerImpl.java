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
                List<Map<String,String>> checkResultList = new ArrayList<>();
                //设置检测起始时间
                String checkStartTime = DateUtil.getDateBefore(currTime,infoUpdateBean.getGz_day())+" 00:00:00";
                if(infoUpdateBean.getGz_type()<=2){
                    List<CategoryBean> categoryBeanList = getCatIds(infoUpdateBean);//获取该规则下配置的所有末级栏目
                    if(!categoryBeanList.isEmpty()){
                        if(infoUpdateBean.getGz_type()==1){//首页
                            String cat_ids = InfoUpdateManager.getInfoUpdateCategoryByGzId(infoUpdateBean.getGz_id());
                            //检查起点时间 为首页所有栏目最后一条信息的时间
                            Map<String,String> map = new HashMap<>();
                            int count = InfoUpdateManager.getInfoUpdateCountByTimeAndCatId(checkStartTime,currTime,cat_ids);
                            map.put("cat_name", "网站首页");
                            map.put("start_time",checkStartTime);
                            map.put("end_time",currTime);
                            map.put("gz_day",infoUpdateBean.getGz_day()+"");
                            map.put("gz_count",infoUpdateBean.getGz_count()+"");
                            map.put("info_count",count+"");
                            if(count<infoUpdateBean.getGz_count()){
                                map.put("desc","不合格");
                            }else{
                                map.put("desc","合格");
                            }
                            checkResultList.add(map);
                        }else if(infoUpdateBean.getGz_type()==2){//列表页
                            for(CategoryBean categoryBean:categoryBeanList){
                                Map<String,String> map = new HashMap<>();
                                int count = InfoUpdateManager.getInfoUpdateCountByTimeAndCatId(checkStartTime,currTime,categoryBean.getCat_id()+"");
                                map.put("cat_name", getCategoryPosition(categoryBean.getCat_id(),infoUpdateBean.getSite_id()));
                                map.put("start_time",checkStartTime);
                                map.put("end_time",currTime);
                                map.put("gz_day",infoUpdateBean.getGz_day()+"");
                                map.put("gz_count",infoUpdateBean.getGz_count()+"");
                                map.put("info_count",count+"");
                                if(count<infoUpdateBean.getGz_count()){
                                    map.put("desc","不合格");
                                }else{
                                    map.put("desc","合格");
                                }
                                checkResultList.add(map);
                            }
                        }
                    }else{
                        System.out.println("定时检查栏目更新情况开始*****" + infoUpdateBean.getGz_name()+":暂未配置栏目!");
                    }
                }else{
                    if(infoUpdateBean.getGz_type()==3){//诉求
                        int count = InfoUpdateManager.getSqPublishCount(checkStartTime,currTime);
                        Map<String,String> map = new HashMap<>();
                        map.put("cat_name", "诉求系统");
                        map.put("start_time",checkStartTime);
                        map.put("end_time",currTime);
                        map.put("gz_day",infoUpdateBean.getGz_day()+"");
                        map.put("gz_count",infoUpdateBean.getGz_count()+"");
                        map.put("info_count",count+"");
                        if(count<infoUpdateBean.getGz_count()){
                            map.put("desc","不合格");
                        }else{
                            map.put("desc","合格");
                        }
                        checkResultList.add(map);
                    }else if(infoUpdateBean.getGz_type()==4){//调查
                        int count = InfoUpdateManager.getSurveyPublishCount(checkStartTime,currTime,infoUpdateBean.getSite_id());
                        Map<String,String> map = new HashMap<>();
                        map.put("cat_name", "调查系统");
                        map.put("start_time",checkStartTime);
                        map.put("end_time",currTime);
                        map.put("gz_day",infoUpdateBean.getGz_day()+"");
                        map.put("gz_count",infoUpdateBean.getGz_count()+"");
                        map.put("info_count",count+"");
                        if(count<infoUpdateBean.getGz_count()){
                            map.put("desc","不合格");
                        }else{
                            map.put("desc","合格");
                        }
                        checkResultList.add(map);
                    }else if(infoUpdateBean.getGz_type()==5){//访谈
                        int count = InfoUpdateManager.getSubjectApplyCount(checkStartTime,currTime,infoUpdateBean.getSite_id());
                        Map<String,String> map = new HashMap<>();
                        map.put("cat_name", "访谈系统");
                        map.put("start_time",checkStartTime);
                        map.put("end_time",currTime);
                        map.put("gz_day",infoUpdateBean.getGz_day()+"");
                        map.put("gz_count",infoUpdateBean.getGz_count()+"");
                        map.put("info_count",count+"");
                        if(count<infoUpdateBean.getGz_count()){
                            map.put("desc","不合格");
                        }else{
                            map.put("desc","合格");
                        }
                        checkResultList.add(map);
                    }
                }
                createExcel(checkResultList,infoUpdateBean.getGz_id(),infoUpdateBean.getSite_id());
                infoUpdateBean.setGz_time(currTime);
                InfoUpdateManager.updateInfoUpdate(infoUpdateBean);
            }
        }else{
            System.out.println("定时检查栏目更新情况开始*****暂无任务" + currTime);
        }
    }

    private static void createExcel(List<Map<String,String>> checkResultList,int gz_id,String site_id){
        //删除今天以前的文件夹
        String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
        String path = FormatUtil.formatPath(root_path + "/project/dz_jyhgl/"+site_id+"/"+gz_id+"/");
        CountUtil.deleteFile(path);
        //创建今天的文件夹和xls文件
        String nowDate = CountUtil.getNowDayDate();
        String fileTemp2 = FormatUtil.formatPath(path+ File.separator);
        File file = new File(fileTemp2);
        if(!file.exists()){
            file.mkdirs();
        }
        String nowTime = CountUtil.getNowDayDateTime();
        String xls = nowTime + CountUtil.getEnglish(1)+".xls";
        String xlsFile = fileTemp2+File.separator+xls;

        String[] head = {"栏目","监测开始时间","监测时间节点","间隔天数","规定更新数量","实际更新数量","是否合格"};
        String[][] data = new String[checkResultList.size()][7];
        for(int i=0;i<checkResultList.size();i++){
            Map<String,String>  map = checkResultList.get(i);
            data[i][0] = map.get("cat_name");//栏目
            data[i][1] = map.get("start_time");//开始时间
            data[i][2] = map.get("end_time");//结束时间
            data[i][3] = map.get("gz_day");//间隔天数
            data[i][4] = map.get("gz_count");//规定更新数量
            data[i][5] = map.get("info_count");//实际更新数量
            data[i][6] = map.get("desc");//实际更新数量
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
