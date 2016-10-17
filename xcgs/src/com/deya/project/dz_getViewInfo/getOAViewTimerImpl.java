package com.deya.project.dz_getViewInfo;

import com.deya.wcm.dataCollection.bean.CollRuleBean;
import com.deya.wcm.dataCollection.dao.CollectionDataDAO;
import com.deya.wcm.dataCollection.services.CollectionDataRPC;
import com.deya.wcm.dataCollection.util.FormatString;
import com.deya.wcm.timer.TimerListener;
import com.deya.wcm.timer.TimerTaskJobForDay;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2015/12/17
 * Time: 15:51
 * Description:
 * Version: v1.0
 */
public class getOAViewTimerImpl implements TimerListener {
    static {
        TimerTaskJobForDay.registerPublishListener(new getOAViewTimerImpl());
    }

    public void timingTask() {
        System.out.println("------------Timer caollDate start....  ---------");
        new getOAView().OAInfoToCMSInfo();
        System.out.println("------------Timer caollDate end....  ---------");
    }
}
