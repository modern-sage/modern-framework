package com.modern.orm.mp.criteria;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modernframework.base.criteria.CriteriaParam;
import com.modernframework.base.criteria.CriteriaParamItem;
import com.modernframework.base.criteria.Relationship;
import com.modernframework.base.criteria.between.BetweenParam;
import com.modernframework.core.func.Predicates;
import com.modernframework.core.utils.StringUtils;

import java.util.function.Predicate;

import static com.modernframework.base.criteria.CriteriaExpress.R_OR;


/**
 * 查询条件转换接口
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 */
public abstract class AbstractCriteriaTranslate implements MpCriteriaTranslate {

    /**
     * 有效的字段判断
     */
    protected Predicate<? super CriteriaParam<?>> funcEffectiveAttribute = x -> StringUtils.isNotBlank(x.getAttribute());

    /**
     * 有效的值判断
     */
    protected Predicate<? super CriteriaParam<?>> funcEffectiveVal = x -> x.getValue() != null;

    /**
     * 有效的键值判断
     */
    protected Predicate<? super CriteriaParam<?>> funcEffectiveCriteriaParam = Predicates.and(funcEffectiveAttribute, funcEffectiveVal);

    /**
     * 有效的between值判断
     */
    protected Predicate<BetweenParam> funcEffectiveBetween = x -> x.getHiValue() != null && x.getLoValue() != null;

    @Override
    public QueryWrapper<?> translate(QueryWrapper<?> query, CriteriaParamItem criteria, Relationship relationship) {
        // 验证参数是否合法
        if (funcEffectiveCriteriaParam.test(criteria) && subValidate(criteria)) {
            // 字段的下划线表示
            String underLineField = StringUtils.camelToUnderline(criteria.getAttribute());
            // 具体的拼接逻辑，子类实现
            doTranslate(R_OR.equals(relationship) ? query.or() : query, underLineField, criteria.getValue());
        }
        return query;
    }

    /**
     * 其他的校验交给子类来做
     *
     * @return boolean
     */
    protected boolean subValidate(CriteriaParamItem criteria) {
        return true;
    }

    /**
     * 根据查询条件转成目标查询条件
     *
     * @param query          目标查询条件
     * @param underLineField 字段下划线表示
     * @param criteriaVal    查询条件参数的值
     */
    protected abstract void doTranslate(QueryWrapper<?> query, String underLineField, Object criteriaVal);
}
