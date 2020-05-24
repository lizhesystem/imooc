package com.lizhe.annotation.valid;

import com.lizhe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.SQLOutput;

/**
 * @author lz
 * @create 2020/5/11
 * 自定义注解约束；
 * 其中：ConstraintValidator<A,T>泛型A指定为要绑定到的注解，@Unrepeatable
 *      T指定要校验字段的类型；
 *      isValid 方法定义校验方法。
 *
 *  * @ComponentScan 扫描范围内的ConstraintValidator实现类会被Spring注入到容器中，
 *  * 因此无须在该类上标注Component即可在类中注入其他Bean，例如本例中注入了一个UserService
 */
public class UsernameUnrepeatableValidator implements ConstraintValidator<Unrepeatable, String> {

    /**
     * 模拟一个service
     */
    @Autowired
    private UserService userService;

    @Override
    public void initialize(Unrepeatable unrepeatableAnnotation) {
        System.out.println(unrepeatableAnnotation);
        System.out.println("UsernameUnrepeatableValidator initialized===================");

    }

    /**
     *   编写自定义校验逻辑，比如查数据库是否存在，根据返回的boolean值来校验。
     * @param value
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("the request username is " + value);
        boolean ifExists = userService.checkUsernameIfExists(value);
        // 返回true或者false来决定验证规则
        return !ifExists;
    }
}
