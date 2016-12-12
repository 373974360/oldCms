package com.deya.wcm.timer;

import com.deya.util.DateUtil;
import com.deya.wcm.dao.cms.info.InfoDAO;
import com.deya.wcm.services.control.site.SiteVisitCountManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 按天的定时任务，需要清空日点击数，月点击数和周点击数
 * @author zhuliang
 *
 */

public class InfoTimerForDayImpl implements Job{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("信息每日点击量汇总开始执行*****" + DateUtil.getCurrentDateTime());
        //首先清空日点击量
        Map<String,String> m = new HashMap<String,String>();
        m.put("item_name", "day_hits");
        InfoDAO.clearInfoHits(m);
        SiteVisitCountManager.clearHits("");//顺便清空下站点的访问量统计:)
        //判断今天是否为周一，如果是，清空周点击量    周一的值为2
        try {
            if(DateUtil.getDayOfWek(DateUtil.getDate(DateUtil.getCurrentDateTime())) == 2)
            {
                m.put("item_name", "week_hits");
                InfoDAO.clearInfoHits(m);
                SiteVisitCountManager.clearHits("week");
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //判断今天是否为一号，如果是，清空月点击量
        try {

            if(DateUtil.getDayOfMonth(DateUtil.getDate(DateUtil.getCurrentDateTime())) == 1)
            {
                m.put("item_name", "month_hits");
                InfoDAO.clearInfoHits(m);
                SiteVisitCountManager.clearHits("month");
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
