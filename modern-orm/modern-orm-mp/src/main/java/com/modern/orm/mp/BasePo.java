package com.modern.orm.mp;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 基础Po
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class BasePo<T extends BasePo<T>> extends IdPo<T> {

    /**
     * 记录生成人的ID
     */
    @TableField(value = "creator_id", fill = FieldFill.INSERT)
    @Getter
    protected Long creatorId;
    /**
     * 最后一个更新人的ID
     */
    @TableField(value = "updater_id", fill = FieldFill.INSERT_UPDATE)
    @Getter
    protected Long updaterId;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Getter
    protected LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @Getter
    protected LocalDateTime updateTime;

    /**
     * 版本号
     */
    @TableField(value = "version", fill = FieldFill.INSERT)
    @Version
    @Getter
    protected Integer version;
    /**
     * 逻辑删除标志，状态1表示记录被删除，0表示正常记录
     */
    @TableField(value = "delete_flag", fill = FieldFill.INSERT)
    @TableLogic
    @Getter
    protected Integer deleteFlag;

    /**
     * 当前实体创建时间
     *
     * @param createTime 时间值
     */
    public T setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return (T) this;
    }

    /**
     * 当前实体更新时间
     *
     * @param updateTime 时间值
     */
    public T setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return (T) this;
    }

    /**
     * 设置记录创建者ID
     */
    public T setCreatorUserId(Long creatorId) {
        this.creatorId = creatorId;
        return (T) this;
    }

    /**
     * 设置记录更新者ID
     */
    public T setUpdaterUserId(Long updaterId) {
        this.updaterId = updaterId;
        return (T) this;
    }

    /**
     * 当前实体乐观锁版本
     *
     * @param version 版本值
     */
    public T setVersion(Integer version) {
        this.version = version;
        return (T) this;
    }

    /**
     * 设置当前实体逻辑删除标志
     *
     * @param deleteFlag 删除标志
     */
    public T setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return (T) this;
    }

}
