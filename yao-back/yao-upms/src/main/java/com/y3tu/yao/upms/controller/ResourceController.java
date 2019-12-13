package com.y3tu.yao.upms.controller;

import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.yao.feign.constant.ServerNameConstants;
import com.y3tu.yao.feign.vo.MenuVO;
import com.y3tu.yao.log.starter.annotation.Log;
import com.y3tu.yao.upms.model.entity.Resource;
import com.y3tu.yao.upms.service.MenuService;
import com.y3tu.yao.upms.service.ResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date 2018-08-05
 */
@RestController
@RequestMapping("/resource")
@Log(serverName = ServerNameConstants.UPMS_SERVER, moduleName = "资源管理")
public class ResourceController extends BaseController<ResourceService, Resource> {

    @Autowired
    ResourceService resourceService;

    @Autowired
    MenuService menuService;

    @Autowired
    HttpServletRequest request;

    /**
     * 当前登录用户的菜单权限资源
     *
     * @return
     */
    @RequestMapping(path = "/current", method = {RequestMethod.GET, RequestMethod.POST})
    public R currentResource() {

        return R.success(resourceService.currentResource());
    }

    /**
     * 功能描述 :根据角色id获取
     *
     * @param roleId 角色ID
     * @return com.y3tu.tool.core.pojo.R
     * @author zht
     * @date 2019/11/23
     */
    @GetMapping("/{role_id}")
    public R roleResource(@PathVariable("role_id") Integer roleId) {
        return R.success(resourceService.getResourceByRoleId(roleId));
    }

    /**
     * 内部服务调用 可以不返回R
     * 根据角色查询资源信息 可添加缓存设置
     *
     * @param roleCode
     */
    @GetMapping("/role/{roleCode}")
    public Set<MenuVO> listResourceByRole(@PathVariable("roleCode") String roleCode) {
        List<MenuVO> menus = menuService.findMenuByRoleCode(roleCode);
        return buildResourceVO(menus);
    }

    /**
     * 内部服务调用 可以不返回R
     * 查询所有资源信息 可添加缓存设置
     *
     * @return
     */
    @GetMapping("/list")
    public Set<MenuVO> listAllResource() {
        List<MenuVO> menus = menuService.list();
        return buildResourceVO(menus);
    }

    /**
     * List<Resource>转换为Set<ResourceVO>
     *
     * @param resources
     * @return
     */
    private Set<MenuVO> buildResourceVO(List<MenuVO> resources) {
        Set<MenuVO> menuVOS = new HashSet<>();
        resources.stream().forEach(menus -> {
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(menus, menuVO);
            menuVOS.add(menuVO);
        });
        return menuVOS;
    }

}
