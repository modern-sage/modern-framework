package com.modern.data;


/**
 * 表结构配置
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class SchemaProps {

    /**
     * 是否逻辑删
     */
    private boolean deleteLogical = true;

    /**
     * 是否支持版本
     */
    boolean versionSupport = true;

    /**
     * 表说明
     */
    private String comment;

    public SchemaProps() {
    }

    public boolean isDeleteLogical() {
        return deleteLogical;
    }

    public void setDeleteLogical(boolean deleteLogical) {
        this.deleteLogical = deleteLogical;
    }

    public boolean isVersionSupport() {
        return versionSupport;
    }

    public void setVersionSupport(boolean versionSupport) {
        this.versionSupport = versionSupport;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
