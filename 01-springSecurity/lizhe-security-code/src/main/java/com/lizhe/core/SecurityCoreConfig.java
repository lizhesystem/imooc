package com.lizhe.core;

import com.lizhe.core.properties.SecurityProperties;
import com.lizhe.core.verifycode.sender.DefaultSmsCodeSender;
import com.lizhe.core.verifycode.sender.SmsCodeSender;
import com.lizhe.core.verifycode.po.ImageCode;
import com.lizhe.core.verifycode.generator.DefaultImageCodeGenerator;
import com.lizhe.core.verifycode.generator.VerifyCodeGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lz
 * @create 2020-05-15
 * 用来统一管理配置的配置对象。
 * @EnableConfigurationProperties ：用来启用在启动时将application.properties中的demo.security前缀的配置项注入到SecurityProperties中,统一管理
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {


    /**
     * 将该默认实现注入到容器中，
     * 注意@ConditionOnMissingBean是实现该模式的重点注解，标注了该注解的bean会在所有未标注@ConditionOnMissingBean的bean都被实例化注入到容器中后，
     * 判断容器中是否存在id为imageCodeGenerator的bean，如果不存在才会进行实例化并作为id为imageCodeGenerator的bean被使用
     * <p>
     * 也就是说假如我们再去实现一个ImageCodeGenerator接口注入容器的话，那么defaultImageCodeGenerator就不生效了。
     * 注入容器
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public VerifyCodeGenerator<ImageCode> imageCodeGenerator() {
        // 多态创建对象
        VerifyCodeGenerator  imageCodeGenerator = new DefaultImageCodeGenerator();
        return imageCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsCodeSender")
    public SmsCodeSender smsCodeSender() {
        SmsCodeSender smsCodeSender = new DefaultSmsCodeSender();
        return smsCodeSender;
    }
}
