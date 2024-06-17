package com.modern.orm.mp;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 包含其他不同场景中的模型的业务属性的全属性模型
 *
 * @param <T>
 * @author zhangj
 */
public class BaseBizPo<T extends BaseBizPo<T>> extends IdPo<T> {

    /**
     * 名称
     */
    @Getter
    protected String name;
    /**
     * 编码
     */
    @Getter
    protected String code;
    /**
     * 描述
     */
    @Getter
    protected String memo;
    /**
     * 记录的业务状态
     */
    @TableField(value = "status", fill = FieldFill.INSERT)
    @Getter
    protected Integer status;

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
     * 设置记录名称
     *
     * @param name 记录名称
     * @return
     */
    public T setName(String name) {
        this.name = name;
        return (T) this;
    }

    /**
     * 设置记录编码
     *
     * @param code 记录编码
     * @return
     */
    public T setCode(String code) {
        this.code = code;
        return (T) this;
    }

    /**
     * 设置当前实体描述信息
     *
     * @param memo
     * @return
     */
    public T setMemo(String memo) {
        this.memo = memo;
        return (T) this;
    }

    /**
     * 设置当前实体业务状态
     *
     * @param status 业务状态值
     * @return
     */
    public T setStatus(Integer status) {
        this.status = status;
        return (T) this;
    }

    /**
     * 当前实体创建时间
     *
     * @param createTime 时间值
     * @return
     */
    public T setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return (T) this;
    }

    /**
     * 当前实体更新时间
     *
     * @param updateTime 时间值
     * @return
     */
    public T setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return (T) this;
    }

    /**
     * 设置记录创建者ID
     *
     * @param creatorId
     * @return T
     */
    public T setCreatorUserId(Long creatorId) {
        this.creatorId = creatorId;
        return (T) this;
    }

    /**
     * 设置记录更新者ID
     *
     * @param updaterId
     * @return T
     */
    public T setUpdaterUserId(Long updaterId) {
        this.updaterId = updaterId;
        return (T) this;
    }

    /**
     * 当前实体乐观锁版本
     *
     * @param version 版本值
     * @return
     */
    public T setVersion(Integer version) {
        this.version = version;
        return (T) this;
    }

    /**
     * 设置当前实体逻辑删除标志
     *
     * @param deleteFlag 删除标志
     * @return
     */
    public T setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return (T) this;
    }

}
