package com.lizhe.lambda.cart;

import com.google.common.collect.Lists;
import com.lizhe.lambda.file.FileService;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * 根据url读取文件打印
 * 更改为Consumer 自带的接口来消费数据。。
 * 数据处理的时候用这种方式非常方便。。。消费数据或者返回一个数据  判断是否满足某种条件等等 lambda的方式
 *
 * 总结：根据传入参数来判断一些数据的时候，使用接口方式更灵活，只提供方法，至于你内部怎么实现不管
 *      更能使用到lambda的加持。。但是需要注意的自己实现函数式接口的话，接口里只能有一个抽象方法。
 * @author lz
 * @create 2020-05-25
 */
public class FileServiceTest {

    private static final String URL = "D:\\my-code\\imooc\\02-996\\src\\main\\java\\com\\lizhe\\lambda\\file\\FileService.java";

    @Test
    public void Test() throws IOException {
        FileService fileService = new FileService();

        // 打印该类
        fileService.print(URL, System.out::println);
        //fileService.print(URL, System.out::println);


    }


    @Test
    public void Test3(){
        Stream<String> aaa = Stream.of("aaa", "bbb", "ddd", "ccc", "fff");
        Consumer<String> consumer1 = (s) -> System.out.println(s);//lambda表达式返回的就是一个Consumer接口
    }
}
