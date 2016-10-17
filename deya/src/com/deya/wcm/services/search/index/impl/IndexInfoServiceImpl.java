package com.deya.wcm.services.search.index.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import com.deya.util.HtmlRegexpUtil;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.search.IndexBeanInfo;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.search.IIndexInfoDao;
import com.deya.wcm.dao.search.impl.IndexInfoDaoImpl;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.db.IbatisSessionFactory;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.search.index.IndexLuceneManager;
import com.deya.wcm.services.search.index.IndexService;


/**
 * <p>Title: 索引操作接口实现类--info</p>
 * <p>Company: Cicro</p>
 * @author lisp
 * @version 1.0
 */
public class IndexInfoServiceImpl implements IndexService {

	static int s_count = 50;//每次从数据库中读出50条
	static IIndexInfoDao indexInfoDao = new IndexInfoDaoImpl();
	
	static{
		
	}

	/**
     * 往索引文件中追加site_id的全部索引
     * @param infos Hashtable  输入信息   如信息site_id {site_id:ABCDEFG}
     * @return boolean
     */
	public boolean appendALlDocument(Map mapSite) {
		
		appendALlDocumentLinkInfo(mapSite);
		
		IndexWriter indexWriter = null;
		SqlSession s = IbatisSessionFactory.getInstance().openSession();
		try{
			 int count = indexInfoDao.getInfoListBySiteIdCount(mapSite);
			 int page=count/s_count+1;
			 //System.out.println( mapSite + "appendALlDocument info count===" + count);
			 
			 //索引字段
			 Field typeField = new Field("type", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field typedField = new Field("typed", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field idField = new Field("id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field titleField = new Field("title", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field contentField = new Field("content", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field urlField = new Field("url", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field site_idField = new Field("site_id", "",Field.Store.YES, Field.Index.ANALYZED);
			
			 Field node_idField = new Field("node_id", "",Field.Store.YES, Field.Index.ANALYZED);
			 
			 Field model_idField = new Field("model_id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 
			 Field categoryIdField = new Field("categoryId", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field publish_timeField = new Field("publish_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 //按站点删除用
			 Field site_id_delField = new Field("site_id_del", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 
			 Field is_hostField = new Field("is_host", "",Field.Store.YES, Field.Index.ANALYZED);
			 
			 //得到indexWriter
			 indexWriter = IndexLuceneManager.getIndexModifier(false);
					
			 //循环信息列表
			 for(int k=0;k<page;k++){
	    		  int start_num = s_count*k;
	    		  mapSite.put("start_num", start_num);
	    		  mapSite.put("page_size", s_count);
	    		 
	    		  //由于取得Oracle的clob字段时 转换有问题 --- 修改
//	    		  List<Map> infoList = indexInfoDao.getInfoListBySiteIdPages(mapSite); 
	    		  List<Map> infoList = s.selectList(DBManager.getSqlNameByDataType("search.getInfoListBySiteIdPages"),mapSite);
	    		  
	    		  for(Map infoMap : infoList){
	    			  
	    			  //得到信息bean
	    			  IndexBeanInfo indexBeanInfo = getIndexBeanInfo(infoMap);
	    			  
	    			  //Document对象不要重用，Field对象可以重用 以加快速度
	    			  Document doc = new Document();
	    			  if(indexBeanInfo.getApp_id().equals("ggfw")){
	    				  typeField.setValue(indexBeanInfo.getApp_id());
		    			  doc.add(typeField);
		    			  typedField.setValue(indexBeanInfo.getApp_id());
		    			  doc.add(typedField);
	    			  }else{
		    			  typeField.setValue(indexBeanInfo.getType());
		    			  doc.add(typeField);
		    			  typedField.setValue(indexBeanInfo.getTyped());
		    			  doc.add(typedField);
	    			  }
	    			  idField.setValue(indexBeanInfo.getId());
	    			  doc.add(idField);
	    			  titleField.setValue(indexBeanInfo.getTitle());
	    			  doc.add(titleField);
	    			  contentField.setValue(indexBeanInfo.getContent());
	    			  doc.add(contentField);
	    			  urlField.setValue(indexBeanInfo.getUrl());
	    			  doc.add(urlField);
	    			  site_idField.setValue(indexBeanInfo.getSite_id());
	    			  doc.add(site_idField);
	    			  
	    			  node_idField.setValue(indexBeanInfo.getNode_id());
	    			  doc.add(node_idField);
	    			  
	    			  model_idField.setValue(indexBeanInfo.getModel_id());
	    			  doc.add(model_idField);
	    			  
	    			  //按站点删除用  -- 如果用Field.Index.NOT_ANALYZED则必须是小写字母才能匹配
	    			  site_id_delField.setValue(indexBeanInfo.getSite_id().toLowerCase());
	    			  doc.add(site_id_delField);
	    			  
	    			  categoryIdField.setValue(indexBeanInfo.getCategoryId());
	    			  //System.out.println("appendALlDocument id:" + indexBeanInfo.getId()+"     CategoryId:"+indexBeanInfo.getCategoryId());
	    			  doc.add(categoryIdField);
	    			  publish_timeField.setValue(indexBeanInfo.getPublish_time());
	    			  doc.add(publish_timeField);
	    			  
	    			  
	    			  is_hostField.setValue(indexBeanInfo.getIs_host());
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
     * 把该站点下的链接信息写进索引
     * @param infos Hashtable  输入信息   如信息site_id {site_id:ABCDEFG}
     * @return boolean
     */
	public boolean appendALlDocumentLinkInfo(Map mapSite) {
		IndexWriter indexWriter = null;
		SqlSession s = IbatisSessionFactory.getInstance().openSession();
		try{
			 
			 //索引字段
			 Field typeField = new Field("type", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field typedField = new Field("typed", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field idField = new Field("id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field titleField = new Field("title", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field contentField = new Field("content", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field urlField = new Field("url", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field site_idField = new Field("site_id", "",Field.Store.YES, Field.Index.ANALYZED);
			
			 Field node_idField = new Field("node_id", "",Field.Store.YES, Field.Index.ANALYZED);
			 
			 Field model_idField = new Field("model_id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 
			 Field categoryIdField = new Field("categoryId", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field publish_timeField = new Field("publish_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 //按站点删除用
			 Field site_id_delField = new Field("site_id_del", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 
			 Field is_hostField = new Field("is_host", "",Field.Store.YES, Field.Index.ANALYZED);
			 
			 //得到indexWriter
			 indexWriter = IndexLuceneManager.getIndexModifier(false);
					
//			 //循环信息列表
//			 for(int k=0;k<page;k++){
//	    		  int start_num = s_count*k;
//	    		  mapSite.put("start_num", start_num);
//	    		  mapSite.put("page_size", s_count);
	    		 
	    		  //由于取得Oracle的clob字段时 转换有问题 --- 修改
//	    		  List<Map> infoList = indexInfoDao.getInfoListBySiteIdPages(mapSite); 
	    		  //List<Map> infoList = s.selectList(DBManager.getSqlNameByDataType("search.getInfoListBySiteIdPages"),mapSite);
			      String site_id = (String)mapSite.get("site_id");
			      List<Map> infoList = PublicTableDAO.executeSearchSql("select i.id from cs_info i where i.site_id ='"+site_id+"' and i.info_status=8 and i.final_status=0 and i.model_id=12");
	    		  for(Map infoMap : infoList){
	    			  
	    			    Map map = null;
	    			    //判断是不是链接信息
	    			    String id = (String)(infoMap.get("id")+"");
						InfoBean infoBean = InfoBaseManager.getInfoById(id+"");
						if(infoBean.getModel_id()==12){
							map = new HashMap();
							map.put("info_id", infoBean.getInfo_id()+"");
							map.put("title", infoBean.getTitle()+"");
							map.put("app_id", infoBean.getApp_id()+"");
							map.put("info_content",infoBean.getTitle()+"");
							map.put("content_url", infoBean.getContent_url()+"");
							map.put("site_id", infoBean.getSite_id()+"");
							map.put("model_id", infoBean.getModel_id()+"");
							map.put("cat_id", infoBean.getCat_id()+"");
							map.put("is_host", infoBean.getIs_host()+"");
							map.put("released_dtime", infoBean.getReleased_dtime()+"");
						}else{
							return false;
						}
	    			  
	    			  //得到信息bean
	    			  IndexBeanInfo indexBeanInfo = getIndexBeanInfo(map);
	    			  
	    			  //Document对象不要重用，Field对象可以重用 以加快速度
	    			  Document doc = new Document();
	    			  if(indexBeanInfo.getApp_id().equals("ggfw")){
	    				  typeField.setValue(indexBeanInfo.getApp_id());
		    			  doc.add(typeField);
		    			  typedField.setValue(indexBeanInfo.getApp_id());
		    			  doc.add(typedField);
	    			  }else{
		    			  typeField.setValue(indexBeanInfo.getType());
		    			  doc.add(typeField);
		    			  typedField.setValue(indexBeanInfo.getTyped());
		    			  doc.add(typedField);
	    			  }
	    			  idField.setValue(indexBeanInfo.getId());
	    			  doc.add(idField);
	    			  titleField.setValue(indexBeanInfo.getTitle());
	    			  doc.add(titleField);
	    			  contentField.setValue(indexBeanInfo.getContent());
	    			  doc.add(contentField);
	    			  urlField.setValue(indexBeanInfo.getUrl());
	    			  doc.add(urlField);
	    			  site_idField.setValue(indexBeanInfo.getSite_id());
	    			  doc.add(site_idField);
	    			  
	    			  node_idField.setValue(indexBeanInfo.getNode_id());
	    			  doc.add(node_idField);
	    			  
	    			  model_idField.setValue(indexBeanInfo.getModel_id());
	    			  doc.add(model_idField);
	    			  
	    			  //按站点删除用  -- 如果用Field.Index.NOT_ANALYZED则必须是小写字母才能匹配
	    			  site_id_delField.setValue(indexBeanInfo.getSite_id().toLowerCase());
	    			  doc.add(site_id_delField);
	    			  
	    			  categoryIdField.setValue(indexBeanInfo.getCategoryId());
	    			  //System.out.println("appendALlDocumentLink id:" + indexBeanInfo.getId()+"     CategoryId:"+indexBeanInfo.getCategoryId());
	    			  doc.add(categoryIdField);
	    			  publish_timeField.setValue(indexBeanInfo.getPublish_time());
	    			  doc.add(publish_timeField);
	    			  
	    			  
	    			  is_hostField.setValue(indexBeanInfo.getIs_host());
	    			  doc.add(is_hostField);
	    			  
	    			  //添加一条条info的索引
	    			  IndexLuceneManager.AddDocument(indexWriter,doc);
	    			  
	    		  }
	//		 }
			 
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
				Map map = (Map)s.selectOne(DBManager.getSqlNameByDataType("search.getInfoById"), mapC);
				
				if(map==null){
					//判断是不是链接信息
					InfoBean infoBean = InfoBaseManager.getInfoById(id+"");
					if(infoBean.getModel_id()==12){
						map = new HashMap();
						map.put("info_id", infoBean.getInfo_id()+"");
						map.put("title", infoBean.getTitle()+"");
						map.put("app_id", infoBean.getApp_id()+"");
						map.put("info_content",infoBean.getTitle()+"");
						map.put("content_url", infoBean.getContent_url()+"");
						map.put("site_id", infoBean.getSite_id()+"");
						map.put("model_id", infoBean.getModel_id()+"");
						map.put("cat_id", infoBean.getCat_id()+"");
						map.put("is_host", infoBean.getIs_host()+"");
						map.put("released_dtime", infoBean.getReleased_dtime()+"");
					}else{
						return false;
					}
					
				}
				//得到信息bean
  			    IndexBeanInfo indexBeanInfo = getIndexBeanInfo(map);
				
  			    //索引字段
  				Field typeField = new Field("type", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				Field typedField = new Field("typed", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				Field idField = new Field("id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				Field titleField = new Field("title", "",Field.Store.YES, Field.Index.ANALYZED);
  				Field contentField = new Field("content", "",Field.Store.YES, Field.Index.ANALYZED);
  				Field urlField = new Field("url", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				Field site_idField = new Field("site_id", "",Field.Store.YES, Field.Index.ANALYZED);
  			    //按站点删除用
  				Field site_id_delField = new Field("site_id_del", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				Field categoryIdField = new Field("categoryId", "",Field.Store.YES, Field.Index.ANALYZED);
  				Field publish_timeField = new Field("publish_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  			    
  				Field node_idField = new Field("node_id", "",Field.Store.YES, Field.Index.ANALYZED);
  				
  				Field model_idField = new Field("model_id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				
  				Field is_hostField = new Field("is_host", "",Field.Store.YES, Field.Index.ANALYZED);
  				
  				//Document对象不要重用，Field对象可以重用 以加快速度
	  			Document doc = new Document();
	  			if(indexBeanInfo.getApp_id().equals("ggfw")){
	  				  typeField.setValue(indexBeanInfo.getApp_id());
	    			  doc.add(typeField);
	    			  typedField.setValue(indexBeanInfo.getApp_id());
	    			  doc.add(typedField);
	  			  }else{
	    			  typeField.setValue(indexBeanInfo.getType());
	    			  doc.add(typeField);
	    			  typedField.setValue(indexBeanInfo.getTyped());
	    			  doc.add(typedField);
	  			  }
	  			idField.setValue(indexBeanInfo.getId());
	  	        doc.add(idField);
	  			titleField.setValue(indexBeanInfo.getTitle());
	  			doc.add(titleField);
	  			contentField.setValue(indexBeanInfo.getContent());
	  			doc.add(contentField);
	  			urlField.setValue(indexBeanInfo.getUrl());
	  			doc.add(urlField);
	  			site_idField.setValue(indexBeanInfo.getSite_id());
	  			doc.add(site_idField);
	  			
	  			node_idField.setValue(indexBeanInfo.getNode_id());
	  			doc.add(node_idField);
	  			
	  			model_idField.setValue(indexBeanInfo.getModel_id());
  			    doc.add(model_idField);
	  			
	  		    //按站点删除用
  			    site_id_delField.setValue(indexBeanInfo.getSite_id().toLowerCase());
  			    doc.add(site_id_delField);
	  			
  			    //System.out.println("appendALlDocument id:" + indexBeanInfo.getId()+"     CategoryId:"+indexBeanInfo.getCategoryId());
	  			categoryIdField.setValue(indexBeanInfo.getCategoryId());
	  			doc.add(categoryIdField);
	  			publish_timeField.setValue(indexBeanInfo.getPublish_time());
	  			doc.add(publish_timeField);
  			  
	  			is_hostField.setValue(indexBeanInfo.getIs_host());
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
	public static IndexBeanInfo getIndexBeanInfo(Map info){
		 IndexBeanInfo indexBeanInfo = new IndexBeanInfo();
		 try{
			  //System.out.println("info-------" + info);
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
			  
			  indexBeanInfo.setId(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("info_id")));
			  indexBeanInfo.setTitle(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("title")));
			  indexBeanInfo.setApp_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("app_id")));
			  indexBeanInfo.setContent(HtmlRegexpUtil.filterHtml(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("info_content"))));
			  indexBeanInfo.setUrl(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("content_url")));
			  indexBeanInfo.setSite_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("site_id")));
			  
			  indexBeanInfo.setNode_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("site_id")));
			  
			  indexBeanInfo.setModel_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("model_id")));
			  
			  indexBeanInfo.setCategoryId(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("cat_id")));
			  
			  indexBeanInfo.setIs_host(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("is_host")));
			  
			  String time = ((String)infoMap.get("released_dtime"));
			  time = time == null ? "" : time.replaceAll("-","").replaceAll(" ", "").replaceAll(":","");
			  //System.out.println("com.deya.util.FormatUtil.formatNullString(time)===" + com.deya.util.FormatUtil.formatNullString(time));
			  indexBeanInfo.setPublish_time(com.deya.util.FormatUtil.formatNullString(time)); 
			  return indexBeanInfo;
		 }catch (Exception e) {
			e.printStackTrace();
			return indexBeanInfo;
	     }
		 
	}
	
	
	public static void main(String arr[]){
		IndexInfoServiceImpl impl = new IndexInfoServiceImpl();
		Map map = new HashMap();
		map.put("id","1111");
		impl.appendSingleDocument(map);
	}

}
