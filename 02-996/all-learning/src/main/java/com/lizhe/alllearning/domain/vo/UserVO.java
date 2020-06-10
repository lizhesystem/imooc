package com.lizhe.alllearning.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户VO实体:用户视图层展示和收集
 *
 * @author LZ on 2020/6/10
 */
@Data
public class UserVO implements Serializable {


    private static final long serialVersionUID = -5931024097936353371L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String phone;
}
