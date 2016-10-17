package com.deya.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * <p>生成验证码</p>
 * <p>servlet 生成图片数据流并发送到前台</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Cicro</p>
 */

public class GetAuthImage extends HttpServlet {
	/*序列化是为了保持版本的兼容性，即在版本升级时反序列化仍保持对象的唯一性。
	可以随便写一个，在Eclipse中它替你生成一个，有两种生成方式
    * */
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
		System.setProperty("java.awt.headless", "true");
	}
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			res.setContentType("image/jpeg");
			res.setHeader("Pragma", "No-cache");
			res.setHeader("Cache-Control", "no-cache");
			res.setDateHeader("Expires", 0);
			HttpSession session = req.getSession();

			// 在内存中创建图象

			int width = 100, height = 25;
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			// 获取图形上下文
			Graphics g = image.getGraphics();
			// 生成随机类
			Random random = new Random();
			// 设定背景色
			g.setColor(getRandColor(250, 255));
			g.fillRect(0, 0, width, height);

			// 设定字体
			g.setFont(new Font("Times New Roman", Font.BOLD|Font.ITALIC, 22));

			// 画边框
			// g.setColor(new Color());
			// g.drawRect(0,0,width-1,height-1);
			// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
			g.setColor(getRandColor(240, 255));
			for (int i = 0; i < 10; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
			String rstr = "";
			RandomStrg rst = new RandomStrg();
			rst.setCharset("0-9");
			rst.setLength("4");

			rst.generateRandomObject();
			rstr = rst.getRandom();

			// 取随机产生的认证码(4位数字)
			String sRand = "";
			sRand = rstr;
			for (int i = 0; i < sRand.length(); i++) {
				// String rand=String.valueOf(random.nextInt(10));
				String rand = String.valueOf(rstr.charAt(i));
				// sRand+=rand;
				// 将认证码显示到图象中
				g.setColor(new Color(20 + random.nextInt(110), 20 + random
						.nextInt(110), 20 + random.nextInt(110)));
				// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
				g.drawString(rand, 20 * i + 6, 21);
			}

			// 将认证码存入SESSION
			session.setAttribute("AUTHCODE", sRand);

			// 图象生效
			g.dispose();

			// 输出图象到页面
			ImageIO.write(image, "JPEG", res.getOutputStream());
			// JPEGImageEncoder encoder =
			// JPEGCodec.createJPEGEncoder(res.getOutputStream());
			// encoder.encode(image);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}

	// 给定范围获得随机颜色
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
