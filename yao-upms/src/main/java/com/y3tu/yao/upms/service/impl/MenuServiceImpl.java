package com.y3tu.yao.upms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.y3tu.tool.core.exception.BusinessException;
import com.y3tu.yao.feign.vo.MenuVO;
import com.y3tu.yao.upms.mapper.ActionEntryMapper;
import com.y3tu.yao.upms.mapper.MenuMapper;
import com.y3tu.yao.upms.mapper.RoleMenuMapper;
import com.y3tu.yao.upms.model.entity.RoleMenu;
import com.y3tu.yao.upms.model.entity.SysMenu;
import com.y3tu.yao.upms.model.query.menu.MenuAddQuery;
import com.y3tu.yao.upms.model.query.menu.MenuQuery;
import com.y3tu.yao.upms.model.query.menu.MenuUpdateQuery;
import com.y3tu.yao.upms.model.vo.MenuQueryVO;
import com.y3tu.yao.upms.model.vo.PageVO;
import com.y3tu.yao.upms.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;


@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private ActionEntryMapper actionEntryMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;


    @Override
    public PageVO<MenuQueryVO> page(MenuQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize(), query.getPageNum() != 0);
        List<MenuQueryVO> menuQueryVOList = menuMapper.selectByConditions(query);
        List<MenuVO> menuList = menuMapper.selectAll();
        menuQueryVOList.forEach(menuQueryVO ->menuList.forEach(menu ->{
            if (menuQueryVO.getParentId().equals(menu.getId())){
                menuQueryVO.setParentName(menu.getName());
            }
        } ));
        return new PageVO<>(menuQueryVOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(MenuAddQuery query) {
        SysMenu menu = new SysMenu();
        BeanUtil.copyProperties(query, menu, "actions", "roleIdList");
        menuMapper.insert(menu);

        Integer menuId = menu.getId();

        // 如果角色id列表不为空 插入角色-菜单关联关系表
        List<Integer> roleIdList = query.getRoleIdList();
        List<RoleMenu> roleMenuList = new LinkedList<>();
        if (!roleIdList.isEmpty()) {
            roleIdList.forEach(roleId -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenuList.add(roleMenu);
            });
            roleMenuMapper.batchInsert(roleMenuList);
        }
    }

    @Override
    public void update(MenuUpdateQuery query) {
        SysMenu menu = new SysMenu();
        BeanUtil.copyProperties(query,menu);
        menuMapper.updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer menuId) {
        if (roleMenuMapper.selectByMenuId(menuId).size() > 0) {
            throw new BusinessException("该菜单已经关联角色!");
        }

        // 删除菜单
        menuMapper.deleteById(menuId);
        // 删除菜单下面的按钮
        actionEntryMapper.deleteByMenuId(menuId);
    }

    @Override
    public List<MenuVO> findMenuByRoleCode(String roleCode) {
        return menuMapper.findMenuByRoleCode(roleCode);
    }

    @Override
    public List<MenuVO> list() {
        return menuMapper.selectAll();
    }
}
