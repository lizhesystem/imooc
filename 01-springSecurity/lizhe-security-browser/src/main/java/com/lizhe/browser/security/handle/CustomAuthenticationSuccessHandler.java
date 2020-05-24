package com.lizhe.browser.security.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lizhe.core.properties.LoginProcessTypeEnum;
import com.lizhe.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lz
 * @create 2020-05-16
 * 认证成功的自定义处理类
 */
@Component("customAuthenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 在登录成功后，我们会拿到一个Authentication，这也是security的一个核心接口，
     * 作用是封装用户的相关信息，这里我们将其转成JSON串响应给前端看一下它包含了哪些内容
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (securityProperties.getBrowser().getLoginProcessType() == LoginProcessTypeEnum.REDIRECT) {
            // 重定向到缓存在session中的登录前请求的URL
            super.onAuthenticationSuccess(request,response,authentication);
            return;
        }

        logger.info("用户{}登录成功", authentication.getName());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(authentication));
        response.getWriter().flush();
    }
}

/**
 * {
 * "authorities": [
 * {
 * "authority": "admin"
 * },
 * {
 * "authority": "user"
 * }
 * ],
 * "details": {
 * "remoteAddress": "127.0.0.1",
 * "sessionId": "3DDA54D65C4223B6A719446A8FCAEA9F"
 * },
 * "authenticated": true,
 * "principal": {
 * "password": null,
 * "username": "admin",
 * "authorities": [
 * {
 * "authority": "admin"
 * },
 * {
 * "authority": "user"
 * }
 * ],
 * "accountNonExpired": true,
 * "accountNonLocked": true,
 * "credentialsNonExpired": true,
 * "enabled": true
 * },
 * "credentials": null,
 * "name": "admin"
 * }
 */
