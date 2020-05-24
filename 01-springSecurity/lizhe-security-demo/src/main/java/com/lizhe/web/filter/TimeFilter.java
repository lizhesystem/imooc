package com.lizhe.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * @author lz
 * @create 2020/5/12
 *
 * 过滤器Filter是JavaEE中的标准，是不依赖SpringMVC的，要想在SpringMVC中使用过滤器需要两步，
 * 1：实现Filter接口并注入到Spring容器
 * 2：配置FilterRegistrationBean，这一步相当于传统方式在web.xml中添加一个<Filter>节点
 *
 * 由于Filter是JavaEE中的标准，所以它仅依赖servlet-api而不依赖任何第三方类库，因此它自然也不知道Controller的存在，
 * 自然也就无法知道本次请求将被映射到哪个方法上，SpringMVC通过引入拦截器弥补了这一缺点
 *
 *
 */

//@Component
public class TimeFilter implements Filter {


    /**
     * 容器启动执行
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter init。");
    }

    /**
     * 过滤器拦截部分，
     * 在收到请求时执行，这时请求还未到达SpringMVC的入口DispatcherServlet
     * 单次请求只会执行一次（不论期间发生了几次请求转发）
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("过滤器Filter======= 收到服务" + request.getMethod() + "===" + request.getRequestURL());
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("开始执行" + dateFormat.format(date));

        filterChain.doFilter(req, rsp);
        Date newDate = new Date();

        System.out.println("执行完毕时间" + dateFormat.format(new Date()) + "共耗时" + (newDate.getTime() - date.getTime()) + "毫秒");
        // 收到服务GET===http://127.0.0.1:8080/user/1
        //开始执行2020-05-12 07:24:45
        //1
        //执行完毕时间2020-05-12 07:24:45共耗时108毫秒
    }

    /**
     * 容器销毁时执行
     */
    @Override
    public void destroy() {

    }
}
