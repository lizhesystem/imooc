package com.lizhe.lambda.cart;

/**
 * 谓词接口
 *
 * @author lz
 * @create 2020-05-25
 */
@FunctionalInterface
public interface SkuPrediCate {

    boolean test(Sku sku);
}
