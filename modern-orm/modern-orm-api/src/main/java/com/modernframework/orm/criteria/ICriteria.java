package com.modernframework.orm.criteria;


import com.modernframework.core.utils.StringUtils;

import java.io.Serializable;

/**
 * 查询条件接口参数
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 */
public interface ICriteria<T> extends Serializable {

    /**
     * 获取查询条件类型
     *
     * @return
     */
    CriteriaType getType();

    /**
     * 获取查询条件名称
     *
     * @return
     */
    String getAttribute();

    /**
     * 获取条件属性对应的列名
     *
     * @return
     */
    default String getColumnName() {
        return StringUtils.camelToUnderline(getAttribute());
    }

    /**
     * 获取查询条件值
     *
     * @return
     */
    T getValue();
}
