package com.modern.orm.mp.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.modern.orm.mp.plugins.handler.DynamicTableNameHandler;
import com.modern.orm.mp.plugins.handler.PublicFieldMetaObjectHandler;
import com.modern.orm.mp.plugins.inner.MdDynamicTableInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static com.modern.orm.mp.config.OrmMpProperties.PROPERTIES_PREFIX;

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
@ConditionalOnClass(OrmMpProperties.class)
@EnableConfigurationProperties(OrmMpProperties.class)
public class OrmMpConfiguration {

    private final Logger log = LoggerFactory.getLogger(OrmMpConfiguration.class.getName());

    /**
     * 配置
     * - 下划线
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusPropertiesCustomizer plusPropertiesCustomizer(OrmMpProperties properties) {
        return plusProperties -> plusProperties.getGlobalConfig().getDbConfig().setTableUnderline(properties.isUnderline());
    }

    /**
     * 插件配置
     * - 分页插件
     * - 乐观锁插件
     * - 租户配置
     */
    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
//        // 租户配置
//        try {
//            interceptor.addInnerInterceptor(tenantLineInnerInterceptor(properties));
//        } catch (NoSuchBeanDefinitionException ignore) {
//            // NOP
//        }
        return interceptor;
    }

//    /**
//     * 插件配置
//     * - 租户配置
//     */
//    @Bean
//    @ConditionalOnProperty(prefix = PROPERTIES_PREFIX, name = "enableTenant", havingValue = "true")
//    public TenantLineInnerInterceptor tenantLineInnerInterceptor(OrmMpProperties properties) {
//        TenantLineHandler tenantLineHandler = new MdTenantLineHandler(properties);
//        return new TenantLineInnerInterceptor(tenantLineHandler);
//    }

    /**
     * 插件配置
     * - 动态表名
     */
    @Bean
    @ConditionalOnProperty(prefix = PROPERTIES_PREFIX, name = "enable-dynamic-table-name", havingValue = "true")
    DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor() {
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new MdDynamicTableInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler(new DynamicTableNameHandler());
        mybatisPlusInterceptor().addInnerInterceptor(dynamicTableNameInnerInterceptor);
        return dynamicTableNameInnerInterceptor;
    }

    /**
     * 公共字段自动注入
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new PublicFieldMetaObjectHandler();
    }

}
