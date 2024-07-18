package com.modern.orm.mp.criteria.condition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.orm.mp.criteria.AbstractCriteriaTranslate;
import com.modernframework.base.criteria.CriteriaParamItem;
import com.modernframework.base.criteria.type.ConditionType;
import com.modernframework.core.utils.CollectionUtils;
import net.sf.json.JSONArray;

import java.util.Collection;


/**
 * 集合查询条件
 */
public class In extends AbstractCriteriaTranslate {

    @Override
    protected boolean subValidate(CriteriaParamItem criteria) {
        try {
            return CollectionUtils.isNotEmpty((Collection<?>)criteria.getValue());
        } catch (Exception ignore) {
            return false;
        }

    }

    /**
     * 根据查询条件转成目标查询条件
     *
     * @param query          目标查询条件
     * @param underLineField 字段下划线表示
     * @param criteriaVal    查询条件参数的值
     */
    @Override
    protected void doTranslate(QueryWrapper<?> query, String underLineField, Object criteriaVal) {
        if(criteriaVal instanceof Collection<?>) {
            query.in(underLineField, (Collection<?>)criteriaVal);    
        } else {
            throw new IllegalArgumentException(String.format("查询参数构造错误，使用IN的值应该是Collection类型"));
        }
    }

    /**
     * 条件类型
     *
     * @return ConditionType
     */
    @Override
    public ConditionType getConditionType() {
        return ConditionType.IN;
    }
}
