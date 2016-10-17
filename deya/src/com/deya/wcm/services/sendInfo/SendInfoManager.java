package com.deya.wcm.services.sendInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.deya.util.BeanToMapUtil;
import com.deya.util.CryptoTools;
import com.deya.util.DateUtil;
import com.deya.util.Encode;
import com.deya.wcm.bean.cms.info.GKResFileBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.cms.info.PicItemBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.sendInfo.SendRecordBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.sendInfo.SendRecordDAO;
import com.deya.wcm.services.cms.info.ModelUtil;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.org.user.UserManager;
import com.deya.wcm.services.system.formodel.ModelManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;
import com.deya.wcm.webServices.sendInfo.SendClient;

public class SendInfoManager {
	//需要清空的字段
	private static String empty_item = "opt_dtime,hits,cat_id,modify_user,modify_dtime,app_id,step_id,released_dtime,input_user,lasthit_dtime,is_host,up_dtime,down_dtime,is_auto_down,site_id,input_dtime,from_id,wf_id";
	
	/**
     * 根据url获取到该站点允许报送的栏目
     * @param String site_domain
     * @return String json
     * */
	public static String getReceiveCategoryList(String site_domain)
	{
		try{
			InputStream in = null;
			in = new URL("http://"+site_domain+"/sendConfig/sendConfig").openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer content = new StringBuffer();
            String str = "";
            while ( (str = reader.readLine()) != null) {
            	content.append(str).append("\n");
            }
            
            return Encode.systemToUtf8(content.toString());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	/**
     * 报送信息处理
     * @param String List<SendRecordBean>
     * @return String
     * */
	public static String insertSendInfo(List<SendRecordBean> sendRecordList,List<InfoBean> infoList,SettingLogsBean stl)
	{
		try{
			List<SendRecordBean> srb_list = new ArrayList<SendRecordBean>();//存储已报送成功的报送记录对象
			Map<Integer,String> info_map = new HashMap<Integer,String>();
			//第一步：得到要报送信息的集合
			infoListToMap(infoList,info_map);
			//第二步：汇聚接收站点Map,将共同的站点放入同一个集合当中
			Map<String,List<SendRecordBean>> send_map = new HashMap<String,List<SendRecordBean>>();
			sendRecordListToMap(sendRecordList,send_map);
			Set<String> send_set = send_map.keySet();
			String error_message = "";//错误提示
			//System.out.println("send_map-------"+send_map);
			//循环站点
			for(String site_id : send_set)
			{
				srb_list.clear();
				//System.out.println("site_id-------"+site_id);
				StringBuffer sbf = new StringBuffer();
				List<SendRecordBean> l = send_map.get(site_id);
				String send_site_id = l.get(0).getSend_site_id();
				String domain = "";
				String site_name = "";
				if(send_site_id.startsWith("GK"))
				{
					GKNodeBean gkb = GKNodeManager.getGKNodeBeanByNodeID(send_site_id);
					domain = SiteDomainManager.getDefaultSiteDomainBySiteID(gkb.getRela_site_id());
					site_name = gkb.getNode_name();
				}else
				{
					domain = SiteDomainManager.getDefaultSiteDomainBySiteID(send_site_id);
					site_name = SiteManager.getSiteBeanBySiteID(send_site_id).getSite_name();
				}
				sbf.append("<cicrowcm>");
				sbf.append("<t_site_id><![CDATA["+site_id+"]]></t_site_id>");
				sbf.append("<t_app_id>cms</t_app_id>");
				sbf.append("<s_site_id><![CDATA["+send_site_id+"]]></s_site_id>");
				sbf.append("<s_site_domain><![CDATA["+domain+"]]></s_site_domain>");
				sbf.append("<s_site_name><![CDATA["+site_name+"]]></s_site_name>");
				sbf.append("<s_user_id><![CDATA["+l.get(0).getSend_user_id()+"]]></s_user_id>");
				sbf.append("<s_user_name><![CDATA["+UserManager.getUserRealName(l.get(0).getSend_user_id()+"")+"]]></s_user_name>");
				sbf.append("<s_send_dtime><![CDATA["+l.get(0).getSend_time()+"]]></s_send_dtime>");
				sbf.append("<infoList>");
				//循环栏目
				for(SendRecordBean srb : l)
				{					
					int r_cat_id = srb.getT_cat_id();
					//循环信息列表
					for(InfoBean ib : infoList)
					{
						SendRecordBean newBean = srb.clone();
						newBean.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.SEND_RECORD_TABLE_NAME));
						newBean.setSend_info_id(ib.getInfo_id());
						newBean.setSend_cat_id(ib.getCat_id());
						sbf.append("<info>");
						sbf.append("<r_cat_id>"+r_cat_id+"</r_cat_id>");
						sbf.append("<send_record_id>"+newBean.getId()+"</send_record_id>");
						sbf.append(info_map.get(ib.getInfo_id()));
						sbf.append("</info>");
						srb_list.add(newBean);
					}
				}
				sbf.append("</infoList>");
				sbf.append("</cicrowcm>");
				CryptoTools ct = new CryptoTools();
				String m_code = ct.encode(send_site_id);//加密报送的站点ID
				//调用webservices接口，报送信息
				if(SendClient.getServicesObj(SendConfigManager.getSendConfigBean(site_id).getSite_domain()).sendInfo(sbf.toString(), m_code))
				{
					//成功一个站点后写入报送记录
					SendRecordDAO.insertSendRecord(srb_list);
				}else
				{
					error_message += ","+l.get(0).getT_site_name();
				}
			}
			return error_message;
		}catch(Exception e)
		{
			e.printStackTrace();
			return "false";
		}
	}
	
