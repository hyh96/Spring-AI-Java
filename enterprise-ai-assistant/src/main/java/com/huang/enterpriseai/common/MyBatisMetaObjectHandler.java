package com.huang.enterpriseai.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

/**
 * @Author: huang
 * @Description: 统一拦截赋值所有时间
 * @DateTime: 2026/8/10 15:53
 **/
@Component
public class MyBatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        OffsetDateTime now = OffsetDateTime.now();

        this.strictInsertFill(
                metaObject,
                "createdAt",
                OffsetDateTime.class,
                now
        );

        this.strictInsertFill(
                metaObject,
                "updatedAt",
                OffsetDateTime.class,
                now
        );
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.strictUpdateFill(
                metaObject,
                "updatedAt",
                OffsetDateTime.class,
                OffsetDateTime.now()
        );
    }
}