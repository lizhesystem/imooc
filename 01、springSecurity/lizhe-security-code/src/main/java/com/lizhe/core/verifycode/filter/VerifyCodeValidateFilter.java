package com.lizhe.core.verifycode.filter;

import com.lizhe.core.SecurityConstants;
import com.lizhe.core.properties.SecurityProperties;
import com.lizhe.core.verifycode.exception.VerifyCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 责任是拦截需要进行验证码校验的请求
 * @date 2019/9/2
 * @desc VerifyCodeValidateFilter
 */
@Component
public class VerifyCodeValidateFilter extends OncePerRequestFilter implements InitializingBean {

    // 认证失败处理器
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    // session读写工具
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    // 映射 需要校验验证码的 uri 和 校验码类型，如 /auth/login -> 图形验证码  /auth/sms -> 短信验证码
    private Map<String, VerifyCodeType> uriMap = new HashMap<>();

    /**
     * 配置文件类
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 匹配url工具类
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 利用Spring的依赖查找，聚集容器中所有的
     */
    @Autowired
    private VerifyCodeValidatorHolder verifyCodeValidatorHolder;

    /**
     * 启动前封装uriMap
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        uriMap.put(SecurityConstants.DEFAULT_FORM_LOGIN_URL, VerifyCodeType.IMAGE);
        putUriPatterns(uriMap, securityProperties.getCode().getImage().getUriPatterns(), VerifyCodeType.IMAGE);

        uriMap.put(SecurityConstants.DEFAULT_SMS_LOGIN_URL, VerifyCodeType.SMS);
        putUriPatterns(uriMap, securityProperties.getCode().getSms().getUriPatterns(), VerifyCodeType.SMS);
    }

    private void putUriPatterns(Map<String, VerifyCodeType> urlMap, String uriPatterns, VerifyCodeType verifyCodeType) {
        if (StringUtils.isNotBlank(uriPatterns)) {
            String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(uriPatterns, ",");
            for (String string : strings) {
                urlMap.put(string, verifyCodeType);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException
            , IOException {
        try {
            checkVerifyCodeIfNeed(request, uriMap);
        } catch (VerifyCodeException e) {
            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void checkVerifyCodeIfNeed(HttpServletRequest request, Map<String, VerifyCodeType> uriMap) {
        String requestUri = request.getRequestURI();
        Set<String> uriPatterns = uriMap.keySet();
        for (String uriPattern : uriPatterns) {
            //如果能匹配成功,获取根据key获取枚举类对象 sms或者image
            if (antPathMatcher.match(uriPattern, requestUri)) {
                VerifyCodeType verifyCodeType = uriMap.get(uriPattern);
                // 根据请求的的url获取到对应的请求标识，是sms还是image,得到模板类（抽象类）
                // 获取容器里对应的sms或者image的impl方法，然后调用抽象类的validateVerifyCode的方法来，来
                // 根据传入的参数，再次获取抽象类对象，这次
                verifyCodeValidatorHolder.getVerifyCodeValidator(verifyCodeType).validateVerifyCode(new ServletWebRequest(request), verifyCodeType);
                break;
            }
        }
    }

}
