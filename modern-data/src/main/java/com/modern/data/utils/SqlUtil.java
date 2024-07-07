package com.modern.data.utils;


import com.modern.data.Condition;
import com.modern.data.exception.DataException;

import java.sql.*;

/**
 * SQL辅助工具
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public abstract class SqlUtil {

    /**
     * 创建LIKE语句中的值，创建的结果为：
     *
     * <pre>
     * 1、LikeType.StartWith: %value
     * 2、LikeType.EndWith: value%
     * 3、LikeType.Contains: %value%
     * </pre>
     * <p>
     * 如果withLikeKeyword为true，则结果为：
     *
     * <pre>
     * 1、LikeType.StartWith: LIKE %value
     * 2、LikeType.EndWith: LIKE value%
     * 3、LikeType.Contains: LIKE %value%
     * </pre>
     *
     * @param value           被查找值
     * @param likeType        LIKE值类型 {@link Condition.LikeType}
     * @param withLikeKeyword 是否包含LIKE关键字
     * @return 拼接后的like值
     */
    public static String buildLikeValue(String value, Condition.LikeType likeType, boolean withLikeKeyword) {
        if (null == value) {
            return value;
        }

        StringBuilder likeValue = new StringBuilder();
        if(withLikeKeyword) {
            likeValue.append("LIKE ");
        }
        switch (likeType) {
            case StartWith:
                likeValue.append('%').append(value);
                break;
            case EndWith:
                likeValue.append(value).append('%');
                break;
            case Contains:
                likeValue.append('%').append(value).append('%');
                break;

            default:
                break;
        }
        return likeValue.toString();
    }

    /**
     * 创建Blob对象
     *
     * @param conn {@link Connection}
     * @param data 数据
     * @return {@link Blob}
     */
    public static Blob createBlob(Connection conn, byte[] data) {
        Blob blob;
        try {
            blob = conn.createBlob();
            blob.setBytes(0, data);
        } catch (SQLException e) {
            throw new DataException(e);
        }
        return blob;
    }

    /**
     * 转换为{@link Date}
     *
     * @param date {@link java.util.Date}
     * @return {@link Date}
     */
    public static Date toSqlDate(Date date) {
        return new Date(date.getTime());
    }

    /**
     * 转换为{@link Timestamp}
     *
     * @param date {@link java.util.Date}
     * @return {@link Timestamp}
     */
    public static Timestamp toSqlTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }
}
