package com.modernframework.base.db;

import com.modernframework.core.utils.StringUtils;

/**
 * 常用数据库驱动枚举
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 0.2.0
 */
public enum DatabaseDriver {

    ANSI("", "", ""),

    // MySQL 系列,
    MYSQL("MySQL Connector/J", "mysql", "com.mysql.jdbc.Driver", "com.mysql.cj.jdbc.Driver"),
    MARIADB("MariaDB Connector/J", "mariadb", "org.mariadb.jdbc.Driver"),

    // Oracle 系列
    ORACLE("Oracle JDBC Driver", "oracle", "oracle.jdbc.OracleDriver", "oracle.jdbc.driver.OracleDriver"),

    // PostgreSQL
    POSTGRESQL("PostgreSQL JDBC Driver", "postgresql", "org.postgresql.Driver"),

    // SQLite
    SQLITE("SQLite JDBC Driver", "sqlite3", "org.sqlite.JDBC"),

    // SQL Server
    SQLSERVER("Microsoft JDBC Driver for SQL Server", "sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),

    // Hive
    HIVE("Hive JDBC Driver", "hive", "org.apache.hadoop.hive.jdbc.HiveDriver"),
    HIVE2("Hive2 JDBC Driver", "hive2", "org.apache.hive.jdbc.HiveDriver"),

    // 嵌入式数据库
    H2("H2 JDBC Driver", "h2", "org.h2.Driver"),
    DERBY("Apache Derby JDBC Driver", "derby", "org.apache.derby.jdbc.AutoloadedDriver"),
    HSQLDB("HSQLDB JDBC Driver", "hsqldb", "org.hsqldb.jdbc.JDBCDriver");

    // 驱动名称（如：MySQL Connector/J）
    private final String driverName;
    // 数据库显示名称（如：MySQL）
    private final String databaseName;
    // 驱动类名（支持多个版本）
    private final String[] driverClasses;

    DatabaseDriver(String driverName, String databaseName, String... driverClasses) {
        this.driverName = driverName;
        this.databaseName = databaseName;
        this.driverClasses = driverClasses;
    }

    /**
     * 获取驱动名称（如：MySQL Connector/J）
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * 获取数据库显示名称（如：MySQL）
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * 获取主驱动类名（第一个）
     */
    public String getDriverClass() {
        return driverClasses[0];
    }

    /**
     * 获取所有可能的驱动类名
     */
    public String[] getDriverClasses() {
        return driverClasses;
    }

    public static DatabaseDriver match(String driver) {
        if(StringUtils.isBlank(driver)) {
            return null;
        }
        DatabaseDriver databaseDriver = findByDriverClass(driver);
        if(databaseDriver != null) {
            return databaseDriver;
        }
        return findByDriverName(driver);
    }

    /**
     * 根据驱动类名查找对应的数据库类型
     */
    public static DatabaseDriver findByDriverClass(String driverClass) {
        if (driverClass == null) return null;

        for (DatabaseDriver dbDriver : values()) {
            for (String clazz : dbDriver.getDriverClasses()) {
                if (driverClass.equals(clazz)) {
                    return dbDriver;
                }
            }
        }

        return null;
    }

    /**
     * 根据驱动名称查找对应的数据库类型（模糊匹配）
     */
    public static DatabaseDriver findByDriverName(String driverName) {
        if (driverName == null) return null;

        for (DatabaseDriver dbDriver : values()) {
            if (dbDriver.getDriverName().equalsIgnoreCase(driverName) ||
                    dbDriver.getDriverName().toLowerCase().contains(driverName.toLowerCase())) {
                return dbDriver;
            }
        }

        return null;
    }
}
