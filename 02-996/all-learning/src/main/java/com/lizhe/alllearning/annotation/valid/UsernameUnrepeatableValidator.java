package com.lizhe.alllearning.annotation.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 接口描述：自定义校验注解
 *
 * @author LZ on 2020/6/11
 */
public class UsernameUnrepeatableValidator implements ConstraintValidator<Unrepeatable, String> {

    /**
     * 模拟一个service
     */
    //@Autowired
    //private UserService userService;
    @Override
    public void initialize(Unrepeatable constraintAnnotation) {

    }

    /**
     * 编写自定义校验逻辑，比如查数据库是否存在，根据返回的boolean值来校验。
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("the request username is " + value);
        // 返回true或者false来决定验证规则
        return !"username001".equals(value);
    }
}
