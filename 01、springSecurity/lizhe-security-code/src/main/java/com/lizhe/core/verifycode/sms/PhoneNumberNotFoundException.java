package com.lizhe.core.verifycode.sms;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 继承登录名未找到异常
 *
 * @author lz
 * @create 2020/5/19
 */
public class PhoneNumberNotFoundException extends UsernameNotFoundException {
    public static final String DEFAULT_MESSAGE = "电话号码不存在";

    public PhoneNumberNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public PhoneNumberNotFoundException(Throwable t) {
        super(DEFAULT_MESSAGE, t);
    }
}
