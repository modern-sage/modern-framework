package ${package.Service}.base;

import com.fline.tp.repo.wrapper.base.biz.FlineBizService;
import com.fline.tp.tools.core.util.StrUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
* <p>
* 业务服务抽象类父类
* </p>
*
* @author ${author}
* @since ${date}
*/
@Data
<#if kotlin>
open class AbstractBizService : FlineBizService(), IBizService {

}
<#else>
public abstract class AbstractBizService extends FlineBizService implements IBizService{

    @Autowired
    private Map<String,ICustomService> customServiceMap;
    /**
    * 获取对应实体的服务接口
    *
    * @param tClass
    * @return
    */
    @Override
    public <T> ICustomService<T> getCustomService(Class<T> tClass) {
        for (String key : customServiceMap.keySet()) {
            if (StrUtil.equalsIgnoreCase(tClass.getSimpleName() + suffix, key)) {
                return customServiceMap.get(key);
            }
        }
        return null;
    }
}
</#if>