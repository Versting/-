package com.twotwo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.twotwo.common.UserContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义元数据对象处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入操作，自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
//        if (metaObject.hasSetter("userId")) {
//            metaObject.setValue("userId", UserContext.getId());
//        }
        if (metaObject.hasSetter("createTime")) {
            metaObject.setValue("createTime", new Date());
        }
    }

    /**
     * 更新操作，自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", new Date());
    }
}