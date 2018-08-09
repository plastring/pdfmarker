package org.plastring;

import org.plastring.util.PdfUtil;

import java.io.*;

/**
 * Add bookmark to pdf by content file.
 *
 * @author Thomas Jay
 */
public class PdfMarker
{
    public static void main( String[] args ) throws Throwable
    {
        String bookmarkFileName = args[0];
        String pdfFileName = args[1];

        File bookmarkFile = new File(bookmarkFileName);
        File pdfFile = new File(pdfFileName);

        StringBuilder sb = new StringBuilder(pdfFileName.replaceFirst("[.][^.]+$", ""));
        sb.append("_changed.pdf");

        System.out.printf( "bookmark file is: %n %s %nsource pdf file is: %n%s %n", bookmarkFile.getAbsolutePath(), pdfFile.getAbsolutePath());

        PdfUtil.addBookmark2Pdf(pdfFileName, sb.toString(), bookmarkFileName);

        System.out.printf("pdf file that is added bookmark is: %n %s %n", new File(sb.toString()).getAbsolutePath());
    }

}
