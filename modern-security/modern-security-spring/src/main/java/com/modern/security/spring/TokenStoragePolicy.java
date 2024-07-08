package com.modern.security.spring;

/**
 * 权限存储策略
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public enum TokenStoragePolicy {

    useMemory,
    useRedis,
    useDataBase

}
