package com.modernframework.base.service;



import com.modernframework.base.PageRec;
import com.modernframework.base.criteria.GrateParam;

import java.util.List;

/**
 * 业务层Service父接口，该接口实现同时对多个 IBaseService 操作
 *
 * @author zj
 *@since 1.0.0
 */
public interface BaseBizService {

    /**
     * 保存实体对象
     *
     * @param po 持久化对象
     * @return boolean
     */
    boolean savePo(Object po);

    /**
     * 更新对象
     *
     * @param po 持久化对象
     * @return boolean
     */
    boolean updatePo(Object po);

    /**
     * 根据对象类型和主键获取对象
     *
     * @param id     主键
     * @param tClass 对象类型
     * @return T
     */
    <T> T getPo(Long id, Class<T> tClass);

    /**
     * 根据对象类型和主键删除对象
     *
     * @param id     主键
     * @param tClass 对象类型
     * @return boolean
     */
    <T> boolean removePo(Long id, Class<T> tClass);

    /**
     * 根据类型获取对象集合
     *
     * @param tClass 查询类型
     * @return List
     */
    <T> List<T> listPos(Class<T> tClass);

    /**
     * 根据类型和查询条件获取分页查询结果
     *
     * @param param  查询参数
     * @param tClass 查询类型
     * @return PageRecord
     */
    <T> PageRec<T> pagePos(GrateParam<T> param, Class<T> tClass);

    /**
     * 根据类型和查询条件获取集合查询结果
     *
     * @param param  查询参数
     * @param tClass 查询类型
     * @return List
     */
    <T> List<T> listPos(GrateParam<T> param, Class<T> tClass);

    /**
     * 根据查询条件获取单条查询结果
     *
     * @param param  查询参数
     * @param tClass 对象类型
     * @return oneDo
     */
    <T> T onePo(GrateParam<T> param, Class<T> tClass);

    /**
     * 根据对象类型获取相应的对象业务service
     *
     * @param tClass 对象类型
     * @return IBaseService
     */
    <T> BaseService<T> getService(Class<T> tClass);

    /**
     * 根据类型获取相应的查询条件生成方法
     *
     * @param tClass 数据类型
     * @return QueryParam
     */
    <T> GrateParam<T> where(Class<T> tClass);
}
