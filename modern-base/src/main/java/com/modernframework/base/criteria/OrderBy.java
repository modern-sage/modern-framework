package com.modernframework.base.criteria;

import com.modernframework.core.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排序字段
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBy {

    /**
     * 排序字段名
     */
    private String attribute;
    /**
     * 排序方式
     */
    private Integer asc = Constants.ASC;

    /**
     * 判断当前条件是否正序
     *
     * @return boolean
     */
    public boolean isAsc() {
        return getAsc() > Constants.DESC;
    }

    /**
     * 获取字段名（属性名是驼峰，而字段名是下划线形式）
     *
     * @return String
     */
    public String getColumnName() {
        return StringUtils.camelToUnderline(attribute);
    }

}
