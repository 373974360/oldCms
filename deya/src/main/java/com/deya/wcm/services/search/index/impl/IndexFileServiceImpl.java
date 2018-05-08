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
import com.deya.wcm.bean.search.IndexBeanFile;
import com.deya.wcm.dao.search.IIndexInfoDao;
import com.deya.wcm.dao.search.impl.IndexFileDaoImpl;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.db.IbatisSessionFactory;
import com.deya.wcm.services.search.index.IndexLuceneManager;
import com.deya.wcm.services.search.index.IndexService;


/**
 * <p>Title: 索引操作接口实现类--info</p>
 * <p>Company: Cicro</p>
 * @author lisp
 * @version 1.0
 */
public class IndexFileServiceImpl implements IndexService {

	static int s_count = 50;//每次从数据库中读出50条
	static IIndexInfoDao indexInfoDao = new IndexFileDaoImpl();
	
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
			 //System.out.println("appendALlDocument file count===" + count);
			 
			 //索引字段
			 Field typeField = new Field("type", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field typedField = new Field("typed", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field idField = new Field("id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field titleField = new Field("title", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field contentField = new Field("content", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field urlField = new Field("url", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field site_idField = new Field("site_id", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field wnumberField = new Field("wnumber", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field codeField = new Field("code", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field publish_timeField = new Field("publish_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field c_timeField = new Field("c_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field take_timeField = new Field("take_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field over_timeField = new Field("over_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 Field object_wordsField = new Field("object_words", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field form_typeField = new Field("form_type", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field content_typeField = new Field("content_type", "",Field.Store.YES, Field.Index.ANALYZED);
			 Field publish_orgField = new Field("publish_org", "",Field.Store.YES, Field.Index.ANALYZED); 
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
	    		  //List<Map> infoList = indexInfoDao.getInfoListBySiteIdPages(mapSite); 
	    		  List<Map> infoList = s.selectList(DBManager.getSqlNameByDataType("search.getFileListBySiteIdPages"),mapSite);
	    		  
	    		  for(Map infoMap : infoList){
	    			  
	    			  //得到信息bean
	    			  IndexBeanFile indexBeanFile = getIndexBeanInfo(infoMap);
	    			  
	    			  //System.out.println("appendALlDocument id===" + indexBeanFile.getId());
	    			  
	    			  //Document对象不要重用，Field对象可以重用 以加快速度
	    			  Document doc = new Document();
	    			  if(indexBeanFile.getApp_id().equals("ggfw")){
	    				  typeField.setValue(indexBeanFile.getApp_id());
		    			  doc.add(typeField);
		    			  typedField.setValue(indexBeanFile.getApp_id());
		    			  doc.add(typedField);
	    			  }else{ 
		    			  typeField.setValue(indexBeanFile.getType());
		    			  doc.add(typeField);
		    			  typedField.setValue(indexBeanFile.getTyped());
		    			  doc.add(typedField);
	    		      }  
	    			  idField.setValue(indexBeanFile.getId());
	    			  doc.add(idField);
	    			  titleField.setValue(indexBeanFile.getTitle());
	    			  doc.add(titleField);
	    			  contentField.setValue(indexBeanFile.getContent());
	    			  doc.add(contentField);
	    			  urlField.setValue(indexBeanFile.getUrl());
	    			  doc.add(urlField);
	    			  site_idField.setValue(indexBeanFile.getSite_id());
	    			  doc.add(site_idField);
	    			  
	    			  node_idField.setValue(indexBeanFile.getNode_id());
	    			  doc.add(node_idField);
	    			  
	    			  model_idField.setValue(indexBeanFile.getModel_id());
	    			  doc.add(model_idField);
	    			  
	    			  wnumberField.setValue(indexBeanFile.getWnumber());
	    			  doc.add(wnumberField);
	    			  codeField.setValue(indexBeanFile.getCode());
	    			  doc.add(codeField);
	    			  publish_timeField.setValue(indexBeanFile.getPublish_time());
	    			  doc.add(publish_timeField);
	    			  
	    			  c_timeField.setValue(indexBeanFile.getC_time());
	    			  doc.add(c_timeField);
	    			  take_timeField.setValue(indexBeanFile.getTake_time());
	    			  doc.add(take_timeField);
	    			  over_timeField.setValue(indexBeanFile.getOver_time());
	    			  doc.add(over_timeField);
	    			  object_wordsField.setValue(indexBeanFile.getObject_words());
	    			  doc.add(object_wordsField);
	    			  form_typeField.setValue(indexBeanFile.getForm_type());
	    			  doc.add(form_typeField);
	    			  content_typeField.setValue(indexBeanFile.getContent_type());
	    			  doc.add(content_typeField);
	    			  publish_orgField.setValue(indexBeanFile.getPublish_org());
	    			  doc.add(publish_orgField);
	    			  //按站点删除用  -- 如果用Field.Index.NOT_ANALYZED则必须是小写字母才能匹配
	    			  site_id_delField.setValue(indexBeanFile.getSite_id().toLowerCase());
	    			  doc.add(site_id_delField);
	    			  
	    			  categoryIdField.setValue(indexBeanFile.getCategoryId());
	    			  //System.out.println("appendALlDocument id:" + indexBeanFile.getId()+"     CategoryId:"+indexBeanFile.getCategoryId());
	    			  doc.add(categoryIdField);
	    			  
	    			  is_hostField.setValue(indexBeanFile.getIs_host());
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
				Map map = (Map)s.selectOne(DBManager.getSqlNameByDataType("search.getInfoById"), mapC);
				
				if(map==null){
					return false;
				}
				//得到信息bean
  			    IndexBeanFile indexBeanFile = getIndexBeanInfo(map);

  			     //索引字段
  				 Field typeField = new Field("type", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				 Field typedField = new Field("typed", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				 Field idField = new Field("id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				 Field titleField = new Field("title", "",Field.Store.YES, Field.Index.ANALYZED);
  				 Field contentField = new Field("content", "",Field.Store.YES, Field.Index.ANALYZED);
  				 Field urlField = new Field("url", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				 Field site_idField = new Field("site_id", "",Field.Store.YES, Field.Index.ANALYZED);
  				 Field wnumberField = new Field("wnumber", "",Field.Store.YES, Field.Index.ANALYZED);
  				 Field codeField = new Field("code", "",Field.Store.YES, Field.Index.ANALYZED);
  				 Field publish_timeField = new Field("publish_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				 Field c_timeField = new Field("c_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				 Field take_timeField = new Field("take_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				 Field over_timeField = new Field("over_time", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
  				 Field object_wordsField = new Field("object_words", "",Field.Store.YES, Field.Index.ANALYZED);
  				 Field form_typeField = new Field("form_type", "",Field.Store.YES, Field.Index.ANALYZED);
  				 Field content_typeField = new Field("content_type", "",Field.Store.YES, Field.Index.ANALYZED);
  				 Field publish_orgField = new Field("publish_org", "",Field.Store.YES, Field.Index.ANALYZED); 
  				 //按站点删除用
 				 Field site_id_delField = new Field("site_id_del", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
 				 
 				 Field categoryIdField = new Field("categoryId", "",Field.Store.YES, Field.Index.ANALYZED);
 				 
 				 Field node_idField = new Field("node_id", "",Field.Store.YES, Field.Index.ANALYZED);
 				 
 				 Field model_idField = new Field("model_id", "",Field.Store.YES, Field.Index.NOT_ANALYZED);
 				
 				Field is_hostField = new Field("is_host", "",Field.Store.YES, Field.Index.ANALYZED);
  				  //Document对象不要重用，Field对象可以重用 以加快速度
	   			  Document doc = new Document();
		   		  if(indexBeanFile.getApp_id().equals("ggfw")){
	  				  typeField.setValue(indexBeanFile.getApp_id());
	    			  doc.add(typeField);
	    			  typedField.setValue(indexBeanFile.getApp_id());
	    			  doc.add(typedField);
		  		  }else{ 
	    			  typeField.setValue(indexBeanFile.getType());
	    			  doc.add(typeField);
	    			  typedField.setValue(indexBeanFile.getTyped());
	    			  doc.add(typedField);
		  		  }
	   			  idField.setValue(indexBeanFile.getId());
	   			  doc.add(idField);
	   			  titleField.setValue(indexBeanFile.getTitle());
	   			  doc.add(titleField);
	   			  contentField.setValue(indexBeanFile.getContent());
	   			  doc.add(contentField);
	   			  urlField.setValue(indexBeanFile.getUrl());
	   			  doc.add(urlField);
	   			  site_idField.setValue(indexBeanFile.getSite_id());
	   			  doc.add(site_idField);
	   			  
	   			  node_idField.setValue(indexBeanFile.getNode_id());
	   			  doc.add(node_idField);
	   			  
	   			  model_idField.setValue(indexBeanFile.getModel_id());
  			      doc.add(model_idField);
	   			  
	   			  wnumberField.setValue(indexBeanFile.getWnumber());
	   			  doc.add(wnumberField);
	   			  codeField.setValue(indexBeanFile.getCode());
	   			  doc.add(codeField);
	   			  publish_timeField.setValue(indexBeanFile.getPublish_time());
	   			  doc.add(publish_timeField);
	   			  
	   			  c_timeField.setValue(indexBeanFile.getC_time());
	   			  doc.add(c_timeField);
	   			  take_timeField.setValue(indexBeanFile.getTake_time());
	   			  doc.add(take_timeField);
	   			  over_timeField.setValue(indexBeanFile.getOver_time());
	   			  doc.add(over_timeField);
	   			  object_wordsField.setValue(indexBeanFile.getObject_words());
	   			  doc.add(object_wordsField);
	   			  form_typeField.setValue(indexBeanFile.getForm_type());
	   			  doc.add(form_typeField);
	   			  content_typeField.setValue(indexBeanFile.getContent_type());
	   			  doc.add(content_typeField);
	   			  publish_orgField.setValue(indexBeanFile.getPublish_org());
	   			  doc.add(publish_orgField);
	   			  //按站点删除用  -- 如果用Field.Index.NOT_ANALYZED则必须是小写字母才能匹配
	  			  site_id_delField.setValue(indexBeanFile.getSite_id().toLowerCase());
	  			  doc.add(site_id_delField);
  			  
	  			  //System.out.println("appendALlDocument id:" + indexBeanFile.getId()+"     CategoryId:"+indexBeanFile.getCategoryId());
	  			  categoryIdField.setValue(indexBeanFile.getCategoryId());
	  			  doc.add(categoryIdField);
	  			
	  			is_hostField.setValue(indexBeanFile.getIs_host());
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
	public static IndexBeanFile getIndexBeanInfo(Map info){
		IndexBeanFile indexBeanFile = new IndexBeanFile();
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
			  indexBeanFile.setId(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("info_id")));
			  indexBeanFile.setTitle(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("title")));
			  indexBeanFile.setApp_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("app_id")));
			  indexBeanFile.setContent(HtmlRegexpUtil.filterHtml(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("gk_content"))));
			  indexBeanFile.setUrl(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("content_url")));
			  String site_id = com.deya.wcm.services.control.site.SiteAppRele.getSiteIDByAppID("zwgk");
			  indexBeanFile.setSite_id(site_id); 
			  
			  indexBeanFile.setNode_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("site_id")));

			  indexBeanFile.setModel_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("model_id")));
			  
			  indexBeanFile.setWnumber(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("doc_no")));
			  indexBeanFile.setCode(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("gk_index")));
			  String time = ((String)infoMap.get("released_dtime"));
			  time = time == null ? "" : time.replaceAll("-","").replaceAll(" ", "").replaceAll(":","");
			  indexBeanFile.setPublish_time(com.deya.util.FormatUtil.formatNullString(time)); 
			  time = ((String)infoMap.get("generate_dtime")); 
			  time = time == null ? "" : time.replaceAll("-","").replaceAll(" ", "").replaceAll(":","");
			  indexBeanFile.setC_time(com.deya.util.FormatUtil.formatNullString(time)); 
			  time = ((String)infoMap.get("effect_dtime"));
			  time = time == null ? "" : time.replaceAll("-","").replaceAll(" ", "").replaceAll(":","");
			  indexBeanFile.setTake_time(com.deya.util.FormatUtil.formatNullString(time)); 
			  time = ((String)infoMap.get("aboli_dtime"));
			  time = time == null ? "" : time.replaceAll("-","").replaceAll(" ", "").replaceAll(":","");
			  indexBeanFile.setOver_time(com.deya.util.FormatUtil.formatNullString(time)); 
			  indexBeanFile.setObject_words(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("topic_key")));
			  indexBeanFile.setForm_type(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("theme_name")));
			  indexBeanFile.setContent_type(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("topic_name")));
			  indexBeanFile.setPublish_org(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("gk_input_dept")));
			  indexBeanFile.setCategoryId(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("cat_id")));
			  indexBeanFile.setIs_host(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("is_host")));
			  return indexBeanFile; 
		 }catch (Exception e) {
			e.printStackTrace();
			return indexBeanFile;
	     }
		 
	}

}
