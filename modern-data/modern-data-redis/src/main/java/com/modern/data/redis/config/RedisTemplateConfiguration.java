package com.modern.data.redis.config;

import com.modernframework.core.utils.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;

/**
 * Redis Template 配置
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisTemplateConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    /**
     * jedis连接池信息
     */
    @Bean
    public LettuceClientConfiguration lettuceClientConfiguration() {
        RedisProperties.Pool pool = redisProperties.getPool();
        if (pool == null) {
            pool = new RedisProperties.Pool();
        }

        GenericObjectPoolConfig<Object> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxIdle(pool.getMaxIdle());
        genericObjectPoolConfig.setMaxTotal(pool.getMaxTotal());
        genericObjectPoolConfig.setMaxWaitMillis(pool.getMaxWaitMillis());
        genericObjectPoolConfig.setMinEvictableIdleTimeMillis(pool.getMinEvictableIdleTimeMillis());
        genericObjectPoolConfig.setNumTestsPerEvictionRun(pool.getNumTestsPerEvictionRun());
        genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRunsMillis());
        genericObjectPoolConfig.setTestOnBorrow(pool.getTestOnBorrow());
        genericObjectPoolConfig.setTestWhileIdle(pool.getTestWhileIdle());

        return LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(10_000))
                .poolConfig(genericObjectPoolConfig)
                .build();
    }

    /**
     * 集群模式连接工厂
     */
    @Bean
    @ConditionalOnProperty(prefix = "modern.data.redis", name = "type", havingValue = "cluster")
    public RedisConnectionFactory clusterConnectionFactory(LettuceClientConfiguration lettuceClientConfiguration) {
        RedisProperties.Cluster cluster = redisProperties.getCluster();
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(cluster.getNodes());
        clusterConfiguration.setMaxRedirects(cluster.getMaxRedirects());
        if (StringUtils.isNotBlank(cluster.getPassword())) {
            clusterConfiguration.setPassword(RedisPassword.of(cluster.getPassword()));
        }
        return new LettuceConnectionFactory(clusterConfiguration, lettuceClientConfiguration);
    }

    /**
     * 单机模式连接工厂
     */
    @Bean
    @ConditionalOnProperty(prefix = "modern.data.redis", name = "type", havingValue = "standalone")
    public RedisConnectionFactory standaloneConnectionFactory(LettuceClientConfiguration lettuceClientConfiguration) {
        RedisProperties.Standalone standalone = redisProperties.getStandalone();
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(standalone.getHostName());
        standaloneConfiguration.setPort(standalone.getPort());
        if (StringUtils.isNotBlank(standalone.getPassword())) {
            standaloneConfiguration.setPassword(RedisPassword.of(standalone.getPassword()));
        }
        return new LettuceConnectionFactory(standaloneConfiguration, lettuceClientConfiguration);
    }

}
