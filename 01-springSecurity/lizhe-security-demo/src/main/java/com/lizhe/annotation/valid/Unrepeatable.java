package com.lizhe.annotation.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author lz
 * @create 2020/5/11
 * 自定义校验字段的注解,使用@Target({FIELD})代表作用到字段上，RUNTIME代表运行时加载
 * 参考NotNull、NotBlank，它们都有三个方法
 * 通过validatedBy属性指定该注解绑定的一系列校验类（这些校验类必须是ConstraintValidator<A,T>的实现类)，自己实现的
 *
 */

@Constraint(validatedBy = {UsernameUnrepeatableValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface Unrepeatable {

    String message() default "用户名已被注册";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
