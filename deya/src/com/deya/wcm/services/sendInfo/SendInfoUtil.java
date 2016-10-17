package com.deya.wcm.services.sendInfo;

 
 

public class SendInfoUtil {
	
	/**@
	 * 编译文件 FileToBase64
	 * @param path 编译的目标地址 【例：D://test/1.jpg】
	 * @return
	 */
	public static String encodeBase64File(String path) 
	{  
		String str = "";
	  try {
		  java.io.File  file = new java.io.File(path);  
		  java.io.FileInputStream inputFile;
				  inputFile = new java.io.FileInputStream(file);
		          byte[] buffer = new byte[(int)file.length()];  
	              inputFile.read(buffer);  
		          inputFile.close();  
		          str = new sun.misc.BASE64Encoder().encode(buffer); 
		   } catch (java.io.FileNotFoundException e) {
					e.printStackTrace();
		   } catch (java.io.IOException e) {
					e.printStackTrace();
		   } 
		   return str; 
	 }
	/**@
	 * 写文件 Base64ToFile
	 * @param base64Code  加密码文件
	 * @param targetPath  创建文件的目标路径
	 */

	 public static void decoderBase64File(String base64Code,String filePath,String fileName)
	 {  
		    byte[] buffer;
			try {
				java.io.File file = new java.io.File(filePath);
			       //System.out.println("正在创建目录...");
					if (!file.exists())
						file.mkdirs();
				   //System.out.println("创建目录成功..."+filePath);
				   //System.out.println("正在写入文件..."+fileName);
				  buffer = new sun.misc.BASE64Decoder().decodeBuffer(base64Code);
				  java.io.FileOutputStream out = new java.io.FileOutputStream(filePath+fileName);  
		          out.write(buffer);  
		          out.close();  
		          //System.out.println("写入文件成功..."+fileName);
			} catch (java.io.IOException e) {
				e.printStackTrace();
				//System.out.println("写入文件失败...");
			}  
     }
    public static void main(String[] args){
    }
}
