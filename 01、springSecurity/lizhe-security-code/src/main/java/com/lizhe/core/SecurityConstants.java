package com.lizhe.core;

/**
 * @author lz
 * @create 2020-05-18
 */
public interface SecurityConstants {


    /**
     * 表单密码登录URL
     */
    String DEFAULT_FORM_LOGIN_URL = "/auth/login";

    /**
     * 短信登录URL
     */
    String DEFAULT_SMS_LOGIN_URL = "/auth/sms";

    /**
     * 前端图形验证码参数名
     */
    String DEFAULT_IMAGE_CODE_PARAMETER_NAME = "imageCode";

    /**
     * 前端短信验证码参数名
     */
    String DEFAULT_SMS_CODE_PARAMETER_NAME = "smsCode";

    /**
     * 图形验证码缓存在Session中的key
     */
    String IMAGE_CODE_SESSION_KEY = "IMAGE_CODE_SESSION_KEY";

    /**
     * 短信验证码缓存在Session中的key
     */
    String SMS_CODE_SESSION_KEY = "SMS_CODE_SESSION_KEY";

    /**
     * 验证码校验器bean名称的后缀
     */
    String VERIFY_CODE_VALIDATOR_NAME_SUFFIX = "CodeValidatorImpl";

    /**
     * 未登录访问受保护URL则跳转路径到 此
     */
    String FORWARD_TO_LOGIN_PAGE_URL = "/auth/require";

    /**
     * 用户点击发送验证码调用的服务
     */
    String VERIFY_CODE_SEND_URL = "/verifyCode/**";
}
