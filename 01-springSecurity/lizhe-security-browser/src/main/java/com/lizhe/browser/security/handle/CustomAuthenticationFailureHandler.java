package com.lizhe.browser.security.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lizhe.core.properties.LoginProcessTypeEnum;
import com.lizhe.core.properties.SecurityProperties;
import com.lizhe.core.support.SimpleResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lz
 * @create 2020-05-16
 * 认证失败的自定义处理类
 * 返回的结果 省略了部分内容：
 * {
 *       "cause": null,
 *       "stackTrace": [],
 *       "localizedMessage": "用户名或密码错误",
 *       "message": "用户名或密码错误",
 *       "suppressed": []
 * }
 *
 * 在Spring Security Web框架内部，缺省使用的认证错误处理策略是AuthenticationFailureHandler的实现类SimpleUrlAuthenticationFailureHandler。
 * 它由配置指定一个defaultFailureUrl,表示认证失败时缺省使用的重定向地址。一旦认证失败，它的方法onAuthenticationFailure被调用时，它就会将用户重定向到该地址。
 * 如果该属性没有设置，它会向客户端返回一个401状态码。另外SimpleUrlAuthenticationFailureHandler还有一个属性useForward,如果该属性设置为true,
 * 页面跳转将不再是重定向(redirect)机制，取而代之的是转发(forward)机制。
 */
@Component("customAuthenticationFailureHandler")
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (securityProperties.getBrowser().getLoginProcessType() == LoginProcessTypeEnum.REDIRECT) {
            // onAuthenticationFailure被调用时，它就会将用户重定向到该地址。
            super.onAuthenticationFailure(request, response, exception);
            return;
        }

        logger.info("登录失败=>{}", exception.getMessage());
        response.setContentType("application/json;charset=utf-8");
        // 优化：在CustomAuthenticationFailureHandler中返回从exception中提取的异常信息，而不要直接返回exception
        response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponseResult(exception.getMessage())));
        response.getWriter().flush();
    }
}
