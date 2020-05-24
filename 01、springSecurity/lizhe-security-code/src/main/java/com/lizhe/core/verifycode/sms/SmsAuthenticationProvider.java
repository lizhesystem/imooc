package com.lizhe.core.verifycode.sms;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 自定义一个SmsAuthenticationProvider
 * @author lz
 * @create 2020/5/19
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public SmsAuthenticationProvider() {

    }

    /**
     * 该方法会被 AuthenticationManager调用，对authentication进行验证，
     * 并返回一个认证通过的{@link Authentication}
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 用户名密码登录方式需要在这里校验前端传入的密码和后端存储的密码是否一致
        // 但如果将短信验证码的校验放在这里的话就无法复用了，例如用户登录后访问“我的钱包”服务可能也需要发送短信验证码并进行验证
        // 因此短信验证码的校验逻辑单独抽取到一个过滤器里（留到后面实现）, 这里直接返回一个认证成功的authentication
        if (authentication instanceof SmsAuthenticationToken == false) {
            throw new IllegalArgumentException("仅支持对SmsAuthenticationToken的认证");
        }
        SmsAuthenticationToken authRequest = (SmsAuthenticationToken) authentication;
        UserDetails userDetails = getUserDetailsService().loadUserByUsername((String) authentication.getPrincipal());
        SmsAuthenticationToken successfulAuthentication = new SmsAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        return successfulAuthentication;
    }


    /**
     * Authentication的authenticate方法在遍历所有AuthenticationProvider时会调用该方法判断当前AuthenticationProvider是否对
     * 某个具体Authentication的校验
     * 重写此方法以支持对 {@link SmsAuthenticationToken} 的认证校验
     * @param clazz :支持的token类型
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        // 如果传入的类是否是SmsAuthenticationToken或其子类
        return SmsAuthenticationToken.class.isAssignableFrom(clazz);
    }


    private UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }


    /**
     * 提供对UserDetailsService的动态注入
     * @return
     */
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
