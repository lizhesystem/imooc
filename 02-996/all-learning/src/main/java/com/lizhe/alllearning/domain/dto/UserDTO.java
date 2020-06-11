package com.lizhe.alllearning.domain.dto;

import com.lizhe.alllearning.utils.InsertValidationGroup;
import com.lizhe.alllearning.utils.UpdateValidationGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 类描述：UserDTO用户实体信息  (数据中间交换)
 *
 * @author LZ on 2020/6/10
 */

@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -5176066228934038385L;


    /**
     * 姓名
     */
    @NotBlank(message = "用户名不能为空",
            groups = {InsertValidationGroup.class}
    )
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空",
            groups = {InsertValidationGroup.class}
    )
    @Length(min = 6, max = 18,
            message = "密码长度不能小于6,不能多于18位！")
    private String password;

    /**
     * 邮箱
     */
    @NotEmpty(message = "邮箱不能为空！",
            groups = {InsertValidationGroup.class}
    )
    @Email(message = "必须为有效邮箱！")
    private String email;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不能为空！",
            groups = {InsertValidationGroup.class})
    @Max(value = 60, message = "年龄不能大于60岁！")
    @Min(value = 18, message = "年龄不能小于18岁！")
    private Integer age;

    /**
     * 电话
     */
    @NotNull(message = "手机号不能为空！",
            groups = {InsertValidationGroup.class})
    private String phone;

    /**
     * 版本号
     */
    @NotNull(message = "版本号不能为空",
            groups = {UpdateValidationGroup.class})
    private Long version;


}
