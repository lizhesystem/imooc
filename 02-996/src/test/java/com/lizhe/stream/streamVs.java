package com.lizhe.stream;
import com.alibaba.fastjson.JSON;
import com.lizhe.lambda.cart.CartService;
import com.lizhe.lambda.cart.Sku;
import com.lizhe.lambda.cart.SkuCategoryEnum;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author lz
 * @create 2020/5/28
 */
public class streamVs {

    /**
     * 流操作演示
     */
    @Test
    public void newCartHandle() {
        List<Sku> skuList = CartService.getSkuList();

        AtomicReference<Double> money = new AtomicReference<>(0.0);
        List<String> resultSkuNameList = skuList.stream()
                /**
                 *  1:打印商品信息, peek传入是一个Consumer函数式接口,没有返回值处理数据
                 */
                .peek(sku -> System.out.println(
                        JSON.toJSONString(sku, true)))
                /**
                 * 2:过滤掉所有图书类的商品, filter传入是一个Predicate函数式接口，判断传入值是否符合条件
                 */
                .filter(sku -> !SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory()))

                /**
                 *  3：排序引入Comparator接口根据TotalPrice排序  reversed倒序
                 */
                .sorted(Comparator.comparing(Sku::getTotalPrice).reversed())

                /**
                 *  4:Top2
                 */
                .limit(2)
                /**
                 *  累计商品总金额, 使用AtomicReference窗口金额对象,它是个线程安全的对象
                 */
                .peek(sku -> money.set(money.get() + sku.getTotalPrice()))

                /**
                 *  获取商品名称
                 */
                .map(Sku::getSkuName)

                /**
                 *  收集结果
                 */
                .collect(Collectors.toList());
        /**
         *  打印结果
         */
        System.out.println(JSON.toJSONString(resultSkuNameList, true));
        System.out.println("商品总价" + money.get());
    }
}
