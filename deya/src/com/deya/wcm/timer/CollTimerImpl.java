package com.deya.wcm.timer;

import com.deya.util.DateUtil;
import com.deya.wcm.dataCollection.bean.CollRuleBean;
import com.deya.wcm.dataCollection.dao.CollectionDataDAO;
import com.deya.wcm.dataCollection.services.CollectionDataRPC;
import com.deya.wcm.dataCollection.util.FormatString;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class CollTimerImpl implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时采集信息开始执行*****" + DateUtil.getCurrentDateTime());
        List<CollRuleBean> col_list = CollectionDataDAO.getAllCollRuleBeanList(null);
        if ((col_list != null) && (col_list.size() > 0)) {
            System.out.println("------------Timer caollDate start....  ---------");
            for (CollRuleBean collBean : col_list) {
                String collinterval = collBean.getColl_interval();
                if ((FormatString.strIsNull(collinterval)) &&
                        (!"0".equals(collinterval)))
                    CollectionDataRPC.collTimer(collBean.getRule_id() + "");
            }
        }
    }
}
