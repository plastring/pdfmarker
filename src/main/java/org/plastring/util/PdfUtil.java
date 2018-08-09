package org.plastring.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
*  从目录文件中生成pdf的目录，写入新文件中.
*
*  @author Thomas Jay
*
*/
public class PdfUtil {

    public static void addBookmark2Pdf(String pdfFileName, String destPdfFileName, String bookmarkFileName) throws
            IOException, DocumentException {
        PdfReader reader = new PdfReader(pdfFileName);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destPdfFileName));
        HashMap<String, Object> bmap = BookmarkUtil.getBookmarksFromFile(new File(bookmarkFileName));

        List<HashMap<String, Object>> list =  BookmarkUtil.getBookmarks((ArrayList<HashMap<String, Object>>)bmap.get("contents"), Integer.parseInt(bmap.get("offset").toString()));

        stamper.setOutlines(list);
        stamper.close();
        reader.close();
    }
}
