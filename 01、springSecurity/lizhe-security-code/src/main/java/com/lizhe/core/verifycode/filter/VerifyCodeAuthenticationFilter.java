package com.lizhe.core.verifycode.filter;

import com.lizhe.core.SecurityConstants;
import com.lizhe.core.properties.SecurityProperties;
import com.lizhe.core.verifycode.exception.VerifyCodeException;
import com.lizhe.core.verifycode.po.ImageCode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.commons.lang.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.lizhe.core.SecurityConstants.IMAGE_CODE_SESSION_KEY;

/**
 * 校验图形验证码的过滤器，继承OncePerRequestFilter的过滤器在一次请求中只会被执行一次
 * 责任是拦截需要进行验证码校验的请求
 *
 * @author lz
 * @create 2020-05-16
 */
//@Component 封装模式模式后暂不用
public class VerifyCodeAuthenticationFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 认证失败处理类
     */
    //@Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;


    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    //@Autowired
    private SecurityProperties securityProperties;

    /**
     * 匹配URI的工具类，帮我们做类似/user/1到/user/*的匹配
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Set<String> uriPatternSet = new HashSet<>();

    /**
     * 我们将uriPatternSet的初始化逻辑写在了InitializingBean接口的afterPropertiesSet方法中，
     * 【InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候会执行该方法】。
     * 这相当于在传统的spring.xml中配置了一个init-method标签，（https://blog.csdn.net/maclaren001/article/details/37039749）
     * 该方法会在VerifyCodeAuthenticationFilter的所有autowire属性被赋值后由spring执行，只在启动的时候执行一次！！！
     * <p>
     * 结果：访问/user、/user/1均被提示验证码不能为空，修改配置项为uriPattern=/user/*重启后登录/login.html再访问/user没被拦截，
     * 而访问/user/1提示验证码不能为空
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String uriPatterns = securityProperties.getCode().getImage().getUriPatterns();
        if (StringUtils.isNotBlank(uriPatterns)) {
            String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(uriPatterns, ",");
            uriPatternSet.addAll(Arrays.asList(strings));
        }
        uriPatternSet.add("/auth/login");
    }
    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return customAuthenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.customAuthenticationFailureHandler = authenticationFailureHandler;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
            IOException {
        for (String uriPattern : uriPatternSet) {
            if (antPathMatcher.match(uriPattern, request.getRequestURI())) {
                try {
                    this.validateVerifyCode(new ServletWebRequest(request));
                } catch (VerifyCodeException e) {
                    // 若抛出异常则使用自定义认证失败处理器处理一下，否则没人捕获（因为该过滤器配在了UsernamePasswordAuthenticationFilter的前面）就抛给前端了
                    customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                    return;
                }
                break;
            }
        }
        filterChain.doFilter(request, response);
    }

    // 拦截用户登录的请求，从Session中读取验证码和用户提交的验证码进行比对
    private void validateVerifyCode(ServletWebRequest request) {
        String verifyCode = (String) request.getParameter("verifyCode");
        if (StringUtils.isBlank(verifyCode)) {
            throw new VerifyCodeException("验证码不能为空");
        }
        ImageCode imageCode = (ImageCode) sessionStrategy.getAttribute(request, IMAGE_CODE_SESSION_KEY);
        if (imageCode == null) {
            throw new VerifyCodeException("验证码不存在");
        }
        if (imageCode.isExpired()) {
            throw new VerifyCodeException("验证码已过期，请刷新页面");
        }
        if (StringUtils.equals(verifyCode,imageCode.getCode()) == false) {
            throw new VerifyCodeException("验证码错误");
        }
        sessionStrategy.removeAttribute(request, IMAGE_CODE_SESSION_KEY);
    }

}
