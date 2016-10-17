package com.deya.wcm.webServices.sendInfo;

import com.deya.wcm.services.sendInfo.ReceiveConfigManager;
import com.deya.wcm.services.sendInfo.SendInfoDispose;
import com.deya.wcm.services.sendInfo.SendRecordManager;

public class SendInfoImpl implements ISendInfo{
	/**
     * 得到该站群服务器上允许报送的站点和栏目(json)
     * @param 
     * @return List
     * */
	public String getReceiveConfigForJSON(){
		return ReceiveConfigManager.getReceiveConfigForJSON();
	}
	
	/**
     * 得到该站点下所有可以报送的站点和栏目,webservice接口用
     * @param List<ReceiveCatConf> l
     * @param String site_id
     * @return Str
     * */
	public String getReceiveConfigForXML(){
		return ReceiveConfigManager.getReceiveConfigForXML();
	}
	
	/**
     * 报送信息,webservice接口用
     * @param String xml
     * @param String user
     * @return boolean
     * */
	public boolean sendInfo(String xml,String user)
	{
		if(xml == null || "".equals(xml) || user == null || "".equals(user))
		{
			return false;
		}else
		{
			//System.out.println(xml.toString());
			SendInfoDispose.sendInfoHandl(xml,user);
			return true;
		}
	}
	
	/**
     * 记录采用结果,webservice接口用
     * @param String xml
     * @param String user
     * @return boolean
     * */
	public boolean recordAdoptStatus(String xml)
	{
		if(xml == null || "".equals(xml))
		{
			return false;
		}else
		{
			return SendRecordManager.recordAdoptStatusHandl(xml);
		}
	}
	
	/**
     * 第三方报送信息,webservice接口用
     * @param String xml
     * @param String user
     * @return boolean
     * */
	public boolean sendInfoFormThirdParty(String xml,String username,String password)
	{
		if(xml == null || "".equals(xml))
		{
			return false;
		}else 
		{
			SendInfoDispose.sendInfoHandlFormThirdParty(xml);
			return true;
		}
	}

}