package com.modern.orm.mp.plugins.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.modern.orm.mp.config.MdMpProperties;
import com.modernframework.base.security.context.UserContext;
import com.modernframework.core.utils.ArrayUtils;
import com.modernframework.core.utils.StringUtils;
import com.modernframework.orm.DataOrmConstant;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;


/**
 * 租户处理实现
 */
public class TenantLineHandlerImpl implements TenantLineHandler {

    private final MdMpProperties mdMpProperties;

    public TenantLineHandlerImpl(MdMpProperties mdMpProperties) {
        this.mdMpProperties = mdMpProperties;
    }

    /**
     * 获取租户 ID 值表达式，只支持单个 ID 值
     * <p>
     *
     * @return 租户 ID 值表达式
     */
    @Override
    public Expression getTenantId() {
        return new LongValue(UserContext.getTenantId());
    }

    @Override
    public String getTenantIdColumn() {
        return StringUtils.camelToUnderline(DataOrmConstant.ATTR_TENANT_ID);
    }

    @Override
    public boolean ignoreTable(String tableName) {
        tableName = tableName.toLowerCase();
        // 这里可以判断是否过滤表
        if (ArrayUtils.isNotEmpty(mdMpProperties.getTenantIgnoreTables())) {
            return ArrayUtils.contains(mdMpProperties.getTenantIgnoreTables(), tableName);
        }
        return false;
    }
}