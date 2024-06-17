package com.modern.orm.mp.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.modern.orm.mp.injector.MdDefaultMpSqlInjector;
import com.modern.orm.mp.plugins.handler.TableNamePrefixHandler;
import com.modern.orm.mp.plugins.handler.TenantLineHandlerImpl;
import com.modern.orm.mp.plugins.inner.MdDynamicTableInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.modern.orm.mp.config.MdMpConfigContent.PROPERTIES_PREFIX;


/**
 * <p>
 * MybatisPlus配置
 * </p>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Configuration
@ConditionalOnBean(DataSource.class)
@ConditionalOnClass(MdMpProperties.class)
@EnableConfigurationProperties(MdMpProperties.class)
public class MdMpConfiguration implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(MdMpConfiguration.class.getName());

    /**
     * 数据库表名集合
     */
    private List<String> tableList = new ArrayList<>();

    /**
     * 配置
     * - 下划线
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusPropertiesCustomizer plusPropertiesCustomizer(MdMpProperties properties) {
        return plusProperties -> {
            plusProperties.getGlobalConfig().getDbConfig().setTableUnderline(properties.isUnderline());
        };
    }

    /**
     * 插件配置
     * - 分页插件
     * - 乐观锁插件
     * - 表前缀处理
     * - 租户配置
     */
    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor(MdMpProperties properties) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 表前缀处理插件
        try {
            interceptor.addInnerInterceptor(tablePrefixHandle(properties));
        } catch (NoSuchBeanDefinitionException ignore) {
            // NOP
        }
        // 租户配置
        try {
            interceptor.addInnerInterceptor(tenantLineInnerInterceptor(properties));
        } catch (NoSuchBeanDefinitionException ignore) {
            // NOP
        }
        return interceptor;
    }

    /**
     * 插件配置
     * - 表前缀处理
     */
    @Bean
    @Conditional(TablePrefixCondition.class)
    DynamicTableNameInnerInterceptor tablePrefixHandle(MdMpProperties properties) {
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new MdDynamicTableInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler(new TableNamePrefixHandler(properties, tableList));
        return dynamicTableNameInnerInterceptor;
    }

    /**
     * 插件配置
     * - 租户配置
     */
    @Bean
    @ConditionalOnProperty(prefix = PROPERTIES_PREFIX, name = "enableTenant", havingValue = "true")
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(MdMpProperties properties) {
        TenantLineHandler tenantLineHandler = new TenantLineHandlerImpl(properties);
        return new TenantLineInnerInterceptor(tenantLineHandler);
    }

    /**
     * 公共字段自动注入
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MdMpPublicFieldHandler();
    }

    /**
     * 自定义 SqlInjector
     * 里面包含自定义的全局方法
     */
    @Bean
    public ISqlInjector lsSqlInjector() {
        return new MdDefaultMpSqlInjector();
    }

    @Override
    public void afterPropertiesSet() {
//        try {
//            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
//            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
//            while (tables.next()) {
//                String tableName = tables.getString(3);
//                tableList.add(tableName);
//            }
//        } catch (SQLException e) {
//            log.error("数据库获取表失败", e);
//        }
    }
}
