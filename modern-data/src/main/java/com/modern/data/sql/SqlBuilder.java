package com.modern.data.sql;

import com.modern.data.ColumnWrapper;
import com.modern.data.constant.DialectName;
import com.modern.data.constant.Join;
import com.modern.data.constant.Link;
import com.modernframework.core.constant.StrConstant;
import com.modernframework.core.utils.ArrayUtils;
import com.modernframework.core.utils.CollectionUtils;
import com.modernframework.core.utils.StringUtils;

import javax.management.Query;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SQL构建器
 * 首先拼接SQL语句，值使用 ? 占位<br>
 * 调用getParamValues()方法获得占位符对应的值
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class SqlBuilder {

    /**
     * 创建SQL构建器
     *
     * @return SQL构建器
     */
    public static SqlBuilder create() {
        return new SqlBuilder();
    }

    /**
     * 创建SQL构建器
     *
     * @param wrapper 包装器
     * @return SQL构建器
     */
    public static SqlBuilder create(ColumnWrapper wrapper) {
        return new SqlBuilder(wrapper);
    }

    final private StringBuilder sql = new StringBuilder();
    /**
     * 字段列表（仅用于插入和更新）
     */
    final private List<String> columns = new ArrayList<>();
    /**
     * 占位符对应的值列表
     */
    final private List<Object> paramValues = new ArrayList<>();
    /**
     * 包装器
     */
    private ColumnWrapper wrapper;

    // --------------------------------------------------------------- Constructor start
    public SqlBuilder() {
    }

    public SqlBuilder(ColumnWrapper wrapper) {
        this.wrapper = wrapper;
    }
    // --------------------------------------------------------------- Constructor end

    // --------------------------------------------------------------- Builder start

    /**
     * 插入，使用默认的ANSI方言
     *
     * @return 自己
     */
    public SqlBuilder insert(String tableName, Map<String, Object> dataMap) {
        return this.insert(tableName, dataMap, DialectName.ANSI);
    }

    /**
     * 插入<br>
     * 插入会忽略空的字段名及其对应值，但是对于有字段名对应值为{@code null}的情况不忽略
     *
     * @param tableName   表名
     * @param dataMap     数据
     * @param dialectName 方言名
     * @return 自己
     */
    public SqlBuilder insert(String tableName, Map<String, Object> dataMap, DialectName dialectName) {
        // 验证
        validateTableData(tableName, dataMap);

        if (null != wrapper) {
            // 包装表名
            tableName = wrapper.wrap(tableName);
        }

        final boolean isOracle = DialectName.ORACLE.equals(dialectName);// 对Oracle的特殊处理
        final StringBuilder columnsPart = new StringBuilder();
        final StringBuilder placeHolder = new StringBuilder();

        boolean isFirst = true;
        String field;
        Object value;
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            field = entry.getKey();
            value = entry.getValue();
            if (StringUtils.isNotBlank(field) /* && null != value */) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    // 非第一个参数，追加逗号
                    columnsPart.append(", ");
                    placeHolder.append(", ");
                }

                this.columns.add(field);
                columnsPart.append((null != wrapper) ? wrapper.wrap(field) : field);
                if (isOracle && value instanceof String && StringUtils.endWithIgnoreCase((String) value, ".nextval")) {
                    // Oracle的特殊自增键，通过字段名.nextval获得下一个值
                    placeHolder.append(value);
                } else {
                    placeHolder.append("?");
                    this.paramValues.add(value);
                }
            }
        }
        sql.append("INSERT INTO ")//
                .append(tableName).append(" (").append(columnsPart).append(") VALUES (")//
                .append(placeHolder).append(")");

        return this;
    }

    /**
     * 删除
     *
     * @param tableName 表名
     * @return 自己
     */
    public SqlBuilder delete(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            throw new DbException("Table name is blank !");
        }

        if (null != wrapper) {
            // 包装表名
            tableName = wrapper.wrap(tableName);
        }

        sql.append("DELETE FROM ").append(tableName);

        return this;
    }

    /**
     * 更新
     *
     * @param tableName   表名
     * @param dataMap     数据
     * @return 自己
     */
    public SqlBuilder update(String tableName, Map<String, Object> dataMap) {
        // 验证
        validateTableData(tableName, dataMap);

        if (null != wrapper) {
            // 包装表名
            tableName = wrapper.wrap(tableName);
        }

        sql.append("UPDATE ").append(tableName).append(" SET ");
        String field;
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            field = entry.getKey();
            if (StringUtils.isNotBlank(field)) {
                if (paramValues.size() > 0) {
                    sql.append(", ");
                }
                this.columns.add(field);
                sql.append((null != wrapper) ? wrapper.wrap(field) : field).append(" = ? ");
                this.paramValues.add(entry.getValue());// 更新不对空做处理，因为存在清空字段的情况
            }
        }

        return this;
    }

    /**
     * 查询
     *
     * @param isDistinct 是否添加DISTINCT关键字（查询唯一结果）
     * @param columns    查询的字段
     * @return 自己
     */
    public SqlBuilder select(boolean isDistinct, String... columns) {
        return select(isDistinct, Arrays.asList(columns));
    }

    /**
     * 查询
     *
     * @param isDistinct 是否添加DISTINCT关键字（查询唯一结果）
     * @param columns    查询的字段
     * @return 自己
     */
    public SqlBuilder select(boolean isDistinct, Collection<String> columns) {
        sql.append("SELECT ");
        if (isDistinct) {
            sql.append("DISTINCT ");
        }

        if (CollectionUtils.isEmpty(columns)) {
            sql.append("*");
        } else {
            if (null != wrapper) {
                // 包装字段名
                columns = wrapper.wrap(columns);
            }
            sql.append(columns.stream().collect(Collectors.joining(StrConstant.COMMA)));
        }

        return this;
    }

    /**
     * 查询（非Distinct）
     *
     * @param columns 查询的字段
     * @return 自己
     */
    public SqlBuilder select(String... columns) {
        return select(false, columns);
    }

    /**
     * 查询（非Distinct）
     *
     * @param columns 查询的字段
     * @return 自己
     */
    public SqlBuilder select(Collection<String> columns) {
        return select(false, columns);
    }

    /**
     * 添加 from语句
     *
     * @param tableNames 表名列表（多个表名用于多表查询）
     * @return 自己
     */
    public SqlBuilder from(String... tableNames) {
        if (ArrayUtils.isEmpty(tableNames) || StringUtils.isAnyBlank(tableNames)) {
            throw new DbException("Table name is blank in table names !");
        }

        if (null != wrapper) {
            // 包装表名
            tableNames = wrapper.wrap(tableNames);
        }

        sql.append(" FROM ").append(Arrays.stream(tableNames).collect(Collectors.joining(StrConstant.COMMA)));

        return this;
    }

    /**
     * 添加Where语句<br>
     * 只支持单一的逻辑运算符（例如多个条件之间）
     *
     * @param conditionSegment 满足内嵌条件的条件链结构
     * @return 自己
     */
    public SqlBuilder where(ConditionSegment conditionSegment) {
        if (conditionSegment != null) {
            where(buildCondition(conditionSegment));
        }
        return this;
    }


    /**
     * 添加Where语句<br>
     *
     * @param whereString WHERE语句之后跟的条件语句字符串
     * @return 自己
     */
    public SqlBuilder where(String whereString) {
        if (StringUtils.isNotBlank(whereString)) {
            sql.append(" WHERE ").append(whereString);
        }
        return this;
    }

    /**
     * 包裹条件<br>
     *
     * @param condition 包裹的条件
     * @return 自己
     */
    public SqlBuilder packCondition(String condition) {
        if (StringUtils.isNotBlank(condition)) {
            sql.append(" ( ").append(condition).append(" ) ");
        }
        return this;
    }

    /**
     * 多值选择
     *
     * @param <T>    值类型
     * @param field  字段名
     * @param values 值列表
     * @return 自身
     */
    @SuppressWarnings("unchecked")
    public <T> SqlBuilder in(String field, T... values) {
        sql.append(wrapper.wrap(field)).append(" IN ").append("(")
                .append(Arrays.stream(values).map(String::valueOf).collect(Collectors.joining(StrConstant.COMMA)))
                .append(")");
        return this;
    }

    /**
     * 分组
     *
     * @param columns 字段
     * @return 自己
     */
    public SqlBuilder groupBy(String... columns) {
        if (ArrayUtils.isNotEmpty(columns)) {
            if (null != wrapper) {
                // 包装字段名
                columns = wrapper.wrap(columns);
            }

            sql.append(" GROUP BY ").append(Arrays.stream(columns).collect(Collectors.joining(StrConstant.COMMA)));
        }

        return this;
    }

    /**
     * 添加Having语句
     *
     * @param logicalOperator 逻辑运算符
     * @param conditions      条件
     * @return 自己
     */
    public SqlBuilder having(Link logicalOperator, Condition... conditions) {
        if (ArrayUtils.isNotEmpty(conditions)) {
            if (null != wrapper) {
                // 包装字段名
                conditions = wrapper.wrap(conditions);
            }
            having(buildCondition(logicalOperator, conditions));
        }

        return this;
    }

    /**
     * 添加Having语句
     *
     * @param having 条件语句
     * @return 自己
     */
    public SqlBuilder having(String having) {
        if (StringUtils.isNotBlank(having)) {
            sql.append(" HAVING ").append(having);
        }
        return this;
    }

    /**
     * 排序
     *
     * @param orders 排序对象
     * @return 自己
     */
    public SqlBuilder orderBy(Collection<OrderBy> orders) {
        if (CollectionUtils.isEmpty(orders)) {
            return this;
        }

        sql.append(" ORDER BY ");
        String field = null;
        boolean isFirst = true;
        for (OrderBy order : orders) {
            if (null != wrapper) {
                // 包装字段名
                field = wrapper.wrap(order.getColumnName());
            }
            if (StringUtils.isBlank(field)) {
                continue;
            }

            // 只有在非第一项前添加逗号
            if (isFirst) {
                isFirst = false;
            } else {
                sql.append(StrConstant.COMMA);
            }
            sql.append(field);
            final String direction = order.isAsc() ? "ASC" : "DESC";
            sql.append(StrConstant.SPACE).append(direction);
        }
        return this;
    }

    /**
     * 排序
     *
     * @param orders 排序对象
     * @return 自己
     */
    public SqlBuilder orderBy(OrderBy... orders) {
        return orderBy(Arrays.asList(orders));
    }

    /**
     * 多表关联
     *
     * @param tableName 被关联的表名
     * @param join      内联方式
     * @return 自己
     */
    public SqlBuilder join(String tableName, Join join) {
        if (StringUtils.isBlank(tableName)) {
            throw new DbException("Table name is blank !");
        }

        if (null != join) {
            sql.append(StrConstant.SPACE).append(join).append(" JOIN ");
            if (null != wrapper) {
                // 包装表名
                tableName = wrapper.wrap(tableName);
            }
            sql.append(tableName);
        }
        return this;
    }

    /**
     * 配合JOIN的 ON语句，多表关联的条件语句<br>
     * 只支持单一的逻辑运算符（例如多个条件之间）
     *
     * @param logicalOperator 逻辑运算符
     * @param conditions      条件
     * @return 自己
     */
    public SqlBuilder on(Link logicalOperator, Condition... conditions) {
        if (ArrayUtils.isNotEmpty(conditions)) {
            if (null != wrapper) {
                // 包装字段名
                conditions = wrapper.wrap(conditions);
            }
            on(buildCondition(logicalOperator, conditions));
        }

        return this;
    }

    /**
     * 配合JOIN的 ON语句，多表关联的条件语句<br>
     * 只支持单一的逻辑运算符（例如多个条件之间）
     *
     * @param on 条件
     * @return 自己
     */
    public SqlBuilder on(String on) {
        if (StringUtils.isNotBlank(on)) {
            this.sql.append(" ON ").append(on);
        }
        return this;
    }

    /**
     * 在SQL的开头补充SQL片段
     *
     * @param sqlFragment SQL片段
     * @return this
     */
    public SqlBuilder insertPreFragment(Object sqlFragment) {
        if (null != sqlFragment) {
            this.sql.insert(0, sqlFragment);
        }
        return this;
    }

    /**
     * 追加SQL其它部分片段
     *
     * @param sqlFragment SQL其它部分片段
     * @return this
     */
    public SqlBuilder append(Object sqlFragment) {
        if (null != sqlFragment) {
            this.sql.append(sqlFragment);
        }
        return this;
    }

    /**
     * 手动增加参数，调用此方法前需确认SQL中有对应占位符，主要用于自定义SQL片段中有占位符的情况，例如：
     *
     * <pre>
     *     SqlBuilder builder = SqlBuilder.of("select * from user where id=?");
     *     builder.append(" and name=?")
     *     builder.addParams(1, "looly");
     * </pre>
     *
     * @param params 参数列表
     * @return this
     * @since 5.5.3
     */
    public SqlBuilder addParams(Object... params) {
        if (ArrayUtils.isNotEmpty(params)) {
            Collections.addAll(this.paramValues, params);
        }
        return this;
    }

    /**
     * 构建查询SQL
     *
     * @param query {@link Query}
     * @return this
     */
    public SqlBuilder query(Query query) {
        return this.select(query.getColumns()).from(query.getTableNames()).where(query.getCondition());
    }
    // --------------------------------------------------------------- Builder end

    /**
     * 获得插入或更新的数据库字段列表
     *
     * @return 插入或更新的数据库字段列表
     */
    public String[] getFieldArray() {
        return this.columns.toArray(new String[this.columns.size()]);
    }

    /**
     * 获得插入或更新的数据库字段列表
     *
     * @return 插入或更新的数据库字段列表
     */
    public List<String> getFields() {
        return this.columns;
    }

    /**
     * 获得占位符对应的值列表<br>
     *
     * @return 占位符对应的值列表
     */
    public List<Object> getParamValues() {
        return this.paramValues;
    }

    /**
     * 获得占位符对应的值列表<br>
     *
     * @return 占位符对应的值列表
     */
    public Object[] getParamValueArray() {
        return this.paramValues.toArray(new Object[this.paramValues.size()]);
    }

    /**
     * 构建，默认打印SQL日志
     *
     * @return 构建好的SQL语句
     */
