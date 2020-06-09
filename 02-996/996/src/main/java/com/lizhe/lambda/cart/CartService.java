package com.lizhe.lambda.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lz
 * @create 2020-05-25
 */
public class CartService {
    private static List<Sku> skuList = new ArrayList<Sku>() {
        {
            add(new Sku(2020001, "无人机", 999.00, 1, 999.00, SkuCategoryEnum.ELECTRONIC));
            add(new Sku(2020002, "T-shirt", 50.00, 2, 100.00, SkuCategoryEnum.CLOTHING));
            add(new Sku(2020003, "人生的枷锁", 30.00, 1, 30.00, SkuCategoryEnum.BOOKS));
            add(new Sku(2020004, "老人与海", 20.00, 1, 20.00, SkuCategoryEnum.BOOKS));
            add(new Sku(2020005, "剑指高效编程", 288.00, 1, 288.00, SkuCategoryEnum.BOOKS));
            add(new Sku(2020006, "大头皮鞋", 300.00, 1, 300.00, SkuCategoryEnum.CLOTHING));
            add(new Sku(2020007, "杠铃", 2000.00, 1, 2000.00, SkuCategoryEnum.SPROTS));
            add(new Sku(2020008, "ThinkPad", 5000.00, 1, 5000.00, SkuCategoryEnum.ELECTRONIC));
        }
    };


    public static List<Sku> getSkuList() {
        return skuList;
    }

    /**
     * 找出电子类的购物车商品
     *
     * @param skuList 所有购物车
     * @return
     */
    public static List<Sku> getElectronicSkuList(List<Sku> skuList) {
        ArrayList<Sku> electronicList = new ArrayList<>();
        for (Sku sku : skuList) {
            if (SkuCategoryEnum.ELECTRONIC.equals(sku.getSkuCategory())) {
                electronicList.add(sku);
            }
        }
        return electronicList;
    }

    /**
     * 根据传入的类别筛选,单一维度条件参数化,传入不同的enum，打印出不同的产品列表
     *
     * @param skuList
     * @param skuCategoryEnum
     * @return
     */
    public static List<Sku> filterSkuListByParam(List<Sku> skuList, SkuCategoryEnum skuCategoryEnum) {
        ArrayList<Sku> electronicList = new ArrayList<>();
        skuList.forEach(sku -> {
            if (sku.getSkuCategory().equals(skuCategoryEnum)) {
                electronicList.add(sku);
            }
        });
        return electronicList;
    }

    /**
     * 若flag是true 根据 枚举类判断
     * 若为false  根据传入的总价进行判断
     *
     * @param skuList
     * @param skuCategoryEnum
     * @param dTotalPrice
     * @param flag
     * @return
     */
    public static List<Sku> filterSkuListByParamB(List<Sku> skuList, SkuCategoryEnum skuCategoryEnum,
                                                  Double dTotalPrice, boolean flag) {
        ArrayList<Sku> electronicList = new ArrayList<>();
        skuList.forEach(sku -> {
            if (flag && sku.getSkuCategory().equals(skuCategoryEnum)
                    ||
                    !flag && sku.getTotalPrice() > dTotalPrice) {
                electronicList.add(sku);
            }
        });
        return electronicList;
    }

    /**
     *  优化上面的方法，使用接口实现方式来处理 传入参数逻辑 根据总价大于200的数据
     *
     * @param skuList
     * @param
     * @return
     */
    //public static List<Sku> filterSkuListByParamPrediCate(List<Sku> skuList, TotalPricePrediCate totalPricePrediCate) {
    //    ArrayList<Sku> electronicList = new ArrayList<>();
    //    skuList.forEach(sku -> {
    //        if (totalPricePrediCate.test(sku)) {
    //            electronicList.add(sku);
    //        }
    //    });
    //    return electronicList;
    //}


    /**
     * 同上 使用接口实现方式来处理 传入参数逻辑 根据类别是BOOKs的判断
     *
     * @param skuList
     * @param
     * @return
     */
    //public static List<Sku> filterSkuListByParamPrediCate(List<Sku> skuList, CategoryPrediCate categoryPrediCate) {
    //    ArrayList<Sku> electronicList = new ArrayList<>();
    //    skuList.forEach(sku -> {
    //        if (categoryPrediCate.test(sku)) {
    //            electronicList.add(sku);
    //        }
    //    });
    //    return electronicList;
    //}

    /**
     *  第二个判断逻辑传入接口，根据接口里的方法判断如果是true就add，至于怎么实现这个方法主要看调用者。
     * @param skuList
     * @param skuPrediCate
     * @return
     */
    public static List<Sku> filterSkuListByParamPrediCate(List<Sku> skuList, SkuPrediCate skuPrediCate) {
        List<Sku> electronicList = new ArrayList<>();
        skuList.forEach(sku -> {
            if (skuPrediCate.test(sku)) {
                electronicList.add(sku);
            }
        });
        return electronicList;
    }

}
