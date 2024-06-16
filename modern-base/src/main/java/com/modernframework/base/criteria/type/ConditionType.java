package com.modernframework.base.criteria.type;


import lombok.Getter;

import java.util.Arrays;

/**
 * 查询条件类型
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 */
@Getter
public enum ConditionType {
    /**
     * 忽略该条件
     */
    IGNORE((short) 0),
    /**
     * 该条件必须为正数
     */
    POSITIVE((short) 10),
    /**
     * 该条件为主键
     */
    PRIMARY_KEY((short) 11),
    /**
     * 该条件为相等判定
     */
    EQ((short) 21),
    /**
     * 该条件为不等判定
     */
    NOT_EQUAL((short) 22),
    /**
     * 该条件为模糊判定
     */
    LIKE((short) 23),
    /**
     * 该条件为大于判定
     */
    GT((short) 24),
    /**
     * 该条件为小于判定
     */
    LT((short) 25),
    /**
     * 该条件为小于等于判定
     */
    LE((short) 26),
    /**
     * 该条件为大于等于判定
     */
    GE((short) 27),
    /**
     * 该条件为范围判定
     */
    IN((short) 30),
    /**
     * 该条件null判定
     */
    IS_NULL((short) 31),
    /**
     * 该条件非null判定
     */
    IS_NOT_NULL((short) 32),
    /**
     * 该条件为范围判定，左小右大
     */
    BETWEEN((short) 33),
    /**
     * 该条件为范围反向判定
     */
    NOT_IN((short) 34),

    /**
     * 该条件表示所有条件用equal查询
     */
    EQ_ALL((short) 35),
    /**
     * 左模糊判定
     */
    LIKE_LEFT((short) 2301),
    /**
     * 右模糊判定
     */
    LIKE_RIGHT((short) 2302),
    ;

    /**
     * 枚举值，不同枚举定义不同的值进行区分
     * -- GETTER --
     *  获取值
     *
     * @return the value

     */
    private short value;

    /**
     * 构造器
     *
     * @param value 查询类型
     */
    ConditionType(short value) {
        this.value = value;
    }

    /**
     * 根据枚举值获取枚举对象
     *
     * @param value 查询类型
     * @return CriteriaType
     */
    public static ConditionType get(short value) {
        for (ConditionType type : ConditionType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        //找不到匹配的值则忽略条件
        return IGNORE;
    }

    /**
     * 根据枚举名称获取枚举对象
     *
     * @param name 名称值
     * @return CriteriaType
     */
    public static ConditionType get(String name) {
        return Arrays.stream(ConditionType.values())
                .filter(n -> n.name().equals(name))
                .findFirst()
                //找不到匹配的值则忽略条件
                .orElse(IGNORE);
    }

    /**
     * 根据枚举名称获取枚举对象
     *
     * @param name 名称值
     * @return CriteriaType
     */
    public static ConditionType getNameIgnoreCase(String name) {
        return Arrays.stream(ConditionType.values())
                .filter(n -> n.name().equalsIgnoreCase(name))
                .findFirst()
                //找不到匹配的值则忽略条件
                .orElse(IGNORE);
    }

    /**
     * 获取姓名
     *
     * @return the name
     */
    public String getName() {
        return name();
    }

}
