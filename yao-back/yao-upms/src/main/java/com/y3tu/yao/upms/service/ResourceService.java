package com.y3tu.yao.upms.service;

import com.y3tu.yao.feign.vo.ResourceVO;
import com.y3tu.yao.feign.vo.RoleVO;
import com.y3tu.yao.upms.model.entity.Resource;
import com.y3tu.tool.core.pojo.TreeNode;
import com.y3tu.tool.web.base.service.BaseService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author y3tu
 */
public interface ResourceService extends BaseService<Resource> {
    /**
     * 根据角色codes查询菜单树形
     *
     * @param roleCodes
     * @return
     */
    List<TreeNode<Resource>> getMenuTreeByRoleCodes(List<String> roleCodes);

    /**
     * 根据角色codes查询菜单列表
     *
     * @param roleCodes
     * @return
     */
    Set<Resource> getResourceRoleCodes(List<String> roleCodes);

    /**
     * 查询所有的资源
     *
     * @return
     */
    List<TreeNode<Resource>> getAllResourceTree();

    /**
     * 删除资源以及子资源
     *
     * @param id
     * @return
     */
    Boolean deleteResource(Integer id);

    /**
     * 根据角色code查询资源信息
     *
     * @param roleCode
     * @return
     */
    List<Resource> findResourceByRoleCode(String roleCode);

    List<String> findPermission(List<String> roles);


    /**
     * 获取当前用户的菜单资源
     *
     * @return 当前用户的菜单权限资源
     */
    List<ResourceVO> currentResource();


    /**
     * 功能描述 :根据用户ID查询资源
     *
     * @param uid
     * @return com.y3tu.yao.common.vo.sys.ResourceVO
     * @author zht
     * @date 2019/11/23
     */
    ResourceVO getResourceByUid(Integer uid);

    /**
     * 功能描述 :根据角色查询资源权限
     *
     * @param roleId 角色ID
     * @return com.y3tu.yao.upms.model.vo.sys.RoleVO
     * @author zht
     * @date 2019/11/23
     */
    RoleVO getResourceByRoleId(Integer roleId);

}
