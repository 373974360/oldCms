package com.deya.wcm.services.search.search;

import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKQueryParser;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.search.PageControl;
import com.deya.wcm.bean.search.ResultBean;
import com.deya.wcm.bean.search.SearchResult;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.search.SearchForInterface;
import com.deya.wcm.services.search.search.util.Arith;
import com.deya.wcm.services.search.search.util.SearchUtil;
import com.deya.wcm.services.zwgk.node.GKNodeManager;

public class SearchInfoManager {
    
	/**
	 *得到关键词的query
	 *@param String scope 搜索范围：标题 title,正文 content
	 *@param String q 关键词
	 */
	public static Query getQuery(String scope,String q){
		Query query = null;
		try{
			List fieldsList = new ArrayList();
			List keysList = new ArrayList();
			List occurList = new ArrayList();
			//System.out.println("scope===" + scope);
			if(scope.equals("")){
				fieldsList.add("title");
				fieldsList.add("content");
				fieldsList.add("doc_no");
				keysList.add(q);
				keysList.add(q);
				keysList.add(q);
				occurList.add(BooleanClause.Occur.SHOULD);
				occurList.add(BooleanClause.Occur.SHOULD);
				occurList.add(BooleanClause.Occur.SHOULD);
			}else if(scope.equals("title")){
				fieldsList.add("title");
				keysList.add(q);
				occurList.add(BooleanClause.Occur.SHOULD);
			}
			else if(scope.equals("content")){
				fieldsList.add("content");
				keysList.add(q);
				occurList.add(BooleanClause.Occur.SHOULD);
			} else if(scope.equals("doc_no")){
				fieldsList.add("doc_no");
				keysList.add(q);
				occurList.add(BooleanClause.Occur.SHOULD);
			}
			String[] fields = (String[])fieldsList.toArray(new String[fieldsList.size()]);//搜索的字段
			String[] keys = (String[])keysList.toArray(new String[keysList.size()]);//关键字数组
			BooleanClause.Occur[] flags = (BooleanClause.Occur[])occurList.toArray(new BooleanClause.Occur[occurList.size()]);
			
			//使用 IKQueryParser类提供的parseMultiField方法构建多字段多条件查询
			//query = IKQueryParser.parseMultiField(fields,keys, flags);     //IKQueryParser多个字段搜索 
			
			//用StandardAnalyzer分词法
			QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_30, fields, new StandardAnalyzer(Version.LUCENE_30));
			query = parser.parse(q);
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return query;
		}
	}
	 
	
	/**
	 *简单搜索
	 *@param Map map
	 *
	  map.remove("type");//搜索标示  没有该值说明全部信息 （ggfw：服务，info：信息，xxgk：信息公开）
	  map.remove("q");//关键字        *必须
	  map.remove("q2");//关键字2
	  map.remove("p");//当前页数     
	  map.remove("pz");//页面显示条数
	  map.remove("length");//结果页面简介个数
	  map.remove("color");//高亮字颜色
	  map.remove("site_id");//站点id  前台表单传过来的参数为""时 默认本站点  为"all"时 是所有站点
	  map.remove("categoryId");//所属栏目  
	  map.remove("scope");//搜索范围：标题 title,正文 content , 全部为""
	 *@return Map   
	 * */
	public static SearchResult search(Map map){ 
		SearchResult searchResult = new SearchResult();
		long timeS = System.currentTimeMillis();
	    List listResult = new ArrayList();//存放搜索结果
	    IndexSearcher indexSearcher = null;
		try{
			String q = (String)map.get("q");
			if(q==null){
				return searchResult; 
			}
			
			String scope = (String)map.get("scope")==null?"":(String)map.get("scope");//搜索范围：标题 title,正文 content
			Query query = getQuery(scope, q);
			//System.out.println("query==="+query.toString()); //查询条件
			
			indexSearcher = SearchLuceneManager.getIndexSearcher();
			if(indexSearcher==null){
				return searchResult;   
			}
			
			//要排序的字段     false是正序，true是倒叙   (多个字段进行排序)  -- 先按时间倒叙再按id倒叙
			Sort sort = new Sort(new SortField[]{new SortField("publish_time", SortField.STRING,true),new SortField("id", SortField.INT,true)});

			//合并多个query对象
			String q2 = (String)map.get("q2")==null?"":(String)map.get("q2");
			if(q2 != null){
				q2 = new String(q.getBytes("iso-8859-1"),"utf-8");
			}
			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(query, BooleanClause.Occur.MUST);
			if(!q2.equals("")){  
				Query query2 = getQuery(scope, q2);     //IKQueryParser多个字段搜索 
				booleanQuery.add(query2, BooleanClause.Occur.MUST);
				//System.out.println("query2==="+query2.toString()); //查询条件
			}
			
			
			//包含以下全部关键词   多个关键词以逗号分隔
			String qn1 = (String)map.get("qn1")==null?"":(String)map.get("qn1");
			StringTokenizer st = new StringTokenizer(qn1, " ");      
			while (st.hasMoreElements()) {    
			     String token = st.nextElement().toString();           
			     if(!token.equals("")){  
						Query query_qn1 = getQuery(scope, token);     //IKQueryParser多个字段搜索 
						booleanQuery.add(query_qn1, BooleanClause.Occur.MUST);
						//System.out.println("query_qn1"+"_"+token+"==="+query_qn1.toString()); //查询条件
				}   
		    }
			
			
			//过滤条件
			QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_30, new String[]{""}, new IKAnalyzer());
            //QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_30, new String[]{""}, new StandardAnalyzer(Version.LUCENE_30));
			//如果站点id为""则说明前台表单提交过来的是all值  即时在所有站点下搜索信息 
		    String site_idB = (String)map.get("site_id")==null?"":(String)map.get("site_id"); 
		    //如果site_domain不为"" 则说明搜索的是本站点的信息
		    String site_domain = (String)map.get("site_domain")==null?"":(String)map.get("site_domain"); 
			
			//去除不要过滤的字段
			int p = (String)map.get("p")==null?1:Integer.valueOf((String)map.get("p"));//当前是第几页
			int pz = (String)map.get("pz")==null?0:Integer.valueOf((String)map.get("pz"));//页面显示条数
			int length = (String)map.get("length")==null?100:Integer.valueOf((String)map.get("length"));//结果页面简介个数
			String color = (String)map.get("color");//高亮字颜色
			if(color==null){
				color = "red";
			}
			map.remove("q");
			map.remove("q2");
			map.remove("p");
			map.remove("pz");
			map.remove("length");
			map.remove("color"); 
			map.remove("scope");
			map.remove("site_domain");
			map.remove("qn1");
			//System.out.println(map);
			
			String tempQuery = getAllQuery(map);
			QueryWrapperFilter filter = tempQuery.equals("")?null:new QueryWrapperFilter(parser.parse(tempQuery.toString()));
					
			//分页信息
			PageControl pageControl = new PageControl();
			pageControl.setRowsPerPage(pz);//每页有多少行  默认10行
			pageControl.setCurPage(p);//当前页数
			pageControl.countStart();//根据当前页数和每页条数计算开始数
			int sn = pageControl.getStart();
			pz = pageControl.getRowsPerPage();
			
//			System.out.println("sn===" + Integer.valueOf(sn));
//			System.out.println("pz===" + Integer.valueOf(pz));
//			System.out.println("sn+pz===" + (Integer.valueOf(sn)+Integer.valueOf(pz)));
			TopDocs hits = indexSearcher.search(booleanQuery , filter, (Integer.valueOf(sn)+Integer.valueOf(pz)), sort);
			// hits.totalHits表示一共搜到多少个
			//System.out.println("找到了" + hits.totalHits + "个");
			
			//分页信息
			pageControl.setMaxRowCount(Long.valueOf(hits.totalHits));//一共有多少行 
			pageControl.countMaxPage(); //根据总行数计算总页数 
			
			
			//高亮显示设置 
		    Highlighter highlighter = null; 
		    SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter( 
		          "<font color='"+color+"'>", "</font>"); 
		    highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(booleanQuery)); 
		    // 这个100是指定关键字字符串的context的长度，你可以自己设定，因为不可能返回整篇正文内容 
		    highlighter.setTextFragmenter(new SimpleFragmenter(length));
		    
		    // 循环hits.scoreDocs数据，并使用indexSearch.doc方法把Document还原，再拿出对应的字段的值
			for (int j = sn; j < hits.scoreDocs.length; j++) {
				ScoreDoc sdoc = hits.scoreDocs[j];
				Document doc = indexSearcher.doc(sdoc.doc);
				//System.out.println("**************************************");
				// 高亮显示摘要 
				String content = doc.get("content");
				//System.out.println("content11===" + content);
				String contentTemp = content;
		        //TokenStream tokenStream = new IKAnalyzer().tokenStream("token",new StringReader(content)); 
		        TokenStream tokenStream = new StandardAnalyzer(Version.LUCENE_30).tokenStream("token",new StringReader(content));
		        content = highlighter.getBestFragment(tokenStream, content); 
		        //System.out.println("content22===" + content);
		        //如果没有高亮 就截取内容一段
				if(content==null || content.equals("")){
					int countWords = Integer.valueOf(length);
					if(countWords>=contentTemp.length()){
						content = contentTemp;
					}else{
						content = contentTemp.substring(0,countWords);
					}
				}
				
				String url = doc.get("url");
				String title = doc.get("title");
				String type = doc.get("type");
				String time = doc.get("publish_time");
				String id = doc.get("id");
				String categoryId = doc.get("categoryId");
				String site_id = doc.get("site_id");
				String model_id = doc.get("model_id");
				
				ResultBean resultBean = new ResultBean();
				if(!site_domain.equals("")){//搜索本站点的信息时调用
					url = "http://"+site_domain+url;
					//System.out.println(url);
				}else{
					if(site_idB.equals("")){
						url = "http://"+SearchForInterface.getDomainBySiteId(site_id)+url;
					}
				}
				
				resultBean.setUrl(url);
				resultBean.setTitle(title);
				resultBean.setType(type);
				//resultBean.setTime(time);
				resultBean.setTime(SearchUtil.formatTimeYYYYMMDDHHMMSS(time));
				resultBean.setId(id);
				resultBean.setCategory_id(categoryId);
				resultBean.setCategory_name(SearchForInterface.getCategoryNameById(categoryId));
				resultBean.setSite_id(site_id);
				resultBean.setContent(content);
				resultBean.setModel_id(model_id);
				
				listResult.add(resultBean);
				
			}
		    
			//得到搜索所用时间
			timeS = System.currentTimeMillis() - timeS;
			Double timeQ = Arith.div(Double.valueOf(timeS), Double.valueOf(2000), 2);
			
			searchResult.setItems(listResult);
			searchResult.setPageControl(pageControl);
			searchResult.setTime(String.valueOf(timeQ));
			if(!q2.equals("")){
				q = q + "AND" + q2;
			}
			searchResult.setKey(q);
			searchResult.setCount(pageControl.getMaxRowCount());
			indexSearcher.close();
			
			return searchResult;
		}catch (Exception e) {
			e.printStackTrace();
			if(indexSearcher!=null){
				try {
					indexSearcher.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return searchResult;
		}
		
	}
	
	 
	//通过参数判断 要得到什么搜索解析器
	//返回值： 1：一般的解析器  2：素材解析器  3：全部的解析器
	public static String getSearcher(Map map){ 
		try{
			String retype = (String)map.get("retype")==null?"":((String)map.get("retype")).trim().toString();
			if("".equals(retype)){ 
				retype = "1";
			}else if("all".equals(retype)){
				retype = "3";
			}else{
				retype = "2";
			} 
			//System.out.println("getSearcher----retype-----------------:::"+retype);
			return retype;
		}catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
	}
	
	/**
	 *简单搜索
	 *@param Map map
	 *
	  map.remove("type");//搜索标示  没有该值说明全部信息 （bszn：办事指南，file：文件，info：信息，xxgk：信息公开）
	  map.remove("q");//关键字        *必须
	  map.remove("q2");//关键字2
	  map.remove("p");//当前页数     *必须
	  map.remove("pz");//页面显示条数
	  map.remove("length");//结果页面简介个数
	  map.remove("color");//高亮字颜色 
	  map.remove("site_id");//站点id  为""时 默认本站点  为"all"时 是所有站点
	  map.remove("categoryId");//所属栏目 
	  map.remove("scope");//搜索范围：标题 title,正文 content , 全部为""
	  map.remove("ts");//搜索时间范围：开始时间
	  map.remove("te");//搜索时间范围：结束时间
	  map.remove("st");//搜索结果排序方式：1 按时间倒叙，2按时间正序 
	 *@return Map 
	 * */
	public static SearchResult searchGJ(Map map){
		//System.out.println("map====" + map);
		
		//排序方式
		//String sortType = (String)map.get("sort")==null?"time":(String)map.get("sort");//搜索时间范围：开始时间
		
		SearchResult searchResult = new SearchResult();
		long timeS = System.currentTimeMillis();
	    List listResult = new ArrayList();//存放搜索结果
	    IndexSearcher indexSearcher = null;
	    MultiSearcher indexSearcherAll = null;
		try{
			String q = (String)map.get("q");
			if(q==null){
				q = "";
				//return null; 
			}
			
			String scope = (String)map.get("scope")==null?"":(String)map.get("scope");//搜索范围：标题 title,正文 content
			//使用 IKQueryParser类提供的parseMultiField方法构建多字段多条件查询
//			Query query = getQuery(scope, q);     //IKQueryParser多个字段搜索 
//			System.out.println("query==="+query.toString()); //查询条件
			Query query = null;
			
			if(getSearcher(map).equals("1")){
				indexSearcher = SearchLuceneManager.getIndexSearcher();
				if(indexSearcher==null){
					return searchResult;
				}
			}else if(getSearcher(map).equals("2")){
				indexSearcher = SearchLuceneManager.getIndexSearcherResource();
				if(indexSearcher==null){
					return searchResult;
				}
			}else if(getSearcher(map).equals("3")){
				indexSearcherAll = SearchLuceneManager.getIndexSearcherAll();
				if(indexSearcherAll==null){
					return searchResult;
				}
			}
			
			
			//过滤条件
			QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_30, new String[]{""}, new IKAnalyzer());
            //QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_30, new String[]{""}, new StandardAnalyzer(Version.LUCENE_30));
			
			//如果站点id为""则说明前台表单提交过来的是all值  即时在所有站点下搜索信息 
		    String site_idB = (String)map.get("site_id")==null?"":(String)map.get("site_id"); 
		    //如果site_domain不为"" 则说明搜索的是本站点的信息
		    String site_domain = (String)map.get("site_domain")==null?"":(String)map.get("site_domain");
			 
			String st = (String)map.get("st")==null?"":(String)map.get("st");//搜索结果排序方式：1 按时间倒叙，2按时间正序
			//要排序的字段     false是正序，true是倒叙   (多个字段进行排序)  -- 先按时间倒叙再按id倒叙
			Boolean sortB = true;
			if(st.equals("2")){
				sortB = false;
			}
			String sortType = "";
			if(st.equals("1") || st.equals("") || st.equals("2")){
				sortType = "time";
			}
			//Sort sort = new Sort(new SortField[]{new SortField("publish_time", SortField.STRING,sortB),new SortField("id", SortField.INT,true)});
			Sort sort = new Sort(new SortField[]{new SortField("publish_time", SortField.STRING,sortB)});
			
			//搜索条件合并
			BooleanQuery booleanQuery = new BooleanQuery();
			BooleanQuery booleanQueryHighlighter = new BooleanQuery(); //只高亮搜索的关键词  其他过滤条件不高亮
			if(q.equals("")){
				Query queryTime = parser.parse("publish_time:[20000101000001 TO "+DateUtil.getCurrentDateTime().replaceAll("-","").replaceAll(" ","").replaceAll(":", "")+"]");
				booleanQuery.add(queryTime, BooleanClause.Occur.MUST);
			}else{
				if(!q.contains("AND")){
					//使用 IKQueryParser类提供的parseMultiField方法构建多字段多条件查询
					query = getQuery(scope, q);     //IKQueryParser多个字段搜索 
					//System.out.println("query==="+query.toString()); //查询条件
					booleanQuery.add(query, BooleanClause.Occur.MUST);
				}
			}
			
			
			//包含以下全部关键词   多个关键词以逗号分隔 ---------------------------start-------------------新加
			String qn1 = (String)map.get("qn1")==null?"":(String)map.get("qn1");
			StringTokenizer stqn1 = new StringTokenizer(qn1, " ");      
			while (stqn1.hasMoreElements()) {    
			     String token = stqn1.nextElement().toString();           
			     if(!token.equals("")){  
						Query query_qn1 = getQuery(scope, token);     //IKQueryParser多个字段搜索 
						booleanQuery.add(query_qn1, BooleanClause.Occur.MUST);
						booleanQueryHighlighter.add(query_qn1, BooleanClause.Occur.MUST);
						//System.out.println("query_qn1"+"_"+token+"==="+query_qn1.toString()); //查询条件
				}   
		    }
			//包含以下全部关键词   多个关键词以逗号分隔 ---------------------------end-------------------新加
			
			//包含以下任意一个关键词   多个关键词以逗号分隔 --------------------------start--------------------新加
			boolean booleanQn2 = false;
			BooleanQuery booleanQueryQn2 = new BooleanQuery();
			String qn2 = (String)map.get("qn2")==null?"":(String)map.get("qn2");
			StringTokenizer stqn2 = new StringTokenizer(qn2, " ");      
			while (stqn2.hasMoreElements()) {    
			     String token = stqn2.nextElement().toString();           
			     if(!token.equals("")){  
			    	    booleanQn2 = true;
						Query query_qn2 = getQuery(scope, token);     //IKQueryParser多个字段搜索 
						booleanQueryQn2.add(query_qn2, BooleanClause.Occur.SHOULD);
						booleanQueryHighlighter.add(query_qn2, BooleanClause.Occur.MUST);
						//System.out.println("query_qn2"+"_"+token+"==="+query_qn2.toString()); //查询条件
				}   
		    } 
			if(booleanQn2){
				//System.out.println("booleanQueryQn2==="+booleanQueryQn2.toString()); //查询条件
				booleanQuery.add(booleanQueryQn2, BooleanClause.Occur.MUST);
			}
			//包含以下任意一个关键词   多个关键词以逗号分隔 --------------------------end--------------------新加
			
			//不包含以下全部关键词   多个关键词以逗号分隔 ---------------------------start-------------------新加
			String qn3 = (String)map.get("qn3")==null?"":(String)map.get("qn3");
			StringTokenizer stqn3 = new StringTokenizer(qn3, " ");      
			while (stqn3.hasMoreElements()) { 
			     String token = stqn3.nextElement().toString();           
			     if(!token.equals("")){  
						Query query_qn3 = getQuery(scope, token);     //IKQueryParser多个字段搜索 
						booleanQuery.add(query_qn3, BooleanClause.Occur.MUST_NOT);
						//booleanQueryHighlighter.add(query_qn3, BooleanClause.Occur.MUST);
						//System.out.println("query_qn1"+"_"+token+"==="+query_qn3.toString()); //查询条件
				}   
		    }
			//不包含以下全部关键词   多个关键词以逗号分隔 ---------------------------end-------------------新加
			
			//过滤时间
			String ts = (String)map.get("ts")==null?"":(String)map.get("ts");//搜索时间范围：开始时间
			String te = (String)map.get("te")==null?"":(String)map.get("te");//搜索时间范围：结束时间
			if(!ts.equals("") || !te.equals("")){
				if(ts.equals("")){
					ts = "2000-01-01 00:00:01"; 
					te = te + " 59:59:59";
				}
				if(te.equals("")){
					ts = ts + " 00:00:01";
					te = DateUtil.getCurrentDateTime();
				}
				if(!te.contains(":")){
					ts = ts + " 00:00:01";
				}
				if(!te.contains(":")){
					te = te + " 59:59:59";
				}
				ts = ts.replaceAll("-","").replaceAll(" ","").replaceAll(":", "");
				te = te.replaceAll("-","").replaceAll(" ","").replaceAll(":", "");
//				//参数：字段，最小值，最大值，是否包含最小值，是否包含最大值
//				NumericRangeQuery numericRangeQuery = NumericRangeQuery.newLongRange
//                      ("publish_time",Long.valueOf(ts), Long.valueOf(te), true,true);
//		        booleanQuery.add(numericRangeQuery, BooleanClause.Occur.MUST);
		        Query queryTime = parser.parse("publish_time:["+ts+" TO "+te+"]");
				booleanQuery.add(queryTime, BooleanClause.Occur.MUST);
			}
			
			//过滤掉的站点id   --多个站点以逗号隔开
			String ds_id = (String)map.get("ds_id")==null?"":(String)map.get("ds_id");//搜索时间范围：开始时间
			if(!ds_id.equals("")){
				for(String s : ds_id.split(",")){
					if(s!=null && !"".equals(s)){
						Query queryDsId = parser.parse("site_id:"+s);
						booleanQuery.add(queryDsId, BooleanClause.Occur.MUST_NOT);
					}
				}
			}


			//文号过滤
			if(map.containsKey("wnumber")){
				Query queryGkNo = getQuery("doc_no",map.get("wnumber").toString());
				booleanQuery.add(queryGkNo, BooleanClause.Occur.MUST);
				System.out.println(booleanQuery.toString());
			}

			//合并多个query对象
			String q2 = (String)map.get("q2")==null?"":(String)map.get("q2");
			
			/////////////////////////////
			if(q.trim().equals("AND")){
				return searchResult;
			}
			if(!q.contains("AND")){
				if(!q.equals("")){
					booleanQueryHighlighter.add(query, BooleanClause.Occur.MUST);
				}
			}else{
				List<String> listF = Arrays.asList(q.split("AND"));
				for(String str44 :listF){
					if(str44!=null && !"".equals(str44)){
						Query query_3 = getQuery(scope, str44);     //IKQueryParser多个字段搜索 
						booleanQuery.add(query_3, BooleanClause.Occur.MUST);
						booleanQueryHighlighter.add(query_3, BooleanClause.Occur.MUST);
						//System.out.println("query_3==="+query_3.toString()); //查询条件
					}
				}
			}
			///////////////////////////////
			if(!q2.equals("")){  
				Query query2 = getQuery(scope, q2);     //IKQueryParser多个字段搜索 
				booleanQuery.add(query2, BooleanClause.Occur.MUST);
				booleanQueryHighlighter.add(query2, BooleanClause.Occur.MUST);
				//System.out.println("query2==="+query2.toString()); //查询条件
			} 
 
			//去除不要过滤的字段 
			int p = (String)map.get("p")==null?1:Integer.valueOf((String)map.get("p"));//当前是第几页
			int pz = (String)map.get("pz")==null?0:Integer.valueOf((String)map.get("pz"));//页面显示条数
			int length = (String)map.get("length")==null?100:Integer.valueOf((String)map.get("length"));//结果页面简介个数
			String color = (String)map.get("color");//高亮字颜色
			if(color==null){
				color = "red";
			}
			map.remove("q");
			map.remove("q2");
			map.remove("p");
			map.remove("pz");
			map.remove("length");
			map.remove("color");
			map.remove("scope");//搜索范围：标题 title,正文 content , 全部为""
			map.remove("ts");//搜索时间范围：开始时间
			map.remove("te");//搜索时间范围：结束时间
			map.remove("st");//搜索结果排序方式：1 按时间倒叙，2按时间正序 
			map.remove("site_domain");
			map.remove("ds_id");
			map.remove("qn1");
			map.remove("qn2");
			map.remove("qn3");
			map.remove("sort");
			map.remove("datetype");
			map.remove("wnumber");

			//如有信息栏目参数 就得到该栏目下面的所有子栏目
			if(map.get("categoryId")!=null && !"".equals(map.get("categoryId"))){//有栏目参数
				String site_id = (String)map.get("site_id");
				int categoryId = Integer.parseInt((String)map.get("categoryId"));
				if(!site_id.equals("all")){
					String cat_ids = CategoryManager.getAllChildCategoryIDS(categoryId,site_id);
					if(cat_ids==null || "".equals(cat_ids)){
						cat_ids = (String)map.get("categoryId");
					}
					map.put("categoryId",cat_ids);
				}
			}
			//Query queryFilter = parser.parse(sb.toString());
			String tempQuery = getAllQuery(map);
			QueryWrapperFilter filter = tempQuery.equals("")?null:new QueryWrapperFilter(parser.parse(tempQuery.toString()));
			
			//分页信息
			PageControl pageControl = new PageControl();
			pageControl.setRowsPerPage(pz);//每页有多少行  默认10行
			pageControl.setCurPage(p);//当前页数
			pageControl.countStart();//根据当前页数和每页条数计算开始数 
			int sn = pageControl.getStart();
			pz = pageControl.getRowsPerPage();
			
//			System.out.println("sn===" + Integer.valueOf(sn));
//			System.out.println("pz===" + Integer.valueOf(pz));
//			System.out.println("sn+pz===" + (Integer.valueOf(sn)+Integer.valueOf(pz)));
			//System.out.println("booleanQuery : " + booleanQuery);
			TopDocs hits = null;
			if(sortType.equals("time")){
				hits = (indexSearcher==null?indexSearcherAll:indexSearcher).search(booleanQuery , filter, (Integer.valueOf(sn)+Integer.valueOf(pz)), sort);
			}else{
				hits = (indexSearcher==null?indexSearcherAll:indexSearcher).search(booleanQuery , filter, (Integer.valueOf(sn)+Integer.valueOf(pz)));
			}
			
			// hits.totalHits表示一共搜到多少个
			//System.out.println("找到了" + hits.totalHits + "个");
			
			//分页信息
			pageControl.setMaxRowCount(Long.valueOf(hits.totalHits));//一共有多少行 
			pageControl.countMaxPage(); //根据总行数计算总页数 
			
			
			//高亮显示设置 
		    Highlighter highlighter = null; 
		    SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter( 
		          "<font color='"+color+"'>", "</font>"); 
		    //System.out.println("booleanQueryHighlighter--------" + booleanQueryHighlighter.toString());
		    highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(booleanQueryHighlighter)); 
		    // 这个100是指定关键字字符串的context的长度，你可以自己设定，因为不可能返回整篇正文内容 
		    //System.out.println("length==========" + length); 
		    highlighter.setTextFragmenter(new SimpleFragmenter(length));
			
		    // 循环hits.scoreDocs数据，并使用indexSearch.doc方法把Document还原，再拿出对应的字段的值
			for (int j = sn; j < hits.scoreDocs.length; j++) {
				ScoreDoc sdoc = hits.scoreDocs[j];
				//Document doc = indexSearcher.doc(sdoc.doc);
				Document doc = (indexSearcher==null?indexSearcherAll:indexSearcher).doc(sdoc.doc);
				//System.out.println("**************************************");
				// 高亮显示摘要 
				String content = doc.get("content");
				//System.out.println("content11===" + content);
				String contentTemp = content;
		        //TokenStream tokenStream = new IKAnalyzer().tokenStream("token",new StringReader(content)); 
				TokenStream tokenStream = new StandardAnalyzer(Version.LUCENE_30).tokenStream("token",new StringReader(content)); 
		        content = highlighter.getBestFragment(tokenStream, content); 
		        //System.out.println("content22===" + content);
		        //如果没有高亮 就截取内容一段
				if(content==null || content.equals("")){
					int countWords = Integer.valueOf(length);
					if(countWords>=contentTemp.length()){
						content = contentTemp;
					}else{
						content = contentTemp.substring(0,countWords);
					}
				}
				
				String url = doc.get("url");
				String title = doc.get("title"); 
				tokenStream = new StandardAnalyzer(Version.LUCENE_30).tokenStream("token",new StringReader(title));
				title = highlighter.getBestFragment(tokenStream, title); 
				if(title==null){
					title = doc.get("title");
				}
				String type = doc.get("type");
				String time = doc.get("publish_time");
				String id = doc.get("id");
				String categoryId = doc.get("categoryId");
				String site_id = doc.get("site_id");
				String model_id = doc.get("model_id");
				String retype = doc.get("retype");
				
				
				ResultBean resultBean = new ResultBean();
				//System.out.println("site_domain-------------" + site_domain);
				//System.out.println("site_idB-------------" + site_idB);
				if(!url.startsWith("http://")){
					if(!site_domain.equals("")){
						url = "http://"+site_domain+url;
					}else{
						if(site_idB.equals("")){
							url = "http://"+SearchForInterface.getDomainBySiteId(site_id)+url;
						}else{
							url = "http://"+SearchForInterface.getDomainBySiteId(site_idB)+url;
						}
					}
				}
				if(retype!=null && !"".equals(retype)){
					resultBean.setRetype(retype);
				}
				resultBean.setUrl(url);
				resultBean.setTitle(title);
				resultBean.setType(type);
				//resultBean.setTime(time);
				resultBean.setTime(SearchUtil.formatTimeYYYYMMDDHHMMSS(time));
				resultBean.setId(id); 
				resultBean.setCategory_id(categoryId);
				//System.out.println("(categoryId===" + categoryId+")  (id==="+id+")");
				if(categoryId==null || categoryId.equals("")){
					resultBean.setCategory_name("");
				}else{
					resultBean.setCategory_name(SearchForInterface.getCategoryNameById(categoryId));
				}
				resultBean.setSite_id(site_id);
				resultBean.setContent(content);
				resultBean.setModel_id(model_id);
				
				listResult.add(resultBean);
				
			}
		    
			//得到搜索所用时间
			timeS = System.currentTimeMillis() - timeS;
			Double timeQ = Arith.div(Double.valueOf(timeS), Double.valueOf(2000), 2);

			searchResult.setItems(listResult);
			searchResult.setPageControl(pageControl);
			searchResult.setTime(String.valueOf(timeQ));
			if(!q2.equals("")){
				q = q + "AND" + q2;
			}
			searchResult.setKey(q);
			searchResult.setCount(pageControl.getMaxRowCount());
			(indexSearcher==null?indexSearcherAll:indexSearcher).close();
			
			return searchResult;
		}catch (Exception e) {
			e.printStackTrace();
			if((indexSearcher==null?indexSearcherAll:indexSearcher)!=null){
				try {
					(indexSearcher==null?indexSearcherAll:indexSearcher).close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return searchResult;
		}
		
	}
	
	
	//根据参数过滤条件     map 里面是 参数-值
	public static String getAllQuery(Map map){
		//System.out.println("getAllQuery --- map = " + map);
		StringBuffer sb = new StringBuffer("");
	    Iterator it = map.keySet().iterator();
	    int i = 0;
	    while(it.hasNext()){
	    	i++;
		    String key = (String)it.next();
		    String object = (String)map.get(key);
		    if(key.equals("type")){
		    	if(!sb.toString().trim().equals("") && sb.toString().trim().endsWith(")")){
		    		sb.append(" && ");
		    	}
		    	sb.append(getTypeQuery(key,object));
		    }
		    
		    else if(key.equals("retype")){
		    	if(!object.equals("all")){
		    		if(!sb.toString().trim().equals("") && sb.toString().trim().endsWith(")")){
			    		sb.append(" && ");
			    	} 
			    	sb.append(getRetypeQuery(key,object));
		    	}
		    }
		    
		    else if(key.equals("categoryId")){
		    	if(!sb.toString().trim().equals("") && sb.toString().trim().endsWith(")")){
		    		sb.append(" && ");
		    	} 
		    	sb.append(getCategoryIdQuery(key,object));
		    }else if(key.equals("site_id")){
		    	String typef = (String)map.get("type");
		    	if(typef==null){ 
		    		typef = "";
		    	}
				if(!typef.equals("info,zwgk")){ 
					if(!sb.toString().trim().equals("") && sb.toString().trim().endsWith(")")){
			    		sb.append(" && ");
			    	} 
				    if(i==map.size()){
				    	sb.append("("+key+":"+object+")");
				    }else{
				    	sb.append("("+key+":"+object+")");
				    }
				}else{//子站中需要搜索和该站点关联的公开节点的信息 和 该站点下的所有信息
					List<GKNodeBean> gkNodeBeans = GKNodeManager.getAllGKNodeList();
					String node_id = "";
					String site_idv = (String)map.get(key);
					for(GKNodeBean nodeBean : gkNodeBeans){
						if(nodeBean.getRela_site_id().equals(site_idv)){
							node_id = nodeBean.getNode_id();
							break;
						}
					}
					if(!node_id.equals("")){
						if(!sb.toString().trim().equals("") && sb.toString().trim().endsWith(")")){
				    		sb.append(" && ");
				    	}
						sb.append(getNodeIdAndSiteIdQuery(site_idv,node_id));
					}
				}
		    }else{
		    	if(!sb.toString().trim().equals("")){
		    		sb.append(" && ");
		    	}
			    if(i==map.size()){
			    	sb.append("("+key+":"+object+")");
			    }else{
			    	sb.append("("+key+":"+object+")");
			    }
		    }
	    } 
	    //System.out.println("sb.toString()====" + sb.toString());
	    return sb.toString();
	}
	
	
	
	//根据参数过滤条件
	public static String getTypeQuery(String key,String str){
		StringBuffer sb = new StringBuffer();
		if(str.indexOf(",")>0){
		    sb.append("(");
			String temp[] = str.split(",");
			for(int i=0;i<temp.length;i++){
				if(i==temp.length-1){
					sb.append("("+key+":"+temp[i]+")");
				}else{
					sb.append("("+key+":"+temp[i]+") || ");
				}
			}
			sb.append(")");
			return sb.toString();
		}else if(!str.toString().equals("")){
			sb.append("("+key+":"+str+")");
			return sb.toString();
		}else{
			return "";
		}
	}
	
	//根据参数过滤条件 --- 按信息栏目条件来过滤
	public static String getCategoryIdQuery(String key,String str){
		StringBuffer sb = new StringBuffer();
		if(str.indexOf(",")>0){
		    sb.append("(");
			String temp[] = str.split(",");
			for(int i=0;i<temp.length;i++){
				if(i==temp.length-1){
					sb.append("("+key+":"+temp[i]+")");
				}else{
					sb.append("("+key+":"+temp[i]+") || ");
				}
			}
			sb.append(")");
			return sb.toString();
		}else if(!str.toString().equals("")){
			sb.append("("+key+":"+str+")");
			return sb.toString();
		}else{
			return "";
		}
	}
	
	//根据参数过滤条件 --- 子站中需要搜索和该站点关联的公开节点的信息 和 该站点下的所有信息
	public static String getNodeIdAndSiteIdQuery(String site_id,String node_id){
		StringBuffer sb = new StringBuffer();
		if(!site_id.equals("") && !node_id.equals("")){
		    sb.append("(");
			sb.append("(site_id:"+site_id+") || ");
			sb.append("(node_id:"+node_id+")");
			sb.append(")");
			return sb.toString();
		}else{
			return "";
		}
	}
	
	
	public static String getRetypeQuery(String key,String value){
		StringBuffer sb = new StringBuffer();
		if(!key.equals("") && !value.equals("")){
			sb.append("(retype:"+value+")");
			return sb.toString();
		}else{ 
			return "";
		}
	}
	
}
