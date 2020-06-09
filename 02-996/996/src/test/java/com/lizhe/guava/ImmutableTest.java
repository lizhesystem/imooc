package com.lizhe.guava;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author lz
 * @create 2020/6/2
 */
public class ImmutableTest {

    @Test
    public void immutable() {
        ArrayList<Integer> list = Lists.newArrayList(1, 2, 3);
        // 使用jdk自带的方法把list集合变成不可变的集合
        List<Integer> integers = Collections.unmodifiableList(list);
        integers.add(5);
    }

    @Test
    public void immutable1(){
        /**
         *  使用guava工具类 创建不可变集合实例
         */
        ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3, 4, 5);

        // 通过研究存在的集合创建
        ImmutableSet<Integer> integers1 = ImmutableSet.copyOf(integers);

        // 直接初始化创建不可变集合
        ImmutableSet<Integer> immutableSet = ImmutableSet.of(1, 2, 3);

        // 以builder方式创建
        ImmutableSet<Object> buildList = ImmutableSet.builder()
                .add(1)
                .addAll(Sets.newHashSet(2, 3))
                .add(9)
                .build();

        System.out.println(buildList);
        // [1, 2, 3, 9]
    }
}
