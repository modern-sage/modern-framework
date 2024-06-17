package com.modern.orm.mp.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据 ID 更新有值字段
 * 无内置租户
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 *@since 1.0.0
 */
public class TenantIgnoreUpdateById extends AbstractMethod {

    /**
     * @param methodName 方法名
     */
    protected TenantIgnoreUpdateById(String methodName) {
        super(methodName);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        boolean logicDelete = tableInfo.isWithLogicDelete();
        TenantIgnoreSqlMethodEnum sqlMethod = TenantIgnoreSqlMethodEnum.UPDATE_BY_ID_NO_TA;
        final String additional = optlockVersion(tableInfo) + tableInfo.getLogicDeleteSql(true, true);
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
            sqlSet(logicDelete, false, tableInfo, false, ENTITY, ENTITY_DOT),
            tableInfo.getKeyColumn(), ENTITY_DOT + tableInfo.getKeyProperty(), additional);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
    }
}
