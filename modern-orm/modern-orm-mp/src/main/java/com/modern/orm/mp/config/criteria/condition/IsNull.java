package com.modern.orm.mp.config.criteria.condition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.base.criteria.type.ConditionType;
import com.modern.data.orm.mp.criteria.AbstractCriteriaTranslate;

/**
 * is null 条件
 */
public class IsNull extends AbstractCriteriaTranslate {

    /**
     * 根据查询条件转成目标查询条件
     *
     * @param query          目标查询条件
     * @param underLineField 字段下划线表示
     * @param criteriaVal    查询条件参数的值
     */
    @Override
    protected void doTranslate(QueryWrapper<?> query, String underLineField, Object criteriaVal) {
        query.isNull(underLineField);
    }

    /**
     * 条件类型
     *
     * @return ConditionType
     */
    @Override
    public ConditionType getConditionType() {
        return ConditionType.IS_NULL;
    }
}
