package com.deya.wcm.services.model.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.cms.info.ArticleBean;
import com.deya.wcm.bean.cms.info.GKInfoBean;
import com.deya.wcm.bean.system.formodel.ModelBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.services.model.Fields;
import com.deya.wcm.services.system.formodel.ModelManager;


/**
 * 自定义数据操作处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 自定义数据操作处理类</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class InfoCustomRPC {

	
	//添加自定义表单的数据
	public static boolean addInfoCuston(String model_id,Map map){
		return InfoCustomService.addInfoCuston(model_id,map);
	}
	
	//修改自定义表单的数据
	public static boolean updateInfoCuston(String model_id,Map map){
		return InfoCustomService.updateInfoCuston(model_id,map);
	}
	
	//通过信息id得到cs_info主信息
	public static ArticleBean getArticle(String infoId){
		return InfoCustomService.getArticle(infoId);
	}
	
	//通过信息id得到用户自定义数据
	public static Map getCustomInfoMap(String model_id,String info_id){
		return InfoCustomService.getCustomInfoMap(model_id, info_id);
	}
	
	//修改引用此信息的信息
	public static boolean updateQuoteInfoCustom(ArticleBean articleBean, Map cusBean,String model_ename,HttpServletRequest request){
		return InfoCustomService.updateQuoteInfoCustom(articleBean, cusBean, model_ename, request);
	}
	
	//通过信息id得到cs_info主信息
	public static Map getGKInfo(String infoId){
		return InfoCustomService.getGKInfo(infoId);
	}
	
	//修改引用此信息的信息 -- 公开用
	public static boolean updateQuoteInfoCustomGk(GKInfoBean gkInfoBean, Map cusBean,String model_ename,HttpServletRequest request){
		return InfoCustomService.updateQuoteInfoCustomGk(gkInfoBean, cusBean, model_ename, request);
	}
}
