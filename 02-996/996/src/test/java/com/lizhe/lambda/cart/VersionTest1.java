package com.lizhe.lambda.cart;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lz
 * @create 2020-05-25
 */


public class VersionTest1 {

    /**
     * 筛选出电子产品
     */
    @Test
    public void getElectronicsSkuList() {
        // 获取集合数据
        List<Sku> skuList = CartService.getSkuList();
        // 调用过滤方法查找电子类的sku
        List<Sku> electronicSkuList = CartService.getElectronicSkuList(skuList);
        // 打印数据使用fastJson工具库
        String formatString = JSON.toJSONString(electronicSkuList, true);
        System.out.println(formatString);
    }


    /**
     * 常用list集合初始化方式,java9有个lists.of创建
     */
    @Test
    public void createLists() {
        // 快速创建集合01,需要引入guava工具类
        ArrayList<Integer> lists = Lists.newArrayList(1, 2, 3);
        lists.add(2);
        System.out.println(lists);

        // 快速创建集合02,需要java8+ 流创建
        List<Integer> list2 = Stream.of(1, 2, 3).collect(Collectors.toList());
        list2.add(3);
        System.out.println(list2);

        // 错误的创建方式,这个方法创建的是不可变的集合。
        List<Integer> list3 = Arrays.asList(1, 2, 3);
        //list3.add(2);  错误,生成的集合不可变
        System.out.println(list3);
    }
}
