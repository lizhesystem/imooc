package com.lizhe.stream;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * 演示流的4中创建方式
 * @author lz
 * @create 2020-05-29
 */
public class streamConstructor {

    @Test
    public void createStream() throws IOException {
        /**
         *  1:由数值直接构建流
         */
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6);

        /**
         *  2:由文件生成流
         */
        Stream<String> lines = Files.lines(Paths.get("D:\\my-code\\imooc\\02-996\\src\\test\\java\\com\\lizhe\\stream\\StreamConstructor.java"));
        lines.forEach(System.out::println);

        /**
         *  3: 通过函数生成流，不过如果不控制，生成的是无限的流..
         *     使用iterate 迭代器。
         *     使用generate 生成
         */
        Stream<Integer> stream = Stream.iterate(0, i -> i + 3).limit(10);
        stream.forEach(System.out::println);

        Stream<Double> generate = Stream.generate(Math::random);
        generate.limit(10).forEach(System.out::println);

    }
}
