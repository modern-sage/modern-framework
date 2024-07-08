package com.modernframework.orm;

/**
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface Constant {

    /**
     * 逻辑已删除
     */
    int DELETED = 1;

    /**
     * 逻辑未删除
     */
    int NOT_DELETED = 0;

    /**
     * 初始版本
     */
    int INIT_VERSION = 1;

    String TABLE_NAME = "tableName";
    String ATTR_ID = "id";
    String COLUMN_ID = "id";
    String ATTR_VERSION = "version";
    String COLUMN_VERSION = "version";
    String ATTR_DELETE_FLAG = "deleteFlag";
    String COLUMN_DELETE_FLAG = "delete_flag";
    String ATTR_CREATE_TIME = "createTime";
    String COLUMN_CREATE_TIME = "create_time";
    String ATTR_UPDATE_TIME = "updateTime";
    String COLUMN_UPDATE_TIME = "update_time";
    String ATTR_CREATOR_ID = "creatorId";
    String COLUMN_CREATOR_ID = "creator_id";
    String ATTR_UPDATER_ID = "updaterId";
    String COLUMN_UPDATER_ID = "updater_id";
    String ATTR_TENANT_ID = "tenantId";
    String COLUMN_TENANT_ID = "tenant_id";

}
