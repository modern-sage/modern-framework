package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Service}.base.ICustomService;

/**
* <p>
* ${table.comment!} 服务类
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
interface ${table.serviceName} : ICustomService<${entity}>
<#else>
public interface ${table.serviceName} extends ICustomService<${entity}> {

}
</#if>