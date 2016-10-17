package com.deya.wcm.services.search.index.impl;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import com.deya.util.HtmlRegexpUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.search.IndexBeanInfo;
import com.deya.wcm.dao.search.IIndexInfoDao;
import com.deya.wcm.dao.search.impl.IndexResourcePdfDaoImpl;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.db.IbatisSessionFactory;
import com.deya.wcm.services.search.index.IndexLuceneManager;
import com.deya.wcm.services.search.index.IndexLuceneManagerResource;
import com.deya.wcm.services.search.index.IndexService;
import com.deya.wcm.services.search.util.pdf.PdfManager;


/**
 * <p>Title: 索引操作接口实现类--info</p>
 * <p>Company: Cicro</p>
 * @author lisp
 * @version 1.0 
 */
public class IndexResourceShkwWsjcServiceImpl implements IndexService {

	static String retype = "retype";//资源的类型参数
	static String retypeValue = "wsjc";//资源的类型   wsjc:网上教程   pic:图片   word:WORD文档   pdf:PDF文档   video:视频  new:所有的新闻  all:包含以上全部
	static int s_count = 50;//每次从数据库中读出50条
	static IIndexInfoDao indexInfoDao = new IndexResourcePdfDaoImpl();
	static Set urlSet = new HashSet();
	
	static{
		
	}

	/**
     * 往索引文件中追加site_id的全部索引
     * @param infos Hashtable  输入信息   如信息site_id {site_id:ABCDEFG}
     * @return boolean
	public boolean appendALlDocument(Map mapSite) {
		urlSet.clear();
		IndexWriter indexWriter = null;
		SqlSession s = IbatisSessionFactory.getInstance().openSession();
		try{
		    String sqlall="select c_name,c_frame,c_id,c_createtime from kp_course order by c_sort desc,c_createtime desc";
			List<Map> listMap = CourseDao.selectCourseSql(sqlall);
			String[][] resultList = new String[listMap.size()][5];
			for(int i=0;i<listMap.size();i++){
			   Map map = listMap.get(i);
			   resultList[i][0] = (String)map.get("c_name");
			   resultList[i][1] = (String)map.get("c_frame");
			   resultList[i][2] = (String)map.get("c_id");
			   resultList[i][3] = (String)map.get("c_createtime");
		   }
			 //int count = indexInfoDao.getInfoListBySiteIdCount(mapSite);
//			 int page=count/s_count+1;
			 System.out.println( mapSite + "appendALlDocument info count===" + resultList.length);
			 
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
			 
			 Field retypeField = new Field(retype, "",Field.Store.YES, Field.Index.NOT_ANALYZED);
			 
			 //得到indexWriter
			 indexWriter = IndexLuceneManagerResource.getIndexModifier(false);
			 System.out.println("indexWriter----" + indexWriter);
			 //循环信息列表
//			 for(int k=0;k<page;k++){
//	    		  int start_num = s_count*k;
//	    		  mapSite.put("start_num", start_num);
//	    		  mapSite.put("page_size", s_count);
	    		 
	    		  //由于取得Oracle的clob字段时 转换有问题 --- 修改
//	    		  List<Map> infoList = indexInfoDao.getInfoListBySiteIdPages(mapSite); 
	    		  //List<Map> infoList = s.selectList(DBManager.getSqlNameByDataType("search.getInfoPdfsListByPages"),mapSite);
	    		  
	    		  //for(Map infoMap : infoList){
	    		  for(int i=0;i<resultList.length;i++){
	    			  
	    			  //得到信息bean
	    			  IndexBeanInfo indexBeanInfo = getIndexBeanInfo(resultList[i]);
	    			  
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
	    			  String url = indexBeanInfo.getUrl();
	    			  if(urlSet.contains(url)){
	    				  continue;
	    			  }else{
	    				  urlSet.add(url);
	    			  }
	    			  //System.out.println("IndexResourcePicServiceImpl.java-----indexBeanInfo.getUrl()::"+indexBeanInfo.getUrl());
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
	    			  System.out.println("appendALlDocument id:" + indexBeanInfo.getId()+"     CategoryId:"+indexBeanInfo.getCategoryId());
	    			  doc.add(categoryIdField);
	    			  publish_timeField.setValue(indexBeanInfo.getPublish_time());
	    			  doc.add(publish_timeField);
	    			  //System.out.println("IndexResourcePicServiceImpl.java-----publish_time::"+indexBeanInfo.getPublish_time());
	    			  
	    			  is_hostField.setValue(indexBeanInfo.getIs_host());
	    			  doc.add(is_hostField);
	    			  
	    			  //System.out.println("IndexResourcePicServiceImpl.java-----retypeValue::"+retypeValue);
	    			  retypeField.setValue(retypeValue);
	    			  doc.add(retypeField); 
	    			  
	    			  //添加一条条info的索引
	    			  IndexLuceneManagerResource.AddDocument(indexWriter,doc);
	    			  
	    		  }
//			 }
			 
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{ 
			//关闭indexWriter
			IndexLuceneManagerResource.closeIndexWriter(indexWriter);
			s.close();
		}    
		return true;
	}
	*/
    /**
     * 往索引文件中追加一条记录
     * @param infos Hashtable  输入信息,如信息id {id:110}
     * @return boolean
     
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
	  			
  			    System.out.println("appendALlDocument id:" + indexBeanInfo.getId()+"     CategoryId:"+indexBeanInfo.getCategoryId());
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
     */
	/**
     * 通过site_id删除该类信息的全部索引
     * @param infos Hashtable  输入信息   如信息site_id {site_id:ABCDEFG}
     * @return boolean
     
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
	*/

