package com.rain.learn.algorithm.code.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class AbstractCodeCounter implements CodeCounter {

    @Override
    public CodeCountResult calculate(File file) throws IOException {
        int bln = 0, cmln = 0, cdln = 0, tln = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (isBlankLine(line)) {
                    bln++;
                } else if (isCommentWithCodeLine(line)) {
                    cdln++;
                    cmln++;
                } else if (isCommentWithoutCodeLine(line)) {
                    cmln++;
                } else if (isCodeLine(line)) {
                    cdln++;
                }
                tln++;
            }
        }
        return new CodeCountResult(file, bln, cmln, cdln, tln);
    }

    protected boolean isBlankLine(String line) {
        return line.isEmpty();
    }

    abstract protected boolean isCommentWithoutCodeLine(String line);

    protected boolean isCodeLine(String line) {
        return true;
    }

    abstract protected boolean isCommentWithCodeLine(String line);

}
