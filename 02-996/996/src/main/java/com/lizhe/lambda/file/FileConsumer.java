package com.lizhe.lambda.file;

/**
 * @author lz
 * @create 2020-05-25
 */
@FunctionalInterface
public interface FileConsumer {
    void fileHandler(String fileContent);
}

