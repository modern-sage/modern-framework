package com.modern.orm.mp.plugins.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.modern.orm.mp.config.MdMpProperties;
import com.modernframework.core.utils.ArrayUtils;
import com.modernframework.core.utils.CollectionUtils;
import com.modernframework.core.utils.StringUtils;

import java.util.List;

/**
 * 动态表明前缀处理
 */
public class TableNamePrefixHandler implements TableNameHandler {

    private final MdMpProperties mdMpProperties;

    /**
     * 数据库所有表名集合
     */
    private final List<String> tableNameList;

    public TableNamePrefixHandler(MdMpProperties mdMpProperties, List<String> tableNameList) {
        this.mdMpProperties = mdMpProperties;
        this.tableNameList = tableNameList;
    }

    /**
     * 生成动态表名
     *
     * @param sql       当前执行 SQL
     * @param tableName 表名
     * @return String
     */
    @Override
    public String dynamicTableName(String sql, String tableName) {
        if (ArrayUtils.isEmpty(mdMpProperties.getPrefixs())) {
            return tableName;
        }
        //使用表前缀子串拼接根据实体生成的表名，和库中的数据库表做比对，若相同则认为唯一表
        for (String ts : tableNameList) {
            for (String ps : mdMpProperties.getPrefixs()) {
                if (StringUtils.equalsIgnoreCase(ts, ps + tableName)) {
                    return ts;
                }
            }
        }
        return tableName;
    }
}
