package com.modern.orm.mp.config.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.modernframework.base.security.context.SessionUser;
import com.modernframework.base.security.context.UserContext;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

import static com.modernframework.orm.DataOrmConstant.*;


/**
 * 处理公共字段
 */
public class MdMpPublicFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //1.创建对象默认设置版本为1
        Object version = getFieldValByName(ATTR_VERSION, metaObject);
        if (null == version && metaObject.hasSetter(ATTR_VERSION)) {
            strictInsertFill(metaObject, ATTR_VERSION, Integer.class, INIT_VERSION);
        }
        //3.创建对象设置默认删除标记为删除默认值
        Object deleteFlag = getFieldValByName(ATTR_DELETE_FLAG, metaObject);
        if (null == deleteFlag && metaObject.hasSetter(ATTR_DELETE_FLAG)) {
            strictInsertFill(metaObject, ATTR_DELETE_FLAG, Integer.class, NOT_DELETED);
        }

        //4.创建对象设置默认创建时间为系统当前时间
        Object tVal = getFieldValByName(ATTR_CREATE_TIME, metaObject);
        LocalDateTime t = LocalDateTime.now();
        if (null == tVal && metaObject.hasSetter(ATTR_CREATE_TIME)) {
            strictInsertFill(metaObject, ATTR_CREATE_TIME, LocalDateTime.class, t);
        }
        //5.创建对象设置默认更新时间为系统当前时间
        tVal = getFieldValByName(ATTR_UPDATE_TIME, metaObject);
        if (null == tVal && metaObject.hasSetter(ATTR_UPDATE_TIME)) {
            strictInsertFill(metaObject, ATTR_UPDATE_TIME, LocalDateTime.class, t);
        }

        //6.设置创建人ID
        SessionUser user = UserContext.getUser();
        Long userId = user == null ? null : user.getId();
        if (metaObject.hasSetter(ATTR_CREATOR_ID)) {
            strictInsertFill(metaObject, ATTR_CREATOR_ID, Long.class, userId);
        }

        //7.设置更新人ID
        if (metaObject.hasSetter(ATTR_UPDATER_ID)) {
            strictInsertFill(metaObject, ATTR_UPDATER_ID, Long.class, userId);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //1.创建对象设置默认创建时间为系统当前时间
        Object tVal = getFieldValByName(ATTR_UPDATE_TIME, metaObject);
        if (null == tVal && metaObject.hasSetter(ATTR_UPDATE_TIME)) {
            strictUpdateFill(metaObject, ATTR_UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
        }

        //2.设置更新人ID
        SessionUser user = UserContext.getUser();
        Long userId = user == null ? null : user.getId();
        if (metaObject.hasSetter(ATTR_UPDATER_ID)) {
            strictInsertFill(metaObject, ATTR_UPDATER_ID, Long.class, userId);
        }
    }
}
