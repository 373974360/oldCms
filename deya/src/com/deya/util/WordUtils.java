package com.deya.util;

import java.io.*;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * word 转换成html
 */
public class WordUtils {

    public static void main(String[] args){
        System.out.println(Word2003ToHtml("/Users/chaoweima/Downloads/","2018版-信息协同接口设计-外部协作的信息协同接口1.0.doc"));
    }

    /**
     * 2007版本word转换成html
     */
    public static String Word2007ToHtml(String filepath, String fileName) {
        try {
            final String file = filepath + fileName;
            //String htmlName = "test.html";
            File f = new File(file);
            if (!f.exists()) {
                System.out.println("Sorry File does not Exists!");
            } else {
                if (f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX")) {

                    // 1) 加载word文档生成 XWPFDocument对象
                    InputStream in = new FileInputStream(f);
                    XWPFDocument document = new XWPFDocument(in);

                    // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
                    File imageFolderFile = new File(filepath);
                    XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));
                    options.setExtractor(new FileImageExtractor(imageFolderFile));
                    options.setIgnoreStylesIfUnused(false);
                    options.setFragment(true);

                    // 3) 将 XWPFDocument转换成XHTML
                    //OutputStream out = new FileOutputStream(new File(filepath + htmlName));
                    //XHTMLConverter.getInstance().convert(document, out, options);

                    // 3) 使用字符数组流获取解析的内容
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    XHTMLConverter.getInstance().convert(document, baos, options);
                    String content = baos.toString();
                    baos.close();
                    return content;
                } else {
                    System.out.println("Enter only MS Office 2007+ files");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String Word2003ToHtml(String filepath, String fileName) {
        try {
            final String imagepath = filepath+"image/";
            //String htmlName = "滕王阁序2003.html";
            final String file = filepath + fileName;
            InputStream input = new FileInputStream(new File(file));
            HWPFDocument wordDocument = new HWPFDocument(input);
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            //设置图片存放的位置
            wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                    File imgPath = new File(imagepath);
                    if (!imgPath.exists()) {//图片目录不存在则创建
                        imgPath.mkdirs();
                    }
                    File file = new File(imagepath + suggestedName);
                    try {
                        OutputStream os = new FileOutputStream(file);
                        os.write(content);
                        os.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return imagepath + suggestedName;
                }
            });

            //解析word文档
            wordToHtmlConverter.processDocument(wordDocument);
            Document htmlDocument = wordToHtmlConverter.getDocument();

            //File htmlFile = new File(filepath + htmlName);
            //OutputStream outStream = new FileOutputStream(htmlFile);

            //使用字符数组流获取解析的内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStream outStream = new BufferedOutputStream(baos);

            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(outStream);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer serializer = factory.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");

            serializer.transform(domSource, streamResult);

            //也可以使用字符数组流获取解析的内容
            String content = baos.toString();
            baos.close();
            outStream.close();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}