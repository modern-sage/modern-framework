package com.modernframework.base.service;



import com.modernframework.base.PageRec;
import com.modernframework.base.criteria.GrateParam;
import com.modernframework.core.lang.Asserts;
import com.modernframework.core.utils.CollectionUtils;
import com.modernframework.core.utils.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * BO表示业务对象 业务Service组件父类，填充所有单实体操作组件Service
 *
 * @author zj
 *@since 1.0.0
 */
public abstract class AbstractBaseBizService implements BaseBizService {

    /**
     * 包含所有实体的单体操作组件service
     */
//    @Autowired
//    protected Map<String, BaseService> serviceMap;

    protected abstract Map<String, BaseService> getServiceMap();

    /**
     * 保存实体
     *
     * @param po 实体对象
     * @return Boolean
     */
    @Override
    public boolean savePo(Object po) {
        BaseService service = getService(po.getClass());
        if (service != null) {
            return service.save(po);
        }
        return false;
    }

    /**
     * 更新实体
     *
     * @param po 实体对象
     * @return Boolean
     */
    @Override
    public boolean updatePo(Object po) {
        BaseService service = getService(po.getClass());
        if (service != null) {
            return service.updateById(po);
        }
        return false;
    }

    /**
     * 根据主键获取实体对象
     *
     * @param id     主键
     * @param tClass 实体类型
     * @param <T>    对象类型
     * @return T
     */
    @Override
    public <T> T getPo(Long id, Class<T> tClass) {
        BaseService service = getService(tClass);
        if (service != null) {
            return (T) service.getById(id);
        }
        return null;
    }

    /**
     * 获取集合列表
     *
     * @param tClass 实体类型
     * @param <T>    对象类型
     * @return List<T>
     */
    @Override
    public <T> List<T> listPos(Class<T> tClass) {
        BaseService service = getService(tClass);
        if (service != null) {
            return service.list();
        }
        return Collections.emptyList();
    }

    /**
     * 分页查询方法
     *
     * @param param  查询条件
     * @param tClass 查询对象类型
     * @param <T>    实体类型
     * @return PageRec<T>
     */
    @Override
    public <T> PageRec<T> pagePos(GrateParam<T> param, Class<T> tClass) {
        BaseService service = getService(tClass);
        if (service != null) {
            return service.page(param);
        }
        return new PageRec<>();
    }

    /**
     * 根据实体类型获取单实体service组件，若有自定义
     *
     * @param tClass 实体类型
     * @param <T>    实体类型
     * @return IBaseService<T>
     */
    @Override
    public <T> BaseService<T> getService(Class<T> tClass) {
        BaseService<T> service = service(getServiceMap(), tClass);
        Asserts.notNull(service, () -> {throw new IllegalStateException(
                StringUtils.format("entity [{}] service not found.", tClass.getSimpleName()));});
        return service;
    }

    /**
     * 根据实体类型获取crud组件
     * 静态方法，全局都可以调用，待优化
     *
     * @param serviceMap crud组件集合
     * @param tClass     实体类型
     * @param <T>        实体类型
     * @return IBaseService<T>
     */
    public static <T> BaseService<T> service(Map<String, BaseService> serviceMap, Class<T> tClass) {
        for (String key : serviceMap.keySet()) {
            if (StringUtils.equalsIgnoreCase(tClass.getSimpleName() + "Service", key)
                    || StringUtils.equalsIgnoreCase(tClass.getSimpleName() + "ServiceImpl", key)) {
                return serviceMap.get(key);
            }
        }
        return null;
    }

    /**
     * 根据对象类型和主键删除对象
     *
     * @param id     主键
     * @param tClass 对象类型
     * @return Boolean
     */
    @Override
    public <T> boolean removePo(Long id, Class<T> tClass) {
        BaseService service = getService(tClass);
        if (service != null) {
            return service.removeById(id);
        }
        return false;
    }

    /**
     * 根据类型获取相应的查询条件生成方法
     *
     * @param tClass 数据类型
     * @param <T>    实体类型
     * @return QueryParam<T>
     */
    @Override
    public <T> GrateParam<T> where(Class<T> tClass) {
        BaseService<T> baseService = getService(tClass);
        if (baseService != null) {
            return baseService.where();
        }
        return null;
    }

    /**
     * 根据类型和查询条件获取集合查询结果
     *
     * @param param  查询参数
     * @param tClass 查询类型
     * @return List<T>
     */
    @Override
    public <T> List<T> listPos(GrateParam<T> param, Class<T> tClass) {
        return pagePos(param.setPageNumber(1).setPageSize(Integer.MAX_VALUE), tClass).getRecords();
    }

    /**
     * 根据查询条件获取单条查询结果
     *
     * @param param  查询参数
     * @param tClass 对象类型
     * @return T
     */
    @Override
    public <T> T onePo(GrateParam<T> param, Class<T> tClass) {
        List<T> ts = listPos(param, tClass);
        if (CollectionUtils.isEmpty(ts)) {
            return null;
        } else {
            return ts.get(0);
        }
    }
}
