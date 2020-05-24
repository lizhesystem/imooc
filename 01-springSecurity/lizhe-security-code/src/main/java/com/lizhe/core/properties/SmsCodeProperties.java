package com.lizhe.core.properties;

import lombok.Data;

/**
 * @author lz
 * @create 2020-05-18
 */
@Data
public class SmsCodeProperties {
    // 短信验证码个数,默认为4
    private int strLength = 4;
    // 有效时间，默认60秒
    private int durationSeconds = 60;
    // 需要拦截的URI列表，多个URI用逗号分隔
    private String uriPatterns;
}
