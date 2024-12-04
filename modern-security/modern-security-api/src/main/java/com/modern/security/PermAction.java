package com.modern.security;

/**
 * 操作
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public interface PermAction extends Perm {

    /**
     * 服务唯一编码
     */
    String getSvcCode();

    /**
     * 相关的API操作接口名称
     */
    String getActionName();

}
