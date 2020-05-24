package com.lizhe.core.verifycode.sms;

import com.lizhe.core.verifycode.sms.SmsAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lz
 * @create 2020/5/19
 */

public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    // ~ Static fields/initializers
    // =====================================================================================

    public static final String SPRING_SECURITY_FORM_PHONE_NUMBER_KEY = "phoneNumber";

    private String phoneNumberParameter = SPRING_SECURITY_FORM_PHONE_NUMBER_KEY;
    private boolean postOnly = true;

    // ~ Constructors
    // ===================================================================================================

    public SmsAuthenticationFilter() {
        super(new AntPathRequestMatcher("/auth/sms", "POST"));
    }

    // ~ Methods
    // ========================================================================================================

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String phoneNumber = obtainPhoneNumber(request);

        if (phoneNumber == null) {
            phoneNumber = "";
        }

        phoneNumber = phoneNumber.trim();

        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(phoneNumber);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Enables subclasses to override the composition of the phoneNumber, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the phoneNumber that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    protected String obtainPhoneNumber(HttpServletRequest request) {
        return request.getParameter(phoneNumberParameter);
    }

    /**
     * Sets the parameter name which will be used to obtain the phoneNumber from the login
     * request.
     *
     * @param phoneNumberParameter the parameter name. Defaults to "phoneNumber".
     */
    public void setPhoneNumberParameter(String phoneNumberParameter) {
        Assert.hasText(phoneNumberParameter, "phoneNumber parameter must not be empty or null");
        this.phoneNumberParameter = phoneNumberParameter;
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getPhoneNumberParameter() {
        return phoneNumberParameter;
    }

}
