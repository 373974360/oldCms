package com.deya.wcm.services.cms.info;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.beanutils.BeanUtils;

import com.cicro.iresource.service.resourceService.rmi.ResourceRmiI;
import com.deya.util.jconfig.JconfigUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import com.deya.wcm.services.control.site.SiteAppRele;
import com.deya.wcm.services.system.formodel.ModelManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;

public class ZYMLEventHandl {
	private static JconfigUtil jfu = JconfigUtilContainer.bashConfig();
	
	private static String state = jfu.getProperty("state", "", "wcm_zyml");
	private static String rmi_path = jfu.getProperty("rmi_path", "", "wcm_zyml");
	
	private static String mdcmdId = jfu.getProperty("mdcmdId", "", "wcm_zyml");
	private static String mdcauthType = jfu.getProperty("mdcauthType", "", "wcm_zyml");
	private static String mdcresType = jfu.getProperty("mdcresType", "", "wcm_zyml");
	
	public static void saveCMSFormData(InfoBean ib)
	{
		if("on".equals(state))
		{					
			Context namingContext;
			try {
				namingContext = new InitialContext();
				ResourceRmiI rmi=(ResourceRmiI)namingContext.lookup(rmi_path);
				
				List<String> str_list = new ArrayList<String>();
				str_list.add(returnInfoJson(ib));
				System.out.println("str_list---------"+str_list.get(0));
				//rmi.saveCMSFormData(str_list, "1", mdcmdId);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			*/		
			
		}
	}
	
	public static void saveCMSFormData(List<InfoBean> l,Set<Integer> cat_ids)
	{
		if("on".equals(state))
		{			
			if(cat_ids != null)
			{
				
				Context namingContext;
				try {
					namingContext = new InitialContext();
					ResourceRmiI rmi=(ResourceRmiI)namingContext.lookup(rmi_path);
					
					Iterator<Integer> it = cat_ids.iterator();
					while(it.hasNext()){
						String cat_id = it.next()+"";
						List<String> str_list = new ArrayList<String>();
						for(InfoBean ib : l)
						{
							String s = returnInfoJson(ib);
							System.out.println("str_list---------"+s);
							if(s != "" && !"".equals(ib))
								str_list.add(s);
						}
						//rmi.saveCMSFormData(str_list, "1", mdcmdId);
					}
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				/*catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				*/				
			}
		}
	}
	
	public static String returnInfoJson(InfoBean ib)
	{
		String json = "";
		Object o = ModelUtil.select(ib.getInfo_id()+"",ib.getSite_id(),ModelManager.getModelEName(ib.getModel_id()));
		if(o != null)
		{
			try {
				String domain = SiteDomainManager.getDefaultSiteDomainBySiteID(SiteAppRele.getSiteIDByAppID(ib.getApp_id()));
				String mdcresTitle = BeanUtils.getProperty(o, "title");
				String mdcabstract = BeanUtils.getProperty(o, "description");
				String mdcrpOrgName = GKNodeManager.getNodeNameByNodeID(ib.getSite_id());
				String mdconLineSrc = "http://"+domain+BeanUtils.getProperty(o, "content_url");
				
				String mdccateStd_zt = "主题分类";
				String mdccateName_zt = BeanUtils.getProperty(o, "topic_name");
				String mdccateCode_zt = "";
				String mdcresID = BeanUtils.getProperty(o, "gk_index");
				
				List<Map<String,String>> l = new ArrayList<Map<String,String>>();
				if(!"0".equals(BeanUtils.getProperty(o, "topic_id")))
				{
					Map<String,String> m = new HashMap<String,String>();
					m.put("type_name", "主题分类");
					m.put("cat_name", BeanUtils.getProperty(o, "topic_name"));
					m.put("cat_code", CategoryManager.getCategoryBean(Integer.parseInt(BeanUtils.getProperty(o, "topic_id"))).getCat_code());
					l.add(m);
				}
				if(!"0".equals(BeanUtils.getProperty(o, "theme_id")))
				{
					Map<String,String> m = new HashMap<String,String>();
					m.put("type_name", "体裁分类");
					m.put("cat_name", BeanUtils.getProperty(o, "theme_name"));
					m.put("cat_code", CategoryManager.getCategoryBean(Integer.parseInt(BeanUtils.getProperty(o, "theme_id"))).getCat_code());
					l.add(m);
				}
				if(!"0".equals(BeanUtils.getProperty(o, "serve_id")))
				{
					Map<String,String> m = new HashMap<String,String>();
					m.put("type_name", "服务对象分类");
					m.put("cat_name", BeanUtils.getProperty(o, "serve_name"));
					m.put("cat_code", CategoryManager.getCategoryBean(Integer.parseInt(BeanUtils.getProperty(o, "serve_id"))).getCat_code());
					l.add(m);
				}
				
				json = "{\"id\":\""+ib.getInfo_id()+"\",\"mdcresTitle\":\""+mdcresTitle+"\",\"mdcabstract\":\""+mdcabstract+"\"," +
						"\"mdcrpOrgName\":\""+mdcrpOrgName+"\",\"mdcaddress\":\""+mdcrpOrgName+"\","+
						"\"mdcresID\":\""+mdcresID+"\",\"mdcmdId\":\""+mdcmdId+"\"," +
						"\"mdconLineSrc\":\""+mdconLineSrc+"\",\"mdcauthType\":\""+mdcauthType+"\",\"mdcresType\":\""+mdcresType+"\"";
				String str = "";
						for(int i=0;i<l.size();i++)
						{
							Map<String,String> m = l.get(i);
							if(i == 0)
							{
								json += ",\"mdccateStd\":\""+m.get("type_name")+"\",\"mdccateName\":\""+m.get("cat_name")+"\",\"mdccateCode\":\""+m.get("cat_code")+"\"";
							}else
							{
								str += ",{\"fieldName\":\"mdccateStd_"+i+"\",\"fieldCnName\":\"分类方式\",\"fieldValue\":\""+m.get("type_name")+"\"}";
								str += ",{\"fieldName\":\"mdccateName_"+i+"\",\"fieldCnName\":\"类目名称\",\"fieldValue\":\""+m.get("cat_name")+"\"}";
								str += ",{\"fieldName\":\"mdccateCode_"+i+"\",\"fieldCnName\":\"类目编码\",\"fieldValue\":\""+m.get("cat_code")+"\"}";
							}
						}
						if(l.size() > 1)
						{
							str = ",\"mfd\":["+str.substring(1)+"]";
							json += str;
						}
				json += "}";
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
		}
		return json;
	}
	
	public static void removeFormData(String info_id)
	{
		if("on".equals(state))
		{
			String[] s = new String[1];
			s[0] = info_id+"#"+mdcmdId;
			System.out.println("str_list---------"+s.toString());
			Context namingContext;
			try {
				namingContext = new InitialContext();
				ResourceRmiI rmi=(ResourceRmiI)namingContext.lookup(rmi_path);
				//rmi.removeFormData(s);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			/*catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/ 
			
		}
	}
	
	public static void removeFormData(List<InfoBean> l)
	{
		if("on".equals(state))
		{
			String[] s = new String[l.size()];
			for(int i=0;i<l.size();i++)
				s[i] = l.get(i).getInfo_id()+"#"+mdcmdId;
			System.out.println("str_list---------"+s.toString());
			Context namingContext;
			try {
				namingContext = new InitialContext();
				ResourceRmiI rmi=(ResourceRmiI)namingContext.lookup(rmi_path);
				//rmi.removeFormData(s);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			/*catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			*/			
		}
	}
}
