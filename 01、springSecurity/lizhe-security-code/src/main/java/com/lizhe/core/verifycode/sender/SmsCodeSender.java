package com.lizhe.core.verifycode.sender;

/**
 * 抽象出来的短信发发送接口
 * @author lz
 * @create 2020-05-18
 */
public interface SmsCodeSender {
    /**
     * 根据手机号发送短信验证码
     * @param smsCode
     * @param phoneNumber
     */
    void send(String smsCode,String phoneNumber);
}
