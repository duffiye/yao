package com.y3tu.yao.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.tool.core.date.DateUtil;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.yao.common.constants.CommonConstants;
import com.y3tu.yao.common.enums.DataStatusEnum;
import com.y3tu.yao.feign.vo.ResourceVO;
import com.y3tu.yao.log.starter.annotation.Log;
import com.y3tu.yao.log.starter.constant.ActionTypeEnum;
import com.y3tu.yao.upms.model.entity.Resource;
import com.y3tu.yao.upms.model.entity.Role;
import com.y3tu.yao.upms.model.entity.RoleResource;
import com.y3tu.yao.upms.service.ResourceService;
import com.y3tu.yao.upms.service.RoleResourceService;
import com.y3tu.yao.upms.service.UserRoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RoleResourceService roleResourceService;

    /**
     * 获取所有的菜单树
     *
     * @return
     */
    @Log(actionName = "获取所有的菜单树")
    @ApiOperation(value = "获取当前用户的菜单树", notes = "获取所有的菜单树", httpMethod = "GET")
    @GetMapping("/menu/getAllMenuTree")
    public R getAllMenuTree() {
        return R.success(resourceService.getAllResourceTree());
    }

    /**
     * 添加权限
     *
     * @param resource
     * @return
     */
    @PostMapping(value = "/save")
    @Log(actionName = "新增资源", actionType = ActionTypeEnum.ADD)
    public R save(@RequestBody Resource resource) {
        // 判断拦截请求的操作权限按钮名是否已存在
        if (CommonConstants.PERMISSION_OPERATION.equals(resource.getType())) {
            List<Resource> list = resourceService.list(new QueryWrapper<Resource>().eq("name", resource.getName()));
            if (list != null && list.size() > 0) {
                return R.warn("名称已存在");
            }
        }
        resource.setCreateTime(DateUtil.date());
        resource.setUpdateTime(DateUtil.date());
        resource.setDelFlag(DataStatusEnum.NORMAL.getCode());
        resourceService.save(resource);
        return R.success(resource);
    }

    /**
     * 更新权限
     *
     * @param resource
     * @return
     */
    @PostMapping(value = "/update")
    @Log(actionName = "更新资源", actionType = ActionTypeEnum.EDIT)
    public R update(@RequestBody Resource resource) {
        // 判断拦截请求的操作权限按钮名是否已存在
        if (CommonConstants.PERMISSION_OPERATION.equals(resource.getType())) {
            // 若名称修改
            Resource p = resourceService.getById(resource.getId());
            if (!p.getName().equals(resource.getName())) {
                List<Resource> list;
                list = resourceService.list(new QueryWrapper<Resource>().eq("name", resource.getName()));
                if (list != null && list.size() > 0) {
                    return R.warn("名称已存在");
                }
            }
        }
        resource.setUpdateTime(DateUtil.date());
        resourceService.updateById(resource);
        return R.success();
    }

    /**
     * 批量删除权限
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量通过ids删除")
    @DeleteMapping(value = "/delByIds/{ids}")
    @Log(actionName = "删除资源", actionType = ActionTypeEnum.DELETE)
    public R delByIds(@PathVariable String[] ids) {
        for (String id : ids) {
            List<RoleResource> list = roleResourceService.list(new QueryWrapper<RoleResource>().eq("resource_id", id));
            if (list != null && list.size() > 0) {
                return R.warn("删除失败，包含正被角色使用关联的菜单或权限");
            }
        }
        for (String id : ids) {
            resourceService.removeById(id);
        }
        return R.success();
    }

    /**
     * 内部服务调用 可以不返回R
     * 根据角色查询资源信息 可添加缓存设置
     *
     * @param roleCode
     */
    @ApiOperation(value = "根据角色查询资源信息", notes = "根据角色查询资源信息", httpMethod = "GET")
    @ApiImplicitParam(name = "roleCode", value = "角色code", required = true, dataType = "string")
    @GetMapping("/role/{roleCode}")
    public Set<ResourceVO> listResourceByRole(@PathVariable("roleCode") String roleCode) {

        List<Resource> resources = resourceService.findResourceByRoleCode(roleCode);
        return buildResourceVO(resources);
    }

    /**
     * 内部服务调用 可以不返回R
     * 查询所有资源信息 可添加缓存设置
     *
     * @return
     */
    @GetMapping("/listAllResource")
    public Set<ResourceVO> listAllResource() {
        List<Resource> resources = resourceService.list(new QueryWrapper<Resource>().eq("del_flag", DataStatusEnum.NORMAL.getCode()));
        return buildResourceVO(resources);
    }

    /**
     * List<Resource>转换为Set<ResourceVO>
     *
     * @param resources
     * @return
     */
    private Set<ResourceVO> buildResourceVO(List<Resource> resources) {
        Set<ResourceVO> resourceVOS = new HashSet<>();
        resources.stream().forEach(resource -> {
            ResourceVO resourceVO = new ResourceVO();
            BeanUtils.copyProperties(resource, resourceVO);
            resourceVOS.add(resourceVO);
        });
        return resourceVOS;
    }
}
