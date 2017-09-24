package com.rain.learn.algorithm.code.statistics;

import java.io.File;

public class CodeCountResult {

    private File file;
    private int totalLines;
    private int codeLines;
    private int commentLines;
    private int blankLines;

    public CodeCountResult() {
    }

    public CodeCountResult(File file) {
        this.file = file;
    }

    public CodeCountResult(File file, int blankLines, int commentLines, int codeLines, int totalLines) {
        super();
        this.file = file;
        this.codeLines = codeLines;
        this.commentLines = commentLines;
        this.totalLines = totalLines;
        this.blankLines = blankLines;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getCodeLines() {
        return codeLines;
    }

    public void setCodeLines(int codeLines) {
        this.codeLines = codeLines;
    }

    public int getCommentLines() {
        return commentLines;
    }

    public void setCommentLines(int commentLines) {
        this.commentLines = commentLines;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public int getBlankLines() {
        return blankLines;
    }

    public void setBlankLines(int blankLines) {
        this.blankLines = blankLines;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((file == null) ? 0 : file.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CodeCountResult other = (CodeCountResult) obj;
        if (file == null) {
            if (other.file != null)
                return false;
        } else if (!file.equals(other.file))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CodeCountResult [file=" + file + ", totalLines=" + totalLines + ", codeLines=" + codeLines
                + ", commentLines=" + commentLines + ", blankLines=" + blankLines + "]";
    }

    public void printResult() {
        System.out.println("[file=" + file.getAbsolutePath() + ", totalLines=" + totalLines + ", codeLines="
                + codeLines + ", commentLines=" + commentLines + ", blankLines=" + blankLines + "]");
    }
}
