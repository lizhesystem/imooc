package com.lizhe.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

/**
 * 引入匿名内部类
 *
 * @author lz
 * @create 2020-05-25
 */


public class VersionTest5 {

    /**
     * 根据传入的接口的实现，返回的状态来进行判断，为了引入匿名内部类。
     */
    @Test
    public void getElectronicsSkuList() {
        // 获取集合数据
        List<Sku> skuList = CartService.getSkuList();

        // 获取所有总价大于200商品
        List<Sku> skus = CartService.filterSkuListByParamPrediCate(skuList, new SkuPrediCate() {
            @Override
            public boolean test(Sku sku) {
                return 200.00 < sku.getTotalPrice();
            }
        });

        // 获取所有类别是books的
        List<Sku> skuForBooks = CartService.filterSkuListByParamPrediCate(skuList, new SkuPrediCate() {
            @Override
            public boolean test(Sku sku) {
                return SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory());
            }
        });

        /**
         * 近一步使用lambda来获取数据。一行代码搞定
         */
        List<Sku> skus1 = CartService.filterSkuListByParamPrediCate(skuList, sku -> SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory()));


        // 打印数据使用fastJson工具库
        //String formatString = JSON.toJSONString(skus, true);
        //System.out.println(formatString);
        System.out.println(JSON.toJSONString(skus1, true));
    }


}
