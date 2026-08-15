package com.huang.enterpriseai.common;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Date;

/**
 * @Author: huang
 * @Description: 统一拦截赋值所有时间
 * @DateTime: 2026/8/10 15:53
 **/
@Component
public class MyBatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        this.strictInsertFill(
                metaObject,
                "createdAt",
                Date.class,
                now
        );

        this.strictInsertFill(
                metaObject,
                "updatedAt",
                Date.class,
                now
        );
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(
                metaObject,
                "updatedAt",
                Date.class,
                new Date()
        );
    }
}

