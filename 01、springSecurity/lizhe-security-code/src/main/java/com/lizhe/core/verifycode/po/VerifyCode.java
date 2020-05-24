package com.lizhe.core.verifycode.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 封装短信验证码的类
 *
 * @author lz
 * @create 2020-05-18
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCode {
    private String code;
    protected LocalDateTime expireTime;

    /**
     *  判断传入的时间是否在当前时间之后
     * @return
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
