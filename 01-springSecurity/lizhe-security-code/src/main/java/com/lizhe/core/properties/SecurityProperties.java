package com.lizhe.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lz
 * @create 2020-05-15
 * <p>
 * 封封装整个项目各模块的配置项
 */
@Data
@ConfigurationProperties(prefix = "demo.security")
public class SecurityProperties {

    /**
     * 请求html
     */
    private BrowserProperties browser = new BrowserProperties();

    /**
     * 验证码配置项
     */
    private VerifyCodeProperties code = new VerifyCodeProperties();
}
