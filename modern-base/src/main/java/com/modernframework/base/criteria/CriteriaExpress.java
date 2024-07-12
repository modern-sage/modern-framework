package com.modernframework.base.criteria;


import static com.modernframework.base.BaseConstant.*;

/**
 * 查询标准表达式接口
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface CriteriaExpress {

    /**
     * and
     */
    Relationship R_AND = new Relationship(RELATIONSHIP_AND);

    /**
     * or
     */
    Relationship R_OR = new Relationship(RELATIONSHIP_OR);

    /**
     * 内嵌开始
     */
    EmbeddedLink EMBEDDED_START = new EmbeddedLink(RELATIONSHIP_EMBEDDED_START);

    /**
     * 内嵌结束
     */
    EmbeddedLink EMBEDDED_END = new EmbeddedLink(RELATIONSHIP_EMBEDDED_END);

}


