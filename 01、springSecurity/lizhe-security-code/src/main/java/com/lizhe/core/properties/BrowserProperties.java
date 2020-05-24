package com.lizhe.core.properties;

import lombok.Data;

/**
 * @author lz
 * @create 2020-05-15
 * <p>
 * 封装security-browser模块的配置项
 */

@Data
public class BrowserProperties {
    /**
     * 提供一个默认的登录页
     */
    private String loginPage = "/sign-in.html";

    /**
     * 提供一个默认配置，返回结果是JSON还是重定向
     * 这里的默认参数没有直接写死，而是搞了一个枚举类来配置,默认返回JSON
     */
    private LoginProcessTypeEnum loginProcessType = LoginProcessTypeEnum.JSON;


}
