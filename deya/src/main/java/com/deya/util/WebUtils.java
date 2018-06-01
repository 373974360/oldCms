package com.deya.util;


// WebUtils.java
// Andrew Davison, May 2009, ad@fivedots.coe.psu.ac.th

/* Various useful methods used for querying, parsing, and
   downloading images from Web sites.

   The methods fall into 3 groups:
        * methods for querying the web
        * DOM methods for parsing and printing
        * methods for downloading and saving a PNG image

*/

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;



public class WebUtils
{
    // --------------------------- query the web -------------------------------

    public static String webGetString(String urlStr)
    // contact the specified URL, and return the response as a string
    {
        //System.out.println("Contacting \"" + urlStr + "\"");
        try {
            URL url = new URL(urlStr);

            String line;
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream(), "UTF-8"));
            while((line = reader.readLine()) != null)
                sb.append(line);
            reader.close();
            return sb.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println(e);
            System.exit(1);
        }

        return null;
    }  // end of webGetString()



    public static InputStream webGet(String urlStr)
    // contact the specified URL, and return the response stream
    {
        //System.out.println("Contacting \"" + urlStr + "\"");
        try {
            URL url = new URL(urlStr);
            return url.openStream();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println(e);
            System.exit(1);
        }

        return null;
    }  // end of webGet()



    public static void saveString(String fnm, String str)
    // save the string str into the file fnm
    {
        try {
            FileWriter fw = new FileWriter(fnm);
            fw.write(str + "\n");
            fw.close();
            //System.out.println("Saved to " + fnm);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            //System.out.println("Could not write to " + fnm);
        }
    }  // end of saveString()



    // ---------------------------- DOM parsing and printing --------------------


    public static Document parse(String fileName)
    // Parse the XML file and create a Document
    {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);  // inportant

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File(fileName));
            return document;
        }
        catch (SAXParseException spe) {    // Error generated by the parser
            //System.out.println("\n** Parsing error , line " + spe.getLineNumber() + ", uri " + spe.getSystemId());
            //System.out.println(" " + spe.getMessage());
            // Use the contained exception, if any
            Exception x = spe;
            if (spe.getException() != null)
                x = spe.getException();
            x.printStackTrace();
        }
        catch (SAXException sxe) {  // Error generated during parsing
            Exception x = sxe;
            if (sxe.getException() != null)
                x = sxe.getException();
            x.printStackTrace();
        }
        catch (ParserConfigurationException pce) {
            // Parser with specified options can't be built
            pce.printStackTrace();
        }
        catch (IOException ioe)
        {ioe.printStackTrace();  }

        return null;
    }  // end of parse() for a file




    public static Document parse(InputStream is)
    // Parse the XML input stream and create a Document
    {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(is);
            return document;
        }
        catch (SAXParseException spe) {    // Error generated by the parser
            //System.out.println("\n** Parsing error , line " + spe.getLineNumber() + ", uri " + spe.getSystemId());
            //System.out.println(" " + spe.getMessage());
            // Use the contained exception, if any
            Exception x = spe;
            if (spe.getException() != null)
                x = spe.getException();
            x.printStackTrace();
        }
        catch (SAXException sxe) {  // Error generated during parsing
            Exception x = sxe;
            if (sxe.getException() != null)
                x = sxe.getException();
            x.printStackTrace();
        }
        catch (ParserConfigurationException pce) {
            // Parser with specified options can't be built
            pce.printStackTrace();
        }
        catch (IOException ioe)
        {ioe.printStackTrace();  }

        return null;
    }  // end of parse() for an input stream




    public static void saveDoc(Document doc, String fnm)
    // pretty print XML document to the file fnm
    {
        try {
            Transformer xformer = TransformerFactory.newInstance().newTransformer();

            //Setup indenting to "pretty print"
            xformer.setOutputProperty(OutputKeys.INDENT, "yes");
            xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // Write document to the file
            //System.out.println("Saving document in " + fnm);
            xformer.transform(new DOMSource(doc), new StreamResult(new File(fnm)));
        }
        catch (TransformerConfigurationException e)
        {
            e.printStackTrace();
            //System.out.println("TransformerConfigurationException: " + e);
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
            //System.out.println("TransformerException: " + e);
        }
    }  // end of saveDoc()



    public static void listDoc(Document doc)
    // pretty print XML document to stdout
    {
        try {
            Transformer xformer = TransformerFactory.newInstance().newTransformer();

            //Setup indenting to "pretty print"
            xformer.setOutputProperty(OutputKeys.INDENT, "yes");
            xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // Write document to stdout
            //System.out.println("------------------------------------------------");
            xformer.transform(new DOMSource(doc), new StreamResult(System.out));
            //System.out.println("------------------------------------------------");

        }
        catch (TransformerConfigurationException e)
        {
            e.printStackTrace();
            //System.out.println("TransformerConfigurationException: " + e);
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
            //System.out.println("TransformerException: " + e);
        }
    }  // end of listDoc() for stdout



    public static String getElemText(Element elem, String tagName)
    // return the text of the first element with the name 'tagName' below elem
    {
        NodeList nodeList = elem.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0)
            return nodeList.item(0).getFirstChild().getNodeValue();
        return null;
    }  // end of getElemText()



    // ------------------- image downloading ----------------------------------


    public static void downloadImage(String urlStr)
    // download the image at urlStr, and save it as a PNG file
    {
        //System.out.println("Downloading image at:\n\t" + urlStr);
        URL url = null;
        BufferedImage image = null;
        try {
            url = new URL(urlStr);
            image = ImageIO.read(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            //System.out.println("Problem downloading");
        }

        if (image != null) {
            String fnm = urlStr.substring( urlStr.lastIndexOf("/")+1 );
            savePNG(fnm, image);
        }
    }  // end of downloadImage()



    private static void savePNG(String fnm, BufferedImage im)
    // save the image as a PNG in <fnm>Cover.png
    {
        int extPosn = fnm.lastIndexOf(".");
        String pngFnm;
        if (extPosn == -1)
            pngFnm = "cover.png";
        else
            pngFnm = fnm.substring(0, extPosn) + "Cover.png";
        //System.out.println("Saving image to " + pngFnm);

        try {
            ImageIO.write(im, "png", new File(pngFnm));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            //System.out.println("Could not save file");
        }
    }  // end of savePNG()



}  // end of WebUtils class