package com.modernframework.orm.criteria;


/**
 * 查询条件类型
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @version 1.0.0
 */
public enum CriteriaType {
    /**
     * 忽略该条件
     */
    ignore,
    /**
     * 该条件必须为正数
     */
    positive,
    /**
     * 该条件为主键
     */
    primaryKey,
    /**
     * 该条件为外键
     */
    foreignKey,
    /**
     * 该条件为相等判定
     */
    eq,
    /**
     * 该条件为不等判定
     */
    ne,
    /**
     * 该条件为模糊判定
     */
    like,
    /**
     * 该条件为大于判定
     */
    gt,
    /**
     * 该条件为小于判定
     */
    lt,
    /**
     * 该条件为小于等于判定
     */
    lte,
    /**
     * 该条件为大于等于判定
     */
    gte,
    /**
     * 该条件为范围判定
     */
    in,
    /**
     * 该条件null判定
     */
    isNull,
    /**
     * 该条件非null判定
     */
    isNotNull,
    /**
     * 该条件为范围判定，左小右大
     */
    between,
    /**
     * 该条件为范围反向判定
     */
    notIn;
}
