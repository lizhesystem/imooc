package com.lizhe.web.async;

import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 * @author lz
 * @create 2020-05-14
 *  下单消息MQ
 */
@Component
public class OrderProcessingQueue extends LinkedList<String> {
}
