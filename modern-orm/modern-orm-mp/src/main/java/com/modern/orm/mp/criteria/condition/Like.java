package com.modern.orm.mp.criteria.condition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.orm.mp.criteria.AbstractCriteriaTranslate;
import com.modernframework.base.criteria.type.ConditionType;

/**
 * 模糊查询条件
 */
public class Like extends AbstractCriteriaTranslate {

    /**
     * 根据查询条件转成目标查询条件
     *
     * @param query          目标查询条件
     * @param underLineField 字段下划线表示
     * @param criteriaVal    查询条件参数的值
     */
    @Override
    protected void doTranslate(QueryWrapper<?> query, String underLineField, Object criteriaVal) {
        query.like(underLineField, criteriaVal);
    }

    /**
     * 条件类型
     *
     * @return ConditionType
     */
    @Override
    public ConditionType getConditionType() {
        return ConditionType.LIKE;
    }
}
