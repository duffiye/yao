package com.y3tu.yao.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.tool.core.date.DateUtil;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.tool.web.base.pojo.PageInfo;
import com.y3tu.yao.common.enums.DataStatusEnum;
import com.y3tu.yao.feign.constant.ServerNameConstants;
import com.y3tu.yao.feign.vo.RoleVO;
import com.y3tu.yao.log.starter.annotation.Log;
import com.y3tu.yao.log.starter.constant.ActionTypeEnum;
import com.y3tu.yao.upms.model.entity.*;
import com.y3tu.yao.upms.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/role")
@Log(serverName = ServerNameConstants.UPMS_SERVER, moduleName = "角色管理")
public class RoleController extends BaseController<RoleService, Role> {

    @Autowired
    RoleService roleService;
    @Autowired
    private RoleResourceService roleResourceService;
    @Autowired
    private RoleDepartmentService roleDepartmentService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "分页获取角色")
    @PostMapping("/page")
    @Log(actionName = "查询角色分页数据")
    @Override
    public R page(@RequestBody PageInfo pageInfo) {
        PageInfo<Role> page = service.page(pageInfo);
        for (Role role : page.getRecords()) {
            //角色拥有权限
            List<RoleResource> roleResources = roleResourceService.list(new QueryWrapper<RoleResource>().eq("role_id", role.getId()));
            if (roleResources.size() > 0) {
                Collection<Resource> resources = resourceService.listByIds(roleResources.stream().map(roleResource -> roleResource.getResourceId()).collect(Collectors.toList()));
                role.setResources((List<Resource>) resources);
            }
            //角色所属部门
            List<RoleDepartment> roleDepartments = roleDepartmentService.list(new QueryWrapper<RoleDepartment>().eq("role_id", role.getId()));
            if (roleDepartments.size() > 0) {
                Collection<Department> departments = departmentService.listByIds(roleDepartments.stream().map(roleDepartment -> roleDepartment.getDepartmentId()).collect(Collectors.toList()));
                role.setDepartments((List<Department>) departments);
            }
        }
        return R.success(pageInfo);
    }

    @RequestMapping(value = "/setDefault", method = RequestMethod.POST)
    @Log(actionName = "设置或取消默认角色", actionType = ActionTypeEnum.EDIT)
    @ApiOperation(value = "设置或取消默认角色")
    public R setDefault(@RequestParam String id, @RequestParam Boolean isDefault) {

        Role role = roleService.getById(id);
        if (role == null) {
            return R.warn("角色不存在");
        }
        role.setDefaultRole(isDefault);
        roleService.updateById(role);
        return R.success("设置成功");
    }

    /**
     * 保存数据
     *
     * @param role 保存的数据
     * @return
     */
    @ApiOperation(value = "保存", httpMethod = "POST")
    @PostMapping("/save")
    @Log(actionName = "新增角色", actionType = ActionTypeEnum.ADD)
    @Override
    public R save(@RequestBody Role role) {
        role.setCreateTime(DateUtil.date());
        role.setUpdateTime(DateUtil.date());
        role.setDelFlag(DataStatusEnum.NORMAL.getCode());
        roleService.save(role);
        return R.success("保存成功!");
    }

    /**
     * 更新数据
     *
     * @param role 更新的数据
     * @return
     */
    @ApiOperation(value = "更新", httpMethod = "PUT")
    @PutMapping("/update")
    @Log(actionName = "更新角色", actionType = ActionTypeEnum.EDIT)
    @Override
    public R update(@RequestBody Role role) {
        role.setUpdateTime(DateUtil.date());
        roleService.updateById(role);
        return R.success("更新成功!");
    }


    @RequestMapping(value = "/editRoleResource", method = RequestMethod.POST)
    @Log(actionName = "编辑角色分配菜单权限", actionType = ActionTypeEnum.EDIT)
    @ApiOperation(value = "编辑角色分配菜单权限")
    public R editRoleResource(@RequestBody Map params) {

        String roleId = (String) params.get("roleId");
        List<String> resourceIds = (List<String>) params.get("resourceIds");

        //删除其关联权限
        roleResourceService.remove(new QueryWrapper<RoleResource>().eq("role_id", roleId));
        //分配新权限
        for (String resourceId : resourceIds) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceId);
            roleResourceService.save(roleResource);
        }
        return R.success();
    }

    @RequestMapping(value = "/editRoleDepartment", method = RequestMethod.POST)
    @Log(actionName = "编辑角色分配数据权限", actionType = ActionTypeEnum.EDIT)
    @ApiOperation(value = "编辑角色分配数据权限")
    public R editRoleDepartment(@RequestBody Map params) {

        String roleId = (String) params.get("roleId");
        int dataType = (int) params.get("dataType");
        List<String> departmentIds = (List<String>) params.get("departmentIds");

        Role r = roleService.getById(roleId);
        r.setDataType(dataType);
        roleService.updateById(r);
        // 删除其关联数据权限
        roleDepartmentService.remove(new QueryWrapper<RoleDepartment>().eq("role_id", roleId));
        // 分配新数据权限
        for (String departmentId : departmentIds) {
            RoleDepartment roleDepartment = new RoleDepartment();
            roleDepartment.setRoleId(roleId);
            roleDepartment.setDepartmentId(departmentId);
            roleDepartmentService.save(roleDepartment);
        }
        return R.success();
    }

    @ApiOperation(value = "批量通过ids删除")
    @DeleteMapping(value = "/delByIds/{ids}")
    @Log(actionName = "删除角色", actionType = ActionTypeEnum.DELETE)
    @Override
    public R delByIds(@PathVariable String[] ids) {
        for (String id : ids) {
            List<UserRole> list = userRoleService.list(new QueryWrapper<UserRole>().eq("role_id", id));
            if (list != null && list.size() > 0) {
                return R.warn("删除失败，包含正被用户使用关联的角色");
            }
        }

        for (String id : ids) {
            roleService.removeById(id);
            //删除关联菜单权限
            roleResourceService.remove(new QueryWrapper<RoleResource>().eq("role_id", id));
            //删除关联部门数据
            roleDepartmentService.remove(new QueryWrapper<RoleDepartment>().eq("role_id", id));
        }
        return super.delByIds(ids);
    }


    /**
     *功能描述 :服务内部调用 根据用户ID查询角色信息
     * @author zht
     * @date 2019/12/13
     * @param userId
     * @return com.y3tu.yao.feign.vo.RoleVO
     */
    @GetMapping(value = "/{userId}")
    public RoleVO getRoleVoByUserId(@PathVariable(value = "userId") String userId) {
        return userRoleService.findRoleVoByUserId(userId);
    }

}
