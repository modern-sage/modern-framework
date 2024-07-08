package com.modern.orm.mp;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 做关联的模型
 *
 * @param <T>
 * @author zhangj
 */
public class LinkPo<T extends LinkPo<T>> extends IdPo<T> {

    /**
     * 记录生成人的ID
     */
    @TableField(value = "creator", fill = FieldFill.INSERT)
    @Getter
    protected Long creator;
    /**
     * 最后一个更新人的ID
     */
    @TableField(value = "updater", fill = FieldFill.INSERT_UPDATE)
    @Getter
    protected Long updater;
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
    public T setCreator(Long creator) {
        this.creator = creator;
        return (T) this;
    }

    /**
     * 设置记录更新者ID
     */
    public T setUpdater(Long updater) {
        this.updater = updater;
        return (T) this;
    }

}
