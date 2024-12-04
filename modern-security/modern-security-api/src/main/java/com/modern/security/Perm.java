package com.modern.security;

/**
 * 权限策略上下文
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public interface Perm {

    /**
     * 鉴权前缀
     */
    String PREFIX_PERM = "perm";

    /**
     * 通用条件关键字前缀
     */
    String PREFIX_CONDITION = "cond";

}
