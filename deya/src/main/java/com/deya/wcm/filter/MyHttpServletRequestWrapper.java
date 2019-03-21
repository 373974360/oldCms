package com.deya.wcm.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @Description:
 * @Author: like
 * @Date: 2017-09-07 16:37
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public MyHttpServletRequestWrapper(HttpServletRequest request)
            throws IOException {
        super(request);
        body = readBytes(request.getReader(),"utf-8");
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            public boolean isFinished() {
                return false;
            }


            public boolean isReady() {
                return false;
            }


            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    /**
     * 通过BufferedReader和字符编码集转换成byte数组
     * @param br
     * @param encoding
     * @return
     * @throws IOException
     */
    private byte[] readBytes(BufferedReader br,String encoding) throws IOException{
        String str,retStr="";
        while ((str = br.readLine()) != null) {
            retStr += str;
        }
        if (StringUtils.isNotBlank(retStr)) {
            return retStr.getBytes(Charset.forName(encoding));
        }
        return null;
    }
}
