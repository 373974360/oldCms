package com.deya.util.img;

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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;

import com.deya.util.StringManager;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.server.ServerManager;
import com.gif4j.GifDecoder;
import com.gif4j.GifEncoder;
import com.gif4j.GifImage;
import com.gif4j.GifTransformer;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public final class ImageUtils
{
	private static String GM_PATH = JconfigUtilContainer.bashConfig().getProperty("path", "", "gm_path"); 
	
  public static boolean CreatetHumbnail(File fi, File fo, int nw)
  {
    try
    {
      AffineTransform transform = new AffineTransform();
      BufferedImage bis = ImageIO.read(fi);
      int w = bis.getWidth();
      int h = bis.getHeight();
      int nh = nw * h / w;
      double sx = nw / w;
      double sy = nh / h;
      transform.setToScale(sx, sy);

      AffineTransformOp ato = new AffineTransformOp(transform, null);
      BufferedImage bid = new BufferedImage(nw, nh, 5);
      ato.filter(bis, bid);
      ImageIO.write(bid, "jpeg", fo);
      return true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }return false;
  }

  public static boolean CreatetHumbnailByGif(File fi, File fo, int nw)
  {
    try
    {
      BufferedImage bis = ImageIO.read(fi);
      int w = bis.getWidth();
      int h = bis.getHeight();
      int nh = nw * h / w;
      return CreatetHumbnailByGifHandl(fi, fo, nw, nh, true);
    }
    catch (Exception localException) {
    }
    return false;
  }

  public static boolean CreatetHumbnailByGifHandl(File srcImg, File destImg, int width, int height, boolean smooth)
  {
    try
    {
      GifImage gifImage = GifDecoder.decode(srcImg);
      GifImage resizeIMG = GifTransformer.resize(gifImage, width, height, smooth);
      GifEncoder.encode(resizeIMG, destImg);
      return true;
    }
    catch (IOException e) {
      e.printStackTrace();
    }return false;
  }

  public static boolean isMovingGif(File srcImg)
  {
    try
    {
      GifImage gifImage = GifDecoder.decode(srcImg);
      if (gifImage.getNumberOfFrames() > 1) {
        return true;
      }
      return false;
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }return false;
  }

  public static final void pressImage(String pressImg, File _file, int position_type, String extName, float f)
  {
    try
    {
      if ((".gif".equals(extName)) && (isMovingGif(_file)))
      {
        System.out.println("pressImage:file is moving gif");
        return;
      }

      BufferedImage image = ImageIO.read(_file);
      int width = image.getWidth();
      int height = image.getHeight();
      Graphics g = image.createGraphics();

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
      }

      g.drawImage(src_biao, x, y, width_biao, height_biao, null);

      g.dispose();
      FileOutputStream out = new FileOutputStream(_file);

      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
      JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
      param.setQuality(f, false);
      encoder.setJPEGEncodeParam(param);
      encoder.encode(image);
      out.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void pressText(String pressText, File _file, String fontName, int fontStyle, String color, int fontSize, int position_type, float alpha, String extName)
  {
    try
    {
      if ((".gif".equals(extName)) && (isMovingGif(_file)))
      {
        System.out.println("pressText: file is moving gif");
        return;
      }
      Image src = ImageIO.read(_file);
      int width = src.getWidth(null);
      int height = src.getHeight(null);
      BufferedImage image = new BufferedImage(width, height, 
        1);
      Graphics2D g = image.createGraphics();

      g.drawImage(src, 0, 0, width, height, null);
      g.setColor(Color.decode(color));
      g.setFont(new Font(fontName, fontStyle, fontSize));

      g.setComposite(AlphaComposite.getInstance(10, 
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
        x = width - StringManager.getLength(pressText) * fontSize - fontSize;
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
        x = width - StringManager.getLength(pressText) * fontSize - fontSize;
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
        x = width - StringManager.getLength(pressText) * fontSize - fontSize;
        y = height - 10;
      }

      g.drawString(pressText, x, y);
      g.dispose();
      FileOutputStream out = new FileOutputStream(_file);
      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
      encoder.encode(image);
      out.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static String processImgLable(String content_str, String is_remote_img, String site_id)
  {
    try
    {
      Pattern p = Pattern.compile("<\\s*img\\s+([^>]*)\\s*>");
      Matcher m = p.matcher(content_str);
      while (m.find()) {
        String old_imgStr = m.group(0);
        String new_imgStr = "";

        if (old_imgStr.contains("onload"))
        {
          String onloadStr = old_imgStr.replaceFirst(".*?onload=[\"|']?([^\"|'|\\s]+)[\"|']?.*", "$1");

          if (onloadStr.equals(old_imgStr)) {
            onloadStr = old_imgStr.replaceFirst(".*?(onload=.*?)\\s.*", "$1");
            new_imgStr = StringManager.replace(old_imgStr, onloadStr, "onload=\"imgReloadCicro(this)\"");
            content_str = StringManager.replace(content_str, old_imgStr, new_imgStr);
          }
          else if (!"imgReloadCicro(this)".equals(onloadStr))
          {
            new_imgStr = StringManager.replace(old_imgStr, onloadStr, "imgReloadCicro(this)");
            content_str = StringManager.replace(content_str, old_imgStr, new_imgStr);
          }
        }
        else
        {
          new_imgStr = old_imgStr.replaceAll("src", "onload=\"imgReloadCicro(this)\" src");
          content_str = StringManager.replace(content_str, old_imgStr, new_imgStr);
        }

        if ("1".equals(is_remote_img))
        {
          String old_srcStr = old_imgStr.replaceFirst(".*?src=[\"|']?([^\"|'|\\s]+)[\"|']?.*", "$1");

          old_srcStr.startsWith("http://" + JconfigUtilContainer.bashConfig().getProperty("domain", "", "resource_server"));

        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return content_str;
  }

  public static int getImgWidth()
  {
    return Integer.parseInt(JconfigUtilContainer.bashConfig().getProperty("image_width", "550", "resource_server"));
  }

  public static int getImgPreWidth()
  {
    return Integer.parseInt(JconfigUtilContainer.bashConfig().getProperty("image_pre_width", "100", "resource_server"));
  }

  public static void cutImage(int width, String srcPath, String newPath)
    throws Exception
  {
    IMOperation op = new IMOperation();
    op.addImage(srcPath);
    op.resize(Integer.valueOf(width), null);
    op.sharpen(1.0, 3.0);
    op.quality(100d);
    op.addImage(newPath);

      ConvertCmd cmd = new ConvertCmd(true);
    
    if(ServerManager.isWindows())
    {
    	cmd.setSearchPath(GM_PATH);
    }

    cmd.setErrorConsumer(StandardStream.STDERR);
    try{
        cmd.run(op);
    }
    catch (Exception e){
        e.printStackTrace();
    }
  }

  public static void addImgText(String syPath, String srcPath, int position, String extName, int alpha)
  {
    try
    {
      if ((".gif".equals(extName)) && (isMovingGif(new File(srcPath))))
      {
        System.out.println("pressText: file is moving gif");
        return;
      }
      IMOperation op = new IMOperation();
      String post = "SouthEast";
      switch (position)
      {
      case 1:
        post = "NorthWest";
        break;
      case 2:
        post = "North";
        break;
      case 3:
        post = "NorthEast";
        break;
      case 4:
        post = "West";
        break;
      case 5:
        post = "Center";
        break;
      case 6:
        post = "East";
        break;
      case 7:
        post = "SouthWest";
        break;
      case 8:
        post = "South";
        break;
      case 9:
        post = "SouthEast";
      }
      File file = new File(syPath);
      BufferedImage sourceImg = ImageIO.read(new FileInputStream(file));
      op.gravity(post).geometry(Integer.valueOf(sourceImg.getWidth()), Integer.valueOf(sourceImg.getHeight()), Integer.valueOf(10), Integer.valueOf(5)).dissolve(Integer.valueOf(alpha));
      op.quality(200d);
      op.addImage(syPath);
      op.addImage(srcPath);
      op.addImage(srcPath);

      CompositeCmd cmd = new CompositeCmd(true);
      
      if(ServerManager.isWindows())
      {
      	cmd.setSearchPath(GM_PATH);
      }

      cmd.setErrorConsumer(StandardStream.STDERR);
      cmd.run(op);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IM4JavaException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args)
  {
    System.out.println(processImgLable("123<img onload=\"ss\" src=http://img8.itiexue.net/1250/12504752.jpg width=\"100px\" height=\"100px\"/>456<img src=\"http://www.baidu.com/img/baidu_logo_jr_0215_yxj_9f82deab5c9a2d48d4c5af3605656856.gif\" width=\"100px\" height=\"100px\"/>789", "0", "aa"));
  }
}