package com.deya.wcm.services.cms.info;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.deya.util.labelUtil.AutoPagerHandl;
import com.deya.wcm.bean.cms.info.ArticleBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.cms.info.PicBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.cms.info.ModelUtilDAO;
import com.deya.wcm.services.model.services.BeanToMapUtil;
import com.deya.wcm.services.model.services.InfoCustomService;
import com.deya.wcm.services.model.services.MapManager;

/**
 * 内容模型通用处理类，包括文章模型，通用图组新闻，通用视频新闻，用户自定义字段等
 * @author liqi
 *
 */
public class ModelUtil {
	
	/**
	 * 内容模型添加处理
	 * @param ob	具体模型的Bean对象
	 * @param model_name	具体模型的名称
	 * @param stl
	 * @return
	 */
	public static boolean insert(Object ob, String model_name, SettingLogsBean stl)
	{
		try{
			Map<String, String> mp = ModelConfig.getModelMap(model_name);
			
			System.out.println("insert ---- model_name :: " + model_name);
			
			setPageCountInObject(ob,model_name);
			
			String addSql = "";
			if(mp!=null){
				addSql = mp.get("AddSQL");
			}
			if(ModelUtilDAO.addModel(ob,addSql,model_name, stl))
			{
				//如果ob是java.util.HashMap的则说明 是自定义表的数据  不处理
				if(ob instanceof java.util.HashMap){
					return true;
				}
				InfoBean info = (InfoBean)ob;
				//信息状态的逻辑判断在InfoDAO.changeInfoStatus中，需要调用一下
				InfoBaseManager.changeInfoStatus(info);
				
				if(info.getInfo_status() == 8){//发布后要处理的事情			
						InfoPublishManager.publishAfterEvent(info);	
				}
				return true;
			}else
				return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 内容模型更改处理
	 * @param ob	具体模型的Bean对象
	 * @param model_name	具体模型的名称
	 * @param stl
	 * @return
	 */
	public static boolean update(Object ob, String model_name, SettingLogsBean stl)
	{
		try{
			Map<String, String> mp = ModelConfig.getModelMap(model_name);
			setPageCountInObject(ob,model_name);
			
			String UpdateSQL = "";
			if(mp!=null){ 
				UpdateSQL = mp.get("UpdateSQL");
			}
			if(ModelUtilDAO.updateModel(ob, UpdateSQL,model_name, stl))
			{
				InfoBean info = (InfoBean)ob;
				InfoBaseManager.changeInfoStatus(info);
				if(info.getInfo_status() == 8){//发布后要处理的事情				
					InfoPublishManager.publishAfterEvent(info);	
				} 
				return true;
			}else
				return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static void setPageCountInObject(Object ob,String model_name)
	{
		//得到是否有自动翻页的选项，如果有，需要获取到总页数的信息
		if((InfoBaseManager.ARTICLE_MODEL_ENAME.equals(model_name) || InfoBaseManager.GKTYGS_MODEL_ENAME.equals(model_name)))
		{
			try {
				String is_am_tupage = BeanUtils.getProperty(ob, "is_am_tupage");
				if("1".equals(is_am_tupage))
				{
					String tupage_num = BeanUtils.getProperty(ob, "tupage_num"); //翻页字数
					String item_name = "";
					if(InfoBaseManager.ARTICLE_MODEL_ENAME.equals(model_name))
						item_name = "info_content";
					if(InfoBaseManager.GKTYGS_MODEL_ENAME.equals(model_name))
						item_name = "gk_content";
					
					String content = BeanUtils.getProperty(ob, item_name);
					
					String page_count = AutoPagerHandl.splitContent(content, 0, Integer.parseInt(tupage_num));
					BeanUtils.setProperty(ob, "page_count", page_count);
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
		}
	}
	
	/**
	 * 根据站点ID，信息ID取得模型信息
	 * 返回一个Object对象，请在具体调用后根据情况强转成相应的类型
	 * @param infoId	信息ID
	 * @param siteId	站点ID
	 * @param model_name	具体模型的名称
	 * @return	取得的Object对象
	 */
	public static Object select(String infoId, String siteId, String model_name)
	{//这里site_id未用上
		try{
			Map<String, String> mp = ModelConfig.getModelMap(model_name);	
			String SelectSQL = "";
			if(mp!=null){
				SelectSQL = mp.get("SelectSQL");
				return ModelUtilDAO.selectModel(infoId, siteId, SelectSQL);
			}else{
				System.out.println("ModelUtil.java----读取自定义表的内容----");//李苏培加
				
				//得到一般信息cs_info
				InfoBean infoBean = InfoCustomService.getArticle(infoId);
				Map mapInfo = BeanToMapUtil.convertBean(infoBean);
				
				String model_id = String.valueOf(infoBean.getModel_id());
				Map mapCustom = (Map)InfoCustomService.getCustomInfoMap(model_id, infoId);
				
				//如果是公开信息得到公开信息表cs_gk_info
				if(infoBean.getApp_id().equals("zwgk")){
					Map gkInfo = InfoCustomService.getGKInfo(infoId);
					mapCustom.putAll(gkInfo);
				}
				//System.out.println("mapCustom11 :: " + mapCustom);
				if(mapCustom!=null && mapCustom.keySet().size()>0){
					mapCustom.putAll(mapInfo);
					mapCustom = MapManager.mapKeyValueToLow(mapCustom);
					//System.out.println("mapCustom22 :: " + mapCustom);
					return  mapCustom;
				}else{
					return  null;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			return  null;
		}
		
		
	}	
	
	
	public static void main(String[] args)
	{
		PicBean pb = (PicBean)select("1150","11111ddd","pic");
		System.out.println(pb.getPic_content()+"----");
	}
}