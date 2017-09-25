package com.rain.learn.algorithm.code.statistics;

import java.io.File;
import java.util.Arrays;

public class CommonCodeCounter extends AbstractCodeCounter {

    private final String[] supportExtensions = new String[] { ".java", ".cpp", ".c" };

    private static final String SINGLE_COMMNET_TOKEN = "//";
    private static final String MULTI_COMMNET_BEGIN_TOKEN = "/*";
    private static final String MULTI_COMMNET_END_TOKEN = "*/";

    // multiple comment state, true multiple line has begin, false multiple line has end
    private final ThreadLocal<Boolean> mcs = new ThreadLocal<Boolean>();

    @Override
    protected File preCalculate(File file) {
        mcs.set(false);
        return file;
    }

    @Override
    protected CodeCountResult postCalculate(CodeCountResult result, Throwable thrown) {
        mcs.set(null);
        return result;
    }

    @Override
    protected CodeCommentNumber countCodeComment(String line) {
        if (mcs.get()) {
            return proccessMCTHasBegin(line);
        } else {
            return proccessMCTHasnotBegin(line);
        }
    }

    private CodeCommentNumber proccessMCTHasBegin(String line) {
        int mei = line.indexOf(MULTI_COMMNET_END_TOKEN);
        if (mei >= 0) {
            // has multiple line end token
            mcs.set(false);
            int tail = line.length() - MULTI_COMMNET_END_TOKEN.length() - 1;
            if (mei == tail) {
                // without code
                return new CodeCommentNumber(0, 1);
            } else {
                // has code after multiple line end token
                return new CodeCommentNumber(1, 1);
            }
        } else {
            // do not have multiple line end token
            return new CodeCommentNumber(0, 1);
        }
    }

    private CodeCommentNumber proccessMCTHasnotBegin(String line) {
        int si = line.indexOf(SINGLE_COMMNET_TOKEN);
        if (si == 0) {
            // single comment line without code
            return new CodeCommentNumber(0, 1);
        } else if (si > 0) {
            // single comment line with code
            return new CodeCommentNumber(1, 1);
        } else {
            int mbi = line.indexOf(MULTI_COMMNET_BEGIN_TOKEN);
            if (mbi < 0) {
                // without comments
                return new CodeCommentNumber(1, 0);
            }
            int mei = line.indexOf(MULTI_COMMNET_END_TOKEN);
            if (mei < 0) {
                // multiple line begin without end
                mcs.set(true);
                if (mbi == 0) {
                    return new CodeCommentNumber(0, 1);
                } else {
                    return new CodeCommentNumber(1, 1);
                }
            } else {
                // multiple line begin, also multiple line end
                int tail = line.length() - MULTI_COMMNET_END_TOKEN.length() - 1;
                if (mbi == 0 && mei == tail) {
                    return new CodeCommentNumber(0, 1);
                } else {
                    return new CodeCommentNumber(1, 1);
                }
            }
        }
    }

    @Override
    public boolean supports(String fileNameExtension) {
        for (String e : supportExtensions) {
            if (e.equals(fileNameExtension)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String[] supports() {
        return Arrays.copyOf(supportExtensions, supportExtensions.length);
    }
}
