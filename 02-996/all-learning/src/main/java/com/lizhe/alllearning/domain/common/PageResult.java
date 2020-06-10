package com.lizhe.alllearning.domain.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：通用分页查询返回实体
 *
 * @author LZ on 2020/6/10
 */

@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 6868979826735323161L;

    /**
     * 当前页号
     */
    private Integer pageNo;

    /**
     * 每页多少行
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long pageNum;

    private T data;
}
