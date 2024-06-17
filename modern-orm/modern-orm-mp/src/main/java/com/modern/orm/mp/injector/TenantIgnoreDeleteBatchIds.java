package com.modern.orm.mp.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;


/**
 * @Description 根据ID批量删除，无内置租户
 * @Author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @Date 2020/9/28 下午7:33
 */
public class TenantIgnoreDeleteBatchIds extends AbstractMethod {

    protected TenantIgnoreDeleteBatchIds(String methodName) {
        super(methodName);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql;
        TenantIgnoreSqlMethodEnum sqlMethod = TenantIgnoreSqlMethodEnum.LOGIC_DELETE_BATCH_BY_IDS_NO_TA;
        if (tableInfo.isLogicDelete()) {
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlLogicSet(tableInfo),
                    tableInfo.getKeyColumn(),
                    SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA),
                    tableInfo.getLogicDeleteSql(true, true));
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
        } else {
            sqlMethod = TenantIgnoreSqlMethodEnum.DELETE_BATCH_BY_IDS_NO_TA;
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(),
                    SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA));
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource);
        }
    }
}
