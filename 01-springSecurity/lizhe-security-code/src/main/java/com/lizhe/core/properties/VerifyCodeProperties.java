package com.lizhe.core.properties;

import lombok.Data;

/**
 * 封装图形验证码和短信验证码
 * @author lz
 * @create 2020-05-16
 */
@Data
public class VerifyCodeProperties {
    // 图形验证码
    private ImageCodeProperties image = new ImageCodeProperties();

    // 短信验证码
    private SmsCodeProperties sms = new SmsCodeProperties();
}
