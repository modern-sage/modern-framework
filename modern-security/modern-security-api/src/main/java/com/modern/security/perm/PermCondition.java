package com.modern.security.perm;

/**
 * 条件
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public interface PermCondition extends Perm {

    /**
     * 服务唯一编码
     */
    String getSvcCode();

    /**
     * 账号ID
     */
    String getAccountId();

    /**
     * 与服务相关的资源描述部分。这部分的格式支持树状结构（类似文件路径）。
     */
    String getRelativeId();
}
