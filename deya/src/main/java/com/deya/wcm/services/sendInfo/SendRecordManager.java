package com.deya.wcm.services.sendInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.deya.util.FormatUtil;
import com.deya.util.xml.XmlManager;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.sendInfo.SendRecordBean;
import com.deya.wcm.dao.sendInfo.SendRecordDAO;

public class SendRecordManager {
	/**
     * 得到报送记录总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getSendRecordCount(Map<String,String> m)
	{
		return SendRecordDAO.getSendRecordCount(m);
	}
	
	/**
     * 得到报送记录列表
     * @param Map<String,String> m
     * @return String
     * */
	public static List<SendRecordBean> getSendRecordList(Map<String,String> m)
	{
		return SendRecordDAO.getSendRecordList(m);
	}
	
	/**
     * 修改报送记录，用于返回是否采用信息
     * @param Map<String,String> m
     * @return String
     * */
	public static boolean updateSendRecord(Map<String,String> m)
	{
		return SendRecordDAO.updateSendRecord(m);
	}
	
	/**
     * 删除报送记录
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return String
     * */
	public static boolean deleteSendRecord(Map<String,String> m,SettingLogsBean stl)
	{
		return SendRecordDAO.deleteSendRecord(m, stl);
	}
	
	/**
     * 记录采用结果
     * @param String xml
     * @param String user
     * @return boolean
     * */
	public static boolean recordAdoptStatusHandl(String xml)
	{
		try {
			Node node = XmlManager.createNode(xml);
			Map<String,String> m = new HashMap<String,String>();
			m.put("ids", XmlManager.queryNodeValue(node, "//result_ids"));
			m.put("adopt_status", XmlManager.queryNodeValue(node, "//adopt_status"));
			m.put("adopt_dtime", XmlManager.queryNodeValue(node, "//adopt_dtime"));
			m.put("adopt_desc", FormatUtil.formatNullString(XmlManager.queryNodeValue(node, "//adopt_desc")));
			return updateSendRecord(m);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String args[])
	{
		recordAdoptStatusHandl("<cicrowcm><result_ids>11</result_ids><adopt_dtime>2012-07-03 09:54:05</adopt_dtime><adopt_desc></adopt_desc><adopt_status>1</adopt_status></cicrowcm>");
	}
}
