package com.rain.learn.algorithm.code.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AbstractCodeStatistics {

    private int blankLineNum;
    private int commentLineNum;
    private int codeLineNum;
    private int totalLineNum;

    private File file;

    private String singleCommentLineToken;
    private String multiCommentLineBeginToken;
    private String multiCommentLineEndToken;

    protected boolean multiCommentLineBegin = false;

    public CodeCountResult calculate() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                totalLineNum++;
                if (isBlankLine(line)) {
                    blankLineNum++;
                    continue;
                }
                if (multiCommentLineBegin) {

                } else {
                    // is code
                    // is comment
                    // is code and comment

                    // single comment // or /* */
                    if (isSingleCommentLine(line)) {
                        commentLineNum++;
                        continue;
                    }
                    // single comment with code (code //)
                    int index;
                    if ((index = line.indexOf(singleCommentLineToken)) > 0) {

                    }
                    // single comment with code (/**/ code) (code /**/ code) (code /**/)
                    // multi comment begin (/*)
                    // multi comment with code begin (code /*) (code /**/ code /*)

                    if (isValidCommentLineBegin(line, singleCommentLineToken)) {
                        commentLineNum++;
                        codeLineNum++;
                        continue;
                    }
                    if (isValidCommentLineBegin(line, multiCommentLineBeginToken)) {
                        commentLineNum++;
                        codeLineNum++;
                        continue;
                    }
                    String str = "\"" + "//";
                }
            }
        }
        return null;
    }

    protected boolean isBlankLine(String line) {
        return line.isEmpty();
    }

    protected boolean isSingleCommentLine(String line) {
        if (line.startsWith(singleCommentLineToken)
                || (line.startsWith(multiCommentLineBeginToken) && line.endsWith(multiCommentLineEndToken))) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param lcs
     * @param tcs
     * @param loffset
     *            lcs's offset
     * @return first valid token from {@code loffset}
     */
    protected int find1stValidToken(char[] lcs, char[] tcs, int loffset) {
        int tl = tcs.length;
        int lrl = lcs.length;
        int ll = lrl - loffset;
        if (ll < tl) {
            return -1;
        } else if (ll == tl) {
            if (charsEqual(lcs, tcs, loffset)) {
                return 0;
            } else {
                return -1;
            }
        }
        char dq = '"';
        char bs = '\\';
        int dqn = 0;
        if (lcs[loffset] == dq) {
            dqn++;
        }
        int to = lrl - tl;
        for (int i = loffset + 1; i < to;) {
            if (charsEqual(lcs, tcs, i)) {
                if ((dqn & 1) == 0) {
                    return i;
                } else {
                    i += tl;
                    continue;
                }
            } else if (lcs[i] == dq && lcs[i - 1] != bs) {
                dqn++;
            }
            i++;
        }
        // no need to check dqn is an odd or even number
        if (charsEqual(lcs, tcs, to)) {
            return to;
        } else {
            return -1;
        }
    }

    private boolean charsEqual(char[] a, char[] b, int aOffset) {
        int bl = b.length;
        int i = 0;
        while (--bl >= 0) {
            if (a[aOffset++] != b[i++]) {
                return false;
            }
        }
        return true;
    }

    protected boolean isMultiCommentLineEndWithoutCode(String line) {
        return line.endsWith(multiCommentLineEndToken);
    }

    protected boolean isMultiCommentLineEndWithCode(String line) {
        if (isValidMultiCommentLineEnd(line)) {
            return true;
        }
        return false;
    }

    protected boolean isValidMultiCommentLineEnd(String line) {
        if (line.contains(multiCommentLineEndToken)) {

        }
        return false;
    }

    protected boolean isValidCommentLineBegin(String line, String token) {
        int index = line.indexOf(token);
        if (index < 0) {
            return false;
        } else if (index == 0) {
            return true;
        }
        int l = token.length();
        char[] cs = line.toCharArray();
        char doubleQuote = '"';
        int num = 0;
        for (int i = 0; i < cs.length;) {
            if (i == index) {
                if ((num & 1) == 0) {
                    return true;
                } else {
                    index = line.indexOf(token, index + l);
                    if (index < 0) {
                        return false;
                    }
                    i += l;
                    continue;
                }
            } else if (doubleQuote == cs[i]) {
                num++;
            }
            i++;
        }
        return false;
    }

    protected boolean isCodeWithCommentLine(String line) {
        /*
         * //
         *//* *//*
         */
        int a = 0; /* /* /*df */
        int c; /*
                * /*
                */
        int b;
        return false;
    }

}
