package com.lizhe.core.verifycode.filter;

import com.lizhe.core.SecurityConstants;
import com.lizhe.core.verifycode.exception.VerifyCodeException;
import com.lizhe.core.verifycode.generator.VerifyCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *  利用Spring的依赖查找，聚集容器中所有的
 * @author lz
 * @create 2020-05-19
 */
@Component
public class VerifyCodeValidatorHolder {

    /**
     * Spring高级特性 自动装配
     * Spring会查找容器中所有实现{@link AbstractVerifyCodeValidator}的实例并以 key=beanId,value=bean的形式注入到该map中
     * 这个特性的其实还得归纳到@Autowired的特性上，或者说@Autowired包涵了一些我们不知道的能力。
     */
    @Autowired
    private Map<String, AbstractVerifyCodeValidator> verifyCodeValidatorMap = new HashMap<>();

    public AbstractVerifyCodeValidator getVerifyCodeValidator(VerifyCodeType verifyCodeType) {
        AbstractVerifyCodeValidator abstractVerifyCodeValidator =
                verifyCodeValidatorMap.get(verifyCodeType.toString().toLowerCase() + SecurityConstants.VERIFY_CODE_VALIDATOR_NAME_SUFFIX);
        if (Objects.isNull(verifyCodeType)) {
            throw new VerifyCodeException("不支持的验证码类型:" + verifyCodeType);
        }
        return abstractVerifyCodeValidator;
    }
}
