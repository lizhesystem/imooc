package com.lizhe.core.verifycode.constont;

import jdk.nashorn.internal.ir.CallNode;

/**
 * 常量类，用于存放一些常量
 * @author lz
 * @create 2020-05-18
 */
public class VerifyCodeConstant {
    // 图片验证码
    public static final String IMAGE_CODE_SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    // 短信验证码
    public static final String SMS_CODE_SESSION_KEY = "SESSION_KEY_SMS_CODE";

    //
    public static final String VERIFY_CODE_PROCESSOR_IMPL_SUFFIX = "CodeProcessorImpl";

    public static final String VERIFY_CODE_GENERATOR_IMPL_SUFFIX = "CodeGenerator";

    // 手机号
    public static final String PHONE_NUMBER_PARAMETER_NAME = "phoneNumber";

}
