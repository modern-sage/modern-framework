package com.modern.data.dialect;


import com.modern.data.ColumnWrapper;
import com.modern.data.Dialect;
import com.modern.data.SchemaProps;
import com.modern.data.constant.DialectName;
import com.modern.data.sql.SqlBuilder;
import com.modern.data.utils.StatementUtil;
import com.modernframework.core.lang.Asserts;
import com.modernframework.core.utils.ArrayUtils;
import com.modernframework.core.utils.StringUtils;
import org.w3c.dom.Attr;

import javax.management.Query;
import java.io.Serial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * ANSI SQL 方言
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 0.2.0
 */
public class AbstractDialect implements Dialect {
    @Serial
    private static final long serialVersionUID = 7591974309843822976L;

    protected ColumnWrapper wrapper = new ColumnWrapper();

    @Override
    public ColumnWrapper getWrapper() {
        return this.wrapper;
    }

    @Override
    public void setWrapper(ColumnWrapper wrapper) {
        this.wrapper = wrapper;
    }

    /**
     * 构建用于创建表的PreparedStatement
     *
     * @param conn       数据库连接对象
     * @param tableName  表名
     * @param attributes 属性
     * @return PreparedStatement
     */
    @Override
    public PreparedStatement psForCreate(Connection conn, String tableName, SchemaProps schemaProps, Collection<? extends Attr> attributes) {
        Asserts.notBlank(tableName, "表名不能为空");
        String sqlFormat = "CREATE TABLE %s (\n %s ) COMMENT '%s';";
        String sql = String.format(sqlFormat, tableName,
                genCreateColumnSql(schemaProps.isVersionSupport(), schemaProps.isDeleteLogical(), attributes), StringUtils.nullToEmpty(schemaProps.getComment()));
        return StatementUtil.prepareStatement(conn, sql);
    }

    /**
     * 构建用于表增加字段的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @param attr      属性
     * @return PreparedStatement
     */
    @Override
    public PreparedStatement psForAddColumn(Connection conn, String tableName, Attr attr) {
        ValidateUtil.assertTableNameNotNull(tableName);
        ValidateUtil.assertEntitySchemaAttrs(Collections.singleton(attr));

        String sql;
        if (!attr.findAttrType().hasLength) {
            sql = String.format("ALTER TABLE %s ADD COLUMN %s %s NULL COMMENT '%s' AFTER id;", tableName,
                    attr.getColumnName(), attr.findAttrType().sqlTypeName,
                    attr.getName());
        } else {
            sql = String.format("ALTER TABLE %s ADD COLUMN %s %s(%s) null COMMENT '%s' AFTER id;", tableName,
                    attr.getColumnName(), attr.findAttrType().sqlTypeName,
                    attr.findAttrType().getLength(),
                    attr.getName());
        }
        return StatementUtil.prepareStatement(conn, sql);
    }

    /**
     * 生成建表语句，如下：
     * <pre>
     *      id          bigint auto_increment comment '主键' primary key,
     *     dict_key    varchar(255) not null default '' comment '字典键',
     *     dict_value  varchar(255) not null default '' comment '字典值',
     * </pre>
     */
    protected String genCreateColumnSql(boolean versionSupport, boolean deleteLogical, Collection<? extends Attr> attributeList) {
        Asserts.notEmpty(attributeList, "实体属性不能为空");
        StringBuilder sb = new StringBuilder();
        // 创建ID
        sb.append(genCreateColumnIdSql());
        // 创建业务列
        attributeList.forEach(attr -> sb.append(genCreateColumnSql(attr)));
        // 创建公共列
        sb.append(genCreateCommonColumnSql(versionSupport, deleteLogical));
        return sb.toString();
    }

    /**
     * 建表语句固定字段ID创建 sql，如下：
     * <pre>
     *      id          bigint auto_increment comment '主键' primary key,
     * </pre>
     */
    static String genCreateColumnIdSql() {
        return COLUMN_ID + " BIGINT AUTO_INCREMENT COMMENT '主键' PRIMARY KEY, \n";
    }

    /**
     * 建表语句公共字段创建 sql，如下：
     * <pre>
     *     create_time datetime              default CURRENT_TIMESTAMP not null comment '创建时间',
     *     update_time datetime              default CURRENT_TIMESTAMP not null comment '修改时间',
     *     version     int                   default 1 comment '版本号，乐观锁',
     *     delete_flag int                   default 0 comment '逻辑删除标志，状态1表示记录被删除，0表示正常记录'
     * </pre>
     */
    static String genCreateCommonColumnSql(boolean versionSupport, boolean deleteLogical) {
        StringBuilder sb = new StringBuilder(COLUMN_CREATE_TIME).append(" DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'");
        sb.append(",\n").append(COLUMN_UPDATE_TIME).append(" DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '修改时间'");
        if (versionSupport) {
            sb.append(",\n").append(COLUMN_VERSION).append(" INT DEFAULT 1 COMMENT '版本号，乐观锁'");
        }
        if (deleteLogical) {
            sb.append(",\n").append(COLUMN_DELETE_FLAG).append(" INT DEFAULT 0 COMMENT '逻辑删除标志，状态1表示记录被删除，0表示正常记录'");
        }
        sb.append("\n");


        return sb.toString();
    }

