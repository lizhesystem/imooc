package com.lizhe.web.config;

import com.lizhe.web.filter.TimeFilter;
import com.lizhe.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author lz
 * @create 2020-05-12
 * 标记为注解类，配置个过滤器，相当于传统方式在web.xml中添加一个Filter
 * 继承WebMvcConfigureAdapter并重写addInterceptor方法是为了添加自定义拦截器
 */

//@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    TimeFilter timeFilter;

    @Autowired
    TimeInterceptor timeInterceptor;



    /**
     * 注册过滤器
     *
     * 发现：过滤器好像继承了Filter接口并添加@Component就能生效，注释掉WebConfig中的registerTimeFilter方法，发现TimeFilter还是打印了日志
     */
    //@Bean
    //public FilterRegistrationBean registerTimeFilter() {
    //    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    //    filterRegistrationBean.setFilter(timeFilter);
    //    // 默认拦截所有请求
    //    filterRegistrationBean.addUrlPatterns("/*");
    //    return filterRegistrationBean;
    //}

    /**
     * 添加自定义拦截器interceptor，多次调用addInterceptor可添加多个拦截器
     * @param registry
     */
    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
    //    registry.addInterceptor(timeInterceptor);
    //}
}
