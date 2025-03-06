package com.modernframework.base.criteria;

import com.modernframework.base.criteria.type.ConditionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 查询参数
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CriteriaParamItem implements CriteriaParam<Object>, CriteriaExpress {

    /**
     * 查询属性名称
     */
    protected String attribute;
    /**
     * 查询属性值
     */
    protected Object value;
    /**
     * 查询条件
     */
    protected String conditionType;

    @Override
    public String toString() {
        return attribute + " " + conditionType + " " + value;
    }
}
