package com.deya.wcm.services.search.index.impl;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import com.deya.util.FormatUtil;
import com.deya.util.HtmlRegexpUtil;
import com.deya.wcm.bean.search.IndexBeanBsznInfo;
import com.deya.wcm.dao.search.IIndexInfoDao;
import com.deya.wcm.dao.search.impl.IndexBsznDaoImpl;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.db.IbatisSessionFactory;
import com.deya.wcm.services.search.index.IndexLuceneManager;
import com.deya.wcm.services.search.index.IndexService;


/**
 * <p>Title: 索引操作接口实现类--办事指南</p>
 * <p>Company: Cicro</p>
 * @author lisp
 * @version 1.0
 */
public class IndexBsznServiceImpl implements IndexService {

	static int s_count = 50;//每次从数据库中读出50条
	static IIndexInfoDao indexInfoDao = new IndexBsznDaoImpl();
	
	static{
		
	}

	/**
     * 往索引文件中追加site_id的全部索引
     * @param infos Hashtable  输入信息   如信息site_id {site_id:ABCDEFG}
     * @return boolean
     */
	public boolean appendALlDocument(Map mapSite) {
		IndexWriter indexWriter = null;
		SqlSession s = IbatisSessionFactory.getInstance().openSession();
		try{
			 int count = indexInfoDao.getInfoListBySiteIdCount(mapSite);
			 int page=count/s_count+1;
			 //System.out.println("appendALlDocument bszn count===" + count);
			 
			 //索引字段
			 Field typeField = new Field("type", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field typedField = new Field("typed", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field idField = new Field("id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field titleField = new Field("title", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field contentField = new Field("content", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field urlField = new Field("url", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field site_idField = new Field("site_id", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field bszn_typeField = new Field("bszn_type", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field bszn_orgField = new Field("bszn_org", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field bszn_objectField = new Field("bszn_object", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field publish_timeField = new Field("publish_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 //按站点删除用
			 Field site_id_delField = new Field("site_id_del", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field categoryIdField = new Field("categoryId", "",Field.Store.YES, Field.Index.ANALYZED);
			 
			 Field node_idField = new Field("node_id", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field model_idField = new Field("model_id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 
			 Field is_hostField = new Field("is_host", "",Field.Store.YES, Field.Index.ANALYZED);
			 
			 //得到indexWriter
			 indexWriter = IndexLuceneManager.getIndexModifier(false);
					
			 //循环信息列表
			 for(int k=0;k<page;k++){
	    		  int start_num = s_count*k;
	    		  mapSite.put("start_num", start_num);
	    		  mapSite.put("page_size", s_count);
	    		  mapSite.put("app_id","cms");
	    		  //List<Map> infoList = indexInfoDao.getInfoListBySiteIdPages(mapSite); 
	    		  List<Map> infoList = s.selectList(DBManager.getSqlNameByDataType("search.getBsznListBySiteIdPages"),mapSite);
	    		  
	    		  for(Map infoMap : infoList){
	    			  
	    			  //得到信息bean
	    			  IndexBeanBsznInfo indexBeanBsznInfo = getIndexBeanInfo(infoMap);
	    			  
	    			  //System.out.println("appendALlDocument id===" + indexBeanBsznInfo.getId());
	    			  
	    			  //Document对象不要重用，Field对象可以重用 以加快速度
	    			  Document doc = new Document();
	    			  if(indexBeanBsznInfo.getApp_id().equals("ggfw")){
	    				  typeField.setValue(indexBeanBsznInfo.getApp_id());
		    			  doc.add(typeField);
		    			  typedField.setValue(indexBeanBsznInfo.getApp_id());
		    			  doc.add(typedField);
	    			  }else{
	    				  typeField.setValue(indexBeanBsznInfo.getType());
		    			  doc.add(typeField);
		    			  typedField.setValue(indexBeanBsznInfo.getTyped());
		    			  doc.add(typedField);
	    			  }
	    			  idField.setValue(indexBeanBsznInfo.getId());
	    			  doc.add(idField);
	    			  titleField.setValue(indexBeanBsznInfo.getTitle());
	    			  doc.add(titleField);
	    			  contentField.setValue(indexBeanBsznInfo.getContent());
	    			  doc.add(contentField);
	    			  urlField.setValue(indexBeanBsznInfo.getUrl());
	    			  doc.add(urlField);
	    			  site_idField.setValue(indexBeanBsznInfo.getSite_id());
	    			  doc.add(site_idField);
	    			  
	    			  node_idField.setValue(indexBeanBsznInfo.getNode_id());
	    			  doc.add(node_idField);
	    			  
	    			  model_idField.setValue(indexBeanBsznInfo.getModel_id());
	    			  doc.add(model_idField);
	    			  
	    			  bszn_typeField.setValue(indexBeanBsznInfo.getBszn_type());
	    			  doc.add(bszn_typeField);
	    			  bszn_orgField.setValue(indexBeanBsznInfo.getBszn_org());
	    			  doc.add(bszn_orgField);
	    			  bszn_objectField.setValue(indexBeanBsznInfo.getBszn_object());
	    			  doc.add(bszn_objectField);
	    			  publish_timeField.setValue(indexBeanBsznInfo.getPublish_time());
	    			  doc.add(publish_timeField);
	    		      //按站点删除用  -- 如果用Field.Index.NOT_ANALYZED则必须是小写字母才能匹配
	    			  site_id_delField.setValue(indexBeanBsznInfo.getSite_id().toLowerCase());
	    			  doc.add(site_id_delField);
	    			  
	    			  categoryIdField.setValue(indexBeanBsznInfo.getCategoryId());
	    			  //System.out.println("appendALlDocument id:" + indexBeanBsznInfo.getId()+"     CategoryId:"+indexBeanBsznInfo.getCategoryId());
	    			  doc.add(categoryIdField);
	    			  
	    			  is_hostField.setValue(indexBeanBsznInfo.getIs_host());
	    			  doc.add(is_hostField);
	    			  
	    			  //添加一条条info的索引
	    			  IndexLuceneManager.AddDocument(indexWriter,doc);
	    			  
	    		  }
			 }
			 
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			//关闭indexWriter
			IndexLuceneManager.closeIndexWriter(indexWriter);
			s.close();
		}
		return true;
	}

    /**
     * 往索引文件中追加一条记录
     * @param infos Hashtable  输入信息,如信息id {id:110}
     * @return boolean
     */
	public boolean appendSingleDocument(Map infos) {
		//得到indexWriter
		IndexWriter indexWriter = null;
		SqlSession s = IbatisSessionFactory.getInstance().openSession();
		try{
			
			//得到indexWriter
			indexWriter = IndexLuceneManager.getIndexModifier(false);
			
			if(infos.get("id")!=null){
				int id = Integer.valueOf((String)infos.get("id"));
				//Map map = indexInfoDao.getInfoById(id); 
				Map mapC = new HashMap();
				mapC.put("id",id);
				Map map = (Map)s.selectOne(DBManager.getSqlNameByDataType("search.getBsznById"), mapC);
				
				if(map==null){
					return false;
				}
				//得到信息bean
				IndexBeanBsznInfo indexBeanBsznInfo = getIndexBeanInfo(map);
				  
				//索引字段
				Field typeField = new Field("type", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
				Field typedField = new Field("typed", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
				Field idField = new Field("id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
				Field titleField = new Field("title", "",Field.Store.YES, Field.Index.ANALYZED);
				Field contentField = new Field("content", "",Field.Store.YES, Field.Index.ANALYZED);
				Field urlField = new Field("url", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
				Field site_idField = new Field("site_id", "",Field.Store.YES, Field.Index.ANALYZED);
				Field bszn_typeField = new Field("bszn_type", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
				Field bszn_orgField = new Field("bszn_org", "",Field.Store.YES, Field.Index.ANALYZED);
				Field bszn_objectField = new Field("bszn_object", "",Field.Store.YES, Field.Index.ANALYZED);
				Field publish_timeField = new Field("publish_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
				//按站点删除用
				Field site_id_delField = new Field("site_id_del", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
	  			
				Field categoryIdField = new Field("categoryId", "",Field.Store.YES, Field.Index.ANALYZED);
				
				Field node_idField = new Field("node_id", "",Field.Store.YES, Field.Index.ANALYZED);
				
				Field model_idField = new Field("model_id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
				
				
				Field is_hostField = new Field("is_host", "",Field.Store.YES, Field.Index.ANALYZED);
				
	  			//Document对象不要重用，Field对象可以重用 以加快速度
	  			Document doc = new Document();
	  			if(indexBeanBsznInfo.getApp_id().equals("ggfw")){
  				  typeField.setValue(indexBeanBsznInfo.getApp_id());
	    			  doc.add(typeField);
	    			  typedField.setValue(indexBeanBsznInfo.getApp_id());
	    			  doc.add(typedField);
	  			 }else{
	  				  typeField.setValue(indexBeanBsznInfo.getType());
		    		  doc.add(typeField);
		    		  typedField.setValue(indexBeanBsznInfo.getTyped());
		    		  doc.add(typedField);
	  			 }
	  			idField.setValue(indexBeanBsznInfo.getId());
	  			doc.add(idField);
	  			titleField.setValue(indexBeanBsznInfo.getTitle());
	  			doc.add(titleField);
	  			contentField.setValue(indexBeanBsznInfo.getContent());
	  			doc.add(contentField);
	  			urlField.setValue(indexBeanBsznInfo.getUrl());
	  			doc.add(urlField);
	  			site_idField.setValue(indexBeanBsznInfo.getSite_id());
	  			doc.add(site_idField);
	  			
	  			node_idField.setValue(indexBeanBsznInfo.getNode_id());
  			    doc.add(node_idField);
	  			
  			  model_idField.setValue(indexBeanBsznInfo.getModel_id());
			  doc.add(model_idField);
  			    
	  			bszn_typeField.setValue(indexBeanBsznInfo.getBszn_type());
	  			doc.add(bszn_typeField);
	  			bszn_orgField.setValue(indexBeanBsznInfo.getBszn_org());
	  			doc.add(bszn_orgField);
	  			bszn_objectField.setValue(indexBeanBsznInfo.getBszn_object());
	  			doc.add(bszn_objectField);
	  			publish_timeField.setValue(indexBeanBsznInfo.getPublish_time());
	  			doc.add(publish_timeField);
	  		    //按站点删除用  -- 如果用Field.Index.NOT_ANALYZED则必须是小写字母才能匹配
  			    site_id_delField.setValue(indexBeanBsznInfo.getSite_id().toLowerCase());
  			    doc.add(site_id_delField);
  			  
  			    //System.out.println("appendALlDocument id:" + indexBeanBsznInfo.getId()+"     CategoryId:"+indexBeanBsznInfo.getCategoryId());
	  			categoryIdField.setValue(indexBeanBsznInfo.getCategoryId());
	  			doc.add(categoryIdField);
	  			
	  			is_hostField.setValue(indexBeanBsznInfo.getIs_host());
  			    doc.add(is_hostField);
	  			
  			    //添加一条条info的索引
  			    IndexLuceneManager.AddDocument(indexWriter,doc);
  			    
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			//关闭indexWriter
			IndexLuceneManager.closeIndexWriter(indexWriter);
			s.close();
		}
		return true;
	}

	/**
     * 通过site_id删除该类信息的全部索引
     * @param infos Hashtable  输入信息   如信息site_id {site_id:ABCDEFG}
     * @return boolean
     */
	public boolean deleteALlDocument(Map map) {
		//得到indexWriter
		IndexWriter indexWriter = null;
		try{
			if(map.get("site_id")!=null){
				indexWriter = IndexLuceneManager.getIndexModifier(false);
				
				Term term = new Term("site_id_del",((String)map.get("site_id")).toLowerCase());
				IndexLuceneManager.DeleteDocument(indexWriter,term);

			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			//关闭indexWriter
			IndexLuceneManager.closeIndexWriter(indexWriter);
		}
		return true;
	}

    /**
     * 删除索引文件中的一条记录
     * @param infos Hashtable  输入信息,如信息id {id:110}
     * @return boolean
     */
	public boolean deleteSingleDocument(Map infos) {
		//得到indexWriter
		IndexWriter indexWriter = null;
		try{
			if(infos.get("id")!=null){
				
				indexWriter = IndexLuceneManager.getIndexModifier(false);
				
				Term term = new Term("id",(String)infos.get("id"));
				IndexLuceneManager.DeleteDocument(indexWriter,term);
			
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			//关闭indexWriter
			IndexLuceneManager.closeIndexWriter(indexWriter);
		}
		return true;
	}
	
	//得到IndexBeanInfo对象
	public static IndexBeanBsznInfo getIndexBeanInfo(Map info){
		IndexBeanBsznInfo indexBeanBsznInfo = new IndexBeanBsznInfo();
		 try{
			  Map infoMap = new HashMap();
			  Iterator it = info.keySet().iterator();
			  while(it.hasNext()){
				    String key = (String)it.next();
				    Object object = (Object)info.get(key);
					String value = "";
					if(object instanceof java.math.BigDecimal){
						value = object.toString();
					}else if(object instanceof oracle.sql.CLOB){
						  oracle.sql.CLOB clob = (oracle.sql.CLOB)object;
//						  BufferedReader br = new BufferedReader(clob.getCharacterStream());
//				    	  String str = "";
//				    	  while((str=br.readLine()) != null){
//				    		  value = value.concat(str);
//				    	  }
						  if(clob != null){
				    		  value = clob.getSubString((long)1,(int)clob.length());
				    	  }
					}
					else{
						//value = (String)object; 
						value = String.valueOf(object); 
					}
				    infoMap.put(key.toLowerCase(),value);
			  }
			  indexBeanBsznInfo.setId(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("info_id")));
			  indexBeanBsznInfo.setTitle(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("title")));
			  indexBeanBsznInfo.setApp_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("app_id")));
			  StringBuffer sb = new StringBuffer();
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_bsjg"))); 
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_sxyj")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_bldx")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_bltj")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_blsx")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_bllc")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_gsfs")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_sfbz")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_sfyj")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_jdjc")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_zrzj")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_wszx")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_wsbl")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_ztcx")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_wsts")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_memo")));
			  indexBeanBsznInfo.setContent(HtmlRegexpUtil.filterHtml(sb.toString()));
			  indexBeanBsznInfo.setUrl(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("content_url")));
			  String site_id = com.deya.wcm.services.control.site.SiteAppRele.getSiteIDByAppID("cms");
			  indexBeanBsznInfo.setSite_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("site_id")));
			  
			  indexBeanBsznInfo.setNode_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("site_id")));
			  
			  indexBeanBsznInfo.setModel_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("model_id")));
			  
			  indexBeanBsznInfo.setBszn_type(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("gk_fwlb")));
			  indexBeanBsznInfo.setBszn_org(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("gk_bsjg")));
			  indexBeanBsznInfo.setBszn_object(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("gk_bldx")));
			  String time = ((String)infoMap.get("released_dtime"));  
			  time = time == null ? "" : time.replaceAll("-","").replaceAll(" ", "").replaceAll(":","");
			  indexBeanBsznInfo.setPublish_time(com.deya.util.FormatUtil.formatNullString(time)); 
			  indexBeanBsznInfo.setCategoryId(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("cat_id")));
			  indexBeanBsznInfo.setIs_host(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("is_host")));
			  return indexBeanBsznInfo;
		 }catch (Exception e) {  
			e.printStackTrace();
			return indexBeanBsznInfo;
	     }
		 
	}
	
	
	public static void main(String arr[]){
		IndexBsznServiceImpl impl = new IndexBsznServiceImpl();
		Map map = new HashMap();
		map.put("id","1111");
		impl.appendSingleDocument(map);
	}

}
