package com.lizhe.resource;

import org.junit.Test;

import java.io.*;

/**
 * @author lz
 * @create 2020/6/2
 */
public class FileCopyTest {

    private static final String originalUrl = "D:\\JS\\Object-Oriented.rar";
    private static final String targetUrl = "D:\\JS\\a.rar";

    @Test
    public void copyFile() throws IOException {

        /**
         *  以前的方式
         */

        // 声明文件输入流，文件输出流
        //FileInputStream inputStream = null;
        //FileOutputStream outputStream = null;

        //try {
        //    inputStream = new FileInputStream(originalUrl);
        //    outputStream = new FileOutputStream(targetUrl);
        //
        //    // 设置读取的字节信息
        //    int content;
        //
        //    while ((content = inputStream.read()) != -1) {
        //        outputStream.write(content);
        //    }
        //} catch (IOException e) {
        //    e.printStackTrace();
        //} finally {
        //    if (outputStream != null) {
        //        outputStream.close();
        //    }
        //    if (inputStream != null) {
        //        inputStream.close();
        //    }
        //}

        /**
         *  java7的方式
         */
        try (FileInputStream inputStream = new FileInputStream(originalUrl); FileOutputStream outputStream = new FileOutputStream(targetUrl)) {

            // 设置读取的字节信息
            int content;

            while ((content = inputStream.read()) != -1) {
                outputStream.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
