package com.lizhe.alllearning.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：UserDTO用户实体信息  (数据中间交换)
 *
 * @author LZ on 2020/6/10
 */

@Data
public class UserDTO  implements Serializable {

    private static final long serialVersionUID = -5176066228934038385L;


    /**
     * 姓名
     */
    private String username;

    /**
     * 密码
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
     * 电话
     */
    private String phone;

    /**
     * 版本号
     */
    private Long version;


}
