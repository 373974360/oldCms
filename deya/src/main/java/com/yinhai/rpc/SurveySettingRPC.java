package com.yinhai.rpc;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;
import com.yinhai.model.SurveySettingVo;
import com.yinhai.webservice.client.SurveySettingServiceClient;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 问卷调查RPC
 * @User: Administrator
 * @Date: 2018/5/10 0010
 */
public class SurveySettingRPC {

    /**
     * 获取OA里面调查问卷配置
     * @param map
     * @return boolean　true or false
     * */
    public static List<SurveySettingVo> getSurveySettingList(Map<String, String> map, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return SurveySettingServiceClient.getSurveySettingList(map);
        }else
            return new ArrayList<>();
    }

    /**
     * 获取OA里面调查问卷配置
     * @param map
     * @return boolean　true or false
     * */
    public static boolean updateSurveySettingState(Map<String, String> map, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return SurveySettingServiceClient.updateSurveySettingState(map);
        }else
            return false;
    }
}
