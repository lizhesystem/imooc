package com.lizhe.core.verifycode.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常类
 *
 * @author lz
 * @create 2020-05-16
 */
public class VerifyCodeException extends AuthenticationException {
    public VerifyCodeException(String explanation) {
        super(explanation);
    }
}
