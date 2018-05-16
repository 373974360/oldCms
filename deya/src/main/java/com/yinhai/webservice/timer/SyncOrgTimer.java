package com.yinhai.webservice.timer;

import com.yinhai.webservice.client.SyncOrgServiceClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Description:
 * @User: like
 * @Date: 2016/12/8 11:29
 * @Version: 1.0
 * @Created with IntelliJ IDEA.
 */
public class SyncOrgTimer implements Job{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SyncOrgServiceClient.syncOrgDeptOrUser("user",1);
        SyncOrgServiceClient.syncOrgDeptOrUser("dept",1);
    }
}

