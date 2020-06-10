package com.lizhe.alllearning.domain.dto;

import lombok.Data;

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
    private String username;
}
