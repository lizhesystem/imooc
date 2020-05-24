package com.lizhe.core.verifycode.filter;

import com.lizhe.core.SecurityConstants;
import com.lizhe.core.properties.SecurityProperties;
import com.lizhe.core.verifycode.exception.VerifyCodeException;
import com.lizhe.core.verifycode.po.ImageCode;
import com.lizhe.core.verifycode.po.VerifyCode;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 校验短信验证码的filter过滤器，也去继承OncePerRequestFilter的过滤器在一次请求中只会被执行一次
 *
 * @author lz
 * @create 2020-05-16
 */
@Component
public class SmsCodeAuthenticationFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    @Autowired
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
        // 获取配置文件里配置的uri处理后放到集合里
        String uriPatterns = securityProperties.getCode().getSms().getUriPatterns();
        if (StringUtils.isNotBlank(uriPatterns)) {
            String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(uriPatterns, ",");
            uriPatternSet.addAll(Arrays.asList(strings));
        }
        // 如果没有配置，或者最终不管如何 login永远需要验证码（启动后uriPatternSet集合里最少就有一个"/auth/login"）
        uriPatternSet.add("/auth/sms");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse rsp, FilterChain filterChain) throws ServletException, IOException {
        for (String uriPattern : uriPatternSet) {
            // 有一个匹配就需要拦截 校验验证码
            if (antPathMatcher.match(uriPattern, req.getRequestURI())) {
                try {
                    // 执行验证码逻辑
                    this.validateVerifyCode(new ServletWebRequest(req));
                } catch (VerifyCodeException e) {
                    // 若抛出异常则使用自定义认证失败处理器处理一下，否则没人捕获（因为该过滤器配在了UsernamePasswordAuthenticationFilter的前面）
                    customAuthenticationFailureHandler.onAuthenticationFailure(req, rsp, e);
                    // 优化2：在VerifyCodeAuthenticationFilter发现认证失败异常并调用认证失败处理器处理后，
                    // 应该return一下，没有必要再走后续的过滤器了
                    return;
                }
                break;
            }
        }
        // 【之前没有走配置文件的时候，直接判断路径和方法了】
        // 如果是登录请求,Objects.equals比较2个2个对象是否相等  and  方法是POST的方法
        //if (Objects.equals(req.getRequestURI(), "/auth/login") && StringUtils.endsWithIgnoreCase(req.getMethod(), "POST")) {
        //    try {
        //        this.validateVerifyCode(new ServletWebRequest(req));
        //    } catch (VerifyCodeException e) {
        //        // 若抛出异常则使用自定义认证失败处理器处理一下，否则没人捕获（因为该过滤器配在了UsernamePasswordAuthenticationFilter的前面）
        //        customAuthenticationFailureHandler.onAuthenticationFailure(req, rsp, e);
        //        // 优化2：在VerifyCodeAuthenticationFilter发现认证失败异常并调用认证失败处理器处理后，
        //        // 应该return一下，没有必要再走后续的过滤器了
        //        return;
        //    }
        //}

        filterChain.doFilter(req, rsp);

    }

    // 校验短信验验证码的方法
    // 从Session中读取验证码和用户提交的验证码进行比对
    private void validateVerifyCode(ServletWebRequest request) {
        String smsCode = request.getParameter("smsCode");
        if (StringUtils.isBlank(smsCode)) {
            throw new VerifyCodeException("验证码不能为空");
        }
        // 从session中拿短信验证码对象
        VerifyCode verifyCode = (VerifyCode) sessionStrategy.getAttribute(request, SecurityConstants.SMS_CODE_SESSION_KEY);
        if (verifyCode == null) {
            throw new VerifyCodeException("短信验证码错误");
        }
        if (verifyCode.isExpired()) {
            throw new VerifyCodeException("验证码已过期");
        }

        if (!StringUtils.equals(smsCode, verifyCode.getCode())) {
            throw new VerifyCodeException("短信验证码错误");
        }

        // 登录成功,移除Session中保存的验证码
        sessionStrategy.removeAttribute(request, SecurityConstants.SMS_CODE_SESSION_KEY);
    }

}
