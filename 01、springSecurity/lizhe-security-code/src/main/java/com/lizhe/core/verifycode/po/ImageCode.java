package com.lizhe.core.verifycode.po;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 验证码类
 * (这里由于ImageCode和要定义的smsCode都有相同的两个属性，
 * 因此将SmsCode重命名为VerifyCode让ImageCode继承以复用代码)
 *
 * @author lz
 * @create 2020-05-16
 */
@Data
public class ImageCode extends VerifyCode {
    // 验证码
    //private String code;
    // 验证码对象
    private BufferedImage image;
    // 验证码过期时间
    //private LocalDateTime expireTime;

    public ImageCode(String code, BufferedImage image, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }

    /**
     * LocalDateTime类的plusSeconds()方法用于返回此日期时间的副本，其中添加了指定的秒数。
     * 此方法基于此日期时间(加上秒)返回LocalDateTime
     */
    public ImageCode(String code, BufferedImage image, int durationSeconds) {
        this(code, image, LocalDateTime.now().plusSeconds(durationSeconds));
    }

    /**
     * 比较现在的时间是不是比设定的时间之前，返回的类型是Boolean类型
     * @return
     */
    //public boolean isExpired() {
    //    return LocalDateTime.now().isAfter(expireTime);
    //}
}
