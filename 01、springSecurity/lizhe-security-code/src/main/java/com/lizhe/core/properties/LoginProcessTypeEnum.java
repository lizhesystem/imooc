package com.lizhe.core.properties;

/**
 * @author lz
 * @create 2020-05-16
 */
public enum LoginProcessTypeEnum {
    /**
     * 重定向到之前的请求页或登录失败页
     */
    REDIRECT("redirect"),

    /**
     * 登录成功返回用户信息，登录失败返回错误信息
     */
    JSON("json");

    private final String type;

    LoginProcessTypeEnum(String type) {
        this.type = type;
    }
}
