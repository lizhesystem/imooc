package com.lizhe.alllearning.service;

import java.io.File;
import java.io.InputStream;

/**
 * 接口描述：文件上传接口类
 *
 * @author LZ on 2020/6/13
 */
public interface FileService {

    /**
     * 文件上传
     *
     * @param inputStream
     * @param filename
     */
    void upload(InputStream inputStream, String filename);

    /**
     * 文件上传
     *
     * @param file
     */
    void upload(File file);
}
