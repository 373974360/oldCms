package com.deya.wcm.services.search.index;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
 
import com.deya.wcm.services.search.SearchForInterface;

public class IndexLuceneManager {

	// 保存索引文件的地方
	static String indexDir = SearchForInterface.getIndexPathRoot();
	static Directory dir = null;
	
	static{
		
	}
	
	//初始化IndexWriter
	public static void initIndex(){
		File file = new File(indexDir);
		if(!file.exists()){
			file.mkdir();
		}
		//初始化IndexWriter
		try {
			getIndexModifier(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//得到IndexWriter
	public static IndexWriter getIndexModifier(boolean isNew) throws CorruptIndexException, LockObtainFailedException, IOException{
		if(dir==null){
			// 创建Directory对象	
			dir = new SimpleFSDirectory(new File(indexDir));
			if(!isNew){ 
				File file = new File(indexDir);
				if(!file.exists()){
					file.mkdir(); 
					initIndex();//用于没有在站群中对lucene初始化  所以在用之前必须要初始化
				}
			}
		}
		
		//判断/cicro/wcm/search/write.lock是否存在  如果存在就删掉
		File lockFile = new File(indexDir+File.separator+"write.lock"); 
		System.out.println("lockFile-----" + lockFile);
		if(lockFile.exists()){
			System.out.println("lockFile delete -----" + lockFile);
			lockFile.deleteOnExit();
		}
		
		// 创建IndexWriter对象,第一个参数是Directory,第二个是分词器,第三个表示是否是创建,如果为false为在此基础上面修改,第四表示表示分词的最大值，比如说new
		// MaxFieldLength(2)，就表示两个字一分，一般用IndexWriter.MaxFieldLength.LIMITED 
		//IndexWriter indexWriter = new IndexWriter(dir, new org.wltea.analyzer.lucene.IKAnalyzer(), isNew, IndexWriter.MaxFieldLength.UNLIMITED);
		IndexWriter indexWriter = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30), isNew, IndexWriter.MaxFieldLength.UNLIMITED);
		//SetMaxMergeDocs是控制一个segment中可以保存的最大document数目，值较小有利于追加索引的速度，默认Integer.MAX_VALUE，无需修改
		//indexWriter.setMaxMergeDocs(Integer.MAX_VALUE);
		indexWriter.setMergeFactor(10);//参数值较小的时候，创建索引的速度较慢。当参数值较大的时候，创建索引的速度就比较快。大于10适合批量创建索引
		indexWriter.setRAMBufferSizeMB(500);//该方法可以设置更新文档使用的内存达到指定大小之后才写入硬盘，这样可以提高写索引的速度，尤其是在批量建索引的时候
		if(isNew){  
			indexWriter.close();
		}
		return indexWriter; 
	}
	
	//关闭IndexWriter
	public static void closeIndexWriter(IndexWriter indexWriter){
		try {
			if(indexWriter!=null){
				indexWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/*
	* Field.Store.COMPRESS:压缩保存,用于长文本或二进制数据 
    * Field.Store.YES:保存
    * Field.Store.NO:不保存
    * 
    * Field.Index.NO:不建立索引 
    * Field.Index.TOKENIZED:分词,建索引
    * Field.Index.UN_TOKENIZED:不分词,建索引
    */
	//写索引
	public static void AddDocument(IndexWriter indexWriter,Document doc){
		try{
			indexWriter.addDocument(doc);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//删索引
	public static void DeleteDocument(IndexWriter indexWriter,Term term){
		try{
			indexWriter.deleteDocuments(term);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String arr[]){

	}
}
