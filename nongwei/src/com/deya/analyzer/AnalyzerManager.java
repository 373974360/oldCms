package com.deya.analyzer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;

import com.deya.analyzer.lucene.IKAnalyzer;
import com.deya.util.HtmlRegexpUtil;
import com.deya.util.io.FileOperation;

public class AnalyzerManager {

	
	//得到标题中的分词
	public static List getWordsByTile(String title){
		 List list = new ArrayList();
		 try{
			    IKAnalyzer ka = new  IKAnalyzer(true);
				Reader r = new StringReader(title);
				TokenStream ts = (TokenStream)ka.tokenStream("title", r);
				ts.addAttribute(TermAttribute.class);
		        while (ts.incrementToken()) {
		               TermAttribute ta =ts.getAttribute(TermAttribute.class);
		               String str = ta.term();
		               if(!list.contains(str)){
		                   list.add(str);
		               }
		        }
		        return list;
			 }catch (Exception e) {
				e.printStackTrace();
				return list;
			}
	   }
	
	/* 词频统计     
	 * @param text 需要统计的文本    
	 * @param top   返回高频率词汇的个数  
	 *  @return 高频词汇列表     
	 *   */   
	public static List<Map> findMaxOfenWord(String text,int top){         
		Map<String,Integer> words=new HashMap<String,Integer>();      
		IKSegmentation seg = new IKSegmentation(new StringReader(text) , true);       
		try {            
			Lexeme l = null;         
			while( (l = seg.next()) != null){          
				if(words.containsKey(l.getLexemeText()))           
					words.put(l.getLexemeText(), words.get(l.getLexemeText())+1);      
				else         
					words.put(l.getLexemeText(), 1);       
				}      
			}         
		catch (IOException e){
			e.printStackTrace();   
		}        
		int max=0;         
		String maxKey=null;         
		List<Map> ofenwords=new ArrayList<Map>();        
		for(int i=0;i<top&&i<words.size();i++){         
			for(String key:words.keySet()){         
				if(words.get(key)>max){           
					max=words.get(key);        
					maxKey=key;               
					}          
				}           
			  
			Map map = new HashMap();
			map.put(maxKey, words.get(maxKey));
			ofenwords.add(map);
			
			max=0;          
			words.put(maxKey, -1); 
			
			}         
		return ofenwords;  
		} 
	
	public static String getWordsByTitleAndText(String title,String content){
		StringBuffer sb = new StringBuffer("");
		try{
			   content = HtmlRegexpUtil.filterHtml(content);
			   Map mapWords = new HashMap();
			   List<Map> list = findMaxOfenWord(content,10);
			   for(Map map : list){
				  Iterator it = map.keySet().iterator();
				  if(it.hasNext()){
					  String key = (String)it.next();
					  int value = (Integer)map.get(key);
					  mapWords.put(key, value);
				  }
			   }
			   
			   Map<String,Integer> resultMap = new HashMap<String,Integer>();
			   List listTitle = getWordsByTile(title);
			   for(int i=0;i<listTitle.size();i++){
				   String t = (String)listTitle.get(i);
				   if(mapWords.containsKey(t)){
					   resultMap.put(t, (Integer)mapWords.get(t));
				   }
			   }
			   
			   List<Map.Entry<String, Integer>> info = SorterMap.sortMapValue(resultMap);
			   int size = info.size()>3?3:info.size();
			   for (int j = 0; j<size;j++) {
					sb.append(info.get(j).getKey()+" ");
			   }
			   
			return sb.toString();
		}catch (Exception e) {
			e.printStackTrace();
			return sb.toString();
		}
	}
	
	
	public static String getWordsByTitle(String s){
		StringBuffer sb = new StringBuffer("");
		try{
				//s = "外地城市用工单位招聘信息";
			    List list = new ArrayList();
			    IKAnalyzer ka = new  IKAnalyzer(true);
				Reader r = new StringReader(s);
				TokenStream ts = (TokenStream)ka.tokenStream("title", r);
				ts.addAttribute(TermAttribute.class);
		        while (ts.incrementToken()) {
		               TermAttribute ta =ts.getAttribute(TermAttribute.class);
		               String str = ta.term();
		               if(!list.contains(str)){
		                   list.add(str);
		                   if(str.length()>=2){
		                	   sb.append(str+" ");
		                   }
		               }
		        }
		        return sb.toString();
		 }catch (Exception e) {
				e.printStackTrace();
				return sb.toString();
		}
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String title = "统计局：2012年2月份CPI同比涨3.2% PPI同比持平  ";
//		String content = FileOperation.readFileToString("D:\\cicro\\Eclipse8_work\\IKAnalyzer\\src\\text2.txt");
//		String streing = getWordsByTitleAndText(title,content); 
//		System.out.println("-----------------------------------------------------------------");
//		System.out.println(streing);
		
		String streing = getWordsByTitle(title); 
		System.out.println(streing);
		
//		System.out.println("111111111111");
//		String fileRoot = new AnalyzerManager().getClass().getClassLoader().getResource("/").getPath();
//		System.out.println(fileRoot);
		
		List<Map> map = findMaxOfenWord(title,10);
		System.out.println(map);
	}

}
