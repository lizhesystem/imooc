package com.lizhe.stream;

import com.alibaba.fastjson.JSON;
import com.lizhe.lambda.cart.CartService;
import com.lizhe.lambda.cart.Sku;
import com.lizhe.lambda.cart.SkuCategoryEnum;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;

/**
 * 收集器
 *
 * @author lz
 * @create 2020-05-29
 */
public class streamCollector {

    @Test
    public void Test() {
        List<Sku> skuList = CartService.getSkuList();

        //toMap方法生成map对象
        Map<String, Enum> collect = skuList.stream()
                .filter(sku -> SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory()))
                .collect(Collectors.toMap(Sku::getSkuName, Sku::getSkuCategory));
        System.out.println(collect);
        //{老人与海=BOOKS, 人生的枷锁=BOOKS, 剑指高效编程=BOOKS}

        // 使用minBy获取price最小的对象
        Optional<Sku> collect2 = skuList.stream().collect(Collectors.minBy(Comparator.comparing(Sku::getSkuPrice)));
        Sku sku = collect2.get();
        System.out.println(JSON.toJSONString(sku, true));
        /**
         * {
         * 	"skuCategory":"BOOKS",
         * 	"skuId":2020004,
         * 	"skuName":"老人与海",
         * 	"skuPrice":20.0,
         * 	"totalNum":1,
         * 	"totalPrice":20.0
         * }
         */

        // 使用joining分割获取到的name
        String collect1 = skuList.stream().map(Sku::getSkuName).collect(joining(","));
        System.out.println(collect1);
        // A,B,C

        //先执行collect操作后再执行第二个参数的表达式。这个例子在最后的数组里加+ "ok"。
        String collect3 = skuList.stream().map(Sku::getSkuName).collect(collectingAndThen(joining(","), x -> x + "ok!"));
        System.out.println(collect3);
        // 无人机,T-shirt,人生的枷锁,老人与海,剑指高效编程,大头皮鞋,杠铃,ThinkPad ok!

        //Collectors.mapping(...)：跟map操作类似，只是参数有点区别
        System.out.println(Stream.of("a", "b", "c").collect(Collectors.mapping(x -> x.toUpperCase(), Collectors.joining(","))));
        // 无人机,T-shirt,人生的枷锁,老人与海,剑指高效编程,大头皮鞋,杠铃,ThinkPad


    }

}
