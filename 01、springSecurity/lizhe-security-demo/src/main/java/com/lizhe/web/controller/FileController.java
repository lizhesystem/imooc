package com.lizhe.web.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author lz
 * @create 2020-05-12
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static final String FOLDER = "D:\\my-code\\security-study\\lizhe-security-demo\\src\\main\\java\\com\\lizhe\\web\\controller";

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping
    public String upload(MultipartFile file) throws Exception {
        System.out.println("[FileController]文件请求参数: " + file.getName());
        System.out.println("[FileController]文件名称: " + file.getName());
        System.out.println("[FileController]文件大小: " + file.getSize() + "字节");

        // 可以通过file.getInputStream将文件上传到FastDFS、云OSS等存储系统中
        // InputStream inputStream = file.getInputStream();
        // byte[] content = new byte[inputStream.available()];
        // inputStream.read(content);

        // 生成文件名getOriginalFilename获取上传的文件名称 + 当前系统时间
        String fileKey = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File storeFile = new File(FOLDER, fileKey);
        // transferTo()方法，是springMvc封装的方法，用于图片上传时，把内存中图片写入磁盘
        file.transferTo(storeFile);
        return fileKey;
    }

    /**
     * 文件下载
     * 映射写成/{fileKey:.+}而不是/{fileKey}的原因是SpringMVC会忽略映射中.符号之后的字符。正则.+表示匹配任意个非\n的字符，
     * 不加该正则的话，方法入参fileKey获取到的值将是1566349460611_hello而不是1566349460611_hello.txt
     * <p>
     * 测试：浏览器访问http://localhost:8080/file/1566349460611_hello.txt
     */
    @GetMapping("/{fileKey:.+}")
    public void download(@PathVariable String fileKey, HttpServletRequest req, HttpServletResponse rsp) throws Exception {
        try (
                FileInputStream fileInputStream = new FileInputStream(new File(FOLDER, fileKey));
                // 字节输出流：一般使用这种方式把服务端的数据用字节的方式输出到浏览器。
                ServletOutputStream outputStream = rsp.getOutputStream();
        ) {
            // 下载需要设置响应头为 application/x-download
            rsp.setContentType("application/x-download");
            // 设置下载询问框中的文件名
            rsp.setHeader("Content-Disposition", "attachment;filename=" + fileKey);

            // 文件输入流copy到输出流，最后的结果就是个下载。
            IOUtils.copy(fileInputStream, outputStream);
            outputStream.flush();
        }

    }
}
