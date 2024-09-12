package com.modern.data.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * RedisProperties
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "modern.data.redis")
public class RedisProperties {

    public enum Type {
        CLUSTER,
        STANDALONE;
    }

    /**
     * 类型；默认单体
     */
    private Type type = Type.STANDALONE;

    private Pool pool;

    private Cluster cluster;

    private Standalone standalone;

    @Data
    public static class Cluster {
        private List<String> nodes;
        private Integer maxRedirects;
        private String password;
    }

    @Data
    public static class Standalone {
        /**
         * IP地址
         */
        private String hostName;
        /**
         * 如果Redis设置有密码
         */
        private String password;
        /**
         * 端口号
         */
        private Integer port;
    }

    @Data
    public static class Pool {
        /**
         * 最大空闲数
         */
        private Integer maxIdle = 10;
        /**
         * 连接池的最大数据库连接数
         */
        private Integer maxTotal = 40;
        /**
         * 最大建立连接等待时间
         */
        private Long maxWaitMillis = -1L;
        /**
         * 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
         */
        private Integer minEvictableIdleTimeMillis = 1800000;
        /**
         * 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
         */
        private Integer numTestsPerEvictionRun = 3;
        /**
         * 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
         */
        private Integer timeBetweenEvictionRunsMillis = -1;
        /**
         * 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
         */
        private Boolean testOnBorrow = true;
        /**
         * 在空闲时检查有效性, 默认false
         */
        private Boolean testWhileIdle = false;
    }


}
