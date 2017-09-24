package com.rain.learn.algorithm.code.statistics;

import java.util.Arrays;

public class CommonCodeCounter extends AbstractCodeCounter {

    private String[] supportExtensions = new String[] { ".java", ".cpp", ".c" };

    private static final String SINGLE_COMMNET_TOKEN = "//";
    private static final String MULTI_COMMNET_BEGIN_TOKEN = "/*";
    private static final String MULTI_COMMNET_END_TOKEN = "*/";

    // multiple comment state
    private boolean mcs = false;

    @Override
    protected boolean isCommentWithoutCodeLine(String line) {
        if (line.startsWith(SINGLE_COMMNET_TOKEN) || line.startsWith(MULTI_COMMNET_BEGIN_TOKEN)
                && line.endsWith(MULTI_COMMNET_END_TOKEN)) {
            return true;
        }
        if (line.startsWith(MULTI_COMMNET_BEGIN_TOKEN)) {
            mcs = true;
            return true;
        }
        if (line.endsWith(MULTI_COMMNET_END_TOKEN)) {
            mcs = false;
            return true;
        }
        if (mcs) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean isCommentWithCodeLine(String line) {
        int i = line.indexOf(SINGLE_COMMNET_TOKEN);
        if (i > 0) {
            return true;
        }
        i = line.indexOf(MULTI_COMMNET_BEGIN_TOKEN);
        int ie = line.indexOf(MULTI_COMMNET_END_TOKEN);
        int tail = line.length() - MULTI_COMMNET_END_TOKEN.length();
        if (i > 0 && ie > i || i >= 0 && ie < tail) {
            return true;
        }
        if (i > 0 && ie < 0) {
            mcs = true;
            return true;
        }
        if (i < 0 && ie >= 0 && ie < tail) {
            mcs = false;
            return true;
        }
        return false;
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
