package com.y3tu.yao.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.y3tu.tool.core.pojo.TreeNode;
import com.y3tu.tool.core.util.StrUtil;
import com.y3tu.tool.core.util.TreeUtil;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import com.y3tu.yao.common.enums.DataStatusEnum;
import com.y3tu.yao.common.enums.ResourceTypeEnum;
import com.y3tu.yao.feign.vo.ActionEntryVO;
import com.y3tu.yao.feign.vo.MenuVO;
import com.y3tu.yao.feign.vo.ResourceVO;
import com.y3tu.yao.feign.vo.RoleVO;
import com.y3tu.yao.upms.mapper.*;
import com.y3tu.yao.upms.model.entity.Resource;
import com.y3tu.yao.upms.model.entity.RoleActionEntry;
import com.y3tu.yao.upms.model.entity.User;
import com.y3tu.yao.upms.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.y3tu.yao.upms.constant.UpmsConstant.ROOT_MENU;
import static com.y3tu.yao.upms.constant.UpmsConstant.ResourceCheck.IS_CHECK;
import static com.y3tu.yao.upms.constant.UpmsConstant.ResourceCheck.NOT_CHECK;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-05
 */
@Service
public class ResourceServiceImpl extends BaseServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    ActionEntryMapper actionEntryMapper;

    @Autowired
    RoleActionEntryMapper roleActionEntryMapper;

    /**
     * 资源树根节点id
     */
    private final static String TREE_ROOT = "-1";

    @Override
    public List<TreeNode<Resource>> getMenuTreeByRoleCodes(List<String> roleCodes) {
        // 1、首选获取所有角色的资源集合
        Set<Resource> resources = getResourceRoleCodes(roleCodes);
        // 2、找出类型为菜单类型的 然后排序
        List<Resource> newResources = resources.stream()
                .filter(resource -> ResourceTypeEnum.MENU.getCode() == resource.getType() || ResourceTypeEnum.TOP_MENU.getCode() == resource.getType())
                .sorted(Comparator.comparingInt(Resource::getSort))
                .collect(Collectors.toList());
        // 3、构建树
        List<TreeNode<Resource>> treeNodeList = newResources.stream().map(resource -> {
            TreeNode<Resource> treeNode = new TreeNode<>(resource.getId(), resource.getName(), resource.getParentId(), resource);
            return treeNode;
        }).collect(Collectors.toList());
        return TreeUtil.buildList(treeNodeList, TREE_ROOT);
    }

    @Override
    public Set<Resource> getResourceRoleCodes(List<String> roleCodes) {
        Set<Resource> sysResources = new HashSet<>();
        roleCodes.forEach(roleCode -> {
            sysResources.addAll(resourceMapper.findResourceByRoleCode(roleCode));
        });
        return sysResources;
    }

    @Override
    public List<TreeNode<Resource>> getAllResourceTree() {
        QueryWrapper<Resource> query = new QueryWrapper();
        query.eq("del_flag", DataStatusEnum.NORMAL.getCode());
        query.orderByAsc("sort");
        List<Resource> resources = resourceMapper.selectList(query);
        List<TreeNode<Resource>> treeNodeList = resources.stream().map(resource -> {
            TreeNode<Resource> treeNode = new TreeNode<>(resource.getId(), resource.getName(), resource.getParentId(), resource);
            return treeNode;
        }).collect(Collectors.toList());
        return TreeUtil.buildList(treeNodeList, TREE_ROOT);
    }

    @Override
    public Boolean deleteResource(Integer id) {
        // 伪删除
        Resource resource = super.getById(id);
        resource.setDelFlag(DataStatusEnum.DELETE.getCode());
        super.updateById(resource);

        Resource resource1 = new Resource();
        resource.setDelFlag(DataStatusEnum.DELETE.getCode());
        UpdateWrapper<Resource> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(Resource::getParentId, resource1.getId());
        super.update(resource1, wrapper);
        return true;
    }

    /**
     * todo 使用缓存减少数据查询压力
     */
    @Override
    public List<Resource> findResourceByRoleCode(String roleCode) {
        return resourceMapper.findResourceByRoleCode(roleCode);
    }

    @Override
    public List<String> findPermission(List<String> roles) {
        List<Resource> resourcesVoList = new ArrayList<>();
        //循环遍历所有角色
        for (String role : roles) {
            //查询出每个角色对应的资源
            List<Resource> menuVos = findResourceByRoleCode(role);
            //然后放到资源集合中
            resourcesVoList.addAll(menuVos);
        }
        List<String> permissions = new ArrayList<>();
        //循环遍历资源集合
        for (Resource menuVo : resourcesVoList) {
            if (StrUtil.isNotEmpty(menuVo.getPermission())) {
                String permission = menuVo.getPermission();
                permissions.add(permission);
            }
        }
        return permissions;

    }

    @Override
    public List<ResourceVO> currentResource() {
        return null;
    }

    @Override
    public ResourceVO getResourceByUid(Integer uid) {
        //  为ResourceVO里面的用户信息赋值
        ResourceVO resourceVO = new ResourceVO();
        User user = userMapper.selectById(uid);
        resourceVO.setUsername(user.getUsername());
        resourceVO.setFullName(user.getFullName());

        // 为ResourceVO里面的role赋值(根据用户信息查出角色)
        // 根据用户id查询角色
        RoleVO roleVO = roleMapper.selectRoleByUserId(user.getId());
        if (Objects.isNull(roleVO)) {
            return resourceVO;
        }

        // 查询出所有的菜单下面的所有按钮.放到roleVo里面去 . 再查出来角色拥有的菜单和按钮,去匹配
        // 查所有菜单
        List<MenuVO> allMenus = menuMapper.selectAll();
        List<MenuVO> roleMenus = menuMapper.findMenuByRoleId(roleVO.getId());
        // 如果角色没有分配菜单 ,直接返回
        if (roleMenus.isEmpty()) {
            List<MenuVO> menuTree = buildTree(allMenus);
            //把菜单信息放到角色信息对象里
            roleVO.setMenus(menuTree);
            resourceVO.setRoleVO(roleVO);
            return resourceVO;
        }
        checkMenu(allMenus, roleMenus);
        // 查所有按钮
        List<ActionEntryVO> allActionEntrys = actionEntryMapper.selectAll();
        List<RoleActionEntry> roleActions = roleActionEntryMapper.selectByRoleId(roleVO.getId());
        checkAction(allActionEntrys, roleActions);

        // 构建返回对象
        resourceVO.setRoleVO(structureRoleVo(roleVO, allMenus, allActionEntrys));
        return resourceVO;
    }


    @Override
    public RoleVO getResourceByRoleId(Integer roleId) {
        //查询角色
        RoleVO roleVO = roleMapper.selectByPrimaryKey(roleId);
        roleMapper.selectById(roleId);

        // 查询出所有的菜单下面的所有按钮.放到roleVo里面去 . 再查出来角色拥有的菜单和按钮,去匹配
        // 查所有菜单
        List<MenuVO> allMenus = menuMapper.selectAll();
        // 查询所有按钮
        List<ActionEntryVO> allActionEntrys = actionEntryMapper.selectAll();
        if (Objects.isNull(roleVO)) {
            roleVO = new RoleVO();
            allMenus.forEach(menuVO -> menuVO.setIsCheck(NOT_CHECK));
            allActionEntrys.forEach(actionEntryVO -> actionEntryVO.setIsCheck(NOT_CHECK));
            return structureRoleVo(roleVO, allMenus, allActionEntrys);
        }
        List<MenuVO> roleMenus = menuMapper.findMenuByRoleId(roleId);


        if (roleMenus.isEmpty()) {
            return structureRoleVo(roleVO, allMenus, allActionEntrys);
        }
        // 选中菜单
        checkMenu(allMenus, roleMenus);

        // 选中按钮
        List<RoleActionEntry> roleActions = roleActionEntryMapper.selectByRoleId(roleId);
        checkAction(allActionEntrys, roleActions);

        return structureRoleVo(roleVO, allMenus, allActionEntrys);
    }

    private RoleVO structureRoleVo(RoleVO roleVO, List<MenuVO> allMenus, List<ActionEntryVO> allActionEntrys) {
        // 把菜单和按钮匹配
        matchMenuAction(allMenus, allActionEntrys);

        // 构建菜单树
        List<MenuVO> menuTree = buildTree(allMenus);
        //把菜单信息放到角色信息对象里
        roleVO.setMenus(menuTree);
        return roleVO;
    }

    /**
     * 功能描述 :选中按钮
     *
     * @param allActionEntrys 全部按钮列表
     * @param roleActions     角色拥有按钮列表
     * @return void
     * @author zht
     * @date 2019/11/23
     */
    private void checkAction(List<ActionEntryVO> allActionEntrys, List<RoleActionEntry> roleActions) {
        if (!roleActions.isEmpty()) {
            for (ActionEntryVO action : allActionEntrys) {
                for (RoleActionEntry roleAction : roleActions) {
                    if (action.getId().equals(roleAction.getActionEntryId())) {
                        action.setIsCheck(IS_CHECK);
                        break;
                    } else {
                        action.setIsCheck(NOT_CHECK);
                    }
                }
            }
        }
    }

    /**
     * 功能描述 :选中菜单
     *
     * @param allMenus  所有菜单列表
     * @param roleMenus 角色拥有菜单列表
     * @return void
     * @author zht
     * @date 2019/11/23
     */
    private void checkMenu(List<MenuVO> allMenus, List<MenuVO> roleMenus) {
        for (MenuVO menuVO : allMenus) {
            for (MenuVO roleMenu : roleMenus) {
                if (roleMenu.getId().equals(menuVO.getId())) {
                    menuVO.setIsCheck(IS_CHECK);
                    break;
                } else {
                    menuVO.setIsCheck(NOT_CHECK);
                }
            }
        }
    }

    /**
     * 功能描述 : 菜单匹配按钮
     *
     * @param menus   菜单列表
     * @param actions 按钮列表
     * @return void
     * @author zht
     * @date 2019/11/23
     */
    private void matchMenuAction(List<MenuVO> menus, List<ActionEntryVO> actions) {
        menus.forEach(menuVO -> {
            List<ActionEntryVO> actionEntrySet = new LinkedList<>();
            actions.forEach(actionEntryVO -> {
                if (actionEntryVO.getMenuId().equals(menuVO.getId())) {
                    actionEntrySet.add(actionEntryVO);
                }
                menuVO.setActionEntrySet(actionEntrySet);
            });
        });
    }

    /**
     * 功能描述 :把menuList转成树
     *
     * @param menuList
     * @return java.util.List<com.y3tu.tool.core.pojo.TreeNode                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               com.y3tu.yao.common.vo.sys.MenuVO>>
     * @author zht
     * @date 2019/11/23
     */
    private List<TreeNode<MenuVO>> getTreeNodes(List<MenuVO> menuList) {
        List<TreeNode<MenuVO>> treeNodeList = menuList.stream().map(menuVO -> new TreeNode<>(menuVO.getId().toString(),
                menuVO.getName(), menuVO.getParentId(), menuVO)).collect(Collectors.toList());
        return TreeUtil.buildList(treeNodeList, ROOT_MENU);
    }

    /**
     * 功能描述 :构建树 与上面 getTreeNodes二选一
     *
     * @param menuList
     * @return java.util.Map<java.lang.String                                                               ,                                                               com.y3tu.yao.common.vo.sys.MenuVO>
     * @author zht
     * @date 2019/11/23
     */
    private List<MenuVO> buildTree(List<MenuVO> menuList) {
        List<MenuVO> treeList = new LinkedList<>();
        for (MenuVO treeNode : menuList) {
            if (ROOT_MENU.equals(treeNode.getParentId())) {
                treeList.add(treeNode);
            }
            for (MenuVO menu : menuList) {
                if (!Objects.isNull(menu.getParentId()) && menu.getParentId().equals(treeNode.getId().toString())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new LinkedList<>());
                    }
                    treeNode.add(menu);
                }
            }
        }
        return treeList;
    }
}
