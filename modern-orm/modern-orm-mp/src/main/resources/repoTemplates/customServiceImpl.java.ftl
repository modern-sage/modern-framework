package ${package.Service}.base;

import ${superMapperClassPackage};
import ${superServiceImplClassPackage};

/**
* <p>
* 服务接口实现类父类
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
open class CustomServiceImpl<M extends ${superMapperClass}<T>, T> : ${superServiceImplClass}<M, T>(), ${table.serviceName} {

}
<#else>
public class CustomServiceImpl<M extends ${superMapperClass}<T>, T> extends ${superServiceImplClass}<M, T> implements ICustomService<T>{

}
</#if>