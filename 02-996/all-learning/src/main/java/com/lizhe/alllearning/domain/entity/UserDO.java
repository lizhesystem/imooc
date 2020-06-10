package com.lizhe.alllearning.domain.entity;/**
 * @author lz
 * @create 2020/6/9
 */

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户DO实体
 *
 * @author LZ on 2020/6/9
 */
@Data
@TableName("user")
public class UserDO implements Serializable {

    private static final long serialVersionUID = -5871697837354156011L;

    /**
     * 主键id 雪花算法生成id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

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
     * 数据创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    /**
     * 数据修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modified;

    /**
     * 最后修改者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String operator;

    /**
     * 逻辑删除字段：0：正常，1：逻辑删除
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer status;

    /**
     * 版本号  update/insert的时候自动+1
     *  使用@Version注解： 乐观锁的配置，举个例子如果更新的数据当前数据库版本号1 传递的是2 那么此条更新语句没效果
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Long version;

}
