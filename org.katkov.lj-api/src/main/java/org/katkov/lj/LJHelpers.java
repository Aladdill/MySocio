/*
 * Copyright (c) 2006, Igor Katkov
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided 
 * that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice, this list of conditions 
 *       and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
 *       and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     * The name of the author may not be used may not be used to endorse or 
 *       promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, 
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT 
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.katkov.lj;

import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LJHelpers {

    public static String bytesToHex(byte[] buf) {
        final StringBuilder b = new StringBuilder(buf.length * 2);

        for (int i = 0; i < buf.length; i++) {
            final int cell = (int) (buf[i] & 0xFF);
            if (cell < 16) {
                b.append("0");
            }

            b.append(Integer.toString(cell, 16));
        }

        return b.toString();
    }

    /**
     * Produces MD5 hash for the specified string
     *
     * @param s String to encode
     * @return MD5 hash
     */
    public static String MD5Encode(String s) {
        try {
            final java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            return bytesToHex(md.digest(s.getBytes()));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Parses HTML color representation
     *
     * @param htmlColor HTML representation of the color #RRGGBB
     * @return Integer color value
     */
    public static int HTMLtoRGB(String htmlColor) {
        return Integer.parseInt(htmlColor.replace("#", ""), 16);
    }

    /**
     * Produces HTML color representation
     *
     * @param rgb Integer color value
     * @return HTML color representation
     */
    public static String RGBtoHTML(int rgb) {
        final StringBuilder b = new StringBuilder("0000000");
        b.replace(0, 1, "#");
        for (int i = 5; i > 0; i -= 2) {
            final int bt = rgb & 0xFF;
            rgb = rgb >> 8;
            StringBuilder b2 = new StringBuilder(Integer.toString(bt, 16));
            if (bt < 16) {
                b2.insert(0, "0");
            }
            b.replace(i, i + 2, b2.toString());
        }

        return b.toString();
    }

    public static String formatDate(Date date, SimpleDateFormat dateFormat) {
        if (date != null) {
            return dateFormat.format(date);
        } else {
            return null;
        }
    }

    public static String getUnicodeText(Object text) throws UnsupportedEncodingException {
        if (text == null) {
            return null;
        } else if (text instanceof byte[]) {
            return new String((byte[]) text, "UTF-8");
        } else {
            return String.valueOf(text);
        }
    }

    public static Date parseDate(String dateString, SimpleDateFormat simpleDateFormat) throws ParseException {
        if (dateString != null && dateString.length() > 0) {
            return simpleDateFormat.parse(dateString);
        } else {
            return null;
        }
    }


    // This method writes a DOM document to a file
    public static void writeXmlFile(Document doc, String filename) throws TransformerException {

        // Prepare the DOM document for writing
        Source source = new DOMSource(doc);

        // Prepare the output file
        File file = new File(filename);
        Result result = new StreamResult(file);

        // Write the DOM document to the file
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform(source, result);
    }

    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

}
