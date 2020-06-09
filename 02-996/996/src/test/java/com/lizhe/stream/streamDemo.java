package com.lizhe.stream;

import com.alibaba.fastjson.JSON;
import com.lizhe.lambda.cart.CartService;
import com.lizhe.lambda.cart.Sku;
import com.lizhe.lambda.cart.SkuCategoryEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * 演示流的各种操作
 *
 * @author lz
 * @create 2020/5/28
 */
public class streamDemo {
    List<Sku> list;

    @Before
    public void init() {
        list = CartService.getSkuList();
    }

    @Test
    public void streamTest() {
        /**
         *  filter：过滤掉不符合断言判断的数据
         */
        System.out.println("filter使用==========================");
        list.stream()
                .filter(sku -> SkuCategoryEnum.SPROTS.equals(sku.getSkuCategory()))
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));


        /**
         *  map：处理元素，将一个元素转换成另一个元素
         */
        System.out.println("map使用==========================");
        list.stream()
                .map(sku -> sku.getSkuPrice() + 10000)
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));


        /**
         *  flatMap：将一个对象转换成流,处理后的数据必须还是个流
         */
        System.out.println("flatMap使用==========================");
        list.stream()
                .flatMap(sku -> Arrays.stream(sku.getSkuName().split("")))
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));

        /**
         *  peek：对流中元素进行遍历， 与forEach类似 但不会摧毁流元素  没有返回值
         */
        System.out.println("peek使用==========================");
        list.stream()
                .peek(sku -> System.out.println(sku.getSkuName()))
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));

        /**
         *  sort：对流中元素排序，可选则自然排序或者指定排序规则
         */
        System.out.println("sorted使用==========================");
        list.stream()
                .sorted(Comparator.comparing(Sku::getSkuPrice))
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));

        /**
         *  distince：对流元素进行去重，有状态操作, 不能根据字段去重，只能去重实现equals() 以及 hashCode()的对象。
         */
        System.out.println("distince使用==========================");
        list.stream()
                // 去重
                .distinct()
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));

        /**
         *  skip：跳过前N条记录。
         */
        System.out.println("skip使用==========================");
        list.stream()
                .skip(3)
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));

        /**
         *  limit：截断前N条记录。
         */
        System.out.println("limit使用==========================");
        list.stream()
                .limit(3)
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));


        /**
         *  allMatch：终端操作，短路操作。所有元素匹配，返回true
         */
        System.out.println("allMatch使用==========================");
        boolean match = list.stream()
                .allMatch(sku -> sku.getSkuPrice() > 100);// 因为是短路所以就结束了,后面不能继续流操作
        //.forEach(item -> System.out.println(JSON.toJSONString(item, true)));
        System.out.println(match);


        /**
         *  anyMatch：终端操作，短路操作。只要其中有一个任何元素匹配，返回true
         */
        System.out.println("anyMatch使用==========================");
        boolean anyMatch = list.stream()
                .anyMatch(sku -> sku.getSkuPrice() > 100);// 因为是短路所以就结束了,后面不能继续流操作
        //.forEach(item -> System.out.println(JSON.toJSONString(item, true)));
        System.out.println(anyMatch);


        /**
         *  noneMatch：终端操作，短路操作。任何元素都不匹配，返回true
         */
        System.out.println("noneMatch使用==========================");
        boolean noneMatch = list.stream()
                .noneMatch(sku -> sku.getTotalPrice() > 10_000);
        //.forEach(item -> System.out.println(JSON.toJSONString(item, true)));
        System.out.println(noneMatch);

        /**
         *  findFirst：找到第一个对象,短路操作。返回Optional对象
         */
        System.out.println("findFirst使用==========================");
        Optional<Sku> first = list.stream()
                .findFirst();
        System.out.println(JSON.toJSONString(first.isPresent() ? first.get() : "", true));

        /**
         *  findAny：找任意一个
         */
        System.out.println("findAny使用==========================");
        Optional<Sku> findAny = list.stream()
                .findAny();
        System.out.println(JSON.toJSONString(findAny.get(), true));


        /**
         *  max：找最大 | min同理
         */
        System.out.println("max|min使用==========================");
        Optional<Sku> max = list.stream()
                .max(Comparator.comparing(Sku::getTotalPrice));
        System.out.println(JSON.toJSONString(max.get(), true));

        // 使用mapToDouble过滤下 再获取
        OptionalDouble max1 = list.stream()
                .mapToDouble(Sku::getSkuPrice)
                .max();
        System.out.println(max1.getAsDouble());


        /**
         *  count： 汇总
         */
        System.out.println("count使用==========================");
        long count = list.stream()
                .count();

        long count1 = list.stream()
                .mapToDouble(Sku::getSkuPrice)
                .count();

        System.out.println(count);
        System.out.println(count1);
    }
}
