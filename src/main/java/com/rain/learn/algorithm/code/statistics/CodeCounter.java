package com.rain.learn.algorithm.code.statistics;

import java.io.File;
import java.io.IOException;

public interface CodeCounter {

    CodeCountResult calculate(File file) throws IOException;

    boolean supports(String fileNameExtension);

    String[] supports();
}
