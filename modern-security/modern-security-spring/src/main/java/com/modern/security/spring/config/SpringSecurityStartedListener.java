package com.modern.security.spring.config;

import com.modern.orm.mp.utils.DataSourceUtils;
import com.modern.security.spring.support.entity.SysAuthDetails;
import com.modern.security.spring.support.entity.SysAuthUser;
import com.modern.security.spring.support.service.impl.DefaultUserDetailsServiceImpl;
import com.modern.security.spring.support.service.impl.SysAuthDetailsServiceImpl;
import com.modern.security.spring.utils.SqlReadUtil;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * SpringSecurityListener
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class SpringSecurityStartedListener implements ApplicationListener<ApplicationStartedEvent> {


    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        initDataBase(event);
    }

    /**
     * 初始化数据库
     */
    private void initDataBase(ApplicationStartedEvent event) {
        Map<String, String> createTableSql = null;
        List<String> tables = null;
        ConfigurableApplicationContext context = event.getApplicationContext();
        // 初始化 系统用户
        Object userDetailsService = context.getBean("userDetailsService");
        if (userDetailsService instanceof DefaultUserDetailsServiceImpl) {
            if (tables == null) {
                tables = DataSourceUtils.getAllTableNames(context.getBean(DataSource.class));
            }
            if (!tables.contains(SysAuthUser.TABLE_NAME)) {
                if (createTableSql == null) {
                    createTableSql = SqlReadUtil.getTableCreateSql("com/modern/security/db/create/security.mysql.create.sql");
                }
                context.getBean(JdbcTemplate.class).execute(createTableSql.get(SysAuthUser.TABLE_NAME));
            }
        }

        // 初始化 token存储
        Object authDetailsService = context.getBean("authenticationDetailsService");
        if (authDetailsService instanceof SysAuthDetailsServiceImpl) {
            tables = DataSourceUtils.getAllTableNames(context.getBean(DataSource.class));
            if (!tables.contains(SysAuthDetails.TABLE_NAME)) {
                if (createTableSql == null) {
                    createTableSql = SqlReadUtil.getTableCreateSql("com/modern/security/db/create/security.mysql.create.sql");
                }
                context.getBean(JdbcTemplate.class).execute(createTableSql.get(SysAuthDetails.TABLE_NAME));
            }
        }
    }

}