//    @Override
    public String build() {
        return this.sql.toString();
    }

    @Override
    public String toString() {
        return this.build();
    }

    // --------------------------------------------------------------- private method start

    /**
     * 构建组合条件<br>
     * 例如：name = ? AND type IN (?, ?) AND other LIKE ?
     *
     * @param logicalOperator 逻辑运算符
     * @param conditions      条件对象
     * @return 构建后的SQL语句条件部分
     */
    private String buildCondition(Link logicalOperator, Condition... conditions) {
        if (ArrayUtils.isEmpty(conditions)) {
            return StringUtils.EMPTY;
        }
        if (null == logicalOperator) {
            logicalOperator = Link.AND;
        }

        final StringBuilder conditionStrBuilder = this.sql;
        boolean isFirst = true;
        for (Condition condition : conditions) {
            // 添加逻辑运算符
            if (isFirst) {
                isFirst = false;
            } else {
                // " AND " 或者 " OR "
                conditionStrBuilder.append(StrConstant.SPACE).append(logicalOperator).append(StrConstant.SPACE);

            }

            // 构建条件部分："name = ?"、"name IN (?,?,?)"、"name BETWEEN ？AND ？"、"name LIKE ?"
            conditionStrBuilder.append(condition.toString(this.paramValues));
        }

        return conditionStrBuilder.toString();
    }

    private String buildCondition(ConditionSegment head) {
        if (head == null) {
            return StringUtils.EMPTY;
        }
        final StringBuilder conditionStrBuilder = new StringBuilder();
        boolean isFirst = true;
        ConditionSegment current = head;
        while (current != null) {
            if (isFirst) {
                isFirst = false;
            } else {
                conditionStrBuilder.append(StrConstant.SPACE).append(current.getLogicalOperator()).append(StrConstant.SPACE);
            }

            if (current.getChild() != null) {
                // 如果存在嵌套条件，则递归调用
                conditionStrBuilder.append(" ( ")
                        .append(buildCondition(current.getChild()))
                        .append(" ) ");
            } else {
                conditionStrBuilder.append(current.tryWrappedCondition(wrapper).toString(this.paramValues));
            }

            // 下一个
            current = current.getNext();
        }
        return conditionStrBuilder.toString();
    }


    /**
     * 添加组合条件<br>
     * 例如：( name = ? AND type IN (?, ?) AND other LIKE ? )
     *
     * @param logicalOperator 逻辑运算符
     * @param conditions      条件对象
     * @return 构建后的SQL语句条件部分
     */
    private String appendCondition(Link logicalOperator, Condition... conditions) {
        if (ArrayUtils.isEmpty(conditions)) {
            return StringUtils.EMPTY;
        }
        if (null == logicalOperator) {
            logicalOperator = Link.AND;
        }

        final StringBuilder conditionStrBuilder = new StringBuilder();
        boolean isFirst = true;
        for (Condition condition : conditions) {
            // 添加逻辑运算符
            if (isFirst) {
                isFirst = false;
            } else {
                // " AND " 或者 " OR "
                conditionStrBuilder.append(StrConstant.SPACE).append(logicalOperator)
                        .append(StrConstant.SPACE).append("(").append(StrConstant.SPACE);
            }

            // 构建条件部分："name = ?"、"name IN (?,?,?)"、"name BETWEEN ？AND ？"、"name LIKE ?"
            conditionStrBuilder.append(condition.toString(this.paramValues));
        }
        conditionStrBuilder.append(")");
        return conditionStrBuilder.toString();
    }

    /**
     * 验证实体类对象的有效性
     *
     * @throws DbException SQL异常包装，获取元数据信息失败
     */
    public static void validateTableData(String tableName, Map<String, Object> dataMap) throws DbException {
        if (StringUtils.isBlank(tableName)) {
            throw new DbException("table name is null !");
        }
        if (CollectionUtils.isEmpty(dataMap)) {
            throw new DbException("No filed and value in this entity !");
        }
    }

    // --------------------------------------------------------------- private method end
}
