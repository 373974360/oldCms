package com.deya.wcm.services.cms.count;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.category.ZTCategoryBean;
import com.deya.wcm.bean.cms.count.CmsCountBean;
import com.deya.wcm.bean.cms.count.SiteCountBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.dao.cms.count.CmsCountDAO;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.category.CategoryRPC;
import com.deya.wcm.services.cms.category.ZTCategoryManager;
import com.deya.wcm.services.control.site.SiteManager;

public class CmsCountManager {
	
	/**
	 * 取得站点信息统计,可以按日,按月,按类目统计.
	 * 按日mp须有byday项,按月须有bymonth项,否则按照类目统计
	 * 按类目统计时,只取到下一级目录统计结果
	 * @param mp	统计参数
	 * @return	统计结果list
	 */
	public static List<CmsCountBean> getInfoCount(Map<String, String> mp){
		
		formatDate(mp);
		if(mp.get("byday") != null || mp.get("bymonth") != null){
			return getInfoCountByDate(mp);
		}else {
			return getInfoCountByCat(mp);
		}
	}
	
	/**
	 * 按栏目分类取得信息统计情况
	 * 如果栏目是叶节点取得叶节点的统计信息,如果栏目是枝节点,
	 * 则取得节点下一级栏目的统计情况.
	 * @param mp	site_id和cat_id这两个参数不可或缺.
	 * @return	统计结果列表,如果为空返回空列表
	 */
	
	/*
	private static List<CmsCountBean> getInfoCountByCat(Map<String, String> mp){
		int cat_id = Integer.parseInt(mp.get("cat_id"));
		String site_id = mp.get("site_id");
		// 判断cat_id对应的分类是否是枝节点
		boolean isMinorNode = CategoryManager.isHasChildNode(cat_id, site_id);
		List<CmsCountBean> ret = new ArrayList<CmsCountBean>();
		
		// 如果是叶节点直接取统计结果
		if(!isMinorNode){
			CmsCountBean bean = CmsCountDAO.getCountListByCat(mp);
			bean.setCat_id(cat_id);
			bean.setCat_name(CategoryManager.getCategoryBean(cat_id).getCat_cname());
			ret.add(bean);
		} else {
			List<CategoryBean> directSubNode;
			
			// cat_id为零时,表示取得站点下的所有内容管理目录
			if(cat_id == 0){
				directSubNode = CategoryManager.getCategoryListBySiteID(site_id, 0);
			}else{
				directSubNode = CategoryManager.getChildCategoryList(cat_id, site_id);
			}
			for(CategoryBean bean : directSubNode) {
				mp.remove("cat_id");
				String ids = CategoryManager.getAllChildCategoryIDS(bean.getCat_id(), site_id);
				if(ids== null || "".equals(ids)){
					ids = bean.getCat_id() + "";
				} else {
					ids += "," + bean.getCat_id();
				}
				mp.put("cat_id", ids);
				CmsCountBean retBean = CmsCountDAO.getCountListByCat(mp);
				retBean.setCat_id(bean.getCat_id());
				retBean.setCat_name(bean.getCat_cname());
				ret.add(retBean);
			}
		}
		return ret;
	}
	*/
	
    //    add 2012-12
	private static List<CmsCountBean> getInfoCountByCat(Map<String, String> mp)
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
		List<CmsCountBean> ret = new ArrayList<CmsCountBean>();
		CmsCountBean retBean = null;
		
