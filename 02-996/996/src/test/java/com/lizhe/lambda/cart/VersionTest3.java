package com.lizhe.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

/**
 * @author lz
 * @create 2020-05-25
 */


public class VersionTest3 {

    /**
     * 筛选根据传入枚举类的不同获取不同的sku,h很明显越来越麻烦根据参数来判断。！
     */
    @Test
    public void getElectronicsSkuList() {
        // 获取集合数据
        List<Sku> skuList = CartService.getSkuList();
        // 根据传入枚举类的不同获取不同的sku
        List<Sku> electronicSkuList = CartService.filterSkuListByParamB(skuList, null, 2000.00, false);
        // 打印数据使用fastJson工具库
        String formatString = JSON.toJSONString(electronicSkuList, true);
        System.out.println(formatString);
    }

    /*
     * [
     *        {
     * 		"skuCategory":"BOOKS",
     * 		"skuId":2020003,
     * 		"skuName":"人生的枷锁",
     * 		"skuPrice":30.0,
     * 		"totalNum":1,
     * 		"totalPrice":30.0
     *    },
     *    {
     * 		"skuCategory":"BOOKS",
     * 		"skuId":2020004,
     * 		"skuName":"老人与海",
     * 		"skuPrice":20.0,
     * 		"totalNum":1,
     * 		"totalPrice":20.0
     *    },
     *    {
     * 		"skuCategory":"BOOKS",
     * 		"skuId":2020005,
     * 		"skuName":"剑指高效编程",
     * 		"skuPrice":288.0,
     * 		"totalNum":1,
     * 		"totalPrice":288.0
     *    }
     * ]
     */

}
