package com.modern.orm.mp.injector;

/**
 * @Description LsSqlMethod
 * @Author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 */
public enum TenantIgnoreSqlMethodEnum {


    /**
     * 根据id逻辑查询
     */
    LOGIC_SELECT_BY_ID_NO_TA("selectByIdNoTa", "根据ID 查询一条数据，无内置租户", "SELECT %s FROM %s WHERE %s=#{%s} %s"),

    /**
     * 修改
     */
    UPDATE_BY_ID_NO_TA("updateByIdNoTa", "根据ID 选择修改数据，无内置租户", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),

    /**
     * 删除
     */
    DELETE_BATCH_BY_IDS_NO_TA("deleteBatchIdsNoTa", "根据ID集合，批量删除数据，无内置租户", "<script>\nDELETE FROM %s WHERE %s IN (%s)\n</script>"),

    /**
     * 逻辑删除
     */
    LOGIC_DELETE_BATCH_BY_IDS_NO_TA("deleteBatchIdsNoTa", "根据ID批量删除，无内置租户", "<script>\nUPDATE %s %s WHERE %s IN (%s) %s\n</script>"),
    ;

    private final String method;
    private final String desc;
    private final String sql;

    TenantIgnoreSqlMethodEnum(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }

}
