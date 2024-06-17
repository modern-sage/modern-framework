package ${package.Service}.base;

import com.fline.tp.repo.wrapper.base.biz.IBizLayerService;

/**
* <p>
* 业务服务接口父类
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
interface IBizService : IBizLayerService {
<#else>
public interface IBizService extends IBizLayerService {

    /**
    * 获取对应实体的服务接口
    * @param tClass
    * @return
    * @param <T>
    */
    <T>ICustomService<T> getCustomService(Class<T> tClass);
}
</#if>