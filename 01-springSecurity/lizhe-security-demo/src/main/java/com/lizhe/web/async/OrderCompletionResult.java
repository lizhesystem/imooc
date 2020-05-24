package com.lizhe.web.async;

/**
 * @author lz
 * @create 2020-05-14
 */
public class OrderCompletionResult {
    private String orderNumber;
    private String result;

    public OrderCompletionResult() {
    }

    public OrderCompletionResult(String orderNumber, String result) {
        this.orderNumber = orderNumber;
        this.result = result;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
