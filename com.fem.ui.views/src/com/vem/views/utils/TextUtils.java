
package com.vem.views.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;


public class TextUtils {

    public static String getLine(StyledText st, int offset) {
        int currentLine = st.getLineAtOffset(offset);
        return st.getContent().getLine(currentLine);
    }

    public static String getLine(IDocument doc, int offset) {
        String line = "";
        try {
            IRegion range = doc.getLineInformationOfOffset(offset);
            line = doc.get(range.getOffset(), range.getLength());
        }
        catch (Exception e) {
            System.err.println("Error fetching textrange: " + e);
        }

        return line;
    }

    public static int getLineNumber(StyledText st, int offset) {
        int currentLine = st.getLineAtOffset(offset);
        return currentLine;
    }

    public static int getOffsetAtLine(IDocument doc, int line) {
        try {
            IRegion region = doc.getLineInformation(line);
            return region.getOffset();
        }
        catch (Exception e) {
            System.err.println("Error getting offset by hand" + e);
        }

        return 0;
    }


    public static int[] getLineNumbers(StyledText st, Point range) {
        int start = range.x;
        int end = (range.x + range.y);

        List<Integer> v = new ArrayList<Integer>();

        for (int i = start; i < end; i++) {
            int line = st.getLineAtOffset(i);
            Integer integ = new Integer(line);
            if (!v.contains(integ))
                v.add(integ);
        }

        int[] array = new int[v.size()];
        for (int i = 0; i < v.size(); i++) {
            Integer ite = (Integer) v.get(i);
            array[i] = ite.intValue();
        }

        return array;
    }

    /**
     * Doubles a line and puts caret at the correct position one line down
     *
     * @param st StyledText
     */
    public static void doubleLine(StyledText st) {
        String lineToDouble = getLine(st, st.getCaretOffset());
        // move caret 1 line down exactly
        int newCaret = st.getCaretOffset() + lineToDouble.length() + 1;

        int line = st.getLineAtOffset(st.getCaretOffset());
        int start = st.getOffsetAtLine(line);
        int end = lineToDouble.length();

        // double it
        lineToDouble += st.getLineDelimiter() + lineToDouble;

        st.replaceTextRange(start, end, lineToDouble);
        st.setCaretOffset(newCaret);
    }

    /**
     * Adjusts a string to be max 'maxLength' chars per line, and breaks the line
     * in such a way that a word does not get cut in half.
     *
     * @param str       String to fix line width on
     * @param maxLength max chars per line
     * @return modified string
     */
    public static String fixStringWidth(String str, int maxLength) {

        if (str == null || str.length() == 0)
            return str;

        String retVal = new String();

        StringTokenizer stn = new StringTokenizer(str, "\n");

        while (stn.hasMoreTokens()) {

            str = stn.nextToken();
            if (str.length() == 0) {
                retVal += "\n";
                continue;
            }

            StringTokenizer st = new StringTokenizer(str);
            String temp = new String();

            while (st.hasMoreTokens()) {
                String token = st.nextToken();

                if (temp.length() <= maxLength) {
                    temp += token + " ";
                }
                else {
                    temp = temp.trim();
                    retVal += temp + "\n";
                    temp = "";
                    temp += token + " ";
                }
            }

            // leftovers
            retVal += temp.trim() + "\n";
        }

        return retVal;
    }


    public static int convertToDocumentOffset(String line, int beginColumn, int offset, int tabWidth) {
        int cs_pos = 0;
        int javacc_pos = 0;

        for (int i = 0; i < line.length(); i++) {
            if (javacc_pos >= beginColumn)
                break;

            if (line.charAt(i) == '\t') {
                javacc_pos = javacc_pos - (javacc_pos % 8) + 8;
            }
            else {
                javacc_pos++;
            }
            cs_pos++;
        }

        int ret = cs_pos + offset - 1;
     
        return ret;
    }

    
    public static boolean wordInString(String word, String str, boolean caseSensetive) {
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (!caseSensetive) {
                if (token.equalsIgnoreCase(word))
                    return true;
            }
            else {
                if (token.equals(word))
                    return true;
            }
        }

        return false;
    }

}
