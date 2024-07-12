package com.modernframework.base.criteria;

import com.modernframework.base.criteria.type.ConditionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

import static com.modernframework.base.BaseConstant.RELATIONSHIP_AND;


/**
 * 查询参数
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiCriteriaItem implements Serializable {

    /**
     * 连接符号
     */
    private String link = RELATIONSHIP_AND;

    /**
     * 查询属性名称
     */
    private String attr;
    /**
     * 查询属性值
     */
    private Object value;
    /**
     * 查询条件
     */
    private ConditionType condition;

    private List<ApiCriteriaItem> embedded;

}
