package com.rain.learn.algorithm.code.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class AbstractCodeCounter implements CodeCounter {

    @Override
    public CodeCountResult calculate(File file) throws IOException {
        File f = preCalculate(file);
        CodeCountResult ccr = null;
        Throwable thrown = null;
        try {
            ccr = doCalculate(f);
        } catch (IOException e) {
            thrown = e;
            throw e;
        } finally {
            postCalculate(ccr, thrown);
        }
        return ccr;
    }

    protected File preCalculate(File file) {
        return file;
    }

    protected CodeCountResult postCalculate(CodeCountResult result, Throwable thrown) {
        return result;
    }

    protected String preproccessCode(String line) {
        return line.trim();
    }

    protected CodeCountResult doCalculate(File file) throws IOException {
        int bln = 0, cmln = 0, cdln = 0, tln = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String l = preproccessCode(line);
                tln++;
                if (isBlankLine(l)) {
                    bln++;
                } else {
                    CodeCommentNumber ccn = countCodeComment(l);
                    cdln += ccn.codeNumber;
                    cmln += ccn.commentNumber;
                }
            }
        }
        return new CodeCountResult(file, bln, cmln, cdln, tln);
    }

    protected boolean isBlankLine(String line) {
        return line.isEmpty();
    }

    protected CodeCommentNumber countCodeComment(String line) {
        return new CodeCommentNumber(1, 0);
    }

    protected static class CodeCommentNumber {
        int codeNumber;
        int commentNumber;

        public CodeCommentNumber(int codeNumber, int commentNumber) {
            this.codeNumber = codeNumber;
            this.commentNumber = commentNumber;
        }

    }
}
