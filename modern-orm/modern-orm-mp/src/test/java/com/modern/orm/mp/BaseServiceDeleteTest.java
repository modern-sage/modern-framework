package com.modern.orm.mp;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.modern.orm.mp.config.OrmMpConfiguration;
import com.modern.orm.mp.config.TestConfiguration;
import com.modern.orm.mp.service.MdDataTestPersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Service 删除测试
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 0.2.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
@ContextConfiguration(classes = {
        // 数据源自动装配
        DataSourceAutoConfiguration.class,
        // Mybatis 相关自动装配
        MybatisPlusAutoConfiguration.class,
        OrmMpConfiguration.class,

        // 测试配置
        TestConfiguration.class,
})
@SpringBootTest(classes = {BaseServiceDeleteTest.class})
public class BaseServiceDeleteTest {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected MdDataTestPersonService mdDataTestPersonService;

    @Before
    public void init() {
        jdbcTemplate.execute(MdDataTestPerson.DROP_SQL);
        jdbcTemplate.execute(MdDataTestPerson.CREATE_SQL);
    }

    @After
    public void destroy() {
        jdbcTemplate.execute(MdDataTestPerson.DROP_SQL);
    }

    @Test
    public void testDelete() {
        // 插入一条数据
        MdDataTestPerson person = new MdDataTestPerson();
        person.setCode("code-" + System.currentTimeMillis());
        person.insert();

        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getId());
        Assert.assertEquals(Integer.valueOf(1), person.getVersion());

        // 删除
        mdDataTestPersonService.removeById(person);

        // 版本保持一致
        jdbcTemplate.query("select version from md_data_test_person where id = " + person.getId(), rs -> {
            String version = rs.getString(1);
            Assert.assertEquals(person.getVersion() + "", version);
        });

    }
}
