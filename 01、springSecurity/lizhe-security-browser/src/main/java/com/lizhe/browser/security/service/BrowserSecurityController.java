package com.lizhe.browser.security.service;

import com.lizhe.core.properties.SecurityProperties;
import com.lizhe.core.support.SimpleResponseResult;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lz
 * @create 2020-05-15
 */

@RestController
public class BrowserSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    // security会将跳转前的请求存储到session中
    private RequestCache requestCache = new HttpSessionRequestCache();

    // 默认的重定向策略
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @RequestMapping("/auth/require")
    // 该注解用来设置响应码
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponseResult requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 从session中取出跳转前用户访问的url
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        // 如果session有数据代表是从浏览器访问的,
        if (savedRequest != null) {
            // 获取请求url
            String redirectUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转到/auth/login的请求是:{}", redirectUrl);
            // 判断请求的url是不是html结尾
            if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
                // 如果用户访问html页面被FilterSecurityInterceptor拦截从而跳转到/auth/login,那么就重定向到登录页面
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }

        }
        // 如果不是访问html而被拦截跳转到了/auth/login，则返回JSON提示
        return new SimpleResponseResult("用户未登录，请引导用户至登录页");
    }
}
