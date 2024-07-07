package com.modern.data;


import com.modernframework.core.utils.ArrayUtils;
import com.modernframework.core.utils.CollectionUtils;
import com.modernframework.core.utils.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.modernframework.core.constant.StrConstant.C_DOT;
import static com.modernframework.core.constant.StrConstant.DOT;


/**
 * 字段包装器<br/>
 * 主要用于字段名的包装（在字段名的前后加字符，例如反引号来避免与数据库的关键字冲突）
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class ColumnWrapper {

    /**
     * 前置包装符号
     */
    private Character preWrapQuote;
    /**
     * 后置包装符号
     */
    private Character sufWrapQuote;

    public ColumnWrapper() {
    }

    /**
     * 构造
     *
     * @param wrapQuote 单包装字符
     */
    public ColumnWrapper(Character wrapQuote) {
        this.preWrapQuote = wrapQuote;
        this.sufWrapQuote = wrapQuote;
    }

    /**
     * 包装符号
     *
     * @param preWrapQuote 前置包装符号
     * @param sufWrapQuote 后置包装符号
     */
    public ColumnWrapper(Character preWrapQuote, Character sufWrapQuote) {
        this.preWrapQuote = preWrapQuote;
        this.sufWrapQuote = sufWrapQuote;
    }

    //--------------------------------------------------------------- Getters and Setters start

    /**
     * @return 前置包装符号
     */
    public char getPreWrapQuote() {
        return preWrapQuote;
    }

    /**
     * 设置前置包装的符号
     *
     * @param preWrapQuote 前置包装符号
     */
    public void setPreWrapQuote(Character preWrapQuote) {
        this.preWrapQuote = preWrapQuote;
    }

    /**
     * @return 后置包装符号
     */
    public char getSufWrapQuote() {
        return sufWrapQuote;
    }

    /**
     * 设置后置包装的符号
     *
     * @param sufWrapQuote 后置包装符号
     */
    public void setSufWrapQuote(Character sufWrapQuote) {
        this.sufWrapQuote = sufWrapQuote;
    }
    //--------------------------------------------------------------- Getters and Setters end

    /**
     * 包装字段名<br>
     * 有时字段与SQL的某些关键字冲突，导致SQL出错，因此需要将字段名用单引号或者反引号包装起来，避免冲突
     *
     * @param column 字段名
     * @return 包装后的字段名
     */
    public String wrap(String column) {
        if (preWrapQuote == null || sufWrapQuote == null || StringUtils.isBlank(column)) {
            return column;
        }

        //如果已经包含包装的引号，返回原字符
        if(column.startsWith(preWrapQuote + "") && column.endsWith(sufWrapQuote + "")) {
            return column;
        }

        //如果字段中包含通配符或者括号（字段通配符或者函数），不做包装
        if (StringUtils.containsAnyIgnoreCase(column, "*", "(", " ", " as ")) {
            return column;
        }

        //对于Oracle这类数据库，表名中包含用户名需要单独拆分包装
        if (column.contains(DOT)) {
            String[] columns = StringUtils.split(column, C_DOT);
            return Arrays.stream(columns).map(x -> String.format("%s%s%s", preWrapQuote, x, sufWrapQuote))
                    .collect(Collectors.joining(DOT));
        }

        return String.format("%s%s%s", preWrapQuote, column, sufWrapQuote);
    }

    /**
     * 包装字段名<br>
     * 有时字段与SQL的某些关键字冲突，导致SQL出错，因此需要将字段名用单引号或者反引号包装起来，避免冲突
     *
     * @param columns 字段名
     * @return 包装后的字段名
     */
    public String[] wrap(String... columns) {
        if (ArrayUtils.isEmpty(columns)) {
            return columns;
        }

        String[] wrappedFields = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            wrappedFields[i] = wrap(columns[i]);
        }

        return wrappedFields;
    }

    /**
     * 包装字段名<br>
     * 有时字段与SQL的某些关键字冲突，导致SQL出错，因此需要将字段名用单引号或者反引号包装起来，避免冲突
     *
     * @param columns 字段名
     * @return 包装后的字段名
     */
    public Collection<String> wrap(Collection<String> columns) {
        if (CollectionUtils.isEmpty(columns)) {
            return columns;
        }

        return Arrays.asList(wrap(columns.toArray(new String[columns.size()])));
    }

    /**
     * 包装字段名<br>
     * 有时字段与SQL的某些关键字冲突，导致SQL出错，因此需要将字段名用单引号或者反引号包装起来，避免冲突
     *
     * @param conditions 被包装的实体
     * @return 包装后的字段名
     */
    public Condition[] wrap(Condition... conditions) {
        final Condition[] clonedConditions = new Condition[conditions.length];
        if (ArrayUtils.isNotEmpty(conditions)) {
            Condition clonedCondition;
            for (int i = 0; i < conditions.length; i++) {
                clonedCondition = conditions[i].clone();
                clonedCondition.setField(wrap(clonedCondition.getField()));
                clonedConditions[i] = clonedCondition;
            }
        }

        return clonedConditions;
    }
}
