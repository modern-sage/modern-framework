package com.modernframework.base.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class SqlReadUtilsTest {

    @Test
    public void resolveSqlScript() {
        Map<String, SqlReadUtils.SqlInit> stringSqlInitMap = SqlReadUtils.resolveSqlScript("utils/test.sql");
        Assert.assertEquals(1, stringSqlInitMap.size());
        Assert.assertNotNull(stringSqlInitMap.get("sys_user"));
        Assert.assertEquals(1, stringSqlInitMap.get("sys_user").getInserts().size());
    }

}
