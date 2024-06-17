package ${package.Service}.base;

import ${superServiceClassPackage};

/**
* <p>
* 服务接口父类
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
interface ICustomService<T> : ${superServiceClass}<T>
<#else>
public interface ICustomService<T> extends ${superServiceClass}<T> {

}
</#if>