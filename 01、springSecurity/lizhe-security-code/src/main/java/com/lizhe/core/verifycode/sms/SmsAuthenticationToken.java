package com.lizhe.core.verifycode.sms;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 参考UsernamePasswordAuthenticationToken自己实现一个验证短信的认证规则（自定义）
 *
 * @author lz
 * @create 2020/5/19
 */
public class SmsAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    // ~ Instance fields
    // ================================================================================================
    /**
     * 认证前保存的是用户输入的手机号，认证成功后保存的是后端存储的用户详情
     */
    private final Object principal;

    // ~ Constructors
    // ===================================================================================================

    /**
     * This constructor can be safely used by any code that wishes to create a
     * <code>UsernamePasswordAuthenticationToken</code>, as the {@link #isAuthenticated()}
     * will return <code>false</code>.
     * 认证前时调用该方法封装请求参数成一个未认证的token => authRequest
     */
    public SmsAuthenticationToken(Object phoneNumber) {
        super(null);
        this.principal = phoneNumber;
        setAuthenticated(false);
    }

    /**
     * This constructor should only be used by <code>AuthenticationManager</code> or
     * <code>AuthenticationProvider</code> implementations that are satisfied with
     * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
     * authentication token.
     * <p>
     * 认证成功后需要调用该方法封装用户信息成一个已认证的token => successToken
     *
     * @param principal
     * @param credentials
     * @param authorities
     */
    public SmsAuthenticationToken(Object principal, Object credentials,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }

    // ~ Methods
    // ========================================================================================================

    /**
     * 用户名密码登录的凭证是密码，验证码登录不传密码
     *
     * @return
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }


}
