package com.modern.orm.mp.criteria.condition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.orm.mp.criteria.AbstractCriteriaTranslate;
import com.modernframework.base.criteria.type.ConditionType;


/**
 * 相等查询条件
 */
public class Eq extends AbstractCriteriaTranslate {

    /**
     * 条件类型
     *
     * @return ConditionType
     */
    @Override
    public ConditionType getConditionType() {
        return ConditionType.EQ;
    }

//    /**
//     * 根据查询条件转成目标查询条件
//     *
//     * @param query        目标查询条件
//     * @param criteria     查询条件参数
//     * @param relationship 关联关系
//     * @return Q
//     */
//    @Override
//    public QueryWrapper<?> translate(QueryWrapper<?> query, CriteriaParamItem criteria, Relationship relationship) {
//        boolean condition = funcEffectiveCriteriaParam.test(criteria);
//        String underLineField = StringUtils.camelToUnderline(criteria.getAttribute());
//
//        if (R_AND.equals(relationship)) {
//            query.eq(condition, underLineField, criteria.getValue());
//        } else if (R_OR.equals(relationship)) {
//            query.or().eq(condition, underLineField, criteria.getValue());
//        }
//        return query;
//    }

    /**
     * 根据查询条件转成目标查询条件
     *
     * @param query          目标查询条件
     * @param underLineField 字段下划线表示
     * @param criteriaVal    查询条件参数的值
     * @return T
     */
    @Override
    protected void doTranslate(QueryWrapper<?> query, String underLineField, Object criteriaVal) {
        query.eq(underLineField, criteriaVal);
    }
}
