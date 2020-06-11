package com.lizhe.alllearning.domain.dto;

import com.lizhe.alllearning.annotation.valid.Unrepeatable;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 类描述：数据查询DTO实体
 *
 * @author LZ on 2020/6/10
 */
@Data
public class UserQueryDTO implements Serializable {

    private static final long serialVersionUID = 5760528483868853406L;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户姓名不能为空！")
    @Unrepeatable
    private String username;
}
