package com.lizhe.web.async;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lz
 * @create 2020-05-14
 * @desc DeferredResultHolder:订单处理结果凭证缓存，通过凭证可以在未来的时间点获取处理结果
 */
@Component
public class DeferredResultHolder {

    private Map<String, DeferredResult<String>> holder = new HashMap<>();

    // 将订单处理结果凭证放入缓存
    public void placeOrder(@NotBlank String orderNumber, @NotBlank DeferredResult<String> result) {
        holder.put(orderNumber, result);
    }

    public void completeOrder(@NotBlank String orderNumber, String result) {
        if (!holder.containsKey(orderNumber)) {
            throw new IllegalArgumentException("orderNumber not exist");
        }
        DeferredResult<String> deferredResult = holder.get(orderNumber);
        deferredResult.setResult(result);
    }
}
