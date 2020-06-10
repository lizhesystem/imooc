package com.lizhe.alllearning.domain.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：通用分页查询对象
 *
 * @author LZ on 2020/6/10
 */
@Data
public class PageQuery<T> implements Serializable {

    private static final long serialVersionUID = -6180055084337966921L;

    /**
     * 当前页
     */
    private Integer pageNo = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 20;

    /**
     * 动态查询条件
     */
    private T query;

}