    /**
     * 删除索引文件中的一条记录
     * @param infos Hashtable  输入信息,如信息id {id:110}
     * @return boolean
     
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
	public static IndexBeanInfo getIndexBeanInfo(String[] infoLis){
		 IndexBeanInfo indexBeanInfo = new IndexBeanInfo();
		 try{
			  String c_id = infoLis[2].trim();
			  String c_frame = infoLis[1].trim();
			  indexBeanInfo.setId(com.deya.util.FormatUtil.formatNullString(infoLis[2]));
			  indexBeanInfo.setTitle(com.deya.util.FormatUtil.formatNullString(infoLis[0]));
			  indexBeanInfo.setApp_id(com.deya.util.FormatUtil.formatNullString("cms"));
			  
			    //信息内容
			    StringBuffer sbContent = new StringBuffer("");
		        String sqlall="select c_name,c_editor,c_ctype from kp_course where c_id='"+c_id+"'";
			    //String[][] resultList = DBManager.getStringArray("mysql", sqlall);
				List<Map> listMap2 = CourseDao.selectCourseSql(sqlall);
				String[][] resultList = new String[listMap2.size()][3];
				for(int i=0;i<listMap2.size();i++){
				   Map map = listMap2.get(i);
				   resultList[i][0] = (String)map.get("c_name");
				   resultList[i][1] = (String)map.get("c_editor");
				   resultList[i][2] = (String)map.get("c_ctype");
				   sbContent.append(resultList[i][0]+" ");
				   sbContent.append(resultList[i][1]+" ");
			    }
				
				String sqlcontent="select cc_title,ccid,cc_sort from kp_course_content where cc_blong='"+c_id+"' order by cc_sort"; 
				//String[][] resultcon=DBManager.getStringArray("mysql", sqlcontent);
				List<Map> listMap3 = CourseDao.selectCourseSql(sqlcontent);
				for(Map map : listMap3){
					String ccid = (String)map.get("ccid");
					String cc_title = (String)map.get("cc_title");
					sbContent.append(cc_title+" ");
					
					String sqlonecontent="select cc_title,cc_content from kp_course_content where ccid='"+ccid+"'   order by cc_sort";
					//String[][] resultonecon=DBManager.getStringArray("mysql", sqlonecontent);
					List<Map> listMap4 = CourseDao.selectCourseSql(sqlonecontent);
					String[][] resultonecon = new String[listMap4.size()][2];
					for(int i=0;i<listMap4.size();i++){
					   Map map_n = listMap4.get(i);
					   resultonecon[i][0] = (String)map_n.get("cc_title");
					   resultonecon[i][1] = (String)map_n.get("cc_content");
					   sbContent.append(resultonecon[i][0]+" ");
					   sbContent.append(resultonecon[i][1]+" ");
				     }
					
					String sqlall_2="select cp_content,cp_title,cp_sort from kp_course_chapter where cp_cid='"+ccid+"' order by cp_sort";
					//String[][] resultcontent = DBManager.getStringArray("mysql", sqlall);
					List<Map> listMap5 = CourseDao.selectCourseSql(sqlall_2);
					String[][] resultcontent = new String[listMap5.size()][3];
					for(int i=0;i<listMap5.size();i++){
					   Map map_5 = listMap5.get(i);
					   resultcontent[i][0] = (String)map_5.get("cp_content");
					   resultcontent[i][1] = (String)map_5.get("cp_title");
					   resultcontent[i][2] = (String)map_5.get("cp_sort");
					   sbContent.append(resultcontent[i][0]+" ");
					   sbContent.append(resultcontent[i][1]+" ");
				     }
				}
			  indexBeanInfo.setContent(HtmlRegexpUtil.filterHtml(com.deya.util.FormatUtil.formatNullString(sbContent.toString())));
			  String url = "/jsp/course/browser/sourse.jsp?past_id="+c_id+"&frameid=" + c_frame;
			  indexBeanInfo.setUrl(url);
			  //indexBeanInfo.setUrl(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("content_url")));
			  indexBeanInfo.setSite_id("CMSstcsm");
			  indexBeanInfo.setNode_id("CMSstcsm");
			  indexBeanInfo.setModel_id("11");
			  indexBeanInfo.setCategoryId("");
			  
			  indexBeanInfo.setIs_host("0");
			  
			  System.out.println("c_createtime------" + infoLis[3]);
			  String c_createtime = infoLis[3].trim();
			  String time = c_createtime.trim();
			  if(c_createtime.indexOf(".")>-1){
				  time = c_createtime.substring(0, c_createtime.indexOf("."));
			  }
			  time = time == null ? "" : time.replaceAll("-","").replaceAll(" ", "").replaceAll(":","");
			  //System.out.println("com.deya.util.FormatUtil.formatNullString(time)===" + com.deya.util.FormatUtil.formatNullString(time));
			  indexBeanInfo.setPublish_time(com.deya.util.FormatUtil.formatNullString(time)); 
			  return indexBeanInfo;
		 }catch (Exception e) {
			e.printStackTrace();
			return indexBeanInfo;
	     }
		 
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
			  
			  indexBeanInfo.setId(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("att_id")));
			  indexBeanInfo.setTitle(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("att_cname")));
			  indexBeanInfo.setApp_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("app_id")));
			  
			  //得到word在服务器上的真实路径
			  com.deya.wcm.bean.control.SiteBean siteBean = com.deya.wcm.services.control.site.SiteManager.getSiteBeanBySiteID(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("site_id")));
		      String uploadpath = siteBean.getSite_path();
		      String file_P = com.deya.util.FormatUtil.formatNullString((String)infoMap.get("att_path"))+com.deya.util.FormatUtil.formatNullString((String)infoMap.get("att_ename"));
		      String pathfile = "";
		      if(file_P.startsWith("/cms.files")){ 
		    	  //pathfile = "/cicro/wcm"+""+file_P;
		    	  pathfile = JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path")+""+file_P;
			  }else{
				  pathfile = uploadpath+""+file_P;
			  }
			  System.out.println("pathfile::"+pathfile);
			  if(!(new File(pathfile)).exists()){
				  pathfile = "/cicro/wcm/vhosts/img.site.com/ROOT" + file_P;
			  }
			  System.out.println("pathfile-22::"+pathfile);
			  String content = PdfManager.readFdf(pathfile);
			  //System.out.println("content::"+content);
			  indexBeanInfo.setContent(HtmlRegexpUtil.filterHtml(com.deya.util.FormatUtil.formatNullString(content)));
			  indexBeanInfo.setUrl((String)infoMap.get("att_path")+(String)infoMap.get("att_ename"));
			  //indexBeanInfo.setUrl(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("content_url")));
			  indexBeanInfo.setSite_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("site_id")));
			  
			  indexBeanInfo.setNode_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("site_id")));
			  
			  indexBeanInfo.setModel_id(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("model_id")));
			  
			  indexBeanInfo.setCategoryId(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("cat_id")));
			  
			  indexBeanInfo.setIs_host(com.deya.util.FormatUtil.formatNullString((String)infoMap.get("is_host")));
			  
			  String time = ((String)infoMap.get("upload_dtime"));
			  time = time == null ? "" : time.replaceAll("-","").replaceAll(" ", "").replaceAll(":","");
			  //System.out.println("com.deya.util.FormatUtil.formatNullString(time)===" + com.deya.util.FormatUtil.formatNullString(time));
			  indexBeanInfo.setPublish_time(com.deya.util.FormatUtil.formatNullString(time)); 
			  return indexBeanInfo;
		 }catch (Exception e) {
			e.printStackTrace();
			return indexBeanInfo;
	     }
		 
	}
	 */
	
	public static void main(String arr[]){
		IndexResourceShkwWsjcServiceImpl impl = new IndexResourceShkwWsjcServiceImpl();
		Map map = new HashMap();
		map.put("id","1111");
		impl.appendSingleDocument(map);
	}

	@Override
	public boolean appendALlDocument(Map map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteALlDocument(Map map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean appendSingleDocument(Map infos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteSingleDocument(Map infos) {
		// TODO Auto-generated method stub
		return false;
	}

}
