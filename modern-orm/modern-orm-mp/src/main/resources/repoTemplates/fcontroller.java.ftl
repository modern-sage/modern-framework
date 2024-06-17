package ${package.Controller};

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
<#if swagger>
import io.swagger.annotations.*;
</#if>
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import com.fline.tp.tools.core.lang.Assert;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.fline.tp.core.ResponseResult;
import com.fline.tp.core.exception.FlineBizException;
import com.fline.tp.repo.wrapper.PageRec;
import com.fline.tp.repo.wrapper.FlineParam;
import java.util.List;

/**
* <p>
* ${table.comment!} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if swagger>
@Api(tags="${table.comment!} 实体API接口")
</#if>
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
    @ApiOperation(value = "查询${table.comment}实体全部数据",notes = "查询${table.comment}实体全部数据", response = ${entity}.class)
    @GetMapping
    public ResponseResult<List<${entity}>> list(){
        return ResponseResult.ok(targetService.list());
    }

    /**
     * 根据ID查询${table.comment}实体
     * @param id 实体主键
     */
    @ApiOperation(value = "根据ID查询${table.comment}实体",notes = "根据ID查询${table.comment}实体", response = ${entity}.class)
    @GetMapping("/{id}")
    public ResponseResult<${entity}> get(@ApiParam(name = "id",value = "${table.comment}实体主键") @PathVariable Long id){
        return ResponseResult.ok(targetService.getById(id));
    }

    /**
     * 新增${table.comment}实体数据
     * @param entity 实体对象
     */
    @ApiOperation(value = "新增${table.comment}实体数据",notes = "新增${table.comment}实体数据", response = ${entity}.class)
    @PostMapping(value = "/create")
    public ResponseResult<${entity}> save(@ApiParam(name = "entity",value = "${table.comment}实体对象") @RequestBody ${entity} entity){
        if(targetService.save(entity)) {
            return ResponseResult.ok(entity);
        }else {
            return ResponseResult.failed("创建对象失败");
        }
    }

    /**
     * 更新${table.comment}实体数据
     * @param id 实体主键
     * @param entity 实体对象
     */
    @ApiOperation(value = "更新${table.comment}实体数据",notes = "更新${table.comment}实体数据", response = ${entity}.class)
    @PostMapping(value = "/modify/{id}")
    public ResponseResult<${entity}> update(@ApiParam(name = "id",value = "${table.comment}实体主键")
                                            @PathVariable Long id,
                                            @ApiParam(name = "entity",value = "${table.comment}实体对象")
                                            @RequestBody ${entity} entity){
    <#list table.fields as field>
        <#--乐观锁处理-->
        <#if field.versionField>
        Assert.notNull(entity.getVersion(),() -> new FlineBizException("update entity must needs version code"));
        </#if>
    </#list>
        if (targetService.updateById(entity.setId(id))) {
            return ResponseResult.ok(entity);
        }else {
            return ResponseResult.failed("更新对象失败");
        }
    }

    /**
     * 删除${table.comment}实体数据
     * @param id 实体主键
     */
    @ApiOperation(value = "删除${table.comment}实体数据",notes = "删除${table.comment}实体数据", response = Boolean.class)
    @PostMapping(value = "/remove/{id}")
    public ResponseResult<Boolean> delete(@ApiParam(name = "id",value = "${table.comment}实体主键") @PathVariable Long id){
         return ResponseResult.ok(targetService.removeById(id));
    }

     /**
      * 根据查询条件查询${table.comment}实体数据，结果分页显示，默认根据更新时间逆序排序
      * @param param 查询统一参数格式
      */
     @ApiOperation(value = "根据查询条件查询${table.comment}实体数据，结果分页显示，默认根据更新时间逆序排序",notes = "根据查询条件查询${table.comment}实体数据，结果分页显示，默认根据更新时间逆序排序", response = Boolean.class)
     @PostMapping(value = "/page")
     public ResponseResult<PageRec<${entity}>> page(@ApiParam(name = "param",value = "${table.comment}实体查询条件")
                                                    @RequestBody FlineParam<${entity}> param ){
          return ResponseResult.ok(targetService.page(param));
     }
}
</#if>