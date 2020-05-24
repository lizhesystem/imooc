package com.lizhe.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author lz
 * @create 2020-05-12
 *
 *  AOP切面编程
 * [GlobalControllerAspect]开始调用服务【public com.lizhe.dto.User com.lizhe.web.controller.UserController.getInfo(java.lang.String)】 请求参数: [1], 2020-05-12 08:38:27
 * 1
 * [GlobalControllerAspect]服务【public com.lizhe.dto.User com.lizhe.web.controller.UserController.getInfo(java.lang.String)】调用结束，响应结果为: com.lizhe.dto.User@79ce1762, 2020-05-12 08:38:27, 共耗时: 3ms
 */
//
//@Aspect
//@Component
public class GlobalControllerAspect {
    // 切入点表达式：包含web.controller包下的所有Controller的所有方法
    @Around("execution(* com.lizhe.web.controller.*.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint point) throws Throwable {

        // 得到handler对应的方法签名
        String service = "【" + point.getSignature().toLongString() + "】";

        // 得到传入的参数
        Object[] args = point.getArgs();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date start = new Date();
        System.out.println("[GlobalControllerAspect]开始调用服务" + service + " 请求参数: " + Arrays.toString(args) + ", " + simpleDateFormat.format(start));

        Object result = null;
        try {
            // 调用实际的handler并取得结果
            result = point.proceed();
        } catch (Throwable throwable) {
            System.out.println("[GlobalControllerAspect]服务" + service + "发生异常,message = " + throwable.getMessage());
            throw throwable;
        }

        // 计算耗时
        Date end = new Date();
        System.out.println("[GlobalControllerAspect]服务" + service + "调用结束，响应结果为: " + result+", "+simpleDateFormat.format(end)+", 共耗时: "+(end.getTime()-start.getTime())+
                "ms");

        // 返回响应结果，不一定要和handler的处理结果一致
        return result;
    }
}