	/**
     * 汇聚接收站点Map,将共同的站点放入同一个集合当中,报送有可能同时报到多个站点，这里聚合一下，避免多次调用webservices接口
     * @param List<SendRecordBean> sendRecordList
     * @param Map<Integer,List<SendRecordBean>> m
     * @return 
     * */
	public static void sendRecordListToMap(List<SendRecordBean> sendRecordList,Map<String,List<SendRecordBean>> send_map)
	{
		String current_time = DateUtil.getCurrentDateTime();
		for(SendRecordBean srb : sendRecordList)
		{
			srb.setSend_time(current_time);
			if(send_map.containsKey(srb.getT_site_id()))
			{
				send_map.get(srb.getT_site_id()).add(srb);
			}else
			{
				List<SendRecordBean> l = new ArrayList<SendRecordBean>();
				l.add(srb);
				send_map.put(srb.getT_site_id(), l);
			}
		}
	}
	
	/**
     * 循环信息列表，将内容取出后存入Map
     * @param List<InfoBean> infoList
     * @param Map<Integer,String> m
     * @return 
     * */
	public static void infoListToMap(List<InfoBean> infoList,Map<Integer,String> m)
	{
		if(infoList != null && !"".equals(infoList))
		{
			for(InfoBean info : infoList)
			{
				Object o = ModelUtil.select(info.getInfo_id()+"", info.getSite_id(), ModelManager.getModelBean(info.getModel_id()).getModel_ename());
				//此处特殊处理一下，如果碰到的是链接类型的数据且链接地址是本地的以url开头的，在url前面加上域名
				try {
					if("12".equals(BeanUtils.getProperty(o, "model_id")))
					{						
						String content_url = BeanUtils.getProperty(o, "content_url");
						if(content_url.startsWith("/"))
						{
							String site_id = BeanUtils.getProperty(o, "site_id");
							BeanUtils.setProperty(o, "content_url", "http://"+SiteDomainManager.getDefaultSiteDomainBySiteID(site_id)+content_url);
						}
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String xml = ObjectToXMLStr(o);
				if(xml != null && !"".equals(xml))
				{
					m.put(info.getInfo_id(), xml);
				}
			}
		}
	}

	/**
     * 将信息对象转换成xml字符串（先将object转成Map,再转成String）
     * @param String List<SendRecordBean>
     * @return HttpServletRequest request
     * */
	@SuppressWarnings("unchecked")
	public static String ObjectToXMLStr(Object o)
	{
		try{
			StringBuffer sbf = new StringBuffer();
			Map<String,Object> m = BeanToMapUtil.convertBean(o);
			if(m != null)
			{
				Set<String> set = m.keySet();
				sbf.append("<r_content>");
				for(String key : set)
				{	//对图片要特殊处理一下
					if("item_list".equals(key))
					{
						List<PicItemBean> item_list = (List<PicItemBean>) m.get(key);
						if(item_list != null && item_list.size() > 0)
						{
							sbf.append("<item_list>");
							for(PicItemBean pitem : item_list)
							{
								sbf.append("<pic_item>");
								sbf.append("<info_id></info_id>");
								sbf.append("<att_id></att_id>");
								sbf.append("<pic_path><![CDATA["+pitem.getPic_path()+"]]></pic_path>");
								sbf.append("<pic_note><![CDATA["+pitem.getPic_note()+"]]></pic_note>");
								sbf.append("<pic_url><![CDATA["+pitem.getPic_url()+"]]></pic_url>");
								sbf.append("<pic_sort>"+pitem.getPic_sort()+"</pic_sort>");
								sbf.append("<pic_title><![CDATA["+pitem.getPic_title()+"]]></pic_title>");
								sbf.append("</pic_item>");
							}
							sbf.append("</item_list>");
						}
						continue;
					}/*
					if("file_list".equals(key))
					{
						List<GKResFileBean> file_list = (List<GKResFileBean>) m.get(key);
						if(file_list != null && file_list.size() > 0)
						{
							sbf.append("<file_list>");
							for(GKResFileBean fitem : file_list)
							{
								sbf.append("<file_item>");
								sbf.append("<info_id></info_id>");
								sbf.append("<res_id></res_id>");
								sbf.append("<res_name><![CDATA["+fitem.getRes_name()+"]]></res_name>");
								sbf.append("<res_url><![CDATA["+fitem.getRes_url()+"]]></res_url>");
								sbf.append("<sort_id>"+fitem.getSort_id()+"</sort_id>");
								sbf.append("</file_item>");
							}
							sbf.append("</file_list>");
						}
						continue;
					}*/
					
					String value = m.get(key)+"";
					if(empty_item.contains(key))
						value = "";
					sbf.append("<"+key+"><![CDATA["+value+"]]></"+key+">");
					
				}
				sbf.append("</r_content>");
			}
			return sbf.toString();
		}
		catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
}