		if(p_id == -1 && cat_id == -1)
		{   //专题分类列表
			System.out.println("	run in zt'root 	");
			List<ZTCategoryBean> ztlist = ZTCategoryManager.getZTCategoryList(site_id);
			for(int i=0;i<ztlist.size();i++)
			{	
				 List<CmsCountBean> tmp_ret = new ArrayList<CmsCountBean>();
				 int  allCount = 0; //全部信息条数(按照信息类型划分)
				 int  hostInfoCount = 0; //主信息条数
				 int  refInfoCount = 0; //引用信息条数
				 int  linkInfoCount = 0; //连接信息条数
				 
				 System.out.println("	run in ztroot  ztfenglei "+ztlist.get(i).getZt_cat_id());
				 List<CategoryBean> cat_list = CategoryManager.getZTCategoryListBySiteAndType(ztlist.get(i).getZt_cat_id(),site_id);//根据专题id获取所属专题
				 for(int j=0;j<cat_list.size();j++)
				 {
					List<CategoryBean>  directSubNode = CategoryManager.getChildCategoryList(cat_list.get(j).getCat_id(),site_id);
					for(CategoryBean bean : directSubNode) 
					{
						mp.remove("cat_id");
						String ids = CategoryManager.getAllChildCategoryIDS(bean.getCat_id(), site_id);
						if(ids== null || "".equals(ids))
						{
							ids = bean.getCat_id()+"";
						}else{
							ids += ","+bean.getCat_id();
						}
						mp.put("cat_id",ids);
						CmsCountBean tmp_retBean = CmsCountDAO.getCountListByCat(mp);
						if(tmp_retBean != null)
						{
							tmp_retBean.setCat_id(bean.getCat_id());
							tmp_retBean.setCat_name(bean.getCat_cname());
							tmp_ret.add(tmp_retBean);
						}
					}
				 }
				 //System.out.println("		set begin	tmp_ret.size()	"+tmp_ret.size());
				 if(tmp_ret != null)
				 {
					 for(int n=0;n<tmp_ret.size();n++)
					 {
						 CmsCountBean cmsb = tmp_ret.get(n);
						 if(cmsb != null)
						 {
							 	 allCount += cmsb.getAllCount();
								 hostInfoCount += cmsb.getHostInfoCount();
							     refInfoCount += cmsb.getRefInfoCount();
							     linkInfoCount += cmsb.getLinkInfoCount(); 
						 }
					 } 
					 CmsCountBean retB = new CmsCountBean();
					 retB.setCat_id(ztlist.get(i).getZt_cat_id());
					 retB.setCat_name(ztlist.get(i).getZt_cat_name());
					 retB.setAllCount(allCount);
					 retB.setHostInfoCount(hostInfoCount);
					 retB.setRefInfoCount(refInfoCount);
					 retB.setLinkInfoCount(linkInfoCount);			 
					 ret.add(retB);
				 }
			}			
		}else if(p_id == -1 && cat_id != -1)
		{
			int zt_id = cat_id;
			System.out.println("run in one zt zt_id==="+cat_id);
			List<CmsCountBean> tmp_ret = new ArrayList<CmsCountBean>();
			List<CategoryBean> cat_list = CategoryManager.getZTCategoryListBySiteAndType(zt_id,site_id);
			if(cat_list.size()>0)
			{
				for(int i=0;i<cat_list.size();i++)
				{
					int  allCount = 0; //全部信息条数(按照信息类型划分)
					int  hostInfoCount = 0; //主信息条数
					int  refInfoCount = 0; //引用信息条数
					int  linkInfoCount = 0; //连接信息条数
					//System.out.println("run in zt_id==="+cat_id+"==="+cat_list.get(i).getCat_cname());
					List<CategoryBean> directSubNode = CategoryManager.getChildCategoryList(cat_list.get(i).getCat_id(),site_id);
					for(CategoryBean bean : directSubNode) 
					{
						mp.remove("cat_id");
						String ids = CategoryManager.getAllChildCategoryIDS(bean.getCat_id(), site_id);
						if(ids== null || "".equals(ids)){
							ids = bean.getCat_id() + "";
						} else {
							ids += "," + bean.getCat_id();
						}
						mp.put("cat_id", ids);
						CmsCountBean tmp_retBean = CmsCountDAO.getCountListByCat(mp);
						if(tmp_retBean != null)
						{
							tmp_retBean.setCat_id(bean.getCat_id());
							tmp_retBean.setCat_name(bean.getCat_cname());
							tmp_ret.add(tmp_retBean);
						}
					 }
					 if(tmp_ret != null)
					 {
						 for(int n=0;n<tmp_ret.size();n++)
						 {
							CmsCountBean cmsb = tmp_ret.get(n);
							//System.out.println(n+"=======tmp_ret.get(n)===2222222======"+tmp_ret.get(n));
							if(cmsb != null)
							{
								int catid = cmsb.getCat_id();
								  CategoryBean cb = CategoryManager.getCategoryBean(catid);
								if(cb.getCat_position().startsWith(cat_list.get(i).getCat_position()))
								{
									allCount += cmsb.getAllCount();
									hostInfoCount += cmsb.getHostInfoCount();
									refInfoCount += cmsb.getRefInfoCount();
									linkInfoCount += cmsb.getLinkInfoCount(); 
								}
							}
						 }
					     CmsCountBean retB  = new CmsCountBean();
						 retB.setCat_id(cat_list.get(i).getCat_id());
						 retB.setCat_name(cat_list.get(i).getCat_cname());
						 
						 retB.setAllCount(allCount);
						 retB.setHostInfoCount(hostInfoCount);
						 retB.setRefInfoCount(refInfoCount);
						 retB.setLinkInfoCount(linkInfoCount);			 
						 ret.add(retB); 
					 }
				}
		   }
		}else{
			// 判断cat_id对应的分类是否是枝节点
			System.out.println("======= run  in  sitecates  cat_id == "+cat_id);
			boolean isMinorNode = CategoryManager.isHasChildNode(cat_id,site_id);
			// 如果是叶节点直接取统计结果
			if(!isMinorNode){
				CmsCountBean bean = CmsCountDAO.getCountListByCat(mp);
				bean.setCat_id(cat_id);
				bean.setCat_name(CategoryManager.getCategoryBean(cat_id).getCat_cname());
				ret.add(bean);
			}else{
				// cat_id为零时,表示取得站点下的所有内容管理目录
				List<CategoryBean> directSubNode;
				if(cat_id == 0){
					directSubNode = CategoryManager.getCategoryListBySiteID(site_id,0);
				}else{
					directSubNode = CategoryManager.getChildCategoryList(cat_id,site_id);
				}	
				for(CategoryBean bean : directSubNode)
				{
					mp.remove("cat_id");
					String ids = CategoryManager.getAllChildCategoryIDS(bean.getCat_id(),site_id);
					if(ids== null || "".equals(ids)){
						ids = bean.getCat_id() + "";
					} else {
						ids += "," + bean.getCat_id();
					}
					//System.out.println("run  in  sitecates	ids	========"+ids);
					mp.put("cat_id", ids);
				    retBean = CmsCountDAO.getCountListByCat(mp);
					retBean.setCat_id(bean.getCat_id());
					retBean.setCat_name(bean.getCat_cname());
					ret.add(retBean);
				}
			}
		}
		return ret;
	}

	/**
	 * 按时间取得信息统计情况
	 * 取得选中节点按时间分类的统计情况,时间分为日,月两种
	 * @param mp
	 * @return	统计结果列表,如果为空返回空列表
	 */
	private static List<CmsCountBean> getInfoCountByDate(Map<String, String> mp){
		int cat_id = Integer.parseInt(mp.get("cat_id"));
		String site_id = mp.get("site_id");
		// 判断cat_id对应的分类是否是枝节点
		boolean isMinorNode = CategoryManager.isHasChildNode(cat_id, site_id);
		String ids = cat_id+"";
		if(isMinorNode){
			ids = CategoryManager.getAllChildCategoryIDS(cat_id, site_id);
		}
		mp.remove("cat_ids");
		mp.put("cat_ids", ids);
		
		Map<String, CmsCountBean> p = CmsCountDAO.getCountListByDate(mp);
		
		List<CmsCountBean> ret = new ArrayList<CmsCountBean>();
		ret.addAll(p.values());
		
		Collections.sort(ret, new DescComparator());
		return ret;
	}
	
	/**
	 * 取得站点下用户的工作量统计信息
	 * @param mp	cat_ids
	 * @return	用户工作量统计信息列表
	 */
	public static List<CmsCountBean> getInputCountList(Map<String, String> mp){
		formatDate(mp);
		// 取得站点下用户的所有信息,包括已发和未发的
		mp.remove("info_status");
		Map<String, CmsCountBean> m = CmsCountDAO.getInputCountList(mp);
		
		// 取得站点下用户的已发信息
		mp.put("info_status", "8");
		Map<String, CmsCountBean> m1 = CmsCountDAO.getInputCountList(mp);
		
		// 取得站点下用户的已发信息 并且有图片的信息
		mp.put("is_pic", "1");
		Map<String, CmsCountBean> m2 = CmsCountDAO.getInputCountList(mp);
		
		List<CmsCountBean> ret = new ArrayList<CmsCountBean>();
		// 数据合并,将全部的统计和已发的统计合并到bean中
		for(String s : m.keySet()){
			CmsCountBean bean = m.get(s);
			bean.setInputCount(bean.getCount()); // 设置取得的全部信息
			if(m1.containsKey(s)){
				CmsCountBean temBean = m1.get(s);  // 已发布信息
				bean.setReleasedCount(temBean.getCount());
			}
			if(m2.containsKey(s)){
				CmsCountBean temBean = m2.get(s);  // 已发布信息
				bean.setPicInfoCount(temBean.getCount());
			}  
			bean.setReleaseRate();
			ret.add(m.get(s));
		}
		// 排序
		Collections.sort(ret, new CntDescComparator());
		return ret;
	}

    /**
     * 取得站点下用户的工作量统计信息
     * @param mp	cat_ids
     * @return	用户工作量统计信息列表
     */
    public static List<CmsCountBean> getMemberInputCountList(Map<String, String> mp){
        formatDate(mp);
        // 取得站点下用户的所有信息,包括已发和未发的
        mp.remove("info_status");
        Map<String, CmsCountBean> m = CmsCountDAO.getMemberInputCountList(mp);

        // 取得站点下用户的已发信息
        mp.put("info_status", "8");
        Map<String, CmsCountBean> m1 = CmsCountDAO.getMemberInputCountList(mp);

        // 取得站点下用户的已发信息 并且有图片的信息
        mp.put("is_pic", "1");
        Map<String, CmsCountBean> m2 = CmsCountDAO.getMemberInputCountList(mp);

        List<CmsCountBean> ret = new ArrayList<CmsCountBean>();
        // 数据合并,将全部的统计和已发的统计合并到bean中
        for(String s : m.keySet()){
            CmsCountBean bean = m.get(s);
            bean.setInputCount(bean.getCount()); // 设置取得的全部信息
            if(m1.containsKey(s)){
                CmsCountBean temBean = m1.get(s);  // 已发布信息
                bean.setReleasedCount(temBean.getCount());
            }
            if(m2.containsKey(s)){
                CmsCountBean temBean = m2.get(s);  // 已发布信息
                bean.setPicInfoCount(temBean.getCount());
            }
            bean.setReleaseRate();
            ret.add(m.get(s));
        }
        // 排序
        Collections.sort(ret, new CntDescComparator());
        return ret;
    }
	
	/**
	 * 取得单个用户各个栏目下的工作量统计信息
	 * @param mp
	 * @return	用户工作量统计信息列表
	 */
	public static List<CmsCountBean> getInputCountListByUserID(Map<String, String> mp){
		formatDate(mp);
		mp.remove("info_status"); // 取得全部信息
		Map<String, CmsCountBean> all_m = CmsCountDAO.getInputCountListByUserID(mp);
		
		mp.put("info_status", "8"); // 取得已发信息
		Map<String, CmsCountBean> m = CmsCountDAO.getInputCountListByUserID(mp);
		
		List<CmsCountBean> ret = new ArrayList<CmsCountBean>();
		for(String s : all_m.keySet()){
			CmsCountBean bean = all_m.get(s);
			bean.setInputCount(bean.getCount()); // 设置取得的全部信息
			if(m.containsKey(s)){
				CmsCountBean temBean = m.get(s);
				bean.setReleasedCount(temBean.getCount()); //设置取得的已发信息
			}
			bean.setReleaseRate();  //设置发布率
			ret.add(all_m.get(s));
		}
		Collections.sort(ret, new CntDescComparator());
		return ret;
	}
	
	/**
	 * 取得单个用户各个栏目下的工作量统计信息
	 * @param mp
	 * @return	用户工作量统计信息列表
	 */
	public static List<SiteCountBean> getInputCountListByUserIDCate(Map<String, String> mp){
		num = 0;
		List<SiteCountBean> resultList = new ArrayList<SiteCountBean>();//存放结果
		try{
			formatDate(mp);
			mp.remove("info_status"); // 取得全部信息
			Map<String, CmsCountBean> all_m = CmsCountDAO.getInputCountListByUserID(mp);
			
			mp.put("info_status", "8"); // 取得已发信息
			Map<String, CmsCountBean> m = CmsCountDAO.getInputCountListByUserID(mp);
			mp.put("is_pic", "1"); // 取得已发信息 并且有图片
			Map<String, CmsCountBean> m1 = CmsCountDAO.getInputCountListByUserID(mp);
			
			List<CmsCountBean> ret = new ArrayList<CmsCountBean>();
			for(String s : all_m.keySet()){
				CmsCountBean bean = all_m.get(s);
				bean.setInputCount(bean.getCount()); // 设置取得的全部信息
				if(m.containsKey(s)){
					CmsCountBean temBean = m.get(s);
					bean.setReleasedCount(temBean.getCount()); //设置取得的已发信息
				}
				
				if(m1.containsKey(s)){
					CmsCountBean temBean = m1.get(s);
					bean.setPicInfoCount(temBean.getCount()); //设置取得的已发信息
				}
				
				bean.setReleaseRate();  //设置发布率
				ret.add(all_m.get(s));
			}
			Collections.sort(ret, new CntDescComparator());
			
			//得到用户自己的栏目管理权限树
			
			String site_id = (String)mp.get("site_id");
			int user_id = Integer.valueOf(String.valueOf(mp.get("user_id")));
			//String treeStr = CategoryRPC.getInfoCategoryTreeByUserID(site_id,user_id);
			String treeStr = CategoryRPC.getCategoryTreeByCategoryID(0, site_id);
			//添加 专题栏目的统计        add by 2012-11-28 
			String ztTreeStr = getZTCategoryTreeStrWidthZTName(site_id,user_id);
			String allStr = treeStr.substring(0,treeStr.length()-1)+","+ztTreeStr+"]";
			//System.out.println("allStr	==	"+allStr);
				JSONArray jsonArray = JSONArray.fromObject(allStr); 
			    //JSONArray jsonArray = JSONArray.fromObject(treeStr);  
	        Iterator it = jsonArray.iterator();
	        while(it.hasNext()){
	        	JSONObject jsonObject = (JSONObject)it.next();
	        	SiteCountBean  siteCountBean = doOutTreeBean(jsonObject,ret,site_id);
	        	//如果该栏目添加的信息数是0时  不显示该栏目
	        	if(siteCountBean.getInputCount()!=0){
	        		resultList.add(siteCountBean);
	        	}
	        }
			
			return resultList;
		}catch (Exception e) {
			e.printStackTrace();
			return resultList;
		}
		
	}
	
	static int num = 0;
	
	//getInputCountListByUserID  -- 调用的递归方法用
	public static SiteCountBean doOutTreeBean(JSONObject jsonObject,List<CmsCountBean> list,String site_id){
		SiteCountBean siteCountBean = new SiteCountBean();
		try{
			String str = jsonObject.toString();
			JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("children"));
			if(!jsonArray.toString().equals("[null]") && jsonArray!=null && !"".equals(jsonArray) && jsonArray.size()>0){//有子节点
				int cat_id = Integer.valueOf(String.valueOf(jsonObject.get("id")));
				siteCountBean.setCat_id(cat_id);
				siteCountBean.setIs_leaf(false);
				
				SiteBean sb = SiteManager.getSiteBeanBySiteID(site_id);
				String cat_name = String.valueOf(jsonObject.get("text"));
				if(cat_name==null || "".equals(cat_name)){
					cat_name = sb.getSite_name();
				}
				siteCountBean.setCat_name(cat_name);
				//siteCountBean.setCat_name(String.valueOf(jsonObject.get("text")));
				int inputCount = 0;
				int releasedCount = 0; //发布信息条数
				int picReleasedCount = 0; //发布信息条数 并且有图片
				Iterator it = jsonArray.iterator();
				List<SiteCountBean> childList = new ArrayList<SiteCountBean>();//存放结果
		        while(it.hasNext()){  
		        	Object object = it.next();
		        	if(object!=null){ 
			        	JSONObject jsonObject2 = (JSONObject)object;
			        	if(jsonObject2!=null){
			        		SiteCountBean countBean = doOutTreeBean(jsonObject2,list,site_id);
			        		inputCount += countBean.getInputCount();
			        		releasedCount += countBean.getReleasedCount();
							picReleasedCount += countBean.getPicReleasedCount();
							//如果该栏目添加的信息数是0时  不显示该栏目
			        		if(countBean.getInputCount()!=0){
			        			childList.add(countBean);
			        		}
			        	}	        		
		        	}
		        }
		        siteCountBean.setInputCount(inputCount);
				siteCountBean.setReleasedCount(releasedCount);
				siteCountBean.setPicReleasedCount(picReleasedCount);
				siteCountBean.setReleaseRate();
				siteCountBean.setChild_list(childList); 
				
			}else{//没有子节点
				int cat_id = Integer.valueOf(String.valueOf(jsonObject.get("id")));
				siteCountBean.setCat_id(cat_id);
				siteCountBean.setIs_leaf(true);
				siteCountBean.setCat_name(String.valueOf(jsonObject.get("text")));
				int inputCount = 0;
				int releasedCount = 0; //发布信息条数
				int picReleasedCount = 0; //发布信息条数 并且有图片
				for(CmsCountBean cmsCountBean : list){
					if(cat_id==cmsCountBean.getCat_id()){
						num++;
						System.out.println("cat_id --- " + cat_id);
						System.out.println("num --- " + num);
						
						inputCount += cmsCountBean.getInputCount();
						releasedCount += cmsCountBean.getReleasedCount();
						picReleasedCount += cmsCountBean.getPicInfoCount();
						break;
					}
				}
				siteCountBean.setInputCount(inputCount);
				siteCountBean.setReleasedCount(releasedCount);
				siteCountBean.setPicReleasedCount(picReleasedCount);
				siteCountBean.setReleaseRate();
				
			}
			
			return siteCountBean;
		}catch (Exception e) {
			e.printStackTrace();
			return siteCountBean;
		}
		
	}
	
	/**
	 * 对参数中的字符型时间进行格式化
	 * 主要是针对时间格式中出现类似 2011-1-1短格式日期
	 * 处理完后,会变成2011-01-01形式的标准格式,然后在对结束时间拼接上
	 * 时间 " 23:59:59"以便包括当日的所有信息
	 * 
	 * exception-如果时间的解析出现问题,开始结束时间回取得当日的开始结束时间
	 * 如果有其他规则请重新处理catch{}中的代码
	 * @param mp
	 */
	private static void formatDate(Map<String, String> mp){
		String s_day = mp.get("start_day");
		String e_day = mp.get("end_day");
		
		// 如果条件中没有时间,则直接退出
		if(e_day == null){
			return;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			Date date = sdf.parse(e_day);
			Date s_date = sdf.parse(s_day);
			e_day = sdf.format(date) + " 23:59:59";
			s_day = sdf.format(s_date);
			
		}catch(ParseException pex){
			e_day = sdf.format(new Date()) + " 23:59:59";
			s_day = sdf.format(new Date());
		}
		
		mp.remove("end_day");
		mp.put("end_day", e_day);
		mp.remove("start_day");
		mp.put("start_day", s_day);
	}
	
	
	
	/**
	 * 取得所选栏目下的工作量统计信息
	 * @param mp
	 * @return	取得所选栏目下的工作量统计信息列表
	 */
	public static List<SiteCountBean> getInputCountListByCate(Map<String, String> mp){
		
		List<SiteCountBean> listResult = new ArrayList<SiteCountBean>();
		try{
			String cat_ids = (String)mp.get("cat_ids");
			if(cat_ids==null){
				cat_ids = ""; 
			}
			Set<String> catSet = new HashSet<String>();
			if(!cat_ids.equals("")){
				List<String> catList = Arrays.asList(cat_ids.split(","));
				for(String cat_id : catList){
					if(cat_id!=null && !"".equals(cat_id)){
					 	CategoryBean categoryBean = CategoryManager.getCategoryBean(Integer.parseInt(cat_id));
					 	String cat_position = categoryBean.getCat_position();
					 	List<String> catpList = Arrays.asList(cat_position.split("\\$"));
					 	for(String catp_id : catpList){
					 		if(catp_id!=null && !"".equals(catp_id)){
					 			catSet.add(catp_id);
							}
					 	}
					}
				}
			}
			System.out.println("---getInputCountListByCate----catSet == " + catSet);
			
			listResult = getInputCountListByCate(mp,catSet);
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return listResult;
		}
	}
	
	
	/**
	 * 取得所选栏目下的工作量统计信息
	 * @param mp
	 * @return	取得所选栏目下的工作量统计信息列表
	 */
	public static List<SiteCountBean> getInputCountListByCate(Map<String, String> mp,Set<String> catSet){
		List<SiteCountBean> resultList = new ArrayList<SiteCountBean>();//存放结果
		try{
			formatDate(mp);
			System.out.println("---getInputCountListByCate---map = " + mp);
			mp.remove("info_status"); // 取得全部信息
			Map<String, CmsCountBean> all_m = CmsCountDAO.getInputCountListByCate(mp);
			
			mp.put("info_status", "8"); // 取得已发信息
			Map<String, CmsCountBean> m = CmsCountDAO.getInputCountListByCate(mp);
			mp.put("is_pic", "1"); // 取得已发信息 并且有图片
			Map<String, CmsCountBean> m1 = CmsCountDAO.getInputCountListByCate(mp);
			  
			List<CmsCountBean> ret = new ArrayList<CmsCountBean>();
			for(String s : all_m.keySet()){
				CmsCountBean bean = all_m.get(s);
				bean.setInputCount(bean.getCount()); // 设置取得的全部信息
				if(m.containsKey(s)){
					CmsCountBean temBean = m.get(s);
					bean.setReleasedCount(temBean.getCount()); //设置取得的已发信息
				}
				
				if(m1.containsKey(s)){
					CmsCountBean temBean = m1.get(s);
					bean.setPicInfoCount(temBean.getCount()); //设置取得的已发信息
				}
				
				bean.setReleaseRate();  //设置发布率
				ret.add(all_m.get(s));
			}
			Collections.sort(ret, new CntDescComparator());
			
			//得到用户自己的栏目管理权限树
			String site_id = (String)mp.get("site_id");
			String input_users = (String)mp.get("input_user");
			if(input_users==null){
				input_users = "0";
			}
			int user_id = Integer.parseInt(input_users);
			//String treeStr = CategoryRPC.getInfoCategoryTreeByUserID(site_id,user_id);
			String treeStr = CategoryRPC.getCategoryTreeBySiteID(site_id);
			System.out.println("---CmsCountManager--getInputCountListByCate---catetreeStr = " + treeStr);
			//添加 专题栏目的统计        add by 2012-11-28
			String ztTreeStr = getZTCategoryTreeStrWidthZTName(site_id);
			String allStr = treeStr.substring(0,treeStr.length()-1)+","+ztTreeStr+"]";
			   System.out.println("---CmsCountManager--getInputCountListByCate---treeStr   allStr	==	"+allStr);
				JSONArray jsonArray = JSONArray.fromObject(allStr); 
			    //JSONArray jsonArray = JSONArray.fromObject(treeStr);  
	        Iterator it = jsonArray.iterator();
	        while(it.hasNext()){
	        	JSONObject jsonObject = (JSONObject)it.next();
	        	SiteCountBean  siteCountBean = doOutTreeBeanByCate(jsonObject,ret,catSet);
	        	resultList.add(siteCountBean);
	        }
			return resultList;
		}catch (Exception e) {
			e.printStackTrace();
			return resultList;
		}
	}
	
	//getInputCountListByCate  -- 调用的递归方法用
	public static SiteCountBean doOutTreeBeanByCate(JSONObject jsonObject,List<CmsCountBean> list,Set<String> catSet){
		SiteCountBean siteCountBean = new SiteCountBean();
		try{
			String str = jsonObject.toString();
			JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("children"));
			if(!jsonArray.toString().equals("[null]") && jsonArray!=null && !"".equals(jsonArray) && jsonArray.size()>0){//有子节点
				int cat_id = Integer.valueOf(String.valueOf(jsonObject.get("id")));
				//判断选没有选该栏目
				if(catSet.size()>0 && !catSet.contains(cat_id+"")){
					return null;
				}
				siteCountBean.setCat_id(cat_id);
				siteCountBean.setIs_leaf(false);
				siteCountBean.setCat_name(String.valueOf(jsonObject.get("text")));
				int inputCount = 0;
				int releasedCount = 0; //发布信息条数
				int picReleasedCount = 0; //发布信息条数 并且有图片
				Iterator it = jsonArray.iterator();
				List<SiteCountBean> childList = new ArrayList<SiteCountBean>();//存放结果
		        while(it.hasNext()){  
		        	Object object = it.next();
		        	if(object!=null){ 
			        	JSONObject jsonObject2 = (JSONObject)object;
			        	if(jsonObject2!=null){
			        		SiteCountBean countBean = doOutTreeBeanByCate(jsonObject2,list,catSet);
			        		if(countBean!=null){
			        			inputCount += countBean.getInputCount();
								releasedCount += countBean.getReleasedCount();
								picReleasedCount += countBean.getPicReleasedCount();
								childList.add(countBean);
			        		}
			        	}	        		
		        	}
		        }
		        siteCountBean.setInputCount(inputCount);
				siteCountBean.setReleasedCount(releasedCount);
				siteCountBean.setPicReleasedCount(picReleasedCount);
				siteCountBean.setReleaseRate();
				siteCountBean.setChild_list(childList); 
				
			}else{//没有子节点
				int cat_id = Integer.valueOf(String.valueOf(jsonObject.get("id")));
				//判断选没有选该栏目
				if(catSet.size()>0 && !catSet.contains(cat_id+"")){
					return null;
				}
				siteCountBean.setCat_id(cat_id);
				siteCountBean.setIs_leaf(true);
				siteCountBean.setCat_name(String.valueOf(jsonObject.get("text")));
				int inputCount = 0;
				int releasedCount = 0; //发布信息条数
				int picReleasedCount = 0; //发布信息条数 并且有图片
				for(CmsCountBean cmsCountBean : list){
					if(cat_id==cmsCountBean.getCat_id()){
						inputCount += cmsCountBean.getInputCount();
						releasedCount += cmsCountBean.getReleasedCount();
						picReleasedCount += cmsCountBean.getPicInfoCount();
						break;
					}
				}
				siteCountBean.setInputCount(inputCount);
				siteCountBean.setReleasedCount(releasedCount);
				siteCountBean.setPicReleasedCount(picReleasedCount);
				siteCountBean.setReleaseRate();
			}
			
			return siteCountBean;
		}catch (Exception e) {
			e.printStackTrace();
			return siteCountBean;
		}
	}

	/**
     * 根据站点ID得到有权限管理的专题分类树
     * @param String site_id
     * @return String
     * */
	public static String getZTCategoryTreeStrWidthZTName(String site_id,int user_id)
	{
		String roo_name = "专题栏目";
		String cate_str = ZTCategoryManager.getZTCategoryTreeJsonStr(site_id,user_id);
		if(cate_str != null && !"[]".equals(cate_str))
			return "{\"id\":1,\"text\":\""+roo_name+"\",\"attributes\":{\"url\":\"\",\"handl\":\"\"},\"state\":\"'closed'\",\"children\":"+cate_str+"}";
		else 
			return "";
	}
	
	/**
     * 根据站点ID得到有权限管理的专题分类树
     * @param String site_id
     * @return String
     * */
	public static String getZTCategoryTreeStrWidthZTName(String site_id)
	{
		String roo_name = "专题栏目";
		String cate_str = ZTCategoryManager.getZTCategoryTreeJsonStr(site_id);
		if(cate_str != null && !"[]".equals(cate_str))
			return "{\"id\":0,\"text\":\""+roo_name+"\",\"attributes\":{\"url\":\"\",\"handl\":\"\"},\"state\":\"'closed'\",\"children\":"+cate_str+"}";
		else 
			return "";
	}
	
	/*  根据专题分类id 得到该分类下的栏目 */
	public static List<CategoryBean> getZTCategoryListByZTCat_id(int zt_cateid)
	{
		List<CategoryBean> l = new ArrayList<CategoryBean>();
		Set<Integer> s = CategoryManager.category_m.keySet();
		for(int i : s)
		{
			CategoryBean zb = CategoryManager.category_m.get(i);
			if(zt_cateid == zb.getZt_cat_id())
				l.add(zb);
		}
		if(l != null && l.size() > 0)
			//Collections.sort(l,new ZTCategoryComparator());
		    Collections.sort(l,new CategoryManager.CategoryComparator());
		return l;
	}
	
	/**
	 * 取用户工作量统计信息详细列表
	 * @param mp
	 * @return	用户工作量统计信息详细列表
	 */
	public static List<InfoBean> getInfoListByUserIDTimeType(Map<String, String> map)
	{
		return CmsCountDAO.getInfoListByUserIDTimeType(map);
	}
	
	/**
	 * 取得所选栏目下的信息更新情况
	 * @param mp
	 * @return	取得所选栏目下的信息更新列表
	 */
	public static List<SiteCountBean> getInfoUpdateListByCate(Map<String, String> mp){
		List<SiteCountBean> resultList = new ArrayList<SiteCountBean>();//存放结果
		try{
			System.out.println("---getInfoUpdateListByCate---map = " + mp);
			resultList = CmsCountDAO.getInfoUpdateListByCate(mp);
			return resultList;
		}catch (Exception e) {
			e.printStackTrace();
			return resultList;
		}
	}
	
	/**
	 * main函数,做一些简单测试用
	 * @param arg
	 */
	public static void main(String arg[]){
		//String ids = CategoryManager.getAllChildCategoryIDS("1072", "HIWCMdemo");
		String str = "$1$2$3$";
		List<String> catpList = Arrays.asList(str.split("\\$"));
		for(String st : catpList){
			if(st!=null && !"".equals(st)){
				System.out.println(st);
			}
		}
	}
	
	/**
	 * 取得站点下作者的统计信息
	 * @param mp	cat_ids
	 * @return	站点下作者的统计信息
	 */
	public static List<CmsCountBean> getAuthorCountList(Map<String, String> mp){
		formatDate(mp);
		// 取得站点下用户的所有信息,包括已发和未发的
		mp.remove("info_status");
		Map<String, String> m = CmsCountDAO.getAuthorCountList(mp);
		
		// 取得站点下用户的已发信息
		mp.put("info_status", "8");
		Map<String, String> m1 = CmsCountDAO.getAuthorCountList(mp);
		
		// 取得站点下用户的已发信息 并且有图片的信息
		mp.put("is_pic", "1");
		Map<String, String> m2 = CmsCountDAO.getAuthorCountList(mp);
		
		List<CmsCountBean> ret = new ArrayList<CmsCountBean>();
		// 数据合并,将全部的统计和已发的统计合并到bean中
		for(String s : m.keySet()){
			CmsCountBean bean = new CmsCountBean();
			bean.setInputCount(Integer.parseInt(m.get(s))); // 设置取得的全部信息
			bean.setUser_name(s);
			if(m1.containsKey(s)){
				bean.setReleasedCount(Integer.parseInt(m1.get(s)));	// 已发布信息
			}
			if(m2.containsKey(s)){
				bean.setPicInfoCount(Integer.parseInt(m2.get(s)));	// 已发信息 并且有图片的信息
			}  
			bean.setReleaseRate();
			ret.add(bean);
		}
		// 排序
		Collections.sort(ret, new CntDescComparator());
		return ret;
	}
}

/**
 * 按日期统计时,列表的降序统计器
 * 仅用于对CmsCountBean进行排序的情况  
 * @author liqi
 */
class DescComparator implements Comparator<CmsCountBean>{

	public int compare(CmsCountBean o1, CmsCountBean o2) {
		int ret = 0 - o1.getTime().compareTo(o2.getTime());
		return ret;
	}
}

/**
 * 按录入人员统计时,列表的降序统计器
 * @author liqi
 *
 */
class CntDescComparator implements Comparator<CmsCountBean>{

	public int compare(CmsCountBean o1, CmsCountBean o2) {
		int ret = 0 - o1.getInputCount() + o2.getInputCount();
		return ret;
	}
}
