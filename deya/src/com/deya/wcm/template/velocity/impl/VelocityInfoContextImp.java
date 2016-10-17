package com.deya.wcm.template.velocity.impl;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import com.deya.util.labelUtil.AutoPagerHandl;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.zwgk.appcatalog.AppCatalogBean;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.cms.info.ModelUtil;
import com.deya.wcm.services.system.assist.HotWordManager;
import com.deya.wcm.services.zwgk.appcatalog.AppCatalogManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;
import com.deya.wcm.template.velocity.VelocityContextAbstract;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-4-22 下午04:28:38
 */
public class VelocityInfoContextImp extends VelocityContextAbstract {
	public VelocityInfoContextImp(HttpServletRequest request)
	{
		super(request);
	}
	
	/**
     * 内容页数据
     * @param String info_id
     * @param String site_id
     * @param String template_ids
     * @param String model_name
     * @return boolean
     * */
	public VelocityInfoContextImp(String info_id,String site_ids,String template_ids,String model_ename)
	{		
		site_id = site_ids;
		template_id = template_ids;
		vcontext.put("info_id", info_id);
		vcontext.put("site_id", site_ids);
		if(InfoBaseManager.ARTICLE_MODEL_ENAME.equals(model_ename) || InfoBaseManager.GKTYGS_MODEL_ENAME.equals(model_ename) || "video".equals(model_ename)
				|| "pic".equals(model_ename) || "gkvideo".equals(model_ename)
				|| "gkpic".equals(model_ename))
		{
			//文章字段要替换热词
			Object o = ModelUtil.select(info_id,site_ids,model_ename);
			String item_name = "";//内容字段的名称
			if(InfoBaseManager.ARTICLE_MODEL_ENAME.equals(model_ename) || "video".equals(model_ename) || "gkvideo".equals(model_ename))
				item_name = "info_content";
			if(InfoBaseManager.GKTYGS_MODEL_ENAME.equals(model_ename))
				item_name = "gk_content";
			if("pic".equals(model_ename) ||  "gkpic".equals(model_ename))
				item_name = "pic_content";
			try {				
				BeanUtils.setProperty(o, item_name, HotWordManager.replaceContentHotWord(BeanUtils.getProperty(o, item_name)));
				vcontext.put("InfoData", o);	
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
		else
			vcontext.put("InfoData", ModelUtil.select(info_id,site_ids,model_ename));				
	}
	
	/**
     * 动态内容页数据
     * @param String info_id
     * @param String site_id
     * @param String template_ids
     * @param String model_name
     * @return boolean
     * */
	public VelocityInfoContextImp(String info_id,String site_ids,String template_ids,String model_ename,int current_page,HttpServletRequest request)
	{		
		super(request);
		site_id = site_ids;
		template_id = template_ids;
		vcontext.put("info_id", info_id);
		vcontext.put("site_id", site_ids);
		if(InfoBaseManager.ARTICLE_MODEL_ENAME.equals(model_ename) || InfoBaseManager.GKTYGS_MODEL_ENAME.equals(model_ename) || "video".equals(model_ename)
				|| "pic".equals(model_ename) || "gkvideo".equals(model_ename)
				|| "gkpic".equals(model_ename))
		{
			//文章字段要替换热词
			Object o = ModelUtil.select(info_id,site_ids,model_ename);
			String item_name = "";//内容字段的名称
			if(InfoBaseManager.ARTICLE_MODEL_ENAME.equals(model_ename) || "video".equals(model_ename) || "gkvideo".equals(model_ename))
				item_name = "info_content";
			if(InfoBaseManager.GKTYGS_MODEL_ENAME.equals(model_ename))
				item_name = "gk_content";
			if("pic".equals(model_ename) ||  "gkpic".equals(model_ename))
				item_name = "pic_content";
			try {
				String page_count = BeanUtils.getProperty(o, "page_count");
				if("0".equals(page_count))
				{
					BeanUtils.setProperty(o, item_name, HotWordManager.replaceContentHotWord(BeanUtils.getProperty(o, item_name)));
				}else
				{
					String is_am_tupage = BeanUtils.getProperty(o, "is_am_tupage");
					String content = BeanUtils.getProperty(o, item_name);
					
					if("1".equals(is_am_tupage))
					{
						//自动翻页的
						content = AutoPagerHandl.splitContent(content,current_page,Integer.parseInt(BeanUtils.getProperty(o, "tupage_num")));						
					}
					else
					{
						//手动翻页的						
						String[] tempA =  content.split("<p class=\"ke-pageturning\">.*?</p>");
						content = tempA[current_page-1];
					}
					content = HotWordManager.replaceContentHotWord(content);
					BeanUtils.setProperty(o, item_name, content);
				}
				vcontext.put("InfoData", o);	
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
		else
			vcontext.put("InfoData", ModelUtil.select(info_id,site_ids,model_ename));				
	}
	
	/**
     * 文章内容带翻页的
     * @param ArticleBean ab
     * @return boolean
     * */
	public VelocityInfoContextImp(Object o,String site_ids,String template_ids,int cur_page_num)
	{
		site_id = site_ids;
		template_id = template_ids;
		try {
			vcontext.put("info_id", BeanUtils.getProperty(o, "info_id"));
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
		vcontext.put("site_id", site_ids);
		vcontext.put("cur_page_num", cur_page_num);
		vcontext.put("InfoData", o);
	}
	
	/**
     * 栏目首页数据
     * @param int cat_id
     * @param String site_ids
     * @param String template_ids
     * @return boolean
     * */
	public VelocityInfoContextImp(int cat_id,String site_ids,String template_ids)
	{
		site_id = site_ids;
		template_id = template_ids;
		vcontext.put("site_id", site_ids);
		vcontext.put("cat_id", cat_id);
	}
	
	/**
     * 手动设置模板ID     
     * @param String site_ids
     * @param String template_ids
     * @return boolean
     * */
	public void setTemplateID(String site_ids,String template_ids)
	{
		site_id = site_ids;
		template_id = template_ids;
	}
	
	/**
     * 设置业务属性
     * @param String cat_id 栏目ID
     * @param String site_id
     * @param String node_id
     * @param temp_type 模板类型　index,list,content
     * @return boolean
     * */
	public void setTemplateID(String cat_id,String s_site_id,String node_id,String temp_type)
	{	
		String cat_site_id = s_site_id;//栏目所属的站ID,默认取站点ID
		if(node_id != null && !"".equals(node_id))//如果公开节点ID不为空的话，取节点的ID
			cat_site_id = node_id;
		site_id = s_site_id;
		vcontext.put("site_id", site_id);	
		
		if("nodeIndex".equals(temp_type))//节点首页
		{
			template_id = GKNodeManager.getGKNodeBeanByNodeID(node_id).getIndex_template_id();
			vcontext.put("node_id", node_id);
			vcontext.put("cat_id", cat_id);
		}else
		{
			if(cat_id != null && !"".equals(cat_id) && !"0".equals(cat_id))
			{
				vcontext.put("cat_id", cat_id);			
				try{
					CategoryBean cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(cat_id), cat_site_id);					
					if(cb != null)
					{
						if("list".equals(temp_type))
							template_id = cb.getTemplate_list()+"";
						if("index".equals(temp_type))
							template_id = cb.getTemplate_index()+"";
						
						if(template_id == null || "".equals(template_id) || "0".equals(template_id))
						{
							String root_tree_id = cb.getCat_position().split("\\$")[2];
							CategoryBean root_cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(root_tree_id), cat_site_id);
							//如果是专题栏目，查找根节点对象						
							if(root_cb.getCat_type() == 1)
							{							
								if("list".equals(temp_type))
									template_id = root_cb.getTemplate_list()+"";
								if("index".equals(temp_type))
									template_id = root_cb.getTemplate_index()+"";
							}
						}
					}else
					{
						//如果没有取到,尝试用ID去取,有些特殊的栏目并不存在于站点中,所以用带站点的方法获取不到
						cb = CategoryManager.getCategoryBean(Integer.parseInt(cat_id));
						if("list".equals(temp_type))
							template_id = cb.getTemplate_list()+"";
						if("index".equals(temp_type))
							template_id = cb.getTemplate_index()+"";
					}
					
					if(template_id == null || "".equals(template_id) || "0".equals(template_id))
					{//公开年报，指南，指引特殊的
						if("10".equals(cat_id) || "11".equals(cat_id) ||"12".equals(cat_id))
						{
							cb = CategoryManager.getCategoryBean(Integer.parseInt(cat_id));
							if("list".equals(temp_type))
								template_id = cb.getTemplate_list()+"";
							if("index".equals(temp_type))
								template_id = cb.getTemplate_index()+"";
						}
					}
					
					//System.out.println("template_id -- template_id"+template_id);
					//System.out.println("template_id -- site_id"+site_id);
				}catch(Exception e)
				{
					e.printStackTrace();
				}			
			}else
			{
				template_id = CategoryManager.getBaseCategoryTemplateListID();
			}
		}
	}
	
	/**
     * 设置共享目录的栏目
     * @param String cat_id 栏目ID
     * @param String site_id
     * @param temp_type 模板类型　index,list,content
     * @return boolean
     * */
	public void setSharedCategoryTemplateID(String cat_id,String s_site_id,String node_id,String temp_type)
	{	
		site_id = s_site_id;
		vcontext.put("site_id", site_id);	
		if(node_id != null && !"".equals(node_id))
			vcontext.put("node_id", node_id);
		
		if(cat_id != null && !"".equals(cat_id) && !"0".equals(cat_id))
		{
			vcontext.put("cat_id", cat_id);			
			try{
				CategoryBean cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(cat_id), "");
				if(cb != null)
				{
					String root_tree_id = cb.getCat_position().split("\\$")[2];
					CategoryBean root_cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(root_tree_id), "");
					if("list".equals(temp_type))
						template_id = root_cb.getTemplate_list()+"";					
				}else
					System.out.println("setSharedCategoryTemplateID -- Category is null id:"+cat_id);
				
			}catch(Exception e)
			{
				e.printStackTrace();
				//System.out.println("setSharedCategoryTemplateID -- Category is null id:"+cat_id);
			}			
		}
		
	}
	
	/**
     * 设置服务的栏目
     * @param String cat_id 栏目ID
     * @param String site_id
     * @param temp_type 模板类型　index,list,content
     * @return boolean
     * */
	public void setGGFWTemplateID(String cat_id,String s_site_id,String temp_type)
	{
		String cat_site_id = "ggfw";//服务默认站点ID
		
		site_id = s_site_id;
		vcontext.put("site_id", site_id);		
		
		if(cat_id != null && !"".equals(cat_id) && !"0".equals(cat_id))
		{
			vcontext.put("cat_id", cat_id);			
			try{
				if("list".equals(temp_type))
					template_id = CategoryManager.getCategoryBeanCatID(Integer.parseInt(cat_id), cat_site_id).getTemplate_list()+"";
				if("index".equals(temp_type))
					template_id = CategoryManager.getCategoryBeanCatID(Integer.parseInt(cat_id), cat_site_id).getTemplate_index()+"";				
				
			}catch(Exception e)
			{
				e.printStackTrace();
				//System.out.println("setGGFWTemplateID -- Category is null id:"+cat_id);
			}			
		}		
	}
	
	/**
     * 设置公开应用目录
     * @param String cata_id 栏目ID
     * @param String s_site_id
     * @param temp_type 模板类型　index,list
     * @return 
     * */
	public void setGKAppCatelogTemplateID(String cata_id,String s_site_id,String temp_type)
	{
		site_id = s_site_id;
		vcontext.put("site_id", site_id);
		if(cata_id != null && !"".equals(cata_id) && !"0".equals(cata_id))
		{
			vcontext.put("cata_id", cata_id);			
			try{
				AppCatalogBean acb = AppCatalogManager.getAppCatalogBean(Integer.parseInt(cata_id));
				if("list".equals(temp_type))
					template_id = acb.getTemplate_list()+"";
				if("index".equals(temp_type))
					template_id = acb.getTemplate_index()+"";				
				
			}catch(Exception e)
			{
				e.printStackTrace();
				//System.out.println("setGKAppCatelogTemplateID -- Category is null id:"+cata_id);
			}			
		}	
	}
}
