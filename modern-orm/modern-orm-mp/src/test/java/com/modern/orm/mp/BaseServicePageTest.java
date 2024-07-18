package com.modern.orm.mp;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.modern.orm.mp.config.OrmMpConfiguration;
import com.modern.orm.mp.config.TestConfiguration;
import com.modern.orm.mp.service.MdDataTestPersonService;
import com.modernframework.base.criteria.GrateParam;
import com.modernframework.base.vo.PageRec;
import com.modernframework.core.func.ThrowableConsumer;
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

import java.util.Set;

/**
 * Service 分页测试
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
@SpringBootTest(classes = {BaseServicePageTest.class})
public class BaseServicePageTest {
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
    public void testPage() {
        // 插入11条数据
        for (int i = 0; i < 11; i++) {
            MdDataTestPerson person = new MdDataTestPerson();
            person.setCode("code-" + (i + 1));
            person.insert();
        }

        // 查询分页
        GrateParam<MdDataTestPerson> pageParam1 = new GrateParam<>();
        pageParam1.setPageNumber(1);
        pageParam1.setPageSize(20);
        assertPage(pageParam1, 11, page -> {
            for (int i = 0; i < page.size(); i++) {
                Assert.assertEquals("code-" + (11 - i), page.get(i).getCode());
            }
        });

        // 条件查询分页， 查询 ["code-11", "code-10"]的数据
        GrateParam<MdDataTestPerson> pageParam2 = new GrateParam<>();
        pageParam2.setPageNumber(1);
        pageParam2.setPageSize(20);
        pageParam2.in(MdDataTestPerson::getCode, Set.of("code-11", "code-10"));
        assertPage(pageParam2, 2,  page -> {
            for (int i = 0; i < page.size(); i++) {
                Assert.assertEquals("code-" + (11 - i), page.get(i).getCode());
            }
        });

    }

    private void assertPage(GrateParam<MdDataTestPerson> pageParam, long expectTotal, ThrowableConsumer<PageRec<MdDataTestPerson>> throwableConsumer) {
        PageRec<MdDataTestPerson> page = mdDataTestPersonService.page(pageParam);
        Assert.assertNotNull(page);
        Assert.assertEquals(expectTotal, page.getTotal());
        ThrowableConsumer.execute(page, throwableConsumer);
    }

}