    /**
     * 建表语句字段创建 sql，如下：
     * <pre>
     *      id          bigint auto_increment comment '主键' primary key,
     *     dict_key    varchar(255) not null default '' comment '字典键',
     *     dict_value  varchar(255) not null default '' comment '字典值',
     * </pre>
     */
    protected String genCreateColumnSql(Attr attribute) {
        String sqlFormatForNoLength = "%s %s NULL %s COMMENT '%s', \n";
        String sqlFormat = "%s %s(%s) NULL %s COMMENT '%s', \n";
        String columnName = attribute.getColumnName();
        if (null != wrapper) {
            // 包装表名
            columnName = wrapper.wrap(columnName);
        }
        String defaultValue = Optional.ofNullable(attribute.getDefaultValue()).map(x -> x + "").orElse(null);
        if (!attribute.findAttrType().hasLength) {
            return String.format(sqlFormatForNoLength, columnName, attribute.findAttrType().sqlTypeName,
                    StringUtils.isBlank(defaultValue) ? "" : "DEFAULT '" + defaultValue + "'",
                    attribute.getDescription());
        } else {
            return String.format(sqlFormat, columnName,
                    attribute.findAttrType().sqlTypeName,
                    attribute.findAttrType().getLength(),
                    StringUtils.isBlank(defaultValue) ? "" : "DEFAULT '" + defaultValue + "'",
                    attribute.getDescription());
        }
    }

    /**
     * 构建用于表删除字段的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @param attr  属性
     * @return PreparedStatement
     */
    @Override
    public PreparedStatement psForDelColumn(Connection conn, String tableName, Attr attr) {
        ValidateUtil.assertTableNameNotNull(tableName);
        ValidateUtil.assertEntitySchemaAttrs(Collections.singleton(attr));
        String sql = String.format("ALTER TABLE %s DROP COLUMN %s;", tableName, attr.getColumnName());
        return StatementUtil.prepareStatement(conn, sql);
    }

    /**
     * 构建用于表修改字段的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @param attr  属性
     * @return PreparedStatement
     */
    @Override
    public PreparedStatement psForModifyColumn(Connection conn, String tableName, Attr attr) {
        ValidateUtil.assertTableNameNotNull(tableName);
        ValidateUtil.assertEntitySchemaAttrs(Collections.singleton(attr));
        String sql;
        if (!attr.findAttrType().hasLength) {
            sql = String.format("ALTER TABLE %s MODIFY COLUMN %s DATETIME NULL COMMENT '%s' AFTER id;",
                    tableName, attr.getColumnName(), attr.getName());
        } else {
            sql = String.format("ALTER TABLE %s MODIFY COLUMN %s %s(%s) NULL COMMENT '%s' AFTER id;", tableName,
                    attr.getColumnName(), attr.findAttrType().getSqlTypeName(),
                    attr.findAttrType().getLength(),
                    attr.getName());
        }
        return StatementUtil.prepareStatement(conn, sql);
    }


    /**
     * 构建用于删除表的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @return PreparedStatement
     */
    @Override
    public PreparedStatement psForDrop(Connection conn, String tableName) {
        Asserts.notBlank(tableName, "表名不能为空");
        final String sql = String.format("DROP TABLE IF EXISTS %s; ", tableName);
        return StatementUtil.prepareStatement(conn, sql);
    }

    /**
     * 构建用于插入的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @param dataMap   数据
     * @return PreparedStatement
     */
    @Override
    public PreparedStatement psForInsert(Connection conn, String tableName, Map<String, Object> dataMap) {
        final SqlBuilder insert = SqlBuilder.create(wrapper).insert(tableName, dataMap, this.dialectName());
        return StatementUtil.prepareStatement(conn, insert.build(), insert.getParamValueArray());
    }

    /**
     * 构建用于批量插入的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @param dataMaps  数据
     * @return PreparedStatement
     * @throws SQLException SQL执行异常
     */
    @Override
    public PreparedStatement psForInsertBatch(Connection conn, String tableName, Map<String, Object>... dataMaps) throws SQLException {
        if (ArrayUtils.isEmpty(dataMaps)) {
            throw new DbException("Entities for batch insert is empty !");
        }
        // 批量，根据第一行数据结构生成SQL占位符
        final SqlBuilder insert = SqlBuilder.create(wrapper).insert(tableName, dataMaps[0], this.dialectName());
        return StatementUtil.prepareStatementForBatch(conn, insert.build(), insert.getFields(), dataMaps);
    }

