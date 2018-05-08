import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @Description:
 * @User: Administrator
 * @Date: 2018/5/8 0008
 */
public class JavaToPdfCN {
    private static final String DEST = "D:/HelloWorld_CN.pdf";
    private static final String FONT = "font/wryh.ttf";


    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
        document.open();
        Font f1 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        document.add(new Paragraph("hello world,我是测试一号", f1));
        document.close();
        writer.close();
    }
}
