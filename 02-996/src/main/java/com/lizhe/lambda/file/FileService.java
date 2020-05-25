package com.lizhe.lambda.file;

import java.io.*;
import java.util.function.Consumer;

/**
 * @author lz
 * @create 2020-05-25
 */
public class FileService {

    /**
     * 根据url读取文件打印
     * 更改为Consumer 自带的接口来消费数据。。
     * 数据处理的时候用这种方式非常方便。。。消费数据或者返回一个数据  判断是否满足某种条件等等 lambda的方式
     *
     * 总结：根据传入参数来判断一些数据的时候，使用接口方式更灵活，只提供方法，至于你内部怎么实现不管
     *      更能使用到lambda的加持。。但是需要注意的自己实现函数式接口的话，接口里只能有一个抽象方法。
     *
     * @param url
     * @param
     */
    public void print(String url, Consumer<String> consumer /*FileConsumer fileConsumer*/) throws IOException {
        // 读取文件
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(url)));

        // 定义行变量和内容sb
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        // 一直读完 存到sb里
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        consumer.accept(stringBuilder.toString());
        //fileConsumer.fileHandler(stringBuilder.toString());
    }
}
