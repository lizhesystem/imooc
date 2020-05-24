package com.lizhe.core.verifycode.constont;

/**
 * @author lz
 * @create 2020-05-18
 */
public enum VerifyCodeTypeEnum {

    /**
     *  图片枚举,短信枚举
     */
    IMAGE("image"), SMS("sms");

    private String type;

    public String getType() {
        return type;
    }

    VerifyCodeTypeEnum(String type) {
        this.type = type;
    }
}
