package com.deya.wcm.services.search.index.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import com.deya.util.FormatUtil;
import com.deya.util.HtmlRegexpUtil;
import com.deya.wcm.bean.search.IndexBeanInfo;
import com.deya.wcm.bean.search.InfoPic;
import com.deya.wcm.dao.search.IIndexInfoPicDao;
import com.deya.wcm.dao.search.impl.IndexPicDaoImpl;
import com.deya.wcm.services.search.index.IndexLuceneManager;
import com.deya.wcm.services.search.index.IndexService;


/**
 * <p>Title: 索引操作接口实现类--info</p>
 * <p>Company: Cicro</p>
 * @author lisp
 * @version 1.0
 */
public class IndexPicServiceImpl implements IndexService {

	static int s_count = 50;//每次从数据库中读出50条
	static IIndexInfoPicDao indexInfoDao = new IndexPicDaoImpl();
	
	static{
		
	}

	/**
     * 往索引文件中追加site_id的全部索引
     * @param infos Hashtable  输入信息   如信息site_id {site_id:ABCDEFG}
     * @return boolean
     */
	public boolean appendALlDocument(Map mapSite) {
		IndexWriter indexWriter = null;
		try{
			 int count = indexInfoDao.getInfoListBySiteIdCount(mapSite);
			 int page=count/s_count+1;
			 //System.out.println("appendALlDocument pic count===" + count);
			 
			 //索引字段
			 Field typeField = new Field("type", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field typedField = new Field("typed", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field idField = new Field("id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field titleField = new Field("title", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field contentField = new Field("content", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field urlField = new Field("url", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field site_idField = new Field("site_id", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field categoryIdField = new Field("categoryId", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field publish_timeField = new Field("publish_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 //按站点删除用
			 Field site_id_delField = new Field("site_id_del", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 
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
	    		  //List<Map> infoList = indexInfoDao.getInfoListBySiteIdPages(mapSite); 
	    		  List<IndexBeanInfo> infoList = indexInfoDao.getInfoListBySiteIdPages(mapSite); 
	    		  
	    		  for(IndexBeanInfo infoMap : infoList){
	    			  
	    			  //如果信息表的信息没有关联图片信息 则不创建索引
	    			  List<Map> infoListMap = indexInfoDao.getInfosById(Integer.valueOf(infoMap.getId()));  
	    			  if(infoListMap==null || infoListMap.size()<1){
	    				  continue;
	    			  }  
	    			  
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
		try{
			
			//得到indexWriter
			indexWriter = IndexLuceneManager.getIndexModifier(false);
			
			if(infos.get("id")!=null){
				int id = Integer.valueOf((String)infos.get("id"));
				
				//如果信息表的信息没有关联图片信息 则不创建索引
  			    List<Map> infoListMap = indexInfoDao.getInfosById(Integer.valueOf(id));  
  			    if(infoListMap==null || infoListMap.size()<1){
  			    	return false;
  			    }
				
				IndexBeanInfo infoMap = indexInfoDao.getInfoById(id); 
				if(infoMap==null){
					return false;
				}  
				//得到信息bean
  			    IndexBeanInfo indexBeanInfo = getIndexBeanInfo(infoMap); 
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
	public static IndexBeanInfo getIndexBeanInfo(IndexBeanInfo info){
		 IndexBeanInfo indexBeanInfo = new IndexBeanInfo();
		 try{
//			 System.out.println("info=======" + info);
//			  Map infoMap = new HashMap();
//			  Iterator it = info.keySet().iterator();
//			  while(it.hasNext()){
//				    String key = (String)it.next();
//				    Object object = (Object)info.get(key);
//					String value = "";
//					if(object instanceof java.math.BigDecimal){
//						value = object.toString();
//					}else if(object instanceof oracle.sql.CLOB){
//						  oracle.sql.CLOB clob = (oracle.sql.CLOB)object;
//						  BufferedReader br = new BufferedReader(clob.getCharacterStream());
//				    	  String str = "";
//				    	  while((str=br.readLine()) != null){
//				    		  value = value.concat(str);
//				    	  }
//					}
//					else{
//						//value = (String)object; 
//						value = String.valueOf(object); 
//					}
//				    infoMap.put(key.toLowerCase(),value);
//			  }
			  indexBeanInfo.setId(com.deya.util.FormatUtil.formatNullString((String)info.getId()));
			  indexBeanInfo.setTitle(com.deya.util.FormatUtil.formatNullString((String)info.getTitle()));
			  indexBeanInfo.setApp_id(com.deya.util.FormatUtil.formatNullString((String)info.getApp_id()));
			  indexBeanInfo.setUrl(com.deya.util.FormatUtil.formatNullString((String)info.getUrl()));
			  indexBeanInfo.setSite_id(com.deya.util.FormatUtil.formatNullString((String)info.getSite_id()));
			  
			  indexBeanInfo.setNode_id(com.deya.util.FormatUtil.formatNullString((String)info.getSite_id()));
			  indexBeanInfo.setModel_id(com.deya.util.FormatUtil.formatNullString(""+info.getModel_id()));
			  
			  indexBeanInfo.setCategoryId(com.deya.util.FormatUtil.formatNullString((String)info.getCategoryId()));
			  String time = ((String)info.getPublish_time());
			  time = time == null ? "" : time.replaceAll("-","").replaceAll(" ", "").replaceAll(":","");
			  //System.out.println("com.deya.util.FormatUtil.formatNullString(time)===" + com.deya.util.FormatUtil.formatNullString(time));
			  indexBeanInfo.setPublish_time(com.deya.util.FormatUtil.formatNullString(time));
			  List<InfoPic> infoPics = info.getInfoPics();
			  StringBuffer sb = new StringBuffer();
			  for(InfoPic pic : infoPics){
				  if(pic!=null){
					  //System.out.println("         " + pic.getPic_title());
					  sb.append(FormatUtil.formatNullString(pic.getPic_title())+FormatUtil.formatNullString(pic.getPic_content()));
				  }
			  }
			  indexBeanInfo.setContent(HtmlRegexpUtil.filterHtml(com.deya.util.FormatUtil.formatNullString((String)sb.toString())));
			  
			  indexBeanInfo.setIs_host(com.deya.util.FormatUtil.formatNullString((String)info.getIs_host()));
			  
			  return indexBeanInfo;
		 }catch (Exception e) {
			e.printStackTrace();
			return indexBeanInfo;
	     }
		 
	}
	
	
	public static void main(String arr[]){
		IndexPicServiceImpl impl = new IndexPicServiceImpl();
		Map map = new HashMap();
		map.put("id","1111");
		impl.appendSingleDocument(map);
	}

}
