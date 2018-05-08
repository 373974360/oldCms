package com.deya.util.img;

/**
 * 图片处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 包括图片生成缩略图及添加水印</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.deya.util.HtmlRegexpUtil;
import com.deya.util.StringManager;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.gif4j.GifDecoder;
import com.gif4j.GifEncoder;
import com.gif4j.GifImage;
import com.gif4j.GifTransformer;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public final class ImageUtils_20150601 {
	
	private final static String img_onload_str = "imgReloadCicro(this)";
	
	public ImageUtils_20150601() {

	}

	/**
	 * 生成缩略图 jpg,png
	 * 
	 * @param fi
	 *            原文件
	 * @param fo
	 *            缩略图目标文件
	 * @param int 
	 * 			  宽           
	 * @return boolean
	 *            返回成功或失败
	 */
	public static boolean CreatetHumbnail(File fi,File fo,int nw)
	{
		try{
			AffineTransform transform = new AffineTransform();
			BufferedImage bis = ImageIO.read(fi);
			int w = bis.getWidth();//原来的长度
			int h = bis.getHeight();//原来的高度
			int nh = (nw*h)/w ;//新的高度
			double sx = (double)nw/w;//宽度比例
			double sy = (double)nh/h;//高度比例
			transform.setToScale(sx,sy);
			
//			AffineTransformOp ato = new AffineTransformOp(transform,null);
//			//BufferedImage bid = new BufferedImage(nw,nh,BufferedImage.TYPE_3BYTE_BGR);
//			BufferedImage bid = new BufferedImage(nw,nh,BufferedImage.TYPE_INT_RGB);
//			ato.filter(bis,bid); 
//			ImageIO.write(bid,"jpeg",fo);
			
			BufferedImage outputImage = new BufferedImage(nw,nh, BufferedImage.TYPE_INT_RGB);
			Graphics2D gd2 = outputImage.createGraphics();
			gd2.drawImage(bis, transform,null);
			gd2.dispose();
			ImageIO.write(outputImage,"png",fo);
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 生成gif格式缩略图
	 * 
	 * @param fi
	 *            水印文件 png
	 * @param fo
	 *            目标文件
	 * @param int 
	 * 			  宽             
	 * @return boolean
	 *            返回成功或失败
	 */
	public static boolean CreatetHumbnailByGif(File fi,File fo,int nw)
	{
		try{					
			BufferedImage bis = ImageIO.read(fi);		
			int w = bis.getWidth();//原来的长度
			int h = bis.getHeight();//原来的高度			
			int nh = (nw*h)/w ;//新的高度	            
			return CreatetHumbnailByGifHandl(fi, fo, nw, nh, true); 		
		}
		catch(Exception e)
		{
			return false;
		}
	}
	/**
	 * 生成gif格式缩略图操作方法
	 * @param pressImg
	 *            水印文件 png
	 * @param targetImg
	 *            目标文件
	 * @return boolean
	 *            返回成功或失败
	 * */
	public static boolean CreatetHumbnailByGifHandl(File srcImg, File destImg, int width,int height, boolean smooth) 
	{
		try {
			GifImage gifImage = GifDecoder.decode(srcImg);// 创建一个GifImage对象.			
			GifImage resizeIMG = GifTransformer.resize(gifImage, width, height,smooth);			
			GifEncoder.encode(resizeIMG, destImg);
			return true;
		} catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
	} 
	
	/**
	 * 判断图片是否为动态多帧的gif图片
	 * 
	 * @param targetImg
	 *            目标文件
	 * @return boolean 
	 */
	public static boolean isMovingGif(File srcImg)
    {
    	try{
    		GifImage gifImage = GifDecoder.decode(srcImg);// 创建一个GifImage对象.
			if(gifImage.getNumberOfFrames() > 1)
    			return true;
    		else
    			return false;	
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    		return false;
    	}
    }
	
	/**
	 * 给图片加上图片水印
	 * 
	 * @param pressImg
	 *            水印文件 png
	 * @param targetImg
	 *            目标文件
	 * @param position_type
	 *            目标位置 为1 到9，9个数字，分别对应9宫格的对应位置
	 * @param extName
	 *            文件后缀名    
	 * @param f
	 *            透明度       
	 */
	public final static void pressImage(String pressImg, File _file,
			int position_type,String extName,float f) {
		try {
			//判断目录文件是否是动态的GIf图片，如果是，不添加水印
    		if(".gif".equals(extName) && isMovingGif(_file))
    		{
    			System.out.println("pressImage:file is moving gif");
    			return;
    		}
			// 目标文件			
			BufferedImage image = ImageIO.read(_file);//new BufferedImage(wideth, height,BufferedImage.TYPE_INT_RGB);
			int width = image.getWidth();
			int height = image.getHeight();			
			Graphics g = image.createGraphics();
			// 水印文件
			File _filebiao = new File(pressImg);
			Image src_biao = ImageIO.read(_filebiao);
			int width_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);

			int x = 0;
			int y = 0;
			switch (position_type) {
			case 1:
				x = 0;
				y = 0;
				break;
			case 2:
				x = (width - width_biao) / 2;
				y = 0;
				break;
			case 3:
				x = width - width_biao;
				y = 0;
				break;
			case 4:
				x = 0;
				y = (height - height_biao) / 2;
				break;
			case 5:
				x = (width - width_biao) / 2;
				y = (height - height_biao) / 2;
				break;
			case 6:
				x = width - width_biao;
				y = (height - height_biao) / 2;
				break;
			case 7:
				x = 0;
				y = height - height_biao;
				break;
			case 8:
				x = (width - width_biao) / 2;
				y = height - height_biao;
				break;
			case 9:
				x = width - width_biao;
				y = height - height_biao;
				break;
			}

			g.drawImage(src_biao, x, y, width_biao, height_biao, null);
			// 水印文件结束 		
			 
			g.dispose();
			FileOutputStream out = new FileOutputStream(_file);
			
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);			
			JPEGEncodeParam   param   =   encoder.getDefaultJPEGEncodeParam(image); 
			param.setQuality(f,   false); 
			encoder.setJPEGEncodeParam(param);			
			encoder.encode(image);
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片加上文字水印
	 * 
	 * @param pressText
	 *            文字
	 * @param targetImg
	 *            目标图片
	 * @param fontName
	 *            字体名
	 * @param fontStyle
	 *            字体样式
	 * @param color
	 *            字体颜色
	 * @param fontSize
	 *            字体大小
	 * @param position_type
	 *            目标位置 为1 到9，9个数字，分别对应9宫格的对应位置
	 * @param alpha
	 *            透明度 0.0-1.1之间
	 */
	public static void pressText(String pressText, File _file,
			String fontName, int fontStyle, String color, int fontSize,
			int position_type, float alpha,String extName) {
		try {
//			判断目录文件是否是动态的GIf图片，如果是，不添加水印
    		if(".gif".equals(extName) && isMovingGif(_file))
    		{
    			System.out.println("pressText: file is moving gif");
    			return;
    		}			
			Image src = ImageIO.read(_file);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();

			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(Color.decode(color));
			g.setFont(new Font(fontName, fontStyle, fontSize));

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));

			int x = 0;
			int y = 0;
			switch (position_type) {
			case 1:
				x = 0;
				y = fontSize;
				break;
			case 2:
				x = (width - StringManager.getLength(pressText) * fontSize) / 2;
				y = fontSize;
				break;
			case 3:
				x = (width - StringManager.getLength(pressText) * fontSize) - fontSize;
				y = fontSize;
				break;
			case 4:
				x = 0;
				y = (height - fontSize) / 2;
				break;
			case 5:
				x = (width - StringManager.getLength(pressText) * fontSize) / 2;
				y = (height - fontSize) / 2;
				break;
			case 6:
				x = (width - StringManager.getLength(pressText) * fontSize) - fontSize;
				y = (height - fontSize) / 2;
				break;
			case 7:
				x = 0;
				y = height - 10;
				break;
			case 8:
				x = (width - StringManager.getLength(pressText) * fontSize) / 2;
				y = height - 10;
				break;
			case 9:
				x = (width - StringManager.getLength(pressText) * fontSize) - fontSize;
				y = height - 10;
				break;
			}

			g.drawString(pressText, x, y);
			g.dispose();
			FileOutputStream out = new FileOutputStream(_file);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			//System.out.println(e);
            e.printStackTrace();
		}
	}

	/**
	 * 处理信息内容中的图片标签，给图片标签添加onload属性，并根据标识判定远程图片是否上传
	 * 
	 * @param String content_str
	 *            信息内容字符串
	 * @param String is_remote_img
	 *            是否处理远程图片，0不处理　1将远程图片下载到本地
	 * @param String site_id
	 *            站点ID
	 * @param return
	 *            新的信息内容
	 */
	public static String processImgLable(String content_str,String is_remote_img,String site_id)
	{
		try{
			Pattern p = Pattern.compile(HtmlRegexpUtil.REGXPFORIMGTAG);
			Matcher m = p.matcher(content_str);
			while(m.find()){
	           String old_imgStr = m.group(0);
	           String new_imgStr = "";
	           //首先判断是否有onload属性
	           if(old_imgStr.contains("onload"))
	           {
	        	   //如果有，判断它的内容是否为imgReloadCicro(this)
	        	   String onloadStr = old_imgStr.replaceFirst(".*?onload=[\"|\']?([^\"|\'|\\s]+)[\"|\']?.*", "$1");
	        	   //如果不是，将它替换成onload="imgReloadCicro(this)"
	        	   if(onloadStr.equals(old_imgStr)){
	        		   onloadStr = old_imgStr.replaceFirst(".*?(onload=.*?)\\s.*", "$1");
	        		   new_imgStr = StringManager.replace(old_imgStr, onloadStr, "onload=\"" + img_onload_str + "\"");
	        		   content_str = StringManager.replace(content_str, old_imgStr, new_imgStr);
	        	   }
	        	   else
	        	   {
		        	   if(!img_onload_str.equals(onloadStr))
		        	   {
		        		   new_imgStr = StringManager.replace(old_imgStr, onloadStr, img_onload_str);
		        		   content_str = StringManager.replace(content_str, old_imgStr, new_imgStr);
		        	   }
	        	   }
	           }else{
	        	   //如果没有，添加此属性onload="imgReloadCicro(this)"
	        	   new_imgStr = old_imgStr.replaceAll("src", "onload=\""+img_onload_str+"\" src");
	        	   content_str = StringManager.replace(content_str, old_imgStr, new_imgStr);
	           }
	           //处理图片上传
	           if("1".equals(is_remote_img))
			   {
				   String old_srcStr = old_imgStr.replaceFirst(".*?src=[\"|\']?([^\"|\'|\\s]+)[\"|\']?.*", "$1");
				   //判断此图片路径地址是否为本服务器的域名
				   if(!old_srcStr.startsWith("http://"+JconfigUtilContainer.bashConfig().getProperty("domain", "", "resource_server")))
				   {
					   
				   }
				   //System.out.println(old_srcStr);
			   }
		   }		   
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return content_str;
	}
	
	/**
	 * 得到配置文件中图片的最大宽度
	 *	 	
	 * @param int
	 *            
	 */
	public static int getImgWidth()
	{
		return Integer.parseInt(JconfigUtilContainer.bashConfig().getProperty("image_width", "550", "resource_server"));
	}
	
	/**
	 * 得到配置文件中缩略图的设置宽度
	 *	 	 
	 * @param int
	 * 
	 */
	public static int getImgPreWidth()
	{
		return Integer.parseInt(JconfigUtilContainer.bashConfig().getProperty("image_pre_width", "100", "resource_server"));
	}

	public static void main(String[] args) {
		 File f = new File("D:\\yikangyuan.png");
		 File fout = new File("D:\\yikangyuan2.png");
		 CreatetHumbnail(f,fout, 200);
		//pressText("cicro.net", "c:\\p2.jpg", "隶书", Font.BOLD, "#FFFFFF", 42, 9,1.0f);
		//pressImage("c:\\Koala.JPG","c:\\Koala2.JPG",100);
		
		//processImgLable("123<img onload=\"\" src=http://img8.itiexue.net/1250/12504752.jpg width=\"100px\" height=\"100px\"/>456<img src=\"http://www.baidu.com/img/baidu_logo_jr_0215_yxj_9f82deab5c9a2d48d4c5af3605656856.gif\" width=\"100px\" height=\"100px\"/>789","1","aa");
		//System.out.println(processImgLable("123<img onload=\"ss\" src=http://img8.itiexue.net/1250/12504752.jpg width=\"100px\" height=\"100px\"/>456<img src=\"http://www.baidu.com/img/baidu_logo_jr_0215_yxj_9f82deab5c9a2d48d4c5af3605656856.gif\" width=\"100px\" height=\"100px\"/>789","0","aa"));
	}
}
