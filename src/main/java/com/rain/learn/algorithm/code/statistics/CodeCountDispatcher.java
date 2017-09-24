package com.rain.learn.algorithm.code.statistics;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CodeCountDispatcher {

    private File rootPath;

    private CompletionService<CodeCountResult> cs;

    private Map<String, CodeCounter> counterCache;

    public CodeCountDispatcher(Executor executor, File rootPath) {
        super();
        this.rootPath = rootPath;
        this.cs = new ExecutorCompletionService<>(executor);
    }

    public CodeCountDispatcher(Executor executor, File rootPath, Set<Class<? extends CodeCounter>> codeCounters) {
        super();
        this.rootPath = rootPath;
        this.cs = new ExecutorCompletionService<>(executor);
    }

    public boolean registerCodeCounter(Class<? extends CodeCounter> codeCounter) {
        try {
            CodeCounter cc = codeCounter.newInstance();
            return addCounterToCache(cc);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerCodeCounter(CodeCounter codeCounter) {
        if (codeCounter == null) {
            return false;
        }
        return addCounterToCache(codeCounter);
    }

    private boolean addCounterToCache(CodeCounter codeCounter) {
        if (counterCache == null) {
            counterCache = new HashMap<>();
        }
        String[] es = codeCounter.supports();
        for (int i = 0; i < es.length; i++) {
            counterCache.put(es[i], codeCounter);
        }
        if (es.length > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean unRegisterCodeCounter(Class<? extends CodeCounter> codeCounter) {
        if (counterCache == null || counterCache.isEmpty()) {
            return false;
        }
        boolean result = false;
        Iterator<Entry<String, CodeCounter>> iter = counterCache.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, CodeCounter> next = iter.next();
            CodeCounter v = next.getValue();
            if (v.getClass().equals(codeCounter)) {
                iter.remove();
                result = true;
            }
        }
        return result;
    }

    public boolean unRegisterCodeCounter(CodeCounter codeCounter) {
        return rmCounterFromCache(codeCounter);
    }

    private boolean rmCounterFromCache(CodeCounter codeCounter) {
        if (counterCache == null || counterCache.isEmpty()) {
            return false;
        }
        boolean result = false;
        Iterator<Entry<String, CodeCounter>> iter = counterCache.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, CodeCounter> next = iter.next();
            CodeCounter v = next.getValue();
            if (v == codeCounter) {
                iter.remove();
                result = true;
            }
        }
        return result;
    }

    public FutureTask<CodeStatisticsResult> dispatches() {
        if (rootPath != null) {
            final int total = traverseDirAndSubmitCountTask(rootPath);
            if (total > 0) {
                return new FutureTask<>(new Callable<CodeStatisticsResult>() {
                    @Override
                    public CodeStatisticsResult call() throws Exception {
                        Set<CodeCountResult> subResults = new TreeSet<>(new Comparator<CodeCountResult>() {
                            @Override
                            public int compare(CodeCountResult o1, CodeCountResult o2) {
                                return o1.getFile().getAbsolutePath().compareTo(o2.getFile().getAbsolutePath());
                            }
                        });
                        int tl = 0, bl = 0, cdl = 0, cml = 0;
                        for (int i = 0; i < total; i++) {
                            Future<CodeCountResult> fr = cs.take();
                            CodeCountResult r = fr.get();
                            tl += r.getTotalLines();
                            bl += r.getBlankLines();
                            cdl += r.getCodeLines();
                            cml += r.getCommentLines();
                            subResults.add(r);
                        }
                        CodeCountResult totalResult = new CodeCountResult(rootPath, bl, cml, cdl, tl);
                        return new CodeStatisticsResult(total, subResults, totalResult);
                    }
                });
            }
        }
        return new FutureTask<>(new Callable<CodeStatisticsResult>() {
            @Override
            public CodeStatisticsResult call() throws Exception {
                Set<CodeCountResult> subResults = Collections.emptySet();
                CodeCountResult totalResult = new CodeCountResult(rootPath);
                return new CodeStatisticsResult(0, subResults, totalResult);
            }
        });
    }

    private int traverseDirAndSubmitCountTask(File path) {
        File[] files = path.listFiles();
        int totalFiles = 0;
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f.isDirectory()) {
                    totalFiles += traverseDirAndSubmitCountTask(f);
                } else if (f.isFile()) {
                    totalFiles++;
                    CodeCountTask cct = new CodeCountTask(f);
                    cs.submit(cct);
                }
            }
        }
        return totalFiles;
    }

    private class CodeCountTask implements Callable<CodeCountResult> {
        private File file;

        public CodeCountTask(File file) {
            this.file = file;
        }

        @Override
        public CodeCountResult call() throws Exception {
            CodeCounter cc = counterCache.get(getExtension());
            if (cc == null) {
                return new CodeCountResult(file);
            } else {
                return cc.calculate(file);
            }
        }

        private String getExtension() {
            String fn = file.getName();
            int index = fn.lastIndexOf('.');
            if (index > -1) {
                return fn.substring(index);
            }
            return null;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int num = Runtime.getRuntime().availableProcessors();
        System.out.println("Processor num is: " + num);

        ExecutorService executorService = Executors.newFixedThreadPool(num);
        CodeCountDispatcher ccd = new CodeCountDispatcher(executorService, new File("/home/test/test"));
        ccd.registerCodeCounter(CommonCodeCounter.class);

        System.out.println("---begin---");
        long begin = System.currentTimeMillis();
        FutureTask<CodeStatisticsResult> f = ccd.dispatches();
        f.run();
        CodeStatisticsResult csr = f.get();
        long end = System.currentTimeMillis();

        executorService.shutdown();

        csr.printResult();
        System.out.println("---finish---");
        System.out.println("Time taken: " + (end - begin));
    }
}
