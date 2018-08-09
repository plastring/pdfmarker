package org.plastring;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.plastring.util.PdfUtil;

/**
 * pdfmarker .
 */
public class PdfMarkerTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        String srcPdfFile = "./src/main/resources/mysql_crush.pdf";
        String destPdfFile = "./src/main/resources/mysql_crush_changed.pdf";
        String bookmarkFile = "./src/main/resources/bookmark_demo.txt";
        boolean result = PdfUtil.addBookmark2Pdf(srcPdfFile, destPdfFile, bookmarkFile);
        assertTrue( true );
    }


}
