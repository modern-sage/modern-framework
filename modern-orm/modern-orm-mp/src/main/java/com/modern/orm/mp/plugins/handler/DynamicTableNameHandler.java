package com.modern.orm.mp.plugins.handler;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.modern.orm.mp.utils.DynamicTableNameUtils;
import com.modernframework.orm.anno.DynamicTableName;

/**
 * 动态表名
 */
public class DynamicTableNameHandler implements TableNameHandler {

    /**
     * 生成动态表名
     *
     * @param sql       当前执行 SQL
     * @param tableName 表名
     * @return String
     */
    @Override
    public String dynamicTableName(String sql, String tableName) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(tableName);
        Class<?> entityType = tableInfo.getEntityType();
        DynamicTableName annotation = entityType.getAnnotation(DynamicTableName.class);
        if(annotation != null) {
            return DynamicTableNameUtils.parseTableName(entityType, annotation.dynamicExpression());
        } else {
            return tableName;
        }
    }
}
