package com.lizhe.core.verifycode.sender;

/**
 *  发送短信验证码实现类
 * @author lz
 * @create 2020-05-18
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String smsCode, String phoneNumber) {
        System.out.printf("向手机号%s发送短信验证码%s", phoneNumber, smsCode);
    }
}
