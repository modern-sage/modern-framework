package com.modernframework.base.criteria;


import com.modernframework.base.criteria.type.ConditionType;

import java.io.Serializable;

/**
 * 查询条件接口参数
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface CriteriaParam<T> extends CriteriaExpress, Serializable {

    /**
     * 获取查询条件类型
     *
     * @return CriteriaType
     */
    String getConditionType();

    /**
     * 获取查询条件名称
     *
     * @return String
     */
    String getAttribute();

    /**
     * 获取查询条件值
     *
     * @return T
     */
    T getValue();
}
