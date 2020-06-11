package com.lizhe.alllearning.annotation.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 接口描述：自定义校验字段的注解
 *
 * @author LZ on 2020/6/11
 */

@Constraint(validatedBy = {UsernameUnrepeatableValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface Unrepeatable {

    // 参考已有的约束注解如NotNull、NotBlank，它们都有三个方法
    String message() default "用户名已被注册";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
