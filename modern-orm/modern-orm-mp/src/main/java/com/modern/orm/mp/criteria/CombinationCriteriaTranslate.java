package com.modern.orm.mp.criteria;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.orm.mp.criteria.condition.*;
import com.modernframework.base.criteria.CriteriaExpress;
import com.modernframework.base.criteria.CriteriaParamItem;
import com.modernframework.base.criteria.ICriteriaTranslate;
import com.modernframework.base.criteria.Relationship;
import com.modernframework.base.criteria.type.ConditionType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询条件转换组合器<br/>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class CombinationCriteriaTranslate implements ICriteriaTranslate<QueryWrapper<?>> {

    private static final Map<ConditionType, MpCriteriaTranslate> CRITERIA_MAP;

    static {
        Map<ConditionType, MpCriteriaTranslate> map = new HashMap<>();
        Arrays.asList(
                new Between(),
                new Equal(),
                new Greaterthan(),
                new GreaterThanOrEqual(),
                new In(),
                new IsNotNull(),
                new IsNull(),
                new Lessthan(),
                new LessThanOrEqual(),
                new Like(),
                new NotEqual(),
                new PrimaryKey()
        ).forEach(c -> map.put(c.getConditionType(), c));
        CRITERIA_MAP = Collections.unmodifiableMap(map);
    }


    /**
     * 根据查询条件转成目标查询条件
     *
     * @param query        目标查询条件
     * @param criteria     查询条件参数
     * @param relationship 连接符号，默认 and
     * @return Q
     */
    @Override
    public QueryWrapper<?> translate(QueryWrapper<?> query, CriteriaParamItem criteria, Relationship relationship) {
        relationship = relationship == null ? CriteriaExpress.R_AND : relationship;
        return CRITERIA_MAP.get(criteria.getConditionType()).translate(query, criteria, relationship);
    }


}
