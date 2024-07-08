package com.modern.orm.mp.utils;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * DataSourceUtils
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Slf4j
public abstract class DataSourceUtils {

    public static List<String> getAllTableNames(DataSource dataSource) {
        List<String> tableNames = new LinkedList<>();
        try {
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString(3);
                tableNames.add(tableName);
            }
        } catch (SQLException e) {
            log.error("数据库获取表失败", e);
        }
        return Collections.unmodifiableList(tableNames);
    }


}
