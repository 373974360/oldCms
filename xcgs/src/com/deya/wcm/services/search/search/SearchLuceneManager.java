package com.deya.wcm.services.search.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.wltea.analyzer.lucene.IKSimilarity;

import com.deya.wcm.services.search.SearchForInterface;

public class SearchLuceneManager {

	// 保存索引文件的地方
	static String indexDir = SearchForInterface.getIndexPathRoot();
	static Directory dir = null;
	static Directory dirResource = null;
	static Directory dirAll = null; 
	
	public static IndexSearcher getIndexSearcher() throws IOException{
		if(dir==null){
			// 创建Directory对象
			if(!(new File(indexDir)).exists()){
				return null;
			}
			dir = new SimpleFSDirectory(new File(indexDir));
		} 
		// 创建 IndexSearcher对象，相比IndexWriter对象，这个参数就要提供一个索引的目录就行了
		IndexSearcher indexSearch = new IndexSearcher(dir);
		//Analyzer analyzer = new IKAnalyzer();
		indexSearch.setSimilarity(new IKSimilarity());//在索引器中使用IKSimilarity相似度评估器 
		
		//添加过滤关键字
		List list = new ArrayList();
		//list.add("李"); 
		org.wltea.analyzer.dic.Dictionary.loadExtendStopWords(list);

		return indexSearch;
	}
	
	//得到素材的搜索构造器
	public static IndexSearcher getIndexSearcherResource() throws IOException{
		if(dirResource==null){
			// 创建Directory对象
			if(!(new File(indexDir+"_resource")).exists()){
				return null;
			}
			dirResource = new SimpleFSDirectory(new File(indexDir+"_resource"));
		} 
		// 创建 IndexSearcher对象，相比IndexWriter对象，这个参数就要提供一个索引的目录就行了
		IndexSearcher indexSearch = new IndexSearcher(dirResource);
		//Analyzer analyzer = new IKAnalyzer();
		indexSearch.setSimilarity(new IKSimilarity());//在索引器中使用IKSimilarity相似度评估器 
		
		//添加过滤关键字
		List list = new ArrayList();
		//list.add("李"); 
		org.wltea.analyzer.dic.Dictionary.loadExtendStopWords(list);

		return indexSearch;
	}
	
	
	//得到全部的搜索构造器
	public static MultiSearcher getIndexSearcherAll() throws IOException{
		String[] index = {indexDir,indexDir+"_resource"};
		int length = index.length;
		IndexSearcher[] indexSearcher = new IndexSearcher[length];
		for(int i=0;i<length;i++){ 
			Directory dir = new SimpleFSDirectory(new File(index[i]));
			indexSearcher[i] = new IndexSearcher(dir);
		}
		MultiSearcher indexSearch = new MultiSearcher(indexSearcher);
//		// 创建 IndexSearcher对象，相比IndexWriter对象，这个参数就要提供一个索引的目录就行了
//		IndexSearcher indexSearch = new IndexSearcher(dir);
		//Analyzer analyzer = new IKAnalyzer();
		indexSearch.setSimilarity(new IKSimilarity());//在索引器中使用IKSimilarity相似度评估器 
		 
		//添加过滤关键字
		List list = new ArrayList();
		//list.add("李"); 
		org.wltea.analyzer.dic.Dictionary.loadExtendStopWords(list);

		return indexSearch;
	}
}
