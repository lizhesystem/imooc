package com.lizhe.alllearning.filter;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * 类名称：TraceIdFilter
 * ********************************
 * <p>
 * 类描述：TraceId过滤器 ：用于过滤请求，生成日志的时候多个用户请求打印一个他的traceId。
 *                       实现用户跟踪。打印到日志。
 *
 * @author
 * @date 下午10:30
 */
@WebFilter(urlPatterns = "/*")
@Order(1)
public class TraceIdFilter implements Filter {

    /**
     * TraceId常量
     */
    private static final String TRACE_ID = "traceId";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 尝试从请求信息中获取TraceId信息
        String traceId = servletRequest.getParameter(TRACE_ID);

        // 为空设置默认值
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        // 在MDC中放入traceId
        MDC.put(TRACE_ID, traceId);

        filterChain.doFilter(servletRequest, servletResponse);
    }


}
