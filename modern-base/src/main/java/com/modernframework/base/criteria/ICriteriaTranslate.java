package com.modernframework.base.criteria;


/**
 * 查询条件转换接口
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface ICriteriaTranslate<T> {

    /**
     * 根据查询条件转成目标查询条件
     *
     * @param query        目标查询条件
     * @param criteria     查询条件参数
     * @param relationship 连接符号
     * @return T
     */
    T translate(T query, CriteriaParamItem criteria, Relationship relationship);
}
