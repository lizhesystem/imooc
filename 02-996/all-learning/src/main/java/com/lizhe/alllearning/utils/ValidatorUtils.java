package com.lizhe.alllearning.utils;


import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 类描述：校验工具类
 *
 * @author LZ on 2020/6/11
 */
public class ValidatorUtils {

    /**
     * 校验器
     */
    private static Validator validator = Validation
            .buildDefaultValidatorFactory().getValidator();

    public static <T> void validate(T object, Class... groups) {
        // 收集校验的结果集合
        Set<ConstraintViolation<T>> validate =
                validator.validate(object, groups);

        // 处理：如果校验结果不为空
        if (!CollectionUtils.isEmpty(validate)) {
            StringBuilder exceptionMessage = new StringBuilder();
            validate.forEach(constraintViolation -> {
                exceptionMessage.append(constraintViolation.getMessage());
            });
            // 异常抛出
            throw new RuntimeException(exceptionMessage.toString());
        }
    }
}
