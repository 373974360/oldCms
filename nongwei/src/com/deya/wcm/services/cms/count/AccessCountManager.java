package com.deya.wcm.services.cms.count;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.category.ZTCategoryBean;
import com.deya.wcm.bean.cms.count.InfoAccessBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.dao.cms.count.AccessCountDao;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.category.CategoryTreeUtil;
import com.deya.wcm.services.cms.category.ZTCategoryManager;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.control.site.SiteManager;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class AccessCountManager {
	
	//按站点统计
	public static List<InfoAccessBean> getAccessCountsBySite(Map m)
	{
		List<InfoAccessBean> accbeanList = AccessCountDao.getAccessCountsBySite(m);
		if(accbeanList != null){
			for(int i=0;i<accbeanList.size();i++)
			{
				InfoAccessBean  accCountBean = accbeanList.get(i);
				SiteBean sb = SiteManager.getSiteBeanBySiteID(accCountBean.getSite_id());
				
				accCountBean.setSite_name(sb.getSite_name());
				accCountBean.setCount(accCountBean.getIcount());
			}
			return accbeanList; 	
		}else{
			return null;
		}
	}
	
	//按某一个站点下面的所有栏目统计
	public static List<InfoAccessBean> getSiteCountListByCate(Map mp){
		List<InfoAccessBean> listResult = new ArrayList<InfoAccessBean>();
		try{
			List<InfoAccessBean> listAll = getSiteCountListByCate(mp);
										   
			//得到站点的名称 也就是栏目树的最大节点
			String site_id = (String)mp.get("site_id");
			SiteBean sb = SiteManager.getSiteBeanBySiteID(site_id);
			CategoryBean categoryBeanRoot = new CategoryBean();
			categoryBeanRoot.setCat_cname(sb.getSite_name());
			categoryBeanRoot.setCat_id(0);
			categoryBeanRoot.setSite_id(site_id);
			
			//得到网站信息栏目
			InfoAccessBean bean = getSiteChildListCate(categoryBeanRoot,listAll);
			listResult.add(bean);
			
			return listResult;
		}catch (Exception e) {
			e.printStackTrace();
			return listResult;
		}
	}
	
	//getSiteCountListByCate 方法 --- 递归用 -- 网站一般信息用
	private static InfoAccessBean getSiteChildListCate(CategoryBean categoryBean,List<InfoAccessBean> list){
		InfoAccessBean InfoAccessBean = new InfoAccessBean();
		InfoAccessBean.setCat_id(categoryBean.getCat_id());
		
		List<CategoryBean> cateBeans = CategoryManager.getChildCategoryList(categoryBean.getCat_id(),categoryBean.getSite_id());
		if (cateBeans.size() != 0) {
			List<InfoAccessBean> childList = new ArrayList<InfoAccessBean>();
			int accesscount = 0;
			for(InfoAccessBean bean : list){
				if(InfoAccessBean.getCat_id() == bean.getCat_id()){
					accesscount += bean.getIcount();
				}
			}
			for (CategoryBean beanR : cateBeans) {
				if(beanR.getCat_type()!=0){
					continue;
				}
				InfoAccessBean refBean = getSiteChildListCate(beanR, list);			
				accesscount += refBean.getIcount();
				childList.add(refBean);
			} 
			InfoAccessBean.setIcount(accesscount);
		}else {
			int accesscount = 0;
			for(InfoAccessBean bean : list){
				if(InfoAccessBean.getCat_id()==bean.getCat_id()){
					accesscount += bean.getIcount();
				}
			}
			InfoAccessBean.setIcount(accesscount);
	  }
	  return InfoAccessBean;
   }
	
   public static List<InfoAccessBean> getSiteCateAccessList(Map<String, String> mp)
	{
		int cat_id = Integer.parseInt(mp.get("cat_id"));
		String tmp_id =  mp.get("p_id");
		int p_id = 0;
		String site_id = mp.get("site_id");
		if(tmp_id != null){
			 p_id = Integer.parseInt(tmp_id);
		}else{
			 p_id = 0;
		}
		List<CategoryBean>  temp_cat_list = null;
		List<InfoAccessBean> ret = new ArrayList<InfoAccessBean>();
		List<CategoryBean> directSubNode = null;
		if(p_id == -1 && cat_id == -1)
		{
			//点击专题根节点,获取专题分类
			//System.out.println("	zt root	 begin		");
			InfoAccessBean  accessbean = null;
			List<ZTCategoryBean> ztlist = ZTCategoryManager.getZTCategoryList(site_id);
			int icount =0;
			for(int i=0;i<ztlist.size();i++)
			{				
					int zt_cat_id = ztlist.get(i).getZt_cat_id();//专题分类id
					    temp_cat_list = CategoryManager.getZTCategoryListBySiteAndType(zt_cat_id,site_id);
					for(int n=0;n<temp_cat_list.size();n++)
				    {
						String ids = CategoryManager.getAllChildCategoryIDS(temp_cat_list.get(n).getCat_id(),site_id);
						String temp[] = null; 
						if(ids == ""){
							accessbean = new InfoAccessBean();
				        	accessbean.setCat_id(ztlist.get(i).getZt_cat_id());
							accessbean.setCat_name(ztlist.get(i).getZt_cat_name());
							accessbean.setIcount(0);
						}else{
							temp = ids.split(",");
							mp.put("cat_id",ids);
							List<InfoAccessBean> InfoList = AccessCountDao.getAccessInfoListsByCat_SiteId(mp);
							if(InfoList.size()>0 )
							{
							    for(int j=0;j<InfoList.size();j++)
								{
									accessbean = InfoList.get(j);
									if(accessbean != null)
									{
										  int cid = accessbean.getCat_id();
										  int count = accessbean.getIcount();
										  for(int n1=0;n1<temp.length;n1++)
										  {
											if(Integer.parseInt(temp[n1]) == cid)
										    {
										     icount +=count;
											}
										  }
									 }
								} 
								accessbean.setCat_id(ztlist.get(i).getZt_cat_id());
								accessbean.setCat_name(ztlist.get(i).getZt_cat_name());
								accessbean.setIcount(icount);
					        }else{
					        	accessbean = new InfoAccessBean();
					        	accessbean.setCat_id(ztlist.get(i).getZt_cat_id());
								accessbean.setCat_name(ztlist.get(i).getZt_cat_name());
								accessbean.setIcount(0);
					        }
						}
				   }
				ret.add(accessbean);
			}
		}else if(p_id == -1 && cat_id != -1)
		{
			//点击专题分类 cat_id,专题分类下的栏目
			int zt_cat_id  = cat_id;
			int icount =0;
			    temp_cat_list = CategoryManager.getZTCategoryListBySiteAndType(zt_cat_id,site_id);
				InfoAccessBean  accessbean = null;
			for(int i=0;i<temp_cat_list.size();i++)
			{
				boolean isMinorNode = CategoryManager.isHasChildNode(temp_cat_list.get(i).getCat_id(),site_id);
				if(!isMinorNode)
				{
					InfoAccessBean bean = getAccessListBySiteCateId(mp);
					if(bean != null)
					{
						bean.setCat_id(temp_cat_list.get(i).getCat_id());
						bean.setCat_name(temp_cat_list.get(i).getCat_cname());
						ret.add(bean);
					}
				}else{
					directSubNode = CategoryManager.getChildCategoryList(temp_cat_list.get(i).getCat_id(),site_id);
					for(int k=0;k< directSubNode.size();k++)
					{
						String ids = CategoryManager.getAllChildCategoryIDS(directSubNode.get(k).getCat_id(),site_id);
						if(ids == null || "".equals(ids))
						{
							ids = directSubNode.get(k).getCat_id()+"";
						}else{
							ids += ","+directSubNode.get(k).getCat_id();
						}
						mp.put("cat_id",ids);
						List<InfoAccessBean> InfoList = AccessCountDao.getAccessInfoListsByCat_SiteId(mp);
						if(InfoList.size()>0)
						{
							  String temp[] = ids.split(",");
							  for(int j=0;j<InfoList.size();j++)
							  {
									accessbean = InfoList.get(j);
									int cid = accessbean.getCat_id();
									int count = accessbean.getIcount();
									for(int n1=0;n1<temp.length;n1++)
									{
									   if(Integer.parseInt(temp[n1]) == cid)
									   {
										   icount += count;
									   }
									}
							  }
							accessbean.setCat_id(temp_cat_list.get(i).getCat_id());
						    accessbean.setCat_name(temp_cat_list.get(i).getCat_cname());
							accessbean.setIcount(icount);	
				        }else{
				        	accessbean = new InfoAccessBean();
				        	accessbean.setCat_id(temp_cat_list.get(i).getCat_id());
				        	accessbean.setCat_name(temp_cat_list.get(i).getCat_cname());
				        	accessbean.setIcount(0);	
				        }
				    }
					ret.add(accessbean);
			    }
			}	      
		}else{
				//cat_id为零时,表示取得站点下的所有内容管理目录
				if(cat_id == 0)
				{
					directSubNode = CategoryManager.getCategoryListBySiteID(site_id,0);
					for(int n=0;n<directSubNode.size();n++)
					{
						CategoryBean cat = directSubNode.get(n);
						if(cat != null || cat.equals("null"))
						{
							boolean hasOrNo = CategoryManager.isHasChildNode(cat.getCat_id(),site_id);
							if(!hasOrNo)
							{
								mp.remove("cat_id");
								mp.put("cat_id",cat.getCat_id()+"");
								InfoAccessBean bean = getAccessListBySiteCateId(mp);
								if(bean != null)
								{
									bean.setCat_id(cat.getCat_id());
									bean.setCat_name(CategoryManager.getCategoryBean(cat.getCat_id()).getCat_cname());
								}else{
								    bean = new InfoAccessBean();
								    bean.setCat_id(cat.getCat_id());
								    bean.setCat_name(cat.getCat_cname());
								    bean.setIcount(0);
								}
								ret.add(bean);
							}else{
								InfoAccessBean bean = null;
								int icount2 = 0;
								String cat_ids = getAllLeafCateChildIDS(cat,site_id).trim();
								if(cat_ids != null && cat_ids !="")
								{
									mp.remove("cat_id");
									mp.put("cat_id",cat_ids);
									String temp[] = cat_ids.split(",");
									List<InfoAccessBean> InfoList = AccessCountDao.getAccessInfoListsByCat_SiteId(mp);
									if(InfoList.size() >0 )
									{
										  for(int j=0;j<InfoList.size();j++)
										  {
											bean = InfoList.get(j);
											int cid = bean.getCat_id();
											int count = bean.getIcount();
											for(int n1=0;n1<temp.length;n1++)
											{
											   if(Integer.parseInt(temp[n1]) == cid)
											   {
												   icount2 += count;
											   }
											}
										  }
										  bean.setCat_id(cat.getCat_id());
										  bean.setCat_name(cat.getCat_cname());
										  bean.setIcount(icount2);
										  ret.add(bean);
									 }else{
									      bean = new InfoAccessBean();
										  bean.setCat_id(cat.getCat_id());
										  bean.setCat_name(cat.getCat_cname());
										  bean.setIcount(0);
									      ret.add(bean);
									}
								}else{
									continue;
								}
							}
						}else{
							continue;
						}
					}
				}else{
					// 判断cat_id对应的分类是否是枝节点
					boolean isMinorNode = CategoryManager.isHasChildNode(cat_id,site_id);
					// 如果是叶节点直接取统计结果
					if(!isMinorNode)
					{
						InfoAccessBean bean = getAccessListBySiteCateId(mp);
						if(bean != null)
						{
							bean.setCat_id(cat_id);
							bean.setCat_name(CategoryManager.getCategoryBean(cat_id).getCat_cname());
							ret.add(bean);
						}
					}else{
						directSubNode = CategoryManager.getChildCategoryList(cat_id,site_id);
						for(int n=0;n<directSubNode.size();n++)
						{
							CategoryBean cat = directSubNode.get(n);
							if(cat != null || cat.equals("null"))
							{
								boolean hasOrNo = CategoryManager.isHasChildNode(cat.getCat_id(),site_id);
								if(!hasOrNo)
								{
									mp.remove("cat_id");
									mp.put("cat_id",cat.getCat_id()+"");
									InfoAccessBean bean = getAccessListBySiteCateId(mp);
									if(bean != null)
									{
										bean.setCat_id(cat.getCat_id());
										bean.setCat_name(CategoryManager.getCategoryBean(cat.getCat_id()).getCat_cname());
									}else{
									    bean = new InfoAccessBean();
									    bean.setCat_id(cat.getCat_id());
									    bean.setCat_name(cat.getCat_cname());
									    bean.setIcount(0);
									}
									ret.add(bean);
								}else{
									InfoAccessBean bean = null;
									int icount2 = 0;
									String cat_ids = getAllLeafCateChildIDS(cat,site_id).trim();
									if(cat_ids != null && cat_ids !="")
									{
										mp.remove("cat_id");
										mp.put("cat_id",cat_ids);
										String temp[] = cat_ids.split(",");
										List<InfoAccessBean> InfoList = AccessCountDao.getAccessInfoListsByCat_SiteId(mp);
										if(InfoList.size() >0 )
										{
											  for(int j=0;j<InfoList.size();j++)
											  {
												bean = InfoList.get(j);
												int cid = bean.getCat_id();
												int count = bean.getIcount();
												for(int n1=0;n1<temp.length;n1++)
												{
												   if(Integer.parseInt(temp[n1]) == cid)
												   {
													   icount2 += count;
												   }
												}
											  }
											  bean.setCat_id(cat.getCat_id());
											  bean.setCat_name(cat.getCat_cname());
											  bean.setIcount(icount2);
											  ret.add(bean);
										 }else{
										      bean = new InfoAccessBean();
											  bean.setCat_id(cat.getCat_id());
											  bean.setCat_name(cat.getCat_cname());
											  bean.setIcount(0);
										      ret.add(bean);
										}
									}
								}
							}
						}
				  }
			}
		}
		return ret;
	}
	
	public static InfoAccessBean getAccessListBySiteCateId(Map<String, String> mp)
	{
		int icount = 0;
		String count ="";
		String site_id = mp.get("site_id");
		String cat_id  = mp.get("cat_id");
		mp.remove("cat_id");
		String[] temp = null;
		InfoAccessBean bean  = null;
		List<InfoAccessBean> InfoList = AccessCountDao.getAccessInfoListsByCat_SiteId(mp);
		if(InfoList != null)
		{
			for(int i=0;i<InfoList.size();i++)
			{
				bean = InfoList.get(i);
				if(cat_id != null)
				{
					CategoryBean cb2 = CategoryManager.getCategoryBean(bean.getCat_id());
					temp = cat_id.split(",");
					for(int j=0;j<temp.length;j++)
					{
						CategoryBean cb1 = CategoryManager.getCategoryBean(Integer.parseInt(temp[j]));
						if(Integer.parseInt(temp[j]) == cb2.getParent_id())
						{
							icount += bean.getIcount();
						}else if(Integer.parseInt(temp[j]) == bean.getCat_id())
						{
							icount = bean.getIcount();
						}
					}
					bean.setCat_name(cb2.getCat_cname());
					bean.setIcount(icount);
				}
			}
		}
		return bean;
	}
	
	public static List<InfoAccessBean> getAccessInfoListsByCats(Map<String, String> mp)
	{
		int cat_id = Integer.parseInt(mp.get("cat_id"));
		String site_id  = mp.get("site_id");
		String ids ="";
		boolean isMinorNode = CategoryManager.isHasChildNode(cat_id,site_id);
		//如果是叶节点直接取统计结果
		if(!isMinorNode)
		{
			return  AccessCountDao.getAccessInfoLists(mp);
		}else{
			ids = CategoryManager.getAllChildCategoryIDS(cat_id,site_id);		
			mp.put("cat_id",ids);
			return  AccessCountDao.getAccessInfoLists(mp);
		}
	}
	
	
	public static  InfoAccessBean getBeanByID(String info_id){
		return  AccessCountDao.getBeanByID(info_id);
	}
	
	public static String getAllLeafCateChildIDS(CategoryBean cgb,String site_id)
	{
		if(cgb != null)
		{
			boolean isSub = cgb.isIs_sub();
			String cat_position = cgb.getCat_position();
			Set<Integer> set = CategoryManager.category_m.keySet();
			String cat_ids = "";
			for(int i : set){
				cgb = CategoryManager.category_m.get(i);
				if(cgb.getCat_position().startsWith(cat_position) && !cat_position.equals(cgb.getCat_position()) && site_id.equals(cgb.getSite_id()) && !isSub)
					cat_ids += ","+cgb.getCat_id();
			}			
			if(cat_ids.length() > 0)
				cat_ids = cat_ids.substring(1);
			return cat_ids;
		}else
			return null;
	}
	public static String getCateChildByZt_cateid(CategoryBean cgb,String site_id)
	{
		if(cgb != null)
		{
			boolean isSub = cgb.isIs_sub();
			String cat_position = cgb.getCat_position();
			Set<Integer> set = CategoryManager.category_m.keySet();
			String cat_ids = "";
			for(int i : set){
				cgb = CategoryManager.category_m.get(i);
				if(cgb.getCat_position().startsWith(cat_position) && !cat_position.equals(cgb.getCat_position()) && site_id.equals(cgb.getSite_id()) && !isSub)
					cat_ids += ","+cgb.getCat_id();
			}			
			if(cat_ids.length() > 0)
				cat_ids = cat_ids.substring(1);
			return cat_ids;
		}else
			return null;
	}
	
	public static boolean deleteAccessCountInfos(Map m){
		if(AccessCountDao.deleteAccessCountInfo(m))
		{
			return  true;
		}else{
			return false;
		}
	}
	
	
	//统计每个子栏目的信息统计量
	public static List<InfoAccessBean>  getCatOrderListByCat_id(Map mp)
	{
		if(mp.get("row_count") == "" || mp.get("row_count") == null){
			mp.put("row_count", 10);
		}
		List<InfoAccessBean> list = AccessCountDao.getCatOrderListByCat_id(mp);
		if(list != null)
		{
			for(int i=0;i<list.size();i++)
			{
				InfoAccessBean accessbean = list.get(i);
				int cid = accessbean.getCat_id();
				int count = accessbean.getIcount();
				CategoryBean cb1 = CategoryManager.getCategoryBean(cid);
				
				list.get(i).setCat_name(cb1.getCat_cname());
				list.get(i).setCount(count);
			}
		}
		return list;
	}
	
	//统计每个子栏目的信息统计量
	public static List<InfoAccessBean>  getInfoOrderListByInfo_id(Map mp){
		List<InfoAccessBean> l = new ArrayList<InfoAccessBean>();
		if(mp.get("row_count") == "" || mp.get("row_count") == null){
			mp.put("row_count", 10);
		}
		List<InfoAccessBean> list = AccessCountDao.getInfoOrderListByInfo_id(mp);
		if(list != null){
			for(int i=0;i<list.size();i++)
		    {
				InfoAccessBean accessbean = list.get(i);
				int info_id = accessbean.getInfo_id();
				int count = accessbean.getIcount();
				InfoBean b = InfoBaseManager.getInfoById(info_id+"");
				if(b != null)
				{
					list.get(i).setInfo_title(b.getTitle());
					list.get(i).setCount(count);
					
					l.add(list.get(i));
				}
			}
		}
		return l;
    }
	
	 //昨日，今日，日均访问量
    public static Map<String,String> getDayAccessCountList(String site_id,String constant) 
  	{
    	Map<String, String> rs_mp = new HashMap<String, String>();
    	try {
    		rs_mp = AccessCountDao.getDayAccessCountList(site_id,constant);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs_mp;
  	}
	
	
	
	public static void main(String[] args)
	{
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("cat_id", "0");
		mp.put("site_id", "HIWCMdemo");
		System.out.println(getAccessInfoListsByCats(mp));
	}
}