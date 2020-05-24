package com.lizhe.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * @author lz
 * @create 2020-05-12

 * 拦截器

 * 拦截器与Filter的有如下不同之处
 * Filter是基于请求的，Interceptor是基于Controller的，一次请求可能会执行多个Controller（通过转发），因此一次请求只会执行一次Filter但可能执行多次Interceptor
 * Interceptor是SpringMVC中的组件，因此它知道Controller的存在，能够获取相关信息（如该请求映射的方法，方法所在的bean等）

 * 使用SpringMVC提供的拦截器也需要两步
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {

    /**
     * 在controller方法执行之前被执行
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        // 强转
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String service = "【====" + handlerMethod.getMethod().getName() + "#" + handlerMethod.getBean().getClass().getName() + "====】";
        Date start = new Date();
        System.out.println("[TimeInterceptor # preHandle] 服务" + service + "被调用 " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(start));
        httpServletRequest.setAttribute("start", start.getTime());
        return true;

    }

    /**
     * Controller方法正常执行完毕后执行，如果Controller方法抛出异常则不会执行此方法
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        // 强转
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String service = "【====" + handlerMethod.getMethod().getName() + "#" + handlerMethod.getBean().getClass().getName() + "====】";
        Date end = new Date();
        System.out.println("[TimeInterceptor # postHandle] 服务" + service + "调用结束" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(end)
                + "共耗时" + (end.getTime() - (Long) httpServletRequest.getAttribute("start")) + "ms");
    }

    /**
     * 无论Controller方法是否抛出异常，都会被执行
     *
     *  测试异常的时候发现afterCompletion中的异常打印逻辑并未被执行，
     *  这是因为IdNotExistException被我们之前自定义的异常处理器处理掉了，
     *  没有抛出来。我们改为抛出系统异常RuntimeException再试一下，就可以了！
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String service = "【" + handlerMethod.getBean() + "#" + handlerMethod.getMethod().getName() + "】";
        Date end = new Date();
        System.out.println("[TimeInterceptor # afterCompletion] 服务" + service + "调用结束 " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(end)
                + " 共耗时：" + (end.getTime() - (Long) httpServletRequest.getAttribute("start")) + "ms");

        if (e != null) {
            System.out.println("[TimeInterceptor#afterCompletion] 服务" + service + "调用异常：" + e.getMessage());

        }

    }
}
