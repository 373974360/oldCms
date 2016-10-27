<%@ page import="java.awt.Color,java.awt.Font,java.awt.Graphics,java.awt.image.BufferedImage,java.io.IOException,java.util.Random" %>
<%@ page import="javax.imageio.ImageIO,com.deya.util.RandomStrg" %>
<%	
//设置页面不缓存
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
try{
	// 设置图片的长宽
	int width = 62, height = 22;

	// ////// 创建内存图像
	BufferedImage image = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_RGB);
	// 获取图形上下文
	Graphics g = image.createGraphics();

	// 设定图像背景色(因为是做背景，所以偏淡)
	g.setColor(getRandColor(180, 250));
	g.fillRect(0, 0, width, height);
	// 设置字体
	g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
	
	String rstr = "";	
	RandomStrg rst = new RandomStrg();
	rst.setCharset("0-9");
	rst.setLength("2");
	rst.generateRandomObject();
	rstr = rst.getRandom();

	// 取随机产生的认证码(2位数字)
	String sRand = "";
	sRand = rstr;
		
	String rand = "";
	int count = 0;
	for (int i = 0; i < sRand.length(); i++) {
		count += Integer.parseInt(String.valueOf(rstr.charAt(i)));
		rand += String.valueOf(rstr.charAt(i));
		if(i==0)
			rand += " + ";
		
		// 生成随机颜色(因为是做前景，所以偏深)
		g.setColor(getRandColor(10, 150));

		// 将此字画到图片上
		// g.drawString(str.toString(), 4, 17);	
	}
	rand = rand+" = ?";
	g.drawString(rand, 0, 21);
	
	// 将认证码存入session
	request.getSession().setAttribute("valiCode", count+"");

	// 图象生效
	g.dispose();

	// 输出图象到页面
	ImageIO.write(image, "JPEG", response.getOutputStream());

	out.clear();   
	out = pageContext.pushBody();
} catch (Exception e) {
	e.printStackTrace(System.out);
}
%>
<%!
public void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
	this.doGet(request, response);
}

// 给定范围获得一个随机颜色
Color getRandColor(int fc, int bc) {
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
%>
