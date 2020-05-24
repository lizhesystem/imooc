package com.lizhe.core.properties;

import lombok.Data;

/**
 * 验证码配置类
 *
 * @author lz
 * @create 2020-05-16
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties {
    private int width = 67;
    private int height = 23;
    // 短信验证码和图形验证码都有长度和过期时间，所以封装以下，继承
    //private int strLength = 4;  继承sms后这里可以注释了
    //private int durationSeconds = 60;
    // 需要拦截的URI列表，多个URI用逗号分隔
    private String uriPatterns;

    public ImageCodeProperties(){
        // 图形验证码默认显示6个字符
        this.setStrLength(6);
        // 图形验证码过期时间默认为3分钟
        this.setDurationSeconds(180);
    }
}
