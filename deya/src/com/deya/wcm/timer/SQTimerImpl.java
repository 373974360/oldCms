package com.deya.wcm.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.model.ModelBean;
import com.deya.util.DateUtil;
import com.deya.wcm.bean.appeal.sq.SQBean;
import com.deya.wcm.dao.appeal.sq.SQDAO;
import com.deya.wcm.services.appeal.calendar.CalendarManager;
import com.deya.wcm.services.appeal.model.ModelManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SQTimerImpl implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("诉求定时任务开始执行*****" + DateUtil.getCurrentDateTime());
        List<SQBean> l = SQDAO.getNotEndSQList();
        if (l != null && l.size() > 0) {
            Map<String, String> m = new HashMap<String, String>();
            String now_date = DateUtil.getCurrentDate();
            for (int i = 0; i < l.size(); i++) {
                try {
                    SQBean sb = l.get(i);
                    int sq_id = sb.getSq_id();//信件ID
                    int model_id = sb.getModel_id();//业务ID
                    String sq_dtime = sb.getSq_dtime();//提交时间
                    int time_limit = sb.getTime_limit();//办理时限
                    int warn_num = -5;          //提醒时间
                    int yellow_num = -2;        //黄牌时间
                    int red_num = 2;           //红牌时间
                    ModelBean mb = ModelManager.getModelBean(model_id);

                    if (mb == null) {
                        continue;
                    } else {
                        warn_num = mb.getWarn_num();
                        yellow_num = mb.getYellow_num();
                        red_num = mb.getRed_num();
                    }
                    if (sq_dtime != null && !"".equals(sq_dtime)) {
                        sq_dtime = sq_dtime.substring(0, 10);
                    } else {//提交时间为空跳出这次
                        continue;
                    }

                    m.clear();
                    //第一步：根据提交日期和当前日期得到已过的工作日期天数
                    int work_date = CalendarManager.getWorkDays(sq_dtime, now_date);
                    //System.out.println(sq_id+" ---- "+work_date);
                    //工作日大于办理天数为超期
                    if (work_date > time_limit) {
                        m.put("timeout_flag", "1");
                    }
                    //预警
                    if (work_date > (time_limit + warn_num)) {
                        m.put("alarm_flag", "1");
                    }
                    //黄牌
                    if (work_date > (time_limit + yellow_num)) {
                        m.put("alarm_flag", "2");
                    }
                    //红牌
                    if (work_date > (time_limit + red_num)) {
                        m.put("alarm_flag", "3");
                    }
                    //System.out.println(work_date+"  "+(time_limit + warn_num));
                    //System.out.println(m.size());
                    if (m.size() > 0) {
                        m.put("sq_id", sq_id + "");
                        SQDAO.updateStatus(m);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }
}
