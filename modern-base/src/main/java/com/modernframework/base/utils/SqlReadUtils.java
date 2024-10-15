package com.modernframework.base.utils;

import com.modernframework.core.utils.IOUtils;
import com.modernframework.core.utils.StringUtils;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * SqlReadUtils
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class SqlReadUtils {

    public final static String CREATE = "CREATE TABLE";
    public final static String INSERT = "INSERT INTO";

    public static Map<String, SqlInit> resolveSqlScript(String resourcePath) {
        List<String> paragraphList = new LinkedList<>();
        InputStream inputStream = null;
        // 整理出段落
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
            byte[] bytes = IOUtils.readInputStream(inputStream);
            String ddlStatements = new String(bytes);
            // 逐行读取
            BufferedReader reader = new BufferedReader(new StringReader(ddlStatements));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if(StringUtils.isBlank(line)) {
                    continue;
                }
                sb.append(line).append("\n");
                if (line.endsWith(";")) {
                    paragraphList.add(sb.toString());
                    sb.setLength(0);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeSilently(inputStream);
        }
        Map<String, SqlInit> map = new HashMap<>();
        paragraphList.forEach(x -> resolveOneSql(x, map));
        return map;
    }

    private static void resolveOneSql(String sql, Map<String, SqlInit> map) {
        String tableName;
        if (sql.toUpperCase().startsWith(CREATE)) {
            tableName = getTableName(sql, CREATE);
            SqlInit sqlInit = map.computeIfAbsent(tableName, m -> new SqlInit());
            sqlInit.create = sql;
        } else if (sql.toUpperCase().startsWith(INSERT)) {
            tableName = getTableName(sql, INSERT);
            SqlInit sqlInit = map.computeIfAbsent(tableName, m -> new SqlInit());
            if(sqlInit.inserts == null) {
                sqlInit.inserts = new LinkedList<>();
            }
            sqlInit.inserts.add(sql);
        }
    }

    private static String getTableName(String sql, String sqlPre) {
        return  StringUtils.sub(sql, sqlPre.length() + 1,
                StringUtils.indexOf(sql, '(')).trim();
    }

    @Data
    static class SqlInit {
        private String create;
        private List<String> inserts;
    }

}
