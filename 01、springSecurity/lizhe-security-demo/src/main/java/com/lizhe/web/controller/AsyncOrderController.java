package com.lizhe.web.controller;

import com.lizhe.web.async.DeferredResultHolder;
import com.lizhe.web.async.OrderProcessingQueue;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author lz
 * @create 2020-05-14
 * Callable异步处理 (模拟REST异步处理)
 * 在Controller中，如果将一个Callable作为方法的返回值，那么tomcat线程池中的线程在响应结果时会新建一个线程执行该Callable并将其返回结果返回给客户端
 * <p>
 * 2020-05-14 11:36:05.333  INFO 9684 --- [nio-8989-exec-3] c.l.web.controller.AsyncOrderController  : ===主线程===收到创建订单请求，订单号=> 811665600110
 * 2020-05-14 11:36:05.334  INFO 9684 --- [nio-8989-exec-3] c.l.web.controller.AsyncOrderController  : [主线程]已将请求委托副线程处理(订单号=>811665600110)，继续处理其它请求
 * 2020-05-14 11:36:05.334  INFO 9684 --- [      MvcAsync5] c.l.web.controller.AsyncOrderController  : ===副线程===创建订单开始start，订单号=>811665600110
 * 2020-05-14 11:36:08.334  INFO 9684 --- [      MvcAsync5] c.l.web.controller.AsyncOrderController  : ===副线程===创建订单完成ok=>,订单号811665600110
 * <p>
 * callable详解：
 * 可以看出主线程没有执行callable里的下单任务，而是直接监听其他请求了。下单任务由springmvc开启一个线程MvcAsync5去执行了。测试接口也在
 * callable执行完毕后才得到他的返回值。
 * 对于客户端来说，后端的异步处理是透明的，与同步时没有什么区别；但是对于后端来说，tomcat监听请求的线程被占用的时间很短，大大提高了自身的并发能力
 * <p>
 * ===============DeferredResult异步处理=============
 * Callable异步处理的缺陷是，只能通过在本地新建副线程的方式进行异步处理，但现在随着微服务架构的盛行，我们经常需要跨系统的异步处理。
 * 例如在秒杀系统中，并发下单请求量较大，如果后端对每个下单请求做同步处理（即在请求线程中处理订单）后再返回响应结果，会导致服务假死（发送下单请求没有任何响应）；
 * 这时我们可能会利用消息中间件，请求线程只负责监听下单请求，然后发消息给MQ，让订单系统从MQ中拉取消息（如单号）进行下单处理并将处理结果返回给秒杀系统；
 * 秒杀系统独立设一个监听订单处理结果消息的线程，将处理结果返回给客户端。
 */

@RestController
@RequestMapping("/order")
public class AsyncOrderController {

    // 打印日志
    private Logger logger = LoggerFactory.getLogger(getClass());


    // 凭证缓存
    @Autowired
    private DeferredResultHolder deferredResultHolder;


    @Autowired
    private OrderProcessingQueue orderProcessingQueue;


    /**
     * 创建订单
     *
     * @return
     */
    //@PostMapping
    //public Callable<String> createOrder() {
    //    // 生成12位单号
    //    String orderNumber = RandomStringUtils.randomNumeric(12);
    //    logger.info("===主线程===收到创建订单请求，订单号=> " + orderNumber);
    //    // 开辟一个副线程~
    //    Callable<String> result = () -> {
    //        logger.info("===副线程===创建订单开始start，订单号=>" + orderNumber);
    //        // 模拟创建订单逻辑,使用TimeUnit类，休息3秒
    //        TimeUnit.SECONDS.sleep(3);
    //        // Thread.sleep(3000);
    //        logger.info("===副线程===创建订单完成ok=>,订单号" + orderNumber);
    //        return orderNumber;
    //    };
    //
    //    logger.info("[主线程]已将请求委托副线程处理(订单号=>" + orderNumber + ")，继续处理其它请求");
    //    return result;
    //}

    /**
     * 使用DeferredResult异步处理：
     * 场景：主要处理异步请求，比如浏览器A发起请求，该请求需要等到B系统（如MQ）给A推送数据时，A才会立刻向浏览器返回数据。
     * 作用：如果该API接口的return返回值是DeferredResult，在没有超时或者DeferredResult对象设置setResult时，
     * 接口不会返回，但是Servlet容器线程会结束，DeferredResult另起线程来进行结果处理(即这种操作提升了服务短时间的吞吐能力)，并setResult，如此以来这个请求不会占用服务连接池太久，
     * 线程会去干其他事，如果超时或设置setResult，接口会立即返回。
     *
     * @return
     */
    @PostMapping
    public DeferredResult<String> createOrder() {
        logger.info("【请求线程】收到下单请求");

        // 生成12位单号
        String orderNumber = RandomStringUtils.randomNumeric(12);

        // 创建处理结果凭证放入缓存，以便监听（订单系统向MQ发送的订单处理结果消息的）线程向凭证中设置结果，
        // 这会触发该结果响应给客户端
        DeferredResult<String> deferredResult = new DeferredResult<>();
        deferredResultHolder.placeOrder(orderNumber, deferredResult);
        // 异步向MQ发送下单消息，假设需要200ms
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                synchronized (orderProcessingQueue) {
                    while (orderProcessingQueue.size() >= Integer.MAX_VALUE) {
                        try {
                            orderProcessingQueue.wait();
                        } catch (Exception e) {
                        }
                    }
                    orderProcessingQueue.addLast(orderNumber);
                    orderProcessingQueue.notifyAll();
                }
                logger.info("向MQ发送下单消息, 单号: {}", orderNumber);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "本地临时线程-向MQ发送下单消息").start();

        logger.info("【请求线程】继续处理其它请求");

        // 并不会立即将deferredResult序列化成JSON并返回给客户端，而会等deferredResult的setResult被调用后，将传入的result转成JSON返回
        return deferredResult;
    }

}
