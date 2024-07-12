package com.modernframework.base.criteria;


/**
 * 常量
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface Constant {


    /**
     * 排序逆序
     */
    Integer DESC = 0;
    /**
     * 排序正序
     */
    Integer ASC = 1;

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

    // 字段
    /**
     * 逻辑删除字段
     */
    String ATTR_DELETE_FLAG = "deleteFlag";
    /**
     * 逻辑删除字段
     */
    String COLUMN_DELETE_FLAG = "delete_flag";

}
