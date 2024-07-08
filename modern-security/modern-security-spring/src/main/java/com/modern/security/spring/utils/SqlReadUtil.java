package com.modern.security.spring.utils;

import com.modernframework.core.utils.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * SqlRead
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public abstract class SqlReadUtil {

    public final static String CT = "create table";

    public static Map<String, String> getTableCreateSql(String resourcePath) {
        Map<String, String> map = new HashMap<>();
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
            byte[] bytes = IOUtils.readInputStream(inputStream);
            String ddlStatements = new String(bytes);
            // 逐行读取
            BufferedReader reader = new BufferedReader(new StringReader(ddlStatements));
            String tableName = "";
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                sb.append(line).append("\n");
                if (line.endsWith(";")) {
                    map.put(tableName, sb.toString());
                    sb.setLength(0);
                    tableName = "";
                } else if (line.toLowerCase().startsWith(CT)) {
                    tableName = line.substring(CT.length() + 1).trim();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeSilently(inputStream);
        }
        return map;
    }

}
