package com.deya.util;

public class Test {
    public static void main(String[] args){
        String content = "<p style=\"text-align:center\">\n" +
                " <vsbvideo id=\"H8wxph\" height=\"280\" name=\"H8wxph\" type=\"application/x-shockwave-flash\" align=\"center\" width=\"420\" src=\"/system/resource/swf/flowplayer.commercial.swf\" data=\"/system/resource/swf/flowplayer.commercial.swf\" movie=\"/system/resource/swf/flowplayer.commercial.swf\" flashvars=\"config={&quot;playlist&quot;:[{&quot;url&quot;:&quot;/_mediafile/ycx/2018/09/11/16lon0n18u.mp4&quot;,&quot;scaling&quot;:&quot;fit&quot;,&quot;autoPlay&quot;:false,&quot;autoBuffering&quot;:false}]}\" wmode=\"opaque\" play=\"0\" quality=\"High\" bgcolor=\"#000000\" ue_src=\"/_mediafile/ycx/2018/09/11/16lon0n18u.mp4\" vsbautoplay=\"false\" vsburl=\"/_mediafile/ycx/2018/09/11/16lon0n18u.mp4\" vsbmp4video=\"true\" vautoplay=\"false\" vsbhrefname=\"\" vurl=\"/_mediafile/ycx/2018/09/11/16lon0n18u.mp4\" vheight=\"280\" vwidth=\"420\"></vsbvideo></p>";
        System.out.println(content.substring(content.indexOf("vurl")+6,content.indexOf("vautoplay")-2));
    }
}
