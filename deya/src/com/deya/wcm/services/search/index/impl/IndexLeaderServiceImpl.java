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

import com.deya.util.FormatUtil;
import com.deya.util.HtmlRegexpUtil;
import com.deya.wcm.bean.search.IndexBeanXxgkInfo;
import com.deya.wcm.dao.search.IIndexInfoDao;
import com.deya.wcm.dao.search.impl.IndexLeaderDaoImpl;
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
public class IndexLeaderServiceImpl implements IndexService {

	static int s_count = 50;//每次从数据库中读出50条
	static IIndexInfoDao indexInfoDao = new IndexLeaderDaoImpl();
	
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
			 //System.out.println("appendALlDocument xxgk count===" + count);
			 
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
	    		  List<Map> infoList = s.selectList(DBManager.getSqlNameByDataType("search.getLeaderListBySiteIdPages"),mapSite);
	    		  
	    		  for(Map infoMap : infoList){
	    			  
	    			  //得到信息bean
	    			  IndexBeanXxgkInfo indexBeanXxgk = getIndexBeanInfo(infoMap);
	    			  
	    			  //System.out.println("appendALlDocument id===" + indexBeanXxgk.getId());
	    			  
	    			  //Document对象不要重用，Field对象可以重用 以加快速度
	    			  Document doc = new Document();
	    			  if(indexBeanXxgk.getApp_id().equals("ggfw")){
	    				  typeField.setValue(indexBeanXxgk.getApp_id());
		    			  doc.add(typeField);
		    			  typedField.setValue(indexBeanXxgk.getApp_id());
		    			  doc.add(typedField);
	    			  }else{
		    			  typeField.setValue(indexBeanXxgk.getType());
		    			  doc.add(typeField);
		    			  typedField.setValue(indexBeanXxgk.getTyped());
		    			  doc.add(typedField);
	    			  }
	    			  idField.setValue(indexBeanXxgk.getId());
	    			  doc.add(idField);
	    			  titleField.setValue(indexBeanXxgk.getTitle());
	    			  doc.add(titleField);
	    			  contentField.setValue(indexBeanXxgk.getContent());
	    			  doc.add(contentField);
	    			  urlField.setValue(indexBeanXxgk.getUrl());
	    			  doc.add(urlField);
	    			  site_idField.setValue(indexBeanXxgk.getSite_id());
	    			  doc.add(site_idField);
	    			  
	    			  node_idField.setValue(indexBeanXxgk.getNode_id());
	    			  doc.add(node_idField);
	    			  
	    			  model_idField.setValue(indexBeanXxgk.getModel_id());
	    			  doc.add(model_idField);
	    			  
	    			  wnumberField.setValue(indexBeanXxgk.getWnumber());
	    			  doc.add(wnumberField);
	    			  codeField.setValue(indexBeanXxgk.getCode());
	    			  doc.add(codeField);
	    			  publish_timeField.setValue(indexBeanXxgk.getPublish_time());
	    			  doc.add(publish_timeField);
	    			  
	    			  c_timeField.setValue(indexBeanXxgk.getC_time());
	    			  doc.add(c_timeField);
	    			  take_timeField.setValue(indexBeanXxgk.getTake_time());
	    			  doc.add(take_timeField);
	    			  over_timeField.setValue(indexBeanXxgk.getOver_time());
	    			  doc.add(over_timeField);
	    			  object_wordsField.setValue(indexBeanXxgk.getObject_words());
	    			  doc.add(object_wordsField);
	    			  form_typeField.setValue(indexBeanXxgk.getForm_type());
	    			  doc.add(form_typeField);
	    			  content_typeField.setValue(indexBeanXxgk.getContent_type());
	    			  doc.add(content_typeField);
	    			  publish_orgField.setValue(indexBeanXxgk.getPublish_org());
	    			  doc.add(publish_orgField);
	    			  //按站点删除用  -- 如果用Field.Index.NOT_ANALYZED则必须是小写字母才能匹配
	    			  site_id_delField.setValue(indexBeanXxgk.getSite_id().toLowerCase());
	    			  doc.add(site_id_delField);
	    			  
	    			  categoryIdField.setValue(indexBeanXxgk.getCategoryId());
	    			  //System.out.println("appendALlDocument id:" + indexBeanXxgk.getId()+"     CategoryId:"+indexBeanXxgk.getCategoryId());
	    			  doc.add(categoryIdField);
	    			  
	    			  is_hostField.setValue(indexBeanXxgk.getIs_host());
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
				Map map = (Map)s.selectOne(DBManager.getSqlNameByDataType("search.getLeaderById"), mapC);
				
				if(map==null){
					return false;
				}
				//得到信息bean
  			    IndexBeanXxgkInfo indexBeanXxgk = getIndexBeanInfo(map);

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
	   			  if(indexBeanXxgk.getApp_id().equals("ggfw")){
  				      typeField.setValue(indexBeanXxgk.getApp_id());
	    			  doc.add(typeField);
	    			  typedField.setValue(indexBeanXxgk.getApp_id());
	    			  doc.add(typedField);
	  			  }else{
	    			  typeField.setValue(indexBeanXxgk.getType());
	    			  doc.add(typeField);
	    			  typedField.setValue(indexBeanXxgk.getTyped());
	    			  doc.add(typedField);
	  			  } 
	   			  idField.setValue(indexBeanXxgk.getId());
	   			  doc.add(idField);
	   			  titleField.setValue(indexBeanXxgk.getTitle());
	   			  doc.add(titleField);
	   			  contentField.setValue(indexBeanXxgk.getContent());
	   			  doc.add(contentField);
	   			  urlField.setValue(indexBeanXxgk.getUrl());
	   			  doc.add(urlField);
	   			  site_idField.setValue(indexBeanXxgk.getSite_id());
	   			  doc.add(site_idField);
	   			  
	   			  node_idField.setValue(indexBeanXxgk.getNode_id());
	   			  doc.add(node_idField);
	   			  
	   			  model_idField.setValue(indexBeanXxgk.getModel_id());
  			      doc.add(model_idField);
	   			  
	   			  wnumberField.setValue(indexBeanXxgk.getWnumber());
	   			  doc.add(wnumberField);
	   			  codeField.setValue(indexBeanXxgk.getCode());
	   			  doc.add(codeField);
	   			  publish_timeField.setValue(indexBeanXxgk.getPublish_time());
	   			  doc.add(publish_timeField);
	   			  
	   			  c_timeField.setValue(indexBeanXxgk.getC_time());
	   			  doc.add(c_timeField);
	   			  take_timeField.setValue(indexBeanXxgk.getTake_time());
	   			  doc.add(take_timeField);
	   			  over_timeField.setValue(indexBeanXxgk.getOver_time());
	   			  doc.add(over_timeField);
	   			  object_wordsField.setValue(indexBeanXxgk.getObject_words());
	   			  doc.add(object_wordsField);
	   			  form_typeField.setValue(indexBeanXxgk.getForm_type());
	   			  doc.add(form_typeField);
	   			  content_typeField.setValue(indexBeanXxgk.getContent_type());
	   			  doc.add(content_typeField);
	   			  publish_orgField.setValue(indexBeanXxgk.getPublish_org());
	   			  doc.add(publish_orgField);
	   			  //按站点删除用  -- 如果用Field.Index.NOT_ANALYZED则必须是小写字母才能匹配
    			  site_id_delField.setValue(indexBeanXxgk.getSite_id().toLowerCase());
    			  doc.add(site_id_delField);
    			  
    			  //System.out.println("appendALlDocument id:" + indexBeanXxgk.getId()+"     CategoryId:"+indexBeanXxgk.getCategoryId());
	  			  categoryIdField.setValue(indexBeanXxgk.getCategoryId());
	  			  doc.add(categoryIdField);
	  			  
	  			 is_hostField.setValue(indexBeanXxgk.getIs_host());
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
				
				Term term = new Term("site_id",((String)map.get("site_id")).toLowerCase());
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
	public static IndexBeanXxgkInfo getIndexBeanInfo(Map info){
		IndexBeanXxgkInfo indexBeanXxgk = new IndexBeanXxgkInfo();
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
			  indexBeanXxgk.setId(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("info_id")));
			  indexBeanXxgk.setTitle(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("title")));
			  indexBeanXxgk.setApp_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("app_id")));
			  StringBuffer sb = new StringBuffer();
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_ldzw")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_grjl")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_gzfg")));
			  sb.append(FormatUtil.formatNullString((String)infoMap.get("gk_dz")));
			  indexBeanXxgk.setContent(HtmlRegexpUtil.filterHtml(sb.toString())); 
			  indexBeanXxgk.setUrl(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("content_url")));
			  String site_id = com.deya.wcm.services.control.site.SiteAppRele.getSiteIDByAppID("zwgk");
			  indexBeanXxgk.setSite_id(site_id);  
			  
			  indexBeanXxgk.setNode_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("site_id")));
			  indexBeanXxgk.setModel_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("model_id")));
			  
			  indexBeanXxgk.setWnumber(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("doc_no")));
			  indexBeanXxgk.setCode(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("gk_index")));
			  String time = ((String)infoMap.get("released_dtime"));
			  time = time == null ? "" : time.replaceAll("-","").replaceAll(" ", "").replaceAll(":","");
			  indexBeanXxgk.setPublish_time(com.deya.util.FormatUtil.formatNullString(time)); 
			  time = ((String)infoMap.get("generate_dtime"));
			  time = time == null ? "" : time.replaceAll("-","").replaceAll(" ", "").replaceAll(":","");
			  indexBeanXxgk.setC_time(com.deya.util.FormatUtil.formatNullString(time)); 
			  time = ((String)infoMap.get("effect_dtime"));
			  time = time == null ? "" : time.replaceAll("-","").replaceAll(" ", "").replaceAll(":","");
			  indexBeanXxgk.setTake_time(com.deya.util.FormatUtil.formatNullString(time)); 
			  time = ((String)infoMap.get("aboli_dtime")); 
			  time = time == null ? "" : time.replaceAll("-","").replaceAll(" ", "").replaceAll(":","");
			  indexBeanXxgk.setOver_time(com.deya.util.FormatUtil.formatNullString(time)); 
			  indexBeanXxgk.setObject_words(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("topic_key"))+" "+com.deya.util.FormatUtil.formatNullString((String)infoMap.get("place_key")));
			  indexBeanXxgk.setForm_type(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("theme_name")));
			  indexBeanXxgk.setContent_type(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("topic_name")));
			  indexBeanXxgk.setPublish_org(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("gk_input_dept")));
			  indexBeanXxgk.setCategoryId(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("cat_id")));
			  indexBeanXxgk.setIs_host(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("is_host")));
			  
			  return indexBeanXxgk;
		 }catch (Exception e) {
			e.printStackTrace();
			return indexBeanXxgk;
	     }
		 
	}

}
