package com.lizhe.alllearning.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 类描述：公共元数据处理器 新增or修改自动填充
 *
 * @author LZ on 2020/6/10
 */
@Component
@Slf4j
public class CommonMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入处理自动填充字段：mybatisPlus功能
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("新建时,开始填充系统字段");

        // 创建时间
        this.strictInsertFill(metaObject, "created",
                LocalDateTime.class, LocalDateTime.now());

        // 修改时间
        this.strictInsertFill(metaObject, "modified",
                LocalDateTime.class, LocalDateTime.now());

        // 可以从上下文获取当前登录用户或者其他信息,自己定义
        this.strictInsertFill(metaObject, "creator",
                String.class, "TODO  从上下文获取创建人");

        this.strictInsertFill(metaObject, "operator",
                String.class, "TODO  从上下文获取当前人");

        // 默认状态
        this.strictInsertFill(metaObject, "status",
                Integer.class, 0);

        // 版本
        this.strictInsertFill(metaObject, "version",
                Long.class, 1L);
    }

    /**
     * 更新处理
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        log.info("更新时,开始填充系统字段!");

        // 修改时间
        this.strictUpdateFill(metaObject, "modified",
                LocalDateTime.class, LocalDateTime.now());

        // 修改人
        this.strictInsertFill(metaObject, "operator",
                String.class, "TODO 从上下文获取修改人");
    }
}
