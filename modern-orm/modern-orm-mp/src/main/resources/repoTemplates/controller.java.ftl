package ${package.Controller};

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.modern.base.mvc.Rs;
import com.modern.base.mvc.PageRec;
import com.modern.base.criteria.GrateParam;
import java.util.List;

/**
* <p>
* ${table.comment!} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@RequiredArgsConstructor
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    </#if>

    /**
    * ${table.comment!} service组件
    */
    private final ${table.serviceName} targetService;

    /**
     * 查询${table.comment}实体全部数据
     */
    @GetMapping
    public Rs<List<${entity}>> list(){
        return Rs.ok(targetService.list());
    }

    /**
     * 根据ID查询${table.comment}实体
     * @param id 实体主键
     */
    @GetMapping("/{id}")
    public Rs<${entity}> get(@PathVariable Long id){
        return Rs.ok(targetService.getById(id));
    }

    /**
     * 新增${table.comment}实体数据
     * @param entity 实体对象
     */
    @PostMapping(value = "/create")
    public Rs<${entity}> save(@RequestBody ${entity} entity){
        if(targetService.save(entity)) {
            return Rs.ok(entity);
        }else {
            return Rs.fail("创建对象失败");
        }
    }

    /**
     * 更新${table.comment}实体数据
     * @param id 实体主键
     * @param entity 实体对象
     */
    @PostMapping(value = "/modify/{id}")
    public Rs<${entity}> update(@PathVariable Long id,@RequestBody ${entity} entity){
    <#list table.fields as field>
        <#--乐观锁处理-->
        <#if field.versionField>
        Assert.notNull(entity.getVersion(),() -> new FlineBizException("update entity must needs version code"));
        </#if>
    </#list>
        if (targetService.updateById(entity.setId(id))) {
            return Rs.ok(entity);
        }else {
            return Rs.fail("更新对象失败");
        }
    }

    /**
     * 删除${table.comment}实体数据
     * @param id 实体主键
     */
    @PostMapping(value = "/remove/{id}")
    public Rs<Boolean> delete(@PathVariable Long id){
         return Rs.ok(targetService.removeById(id));
    }

     /**
      * 根据查询条件查询${table.comment}实体数据，结果分页显示，默认根据更新时间逆序排序
      * @param param 查询统一参数格式
      */
     @PostMapping(value = "/page")
     public Rs<PageRec<${entity}>> page(@RequestBody GrateParam<${entity}> param ){
          return Rs.ok(targetService.page(param));
     }
}
</#if>
