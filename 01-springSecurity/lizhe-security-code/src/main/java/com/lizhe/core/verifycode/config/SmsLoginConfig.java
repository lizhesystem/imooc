package com.lizhe.core.verifycode.config;

import com.lizhe.core.verifycode.sms.SmsAuthenticationFilter;
import com.lizhe.core.verifycode.sms.SmsAuthenticationProvider;
import com.lizhe.core.verifycode.sms.SmsUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 各个环节的组件我们都实现了，现在我们需要写一个配置类将这些组件串起来，告诉security这些自定义组件的存在。
 * 由于短信登录方式在PC端和移动端都用得上，因此我们将其定义在security-core中
 *
 * @author lz
 * @create 2020/5/19
 */
@Component
public class SmsLoginConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    // 成功的回调
    @Autowired
    AuthenticationSuccessHandler customAuthenticationSuccessHandler;
    // 失败的回调
    @Autowired
    AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    UserDetailsService smsUserDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        // 认证过滤器会请求AuthenticationManager认证authRequest，因此我们需要为其注入AuthenticatonManager，
        // 但是该实例是由Security管理的，我们需要通过getSharedObject来获取
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        // 认证成功/失败处理器还是使用之前的

        smsAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);

        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        // 将SmsUserDetailsService注入到SmsAuthenticationProvider中
        // 自己new一下,要不然提示会有2个Bean
        SmsUserDetailsServiceImpl smsUserDetailsService = new SmsUserDetailsServiceImpl();
        smsAuthenticationProvider.setUserDetailsService(smsUserDetailsService);
        // 将SmsAuthenticationProvider加入到Security管理的AuthenticationProvider集合中
        http.authenticationProvider(smsAuthenticationProvider)
                // 注意要添加到UsernamePasswordAuthenticationFilter之后，自定义的认证过滤器都应该添加到其之后，自定义的验证码等过滤器都应该添加到其之前
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


    }
}
