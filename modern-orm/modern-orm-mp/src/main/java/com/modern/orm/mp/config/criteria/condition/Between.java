package com.modern.orm.mp.config.criteria.condition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.base.criteria.CriteriaParamItem;
import com.modern.base.criteria.between.BetweenParam;
import com.modern.base.criteria.type.ConditionType;
import com.modern.data.orm.mp.criteria.AbstractCriteriaTranslate;
import net.sf.json.JSONObject;

/**
 * 区间查询条件
 */
public class Between extends AbstractCriteriaTranslate {

    /**
     * 条件类型
     *
     * @return ConditionType
     */
    @Override
    public ConditionType getConditionType() {
        return ConditionType.BETWEEN;
    }

    @Override
    protected boolean subValidate(CriteriaParamItem criteria) {
        JSONObject criteriaJson = JSONObject.fromObject(criteria.getValue());
        BetweenParam betweenParam = (BetweenParam) JSONObject.toBean(criteriaJson, BetweenParam.class);
        return funcEffectiveBetween.test(betweenParam);
    }

    @Override
    protected void doTranslate(QueryWrapper<?> query, String underLineField, Object criteriaVal) {
        JSONObject criteriaJson = JSONObject.fromObject(criteriaVal);
        BetweenParam betweenParam = (BetweenParam) JSONObject.toBean(criteriaJson, BetweenParam.class);
        query.between(underLineField, betweenParam.getLoValue(), betweenParam.getHiValue());
    }
}
