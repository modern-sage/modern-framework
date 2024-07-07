package com.modern.data;

import com.modern.data.constant.DialectName;
import org.w3c.dom.Attr;

import javax.management.Query;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * Dialect
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface Dialect extends Serializable {

    /**
     * 包装器
     */
    ColumnWrapper getWrapper();

    /**
     * 设置包装器
     */
    void setWrapper(ColumnWrapper wrapper);

    // -------------------------------------------- DDL

    /**
     * 构建用于创建表的PreparedStatement
     *
     * @param conn       数据库连接对象
     * @param attributes 属性
     * @return PreparedStatement
     */
    PreparedStatement psForCreate(Connection conn, String tableName, SchemaProps schemaProps, Collection<? extends Attr> attributes);

    /**
     * 构建用于表增加字段的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @param attrType  属性
     * @return PreparedStatement
     */
    PreparedStatement psForAddColumn(Connection conn, String tableName, Attr attrType);

    /**
     * 构建用于表删除字段的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @param attr  属性
     * @return PreparedStatement
     */
    PreparedStatement psForDelColumn(Connection conn, String tableName, Attr attr);

    /**
     * 构建用于表修改字段的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @param attr  属性
     * @return PreparedStatement
     */
    PreparedStatement psForModifyColumn(Connection conn, String tableName, Attr attr);

    /**
     * 构建用于删除表的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @return PreparedStatement
     */
    PreparedStatement psForDrop(Connection conn, String tableName);


    // -------------------------------------------- Execute

    /**
     * 构建用于插入的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @param dataMap   数据
     * @return PreparedStatement
     * @throws SQLException SQL执行异常
     */
    PreparedStatement psForInsert(Connection conn, String tableName, Map<String, Object> dataMap) throws SQLException;

    /**
     * 构建用于批量插入的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @param dataMap   数据
     * @return PreparedStatement
     * @throws SQLException SQL执行异常
     */
    PreparedStatement psForBatchInsert(Connection conn, String tableName, Map<String, Object>... dataMap) throws SQLException;

    /**
     * 构建用于删除的PreparedStatement
     *
     * @param conn  数据库连接对象
     * @param query 查找条件（包含表名）
     * @return PreparedStatement
     * @throws SQLException SQL执行异常
     */
    PreparedStatement psForDelete(Connection conn, Query query) throws SQLException;

    /**
     * 构建用于清除表数据的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @return PreparedStatement
     */
    PreparedStatement psForTruncate(Connection conn, String tableName) throws SQLException;

    /**
     * 构建用于更新的PreparedStatement
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @param dataMap   数据
     * @param query     查找条件（包含表名）
     * @return PreparedStatement
     * @throws SQLException SQL执行异常
     */
    PreparedStatement psForUpdate(Connection conn, String tableName, Map<String, Object> dataMap, Query query) throws SQLException;

    // -------------------------------------------- Query

    /**
     * 构建用于获取多条记录的PreparedStatement
     *
     * @param conn  数据库连接对象
     * @param query 查询条件（包含表名）
     * @return PreparedStatement
     */
    PreparedStatement psForListLimit(Connection conn, Query query);

    /**
     * 构建用于获取多条记录的PreparedStatement
     *
     * @param conn  数据库连接对象
     * @param query 查询条件（包含表名）
     * @return PreparedStatement
     */
    PreparedStatement psForList(Connection conn, Query query);

    /**
     * 构建用于获取多条记录的PreparedStatement
     *
     * @param conn  数据库连接对象
     * @param query 查询条件（包含表名）
     * @return PreparedStatement
     */
    PreparedStatement psForFind(Connection conn, Query query);

    /**
     * 构建用于查询行数的PreparedStatement
     *
     * @param conn  数据库连接对象
     * @param query 查询条件（包含表名）
     * @return PreparedStatement
     */
    PreparedStatement psForCount(Connection conn, Query query);

    /**
     * 构建用于查询DDL信息的PreparedStatement
     *
     * @param conn  数据库连接对象
     * @param tableName 表名
     * @return PreparedStatement
     */
    PreparedStatement psForDdl(Connection conn, String tableName);

    /**
     * 构建用于upsert的{@link PreparedStatement}<br>
     * 方言实现需实现此默认方法，如果没有实现，抛出{@link SQLException}
     *
     * @param conn      数据库连接对象
     * @param tableName 表名
     * @param dataMap   数据
     * @param keys      查找字段，某些数据库此字段必须，如H2，某些数据库无需此字段，如MySQL（通过主键）
     * @return PreparedStatement
     * @throws SQLException SQL执行异常，或方言数据不支持此操作
     */
    default PreparedStatement psForUpsert(Connection conn, String tableName, Map<String, Object> dataMap, String... keys) throws SQLException {
        throw new SQLException("Unsupported upsert operation of " + dialectName());
    }

    /**
     * 方言名
     *
     * @return 方言名
     */
    DialectName dialectName();
}