    @Override
    public PreparedStatement psForDelete(Connection conn, Query query) throws SQLException {
        Asserts.notNull(query, "query must not be null !");
        ConditionSegment conditionSegment = query.getCondition();
        if (conditionSegment == null) {
            // 对于无条件的删除语句直接抛出异常禁止，防止误删除
            throw new SQLException("No 'WHERE' condition, we can't prepared statement for delete everything.");
        }
        final SqlBuilder sqlBuilder = SqlBuilder.create(wrapper).delete(query.getFirstTableName()).where(conditionSegment);
        return StatementUtil.prepareStatement(conn, sqlBuilder.build(), sqlBuilder.getParamValueArray());

    }

    /**
     * 构建用于清除表数据的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @return PreparedStatement
     */
    @Override
    public PreparedStatement psForTruncate(Connection conn, String tableName) {
        ValidateUtil.assertTableNameNotNull(tableName);
        String sql = String.format("TRUNCATE TABLE %s;", tableName);
        return StatementUtil.prepareStatement(conn, sql);
    }

    @Override
    public PreparedStatement psForUpdate(Connection conn, String tableName, Map<String, Object> dataMap, Query query) throws SQLException {
        Asserts.notNull(query, "query must not be null !");
        ConditionSegment where = query.getCondition();
        if (where == null) {
            // 对于无条件的删除语句直接抛出异常禁止，防止误删除
            throw new SQLException("No 'WHERE' condition, we can't prepare statement for update everything.");
        }
        final SqlBuilder sqlBuilder = SqlBuilder.create(wrapper).update(tableName, dataMap).where(where);
        return StatementUtil.prepareStatement(conn, sqlBuilder.build(), sqlBuilder.getParamValueArray());
    }

    /**
     * 构建用于获取多条记录的PreparedStatement
     *
     * @param conn  数据库连接对象
     * @param query 查询条件（包含表名）
     * @return PreparedStatement
     */
    @Override
    public PreparedStatement psForListLimit(Connection conn, Query query) {
        // 验证
        if (query == null || StringUtils.isAnyBlank(query.getTableNames())) {
            throw new DbException("Table name must not be null !");
        }
        SqlBuilder find = SqlBuilder.create(wrapper).query(query).orderBy(query.getOrderBys());
        // 根据不同数据库在查询SQL语句基础上包装其分页的语句
        find = wrapPageSql(find, query.getLimit());

        return StatementUtil.prepareStatement(conn, find.build(), find.getParamValueArray());
    }

    /**
     * 构建用于获取多条记录的PreparedStatement
     *
     * @param conn  数据库连接对象
     * @param query 查询条件（包含表名）
     * @return PreparedStatement
     */
    @Override
    public PreparedStatement psForList(Connection conn, Query query) {
        // 验证
        if (query == null || StringUtils.isAnyBlank(query.getTableNames())) {
            throw new DbException("Table name must not be null !");
        }
        SqlBuilder find = SqlBuilder.create(wrapper).query(query).orderBy(query.getOrderBys());
        return StatementUtil.prepareStatement(conn, find.build(), find.getParamValueArray());
    }

    @Override
    public PreparedStatement psForFind(Connection conn, Query query) {
        Asserts.notNull(query, "query must not be null !");
        final SqlBuilder find = SqlBuilder.create(wrapper).query(query);
        return StatementUtil.prepareStatement(conn, find.build(), find.getParamValueArray());
    }

    /**
     * 根据不同数据库在查询SQL语句基础上包装其分页的语句<br>
     * 各自数据库通过重写此方法实现最小改动情况下修改分页语句
     *
     * @param find       标准查询语句
     * @param queryLimit 分页对象
     * @return 分页语句
     */
    protected SqlBuilder wrapPageSql(SqlBuilder find, QueryLimit queryLimit) {
        // limit A offset B 表示：A就是你需要多少行，B就是查询的起点位置。
        return find.append(" LIMIT ").append(queryLimit.getSize()).append(" OFFSET ").append(queryLimit.getStartPosition());
    }

    /**
     * 构建用于查询行数的PreparedStatement
     *
     * @param conn  数据库连接对象
     * @param query 查询条件（包含表名）
     * @return PreparedStatement
     * @throws SQLException SQL执行异常
     */
    @Override
    public PreparedStatement psForCount(Connection conn, Query query) {
        query.select("COUNT(*)");
        return psForFind(conn, query);
    }

    /**
     * 构建用于查询DDL信息的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @return PreparedStatement
     */
    @Override
    public PreparedStatement psForDdl(Connection conn, String tableName) {
        Asserts.notBlank(tableName, "表名不能为空");
        final String sql = String.format("SHOW CREATE TABLE %s; ", tableName);
        return StatementUtil.prepareStatement(conn, sql);
    }

    @Override
    public DialectName dialectName() {
        return DialectName.ANSI;
    }

}

