package com.lizhe.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lz
 * @create 2020-05-14
 *  两个队列对应的两个监听  ---ApplicationListener是SpringBoot的监听器
 */
@Component
public class OrderProcessingListener implements ApplicationListener<ContextRefreshedEvent> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    OrderProcessingQueue orderProcessingQueue;

    @Autowired
    OrderCompletionQueue orderCompletionQueue;

    @Autowired
    DeferredResultHolder deferredResultHolder;

    // spring容器启动或刷新时执行此方法(spring boot启动开始时执行的事件)
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        // 本系统（秒杀系统）启动时，启动一个监听MQ下单完成消息的线程
        new Thread(() -> {
            while (true) {
                String finishedOrderNumber;
                OrderCompletionResult orderCompletionResult;
                synchronized (orderCompletionQueue) {
                    while (orderCompletionQueue.isEmpty()) {
                        try {
                            orderCompletionQueue.wait();
                        } catch (InterruptedException e) { }
                    }
                    orderCompletionResult = orderCompletionQueue.pollFirst();
                    orderCompletionQueue.notifyAll();
                }
                finishedOrderNumber = orderCompletionResult.getOrderNumber();
                logger.info("收到订单处理完成消息，单号为: {}", finishedOrderNumber);
                deferredResultHolder.completeOrder(finishedOrderNumber, orderCompletionResult.getResult());
            }

        },"本地监听线程-监听订单处理完成")
                .start();


        // 假设是订单系统监听MQ下单消息的线程
        new Thread(() -> {
            while (true) {
                String orderNumber;
                synchronized (orderProcessingQueue) {
                    while (orderProcessingQueue.isEmpty()) {
                        try {
                            orderProcessingQueue.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                    orderNumber = orderProcessingQueue.pollFirst();
                    orderProcessingQueue.notifyAll();
                }

                logger.info("收到下单请求，开始执行下单逻辑，单号为: {}", orderNumber);
                boolean status;
                // 模拟执行下单逻辑
                try {
                    TimeUnit.SECONDS.sleep(2);
                    status = true;
                } catch (Exception e) {
                    logger.info("下单失败=>{}", e.getMessage());
                    status = false;
                }
                // 向 订单处理完成MQ 发送消息
                synchronized (orderCompletionQueue) {
                    orderCompletionQueue.addLast(new OrderCompletionResult(orderNumber, status == true ? "success" : "error"));
                    logger.info("发送订单完成消息, 单号: {}",orderNumber);
                    orderCompletionQueue.notifyAll();
                }
            }

        },"订单系统线程-监听下单消息")
                .start();
    }
}
