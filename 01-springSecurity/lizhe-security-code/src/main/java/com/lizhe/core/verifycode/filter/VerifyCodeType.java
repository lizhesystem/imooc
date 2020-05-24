package com.lizhe.core.verifycode.filter;

import com.lizhe.core.SecurityConstants;

/**
 * @author lz
 * @create 2020-05-19
 */
public enum VerifyCodeType {
    /**
     * 短信的枚举类属性
     */
    SMS {
        @Override
        public String getVerifyCodeParameterName() {
            return SecurityConstants.DEFAULT_SMS_CODE_PARAMETER_NAME;
        }
    },
    /**
     *  图形验证码的枚举类属性
     */
    IMAGE {
        @Override
        public String getVerifyCodeParameterName() {
            return SecurityConstants.DEFAULT_IMAGE_CODE_PARAMETER_NAME;
        }
    };

    public abstract String getVerifyCodeParameterName();
}
