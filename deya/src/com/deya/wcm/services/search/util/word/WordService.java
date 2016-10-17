package com.deya.wcm.services.search.util.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;




public class WordService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         System.out.println(wordToString("D:\\cicro\\Eclipse8_work\\wcm\\WebRoot\\brower\\search\\201205140349059.doc"));
	}
	
	
	public static String wordToString(String path){
		String content = "";
		try{
			//String path = "src\\io\\wordtohtml\\linuxOS.docx";
			String uploadFilePath = path;
			if(path.endsWith(".doc") || path.endsWith(".docx")){
	         	if(path.endsWith(".doc")){
	         		//word 2003： 图片不会被读取   
	 	            InputStream is = new FileInputStream(new File(uploadFilePath));   
	 	            WordExtractor ex = new WordExtractor(is);    
	 	            String text2003 = ex.getText();   
	 	            content = text2003.replaceAll("", "");
	         	}else if(path.endsWith(".docx")){
	         		//word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后   
	 	            OPCPackage opcPackage = POIXMLDocument.openPackage(uploadFilePath);   
	 	            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);   
	 	            String text2007 = extractor.getText();   
	 	            content = text2007.replaceAll("", "");
	         	} 
	         	content = content.replaceAll("", "").replaceAll("", "").replaceAll("[\\t\\n\\r]", " ");
		   }
			return content;
		}catch (Exception e) {
			e.printStackTrace();
			return content;
		}
	}

}
