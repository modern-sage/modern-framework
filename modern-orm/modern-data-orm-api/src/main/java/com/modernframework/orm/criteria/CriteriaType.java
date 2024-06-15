package com.modernframework.orm.criteria;


import com.modernframework.core.utils.StringUtils;

/**
 * 查询条件类型
 *
 * @author jjz
 */
public enum CriteriaType {
    /**
     * 忽略该条件
     */
    Ignore((short) 0),
    /**
     * 该条件必须为正数
     */
    Positive((short) 10),
    /**
     * 该条件为主键
     */
    PrimaryKey((short) 11),
    /**
     * 该条件为外键
     */
    ForeignKey((short) 12),
    /**
     * 该条件为相等判定
     */
    Equal((short) 21),
    /**
     * 该条件为不等判定
     */
    NotEqual((short) 22),
    /**
     * 该条件为模糊判定
     */
    Like((short) 23),
    /**
     * 该条件为大于判定
     */
    Greaterthan((short) 24),
    /**
     * 该条件为小于判定
     */
    Lessthan((short) 25),
    /**
     * 该条件为小于等于判定
     */
    LessthanOrEqual((short) 26),
    /**
     * 该条件为大于等于判定
     */
    GreaterthanOrEqual((short) 27),
    /**
     * 该条件为范围判定
     */
    In((short) 30),
    /**
     * 该条件null判定
     */
    IsNull((short) 31),
    /**
     * 该条件非null判定
     */
    IsNotNull((short) 32),
    /**
     * 该条件为范围判定，左小右大
     */
    Between((short) 33),
    /**
     * 该条件为范围反向判定
     */
    NotIn((short) 34);

    /**
     * 枚举值，不同枚举定义不同的值进行区分
     */
    private short value;

    /**
     * 构造器
     *
     * @param value
     */
    CriteriaType(short value) {
        this.value = value;
    }

    /**
     * 根据枚举值获取枚举对象
     *
     * @param value
     * @return
     */
    public static CriteriaType get(short value) {
        for (CriteriaType type : CriteriaType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }

    /**
     * 根据枚举名称获取枚举对象
     *
     * @param name
     * @return
     */
    public static CriteriaType get(String name) {
        if (EnumUtils.contains(CriteriaType.class, name)) {
            return Enum.valueOf(CriteriaType.class, name);
        }
        return null;
    }

    /**
     * 根据枚举名称获取枚举对象
     *
     * @param name
     * @return
     */
    public static CriteriaType getNameIgnoreCase(String name) {
        for (CriteriaType type : CriteriaType.values()) {
            if (StringUtils.equalsIgnoreCase(type.getName(), name)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 获取姓名
     *
     * @return the name
     */
    public String getName() {
        return name();
    }

    /**
     * 获取值
     *
     * @return the value
     */
    public short getValue() {
        return value;
    }

}
