package com.modern.security.spring.utils;

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

    public static Map<String, String> getTableCreateSql(String filePath) {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));) {
            String tableName = "";
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.endsWith(";")) {
                    map.put(tableName, sb.toString());
                    sb.setLength(0);
                    tableName = "";
                } else {
                    sb.append(line);
                    if (line.toLowerCase().startsWith(CT)) {
                        tableName = line.substring(CT.length() + 1).trim();
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

}
