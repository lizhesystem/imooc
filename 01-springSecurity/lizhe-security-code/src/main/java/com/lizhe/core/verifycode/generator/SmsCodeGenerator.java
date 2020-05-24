package com.lizhe.core.verifycode.generator;

import com.lizhe.core.properties.SecurityProperties;
import com.lizhe.core.verifycode.po.VerifyCode;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 短信验证码实现类
 *
 * @author lz
 * @create 2020-05-18
 */

@Component("smsCodeGenerator")
public class SmsCodeGenerator implements VerifyCodeGenerator<VerifyCode> {


    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public VerifyCode generateVerifyCode() {
        // 随机生成一串纯数字字符串，数字个数为 strLength
        String randomCode = RandomStringUtils.randomNumeric(securityProperties.getCode().getImage().getStrLength());
        // 封装验证码对象，默认60秒
        return new VerifyCode(randomCode, LocalDateTime.now().plusSeconds(securityProperties.getCode().getImage().getDurationSeconds()));
    }
}
