package com.lizhe.alllearning.domain.common;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "页号不能为空")
    @Min(value = 1, message = "页号必须为正数！")
    private Integer pageNo = 1;

    /**
     * 每页条数
     */
    @NotNull(message = "每页条数不能为空")
    @Max(value = 100, message = "每页条数不能超过100条！")
    private Integer pageSize = 20;

    /**
     * 动态查询条件
     *
     * @Valid 代表级联校验 验证PageQuery这个实体的时候，如果发现下面的query对象
     * 也有使用验证框架的属性的话，级联追踪到这个实体下面是否也满足条件
     *  比如下面有PageQuery<UserQueryDTO> 要看UserQueryDTO的验证是否满足
     */
    @Valid
    @NotNull(message = "动态查询条件不能为空！")
    private T query;

}
