package com.lizhe.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

/**
 * @author lz
 * @create 2020-05-25
 */


public class VersionTest4 {

    /**
     * 根据传入的接口的实现，返回的状态来进行判断，为了引入匿名内部类。
     */
    @Test
    public void getElectronicsSkuList() {
        // 获取集合数据
        List<Sku> skuList = CartService.getSkuList();

        // 获取所有总价大于200商品
        //List<Sku> skus = CartService.filterSkuListByParamPrediCate(skuList, new TotalPricePrediCate());

         // 获取所有类别是books的
        //List<Sku> skuForBooks = CartService.filterSkuListByParamPrediCate(skuList, new CategoryPrediCate());

        // 打印数据使用fastJson工具库
        //String formatString = JSON.toJSONString(skus, true);
        //System.out.println(formatString);
        //System.out.println(JSON.toJSONString(skuForBooks,true));
    }


}
