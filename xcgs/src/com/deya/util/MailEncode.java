package com.deya.util;

/**
 * <p>Title: Mail邮件转码类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @version 1.0
 */

public class MailEncode {

  public MailEncode() {
  }
  /**
   * 邮件转码类
   * @param String 转码前字串
   * @return 转码后字串
   * */
  public static String encode(String s)
  {
      return encode(s.getBytes());
  }

  /**
   * 邮件转码类
   * @param byte 转码前字串
   * @return 转码后字串
   * */
  public static String encode(byte abyte0[])
  {
      char ac[] = new char[((abyte0.length - 1) / 3 + 1) * 4];
      int k1 = 0;
      int l1;
      for(l1 = 0; l1 + 3 <= abyte0.length;)
      {
          int i = (abyte0[l1++] & 0xff) << 16;
          i |= (abyte0[l1++] & 0xff) << 8;
          i |= (abyte0[l1++] & 0xff) << 0;
          int l = (i & 0xfc0000) >> 18;
          ac[k1++] = alphabet[l];
          l = (i & 0x3f000) >> 12;
          ac[k1++] = alphabet[l];
          l = (i & 0xfc0) >> 6;
          ac[k1++] = alphabet[l];
          l = i & 0x3f;
          ac[k1++] = alphabet[l];
      }

      if(abyte0.length - l1 == 2)
      {
          int j = (abyte0[l1] & 0xff) << 16;
          j |= (abyte0[l1 + 1] & 0xff) << 8;
          int i1 = (j & 0xfc0000) >> 18;
          ac[k1++] = alphabet[i1];
          i1 = (j & 0x3f000) >> 12;
          ac[k1++] = alphabet[i1];
          i1 = (j & 0xfc0) >> 6;
          ac[k1++] = alphabet[i1];
          ac[k1++] = '=';
      } else
      if(abyte0.length - l1 == 1)
      {
          int k = (abyte0[l1] & 0xff) << 16;
          int j1 = (k & 0xfc0000) >> 18;
          ac[k1++] = alphabet[j1];
          j1 = (k & 0x3f000) >> 12;
          ac[k1++] = alphabet[j1];
          ac[k1++] = '=';
          ac[k1++] = '=';
      }
      return new String(ac);
  }

  public static final char alphabet[] = {
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
      'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
      'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
      'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
      'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
      'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
      '8', '9', '+', '/'
  };
  
  public static void main(String args[]) {
	  System.out.println(encode("000"));
  }
}
