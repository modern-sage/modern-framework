package com.modern.orm.mp.config.criteria;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modernframework.base.criteria.ICriteriaTranslate;
import com.modernframework.base.criteria.type.ConditionType;

/**
 * 查询条件转换接口
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 */
public interface MpCriteriaTranslate extends ICriteriaTranslate<QueryWrapper<?>> {

    /**
     * 条件类型
     *
     * @return ConditionType
     */
    ConditionType getConditionType();

}
