package com.deya.wcm.services.page;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.io.FileOperation;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.page.PageBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.page.PageDAO;
import com.deya.wcm.rmi.file.FileRmiFactory;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.template.velocity.impl.VelocityPageContextImp;

/**
 *  页面管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 页面管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class PageManager implements ISyncCatch{
	private static TreeMap<Integer,PageBean> page_map = new TreeMap<Integer,PageBean>();
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		page_map.clear();		
		List<PageBean> page_list = PageDAO.getAllPageList();
		if(page_list != null && page_list.size() > 0)
		{
			for(int i=0;i<page_list.size();i++)
			{
				page_map.put(page_list.get(i).getId(), page_list.get(i));				
			}
		}
	}
	
	/**
     * 初始化方法
     * @param
     * @return 
     * */
	public static void reloadPage()
	{
        reloadCatchHandl();
		//SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.page.PageManager");
	}	
	
	/**
     * 得到新的页面ID
     * @param
     * @return int
     * */
	public static int getNewPageID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.PAGE_TABLE_NAME);
	}
	
	/**
     * 得到页面ID树字符串
     * @param String app_id
     * @param String site_id
     * @return String
     * */
	public static String getPageJSONTreeStr(String app_id,String site_id)
	{
		List<PageBean> pl = getPageList(app_id,site_id,0);	
		return "["+getPageJSONTreeStrHandl(pl)+"]";
	}
	
	public static String getPageJSONTreeStrHandl(List<PageBean> pl)
	{
		String json_str = "";
		for(PageBean pb : pl)
		{
			String url = pb.getPage_path()+"/"+pb.getPage_ename()+".htm";
			url.replaceAll("//", "/");
			json_str += ",{";
			json_str += "\"id\":"+pb.getPage_id()+",\"text\":\""+pb.getPage_cname()+"\",\"attributes\":{\"app_id\":\""+pb.getApp_id()+"\",\"url\":\""+url+"\"}";
			List<PageBean> child_list = pb.getChild_list();
			if(child_list != null && child_list.size() > 0)
			{
				json_str += ",\"children\":["+getPageJSONTreeStrHandl(child_list)+"]";
			}
			json_str += "}";
		}
		if(json_str != null && !"".equals(json_str))
			json_str = json_str.substring(1);
		return json_str;
	}
	
	/**
     * 根据app_id，site_id得到页面列表
     * @param String app_id
     * @param String site_id
     * @param int parent_id
     * @return int
     * */
	public static List<PageBean> getPageList(String app_id,String site_id,int parent_id)
	{		
		List<PageBean> pl = new ArrayList<PageBean>();		
		Set<Integer> s = page_map.keySet();
		for(int i : s)
		{
			PageBean pb = page_map.get(i);
			if(pb.getApp_id().equals(app_id) && pb.getSite_id().equals(site_id))
			{
				pl.add(pb);
			}
		}
		return getPageChildList(pl,parent_id);
	}
	
	public static List<PageBean> getPageChildList(List<PageBean> pl,int parent_id)
	{
		List<PageBean> page_list = new ArrayList<PageBean>();
		for(int i=0;i<pl.size();i++)
		{
			if(parent_id == pl.get(i).getParent_id())
			{
				pl.get(i).setChild_list(getPageChildList(pl,pl.get(i).getPage_id()));
				page_list.add(pl.get(i));
			}				
		}
		return page_list;
	}
	
	
	/**
     * 得到页面对象
     * @param int id
     * @return int
     * */
	public static PageBean getPageBean(int id)
	{
		if(page_map.containsKey(id))
		{
			return page_map.get(id);
		}else
		{
			PageBean pb = PageDAO.getPageBean(id);
			if(pb != null)
			{
				page_map.put(id,pb);
				return pb;
			}else
				return null;
		}
	}
	
	/**
     * 根据存储路径，英文名，站点ID判断该目录下是否有页面或目录存在    
     * @param String  page_path
     * @param String page_ename
     * @param String app_id     
     * @return boolean true表示已存在相同的页面
     * */
	public static boolean pageFileIsExist(String page_path,String page_ename,String site_id)
	{
		String site_path = SiteManager.getSitePath(site_id);
		String file_path = FormatUtil.formatPath(site_path+page_path+"/"+page_ename);
		//File folder_f = new File(file_path);
		File file = new File(file_path+".htm");
		//if(folder_f.exists() == true || file.exists() == true)
		if(file.exists() == true)
			return true;
		else
			return false;
	}
	
	/**
     * 根据页面ID，存储路径，英文名判断页面是否存在    
     * @param String  page_path
     * @param String page_ename
     * @param String site_id
     * @return boolean true表示已存在相同的页面
     * */
	public static boolean pageIsExist(String page_path,String page_ename,String site_id)
	{
		Set<Integer> pm = page_map.keySet();
		for(int i : pm)
		{
			PageBean pb = page_map.get(i);		
			
			if(page_path.equals(pb.getPage_path()) && page_ename.equals(pb.getPage_ename()) && site_id.equals(pb.getSite_id()))
				return true;
			
		}
		return false;
	}
	
	//新增
	public static boolean insertPage(PageBean pb,SettingLogsBean stl)
	{
		if(pb.getPage_interval() > 0)
			pb.setNext_dtime(DateUtil.getDateTimeAfter(DateUtil.getCurrentDateTime(),pb.getPage_interval()));
		if(PageDAO.insertPage(pb, stl))
		{
			reloadPage();
			return true;
		}
		else
			return false;
	}
	//修改
	public static boolean updatePage(PageBean pb,SettingLogsBean stl)
	{
		//删除服务器上的静态页面 --- 李苏培 2014-01-01修改
		deletePageFile(pb.getId());
		if(PageDAO.updatePage(pb, stl))
		{
			reloadPage();
			return true;
		}
		else
			return false;
	}
	//删除
	public static boolean deletePage(int id,SettingLogsBean stl)
	{
		if(PageDAO.deletePage(id, stl))
		{
			deletePageFile(id);
			reloadPage();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除静态页面　
     * @param int id
     * @return boolean
	 * @throws IOException 
     * */
	public static boolean deletePageFile(int id)
	{
		PageBean pb = getPageBean(id);
		if(pb != null)
		{
			SiteBean sb = SiteManager.getSiteBeanBySiteID(pb.getSite_id());
			if(sb != null)
			{
				String save_path = sb.getSite_path();
				save_path = FormatUtil.formatPath(save_path+pb.getPage_path()+"/"+pb.getPage_ename()+".htm");
				//File f = new File(save_path);
				return FileRmiFactory.delFile(pb.getSite_id(), save_path);
			}
		}
		return false;
	}
	
	/**
     * 生面静态页面　
     * @param int id
     * @return boolean
	 * @throws IOException 
     * */
	public static boolean createHtmlPage(PageBean pb) throws IOException
	{
		return FileRmiFactory.createPage(pb);
	}
	
	public static boolean createPageHandl(PageBean pb) throws IOException
	{
		SiteBean sb = SiteManager.getSiteBeanBySiteID(pb.getSite_id());
		if(sb != null)
		{
			VelocityPageContextImp vpc = new VelocityPageContextImp();
			String html_content = vpc.getHtmlContent(pb);
			
			String save_path = sb.getSite_path();
			save_path = FormatUtil.formatPath(save_path+pb.getPage_path());
			
			File f = new File(save_path);
			if(!f.exists())
			{
				f.mkdirs();
			}
			save_path = FormatUtil.formatPath(save_path+"/"+pb.getPage_ename()+".htm");
			
			FileOperation.writeStringToFile(save_path,html_content,false,"utf-8");
			
			Map<String,String> m = new HashMap<String,String>();
			m.put("last_dtime", DateUtil.getCurrentDateTime());
			m.put("id", pb.getId()+"");
			if(pb.getPage_interval() > 0)
			{					
				m.put("next_dtime", DateUtil.getDateTimeAfter(DateUtil.getCurrentDateTime(),pb.getPage_interval()));
			}
			PageDAO.updatePageTime(m);
			reloadPage();
			return true;
		}
		return false;
	}
	
	/**
     * 生面静态页面　
     * @param int id
     * @return boolean
	 * @throws IOException 
     * */
	public static boolean createHtmlPage(int id) throws IOException
	{
		PageBean pb = getPageBean(id);
		if(pb != null)
		{
			return createHtmlPage(pb);
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException
	{
		//System.out.println(getPageList("cms","11111ddd",0).get(0).getChild_list().size());
		//System.out.println(getPageJSONTreeStr("cms","11111ddd"));
		System.out.println("//index.htm".replaceAll("//", "/"));
	}
}
