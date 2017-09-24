package com.rain.learn.algorithm.code.statistics;

import java.util.Set;

public class CodeStatisticsResult {

    private int totalFiles;
    private Set<CodeCountResult> subResults;
    private CodeCountResult totalResult;

    public CodeStatisticsResult() {
    }

    public CodeStatisticsResult(int totalFiles, Set<CodeCountResult> subResults, CodeCountResult totalResult) {
        super();
        this.totalFiles = totalFiles;
        this.subResults = subResults;
        this.totalResult = totalResult;
    }

    public int getTotalFile() {
        return totalFiles;
    }

    public void setTotalFile(int totalFile) {
        this.totalFiles = totalFile;
    }

    public Set<CodeCountResult> getSubResults() {
        return subResults;
    }

    public void setSubResults(Set<CodeCountResult> subResults) {
        this.subResults = subResults;
    }

    public CodeCountResult getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(CodeCountResult totalResult) {
        this.totalResult = totalResult;
    }

    public void printResult() {
        System.out.println("Root path is: " + totalResult.getFile().getAbsolutePath());
        System.out.println("Total file number is: " + totalFiles);
        System.out.println("Total result is: [TotalLines=" + totalResult.getTotalLines() + ", CodeLines="
                + totalResult.getCodeLines() + ", CommentLines=" + totalResult.getCommentLines() + ", BlankLines="
                + totalResult.getBlankLines() + "]");
        if (subResults != null && !subResults.isEmpty()) {
            System.out.println("Sub result:");
            System.out.println();
            for (CodeCountResult ccr : subResults) {
                ccr.printResult();
            }
        }
        System.out.println();
    }
}
