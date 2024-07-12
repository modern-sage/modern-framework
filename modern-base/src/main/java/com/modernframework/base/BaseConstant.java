package com.modernframework.base;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;


/**
 * 常量
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface BaseConstant {

    Integer YES = 1;

    Integer NO = 0;

    /**
     * 排序逆序
     */
    Integer DESC = 0;
    /**
     * 排序正序
     */
    Integer ASC = 1;

    /**
     * token 名称
     */
    String TOKEN = "MD-Token";

    /**
     * 默认日期格式
     */
    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 连接关系 and
     */
    String RELATIONSHIP_AND = "AND";

    /**
     * 连接关系 or
     */
    String RELATIONSHIP_OR = "OR";

    /**
     * 内嵌关系开始符号
     */
    String RELATIONSHIP_EMBEDDED_START = "(";

    /**
     * 内嵌关系开始符号
     */
    String RELATIONSHIP_EMBEDDED_END = ")";

    // ------- entity schema ------
    String TABLE_NAME = "tableName";
    String ATTR_ID = "id";
    String COLUMN_ID = "id";
    String ATTR_VERSION = "version";
    String COLUMN_VERSION = "version";
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
    String ATTR_DELETE_FLAG = "deleteFlag";
    String COLUMN_DELETE_FLAG = "delete_flag";
    List<String> BUILT_IN_ATTRS = Arrays.asList(
            ATTR_ID, ATTR_VERSION, ATTR_DELETE_FLAG, ATTR_CREATE_TIME, ATTR_UPDATE_TIME,
            ATTR_CREATOR_ID, ATTR_UPDATER_ID, ATTR_TENANT_ID
    );
    // ------- entity schema ------

    // -------- entity data ---------------

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

    // -------- entity data ---------------

    /**
     * 查询最大上限（5w），避免全量查询
     */
    Integer EXPORT_QUERY_UPPER_LIMIT = 5 * 10000;

    /**
     * csv 字符处理
     */
    byte[] BYTES_ENTER = "\n".getBytes(StandardCharsets.UTF_8);

    byte[] BYTES_SEPARATOR = ",".getBytes(StandardCharsets.UTF_8);

}
