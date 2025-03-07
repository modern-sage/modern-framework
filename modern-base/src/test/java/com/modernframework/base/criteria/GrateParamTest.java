package com.modernframework.base.criteria;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

/**
 * GrateParamTest
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class GrateParamTest {

    @Data
    class TestUser {
        private String name;
    }

    @Data
    class TestRole {
        private String roleName;
    }

    @Test
    public void test() {
        GrateParam<TestUser> param = new GrateParam();
        param.select(TestUser::getName);
        Assert.assertEquals("name", param.getIncludeAttributes().iterator().next());
        param.select(TestRole.class, TestRole::getRoleName);
        Assert.assertTrue(param.getIncludeAttributes().contains("roleName"));

    }


}
