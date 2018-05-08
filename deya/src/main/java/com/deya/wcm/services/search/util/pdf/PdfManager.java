package com.deya.wcm.services.search.util.pdf;


import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PdfManager {

	public static String readFdf(String file){
		String content = "";  
		try{
			   FileInputStream   fis   =   new   FileInputStream(file); 
			   COSDocument cosDoc = null;
			   PDFParser parser = new PDFParser(fis);
			      parser.parse();
			   cosDoc = parser.getDocument();
			   PDFTextStripper stripper = new PDFTextStripper();
			   content = stripper.getText(new PDDocument(cosDoc)).replaceAll("  ","").replaceAll("[\\t\\n\\r]", " ");
			   content = replaceBlank(content); 
		}catch(Exception e){
			   e.printStackTrace();
			   //System.out.println("bb="+e.getMessage());
		  }finally{
			  return content;
		  }
    }

	
	//java去除字符串中的空格、回车、换行符、制表符
	public static String replaceBlank(String str) {  
		String dest = "";  
		if (str!=null) {  
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");  
			Matcher m = p.matcher(str);  
			dest = m.replaceAll("");  
		}  
		return dest;  
   }  


	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String content = PdfManager.readFdf("D:\\cicro\\TRS资料\\模板编辑器用户手册.pdf");
		System.out.println(content);
	}

}
