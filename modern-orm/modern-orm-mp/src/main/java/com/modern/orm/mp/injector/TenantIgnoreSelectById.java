package com.modern.orm.mp.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;

/**
 * @Description 根据ID 查询一条数据，无内置租户
 * @Author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @Date 2020/7/28 10:57
 */
public class TenantIgnoreSelectById extends AbstractMethod {

    /**
     * @param methodName 方法名
     */
    protected TenantIgnoreSelectById(String methodName) {
        super(methodName);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        TenantIgnoreSqlMethodEnum sqlMethod = TenantIgnoreSqlMethodEnum.LOGIC_SELECT_BY_ID_NO_TA;
        SqlSource sqlSource = new RawSqlSource(configuration, String.format(sqlMethod.getSql(),
                sqlSelectColumns(tableInfo, false),
                tableInfo.getTableName(), tableInfo.getKeyColumn(), tableInfo.getKeyProperty(),
                tableInfo.getLogicDeleteSql(true, true)), Object.class);
        return this.addSelectMappedStatementForTable(mapperClass, sqlMethod.getMethod(), sqlSource, tableInfo);
    }
}
